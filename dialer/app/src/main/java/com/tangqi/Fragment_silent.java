package com.tangqi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Fragment_silent extends AppCompatActivity implements View.OnClickListener,Fragment1.MyListener {
    private Button fg1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_silent);

        fg1=findViewById(R.id.button_fg1);
        fg1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_fg1:
                Toast.makeText(this,"干嘛点我，讨厌！",Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    public void passData(String data) {

    }
}
