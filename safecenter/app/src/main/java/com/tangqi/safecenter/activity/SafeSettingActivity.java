package com.tangqi.safecenter.activity;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.tangqi.safecenter.R;
import com.tangqi.safecenter.receiver.MyDeviceAdminReceiver;
import com.tangqi.safecenter.utils.ConstantValues;
import com.tangqi.safecenter.utils.SpUtils;
import com.tangqi.safecenter.utils.ToastUtil;
import com.tangqi.safecenter.view.SettingItemArrow;
import com.tangqi.safecenter.view.SettingItemCb;

public class SafeSettingActivity extends BaseActivity {

    private static final String TAG = "Mylog";
    private EditText mEt_safe_tel;
    private ImageView img_sele_tel;
    private Button start_safe;
    private SettingItemArrow start_device_manager;
    private Context mc;

    @Override
    public void initView() {
        setContentView(R.layout.activity_safe_setting);

        mc = this;
        start_device_manager = (SettingItemArrow) findViewById(R.id.start_device_manager);
        SettingItemCb si_bindSIM = (SettingItemCb) findViewById(R.id.si_bindSIM);
        start_safe = (Button) findViewById(R.id.start_safe);
        mEt_safe_tel = (EditText) findViewById(R.id.safe_tel);
        img_sele_tel = (ImageView) findViewById(R.id.img_select_tel);


        si_bindSIM.setItems(ConstantValues.BIND_SIM, "已绑定", "未绑定");
        mEt_safe_tel.setText(SpUtils.getString(this, ConstantValues.SAFE_TEL, ""));
    }

    @Override
    public void showNextView() {

    }

    @Override
    public void showPrevious() {
        goActivity(SafeActivity.class);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {


//      完成设置，打开安全保护
        start_safe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sim_number = SpUtils.getString(SafeSettingActivity.this, ConstantValues
                        .SIM_NUMBER, "");
                String safe_tel = mEt_safe_tel.getText().toString();

                if (!TextUtils.isEmpty(sim_number) && !TextUtils.isEmpty(safe_tel)) {
                    Intent intent = new Intent(SafeSettingActivity.this, SafeActivity.class);

                    SpUtils.putState(SafeSettingActivity.this, ConstantValues.AGAINST_THEFT, true);
                    SpUtils.putString(SafeSettingActivity.this, ConstantValues.SAFE_TEL,
                            safe_tel, false);
                    startActivity(intent);

                    finish();
//                    overridePendingTransition(R.anim.left_slide_in,R.anim.right_slide_out);
                } else {
                    ToastUtil.show(SafeSettingActivity.this, "您没有勾选绑定SIM卡或输入有效的手机号");
                }
            }
        });


//      添加手机号
        img_sele_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent, 1);
            }
        });
    }


    //获取联系人后的回调方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            ContentResolver contentResolver = getContentResolver();
            Uri contactData = data.getData();

            // 获取联系人姓名，id
            Cursor cursor = getContentResolver().query(contactData, null, null, null, null);
            cursor.moveToFirst();
            String username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts
                    .DISPLAY_NAME));
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts
                    ._ID));
            cursor.close();

            // 根据id获取联系人电话
            Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone
                    .CONTENT_URI, null, "_id = " + contactId, null, null, null);
            String usernumber1 = null;
            while (phoneCursor.moveToNext()) {
                usernumber1 = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract
                        .CommonDataKinds.Phone.NUMBER));
            }
            phoneCursor.close();
//            ToastUtil.show(this, username + "----" + usernumber1);
            mEt_safe_tel.setText(usernumber1);

        }
    }

    public void start_device_manager(View view) {
        Log.i(TAG, "start_device_manager: 点击了开启设备管理器");
        ComponentName mDeviceAdminSample = new ComponentName(mc, MyDeviceAdminReceiver.class);
        //记得转成员变量

        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdminSample);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "设备管理器");
        //第二个参数就是个字符串，直接打字就行了，如”设备管理器“
        startActivity(intent);

    }
}
