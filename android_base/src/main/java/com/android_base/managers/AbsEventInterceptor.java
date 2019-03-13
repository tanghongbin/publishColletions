package com.android_base.managers;


import com.android_base.core.common.eventbus.BusHelper;

/**
 * 事件拦截器,根据传入事件自己决定是否拦截，不进行发送
 */
public abstract class AbsEventInterceptor<T> {
    T event;

    public AbsEventInterceptor(T event) {
        this.event = event;
    }
    public abstract boolean isIntercept();
    //根据拦截条件判断是否拦截事件
    public void post(){
        if (!isIntercept()){
            BusHelper.postEvent(event);
        }
    }
}
