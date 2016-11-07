package com.example.administrator.myapplication07;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/10/22.
 */
public class StudentRepo {
    private DBHelper dbHelper;

    public StudentRepo(Context context){
        dbHelper=new DBHelper(context);
    }

    public int insert(Student student){
        //打开连接，写入数据
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Student.KEY_age,student.age);
        values.put(Student.KEY_email,student.email);
        values.put(Student.KEY_name,student.name);
        long student_Id=db.insert(Student.TABLE,null,values);//**********student_Id出现的问题
        db.close();
        return (int)student_Id;
    }

    public void delete(int student_Id){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(Student.TABLE,Student.KEY_ID+"=?", new String[]{String.valueOf(student_Id)});
        db.close();
    }
    public void update(Student student){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(Student.KEY_age,student.age);
        values.put(Student.KEY_email,student.email);
        values.put(Student.KEY_name,student.name);

        db.update(Student.TABLE,values,Student.KEY_ID+"=?",new String[] { String.valueOf(student.student_ID) });
        db.close();
    }
    public void upOnduty(int s_i,int i){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String upOnduty="update "+Student.TABLE+" set Onduty ="+i+" where id="+s_i;
//        student.Onduty=i;
//        ContentValues values=new ContentValues();
//        values.put(Student.KEY_Onduty,student.Onduty);
        db.execSQL(upOnduty);
        db.close();
    }

    public void upfinalgrade(int s_i,int i){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String upOnduty="update "+Student.TABLE+" set finalgrade ="+i+" where id="+s_i;
//        student.Onduty=i;
//        ContentValues values=new ContentValues();
//        values.put(Student.KEY_Onduty,student.Onduty);
        db.execSQL(upOnduty);
        db.close();
    }
    public void upexperimentgrade(int s_i,int i){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String upOnduty="update "+Student.TABLE+" set experimentgrade ="+i+" where id="+s_i;
//        student.Onduty=i;
//        ContentValues values=new ContentValues();
//        values.put(Student.KEY_Onduty,student.Onduty);
        db.execSQL(upOnduty);
        db.close();
    }

    public ArrayList<HashMap<String, String>> getStudentList(){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String selectQuery="SELECT "+
                Student.KEY_ID+","+
                Student.KEY_name+","+
                Student.KEY_email+","+
                Student.KEY_age+" FROM "+Student.TABLE;
        ArrayList<HashMap<String,String>> studentList=new ArrayList<HashMap<String, String>>();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                HashMap<String,String> student=new HashMap<String,String>();
                student.put("id",cursor.getString(cursor.getColumnIndex(Student.KEY_ID)));
                student.put("name",cursor.getString(cursor.getColumnIndex(Student.KEY_name)));
                studentList.add(student);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return studentList;
    }

    public Student getStudentById(int Id){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String selectQuery="SELECT "+
                Student.KEY_ID + "," +
                Student.KEY_name + "," +
                Student.KEY_email + "," +
                Student.KEY_age +
                " FROM " + Student.TABLE
                + " WHERE " +
                Student.KEY_ID + "=?";
        int iCount=0;
        Student student=new Student();
        Cursor cursor=db.rawQuery(selectQuery,new String[]{String.valueOf(Id)});
        if(cursor.moveToFirst()){
            do{
                student.student_ID =cursor.getInt(cursor.getColumnIndex(Student.KEY_ID));
                student.name =cursor.getString(cursor.getColumnIndex(Student.KEY_name));
                student.email  =cursor.getString(cursor.getColumnIndex(Student.KEY_email));
                student.age =cursor.getInt(cursor.getColumnIndex(Student.KEY_age));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return student;
    }
    public double getStudentGrade(int id){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String selectQuery="SELECT "+
                Student.KEY_Onduty + "," +
                Student.KEY_Finalgrade + "," +
                Student.KEY_Experimentgrade + "," +
                Student.KEY_ID +
                " FROM " + Student.TABLE
                + " WHERE " +
                Student.KEY_ID + "=?";
        double all=0;//总评成绩
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});
        if(cursor.moveToFirst()){
            do{
              //  student.student_ID =cursor.getInt(cursor.getColumnIndex(Student.KEY_ID));
                int x2=cursor.getInt(cursor.getColumnIndex(Student.KEY_Finalgrade));//期末成绩
                int x3=cursor.getInt(cursor.getColumnIndex(Student.KEY_Experimentgrade));//实验成绩
                int x1=cursor.getInt(cursor.getColumnIndex(Student.KEY_Onduty));
                switch (x1){
                    case 1:
                        all=0.7*x2+0.2*x3+8;break;
                    case 2:
                        all=0.7*x2+0.2*x3+7;break;
                    case 3:
                        all=0.7*x2+0.2*x3+0;break;
                    case 4:
                        all=0.7*x2+0.2*x3+10;break;
                    case 5:
                        all=0.7*x2+0.2*x3+9;break;
                    default:
                        break;
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return all;
    }
}
