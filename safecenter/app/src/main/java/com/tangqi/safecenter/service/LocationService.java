package com.tangqi.safecenter.service;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.util.Log;

import com.tangqi.safecenter.utils.ConstantValues;
import com.tangqi.safecenter.utils.SpUtils;

public class LocationService extends Service {
    private String TAG = "Mylog";

    public LocationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: 位置服务已开启");
        //获取位置，并发送给安全号码
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //优化：以最优的方式获取经纬度坐标
        Criteria criteria = new Criteria();
        criteria.setCostAllowed(true);//允许花费
        criteria.setAccuracy(Criteria.ACCURACY_FINE);//要求的精确度
        String bestProvider = lm.getBestProvider(criteria, true);

        //判断是否有权限，没有的话就直接返回。额，不是我要加的，是studio要加的。
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[]
            // permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the
            // documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        MyLocationListener myLocationListener = new MyLocationListener();
        //把之前最优的bestProvider拿过来
        lm.requestLocationUpdates(bestProvider, 0, 0, myLocationListener);

    }

    class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            //位置发生变化
            double longitude = location.getLongitude();//经度
            double latitude = location.getLatitude();//纬度
            String where = "longitude:" + longitude + ",latitude:" + latitude;
            Log.i(TAG, where);

            //转为中国坐标，感觉转的不对，不用了先。
//            String[] real_location = ModifyGps.s2c(longitude, latitude);
//            String real_longtitude =real_location[0];
//            String real_latitude=real_location[1];
//
//            where = "reallongitude:" + real_longtitude + ",reallatitude:" + real_latitude;
//            Log.i(TAG, where);

            //发短信，模拟器不支持中文短信，会乱码
            SmsManager sm = SmsManager.getDefault();
            sm.sendTextMessage(SpUtils.getString(getApplicationContext(), ConstantValues
                    .SAFE_TEL, ""), null, where, null, null);
            Log.i(TAG, "onLocationChanged: 发送了位置变更的短信");
            //关闭自己?
//            stopSelf();
//            Log.i(TAG, "onLocationChanged: 服务关闭了自己");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            //状态发生变化

        }

        @Override
        public void onProviderEnabled(String provider) {
            //GPS可用

        }

        @Override
        public void onProviderDisabled(String provider) {
            //GPS不可以

        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
