package com.example.administrator.myapplication07;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/26.
 */
public class MyAdapter01 extends BaseAdapter {// implements View.OnClickListener
    List<HashMap<String,String>> list;

    LayoutInflater inflater;

    private Context context;


    public MyAdapter01(Context context){
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
            convertView = inflater.inflate(R.layout.item_student_onduty, null);//null参数的意义
            holder=new viewHolder();
            holder.tv_student_Id = (TextView) convertView.findViewById(R.id.tv_student_Id);
            holder.tv_student_name = (TextView) convertView.findViewById(R.id.tv_student_name);
            holder.rg_1 = (RadioGroup) convertView.findViewById(R.id.rg_1);
            holder.rb_1 = (RadioButton) convertView.findViewById(R.id.rb_1);
            holder.rb_2 = (RadioButton) convertView.findViewById(R.id.rb_2);
            holder.rb_3 = (RadioButton) convertView.findViewById(R.id.rb_3);
            holder.rb_4 = (RadioButton) convertView.findViewById(R.id.rb_4);
            holder.rb_5 = (RadioButton) convertView.findViewById(R.id.rb_5);
            convertView.setTag(holder);
        }else{
            holder= (viewHolder) convertView.getTag();
        }
        Map map = list.get(position);
        holder.tv_student_Id.setText((String) map.get("id"));
        holder.tv_student_name.setText((String) map.get("name"));

        holder.rg_1.clearCheck();
        holder.rg_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                StudentRepo repo = new StudentRepo(context);
                int s_i = Integer.parseInt(list.get(position).get("id"));
              //  Student student=repo.getStudentById(s_i);
                switch (checkedId){
                    case R.id.rb_1:repo.upOnduty(s_i,1);
                        Toast.makeText(context, "有反应", Toast.LENGTH_LONG).show();break;
                    case R.id.rb_2:repo.upOnduty(s_i,2);break;
                    case R.id.rb_3:repo.upOnduty(s_i,3);break;
                    case R.id.rb_4:repo.upOnduty(s_i,4);break;
                    case R.id.rb_5:repo.upOnduty(s_i,5);break;
                }
            }
        });

        return convertView;
    }

    public class viewHolder{
        TextView tv_student_Id;
        TextView tv_student_name;
        RadioGroup rg_1;
        RadioButton rb_1;
        RadioButton rb_2;
        RadioButton rb_3;
        RadioButton rb_4;
        RadioButton rb_5;
    }
}
