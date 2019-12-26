package com.tangqi.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class MySQLite extends SQLiteOpenHelper {
    /**
     * 构造方法
     * @param context 上下文
     *
     */
    public MySQLite(Context context) {
        super(context, "mySqlite.db", null, 2);
    }

    /**
     * 当数据库第一次创建的时候调用，这个方法适合做表结构的初始化。
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //id一般以_id开始
        db.execSQL("create table info(_id integer primary key autoincrement,name varchar(20),age varchar(20))");
    }

    /**
     * 当数据库版本升级的时候调用，这个方法适合做表结构的更新。
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("alter table info add phone varchar(20)");
    }
}
