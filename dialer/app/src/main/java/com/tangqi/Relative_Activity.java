package com.tangqi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Relative_Activity extends AppCompatActivity {
    private EditText words;
    private Button button;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relative_);
        mContext=this;
        button=findViewById(R.id.back);
        words=findViewById(R.id.words);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data=new Intent();
                String number = words.getText().toString().trim();
                data.putExtra("number",number);
                setResult(1,data);
                finish();
            }
        });
    }
}
