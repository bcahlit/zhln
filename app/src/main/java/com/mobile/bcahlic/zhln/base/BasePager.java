package com.mobile.bcahlic.zhln.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.mobile.bcahlic.zhln.R;
import com.mobile.bcahlic.zhln.activitys.MainActivity;

/**
 * Created by bcahlic on 16-7-4.
 */
public class BasePager {
    public Activity mcontext;
    public TextView title;
    public FrameLayout flcontext;
    public ImageButton button;
    public View rl_root;
    public ImageButton btPhoto;

    protected BasePager(Activity context){
        mcontext=context;
        initView();
    }
    public void initView(){
        View view = View.inflate(mcontext, R.layout.base_page, null);
        title = (TextView) view.findViewById(R.id.tv_title);
        flcontext = (FrameLayout) view.findViewById(R.id.fl_content);
        button = (ImageButton) view.findViewById(R.id.btn_menu);
        btPhoto = (ImageButton) view.findViewById(R.id.btn_photo);

        rl_root=view;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSlidingMenu();
            }
        });
    }
    public void initData(){}
    public void setSlidingMenuEnable (boolean enable){
        MainActivity mainUI= (MainActivity) mcontext;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        if (enable){
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }
    private void toggleSlidingMenu() {
        MainActivity mmainacty= (MainActivity) mcontext;
        SlidingMenu slidingMenu = mmainacty.getSlidingMenu();
        slidingMenu.toggle();
    }
}
