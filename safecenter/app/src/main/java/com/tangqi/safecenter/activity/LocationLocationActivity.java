package com.tangqi.safecenter.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tangqi.safecenter.R;
import com.tangqi.safecenter.utils.ConstantValues;
import com.tangqi.safecenter.utils.SpUtils;

/**
 * 设置归属地提示框位置的图层
 * <p>
 * 1、写方法：获取按钮的位置，判断如果在屏幕下方，则隐藏下面的，显示上面的，反之亦然。
 * 2、设置按钮的拖动监听，按下时获取起始坐标，拖动时获取当前坐标，从而算出横向和纵向移动距离，然后显示移动后的按钮，抬起时存储最后的按钮坐标；
 * 3、来电时的吐司位置读取存储的坐标
 * 4、双击居中
 */
public class LocationLocationActivity extends BaseActivity {


    private ImageView iv_drag;
    private Button mBt_button;
    private Button bt_button;
    private Button bt_top;
    private WindowManager mwm;
    private int mScreen_height;
    private int mScreen_width;
    private Context mc;
    private long[] click=new long[2];

    @Override
    public void initView() {
        setContentView(R.layout.activity_location_location);
        mc=this;
        bt_top = (Button) findViewById(R.id.bt_top);
        iv_drag = (ImageView) findViewById(R.id.iv_drag);
        bt_button = (Button) findViewById(R.id.bt_button);

        mwm = (WindowManager) getSystemService(WINDOW_SERVICE);
        mScreen_height = mwm.getDefaultDisplay().getHeight();
        mScreen_width = mwm.getDefaultDisplay().getWidth();


        //回显按钮位置
        int location_x = SpUtils.getInt(mc, ConstantValues.LOCATION_X, 0);
        int location_y = SpUtils.getInt(mc, ConstantValues.LOCATION_Y, 0);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout
                .LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin=location_x;
        params.topMargin=location_y;

        iv_drag.setLayoutParams(params);


        hide();
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


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initEvent() {
/**
 * 拖拽的事件
 */
        iv_drag.setOnTouchListener(new View.OnTouchListener() {
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

                        int top = iv_drag.getTop() + disY;
                        int left = iv_drag.getLeft() + disX;
                        int bottom = iv_drag.getBottom() + disY;
                        int right = iv_drag.getRight() + disX;

                        //容错
                        if (top < 0 || left < 0 || bottom > (mScreen_height - 32) || right >
                                mScreen_width) {
                            return true;
                        }

                        //展示
                        iv_drag.layout(left, top, right, bottom);
                        hide();

                        //重新赋值起始坐标
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();

                        break;


                    case MotionEvent.ACTION_UP:
                        SpUtils.putInt(mc,ConstantValues.LOCATION_X,iv_drag.getLeft());
                        SpUtils.putInt(mc,ConstantValues.LOCATION_Y,iv_drag.getTop());
                        break;

                }


                //既要响应点击事件,又要响应拖拽过程,则此返回值结果需要修改为false
                return false;
            }
        });


        /**
         * 双击居中的事件
         */
        iv_drag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.arraycopy(click,1,click,0,click.length-1);
                click[click.length-1] = SystemClock.uptimeMillis();
                if(click[click.length-1]-click[0]<1000){
                    int width = iv_drag.getWidth();
                    int height = iv_drag.getHeight();
                    iv_drag.layout((mScreen_width-width)/2, (mScreen_height-height)/2, (mScreen_width+width)/2, (mScreen_height+height)/2);
                    hide();

                    SpUtils.putInt(mc,ConstantValues.LOCATION_X,iv_drag.getLeft());
                    SpUtils.putInt(mc,ConstantValues.LOCATION_Y,iv_drag.getTop());

                }
            }
        });

    }


    /**
     * 隐藏或显示上下的按钮
     */
    private void hide() {
        int top = iv_drag.getTop();
        if (top > (mScreen_height / 2)) {
            bt_top.setVisibility(View.VISIBLE);
            bt_button.setVisibility(View.INVISIBLE);
        } else {
            bt_top.setVisibility(View.INVISIBLE);
            bt_button.setVisibility(View.VISIBLE);
        }

    }
}
