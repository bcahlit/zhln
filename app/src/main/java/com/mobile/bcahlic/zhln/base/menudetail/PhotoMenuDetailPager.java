package com.mobile.bcahlic.zhln.base.menudetail;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mobile.bcahlic.zhln.R;
import com.mobile.bcahlic.zhln.base.BaseMenuDetailPage;
import com.mobile.bcahlic.zhln.doman.PhotosData;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by bcahlic on 16-7-5.
 */
public class PhotoMenuDetailPager extends BaseMenuDetailPage {

    private ImageButton mbtPhoto;
    private GridView mgridview;
    private ListView miistview;
    private ArrayList<PhotosData.PhotoInfo> photos;
    private Boolean btnState=true;

    public PhotoMenuDetailPager(Activity activity, ImageButton btPhoto) {
        super(activity);
        mbtPhoto = btPhoto;
        mbtPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnState){
                    miistview.setVisibility(View.GONE);
                    mgridview.setVisibility(View.VISIBLE);
                    mbtPhoto.setImageResource(R.drawable.icon_pic_grid_type);
                }
                else {
                    mgridview.setVisibility(View.GONE);
                    miistview.setVisibility(View.VISIBLE);
                    mbtPhoto.setImageResource(R.drawable.icon_pic_list_type);
                }
                btnState=!btnState;
            }
        });
    }

    @Override
    public View initView() {
        View view = View.inflate(mactivity, R.layout.photodetail, null);

        mgridview = (GridView) view.findViewById(R.id.gv_photo);
        miistview = (ListView) view.findViewById(R.id.lv_photo);
        btnState = true;
        initdata();
        return view;
    }

    @Override
    public void initdata() {
        getdatafromserver();
    }
    public void getdatafromserver(){
        final RequestParams params=new RequestParams("http://123.206.72.85/t/zhbj/photos/photos_1.json");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                parseData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }

    public void parseData(String result){
        Gson gson = new Gson();
        PhotosData photosData = gson.fromJson(result, PhotosData.class);

        photos = photosData.data.news;
        if (photos!=null) {
            miistview.setAdapter(new photoAdapter());
            mgridview.setAdapter(new photoAdapter());
        }
    }
    class photoAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return photos.size();
        }

        @Override
        public PhotosData.PhotoInfo getItem(int i) {
            return photos.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view==null){
                view=View.inflate(mactivity,R.layout.list_photo_item,null);
                viewHolder=new ViewHolder();
                viewHolder.tvTitle= (TextView) view.findViewById(R.id.tv_title);
                viewHolder.ivPic= (ImageView) view.findViewById(R.id.iv_pic);

                view.setTag(viewHolder);

            }else {
                viewHolder= (ViewHolder) view.getTag();
            }

            viewHolder.tvTitle.setText(getItem(i).title);
            x.image().bind(viewHolder.ivPic,photos.get(i).listimage);

            return view;
        }
    }

    static class ViewHolder {
        public TextView tvTitle;
        public ImageView ivPic;
    }
}
