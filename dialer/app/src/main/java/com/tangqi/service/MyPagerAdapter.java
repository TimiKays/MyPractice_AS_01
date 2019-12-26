package com.tangqi.service;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2018/4/2.
 */

public class MyPagerAdapter extends PagerAdapter {
    private List<View> list_of_view;
    private List<String> list_of_title;

    public MyPagerAdapter(List<View> list_of_view,List<String> list_of_title) {
        super();
        this.list_of_title=list_of_title;
        this.list_of_view=list_of_view;
    }

    //返回所有视图的数量
    @Override
    public int getCount() {
        return list_of_view.size();
    }

    //判断视图是否由对象产生
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    //设置标题
    @Override
    public CharSequence getPageTitle(int position) {
        return list_of_title.get(position);
    }

    //删除页面
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list_of_view.get(position));

    }

    //实例化页面
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list_of_view.get(position));

        return list_of_view.get(position);
    }
}
