package com.tangqi.contactstest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    List<String> contactsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //为列表设置适配器
        ListView contactsView = (ListView)findViewById(R.id.contacts_view);//找到列表的对象
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,contactsList);//把适配器的样式和数据源设置好
        contactsView.setAdapter(adapter);

        //如果获取了读取联系人的权限，就读，没有就获取权限先
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);
        }else{
            readContacts();
        }
    }

    private void readContacts() {
        Cursor cursor = null;//这是什么鬼？
        try{
            // 查询联系人数据
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
            if(cursor!=null){
                while(cursor.moveToNext()){
                    String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));//获取联系人姓名
                    String number = cursor.getString( cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)); //获取联系人号码
                    contactsList.add(displayName+"\n"+number);
                }
                adapter.notifyDataSetChanged();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if(cursor != null){
                cursor.close();
            }
        }
    }

    //判断上面请求权限的结果，授权成功就读取联系人，失败就toast提示
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    readContacts();
                }else{
                    Toast.makeText(this,"你拒绝了授权",Toast.LENGTH_SHORT).show();
                    
                }
        }
    }
}
