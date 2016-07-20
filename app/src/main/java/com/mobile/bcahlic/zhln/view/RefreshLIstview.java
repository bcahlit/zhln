package com.mobile.bcahlic.zhln.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mobile.bcahlic.zhln.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bcahlic on 16-7-11.
 */
public class RefreshLIstview extends ListView implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener {
    private final int STATE_PULL_REFERSH=0;//下拉刷新
    private final int STATE_RELASE_REFERSH=1;//松开刷新
    private final int STATE_REFERSH=2;//正在刷新
    private int starY=-1;
    private View holder;
    private int measuredHeight;
    private int mcurrentstate=STATE_PULL_REFERSH;
    private TextView tvTitle;
    private TextView tvTime;
    private ImageView ivArrow;
    private ProgressBar pbProgress;
    private RotateAnimation animUp;
    private RotateAnimation animDown;
    private int padding;
    private View footeview;
    private int footermeasuredHeight;
    public boolean isLoadingmore=false;
    private OnItemClickListener mitemclicklistener;

    public RefreshLIstview(Context context) {
        super(context);
        initHoldView();
        initFoodView();
    }

    public RefreshLIstview(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHoldView();
        initFoodView();
    }

    public RefreshLIstview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHoldView();
        initFoodView();
    }
    public void initHoldView(){
        holder = View.inflate(getContext(), R.layout.refersh_holder, null);

        tvTitle = (TextView) holder.findViewById(R.id.tv_title);
        tvTime = (TextView) holder.findViewById(R.id.tv_time);
        ivArrow = (ImageView) holder.findViewById(R.id.iv_arr);
        pbProgress = (ProgressBar) holder.findViewById(R.id.pb_progress);

        addHeaderView(holder);
        holder.measure(0,0);
        measuredHeight = holder.getMeasuredHeight();
        holder.setPadding(0,-measuredHeight,0,0);

        tvTime.setText("最后刷新时间:" + getCurrentTime());

        initArrowAnim();
    }

    private void initFoodView() {
        footeview = View.inflate(getContext(), R.layout.refersh_foote_listview, null);
        addFooterView(footeview);
        footeview.measure(0,0);
        footermeasuredHeight = footeview.getMeasuredHeight();
        footeview.setPadding(0,-footermeasuredHeight,0,0);

        this.setOnScrollListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                starY = (int)  ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (starY==-1){
                    starY = (int)  ev.getRawY();
                }
                if (mcurrentstate==STATE_REFERSH)break;
                int endY = (int) ev.getRawY();
                int dy = endY - starY;
                if (dy>0&&getFirstVisiblePosition()==0){
                    padding = dy-measuredHeight;
                    holder.setPadding(0, padding,0,0);
                    if (padding >0&&mcurrentstate!=STATE_RELASE_REFERSH){//状态改为松开刷新
                        mcurrentstate=STATE_RELASE_REFERSH;
                        refershState();
                    }else if (padding <0&&mcurrentstate!=STATE_PULL_REFERSH){
                        mcurrentstate=STATE_PULL_REFERSH;
                        refershState();
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                starY=-1;
                if (mcurrentstate==STATE_RELASE_REFERSH){
                    mcurrentstate=STATE_REFERSH;
                    refershState();
                }else if (padding<0&&mcurrentstate==STATE_PULL_REFERSH){
                    refershState();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
    private void refershState(){
        switch (mcurrentstate){
            case STATE_PULL_REFERSH:
                holder.setPadding(0,-measuredHeight,0,0);
                tvTitle.setText("下拉刷新");
                ivArrow.setVisibility(View.VISIBLE);
                pbProgress.setVisibility(View.INVISIBLE);
                ivArrow.startAnimation(animDown);
                break;
            case STATE_RELASE_REFERSH:
                tvTitle.setText("松开刷新");
                ivArrow.setVisibility(View.VISIBLE);
                pbProgress.setVisibility(View.INVISIBLE);
                ivArrow.startAnimation(animUp);
                break;
            case STATE_REFERSH:
                tvTitle.setText("正在刷新...");
                holder.setPadding(0,0,0,0);
                ivArrow.clearAnimation();// 必须先清除动画,才能隐藏
                ivArrow.setVisibility(View.INVISIBLE);
                pbProgress.setVisibility(View.VISIBLE);
                if (mListener!=null){
                    mListener.onRefresh();
                }
                break;
        }
    }
    OnRefreshListener mListener;
    public void setOnRefreshListener(OnRefreshListener listener) {
        mListener = listener;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        if (i==SCROLL_STATE_IDLE|| i==SCROLL_STATE_FLING){
            if (getLastVisiblePosition()==getCount()-1 && !isLoadingmore){
                footeview.setPadding(0,0,0,0);
                setSelection(getCount()-1);
                if (mListener!=null){
                    mListener.onLoadMore();
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }


    public interface OnRefreshListener {
        void onRefresh();

        void onLoadMore();// 加载下一页数据
    }
    private void initArrowAnim() {
        // 箭头向上动画

        animUp = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animUp.setDuration(200);
        animUp.setFillAfter(true);

        // 箭头向下动画
        animDown = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animDown.setDuration(200);
        animDown.setFillAfter(true);

    }
    public void onRefreshComplete(boolean success) {
        if(isLoadingmore){
            footeview.setPadding(0,-footermeasuredHeight,0,0);
            isLoadingmore=false;
        }else{
            mcurrentstate = STATE_PULL_REFERSH;
            tvTitle.setText("下拉刷新");
            ivArrow.setVisibility(View.VISIBLE);
            pbProgress.setVisibility(View.INVISIBLE);

            holder.setPadding(0, -measuredHeight, 0, 0);// 隐藏

            if (success) {
                tvTime.setText("最后刷新时间:" + getCurrentTime());
            }
        }

    }
    /**
     * 获取当前时间
     */
    public String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    /**
     * 重写方法将开始的两个头布局占的位置去掉
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (mitemclicklistener!=null){
            mitemclicklistener.onItemClick(adapterView,view,i-getHeaderViewsCount(),l);
        }
    }
    @Override
    public void setOnItemClickListener(OnItemClickListener listener) {
        super.setOnItemClickListener(this);
        mitemclicklistener = listener;
    }
}
