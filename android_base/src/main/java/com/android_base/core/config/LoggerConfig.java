package com.android_base.core.config;

/**
 * 日志打印常用配置参数
 */
public class LoggerConfig {
    /**
     * 配置常用标签
     * @param tag
     */
    public static void init(String tag){
        TAG = tag;
    }
    public static  String TAG = "TAG";
}
