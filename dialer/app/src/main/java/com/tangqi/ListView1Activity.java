package com.tangqi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tangqi.service.RpTestActivity;

public class ListView1Activity extends AppCompatActivity implements AdapterView
        .OnItemClickListener {
    ArrayAdapter<String> textAdapter;
    Context mContext;
    String[] data = {"日期选择器", "GridView", "单行文本下拉列表", "带图片的listView", "带图片的下拉列表", "ProgressBar",
            "WebView_百度一下", "Fragment", "ViewPager", "ViewFlipper_轮播图", "Gallery",
            "SeekBar_可拖动进度条", "登录并记住密码", "Toast_全集", "Dialog_全集", "XML的序列化", "SQLite", "网页源码查看器",
            "查看网络图片", "看新闻", "登录_联网版", "多线程下载", "人品计算器", "祝福短信","内容提供器","动画"};
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view1);

        lv = findViewById(R.id.listview);

        mContext = this;

        textAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        lv.setAdapter(textAdapter);
        lv.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                Intent intent0 = new Intent(mContext, DatePickerActivity.class);
                startActivity(intent0);
                break;
            case 1:
                Intent intent1 = new Intent(mContext, GridViewTest.class);
                startActivity(intent1);
                break;
            case 2:
                Intent intent2 = new Intent(mContext, SpinnerTest.class);
                startActivity(intent2);
                break;
            case 3:
                Intent intent3 = new Intent(mContext, ListView2Activity.class);
                startActivity(intent3);
                break;
            case 4:
                Intent intent4 = new Intent(mContext, SpinnerWithImg.class);
                startActivity(intent4);
                break;
            case 5:
                Intent intent5 = new Intent(mContext, ProgressBarTest.class);
                startActivity(intent5);
                break;
            case 6:
                Intent intent6 = new Intent(mContext, WebViewTest.class);
                startActivity(intent6);
                break;
            case 7:
                Intent intent7 = new Intent(mContext, FragmentDemo.class);
                startActivity(intent7);
                break;
            case 8:
                Intent intent8 = new Intent(mContext, ViewPagerTest.class);
                startActivity(intent8);
                break;
            case 9:
                Intent intent9 = new Intent(mContext, ViewFlipperTest.class);
                startActivity(intent9);
                break;
            case 10:
                Intent intent10 = new Intent(mContext, GalleryTest.class);
                startActivity(intent10);
                break;
            case 11:
                Intent intent11 = new Intent(mContext, SeekBarTest.class);
                startActivity(intent11);
                break;
            case 12:
                Intent intent12 = new Intent(mContext, Log_in.class);
                startActivity(intent12);
                break;
            case 13:
                Intent intent13 = new Intent(mContext, Toast_test.class);
                startActivity(intent13);
                break;
            case 14:
                Intent intent14 = new Intent(mContext, DialogTest.class);
                startActivity(intent14);
                break;
            case 15:
                Intent intent15 = new Intent(mContext, XmlStorage.class);
                startActivity(intent15);
                break;
            case 16:
                Intent intent16 = new Intent(mContext, MySqliteDemo.class);
                startActivity(intent16);
                break;
            case 17:
                Intent intent17 = new Intent(mContext, WebCodeView.class);
                startActivity(intent17);
                break;
            case 18:
                Intent intent18 = new Intent(mContext, WebImageView.class);
                startActivity(intent18);
                break;
            case 19:
                Intent intent19 = new Intent(mContext, NewsClient.class);
                startActivity(intent19);
                break;
            case 20:
                Intent intent20 = new Intent(mContext, SubmitToServer.class);
                startActivity(intent20);
                break;
            case 21:
                Intent intent21 = new Intent(mContext, MultiThreadDownload.class);
                startActivity(intent21);
                break;
            case 22:
                Intent intent22 = new Intent(mContext, RpTestActivity.class);
                startActivity(intent22);
                break;
            case 23:
                Intent intent23 = new Intent(mContext, WishActivity.class);
                startActivity(intent23);
                break;
            case 24:
                Intent intent24 = new Intent(mContext, ContentProviderActivity.class);
                startActivity(intent24);
                break;
            case 25:
                Intent intent25 = new Intent(mContext, AnimationActivity.class);
                startActivity(intent25);
                break;
        }
    }
}
