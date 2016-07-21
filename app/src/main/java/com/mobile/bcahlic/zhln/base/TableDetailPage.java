package com.mobile.bcahlic.zhln.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mobile.bcahlic.zhln.R;
import com.mobile.bcahlic.zhln.doman.NewsData;
import com.mobile.bcahlic.zhln.doman.TabData;
import com.mobile.bcahlic.zhln.global.GlobalContent;
import com.mobile.bcahlic.zhln.utils.Sputils;
import com.mobile.bcahlic.zhln.view.RefreshLIstview;
import com.mobile.bcahlic.zhln.view.webView;
import com.viewpagerindicator.CirclePageIndicator;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;

import static com.mobile.bcahlic.zhln.utils.CacheUtils.readcache;
import static com.mobile.bcahlic.zhln.utils.CacheUtils.setcache;

/**
 * Created by bcahlic on 16-7-6.
 * 这是slidingmenu点击后进入的画面界面。
 */
public class TableDetailPage extends BaseMenuDetailPage {
    NewsData.NewsTabData mdata;
    private TextView text;
    private String murl;
    private TabData tabData;
    private ViewPager mviewpager;
    private TextView tvTitle;
    private CirclePageIndicator indicator;
    private RefreshLIstview lvnewslist;
    private ArrayList<TabData.TabNewsData> newsdetail;
    private String moreurl=null;
    private NewsAsapter newsAsapter;
    private Handler cyclehandler;

    public TableDetailPage(Activity activity, NewsData.NewsTabData newsTabData) {
        super(activity);
        mdata=newsTabData;
    }

    @Override
    public View initView() {
        View view = View.inflate(mactivity, R.layout.ab_detil_pager, null);

        View newstitle = View.inflate(mactivity, R.layout.ist_header_topnews, null);
        mviewpager= (ViewPager) newstitle.findViewById(R.id.vp_news);
        tvTitle = (TextView) newstitle.findViewById(R.id.tv_title);
        indicator = (CirclePageIndicator) newstitle.findViewById(R.id.indicator);
        lvnewslist = (RefreshLIstview) view.findViewById(R.id.lv_list);

        lvnewslist.addHeaderView(newstitle);

        lvnewslist.setOnRefreshListener(new RefreshLIstview.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getServicedata();
            }

            @Override
            public void onLoadMore() {
                if (moreurl!=null){
                    getServicemore();
                    lvnewslist.isLoadingmore=true;
                }else {
                    lvnewslist.onRefreshComplete(false);
                    Toast.makeText(mactivity, "最后一页了", Toast.LENGTH_SHORT).show();
                }
            }
        });
        lvnewslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("testIde value",i+" "+ newsdetail.get(i).id);
                TextView mlisttitle = (TextView) view.findViewById(R.id.tv_newstitle);
                mlisttitle.setTextColor(Color.GRAY);
                if (Sputils.getStrign(mactivity,"read","").contains(newsdetail.get(i).id)){
                }else {
                    Sputils.setString(mactivity,"read",Sputils.getStrign(mactivity,"read",null)+newsdetail.get(i).id+",");
                }
                Intent intent=new Intent();
                intent.setClass(mactivity,webView.class);
                intent.putExtra("url",newsdetail.get(i).url);
                mactivity.startActivity(intent);
            }
        });
        return view;
    }
    class NewsAsapter extends BaseAdapter{

        private viewHolder holder;

        @Override
        public int getCount() {
            return newsdetail.size();
        }

        @Override
        public TabData.TabNewsData getItem(int i) {
            return newsdetail.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view==null){
                holder = new viewHolder();
                view = View.inflate(mactivity, R.layout.list_news_item, null);
                holder.tvTitle= (TextView) view.findViewById(R.id.tv_newstitle);
                holder.tvDate= (TextView) view.findViewById(R.id.tv_newstime);
                holder.ivPic= (ImageView) view.findViewById(R.id.imageView);
                view.setTag(holder);
            }
            else {
                holder = (viewHolder) view.getTag();
            }
            if (Sputils.getStrign(mactivity,"read","").contains(newsdetail.get(i).id)){
                holder.tvTitle.setTextColor(Color.GRAY);
            }
            TabData.TabNewsData item = getItem(i);
            x.image().bind(holder.ivPic,item.listimage);
//            holder.ivPic.setImageResource(R.drawable.image_demo);
            holder.tvDate.setText(newsdetail.get(i).pubdate);
            holder.tvTitle.setText(item.title);
            return view;
        }
    }

    static class viewHolder{
        public TextView tvTitle;
        public TextView tvDate;
        public ImageView ivPic;
    }

    class TopNewsAdapter extends PagerAdapter{
        public TopNewsAdapter(){

        }

        @Override
        public int getCount() {
            return tabData.data.topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView mimage=new ImageView(mactivity);
            mimage.setImageResource(R.drawable.topnews_item_default);
            mimage.setScaleType(ImageView.ScaleType.FIT_XY);//基于控件大小设置图片大小

            x.image().bind(mimage,tabData.data.topnews.get(position).topimage);
            container.addView(mimage);
         //   mimage.setOnTouchListener(new TopNewsTouchListener());
            mimage.setOnClickListener(new View.OnClickListener() {//ImageView的TouchListener事件与OnClick事件好象是有冲突。。
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(mactivity,webView.class);
                    intent.putExtra("url",tabData.data.topnews.get(position).url);
                    mactivity.startActivity(intent);
                }
            });
            return mimage;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    class TopNewsTouchListener implements View.OnTouchListener{

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    cyclehandler.removeCallbacksAndMessages(null);
                    break;
                case MotionEvent.ACTION_UP:
                    cyclehandler.sendEmptyMessageDelayed(1,3000);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    cyclehandler.sendEmptyMessageDelayed(1,3000);
                default: break;
            }
            return true;
        }
    }
    @Override
    public void initdata() {
        getServicedata();

        mviewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvTitle.setText(tabData.data.topnews.get(position).title);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void getServicedata(){
        murl=GlobalContent.SERVICE_URL+mdata.url;
        RequestParams params =new RequestParams(murl);
        String readcache = readcache(murl);
        if (!TextUtils.isEmpty(readcache)){parseData(readcache,false);}
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //             Log.d("Xutils",result);
                parseData(result,false);
                try {
                    setcache(murl,result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                lvnewslist.onRefreshComplete(true);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                lvnewslist.onRefreshComplete(false);
            }
            @Override
            public void onCancelled(CancelledException cex) {}
            @Override
            public void onFinished() {}
        });
    }

    private void getServicemore(){
        RequestParams params =new RequestParams(moreurl);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                parseData(result,true);
                lvnewslist.onRefreshComplete(true);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                lvnewslist.onRefreshComplete(false);
            }
            @Override
            public void onCancelled(CancelledException cex) {}
            @Override
            public void onFinished() {}
        });
    }

    private void parseData(String result,Boolean more) {
        Gson gson = new Gson();
        tabData = gson.fromJson(result, TabData.class);
        Log.d("TableDetailPage",tabData.toString());

        newsdetail = tabData.data.news;
        if (TextUtils.isEmpty(tabData.data.more)){
            moreurl=null;
        }else {
            moreurl = GlobalContent.SERVICE_URL + tabData.data.more;
        }
        // Log.d("aaaaaaaaaaa",newsdetail.get(0).pubdate+"hgjf"+newsdetail.get(0).title);
        if (more){
            newsdetail.addAll(tabData.data.news);
            newsAsapter.notifyDataSetChanged();
            lvnewslist.onRefreshComplete(false);
        }else {
            newsAsapter = new NewsAsapter();
            lvnewslist.setAdapter(newsAsapter);

            mviewpager.setAdapter(new TopNewsAdapter());
            indicator.setViewPager(mviewpager);
            indicator.setSnap(true);
            indicator.onPageSelected(0);
            tvTitle.setText(tabData.data.topnews.get(0).title);
        }
        cyclehandler =  new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int current=mviewpager.getCurrentItem();

                int pagemax=mviewpager.getChildCount();
                if (current < pagemax) {
                    current++;
                } else {
                    current = 0;
                }
                indicator.setCurrentItem(current);
                int what = msg.what;
                Log.d("Handle",what+"");
                cyclehandler.removeCallbacksAndMessages(null);
                cyclehandler.sendEmptyMessageDelayed(1,3000);
            }
        };
        cyclehandler.sendEmptyMessageDelayed(0,3000);
    }
}
