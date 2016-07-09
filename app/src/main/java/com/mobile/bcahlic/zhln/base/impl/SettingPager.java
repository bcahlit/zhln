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
public class SettingPager extends BasePager {
    public SettingPager(Activity context) {
        super(context);
    }
    @Override
    public void initData() {
        button.setVisibility(View.INVISIBLE);
        setSlidingMenuEnable(false);
        title.setText("设置");
        TextView con=new TextView(mcontext);
        con.setText("设置");
        con.setTextColor(Color.RED);
        con.setTextSize(30);
        con.setGravity(Gravity.CENTER);
        flcontext.addView(con);
    }
}
