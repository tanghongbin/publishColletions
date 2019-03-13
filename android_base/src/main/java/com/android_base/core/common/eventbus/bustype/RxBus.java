package com.android_base.core.common.eventbus.bustype;

import com.android_base.core.common.eventbus.BusInterface;
import com.hwangjr.rxbus.Bus;



/**
 *
 */
public class RxBus implements BusInterface {
    private static Bus bus = new Bus();


    @Override
    public void regist(Object o) {
        bus.register(o);
    }

    @Override
    public void unRegiest(Object o) {
        bus.unregister(o);
    }

    @Override
    public void postEvent(Object event) {
        bus.post(event);
    }

    @Override
    public void postEvent(String tag, Object event) {
        bus.post(event);
    }
}
