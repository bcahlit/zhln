package com.mobile.bcahlic.zhln.base;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.mobile.bcahlic.zhln.doman.NewsData;

/**
 * Created by bcahlic on 16-7-6.
 */
public class TableDetailPage extends BaseMenuDetailPage {
    NewsData.NewsTabData mdata;
    private TextView text;

    public TableDetailPage(Activity activity, NewsData.NewsTabData newsTabData) {
        super(activity);
        mdata=newsTabData;
    }

    @Override
    public View initView() {
        text = new TextView(mactivity);
        text.setText("菜单详情页");
        text.setTextColor(Color.RED);
        text.setTextSize(25);
        text.setGravity(Gravity.CENTER);
        return text;
    }

    @Override
    public void initdata() {
        text.setText(mdata.title);
    }
}
