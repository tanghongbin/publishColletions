package com.yiqihudong.imageutil.callback;

/**
 * Created by tanghongbin on 2017/7/31.监听管理器
 */

public class ListenerHolder {
    PhotoWallLoadErrorListener photoWallLoadErrorListener;
    protected PhotoWallOnItemClickListener onItemClickListener;
    protected PhotoWallOnLongItemClickListener onLongItemClickListener;

    public PhotoWallLoadErrorListener getPhotoWallLoadErrorListener() {
        return photoWallLoadErrorListener;
    }

    public void setPhotoWallLoadErrorListener(PhotoWallLoadErrorListener photoWallLoadErrorListener) {
        this.photoWallLoadErrorListener = photoWallLoadErrorListener;
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
}
