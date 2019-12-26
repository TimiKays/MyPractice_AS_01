package com.tangqi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class WishActivity extends AppCompatActivity {

    private EditText et_number;
    private EditText et_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish);
        et_content=(EditText) findViewById(R.id.sms_content);
        et_number=(EditText) findViewById(R.id.sms_numbers);

    }



    //添加号码
    public void add_number(View view) {
        Intent intent = new Intent(this,WishNumberActivity.class);
        startActivityForResult(intent,1);
    }

    //添加常用语
    public void add_content(View view) {
        Intent intent = new Intent(this,WishContentActivity.class);
        startActivityForResult(intent,2);
    }

    //点击发送短信
    public void send(View view) {
        String number = et_number.getText().toString().trim();
        String content = et_content.getText().toString().trim();
        SmsManager smsManager=SmsManager.getDefault();//获取短信管理器实例
        ArrayList<String> divided = smsManager.divideMessage(content);
        for(String div :divided){
            smsManager.sendTextMessage(number,null,div,null,null);
            Toast.makeText(this, "短信发送完成", Toast.LENGTH_SHORT).show();
        }

    }


    //处理回来的数据吖
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1){
            String number=data.getStringExtra("number");
            et_number.setText(number);
        }else if(resultCode==2){
            String content=data.getStringExtra("content");
            et_content.setText(content);

        }


    }


}
