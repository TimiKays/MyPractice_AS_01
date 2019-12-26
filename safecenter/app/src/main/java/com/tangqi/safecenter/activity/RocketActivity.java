package com.tangqi.safecenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.tangqi.safecenter.R;
import com.tangqi.safecenter.service.RocketService;
import com.tangqi.safecenter.utils.ServiceUtil;

public class RocketActivity extends AppCompatActivity {

    private Intent intent;
    private static final String TAG = "Mylog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rocket);
    }


    public void start_rocket(View view) {
        intent = new Intent(this, RocketService.class);
        startService(intent);
        Log.i(TAG, "start_rocket: 小火箭开启了");
        
    }



    public void stop_rocket(View view) {
        if(ServiceUtil.isRunning(this,"com.tangqi.safecenter.service.RocketService")){
            stopService(intent);
            Log.i(TAG, "start_rocket: 小火箭关闭了");
        }

    }
}
