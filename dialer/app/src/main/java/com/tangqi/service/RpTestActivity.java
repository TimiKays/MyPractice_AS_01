package com.tangqi.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.tangqi.R;

public class RpTestActivity extends AppCompatActivity {


    private EditText mName;
    private RadioGroup mSex;
    private Button mSubmit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rp_test);

        mName = (EditText) findViewById(R.id.name);
        mSex = (RadioGroup) findViewById(R.id.group_sex);
        mSubmit = (Button) findViewById(R.id.submit_name);

    }

    public void submit(View view) {
        String name = mName.getText().toString().trim();
        String sex="女";
        switch (mSex.getCheckedRadioButtonId()) {
            case R.id.male:
                sex = "男";
                break;
            case R.id.female:
                sex = "女";
                break;
            case R.id.secret:
                sex = "保密";
                break;
        }

        Intent intent=new Intent(this,RpResultActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("sex",sex);
        startActivity(intent);

    }
}
