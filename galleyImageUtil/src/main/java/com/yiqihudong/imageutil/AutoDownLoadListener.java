package com.yiqihudong.imageutil;

import android.support.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * Created by tangt on 2016/1/15.
 */
public class AutoDownLoadListener implements RequestListener {


    @Override
    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
        return false;
    }

    @Override
    public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
        String fileName = System.currentTimeMillis() + ".jpg";
//        Utils.saveImageToGallery(fileName, context, loadedImage);
//        Toast.makeText(context, "图片保存成功", Toast.LENGTH_SHORT).show();
        return false;
    }
}
