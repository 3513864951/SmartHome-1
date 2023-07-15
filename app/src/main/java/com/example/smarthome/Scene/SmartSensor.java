package com.example.smarthome.Scene;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adapter.SensorListAdaptor;
import com.example.smarthome.Database.Sensor;
import com.example.smarthome.MQTT.ClientMQTT;
import com.example.smarthome.R;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class SmartSensor extends AppCompatActivity {
    private List<Sensor> sensorList=new ArrayList<>();
    private Toolbar toolbar;
    private ClientMQTT clientMQTT;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smart_sensors);
        toolbar=findViewById(R.id.toolbar_back);
        initContent();
        initRecyclerView();
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void initRecyclerView()
    {

        sensorList= LitePal.findAll(Sensor.class);
        if(sensorList.size()!=0){
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            LinearLayoutManager linearLayout = new LinearLayoutManager(SmartSensor.this);
            recyclerView.setLayoutManager(linearLayout);
            SensorListAdaptor sensorListAdaptor = new SensorListAdaptor(sensorList);
            recyclerView.setAdapter(sensorListAdaptor);
            sensorListAdaptor.notifyDataSetChanged();
        }

    }
    private void  initContent()
    {

    }
}
