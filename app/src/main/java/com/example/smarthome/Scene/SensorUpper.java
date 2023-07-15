package com.example.smarthome.Scene;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smarthome.Page_Samrt.ChsngeTm;
import com.example.smarthome.R;
/**
 * @description 无用
 */
public class SensorUpper extends AppCompatActivity {
    Toolbar toolbar;
    TextView min,max;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_temp);
        toolbar=findViewById(R.id.addhome_tb);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    min.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(SensorUpper.this, ChsngeTm.class);
            startActivity(intent);
        }
    });
max.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(SensorUpper.this,More.class);
        startActivity(intent);
    }
});


    }
}
