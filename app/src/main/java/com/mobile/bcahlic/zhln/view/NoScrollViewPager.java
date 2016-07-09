package com.mobile.bcahlic.zhln.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by bcahlic on 16-7-4.
 */
public class NoScrollViewPager extends ViewPager{
    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public NoScrollViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return false;
    }
}
