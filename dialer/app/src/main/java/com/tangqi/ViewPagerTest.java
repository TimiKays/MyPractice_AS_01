package com.tangqi;

import android.graphics.Color;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tangqi.service.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerTest extends AppCompatActivity {

    private List<View> list_of_view;
    private List<String> list_of_title;
    private ViewPager myViewPager;
    private PagerTabStrip pagerTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_test);
        myViewPager=findViewById(R.id.my_view_pager);
        list_of_view= new ArrayList<View>();
        list_of_title= new ArrayList<String>();
        pagerTabs=findViewById(R.id.pager_tabs);

        View view1= View.inflate(this,R.layout.view_page1,null);
        View view2= View.inflate(this,R.layout.view_page2,null);
        View view3= View.inflate(this,R.layout.view_page3,null);
        View view4= View.inflate(this,R.layout.view_page4,null);

        list_of_view.add(view1);
        list_of_view.add(view2);
        list_of_view.add(view3);
        list_of_view.add(view4);

        list_of_title.add("页面一");
        list_of_title.add("页面二");
        list_of_title.add("页面三");
        list_of_title.add("页面四");

        //为PAGERTAB设置属性
        pagerTabs.setBackgroundColor(Color.LTGRAY);
        pagerTabs.setDrawFullUnderline(false);//是否显示下面那一条长线
        pagerTabs.setTabIndicatorColor(Color.YELLOW);//选中状态下那条粗线的颜色
        pagerTabs.setTextColor(Color.BLACK);

//        myViewPager.setOffscreenPageLimit(5);

        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(list_of_view,list_of_title);
        myViewPager.setAdapter(myPagerAdapter);

    }
}
