package com.tangqi.listviewtest_new;

import android.content.Context;
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
 * Created by Timi on 2017/11/7.
 */

public class FruitsAdapter extends ArrayAdapter<Fruits> {
    private int resourceid;

    public FruitsAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Fruits> objects) {
        super(context, resource, objects);
        this.resourceid = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Fruits fruit = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceid,parent,false);

        ImageView fruitImage = (ImageView)view.findViewById(R.id.fruit_image);
        TextView fruitName = (TextView)view.findViewById(R.id.fruit_name);
        fruitImage.setImageResource(fruit.getImage());
        fruitName.setText(fruit.getName());
        return view;



    }
}
