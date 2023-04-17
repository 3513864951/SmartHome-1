package com.example.smarthome.Page_Home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.smarthome.R;
import com.example.smarthome.View.SelfTextView;
/**
 * @description 弃用
 */
public class WangGuan extends AppCompatActivity {
    SelfTextView selfTextView1,selfTextView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensorlist);
        selfTextView1=findViewById(R.id.tv1);
        selfTextView2=findViewById(R.id.tv2);
        selfTextView1.setmTitleText(25.41F,2);
        selfTextView2.setmTitleText(23.51F,2);
    }
}