package com.mobile.bcahlic.zhln.base.menudetail;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.mobile.bcahlic.zhln.base.BaseMenuDetailPage;

/**
 * Created by bcahlic on 16-7-5.
 */
public class InteractMenuDetailPager extends BaseMenuDetailPage {
    public InteractMenuDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        TextView text=new TextView(mactivity);
        text.setText("菜单详情页——互动");
        text.setTextColor(Color.RED);
        text.setTextSize(25);
        text.setGravity(Gravity.CENTER);
        return text;
    }
}
