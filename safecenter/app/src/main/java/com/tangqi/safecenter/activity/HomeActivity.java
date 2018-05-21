package com.tangqi.safecenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.tangqi.safecenter.R;

public class HomeActivity extends AppCompatActivity {
    private String[] gridName = {"手机防盗", "通信卫士", "软件管理", "进程管理", "流量统计", "手机杀毒", "缓存清理", "高级工具",
            "设置中心"};
    private int[] gridPic = {R.drawable.home_safe, R.drawable.home_callmsgsafe, R.drawable
            .home_apps, R.drawable.home_taskmanager, R.drawable.home_netmanager, R.drawable
            .home_trojan, R.drawable.home_sysoptimize, R.drawable.home_tools, R.drawable
            .home_settings};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        GridView gv_function_list = (GridView) findViewById(R.id.gv_function_list);
        gv_function_list.setAdapter(new gvAdapter());
        gv_function_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 8:
                        Intent intent=new Intent(HomeActivity.this,SettingActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }


    private class gvAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return gridName.length;
        }

        @Override
        public Object getItem(int position) {
            return gridName[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.grid_item_home, null);
            ImageView mode_pic = view.findViewById(R.id.mode_pic);
            TextView mode_name = view.findViewById(R.id.mode_name);

            mode_pic.setBackgroundResource(gridPic[position]);
            mode_name.setText(gridName[position]);


            return view;
        }
    }
}
