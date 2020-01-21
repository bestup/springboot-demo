package com.demo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author halo.l
 * @date 2020-01-21
 */
public class DateUtils {

    public static String dateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
}
