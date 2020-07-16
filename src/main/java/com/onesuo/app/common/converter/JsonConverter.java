package com.onesuo.app.common.converter;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class JsonConverter {

    public static String convertObj(Object obj) {
        Gson gson=new Gson();
        return gson.toJson(obj);
    }

    public static JSONObject convertObj(String jsonStr) {
        if(StringUtils.isBlank(jsonStr)){
            return null;
        }
        return JSONObject.parseObject(jsonStr);
    }

    public static <T> T convertObj(String jsonStr, Class<T> targetClass) {
        if(StringUtils.isBlank(jsonStr)){
            return null;
        }
        return new Gson().fromJson(jsonStr, targetClass);
    }

    public static <T> List<T> convertObjList(String jsonStr, Class<T> targetClass) {
        if(StringUtils.isBlank(jsonStr)){
            return null;
        }
        return JSONObject.parseArray(jsonStr, targetClass);
    }
}