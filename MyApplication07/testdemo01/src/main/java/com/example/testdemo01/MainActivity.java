package com.example.testdemo01;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {
    private SimpleCursorAdapter mAdapter;
    private ListView listView;

    private Button btn_add;
    private Button btn_delete;

    private Button btn_back;
    private EditText et_id;
    private EditText et_name;
    private EditText et_sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = (Button) findViewById(R.id.btn_add);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        listView = (ListView) findViewById(R.id.list);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
                refleshListView();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
                refleshListView();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this,""+position,Toast.LENGTH_SHORT).show();
                int positions = position + 1;
                Db db = new Db(MainActivity.this);
                SQLiteDatabase dbWrite = db.getWritableDatabase();
                dbWrite.delete("user2","_id=?",new String[]{""+positions});
                dbWrite.close();
                refleshListView();
            }
        });
    }

    public void addData() {
        btn_back = (Button) findViewById(R.id.btn_back);
        et_id = (EditText) findViewById(R.id.et_id);
        et_name = (EditText) findViewById(R.id.et_name);
        et_sex = (EditText) findViewById(R.id.et_sex);

        btn_add = (Button) findViewById(R.id.btn_add);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        listView = (ListView) findViewById(R.id.list);

        btn_add.setVisibility(View.GONE);
        btn_delete.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);

        btn_back.setVisibility(View.VISIBLE);
        et_id.setVisibility(View.VISIBLE);
        et_name.setVisibility(View.VISIBLE);
        et_sex.setVisibility(View.VISIBLE);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_add.setVisibility(View.VISIBLE);
                btn_delete.setVisibility(View.VISIBLE);
                listView.setVisibility(View.VISIBLE);

                btn_back.setVisibility(View.GONE);
                et_id.setVisibility(View.GONE);
                et_name.setVisibility(View.GONE);
                et_sex.setVisibility(View.GONE);

                String id = et_id.getText().toString(),
                        name =et_name.getText().toString(),
                        sex = et_sex.getText().toString();


                Db db = new Db(MainActivity.this);
                SQLiteDatabase dbWrite = db.getWritableDatabase();

                ContentValues cv = new ContentValues();
                cv.put("_id", id);
                cv.put("name", name);
                cv.put("sex", sex);
                dbWrite.insert("user2", null, cv);

                dbWrite.close();

                refleshListView();
            }
        });

//        String id = et_id.getText().toString(),
//               name =et_name.getText().toString(),
//               sex = et_sex.getText().toString();
//
//
//        Db db = new Db(MainActivity.this);
//        SQLiteDatabase dbWrite = db.getWritableDatabase();
//
//        ContentValues cv = new ContentValues();
//        cv.put("_id",id);
//        cv.put("name", name);
//        cv.put("sex", sex);
//        dbWrite.insert("user2", null, cv);
//
//        dbWrite.close();
    }

    public void deleteData() {
        Db db = new Db(MainActivity.this);
        SQLiteDatabase dbWrite = db.getWritableDatabase();
        dbWrite.delete("user2",null,new String[]{});
        dbWrite.close();
    }

    public void refleshListView() {
        Db db = new Db(MainActivity.this);
        SQLiteDatabase dbRead = db.getReadableDatabase();
        Cursor c = dbRead.query("user2", null, null, null, null, null, null);

        mAdapter = new SimpleCursorAdapter(MainActivity.this, R.layout.user_info, c,
                new String[]{"_id","name","sex"}, new int[]{R.id._id,R.id._name,R.id._sex});

        listView.setAdapter(mAdapter);

        dbRead.close();
    }

}