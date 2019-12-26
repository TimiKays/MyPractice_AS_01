package com.tangqi;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

public class FragmentDemo extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,Fragment1.MyListener {

    private RadioButton radio1;
    private RadioButton radio2;
    private RadioButton radio3;
    private RadioButton radio4;
    private RadioGroup radioGroup;
    private Context mc = this;
    private FragmentManager fragmentManager;
    private FragmentTransaction ft;
    private String data_to_pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_demo);

        radio1 = findViewById(R.id.radio_1);
        radio2 = findViewById(R.id.radio_2);
        radio3 = findViewById(R.id.radio_3);
        radio4 = findViewById(R.id.radio_4);
        radioGroup = findViewById(R.id.radio_group);





        radioGroup.check(R.id.radio_1);
        radioGroup.setOnCheckedChangeListener(this);

        fragmentManager = getFragmentManager();
        ft = fragmentManager.beginTransaction();
        Fragment1 fragment1 = new Fragment1();
        ft.replace(R.id.linear_layout, fragment1);
        ft.commit();



    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        switch (checkedId) {
            case R.id.radio_1:
                Fragment1 fragment1 = new Fragment1();
                ft.replace(R.id.linear_layout, fragment1);
                ft.commit();
                break;

            case R.id.radio_2:
                Fragment2 fragment2 = new Fragment2();
                //如果data_to_pass里面有东西，就传过去显示
                if(data_to_pass!=null&&data_to_pass!=""){
                    Bundle bundle=new Bundle();
                    bundle.putString("frag1",data_to_pass);
                    fragment2.setArguments(bundle);
                }
//                FragmentManager fragmentManager2 = getFragmentManager();
//                FragmentTransaction ft2 = fragmentManager2.beginTransaction();
                ft.replace(R.id.linear_layout, fragment2);
                ft.commit();

                break;

            case R.id.radio_3:
                break;

            case R.id.radio_4:
                Intent intent = new Intent(mc,Fragment_silent.class);
                startActivity(intent);
                break;
        }

    }


    @Override
    public void passData(String data) {
        data_to_pass=data;
        Toast.makeText(this,"从fragment1收到数据："+data,Toast.LENGTH_SHORT).show();
        radioGroup.check(R.id.radio_2);

//        Fragment2 fragment2 = new Fragment2();



    }
}
