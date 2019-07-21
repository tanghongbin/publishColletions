package publish.tang.common.commonutils;

import android.os.Environment;

import java.io.File;

/**
 * Created by tanghongbin on 16/7/6.
 */
public class Constant {
    public static final String ALREADY_SELECT_KEY = "already_select_key";
    public static final int IMAGE_MAX = 9;
    public static final int PHOTO_REQUEST_GALLERY = 0x5000;//选择系统的图片
    public static final int PHOTO_REQUEST_CAREMA = 0x5001;
    public static final int PHOTO_REQUEST_CUT = 0x5002;

    public static String COMMON_PHOTO_FILE = Environment.getExternalStorageDirectory()+
            File.separator+"template.png";
}
