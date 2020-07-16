package com.onesuo.app.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class BizUtils {
    /**
     * 获取url
     * @param param
     * @param url
     * @param <T>
     * @return
     */
    public static <T>String prepareGetUrl(T param, String url){
        if (null == param || StringUtils.isBlank(url)){
            return url;
        }

        Field[] fields = param.getClass().getDeclaredFields();
        if (null == fields || fields.length < 1){
            return url;
        }

        String resultUrl = url.concat("?");
        try {
            int i = 0;
            for (Field field: fields){
                PropertyDescriptor pd = new PropertyDescriptor(
                    field.getName(), param.getClass());
                Method getMethod = pd.getReadMethod();// 获得get方法
                Object value = getMethod.invoke(param);// 执行get方法返回一个Object
                if (null == value){
                    continue;
                }
                if (i == 0){
                    resultUrl = resultUrl.concat(field.getName()).concat("=").concat(String.valueOf(value));
                }else{
                    resultUrl = resultUrl.concat("&").concat(field.getName()).concat("=").concat(String.valueOf(value));
                }
                i++;
            }
        } catch (IntrospectionException e) {
            log.error("获取url失败", e);
        } catch (IllegalAccessException e) {
            log.error("获取url失败", e);
        } catch (InvocationTargetException e) {
            log.error("获取url失败", e);
        }

        return resultUrl;
    }
}