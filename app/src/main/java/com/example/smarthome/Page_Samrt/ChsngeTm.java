package com.example.smarthome.Page_Samrt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.smarthome.Activity.Test;
import com.example.smarthome.R;
import com.example.smarthome.View.PickerView;


import java.util.ArrayList;
import java.util.List;

public class ChsngeTm extends AppCompatActivity {
    PickerView minute_pv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chsnge_tm);
        minute_pv = (PickerView) findViewById(R.id.minute_pv);

        List<String> data = new ArrayList<String>();
        for (int i = 1; i < 100; i++) {
            data.add("" + i);//添加da
        }
        minute_pv.setData(data);


        minute_pv.setOnSelectListener(new PickerView.onSelectListener()
        {

            @Override
            public void onSelect(String text) {
                Intent intent = new Intent(ChsngeTm.this, Test.class);
                String tm = text;
                intent.putExtra("tm", tm);
                startActivity(intent);

            }
        });

    }
}