package com.mobile.bcahlic.zhln.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by bcahlic on 16-7-10.
 */
public class TopNewsViewPager extends ViewPager {

    private int startx;
    private int starty;

    public TopNewsViewPager(Context context) {
        super(context);
    }

    public TopNewsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startx = (int) ev.getRawX();
                starty = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int endx= (int) ev.getRawX();
                int endy= (int) ev.getRawY();
                if (Math.abs(endx-startx)>Math.abs(endy-starty)){
                    if (endx>startx){
                        if (getCurrentItem()==0){
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }else {
                        if (getCurrentItem()==getAdapter().getCount()-1){
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                }else getParent().requestDisallowInterceptTouchEvent(false);

        }
        return super.dispatchTouchEvent(ev);
    }
}
