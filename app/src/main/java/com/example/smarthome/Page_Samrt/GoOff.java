package com.example.smarthome.Page_Samrt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import com.example.smarthome.Database.Model;
import com.example.smarthome.R;
/**
 * @description 弃用
 */
public class GoOff extends AppCompatActivity {
    Toolbar go_off_tb;
    private int model;//模式
    private int curtain_model;
    private int light_model;
    private int lock_model;
    private int heater_model;
    private Model models;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_off);
        go_off_tb=findViewById(R.id.go_off_tb);
        go_off_tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        initcurtain();
//        initlights();
//        initmentor();

    }
//    private void initcurtain() {
//        ArrayAdapter adapter_curtain=ArrayAdapter.createFromResource(getApplicationContext(),R.array.choose_curtain_model, android.R.layout.simple_spinner_item);
//        Spinner sp_dropdown=findViewById(R.id.go_off_curtain);
//         adapter_curtain.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        sp_dropdown.setPrompt("请选择模式");
//        sp_dropdown.setAdapter( adapter_curtain);
//        sp_dropdown.setSelection(0);
//        sp_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
////                models.setCurtain_model(position1);//直接updateAll？
//                models.save();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }
//    private void initlights() {
//        ArrayAdapter adapter_lights=ArrayAdapter.createFromResource(getApplicationContext(),R.array.choose_lights_model, android.R.layout.simple_spinner_item);
//        Spinner sp_dropdown=findViewById(R.id.go_off_lights);
//        adapter_lights.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        sp_dropdown.setPrompt("请选择模式");
//        sp_dropdown.setAdapter(adapter_lights);
//        sp_dropdown.setSelection(0);
//        sp_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {
//                models.setLight_model(position2);
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }
//    private void initmentor() {
//        ArrayAdapter adapter_mentor=ArrayAdapter.createFromResource(getApplicationContext(),R.array.choose_mentor, android.R.layout.simple_spinner_item);
//        Spinner sp_dropdown=findViewById(R.id.go_off_mentor);
//        adapter_mentor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        sp_dropdown.setPrompt("请选择模式");
//        sp_dropdown.setAdapter(adapter_mentor);
//        sp_dropdown.setSelection(0);
//        sp_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position3, long id) {
//                models.setLight_model(position3);
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }
}