package com.yiqihudong.imageutil;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.yiqihudong.imageutil.callback.ListenerHolder;
import com.yiqihudong.imageutil.callback.PhotoWallLoadErrorListener;
import com.yiqihudong.imageutil.callback.PhotoWallOnItemClickListener;
import com.yiqihudong.imageutil.callback.PhotoWallOnLongItemClickListener;
import com.yiqihudong.imageutil.utils.ImageLoaderUtil;
import com.yiqihudong.imageutil.utils.Utils;
import com.yiqihudong.imageutil.view.Options;
import com.yiqihudong.imageutil.view.WindowUtil;
import com.yiqihudong.myutils.R;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;


/**
 * PhotoWallAdapter
 *
 * @author weiwu.song
 */
public class PhotoWallAdapter extends PagerAdapter {
    protected   SDFileHelper sdHelper;
    protected Context context;
    protected PhotoWallOnItemClickListener onItemClickListener;
    protected PhotoWallOnLongItemClickListener onLongItemClickListener;
    protected PhotoWallLoadErrorListener errorListener;
    protected ListenerHolder listenerHolder;

    public PhotoWallLoadErrorListener getErrorListener() {
        return errorListener;
    }

    public void setErrorListener(PhotoWallLoadErrorListener errorListener) {
        this.errorListener = errorListener;
    }

    protected ArrayList<String> photoList = new ArrayList<String>();

    public PhotoWallAdapter(ArrayList<String> photoArrayList,Context context) {
        this.photoList = photoArrayList;
        this.context = context;
        sdHelper = new SDFileHelper(context);
        onItemClickListener = SelectCallbackManager.getInstance().getOnItemClickListener();
        onLongItemClickListener = SelectCallbackManager.getInstance().getOnLongItemClickListener();
        listenerHolder = SelectCallbackManager.getInstance().getListenerHolder();
        initListener();
    }

    private void initListener() {
        if (listenerHolder != null){
            if (listenerHolder.getOnItemClickListener() != null){
                onItemClickListener = listenerHolder.getOnItemClickListener();
            }
            if (listenerHolder.getOnLongItemClickListener() != null){
                onLongItemClickListener = listenerHolder.getOnLongItemClickListener();
            }
            if (listenerHolder.getPhotoWallLoadErrorListener() != null){
                errorListener = listenerHolder.getPhotoWallLoadErrorListener();
            }

        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);//删除页卡
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡
        final PhotoView photoView = new PhotoView(container.getContext());
        photoView.setImageResource(R.drawable.zhanweitu);
        String uri = photoList.get(position);
        addClick(position,photoView,uri);
        if (!Utils.stringIsNull(uri)) {
            if (!uri.contains("http")) {
                uri = "file:" + uri;
            } else {
                if (!(Utils.stringIsNull(uri)) && uri.contains("@")) {
                    uri = uri.substring(0, uri.indexOf("@"));
                }
            }
        }
        Options options = new Options(WindowUtil.getDisplayMetrics(context).widthPixels,
                WindowUtil.getDisplayMetrics(context).heightPixels);
        ImageLoaderUtil.downLoadImageLoader(uri, options, new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                if (getErrorListener() != null){
                    getErrorListener().loadError();
                }else {
                    photoView.setImageResource(R.drawable.zhanweitu);
                }
                Log.i(PhotoWallAdapter.class.getName(),"图片加载失败");

                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                Log.i(PhotoWallAdapter.class.getName(),"图片加载成功");
                photoView.setImageDrawable(resource);
                return false;
            }

        });

        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return photoView;
    }

    protected void addClick(final int position,final PhotoView photoView, final String uri) {
        photoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onLongItemClickListener != null){
                    onLongItemClickListener.onItemLongClick(null,photoView,position,0);
                    return false;
                }
                View view = LayoutInflater.from(context).inflate(R.layout.util_save_pic,null);
                TextView save = (TextView) view.findViewById(R.id.util_save);
                TextView copy = (TextView) view.findViewById(R.id.util_copy);
                final AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setView(view)
                        .create();
                WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
                layoutParams.width = WindowUtil.getDisplayMetrics(context).widthPixels/2;
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                alertDialog.getWindow().setAttributes(layoutParams);
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(true);

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        sdHelper.savePicture(createFileName(),uri);
                    }
                });
                copy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        copyText(context,uri);
                        Toast.makeText(context,"图片拷贝成功",Toast.LENGTH_SHORT).show();
                    }
                });

                return false;
            }
        });
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(null,photoView,position,0);
                }
            }
        });
    }


    protected String createFileName() {
        return System.currentTimeMillis()+"lx.png";
    }

    @Override
    public int getCount() {
        return photoList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    public  void copyText(Context context, CharSequence text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newPlainText("text", text));
    }
}
