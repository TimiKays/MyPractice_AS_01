package com.tangqi.safecenter.activity;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tangqi.safecenter.R;
import com.tangqi.safecenter.engine.QueryNumberDao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class QueryNumberBelongsActivity extends BaseActivity {

    private static final String TAG = "Mylog";
    private EditText et_tel;
    private Button bt_query;
    private Context mc;
    private File mFile;
    String belongs = "未知号码";
    private TextView position;
//   我这里直接用runOnUIThread的方法了，不传message了
//   private Handler mHandler=new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//            }
//        };


    @Override
    public void initView() {
        setContentView(R.layout.activity_query_number_belongs);
        mc = this;
        et_tel = (EditText) findViewById(R.id.et_tel);
        bt_query = (Button) findViewById(R.id.bt_query);
        position = (TextView) findViewById(R.id.position);


    }

    @Override
    public void showNextView() {

    }

    @Override
    public void showPrevious() {

    }

    @Override
    public void initData() {
        //读取assets目录下的数据库,将其转换在工程的Files,(Cache,sd)
        initDataBase("address.db");



    }

    @Override
    public void initEvent() {
        et_tel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String number = et_tel.getText().toString().replaceAll(" ","");
                queryNumber(number);
            }
        });
        bt_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = et_tel.getText().toString().replaceAll(" ","");
                if (number.equals("")) {
                    Animation ta = AnimationUtils.loadAnimation(mc, R.anim.translat_3010);
                    //插补器
//                    ta.setInterpolator(new Interpolator() {
//                        @Override
//                        public float getInterpolation(float input) {
//                            return (float)(Math.sin(input));
//
//                        }
//                    });

                    et_tel.startAnimation(ta);
                    return;
                }
                queryNumber(number);
            }
        });


    }

    private void queryNumber(final String number) {
        Log.i(TAG, "queryNumber: "+number);
        new Thread(){
            @Override
            public void run() {
               belongs = QueryNumberDao.queryNumber(number);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        position.setText(belongs);
                    }
                });

            }
        }.start();



    }

    private void initDataBase(String dbName) {
        /*
        * 1、获取输入流：getAssets().open(dbName)
        * 2、获取输出流：通过file对象
        * 3、读入的同时写出到file文件
        * 4、最后，关闭流
        * */
        FileOutputStream fos = null;
        InputStream dbFile = null;
        try {
            dbFile = getAssets().open(dbName);
            File dir = getFilesDir();
            mFile = new File(dir, dbName);
            if (mFile.exists()) {
                return;
            }

            fos = new FileOutputStream(mFile);
            byte[] bys = new byte[1024];
            int len = -1;
            while ((len = dbFile.read(bys)) != -1) {
                fos.write(bys);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (dbFile != null) {
                    dbFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
