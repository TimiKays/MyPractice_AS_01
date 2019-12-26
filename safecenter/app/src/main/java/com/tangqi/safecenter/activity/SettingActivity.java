package com.tangqi.safecenter.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.tangqi.safecenter.R;
import com.tangqi.safecenter.service.BelongsService;
import com.tangqi.safecenter.service.BlackNumberService;
import com.tangqi.safecenter.utils.ConstantValues;
import com.tangqi.safecenter.utils.ServiceUtil;
import com.tangqi.safecenter.utils.SpUtils;
import com.tangqi.safecenter.view.SettingItemArrow;
import com.tangqi.safecenter.view.SettingItemCb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SettingActivity extends BaseActivity {
    private static final String TAG = "Mylog";
    private Context mc;
    private String[] colorNames = new String[]{"透明", "橙色", "蓝色", "灰色", "绿色"};
    private SettingItemArrow si_location_style;
    private SettingItemCb si_number_location;


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

    /**
     * 设置黑名单
     */
    private void initBlackList() {
        final SettingItemCb si_black_list = (SettingItemCb) findViewById(R.id.black_list);
        si_black_list.setItem(ConstantValues.BLACK_LIST);
        boolean running = ServiceUtil.isRunning(this, "com.tangqi.safecenter.service.BlackNumberService");
        si_black_list.setChecked(running);
        si_black_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isCheck = si_black_list.isChecked();
                si_black_list.setChecked(!isCheck);
                if(isCheck){
                    stopService(new Intent(mc,BlackNumberService.class));
                }else{
                    startService(new Intent (mc,BlackNumberService.class));
                }

            }
        });
    }


    /**
     * 设置来电显示的位置
     */
    private void initLocationLocation() {
        SettingItemArrow si_location_location = (SettingItemArrow) findViewById(R.id
                .location_location);
        si_location_location.setTitle(ConstantValues.LOCATION_LOCATION);
        si_location_location.setDes("设置"+ConstantValues.LOCATION_LOCATION);

        si_location_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,LocationLocationActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 设置归属地显示风格
     */
    private void initLocationStyle() {
        //初始化
        si_location_style = (SettingItemArrow) findViewById(R.id.location_style);
        si_location_style.setTitle(ConstantValues.LOCATION_STYLE);
        si_location_style.setDes(colorNames[SpUtils.getInt(this, ConstantValues.LOCATION_STYLE,
                0)]);

//      设置点击事件
        si_location_style.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击后出现单选对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(mc);
                builder.setTitle("选择背景颜色");
                builder.setIcon(R.mipmap.ic_launcher);

                int defColor = SpUtils.getInt(mc, ConstantValues.LOCATION_STYLE, 0);
                builder.setSingleChoiceItems(colorNames, defColor, new DialogInterface
                        .OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SpUtils.putInt(mc, ConstantValues.LOCATION_STYLE, which);
                        si_location_style.setDes(colorNames[which]);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

            }
        });
    }

    /**
     * 归属地显示开关
     */
    private void initNumberLocation() {
        si_number_location = (SettingItemCb) findViewById(R.id.number_location);
        si_number_location.setItem(ConstantValues.NUMBER_LOCATION);
        boolean running = ServiceUtil.isRunning(this, "com.tangqi.safecenter.service.BelongsService");
        si_number_location.setChecked(running);


        si_number_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                si_number_location.toggle();
                Intent intent = new Intent(mc, BelongsService.class);
                if (si_number_location.isChecked()) {
                    //开启了，就存一下状态，开启服务
//                    SpUtils.putState(mContext, ConstantValues.NUMBER_LOCATION, true);
                    //在窗体上挂在一个view(权限)
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (!Settings.canDrawOverlays(mc)) {
                            Intent request = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                            request.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(request);
                        } //else {
//                            mWm.addView(mViewToast, params);
//                        }
                    }
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

    @Override
    protected void onResume() {
        super.onResume();

        si_location_style.setTitle(ConstantValues.LOCATION_STYLE);
        boolean running = ServiceUtil.isRunning(this, "com.tangqi.safecenter.service" + "" + "" +
                ".BelongsService");
        si_number_location.setChecked(running);
    }
}
