package com.qk365.datadict.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.Timestamp;
import java.util.Date;

public class DateUtils {

    public static final String FORMAT1 = "yyyy-MM-dd";

    public static final String FORMAT2 = "yyyyMMdd";

    public static final String FORMAT3 = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMAT4 = "yyyyMMddHHmmss";

    public static final String FORMAT5 = "yyyy-MM-dd HH:mm:ss:SSS";

    public static final String FORMAT6 = "yyyyMMddHHmmssSSS";

    public static final String FORMAT7 = "yyyy-MM";

    public static final String FORMAT8 = "yyyyMM";

    public static final String FORMAT9 = "yyyy年MM月dd日";

    /**
     * 根据传入的format格式，把date转成string
     *
     * @param format
     * @param date
     * @return
     */
    public static String dateToStringByFormat(String format, Date date) {

        if (null == date) {
            date = new Date();
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(format);
    }

    /**
     * 根据传入的format格式，把timestamp转成string
     *
     * @param format
     * @param ts
     * @return
     */
    public static String timestampToStringByFormat(String format, Timestamp ts) {
        if (null == ts) {
            ts = new Timestamp(System.currentTimeMillis());
        }
        DateTime dateTime = new DateTime(ts);
        return dateTime.toString(format);
    }

    /**
     * 把字符串转成date
     *
     * @param dateString
     * @return
     */
    public static Date stringToDate(String dateString) {
        if (StringUtils.isBlank(dateString)) {
            return null;
        }
        return new DateTime(dateString).toDate();
    }

    /**
     * 把字符串转成timestamp
     *
     * @param dateString
     * @return
     */
    public static Timestamp stringToTimestamp(String dateString) {
        if (StringUtils.isBlank(dateString)) {
            return null;
        }
        Date date = new DateTime(dateString).toDate();
        return new Timestamp(date.getTime());
    }

    /**
     * 根据字符串时间以及格式转成timestamp
     *
     * @param dateString
     * @param format
     * @return
     */
    public static Timestamp stringToTimestamp(String dateString, String format) {
        if (StringUtils.isBlank(dateString)) {
            return null;
        }
        DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
        Date date = DateTime.parse(dateString, fmt).toDate();
        return new Timestamp(date.getTime());
    }

    /**
     * 把字符串转timestamp 加减num
     *
     * @param dateString
     * @param format
     * @param num
     * @return
     */
    public static Timestamp addOrSubtractTimestamp(String dateString, String format, Integer num) {
        if (StringUtils.isBlank(dateString)) {
            return null;
        }
        if (null == num) {
            num = 0;
        }
        DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
        DateTime dt = DateTime.parse(dateString, fmt).plusDays(num).dayOfMonth().getDateTime();
        return new Timestamp(dt.toDate().getTime());
    }

    /**
     * 得到月底的时间戳日期
     *
     * @param dateString
     * @param format
     * @return
     */
    public static Timestamp getMinTimestamp(String dateString, String format) {
        if (StringUtils.isBlank(dateString)) {
            return null;
        }
        DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
        DateTime dt = DateTime.parse(dateString, fmt);
        Date date = dt.dayOfMonth().withMinimumValue().toDate();
        return new Timestamp(date.getTime());
    }

    /**
     * 得到月底的时间戳日期
     *
     * @param dateString
     * @param format
     * @return
     */
    public static Timestamp getMaxTimestamp(String dateString, String format) {
        if (StringUtils.isBlank(dateString)) {
            return null;
        }
        DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
        DateTime dt = DateTime.parse(dateString, fmt);
        Date date = dt.dayOfMonth().withMaximumValue().toDate();
        return new Timestamp(date.getTime());
    }

    /**
     * 获取date增加或减少天数后的日期
     *
     * @param day
     * @param date
     * @return
     */
    public static Date addOrSubtractDay(int day, Date date) {
        if (null == date) {
            date = new Date();
        }

        DateTime dateTime = new DateTime(date);
        DateTime dt = dateTime.plusDays(day).dayOfYear().getDateTime();
        return dt.toDate();
    }

    public static String addOrSubtractDay(int day, Date date, String format) {
        if (null == date) {
            date = new Date();
        }

        DateTime dateTime = new DateTime(date);
        DateTime dt = dateTime.plusDays(day).dayOfYear().getDateTime();
        return dt.toString(format);
    }

    /**
     * 获取date增加或减少天数后的日期
     *
     * @param month
     * @param date
     * @return
     */
    public static String addOrSubtractMonth(int month, Date date, String format) {

        if (null == date) {
            return null;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMonths(month).dayOfMonth().getDateTime()
                .toString(format);
    }

    /**
     * 获取date的所在月的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDay(Date date) {
        if (null == date) {
            date = new Date();
        }
        DateTime dateTime = new DateTime(date);

        return dateTime.dayOfMonth().withMinimumValue().toDate();
    }

    /**
     * 获取当月第一天的格式化时间字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String getFirstDay(Date date, String format) {
        if (null == date) {
            date = new Date();
        }
        DateTime dateTime = new DateTime(date);
        DateTime dt = dateTime.dayOfMonth().withMinimumValue().toDateTime();
        return dt.toString(format);
    }

    public static String getLastDay(Date date, String format) {
        if (null == date) {
            date = new Date();
        }
        DateTime dateTime = new DateTime(date);
        DateTime dt = dateTime.dayOfMonth().withMaximumValue().toDateTime();
        return dt.toString(format);
    }

    /**
     * 获取date的所在月的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDay(Date date) {
        if (null == date) {
            date = new Date();
        }
        DateTime dateTime = new DateTime(date);

        return dateTime.dayOfMonth().withMaximumValue().toDate();
    }

    /**
     * 根据字符串以及format 得到DateTime
     *
     * @param date
     * @param format
     * @return
     */
    public static DateTime getDateTimeByStr(String date, String format) {
        if (null == date) {
            return null;
        }
        DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
        return DateTime.parse(date, fmt);
    }

    /**
     * 获取两个时间的相差天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int differDay(Date startDate, Date endDate) {
        if (null == startDate || null == endDate) {
            return 0;
        }
        DateTime start = new DateTime(startDate);
        DateTime end = new DateTime(endDate);
        Period period = new Period(start, end, PeriodType.days());
        return Math.abs(period.getDays());
    }

    /**
     * 获取两个时间的相差天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int differDayStr(String startDate, String endDate) {
        if (null == startDate || null == endDate) {
            return 0;
        }
        DateTime start = DateTime.parse(startDate);
        DateTime end = DateTime.parse(endDate);
        Period period = new Period(start, end, PeriodType.days());
        return Math.abs(period.getDays());
    }

    /**
     * 判断两个date是否是同年月或者同一天 type=0时，判断是否是同年月； type=1时，判断是否是同一天
     *
     * @param date
     * @param anotherDate
     * @param type
     * @return
     */
    public static boolean isSameMonthOrDay(Date date, Date anotherDate, int type) {
        if (null == date || null == anotherDate) {
            return false;
        }
        DateTime dtOne = new DateTime(date);
        DateTime dtTwo = new DateTime(anotherDate);
        String dtStrOne = null;
        String dtStrTwo = null;
        if (0 == type) {
            dtStrOne = dtOne.toString(FORMAT7);
            dtStrTwo = dtTwo.toString(FORMAT7);
        }
        if (1 == type) {
            dtStrOne = dtOne.toString(FORMAT1);
            dtStrTwo = dtTwo.toString(FORMAT1);
        }
        if (dtStrOne.equals(dtStrTwo)) {
            return true;
        }
        return false;
    }

    /**
     * 把date转换成中文年月日
     *
     * @param date
     * @return
     */
    public static String dateToStringOfCN(Date date) {
        if (null == date) {
            date = new Date();
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(FORMAT9);
    }

    /**
     * 判断date是否是闰年
     *
     * @param date
     * @return
     */
    public static boolean isLeapYear(Date date) {
        if (null == date) {
            return false;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.year().isLeap();
    }

    /**
     * 根据年月获得月份最大天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int getDaysByYearAndMonth(int year, int month) {
        DateTime dateTime = new DateTime(year, month, 1, 0, 0);

        int maximumValue = dateTime.dayOfMonth().getMaximumValue();

        return maximumValue;
    }

    /**
     * 根据字符串时间查询时间的最大天数
     *
     * @param date
     * @return
     */
    public static int getDaysByDateStr(String date, String format) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
        DateTime dateTime = DateTime.parse(date, fmt);
        int maximumValue = dateTime.dayOfMonth().getMaximumValue();

        return maximumValue;
    }

    /**
     * 根据当前时间获取上个月1号字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String getLastMonthFirstDayByNow(Date date, String format) {
        if (null == date) {
            date = new Date();
        }

        DateTime dt = new DateTime(date);
        return dt.minusMonths(1).dayOfMonth().withMinimumValue()
                .toString(format);
    }

    /**
     * 根据字符串时间格式求加减num个月的字符串时间
     *
     * @param date
     * @param num
     * @param format
     * @return
     */
    public static String addOrSubtractMonth(String date, int num, String format) {
        if (null == date) {
            return null;
        }

        DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
        DateTime dateTime = DateTime.parse(date, fmt);
        return dateTime.plusMonths(num).dayOfMonth().getDateTime()
                .toString(format);
    }

    /**
     * 根据当前时间获取上个月月底字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String getLastMonthEndDayByNow(Date date, String format) {
        if (null == date) {
            date = new Date();
        }

        DateTime dt = new DateTime(date);
        return dt.minusMonths(1).dayOfMonth().withMaximumValue()
                .toString(format);
    }

    /**
     * 根据当前时间获取上个月年月
     *
     * @param date
     * @param format
     * @return
     */
    public static String getLastMonth(Date date, String format) {
        if (null == date) {
            date = new Date();
        }
        DateTime dt = new DateTime(date);
        DateTime newDt = dt.minusMonths(1).dayOfMonth().getDateTime();
        return newDt.toString(format);
    }

    public static String getChMonthAndDay(String dateStr) {
        if (StringUtils.isNotBlank(dateStr)) {
            String[] split = dateStr.split("-");
            if (null != split && split.length > 0) {
                StringBuilder sb = new StringBuilder();
                String month = split[1];
                String day = split[2];
                sb.append(Integer.valueOf(month) + "月");
                sb.append(Integer.valueOf(day) + "日");
                return sb.toString();
            }
        }
        return null;
    }

    /**
     * ts1 大于 ts2 return 1
     * ts1 等于 ts2 return 0
     * ts1 小于 ts2 return -1
     *
     * @param ts1
     * @param ts2
     * @return
     */
    public static int compareDate(Timestamp ts1, Timestamp ts2) {
        long time1 = ts1.getTime();
        long time2 = ts2.getTime();
        if (time1 > time2) {
            return 1;
        } else if (time1 < time2) {
            return -1;
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
//		System.out.println(getDaysByDateStr("201802", FORMAT8));
//		System.out.println(addOrSubtractDay(5, new Date()));
//		System.out.println(addOrSubtractMonth("201803", -1, DateUtils.FORMAT8));
//		System.out.println(getLastMonth(new Date(), DateUtils.FORMAT8));
//		System.out.println(getLastMonthFirstDayByNow(new Date(), DateUtils.FORMAT1));
//		System.out.println(getFirstDay(new Date(), DateUtils.FORMAT1));
//		System.out.println(addOrSubtractTimestamp("2017-08-21", DateUtils.FORMAT1, -5));
//		System.out.println(getChMonthAndDay("2018-02-05"));
//		System.out.println(stringToTimestamp("201810", DateUtils.FORMAT8));
        System.out.println(getFirstDay(new Date()));
    }

}
