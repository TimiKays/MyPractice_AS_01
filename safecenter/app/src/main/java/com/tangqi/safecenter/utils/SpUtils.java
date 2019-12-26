package com.tangqi.safecenter.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 方便处理SharePreference的工具类。
 *
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class SpUtils {

    private static SharedPreferences sp;
    private static String TAG="MyLog";

    /**
     * 存储设置状态到sp的方法
     * @param context 上下文
     * @param key   键
     * @param value 值
     */
    public static void putState(Context context, String key, Boolean value){
        if(sp==null){
            sp = context.getSharedPreferences("SettingStates", Context
                    .MODE_PRIVATE);
        }
        try {
            SharedPreferences.Editor edit = sp.edit();
            edit.putBoolean(key,value).commit();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }


    /**
     * 获取对应键的布尔值。
     * @param context   上下文
     * @param key   键
     * @param defaultValue  没有获取到值时的默认值。
     * @return  返回状态的布尔值
     */
    public static boolean getState(Context context, String key, Boolean defaultValue){
        if(sp==null){
            sp = context.getSharedPreferences("SettingStates", Context
                    .MODE_PRIVATE);
        }
//        Log.i(TAG, "key:"+key);
//        Log.i(TAG, "defaultValue:"+defaultValue);
//        Log.i(TAG, "getBoolean(key,defaultValue):"+sp.getBoolean(key,defaultValue));

        return sp.getBoolean(key,defaultValue);

    }

    /**
     * 存储密码,密码的值用Md5Util加密了。
     * @param context
     * @param key
     * @param value
     */
    public static void putString(Context context, String key, String value,Boolean encode){
        if(sp==null){
            sp = context.getSharedPreferences("SettingStates", Context
                    .MODE_PRIVATE);
        }
        try {
            SharedPreferences.Editor edit = sp.edit();
            if(encode){

                String codeValue=Md5Util.encoder(value);
                edit.putString(key,codeValue).commit();
            }else {
                edit.putString(key,value).commit();
            }

        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }



    /**
     * 移除节点
     * @param context
     * @param key
     */
    public static void removeString(Context context, String key){
        if(sp==null){
            sp = context.getSharedPreferences("SettingStates", Context
                    .MODE_PRIVATE);
        }
        try {
            SharedPreferences.Editor edit = sp.edit();
            edit.remove(key).commit();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    /**
     * 获取字符串
     * @param context
     * @param key
     * @param defaultString
     * @return
     */
    public static String getString(Context context, String key, String defaultString){
        if(sp==null){
            sp = context.getSharedPreferences("SettingStates", Context
                    .
                    MODE_PRIVATE);
        }


        return sp.getString(key,defaultString);
    }




    /**
     * 获取int
     * @param context
     * @param key
     * @param defaultNumber
     * @return
     */
    public static int getInt(Context context, String key, int defaultNumber){
        if(sp==null){
            sp = context.getSharedPreferences("SettingStates", Context.MODE_PRIVATE);
        }

        return sp.getInt(key,defaultNumber);
    }

    /**
     * 存储int
     * @param context
     * @param key
     * @param value
     */
    public static void putInt(Context context, String key, int value){
        if(sp==null){
            sp = context.getSharedPreferences("SettingStates", Context
                    .MODE_PRIVATE);
        }
        try {
            SharedPreferences.Editor edit = sp.edit();
           edit.putInt(key,value).commit();

        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }










}
