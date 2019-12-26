package com.tangqi.safecenter.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.tangqi.safecenter.R;
import com.tangqi.safecenter.utils.ConstantValues;
import com.tangqi.safecenter.utils.Md5Util;
import com.tangqi.safecenter.utils.PermissionUtils;
import com.tangqi.safecenter.utils.SpUtils;
import com.tangqi.safecenter.utils.ToastUtil;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "Mylog";
    private static final String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CONTACTS, Manifest
            .permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission
            .ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE};
    private String[] gridName = {"手机防盗", "通信卫士", "软件管理", "进程管理", "流量统计", "手机杀毒", "缓存清理", "高级工具",
            "设置中心"};
    private int[] gridPic = {R.drawable.home_safe, R.drawable.home_callmsgsafe, R.drawable
            .home_apps, R.drawable.home_taskmanager, R.drawable.home_netmanager, R.drawable
            .home_trojan, R.drawable.home_sysoptimize, R.drawable.home_tools, R.drawable
            .home_settings};
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mContext = this;

        requestMyPermission();

        GridView gv_function_list = (GridView) findViewById(R.id.gv_function_list);
        gv_function_list.setAdapter(new gvAdapter());
        gv_function_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if(SpUtils.getState(mContext,ConstantValues.APP_LOCK,false)){
                            String password = SpUtils.getString(mContext, ConstantValues
                                    .APP_LOCK_PASSWORD, "");
                            if (password != null && !password.equals("")) {
                                showEtDialog();
                            } else {
                                show2EtDialog();
                            }
                        }else{
                            Intent intent = new Intent(mContext, SafeActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case 1:
                        Intent intent1 =new Intent (mContext,BlackNumberActivity.class);
                        startActivity(intent1);
                        break;

                    case 7:
                        Intent intent7 =new Intent (mContext,AdvancedToolsActivity.class);
                        startActivity(intent7);
                        break;

                    case 8:
                        Intent intent8 = new Intent(HomeActivity.this, SettingActivity.class);
                        startActivity(intent8);
                        break;
                }
            }
        });
    }

    /**
     * 通过工具类请求权限。
     */
    private void requestMyPermission() {
        PermissionUtils.checkAndRequestMorePermissions(this, PERMISSIONS, 2, new PermissionUtils
                .PermissionRequestSuccessCallBack() {
            @Override
            public void onHasPermission() {
                Log.i(TAG, "onHasPermission: 请求权限成功");
            }
        });


    }

    /**
     * 确认进入的密码对话框。
     */
    public void showEtDialog() {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_single_password_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        Button bt_cancel = view.findViewById(R.id.bt_password_cancel);
        Button bt_confirm = view.findViewById(R.id.bt_password_confirm);
        TextView tv_title = view.findViewById(R.id.tv_dialog_title_1et);
        final EditText et_new = view.findViewById(R.id.et_dialog_password);
        et_new.setHint("请输入密码进入手机防盗");

        tv_title.setText("请输入密码");

        //取消按钮的点击事件
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //确认按钮的点击事件
        bt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_password = et_new.getText().toString().trim();
                String real_password = SpUtils.getString(mContext, ConstantValues
                        .APP_LOCK_PASSWORD, "");
                if (new_password != null && Md5Util.encoder(new_password).equals(real_password)) {


                    ToastUtil.show(mContext, "密码正确");

                    dialog.dismiss();

                    Intent intent = new Intent(mContext, SafeActivity.class);
                    startActivity(intent);

                } else {
                    ToastUtil.show(mContext, "密码错误，请重试");
                    et_new.setText("");
                }
            }
        });
    }

    /**
     * 初始化密码的两个ET的对话框。
     */
    public void show2EtDialog() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_init_password_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        Button bt_cancel = view.findViewById(R.id.bt_init_password_cancel);
        Button bt_confirm = view.findViewById(R.id.bt_init_password_confirm);
        TextView tv_title = view.findViewById(R.id.tv_dialog_title_2et);
        final TextView et_new = view.findViewById(R.id.et_dialog_password_new);
        final TextView et_confirm = view.findViewById(R.id.et_dialog_password_confirm);

        tv_title.setText("设置新密码");

        //取消按钮的点击事件
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //确认按钮的点击事件
        bt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_password = et_new.getText().toString().trim();
                String confrim_password = et_confirm.getText().toString().trim();
                if (new_password != null && confrim_password != null) {
                    if (new_password.equals(confrim_password)) {
                        SpUtils.putState(mContext, ConstantValues.APP_LOCK, true);
                        SpUtils.putString(mContext, ConstantValues.APP_LOCK_PASSWORD,
                                new_password, true);
                        ToastUtil.show(mContext, "密码设置成功！");
                        dialog.dismiss();

                        Intent intent = new Intent(mContext, SafeActivity.class);
                        startActivity(intent);


                    } else {
                        ToastUtil.show(mContext, "两次输入的密码不一致");
                        et_new.setText("");
                        et_confirm.setText("");
                        et_new.requestFocus();
                    }
                } else {
                    ToastUtil.show(mContext, "请输入密码");
                }
            }
        });
    }


    private class gvAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return gridName.length;
        }
        @Override
        public Object getItem(int position) {
            return gridName[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.grid_item_home, null);
            ImageView mode_pic = view.findViewById(R.id.mode_pic);
            TextView mode_name = view.findViewById(R.id.mode_name);

            mode_pic.setBackgroundResource(gridPic[position]);
            mode_name.setText(gridName[position]);



            return view;
        }
    }
}
