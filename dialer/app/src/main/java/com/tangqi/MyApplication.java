package com.tangqi;

import android.app.Application;
import android.content.Context;

import org.xutils.x;

/**
 * 本宝宝自建的，用于提供上下文的类，管理程序内的全局信息
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
