package com.mobile.bcahlic.zhln.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.mobile.bcahlic.zhln.R;
import com.mobile.bcahlic.zhln.utils.Sputils;

/**
 * Created by bcahlic on 16-7-3.
 */
public class Splashactivity extends Activity {

    private RelativeLayout viewById;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        viewById = (RelativeLayout) findViewById(R.id.rl_root);
        startanimation();
    }
    public void startanimation(){
        AnimationSet set=new AnimationSet(false);

        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1500);
        rotateAnimation.setFillAfter(true);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1f);
        alphaAnimation.setDuration(1500);
        alphaAnimation.setFillAfter(true);

        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(1500);

        set.addAnimation(rotateAnimation);
        set.addAnimation(alphaAnimation);
        set.addAnimation(scaleAnimation);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                shownext();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        viewById.startAnimation(set);
    }
    private void shownext(){
        if (Sputils.getBoolean(this,"is_user_guide_show",false)){
            startActivity(new Intent(Splashactivity.this,MainActivity.class));
        }else {
            startActivity(new Intent(Splashactivity.this,GuideActivity.class));
        }
        finish();
    }
}
