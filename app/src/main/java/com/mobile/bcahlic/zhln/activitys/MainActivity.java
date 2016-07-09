package com.mobile.bcahlic.zhln.activitys;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Window;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.mobile.bcahlic.zhln.R;
import com.mobile.bcahlic.zhln.fragment.LeftMenuFragment;
import com.mobile.bcahlic.zhln.fragment.contentFragment;

public class MainActivity extends SlidingFragmentActivity {

    private static final String LEFT_MENU = "leftmenu";
    private static final String FRAGMENT_CONTENT = "fagmentContent";
    private FragmentManager mfmanager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        setBehindContentView(R.layout.left_menu);
        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffset(500);
        initFragment();
    }
    public void initFragment(){
        mfmanager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction mft = mfmanager.beginTransaction();
        mft.replace(R.id.fl_left_menu, new LeftMenuFragment(),LEFT_MENU);
        mft.replace(R.id.fl_content, new contentFragment(),FRAGMENT_CONTENT);
        mft.commit();
    }
    public LeftMenuFragment getLeftMenuFragment(){
        LeftMenuFragment fragmentByTag = (LeftMenuFragment) mfmanager.findFragmentByTag(LEFT_MENU);
        return fragmentByTag;
    }
    public contentFragment getcontentFragment(){
        contentFragment fragmentByTag = (contentFragment) mfmanager.findFragmentByTag(FRAGMENT_CONTENT);
        return fragmentByTag;
    }
}
