package com.tangqi.safecenter.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class ToastUtil {



    /**
     * 显示吐司的方法
     *
     * @param context
     * @param text
     */
    public static void show(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();

    }
}
