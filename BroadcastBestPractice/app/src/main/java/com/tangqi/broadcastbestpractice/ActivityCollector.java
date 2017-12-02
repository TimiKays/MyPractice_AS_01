package com.tangqi.broadcastbestpractice;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Timi on 2017/12/2.
 */

public class ActivityCollector {
    public static List<Activity> activities= new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void finishAll(){
        for (Activity a:activities){
            if(!a.isFinishing()){
                a.finish();
            }
        }
        activities.clear();//这是什么意思？
    }

}
