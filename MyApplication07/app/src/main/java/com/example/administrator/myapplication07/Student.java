package com.example.administrator.myapplication07;

/**
 * Created by Administrator on 2016/10/22.
 */
public class Student {
    //表名
    public static final String TABLE="Student";

    //表的各域名
    public static final String KEY_ID="id";
    public static final String KEY_name="name";
    public static final String KEY_email="email";
    public static final String KEY_age="age";
    public static final String KEY_Onduty="Onduty";//出勤
    public static final String KEY_Finalgrade="finalgrade";//期末成绩
    public static final String KEY_Experimentgrade="experimentgrade";//期末成绩

    //属性
    public int student_ID;
    public String name;
    public String email;
    public int age;
    public int Onduty;
    public int finalgrade;
    public int experimentgrade;
}
