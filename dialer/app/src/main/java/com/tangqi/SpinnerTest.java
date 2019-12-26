package com.tangqi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class SpinnerTest extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView tv;
    private Spinner spinner;
    private ArrayList<String> data;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_test);
        //初始化
        tv=findViewById(R.id.city_text);
        tv.setText("您选择的城市是:北京");
        spinner=findViewById(R.id.city_chooser);
        data=new ArrayList<>();
        data.add("北京");
        data.add("上海");
        data.add("深圳");
        data.add("广州");
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //设置适配器、监听器
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String city = (String) adapter.getItem(position);
//        String city = data.get(position); 两种都行
        tv.setText("您选择的城市是："+city);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
