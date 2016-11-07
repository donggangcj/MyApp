package com.example.administrator.myapplication07;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/10/31.
 */
public class StudentGrade extends AppCompatActivity {
    private View view1, view2, view3;
    private ViewPager viewPager;  //对应的viewPager
    private PagerTitleStrip pagerTitleStrip;//viewpager的标题
    private PagerTabStrip pagerTabStrip;//viewpager的指示器

    private List<View> viewList;//view数组
    private List<String> tilteList;

    //view2:
    private  ListView lv_experimentgrade;

    //view3:
    private ListView lv_finalgrade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grademanage);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        pagerTabStrip=(PagerTabStrip) findViewById(R.id.pagertab);
        pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.gold));
        pagerTabStrip.setDrawFullUnderline(false);
        pagerTabStrip.setBackgroundColor(getResources().getColor(R.color.azure));
        pagerTabStrip.setTextSpacing(50);

        LayoutInflater inflater=getLayoutInflater();
        view1 = inflater.inflate(R.layout.view_experiment, null);
        view2 = inflater.inflate(R.layout.view_common,null);
        view3 = inflater.inflate(R.layout.view_final, null);

        viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);

        //view2
        lv_experimentgrade= (ListView) view1.findViewById(R.id.lv_experimentgrade);
        StudentRepo repo = new StudentRepo(this);
        ArrayList<HashMap<String,String>> studentList = repo.getStudentList();
        MyAdapter03 adapter03 = new MyAdapter03(this);
        adapter03.setList(studentList);
        lv_experimentgrade.setAdapter(adapter03);


        //view3:
        lv_finalgrade = (ListView) view3.findViewById(R.id.lv_finalgrade);
//        StudentRepo repo = new StudentRepo(this);
//        ArrayList<HashMap<String,String>> studentList = repo.getStudentList();
        MyAdapter02 adapter02 = new MyAdapter02(this);
        adapter02.setList(studentList);
        lv_finalgrade.setAdapter(adapter02);


        tilteList = new ArrayList<String>();  // 每个页面的Title数据
        tilteList.add("实验成绩");
        tilteList.add("平时成绩");
        tilteList.add("期末成绩");


        PagerAdapter pagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return viewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                // TODO Auto-generated method stub
                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // TODO Auto-generated method stub
                container.addView(viewList.get(position));


                return viewList.get(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tilteList.get(position);//显示标题
            }
        };


        viewPager.setAdapter(pagerAdapter);

    }


}
