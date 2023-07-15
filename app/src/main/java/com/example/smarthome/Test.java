package com.example.smarthome;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarthome.Database.Device;
import com.example.smarthome.MQTT.ClientMQTT;
import com.example.smarthome.Scan.ScanActivity;

import org.litepal.LitePal;

import java.util.List;
/**
 * @description 只为测试
 */
public class Test extends AppCompatActivity {
    private Button test_light_normal;
    private Button test_light_open;
    private Button test_light_close;
    private Button test_light_bt_bright;
    private Button test_light_breathe;
    private Button test_light_bright_de;
    private Button test_light_bright_in;
    private Button test_air_open;
    private Button test_air_close;
    private Button test_curtain_open;
    private Button test_curtain_close;
    private Button test_curtain_bt_deep;
    private Button delete_device;

    private EditText test_light_et_bright;
    private EditText test_curtain_et_deep;

    private TextView test_air_temp;
    private TextView test_air_wetness;
    private ClientMQTT clientMQTT;
    private static int bright;
    private static int deep;
    private List<Device> lightList;
    private List<Device> curtainList;
    private List<Device> airList;
    private List<Device> musicList;

    private String target_short_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        clientMQTT = new ClientMQTT("test");
        clientMQTT.Subscribe(Test.this);
        test_light_normal = findViewById(R.id.test_light_normal);
        test_light_open = findViewById(R.id.test_light_open);
        test_light_close = findViewById(R.id.test_light_close);
        test_light_bt_bright = findViewById(R.id.test_light_bt_bright);
        test_light_breathe = findViewById(R.id.test_light_breathe);
        test_light_bright_de = findViewById(R.id.test_light_bright_de);
        test_light_bright_in = findViewById(R.id.test_light_bright_in);
        delete_device=findViewById(R.id.delete_device2);
        test_air_open = findViewById(R.id.test_air_open);
        test_air_close = findViewById(R.id.test_air_close);
        test_curtain_open = findViewById(R.id.test_curtain_open);
        test_curtain_close = findViewById(R.id.test_curtain_close);
        test_curtain_bt_deep = findViewById(R.id.test_curtain_bt_deep);
        test_light_et_bright = findViewById(R.id.test_light_et_bright);
        test_curtain_et_deep = findViewById(R.id.test_curtain_et_deep);
        test_air_temp = findViewById(R.id.test_air_temp);
        test_air_wetness = findViewById(R.id.test_air_wetness);
        lightList= LitePal.order("device_type desc").where("flag= ? and device_type= ?","1","01").find(Device.class);
        curtainList= LitePal.order("device_type desc").where("flag= ? and device_type= ?","1","03").find(Device.class);
        airList= LitePal.order("device_type desc").where("flag= ? and device_type= ?","1","02").find(Device.class);
//        musicList= LitePal.order("device_type desc").where("flag= ? and device_type= ?","1","01").find(Device.class);
    delete_device.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!lightList.isEmpty()){
                target_short_address=lightList.get(0).getTarget_short_address();
                clientMQTT.publishMessagePlusForActivity("null",target_short_address,"01","0004"+lightList.get(0).getTarget_long_address(),"02");
            }
        }
    });
        test_light_normal.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                if(!lightList.isEmpty()){
                 target_short_address=lightList.get(0).getTarget_short_address();
                    clientMQTT.publishMessagePlusForActivity("null",target_short_address,"01","0105","02");
                }

            }
        });

        test_light_bt_bright.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                if(!lightList.isEmpty()){
                    target_short_address=lightList.get(0).getTarget_short_address();
                    clientMQTT.publishMessagePlusForActivity("null",target_short_address,"01","0104"+bright,"03");
                }
            }
        });
        test_light_breathe.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                if(!lightList.isEmpty()){
                    target_short_address=lightList.get(0).getTarget_short_address();
                    clientMQTT.publishMessagePlusForActivity("null",target_short_address,"01","0201","02");
                }
            }
        });
        test_light_bright_de.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                if(!lightList.isEmpty()){
                    target_short_address=lightList.get(0).getTarget_short_address();
                    clientMQTT.publishMessagePlusForActivity("null",target_short_address,"01","0103","02");
                }
            }
        });
        test_light_bright_in.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                if(!lightList.isEmpty()){
                    target_short_address=lightList.get(0).getTarget_short_address();
                    clientMQTT.publishMessagePlusForActivity("null",target_short_address,"01","0102","02");
                }
            }
        });
        test_air_open.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                if(!airList.isEmpty()){
                    target_short_address=airList.get(0).getTarget_short_address();
                    clientMQTT.publishMessagePlusForActivity("null",target_short_address,"02","0101","02");
                }
            }
        });
        test_air_close.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                if(!airList.isEmpty()){
                    target_short_address=airList.get(0).getTarget_short_address();
                    clientMQTT.publishMessagePlusForActivity("null",target_short_address,"02","0001","02");
                }
            }
        });
        test_curtain_open.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                if(!curtainList.isEmpty()){
                    target_short_address=curtainList.get(0).getTarget_short_address();
                    clientMQTT.publishMessagePlusForActivity("null",target_short_address,"03","80","01");
                }
            }
        });
        test_curtain_close.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                if(!curtainList.isEmpty()){
                    target_short_address=curtainList.get(0).getTarget_short_address();
                    clientMQTT.publishMessagePlusForActivity("null",target_short_address,"03","00","01");
                }
            }
        });test_curtain_bt_deep.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                if(!curtainList.isEmpty()){
                    target_short_address=curtainList.get(0).getTarget_short_address();
                    clientMQTT.publishMessagePlusForActivity("null",target_short_address,"03",deep+"","01");
                }
            }
        });
        test_light_et_bright.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence charSequence,int i, int i1, int i2){

            }

            @Override
            public void onTextChanged (CharSequence charSequence,int i, int i1, int i2){
                String s_bright = charSequence.toString();
                bright = Integer.valueOf(s_bright);
            }

            @Override
            public void afterTextChanged (Editable editable){

            }
        });
        test_curtain_et_deep.addTextChangedListener(new
                                                            TextWatcher() {
                                                                            @Override
                                                                            public void beforeTextChanged (CharSequence charSequence,int i, int i1, int i2){

                                                                            }

                                                                            @Override
                                                                            public void onTextChanged (CharSequence charSequence,int i, int i1, int i2){
                                                                                String s_deep = charSequence.toString();
                                                                                deep = Integer.valueOf(s_deep);
                                                                            }

                                                                            @Override
                                                                            public void afterTextChanged (Editable editable){

                                                                            }
                                                                        });


    }


}


