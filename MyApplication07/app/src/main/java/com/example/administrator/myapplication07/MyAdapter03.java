package com.example.administrator.myapplication07;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/1.
 */
public class MyAdapter03 extends BaseAdapter {// implements View.OnClickListener
    List<HashMap<String,String>> list;

    LayoutInflater inflater;

    private Context context;


    public MyAdapter03(Context context){
        this.context=context;
        this.inflater = LayoutInflater.from(context);
    }
    public void setList(List<HashMap<String,String>> list){
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        viewHolder holder=null;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.item_student_experiment, null);//null参数的意义
            holder=new viewHolder();
            holder.tv_student_Id = (TextView) convertView.findViewById(R.id.tv_student_Id2);
            holder.tv_student_name = (TextView) convertView.findViewById(R.id.tv_student_name2);
            holder.ed_experiment= (EditText) convertView.findViewById(R.id.ed_experiment);
            convertView.setTag(holder);
        }else{
            holder= (viewHolder) convertView.getTag();
        }
        Map map = list.get(position);
        holder.tv_student_Id.setText((String) map.get("id"));
        holder.tv_student_name.setText((String) map.get("name"));
        holder.ed_experiment.addTextChangedListener(new TextWatcher() {
            StudentRepo repo = new StudentRepo(context);
            int s_i = Integer.parseInt(list.get(position).get("id"));
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                StudentRepo repo = new StudentRepo(context);
//                int s_i = Integer.parseInt(list.get(position).get("id"));
                String str= s.toString().trim();
                int i = Integer.parseInt(str);
                repo.upexperimentgrade(s_i,i);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        return convertView;
    }

    public class viewHolder{
        TextView tv_student_Id;
        TextView tv_student_name;
        EditText ed_experiment;
    }
}