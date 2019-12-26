package com.tangqi.safecenter.receiver;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.tangqi.safecenter.utils.ConstantValues;
import com.tangqi.safecenter.utils.SpUtils;

public class UnlockReceiver extends BroadcastReceiver {
    private String TAG = "Mylog";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive: 锁屏打开的广播");
        String saved_sim = SpUtils.getString(context, ConstantValues.SIM_NUMBER, "0");
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);


        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) !=
                PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            Log.i(TAG, "onReceive: 没有权限");
            return;
        }
        String present_sim = tm.getSimSerialNumber()+"123";
        if(!saved_sim.equals(present_sim)){
            SmsManager sm = SmsManager.getDefault();
            //这里还可以即时把地理位置发给安全手机哦~
            sm.sendTextMessage(SpUtils.getString(context,ConstantValues.SAFE_TEL,""),null,"sim change!",null,null);
            Log.i(TAG, "onReceive: 发送了提示SIM卡变更的短信");
        }
    }
}
