package com.example.smarthome.Page_Samrt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.smarthome.Database.Device;
import com.example.smarthome.Database.Room;
import com.example.smarthome.MQTT.ClientMQTT;
import com.example.smarthome.R;
import com.king.view.arcseekbar.ArcSeekBar;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdustTheCurtain extends AppCompatActivity {
    Toolbar curtain_tb;
    private ClientMQTT clientMQTT;
    private Spinner spinner_choose_home;
    private Spinner spinner_choose_model;
    private CardView bt_openAll;
    private CardView bt_closeCurtain;
    private int home_choose;
    private String s_home_choose;
    private ArcSeekBar deep;
    float progress1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adust_the_curtain);
        curtain_tb = findViewById(R.id.curtain_tb);
        bt_openAll = findViewById(R.id.open_all);
        deep=findViewById(R.id.deep);

        bt_closeCurtain = findViewById(R.id.close_curtain);
        clientMQTT = new ClientMQTT("light");
        try {
            clientMQTT.Mqtt_innit();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        clientMQTT.startReconnect(AdustTheCurtain.this);
        Intent intent=getIntent();
        String target_long_address=intent.getStringExtra(Device.TARGET_LONG_ADDRESS);
        Device device=LitePal.where("target_long_address = ?",target_long_address).findFirst(Device.class);
        String target_short_address=device.getTarget_short_address();
        String device_type=device.getDevice_type();
        deep.setOnChangeListener(new ArcSeekBar.OnChangeListener() {
            @Override
            public void onStartTrackingTouch(boolean isCanDrag) {

            }

            @Override
            public void onProgressChanged(float progress, float max, boolean fromUser) {
        progress1=progress;
            }
            @Override
            public void onStopTrackingTouch(boolean isCanDrag) {
                String extent=String.valueOf(Integer.valueOf((int) progress1));
                if(extent.length()==1){
                    extent="0"+extent;
                }
                clientMQTT.publishMessagePlus(null,"0x"+target_short_address,"0x"+device_type,"0x"+extent,"0x01");

            }

            @Override
            public void onSingleTapUp() {

            }
        });

        bt_openAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clientMQTT.publishMessagePlus(null,"0x"+target_short_address,"0x"+device_type,"0x80","0x01");
            }
        });

        bt_closeCurtain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clientMQTT.publishMessagePlus(null,"0x"+target_short_address,"0x"+device_type,"0x00","0x01");
            }
        });
    }

    }


