package com.tangqi;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.math.BigDecimal;

public class Calculator extends Activity implements View.OnClickListener{
    private EditText et;
    private Button bt_0;
    private Button bt_1;
    private Button bt_2;
    private Button bt_3;
    private Button bt_4;
    private Button bt_5;
    private Button bt_6;
    private Button bt_7;
    private Button bt_8;
    private Button bt_9;
    private Button bt_clear;
    private Button bt_delete;
    private Button bt_plus;
    private Button bt_subtract;
    private Button bt_multiply;
    private Button bt_divide;
    private Button bt_equal;
    private Button bt_point;
    private Double last;
    private Double now;
    private String counter;
    private String cache;
    private Double result;
    private String result_toshow;
    private boolean clearable;
    private boolean haveCounted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        et=findViewById(R.id.result);
        bt_0=findViewById(R.id.button_0);
        bt_1=findViewById(R.id.button_1);
        bt_2=findViewById(R.id.button_2);
        bt_3=findViewById(R.id.button_3);
        bt_4=findViewById(R.id.button_4);
        bt_5=findViewById(R.id.button_5);
        bt_6=findViewById(R.id.button_6);
        bt_7=findViewById(R.id.button_7);
        bt_8=findViewById(R.id.button_8);
        bt_9=findViewById(R.id.button_9);
        bt_clear=findViewById(R.id.button_c);
        bt_delete=findViewById(R.id.button_del);
        bt_plus=findViewById(R.id.button_plus);
        bt_subtract=findViewById(R.id.button_jian);
        bt_multiply=findViewById(R.id.button_multiply);
        bt_divide=findViewById(R.id.button_divide);
        bt_equal=findViewById(R.id.button_equals);
        bt_point=findViewById(R.id.button_point);

        last=null;
        now=null;
        counter=null;
        cache=null;
        result=null;
        result_toshow=null;
        clearable=false;

        bt_0.setOnClickListener(this);
        bt_1.setOnClickListener(this);
        bt_2.setOnClickListener(this);
        bt_3.setOnClickListener(this);
        bt_4.setOnClickListener(this);
        bt_5.setOnClickListener(this);
        bt_6.setOnClickListener(this);
        bt_7.setOnClickListener(this);
        bt_8.setOnClickListener(this);
        bt_9.setOnClickListener(this);
        bt_clear.setOnClickListener(this);
        bt_delete.setOnClickListener(this);
        bt_plus.setOnClickListener(this);
        bt_subtract.setOnClickListener(this);
        bt_multiply.setOnClickListener(this);
        bt_divide.setOnClickListener(this);
        bt_equal.setOnClickListener(this);
        bt_point.setOnClickListener(this);


        /*用数组批量初始化
        *
        * private Button[]Btn= new Button[10];//用数组定义按钮
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContenetiew(R.layout.activity_main);
        int[] b = {R.id.btn_0,R.id.btn_1,R.id.btn_2,R.id.btn_3,R.id.btn_4,R.id.btn_5,
        R.id.btn_6,R.id.btn_7,R.id.btn_8,R.id.btn_9};

        //实例化按钮并设置点击事件
        for(int i = 0;i<10;i++){
            Btn[i] = (Button)findViewById(b[i]);
            Btn[i].setOnClickListener(this);
        }*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //点击数字按钮
            case R.id.button_0:
            case R.id.button_1:
            case R.id.button_2:
            case R.id.button_3:
            case R.id.button_4:
            case R.id.button_5:
            case R.id.button_6:
            case R.id.button_7:
            case R.id.button_8:
            case R.id.button_9:
                if(clearable==false||haveCounted==true){
                    //清零后，或者刚计算完一轮后，直接按数字就会替换屏幕上的数字。
                    et.setText(((Button)v).getText().toString());
                }else{
                    //否则就会在屏幕上追加本数字。
                    et.append(((Button)v).getText().toString());

                }
                haveCounted=false;
                clearable=true;
                break;

            //点击运算符
            case R.id.button_plus:
            case R.id.button_jian:
            case R.id.button_multiply:
            case R.id.button_divide:
                if(now==null){
                    getNumber();
                }else{
                    getNumber();
                    getResult();
                    showResult();
                }
                //把运算符赋值给counter
                counter=((Button)v).getText().toString();
                haveCounted=true;
                break;

            //点击等号
            case R.id.button_equals:
                if(counter.equals("+")||counter.equals("-")||counter.equals("*")||counter.equals("/")){
                    getNumber();
                    getResult();
                    showResult();
                }
                //把运算符赋值给counter
                counter=((Button)v).getText().toString();
                break;


            case R.id.button_point:
                et.append(((Button)v).getText().toString());
                clearable=true;
                break;

            case R.id.button_c:
                last=null;
                now=null;
                counter=null;
                cache=null;
                result=null;
                result_toshow=null;
                et.setText("0");
                clearable=false;

                break;

            case R.id.button_del:
                cache = et.getText().toString();
                if(clearable==true&&cache!="0"){

                    et.setText(cache.substring(0,cache.length()-1));
                }

                break;
        }
    }

    //获取并存储屏幕上的值
    public void getNumber(){
        last=now;
        now=Double.parseDouble(et.getText().toString());
    }

    //计算结果
    public void getResult(){
        BigDecimal biglast=new BigDecimal(last);
        BigDecimal bignow=new BigDecimal(now);

        //根据上一个运算符计算出结果。
        if(counter.equals("+")){
            result=biglast.add(bignow).doubleValue();
        }else if(counter.equals("-")){
            result=biglast.subtract(bignow).doubleValue();
        }else if(counter.equals("*")){
            result=biglast.multiply(bignow).doubleValue();
        }else if(counter.equals("/")){
            result=biglast.divide(bignow).doubleValue();
        }
        haveCounted=true;



    }

    //显示结果
    public void showResult(){
        //如果结果有小数就显示小数，没有就截取小数点前的显示。
        if(Math.round(result)-result==0){
            String result_double=(result+"").substring(0,2);
            result_toshow= result_double.substring(0,result_double.indexOf("."));
        }else{
            result_toshow=String.valueOf(result);
        }
        //显示出来
        et.setText(result_toshow);
        last=now;
        now=result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
