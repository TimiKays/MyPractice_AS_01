package com.tangqi.service;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.tangqi.R;

public class RpResultActivity extends AppCompatActivity {
private String[] result={"您的运气爆棚了！不管怎么样都没关系！",
        "柔软的你，很容易被感动，你喜欢自由的想像，而且比较内向一点",
        "擅思考计划的你，做事比较倾向保守。",
        "你是个温柔的情人，但小心你的情绪问题"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rp_result);

        TextView tv_name = (TextView) findViewById(R.id.name_show);
        TextView tv_sex = (TextView) findViewById(R.id.sex_show);
        TextView tv_result = (TextView) findViewById(R.id.result_show);


        String name=getIntent().getStringExtra("name");
        String sex = getIntent().getStringExtra("sex");

        //显示
        tv_name.setText(name);
        tv_sex.setText(sex);

        //算命
        int total=0;
        byte[] bys = name.getBytes();
        byte[] bys2 = sex.getBytes();
        for (byte b:bys){

                int number = b&0xff;

                total+=number;

        }
        for(byte b2 : bys2){

            int number2 = b2&0xff;
            total+=number2;
        }
        
        int result_code = total % 4;

        tv_result.setText(this.result[result_code]);
        


    }
}
