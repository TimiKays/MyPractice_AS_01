package com.tangqi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WishNumberActivity extends AppCompatActivity {

    private ListView mLv_num;
    private String[] mArr_data={"5554","5556","13163745132","110"};
    private ArrayAdapter<String> mNumbers_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_number);
        mLv_num = (ListView) findViewById(R.id.list_numbers);

        mNumbers_adapter = new ArrayAdapter<String>(this, android.R.layout
                .simple_list_item_1, mArr_data);

        mLv_num.setAdapter(mNumbers_adapter);

        mLv_num.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String number = mArr_data[position];
                Intent intent=new Intent();
                intent.putExtra("number",number);
                setResult(1,intent);
                finish();

            }
        });
    }
}
