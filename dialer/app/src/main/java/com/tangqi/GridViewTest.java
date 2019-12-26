package com.tangqi;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GridViewTest extends Activity {

    private GridView gridView;
    private int[] img = {R.drawable.address_book, R.drawable.calendar, R.drawable.camera, R.drawable.clock, R.drawable.games_control, R.drawable.messenger, R.drawable.ringtone, R.drawable.settings, R.drawable.speech_balloon, R.drawable.weather, R.drawable.world, R.drawable.youtube};
    private String[] names = {"地址簿", "日历", "相机", "时钟", "游戏", "联系人", "音乐", "设置", "短信", "天气", "浏览器", "youtube"};
    private List<Map<String, Object>> data;
    private Context mc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        //初始化
        gridView = findViewById(R.id.grid_view);
        data = new ArrayList<>();
        mc=this;


        //设置适配器
        SimpleAdapter sp = new SimpleAdapter(this, getdata(), R.layout.grid_item, new String[]{"img", "name"}, new int[]{R.id.grid_img, R.id.grid_name});
        gridView.setAdapter(sp);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mc,names[position],Toast.LENGTH_SHORT).show();
            }
        });

    }

    //初始化数据源
    private List<Map<String, Object>> getdata() {
        for(int i=0;i<img.length;i++){
            Map<String, Object> map= new HashMap<String,Object>();
            map.put("img",img[i]);
            map.put("name",names[i]);
            data.add(map);
        }
        return data;
    }
}
