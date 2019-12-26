package com.tangqi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpinnerWithImg extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner spinner;
    private ImageView img;
    private TextView text;
    private SimpleAdapter adapter;
    private List<Map<String,Object>> data;
    private int[] img_arr = {R.drawable.address_book, R.drawable.calendar, R.drawable.camera, R.drawable.clock, R.drawable.games_control, R.drawable.messenger, R.drawable.ringtone, R.drawable.settings, R.drawable.speech_balloon, R.drawable.weather, R.drawable.world, R.drawable.youtube};
    private String[] names_arr = {"地址簿", "日历", "相机", "时钟", "游戏", "联系人", "音乐", "设置", "短信", "天气", "浏览器", "youtube"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_with_img);
        //初始化
        spinner = findViewById(R.id.app_chooser);
        img = findViewById(R.id.app_img);
        text = findViewById(R.id.app_text);
        data=new ArrayList<>();
        adapter = new SimpleAdapter(this,getData(),R.layout.spinner_item,new String[]{"image","text"},new int[]{R.id.app_chooser_img,R.id.app_chooser_text});
        //设置适配器、监听器
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    private List<Map<String, Object>> getData() {
        for(int i = 0;i<img_arr.length;i++){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("image",img_arr[i]);
            map.put("text",names_arr[i]);
            data.add(map);
        }

        return data;
    }
//下面是重写的方法
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        img.setImageResource(img_arr[position]);
        text.setText(names_arr[position]);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
