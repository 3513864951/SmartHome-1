package com.example.smarthome.View;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarthome.MQTT.ClientMQTT;
import com.example.smarthome.R;

import org.eclipse.paho.client.mqttv3.MqttException;
/**
 * @description 测试
 */
public class TestScene extends AppCompatActivity {
        private Button add_scene;
        private Button add_condition;
        private Button add_mission;
        private Button start_scene;
        private Button delete_scene;
        private Button delete_condition;
        private Button delete_mission;
        private Button forbid_scene;
        private ClientMQTT clientMQTT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testscene);
        add_scene = findViewById(R.id.add_scene);
        add_condition = findViewById(R.id.add_condition);
        add_mission = findViewById(R.id.add_mission);
        start_scene = findViewById(R.id.start_scene);
        delete_scene = findViewById(R.id.delete_scene);
        delete_condition = findViewById(R.id.delete_condition);
        delete_mission = findViewById(R.id.delete_mission);
        forbid_scene = findViewById(R.id.forbid_scene);
        clientMQTT = new ClientMQTT("light");
        try {
            clientMQTT.Mqtt_innit();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        clientMQTT.startReconnect(TestScene.this);
        initListenner();
    }
    private void initListenner(){
        add_scene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: add_scene button clicked
                clientMQTT.publishMessagePlus(null,"0x0000","0xFE", "0x0112E586ACE5A4A9E58786E5A487E59B9EE5AEB600","0x15");
            }
        });

        add_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: add_condition button clicked
                clientMQTT.publishMessagePlus(null,"0x0000","0xFE", "0x0312E586ACE5A4A9E58786E5A487E59B9EE5AEB60B17489E19004B1200001700","0x20");
            }
        });

        add_mission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: add_mission button clicked
                clientMQTT.publishMessagePlus(null,"0x0000","0xFE", "0x0512E586ACE5A4A9E58786E5A487E59B9EE5AEB6050202030180","0x1A");

            }
        });

        start_scene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: start_scene button clicked
                clientMQTT.publishMessagePlus(null,"0x0000","0xFE", "0x0712E586ACE5A4A9E58786E5A487E59B9EE5AEB600","0x15");

            }
        });

        delete_scene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: delete_scene button clicked
                clientMQTT.publishMessagePlus(null,"0x0000","0xFE", "0x0012E586ACE5A4A9E58786E5A487E59B9EE5AEB600","0x15");

            }
        });

        delete_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: delete_condition button clicked
                clientMQTT.publishMessagePlus(null,"0x0000","0xFE", "0x0212E586ACE5A4A9E58786E5A487E59B9EE5AEB60B17489E19004B1200001700","0x20");
            }
        });

        delete_mission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: delete_mission button clicked
                clientMQTT.publishMessagePlus(null,"0x0000","0xFE", "0x0412E586ACE5A4A9E58786E5A487E59B9EE5AEB6050202030180","0x1A");

            }
        });

        forbid_scene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: forbid_scene button clicked
                clientMQTT.publishMessagePlus(null,"0x0000","0xFE", "0x0612E586ACE5A4A9E58786E5A487E59B9EE5AEB600","0x15");

            }
        });



    }
}