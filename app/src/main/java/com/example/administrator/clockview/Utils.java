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

    /**
     * 判断文件是否是相应格式
     *
     * @param path
     * @param formate
     * @return
     */
    public static boolean isFileFormate(String path, String formate) {
        if (null == path || -1 == path.lastIndexOf("/") || -1 == path.lastIndexOf("."))
            return false;
        if (path.substring(path.lastIndexOf(".") + 1) == null) {
            return false;
        }
        if (path.substring(path.lastIndexOf(".") + 1).equals(formate)) {
            return true;
        }
        return false;
    }
}
