package com.moil.hafen.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 时间工具类
 */
public class DateUtil {

    public static final String FULL_TIME_PATTERN = "yyyyMMddHHmmss";

    public static final String FULL_TIME_SPLIT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String YMD = "yyyy-MM-dd";
    public static final String YMD1 = "yyyyMMdd";

    public static final String YM = "yyyy-MM";

    public static String formatFullTime(LocalDateTime localDateTime) {
        return formatFullTime(localDateTime, FULL_TIME_PATTERN);
    }

    public static String formatFullTime(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(dateTimeFormatter);
    }
    public static String formatLocalDate(LocalDate localDate, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return localDate.format(dateTimeFormatter);
    }

    public static String getDateFormat(Date date, String dateFormatType) {
        SimpleDateFormat simformat = new SimpleDateFormat(dateFormatType);
        return simformat.format(date);
    }

    public static String formatCSTTime(String date, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date d = sdf.parse(date);
        return DateUtil.getDateFormat(d, format);
    }

    public static String convertTimestampDateTimeString(long timesStamp) {
        return String.valueOf(timesStamp);
    }
    public static String format2(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    public static Date addDays(Date date,int days) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        //把日期往后增加一天,整数  往后推,负数往前移动
        calendar.add(Calendar.DATE,days);
        //这个时间就是日期往后推一天的结果
        return calendar.getTime();
    }
    public static Date addYear(Date date,int year) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        //把日期往后增加一年,整数  往后推,负数往前移动
        calendar.add(Calendar.YEAR,year);
        //这个时间就是日期往后推一年的结果
        return calendar.getTime();
    }

    public static int betweenDays(Date date1, Date date2) {
        return betweenDays(getDateFormat(date1, "yyyy-MM-dd"), getDateFormat(date2, "yyyy-MM-dd"));
    }
    public static int betweenDays(String date1, String date2) {
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //开始时间
            Date star = dft.parse(date1);
            //结束时间
            Date endDay=dft.parse(date2);
            Long starTime=star.getTime();
            Long endTime=endDay.getTime();
            //时间戳相差的毫秒数
            Long num=endTime-starTime;
            //除以一天的毫秒数
            return (int)(num/24/60/60/1000);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public static Date string2Date(String dateStr, String formart){
        DateFormat dft = new SimpleDateFormat(formart);
        try {
            return dft.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static LocalDate getLocalDate(Date date){
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        // atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        return localDate;
    }
    public static LocalDate getLocalDate(String date, String pattern){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        // atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
        LocalDate localDate = LocalDate.parse(date,dateTimeFormatter);
        return localDate;
    }

    private final static int[] dayArr = new int[] { 20, 19, 21, 20, 21, 22, 23,
            23, 23, 24, 23, 22 };
    private final static String[] constellationArr = new String[] { "摩羯座",
            "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座",
            "天蝎座", "射手座", "摩羯座" };

    /**
     * 根据出生日期计算属相和星座
     *
     * @param args
     */
    public static void main(String[] args) {
        int month = 1;
        int day = 8;
        System.out.println("星座为：" + getConstellation(month, day));
        System.out.println("属相为:" + getYear(1988));

    }

    /**
     * Java通过生日计算星座
     *
     * @param month
     * @param day
     * @return
     */
    public static String getConstellation(int month, int day) {
        return day < dayArr[month - 1] ? constellationArr[month - 1]
                : constellationArr[month];
    }

    /**
     * 通过生日计算属相
     *
     * @param year
     * @return
     */
    public static String getYear(int year) {
        if (year < 1900) {
            return "未知";
        }
        int start = 1900;
        String[] years = new String[] { "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊",
                "猴", "鸡", "狗", "猪" };
        return years[(year - start) % years.length];
    }

}
