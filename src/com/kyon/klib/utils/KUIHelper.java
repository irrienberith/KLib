package com.kyon.klib.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import com.kyon.klib.stable.KConstants;


/**
 * Created by irrienberith on 14-10-29.
 */

public class KUIHelper {

    public static final int TWO_E_EIGHT = 256;
    public static final int TRIPLE_TWO_E_THREE = 24;
    public static final int TWO_E_FOUR = 16;
    public static final int TWO_E_THREE = 8;
    public static final int HEX_FF = 0xff;
    public static final int HEX_FFOOOO = 0xff0000;
    public static final int HEX_FFOO = 0xff00;
    public static final String TAG = "KUIHelper";


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 获取一个居中的rect的坐标
     *
     * @param width  宽度
     * @param height 高度
     * @return 四个点坐标
     */
    public static int[] getRectLocation(Context context, int width, int height) {
        Rect rect = new Rect();
        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int centerX = getScreenWidth(context) >> 1;
        int centerY = (getScreenHeight(context) + rect.top) >> 1;

        return new int[]{centerX - (width >> 1), centerY - (height >> 1), centerX + (width >> 1), centerY + (height >> 1)};

    }

    /**
     * 获取屏幕尺寸
     *
     * @param context 上下文
     * @return 返回尺寸
     */
    public static int getScreenWidth(Context context) {
        if (context == null) {
            return 0;
        }
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        if (context == null) {
            return 0;
        }
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获得圆角bitmap
     *
     * @param bitmap 原bitmap
     * @param rad    圆角半径
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int rad) {
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight()));
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.BLACK);
            canvas.drawRoundRect(rectF, rad, rad, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

            final Rect src = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());

            ColorMatrix cMatrix = new ColorMatrix();
            int brightness = KConstants.BLUR_BRIGHTNESS;
            cMatrix.set(new float[]{1, 0, 0, 0, brightness,
                    0, 1, 0, 0, brightness,// 调整亮度
                    0, 0, 1, 0, brightness,
                    0, 0, 0, 1, 0});
            paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));

            canvas.drawBitmap(bitmap, src, rect, paint);
            return output;
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return bitmap;
        }
    }

    /**
     * 获取一个view的bitmap
     *
     * @param v 目标View
     * @return 返回位图
     */
    public static Bitmap getBitmapFromView(View v) {
        if (v == null) {
            return null;
        }
        Bitmap screenShot;
        screenShot = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(screenShot);
        mCanvas.translate(-v.getScrollX(), -v.getScrollY());
        v.draw(mCanvas);
        return screenShot;
    }


    /**
     * 高斯模糊-图片区域
     *
     * @param bitmap    位图
     * @param startX    起始x
     * @param startY    起始y
     * @param endX      终点x
     * @param endY      终点y
     * @param cornerRad 圆角半径
     * @param blurRad   高斯模糊半径
     * @return 选中区域模糊后的drawable
     */
    public static Drawable generateBlurArea(Bitmap bitmap,
                                            int startX,
                                            int startY,
                                            int endX,
                                            int endY,
                                            int cornerRad,
                                            int blurRad) {
        Bitmap mBitmap = Bitmap.createBitmap(bitmap, startX, startY, Math.abs(endX - startX), Math.abs(endY - startY), null, false);
        return new BitmapDrawable(getRoundedCornerBitmap(getBlurBmp(mBitmap, blurRad), cornerRad));

    }

    public static Drawable generateBlurAreaCenter(Bitmap bitmap,
                                                  Context context,
                                                  int width,
                                                  int height,
                                                  int cornerRad,
                                                  int blurRad) {
        int[] location = getRectLocation(context, width, height);

        return generateBlurArea(bitmap, location[0], location[1], location[2], location[3], cornerRad, blurRad);

    }

    /**
     * 高斯模糊-背景
     *
     * @param bitmap  背景位图
     * @param blurRad 高斯模糊半径
     * @return 模糊后的drawable
     */
    public static Drawable generateBlurBg(Bitmap bitmap, int blurRad) {

        return new BitmapDrawable(getBlurBmp(bitmap, blurRad));

    }

    /**
     * 生成高斯模糊位图
     *
     * @param bitmap  原位图
     * @param blurRad 模糊半径
     * @return 模糊后位图
     */
    public static Bitmap getBlurBmp(Bitmap bitmap, int blurRad) {

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] inPixels = new int[width * height];
        int[] outPixels = new int[width * height];
        Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.getPixels(inPixels, 0, width, 0, 0, width, height);
        int iterNum = 5;
        for (int i = 0; i < iterNum; i++) {
            blur(inPixels, outPixels, width, height, blurRad);
            blur(outPixels, inPixels, height, width, blurRad);
        }
        blurFractional(inPixels, outPixels, width, height, blurRad);
        blurFractional(outPixels, inPixels, height, width, blurRad);
        newBitmap.setPixels(inPixels, 0, width, 0, 0, width, height);
        return newBitmap;

    }

    public static void blur(int[] in, int[] out, int width, int height,
                            float radius) {
        int widthMinus1 = width - 1;
        int r = (int) radius;
        int tableSize = (r << 1) + 1;
        int divide[] = new int[TWO_E_EIGHT * tableSize];

        for (int i = 0; i < TWO_E_EIGHT * tableSize; i++) {
            divide[i] = i / tableSize;
        }
        int inIndex = 0;

        for (int y = 0; y < height; y++) {
            int outIndex = y;
            int ta = 0, tr = 0, tg = 0, tb = 0;

            for (int i = -r; i <= r; i++) {
                int rgb = in[inIndex + clamp(i, 0, width - 1)];
                ta += (rgb >> TRIPLE_TWO_E_THREE) & HEX_FF;
                tr += (rgb >> TWO_E_FOUR) & HEX_FF;
                tg += (rgb >> TWO_E_THREE) & HEX_FF;
                tb += rgb & HEX_FF;
            }

            for (int x = 0; x < width; x++) {
                out[outIndex] = (divide[ta] << TRIPLE_TWO_E_THREE) | (divide[tr] << TWO_E_FOUR)
                        | (divide[tg] << TWO_E_THREE) | divide[tb];

                int i1 = x + r + 1;
                if (i1 > widthMinus1) {
                    i1 = widthMinus1;
                }
                int i2 = x - r;
                if (i2 < 0) {
                    i2 = 0;
                }
                int rgb1 = in[inIndex + i1];
                int rgb2 = in[inIndex + i2];

                ta += ((rgb1 >> TRIPLE_TWO_E_THREE) & HEX_FF) - ((rgb2 >> TRIPLE_TWO_E_THREE) & HEX_FF);
                tr += ((rgb1 & HEX_FFOOOO) - (rgb2 & HEX_FFOOOO)) >> TWO_E_FOUR;
                tg += ((rgb1 & HEX_FFOO) - (rgb2 & HEX_FFOO)) >> TWO_E_THREE;
                tb += (rgb1 & HEX_FF) - (rgb2 & HEX_FF);
                outIndex += height;
            }
            inIndex += width;
        }
    }

    public static void blurFractional(int[] in, int[] out, int width,
                                      int height, float radius) {
        radius -= (int) radius;
        float f = 1.0f / (1 + 2 * radius);
        int inIndex = 0;

        for (int y = 0; y < height; y++) {
            int outIndex = y;

            out[outIndex] = in[0];
            outIndex += height;
            for (int x = 1; x < width - 1; x++) {
                int i = inIndex + x;
                int rgb1 = in[i - 1];
                int rgb2 = in[i];
                int rgb3 = in[i + 1];

                int a1 = (rgb1 >> TRIPLE_TWO_E_THREE) & HEX_FF;
                int r1 = (rgb1 >> TWO_E_FOUR) & HEX_FF;
                int g1 = (rgb1 >> TWO_E_THREE) & HEX_FF;
                int b1 = rgb1 & HEX_FF;
                int a2 = (rgb2 >> TRIPLE_TWO_E_THREE) & HEX_FF;
                int r2 = (rgb2 >> TWO_E_FOUR) & HEX_FF;
                int g2 = (rgb2 >> TWO_E_THREE) & HEX_FF;
                int b2 = rgb2 & HEX_FF;
                int a3 = (rgb3 >> TRIPLE_TWO_E_THREE) & HEX_FF;
                int r3 = (rgb3 >> TWO_E_FOUR) & HEX_FF;
                int g3 = (rgb3 >> TWO_E_THREE) & HEX_FF;
                int b3 = rgb3 & HEX_FF;
                a1 = a2 + (int) ((a1 + a3) * radius);
                r1 = r2 + (int) ((r1 + r3) * radius);
                g1 = g2 + (int) ((g1 + g3) * radius);
                b1 = b2 + (int) ((b1 + b3) * radius);
                a1 *= f;
                r1 *= f;
                g1 *= f;
                b1 *= f;
                out[outIndex] = (a1 << TRIPLE_TWO_E_THREE) | (r1 << TWO_E_FOUR) | (g1 << TWO_E_THREE) | b1;
                outIndex += height;
            }
            out[outIndex] = in[width - 1];
            inIndex += width;
        }
    }

    public static int clamp(int x, int a, int b) {
        return (x < a) ? a : (x > b) ? b : x;
    }


    /**
     * 去除字符串前后双引号
     * @param origin 原字符串
     * @return       结果字符串
     */
    public static String trimQuote(String origin) {
        String afterConvert = origin;
        if (origin.startsWith("\"") && origin.endsWith("\"")) {
            afterConvert = afterConvert.substring(1, afterConvert.length() - 1);
        }
        return afterConvert;
    }

    /**
     * 去除字符串开头的换行符
     * @param origin  原字符串
     * @return
     */
    public static String trimNewLine(String origin) {
        if (KStringUtils.isBlank(origin) || !origin.startsWith("\n")) {
            return origin;
        }
        return origin.substring(1);
    }

    /**
     * 换行字符串
     *
     * @param target 目标字符串
     * @param part1  前半部分
     * @param part2  后半部分
     * @return 返回值
     */
    public static String makeWrapStr(String target, String part1, String part2) {
        target += "\n" + part1 + ":" + part2;
        return target;
    }
}
