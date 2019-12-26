package com.tangqi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Log_in extends AppCompatActivity {

    private EditText et_username;
    private EditText et_password;
    private CheckBox cb_remember;
    private Button bt_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        cb_remember = (CheckBox) findViewById(R.id.cb_remember);
        bt_login = (Button) findViewById(R.id.bt_login);

        //读取数值
        File file = new File(this.getFilesDir(), "info.txt");
        try {
            if (file.exists() && file.length() > 0) {
                //如果文件存在且有内容
                FileInputStream fis = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String s = br.readLine();
                String username = s.split("##")[0];
                String password = s.split("##")[1];
                String flag = s.split("##")[2];
                if (flag.equals("1")) {
                    cb_remember.setChecked(true);
                    et_username.setText(username);
                    et_password.setText(password);
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                String flag = "0";
                if (cb_remember.isChecked()) {
                    flag = "1";
                }

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Log_in.this, "用户名和密码不能为空！", Toast.LENGTH_SHORT).show();
                } else if (username.equals("10000") && password.equals("123456")) {
                    Toast.makeText(Log_in.this, "登录成功！", Toast.LENGTH_SHORT).show();

                    try {
                        File file = new File(Log_in.this.getFilesDir(), "info.txt");
                        FileOutputStream fos = new FileOutputStream(file);

                        fos.write((username + "##" + password + "##" + flag).getBytes());
                        fos.close();
                        Toast.makeText(Log_in.this, "保存密码成功", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    //记住密码


                } else {
                    Toast.makeText(Log_in.this, "用户名或密码错误，请重试", Toast.LENGTH_SHORT).show();
                    et_password.setText("");
                }


            }
        });

    }


}
