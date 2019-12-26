package com.tangqi.safecenter.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.tangqi.safecenter.receiver.SmsReceiver;
import com.tangqi.safecenter.receiver.UnlockReceiver;

public class AgainstTheftService extends Service {

    private UnlockReceiver ur;
    private SmsReceiver sr;

    public AgainstTheftService() {

    }

    @Override
    public void onCreate() {

    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        super.onCreate();
        //动态注册UnlockReceiver
        ur = new UnlockReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("android.intent.action.SCREEN_ON");
        registerReceiver(ur,filter);

        //动态注册SmsReceiver
        sr = new SmsReceiver();
        IntentFilter filter_s=new IntentFilter();
        filter_s.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(sr,filter_s);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unregisterReceiver(ur);
        unregisterReceiver(sr);



    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
