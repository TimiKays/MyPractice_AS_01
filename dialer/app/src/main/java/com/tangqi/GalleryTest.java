package com.tangqi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.tangqi.service.MyGalleryAdapter;

public class GalleryTest extends Activity implements AdapterView.OnItemSelectedListener,ViewSwitcher.ViewFactory{

    private int[] img_arr = {R.drawable.address_book, R.drawable.calendar, R.drawable.camera, R.drawable.clock, R.drawable.games_control, R.drawable.messenger, R.drawable.ringtone, R.drawable.settings, R.drawable.speech_balloon, R.drawable.weather, R.drawable.world, R.drawable.youtube};
    private Gallery gallery;
    private ImageSwitcher is;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_test);

        gallery=findViewById(R.id.gallery);
        is=findViewById(R.id.image_switcher);

        gallery.setAdapter(new MyGalleryAdapter(this,img_arr));
        gallery.setOnItemSelectedListener(this);
        is.setFactory(this);
        //动画效果
        is.setInAnimation(AnimationUtils.loadAnimation(this,android.R.anim.fade_in));//淡入效果
        is.setOutAnimation(AnimationUtils.loadAnimation(this,android.R.anim.fade_out));//淡入效果

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        is.setImageResource(img_arr[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public View makeView() {
        ImageView imageView=new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        return imageView;
    }
}

