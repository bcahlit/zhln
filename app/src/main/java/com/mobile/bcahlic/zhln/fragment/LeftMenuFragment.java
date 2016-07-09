package com.mobile.bcahlic.zhln.fragment;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.mobile.bcahlic.zhln.R;
import com.mobile.bcahlic.zhln.activitys.MainActivity;
import com.mobile.bcahlic.zhln.base.impl.NewsPager;
import com.mobile.bcahlic.zhln.doman.NewsData;

import java.util.ArrayList;

/**
 * Created by bcahlic on 16-7-4.
 */
public class LeftMenuFragment extends Badeframent {
    private int poation;
    private ListView lvmenu;
    private ArrayList<NewsData.NewsMenuData> menuList;
    private mLiAdapter mliAdapter;

    @Override
    public View initview() {
        View view = View.inflate(mActivityactivity, R.layout.activity_left_menu, null);
        lvmenu = (ListView) view.findViewById(R.id.lv_menu);
        return view;
    }

    @Override
    public void initdata() {
        lvmenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                poation=i;
                mliAdapter.notifyDataSetChanged();
                setCurrentMenuDetailPager(i);
                toggleSlidingMenu();
            }
        });
    }

    private void toggleSlidingMenu() {
        MainActivity mmainacty= (MainActivity) mActivityactivity;
        SlidingMenu slidingMenu = mmainacty.getSlidingMenu();
        slidingMenu.toggle();
    }

    class mLiAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return menuList.size();
        }

        @Override
        public NewsData.NewsMenuData getItem(int i) {
            return menuList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View Contentview, ViewGroup viewGroup) {
            View view = View.inflate(mActivityactivity, R.layout.list_menu_item, null);
            TextView title = (TextView) view.findViewById(R.id.tv_title);
            NewsData.NewsMenuData item = menuList.get(i);
            title.setText(item.title);
            if (i==poation){
                title.setEnabled(true);
            }else {
                title.setEnabled(false);
            }
            return view;
        }
    }
    public void setCurrentMenuDetailPager(int past){
        MainActivity mainActivity= (MainActivity) mActivityactivity;
        contentFragment contentfragment = mainActivity.getcontentFragment();
        NewsPager newsPager = contentfragment.getNewsPager();
        newsPager.setCurrentMenuDetailPager(past);
    }
    public void setMenuData(NewsData data) {
        Log.d("Leftmenu",data.toString());
        menuList = data.data;
        mliAdapter = new mLiAdapter();
        lvmenu.setAdapter(mliAdapter);
    }
}
