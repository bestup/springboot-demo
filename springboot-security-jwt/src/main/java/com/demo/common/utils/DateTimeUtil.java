package com.demo.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @description: 时间工具类
 */
public class DateTimeUtil {

    public final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static SimpleDateFormat sdf_date_format = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 获取当前时间的YYYY-MM-DD HH:mm:ss格式
     *
     * @return
     */
    public static String getTime() {
        return sdfTime.format(new Date());
    }

    /**
     * 日期比较，如果s>=e 返回true 否则返回false
     * @param s
     * @param e
     * @return
     */
    public static boolean compareDate(String s, String e) {
        if(fomatDate(s)==null||fomatDate(e)==null){
            return false;
        }
//        return fomatDate(s).getTime() >=fomatDate(e).getTime();
        return s.compareTo(e)>0;
    }

    /**
     * 格式化日期
     * @param date
     * @return
     */
    public static Date fomatDate(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前时间的后i天
     * @param i
     * @return
     */
    public static String getAddDay(int i){
        String currentTime = DateTimeUtil.getTime();
        GregorianCalendar gCal = new GregorianCalendar(
                Integer.parseInt(currentTime.substring(0, 4)),
                Integer.parseInt(currentTime.substring(5, 7)) - 1,
                Integer.parseInt(currentTime.substring(8, 10)));
        gCal.add(GregorianCalendar.DATE, i);
        return sdf_date_format.format(gCal.getTime());
    }

    /**
     * 获取当前时间的后i天
     * 精确到秒
     * @param i
     * @return
     */
    public static String getAddDayTime(int i){
        Date date = new Date(System.currentTimeMillis()+i*24*60*60*1000);
        return sdfTime.format(date);
    }

    /**
     * 获取当前时间的+多少秒
     * 精确到秒
     * @param i
     * @return
     */
    public static String getAddDaySecond(int i){
        Date date = new Date(System.currentTimeMillis()+i*1000);
        return sdfTime.format(date);
    }
    
    /**
	 * 获取n日之前或者之后的日期
	 * 
	 * @return
	 */
	public static String format(String dateTime, String srcFormat, String destFormat) {
		SimpleDateFormat srcSimpleDateFormat = new SimpleDateFormat(srcFormat);
		Date date = null;
		try {
			date = srcSimpleDateFormat.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (null == date) {
			return null;
		}

		SimpleDateFormat destSimpleDateFormat = new SimpleDateFormat(destFormat);
		return destSimpleDateFormat.format(date);
	}

	/**
	 * 
	 * @param src
	 * @return
	 */
	public static String afterNowByMinutes(int minutes) {
		long dstTime = new Date().getTime() + minutes * 60 * 1000;
		Date dstDate = new Date(dstTime);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dstDate);
	}

	/**
	 * 获取当前的年月日时分秒
	 * 
	 * @return
	 */
	public static String getCurrentDataTime() {
		Date date = new Date();
		// 转换提日期输出格式
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

	/**
	 * 获取当前的年月日
	 * 
	 * @return
	 */
	public static String getCurrentData() {
		Date date = new Date();
		// 转换提日期输出格式
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}

	public static void main(String[] args) {
		printWeekdays();
	}

	private static final int FIRST_DAY = Calendar.MONDAY;

	private static void printWeekdays() {
		Calendar calendar = Calendar.getInstance();
		setToFirstDay(calendar);
		for (int i = 0; i < 7; i++) {
			printDay(calendar);
			calendar.add(Calendar.DATE, 1);
		}
	}

	private static void setToFirstDay(Calendar calendar) {
		while (calendar.get(Calendar.DAY_OF_WEEK) != FIRST_DAY) {
			calendar.add(Calendar.DATE, -1);
		}
	}

	private static void printDay(Calendar calendar) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(dateFormat.format(calendar.getTime()));
	}

	/**
	 * 获取当前月第一天
	 * 
	 * @return
	 */
	public static String getCurrentMonthFirstDay() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		return format.format(calendar.getTime());
	}

	/**
	 * 获取当前月最后一天
	 * 
	 * @return
	 */
	public static String getCurrentMonthLastDay() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return format.format(calendar.getTime());
	}

	/**
	 * 获取n日之前或者之后的日期
	 * 
	 * @return
	 */
	public static String getDay(int n) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, n);
		return simpleDateFormat.format(calendar.getTime());
	}

	/**
	 * 获取n月之前或者之后的月份
	 * 
	 * @return
	 */
	public static String getMonth(int n) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, n);
		return simpleDateFormat.format(calendar.getTime());
	}

	/**
	 * 获取n年之前或者之后的年份
	 * 
	 * @return
	 */
	public static String getYear(int n) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, n);
		return simpleDateFormat.format(calendar.getTime());
	}

	/**
	 * 获取当前月第一天的日期和时间
	 * 
	 * @return
	 */
	public static String getCurrentMonth() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		return format.format(calendar.getTime());
	}
	
	
	/**
	 * 将时间戳转换为日期
	 * 
	 * @return
	 */
	public static String stampToDate(long time) {
		String dateStr;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(time);
		dateStr = simpleDateFormat.format(date);
		return dateStr;
	}
	
	
	/**
	 * yyyy-MM-dd HH:mm:ss字符串转成日期
	 * 
	 * @return @throws
	 */
	public static Date stringDateconvertToDate(String date) {
		// 转换日期输出格式
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date newDate = null;
		try {
			newDate = dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;
	}
	
	/**
	 * 获取n日之前或者之后的00时00分00秒
	 * 
	 * @return
	 */
	public static String getDate(int n) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, n);
		return simpleDateFormat.format(calendar.getTime());
	}
}
