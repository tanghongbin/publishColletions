package publish.tang.common.commonutils;

/**
 * Created by tanghongbin on 16/4/14.
 */

//本博客为 http://www.imooc.com/video/6480 的笔记
//
//        1 可以通过 ColorMatrix 类 ,操作底层维护的5*4的float颜色变换矩阵，
//
//        来改变图片的三基色,透明度等来操作图片.
//
//        2 通过获取图片上所有像素点的信息,通过科学公式的变换
//
//        来操作像素点来达到操作图片的效果
//
////图像处理的工具类
//
//        [code]

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;


/**
 * 图像处理的帮助类
 *
 * */

public class ImageHelper {

    /**
     * 底部起始是操作了float类型的数组
     * @param bm
     * @param hue
     * @param saturation
     * @param lum
     * @return
     */
    public static Bitmap ImageEffect(Bitmap bm, float hue, float saturation, float lum){

        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(),
                bm.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        ColorMatrix hueMatrix = new ColorMatrix();
        hueMatrix.setRotate(0, hue);
        hueMatrix.setRotate(1, hue);
        hueMatrix.setRotate(2, hue);

        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(saturation);

        ColorMatrix lumMatrix = new ColorMatrix();
        lumMatrix.setScale(lum, lum, lum, 1);

        ColorMatrix imageMatrix = new ColorMatrix();
        imageMatrix.postConcat(hueMatrix);
        imageMatrix.postConcat(saturationMatrix);
        imageMatrix.postConcat(lumMatrix);

        paint.setColorFilter(new ColorMatrixColorFilter(imageMatrix));
        canvas.drawBitmap(bm, 0, 0, paint);

        return bmp;
    }

    /**
     *
     * @param bitmap 原图片
     * @param data  长度为20 的数组 数组中的值为0或者1
     * @return  处理后的图片
     */
    public static Bitmap ImageEffect(Bitmap bitmap ,float[] data){

        if(data==null)
            return null;

        if(data.length!=20)
            return null;

        //在内存中的图片
        Bitmap bm=Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(),
                Bitmap.Config.ARGB_8888);
        ColorMatrix matrix=new ColorMatrix(data);
        Canvas mCanvas=new Canvas(bm);
        Paint mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

        mPaint.setColorFilter(new ColorMatrixColorFilter(matrix));
        mCanvas.drawBitmap(bm, 0, 0,mPaint);
        return bm;
    }

    public static Bitmap handleImageNegative(Bitmap bm){

        int width = bm.getWidth();
        int height = bm.getHeight();
        int color;
        int r, g, b, a;

        Bitmap bmp = Bitmap.createBitmap(width, height
                , Bitmap.Config.ARGB_8888);

        int[] oldPx = new int[width * height];
        int[] newPx = new int[width * height];
        bm.getPixels(oldPx, 0, width, 0, 0, width, height);

        for (int i = 0; i < width * height; i++) {
            color = oldPx[i];
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);
            a = Color.alpha(color);

            //底片效果的转换公式
            r = 255 - r;
            g = 255 - g;
            b = 255 - b;

            if (r > 255) {
                r = 255;
            } else if (r < 0) {
                r = 0;
            }
            if (g > 255) {
                g = 255;
            } else if (g < 0) {
                g = 0;
            }
            if (b > 255) {
                b = 255;
            } else if (b < 0) {
                b = 0;
            }
            newPx[i] = Color.argb(a, r, g, b);
        }

        bmp.setPixels(newPx, 0, width, 0, 0, width, height);
        return bmp;
    }

    /**
     * 老照片的处理效果
     * @param bm
     * @return
     */
    public static Bitmap handleImagePixelsOldPhoto(Bitmap bm) {
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(),
                Bitmap.Config.ARGB_8888);
        int width = bm.getWidth();
        int height = bm.getHeight();
        int color = 0;
        int r, g, b, a, r1, g1, b1;

        int[] oldPx = new int[width * height];
        int[] newPx = new int[width * height];

        bm.getPixels(oldPx, 0, bm.getWidth(), 0, 0, width, height);
        for (int i = 0; i < width * height; i++) {
            color = oldPx[i];
            a = Color.alpha(color);
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);

            //老照片处理效果的公式
            r1 = (int) (0.393 * r + 0.769 * g + 0.189 * b);
            g1 = (int) (0.349 * r + 0.686 * g + 0.168 * b);
            b1 = (int) (0.272 * r + 0.534 * g + 0.131 * b);

            if (r1 > 255) {
                r1 = 255;
            }
            if (g1 > 255) {
                g1 = 255;
            }
            if (b1 > 255) {
                b1 = 255;
            }

            newPx[i] = Color.argb(a, r1, g1, b1);
        }
        bmp.setPixels(newPx, 0, width, 0, 0, width, height);
        return bmp;
    }

    /**
     * 浮雕效果
     */
    public static Bitmap handleImagePixelsRelief(Bitmap bm) {
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(),
                Bitmap.Config.ARGB_8888);
        int width = bm.getWidth();
        int height = bm.getHeight();
        int color = 0, colorBefore = 0;
        int a, r, g, b;
        int r1, g1, b1;

        int[] oldPx = new int[width * height];
        int[] newPx = new int[width * height];

        bm.getPixels(oldPx, 0, bm.getWidth(), 0, 0, width, height);
        for (int i = 1; i < width * height; i++) {

            //像素点的算法
            colorBefore = oldPx[i - 1];
            a = Color.alpha(colorBefore);
            r = Color.red(colorBefore);
            g = Color.green(colorBefore);
            b = Color.blue(colorBefore);

            color = oldPx[i];
            r1 = Color.red(color);
            g1 = Color.green(color);
            b1 = Color.blue(color);

            r = (r - r1 + 127);
            g = (g - g1 + 127);
            b = (b - b1 + 127);
            if (r > 255) {
                r = 255;
            }
            if (g > 255) {
                g = 255;
            }
            if (b > 255) {
                b = 255;
            }
            newPx[i] = Color.argb(a, r, g, b);
        }
        bmp.setPixels(newPx, 0, width, 0, 0, width, height);
        return bmp;
    }


//    /**
//     * 将彩色图转换为纯黑白二色
//     *
//     * @param 位图
//     * @return 返回转换好的位图
//     */
//    public static Bitmap convertToBlackWhite(Bitmap bmp) {
//        int width = bmp.getWidth(); // 获取位图的宽
//        int height = bmp.getHeight(); // 获取位图的高
//        int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组
//
//        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
//        int alpha = 0xFF << 24;
//        for (int i = 0; i < height; i++) {
//            for (int j = 0; j < width; j++) {
//                int grey = pixels[width * i + j];
//
//                //分离三原色
//                int red = ((grey & 0x00FF0000) >> 16);
//                int green = ((grey & 0x0000FF00) >> 8);
//                int blue = (grey & 0x000000FF);
//
//                //转化成灰度像素
//                grey = (int) (red * 0.3 + green * 0.59 + blue * 0.11);
//                grey = alpha | (grey << 16) | (grey << 8) | grey;
//                pixels[width * i + j] = grey;
//            }
//        }
//        //新建图片
//        Bitmap newBmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
//        //设置图片数据
//        newBmp.setPixels(pixels, 0, width, 0, 0, width, height);
//
////        Bitmap resizeBmp = ThumbnailUtils.extractThumbnail(newBmp, 380, 460);
//        return newBmp;
//    }

    //图片旋转90度
   public static  Bitmap
    adjustPhotoRotation(Bitmap bm, final int orientationDegree)
    {

        Matrix m = new Matrix();
        m.setRotate(orientationDegree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        float targetX, targetY;
        if (orientationDegree == 90) {
            targetX = bm.getHeight();
            targetY = 0;
        } else {
            targetX = bm.getHeight();
            targetY = bm.getWidth();
        }

        final float[] values = new float[9];
        m.getValues(values);

        float x1 = values[Matrix.MTRANS_X];
        float y1 = values[Matrix.MTRANS_Y];

        m.postTranslate(targetX - x1, targetY - y1);

        Bitmap bm1 = Bitmap.createBitmap(bm.getHeight(), bm.getWidth(), Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(bm1);
        canvas.drawBitmap(bm, m, paint);

        return bm1;
    }
//    public static Bitmap getPreviousBitmap(Bitmap b){
//        Bitmap dst = Bitmap.createBitmap(b.getWidth(),b.getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(dst);
//        ColorMatrix cm = new ColorMatrix();
//        //设定图像为灰色，通过查资料 R 0.3 G0.59 B 0.11
//        cm.set(new float[] {
//                0.3f, 0.59f, 0.11f, 0, 0,
//                0.3f, 0.59f, 0.11f, 0, 0,
//                0.3f, 0.59f, 0.11f, 0, 0,
//                0, 0, 0, 1, 0 });
//
////黑白
////那么图像就变成了灰色
////        cm.set(new float[] {
////                0.3086f,0.6094f, 0.0820f, 0, 0,
////                0.3086f,0.6094f,0.0820f, 0, 0,
////                0.3086f,0.6094f,0.0820f, 0, 0,
////                0, 0, 0, 1, 0});
//
//        Paint paint = new Paint();
//        paint.setColorFilter(new ColorMatrixColorFilter(cm));
//        canvas.drawBitmap(b, 0, 0, paint);
//        // 保存图像
//        canvas.save(Canvas.ALL_SAVE_FLAG);
//        // 存储
//        canvas.restore();
//        return dst;
//    }
//    //如果点的颜色是背景干扰色，则变为白色
    public static Bitmap backgroundToWhite(Bitmap bitmap)
    {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        int width= bitmap.getWidth();
        int height= bitmap.getHeight();
        for (int x = 0; x <width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                //去掉边框
                if (x == 0 || y == 0 || x == width - 1 || y == height - 1)
                {
                    newBitmap.setPixel(x, y, Color.WHITE);
                }
                else
                {

                    int color = bitmap.getPixel(x, y);

                    //如果点的颜色是背景干扰色，则变为白色
                    if (color==( Color.rgb(204, 204, 51)) ||
                            color==( Color.rgb(153, 204, 51)) ||
                            color==( Color.rgb(204, 255, 102)) ||
                            color== Color.rgb(204, 204, 204) ||
                            color==( Color.rgb(204, 255, 51)))
                    {
                        newBitmap.setPixel(x, y,Color.WHITE);
                    }
                    else
                    {
                        newBitmap.setPixel(x, y, color);
                    }
                }
            }
        }
        return newBitmap;
    }
}