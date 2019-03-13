package com.yiqihudong.imageutil.view;

import android.content.Context;
import android.content.Intent;

import com.yalantis.ucrop.UCrop;
import com.yiqihudong.imageutil.CropOptions;

/**
 *
 */
public class ImageChooseAndCropUtil {
    public static final String TYPE = "type";
    public static final int TYPE_CHOOSE_AND_CROP = 0x128;//裁剪和选择或照相都要
    public static final int TYPE_CHOOSE = 0x129;//只选择图片或只照相
    public static final int TYPE_CROP = 0x130;//只裁剪
    public static final int TYPE_CAMERA = 0x131;//只做照相，不做裁剪
    public static final int TYPE_CAMERA_CROP = 0x132;//照相，并且裁剪
    public static final int TYPE_SELECTED_PIC_FROM_GALLERY = 0x133;//从图库选择照片，不做裁剪
    public static final int TYPE_SELECTED_PIC_FROM_GALLERY_CROP = 0x134;//从图库选择照片，并且裁剪
    public static final String CHOOSE_TYPE = "choose_type";
    public static final String CROP_OPTIONS = "crop_options";
    public static final String IMAGE_PATH = "image_path";
    public static final String NEED_DOWNLOAD = "need_download";

    private ImageCropCallback cropCallback;
    protected CropOptions cropOptions;
    protected String authorities;

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public CropOptions getCropOptions() {
        return cropOptions;
    }

    public void setCropOptions(CropOptions cropOptions) {
        this.cropOptions = cropOptions;
    }

    static ImageChooseAndCropUtil imageChooseAndCropUtil;
    Context context;

    public static ImageChooseAndCropUtil getInstance(Context context) {
        if (imageChooseAndCropUtil == null) {
            synchronized (ImageChooseAndCropUtil.class) {
                if (imageChooseAndCropUtil == null) {
                    imageChooseAndCropUtil = new ImageChooseAndCropUtil(context);
                }
            }
        }
        return imageChooseAndCropUtil;
    }

    public ImageChooseAndCropUtil(Context context) {
        this.context = context;
    }

    /**
     * 选择单张图片并且裁剪，然后再返回
     *
     * @param cropCallback
     */
    public void chooseCropPic(Context context,ImageCropCallback cropCallback) {
        chooseCropPic(context,cropCallback,null);
    }


    /**
     * 选择单张图片并且裁剪，然后再返回
     *
     * @param cropCallback
     */
    public void chooseCropPic(Context context,ImageCropCallback cropCallback,CropOptions options) {
        this.cropCallback = cropCallback;
        this.cropOptions = options;
        judgeCallback();
        context.startActivity(new Intent(context, ImageSingleChooseActivity.class)
                .putExtra(ImageChooseAndCropUtil.CROP_OPTIONS,options)
                .putExtra(TYPE, TYPE_CHOOSE_AND_CROP));
    }


    /**
     * 只选择图片，不做裁剪
     *
     * @param cropCallback
     */
    public void choosePic(Context context,ImageCropCallback cropCallback) {
        this.cropCallback = cropCallback;
        judgeCallback();
        context.startActivity(new Intent(context, ImageSingleChooseActivity.class)
                .putExtra(TYPE, TYPE_CHOOSE));
    }

    /**
     * 只做裁剪
     *
     * @param cropCallback
     */
    public void cropPic(Context context,String path,ImageCropCallback cropCallback) {
        cropPic(context,path,cropCallback,false,null);
    }

    /**
     * 只做裁剪
     *  @param path 图片路径
     * @param cropCallback
     */
    public void cropPic(Context context,String path,ImageCropCallback cropCallback,boolean isNeedDownload,CropOptions options) {
        this.cropCallback = cropCallback;
        this.cropOptions = options;
        judgeCallback();
        context.startActivity(new Intent(context, ImageSingleChooseActivity.class)
                .putExtra(ImageChooseAndCropUtil.CROP_OPTIONS,options)
                .putExtra(ImageChooseAndCropUtil.IMAGE_PATH,path)
                .putExtra(ImageChooseAndCropUtil.TYPE,TYPE_CROP)
                .putExtra(NEED_DOWNLOAD,isNeedDownload)
                .putExtra(ImageChooseAndCropUtil.CHOOSE_TYPE, TYPE_CROP));

    }


    /**
     * 只做照相，不做裁剪
     *
     * @param context
     * @param cropCallback
     */
    public void camera(Context context, ImageCropCallback cropCallback) {
        this.cropCallback = cropCallback;
        judgeCallback();
        context.startActivity(new Intent(this.context, ImageSingleChooseActivity.class)
                .putExtra(TYPE, TYPE_CHOOSE)
                .putExtra(CHOOSE_TYPE, TYPE_CAMERA));
    }

    /**
     * 照相并且裁剪
     *
     * @param cropCallback
     */
    public void cameraCrop(Context context,ImageCropCallback cropCallback,CropOptions options) {
        this.cropCallback = cropCallback;
        this.cropOptions = options;
        judgeCallback();
        context.startActivity(new Intent(context, ImageSingleChooseActivity.class)
                .putExtra(CHOOSE_TYPE, TYPE_CAMERA_CROP)
                .putExtra(ImageChooseAndCropUtil.CROP_OPTIONS,options)
                .putExtra(TYPE, TYPE_CHOOSE_AND_CROP));
    }

    /**
     * 直接从图库选择照片不做裁剪
     */
    public void selectedImageFromGallery(Context context,ImageCropCallback cropCallback) {
        this.cropCallback = cropCallback;
        judgeCallback();
        context.startActivity(new Intent(context, ImageSingleChooseActivity.class)
                .putExtra(TYPE, TYPE_CHOOSE)
                .putExtra(CHOOSE_TYPE, TYPE_SELECTED_PIC_FROM_GALLERY));
    }

    /**
     * 直接从图库选择照片，并且裁剪
     *
     * @param cropCallback
     */
    public void selectedImageFromGalleryCrop(Context context,ImageCropCallback cropCallback, CropOptions options) {
        this.cropCallback = cropCallback;
        this.cropOptions = options;
        judgeCallback();
        context.startActivity(new Intent(context, ImageSingleChooseActivity.class)
                .putExtra(TYPE, TYPE_CHOOSE_AND_CROP)
                .putExtra(ImageChooseAndCropUtil.CROP_OPTIONS,options)
                .putExtra(CHOOSE_TYPE, TYPE_SELECTED_PIC_FROM_GALLERY_CROP));
    }


    /**
     * 直接从图库选择照片，并且裁剪
     *
     * @param cropCallback
     */
    public void selectedImageFromGalleryCrop(Context context,ImageCropCallback cropCallback) {
        selectedImageFromGalleryCrop(context,cropCallback,null);
    }
    /**
     * 判断callback方法
     */
    private void judgeCallback() {
        if (cropCallback == null) {
            throw new NullPointerException("callback--不能为空");
        }
    }

    public ImageCropCallback getCropCallback() {
        return cropCallback;
    }


}
