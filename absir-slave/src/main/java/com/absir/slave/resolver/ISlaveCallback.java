/**
 * Copyright 2015 ABSir's Studio
 * <p/>
 * All right reserved
 * <p/>
 * Create on 2015年4月9日 下午7:36:21
 */
package com.absir.slave.resolver;

import com.absir.client.SocketAdapter.CallbackAdapter;
import com.absir.core.kernel.KernelList.Orderable;

public interface ISlaveCallback extends CallbackAdapter, Orderable {

    public int getCallbackIndex();

}
