package com.tangqi.safecenter.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tangqi.safecenter.R;

/**
 * Setting页面的item自定义组合对象。
 *
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class SettingItem extends RelativeLayout {
    private static final String TAG = "Mylog";
    public SettingItem(Context context) {
        this(context,null);
        Log.i(TAG, "SettingItem: 第一个构造");
    }

    public SettingItem(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        Log.i(TAG, "SettingItem: 第二个构造");
    }

    public SettingItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i(TAG, "SettingItem: 第三个构造");
        View settingItem =  View.inflate(context, R.layout.item_setting, this);
        Log.i(TAG, "SettingItem: 已添加view到自定义空间组合："+R.layout.item_setting);

        TextView tv_update = (TextView) findViewById(R.id.tv_update);
        TextView tv_update_state = (TextView) findViewById(R.id.tv_update_state);
        CheckBox cb_update = (CheckBox) findViewById(R.id.cb_update);


    }


}
