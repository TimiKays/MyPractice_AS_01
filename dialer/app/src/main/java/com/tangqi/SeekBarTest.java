package com.tangqi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class SeekBarTest extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private SeekBar seekBar;
    private TextView present_doing;
    private TextView present_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_bar_test);

        seekBar=findViewById(R.id.seekBar);
        present_doing=findViewById(R.id.present_doing);
        present_progress=findViewById(R.id.present_progress);

        seekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        present_doing.setText("正在拖动");
        present_progress.setText("当前进度："+ progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        present_doing.setText("开始拖动");

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        present_doing.setText("喵喵喵");
    }
}
