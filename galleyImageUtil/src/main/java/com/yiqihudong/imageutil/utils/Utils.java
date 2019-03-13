package com.yiqihudong.imageutil.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

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

    public static void saveImageToGallery(String fileName, Context context, Bitmap bmp){
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "问界互动家园");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(context, "图片保存失败",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(context, "图片保存失败",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            Toast.makeText(context, "图片保存失败",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        // 最后通知图库更新
        CGLog.d(Uri.fromFile(new File(file.getPath())) + "");
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(file.getPath()))));
//        new SingleMediaScanner(mContext, new File(file.getPath()), null);
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


}
