package com.tangqi.safecenter.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.tangqi.safecenter.R;
import com.tangqi.safecenter.service.AgainstTheftService;
import com.tangqi.safecenter.utils.ConstantValues;
import com.tangqi.safecenter.utils.ServiceUtil;
import com.tangqi.safecenter.utils.SpUtils;
import com.tangqi.safecenter.utils.SubViewsControlUtil;
import com.tangqi.safecenter.view.SettingItemArrow;
import com.tangqi.safecenter.view.SettingItemCb;

public class SafeActivity extends BaseActivity {

    private SettingItemCb mSi_safe_toggle;
    private SettingItemArrow mSi_change_safe_tel;
    private LinearLayout mLl_safe_items;
    private boolean against_theft;
    private Intent safeService;
    private static final String TAG = "Mylog";


    @Override
    public void initView() {
        setContentView(R.layout.activity_safe);
        against_theft = SpUtils.getState(this, ConstantValues.AGAINST_THEFT, false);

        mSi_safe_toggle = (SettingItemCb) findViewById(R.id.safe_toggle);
        mSi_change_safe_tel = (SettingItemArrow) findViewById(R.id.change_safe_tel);
        mLl_safe_items = (LinearLayout) findViewById(R.id.ll_safe_items);



    }

    @Override
    public void showNextView() {

    }

    @Override
    public void showPrevious() {
        //返回主页
        finish();

    }

    @Override
    public void initData() {


    }

    @Override
    protected void onResume() {
        super.onResume();
        safeService = new Intent(this,AgainstTheftService.class);
        boolean running = ServiceUtil.isRunning(this,"com.tangqi.safecenter.service.AgainstTheftService");
        if(SpUtils.getState(this, ConstantValues.AGAINST_THEFT, false) && !running){
            //开启服务

            startService(safeService);
            Log.i(TAG, "initData: 服务被开启");
        }

    }



    @Override
    public void initEvent() {
        mSi_safe_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Toast.makeText(SafeActivity.this,"在代码里设置的点击事件", Toast.LENGTH_SHORT).show();
                boolean state = SpUtils.getState(SafeActivity.this, ConstantValues.AGAINST_THEFT,
                        false);
                if (state) {
                    //如果是选中状态，则关闭手机防盗功能，并取消绑定sim卡
                    SpUtils.putState(SafeActivity.this, ConstantValues.AGAINST_THEFT, false);
                    SpUtils.putState(SafeActivity.this, ConstantValues.BIND_SIM, false);
                    SpUtils.removeString(SafeActivity.this,ConstantValues.SIM_NUMBER);
                    mSi_safe_toggle.setChecked(false);

                    //关闭服务
                    SubViewsControlUtil.setSubViewEnable(mLl_safe_items,false);

                    stopService(safeService);


                    Log.i(TAG, "onClick: 服务被关闭");
                } else {
                    //如果是未选中状态，去重新设置并打开

                    Intent intent = new Intent(SafeActivity.this, SafeSettingActivity.class);
                    startActivity(intent);
//                    finish();
                    overridePendingTransition(R.anim.right_slide_in,R.anim.left_slide_out);


                }

            }
        });

        mSi_change_safe_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SafeActivity.this, SafeSettingActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_slide_in,R.anim.left_slide_out);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        //回显
        mSi_safe_toggle.setItem(ConstantValues.AGAINST_THEFT);
        mSi_change_safe_tel.setTitle(ConstantValues.SAFE_TEL);
        if(!SpUtils.getState(SafeActivity.this, ConstantValues.AGAINST_THEFT, false)){
            SubViewsControlUtil.setSubViewEnable(mLl_safe_items,false);
        }else{
            SubViewsControlUtil.setSubViewEnable(mLl_safe_items,true);
        }

    }
}
