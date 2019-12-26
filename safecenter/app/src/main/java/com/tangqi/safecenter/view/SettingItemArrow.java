package com.tangqi.safecenter.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tangqi.safecenter.R;

/**
 * Setting页面的item自定义组合对象。
 *
 * @author Admin
 * @version $Rev$
 */
public class SettingItemArrow extends RelativeLayout  {
    private final String TAG = "Mylog";
    private TextView tv_arrow_title;
    private TextView tv_arrow_describe;
    private Context mContext;
    private String NAMESPACE="http://schemas.android.com/apk/res/com.tangqi.safecenter";


    public SettingItemArrow(Context context) {
        this(context, null);
        Log.i(TAG, "SettingItem: 第一个构造");
    }

    public SettingItemArrow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        Log.i(TAG, "SettingItem: 第二个构造");
    }

    public SettingItemArrow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        Log.i(TAG, "SettingItem: 第三个构造");
        View settingItem = View.inflate(context, R.layout.item_setting2, this);
        Log.i(TAG, "SettingItem: 已添加view到自定义空间组合：" + R.layout.item_setting2);

        tv_arrow_title = (TextView) findViewById(R.id.tv_arrow_title);
        tv_arrow_describe = (TextView) findViewById(R.id.tv_arrow_describe);

        //获取自定义属性
        String title = attrs.getAttributeValue(NAMESPACE, "title");
        String def_describe = attrs.getAttributeValue(NAMESPACE, "def_describe");

        Log.i(TAG, "初始化arrow的title："+title);
        Log.i(TAG, "初始化arrow的describe："+def_describe);
        tv_arrow_title.setText(title);
        tv_arrow_describe.setText(def_describe);



    }

    //设置Item名字，调用后直接获取对应item的状态并设置。
    public void setTitle(String title) {
        tv_arrow_title.setText(title);
    }

    //设置描述。
    public void setDes( String des) {
        tv_arrow_describe.setText(des);
    }



}
