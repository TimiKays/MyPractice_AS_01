package com.tangqi;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class DatePickerActivity extends AppCompatActivity {

    private TextView date;
    private TextView time;
    private Button edit_date;
    private Button edit_time;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private Context mc;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        setTitle("时间选择器");

        //初始化
//        datePicker =findViewById(R.id.date_picker);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        edit_date = findViewById(R.id.edit_date);
        edit_time = findViewById(R.id.edit_time);
        mc = this;
        title=findViewById(R.id.commom_title);

        title.setText("日期选择器");


        //获取Calender的实例
        Calendar c = Calendar.getInstance();
        //得到当前日期
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        //显示出来
        date.setText("日期" + year + "-" + (month + 1) + "-" + day);
        time.setText("时间" + hour + ":" + minute);

        /*这是在当前页面设置日期，需要定义一个控件，我觉得很不好看啊。
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.setText("日期"+year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
            }
        });

        在当前页面设置时间选择器的话，不需要初始化哦！默认就是当前时间。
        timePicker.setOnTimeChangedListener(new OnTimeChangedListener(){
            public void onTimeChanged(TimePicker view,int hourOfDay,int minute){
            time.setText("时间"+hourOfDay+":"+minute);
            }
        })

*/
        edit_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(mc, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText("日期" + year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, year, month, day).show();
            }
        });

        edit_time.setOnClickListener(new View.OnClickListener() {
            @Override
            //是否24小时制别忘记了。
            public void onClick(View v) {
                new TimePickerDialog(mc, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time.setText("时间" + hourOfDay + ":" + minute);
                    }
                }, hour, minute, true).show();
            }
        });

    }
}
