package com.tangqi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ProgressBarTest extends Activity implements View.OnClickListener {

    View hiddenable_progress;
    private ProgressBar progressBar_hori;
    private Button add_bt;
    private Button abstract_bt;
    private Button reset_bt;
    private Button dialog_bt;
    private TextView progress_show;
    private int first_progress;
    private int second_progress;
    private int max_progress;
    private ProgressDialog pg;
    private Context mc = this;
    private Button show_hidden_progress;
    private ViewStub hidden_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //启用窗口特征，启用带进度和不带进度的进度条
        requestWindowFeature(Window.FEATURE_PROGRESS);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_progress_bar_test);
        //显示两种刻度
        setProgressBarVisibility(true);
        setProgressBarIndeterminate(true);
        //给精确的进度条设置刻度
        setProgress(7000);

        //初始化
        progressBar_hori = findViewById(R.id.progressBar_hori);
        add_bt = findViewById(R.id.add_button);
        abstract_bt = findViewById(R.id.abstract_button);
        reset_bt = findViewById(R.id.reset_button);
        progress_show = findViewById(R.id.progress_show);
        dialog_bt = findViewById(R.id.progress_dialog);
        show_hidden_progress = findViewById(R.id.show_hidden_progress);
        hidden_progress = findViewById(R.id.hidden_progress);
//        mc=this;

        first_progress = progressBar_hori.getProgress();
        second_progress = progressBar_hori.getSecondaryProgress();
        max_progress = progressBar_hori.getMax();
        progress_show.setText("第一进度：" + (int) (first_progress / (float) max_progress * 100) + "%,第二进度：" + (int) (second_progress / (float) max_progress * 100) + "%");
        //设置监听器
        add_bt.setOnClickListener(this);
        abstract_bt.setOnClickListener(this);
        reset_bt.setOnClickListener(this);
        dialog_bt.setOnClickListener(this);
        show_hidden_progress.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_button:
                //这里增加减少刻度，最好用progress.incrementProgressBy(10);第二进度类似，中间加一个Secondary
//                if(first_progress<=90){
//                    first_progress=first_progress+10;
//                }
//                if(second_progress<=80){
//                    second_progress=second_progress+20;
//                }
                progressBar_hori.incrementProgressBy(10);
                progressBar_hori.incrementSecondaryProgressBy(20);
                break;
            case R.id.abstract_button:
//                if (first_progress >= 10) {
//                    first_progress = first_progress - 10;
//                }
//                if (second_progress >= 20) {
//                    second_progress = second_progress - 20;
//                }
//                if (second_progress < first_progress) {
//                    second_progress = first_progress;
//                }
                progressBar_hori.incrementProgressBy(-10);
                progressBar_hori.incrementSecondaryProgressBy(-20);
                break;
            case R.id.reset_button:
//                first_progress = 10;
//                second_progress = 20;
                progressBar_hori.setProgress(10);
                break;
            case R.id.progress_dialog:
                pg = new ProgressDialog(mc);
                pg.setTitle("我是标题");
                pg.setMessage("这是一个对话框样式的进度条");
                pg.setIcon(R.mipmap.ic_launcher);
                pg.setCancelable(false);//默认是true
                pg.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(mc, "哈哈，你点了确定哦", Toast.LENGTH_SHORT).show();
                    }
                });
                pg.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(mc, "额，拜拜👨", Toast.LENGTH_SHORT).show();
                    }
                });
                pg.setButton(DialogInterface.BUTTON_NEUTRAL, "???", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(mc, "我也不知道这是啥", Toast.LENGTH_SHORT).show();
                    }
                });

                pg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pg.setMax(100);
//                pg.setProgress(50);//这个设置不了，还是为0
                pg.incrementProgressBy(60);
                pg.show();
                progressBar_hori.setSecondaryProgress(20);
                break;
            case R.id.show_hidden_progress:
                if(hiddenable_progress==null){
                    hiddenable_progress=hidden_progress.inflate();
                    show_hidden_progress.setText("隐藏");
                }else{
                    if(hiddenable_progress.getVisibility()==View.VISIBLE){
                        hiddenable_progress.setVisibility(View.INVISIBLE);
                        show_hidden_progress.setText("显示");
                    }else{
                        hiddenable_progress.setVisibility(View.VISIBLE);
                        show_hidden_progress.setText("隐藏");

                    }

                }



        }
//        progressBar_hori.setProgress(first_progress);
//        progressBar_hori.setSecondaryProgress(second_progress);
        first_progress = progressBar_hori.getProgress();
        second_progress = progressBar_hori.getSecondaryProgress();
        progress_show.setText("第一进度：" + (int) (first_progress / (float) max_progress * 100) + "%,第二进度：" + (int) (second_progress / (float) max_progress * 100) + "%");
    }
}
