package com.dragon.shadowsocks.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;

/**
 * Created by cjw on 2017/6/25.
 */
public class CommonUtil {
    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    /**
     * 解码base64字符串
     *
     * @param s the s
     * @return the string
     */
    public static String decodeBase64(String s) {
        if (StringUtils.isBlank(s)) {
            return null;
        }

        return new String(Base64.decodeBase64(s));
    }

    /**
     * 编码成base64字符串
     *
     * @param s the s
     * @return the string
     */
    public static String encodeBase64(String s) {
        if (StringUtils.isBlank(s)) {
            return null;
        }

        byte[] bytes = new byte[]{};
        try {
            bytes = s.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.warn(e.getMessage(), e);
        }

        return Base64.encodeBase64String(bytes);
    }

    public static int toInt(String s) {
        if (StringUtils.isBlank(s)) {
            return 0;
        }

        return Integer.parseInt(s);
    }

    public static String getShadowSocksPlistPath() {
        String home = System.getenv("HOME");
        String ShadowSocksPlist = "/Library/Preferences/clowwindy.ShadowsocksX.plist";
        return MessageFormat.format("{0}{1}", home, ShadowSocksPlist);
    }

}
