package com.zhihuishu.innovationcourse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Timi on 2017/10/29.
 */

public class Teacher {
    private String name;
    private int img;
    private String info;

    public Teacher(String name,int img,String info){
        this.name=name;
        this.img=img;
        this.info=info;
    }

    public String getName() {
        return name;
    }

    public int getImg() {
        return img;
    }

    public String getInfo() {
        return info;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public static List<Teacher> getAllTeachers(){
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(new Teacher("毛泽东",R.drawable.mzd,"开国元勋，第一代领导人"));
        teachers.add(new Teacher("邓小平",R.drawable.dxp,"什么总设计师，第二代领导人"));
        teachers.add(new Teacher("周恩来",R.drawable.zel,"国家主席，翩翩君子"));
        return teachers;
        }
}
