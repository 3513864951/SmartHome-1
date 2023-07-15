package com.example.smarthome.Page_Samrt;

import android.animation.ObjectAnimator;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smarthome.Adapter.ManageAdaptor;
import com.example.smarthome.Database.Device;
import com.example.smarthome.MQTT.ClientMQTT;
import com.example.smarthome.R;
import com.example.smarthome.View.air_utils.AirBoardView;
import com.google.android.material.button.MaterialButton;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.litepal.LitePal;

public class AdjustTheAirCondition extends AppCompatActivity {
    Toolbar air_tb;
    MaterialButton add_time, arrow_up, arrow_down,yes,cancel;
    ObjectAnimator animation_min, animation_mid, animation_max;
    Boolean start_min = false;
    Boolean start_mid = false;
    Boolean start_max = false;
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    EditText mAppWidgetPrefix;
    TextView time_input;
    private LinearInterpolator mPanLin = new LinearInterpolator();
    ImageView ib_wind_min, ib_wind_mid, ib_wind_max, ib_worm, ib_cold, ib_wind;
    private String target_short_address;
    private String device_type;
    private AirBoardView airview;
    private int maxNum = 30;
    private int minNum = 16;
    private String temperature;
    private ClientMQTT clientMQTT;
    float time =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_the_air_condition);
        add_time = findViewById(R.id.add_time);
        air_tb = findViewById(R.id.air_tb);
        ib_wind_min = findViewById(R.id.ib_wind_min);
        ib_wind_mid = findViewById(R.id.ib_wind_mid);
        ib_wind_max = findViewById(R.id.ib_wind_max);
        ib_worm = findViewById(R.id.ib_worm);
        ib_cold = findViewById(R.id.ib_cold);
        ib_wind = findViewById(R.id.ib_wind);
        arrow_up = findViewById(R.id.arrow_up);
        arrow_down = findViewById(R.id.arrow_down);
        time_input=findViewById(R.id.time_input);
        yes = findViewById(R.id.yes);
        cancel = findViewById(R.id.cancel);
        clientMQTT = new ClientMQTT("light");
        try {
            clientMQTT.Mqtt_innit();
        } catch (
                MqttException e) {
            e.printStackTrace();
        }
        clientMQTT.startReconnect(AdjustTheAirCondition.this);
        Intent intent = getIntent();
        String target_long_address = intent.getStringExtra(ManageAdaptor.TARGET_LONG_ADDRESS);
        Device device = LitePal.where("target_long_address = ?", target_long_address).findFirst(Device.class);
        target_short_address = device.getTarget_short_address();
        device_type = device.getDevice_type();
        time_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        arrow_up.setOnClickListener(v -> {
            time+=0.5;
            time_input.setText(""+time);
        });
        arrow_down.setOnClickListener(v -> {
            if(time>=0.5){
                time-=0.5;
                time_input.setText(""+time);
            }
        });
        air_tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        yes.setOnClickListener(v -> {

        });
        rote();
        airview = findViewById(R.id.airView);

        airview.setOnAirClickListener(new AirBoardView.OnAirClickListener() {
            @Override
            public void onAirClick(String temp) {
                if (temp.length() == 3) {
                    temperature = temp.substring(0, 2);
                } else if (temp.length() == 2) {
                    temperature = temp.substring(0, 1);
                }
                String hexTemp = Integer.toHexString(Integer.valueOf(temperature));
                clientMQTT.publishMessagePlus(null, "0x" + target_short_address, device_type, "0x08" + hexTemp, "0x02");
            }
        });
    }

    private void rote() {
        ib_worm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clientMQTT.publishMessagePlus(null, "0x" + target_short_address, device_type, "0x02", "0x01");
            }
        });
        ib_cold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clientMQTT.publishMessagePlus(null, "0x" + target_short_address, device_type, "0x03", "0x01");
            }
        });
        ib_wind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clientMQTT.publishMessagePlus(null, "0x" + target_short_address, device_type, "0x05", "0x01");
            }
        });
        ib_wind_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation_min = ObjectAnimator.ofFloat(ib_wind_min, "rotation", 0f, 360.0f);
                animation_min.setInterpolator(new LinearInterpolator());//匀速
                animation_min.setRepeatCount(-1);//无线循环
                animation_min.setDuration(2000);
                start_min = true;
                if (start_mid) {
                    animation_mid.pause();
                }
                if (start_max == true) {
                    animation_max.pause();
                }
                if (start_min == true) {
                    animation_min.pause();
                }
                animation_min.start();
                clientMQTT.publishMessagePlus(null, "0x" + target_short_address, device_type, "0x0701", "0x02");
            }
        });
        ib_wind_mid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation_mid = ObjectAnimator.ofFloat(ib_wind_mid, "rotation", 0f, 360.0f);
                animation_mid.setInterpolator(new LinearInterpolator());//匀速
                animation_mid.setRepeatCount(-1);//无线循环
                animation_mid.setDuration(1300);
                start_mid = true;
                if (start_min == true) {
                    animation_min.pause();
                }
                if (start_max == true) {
                    animation_max.pause();
                }
                if (start_mid == true) {
                    animation_mid.pause();
                }
                animation_mid.start();
                clientMQTT.publishMessagePlus(null, "0x" + target_short_address, device_type, "0x0702", "0x02");
            }
        });
        ib_wind_max.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation_max = ObjectAnimator.ofFloat(ib_wind_max, "rotation", 0f, 360.0f);
                animation_max.setInterpolator(new LinearInterpolator());//匀速
                animation_max.setRepeatCount(-1);//无线循环
                animation_max.setDuration(900);
                start_max = true;
                if (start_min) {
                    animation_min.pause();
                }
                if (start_mid == true) {
                    animation_mid.pause();
                }
                if (start_max == true) {
                    animation_max.pause();
                }
                animation_max.start();
                clientMQTT.publishMessagePlus(null, "0x" + target_short_address, device_type, "0x0703", "0x02");
            }
        });

    }
}
