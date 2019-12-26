package com.tangqi;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SubmitToServer extends AppCompatActivity implements View.OnClickListener {

    private EditText et_username;
    private EditText et_password;
    private Button bt_login;
    private CheckBox cb_remember;
    private Context mc;
    private String mPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        /**
         * 1、找到各个控件，给按钮绑定监听器。
         * 2、点击后，获取输入的用户名和密码，拼成URL，开启子线程；
         * 3、在子线程中通过URL获得HttpUrlConnection，并设置一下，获得返回码
         * 4、判断返回码，=200就显示服务器返回的文字（runonuithread）。否则提示连接失败。
         */

        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        bt_login = (Button) findViewById(R.id.bt_login);
        cb_remember = (CheckBox) findViewById(R.id.cb_remember);
        mc = this;

        bt_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //用开源项目asyncHttpClient提交数据
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        //GET方式
        try {
            mPath = "http://192.168.1.102:8080/WebServer/servlet/LoginServlet?username=" +
                    URLEncoder.encode(username,"utf-8") + "&password=" + URLEncoder.encode(password,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        AsyncHttpClient client= new AsyncHttpClient();
        client.get(mPath, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    Toast.makeText(mc,"成功！"+new String(responseBody,"gbk"),Toast.LENGTH_SHORT)
                                .show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                try {
                    Toast.makeText(mc,"失败！"+new String(responseBody,"gbk"),Toast.LENGTH_SHORT)
                                .show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

//        用HttpUrlConnection提交
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//
//
//                try {
//                    String username = et_username.getText().toString().trim();
//                    String password = et_password.getText().toString().trim();
//                    String data = "username=" + username + "&password=" + password;
//
//                    //用GET方式
//                    URL url = new URL
//                            ("http://192.168.1.102:8080/WebServer/servlet/LoginServlet" +
//                                    "?username=" + username + "&password=" +
//                                    password);
//                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    conn.setRequestMethod("GET");
//                    conn.setConnectTimeout(5000);
//                    int responseCode = conn.getResponseCode();
//
//                    //用POST方式
//                    URL url = new URL
//                            ("http://192.168.1.102:8080/WebServer/servlet/LoginServlet");
//                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    conn.setRequestMethod("POST");
//                    conn.setConnectTimeout(5000);
//                    conn.setRequestProperty("Content-Length", data.length() + "");
//                    conn.setRequestProperty("Content-Type",
//                            "application/x-www-form-urlencoded");
//                    conn.setDoOutput(true);
//                    conn.getOutputStream().write(data.getBytes());
//                    int responseCode = conn.getResponseCode();
//
//
//                    if (responseCode == 200) {
//                        InputStream is = conn.getInputStream();
//                        final String content = UtilStream2String.Stream2String(is);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(mc, content, Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }.start();

    }
}
