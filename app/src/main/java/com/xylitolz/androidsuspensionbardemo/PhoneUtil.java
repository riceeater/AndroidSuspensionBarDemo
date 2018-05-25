package com.xylitolz.androidsuspensionbardemo;

import android.content.Context;

/**
 * @author 小米Xylitol
 * @email xiaomi987@hotmail.com
 * @desc 封装工具类
 * @date 2018-05-15 11:36
 */
public class PhoneUtil {

    // dip转像素
    public static int dipToPixels(final float dip,Context context) {
        final float SCALE = context.getResources().getDisplayMetrics().density;
        float valueDips = dip;
        int valuePixels = (int) (valueDips * SCALE + 0.5f);
        return valuePixels;
    }

    // 像素转dip
    public static float pixelsToDip(final int pixels,Context context) {
        final float SCALE = context.getResources().getDisplayMetrics().density;
        float dips = pixels / SCALE;
        return dips;
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(float spValue,Context context) {
        final float fontScale = context.getResources()
                .getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
