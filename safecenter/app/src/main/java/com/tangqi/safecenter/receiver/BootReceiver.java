package com.tangqi.safecenter.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tangqi.safecenter.service.AgainstTheftService;
import com.tangqi.safecenter.utils.ConstantValues;
import com.tangqi.safecenter.utils.SpUtils;

/**
 * 用来接收开机的广播
 */
public class BootReceiver extends BroadcastReceiver {
    private String TAG = "Mylog";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive: 接收到了开机的广播");
        //获取文件的防盗状态，为true就开机开启服务
        if(SpUtils.getState(context, ConstantValues.AGAINST_THEFT,false)){
            Intent safeService =new Intent(context,AgainstTheftService.class);
            context.startService(safeService);
            Log.i(TAG, "onReceive: 服务开机自启");

        }



    }
}
