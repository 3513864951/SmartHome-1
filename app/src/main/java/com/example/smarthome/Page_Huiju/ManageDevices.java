package com.example.smarthome.Page_Huiju;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adapter.FindDeviceAdapter;
import com.example.smarthome.Adapter.ManageAdaptor;
import com.example.smarthome.Database.Device;
import com.example.smarthome.MQTT.ClientMQTT;
import com.example.smarthome.Page_Home.FindDevices;
import com.example.smarthome.Page_Samrt.AdjustTheLights;
import com.example.smarthome.R;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @description 弃用
 */
public class ManageDevices extends AppCompatActivity {
    private List<Map<String,String>> deviceList=new ArrayList<Map<String,String>>();
    private  List<Device> devicelist=new ArrayList<>();
    private ClientMQTT clientMQTT;
    private Button update_device;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.managedevices);
        update_device=findViewById(R.id.update_device);
        initContent();
    }

    private void initContent()
    {
        deviceList.clear();
        devicelist= LitePal.order("device_type desc").where("flag= ?","1").find(Device.class);
        for(Device devices:devicelist) {
            int count=0;
            String target_long_address = devices.getTarget_long_address();
            int flag = devices.getFlag();
            String source_command = devices.getDevice_type();
            String target_short_adress=devices.getTarget_short_address();
            String network_flag=devices.getNetwork_flag();
            String controller_long_address=devices.getController_long_address();
            Map<String, String> map = new HashMap<>();
            map.put("device_type", source_command);
            map.put("network_flag",network_flag);
            map.put("target_short_address",target_short_adress);
            map.put("target_long_address", target_long_address);
            map.put("flag", String.valueOf(flag));
            map.put("controller_long_address",controller_long_address);
            for(Map<String,String> map2:deviceList) {
                String target_long_address2=map2.get("target_long_address");
                if(target_long_address2.equals(target_long_address))
                    count++;
            }
            if(count==0)
                deviceList.add(map);

        }
    }

}
