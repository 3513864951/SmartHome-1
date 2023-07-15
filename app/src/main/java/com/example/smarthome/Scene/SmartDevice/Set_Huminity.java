package com.example.smarthome.Scene.SmartDevice;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adapter.HumidifierAdaptor;
import com.example.smarthome.Database.Device;
import com.example.smarthome.Database.Scene.Mission;
import com.example.smarthome.Database.Scene.S_Device;
import com.example.smarthome.Database.Scene.Temp;
import com.example.smarthome.R;
import com.google.android.material.button.MaterialButton;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * @description 加湿器
 */
public class Set_Huminity extends AppCompatActivity {
    RecyclerView select_hum;
    CardView open,close;
    MaterialButton create_home;
    private int currentPosition = 0;
    private Mission mission;
    Toolbar voice_tb;
    private MaterialButton create;
    public static final String TIME="time";
    RecyclerView recyclerView;
    private HumidifierAdaptor HumidifierAdaptor;
    private List<Device> mAirList=new ArrayList<>();
    private List<Integer> positionList=new ArrayList<>();//储存选择的电器
    private List<Device> deviceChoose=new ArrayList<>();
    private int flag=-1;//判断是否是创建新的条件
    private int min=-1;
    private int mid=-1;
    private int max=-1;
    private int warm=-1;
    private int cold=-1;
    private int wind=-1;
    private int open1=-1;
    private int close1=-1;
    private int count=-1;
    private String timeIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_huminity);
        recyclerView=findViewById(R.id.select_hum);
        open=findViewById(R.id.open);
        close=findViewById(R.id.close);
        voice_tb=findViewById(R.id.voice_tb);
        create_home=findViewById(R.id.create_home);
        voice_tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(timeIn==null)
            flag=0;//新的
        else
            flag=1;
        if(timeIn!=null){
            mission= LitePal.where("time = ?",timeIn).findFirst(Mission.class,true);
            for(int i=0;i<mission.getS_deviceList().size();i++){
                String target_long_address=mission.getS_deviceList().get(i).getTarget_long_address();
                Device device=LitePal.where("target_long_address = ?",target_long_address).findFirst(Device.class);
                deviceChoose.add(device);
            }
        }
        mAirList=LitePal.where("device_type = ? and flag = ?","09","1").find(Device.class);
        clickListenerInit();
        recyclerView();
    }

    private void clickListenerInit() {
        open.setCardBackgroundColor(getResources().getColor(com.hb.dialog.R.color.actionsheet_gray));
        close.setCardBackgroundColor(getResources().getColor(com.hb.dialog.R.color.actionsheet_gray));
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(open1==-1){
                    open1=1;
                    close1=-1;
                    open.setCardBackgroundColor(getResources().getColor(R.color.yellow0));
                    close.setCardBackgroundColor(getResources().getColor(com.hb.dialog.R.color.actionsheet_gray));
                }else {
                    open1=-1;
                    open.setCardBackgroundColor(getResources().getColor(com.hb.dialog.R.color.actionsheet_gray));

                }

            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(close1==-1){
                    close1=1;
                    open1=-1;
                    close.setCardBackgroundColor(getResources().getColor(R.color.yellow0));
                    open.setCardBackgroundColor(getResources().getColor(com.hb.dialog.R.color.actionsheet_gray));
                }else {
                    close1=-1;
                    close.setCardBackgroundColor(getResources().getColor(com.hb.dialog.R.color.actionsheet_gray));

                }

            }
        });
        create_home.setOnClickListener(new View.OnClickListener() {
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
                    if(open1==-1&&close1==-1)
                        Toast.makeText(Set_Huminity.this,"请选择模式",Toast.LENGTH_SHORT).show();
                    else {
                       {
                            if(!positionList.isEmpty())
                            {//如果没有设备就“请添加执行的设备”
                                //好像忘了temp.setCondition
                                Temp temp= LitePal.findLast(Temp.class);
                                for (int i = 0; i < positionList.size(); i++) {
                                    int n=positionList.get(i);
                                    String target_long_address=mAirList.get(n).getTarget_long_address();
                                    String target_short_address=mAirList.get(n).getTarget_short_address();
                                    S_Device s_device=new S_Device();
                                    s_device.setDevice_type("09");
                                    mission.setJudge(6);
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
                                Toast.makeText(Set_Huminity.this,"请选择电器！",Toast.LENGTH_SHORT).show();
                        }
                    }

                }else
                    Toast.makeText(Set_Huminity.this,"请添加电器！",Toast.LENGTH_SHORT).show();
            }
        });
    }

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
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        HumidifierAdaptor =new HumidifierAdaptor(this,R.layout.scene_airlist,mAirList);
        recyclerView.setAdapter(HumidifierAdaptor);
        HumidifierAdaptor.setOnItemClickListner(new HumidifierAdaptor.OnItemClickListner() {
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
                HumidifierAdaptor.notifyDataSetChanged();

            }
        });
        HumidifierAdaptor.setCallBack(new HumidifierAdaptor.CallBack() {
            @Override
            public <T> void convert(HumidifierAdaptor.ViewHolder holder, T bean, int position) {
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
        HumidifierAdaptor.notifyDataSetChanged();
    }

}
