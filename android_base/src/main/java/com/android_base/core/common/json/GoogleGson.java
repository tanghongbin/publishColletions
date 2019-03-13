package com.android_base.core.common.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;


import java.util.List;

/**
 * 具体json解析类
 */
public class GoogleGson implements GsonInterface {

    private Gson gson;
    public GoogleGson() {
        gson = new Gson();
    }

    @Override
    public <T> T parseObject(String json, Class<T> t) {
        try{
            return (T) gson.fromJson(json,t);
        }catch (Exception e){
            Logger.e("TAG",e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> String toJSONString(T t) {
        return gson.toJson(t);
    }

    @Override
    public <T> List<T> parseJSONArray(String jsonArray, Class<T> t) {
        return gson.fromJson(jsonArray, new TypeToken<List<T>>() {
        }.getType());
    }
}
