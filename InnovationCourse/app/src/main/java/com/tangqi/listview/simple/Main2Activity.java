package com.tangqi.listview.simple;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//import com.zhihuishu.innovationcourse.R;

public class Main2Activity extends AppCompatActivity {
    //自己配置数据---字符串数组
    private String[] data = {"苹果","香蕉","梨子"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //创建适配器，接收三个参数
        ArrayAdapter<String> simpleAdapter = new ArrayAdapter<String>(Main2Activity.this,android.R.layout.simple_list_item_1, data);
        //找到ListView对象
        ListView lv=(ListView) findViewById(R.id.simpleListView);
        //吧适配器关联到ListView对象
        lv.setAdapter(simpleAdapter);
    }
}
