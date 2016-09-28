package com.example.administrator.clockview;

import android.content.Context;

/**
 * Purpose     :
 * Description :
 * Author      : FLY
 * Date        : 2016.09.13 15:27
 */

public class Utils {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
