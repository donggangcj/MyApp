package com.example.testdemo01;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/10/24.
 */
public class Db extends SQLiteOpenHelper {
    public Db(Context context) {
        super(context, "db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user2(" +
                "_id INT DEFAULT 0," +
                "name TEXT DEFAULT \"\"," +
                "sex TEXT DEFAULT \"\")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}