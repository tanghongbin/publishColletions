package com.android_base.core.common.eventbus;

/**
 * 所有事件注册库的接口，
 * 提供注册事件，取消注册事件，发送事件
 */
public interface BusInterface {
    void regist(Object o);
    void unRegiest(Object o);
    void postEvent(Object event);
    void postEvent(String tag, Object event);
}
