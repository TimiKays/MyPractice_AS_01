package com.tangqi.safecenter.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.tangqi.safecenter.R;
import com.tangqi.safecenter.service.BelongsService;
import com.tangqi.safecenter.utils.ConstantValues;
import com.tangqi.safecenter.utils.ServiceUtil;
import com.tangqi.safecenter.view.SettingItemArrow;
import com.tangqi.safecenter.view.SettingItemCb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SettingActivity extends BaseActivity {
    private static final String TAG = "Mylog";
    private Context mc;

    @Override
    public void initView() {
        setContentView(R.layout.activity_setting);
        Log.i(TAG, "onCreate:");
        mc = this;
        initUpdate();
        initNumberLocation();
        initLocationStyle();
        initLocationLocation();
        initBlackList();
        intiAppLock();
    }

    @Override
    public void showNextView() {

    }

    @Override
    public void showPrevious() {

    }

    @Override
    public void initData() {
        initDataBase("address.db");
    }

    @Override
    public void initEvent() {

    }


    private void intiAppLock() {
        SettingItemCb si_app_lock = (SettingItemCb) findViewById(R.id.app_lock);
        si_app_lock.setItem(ConstantValues.APP_LOCK);


    }

    private void initBlackList() {
        SettingItemCb si_black_list = (SettingItemCb) findViewById(R.id.black_list);
        si_black_list.setItem(ConstantValues.BLACK_LIST);
    }


    private void initLocationLocation() {
        SettingItemArrow si_location_location = (SettingItemArrow) findViewById(R.id
                .location_location);
        si_location_location.setItem(ConstantValues.LOCATION_LOCATION);
    }

    private void initLocationStyle() {
        SettingItemArrow si_location_style = (SettingItemArrow) findViewById(R.id.location_style);
        si_location_style.setItem(ConstantValues.LOCATION_STYLE);
    }


    private void initNumberLocation() {
        final SettingItemCb si_number_location = (SettingItemCb) findViewById(R.id.number_location);
        si_number_location.setItem(ConstantValues.NUMBER_LOCATION);
        boolean running = ServiceUtil.isRunning(this, "com.tangqi.safecenter.service" +
                ".BelongsService");
        si_number_location.setChecked(running);


        si_number_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                si_number_location.toggle();
                Intent intent = new Intent(mc, BelongsService.class);
                if (si_number_location.isChecked()) {
                    //开启了，就存一下状态，开启服务
//                    SpUtils.putState(mContext, ConstantValues.NUMBER_LOCATION, true);
                    mc.startService(intent);


                } else {
                    //关闭了，就更新一下状态，关闭服务
//                    SpUtils.putState(mContext, ConstantValues.NUMBER_LOCATION, false);
                    if (ServiceUtil.isRunning(mc, "com.tangqi.safecenter.service.BelongsService")) {
                        stopService(intent);
                    }

                }
            }
        });
    }

    private void initUpdate() {
        SettingItemCb si_update = (SettingItemCb) findViewById(R.id.si_update);
        si_update.setItem(ConstantValues.AUTO_UPDATE);


    }

    private void initDataBase(String dbName) {
        /*
        * 1、获取输入流：getAssets().open(dbName)
        * 2、获取输出流：通过file对象
        * 3、读入的同时写出到file文件
        * 4、最后，关闭流
        * */
        FileOutputStream fos = null;
        InputStream dbFile = null;
        try {
            dbFile = getAssets().open(dbName);
            File dir = getFilesDir();
           File mFile = new File(dir, dbName);
            if (mFile.exists()) {
                return;
            }

            fos = new FileOutputStream(mFile);
            byte[] bys = new byte[1024];
            int len = -1;
            while ((len = dbFile.read(bys)) != -1) {
                fos.write(bys);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (dbFile != null) {
                    dbFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
