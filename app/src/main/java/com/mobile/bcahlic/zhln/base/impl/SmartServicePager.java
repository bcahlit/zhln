package com.mobile.bcahlic.zhln.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.mobile.bcahlic.zhln.base.BasePager;

/**
 * Created by bcahlic on 16-7-4.
 */
public class SmartServicePager extends BasePager {
    public SmartServicePager(Activity context) {
        super(context);
    }
    @Override
    public void initData() {
        button.setVisibility(View.VISIBLE);
        setSlidingMenuEnable(true);
        title.setText("智慧服务");
        TextView con=new TextView(mcontext);
        con.setText("生活");
        con.setTextColor(Color.RED);
        con.setTextSize(30);
        con.setGravity(Gravity.CENTER);
        flcontext.addView(con);
    }
}
