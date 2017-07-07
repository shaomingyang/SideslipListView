package com.ming.shao.sidesliplistview;

import android.app.Application;
import android.util.DisplayMetrics;

/**
 * Created by smy on 2017/6/19 0019.
 */

public class ProjectApp extends Application {
    /**
     * 屏幕宽度
     */
    public static int screenWidth;

    /**
     * 屏幕高度
     */
    public static int screenHeight;
    public static int barHeight;
    private static ProjectApp instance;

    public static ProjectApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        calcScreenSize();
    }

    /**
     * 计算屏幕尺寸
     */
    private void calcScreenSize() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;


    }


}
