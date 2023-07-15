package com.example.smarthome.Scene.Time;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smarthome.Database.Scene.C_Time;
import com.example.smarthome.Database.Scene.Condition;
import com.example.smarthome.Database.Scene.Temp;
import com.example.smarthome.R;
import com.example.smarthome.Scene.More;
import com.google.android.material.button.MaterialButton;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * @description 场景时间点
 */
public class TimePointActivity extends AppCompatActivity {
    private MaterialButton add_time;
    private MaterialButton add_repeat;
    private MaterialButton time_save;
    private Toolbar add_time_toolbar;
    private TextView time_start;
    private Condition condition;
    private C_Time c_time;
    private List<Condition> conditionList;
    private String setTime;
    private String date;
    private int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timepointactivity);
        add_time=findViewById(R.id.add_time);
        add_repeat=findViewById(R.id.add_repeat);
        time_save=findViewById(R.id.time_save);
        time_start=findViewById(R.id.time_start);
        add_time_toolbar=findViewById(R.id.add_time_toolbar);
        Intent intent=getIntent();
        setTime=intent.getStringExtra(TimeActivity.TIME);//condition的时间
        if(setTime!=null)
        {
            flag=1;
            conditionList= LitePal.where("time = ?",setTime).find(Condition.class,true);
            condition=conditionList.get(0);
            c_time=condition.getC_time();
        }else if(setTime==null)
            flag=0;
        time_save.setClickable(false);
        add_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DateDialog dateDialog=new DateDialog(TimePointActivity.this);
                dateDialog.show();
                dateDialog.onClickOkBtnLisitener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        date=dateDialog.getDate();
                        dateDialog.dismiss();
                        time_save.setClickable(true);
                        Toast.makeText(TimePointActivity.this,date,Toast.LENGTH_SHORT).show();
                        time_start.setText(date);
                    }
                });
            }
        });
        add_repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        time_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这边用temp不行，无法精确寻找时间，还是用condition
                //还是要传入时间
                Temp temp= LitePal.findLast(Temp.class);
                c_time=new C_Time();
                c_time.setTime(date);
                c_time.setTemp(temp);
                List<C_Time> c_timeList=new ArrayList<>();
                c_timeList.add(c_time);
                temp.getC_timeList().add(c_time);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                Date date=new Date(System.currentTimeMillis());
                String time=simpleDateFormat.format(date);
                temp.updateAll();
                C_Time c_time2=temp.getC_timeList().get(0);
                if(flag==0){
                    condition=new Condition();
                    condition.setC_time(c_time);
                    c_time.setCondition(condition);
                    condition.setTemp(temp);
                    condition.setTime(time);
                    condition.setJudge(3);
                    temp.getConditionList().add(condition);
                    temp.updateAll();
                    c_time.save();
                    condition.save();
                }
                else{
                    condition.setTime(time);
                    condition.setC_time(c_time);
                    condition.setTemp(temp);
                    condition.setJudge(3);
                    temp.updateAll();
                    c_time.updateAll("condition_id = ?",condition.getId()+"");
                    condition.updateAll("time = ?",setTime);
                }

                Intent intent=new Intent(TimePointActivity.this, More.class);
                //再设计一个暂存类Temp来储存设计好的条件和任务，如果场景保存了就存入Scene中，之后删除Temp中数据，如果没有，就删除Temp中数据
                startActivity(intent);
            }
        });
        add_time_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
