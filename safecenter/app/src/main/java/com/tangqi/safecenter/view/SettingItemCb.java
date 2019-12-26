package com.tangqi.safecenter.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tangqi.safecenter.R;
import com.tangqi.safecenter.utils.ConstantValues;
import com.tangqi.safecenter.utils.Md5Util;
import com.tangqi.safecenter.utils.SpUtils;
import com.tangqi.safecenter.utils.ToastUtil;

/**
 * Setting页面的item自定义组合对象。
 *
 * @author Admin
 * @version $Rev$
 */
public class SettingItemCb extends RelativeLayout implements Checkable, View.OnClickListener {
    private final String TAG = "Mylog";
    private TextView item_name;
    private TextView item_state;
    private CheckBox mCb_update;
    private Context mContext;
    private String item_title;
    private String selectedText;
    private String normalText;
    private String NAMESPACE = "http://schemas.android.com/apk/res/com.tangqi.safecenter";

    public SettingItemCb(Context context) {
        this(context, null);
        Log.i(TAG, "SettingItem: 第一个构造");
    }

    public SettingItemCb(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        Log.i(TAG, "SettingItem: 第二个构造");
    }

    public SettingItemCb(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        Log.i(TAG, "SettingItem: 第三个构造");
        View settingItem = View.inflate(context, R.layout.item_setting, this);
        Log.i(TAG, "SettingItem: 已添加view到自定义空间组合：" + R.layout.item_setting);

        item_name = (TextView) findViewById(R.id.tv_arrow_title);
        item_state = (TextView) findViewById(R.id.tv_arrow_describe);
        mCb_update = (CheckBox) findViewById(R.id.cb_update);

//这些是要自定义属性才要的，现在没做
//      String title = attrs.getAttributeValue(NAMESPACE, "title");
//        String on_describe = attrs.getAttributeValue(NAMESPACE, "on_describe");
//        String off_describe = attrs.getAttributeValue(NAMESPACE, "off_describe");
//
//        Log.i(TAG, "初始化选框的" + title);
//        setItems(title, on_describe, off_describe);

        this.setOnClickListener(this);


//        //cb状态切换时，设置条目状态与其一致。
//        mCb_update.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Log.i(TAG, "onCheckedChanged: 状态改变了：" + isChecked);
//                setChecked(isChecked);
//            }
//        });


    }

    //设置Item名字，调用后直接获取对应item的状态并设置。
    public void setItem(String text) {
        this.setItems(text, "已开启", "已关闭");
    }

    public void setItems(String title, String selectedText, String normalText) {

        item_name.setText(title);
        item_state.setText(normalText);

        this.selectedText = selectedText;
        this.normalText = normalText;

        item_title = title;
        Log.i(TAG, "");
        boolean pre_state = SpUtils.getState(mContext, title, false);
        setChecked(pre_state);


    }


    //定义条目选中状态是什么样的
    @Override
    public boolean isChecked() {
        CharSequence state = item_state.getText();
        Boolean isChecked = state.equals(selectedText);
        return isChecked;
    }

    //设置条目是否为选中状态
    @Override
    public void setChecked(boolean checked) {
        if (checked) {
            item_state.setText(selectedText);
        } else {
            item_state.setText(normalText);
        }
        mCb_update.setChecked(checked);
        SpUtils.putState(mContext, item_title, checked);
        Log.i(TAG, "key:" + item_title + ",value:" + checked);
    }


    @Override
    public void toggle() {
        setChecked(!isChecked());
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick: 点击了" + item_title);
        switch (item_title) {
            case ConstantValues.AUTO_UPDATE:

                SpUtils.putState(mContext,ConstantValues.AUTO_UPDATE,!isChecked());
                toggle();
                break;
            //程序锁
            case ConstantValues.APP_LOCK:
                if (!isChecked()) {
                    //就弹出对话框设置密码
                    show2EtDialog("设置新密码");
                } else {
                    showEtDialog("取消密码");
                }

                break;
            //绑定SIM卡
            case ConstantValues.BIND_SIM:
                toggle();
                if (isChecked()) {
                    //绑定了，就保存SIM卡号
                    TelephonyManager manager = (TelephonyManager) mContext.getSystemService
                            (Context.TELEPHONY_SERVICE);
                    @SuppressLint("MissingPermission") String simNumber = manager
                            .getSimSerialNumber();
                    SpUtils.putString(mContext, ConstantValues.SIM_NUMBER, simNumber, false);
                } else {
                    //没绑定了，就删除sim卡号一项
                    SpUtils.removeString(mContext, ConstantValues.SIM_NUMBER);

                }
                break;
            //电话归属地显示
            case ConstantValues.NUMBER_LOCATION:


                break;

        }

    }

    /**
     * 确认取消的单个ET的对话框。
     *
     * @param title
     */
    public void showEtDialog(String title) {

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
        et_new.setHint("请输入密码以取消");

        tv_title.setText(title);

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


                    ToastUtil.show(mContext, "密码已取消");

                    dialog.dismiss();
                    SpUtils.removeString(mContext, ConstantValues.APP_LOCK_PASSWORD);

//                  新密码设置成功，或密码取消。
                    setChecked(!isChecked());

                } else {
                    ToastUtil.show(mContext, "密码错误，请重试");
                    et_new.setText("");
                }
            }
        });
    }


    /**
     * 初始化密码的两个ET的对话框
     *
     * @param title
     */
    public void show2EtDialog(String title) {
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

        tv_title.setText(title);

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
                        SpUtils.putString(mContext, ConstantValues.APP_LOCK_PASSWORD,
                                new_password, true);
                        ToastUtil.show(mContext, "密码设置成功！");
                        dialog.dismiss();

//                      新密码设置成功，或密码取消。
                        setChecked(!isChecked());

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
}
