package com.tangqi;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/3/31.
 */

public class Fragment2 extends Fragment implements View.OnTouchListener{
    private TextView tv;
    private TextView poem;
    private ScrollView scrollView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2,null);
        tv=view.findViewById(R.id.fragment_text_toshow);
        poem=view.findViewById(R.id.text_poem);

        scrollView=view.findViewById(R.id.scroll_view);
        scrollView.setOnTouchListener(this);

        poem.setText(getResources().getString(R.string.poem));
        //怎么判断bundle里有没有数据呢
        try {
            String text = getArguments().get("frag1").toString();
            setText(text);
        }catch (Exception e){
            Toast.makeText(getActivity(),"没有数据",Toast.LENGTH_SHORT);
        }

        return view;

    }

    public void setText(String text){
        tv.setText(text);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()){
                case MotionEvent.ACTION_MOVE:
                    if(scrollView.getScrollY()<=0){
//                            Toast.makeText(getActivity(),"刷新成功!",Toast.LENGTH_SHORT);
                        Log.i("scroll","到顶部");
                    }
                    if (scrollView.getChildAt(0).getMeasuredHeight()<=scrollView.getHeight()+scrollView.getScrollY()){
//                        Toast.makeText(getActivity(),"已经到底了啦，憋划了！!",Toast.LENGTH_SHORT);
                        Log.i("scroll","到底了");
                    }
                    break;

            }
            return false;

    }
}
