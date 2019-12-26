package com.tangqi.safecenter.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.tangqi.safecenter.R;
import com.tangqi.safecenter.activity.RocketSmokeActivity;

public class RocketService extends Service {
    private WindowManager mwm;
    private int mScreen_height;
    private int mScreen_width;
    private Context mc;
    private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
    private View rocket;
    private static final String TAG = "Mylog";
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mwm.updateViewLayout(rocket,params);

        }
    };
    private WindowManager.LayoutParams params;


    public RocketService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mc=this;
        mwm = (WindowManager) getSystemService(WINDOW_SERVICE);
        mScreen_height = mwm.getDefaultDisplay().getHeight();
        mScreen_width = mwm.getDefaultDisplay().getWidth();
        Log.i(TAG, "屏幕宽高"+mScreen_width+mScreen_height);

        showRocket();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mwm!=null && rocket!=null){
            mwm.removeView(rocket);
        }
    }

    private void showRocket() {

        params = mParams;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        params.format = PixelFormat.TRANSLUCENT;
        //在响铃的时候显示吐司,和电话类型一致
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.setTitle("Toast");
        params.gravity = Gravity.LEFT+Gravity.TOP;



        //吐司显示效果(吐司布局文件),xml-->view(吐司),将吐司挂在到windowManager窗体上
        rocket = View.inflate(mc, R.layout.rocket, null);
        ImageView rocket_bg = rocket.findViewById(R.id.rocket_bg);
        AnimationDrawable anim = (AnimationDrawable) rocket_bg.getBackground();
        anim.start();


        /**
         * 拖拽事件
         */
        rocket.setOnTouchListener(new View.OnTouchListener() {
            private int startX;
            private int startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        int endX = (int) event.getRawX();
                        int endY = (int) event.getRawY();

                        int disX = endX - startX;
                        int disY = endY - startY;

                        params.y = params.y + disY;
                        params.x = params.x + disX;

                        //容错
//                        if (params.y < 0 || params.x < 0 || params.y > (mScreen_height-rocket.getHeight() - 32) || params.x >
//                                mScreen_width*-rocket.getWidth()) {
//                            return true;
//                        }
                        //容错处理
                        if(params.x<0){
                            params.x = 0;
                        }

                        if(params.y<0){
                            params.y=0;
                        }

                        if(params.x>mScreen_width- rocket.getWidth()){
                            params.x = mScreen_width- rocket.getWidth();
                        }

                        if(params.y>mScreen_height- rocket.getHeight()-32){
                            params.y = mScreen_height- rocket.getHeight()-32;
                        }

                        //展示
                        mwm.updateViewLayout(rocket, params);
                        Log.i(TAG, "坐标："+ params.x+ params.y);

                        //重新赋值起始坐标
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();

                        break;


                    case MotionEvent.ACTION_UP:


                        if(startX>mScreen_width/4 && startX<mScreen_width*3/4 && startY>mScreen_height*3/4){
                            //升空
                            new Thread(){
                                @Override
                                public void run() {
                                    super.run();
                                    for(int i = 0;i<15;i++){
                                        params.y= params.y-mScreen_height*3/20;
                                        //展示

                                        mHandler.sendEmptyMessage(0);
                                        try {
                                            sleep(40);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                }
                            }.start();

                            //烟雾 补间动画
                            Intent intent=new Intent(mc, RocketSmokeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);






                        }
                        break;

                }

                return true;
            }
        });

        //在窗体上挂在一个view(权限)
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                mwm.addView(rocket, params);
            }
        }





    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
