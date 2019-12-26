package com.tangqi.safecenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tangqi.safecenter.R;
import com.tangqi.safecenter.view.SettingItemArrow;

public class AdvancedToolsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_tools);

        SettingItemArrow query_number_belongs = (SettingItemArrow) findViewById(R.id.query_number_belongs);

    }

    //归属地查询
    public void query_number_belongs(View view) {
        Intent intent = new Intent (this,QueryNumberBelongsActivity.class);
        startActivity(intent);
    }

    //加速小火箭
    public void rocket(View view) {
        Intent intent = new Intent (this,RocketActivity.class);
        startActivity(intent);
    }
}
