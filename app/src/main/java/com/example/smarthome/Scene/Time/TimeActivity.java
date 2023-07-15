package com.example.smarthome.Scene.Time;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smarthome.Database.Scene.C_Time;
import com.example.smarthome.Database.Scene.Condition;
import com.example.smarthome.Database.Scene.Temp;
import com.example.smarthome.R;
import com.example.smarthome.Scene.More;
import com.google.android.material.snackbar.Snackbar;

import org.litepal.LitePal;

import java.sql.Time;
import java.util.List;
/**
 * @author liukai
 * @description 场景时间条件
 */
public class TimeActivity extends AppCompatActivity {
    public static final String TIME="time";
    public static final String FLAG="isNew";//1 0 判断是否是新的
    private Button add_time_point;
    private Button add_time_period;
    private Toolbar add_time_toolbar;
    private String setTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeactivity);
        add_time_toolbar=findViewById(R.id.add_time_toolbar);
        add_time_point=findViewById(R.id.add_time_point);
        add_time_period=findViewById(R.id.add_time_period);
        add_time_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent=getIntent();
        setTime=intent.getStringExtra(TimeActivity.TIME);
        add_time_point.setOnClickListener(v -> {
            Intent intent1=new Intent(TimeActivity.this,TimePointActivity.class);
            intent1.putExtra(TimeActivity.TIME,setTime);
            startActivity(intent1);
        });
        add_time_period.setOnClickListener(v -> {
            Intent intent1=new Intent(TimeActivity.this,TimePeriodActivity.class);
            intent1.putExtra(TimeActivity.TIME,setTime);
            startActivity(intent1);
        });
    }

}
