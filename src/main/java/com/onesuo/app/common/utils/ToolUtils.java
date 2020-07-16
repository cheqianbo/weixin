package com.onesuo.app.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class ToolUtils {
    private final static int SAMPLECODE_LENGTH = 7;
    private final static int CITYCODE_LENGTH = 4;
    private final static int SMSCODE_LENGTH = 4;

    public static String getRandomSmsCode() {
        return cRandom(SMSCODE_LENGTH);
    }

    public static String getRandomSampleCode() {
        return cRandom(SAMPLECODE_LENGTH);
    }

    public static String getCityCode(String cityCode) {
        return leftAddZero(cityCode,CITYCODE_LENGTH);
    }

    /**
     * 产生n个只含有数字和字母长度为m（m<=52）的无重复随机字符串
     *
     * @param n
     * @param m
     * @return
     */
    private static Set<String> cDifferentRandoms(int n, int m) {
        if (m > 52) {
            return null;
        }
        else {
            Set<String> set = new HashSet<String>();
            while (set.size() < n) {
                set.add(cRandom(m));
            }
            return set;
        }
    }

    /**
     * 产生1个长度为m只含数字的随机字符串
     *
     * @param m
     * @return
     */
    private static String cRandom(int m) {
        char[] chs = new char[m];
        for (int i = 0; i < m; i++) {
            chs[i] = cNumRandom();
        }
        return new String(chs);
    }

    /**
     * 产生一个随机数字
     *
     * @return
     */
    private static char cNumRandom() {
        String temp = "0123456789";
        return (char) temp.charAt(iRandom(0, temp.length()));
    }

    /**
     * 产生一个(m,n)之间的随机整数
     *
     * @param m
     * @param n
     * @return
     */
    private static int iRandom(int m, int n) {
        Random random = new Random();
        int small = m > n ? n : m;
        int big = m <= n ? n : m;
        return small + random.nextInt(big - small);
    }

    /**
     * 左补0
     * @param str
     * @param strLength
     * @return
     */
    public static String leftAddZero(String str, int strLength) {
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                sb.append("0").append(str);
                str = sb.toString();
                strLen = str.length();
            }
        }
        return str;
    }

    /**
     * 右补0
     * @param str
     * @param strLength
     * @return
     */
    public static String rightAddZero(String str, int strLength) {
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                sb.append(str).append("0");
                str = sb.toString();
                strLen = str.length();
            }
        }
        return str;
    }

    /**
     * 使用UUID重构文件名
     * @param filename
     * @return
     */
    public static String getFileName(String filename) {
        if (!filename.equals("")) {
            UUID uuid = UUID.randomUUID();
            int tem = filename.lastIndexOf(".");
            String fileext = filename.substring(tem);
            filename = uuid + fileext;
        } else {
            filename = "";
        }
        return filename;
    }

    /**
     * 获取UUID随机串
     * @return
     */
    public static String getRandomUUID() {
        UUID uuid = UUID.randomUUID();
        String rs = uuid.toString().replaceAll("-", "");
        return rs;
    }

    /**
     * 写文件
     * @param file
     * @param path
     * @param filename
     */
    public static void writeFile(MultipartFile file, String path, String filename) {
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            is = file.getInputStream();
            File f = new File(path + filename);
            fos = new FileOutputStream(f);
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = is.read(buffer, 0, 1024)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
        catch (Exception e) {
            log.error("写文件时出错:" + e.toString());
            e.printStackTrace();
        }
        finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 写文件
     * @param mailxml
     * @param path
     * @param filename
     */
    public static void writeFile(String mailxml, String path, String filename) {
        FileOutputStream fos = null;
        try {
            File file = new File(path + filename);
            fos = new FileOutputStream(file);
            fos.write(mailxml.getBytes());
            fos.close();
        }
        catch (Exception e) {
            log.error("写文件时出错:" + e.toString());
            e.printStackTrace();
        }
        finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @description 日期字符串转日期（日期格式："yyyy-MM-dd"）
     * @param date 日期字符串
     * @return Date
     */
    public static Date strToDate(String date) {
        return strToDate(date, "yyyy-MM-dd");
    }

    /**
     * @description 日期字符串转日期（日期格式："yyyy-MM-dd HH:mm:ss"）
     * @param date 日期字符串
     * @return Date
     */
    public static Date strToDateTime(String date) {
        return strToDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * @description 日期字符串转日期（日期格式："yyyy-MM-dd HH:mm:ss SSS"）
     * @param date 日期字符串
     * @return Date
     */
    public static Date strToDateTimeMillis(String date) {
        return strToDate(date, "yyyy-MM-dd HH:mm:ss SSS");
    }

    /**
     * @description 日期字符串转日期
     * @param date 日期字符串
     * @param formart 日期格式
     * @return Date
     */
    public static Date strToDate(String date, String formart) {
        if (StringUtils.isBlank(date))
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat(formart);
        Date rs = null;
        try {
            rs = sdf.parse(date);
        }
        catch (ParseException e) {
            log.error("字符串转日期出错:" + e.toString());
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * @description 日期转日期字符串（日期格式："yyyy-MM-dd"）
     * @param date 日期
     * @return String
     */
    public static String dateToStr(Date date) {
        return dateToStr(date, "yyyy-MM-dd");
    }

    /**
     * @description 日期转日期字符串（日期格式："yyyy-MM-dd HH:mm:ss"）
     * @param date 日期
     * @return String
     */
    public static String dateTimeToStr(Date date) {
        return dateToStr(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * @description 日期转日期字符串（日期格式："yyyy-MM-dd HH:mm:ss SSS"）
     * @param date 日期
     * @return String
     */
    public static String dateTimeMillisToStr(Date date) {
        return dateToStr(date, "yyyy-MM-dd HH:mm:ss SSS");
    }

    /**
     * @description 日期转日期字符串
     * @param date 日期
     * @param formart 日期格式
     * @return String
     */
    public static String dateToStr(Date date, String formart) {
        if (date == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat(formart);
        String rs = sdf.format(date);
        return rs;
    }

    /**
     * @description 日期字符串转时间戳（日期格式："yyyy-MM-dd"）
     * @param date 日期字符串
     * @return Long
     */
    public static Long strToStamp(String date) {
        return strToStamp(date, "yyyy-MM-dd");
    }

    /**
     * @description 日期字符串转时间戳（日期格式："yyyy-MM-dd HH:mm:ss"）
     * @param date 日期字符串
     * @return Long
     */
    public static Long strToStampTime(String date) {
        return strToStamp(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * @description 日期字符串转时间戳（日期格式："yyyy-MM-dd HH:mm:ss SSS"）
     * @param date 日期字符串
     * @return Long
     */
    public static Long strToStampTimeMillis(String date) {
        return strToStamp(date, "yyyy-MM-dd HH:mm:ss SSS");
    }

    /**
     * @description 日期字符串转时间戳
     * @param date 日期字符串
     * @param formart 日期格式
     * @return Long
     */
    public static Long strToStamp(String date, String formart) {
        if (StringUtils.isBlank(date))
            return null;
        Date temp = strToDate(date, formart);
        long rs = temp.getTime();
        return rs;
    }

    /**
     * @description 时间戳转日期字符串（日期格式："yyyy-MM-dd"）
     * @param stamp 时间戳
     * @return String
     */
    public static String stampToDate(Long stamp) {
        return stampToDate(stamp, "yyyy-MM-dd");
    }

    /**
     * @description 时间戳转日期字符串（日期格式："yyyy-MM-dd HH:mm:ss"）
     * @param stamp 时间戳
     * @return String
     */
    public static String stampToDateTime(Long stamp) {
        return stampToDate(stamp, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * @description 时间戳转日期字符串（日期格式："yyyy-MM-dd HH:mm:ss SSS"）
     * @param stamp 时间戳
     * @return String
     */
    public static String stampToDateTimeMillis(Long stamp) {
        return stampToDate(stamp, "yyyy-MM-dd HH:mm:ss SSS");
    }

    /**
     * @description 时间戳转日期字符串
     * @param stamp 时间戳
     * @param formart 日期格式
     * @return String
     */
    public static String stampToDate(Long stamp, String formart) {
        if (stamp == null)
            return null;
        Date date = new Date(stamp);
        String rs = dateToStr(date, formart);
        return rs;
    }

    /**
     * @description 获取当前日期（日期格式："yyyy-MM-dd"）
     * @return String
     */
    public static String getCurrentDate() {
        return dateToStr(new Date());
    }

    /**
     * @description 获取当前时间（日期格式："yyyy-MM-dd HH:mm:ss"）
     * @return String
     */
    public static String getCurrentDateTime() {
        return dateTimeToStr(new Date());
    }

    /**
     * @description 获取当前时间精确到毫秒（日期格式："yyyy-MM-dd HH:mm:ss SSS"）
     * @return String
     */
    public static String getCurrentDateTimeMillis() {
        return dateTimeMillisToStr(new Date());
    }

    /**
     * @description 昨天的日期（日期格式："yyyy-MM-dd"）
     * @return String
     */
    public static String getYesterday() {
        String rs = getLastDate(1);
        return rs;
    }

    /**
     * @description 过去7天的日期（日期格式："yyyy-MM-dd"）
     * @return String
     */
    public static String getLast7Date() {
        String rs = getLastDate(6);
        return rs;
    }

    /**
     * @description 过去30天的日期（日期格式："yyyy-MM-dd"）
     * @return String
     */
    public static String getLast30Date() {
        String rs = getLastDate(29);
        return rs;
    }

    /**
     * @description 过去60天的日期（日期格式："yyyy-MM-dd"）
     * @return String
     */
    public static String getLast60Date() {
        String rs = getLastDate(59);
        return rs;
    }

    /**
     * @description 过去N天的日期（日期格式："yyyy-MM-dd"）
     * @param number 过去天数
     * @return String
     */
    public static String getLastDate(int number) {
        String rs = getLastDate("yyyy-MM-dd", number);
        return rs;
    }

    /**
     * @description 过去N天
     * @param formart 日期格式
     * @param number 过去天数
     * @return String
     */
    public static String getLastDate(String formart, int number) {
        Calendar gc = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat(formart);
        gc.add(Calendar.DATE, -number);
        String rs = sdf.format(gc.getTime());
        return rs;
    }

    /**
     * @description 某日期前推7天的日期（日期格式："yyyy-MM-dd"）
     * @param date 要前推的日期
     * @return String
     */
    public static String getLastToDate(String date) {
        String rs = getLastToDate(date, "yyyy-MM-dd", 6);
        return rs;
    }

    /**
     * @description 某日期前推N天的日期（日期格式："yyyy-MM-dd"）
     * @param date 要前推的日期
     * @param number 前推天数
     * @return String
     */
    public static String getLastToDate(String date, int number) {
        String rs = getLastToDate(date, "yyyy-MM-dd", number);
        return rs;
    }

    /**
     * @description 某日期前推N天
     * @param date 要前推的日期
     * @param formart 日期格式
     * @param number 前推天数
     * @return String
     */
    public static String getLastToDate(String date, String formart, int number) {
        Calendar gc = new GregorianCalendar();
        gc.setTime(strToDate(date));
        SimpleDateFormat sdf = new SimpleDateFormat(formart);
        gc.add(Calendar.DATE, -number);
        String rs = sdf.format(gc.getTime());
        return rs;
    }

    /**
     * @description 上月的日期（日期格式："yyyy-MM-dd HH:mm:ss"）
     * @return String
     */
    public static String getLastMonth() {
        String rs = getLastToMonth(getCurrentDateTime(),1);
        return rs;
    }

    /**
     * @description 过去N月的日期（日期格式："yyyy-MM-dd HH:mm:ss"）
     * @param date 要前推的日期
     * @param number 过去月数
     * @return String
     */
    public static String getLastToMonth(String date, int number) {
        String rs = getLastToMonth(date, "yyyy-MM-dd HH:mm:ss", number);
        return rs;
    }

    /**
     * @description 某日期前推N月
     * @param date 要前推的日期
     * @param formart 日期格式
     * @param number 前推月数
     * @return String
     */
    public static String getLastToMonth(String date, String formart, int number) {
        Calendar gc = new GregorianCalendar();
        gc.setTime(strToDate(date));
        SimpleDateFormat sdf = new SimpleDateFormat(formart);
        gc.add(Calendar.MONTH, -number);
        String rs = sdf.format(gc.getTime());
        return rs;
    }

    /**
     * @description 过去N年的日期（日期格式："yyyy-MM-dd HH:mm:ss"）
     * @param date 要前推的日期
     * @param number 过去年数
     * @return String
     */
    public static String getLastToYear(String date, int number) {
        String rs = getLastToYear(date, "yyyy-MM-dd HH:mm:ss", number);
        return rs;
    }

    /**
     * @description 某日期前推N年
     * @param date 要前推的日期
     * @param formart 日期格式
     * @param number 前推年数
     * @return String
     */
    public static String getLastToYear(String date, String formart, int number) {
        Calendar gc = new GregorianCalendar();
        gc.setTime(strToDate(date));
        SimpleDateFormat sdf = new SimpleDateFormat(formart);
        gc.add(Calendar.YEAR, -number);
        String rs = sdf.format(gc.getTime());
        return rs;
    }

    /**
     * @description 两个日期之间计算自然日
     * @param startdate 开始日期
     * @param enddate 结束日期
     * @return String[]
     */
    public static String[] getNaturalDay(String startdate, String enddate) {
        List<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar gcstart = new GregorianCalendar();
        Date start = strToDate(startdate);
        gcstart.setTime(start);
        Calendar gcend = new GregorianCalendar();
        Date end = strToDate(enddate);
        gcend.setTime(end);
        gcend.add(Calendar.DAY_OF_YEAR, 1);
        pushList(sdf, Calendar.DAY_OF_MONTH, result, gcstart, gcend);
        String[] rs = result.toArray(new String[result.size()]);
        return rs;
    }

    /**
     * @description 两个日期之间计算自然周
     * @param startdate 开始日期
     * @param enddate 结束日期
     * @return String[]
     */
    public static String[] getNaturalWeek(String startdate, String enddate) {
        String[] day = getNaturalDay(startdate, enddate);
        List<String> result = new ArrayList<String>();
        Calendar gc = new GregorianCalendar();
        for (String str : day) {
            Date temp = strToDate(str);
            gc.clear();
            gc.setTime(temp);
            int k = new Integer(gc.get(Calendar.DAY_OF_WEEK));
            // 若当天是周日
            if (k == 1) {
                result.add(str);
            }
        }
        String[] rs = result.toArray(new String[result.size()]);
        /**/
        if (rs.length > 0) {
            Calendar gctemp = new GregorianCalendar();
            Date temp = strToDate(enddate);
            gctemp.setTime(temp);
            int k = new Integer(gctemp.get(Calendar.DAY_OF_WEEK));
            // 若结束日期不是周日
            if (k != 1) {
                rs = Arrays.copyOf(rs, rs.length - 1);
            }
        }
        /**/
        return rs;
    }

    /**
     * @description 两个日期之间计算自然月
     * @param startdate 开始日期
     * @param enddate 结束日期
     * @return String[]
     */
    public static String[] getNaturalMonth(String startdate, String enddate) {
        List<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar gcstart = new GregorianCalendar();
        Date start = strToDate(startdate);
        gcstart.setTime(start);
        // 开始日期非该月1号
        if (gcstart.get(Calendar.DAY_OF_MONTH) != 1) {
            Date temp = strToDate(startdate, "yyyy-MM");
            String str = dateToStr(temp);
            start = strToDate(str);
            gcstart.clear();
            gcstart.setTime(start);
            gcstart.add(Calendar.MONTH, 1);
        }
        Calendar gcend = new GregorianCalendar();
        Date end = strToDate(enddate);
        gcend.setTime(end);
        gcend.add(Calendar.DAY_OF_MONTH, 1);
        pushList(sdf, Calendar.MONTH, result, gcstart, gcend);
        String[] rs = result.toArray(new String[result.size()]);
        /**/
        if (rs.length > 0) {
            Calendar gc = new GregorianCalendar();
            Date temp = strToDate(enddate);
            gc.setTime(temp);
            int m = gc.getActualMaximum(Calendar.DATE);
            //System.out.println(m);
            int k = new Integer(gc.get(Calendar.DAY_OF_MONTH));
            //System.out.println(k);
            // 若结束日期不是该月最后一天
            if (k != m) {
                rs = Arrays.copyOf(rs, rs.length - 1);
            }
        }
        /**/
        return rs;
    }

    /**
     * @description 根据两个日期之间往集合中追加日期值
     * @param sdf 日期格式
     * @param field 格林时间常量
     * @param list 目标集合
     * @param gcstart 开始日期的格林时间对象
     * @param gcend 结束日期的格林时间对象
     */
    private static void pushList(SimpleDateFormat sdf, int field, List<String> list, Calendar gcstart, Calendar gcend) {
        while (gcend.after(gcstart)) {
            list.add(sdf.format(gcstart.getTime()));
            gcstart.add(field, 1);
        }
    }

    /**
     * @description 根据两个日期之间找出整年、整月、整日
     * @param startdate 开始日期
     * @param enddate 结束日期
     * @return List<List<String>>
     */
    @Deprecated
    public static List<List<String>> figureDate(Date startdate, Date enddate) {
        List<List<String>> rs = new ArrayList<List<String>>();
        if (startdate.after(enddate)) {
            return rs;
        }
        List<String> year = new ArrayList<String>();
        List<String> month = new ArrayList<String>();
        List<String> day = new ArrayList<String>();
        SimpleDateFormat ysdf = new SimpleDateFormat("yyyy");
        SimpleDateFormat msdf = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat dsdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar gcstart = new GregorianCalendar();
        gcstart.setTime(startdate);
        Calendar gcend = new GregorianCalendar();
        // 非1月1号
        if (gcstart.get(Calendar.DAY_OF_YEAR) != 1) {
            // 非每月1号
            if (gcstart.get(Calendar.DAY_OF_MONTH) != 1) {
                // 设置到当年月末
                gcend.set(gcstart.get(Calendar.YEAR), gcstart.get(Calendar.MONTH) + 1, 1);
                gcend.add(Calendar.DAY_OF_MONTH, -1);
                if (gcend.getTime().after(enddate)) {
                    gcend.setTime(enddate);
                }
                pushList(dsdf, Calendar.DAY_OF_MONTH, day, gcstart, gcend);
            }
            // 设置结束日期到年末
            if (gcstart.get(Calendar.MONTH) != 0) {
                gcend.set(gcstart.get(Calendar.YEAR), 11, 31);
                if (gcend.getTime().after(enddate)) {
                    gcend.setTime(enddate);
                    gcend.set(Calendar.DAY_OF_MONTH, 1);
                }
                pushList(msdf, Calendar.MONTH, month, gcstart, gcend);
            }
        }
        // 设置结束日期
        gcend.setTime(enddate);
        if (gcend.get(Calendar.MONTH) == 11 && gcend.get(Calendar.DAY_OF_MONTH) == 31) {
            gcend.add(Calendar.YEAR, 1);
        }
        gcend.set(Calendar.DAY_OF_YEAR, 1);
        gcend.set(Calendar.MONTH, 0);
        pushList(ysdf, Calendar.YEAR, year, gcstart, gcend);
        // 设置结束日期
        gcend.setTime(enddate);
        gcend.set(Calendar.DAY_OF_MONTH, 1);
        pushList(msdf, Calendar.MONTH, month, gcstart, gcend);
        // 设置结束日期
        gcend.setTime(enddate);
        gcend.add(Calendar.DAY_OF_YEAR, 1);
        pushList(dsdf, Calendar.DAY_OF_MONTH, day, gcstart, gcend);
        rs.add(year);
        rs.add(month);
        rs.add(day);
        return rs;
    }

    /**
     * @description 根据某年的某月找出哪些日期为周日（如："2017-01"）
     * @param month 月
     * @return List<String>
     */
    @Deprecated
    public static List<String> figureWeek(String month) {
        Calendar gc = new GregorianCalendar();
        Date mdate = strToDate(month, "yyyy-MM");
        gc.setTime(mdate);
        int lastday = gc.getActualMaximum(Calendar.DAY_OF_MONTH);
        List<String> rs = new ArrayList<String>();
        for (int i = 1; i <= lastday; i++) {
            Date temp = strToDate(month + "-" + i);
            gc.clear();
            gc.setTime(temp);
            int k = new Integer(gc.get(Calendar.DAY_OF_WEEK));
            // 若当天是周日
            if (k == 1) {
                rs.add(dateToStr(temp));
            }
        }
        return rs;
    }

    /**
     * @description 获取当前时间戳
     * @return long
     */
    public static long getCurrentTimestamp() {
        return new Date().getTime();
    }

    /**
     * @description 返回时间戳(当前的日期(yyyy-MM-dd)，取值到秒)
     * @return long
     */
    public static long getCurrentDateSecondTimestamp() {
        long timestamp = strToStamp(getCurrentDate());
        return timestamp / 1000;
    }

    /**
     * @description 获取昨日的时间戳 / 1000 (yyyy-MM-dd)
     * @return long
     */
    public static long getYesterdaySecondTimestamp() {
        long timestamp = strToStamp(getYesterday());
        return timestamp / 1000;
    }

    /**
     * 通过时间戳获取到当前周的第一天日期
     * @param timestamp
     * @return yyyy-MM-dd
     */
    public static String getFirstDayOfWeek(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        cal.setTime(strToDate(stampToDate(timestamp, "yyyy-MM-dd")));
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return sdf.format(cal.getTime());
    }

    /**
     * 通过时间戳获取到当前周的第一天日期
     * 返回时间戳(当前的日期(yyyy-MM-dd)，取值到秒)。
     * @param timestamp
     * @return timestamp /1000
     */
    public static long getFirstDayTimestampOfWeek(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        cal.setTime(strToDate(stampToDate(timestamp, "yyyy-MM-dd")));
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return cal.getTimeInMillis() / 1000;
    }

    /**
     * 通过时间戳获取到当前周的最后一天日期
     * 返回时间戳(当前的日期(yyyy-MM-dd)，取值到秒)。
     * @param timestamp
     * @return timestamp /1000
     */
    public static long getLastDayTimestampOfWeek(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        cal.setTime(strToDate(stampToDate(timestamp, "yyyy-MM-dd")));
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        // System.out.println(sdf.format(cal.getTime()));
        return cal.getTimeInMillis() / 1000;
    }

    /**
     * 通过时间取到当前月的第一天
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getFirstDayOfMonth(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(ToolUtils.strToDate(date));
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        return dateToStr(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 通过时间戳获取到当前月的第一天
     * @param timestamp
     * @return yyyy-MM-dd
     */
    public static String getFirstDayOfMonth(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(strToDate(stampToDate(timestamp, "yyyy-MM-dd")));
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        return dateToStr(cal.getTime(), "yyyy-MM-dd");
    }

    /**
     * 通过时间取到当前月的最后一天
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getLastDayOfMonth(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(strToDate(date));
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dateToStr(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 通过时间戳获取到当前月的第一天
     * 返回时间戳(当前的日期(yyyy-MM-dd)，取值到秒)。
     * @param timestamp
     * @return timestamp /1000
     */
    public static long getFirstDayTimestampOfMonth(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(strToDate(stampToDate(timestamp, "yyyy-MM-dd")));
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        return cal.getTimeInMillis() / 1000;
    }

    /**
     * 通过时间戳获取到当前月的最后一天
     * 返回时间戳(当前的日期(yyyy-MM-dd)，取值到秒)。
     * @param timestamp
     * @return timestamp /1000
     */
    public static long getLastDayTimestampOfMonth(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(strToDate(stampToDate(timestamp, "yyyy-MM-dd")));
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTimeInMillis() / 1000;
    }

    /**
     * @description 某日期减少一个月
     * @param date
     * @return String
     */
    public static String dateMinusOneMonth(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date temp = strToDate(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(temp);
        calendar.add(Calendar.MONTH, -1);
        return sdf.format(calendar.getTime());
    }

    /**
     * @description 某日期增加一个月
     * @param date
     * @return String
     */
    public static String dateAddOneMonth(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date temp = strToDate(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(temp);
        calendar.add(Calendar.MONTH, 1);
        return sdf.format(calendar.getTime());
    }

    /**
     * @description 获取两个日期之间的每月1日的集合
     * @param begin
     * @param end
     * @return List<String>
     */
    public static List<String> getMonthBetween(String begin, String end) {
        List<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月
        Calendar sbegin = Calendar.getInstance();
        Calendar send = Calendar.getInstance();
        try {
            sbegin.setTime(sdf.parse(begin));
            sbegin.set(sbegin.get(Calendar.YEAR), sbegin.get(Calendar.MONTH), 1);
            send.setTime(sdf.parse(end));
            send.set(send.get(Calendar.YEAR), send.get(Calendar.MONTH), 2);
        }
        catch (ParseException e) {
            log.error("字符串转日期出错:" + e.toString());
            e.printStackTrace();
        }
        Calendar curr = sbegin;
        String temp = "";
        while (curr.before(send)) {
            temp = sdf.format(curr.getTime());
            result.add(temp + "-01");
            curr.add(Calendar.MONTH, 1);
        }
        return result;
    }

    public static void main(String[] args) {
        /*
        DecimalFormat decimalFormat=new DecimalFormat(".00");
        System.out.println(decimalFormat.format((1501d-3000d)/3000d*100));
        System.out.println(ToolUtils.getCurrentDateTime().substring(0,4));
        System.out.println(ToolUtils.getCurrentDateTime());
        System.out.println(ToolUtils.getCurrentDateTime().substring(0,10) + " 00:00:00");
        System.out.println(ToolUtils.getYesterday());
        System.out.println(ToolUtils.getYesterday().substring(0,10) + " 00:00:00");
        System.out.println(ToolUtils.getFirstDayOfMonth(ToolUtils.getCurrentDateTime()).substring(0,10) + " 00:00:00");
        System.out.println(ToolUtils.getLastMonth());
        System.out.println(ToolUtils.getLastToMonth(ToolUtils.getFirstDayOfMonth(ToolUtils.getCurrentDateTime()),1).substring(0,10) + " 00:00:00");
        List<String> month = getMonthBetween("2020-01-01", "2020-12-31");
        for (String str : month) {
            System.out.println(str + " 00:00:00");
            System.out.println(ToolUtils.getLastDayOfMonth(str + " 23:59:59"));
        }
        System.out.println(ToolUtils.getRandomUUID());
        System.out.println(ToolUtils.getRandomUUID().length());
        /**/
		/*
		System.out.println(getCurrentDateSecondTimestamp());
		System.out.println(ToolUtils.getFirstDayTimestampOfMonth(ToolUtils.strToStamp("2020-03-13") ));
		System.out.println(ToolUtils.getLastDayTimestampOfMonth(ToolUtils.strToStamp("2020-03-13") ));
		System.out.println(ToolUtils.stampToDate(ToolUtils.getLastDayTimestampOfMonth(1459785600l * 1000) * 1000));
		/**/
		/*
		String cdtm = getCurrentDateTimeMillis();
		Long stamp = strToStamp(cdtm);
		Long stamptime = strToStampTime(cdtm);
		Long stamptimemillis = strToStampTimeMillis(cdtm);
		System.out.println(cdtm);
		System.out.println(stamp);
		System.out.println(stamptime);
		System.out.println(stamptimemillis);
		System.out.println(stampToDate(stamp));
		System.out.println(stampToDateTime(stamptime));
		System.out.println(stampToDateTimeMillis(stamptimemillis));
		/**/
		/*
		System.out.println(getCurrentDate());
		System.out.println(getYesterday());
		System.out.println(getLast7Date());
		System.out.println(getLast30Date());
		System.out.println(getLast60Date());
		/**/
		/*
		String[] day = getNaturalDay("2020-01-02", "2020-12-31");
		for (String str : day) {
			System.out.println(str);
		}
		/**/
		/*
		String[] week = getNaturalWeek("2020-04-01", "2020-05-01");
		for (String str : week) {
			System.out.println(str);
		}
		/**/
		/*
		String[] month = getNaturalMonth("2020-01-02", "2020-12-31");
		for (String str : month) {
			System.out.println(str);
		}
		/**/
		/*
		Calendar bgc = new GregorianCalendar();
		Date begin = strToDate("2020-01-02");
		bgc.setTime(begin);
		Calendar egc = new GregorianCalendar();
		Date end = strToDate("2020-12-31");
		egc.setTime(end);
		List<List<String>> result = figureDate(begin, end);
		for (int i = 0; i < result.size(); i++) {
			List<String> list = result.get(i);
			String[] arr = list.toArray(new String[list.size()]);
			for (String str : arr) {
				System.out.println(str);
			}
		}
		/**/
		/*
		List<String> list3 = figureWeek("2020-03");
		for (String str : list3) {
			System.out.println(str);
		}
		/**/
		/*
		List<String> list4 = figureWeek("2020-04");
		for (String str : list4) {
			System.out.println(str);
		}
		/**/
		/*
		List<String> list10 = figureWeek("2020-10");
		for (String str : list10) {
			System.out.println(str);
		}
		/**/
		/*
		long date1 = ToolUtils.strToStamp("2020-09","yyyy-MM");
		System.out.println(date1 / 1000);
		long date2 = ToolUtils.strToStamp("2020-12","yyyy-MM");
		System.out.println(date2 / 1000);
		System.out.println(ToolUtils.strToStampTime("2020-01-01 00:00:00")/1000);
		System.out.println(ToolUtils.strToStampTime("2018-01-01 00:00:00")/1000);
		/**/
        //System.out.println(ToolUtils.dateMinusOneMonth("2020-01-01"));
        //System.out.println(ToolUtils.dateAddOneMonth("2020-01-01"));
        //System.out.println(ToolUtils.getMonthBetween("2020-01", "2020-09"));
        System.out.println(ToolUtils.getLastToYear(ToolUtils.getCurrentDateTime(),1).substring(0,10) + " 00:00:00");
    }
}