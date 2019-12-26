package com.tangqi.service;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tangqi.R;
import com.tangqi.domain.Student;

import java.util.List;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */

public class AdapterOfSql extends BaseAdapter {
    private List<Student> mlist;
    private Context mContext;

    public AdapterOfSql(List<Student> list, Context context) {
        mlist = list;
        mContext = context;
    }


    /**
     * 必须重写的方法
     *
     * @return 一共有多少条数据需要显示
     */
    @Override
    public int getCount() {
        return mlist.size();
    }

    /**
     * @param position
     * @return 返回指定position位置对应的对象。
     */
    @Override
    public Object getItem(int position) {
        return null;
    }

    /**
     * @param position
     * @return 返回指定position位置对应的对象的ID。
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * 必须重写的方法
     *
     * @param position    第几个Item
     * @param convertView 历史缓存对象
     * @param parent      父控件，一般没有
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            //打气筒，把布局文件变成view对象
            view = View.inflate(mContext, R.layout.item_sqlite, null);
        } else {
            view = convertView;
        }

        //从集合中获取student对象
        Student student = mlist.get(position);

        //找到item布局的控件
        TextView id = view.findViewById(R.id.item_id);
        TextView name = view.findViewById(R.id.item_name);
        TextView age = view.findViewById(R.id.item_age);

        //设置控件的显示文本为获取的对象的成员变量的值。
        id.setText(student.getId());
        name.setText(student.getName());
        age.setText(student.getAge());

        return view;
    }
}
