package com.tangqi.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //定义成员变量
    private IntentFilter intentFilter;
//    private NetworkChangeReceiver networkChangeReceiver;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //创建intentFilter对象，用于添加想接收系统发出的什么广播
        intentFilter = new IntentFilter();

//        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");//接收全局的
//        //初始化广播接收器对象，并调用方法进行注册
//        networkChangeReceiver=new NetworkChangeReceiver();
//        registerReceiver(networkChangeReceiver,intentFilter);

        localBroadcastManager=LocalBroadcastManager.getInstance(this);//获取实例
        intentFilter.addAction("com.example.broadcastTest.LOCAL_BROADCAST");//接收本地的
        localReceiver=new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);//注册本地广播监听器


        //案例三，按钮发送广播，自己接收+案例四，本地广播

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 系统全局的广播
//                Intent intent = new Intent("com.example.broadcastTest.MY_BROADCAST");
//                sendOrderedBroadcast(intent,null);

                //本地的广播发送
                Intent intent = new Intent("com.example.broadcastTest.LOCAL_BROADCAST");
                localBroadcastManager.sendBroadcast(intent);
            }
        });
    }

    //动态注册的广播接收器一定都要取消注册才行
    @Override
    protected void onDestroy(){
        super.onDestroy();
//        unregisterReceiver(networkChangeReceiver);
        localBroadcastManager.unregisterReceiver(localReceiver);
    }
    
    //定义本地广播接收器
    class LocalReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"收到本地广播啦！",Toast.LENGTH_SHORT).show();
    }
}
    // 定义广播接收器的类
    class NetworkChangeReceiver extends BroadcastReceiver{
        private TextView theme=(TextView)findViewById(R.id.theme);
        @Override
        public void onReceive(Context context, Intent intent) {
            // 方案一，用toast简单输出网络状态变了
            // Toast.makeText(context,"network changes",Toast.LENGTH_SHORT).show();
            // 方案二，先判断是否有网络，再输出。
            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo!=null&&networkInfo.isAvailable()){
                theme.setText("Hello , World!");
                Toast.makeText(context,"欢迎你，TQ",Toast.LENGTH_SHORT).show();
            }else{
                theme.setText("无网络，请检查数据连接是否打开。");
            }
        }
    }
}
