package com.example.smarthome.Scene;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adapter.SceneSensorAdaptor;
import com.example.smarthome.Database.Sensor;
import com.example.smarthome.Page_Huiju.HomeManageActivity;
import com.example.smarthome.R;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
/**
 * @description 场景传感器
 */
public class SceneSensor extends AppCompatActivity {
    RecyclerView recyclerView2;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scenesensor);
        recyclerView2=findViewById(R.id.recyclerView2);
        List<Sensor> sensorList=new ArrayList<>();
        sensorList= LitePal.where("device_type = ? or device_type= ?","05","06").find(Sensor.class);
        LinearLayoutManager layoutManager=new LinearLayoutManager(SceneSensor.this);
        recyclerView2.setLayoutManager(layoutManager);
        SceneSensorAdaptor sceneSensorAdaptor=new SceneSensorAdaptor(sensorList);
        recyclerView2.setAdapter(sceneSensorAdaptor);
        sceneSensorAdaptor.notifyDataSetChanged();
        toolbar=findViewById(R.id.tem_tb);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
