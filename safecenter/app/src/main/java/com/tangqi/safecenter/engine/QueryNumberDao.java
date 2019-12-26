package com.tangqi.safecenter.engine;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class QueryNumberDao {
    private static final String TAG = "Mylog";
    private static String belongs;
    public static String path = "data/data/com.tangqi.safecenter/files/address.db";

    public static String queryNumber( String number) {
        belongs = "未知号码";
        Log.i(TAG, "queryNumber: 开始查询");
        if (number.length() != 11) {
//           belongs="暂时只支持11位手机号查询";
            return belongs;
        }

        String subNumber = number.substring(0, 7);
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase
                .OPEN_READONLY);
        Cursor cursor = db.query("data1", new String[]{"outkey"}, "id=?", new
                String[]{subNumber}, null, null, null);
        if (cursor.moveToNext()) {
            String outkey = cursor.getString(0);
            Log.i(TAG, "queryNumber: outkey是" + outkey);
            Cursor cursor2 = db.query("data2", new String[]{"location"}, "id=?", new
                    String[]{outkey}, null, null, null);
            if (cursor2.moveToNext()) {
                belongs = cursor2.getString(0);
            }else{
                Log.i(TAG, "queryNumber: 数据库错误");
            }

        }

        return belongs;

    }


}
