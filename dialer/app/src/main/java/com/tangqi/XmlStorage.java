package com.tangqi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tangqi.domain.Student;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlStorage extends AppCompatActivity implements View.OnClickListener {

    private EditText et_st_id1;
    private EditText et_st_name1;
    private EditText et_st_age1;
    private EditText et_st_id2;
    private EditText et_st_name2;
    private EditText et_st_age2;
    private Button bt_sumit;
    private Button bt_show;
    private TextView tv_show;
    private List<Student> mStudentList;
    private Student s1;
    private Student s2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_storage);

        et_st_id1 = findViewById(R.id.student_id);
        et_st_name1 = findViewById(R.id.student_name);
        et_st_age1 = findViewById(R.id.student_age);
        et_st_id2 = findViewById(R.id.student2_id);
        et_st_name2 = findViewById(R.id.student2_name);
        et_st_age2 = findViewById(R.id.student2_age);
        bt_sumit = findViewById(R.id.student_submit);
        bt_show = findViewById(R.id.student_show);
        tv_show = findViewById(R.id.student_infos);

        bt_sumit.setOnClickListener(this);
        bt_show.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.student_submit:
                String id1 = et_st_id1.getText().toString().trim();
                String name1 = et_st_name1.getText().toString().trim();
                String age1 = et_st_age1.getText().toString().trim();
                String id2 = et_st_id2.getText().toString().trim();
                String name2 = et_st_name2.getText().toString().trim();
                String age2 = et_st_age2.getText().toString().trim();
                //                s1.setId(Integer.parseInt(id1));
                //                s1.setName(name1);
                //                s1.setAge(Integer.parseInt(age1));
                //
                //                mStudentList=new ArrayList<>();
                //                mStudentList.add(s1);


                try {
                    File file = new File(this.getFilesDir(), "student.xml");
                    FileOutputStream fos = new FileOutputStream(file);
                    XmlSerializer serializer = Xml.newSerializer();
                    serializer.setOutput(fos, "utf-8");
                    serializer.startDocument("utf-8", true);

                    serializer.startTag(null, "info");

                    serializer.startTag(null, "student");
                    serializer.attribute(null, "id", id1);
                    serializer.startTag(null, "name");
                    serializer.text(name1);
                    serializer.endTag(null, "name");
                    serializer.startTag(null, "age");
                    serializer.text(age1);
                    serializer.endTag(null, "age");
                    serializer.endTag(null, "student");


                    serializer.startTag(null, "student");
                    serializer.attribute(null, "id", id2);

                    serializer.startTag(null, "name");
                    serializer.text(name2);
                    serializer.endTag(null, "name");

                    serializer.startTag(null, "age");
                    serializer.text(age2);
                    serializer.endTag(null, "age");

                    serializer.endTag(null, "student");
                    serializer.endTag(null, "info");
                    serializer.endDocument();
                    fos.close();
                    Log.d("test", "数据保存成功");


                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("test", "数据保存失败");
                }
                break;
            case R.id.student_show:
                File file = new File(this.getFilesDir(), "student.xml");
                try {
                    FileInputStream fis = new FileInputStream(file);
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setInput(fis, "utf-8");
                    int type = parser.getEventType();
                    while (type != XmlPullParser.END_DOCUMENT) {
                        switch (type) {
                            case XmlPullParser.START_DOCUMENT:
                                mStudentList = new ArrayList<>();
                                break;
                            case XmlPullParser.START_TAG:
                                if ("student".equals(parser.getName())) {
                                    s1 = new Student();
                                    String id = parser.getAttributeValue(0);
                                    s1.setId(id);
                                } else if ("name".equals(parser.getName())) {
                                    s1.setName(parser.nextText());
                                } else if ("age".equals(parser.getName())) {
                                    s1.setAge(parser.nextText());
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                if ("student".equals(parser.getName())) {
                                    mStudentList.add(s1);
                                }
                                break;
                        }
                        type = parser.next();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                StringBuffer sb = new StringBuffer();
                for (Student student : mStudentList) {
                    sb.append(student.toString());
                    sb.append("\n");

                }
                tv_show.setText(sb);
                break;
        }
    }
}
