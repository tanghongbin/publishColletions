package com.yiqihudong.imageutil.view;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by tanghongbin on 2017/3/8.
 */
public class WindowUtil {
    private static DisplayMetrics displayMetrics;

    private static void window(Context context) {
        if (displayMetrics == null) {
            displayMetrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        }
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        window(context);
        return displayMetrics;
    }
}
