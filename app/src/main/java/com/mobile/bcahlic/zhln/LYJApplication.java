package com.mobile.bcahlic.zhln;

import android.app.Application;

import org.xutils.x;

/**
 * Created by bcahlic on 16-7-4.
 */
public class LYJApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);//Xutils初始化
    }
}