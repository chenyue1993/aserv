/**
 * Copyright 2014 ABSir's Studio
 * <p/>
 * All right reserved
 * <p/>
 * Create on 2014年7月28日 下午3:21:31
 */
package com.absir.aserv.support.developer;

import com.absir.context.core.ContextUtils;
import com.absir.core.helper.HelperFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class RenderUtils {

    public static String load(String include, Object... renders) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        IRender.ME.rend(byteArrayOutputStream, include, renders);
        return byteArrayOutputStream.toString(ContextUtils.getCharset().displayName());

    }

    public static String loadExist(String include, Object... renders) throws IOException {
        if (new File(IRender.ME.getRealPath(include, renders)).exists()) {
            return load(include, renders);
        }

        return null;
    }

    public static void generate(String include, Object... renders) throws IOException {
        if (IDeveloper.ME != null) {
            generate(include, IRender.ME.getPath(renders), renders);
        }
    }

    public static void generate(String include, String includeGen, Object... renders) throws IOException {
        if (IDeveloper.ME != null) {
            IDeveloper.ME.generate(IRender.ME.getFullPath(include, renders), IRender.ME.getFullPath(includeGen, renders), renders);
        }
    }

    public static void generate2(String include, String includeGen, String includeGen2, Object... renders) throws IOException {
        if (IDeveloper.ME != null) {
            String full2 = IRender.ME.getFullPath(includeGen2, renders);
            if (HelperFile.fileExists(IRender.ME.getRealPath(full2, renders))) {
                IDeveloper.ME.generate(IRender.ME.getFullPath(include, renders), full2, renders);

            } else {
                IDeveloper.ME.generate(IRender.ME.getFullPath(include, renders), IRender.ME.getFullPath(includeGen, renders), renders);
            }
        }
    }

    public static void include(String include, Object... renders) throws IOException {
        if (IDeveloper.ME != null) {
            include(include, IRender.ME.getPath(renders), renders);
        }
    }

    public static void include(String include, String includeGen, Object... renders) throws IOException {
        generate(include, includeGen, renders);
        IRender.ME.include(include, renders);
    }

    public static boolean includeExist(String path, Object... renders) throws IOException {
        if (new File(IRender.ME.getRealPath(path)).exists()) {
            IRender.ME.include(path, renders);
            return true;
        }

        return false;
    }

    public static void generateTpl(String include, String tpl, Object... renders) throws IOException {
        include = IRender.ME.getFullPath(include);
        if (IDeveloper.ME != null) {
            tpl = IRender.ME.dev(ContextUtils.getContextShortTime()) + "\r\n" + tpl;
            include = IDeveloper.ME.getGeneratePath(include);
        }

        HelperFile.writeStringToFile(new File(IRender.ME.getRealPath(include)), tpl);
    }

}
