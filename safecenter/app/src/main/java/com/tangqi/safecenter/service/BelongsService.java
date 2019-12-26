package com.tangqi.safecenter.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.tangqi.safecenter.R;
import com.tangqi.safecenter.engine.QueryNumberDao;
import com.tangqi.safecenter.utils.ConstantValues;
import com.tangqi.safecenter.utils.SpUtils;

public class BelongsService extends Service {

    private static final String TAG = "Mylog";
    private static View mViewToast;
    private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
    private TelephonyManager mTm;
    private MyPhoneListener mMyListener;
    private Context mc;
    private WindowManager mWm;
    private String mBelongs;
    private int[] mDrawableIds;
    private WindowManager mwm;
    private int mScreen_height;
    private int mScreen_width;
    private InnerCallReceiver mInner;

    public BelongsService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mwm = (WindowManager) getSystemService(WINDOW_SERVICE);
        mScreen_height = mwm.getDefaultDisplay().getHeight();
        mScreen_width = mwm.getDefaultDisplay().getWidth();

        mc = this;
        Log.i(TAG, "onClick: 电话归属的服务开启了");
        mTm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        mMyListener = new MyPhoneListener();
        mTm.listen(mMyListener, PhoneStateListener.LISTEN_CALL_STATE);
        mWm = (WindowManager) getSystemService(WINDOW_SERVICE);


        IntentFilter intentFilter= new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
        mInner = new InnerCallReceiver();
        //注册，记得注销
        registerReceiver(mInner,intentFilter);

    }

    class InnerCallReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            //拨出电话后要显示吐司
            String outNumber=getResultData();
            String outBelongs=QueryNumberDao.queryNumber(outNumber);
            showPhoneBelongs(outBelongs);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTm != null && mMyListener != null) {
            mTm.listen(mMyListener, PhoneStateListener.LISTEN_NONE);
            Log.i(TAG, "onClick: 电话归属的服务关闭了");
        }
        if(mInner!=null){
            unregisterReceiver(mInner);
        }
    }

    public void showPhoneBelongs(String belongs) {
        final WindowManager.LayoutParams params = mParams;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE	默认能够被触摸
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        params.format = PixelFormat.TRANSLUCENT;
        //在响铃的时候显示吐司,和电话类型一致
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.setTitle("Toast");

        //指定吐司的所在位置
        params.gravity = Gravity.LEFT+Gravity.TOP;
        int location_x = SpUtils.getInt(mc, ConstantValues.LOCATION_X, 0);
        int location_y = SpUtils.getInt(mc, ConstantValues.LOCATION_Y, 0);

        params.x = location_x;
        params.y = location_y;

        //吐司显示效果(吐司布局文件),xml-->view(吐司),将吐司挂在到windowManager窗体上
        mViewToast = View.inflate(mc, R.layout.toast_phone_belongs, null);
        final TextView tv_toast = (TextView) mViewToast.findViewById(R.id.tv_toast);
        tv_toast.setText(belongs);


//        //从sp中获取色值文字的索引,匹配图片,用作展示
        mDrawableIds = new int[]{R.drawable.call_locate_white, R.drawable.call_locate_orange, R
                .drawable.call_locate_blue, R.drawable.call_locate_gray, R.drawable
                .call_locate_green};
        int toastStyleIndex = SpUtils.getInt(getApplicationContext(), ConstantValues
                .LOCATION_STYLE, 0);
        tv_toast.setBackgroundResource(mDrawableIds[toastStyleIndex]);


        /**
         * 拖拽事件
         */
        mViewToast.setOnTouchListener(new View.OnTouchListener() {
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
                        params.x =params.x + disX;

                        //容错
//                        if (params.y < 0 || params.x < 0 || params.y > (mScreen_height-mViewToast.getHeight() - 32) || params.x >
//                                mScreen_width*-mViewToast.getWidth()) {
//                            return true;
//                        }
                        //容错处理
                        if(params.x<0){
                            params.x = 0;
                        }

                        if(params.y<0){
                            params.y=0;
                        }

                        if(params.x>mScreen_width-mViewToast.getWidth()){
                            params.x = mScreen_width-mViewToast.getWidth();
                        }

                        if(params.y>mScreen_height-mViewToast.getHeight()-22){
                            params.y = mScreen_height-mViewToast.getHeight()-22;
                        }

                        //展示
                        mwm.updateViewLayout(mViewToast,params);

                        //重新赋值起始坐标
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();

                        break;


                    case MotionEvent.ACTION_UP:
                        SpUtils.putInt(mc,ConstantValues.LOCATION_X,params.x);
                        SpUtils.putInt(mc,ConstantValues.LOCATION_Y,params.y);
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
                mWm.addView(mViewToast, params);
            }
        }




    }


    class MyPhoneListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING://响铃状态
                    mBelongs = QueryNumberDao.queryNumber(incomingNumber);
//                    ToastUtil.show(getApplicationContext(),belongs);
                    showPhoneBelongs(mBelongs);
                    break;
                case TelephonyManager.CALL_STATE_IDLE://空闲状态
                    //空闲状态,没有任何活动(移除吐司)
                    Log.i(TAG, "挂断电话,空闲了.......................");
                    //挂断电话的时候窗体需要移除吐司
                    if (mWm != null && mViewToast != null) {
                        mWm.removeView(mViewToast);
                    }
                    break;

            }

        }
    }
}
