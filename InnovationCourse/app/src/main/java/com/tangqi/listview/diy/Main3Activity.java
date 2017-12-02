package com.tangqi.listview.diy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.zhihuishu.innovationcourse.R;
import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends AppCompatActivity {
    private List<Fruits> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //初始化水果的数据
        for(int i=0;i<10;i++){
            Fruits apple = new Fruits("苹果果"+i,R.drawable.apple);
            Fruits pear = new Fruits("小梨子"+i,R.drawable.pear);
            Fruits banana = new Fruits("香蕉"+i,R.drawable.banana);

            data.add(apple);
            data.add(pear);
            data.add(banana);
        }

        //创建适配器，接收三个参数
        FruitsAdapter fa = new FruitsAdapter(Main3Activity.this,R.layout.fruit_item, data);
        //找到ListView对象
        ListView lv=(ListView) findViewById(R.id.simpleListView);
        //把适配器关联到ListView对象
        lv.setAdapter(fa);
    }
}
