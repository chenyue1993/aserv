/**
 * Copyright 2015 ABSir's Studio
 * <p/>
 * All right reserved
 * <p/>
 * Create on 2015年10月27日 下午7:20:15
 */
package com.absir.client;

import com.absir.core.base.Environment;
import com.absir.core.kernel.KernelByte;
import com.absir.core.kernel.KernelLang.ObjectTemplate;
import com.absir.core.util.UtilActivePool;
import com.absir.core.util.UtilAtom;
import com.absir.core.util.UtilContext;
import com.absir.core.util.UtilPipedStream;
import com.absir.core.util.UtilPipedStream.NextOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class SocketAdapterSel extends SocketAdapter {

    public static final long PIPED_STREAM_TIMEOUT = 30000;

    public static final int POST_BUFF_LEN = 1024;

    private static int buffSize = 1024;

    private static Selector selector;

    private static UtilAtom atom;

    private UtilPipedStream pipedStream;

    private UtilActivePool activePool;

    public static void setBuffSize(int size) {
        if (size < 16) {
            size = 16;
        }

        buffSize = size;
    }

    protected static Selector getAdapterSelector() {
        if (selector == null) {
            try {
                synchronized (SocketAdapterSel.class) {
                    if (selector == null) {
                        selector = Selector.open();
                        atom = new UtilAtom();
                        Thread thread = new Thread() {

                            public void run() {
                                byte[] array = new byte[buffSize];
                                ByteBuffer buffer = ByteBuffer.wrap(array);
                                while (Environment.isStarted()) {
                                    try {
                                        atom.await();
                                        selector.select();
                                        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                                        while (iterator.hasNext()) {
                                            SelectionKey key = iterator.next();
                                            SocketAdapter socketAdapter = null;
                                            SocketChannel socketChannel = null;
                                            try {
                                                socketAdapter = (SocketAdapter) key.attachment();
                                                socketChannel = (SocketChannel) key.channel();
                                                buffer.clear();
                                                int length = socketChannel.read(buffer);
                                                if (length > 0) {
                                                    socketAdapter.receiveByteBuffer(socketChannel.socket(), array, 0,
                                                            length);
                                                    continue;
                                                }

                                            } catch (Exception e) {
                                                Environment.throwable(e);
                                            }

                                            key.cancel();
                                            if (socketAdapter != null && socketChannel != null) {
                                                final SocketAdapter adapter = socketAdapter;
                                                final Socket socket = socketChannel.socket();
                                                UtilContext.executeSecurity(new Runnable() {

                                                    @Override
                                                    public void run() {
                                                        adapter.disconnect(socket);
                                                    }
                                                });
                                            }
                                        }

                                    } catch (Exception e) {
                                        Environment.throwable(e);
                                        break;
                                    }
                                }
                            }

                            ;
                        };

                        thread.setDaemon(true);
                        thread.setName("SocketAdapter.Selector");
                        thread.start();
                    }
                }

            } catch (IOException e) {
                Environment.throwable(e);
            }
        }

        return selector;
    }

    public UtilPipedStream getPipedStream() {
        if (pipedStream == null) {
            pipedStream = new UtilPipedStream(PIPED_STREAM_TIMEOUT);
        }

        return pipedStream;
    }

    public UtilActivePool getActivePool() {
        if (activePool == null) {
            activePool = new UtilActivePool();
        }

        return activePool;
    }

    @Override
    public void close() {
        super.close();
        if (pipedStream != null) {
            pipedStream.close();
            pipedStream = null;
        }

        if (activePool != null) {
            activePool.clear();
        }
    }

    @Override
    public boolean sendData(byte[] buffer, int offset, int length) {
        Socket socket = getSocket();
        if (socket != null) {
            SocketChannel socketChannel = getSocket().getChannel();
            if (socketChannel != null && !socketChannel.isBlocking()) {
                ByteBuffer byteBuffer = ByteBuffer.wrap(buffer, offset, length);
                synchronized (socketChannel) {
                    try {
                        SocketNIO.writeTimeout(socketChannel, byteBuffer);
                        return true;

                    } catch (Exception e) {
                        Environment.throwable(e);
                    }
                }

                disconnect(socket);
                return false;
            }
        }

        return super.sendData(buffer, offset, length);
    }

    protected NextOutputStream createNextOutputStream(int hashIndex) {
        return getPipedStream().createNextOutputStream(hashIndex);
    }

    @Override
    public void receiveCallback(int offset, byte[] buffer, byte flag, Integer callbackIndex) {
        if ((flag & STREAM_CLOSE_FLAG) != 0) {
            int length = buffer.length;
            int streamIndex = getVarints(buffer, offset, length);
            if (length == offset + getVarintsLength(streamIndex)) {
                if ((flag & POST_FLAG) != 0) {
                    if (activePool != null) {
                        getActivePool().remove(streamIndex);
                    }

                } else {
                    NextOutputStream outputStream = getPipedStream().getOutputStream(streamIndex);
                    if (outputStream != null) {
                        try {
                            outputStream.close();

                        } catch (IOException e) {
                            Environment.throwable(e);
                        }
                    }
                }

                return;
            }
        }

        super.receiveCallback(offset, buffer, flag, callbackIndex);
    }

    @Override
    public void receiveCallback(CallbackAdapter callbackAdapter, int offset, byte[] buffer, byte flag,
                                Integer callbackIndex) {
        if ((flag & STREAM_FLAG) != 0) {
            int length = buffer.length;
            int streamIndex = getVarints(buffer, offset, length);
            int indexLength = getVarintsLength(streamIndex);
            int offsetIndex = offset + indexLength;
            if (length > offsetIndex) {
                // 写入流信息
                NextOutputStream outputStream = getPipedStream().getOutputStream(streamIndex);
                if (outputStream != null) {
                    try {
                        outputStream.write(buffer, offsetIndex, length);
                        return;

                    } catch (IOException e) {
                        Environment.throwable(e);
                    }
                }

                sendData(sendDataBytes(0, buffer, offset, indexLength, true, false, STREAM_CLOSE_FLAG, 0,
                        null, 0, 0));
                return;

            } else if (length == offsetIndex) {
                if (callbackAdapter instanceof CallbackAdapterStream) {
                    try {
                        NextOutputStream outputStream = createNextOutputStream(streamIndex);
                        if (outputStream == null) {
                            sendData(sendDataBytes(0, buffer, offset, indexLength, true, false,
                                    (byte) (STREAM_CLOSE_FLAG | POST_FLAG), 0, null, 0, 0));

                        } else {
                            PipedInputStream inputStream = new PipedInputStream();
                            outputStream.connect(inputStream);
                            CallbackAdapterStream callbackAdapteStream = (CallbackAdapterStream) callbackAdapter;
                            callbackAdapter = null;
                            callbackAdapteStream.doWith(this, offset, buffer, inputStream);
                            return;
                        }

                    } catch (Exception e) {
                        LOGGER.error("receiveCallbackStream", e);
                    }

                    if (callbackAdapter != null) {
                        callbackAdapter.doWith(this, offset, null);
                    }

                    return;
                }
            }
        }

        super.receiveCallback(callbackAdapter, offset, buffer, flag, callbackIndex);
    }

    @Override
    public synchronized void receiveSocketChannelStart() {
        if (receiveStarted) {
            return;
        }

        SocketChannel socketChannel = getSocket().getChannel();
        if (socketChannel != null) {
            receiveStarted = true;
            try {
                clearReceiveBuff();
                registerSelector(socketChannel);

            } catch (IOException e) {
                Environment.throwable(e);
            }
        }
    }

    protected void registerSelector(SocketChannel socketChannel) throws IOException {
        socketChannel.configureBlocking(false);
        Selector selector = getAdapterSelector();
        try {
            atom.increment();
            selector.wakeup();
            socketChannel.register(selector, SelectionKey.OP_READ, this);

        } finally {
            atom.decrement();
        }
    }

    /**
     * @return make varints mode right set postBuffLen 128(127 VARINTS_1_LENGTH) ~ 10240(16383 VARINTS_2_LENGTH) - 32
     */
    protected int getPostBuffLen() {
        return POST_BUFF_LEN;
    }

    protected RegisteredRunnable sendStream(byte[] dataBytes, boolean human, final int callbackIndex,
                                            final InputStream inputStream, final CallbackTimeout callbackTimeout, final long timeout) {
        connect();
        boolean sended = false;
        ObjectTemplate<Integer> template = getActivePool().addObject();
        final ObjectTemplate<ObjectTemplate<Integer>> nextTemplate = new ObjectTemplate<ObjectTemplate<Integer>>(
                template);
        if (template == null) {
            return null;
        }

        final int streamIndex = template.object;
        final int indexLength = getVarintsLength(streamIndex);
        try {
            final byte[] buffer = sendDataBytes(indexLength, dataBytes, true, human, STREAM_FLAG, callbackIndex, null);
            int offLen = getVarintsLength(buffer, 0, 4) + 1;
            setVarintsLength(buffer, offLen, streamIndex);
            final Runnable postRunnable = new Runnable() {

                @Override
                public void run() {
                    ObjectTemplate<Integer> nextIndex = nextTemplate.object;
                    try {
                        byte[] sendBuffer = sendDataBytes(indexLength, null, 0, 0, true, false, (byte) (STREAM_FLAG | POST_FLAG), 0,
                                null, 0, getPostBuffLen(), true);
                        setVarintsLength(sendBuffer, 3, streamIndex);
                        int length = buffer.length;
                        int postOff = 3 + indexLength;
                        int len;
                        try {
                            while ((len = inputStream.read(sendBuffer, postOff, length)) > 0) {
                                len += postOff - 2;
                                sendBuffer[0] = (byte) ((len & 0x7F) | 0x80);
                                sendBuffer[1] = (byte) ((len >> 7) & 0x7F);

                                if (nextIndex.object == null || !sendData(sendBuffer, 0, len + 2)) {
                                    return;
                                }

                                if (callbackTimeout != null) {
                                    if (callbackTimeout.socketAdapter == null) {
                                        return;
                                    }

                                    callbackTimeout.timeout = UtilContext.getCurrentTime() + timeout;
                                }
                            }

                        } catch (Exception e) {
                            Environment.throwable(e);
                            return;
                        }

                    } finally {
                        activePool.remove(streamIndex);
                        UtilPipedStream.closeCloseable(inputStream);
                    }

                    sendData(sendDataBytes(0, KernelByte.getLengthBytes(streamIndex), true, false,
                            STREAM_CLOSE_FLAG | POST_FLAG, 0, null));
                }
            };

            if (registered && sendData(buffer)) {
                UtilContext.getThreadPoolExecutor().execute(postRunnable);
                sended = false;
                return null;
            }

            RegisteredRunnable runnable = new RegisteredRunnable() {

                @Override
                public void doRun() {
                    ObjectTemplate<Integer> nextIndex = getActivePool().addObject();
                    if (nextIndex == null) {
                        failed = true;
                        return;
                    }

                    boolean sended = false;
                    try {
                        KernelByte.setLength(buffer, buffer.length - 4, nextIndex.object);
                        failed = !sendData(buffer);
                        if (!failed) {
                            nextTemplate.object = nextIndex;
                            UtilContext.getThreadPoolExecutor().execute(postRunnable);
                            sended = true;
                        }

                    } finally {
                        if (!sended) {
                            activePool.remove(nextIndex.object);
                            UtilPipedStream.closeCloseable(inputStream);
                        }
                    }
                }
            };

            addRegisterRunnable(runnable);
            return runnable;

        } finally {
            if (!sended) {
                activePool.remove(streamIndex);
                UtilPipedStream.closeCloseable(inputStream);
            }
        }
    }

    /**
     * 发送目标数据
     */
    public void sendStreamIndex(int callbackIndex, byte[] dataBytes, boolean head, boolean human,
                                InputStream inputStream, int timeout, CallbackAdapter callbackAdapter) {
        if (callbackIndex == 0 || inputStream == null) {
            sendDataIndex(callbackIndex, dataBytes, head, human, null, timeout, callbackAdapter);

        } else {
            CallbackTimeout callbackTimeout = null;
            if (callbackAdapter != null) {
                callbackTimeout = putReceiveCallbacks(callbackIndex, timeout, callbackAdapter);
            }

            RegisteredRunnable registeredRunnable = sendStream(dataBytes, human, callbackIndex, inputStream,
                    callbackTimeout, timeout);
            if (callbackTimeout != null) {
                callbackTimeout.registeredRunnable = registeredRunnable;
            }
        }
    }

    public static interface CallbackAdapterStream extends CallbackAdapter {

        public void doWith(SocketAdapter adapter, int offset, byte[] buffer, InputStream inputStream);
    }

}
