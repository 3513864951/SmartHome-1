package com.example.smarthome.Scene.SmartDevice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smarthome.Adapter.LightListAdaptor;
import com.example.smarthome.Database.Device;
import com.example.smarthome.Database.Scene.Condition;
import com.example.smarthome.Database.Scene.Mission;
import com.example.smarthome.Database.Scene.S_Device;
import com.example.smarthome.Database.Scene.Temp;
import com.example.smarthome.R;
import com.example.smarthome.View.StepSeekBar;
import com.github.iielse.switchbutton.SwitchView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Set_lights extends AppCompatActivity {
    public static final String TIME="time";
    RecyclerView recyclerView;
    private Mission mission;
    private Temp temp;
    private StepSeekBar brightness;
    private LinearLayout linearLayout;
//    private SwitchView light_normal;
//    private SwitchView light_sleep;
    private SwitchView light_breathe;
//    private SwitchView light_else;
    private MaterialButton create;
    private TextView set_light_1;
    private TextView set_light_2;
    private TextView set_light_3;
    private TextView set_light_4;
    private TextView set_all_lights;
    private LightListAdaptor lightListAdaptor;
    private List<Map<String, String>> mLightList=new ArrayList<>();
    private List<Device> lightList=new ArrayList<>();
    private List<Device> deviceChoose=new ArrayList<>();//之前选择的电器
    private List<Integer> positionList=new ArrayList<>();//储存选择的电器
    private int count = -1;
    private int bright=-1;
    //model只能选一个
    private int normal=-1;
    private int sleep=-1;
    private int breathe=-1;
    private int other=-1;
    private int flag=-1;//判断是否是创建新的条件
    private int light1=-1;
    private int light2=-1;
    private int light3=-1;
    private int light4=-1;
    private int all_lights=-1;
    int currentPosition=0;
//    List<Integer> positionList=new ArrayList<>();
    private String timeIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_lights_scene);
        Intent intent=getIntent();
        timeIn=intent.getStringExtra(Set_lights.TIME);
        brightness = findViewById(R.id.brightness);
        set_light_1=findViewById(R.id.set_light_1);
        set_light_2=findViewById(R.id.set_light_2);
        set_light_3=findViewById(R.id.set_light_3);
        set_light_4=findViewById(R.id.set_light_4);
        set_all_lights=findViewById(R.id.set_all_lights);
//        light_normal = findViewById(R.id.light_normal);
//        light_sleep = findViewById(R.id.light_sleep);
        light_breathe = findViewById(R.id.light_breathe);
//        light_else = findViewById(R.id.light_else);
        create=findViewById(R.id.create_home);
        linearLayout=findViewById(R.id.linearLayout);
        clickListenerInit();

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
        lightList=LitePal.where("device_type = ? and flag = ? and use = ?","01","1","0").find(Device.class);
        recyclerView();
    }

    private void recyclerView() {
//        for (int i = 0; i < lightList.size(); i++) {
//            String target_long_address=lightList.get(i).getTarget_long_address();
//            for(int j=0;j<deviceChoose.size();j++){
//                Log.i("----------------------C",deviceChoose.size()+"");
//                Log.i("----------------------L",lightList.size()+"");
//                if(target_long_address.equals(deviceChoose.get(j).getTarget_long_address())){
//                    lightList.remove(i);
//                    break;
//                }
//            }
//        }
        recyclerView = findViewById(R.id.select_light);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //有任务的不能显示，再Parse那里的短地址
        lightListAdaptor = new LightListAdaptor(this,R.layout.scenelightlist,lightList);
        recyclerView.setAdapter(lightListAdaptor);

        lightListAdaptor.setOnItemClickListner(new LightListAdaptor.OnItemClickListner()
        {
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
                lightListAdaptor.notifyDataSetChanged();
            }
        });
        /**
         * @监听回调 
         */
        lightListAdaptor.setCallBack(new LightListAdaptor.CallBack() {
            @Override
            public <T> void convert(LightListAdaptor.ViewHolder holder, T bean, int position) {
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
        lightListAdaptor.notifyDataSetChanged();
    }
//TODO SensorActivity 添加房屋
    private void clickListenerInit()    {
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                String time = simpleDateFormat.format(date);
                if (flag == 0)   //flag为0就新建Condition
                {
                    temp = LitePal.findLast(Temp.class);
                    mission = new Mission();
                    mission.setTime(time);
                    mission.setTemp(temp);
                    mission.save();
                } else {
                    List<Mission> missionList = LitePal.where("time = ?", timeIn).find(Mission.class);
                    mission = missionList.get(0);
                }
                if(positionList.size()==0)
                    Toast.makeText(Set_lights.this,"请添加电器！",Toast.LENGTH_SHORT).show();
            else
                    if (!positionList.isEmpty()) {
                        for (int i = 0; i < positionList.size(); i++) {
                            int n = positionList.get(i);
                            String target_long_address = lightList.get(n).getTarget_long_address();
                            String target_short_address = lightList.get(n).getTarget_short_address();
                            S_Device s_device = new S_Device();
                            s_device.setDevice_type("01");
                            if (breathe != -1)
                                s_device.setLight_model("3");
                            if(light1==1)
                                s_device.getLightList().add(1);
                            if(light2==1)
                                s_device.getLightList().add(2);
                            if(light3==1)
                                s_device.getLightList().add(3);
                            if(light4==1)
                                s_device.getLightList().add(4);
                            if(all_lights==1){
                                s_device.getLightList().add(1);
                                s_device.getLightList().add(2);
                                s_device.getLightList().add(3);
                                s_device.getLightList().add(4);
                            }
                            s_device.setTarget_long_address(target_long_address);
                            s_device.setTarget_short_address(target_short_address);
                            s_device.setCategory("1");
                            s_device.setTemp(temp);
                            if(bright==0){
                                s_device.setToDefault("brightness");
                            }else
                                s_device.setBrightness(bright);
                            if (flag == 0) {
                                s_device.setMission(mission);
                                mission.getS_deviceList().add(s_device);
                                s_device.save();
                                mission.setJudge(3);
                                mission.save();
                            } else{
                                s_device.updateAll("target_long_address = ? and mission_id = ?", target_long_address,mission.getId()+"");
                            }

                        }
                        finish();
                        //不为0就通过时间搜索
                    }
            }
        });


    brightness.setOnCursorChangeListener(new StepSeekBar.OnCursorChangeListener() {

            @Override
            public void onCursorChanged(int location, String textMark) {
                if(breathe==1){

                    AlertDialog.Builder dialog = new AlertDialog.Builder (Set_lights.
                            this);
                    dialog.setTitle("提醒：");//对话框标题
                    dialog.setMessage("您已经选择呼吸灯模式！");//对话框显示的信息
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("确认", new DialogInterface.
                            OnClickListener() {//对话框中的按钮 OK
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialog.show();//显示对话框
//                    Snackbar.make(linearLayout,"您已经选择呼吸灯模式!",Snackbar.LENGTH_LONG).show();
//                    Toast.makeText(Set_lights.this,"已选择晚睡模式！",Toast.LENGTH_SHORT).show();
                }
            else
                 bright=location;
            //只是保存信息，不发送
            }
        });
        set_all_lights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(all_lights==-1){
                    set_all_lights.setBackgroundResource(R.drawable.smalllight);
                    set_light_1.setBackgroundResource(R.drawable.kuang);
                    set_light_2.setBackgroundResource(R.drawable.kuang);
                    set_light_3.setBackgroundResource(R.drawable.kuang);
                    set_light_4.setBackgroundResource(R.drawable.kuang);
                    all_lights=1;
                    light1=-1;
                    light2=-1;
                    light3=-1;
                    light4=-1;
                }
                else {
                    all_lights=-1;
                    set_all_lights.setBackgroundResource(R.drawable.kuang);
                }

            }
        });
        set_light_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(light1==-1){
                    set_light_1.setBackgroundResource(R.drawable.smalllight);
                    set_all_lights.setBackgroundResource(R.drawable.kuang);
                    all_lights=-1;
                    light1=1;
                }
                else
                {
                    light1=-1;
                    set_light_1.setBackgroundResource(R.drawable.kuang);
                }

            }
        });
        set_light_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(light2==-1){
                    set_light_2.setBackgroundResource(R.drawable.smalllight);
                    set_all_lights.setBackgroundResource(R.drawable.kuang);
                    all_lights=-1;
                    light2=1;
                }
                else
                {
                    light2=-1;
                    set_light_2.setBackgroundResource(R.drawable.kuang);
                }
            }
        });
        set_light_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(light3==-1){
                    set_light_3.setBackgroundResource(R.drawable.smalllight);
                    set_all_lights.setBackgroundResource(R.drawable.kuang);
                    all_lights=-1;
                    light3=1;
                }
                else
                {
                    light3=-1;
                    set_light_3.setBackgroundResource(R.drawable.kuang);
                }
            }
        });
        set_light_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(light4==-1){
                    set_light_4.setBackgroundResource(R.drawable.smalllight);
                    set_all_lights.setBackgroundResource(R.drawable.kuang);
                    all_lights=-1;
                    light4=1;
                }
                else
                {
                    light4=-1;
                    set_light_4.setBackgroundResource(R.drawable.kuang);
                }
            }
        });
        light_breathe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(breathe==-1)
                {
                    breathe=1;
//                    normal=-1;
//                    sleep=-1;
//                    other=-1;
//                    light_sleep.setOpened(false);//应该是让
//                    light_normal.setOpened(false);
//                    light_else.setOpened(false);
                    brightness.setEnabled(true);
                    brightness.setSelected(true);
                    brightness.setFocusable(true);
                }
                else
                {
                    breathe=-1;
                    brightness.setEnabled(false);
                    brightness.setSelected(false);
                    brightness.setFocusable(false);

                }

            }
        });
        //        light_normal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            if(normal==-1)
//            {
//                normal=1;
//                sleep=-1;
//                breathe=-1;
//                other=-1;
//                light_sleep.setOpened(false);//应该是让
//                light_breathe.setOpened(false);
//                light_else.setOpened(false);
//            }
//
//            else
//                normal=-1;
//            }
//        });
//        light_sleep.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(sleep==-1)
//                {
//                    normal=-1;
//                    breathe=-1;
//                    other=-1;
//                    sleep=1;
//                    light_breathe.setOpened(false);//应该是让
//                    light_else.setOpened(false);
//                    light_normal.setOpened(false);
//                }
//
//                else
//                    sleep=-1;
//            }
//        });
//        light_else.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(other==-1)
//                {
//                    normal=-1;
//                    sleep=-1;
//                    breathe=-1;
//                    other=1;
//                    light_breathe.setOpened(false);//应该是让
//                    light_sleep.setOpened(false);
//                    light_normal.setOpened(false);
//                }
//
//                else
//                    other=-1;
//            }
//        });



    }
}