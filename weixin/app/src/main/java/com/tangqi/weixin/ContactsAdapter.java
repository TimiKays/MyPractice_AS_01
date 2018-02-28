package com.tangqi.weixin;

import android.content.Context;
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
 * Created by Timi on 2018/1/30.
 * 适配器。
 */

public class ContactsAdapter extends ArrayAdapter {

    private int resourceId;//这是干嘛的？

    public ContactsAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);//这接收的三个参数是：上下文，布局文件的资源ID，要在ListView中显示的对象
        resourceId=resource;//这是指在构造方法中把获取到的布局文件ID赋值给成员变量。
    }

    @NonNull
    @Override
    //每当有新的子项滚入屏幕时，getView()方法都会被调用。
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        ViewHolder holder;
        Contacts contacts =(Contacts)getItem(position);

        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            holder=new ViewHolder();
            holder.image = (ImageView) view.findViewById(R.id.contacts_image);
            holder.name = (TextView) view.findViewById(R.id.contacts_name);
            view.setTag(holder);
        }else{
            view = convertView;
            holder = (ViewHolder)view.getTag();
        }

        holder.image.setImageResource(contacts.getImage());
        holder.name.setText(contacts.getName());

        return view;

    }

    class ViewHolder{
        ImageView image;
        TextView name;;
    }
}
