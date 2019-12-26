package com.tangqi;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Xml;
import android.view.View;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;

public class ContentProviderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provicer);
    }

    //备份短信
    public void backSms(View view) {
        XmlSerializer serializer = Xml.newSerializer();

        //判断外部存储设备是否挂载
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            File file = new File(getExternalFilesDir(null), "SmsBack.xml");
            try {
                FileOutputStream fos = new FileOutputStream(file, false);
                serializer.setOutput(fos, "utf-8");

                //开始写xml文档开头
                serializer.startDocument("utf-8", true);

                //开始写根节点
                serializer.startTag(null, "smss");

                //获取短信，并循环完成短信节点
                Uri uri = Uri.parse("content://sms");
                Cursor cursor = getContentResolver().query(uri, new String[]{"address", "date",
                        "body"}, null, null, null);
                while (cursor.moveToNext()) {
                    //获取查询结果
                    String address = cursor.getString(0);
                    String date = cursor.getString(1);
                    String body = cursor.getString(2);

                    //写sms节点
                    serializer.startTag(null, "sms");

                    //写地址
                    serializer.startTag(null, "address");
                    serializer.text(address);
                    serializer.endTag(null, "address");

                    //写数据
                    serializer.startTag(null, "date");
                    serializer.text(date);
                    serializer.endTag(null, "date");

                    //写body
                    serializer.startTag(null, "body");
                    serializer.text(body);
                    serializer.endTag(null, "body");

                    serializer.endTag(null, "sms");
                }


                serializer.endTag(null, "smss");

                serializer.endDocument();

            } catch (Exception e) {
                e.printStackTrace();
            }
            //否则提示，且不作处理
        } else {
            Toast.makeText(this, "您的外部存储设备有问题", Toast.LENGTH_SHORT).show();
        }

    }
}
