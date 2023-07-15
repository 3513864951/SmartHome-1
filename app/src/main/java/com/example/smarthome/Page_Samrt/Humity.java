package com.example.smarthome.Page_Samrt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.smarthome.Adapter.ManageAdaptor;
import com.example.smarthome.MQTT.ClientMQTT;
import com.example.smarthome.R;

import org.eclipse.paho.client.mqttv3.MqttException;
/**
 * @description 加湿器
 */
public class Humity extends AppCompatActivity {
    CardView open,close;
    ClientMQTT clientMQTT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.huminity);
        open=findViewById(R.id.open);
        close=findViewById(R.id.close);
        Intent intent=getIntent();
        String target_short_address=intent.getStringExtra(ManageAdaptor.TARGET_SHORT_ADDRESS);
        clientMQTT=new ClientMQTT("light");
        try {
            clientMQTT.Mqtt_innit();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        clientMQTT.startReconnect(Humity.this);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clientMQTT.publishMessagePlus(null,"0x"+target_short_address,"0x09","0x01","0x01");
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clientMQTT.publishMessagePlus(null,"0x"+target_short_address,"0x09","0x00","0x01");

            }
        });
    }
}
