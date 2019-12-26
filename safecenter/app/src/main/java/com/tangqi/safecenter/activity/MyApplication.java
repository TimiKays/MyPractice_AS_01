package com.tangqi.safecenter.activity;

import android.app.Application;
import android.content.Context;

import org.xutils.x;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context=getApplicationContext();

        x.Ext.init(this);
        x.Ext.setDebug(true); //输出debug日志，开启会影响性能
    }

    public static Context getContext(){
        return context;
    }

}
