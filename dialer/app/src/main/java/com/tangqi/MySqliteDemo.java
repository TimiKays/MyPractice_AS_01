package com.tangqi;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.tangqi.domain.Student;
import com.tangqi.service.AdapterOfSql;
import com.tangqi.service.MySQLite;

import java.util.ArrayList;
import java.util.List;

public class MySqliteDemo extends AppCompatActivity implements View.OnClickListener {

    private Button bt_insert;
    private Button bt_delete;
    private Button bt_update;
//    private Button bt_select;
    private SQLiteDatabase db;
    private MySQLite mySQLite;
    private List<Student> mlist;
    private List<Student> zero_list;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sqlite_demo);

        bt_insert = findViewById(R.id.bt_insert);
        bt_delete = findViewById(R.id.bt_delete);
        bt_update = findViewById(R.id.bt_update);
//        bt_select = findViewById(R.id.bt_select);
        mListView=findViewById(R.id.lv_sql);

        bt_insert.setOnClickListener(this);
        bt_delete.setOnClickListener(this);
        bt_update.setOnClickListener(this);
//        bt_select.setOnClickListener(this);

        //得到一个SQLiteOpenHelper的实例
        mySQLite = new MySQLite(this);
        mlist= new ArrayList<Student>();
        zero_list= new ArrayList<Student>();
        zero_list.add(new Student(null,null,"暂无数据"));



    }

    @Override
    public void onClick(View v) {
        //打开或创建数据库
        mlist.clear();
        db = mySQLite.getWritableDatabase();
        ContentValues values = new ContentValues();
        switch (v.getId()) {
            //插入
            case R.id.bt_insert:
                //                db.execSQL("insert into info(name,phone) values('王五',
                // '15088888888')");

                values.put("name", "王五");
                values.put("age", "15");

                long insert_result = db.insert("info", null, values);
                if (insert_result > 0) {
                    Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                }
                break;

            //删除
            case R.id.bt_delete:
                //                db.execSQL("delete from info where name='王五';");
                int delete_result = db.delete("info", "age=?", new String[]{"20"});
                if (delete_result > 0) {
                    Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                }
                break;

            //更新
            case R.id.bt_update:
                //                db.execSQL("update info set age='1322254136' where name='王五';");
                values.put("age", "20");
                int update_result = db.update("info", values, "name=?", new String[]{"王五"});
                if (update_result > 0) {
                    Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show();
                }
                break;

            //选择
//            case R.id.bt_select:
                //Cursor cursor = db.rawQuery("select name,age from info", null);
                //Cursor cursor = db.query("info",new String[]{"name","age"},
                // "name=?",new String[]{"王五"},null,null,null);

//                break;
        }
        Cursor cursor = db.query("info", null, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                //Log.d("test", "cursor位置：" + cursor.getPosition());
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String age = cursor.getString(2);
                Log.d("test", "id=" + id + ",name=" + name + ",age=" + age);
                mlist.add(new Student(name,age,id));
                mListView.setAdapter(new AdapterOfSql(mlist,this));
            }
        }else{
            mListView.setAdapter(new AdapterOfSql(zero_list,this));
        }
        values.clear();
        db.close();

    }
}
