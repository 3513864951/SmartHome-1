package com.example.smarthome.Page_Home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.smarthome.R;
/**
 * @description 弃用
 */
public class Eroll extends AppCompatActivity {
    TextView enroll_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eroll);
        enroll_back=findViewById(R.id.enroll_back);
        enroll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}