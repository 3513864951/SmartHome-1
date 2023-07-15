package com.example.smarthome.Scene;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smarthome.Database.Scene.Temp;
import com.example.smarthome.R;

import org.litepal.LitePal;
/**
 * @description 任务场景
 */
public class MissionActivity extends AppCompatActivity {
    LinearLayout sysMessage;
    LinearLayout smart_device;
    LinearLayout scene_control;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.missionactivity);
        sysMessage=findViewById(R.id.sysMessage);
        smart_device=findViewById(R.id.smart_device);
        scene_control=findViewById(R.id.scene_control);
        toolbar=findViewById(R.id.add_mission_back);
        initLayout();
    }

    private void initLayout(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sysMessage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return true;
            }
        });

        smart_device.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
            Intent intent=new Intent(MissionActivity.this,SmartActivity.class);
            startActivity(intent);
                return true;
            }
        });
        scene_control.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return true;
            }
        });

    }

}
