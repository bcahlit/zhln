package com.mobile.bcahlic.zhln.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mobile.bcahlic.zhln.activitys.MainActivity;
import com.mobile.bcahlic.zhln.base.BaseMenuDetailPage;
import com.mobile.bcahlic.zhln.base.BasePager;
import com.mobile.bcahlic.zhln.base.menudetail.InteractMenuDetailPager;
import com.mobile.bcahlic.zhln.base.menudetail.NewsMenuDetailPager;
import com.mobile.bcahlic.zhln.base.menudetail.PhotoMenuDetailPager;
import com.mobile.bcahlic.zhln.base.menudetail.TopicMenuDetailPager;
import com.mobile.bcahlic.zhln.doman.NewsData;
import com.mobile.bcahlic.zhln.fragment.LeftMenuFragment;
import com.mobile.bcahlic.zhln.global.GlobalContent;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;

import static com.mobile.bcahlic.zhln.utils.CacheUtils.readcache;
import static com.mobile.bcahlic.zhln.utils.CacheUtils.setcache;

/**
 * Created by bcahlic on 16-7-4.
 */
public class NewsPager extends BasePager {
    private ArrayList<BaseMenuDetailPage> mMenuDetail;
    private NewsData newsData;

    public NewsPager(Activity context) {
        super(context);
    }
    @Override
    public void initData() {
        button.setVisibility(View.VISIBLE);
        setSlidingMenuEnable(true);
        title.setText("新闻");
        TextView con=new TextView(mcontext);
        con.setText("加载。。");
        con.setTextColor(Color.RED);
        con.setTextSize(30);
        con.setGravity(Gravity.CENTER);
        flcontext.addView(con);
        GetDataFromServer();

    }
    private void GetDataFromServer(){
        RequestParams params =new RequestParams(GlobalContent.CATEGORIES_URL);
        Log.d("URL:",GlobalContent.CATEGORIES_URL);
        String readcache = readcache(GlobalContent.CATEGORIES_URL);
        if (!TextUtils.isEmpty(readcache)){parseData(readcache);}
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
   //             Log.d("Xutils",result);
                parseData(result);
                try {
                    setcache(GlobalContent.CATEGORIES_URL,result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {}
            @Override
            public void onCancelled(CancelledException cex) {}
            @Override
            public void onFinished() {}
        });
    }
    public void parseData(String result){
        Gson gson = new Gson();
        newsData = gson.fromJson(result, NewsData.class);
        Log.d("Gson",newsData.toString());
        MainActivity mact= (MainActivity) mcontext;
        LeftMenuFragment leftMenuFragment = mact.getLeftMenuFragment();
        leftMenuFragment.setMenuData(newsData);

        /**
         * 初始化四个界面
         */
        mMenuDetail=new ArrayList<BaseMenuDetailPage>();
        Log.d("NewsCenterPager1111111",newsData.data.get(0).children.toString());
        mMenuDetail.add(new NewsMenuDetailPager(mcontext,newsData.data.get(0).children));
        mMenuDetail.add(new TopicMenuDetailPager(mcontext));
        mMenuDetail.add(new PhotoMenuDetailPager(mcontext));
        mMenuDetail.add(new InteractMenuDetailPager(mcontext));
        setCurrentMenuDetailPager(0);
    }

    public void setCurrentMenuDetailPager(int i){
        Log.d("NewsPa",i+"");
        BaseMenuDetailPage page = mMenuDetail.get(i);
        flcontext.removeAllViews();
        flcontext.addView(page.rootview);
        title.setText(newsData.data.get(i).title);
        page.initdata();
    }

}
