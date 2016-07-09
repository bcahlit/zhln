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
public class HomePager extends BasePager {
    public HomePager(Activity context) {
        super(context);
    }
    @Override
    public void initData() {
        button.setVisibility(View.INVISIBLE);
        setSlidingMenuEnable(false);
        title.setText("智慧北京");

        TextView text = new TextView(mcontext);
        text.setText("首页");
        text.setTextColor(Color.RED);
        text.setTextSize(25);
        text.setGravity(Gravity.CENTER);
        // 向FrameLayout中动态添加布局
        flcontext.addView(text);
    }
}
