package com.example.smarthome.Scene.SmartDevice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.smarthome.Adapter.AirListAdaptor;
import com.example.smarthome.Database.Device;
import com.example.smarthome.Database.Scene.Condition;
import com.example.smarthome.Database.Scene.Mission;
import com.example.smarthome.Database.Scene.S_Device;
import com.example.smarthome.Database.Scene.Temp;
import com.example.smarthome.R;
import com.example.smarthome.View.CustomizeGoodsAddView;
import com.google.android.material.button.MaterialButton;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Set_air extends AppCompatActivity {
    public static final String TIME="time";
    RecyclerView recyclerView;
    private AirListAdaptor airListAdaptor;
    private List<Device> mAirList=new ArrayList<>();
    private List<Integer> positionList=new ArrayList<>();//储存选择的电器
    private List<Device> deviceChoose=new ArrayList<>();
    private Mission mission;
    private MaterialButton create;
    private CustomizeGoodsAddView customizeGoodsAddView;
    private ImageView wind_min;
    private ImageView wind_mid;
    private ImageView wind_max;
    private ImageView set_warm;
    private ImageView set_cold;
    private ImageView set_wind;
    private int flag=-1;//判断是否是创建新的条件
    private int min=-1;
    private int mid=-1;
    private int max=-1;
    private int warm=-1;
    private int cold=-1;
    private int wind=-1;
    private int count=-1;
    private String timeIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_air);
        Intent intent=getIntent();
        timeIn=intent.getStringExtra(Set_air.TIME);
        create=findViewById(R.id.create_home);
        //判断是否为再次编辑

        if(timeIn==null)
            flag=0;//新的
        else
            flag=1;
        if(timeIn!=null){
            mission=LitePal.where("time = ?",timeIn).findFirst(Mission.class,true);
            for(int i=0;i<mission.getS_deviceList().size();i++){
                String target_long_address=mission.getS_deviceList().get(i).getTarget_long_address();
                Device device=LitePal.where("target_long_address = ?",target_long_address).findFirst(Device.class);
                deviceChoose.add(device);
            }
        }
        mAirList=LitePal.where("device_type = ? and flag = ?","02","1").find(Device.class);
        clickListenerInit();
        recyclerView();
        //还有点进去判断编辑那通过长地址更新不对，太少了，应该还要通过mission_id或者其他东西，比如时间,毕竟有可能把其他场景的也set了
    }
    //记录当前位置
    private int currentPosition = 0;
    //初始化点击事件

    private void recyclerView() {
        /**
         * 清除已经设置的电器
         */
        for (int i = 0; i < mAirList.size(); i++) {
            String target_long_address=mAirList.get(i).getTarget_long_address();
            for(int j=0;j<deviceChoose.size();j++)
                if(target_long_address.equals(deviceChoose.get(j).getTarget_long_address())){
                    mAirList.remove(i);
                    break;
                }
        }
        recyclerView=findViewById(R.id.select_air);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        airListAdaptor=new AirListAdaptor(this,R.layout.scene_airlist,mAirList);
        recyclerView.setAdapter(airListAdaptor);
        airListAdaptor.setOnItemClickListner(new AirListAdaptor.OnItemClickListner() {
            @Override
            public void onItemClickListner(View v, int position) {
                currentPosition = position;
                if(positionList.isEmpty())
                    positionList.add(position);//选择多项设备通过字符串储存选择的位置，那要是选择两遍呢?遍历，有就删除，没有就添加
                else {
                    for (int i = 0; i < positionList.size(); i++) {
                        if (positionList.get(i) == position) {
                            positionList.remove(i);
                            count=i;
                        }
                    }
                    if(count==-1)
                    {
                        positionList.add(position);//选择多项设备通过字符串储存选择的位置，那要是选择两遍呢?遍历，有就删除，没有就添加

                    }
                }
                airListAdaptor.notifyDataSetChanged();

            }
        });
        airListAdaptor.setCallBack(new AirListAdaptor.CallBack() {
            @Override
            public <T> void convert(AirListAdaptor.ViewHolder holder, T bean, int position) {
                ConstraintLayout constraintLayout = (ConstraintLayout) holder.getView(R.id.constraintLayout);
                if(positionList.isEmpty())
                    constraintLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                for (int i = 0; i < positionList.size(); i++) {
                    if (positionList.get(i) == position) {
                        constraintLayout.setBackgroundResource(R.drawable.blackbackground);
                    }
                    else
                        constraintLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
            }
        });
        airListAdaptor.notifyDataSetChanged();
    }


    private void clickListenerInit(){
        customizeGoodsAddView=findViewById(R.id.select_temp);
        customizeGoodsAddView.setMaxValue(30);
        customizeGoodsAddView.setMinValue(16);
//        customizeGoodsAddView.setValue(temperature);//用来设置初始温度
        customizeGoodsAddView.setValue(16);
        customizeGoodsAddView.setOnValueChangeListene(new CustomizeGoodsAddView.OnValueChangeListener() {
            @Override
            public void onValueChange(int value) {
                if(value>30){
                    customizeGoodsAddView.setValue(30);

                }else if(value<16){
                    customizeGoodsAddView.setValue(16);
                }
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                Date date=new Date(System.currentTimeMillis());
                String time=simpleDateFormat.format(date);
                if(flag==0)   //flag为0就新建Condition
                {
                    Temp temp=LitePal.findLast(Temp.class);
                    mission=new Mission();
                    mission.setTime(time);
                    mission.setTemp(temp);
                    temp.getMissionList().add(mission);


                }else
                {
                    List<Mission> missionList=LitePal.where("time = ?",timeIn).find(Mission.class);
                    mission=missionList.get(0);
                }
                if(positionList!=null){
                    if(mid==-1&&min==-1&&max==-1)
                        Toast.makeText(Set_air.this,"请选择风速",Toast.LENGTH_SHORT).show();
                    else {
                        if(cold==-1&&warm==-1&&wind==-1){
                            Toast.makeText(Set_air.this,"请选择模式",Toast.LENGTH_SHORT).show();
                        }else {
                            if(!positionList.isEmpty())
                            {//如果没有设备就“请添加执行的设备”
                                //好像忘了temp.setCondition
                                Temp temp= LitePal.findLast(Temp.class);
                                for (int i = 0; i < positionList.size(); i++) {
                                    int n=positionList.get(i);
                                    String target_long_address=mAirList.get(n).getTarget_long_address();
                                    String target_short_address=mAirList.get(n).getTarget_short_address();
                                    S_Device s_device=new S_Device();
                                    s_device.setDevice_type("02");
                                    if(warm!=-1)
                                        s_device.setAir_model("1");
                                    if(cold!=-1)
                                        s_device.setAir_model("2");
                                    if(wind!=-1)
                                        s_device.setAir_model("3");
                                    if(min!=-1)
                                        s_device.setWind("1");
                                    if(mid!=-1)
                                        s_device.setWind("2");
                                    if(max!=-1)
                                        s_device.setAir_model("3");
                                    mission.setJudge(4);
                                    s_device.setTarget_short_address(target_short_address);
                                    s_device.setTarget_long_address(target_long_address);
                                    s_device.setTemp(temp);
                                    if(flag==0)
                                    {
                                        s_device.setMission(mission);
                                        mission.getS_deviceList().add(s_device);
                                        temp.getMissionList().add(mission);
                                        mission.setTemp(temp);
                                        mission.save();
                                        Device device=new Device();
                                        device.updateAll("target_long_address = ?",target_long_address);
                                        s_device.save();
                                    }
                                    else{
                                        s_device.updateAll("target_long_address = ? and mission_id = ?",target_long_address,mission.getId()+"");
                                        mission.updateAll("time = ?",timeIn);
                                    }
                                    finish();
                                }
                            }else
                                Toast.makeText(Set_air.this,"请选择电器！",Toast.LENGTH_SHORT).show();
                        }
                    }

                }else
                    Toast.makeText(Set_air.this,"请添加电器！",Toast.LENGTH_SHORT).show();
            }
        });
        wind_min=findViewById(R.id.ib_wind_min);
        wind_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add your code here
                if(mid==-1&&max==1)
                    min=1;
                else
                {
                    min=1;
                    max=-1;
                    mid=-1;
                }

            }
        });
        wind_mid=findViewById(R.id.ib_wind_mid);
        wind_mid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add your code here
                if(min==-1&&max==1)
                    mid=1;
                else
                {
                    mid=1;
                    max=-1;
                    min=-1;
                }
            }
        });
        wind_max=findViewById(R.id.ib_wind_max);
        wind_max.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add your code here
                if(mid==-1&&min==1)
                    max=1;
                else
                {
                    max=1;
                    min=-1;
                    mid=-1;
                }
            }
        });
        set_warm=findViewById(R.id.ib_set_warm);
        set_warm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add your code here
                if(cold==-1&&wind==1)
                    warm=1;
                else
                {
                    warm=1;
                    cold=-1;
                    wind=-1;
                }
            }
        });
        set_cold=findViewById(R.id.ib_set_clod);
        set_cold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add your code here
                if(warm==-1&&wind==1)
                    cold=1;
                else
                {
                    cold=1;
                    warm=-1;
                    wind=-1;
                }
            }
        });
        set_wind=findViewById(R.id.ib_set_wind);
        set_wind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add your code here
                if(cold==-1&&warm==1)
                    wind=1;
                else
                {
                    wind=1;
                    cold=-1;
                    warm=-1;
                }
            }
        });
    }

}