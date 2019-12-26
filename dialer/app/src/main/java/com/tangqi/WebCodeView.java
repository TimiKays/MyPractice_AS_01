package com.tangqi;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tangqi.service.UtilStream2String;

import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class WebCodeView extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "test";
    private static final int CONNECT_SUCCEED = 0;
    private static final int CONNECT_FAILED = 1;
    private static final int CONNECT_ERROR = 3;
    private Button bt_look;
    private EditText et_website;
    private TextView tv_showsite;
    private String website;
    private Context mContext;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CONNECT_SUCCEED:
                    tv_showsite.setText((String) msg.obj);
                    break;
                case CONNECT_FAILED:
                    Toast.makeText(WebCodeView.this, "网络连接失败，错误码：" + msg.obj, Toast
                            .LENGTH_SHORT).show();
                    break;
                case CONNECT_ERROR:
                    Toast.makeText(WebCodeView.this, "无法连接，请检查输入网址是否正确", Toast
                            .LENGTH_SHORT).show();
                    break;
            }


        }
    };
    ;

    public WebCodeView() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_code_view);

        bt_look = findViewById(R.id.bt_look);
        et_website = findViewById(R.id.et_website);
        tv_showsite = findViewById(R.id.tv_show_site);
        mContext = this;
        bt_look.setOnClickListener(this);


        //一开始会报主线程异常 networkonmainthreadexception，加上这一段就自动好了，虽然不知道是什么鬼。貌似是因为Android 4.0
        // 之后不能在主线程中请求HTTP请求，否则不让联网。不用这个，因为不知道是什么鬼
        //        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads()
        //                .detectDiskWrites().detectNetwork().penaltyLog().build());
        //        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
        // .detectLeakedSqlLiteObjects()
        //                .detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_look:
                Log.d(TAG, "按钮被点击了");


//                //这个创建对象不能放到子线程，否则会报错。
//                UtilRequestPermissions urp = new UtilRequestPermissions(mContext);
//                Log.d(TAG, "有了对象");
//                urp.performCodeWithPermission("请求访问网络吖", new UtilRequestPermissions
//                        .PermissionCallback() {

//
//                    @Override
//                    public void hasPermission() {
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
                                            .openConnection();//这里不能写http，不然会返回302

                                    //设置发送get请求,get要求大写，默认就是get请求
                                    Log.d(TAG, "收到了数据");
                                    connection.setRequestMethod("GET");

                                    //设置请求超时时间
                                    connection.setConnectTimeout(500);//设置失败

                                    //获取服务器返回的状态码
                                    int code = connection.getResponseCode();//这里有问题添加上面一段文字就好了。
                                    Log.d(TAG, "返回l状态码");
                                    Log.d(TAG, "状态码" + code);

                                    //如果code=200说明请求成功
                                    if (code == 200) {
                                        //获取服务器以流的形式返回的数据
                                        InputStream is = connection.getInputStream();
                                        //把流转换成字符串，由于把流转换成字符串是一个非常常见的操作，所以抽出一个方法类（utils）。
                                        String content = UtilStream2String.Stream2String(is);
                                        Log.d(TAG, "转换流转成字符串：" + content);

                                        //发送消息
                                        //Message msg=new Message();这种方法效率有点低
                                        Message msg = Message.obtain();//如果消息池有，就赋值一个，没有再创建
                                        msg.obj = content;//携带什么数据
                                        msg.what = CONNECT_SUCCEED;
                                        mHandler.sendMessage(msg);

                                        //tv_showsite
                                        // .setText(content);

                                    } else {

                                        //TOAST也是一个view，不能在子线程处理。
                                        //发送消息
                                        //Message msg=new
                                        //Message();
                                        Message msg = Message.obtain();
                                        msg.what = CONNECT_FAILED;

                                        msg.obj = code;
                                        mHandler.sendMessage(msg);


                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    //输入错误网址或这其他报错的时候：
                                    Message msg = Message.obtain();
                                    msg.what = CONNECT_ERROR;
                                    mHandler.sendMessage(msg);
                                }
                            }
                        }.start();


//                    }

//                    @Override
//                    public void noPermission() {
//                        Log.d(TAG, "请求权限失败");
//                    }
//                }, new String[]{Manifest.permission.INTERNET});


                break;
        }
    }
}
