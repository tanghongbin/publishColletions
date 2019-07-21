package publish.tang.common.commonutils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by tanghongbin on 16/7/1.
 */
public class Utils {
    public static boolean stringIsNull(String str) {
        if (str == null || str.equals("") || str.length() == 0) return true;
        return false;
    }
    public static String isNull(String str) {
        if(stringIsNull(str))return "";
        return str;
    }


    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     *
     * @param file 要删除的目录或文件。
     */
    public static void deleteFolder(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                deleteFolder(f);
            }
            file.delete();
        }
    }


//    此方法得到一个bitmap对象，返回一个base64字符串


    /**===================================================照相选择=========================================*/


    /**
     * 照相或者选择图库的弹出框
     *Co
     * @param activity     选择照片的activity
     * @param mRootView popupwindow显示在此view的下方
     */

//    public static void showChoosePicPopup(final Activity activity, View mRootView) {
//        View view = View.inflate(activity, R.layout.choose_pic_popup, null);
//        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        setCommonPopupWindowStyle(popupWindow);
//        TextView take_pic = (TextView) view.findViewById(R.id.choose_pic_popup_take_pic);
//        final TextView gallery = (TextView) view.findViewById(R.id.choose_pic_popup_gallery);
//        RelativeLayout take_rl = (RelativeLayout) view.findViewById(R.id.choose_pic_popup_rl);
//        take_rl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.dismiss();
//            }
//        });
//        //拍照
//        take_pic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                camera(activity);
//                popupWindow.dismiss();
//            }
//        });
//        //从图库选择
//        gallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                gallery(activity);
//                popupWindow.dismiss();
//            }
//        });
//
//        popupWindow.showAtLocation(mRootView, Gravity.CENTER,0,0);
//    }

    /**
     * 设置弹出框的统一样式
     */
//    public static void setCommonPopupWindowStyle(PopupWindow mPopupWindow, int style) {
//        if (style != 0) {
//            mPopupWindow.setAnimationStyle(style);
//        } else {
//            mPopupWindow.setAnimationStyle(R.style.PopupWindowStyle);
//        }
//        mPopupWindow.setFocusable(true);
//        mPopupWindow.setTouchable(true);
//        mPopupWindow.setOutsideTouchable(true);
//        mPopupWindow.getContentView().setFocusableInTouchMode(true);
//        mPopupWindow.getContentView().setFocusable(true);
//        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
//        mPopupWindow.update();
//    }

//    /**
//     * 设置弹出框的统一样式
//     */
//    public static void setCommonPopupWindowStyle(PopupWindow mPopupWindow) {
//        setCommonPopupWindowStyle(mPopupWindow, 0);
//    }

    /**
     * 从相册获取
     */
    public static void gallery(Activity activity) {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
        activity.startActivityForResult(intent, Constant.PHOTO_REQUEST_GALLERY);
    }


    public static void carema(Activity activity){
        camera(activity,"");
    }

    /**
     * 拍照
     */
    public static void camera(Activity activity,String path) {
        // 激活相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        // 从文件中创建uri
        String url = Utils.stringIsNull(path) ? Constant.COMMON_PHOTO_FILE : path;
        Uri uri = Uri.parse(url);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        activity.startActivityForResult(intent, Constant.PHOTO_REQUEST_CAREMA);
    }


    public static void crop(Activity activity,Uri uri){
        crop(activity,uri,"");
    }
    /**
     * 从图库选择照片
     */

    public static void crop(Activity activity, Uri uri,String saveUrl) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 240);
        intent.putExtra("outputY", 240);
        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", false);
        String url = Utils.stringIsNull(saveUrl) ? Constant.COMMON_PHOTO_FILE : saveUrl;
        intent.putExtra(MediaStore.EXTRA_OUTPUT, url);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        activity.startActivityForResult(intent, Constant.PHOTO_REQUEST_CUT);
    }

}
