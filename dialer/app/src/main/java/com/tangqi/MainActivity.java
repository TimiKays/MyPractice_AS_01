package com.tangqi;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tangqi.service.PermissionUtils;
import com.tangqi.service.Table;

public class MainActivity extends Activity implements View.OnClickListener/*,ActivityCompat
.OnRequestPermissionsResultCallback*/ {

    Intent intent = new Intent();
    private EditText et;
    private Button call;
    private Button go_relative;
    private Button go_table;
    private TextView showText;
    private Button calculator;
    private Button list_view1;
    private final String[] PERMISSIONS = {Manifest.permission.INTERNET,Manifest.permission.CALL_PHONE,Manifest.permission.VIBRATE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = findViewById(R.id.editText);
        call = findViewById(R.id.call);
        calculator = findViewById(R.id.calculator);
        showText = findViewById(R.id.showText);
        go_relative = findViewById(R.id.go_relative);
        go_table = findViewById(R.id.go_table);
        list_view1 = findViewById(R.id.list_view1);



        call.setOnClickListener(this);
        go_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Relative_Activity.class);
                startActivityForResult(intent, 1);
            }
        });


        go_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, Table.class);
                startActivity(intent2);
            }
        });

        calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ca = new Intent(MainActivity.this, Calculator.class);
                Log.i("tag", "intent封好了");
                startActivity(ca);
            }
        });

        list_view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListView1Activity.class);
                startActivity(intent);
            }
        });

//        UtilRequestPermissions urp = new UtilRequestPermissions(this);
//        urp.performCodeWithPermission("请求这些权限，请通过！", new UtilRequestPermissions.PermissionCallback() {
//            @Override
//            public void hasPermission() {
//                Toast.makeText(getApplicationContext(),"恭喜你，可以愉快地玩耍了",Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void noPermission() {
//                Toast.makeText(getApplicationContext(),"请求权限失败",Toast.LENGTH_SHORT).show();
//            }
//        },new String[]{Manifest.permission.INTERNET,Manifest.permission.CALL_PHONE,Manifest.permission.VIBRATE});
        PermissionUtils.checkAndRequestMorePermissions(this, PERMISSIONS, 2, new PermissionUtils.PermissionRequestSuccessCallBack() {
            @Override
            public void onHasPermission() {
                Toast.makeText(MyApplication.getContext(),"权限申请成功",Toast.LENGTH_SHORT).show();
            }
        });


    }

    //这是这个活动和相对布局活动传递信息用的
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (
                requestCode == 1 && resultCode == 1
                ) {
            String number = data.getStringExtra("number");
            showText.setText(number);
        }
    }

    @Override
    public void onClick(View v) {
        //sdk23版本及以上需要检查权限，调用申请权限方法
        requestCallPermission();
    }

    //检查权限，申请权限
    private void requestCallPermission() {
        //首先检查是否有权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            //没有权限，则申请权限,并且系统会回调下一个方法。
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                    1);
            Toast.makeText(this, "正在授权", Toast.LENGTH_SHORT).show();
        } else {
            //有权限，则直接拨打
            callTheNumber();
        }
    }




    /**
     * 根据requestCode和grantResults(授权结果)做相应的处理。
     * @param requestCode 请求码
     * @param permissions 请求的权限
     * @param grantResults 用户操作的结果
     */
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted 获得权限后执行xxx
                callTheNumber();
            } else {
                // Permission Denied 拒绝后xx的操作。
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
                //                handleCallPermission();
            }

        }


    }



    //DIY提示...还没搞懂，先放在这里。
    //    @RequiresApi(api = Build.VERSION_CODES.M)
    //    private void handleCallPermission() {
    //        if (Integer.parseInt(Build.VERSION.SDK) >= 23) {
    //            int hasWriteContactsPermission = checkSelfPermission(Manifest.permission
    // .CALL_PHONE);
    //            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
    //                if (!shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
    //                    showMessageOKCancel("必须同意，不然就别玩了。", new DialogInterface.OnClickListener
    // () {
    //                        @Override
    //                        public void onClick(DialogInterface dialog, int which) {
    //                            requestCallPermission();//确定后申请权限。
    //                        }
    //                    });
    //                    return;
    //                }
    //                //requestCallPermission();//没有权限的话，申请。
    //                callTheNumber();
    //            }
    //        }
    //    }

    //拨打电话的方法
    public void callTheNumber() {
        String number = et.getText().toString().trim();
        //设置动作
        intent.setAction(Intent.ACTION_CALL);
        //这里需要URI类型的数据
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);

    }


    //    private void showMessageOKCancel(String message, DialogInterface.OnClickListener
    // okListener) {
    //        new AlertDialog.Builder(MainActivity.this)
    //                .setMessage(message)
    //                .setPositiveButton("同意", okListener)
    //                .setNegativeButton("就不", null)
    //                .create()
    //                .show();
    //    }


    //设置菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //设置菜单项的点击事件

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item1:
                Toast.makeText(this, "点击了菜单1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_item2:
                Toast.makeText(this, "点击了菜单2", Toast.LENGTH_SHORT).show();
                break;


        }
        return super.onOptionsItemSelected(item);
    }
}


