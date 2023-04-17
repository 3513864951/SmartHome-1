package com.example.smarthome.Scene.Time;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smarthome.App;
import com.example.smarthome.Database.Scene.C_Time;
import com.example.smarthome.Database.Scene.Condition;
import com.example.smarthome.Database.Scene.Temp;
import com.example.smarthome.R;
import com.example.smarthome.Scene.More;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TimePeriodActivity extends AppCompatActivity {

    private MaterialButton add_time_periodStart;
    private MaterialButton add_time_periodEnd;
    private MaterialButton add_repeat;
    private MaterialButton time_save;
    private TextView time_start;
    private TextView time_end;
    private Toolbar add_time_toolbar;
    private Condition condition;
    private List<Condition> conditionList;
    private C_Time c_time=new C_Time();
    private String date1;
    private String date2;
    private String setTime;
    private int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeperiodactivity);
        add_time_periodStart = findViewById(R.id.add_time_periodStart);
        add_time_periodEnd = findViewById(R.id.add_time_periodEnd);
        add_repeat = findViewById(R.id.add_repeat);
        time_save = findViewById(R.id.time_save);
        time_start = findViewById(R.id.time_start);
        time_end = findViewById(R.id.time_end);
        add_time_toolbar=findViewById(R.id.add_time_toolbar);
        initListener();
        Intent intent=getIntent();
        setTime=intent.getStringExtra(TimeActivity.TIME);
        if(setTime!=null)
        {
            flag=1;
            conditionList=LitePal.where("time = ?",setTime).find(Condition.class);
            condition=conditionList.get(0);
        }else if(setTime==null)
            flag=0;
        time_save.setClickable(false);
        add_time_toolbar.setNavigationIcon(R.drawable.back);
        add_time_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(TimePeriodActivity.this,time_end,"7",Snackbar.ANIMATION_MODE_FADE).show();
                finish();
            }
        });

    }
    private void initListener(){

        add_time_periodStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add your code here
                DateDialogPeriod dateDialog=new DateDialogPeriod(TimePeriodActivity.this);
                dateDialog.show();
                dateDialog.onClickOkBtnLisitener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        date1=dateDialog.getDate();
                        dateDialog.dismiss();
                        time_save.setClickable(true);
                        Toast.makeText(TimePeriodActivity.this,date1,Toast.LENGTH_SHORT).show();
                        time_start.setText(date1);
                    }
                });
            }
        });

        add_time_periodEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add your code here
                DateDialogPeriod dateDialog=new DateDialogPeriod(TimePeriodActivity.this);
                dateDialog.show();
                dateDialog.onClickOkBtnLisitener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        date2=dateDialog.getDate();
                        dateDialog.dismiss();
                        time_save.setClickable(true);
                        Toast.makeText(TimePeriodActivity.this,date2,Toast.LENGTH_SHORT).show();
                        time_end.setText(date2);
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
                Temp temp= LitePal.findLast(Temp.class);
                c_time.setTime_start(date1);
                c_time.setTime_end(date2);
                c_time.setTemp(temp);
                c_time.save();
                condition.setJudge(3);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                Date date=new Date(System.currentTimeMillis());
                String time=simpleDateFormat.format(date);
                if(flag==0){
                    condition=new Condition();
                    condition.setC_time(c_time);
                    condition.setTemp(temp);
                    condition.setTime(time);
                    condition.save();
                }
                else
                {
                    condition.setTime(time);
                    condition.updateAll("time = ?",setTime);
                }
                Intent intent=new Intent(TimePeriodActivity.this, More.class);
                startActivity(intent);
            }
        });

    }
}
