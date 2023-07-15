package com.example.smarthome.Page_Samrt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.smarthome.Adapter.SensorListAdaptor;
import com.example.smarthome.Database.Sensor;
import com.example.smarthome.R;
import com.example.smarthome.View.SelfTextView;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
/**
 * @description 弃用
 */
public class ShowSense extends AppCompatActivity {
private List<Sensor> sensorList=new ArrayList<>();
RecyclerView recyclerView;
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
            LinearLayoutManager layoutManager=new LinearLayoutManager(ShowSense.this);
            recyclerView.setLayoutManager(layoutManager);
            SensorListAdaptor sensorListAdaptor=new SensorListAdaptor(sensorList);
            recyclerView.setAdapter(sensorListAdaptor);
            sensorListAdaptor.notifyDataSetChanged();
        }
    }

}