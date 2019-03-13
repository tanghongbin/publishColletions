package com.android_base.managers;

/**
 * 用于管理单例实例
 */
public class DataManager {
    private static DataManager instance;
    public static DataManager getInstance(){
        if(instance == null){
            synchronized (DataManager.class){
                if(instance == null){
                    instance = new DataManager();
                }
            }
        }
        return instance;
    }


    /**
     * 注销时清空数据信息
     */
    public void loginOut(){
        instance = null;
    }
}
