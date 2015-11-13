package com.liuzhuangzhuang.bbarrage;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by liuzhuang on 15/11/13.
 */
public class Utils {
    /**
     * 获得屏幕的宽度
     */
    public static int getWindowWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获得屏幕的高度
     */
    public static int getWindowHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
}
