package com.mobile.bcahlic.zhln.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mobile.bcahlic.zhln.R;
import com.mobile.bcahlic.zhln.utils.Sputils;

import java.util.ArrayList;

/**
 * Created by bcahlic on 16-7-4.
 */
public class GuideActivity extends Activity {

    private ViewPager tpguide;
    int mPointWidth;
    protected int [] guideImages={R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
    ArrayList<ImageView> imageViews;
    private LinearLayout llpoint;
    private ImageView viewRedPoint;
    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        llpoint = (LinearLayout) findViewById(R.id.ll_point_gray);
        viewRedPoint = (ImageView) findViewById(R.id.lliv_point);
        btnStart = (Button) findViewById(R.id.bt_atart);
        initViews();
        tpguide = (ViewPager) findViewById(R.id.vp_guide);
        tpguide.addOnPageChangeListener(new mOnPageChangeListener());
        tpguide.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        tpguide.setAdapter(new mguideadapter());
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sputils.setBoolean(GuideActivity.this,"is_user_guide_show",true);
                startActivity(new Intent(GuideActivity.this,MainActivity.class));
                finish();
            }
        });

    }
    class mOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int len = (int) (mPointWidth * positionOffset) + position
                    * mPointWidth;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewRedPoint
                    .getLayoutParams();// 获取当前红点的布局参数
            params.leftMargin = len;// 设置左边距

            viewRedPoint.setLayoutParams(params);// 重新给小红点设置布局参数
        }

        @Override
        public void onPageSelected(int position) {
            if (position == guideImages.length - 1) {// 最后一个页面
                btnStart.setVisibility(View.VISIBLE);// 显示开始体验的按钮
            } else {
                btnStart.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
    private void initViews(){
        WindowManager wm = this.getWindowManager();

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        imageViews=new ArrayList<ImageView>();
        for(int i:guideImages){
            ImageView imageView=new ImageView(this);
            imageView.setImageResource(i);
            imageViews.add(imageView);
        }
        for (int i : guideImages){
            View view=new View(this);
            view.setBackgroundResource(R.drawable.shape_point_gray);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
            if (i>0){
                params.leftMargin=10;
            }
            view.setLayoutParams(params);
            llpoint.addView(view);
        }
        llpoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llpoint.getViewTreeObserver()
                        .removeGlobalOnLayoutListener(this);
                mPointWidth = llpoint.getChildAt(1).getLeft()
                        - llpoint.getChildAt(0).getLeft();
            }
        });
    }
    class mguideadapter extends PagerAdapter{

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViews.get(position));
            return imageViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeViewInLayout((View) object);
        }
    }
}
