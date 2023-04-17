package com.example.smarthome.Page_Home;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.smarthome.Adapter.FindDeviceAdapter;
import com.example.smarthome.Database.Device;
import com.example.smarthome.MQTT.ClientMQTT;
import com.example.smarthome.Page_Samrt.AdjustTheLights;
import com.example.smarthome.R;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
/**
 * @description 测试用
 */
public class FindDevices extends AppCompatActivity {
    private List<Map<String,String>> deviceList=new ArrayList<Map<String,String>>();
    private  List<Device> devicelist=new ArrayList<>();
    private Switch aSwitch;

    private ClientMQTT clientMQTT;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private Handler mHandler = new Handler();

    // 创建一个Runnable对象，该对象将在定时刷新时被执行

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finddevices);
        swipeRefreshLayout=findViewById(R.id.swipeRefresh);
        aSwitch=findViewById(R.id.switch1);
        SharedPreferences sharedPreferences = getSharedPreferences("app_data", MODE_PRIVATE);
        boolean switchStatus = sharedPreferences.getBoolean("switch_status", false);
        aSwitch.setChecked(switchStatus);
        Device device=new Device();
        device.setToDefault("isUpdate");
        device.updateAll();
        //防止不打开按钮一刷新就显示了

        clientMQTT=new ClientMQTT("light");
        try {
            clientMQTT.Mqtt_innit();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        clientMQTT.startReconnect(FindDevices.this);
        deviceList.clear();
        devicelist= LitePal.order("device_type desc").where("flag= ? and isUpdate= ?","0","1").find(Device.class);
        initContent();
        recyclerView = (RecyclerView) findViewById(R.id.recy_devices);
        LinearLayoutManager linearLayout = new LinearLayoutManager(FindDevices.this);
        recyclerView.setLayoutManager(linearLayout);
        FindDeviceAdapter findDeviceAdapter = new FindDeviceAdapter(deviceList);
        recyclerView.setAdapter(findDeviceAdapter);
        findDeviceAdapter.notifyDataSetChanged();
        //设置进度View样式的大小，只有两个值DEFAULT和LARGE
//设置进度View下拉的起始点和结束点，scale 是指设置是否需要放大或者缩小动画
        swipeRefreshLayout.setProgressViewOffset(true, -0, 100);
//设置进度View下拉的结束点，scale 是指设置是否需要放大或者缩小动画
        swipeRefreshLayout.setProgressViewEndTarget(true, 180);
//设置进度View的组合颜色，在手指上下滑时使用第一个颜色，在刷新中，会一个个颜色进行切换
        swipeRefreshLayout.setColorSchemeColors(Color.BLACK, Color.GREEN, Color.RED, Color.YELLOW, Color.BLUE);

//设置触发刷新的距离
        swipeRefreshLayout.setDistanceToTriggerSync(200);
//如果child是自己自定义的view，可以通过这个回调，告诉mSwipeRefreshLayoutchild是否可以滑动
        swipeRefreshLayout.setOnChildScrollUpCallback(null);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                deviceList.clear();
                                devicelist= LitePal.order("device_type desc").where("flag= ? and isUpdate= ?","0","1").find(Device.class);
                                initContent();
                                recyclerView = (RecyclerView) findViewById(R.id.recy_devices);
                                LinearLayoutManager linearLayout = new LinearLayoutManager(FindDevices.this);
                                recyclerView.setLayoutManager(linearLayout);
                                FindDeviceAdapter findDeviceAdapter = new FindDeviceAdapter(deviceList);
                                recyclerView.setAdapter(findDeviceAdapter);
                                findDeviceAdapter.notifyDataSetChanged();
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                }).start();
            }
        });

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                SharedPreferences sharedPreferences = getSharedPreferences("app_data", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("switch_status", isChecked);
                editor.apply();
                if(isChecked){
                    //之后在数据库加一个是否是点击入网后能够获得的电器isupdate,解析那里的litepal要记得让isupdate为1，这里switch变成false后再全弄为0
//                    clientMQTT.Subscribe();//没有显示不要担心，做一个下拉刷新就好了
                    clientMQTT.publishMessagePlus(null,"0x0000","0xFF", "0x0002","0x02");
                    deviceList.clear();
                    initContent();
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recy_devices);
                    LinearLayoutManager linearLayout = new LinearLayoutManager(FindDevices.this);
                    recyclerView.setLayoutManager(linearLayout);
                    FindDeviceAdapter findDeviceAdapter = new FindDeviceAdapter(deviceList);
                    recyclerView.setAdapter(findDeviceAdapter);
                    findDeviceAdapter.notifyDataSetChanged();
                }else {
                    //setIsUpdate全部都set成0
                    Device device=new Device();
                    device.setToDefault("isUpdate");
                    device.updateAll();

                }
            }
        });
        mHandler.postDelayed(mRunnable, 1000);
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            initContent();
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recy_devices);
            LinearLayoutManager linearLayout = new LinearLayoutManager(FindDevices.this);
            recyclerView.setLayoutManager(linearLayout);
            FindDeviceAdapter findDeviceAdapter = new FindDeviceAdapter(deviceList);
            recyclerView.setAdapter(findDeviceAdapter);
            findDeviceAdapter.notifyDataSetChanged();
            // 在Runnable对象中调用RecyclerView的notifyDataSetChanged()方法
//            recyclerView.getAdapter().notifyDataSetChanged();
            // 使用postDelayed()方法在Handler对象中定时调用Runnable对象
            mHandler.postDelayed(mRunnable, 1000);
        }
    };

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Device device=new Device();
        device.setIsUpdate(0);
        device.updateAll();
//        clientMQTT=null;//防止后台多次处理数据，这个由于退出后switch仍保存之前状态，那就不能置null了
    }
    private void initContent()
    {

        deviceList.clear();
        devicelist= LitePal.order("device_type desc").where("flag= ? and isUpdate= ?","0","1").find(Device.class);
        if(!devicelist.isEmpty())
            for(Device devices:devicelist) {
                int count=0;//判断是否List已经有了重复的
            String source_long_address = devices.getTarget_long_address();
            int flag = devices.getFlag();
            String source_command = devices.getDevice_type();
            String target_short_adress=devices.getTarget_short_address();
            String target_long_address=String.valueOf(source_long_address);
            Map<String, String> map = new HashMap<>();
            map.put("device_type", source_command);
            map.put("target_short_address",target_short_adress);
            map.put("target_long_address", target_long_address);
            map.put("flag", String.valueOf(flag));
            List<Device> devices1=LitePal.order("device_type desc").where("target_long_address = ?",target_long_address).find(Device.class);
                for(Map<String,String> map2:deviceList) {
                    String target_long_address2=map2.get("target_long_address");
                    if(target_long_address2.equals(target_long_address))
                        count++;
                }
                if(count==0)
                    deviceList.add(map);
        }
    }
//    class MyRunnable implements Runnable{
//        @Override
//        public void run() {
//            devicelist= LitePal.order("source_command desc").where("flag = ? and isUpdate = ?","0","1").find(Device.class);
//            while (devicelist.isEmpty()) {
//                devicelist= LitePal.order("source_command desc").where("flag = ? and isUpdate = ?","0","1").find(Device.class);
//            }
//            deviceList.clear();
//            initContent();
//            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recy_devices);
//            LinearLayoutManager linearLayout = new LinearLayoutManager(FindDevices.this);
//            recyclerView.setLayoutManager(linearLayout);
//            FindDeviceAdapter findDeviceAdapter = new FindDeviceAdapter(deviceList);
//            recyclerView.setAdapter(findDeviceAdapter);
//            findDeviceAdapter.notifyDataSetChanged();
//        }
//    }

}
