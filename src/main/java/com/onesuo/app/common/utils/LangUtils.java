package com.onesuo.app.common.utils;

import java.util.Date;
import java.util.regex.Pattern;

/**
 * 基础数据类型 工具类
 *
 * Created by kongfanyi on 2020/01/10
 */
public class LangUtils {

    public static Integer getInteger(Object obj) {
        if(null == obj) {
            return null;
        }
        return Integer.valueOf(obj.toString());
    }

    /**
     * Object 转 Int
     * @param obj
     * @return
     */
    public static Integer getInt(Object obj) {
        if(null == obj) {
            return null;
        }
        return getInt(obj.toString());
    }

    /**
     * Object 转 String
     * @param obj
     * @return
     */
    public static String getStr(Object obj) {
        if(null == obj) {
            return "";
        }
        return obj.toString();
    }

    /**
     * Object 转 Date
     * @param obj
     * @return
     */
    public static Date getDate(Object obj) {
        if(null == obj) {
            return null;
        }
        return (Date) obj;
    }

    /**
     * Object 转 DateStr
     * @param obj
     * @return
     */
    public static String getDateCStr(Object obj) {
        if(null == obj) {
            return "";
        }
        return DateUtils.formatDateTime(DateUtils.FMT_DATE_CHINESE, getDate(obj));
    }

    /**
     * Object 转 DateStr
     * @param obj
     * @return
     */
    public static String getDateStr(Object obj) {
        if(null == obj) {
            return "";
        }
        return DateUtils.formatDateTime(DateUtils.FMT_DATE, getDate(obj));
    }

    /**
     * Object 转 DateStr
     * @param obj
     * @return
     */
    public static String getLongDateStr(Object obj) {
        if(null == obj) {
            return null;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        if(pattern.matcher(obj.toString()).matches()) {
            return DateUtils.fromTimeStamp2StringDateStr((Long) obj);
        }
        return null;
    }

    /**
     * 字符串转int
     * @param value
     * @return
     */
    public static Integer getInt(String value) {
        if(StringUtils.isBlank(value)) {
            return null;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        if(!pattern.matcher(value).matches()) {
            return null;
        }
        return Integer.valueOf(value);
    }
}
