package com.tangqi.safecenter.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.tangqi.safecenter.R;

public abstract class BaseActivity extends Activity {

    private GestureDetector gd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initView();
        initGesture();
        initData();
        initEvent();


    }

    //触摸事件，把手势设置上去
    public boolean onTouchEvent(MotionEvent event) {
        gd.onTouchEvent(event);
        return super.onTouchEvent(event);
    }



    private void initGesture() {
        /*
         * 滑动的方法
         *
         * e1,按下的点
         * e2 松开屏幕的点
         * velocityX x轴方向的速度
         * velocityY y轴方向的速度
         */
        gd = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float
                    distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            /**
             * 滑动的方法
             *
             * e1,按下的点
             * e2 松开屏幕的点
             * velocityX x轴方向的速度
             * velocityY y轴方向的速度
             */
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float
                    velocityY) {
                //判断滑动距离、移动速度
                float len = e2.getX() - e1.getX();
                if (Math.abs(len) > 100 && velocityX > 100) {
                    if (len > 0) {
                        //往右滑，显示上一个
                        previous();
                    } else {
                        //往左划
                        next();
                    }


                    return true;
                } else {
                    return false;
                }

            }
        });
    }


    public void goActivity(Class goalClass){
        Intent intent = new Intent(this,goalClass);
        startActivity(intent);
        finish();//关掉自己
    }

    public void next() {
        showNextView();
        nextAnim();
    }

    public void previous() {
        showPrevious();
        preAnim();
    }


    private void nextAnim() {
        overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
    }

    private void preAnim() {
        overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
    }


    public abstract void initView();

    public abstract void showNextView();

    public abstract void showPrevious();

    public abstract void initData();

    public abstract void initEvent();

}
