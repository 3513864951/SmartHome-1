package com.example.smarthome.Page_Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.smarthome.R;
/**
 * @description 弃用
 */
public class enter extends AppCompatActivity {
    TextView sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        inint();
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(enter.this, Eroll.class);
                startActivity(intent1);
            }
        });

    }

    private void inint() {
        sign_up=findViewById(R.id.sign_up);
    }
}