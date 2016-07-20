package com.mobile.bcahlic.zhln.base.menudetail;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.mobile.bcahlic.zhln.R;
import com.mobile.bcahlic.zhln.activitys.MainActivity;
import com.mobile.bcahlic.zhln.base.BaseMenuDetailPage;
import com.mobile.bcahlic.zhln.base.TableDetailPage;
import com.mobile.bcahlic.zhln.doman.NewsData;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

/**
 * Created by bcahlic on 16-7-5.
 */
public class NewsMenuDetailPager extends BaseMenuDetailPage {

    private ViewPager menudetail;
    private ArrayList<NewsData.NewsTabData> myNewsTableDate;
    private ArrayList<TableDetailPage> mPageList;
    private TabPageIndicator indicator;

    public NewsMenuDetailPager(Activity activity, ArrayList<NewsData.NewsTabData> children) {
        super(activity);
        myNewsTableDate=children;
    }

    @Override
    public View initView() {
        View view = View.inflate(mactivity, R.layout.news_menu_detail, null);
        menudetail = (ViewPager) view.findViewById(R.id.vp_menu_detail);
        indicator = (TabPageIndicator)view.findViewById(R.id.indicator);
        ImageButton btnNext = (ImageButton) view.findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int item = menudetail.getCurrentItem();
                menudetail.setCurrentItem(++item);
            }
        });
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MainActivity manact= (MainActivity) mactivity;
                SlidingMenu slidingMenu = manact.getSlidingMenu();
                if (position==0){
                    slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                }else slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
        indicator.setViewPager(menudetail);
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
        public CharSequence getPageTitle(int position) {
            return myNewsTableDate.get(position).title;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
