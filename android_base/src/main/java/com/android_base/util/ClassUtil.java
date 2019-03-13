package com.android_base.util;

/**
 *
 */
public class ClassUtil {
    public static  <T>T getInstance(Class<T> classType){
        T event = null;
        try {
            event = classType.newInstance();
            return event;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return event;
        }
    }
}
