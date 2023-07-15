package com.example.smarthome.Page_Samrt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.smarthome.R;
import com.example.smarthome.Scene.SmartDevice.Set_air;
import com.example.smarthome.Scene.SmartDevice.Set_curtain;
import com.example.smarthome.Scene.SmartDevice.Set_lights;
/**
 * @description 弃用
 */
public class Condition1 extends AppCompatActivity {
    TextView set_air,set_light,set_curtain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition);
        inite();
        m_intent();
    }



    private void inite() {
        set_air=findViewById(R.id.set_air);
        set_light=findViewById(R.id.set_lights);
        set_curtain=findViewById(R.id.set_curtain);

    }
    private void m_intent() {
        set_air.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Condition1.this, Set_air.class);
                startActivity(intent1);
            }
        });
        set_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(Condition1.this, Set_lights.class);
                startActivity(intent2);
            }
        });
        set_curtain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(Condition1.this, Set_curtain.class);
                startActivity(intent2);
            }
        });

    }
}