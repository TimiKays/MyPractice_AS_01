package com.tangqi.safecenter.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.tangqi.safecenter.R;
import com.tangqi.safecenter.utils.ConstantValues;
import com.tangqi.safecenter.utils.PermissionUtils;
import com.tangqi.safecenter.utils.SpUtils;
import com.tangqi.safecenter.utils.StreamUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "Mylog";

    private TextView mTv_version_name;
    private int mLocalVersionCode;
    private int mServerVisionCode;
    private Context mc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        initUI();

        initData();

    }



    /**
     * 初始化数据
     */
    private void initData() {
        mc = this;
        //[1]显示应用版本名称
        mTv_version_name.setText("版本名称：" + getVersionName());

        //[2]检测更新版本
        mLocalVersionCode = getVersionCode();//本地版本号

        //判断设置中是否设置了自动更新。
        if(SpUtils.getState(this, ConstantValues.AUTO_UPDATE,false)){
            checkVisionCode();
        }else{
            new Thread(){
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    enterHome();
                }
            }.start();

        }


    }

    /**
     * 获取服务器版本号，并和本地的版本号进行对比，如果相同就直接进入主界面；如果不同就提示用户下载。
     */
    private void checkVisionCode() {
        Log.i(TAG, "开始获取服务器版本");
        new Thread() {
            @Override
            public void run() {
                try {
                    long startTime = System.currentTimeMillis();
                    URL url = new URL("http://192.168.1.100:8080/WebServer/update.json");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(2000);
                    connection.setReadTimeout(2000);
                    connection.setRequestMethod("GET");//默认就是get

                    if (connection.getResponseCode() == 200) {
                        InputStream is = connection.getInputStream();
                        String json = StreamUtil.stream2String(is);


                        //json解析
                        JSONObject jsonObject = new JSONObject(json);
                        String versionName = jsonObject.getString("versionName");
                        final String versionDes = jsonObject.getString("versionDes");
                        final String versionCode = jsonObject.getString("versionCode");
                        final String downloadUrl = jsonObject.getString("downloadUrl");

                        Log.i(TAG, versionName);
                        Log.i(TAG, versionDes);
                        Log.i(TAG, versionCode);
                        Log.i(TAG, downloadUrl);

                        if (Integer.parseInt(versionCode) > mLocalVersionCode) {
                            //弹出对话框，提示更新
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(mc);
                                    builder.setTitle("发现新版本！");
                                    builder.setMessage(versionDes);
                                    builder.setIcon(R.mipmap.ic_launcher);
                                    builder.setPositiveButton("开始更新", new DialogInterface
                                            .OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //下载文件
                                            int start = downloadUrl.lastIndexOf("/") + 1;
                                            RequestParams params = new RequestParams(downloadUrl);
                                            params.setSaveFilePath(mc.getExternalFilesDir(null) +
                                                    File.separator + downloadUrl.substring(start)
                                            );//保存的位置
                                            params.setAutoResume(true);//是否支持断点
                                            x.http().get(params, new Callback
                                                    .CommonCallback<File>() {
                                                @Override
                                                public void onSuccess(File result) {
                                                    //提示用户安装
                                                    Log.i(TAG, "onSuccess: 下载成功");
                                                    installAPK(result);
                                                }

                                                @Override
                                                public void onError(Throwable ex, boolean
                                                        isOnCallback) {
                                                    Log.i(TAG, "onError: 下载失败" + ex);

                                                }

                                                @Override
                                                public void onCancelled(CancelledException cex) {
                                                    Log.i(TAG, "onCancelled: 下载取消");
                                                }

                                                @Override
                                                public void onFinished() {
                                                    Log.i(TAG, "onFinished:下载结束 ");
                                                }
                                            });

                                        }
                                    });
                                    builder.setNegativeButton("下次再说", new DialogInterface
                                            .OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            enterHome();
                                        }
                                    });
                                    //即使用户点击返回按键，也进入主界面
                                    builder.setOnCancelListener(new DialogInterface
                                            .OnCancelListener() {
                                        @Override
                                        public void onCancel(DialogInterface dialog) {
                                            enterHome();
                                            dialog.dismiss();
                                        }
                                    });
                                    builder.show();

                                }
                            });
                        } else {

                            //版本号相同，等够3秒，直接进入主界面。
                            long endTime = System.currentTimeMillis();
                            if ((endTime - startTime) < 3000) {
                                try {
                                    Thread.sleep(3000 - (endTime - startTime));
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            enterHome();

                        }
                    }

                } catch (MalformedURLException e) {
                    Log.i(TAG, "MalformedURLException:"+e);
                    e.printStackTrace();
                    enterHome();
                } catch (IOException e) {
                    Log.i(TAG, "IOException:"+e);
                    e.printStackTrace();
                    enterHome();
                } catch (JSONException e) {
                    Log.i(TAG, "JSONException:"+e);
                    e.printStackTrace();
                    enterHome();
                }


            }
        }.start();

    }

    /**
     * 安装APK的方法。
     *
     * @param file 传入的file对象。
     */
    private void installAPK(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        Uri data;
        // 判断版本大于等于7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            data = FileProvider.getUriForFile(mc, "com.tangqi.safecenter.fileprovider", file);
            // 给目标应用一个临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            data = Uri.fromFile(file);
        }
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        startActivityForResult(intent, 0);


        Log.i(TAG, "installAPK: 已请求系统安装界面");

    }

    /**
     * 处理当用户安装时点击取消的逻辑
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {

            enterHome();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 进入主界面的方法
     */
    private void enterHome() {
        Intent intent = new Intent(mc, HomeActivity.class);
        startActivity(intent);
        finish();//一般启动页只显示一次。
    }

    /**
     * 获取本地的版本号
     *
     * @return 返回本地版本号。返回0表示异常。
     */
    private int getVersionCode() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取版本名称
     *
     * @return 返回版本名称，返回null代表异常
     */
    private String getVersionName() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 初始化UI方法
     */
    private void initUI() {
        mTv_version_name = (TextView) findViewById(R.id.tv_version_name);

    }
}
