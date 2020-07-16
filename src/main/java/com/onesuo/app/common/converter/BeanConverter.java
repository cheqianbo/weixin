package com.onesuo.app.common.converter;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean转换器
 * Created by fhp on 16/11/2.
 */
public class BeanConverter {
    /**
     * 转换对象
     * @param source
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T convertObj(Object source, Class<T> targetClass) {
        try {
            if(source==null ){
                return null;
            }
            T target = targetClass.newInstance();
            BeanUtils.copyProperties(source,target);
            return target;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转换ArrayList
     * @param sourceList
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> List<T> convertArrayList(List sourceList, Class<T> targetClass) {
        ArrayList<T> targetList=new ArrayList<T>();
        if(sourceList==null|| sourceList.isEmpty() ){
            return targetList;
        }
        for(Object obj:sourceList) {
            targetList.add(convertObj(obj,targetClass));
        }
        return targetList;
    }
}