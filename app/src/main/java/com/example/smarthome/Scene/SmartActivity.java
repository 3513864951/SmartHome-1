package com.example.smarthome.Scene;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smarthome.Scene.SmartDevice.Set_Huminity;
import com.example.smarthome.Scene.SmartDevice.Set_air;
import com.example.smarthome.Scene.SmartDevice.Set_curtain;
import com.example.smarthome.Scene.SmartDevice.Set_lights;
import com.example.smarthome.R;

/**
 * @description
 * 场景创建时智能设备执行
 */
public class SmartActivity extends AppCompatActivity {
    TextView set_air,set_light,set_curtain,set_humidity;
    Toolbar set_tb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smartactivity);
        init();
        m_intent();
    }



    private void init() {
        set_air=findViewById(R.id.set_air);
        set_light=findViewById(R.id.set_lights);
        set_curtain=findViewById(R.id.set_curtain);
        set_tb=findViewById(R.id.set_tb);
        set_humidity=findViewById(R.id.set_humidity);

    }
    private void m_intent() {
        set_tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        set_air.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(SmartActivity.this, Set_air.class);
                startActivity(intent1);
            }
        });
        set_humidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(SmartActivity.this, Set_Huminity.class);
                startActivity(intent1);
            }
        });
        set_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(SmartActivity.this, Set_lights.class);

                startActivity(intent2);
            }
        });
        set_curtain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(SmartActivity.this, Set_curtain.class);
                startActivity(intent2);
            }
        });

    }
}
