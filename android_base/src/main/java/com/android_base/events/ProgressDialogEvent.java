package com.android_base.events;


import com.android_base.core.activities.BaseActivity;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.thread.EventThread;

/**
 *
 * 进度款给加载事件
 */
public class ProgressDialogEvent {
    BaseActivity activity;

    public ProgressDialogEvent(BaseActivity activity) {
        this.activity = activity;
    }

    @Subscribe(thread = EventThread.MAIN_THREAD)
    public void startRequest(RequestStartEvent event){
        if(!activity.isFinishing()){
            activity.showDialog();
        }
    }
    @Subscribe(thread = EventThread.MAIN_THREAD)
    public void endRequest(RequestEndEvent event){
        if(!activity.isFinishing()){
            activity.dismissDialog();
        }
    }
}
