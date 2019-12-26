package com.tangqi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WishContentActivity extends AppCompatActivity {
    private ListView mlv_contents;
    private String[] mArr_data= {"你好呀！","我不在线，你不要理我。","我不想理你额","再见咯"};
    private ArrayAdapter<String> mNumbers_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_content);

        mlv_contents = (ListView) findViewById(R.id.list_contents);
        mNumbers_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mArr_data);
        mlv_contents.setAdapter(mNumbers_adapter);

        //监听器
        mlv_contents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String content = mArr_data[position];
                Intent intent = new Intent();
                intent.putExtra("content", content);
                setResult(2, intent);
                finish();

            }
        });
    }
}
