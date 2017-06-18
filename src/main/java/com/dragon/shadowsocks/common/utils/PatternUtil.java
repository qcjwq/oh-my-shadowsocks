package com.dragon.shadowsocks.common.utils;

/**
 * Created by cjw on 2017/6/11.
 */
public class PatternUtil {
    public static final String ipAddressRegex = "IP Address:<span[^>]*>([\\w\\.]*)</span>";
    public static final String portRegex = "<h4>Port：(\\d*)</h4>";
    public static final String passwordRegex = "Password:<span[^>]*>(\\d*)</span>";
    public static final String methodRegex = "<h4>Method:(.*)</h4>";
}
