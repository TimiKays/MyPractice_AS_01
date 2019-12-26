package com.tangqi.safecenter.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class ServiceUtil {


    /**
     * 判断服务是否运行
     */
    public static boolean isRunning(Context mc, final String className) {
        ActivityManager activityManager = (ActivityManager) mc.getSystemService(Context
                .ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> info = activityManager.getRunningServices
                (Integer.MAX_VALUE);
        if (info == null || info.size() == 0)
            return false;
        for (ActivityManager.RunningServiceInfo aInfo : info) {
            if (className.equals(aInfo.service.getClassName()))
                return true;
        }
        return false;
    }
}
