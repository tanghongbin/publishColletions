package com.android_base.events;

/**
 * 基础事件类型，请求结果：data，错误事件类型ERROR,su
 */
public class BaseEvent<ERROR extends ErrorEvent> extends JudgeResultEvent{
    protected String data;
    protected ERROR error;

    public BaseEvent() {
    }

    public BaseEvent(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ERROR getError() {
        return error;
    }

    public void setError(ERROR error) {
        this.error = error;
    }

    @Override
    public boolean isSuccess() {
        return error == null;
    }
}
