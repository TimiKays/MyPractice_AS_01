package com.tangqi.weixin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class weixin extends AppCompatActivity {

//    这是简单的列表的数据源
//    private static final String[] contractor = new String[]{
//            "李梦婷","李林燕","岳思洁","李媛媛"
//    };
    private ListView listView;
    private List<Contacts> contacts = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weixin);

        //获取ListView的对象。
        listView = (ListView)findViewById(R.id.dialogs);

        //初始化数据源，这里写方法实现。
        initContacts();
        //这是简单的列表的适配器。listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,contractor));

        //设置适配器
        ContactsAdapter adapter = new ContactsAdapter(this,R.layout.contacts_single,contacts);
        listView.setAdapter(adapter);
    }

    //初始化数据源
    private void initContacts() {
        Contacts c1 = new Contacts(R.drawable.c1,"李梦婷");
        Contacts c2 = new Contacts(R.drawable.c2,"岳思洁");
        Contacts c3 = new Contacts(R.drawable.c3,"王霞");
        contacts.add(c1);
        contacts.add(c2);
        contacts.add(c3);
    }
}
