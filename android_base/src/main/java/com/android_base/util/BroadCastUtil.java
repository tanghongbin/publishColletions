package com.android_base.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

/**
 * 广播处理类
 */
public class BroadCastUtil {
    /**
     * 注册广播
     * @param context
     * @param receiver
     * @param actions
     */
    public static void registReceiver(Context context, BroadcastReceiver receiver,String... actions){
        IntentFilter filter = new IntentFilter();
        for(String action:actions){
            filter.addAction(action);
        }
        context.registerReceiver(receiver,filter);
    }

    public static void registReceiver(Context context, BroadcastReceiver receiver,String[] categories,String... actions){
        IntentFilter filter = new IntentFilter();
        for(String category:categories){
            filter.addCategory(category);
        }
        for(String action:actions){
            filter.addAction(action);
        }
        context.registerReceiver(receiver,filter);
    }

    /**
     * 取消注册
     * @param context
     * @param receiver
     */
    public static void unRegistReceiver(Context context,BroadcastReceiver receiver){
        context.unregisterReceiver(receiver);
    }
}
