package com.yiqihudong.imageutil.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.TransitionOptions;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.yiqihudong.myutils.R;
import com.yiqihudong.imageutil.ContextManager;
import com.yiqihudong.imageutil.view.Options;

import java.io.File;

/**
 * ImageLoaderUtil ,图片工具类
 */
public class ImageLoaderUtil {

    private static DisplayMetrics metric;

    /**
     * 展示本地文件需要加上 file: 前缀
     *
     * @param url
     * @param imageView
     */
    public static void displayImage(String url, int defaultImage, ImageView imageView, boolean isCenterCrop) {
        RequestManager glide = Glide.with(ContextManager.getmContext());
        RequestBuilder drawableRequest = null;
        RequestOptions options = new RequestOptions();
        if (!TextUtils.isEmpty(url)) {
            if (url.startsWith("file:")) {
                url = url.replaceFirst("file:", "");
                drawableRequest = glide.load(new File(url));
            } else {
                drawableRequest = glide.load(url);
            }
        } else {
            drawableRequest = glide.load("");
        }
        if (defaultImage != 0) {
            options.placeholder(defaultImage);
        }
        if (isCenterCrop) {
            options.centerCrop();
        }
        drawableRequest.apply(options);
        drawableRequest.into(imageView);
    }

    /**
     * 是否采用cetrerCrop策略显示图片
     * @param url
     * @param defaultImage
     * @param imageView
     */
    public static void displayImage(String url, int defaultImage,ImageView imageView) {
        displayImage(url, defaultImage, imageView,true);
    }

    public static void displayImage(String url, ImageView imageView) {
        displayImage(url, R.drawable.zhanweitu, imageView);
    }

    /**
     * @param url             显示图片网络地址，如果是本地文件需要加上 file: 前缀
     * @param options
     * @param requestListener
     */
    public static <T> void downLoadImageLoader(String url, Options options, RequestListener<T> requestListener) {
        RequestManager glide = Glide.with(ContextManager.getmContext());
        RequestBuilder drawableRequest = null;
        if (!TextUtils.isEmpty(url)) {
            if (url.startsWith("file:")) {
                url = url.replaceFirst("file:", "");
                drawableRequest = glide.load(new File(url));
            } else {
                drawableRequest = glide.load(url);
            }
        }
        if (options == null) {
            options = generateDefaultOptions();
        }
        drawableRequest.listener(requestListener)
                .into(options.getWidth(), options.getHeight());
    }

    public static <T> void downLoadImageLoader(String url, RequestListener<T> requestListener) {
        downLoadImageLoader(url, null, requestListener);
    }

    /**
     * 默认生成手机屏幕宽度和高度
     *
     * @return
     */
    private static Options generateDefaultOptions() {
        Options options = new Options();
        if (metric == null) {
            WindowManager windowManager =
                    (WindowManager) ContextManager.getmContext().getSystemService(Context.WINDOW_SERVICE);
            metric = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(metric);
        }

        options.setWidth(metric.widthPixels);
        options.setHeight(metric.heightPixels);
        return options;
    }


}
