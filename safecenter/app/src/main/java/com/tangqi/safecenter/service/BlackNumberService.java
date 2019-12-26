package com.tangqi.safecenter.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;
import com.tangqi.safecenter.db.dao.BlackNumberDao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BlackNumberService extends Service {

    private BlackNumberReceiver mBlackNumberReceiver;
    private static final String TAG = "BlackNumberService";
    private TelephonyManager mTM;
    private MyPhoneStateListener mPhoneStateListener;
    private BlackNumberDao mBlackNumberDao;

    public BlackNumberService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mBlackNumberDao = BlackNumberDao.getInstance(getApplicationContext());
        mBlackNumberReceiver = new BlackNumberReceiver();
        IntentFilter intentFilter=new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        intentFilter.setPriority(1000);
        registerReceiver(mBlackNumberReceiver,intentFilter);

        mTM = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mPhoneStateListener = new MyPhoneStateListener();
        mTM.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mBlackNumberReceiver!=null){
            unregisterReceiver(mBlackNumberReceiver);

        }
    }




    //内部类，广播接受者
    private class BlackNumberReceiver extends BroadcastReceiver {



        //接收到短信后
        @Override
        public void onReceive(Context context, Intent intent) {
            Object[] objects = (Object[]) intent.getExtras().get("pdus");
            for (Object ob : objects) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) ob);
                String address = smsMessage.getOriginatingAddress();

                int mode = mBlackNumberDao.getMode(address);
                if (mode==0||mode==2){
                    //拦截短信
                    Log.d(TAG,"拦截短信："+address);
                    abortBroadcast();
                }



            }

        }
    }

    private class MyPhoneStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    endCall(incomingNumber);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }

        private void endCall(String phone) {
//            ITelephony.Stub.asInterface(ServiceManager.getService("iphonesubinfo"));
            int mode=mBlackNumberDao.getMode(phone);
            if(mode==1||mode==2){
                //阻止来电
                try {
                    Log.d(TAG,"拦截电话："+phone);
                    Class<?> clazz = Class.forName("android.os.ServiceManager");
                    Method method = clazz.getMethod("getService", String.class);
                    IBinder iBinder = (IBinder) method.invoke(null, Context.TELEPHONY_SERVICE);
                    ITelephony iTelephony = ITelephony.Stub.asInterface(iBinder);
                    iTelephony.endCall();

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }


            }
        }
    }
}
