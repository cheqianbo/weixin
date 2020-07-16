package com.onesuo.app.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {
   public static final String FMT_DATE = "yyyy-MM-dd";

   public static final String FMT_DATE_SE = "MM/dd/yyyy";

   public static final String FMT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

   public static final String FMT_DATE_TIME_MM = "yyyy-MM-dd HH:mm";

   public static final String FMT_DATE_TIME_mm = "yyyyMMdd HH:mm";

   public static final String FMT_POINT_DATE = "yyyy.MM.dd";

   public static final String FMT_DATE_HOUR_H_M = "HH:mm";

   public static final String FMT_DATE_YEAR = "yyyy";

   public static final String FMT_NO_SYMBOL_DATE_ = "yyyyMMdd";

   public static final String FMT_NO_SYMBOL_DATE_TIME = "yyyyMMddHHmmss";

   public static final String FMT_NO_SYMBOL_DATE_TIME_SSS = "yyyyMMddHHmmssSSS";

   public static final String FMT_NO_SYMBOL_DATE_TIME_mm = "yyyyMMddHH";

   public static final String FMT_DATE_CHINESE = "yyyy年MM月dd日";

   public static final String FMT_DATE_TIME_CHINESE = "yyyy年MM月dd日 HH时mm分ss秒";

   public static String fromTimeStamp2StringDate(long second) {
      SimpleDateFormat sdf = new SimpleDateFormat(FMT_DATE);
      return sdf.format(new Date(second * 1000));
   }

   public static String fromTimeStamp2StringDateStr(long millSecond) {
      SimpleDateFormat sdf = new SimpleDateFormat(FMT_DATE_CHINESE);
      return sdf.format(new Date(millSecond));
   }

   public static String fromTimeStamp2StringDateTime(long second) {
      SimpleDateFormat sdf = new SimpleDateFormat(FMT_DATE_TIME);
      return sdf.format(new Date(second * 1000));
   }

   /**
    * 获取当天开始时间
    * @param date
    * @return
    */
   public static Date getStartTime(Date date) {
      Calendar dateStart = Calendar.getInstance();
      dateStart.setTime(date);
      dateStart.set(Calendar.HOUR_OF_DAY, 0);
      dateStart.set(Calendar.MINUTE, 0);
      dateStart.set(Calendar.SECOND, 0);
      dateStart.set(Calendar.MILLISECOND, 0);
      return dateStart.getTime();
   }

   /**
    * 获取当天结束时间
    * @param date
    * @return
    */
   public static Date getEndTime(Date date) {
      Calendar dateEnd = Calendar.getInstance();
      dateEnd.setTime(date);
      dateEnd.set(Calendar.HOUR_OF_DAY, 23);
      dateEnd.set(Calendar.MINUTE, 59);
      dateEnd.set(Calendar.SECOND, 59);
      dateEnd.set(Calendar.MILLISECOND, 0);
      return dateEnd.getTime();
   }

   /**
    * 获取下一个小时的指定分钟
    *
    * @param nextMinute
    * @return
    */
   public static Date getNextMinuteHour(int nextMinute) {
      Calendar c = Calendar.getInstance();

      int currentMinute = c.get(Calendar.MINUTE);
      //时间超过指定分钟，设置为小一个小时的指定分钟
      if (currentMinute >= nextMinute) {
         int currentHour = c.get(Calendar.HOUR);
         if (currentHour == 23) {
            c.add(Calendar.DATE, 1);
            c.set(Calendar.HOUR, 0);
         } else {
            c.add(Calendar.HOUR, 1);
         }
      }

      c.set(Calendar.MINUTE, nextMinute);
      c.set(Calendar.SECOND, 0);

      return c.getTime();
   }

   /**
    * 获取为大于当前时间的整点或者半点时间，忽略秒
    *
    * @return
    */
   public static Date get0And30Time() {
      Calendar c = Calendar.getInstance();

      int minute = c.get(Calendar.MINUTE);

      if (minute >= 0 && minute < 30) {
         c.add(Calendar.MINUTE, 30 - minute);
      } else {
         c.add(Calendar.MINUTE, 60 - minute);
      }

      return c.getTime();
   }

   /**
    * 获取为大于当前时间的整点时间
    *
    * @return
    */
   public static Date get0Time() {
      Calendar c = Calendar.getInstance();

      int minute = c.get(Calendar.MINUTE);
      c.add(Calendar.MINUTE, 60 - minute);
      c.set(Calendar.SECOND, 0);
      return c.getTime();
   }

   /**
    * 获取为大于当前时间的整点时间1秒
    *
    * @return
    */
   public static Date get01Time() {
      Calendar c = Calendar.getInstance();

      int minute = c.get(Calendar.MINUTE);
      c.add(Calendar.MINUTE, 60 - minute);
      c.set(Calendar.SECOND, 1);
      return c.getTime();
   }

   public static int getDayOfYear() {
      return Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
   }

   /**
    * 获取为大于当前时间的整点时间或半的1秒
    *
    * @return
    */
   public static Date getHmTime() {
      Calendar c = Calendar.getInstance();

      int minute = c.get(Calendar.MINUTE);
      if (minute > 50) {
         c.add(Calendar.MINUTE, 60 - minute);
      } else {
         c.add(Calendar.MINUTE, 50 - minute);
      }
      c.set(Calendar.SECOND, 1);
      return c.getTime();
   }

   // 获取第2天0点2分的时间
   public static Date get2Past0Time() {
      Calendar c = Calendar.getInstance();

      c.set(Calendar.HOUR_OF_DAY, 0);
      c.set(Calendar.MINUTE, 2);
      c.set(Calendar.SECOND, 0);
      c.add(Calendar.DATE, 1);
      return c.getTime();
   }

   // 获取第2天0点1分的时间
   public static Date get1Past0Time() {
      Calendar c = Calendar.getInstance();

      c.set(Calendar.HOUR_OF_DAY, 0);
      c.set(Calendar.MINUTE, 1);
      c.set(Calendar.SECOND, 0);
      c.add(Calendar.DATE, 1);
      return c.getTime();
   }

   // 第二天 12:01的时间
   public static Date getNext1201Time() {
      Calendar c = Calendar.getInstance();

      c.set(Calendar.HOUR_OF_DAY, 12);
      c.set(Calendar.MINUTE, 1);
      c.set(Calendar.SECOND, 0);
      c.add(Calendar.DATE, 1);
      return c.getTime();
   }

   // 当前12:01的时间
   public static Date getCurrent1201Time() {
      Calendar c = Calendar.getInstance();

      c.set(Calendar.HOUR_OF_DAY, 12);
      c.set(Calendar.MINUTE, 1);
      c.set(Calendar.SECOND, 0);
      return c.getTime();
   }

   // 获取当前小时的秒
   public static Date getCurrentHourSeconds() {
      Calendar c = Calendar.getInstance();
      c.set(Calendar.MINUTE, 0);
      c.set(Calendar.SECOND, 0);
      return c.getTime();
   }

   // 获取下一个是n倍数的小时
   public static Date getNextModNHourTime(int n) {
      Calendar c = Calendar.getInstance();
      c.add(Calendar.HOUR_OF_DAY, n - c.get(Calendar.HOUR_OF_DAY) % n);
      c.set(Calendar.MINUTE, 0);
      c.set(Calendar.SECOND, 0);
      return c.getTime();
   }

   public static Date get2SecondPast0Time() {
      Calendar c = Calendar.getInstance();

      c.set(Calendar.HOUR_OF_DAY, 0);
      c.set(Calendar.MINUTE, 0);
      c.set(Calendar.SECOND, 2);
      c.add(Calendar.DATE, 1);
      return c.getTime();
   }

   // 获取第2天0点1分的时间
   public static Date getNextDayPastMinute(int minute) {
      Calendar c = Calendar.getInstance();

      c.set(Calendar.HOUR_OF_DAY, 0);
      c.set(Calendar.MINUTE, minute);
      c.set(Calendar.SECOND, 0);
      c.add(Calendar.DATE, 1);
      return c.getTime();
   }

   /**
    * 获取当前时间
    *
    * @return 返回当前时间与协调世界时 1970 年 1 月 1 日午夜之间的时间差（以秒为单位测量）。
    */
   public static int getCurrentTime4Int() {
      return (int) (System.currentTimeMillis() / 1000);
   }

   // 按指定格式获取日期时间
   public static String getFormatDateTime(String format) {
      SimpleDateFormat sdf = new SimpleDateFormat(format);
      return sdf.format(new Date());
   }

   /**
    * @param fmt
    * @param date
    * @return
    * @category 获取指定格式日期时间
    */
   public static String formatDateTime(String fmt, Date date) {
      if (date == null) return "";
      DateFormat dateTimeFormatS = new SimpleDateFormat(fmt);
      return dateTimeFormatS.format(date);
   }

   public static String formatDateTimeStr(Date date) {
      if (date == null) return "";
      DateFormat dateTimeFormatS = new SimpleDateFormat(FMT_DATE_TIME);
      return dateTimeFormatS.format(date);
   }

   public static String formatDateStr(Date date) {
      if (date == null) return "";
      DateFormat dateTimeFormatS = new SimpleDateFormat(FMT_DATE);
      return dateTimeFormatS.format(date);
   }

   public static String formatDateChineseStr(Date date) {
      if (date == null) return "";
      DateFormat dateTimeFormatS = new SimpleDateFormat(FMT_DATE_CHINESE);
      return dateTimeFormatS.format(date);
   }

   // 获取当前年份
   public static String getCurrentYearOfStr() {
      SimpleDateFormat sdf = new SimpleDateFormat(FMT_DATE_YEAR);
      return sdf.format(new Date());
   }

   // 获取当前时间
   public static String getCurrentTime() {
      SimpleDateFormat sdf = new SimpleDateFormat(FMT_DATE_TIME);
      return sdf.format(new Date());
   }

   public static String getCurrentTimeMM() {
      SimpleDateFormat sdf = new SimpleDateFormat(FMT_DATE_TIME_mm);
      return sdf.format(new Date());
   }

   // 获取当前日期
   public static String getCurrentDate() {
      SimpleDateFormat sdf = new SimpleDateFormat(FMT_DATE);
      return sdf.format(new Date());
   }

   public static String getCurrentDate2() {
      SimpleDateFormat sdf = new SimpleDateFormat(FMT_NO_SYMBOL_DATE_);
      return sdf.format(new Date());
   }

   // 获取两个以秒为单位的时间差(单位:天)
   public static int getDiffDay(int beforeDay,
                                int afterDay) {
      int diffSeconds = afterDay - beforeDay;
      return diffSeconds / 60 / 60 / 24;
   }

   public static String getTomorrowDate() {
      SimpleDateFormat sdf = new SimpleDateFormat(FMT_NO_SYMBOL_DATE_);
      Date dt = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
      return sdf.format(dt);

   }

   public static String getCurrentHourAndMinute() {
      SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
      return sdf.format(new Date());
   }

   public static int getCurrentIntHourAndMinute() {
      SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
      return Integer.parseInt(sdf.format(new Date()));
   }

   /**
    * 获得当前日期的总秒数
    *
    * @return
    */
   public static int getCurrentDaySecond() {
      Date date = new Date();
      SimpleDateFormat hourSdf = new SimpleDateFormat("HH");
      int hour = Integer.parseInt(hourSdf.format(date));
      SimpleDateFormat minuteSdf = new SimpleDateFormat("mm");
      int minute = Integer.parseInt(minuteSdf.format(date));
      SimpleDateFormat secondSdf = new SimpleDateFormat("ss");
      int second = Integer.parseInt(secondSdf.format(date));

      return hour * 60 * 60 + minute * 60 + second;
   }

   /**
    * 获得当前日
    *
    * @return
    */
   public static int getCurrentDay() {
      Calendar c = Calendar.getInstance();
      return c.get(Calendar.DATE);
   }

   /**
    * 今天是一年中的第几天
    *
    * @return
    */
   public static int getCurrentDayOfYear() {
      Calendar c = Calendar.getInstance();
      return c.get(Calendar.DAY_OF_YEAR);
   }

   public static String getYestesday() {
      Calendar c = Calendar.getInstance();
      c.add(Calendar.DATE, -1);
      SimpleDateFormat sdf = new SimpleDateFormat(FMT_DATE);
      return sdf.format(c.getTime());
   }

   //昨天 0点0分1秒
   public static String getYestesday0() {
      Calendar c = Calendar.getInstance();
      c.add(Calendar.DATE, -1);
      c.set(Calendar.HOUR_OF_DAY, 0);
      c.set(Calendar.MINUTE, 0);
      c.set(Calendar.SECOND, 1);
      SimpleDateFormat sdf = new SimpleDateFormat(FMT_DATE_TIME);
      return sdf.format(c.getTime());
   }

   //昨天 23点59分59秒
   public static String getYestesday23() {
      Calendar c = Calendar.getInstance();
      c.add(Calendar.DATE, -1);
      c.set(Calendar.HOUR_OF_DAY, 23);
      c.set(Calendar.MINUTE, 59);
      c.set(Calendar.SECOND, 59);
      SimpleDateFormat sdf = new SimpleDateFormat(FMT_DATE_TIME);
      return sdf.format(c.getTime());
   }

   public static String getNextDate4Num(int n) {
      Calendar c = Calendar.getInstance();
      c.add(Calendar.DATE, n);
      SimpleDateFormat sdf = new SimpleDateFormat(FMT_DATE);
      return sdf.format(c.getTime());
   }

   public static String getNextDate4Num(int n,
                                        String format) {
      Calendar c = Calendar.getInstance();
      c.add(Calendar.DATE, n);
      SimpleDateFormat sdf = new SimpleDateFormat(format);
      return sdf.format(c.getTime());
   }

   //当天的0点1秒
   public static int getTodaySecond() {
      Calendar c = Calendar.getInstance();
      c.set(Calendar.HOUR_OF_DAY, 0);
      c.set(Calendar.MINUTE, 0);
      c.set(Calendar.SECOND, 1);
      return (int) c.getTimeInMillis() / 1000;

   }

   /**
    * 转换日期格式
    *
    * @param date
    * @param from
    * @param to
    * @return
    * @throws ParseException
    */
   public static String formatDate(String date, String from, String to) throws ParseException {
      if (StringUtils.isBlank(date) || StringUtils.isBlank(from) || StringUtils.isBlank(to)) {
         return StringUtils.EMPTY;
      }
      SimpleDateFormat sdfForm = new SimpleDateFormat(from);
      SimpleDateFormat sdfTo = new SimpleDateFormat(to);
      Date dateFrom = sdfForm.parse(date);
      return sdfTo.format(dateFrom);
   }

   /**
    * 通过格式，日期str获取秒
    *
    * @param format
    * @param date
    * @return
    */
   public static int getSecondByDateStr(String format, String date) {
      try {
         SimpleDateFormat sdf = new SimpleDateFormat(format);
         return (int) (sdf.parse(date).getTime() / 1000);
      } catch (Exception e) {
         return 0;
      }
   }

   public static int getSecondByDateStr(String date) {
      return getSecondByDateStr(FMT_DATE_TIME, date);
   }

   public static int getPvPSecondByDateStr(String date) {
      return getSecondByDateStr(FMT_DATE_HOUR_H_M, date);
   }

   public static Date str2Data(String str)
           throws ParseException {
      SimpleDateFormat sdf = new SimpleDateFormat(FMT_DATE_TIME);
      return sdf.parse(str);
   }

   public static Date strToDate(String str, String fmt) throws ParseException {
      SimpleDateFormat sdf = new SimpleDateFormat(fmt);
      return sdf.parse(str);
   }

   public static long strToLong(String str, String fmt) {
      try {
         return strToDate(str, fmt).getTime();
      } catch (ParseException e) {
         e.printStackTrace();
      }
      return 0;
   }

   public static long str2Long(String str) {
      try {
         return str2Data(str).getTime();
      } catch (ParseException e) {
         e.printStackTrace();
      }
      return 0;
   }

   /**
    * 获取当年的最后一天的59分59秒Date类型，如果有需要则截取转换
    *
    * @return
    */
   public static Date getLastDateOfYear() {
      Calendar c = Calendar.getInstance();
      c.set(Calendar.YEAR, c.get(Calendar.YEAR) + 1);
      c.set(Calendar.MONTH, 0);
      c.set(Calendar.DAY_OF_MONTH, 1);
      c.set(Calendar.HOUR_OF_DAY, 0);
      c.set(Calendar.MINUTE, 0);
      c.set(Calendar.SECOND, 0);
      c.add(Calendar.SECOND, -1);
      return c.getTime();
   }

   /**
    * 获取下一年第一天的00分00秒Date类型，如果有需要则截取转换
    *
    * @return
    */
   public static Date getFirstDateOfNextYear() {
      Calendar c = Calendar.getInstance();
      c.set(Calendar.YEAR, c.get(Calendar.YEAR) + 1);
      c.set(Calendar.MONTH, 0);
      c.set(Calendar.DAY_OF_MONTH, 1);
      c.set(Calendar.HOUR_OF_DAY, 0);
      c.set(Calendar.MINUTE, 0);
      c.set(Calendar.SECOND, 0);
      return c.getTime();
   }

   /**
    * 计算两个日期之间相差的天数
    *
    * @param smdate 较小的时间
    * @param bdate  较大的时间
    * @return 相差天数
    * @throws ParseException
    */
   public static int daysBetween(Date smdate, Date bdate) throws ParseException {
      SimpleDateFormat sdf = new SimpleDateFormat(FMT_DATE);
      smdate = sdf.parse(sdf.format(smdate));
      bdate = sdf.parse(sdf.format(bdate));
      Calendar cal = Calendar.getInstance();
      cal.setTime(smdate);
      long time1 = cal.getTimeInMillis();
      cal.setTime(bdate);
      long time2 = cal.getTimeInMillis();
      long between_days = (time2 - time1) / (1000 * 3600 * 24);

      return Integer.parseInt(String.valueOf(between_days));
   }

   public static int daysBetween(String smdate, String bdate) throws ParseException {
      SimpleDateFormat sdf = new SimpleDateFormat(FMT_DATE);
      Calendar cal = Calendar.getInstance();
      cal.setTime(sdf.parse(smdate));
      long time1 = cal.getTimeInMillis();
      cal.setTime(sdf.parse(bdate));
      long time2 = cal.getTimeInMillis();
      long between_days = (time2 - time1) / (1000 * 3600 * 24);

      return Integer.parseInt(String.valueOf(between_days));
   }

   /**
    * 获取当天剩余的时间(分钟)
    *
    * @return
    */
   public static int getDayLeftMinute() {
      Calendar c = Calendar.getInstance();
      int leftMinute = 12 * 60 - (c.get(Calendar.HOUR) * 60 + c.get(Calendar.MINUTE));
      if (c.get(Calendar.AM_PM) == 0) {
         leftMinute += 12 * 60;
      }
      return leftMinute;
   }

   /**
    * 周一为1
    *
    * @return
    */
   public static int getDayOfWeek() {
      Calendar c = Calendar.getInstance();
      int weekDay = c.get(Calendar.DAY_OF_WEEK);
      if (weekDay == 1) {
         weekDay = 7;
      } else {
         weekDay--;
      }
      return weekDay;
   }

   public static int getDayOfWeek(String fmt, String str) {
      Calendar c = Calendar.getInstance();
      c.setTime(DateUtils.getDateByStr(fmt, str));
      int weekDay = c.get(Calendar.DAY_OF_WEEK);
      if (weekDay == 1) {
         weekDay = 7;
      } else {
         weekDay--;
      }
      return weekDay;
   }

   public static Date getWeekFirstDate(String fmt, String str) {
      Calendar c = Calendar.getInstance();
      c.setTime(DateUtils.getDateByStr(fmt, str));
      int weekDay = c.get(Calendar.DAY_OF_WEEK);
      if (weekDay == 1) {
         weekDay = 7;
      } else {
         weekDay--;
      }
      c.add(Calendar.DATE, -weekDay + 1);
      return c.getTime();
   }

   /**
    * 周数字转换成字符
    *
    * @param weekday
    * @return
    */
   public static String weekNum2str(int weekday) {
      switch (weekday) {
         case 1:
            return "周日";
         case 2:
            return "周一";
         case 3:
            return "周二";
         case 4:
            return "周三";
         case 5:
            return "周四";
         case 6:
            return "周五";
         case 7:
            return "周六";
         default:
            return "";
      }
   }

   public static String addHour(Date date, int hour) {
      Calendar c = Calendar.getInstance();
      c.setTime(date);
      c.add(Calendar.HOUR, hour);
      SimpleDateFormat sdf = new SimpleDateFormat(FMT_DATE_TIME);
      return sdf.format(c.getTime());
   }

   /**
    * 一周168小时，获得当前时间
    *
    * @return
    */
   public static int getHourOfWeek() {
      int weekDay = getDayOfWeek();
      return (weekDay - 1) * 24 + getCurrentHour();
   }

   /**
    * 时间是否超出指定时间
    *
    * @param time    比较时间
    * @param outimes 超出时间（秒）
    * @return
    */
   public static boolean isTimeout(long time, int outimes) {
      Calendar c = Calendar.getInstance();
      c.add(Calendar.SECOND, -outimes);

      /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      System.out.println( sdf.format(c.getTimeInMillis()));
      System.out.println( sdf.format(time));*/

      return time < c.getTimeInMillis();
   }

   public static int getCurrentHour() {
      return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
   }

   public static int getCurrentMinute() {
      return Calendar.getInstance().get(Calendar.MINUTE);
   }

   public static int getCurrentSecond() {
      return Calendar.getInstance().get(Calendar.SECOND);
   }

   public static Date getDateByStr(String fmt, String dateStr) {
      try {
         SimpleDateFormat sdf = new SimpleDateFormat(fmt);
         return sdf.parse(dateStr);
      } catch (Exception e) {
         return null;
      }
   }

   public static int getNDayBefore4Int(int n) {
      Calendar c = Calendar.getInstance();
      c.set(Calendar.HOUR_OF_DAY, 0);
      c.set(Calendar.MINUTE, 0);
      c.set(Calendar.SECOND, 0);
      c.add(Calendar.DAY_OF_MONTH, -n);
      return (int) (c.getTimeInMillis() / 1000);
   }

   public static String long2Date(long mill) {
      Date date = new Date(mill);
      String strs = "";
      try {
         SimpleDateFormat sdf = new SimpleDateFormat(FMT_DATE_TIME);
         strs = sdf.format(date);
      } catch (Exception e) {
         e.printStackTrace();
      }
      return strs;
   }

   /**
    * 两个日期相差的月数
    *
    * @param str1
    * @param str2
    * @return
    * @throws ParseException
    */
   public static int monthSpace(String str1, String str2) throws ParseException {
      SimpleDateFormat sdf = new SimpleDateFormat(FMT_DATE);

      Calendar bef = Calendar.getInstance();
      Calendar aft = Calendar.getInstance();
      bef.setTime(sdf.parse(str1));
      aft.setTime(sdf.parse(str2));
      int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
      int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
      return month + result;
   }

   /**
    * 生日格式转换(.)
    *
    * @param birthday
    * @return
    */
   public static String birthdayFormatPoint(String birthday) {
      if (birthday.length() == 8) {
         StringBuffer sb = new StringBuffer();
         sb.append(birthday.substring(0, 4)).append(".").append(birthday.substring(4, 6)).append(".")
                 .append(birthday.substring(6, 8));
         return sb.toString();
      }
      return birthday;
   }

   /**
    * 生日格式转换(-)
    *
    * @param birthday
    * @return
    */
   public static String birthdayFormat_(String birthday) {
      if (birthday.length() == 8) {
         StringBuffer sb = new StringBuffer();
         sb.append(birthday.substring(0, 4)).append("-").append(birthday.substring(4, 6)).append("-")
                 .append(birthday.substring(6, 8));
         return sb.toString();
      }
      return birthday;
   }

   /**
    * 比较连个日期大小
    *
    * @param DATE1
    * @param DATE2
    * @return 1、大于  -1、小于  0、等于
    * @throws ParseException
    */
   public static int compareDate(String DATE1, String DATE2) throws ParseException {
      SimpleDateFormat sdf = new SimpleDateFormat(FMT_DATE);

      Date dt1 = sdf.parse(DATE1);
      Date dt2 = sdf.parse(DATE2);
      if (dt1.getTime() > dt2.getTime()) {
         return 1;
      } else if (dt1.getTime() < dt2.getTime()) {
         return -1;
      } else {
         return 0;
      }
   }

   /**
    * 获取当前日期几个月后的时间
    * @param date
    * @return
    */
   public static Date getNextMonthDate(Date date,Long num){
      Instant instant = date.toInstant();
      ZoneId zoneId = ZoneId.systemDefault();
      LocalDateTime dateTime = LocalDateTime.ofInstant(instant, zoneId);
      LocalDateTime resultdateTime = dateTime.plusMonths(num);
      Instant resultInstant = resultdateTime.atZone(zoneId).toInstant();
      return Date.from(resultInstant);
   }


   /**
    * 给定一个时间段，按月份分割
    * @param beginTime
    * @param endTime
    * @param beginDateList
    * @param endDateList
    */
   public static void SplitByMonth(String beginTime, String endTime, List<Date> beginDateList,
           List<Date> endDateList) throws ParseException {

      Date startDate = strToDate(beginTime,FMT_DATE_TIME);
      Date endDate = strToDate(endTime,FMT_DATE_TIME);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(startDate);
//      beginDateList.add(calendar.getTimeInMillis());
      beginDateList.add(calendar.getTime());
      while (calendar.getTimeInMillis() < endDate.getTime()) {
         calendar.add(Calendar.MONTH, 1);
         calendar.set(Calendar.DAY_OF_MONTH, 1);
         calendar.add(Calendar.DATE, -1);
         calendar.set(Calendar.HOUR_OF_DAY, 23);
         calendar.set(Calendar.MINUTE, 59);
         calendar.set(Calendar.SECOND, 59);
         if (calendar.getTimeInMillis() < endDate.getTime()) {
            endDateList.add(calendar.getTime());
         } else {
            endDateList.add(endDate);
            break;
         }
         calendar.add(Calendar.DATE, 1);
         calendar.set(Calendar.HOUR_OF_DAY, 0);
         calendar.set(Calendar.MINUTE, 0);
         calendar.set(Calendar.SECOND, 0);
         beginDateList.add(calendar.getTime());
      }
   }

   public static void main(String[] args) {
      //System.out.println(getCurrentTime4Int());
//      Date now = new Date();
//      now.setTime(now.getTime() + 30*60*1000);
//      String timeExpire  = DateUtils.formatDateTime(DateUtils.FMT_NO_SYMBOL_DATE_TIME, now);
//      System.out.println(timeExpire);
//      Date nextMonthDate = getNextMonthDate(new Date(), 6L);
//      System.out.println(DateUtils.formatDateTime(DateUtils.FMT_DATE_TIME, nextMonthDate));
      List<Date> beginTime = new ArrayList<>();
      List<Date> endTime = new ArrayList<>();

      try {
         SplitByMonth("2020-01-01 00:00:00", "2020-03-24 13:59:59", beginTime, endTime);
      } catch (ParseException e) {
         e.printStackTrace();
      }

      for (int i = 0; i < beginTime.size(); i++) {
         System.out.println(beginTime.get(i) + "+++++++++++++++++++++++++++++" + endTime.get(i));
      }
   }
}