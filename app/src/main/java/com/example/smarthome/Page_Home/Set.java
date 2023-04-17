package com.example.smarthome.Page_Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.smarthome.Page_Samrt.AdustTheCurtain;
import com.example.smarthome.R;
/**
 * @description 设置
 */
public class Set extends AppCompatActivity {
    Toolbar set_tb;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        textView=findViewById(R.id.set_home_manage);
        set_tb=findViewById(R.id.set_tb);
        set_tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Set.this, AdustTheCurtain.class);
                startActivity(intent);

            }
        });
    }

    }
