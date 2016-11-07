package com.example.administrator.myapplication07;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/25.
 */
public class StudentOnduty extends AppCompatActivity {//implements  AdapterView.OnItemClickListener
    private ListView lv_onduty;
    private TextView tv_showTime;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studnet_onduty);
        tv_showTime = (TextView) findViewById(R.id.tv_showtime);
        SimpleDateFormat formatter =new SimpleDateFormat    ("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate =new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        tv_showTime.setText(str); //显示时间
        lv_onduty = (ListView) findViewById(R.id.lv_onduty);
        StudentRepo repo = new StudentRepo(this);
        ArrayList<HashMap<String,String>> studentList = repo.getStudentList();
//        for(Map p:studentList){
//            for(Object k:p.keySet()){
//                System.out.println(p.get(k));
//            }
//        }
//        SimpleAdapter adapter = new SimpleAdapter(StudentOnduty.this, studentList, R.layout.item_student_onduty, new String[]{"id", "name"}, new int[]{R.id.tv_student_Id, R.id.tv_student_name});
        MyAdapter01 adapter = new MyAdapter01(this);
        adapter.setList(studentList);
        lv_onduty.setAdapter(adapter);
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//    }
}
