package com.yiqihudong.imageutil.view;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;

import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.module.ManifestParser;
import com.yalantis.ucrop.UCrop;
import com.yiqihudong.imageutil.*;
import com.yiqihudong.myutils.BuildConfig;
import com.yiqihudong.myutils.R;


import java.io.File;
import java.io.IOException;
import java.util.Date;

public class ImageSingleChooseActivity extends Activity {

    private static final int REQUEST_CAMERA = 0x1000;
    private static final int REQUEST_CROP = 0x1001;
    private static final int REQUEST_GALLERY = 0x1002;
    public static final String SPECIAL_CUSTOM_URL_HEADER = "com.publishgallery.custom.auth.";
    private TextView gallery;
    private TextView take_pic;
    private TextView cancle;
    static File fileName;
    private Uri mUri;
    /**
     * 临时照片，裁剪使用的路径
     */
    private String mTemporaryPath;
    private ProgressDialog progressDialog;
    protected CropOptions cropOptions;
    protected boolean isNeedDownload;//是否需要下载
    private int mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fileName = initFile();
        initUri();
        openStrictMode();
        cropOptions = ImageChooseAndCropUtil.getInstance(this).getCropOptions();
        if (cropOptions == null) {
            cropOptions = createDefaultOptions();
        }
        isNeedDownload = getIntent().getBooleanExtra(ImageChooseAndCropUtil.NEED_DOWNLOAD, false);

        setContentView(R.layout.image_single_choose);
        if (isNeedSetContent()) {
            findViewById(R.id.choose_view).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.choose_view).setVisibility(View.GONE);
        }
        initView();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

    }

    private File initFile() {

        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "image_select");

        if (!file.exists()) {
            file.mkdirs();
        }
        File fileName = new File(file, "template_" + getTime() + ".png");
        if (!fileName.exists()) {
            try {
                fileName.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    private void openStrictMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }

    private CropOptions createDefaultOptions() {
        CropOptions options = new CropOptions();
        options.setAspectX(1);
        options.setAspectY(1);
        options.setHideBottomControls(true);
        options.setCircleDimmedLayer(false);
        options.setShowCropGrid(true);
        options.setShowCropFrame(true);
        options.setAllowedGestures(1, 1, 1);
        return options;
    }

    private void showDialog(String text) {
        progressDialog.show();
        progressDialog.setMessage(text);
    }

    protected void cancleDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * 因图片框架库如果地址相同则不会重新加载，所以现在每次的地址都设置为
     * 动态生成，并且每次进入该库都会删除掉缓存
     */
    private void initUri() {
//        deleteFilesInDir(file);
        mUri = createFileIfNotExists();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.) {
//
//        }
    }


    private void initView() {
        gallery = (TextView) findViewById(R.id.gallery_choose);
        take_pic = (TextView) findViewById(R.id.take_pic);
        cancle = (TextView) findViewById(R.id.cancle);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.take_pic) {
                    camera(ImageSingleChooseActivity.this, mUri);
                } else if (view.getId() == R.id.gallery_choose) {
                    gallery(ImageSingleChooseActivity.this);
                } else if (view.getId() == R.id.cancle) {
                    destroy();
                }
            }
        };
        gallery.setOnClickListener(listener);
        take_pic.setOnClickListener(listener);
        cancle.setOnClickListener(listener);
    }

    /**
     * 判断是否需要加载视图
     *
     * @return
     */
    private boolean isNeedSetContent() {
        mType = getIntent().getIntExtra(ImageChooseAndCropUtil.CHOOSE_TYPE, -1);
        if (mType == -1) {
            return true;
        }
        if (mType == ImageChooseAndCropUtil.TYPE_CAMERA ||
                mType == ImageChooseAndCropUtil.TYPE_CAMERA_CROP) {
            camera(this, mUri);
        } else if (mType == ImageChooseAndCropUtil.TYPE_SELECTED_PIC_FROM_GALLERY ||
                mType == ImageChooseAndCropUtil.TYPE_SELECTED_PIC_FROM_GALLERY_CROP) {
            gallery(this);
        } else if (mType == ImageChooseAndCropUtil.TYPE_CROP) {
            isNeedDownload();
        }

        return false;
    }

    private void isNeedDownload() {
//        if (isNeedDownload) {
//            downLoad();
//        } else {
        String path = getIntent().getStringExtra(ImageChooseAndCropUtil.IMAGE_PATH);
        File file = new File(path);
        goToCrop(file);
//        }

    }

    private void downLoad() {
//        String path = getIntent().getStringExtra(ImageChooseAndCropUtil.IMAGE_PATH);
//        DrawableTypeRequest<String> glide = Glide.with(this).load(path);
//        progressDialog.setMessage("图片下载中");
//        glide.listener(new RequestListener<String, GlideDrawable>() {
//            @Override
//            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                progressDialog.dismiss();
//                resolveCropResult();
//                return false;
//            }
//
//            @Override
//            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                GlideBitmapDrawable bitmapDrawable = (GlideBitmapDrawable) resource;
//                bitmapDrawable.get
//                return false;
//            }
//
//        });
//        glide.downloadOnly(WindowUtil.getDisplayMetrics(this)
//                .widthPixels, WindowUtil.getDisplayMetrics(this).heightPixels);

    }

    private void goToCrop(File file) {
        Uri uri = Uri.fromFile(file);
        crop(this, mType, uri, mUri, cropOptions);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }


    /**
     * 图片旋转耗时操作
     */
    AsyncTask<String, Void, Void> amendRotate = new AsyncTask<String, Void, Void>() {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog("正在保存图片");

        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                long sys = System.currentTimeMillis();
                PhotoBitmapUtils.amendRotatePhoto(params[0], params[0], ImageSingleChooseActivity.this);
                sys = sys - System.currentTimeMillis();
                Log.i("TAg", "耗时操作" + sys + "秒");
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("TAg", "图片转换出错");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            cancleDialog();
            crop(ImageSingleChooseActivity.this, mType, mUri, mUri, cropOptions);

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int type = getIntent().getIntExtra(ImageChooseAndCropUtil.TYPE, -1);
        if (type == -1) {
            return;
        }
        try {
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case REQUEST_CAMERA://照相返回结果
                        resolveCameraResult(type);
                        break;
                    case REQUEST_CROP:
                        resolveCropResult();
                        break;
                    case REQUEST_GALLERY:
                        resolveGalleryResult(data, type);
                        break;
                    case UCrop.REQUEST_CROP:
                        ImageChooseAndCropUtil.getInstance(this).setCropOptions(null);
                        resolveCropResult();
                        break;
                }
            } else {
                destroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG", "图片处理单选出错");
            cancleDialog();
        }
    }

    private void resolveGalleryResult(Intent data, int type) {
        if (type == ImageChooseAndCropUtil.TYPE_CHOOSE_AND_CROP) {//启动裁剪
            crop(ImageSingleChooseActivity.this, mType, data.getData(), mUri, cropOptions);
        } else {
            try {
                ImageChooseAndCropUtil.getInstance(ImageSingleChooseActivity.this).getCropCallback().
                        call(getPathFromUri(data.getData()));
            } finally {
                destroy();
            }

        }
    }

    private void resolveCropResult() {
        try {
            String path = getLocalPathFromUri(mUri);
            ImageChooseAndCropUtil.getInstance(ImageSingleChooseActivity.this).getCropCallback().
                    call(path);
        } finally {
            destroy();
        }
    }

    /**
     * 判断一下如果authortiy是 com.yiqihudong.myutils.uri
     * 那么手动替换掉返回的path，因为getPathFromUri会从系统图库查找，但是并没有保存到图库中，
     * 中间绕过这个过程
     *
     * @param uri
     * @return
     */
    private String getLocalPathFromUri(Uri uri) {
        String path = fileName.getPath();
        return path;
    }

    private void resolveCameraResult(int type) {
        if (type == ImageChooseAndCropUtil.TYPE_CHOOSE_AND_CROP) {//启动裁剪
            amendRotate.execute(fileName.getPath());
        } else {
            try {
                Log.i("TAG", "authorty:" + mUri.getAuthority() + "    打印地址:" + mUri.getPath());
                String path = getLocalPathFromUri(mUri);

                ImageChooseAndCropUtil.getInstance(ImageSingleChooseActivity.this).getCropCallback().
                        call(path);
            } finally {
                destroy();
            }
        }
    }

    private String getPathFromUri(Uri uri) {
        return ConvertUri.getRealPathFromURIKK(ImageSingleChooseActivity.this, uri);
    }

    /**
     * 拍照
     */
    public static void camera(final Activity activity, Uri uri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        // 从文件中创建uri
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        activity.startActivityForResult(intent, REQUEST_CAMERA);

    }

    private Uri createFileIfNotExists() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return FileProvider.getUriForFile(getApplicationContext(), getAuth(), fileName);
            } else {
                mTemporaryPath = fileName.getCanonicalPath();
                Log.i("TAG", "打印路径getCanonicalPath:" + fileName.getCanonicalPath());
                Uri localUri = Uri.fromFile(fileName);
                return localUri;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getAuth() {
        return ImageChooseAndCropUtil.getInstance(this).getAuthorities();
    }

    /**
     * 从图库选择照片
     */

    public static void crop(Activity activity, int mType, Uri data, Uri saveUri, CropOptions options) {
        if (assetIsGallery(data)) {
            saveUri = Uri.fromFile(fileName);
        } else {
            data = Uri.fromFile(fileName);
            saveUri = data;
        }
        UCrop.of(data, saveUri)
                .withOptions(options)
                .withAspectRatio(options.getAspectX(), options.getAspectY())
                .start(activity);

        // 裁剪图片意图
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(data, "image/*");
//        intent.putExtra("crop", "true");
//        // 裁剪框的比例，1：1
//
//        if (options != null) {
//            if (options.getOutputX() > 0 && options.getOutputY() > 0) {
//                intent.putExtra("outputX", options.getOutputX());
//                intent.putExtra("outputY", options.getOutputY());
//            }
//
//            if (options.getAspectX() > 0 && options.getAspectY() > 0) {
//                intent.putExtra("aspectX", options.getAspectX());
//                intent.putExtra("aspectY", options.getAspectY());
//            }
//
//            intent.putExtra("scale", options.isScale());
//        }
//        // 裁剪后输出图片的尺寸大小
////        intent.putExtra("outputX", 240);
////        intent.putExtra("outputY", 240);
//        intent.putExtra("outputFormat", "JPEG");// 图片格式
//        intent.putExtra("noFaceDetection", true);// 取消人脸识别
//        intent.putExtra("return-data", false);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, saveUri);
//        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
//        activity.startActivityForResult(intent, REQUEST_CROP);
    }

    private static boolean assetIsGallery(Uri uri) {
        if (uri != null && assetSchme(uri) &&
                assetPhoneAuth(uri)) {
            return true;
        }
        return false;
    }

    private static boolean assetSchme(Uri uri) {
        String schme = uri.getScheme();
        return schme.equals("content") || schme.equals("file");
    }

    /**
     * 如果是以com.publishgallery.custom.auth这个字符串开头，那么表示不是图库的照片
     * @param uri
     * @return
     */
    private static boolean assetPhoneAuth(Uri uri) {
        String authorties = uri.getAuthority();
        return !authorties.startsWith(SPECIAL_CUSTOM_URL_HEADER);
    }

    /**
     * 从相册获取
     */
    public static void gallery(final Activity activity) {

        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
        activity.startActivityForResult(intent, REQUEST_GALLERY);
    }

    public void destroy() {
        finish();
    }

    private static long getTime() {
        return new Date().getTime();
    }

    /**
     * 删除目录下的所有文件
     *
     * @param dir 目录
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    private static boolean deleteFilesInDir(File dir) {
        if (dir == null) return false;
        // 目录不存在返回true
        if (!dir.exists()) return true;
        // 不是目录返回false
        if (!dir.isDirectory()) return false;
        // 现在文件存在且是文件夹
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!deleteFile(file)) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return true;
    }

    /**
     * 删除文件
     *
     * @param file 文件
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    private static boolean deleteFile(File file) {
        return file != null && (!file.exists() || file.isFile() && file.delete());
    }

    /**
     * 删除目录
     *
     * @param dir 目录
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    private static boolean deleteDir(File dir) {
        if (dir == null) return false;
        // 目录不存在返回true
        if (!dir.exists()) return true;
        // 不是目录返回false
        if (!dir.isDirectory()) return false;
        // 现在文件存在且是文件夹
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!deleteFile(file)) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return dir.delete();
    }
}
