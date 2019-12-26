package com.tangqi.safecenter.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tangqi.safecenter.db.BlackNumberOpenHelper;
import com.tangqi.safecenter.db.domain.BlackNumberBean;

import java.util.ArrayList;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class BlackNumberDao {

    private static BlackNumberDao mBlackNumberDao = null;
    private final BlackNumberOpenHelper mBlackNumberOpenHelper;

    //单例模式
    //1 私有化构造方法
    private BlackNumberDao(Context context) {
        mBlackNumberOpenHelper = new BlackNumberOpenHelper(context);
    }

    //2 公开方法获取实例
    public static BlackNumberDao getInstance(Context context) {
        if (mBlackNumberDao == null) {
            mBlackNumberDao = new BlackNumberDao(context);
        }
        return mBlackNumberDao;
    }

    /**
     * 新增一条数据
     *
     * @param number 电话号码
     * @param mode   拦截类型
     */
    public void insert(String number, String mode) {
        SQLiteDatabase db = mBlackNumberOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("number", number);
        values.put("mode", mode);
        db.insert("blacknumber", null, values);
        db.close();
    }

    /**
     * 删除一条数据
     *
     * @param number
     */
    public void delete(String number) {
        SQLiteDatabase db = mBlackNumberOpenHelper.getWritableDatabase();
        db.delete("blacknumber", "number=?", new String[]{number});
        db.close();
    }

    /**
     * 更新一条数据
     *
     * @param number
     * @param mode   要更新为什么拦截类型
     */
    public void update(String number, String mode) {
        SQLiteDatabase db = mBlackNumberOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mode", mode);
        db.update("blacknumber", values, "number=?", new String[]{number});
        db.close();
    }

    /**
     * 查询所有数据
     */
    public ArrayList<BlackNumberBean> findAll() {
        SQLiteDatabase db = mBlackNumberOpenHelper.getWritableDatabase();
        Cursor cursor = db.query("blacknumber", new String[]{"number,mode"}, null, null, null,
                null, "_id desc");
        ArrayList<BlackNumberBean> blackNumberBeans = new ArrayList<>();
        while (cursor.moveToNext()) {
            String number = cursor.getString(0);
            String mode = cursor.getString(1);
            BlackNumberBean blackNumberBean = new BlackNumberBean(number, mode);
            blackNumberBeans.add(blackNumberBean);
        }
        cursor.close();
        db.close();
        return blackNumberBeans;
    }

    /**
     * 查询倒叙排列的前20条数据
     */
    public ArrayList<BlackNumberBean> find(int index) {
        SQLiteDatabase db = mBlackNumberOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select number,mode from blacknumber order by _id desc limit ?,20;", new String[]{index + ""});
        ArrayList<BlackNumberBean> blackNumberBeans = new ArrayList<>();
        while (cursor.moveToNext()) {
            String number = cursor.getString(0);
            String mode = cursor.getString(1);
            BlackNumberBean blackNumberBean = new BlackNumberBean(number, mode);
            blackNumberBeans.add(blackNumberBean);
        }
        cursor.close();
        db.close();
        return blackNumberBeans;
    }

    /**
     * 获取数据库条目的总个数
     */
    public int getCount() {
        SQLiteDatabase db = mBlackNumberOpenHelper.getWritableDatabase();
        int count = 0;
        Cursor cursor = db.rawQuery("select count(*) from blacknumber;", null);
        if (cursor.moveToNext()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count;
    }

    /**
     * 根据电话号码查询拦截模式
     * 0表示拦截短信，1表示拦截电话，2表示全部拦截。3表示没查到。
     */
    public int getMode(String phone){
        SQLiteDatabase db = mBlackNumberOpenHelper.getWritableDatabase();
        int mode = 0;
        Cursor cursor = db.rawQuery("select mode from blacknumber where number=?;", new String[]{phone});
        if (cursor.moveToNext()) {
            mode = cursor.getInt(0);
        }
        return mode;
    }


}
