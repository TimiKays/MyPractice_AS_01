package com.tangqi;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListView2Activity extends AppCompatActivity implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener{

    private ListView listView;
    private List<Map<String,Object>> data;
    private SimpleAdapter adapter;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view2);

        listView=findViewById(R.id.list_view2);
        mContext=this;
        data = new ArrayList<Map<String,Object>>();
        adapter=new SimpleAdapter(mContext,getData(),R.layout.item,new String[]{"image","text"},new int[]{R.id.list_img,R.id.list_text});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(this);
    }

    public List<Map<String,Object>> getData(){
        for (int i=1;i<10;i++){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("image",R.mipmap.ic_launcher);
            map.put("text","列表项"+i);
            data.add(map);
        }
        return data;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState){
            case SCROLL_STATE_FLING:
                Log.d("Scroll","由于惯性滑动");

                break;
            case SCROLL_STATE_IDLE:
                Log.i("Scroll","视图停止滑动");
                break;
            case SCROLL_STATE_TOUCH_SCROLL:
                Log.i("Scroll","手指触摸滑动");
                Map<String,Object> map=new HashMap<String,Object>();
                map.put("image",R.mipmap.ic_launcher);
                map.put("text","新增项");
                data.add(map);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
