package com.tangqi.safecenter.utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class SubViewsControlUtil {

    private static String TAG="Mylog";

    /**
     * 遍历单层布局，并禁用所有子控件
     *
     * @param viewGroup   布局对象
     * @param  setEnable  设置是否可用
     *
     */
    public static void setSubViewEnable(ViewGroup viewGroup, Boolean setEnable) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View v = viewGroup.getChildAt(i);
                v.setClickable(setEnable);
                v.setEnabled(setEnable);
                if(setEnable){
                    v.setAlpha(1f);
                }else{
                    v.setAlpha(0.3f);
                }
        }
    }
}
