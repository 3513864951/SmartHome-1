package com.example.smarthome.Scene;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.R;
/**
 * @description 弃用
 */
public class AddOrEditScene extends AppCompatActivity {
    private Button add_condition;
    private Button add_task;
    private RecyclerView recy_condition;
    private RecyclerView recy_mission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addoreditmodel);
        add_condition=findViewById(R.id.add_condition);
        add_task=findViewById(R.id.add_mission);
        recy_condition=findViewById(R.id.recy_condition);
        recy_mission=findViewById(R.id.recy_mission);
        initButton();
    }

    private void initButton(){


        add_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddOrEditScene.this,ConditionActivity.class);
                startActivity(intent);

            }
        });
        add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddOrEditScene.this,MissionActivity.class);
                startActivity(intent);

            }
        });


    }
}

