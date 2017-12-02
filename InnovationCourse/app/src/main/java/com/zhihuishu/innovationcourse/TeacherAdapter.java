package com.zhihuishu.innovationcourse;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


/**
 * Created by Timi on 2017/10/29.
 */

public class TeacherAdapter extends ArrayAdapter<Teacher> {
    public TeacherAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Teacher> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // 获取老师的数据
        final Teacher teacher = getItem(position);

        // 创建布局
        View oneTeacherView = LayoutInflater.from(getContext()).inflate(R.layout.teacher_item, parent, false);

        // 获取ImageView和TextView
        ImageView imageView = (ImageView) oneTeacherView.findViewById(R.id.teacherImage);
        TextView textView = (TextView) oneTeacherView.findViewById(R.id.teacherName);

        // 根据老师数据设置ImageView和TextView的展现
        imageView.setImageResource(teacher.getImg());
        textView.setText(teacher.getName());

        //后来加入的用作点击事件的监听器
        oneTeacherView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  初始化一个准备跳转到TeacherDetailActivity的Intent
                Intent intent = new Intent(getContext(), TeacherDetailActivity.class);
                // 往Intent中传入Teacher相关的数据，供TeacherDetailActivity使用
                intent.putExtra("teacher_image", teacher.getImg());
                intent.putExtra("teacher_desc", teacher.getInfo());
                // 准备跳转
                getContext().startActivity(intent);
            }
        });

        return oneTeacherView;
    }



}
