package com.tangqi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class ViewFlipperTest extends AppCompatActivity {

    private ViewFlipper flipper;
    private int[] pictures={R.drawable.address_book,R.drawable.calendar,R.drawable.games_control,R.drawable.clock};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flipper_test);

        //初始化
        flipper=findViewById(R.id.view_flipper1);
        for(int i=0;i<pictures.length;i++){
            flipper.addView(getImageView(pictures[i]));
        }

        //添加动画效果


        flipper.setOutAnimation(this,R.anim.right_out);
        flipper.setInAnimation(this,R.anim.right_in);
        flipper.setFlipInterval(3000);
        flipper.startFlipping();


    }


    private ImageView getImageView(int resId){
        ImageView imageView = new ImageView(this);
//        imageView.setBackgroundResource(resId); 这个会拉伸唉
        imageView.setImageResource(resId);
        imageView.setBackgroundResource(R.color.lightbule);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);//设置缩放类型

        return imageView;
    }
}
