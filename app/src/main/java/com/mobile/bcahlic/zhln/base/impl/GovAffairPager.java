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
public class GovAffairPager extends BasePager {
    public GovAffairPager(Activity context) {
        super(context);
    }
    @Override
    public void initData() {
        button.setVisibility(View.VISIBLE);
        setSlidingMenuEnable(true);
        title.setText("人口管理");
        TextView con=new TextView(mcontext);
        con.setText("政务");
        con.setTextColor(Color.RED);
        con.setTextSize(30);
        con.setGravity(Gravity.CENTER);
        flcontext.addView(con);
    }
}
