package com.tangqi;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/3/31.
 */

public class Fragment1 extends Fragment {

    private View view;
    private Button button_fg1;
    private EditText et;
    public MyListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //这里的activity是当前fragment1所在的
        listener=(MyListener)activity;

    }

    @Nullable


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_1, container, false);
        button_fg1 = view.findViewById(R.id.button_fg1);

        final TextView text_fg1 = view.findViewById(R.id.text_fg1);
        //点击按钮后，数据传递到activity
        button_fg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et = view.findViewById(R.id.edit_pass);
                String text_to_pass = et.getText().toString();
                listener.passData(text_to_pass);



//                Intent intent = new Intent(getActivity(),Fragment_silent.class);
//                startActivity(intent);

            }
        });
        return view;
    }

    //接口
    public interface MyListener{
        public void passData(String data);
    }
}
