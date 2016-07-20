package com.mobile.bcahlic.zhln;

import android.app.Application;

import org.xutils.x;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by bcahlic on 16-7-4.
 */
public class LYJApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);//Xutils初始化
        ShareSDK.initSDK(this,"152999b89ad10");//Share sdk 初始化。
    }
}