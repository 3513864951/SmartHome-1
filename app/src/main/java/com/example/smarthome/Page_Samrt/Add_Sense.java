package com.example.smarthome.Page_Samrt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smarthome.Database.AddModel;
import com.example.smarthome.Database.AddSense;
import com.example.smarthome.R;

import org.litepal.LitePal;
/**
 * @description 弃用
 */
public class Add_Sense extends AppCompatActivity {
    Button create_sense;
    EditText sense_name;
    String sense_nl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sense);
        inite();

    }

    private void inite() {
        create_sense=findViewById(R.id.create_sense);
        sense_name=findViewById(R.id.sense_name_et);
        create_sense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.getDatabase();
                sense_nl=sense_name.getText().toString();    //获取输入框值
                //判断输入框中是否有值
                if(TextUtils.isEmpty(sense_nl)){
                    Toast.makeText(Add_Sense.this,"请输入传感器名称",Toast.LENGTH_SHORT).show();
                    return;
                }
                AddSense addSense=new AddSense();
                addSense.setSense_name(sense_nl);
                addSense.save();
                finish();

            }
        });
    }
}