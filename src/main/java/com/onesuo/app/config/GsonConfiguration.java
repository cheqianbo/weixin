package com.onesuo.app.config;

import com.google.gson.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.json.Json;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class GsonConfiguration {
    @Bean
    Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        gsonBuilder.registerTypeAdapter(Date.class,
            (JsonDeserializer<Date>) (json, typeOfT, context) -> {
                String asString = json.getAsString();
                if(StringUtils.isNotBlank(asString)) {
                    try {
                        return df.parse(asString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            });
        gsonBuilder.registerTypeAdapter(Date.class,
            (JsonSerializer<Date>) (src, typeOfSrc, context) -> {
                if(src!=null) {
                    String format = df.format(src);
                    JsonPrimitive jsonObject=new JsonPrimitive(format);
                    return jsonObject;

                }
                return null;
            });
        gsonBuilder.registerTypeAdapter(Json.class, new JsonSerializer<Json>() {
            @Override
            public JsonElement serialize(Json json, Type typeOfSrc, JsonSerializationContext context) {
                if (json != null)
                    return new JsonParser().parse(json.value());
                else
                    return new JsonPrimitive("");
            }
        });
        return gsonBuilder.create();
    }
}