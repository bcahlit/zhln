package com.mobile.bcahlic.zhln.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.mobile.bcahlic.zhln.R;
import com.mobile.bcahlic.zhln.utils.Sputils;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class webView extends Activity implements View.OnClickListener {

    private ImageButton btback;
    private WebView mwebview;
    private String url;
    private ImageButton btshare;
    private ImageButton btsize;
    private ProgressBar mprob;
    public int mcurrentitem;
    public int aftercurentitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");

        mwebview = (WebView) findViewById(R.id.wv_web);
        btback = (ImageButton) findViewById(R.id.btn_back);
        btshare = (ImageButton) findViewById(R.id.btn_share);
        btsize = (ImageButton) findViewById(R.id.btn_size);
        mprob = (ProgressBar) findViewById(R.id.pb_progress);
        aftercurentitem= Sputils.getint(this,"websize",2);
        initfunction();
    }
    private void initfunction(){
        mwebview.loadUrl(url);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setwebviewsize();
        WebSettings settings = mwebview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        btshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startshare();
            }
        });
        mwebview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mprob.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mprob.setVisibility(View.GONE);
            }
        });
        btsize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialog();
            }
        });
    }

    private void showdialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        String[] items = new String[] { "超大号字体", "大号字体", "正常字体", "小号字体",
                "超小号字体" };
        builder.setTitle("字体设置");
        builder.setSingleChoiceItems(items, aftercurentitem, new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mcurrentitem = i;
            }
        });
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                aftercurentitem=mcurrentitem;
                Sputils.setint(webView.this,"websize",aftercurentitem);
                setwebviewsize();
            }
        });
        builder.setPositiveButton("取消", null);
        builder.show();
    }
    private void setwebviewsize(){
        WebSettings settings = mwebview.getSettings();
        switch (aftercurentitem){
            case 0:
                settings.setTextSize(WebSettings.TextSize.LARGEST);
                break;
            case 1:
                settings.setTextSize(WebSettings.TextSize.LARGER);
                break;
            case 2:
                settings.setTextSize(WebSettings.TextSize.NORMAL);
                break;
            case 3:
                settings.setTextSize(WebSettings.TextSize.SMALLER);
                break;
            case 4:
                settings.setTextSize(WebSettings.TextSize.SMALLEST);
                break;
            default:break;
        }
    }

    @Override
    public void onClick(View view) {

    }
    public void startshare(){
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
//关闭sso授权
        oks.disableSSOWhenAuthorize();

// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
// titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
// text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }
}
