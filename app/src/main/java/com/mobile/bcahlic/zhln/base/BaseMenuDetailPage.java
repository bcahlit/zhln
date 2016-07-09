package com.mobile.bcahlic.zhln.base;

import android.app.Activity;
import android.view.View;

/**
 * Created by bcahlic on 16-7-5.
 */
public abstract class BaseMenuDetailPage {
    public Activity mactivity;
    public View rootview;
    public BaseMenuDetailPage(Activity activity){
        mactivity=activity;
        rootview=initView();
    }
    public abstract View initView();
    public void initdata(){}
}
