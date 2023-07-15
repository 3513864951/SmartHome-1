package com.example.smarthome.Scene;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adapter.SensorListAdaptor;
import com.example.smarthome.Database.Sensor;
import com.example.smarthome.Page_Samrt.ShowSense;
import com.example.smarthome.R;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
/**
 * @description 传感器数据显示
 */
public class SensorActivity extends AppCompatActivity {
    private List<Sensor> sensorList=new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smart_sensors);
        recyclerView=findViewById(R.id.recyclerView);
        initRecyclerView();
    }
    private void initRecyclerView(){
        sensorList= LitePal.findAll(Sensor.class);
        if(!sensorList.isEmpty())
        {
            LinearLayoutManager layoutManager=new LinearLayoutManager(SensorActivity.this);
            recyclerView.setLayoutManager(layoutManager);
            SensorListAdaptor sensorListAdaptor=new SensorListAdaptor(sensorList);
            recyclerView.setAdapter(sensorListAdaptor);
            sensorListAdaptor.notifyDataSetChanged();
        }
    }
}
