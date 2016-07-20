package com.mobile.bcahlic.zhln.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by bcahlic on 16-7-9.
 */
public class HorizontalViewPager extends ViewPager {
    public HorizontalViewPager(Context context) {
        super(context);
    }

    public HorizontalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentItem()!=0){
            getParent().requestDisallowInterceptTouchEvent(true);
        }else getParent().requestDisallowInterceptTouchEvent(false);
        return super.dispatchTouchEvent(ev);
    }
}
