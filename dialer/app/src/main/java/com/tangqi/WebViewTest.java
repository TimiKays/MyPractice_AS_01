package com.tangqi;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/*
* 存在问题，有的页面无法返回。
* 而且很奇怪，都没有向我申请网络访问的权限，就自动可以访问网络了。而且在权限里也没看到网络访问权限*/
public class WebViewTest extends AppCompatActivity {

    String url = "https://www.baidu.com";
    private WebView webView;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_test);
        webView = findViewById(R.id.web_view);
        requestInternetPermission();
//                openWebs();

    }

    //检查权限，申请权限
    private void requestInternetPermission() {
        //首先检查是否有权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            //没有权限，则申请权限,并且系统会回调下一个方法。
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
            Toast.makeText(this, "正在授权", Toast.LENGTH_SHORT).show();
        } else {
            //有权限，则直接打开网页
            openWebs();
        }
    }


    //根据requestCode和grantResults(授权结果)做相应的处理。
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted 获得权限后执行xxx
                openWebs();
            } else {
                // Permission Denied 拒绝后xx的操作。
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //打开网页
    public void openWebs() {
        webView.loadUrl(url);
        //启用javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

//        settings.setAllowFileAccess(true);
//        settings.setDomStorageEnabled(true);
//        settings.setBlockNetworkImage(false);
//        settings.setBlockNetworkLoads(false);

        settings.setDatabaseEnabled(true);//允许使用数据库
        settings.setGeolocationEnabled(true);//啥？
        String dir = this.getCacheDir()+"/baidudata";
        settings.setGeolocationDatabasePath(dir);
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);// 允许请求JS
        settings.setBuiltInZoomControls(true);

        //使网页在WEBVIEW中直接打开
        try{
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    view.loadUrl(url);
                } //屏蔽掉错误的重定向url："baidumap://map/?src=webapp.default.all.callnaonopenwebapp?"
                return super.shouldOverrideUrlLoading(view, url);
            }

        });}catch (Exception e){
            e.printStackTrace();
        }

        //判断是否要显示进度
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    //网页加载完毕，关闭进度对话框
                    closeDialog();
                } else {
                    //网页正在加载，打开进度对话框
                    openDialog(newProgress);
                }
            }
        });
        //获取焦点。//实测，就算不写这个也不会失去响应。
//        webView.requestFocus();


        //打开缓存
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }


    //显示进度对话框
    private void openDialog(int newProgress) {
        if (dialog == null) {
            dialog = new ProgressDialog(WebViewTest.this);
            dialog.setTitle("加载中...");
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setProgress(newProgress);
            dialog.show();
        } else {
            dialog.setProgress(newProgress);
        }
    }


    //关闭进度对话框
    private void closeDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    //重写返回按键的逻辑，前提是确保网页在当前webview中打开
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();//返回上一页面
                return true;
            } else {
                System.exit(0);//退出当前Activity
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
