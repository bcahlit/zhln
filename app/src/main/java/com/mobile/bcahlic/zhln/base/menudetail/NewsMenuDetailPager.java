package com.mobile.bcahlic.zhln.base.menudetail;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.bcahlic.zhln.R;
import com.mobile.bcahlic.zhln.base.BaseMenuDetailPage;
import com.mobile.bcahlic.zhln.base.TableDetailPage;
import com.mobile.bcahlic.zhln.doman.NewsData;

import java.util.ArrayList;

/**
 * Created by bcahlic on 16-7-5.
 */
public class NewsMenuDetailPager extends BaseMenuDetailPage {

    private ViewPager menudetail;
    private ArrayList<NewsData.NewsTabData> myNewsTableDate;
    private ArrayList<TableDetailPage> mPageList;

    public NewsMenuDetailPager(Activity activity, ArrayList<NewsData.NewsTabData> children) {
        super(activity);
        myNewsTableDate=children;
    }

    @Override
    public View initView() {
        View view = View.inflate(mactivity, R.layout.news_menu_detail, null);
        menudetail = (ViewPager) view.findViewById(R.id.vp_menu_detail);
        return view;
    }

    @Override
    public void initdata() {
        mPageList=new ArrayList<TableDetailPage>();
        for(int i=0;i<myNewsTableDate.size();i++){
            TableDetailPage tableDetailPage = new TableDetailPage(mactivity,myNewsTableDate.get(i));
            mPageList.add(tableDetailPage);
        }
        menudetail.setAdapter(new mviewpageadapter());
    }

    class mviewpageadapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mPageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TableDetailPage tableDetailPage = mPageList.get(position);
            container.addView(tableDetailPage.rootview);
            tableDetailPage.initdata();
            return tableDetailPage.rootview;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
