package com.tangqi.safecenter.receiver;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.tangqi.safecenter.R;
import com.tangqi.safecenter.service.LocationService;
import com.tangqi.safecenter.utils.ConstantValues;
import com.tangqi.safecenter.utils.SpUtils;

public class SmsReceiver extends BroadcastReceiver {
    private String TAG = "Mylog";
    private MediaPlayer player;

    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.i(TAG, "onReceive: 收到短信的广播");
//获取短信内容
        Object[] objects = (Object[]) intent.getExtras().get("pdus");
        for (Object ob : objects) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) ob);
            String body = smsMessage.getMessageBody();
            String address = smsMessage.getOriginatingAddress();
            Log.i(TAG, "短信内容：" + body + "，来自：" + address);
            if (body.contains("#*alarm*#")) {
                //开始播放音乐
                player = MediaPlayer.create(context, R.raw.national_song);
                player.setLooping(true);
                player.start();

            } else if (body.contains("#*stopalarm*#")) {
//              停止音乐播放
                player.stop();

            } else if (body.contains("#*location*#")) {
                //开启一个服务LocationService
                context.startService(new Intent(context, LocationService.class));


            } else {

                //获取设备的管理者对象
                ComponentName mDeviceAdminSample = new ComponentName(context,
                        MyDeviceAdminReceiver.class);
                DevicePolicyManager mDPM = (DevicePolicyManager) context.getSystemService(Context
                        .DEVICE_POLICY_SERVICE);

                if (mDPM.isAdminActive(mDeviceAdminSample)) {
                    if (body.contains("#*lockscreen*#")) {
                        //锁屏
                        mDPM.lockNow();
                        //设置密码
                        mDPM.resetPassword("123456", 0);
                    } else if (body.contains("#*wipedata*#")){
                        //清除数据
                        mDPM.wipeData(0);
                    }
                } else {
                    //没有激活
                    //发短信，模拟器不支持中文短信，会乱码
                    SmsManager sm = SmsManager.getDefault();
                    sm.sendTextMessage(SpUtils.getString(context, ConstantValues
                            .SAFE_TEL, ""), null, "Your device manager is closed!", null, null);
                    Log.i(TAG, "onLocationChanged: 发送了位置变更的短信");
                }

            }
        }

    }


}
