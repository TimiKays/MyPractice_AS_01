package com.tangqi.safecenter.activity;

import android.os.Handler;
import android.os.Message;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.tangqi.safecenter.R;

public class RocketSmokeActivity extends BaseActivity {

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            finish();
        }
    };


    @Override
    public void initView() {
        setContentView(R.layout.activity_rocket_smoke);


        ImageView desktop_smoke_m = (ImageView) findViewById(R.id.desktop_smoke_m);
        ImageView desktop_smoke_t = (ImageView) findViewById(R.id.desktop_smoke_t);

        AlphaAnimation a = new AlphaAnimation(0, 1);
        a.setDuration(500);

        desktop_smoke_m.startAnimation(a);
        desktop_smoke_t.startAnimation(a);

        mHandler.sendEmptyMessageDelayed(0,1000);
    }

    @Override
    public void showNextView() {

    }

    @Override
    public void showPrevious() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }
}
