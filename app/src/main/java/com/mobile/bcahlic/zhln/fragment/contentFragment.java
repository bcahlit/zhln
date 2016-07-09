package com.mobile.bcahlic.zhln.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.mobile.bcahlic.zhln.R;
import com.mobile.bcahlic.zhln.base.BasePager;
import com.mobile.bcahlic.zhln.base.impl.GovAffairPager;
import com.mobile.bcahlic.zhln.base.impl.HomePager;
import com.mobile.bcahlic.zhln.base.impl.NewsPager;
import com.mobile.bcahlic.zhln.base.impl.SettingPager;
import com.mobile.bcahlic.zhln.base.impl.SmartServicePager;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bcahlic on 16-7-4.
 */
public class contentFragment extends Badeframent {
    @ViewInject(R.id.rg_group)
    private RadioGroup rgGroup;
    @ViewInject(R.id.vp_content)
    private ViewPager mviewpage;
    private List<BasePager> mpagelist;

    @Override
    public View initview() {
        View view = View.inflate(mActivityactivity, R.layout.activity_content, null);
        x.view().inject(this,view);
        return view;
    }

    @Override
    public void initdata() {
        rgGroup.check(R.id.home);
        mpagelist = new ArrayList<BasePager>();
        mpagelist.add(new HomePager(mActivityactivity));
        mpagelist.add(new NewsPager(mActivityactivity));
        mpagelist.add(new SmartServicePager(mActivityactivity));
        mpagelist.add(new GovAffairPager(mActivityactivity));
        mpagelist.add(new SettingPager(mActivityactivity));

        mviewpage.setAdapter(new mcontent());

        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.home:
                        mviewpage.setCurrentItem(0,false);
                        break;
                    case R.id.news:
                        mviewpage.setCurrentItem(1,false);
                        break;
                    case R.id.smart:
                        mviewpage.setCurrentItem(2,false);
                        break;
                    case R.id.gov:
                        mviewpage.setCurrentItem(3,false);
                        break;
                    case R.id.setting:
                        mviewpage.setCurrentItem(4,false);
                        break;
                    default:break;
                }
            }
        });
        mviewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mpagelist.get(position).initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mpagelist.get(0).initData();
    }
    public NewsPager getNewsPager(){
        return (NewsPager) mpagelist.get(1);
    }
    class mcontent extends PagerAdapter{

        @Override
        public int getCount() {
            return mpagelist.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            mpagelist.get(position).initData();
            container.addView(mpagelist.get(position).rl_root);
//            BasePager pager = mpagelist.get(position);
//            container.addView(pager.rl_root);
//            pager.initData();
            return mpagelist.get(position).rl_root;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }
}
