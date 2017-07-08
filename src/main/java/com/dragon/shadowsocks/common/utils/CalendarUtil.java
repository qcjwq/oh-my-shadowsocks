package com.dragon.shadowsocks.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by cjw on 2017/7/8.
 */
public final class CalendarUtil {
    public static String format(Calendar calendar) {
        return format(calendar, "yyyy-MM-dd HH:mm:ss");
    }

    public static String format(Calendar calendar, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        date.setTime(calendar.getTimeInMillis());
        return simpleDateFormat.format(date);
    }
}
