package com.example.smarthome.Scene;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smarthome.Database.Scene.Condition;
import com.example.smarthome.Database.Scene.Temp;
import com.example.smarthome.Database.Sensor;
import com.example.smarthome.R;
import com.google.android.material.button.MaterialButton;

import org.litepal.LitePal;

import java.util.Locale;

public class SensorCondition extends AppCompatActivity {
    Toolbar toolbar;
    EditText max_num,min_num;
    MaterialButton save;
    String device_type,time;
    String content1,content2,target_long_address;
    Temp temp;
    Condition condition;
    int condition_id,temp_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_temp);
        toolbar=findViewById(R.id.addhome_tb);
        max_num=findViewById(R.id.max_num);
        save=findViewById(R.id.save);
        min_num=findViewById(R.id.min_num);

        Intent intent=getIntent();
         device_type=intent.getStringExtra("device_type");
         time=intent.getStringExtra("time");//condition的
        target_long_address=intent.getStringExtra("target_long_address");
        if(time!=null){
            condition= LitePal.where("time = ?",time).findFirst(Condition.class);
            condition_id=condition.getId();
        }else
            condition=new Condition();
        temp=LitePal.findLast(Temp.class);
        temp_id=temp.getId();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        max_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                content1=s.toString();
                if(TextUtils.isEmpty(s)){
                    min_num.setVisibility(View.VISIBLE);
                    min_num.setClickable(true);
                }else {
                    min_num.setVisibility(View.INVISIBLE);
                    min_num.setClickable(false);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        min_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                content2=min_num.getText().toString();
                if(TextUtils.isEmpty(s)){
                    max_num.setVisibility(View.VISIBLE);
                    max_num.setClickable(true);
                }else {
                    max_num.setVisibility(View.INVISIBLE);
                    max_num.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sensor sensor=new Sensor();
                if(content2!=null)
                    content2=min_num.getText().toString();
                else
                    content2="null";
                if(content1!=null)
                    content1=max_num.getText().toString();
                else
                    content1="null";
                if(TextUtils.isEmpty(content1)&&TextUtils.isEmpty(content2)){
                    Toast.makeText(SensorCondition.this,"请输入条件！",Toast.LENGTH_SHORT).show();
                }else {
                    condition.setJudge(5);
                    switch (device_type){

                        case "05":
                            sensor.setFlag(1);
                            if(content1.equals("null")){
                                sensor.setUpOrDown(0);
                                int temperature=Integer.valueOf(content2);
                                String hexTemp=Integer.toHexString(temperature).toUpperCase(Locale.ROOT);
                                sensor.setI_temp(hexTemp);
                                if(time==null){
                                    sensor.setTemp(temp);
                                    sensor.setCondition(condition);
                                    sensor.setDevice_type("05");
                                    sensor.setTarget_long_address(target_long_address);
                                    condition.setSensor(sensor);
                                    condition.setTemp(temp);
                                    condition.save();
                                    sensor.save();
                                }
                                else{
                                    sensor.setTemp(temp);
                                    sensor.updateAll("condition_id =?",condition_id+"");
                                }

                            }else {
                                sensor.setUpOrDown(1);
                                int temperature=Integer.valueOf(content1);
                                String hexTemp=Integer.toHexString(temperature).toUpperCase(Locale.ROOT);
                                sensor.setI_temp(hexTemp);
                                if(time==null){
                                    sensor.setTemp(temp);
                                    sensor.setCondition(condition);
                                    sensor.setDevice_type("05");
                                    sensor.setTarget_long_address(target_long_address);

                                    condition.setTemp(temp);
                                    condition.save();
                                    sensor.save();
                                }
                                else{
                                    sensor.setTemp(temp);
                                    sensor.updateAll("condition_id =?",condition_id+"");
                                }
                            }
                            break;
                        case "06":
                            if(content1.equals("null")){
                                sensor.setUpOrDown(0);
                                int wetness=Integer.valueOf(content2);
                                String hexTemp=Integer.toHexString(wetness).toUpperCase(Locale.ROOT);
                                sensor.setI_wetness(hexTemp);
                                if(time==null){
                                    sensor.setTemp(temp);
                                    sensor.setCondition(condition);
                                    condition.setTemp(temp);
                                    sensor.setDevice_type("05");
                                    sensor.setTarget_long_address(target_long_address);

                                    condition.save();
                                    sensor.save();
                                }
                                else{
                                    sensor.setTemp(temp);
                                    sensor.updateAll("condition_id =?",condition_id+"");
                                }
                            }else {
                                sensor.setUpOrDown(1);
                                int wetness=Integer.valueOf(content1);
                                String hexTemp=Integer.toHexString(wetness).toUpperCase(Locale.ROOT);
                                sensor.setI_wetness(hexTemp);
                                if(time==null){
                                    sensor.setTemp(temp);
                                    sensor.setCondition(condition);
                                    condition.setTemp(temp);
                                    condition.save();
                                    sensor.setDevice_type("05");
                                    sensor.setTarget_long_address(target_long_address);

                                    sensor.save();
                                }
                                else{
                                    sensor.setTemp(temp);
                                    sensor.updateAll("condition_id =?",condition_id+"");
                                }
                            }
                            break;
                    }
                    Intent intent=new Intent(SensorCondition.this, More.class);
                    startActivity(intent);
                }

            }
        });

    }

}
