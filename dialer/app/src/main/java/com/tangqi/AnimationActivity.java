package com.tangqi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class AnimationActivity extends AppCompatActivity {

    private ImageView mIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        mIv = (ImageView) findViewById(R.id.anim_image);
    }


    public void rotate(View view) {
        Animation ra = AnimationUtils.loadAnimation(this,R.anim.rotate_shake);
        mIv.startAnimation(ra);

    }

    public void alpha(View view) {
        Animation aa = AnimationUtils.loadAnimation(this,R.anim.alpha_bling);
        mIv.startAnimation(aa);

    }

    public void translate(View view) {
        Animation ta = AnimationUtils.loadAnimation(this,R.anim.translat_3020);
        mIv.startAnimation(ta);
    }

    public void scale(View view) {
        Animation sa = AnimationUtils.loadAnimation(this,R.anim.scale_3020);
        mIv.startAnimation(sa);
    }

    public void crazy(View view) {
        Animation ca = AnimationUtils.loadAnimation(this,R.anim.set_crazy);
        mIv.startAnimation(ca);
    }
}
