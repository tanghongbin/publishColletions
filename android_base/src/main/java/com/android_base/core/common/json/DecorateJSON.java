package com.android_base.core.common.json;

import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Map;

/**
 * JSONObject 的装饰类，使用原声JSONObject的put方法会抛出异常，
 * 使用fastjson有几率出现读取不到某个字段，
 */
public class DecorateJSON {
    JSONObject jsonObject;

    public DecorateJSON(Map map) {
        this.jsonObject = new JSONObject(map);
    }
    public DecorateJSON() {
        this.jsonObject = new JSONObject();
    }
    public DecorateJSON(JSONTokener jsonTokener) {
        try {
            this.jsonObject = new JSONObject(jsonTokener);
        } catch (JSONException e) {
            e.printStackTrace();
            Logger.e("jsonTokener 初始化失败");
        }
    }
    public DecorateJSON(String json) {
        try {
            this.jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
            Logger.e("json 初始化失败");
        }
    }

    public DecorateJSON(JSONObject object, String [] names) {
        try {
            this.jsonObject = new JSONObject(object,names);
        } catch (JSONException e) {
            e.printStackTrace();
            Logger.e("json 初始化失败");
        }
    }

    public DecorateJSON put(String key, Object value){
        try {
            jsonObject.put(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            return this;
        }
    }

    @Override
    public String toString() {
        return jsonObject == null ? "":jsonObject.toString();
    }
}
