package com.example.smarthome.Scene;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import com.example.smarthome.Activity.Test;
import com.example.smarthome.R;
import com.example.smarthome.View.PickerView;

import java.util.ArrayList;
import java.util.List;
/**
 * @description 弃用
 */
public class Test22 extends AppCompatActivity {
    PickerView minutePv;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test22);
        minutePv = (PickerView) findViewById(R.id.minute);

        List<String> data = new ArrayList<String>();
        for (int i = 1; i < 100; i++) {
            data.add("" + i);//添加da
        }
        minutePv.setData(data);


        minutePv.setOnSelectListener(new PickerView.onSelectListener()
        {

            @Override
            public void onSelect(String text) {
//                Intent intent = new Intent(Test22.this, Sen.class);
//                String tm_max=text;
//                intent.putExtra("tm_max",tm_max);
//                startActivity(intent);

            }
        });
    }
}