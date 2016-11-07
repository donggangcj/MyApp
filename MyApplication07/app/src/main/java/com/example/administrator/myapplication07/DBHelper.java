package com.example.administrator.myapplication07;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

/**
 * Created by Administrator on 2016/10/22.
 */
public class DBHelper extends SQLiteOpenHelper{
    //数据库版本号
    private static final int DATABASE_VERSION=6;

    //数据库名称
    private static final String DATABASE_NAME="crud.db";

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据表
        String CREATE_TABLE_STUDENT="CREATE TABLE "+ Student.TABLE+"("
                +Student.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +Student.KEY_name+" TEXT, "
                +Student.KEY_age+" INTEGER, "
                +Student.KEY_email+" TEXT)"
                ;//+Student.KEY_Onduty+" INTEGER)"
        db.execSQL(CREATE_TABLE_STUDENT);
        final int FIRST_DATABASE_VERSION =4;
        onUpgrade(db, FIRST_DATABASE_VERSION, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //如果旧表存在，删除，所以数据将会消失

//        db.execSQL("DROP TABLE IF EXISTS "+ Student.TABLE);

        //再次创建表
//        onCreate(db);
        // 使用for实现跨版本升级数据库
        for (int i = oldVersion; i < newVersion; i++) {
            switch (i) {
                case 4:
                    upgradeToVersion1001(db);
                    break;
                case 5:
                    upgradeToVersion1002(db);
                case 6:
                    upgradeToVersion1003(db);
                default:
                    break;
            }
        }

    }
    private void upgradeToVersion1001(SQLiteDatabase db){
        // favorite表新增1个字段
        String sql1 = "ALTER TABLE "+Student.TABLE+" ADD "+Student.KEY_Onduty+" INTEGER";
        db.execSQL(sql1);
    }
    private void upgradeToVersion1002(SQLiteDatabase db){
        // favorite表新增1个字段
        String sql1 = "ALTER TABLE "+Student.TABLE+" ADD "+Student.KEY_Finalgrade+" INTEGER";
        db.execSQL(sql1);
    }
    private void upgradeToVersion1003(SQLiteDatabase db){
        // favorite表新增1个字段
        String sql1 = "ALTER TABLE "+Student.TABLE+" ADD "+Student.KEY_Experimentgrade+" INTEGER";
        db.execSQL(sql1);
    }
}
