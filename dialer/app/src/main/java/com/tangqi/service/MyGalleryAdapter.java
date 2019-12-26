package com.tangqi.service;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

/**
 * Created by Administrator on 2018/4/4.
 */

public class MyGalleryAdapter extends BaseAdapter{

    private Context mc;
    private int[] img_arr;
    public MyGalleryAdapter(Context context,int[] img_arr){
        this.mc=context;
        this.img_arr=img_arr;
    }

    //返回图片数量
    @Override
    public int getCount() {
        return img_arr.length;
    }

    //告诉适配器取得目前容器中的数据ID和对象
    @Override
    public Object getItem(int position) {
        return img_arr[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //将要显示的图片的View
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mc);
        imageView.setImageResource(img_arr[position]);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new Gallery.LayoutParams(240, 180));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        imageView.setBackgroundResource(R.drawable.blue_bg);

        return imageView;
    }
}