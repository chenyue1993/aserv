/**
 * Copyright 2013 ABSir's Studio
 * <p/>
 * All right reserved
 * <p/>
 * Create on 2013-4-6 下午3:05:14
 */
package com.absir.aserv.system.asset;

import com.absir.aserv.menu.MenuContextUtils;
import com.absir.aserv.system.helper.HelperRandom;
import com.absir.bean.basis.Base;
import com.absir.bean.core.BeanFactoryUtils;
import com.absir.bean.inject.value.Value;
import com.absir.bean.lang.LangCodeUtils;
import com.absir.core.kernel.KernelString;
import com.absir.server.in.Input;
import com.absir.server.value.Body;
import com.absir.server.value.Nullable;
import com.absir.server.value.Param;
import com.absir.server.value.Server;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;

@Base
@Server
public class Asset_verify extends AssetServer {

    public static final Asset_verify ME = BeanFactoryUtils.get(Asset_verify.class);

    public static final String CLICK_REFRESH = LangCodeUtils.get("点击刷新", Asset_verify.class);

    @Value(value = "asset.verify.fontSize")
    protected static float fontSize = 18.0f;

    @Value(value = "asset.verify.fontName")
    protected static String fontName = "Times New Roman";

    @Value(value = "asset.verify.minHeight")
    protected static int minHeight = 16;

    @Value(value = "asset.verify.heightWidth")
    protected static float heightWidth = 1.28f;

    @Value(value = "asset.verify.heightDefault")
    protected static float heightDefault = 28.0f;

    public static boolean verifyInput(Input input) {
        return verifyInput(input, null, true);
    }

    public static boolean verifyInput(Input input, String tag, boolean remove) {
        String name = "verifyCode";
        if (tag != null) {
            name += tag;
        }

        String verifyCode = input.getFacade().getSessionValue(name);
        if (verifyCode != null) {
            String paramVerify = input.getParam("verifyCode");
            if (paramVerify != null) {
                if (verifyCode.equals(paramVerify.toLowerCase())) {
                    if (remove) {
                        input.getFacade().removeSession(name);
                    }

                    return true;
                }
            }
        }

        return false;
    }

    public static void verifyCode(int width, int height, int type, String tag, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (height < minHeight) {
            height = minHeight;
        }

        int fontCount = (int) (width * heightWidth / height);
        if (fontCount < 4) {
            fontCount = 4;
            int newWidth = (int) (height / heightWidth);
            if (newWidth > width) {
                width = newWidth;
            }

        } else if (fontCount > 12) {
            fontCount = 12;
        }

        float scale = height / heightDefault;
        int fontSize = (int) (scale * Asset_verify.fontSize);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics graphics = image.getGraphics();
        graphics.setColor(HelperRandom.randColor(200, 250, -1));
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.black);
        System.setProperty("java.awt.headless", "true");
        graphics.setFont(new Font(fontName, Font.PLAIN, fontSize));
        graphics.setColor(HelperRandom.randColor(160, 200, -1));
        int line = (int) (scale * 12);
        for (int i = 0; i < 128; i++) {
            int x0 = HelperRandom.nextInt(width);
            int y0 = HelperRandom.nextInt(height);
            int x1 = HelperRandom.nextInt(line);
            int y1 = HelperRandom.nextInt(line);
            graphics.drawLine(x0, y0, x0 + x1, y0 + y1);
        }

        line = (int) (height * 0.5f + scale * 5.56f);
        float left = width * 0.05f;
        float step = (width - (left * 2.0f)) / (fontCount + 1.0f);
        left -= scale * 4.56f;
        String verifyCode = HelperRandom.randChars(fontCount, type);
        for (char chr : verifyCode.toCharArray()) {
            left += step;
            graphics.setColor(HelperRandom.randColor(20, 130, -1));
            graphics.drawString(String.valueOf(chr), (int) left, line);
        }

        request.getSession().setAttribute(tag == null ? "verifyCode" : ("verifyCode" + tag), verifyCode.toLowerCase());
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 256);
        ServletOutputStream out = response.getOutputStream();

        ImageIO.write(image, "JPEG", out);
        out.flush();
        out.close();
    }

    @Body
    public void route(@Param @Nullable Integer width, @Param @Nullable Integer height, @Nullable @Param String tag, HttpServletRequest request, HttpServletResponse response) throws Exception {
        verifyCode(width == null ? 64 : width, height == null ? 18 : height, 0, tag, request, response);
    }

    public String show(String attrs, int width, int height, Input input) {
        String img = "<img";
        if (!KernelString.isEmpty(attrs)) {
            img += ' ' + attrs;
        }

        img += " class=\"verifyCode\" src=\"" + MenuContextUtils.getSiteRoute() + "asset/verify?m=" + System.currentTimeMillis() + "&width=" + width + "&height=" + height + "\" onclick=\"javascript:this.src=this.src+'&t='+Math.random()\" alt=\"" + input.getLangMessage(CLICK_REFRESH) + "\">";
        return img;
    }
}
