package com.yiqihudong.imageutil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;
import com.yalantis.ucrop.util.FileUtils;
import com.yiqihudong.imageutil.callback.ListenerHolder;
import com.yiqihudong.imageutil.callback.PhotoWallOnItemClickListener;
import com.yiqihudong.imageutil.callback.PhotoWallOnLongItemClickListener;
import com.yiqihudong.imageutil.callback.SelectPicCallback;
import com.yiqihudong.imageutil.utils.Constant;
import com.yiqihudong.imageutil.view.ImageChooseAndCropUtil;
import com.yiqihudong.imageutil.view.ImageCropCallback;
import com.yiqihudong.imageutil.view.ImageSingleChooseActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

/**
 * Created by tanghongbin on 2016/12/26.
 * 图片选择的帮助类
 * !!!!!!!!!!使用框架前必读此说明
 * 1.上下文环境的初始化 - 在application的onCreate方法中调用
 *
 * ContextManager.init(this)
 *
 *
 *
 * 2.可使用的文件地址授权，在application的onCreate方法中调用
 *
 * ImageSelectedHelper.initProviderAuth(this,authroies)方法
 *
 *
 * 授权的authroies必须以ImageSingleChooseActivity.SPECIAL_CUSTOM_URL_HEADER为前缀
 * 再加上自定义的字符串如：
 *
 *        authroies = ImageSingleChooseActivity.SPECIAL_CUSTOM_URL_HEADER
 *             + (自定义字符串如) com.example
 *
 *
 *
 * 3.同时还必须要在app主工程的AndroidManifest.xml文件下进行如下声明,
 *
 *
 *      < provider
 *         android:name=".module.CustomFileProvider"
 *         android:authorities="com.publishgallery.custom.auth.mgbapp.image"
 *         android:exported="false"
 *         android:grantUriPermissions="true">
 *      < meta-data
 *         android:name="android.support.FILE_PROVIDER_PATHS"
 *         android:resource="@xml/file_paths">< /meta-data>
 *      < /provider>
 *
 *        <provider>标签内容定义如下:
 *           1. 这里的authorities必须和initProviderAuth()方法的authroies保持一致,
 *             如  authroies = ImageSingleChooseActivity.SPECIAL_CUSTOM_URL_HEADER
 * *             + (自定义字符串如) com.example
 * *         2.name为自定义的FileProvider的子类,是个空类，example:
 * <p>
 *            public class CustomFileProvider extends FileProvider {
 * <p>
 *           }
 *
 *
 *        <meta>标签内的内容不用更改
 */

public class ImageSelectedHelper {
    //如果传入数量为0，那么默认设置为不限制数量,也可传入默认显示图片集合
    @Deprecated
    public static void startForResult(Activity activity, int maxCount, ArrayList<String> alreadySelectList, int requestCode) {
        Intent intent = new Intent(activity, GalleryImagesActivity.class);
        if (maxCount > 0) {
            intent.putExtra("image_max", maxCount);
        }
        if (alreadySelectList != null && alreadySelectList.size() > 0) {
            intent.putExtra(Constant.ALREADY_SELECT_KEY, alreadySelectList);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    @Deprecated
    /**
     *推荐使用selectPic 使用回调方式可以直接获取照片
     */
    public static void startForResult(Activity activity, int maxCount, int requestCode) {
        startForResult(activity, maxCount, null, requestCode);
    }

    @Deprecated
    public static void startForResult(Activity activity, int requestCode) {
        startForResult(activity, 0, null, requestCode);
    }

    /**
     * 图片轮播
     */
    public static void photoWall(final Context context, final ArrayList<String> images, final int position,
                                 PhotoWallOnItemClickListener onItemClickListener,
                                 PhotoWallOnLongItemClickListener onItemLongClickListener) {
        SelectCallbackManager.getInstance().setOnItemClickListener(onItemClickListener);
        SelectCallbackManager.getInstance().setOnLongItemClickListener(onItemLongClickListener);

        requestPermission(context, new Callback() {
            @Override
            public void onSuccess(Object o) {
                context.startActivity(new Intent(context, PhotoWallActivity.class)
                        .putExtra("list", images)
                        .putExtra(PhotoWallActivity.KEY_POSITION, position));
            }

            @Override
            public void onFailed(String message) {

            }
        });
    }

    /**
     * 图片轮播
     */
    public static void photoWall(final Context context, final ArrayList<String> images, final int position,
                                 ListenerHolder listenerHolder) {
        SelectCallbackManager.getInstance().setListenerHolder(listenerHolder);

        requestPermission(context, new Callback() {
            @Override
            public void onSuccess(Object o) {
                context.startActivity(new Intent(context, PhotoWallActivity.class)
                        .putExtra("list", images)
                        .putExtra(PhotoWallActivity.KEY_POSITION, position));
            }

            @Override
            public void onFailed(String message) {

            }
        });
    }

    public static void photoWall(Context context, ArrayList<String> images, int position, PhotoWallOnItemClickListener
            onItemClickListener) {
        photoWall(context, images, position, onItemClickListener, null);
    }

    public static void photoWall(Context context, ArrayList<String> images, int position) {
        photoWall(context, images, position, null, null);
    }

    /**
     * 图片轮播
     *
     * @param context 上下文环境对象
     * @param images  显示的图片集合
     */
    public static void photoWall(Context context, ArrayList<String> images) {
        photoWall(context, images, 0);
    }

    public static void selectMutiplePic(final Context context, final int maxCount, final ArrayList<String> alreadySelectList, final SelectPicCallback selectPicCallback) {
        selectMutiplePic(context, maxCount, alreadySelectList, null, selectPicCallback);
    }

    /**
     * 从图库选择多张照片
     *
     * @param maxCount          选择照片的最大数量
     * @param alreadySelectList 刚刚是否已经从图片选择的照片，可为null
     * @param selectPicCallback 照片选择后的回调
     */
    public static void selectMutiplePic(final Context localContext, final int maxCount,
                                        final ArrayList<String> alreadySelectList,
                                        final GalleryImagesActivity.Options options,
                                        final SelectPicCallback selectPicCallback) {
        requestPermission(localContext, new Callback() {
            @Override
            public void onSuccess(Object o) {
                SelectCallbackManager.getInstance().setSelectPicCallback(selectPicCallback);
                Intent intent = new Intent(localContext, GalleryImagesActivity.class);
                if (maxCount > 0) {
                    intent.putExtra("image_max", maxCount);
                }
                if (options != null) {
                    intent.putExtra("options", options);
                }
                if (alreadySelectList != null && alreadySelectList.size() > 0) {
                    intent.putExtra(Constant.ALREADY_SELECT_KEY, alreadySelectList);
                }
                localContext.startActivity(intent);
            }

            @Override
            public void onFailed(String message) {
//                if (selectPicCallback != null) {
//                    selectPicCallback.selectPic(null);
//                }
            }
        });
    }

    /**
     * 选择多张图片
     *
     * @param context           上下文环境对象
     * @param maxCount          选择照片的最大数量
     * @param selectPicCallback 照片选择后的回调
     */
    public static void selectMutiplePic(Context context, int maxCount, SelectPicCallback selectPicCallback) {
        selectMutiplePic(context, maxCount, null, selectPicCallback);
    }

    /**
     * 选择多张图片
     *
     * @param context           上下文环境对象
     * @param selectPicCallback 照片选择后的回调
     */
    public static void selectMutiplePic(Context context, SelectPicCallback selectPicCallback) {
        selectMutiplePic(context, 0, selectPicCallback);
    }

    /**
     * 照相并且可判断是否可裁剪
     *
     * @param context
     * @param isNeedCrop 是否需要裁剪
     * @param callback   回调
     */
    public static void camera(final Context context, final boolean isNeedCrop, final CropOptions options, final ImageCropCallback callback) {
        checkAuthorites(context);
        requestPermission(context, new Callback() {
            @Override
            public void onSuccess(Object o) {
                if (isNeedCrop) {
                    ImageChooseAndCropUtil.getInstance(context).cameraCrop(context, callback, options);
                } else {
                    ImageChooseAndCropUtil.getInstance(context).camera(context, callback);
                }

            }

            @Override
            public void onFailed(String message) {
//                if (callback != null) {
//                    callback.call(null);
//                }
            }
        });
    }

    private static void checkAuthorites(Context context) {
        if (TextUtils.isEmpty(ImageChooseAndCropUtil.getInstance(context).getAuthorities())) {
            throw new NullPointerException("你必须提供自己的FileProvider的子类，并添加路径,调用ImageSelectHelper.setAuthorties(String auth);");
        }
    }

    /**
     * 照相并且可判断是否可裁剪
     *
     * @param context
     * @param isNeedCrop 是否需要裁剪
     * @param callback   回调
     */
    public static void camera(final Context context, final boolean isNeedCrop, final ImageCropCallback callback) {
        camera(context, isNeedCrop, null, callback);
    }

    /**
     * 照相，默认不进行裁剪
     *
     * @param context
     * @param callback
     */
    public static void camera(Context context, ImageCropCallback callback) {
        camera(context, false, callback);
    }

    /**
     * 选择单张照片，包括照相和图库，但不做裁剪
     *
     * @param context
     * @param isNeedCrop 是否需要裁剪
     * @param callback
     */
    public static void selectSinglePic(final Context context, final boolean isNeedCrop, final CropOptions options, final ImageCropCallback callback) {
        requestPermission(context, new Callback() {
            @Override
            public void onSuccess(Object o) {
                if (isNeedCrop) {
                    ImageChooseAndCropUtil.getInstance(context).chooseCropPic(context, callback, options);
                } else {
                    ImageChooseAndCropUtil.getInstance(context).choosePic(context, callback);
                }
            }

            @Override
            public void onFailed(String message) {
//                if (callback != null) {
//                    callback.call(null);
//                }
            }
        });
    }

    /**
     * 选择单张照片，包括照相和图库，但不做裁剪
     *
     * @param context
     * @param isNeedCrop 是否需要裁剪
     * @param callback
     */
    public static void selectSinglePic(final Context context, final boolean isNeedCrop, final ImageCropCallback callback) {
        selectSinglePic(context, isNeedCrop, null, callback);
    }

    /**
     * 选择单张照片，包括照相和图库 ，默认不裁剪
     *
     * @param context
     * @param callback
     */
    public static void selectSinglePic(Context context, ImageCropCallback callback) {
        selectSinglePic(context, false, callback);
    }


    /**
     * 从图库选择单张照片
     *
     * @param context
     * @param isNeedCrop 是否需要裁剪
     * @param callback
     */
    public static void selectedSingleImageFromGallery(final Context context, final boolean isNeedCrop, final ImageCropCallback callback) {
        selectedSingleImageFromGallery(context, isNeedCrop, null, callback);
    }

    /**
     * 从图库选择单张照片
     *
     * @param context
     * @param isNeedCrop 是否需要裁剪
     * @param callback
     */
    public static void selectedSingleImageFromGallery(final Context context, final boolean isNeedCrop, final CropOptions options, final ImageCropCallback callback) {
        requestPermission(context, new Callback() {
            @Override
            public void onSuccess(Object o) {
                if (isNeedCrop) {
                    ImageChooseAndCropUtil.getInstance(context).selectedImageFromGalleryCrop(context, callback, options);
                } else {
                    ImageChooseAndCropUtil.getInstance(context).selectedImageFromGallery(context, callback);
                }
            }

            @Override
            public void onFailed(String message) {
//                if (callback != null) {
//                    callback.call(null);
//                }
            }
        });
    }

    /**
     * 默认不进行裁剪
     *
     * @param context
     * @param callback
     */
    public static void selectedSingleImageFromGallery(Context context, ImageCropCallback callback) {
        selectedSingleImageFromGallery(context, false, callback);
    }

    private static void requestPermission(Context context, final Callback callback) {
        if (TextUtils.isEmpty(ImageChooseAndCropUtil.getInstance(context).getAuthorities()) ||
                !ImageChooseAndCropUtil.getInstance(context).getAuthorities().startsWith(ImageSingleChooseActivity.SPECIAL_CUSTOM_URL_HEADER)) {
            throw new NullPointerException("你必须在application的oncreate方法调用ImageSelectHelper.initProviderAuth(String authorties)初始化," +
                    "authorties是你自定义的FileProvider的子类的authorties属性,FileProvider的xml配置请自行查询文档,并且authorties" +
                    "要以ImageSingleChooseActivity.SPECIAL_CUSTOM_URL_HEADER做开头字符串,后面可以自己拼接");
        }
        AcpOptions options = new AcpOptions.Builder()
                .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.CAMERA)
                .setDeniedMessage("选择图片权限被拒绝").build();
        Acp.getInstance(context)
                .request(options, new AcpListener() {
                    @Override
                    public void onGranted() {
                        if (callback != null) {
                            callback.onSuccess(null);
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        if (callback != null) {
                            callback.onFailed(permissions == null ? "" : permissions.toString());
                        }
                    }
                });
    }

    /**
     * 是否需要下载
     *
     * @param context
     * @param path        本地地址或者网络地址
     *                    是否需要下载
     * @param cropOptions
     * @param callback
     */
    public static void crop(final Context context, final String path,
                            final CropOptions cropOptions
            , final ImageCropCallback callback) {
        requestPermission(context, new Callback() {
            @Override
            public void onSuccess(Object o) {
                if (!assetPathValid(context, false, path)) {
                    return;
                }
                ImageChooseAndCropUtil.getInstance(context).cropPic(context, path, callback, false, cropOptions);
            }

            @Override
            public void onFailed(String message) {
//                if (callback != null) {
//                    callback.call(null);
//                }
            }
        });
    }


    /**
     * 是否需要下载
     *
     * @param context
     * @param path     本地地址或者网络地址
     *                 是否需要下载
     * @param callback
     */
    public static void crop(final Context context, final String path
            , final ImageCropCallback callback) {
        requestPermission(context, new Callback() {
            @Override
            public void onSuccess(Object o) {
                if (!assetPathValid(context, false, path)) {
                    return;
                }
                ImageChooseAndCropUtil.getInstance(context).cropPic(context, path, callback, false, null);
            }

            @Override
            public void onFailed(String message) {
//                if (callback != null) {
//                    callback.call(null);
//                }
            }
        });
    }


    /**
     * 验证路径是否合法
     *
     * @param context
     * @param isNeedDownload
     * @param path
     * @return
     */
    private static boolean assetPathValid(Context context, boolean isNeedDownload, String path) {
        boolean valid = false;
        if (!TextUtils.isEmpty(path)) {
            if (isNeedDownload) {
                if (path.startsWith("http:") || path.startsWith("https:")) {
                    valid = true;
                }
            } else {
                File file = new File(path);
                if (file != null && file.exists()) {
                    valid = true;
                }
            }
        }
        if (valid == false) {
            Toast.makeText(context, "路径不合法", Toast.LENGTH_LONG).show();
        }
        return valid;
    }

    /**
     * authorties是你自定义的FileProvider的子类的authorties属性,FileProvider的xml配置请自行查询文档,并且authorties
     * "要以ImageSingleChooseActivity.SPECIAL_CUSTOM_URL_HEADER做开头字符串,后面可以自己拼接
     *
     * @param context
     * @param authorites
     */
    public static void initProviderAuth(Context context, String authorites) {
        ImageChooseAndCropUtil.getInstance(context).setAuthorities(authorites);
    }

}
