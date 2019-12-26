package com.tangqi;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tangqi.service.PermissionUtils;

import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class WebImageView extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "test";
    private static final int CONNECT_SUCCEED = 0;
    private static final int CONNECT_FAILED = 1;
    private static final int CONNECT_ERROR = 3;
    private Button bt_look;
    private EditText et_website;
    private ImageView img_showsite;
    private String website;
    private Context mContext;
    private final String[] PERMISSIONS = {Manifest.permission.INTERNET};
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CONNECT_SUCCEED:
                    img_showsite.setImageBitmap((Bitmap)msg.obj);
                    break;
                case CONNECT_FAILED:
                    Toast.makeText(WebImageView.this, "网络连接失败，错误码：" + msg.obj, Toast
                            .LENGTH_SHORT).show();
                    break;
                case CONNECT_ERROR:
                    Toast.makeText(WebImageView.this, "无法连接，请检查输入网址是否正确", Toast
                            .LENGTH_SHORT).show();
                    break;
            }


        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_image_view);

        bt_look = findViewById(R.id.bt_look_img);
        et_website = findViewById(R.id.et_imgsite);
        img_showsite = findViewById(R.id.img_show);

        mContext = this;
        bt_look.setOnClickListener(this);

        PermissionUtils.checkAndRequestMorePermissions(this, PERMISSIONS, 2, new PermissionUtils.PermissionRequestSuccessCallBack() {
            @Override
            public void onHasPermission() {
                Toast.makeText(MyApplication.getContext(),"权限申请成功",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_look_img:
                Log.d(TAG, "按钮被点击了");

                        website = et_website.getText().toString().trim();
                        Log.d(TAG, "输入的字符串" + website);

                        new Thread() {
                            @Override
                            public void run() {
                                super.run();

                                try {
                                    //创建RUL，指定我们要访问的路径
                                    URL url = new URL(website);

                                    //得到对象，用于发送或接收数据
                                    HttpsURLConnection connection = (HttpsURLConnection) url
                                            .openConnection();//这里不能写http，不然会返回302，更不能写没有https的，会报异常

                                    //设置发送get请求,get要求大写，默认就是get请求
                                    Log.d(TAG, "收到了数据");
                                    connection.setRequestMethod("GET");

                                    //设置请求超时时间
                                    connection.setConnectTimeout(2000);//设置失败

                                    //获取服务器返回的状态码
                                    int code = connection.getResponseCode();//这里有问题添加上面一段文字就好了。
                                    Log.d(TAG, "返回l状态码");
                                    Log.d(TAG, "状态码" + code);

                                    //如果code=200说明请求成功
                                    if (code == 200) {
                                        //获取服务器以流的形式返回的数据
                                        InputStream is = connection.getInputStream();
                                        //把流转换成字符串，由于把流转换成字符串是一个非常常见的操作，所以抽出一个方法类（utils）。
                                        Bitmap bitmap = BitmapFactory.decodeStream(is);


                                        //发送消息
                                        //Message msg=new Message();这种方法效率有点低
                                        Message msg = Message.obtain();//如果消息池有，就赋值一个，没有再创建
                                        msg.obj = bitmap;//携带什么数据
                                        msg.what = CONNECT_SUCCEED;
                                        mHandler.sendMessage(msg);

                                        //tv_showsite
                                        // .setText(content);

                                    } else {


                                        Message msg = Message.obtain();
                                        msg.what = CONNECT_FAILED;

                                        msg.obj = code;
                                        mHandler.sendMessage(msg);


                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();

                                    Message msg = Message.obtain();
                                    msg.what = CONNECT_ERROR;
                                    mHandler.sendMessage(msg);
                                }
                            }
                        }.start();



                break;
        }
    }
}
