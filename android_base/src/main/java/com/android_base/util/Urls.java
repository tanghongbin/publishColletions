package com.android_base.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.android_base.BuildConfig;


/**
 *
 */
public class Urls {
    private static final String SAVE_DEBUG_URL = "YG_save_debug_url";
    private static final String SAVE_DEBUG_URL_INSTANCE = "save_debug_url_instance";

    // 生产环境地址
    private String PUBLISH_SERVER = "http://114.55.173.214:8080";
//            "/jeecg/xingruiBackendController.do?general";

    // 获取当前环境的URL地址
    public String getServerUrl(Context context) {
        return BuildConfig.DEBUG ? ("http://" + getDebugDomain(context) + ":8080") : PUBLISH_SERVER;
    }

    public static String getDebugDomain(Context context) {
        SharedPreferences refrences =context.getSharedPreferences(SAVE_DEBUG_URL, Context.MODE_PRIVATE);
        return refrences.getString(SAVE_DEBUG_URL_INSTANCE, "120.76.226.150");
    }


    public static void saveDebugDomain(Context context,String url) {
        SharedPreferences refrences =context.getSharedPreferences(SAVE_DEBUG_URL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = refrences.edit();
        editor.putString(SAVE_DEBUG_URL_INSTANCE,url);
        editor.commit();
    }

}
