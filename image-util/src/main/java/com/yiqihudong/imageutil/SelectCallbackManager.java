package com.yiqihudong.imageutil;

import com.yiqihudong.imageutil.callback.ListenerHolder;
import com.yiqihudong.imageutil.callback.PhotoWallOnItemClickListener;
import com.yiqihudong.imageutil.callback.PhotoWallOnLongItemClickListener;
import com.yiqihudong.imageutil.callback.SelectPicCallback;

/**
 * Created by tanghongbin on 2017/1/7.
 */

public class SelectCallbackManager {
    private SelectCallbackManager(){

    }
    private static SelectCallbackManager manager = new SelectCallbackManager();
    public static SelectCallbackManager getInstance(){
        return manager;
    }
    SelectPicCallback selectPicCallback;
    PhotoWallOnItemClickListener onItemClickListener;
    PhotoWallOnLongItemClickListener onLongItemClickListener;
    ListenerHolder listenerHolder;

    public ListenerHolder getListenerHolder() {
        return listenerHolder;
    }

    public void setListenerHolder(ListenerHolder listenerHolder) {
        this.listenerHolder = listenerHolder;
    }

    public PhotoWallOnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(PhotoWallOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public PhotoWallOnLongItemClickListener getOnLongItemClickListener() {
        return onLongItemClickListener;
    }

    public void setOnLongItemClickListener(PhotoWallOnLongItemClickListener onLongItemClickListener) {
        this.onLongItemClickListener = onLongItemClickListener;
    }

    public SelectPicCallback getSelectPicCallback() {
        return selectPicCallback;
    }

    public void setSelectPicCallback(SelectPicCallback selectPicCallback) {
        this.selectPicCallback = selectPicCallback;
    }
    public void clearCallback(){
        selectPicCallback = null;
    }
}
