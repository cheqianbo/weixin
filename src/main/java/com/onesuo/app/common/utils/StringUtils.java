package com.onesuo.app.common.utils;

import org.apache.commons.lang3.StringEscapeUtils;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 * 2017-06-28
 * @author wanggd
 * @version 1.0
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
	
	public static String lowerFirst(String str){
		if(StringUtils.isBlank(str)) {
			return "";
		} else {
			return str.substring(0,1).toLowerCase() + str.substring(1);
		}
	}
	
	public static String upperFirst(String str){
		if(StringUtils.isBlank(str)) {
			return "";
		} else {
			return str.substring(0,1).toUpperCase() + str.substring(1);
		}
	}

	/**
	 * 替换掉HTML标签方法
	 */
	public static String replaceHtml(String html) {
		if (isBlank(html)){
			return "";
		}
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}

	/**
	 * 缩略字符串（不区分中英文字符）
	 * @param str 目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String abbr(String str, int length) {
		if (str == null) {
			return "";
		}
		try {
			StringBuilder sb = new StringBuilder();
			int currentLength = 0;
			for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
				currentLength += String.valueOf(c).getBytes("GBK").length;
				if (currentLength <= length - 3) {
					sb.append(c);
				} else {
					sb.append("...");
					break;
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 缩略字符串（替换html）
	 * @param str 目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String rabbr(String str, int length) {
        return abbr(replaceHtml(str), length);
	}
		
	
	/**
	 * 转换为Double类型
	 */
	public static Double toDouble(Object val){
		if (val == null){
			return 0D;
		}
		try {
			return Double.valueOf(trim(val.toString()));
		} catch (Exception e) {
			return 0D;
		}
	}

	/**
	 * 转换为Float类型
	 */
	public static Float toFloat(Object val){
		return toDouble(val).floatValue();
	}

	/**
	 * 转换为Long类型
	 */
	public static Long toLong(Object val){
		return toDouble(val).longValue();
	}

	/**
	 * 转换为Integer类型
	 */
	public static Integer toInteger(Object val){
		return toLong(val).intValue();
	}
	
	/**
	 * 获取UUID
	 * @author       liandi
	 * @since        esign, 2017年10月23日
	 * @return
	 */
	public static String getUUID()
	{
	    return UUID.randomUUID().toString().replace("-", "");
	}
	
	/**
	 * 将二进制数据转换为base64字符串
	 * @author       liandi
	 * @since        esign, 2017年10月23日
	 * @param data
	 * @return
	 */
	public static String encodeBase64(byte[] data)
	{
	    BASE64Encoder encoder = new BASE64Encoder();
        
        return encoder.encode(data);
	}
	
	/**
	 * 将二进制数据转换成十六进行字符串
	 * @author       liandi
	 * @since        esign, 2017年10月24日
	 * @param bytes
	 * @return
	 */
	public static String bytes2Hex(byte[] bytes)  
    {  
	    if(null == bytes) return null;
	    
        StringBuilder sb = new StringBuilder();  
        String tmp = null;  
        
        for (byte b : bytes)  
        {  
            // 将每个字节与0xFF进行与运算，然后转化为10进制，然后借助于Integer再转化为16进制  
            tmp = Integer.toHexString(0xFF & b);  
            
            if (tmp.length() == 1)// 每个字节8为，转为16进制标志，2个16进制位  
            {  
                tmp = "0" + tmp;  
            }  
            
            sb.append(tmp);  
        }  
  
        return sb.toString();  
    }  
	
	public static String OnlyFilterOutNumbers(String str)
    {
        StringBuilder result = new StringBuilder();
        
        for (int i = 0, len = str.length(); i < len; i++)
        {
            if (str.charAt(i) >= 48 && str.charAt(i) <= 57)
            {
                result.append(str.charAt(i));
            }
        }
        
        return result.toString();
    }

}
