package com.android_base.core.interfaces;

public interface Callback<T> {
    void onSuccess(T t);
    void onFailed(String message);
}