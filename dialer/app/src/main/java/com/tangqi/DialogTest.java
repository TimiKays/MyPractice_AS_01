package com.tangqi;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class DialogTest extends AppCompatActivity implements View.OnClickListener {

    private Button bt_dialog1;
    private Button bt_dialog2;
    private Button bt_dialog3;
    private Button bt_dialog4;
    private Button bt_push;
    private Button bt_unpush;
    private Context mc = this;
    private String[] foods = {"麻辣豆腐", "番茄炒蛋", "青椒肉丝", "蛋炒饭"};
    private String[] stars = {"周星驰", "李连杰", "黄轩", "吕轻侯"};
    private NotificationManager manager;
    private int Notification_ID=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_test);

        bt_dialog1 = (Button) findViewById(R.id.bt_dialog1);
        bt_dialog2 = (Button) findViewById(R.id.bt_dialog2);
        bt_dialog3 = (Button) findViewById(R.id.bt_dialog3);
        bt_dialog4 = (Button) findViewById(R.id.bt_dialog4);
        bt_push = (Button) findViewById(R.id.bt_push);
        bt_unpush = (Button) findViewById(R.id.bt_unpush);

        bt_dialog1.setOnClickListener(this);
        bt_dialog2.setOnClickListener(this);
        bt_dialog3.setOnClickListener(this);
        bt_dialog4.setOnClickListener(this);
        //推送通知栏通知,真机貌似推不出去
        bt_push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置通知的一些属性
                Notification.Builder builder = new Notification.Builder(mc);

                builder.setSmallIcon(R.drawable.camera);
                builder.setTicker("你妈妈喊你回家吃饭！");//状态栏提示貌似不显示
                builder.setContentTitle("唐宝宝的来信");//状态栏标题
                builder.setContentText("有需要计算的吗，快来计算吧~");//状态栏内容
                builder.setWhen(System.currentTimeMillis());//状态栏时间
                builder.setDefaults(Notification.DEFAULT_ALL);//同时设置提示声音SOUND，指示灯LIGHTS，震动VIBRATE

                //设置通知点击后跳转到哪里
                Intent intent = new Intent(mc, Calculator.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(mc, 0, intent, 0);
                builder.setContentIntent(pendingIntent);


                //显示通知
                Notification notification = builder.build();
                //4.1以下用 builder.getNotification();
                notification.flags=Notification.FLAG_AUTO_CANCEL;//设置点击后自动消失
                manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(Notification_ID, notification);//发送通知到通知栏

            }
        });
        bt_unpush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.cancel(Notification_ID);
            }
        });

    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher_round);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("dialog", "点击了确定按钮");
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("dialog", "点击了取消按钮");
            }
        });
        switch (v.getId()) {
            case R.id.bt_dialog1:
                builder.setTitle("确定要吃麻辣豆腐吗？");
                break;
            case R.id.bt_dialog2:
                builder.setTitle("喜欢吃什么？");
                builder.setSingleChoiceItems(foods, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("dialog", "我只想吃" + foods[which]);
                    }
                });
                break;
            case R.id.bt_dialog3:
                builder.setTitle("你喜欢哪些明星？");
                builder.setMultiChoiceItems(stars, null, new DialogInterface
                        .OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            Log.i("dialog", "好喜欢" + stars[which] + "呀！");
                        } else {
                            Log.i("dialog", "我不喜欢" + stars[which] + "了...");
                        }
                    }
                });
                break;
            case R.id.bt_dialog4:
                View view = View.inflate(this, R.layout.diy_toast, null);
                builder.setView(view);
                break;
        }
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}


