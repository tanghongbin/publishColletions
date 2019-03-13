package com.android_base.core.common.json;


import com.android_base.core.config.LoggerConfig;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * 利用helper类方便以后升级更新, 使用代理，
 * 提供方法：
 * 1.parsetObject(json,t),将指定的json字符串解析成指定类-json(json字符串),
 * t(指定解析的对象类型，这里指定object对象，不包括list，如想解析成集合，使用parseJSONArray)
 *
 * 2.parseJSONArray(json,t)将指定json字符串解析成集合,json-json字符串,
 * t-指定解析的List容器内的对象的类型
 *
 * 3.toJSONString(t),t-将要解析成字符串的对象类型，可以是自定义类型或者是List
 * 容器子类，如果要将Map和JSON互换，则可以使用ConvertJSONFromMap类
 */
public final class JsonHelper {
    private static GsonInterface gson;

    private static GsonInterface getInstance() {
        if (gson == null) {
            synchronized (JsonHelper.class) {
                if (gson == null) {
                    gson = new GoogleGson();
                }
            }
        }
        return gson;
    }

    /**
     * @param json
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String json, Class<T> t) {
        try {
            Logger.i(LoggerConfig.TAG,"解析字符串-"+json+"   解析类型-"+t);
            if (json == null || t == null) {
                return null;
            }
            return getInstance().parseObject(json, t);
        } catch (Exception e) {
            Logger.e("TAG", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static <T> String toJSONString(T t) {
        if (t == null) {
            return null;
        }
        return getInstance().toJSONString(t);
    }

    public static <T> List<T> parseJSONArray(String json, Class<T> t) {
        Logger.i(LoggerConfig.TAG,"解析字符串-"+json+"   解析集合类型-"+t);
        if (json == null || t == null) {
            return null;
        }
        return getInstance().parseJSONArray(json, t);
    }
}
