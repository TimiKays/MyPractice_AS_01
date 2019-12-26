package com.tangqi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Toast_test extends AppCompatActivity implements View.OnClickListener {

    private Button bt_move_toast;
    private Button bt_img_toast;
    private Button bt_diy_toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast_test);

        bt_move_toast = (Button) findViewById(R.id.bt_move_toast);
        bt_img_toast = (Button) findViewById(R.id.bt_img_toast);
        bt_diy_toast = (Button) findViewById(R.id.bt_diy_toast);

        bt_move_toast.setOnClickListener(this);
        bt_img_toast.setOnClickListener(this);
        bt_diy_toast.setOnClickListener(this);
        byte b = (byte) 130;
        Log.d("test", String.valueOf(b));



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.bt_move_toast:
                Toast toast=Toast.makeText(this, "我是改变了位置的Toast", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 100, 100);
                toast.show();
                break;
            case R.id.bt_img_toast:
                Toast img_toast=Toast.makeText(this, "我是带图片的Toast", Toast.LENGTH_SHORT);
                LinearLayout linearLayout=(LinearLayout) img_toast.getView();//获取这个TOAST的View
                ImageView imageView = new ImageView(this);//获取一个ImageView
                imageView.setImageResource(R.drawable.youtube);//设置imageview的图片资源
                linearLayout.addView(imageView,0);//把imageview添加到toast的view中去
                img_toast.show();
                break;

            case R.id.bt_diy_toast:
                Toast diy_toast=Toast.makeText(this, "我是自定义的Toast", Toast.LENGTH_LONG);
                View view = View.inflate(this,R.layout.diy_toast,null);
                diy_toast.setView(view);
                diy_toast.show();

                break;

        }
    }
}
