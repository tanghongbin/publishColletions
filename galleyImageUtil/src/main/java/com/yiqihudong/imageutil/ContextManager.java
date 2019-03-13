package com.yiqihudong.imageutil;

import android.content.Context;

/**
 * Created by tanghongbin on 2017/2/23.
 */

public class ContextManager {
    private static ContextManager mConextManager;
    private Context mContext;

    private Context getContext(){
        return mContext;
    }

    public static Context getmContext() {
        return getmConextManager().getContext();
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    private static ContextManager getmConextManager(){
        if (mConextManager == null){
            throw new NullPointerException("你必须在application调用init方法进行初始化");
        }
        return mConextManager;
    }
    public static void init(Context context){
        if (mConextManager == null){
            synchronized (ContextManager.class){
                if (mConextManager == null){
                    mConextManager = new ContextManager();
                }
            }
        }
        getmConextManager().setmContext(context);
    }
}
