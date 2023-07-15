package com.example.smarthome.Scene;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.smarthome.Activity.BottomSmartHome;
import com.example.smarthome.Activity.FirstActivity;
import com.example.smarthome.Adapter.ConAndMissAdaptor;
import com.example.smarthome.Adapter.MissionAdaptor;
import com.example.smarthome.Adapter.SceneAdaptor;
import com.example.smarthome.Database.AddModel;
import com.example.smarthome.Database.Device;
import com.example.smarthome.Database.Scene.C_Time;
import com.example.smarthome.Database.Scene.Condition;
import com.example.smarthome.Database.Scene.Mission;
import com.example.smarthome.Database.Scene.S_Device;
import com.example.smarthome.Database.Scene.Scene;
import com.example.smarthome.Database.Scene.Temp;
import com.example.smarthome.Database.Sensor;
import com.example.smarthome.MQTT.ClientMQTT;
import com.example.smarthome.Page_Samrt.Tesk;
import com.example.smarthome.R;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONObject;
import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * @description 场景活动
 */
public class More extends AppCompatActivity {
    public static final String CONTROLLER_LONG_ADDRESS="70E46125004B1200";
    RelativeLayout select_condition,select_tesk;
    Button create,delete_scene;
    Toolbar back;
    EditText model_name;
    String name_ml;
    private String  id;
    private List<C_Time> c_timeList=new ArrayList<>();
    private  List<Scene> sceneList=new ArrayList<>();
    private List<Map<String,String>> conditionList=new ArrayList<>();
    private List<Map<String,String>> missionList=new ArrayList<>();
    private List<Condition> mConditionList=new ArrayList<>();
    private List<Mission> mMissionList=new ArrayList<>();
    private JSONObject registerInfo;//注册返回信息
    private RecyclerView recy_condition,recy_mission;
    ImageView add_condition,add_mission;
    private ClientMQTT clientMQTT;
    private String hexTime;
    private String time;
    private String validData;
    private String innerData;//内嵌有效数据
    private String innerDataLength;//内嵌有效数据长度
    private String  validDataLength;//有效数据长度
    private int a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        //
        clientMQTT=new ClientMQTT("light");
        try {
            clientMQTT.Mqtt_innit();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        clientMQTT.startReconnect(More.this);
        Intent intent=getIntent();
        a=intent.getIntExtra(Scene.ID,-1);
        init();
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /*
        1.进入后先遍历删除所有Temp数据，之后再创建一个新的temp来储存
        findFirst来找到当前temp
        2.智能设备作为条件可以先弄灯的，空调那些可以先不显示
        灯的就开关嘛
        3.编写适配器，暂时只显示名称图标和开关
        4.还有电器的自定义命名
         */
        //TODO 好像由于没加判断的原因导致，同一个场景能重复的添加相同的设备执行条件
        //TODO recyclerView显示的smartActivity中的那三个，如果还有设备没有设置任务就显示
        List<Scene> sceneList=new ArrayList<>();
        id=String.valueOf(a);
        if(!id.equals("-1"))
            sceneList=LitePal.where("id = ?",id).find(Scene.class,true);
        if(sceneList.isEmpty())
            time=null;
        else{
            time=sceneList.get(0).getTime();
            model_name.setText(sceneList.get(0).getName());
            Temp temp=LitePal.findLast(Temp.class);
        }
        /**
         * @description 数据转移 From scene to temp
         */
        if(!id.equals("-1")&&!id.equals("0")){
            Temp temp=LitePal.findLast(Temp.class);
            Scene scene=LitePal.where("id = ?",id).findFirst(Scene.class,true);
            List<Condition> conditionList=new ArrayList<>();
            conditionList=LitePal.where("scene_id = ?",id).find(Condition.class,true);
            String isClick;
            List<Condition> conditionList1=new ArrayList<>();//条件
            List<Mission> missionList1=new ArrayList<>();//任务
            isClick=scene.getIsClick();
            conditionList1=LitePal.where("scene_id = ?",id).find(Condition.class,true);
            missionList1=LitePal.where("scene_id = ?",id).find(Mission.class,true);
//

            for(int i=0;i<conditionList1.size();i++){
                Condition condition=new Condition();
                condition=conditionList1.get(i);
                Condition condition1=new Condition();
                TransferDataFromCondition(condition,condition1);
                condition1.setScene(null);
                condition1.setTemp(temp);
                temp.getConditionList().add(condition);
                condition1.save();
                temp.save();
            }

            for(int j=0;j<missionList1.size();j++){
                Mission mission = new Mission();
                mission=missionList1.get(j);
                Mission mission1=new Mission();
                TransferDataFromMission(mission,mission1);
                if(mission1.getJudge()!=0){
                    List<S_Device> s_deviceList2=LitePal.where("scene_id = ?",id).find(S_Device.class);
                    for(int i=0;i<s_deviceList2.size();i++){
                        S_Device s_device=new S_Device();
                        TransferDataFromS_Device(s_deviceList2.get(i),s_device);
                        s_device.setMission(mission1);
                        s_device.setTemp(temp);
                        mission1.getS_deviceList().add(s_device);
                        s_device.save();
                    }
                }
                mission1.setScene(null);
                temp.getMissionList().add(mission1);
                mission1.save();
            }
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            Date date=new Date(System.currentTimeMillis());
            String time1=simpleDateFormat.format(date);
            temp.setIsClick(isClick);
            temp.setTime(time1);
            temp.save();
        }

        initRecyclerViewCon();
        initRecyclerViewMiss();
    }

    private void init() {
        select_condition =findViewById(R.id.select_condition);
        select_tesk=findViewById(R.id.select_tesk);
        create=findViewById(R.id.create);
        model_name=findViewById(R.id.model_name);
        recy_mission=findViewById(R.id.recy_mission);
        recy_condition=findViewById(R.id.recy_condition);
        delete_scene=findViewById(R.id.delete_scene);
        add_condition=findViewById(R.id.add_condition);
        add_mission=findViewById(R.id.add_mission);
        if(a==-1){
            delete_scene.setVisibility(View.INVISIBLE);
            delete_scene.setClickable(false);
        }
        else {
            delete_scene.setVisibility(View.VISIBLE);
            delete_scene.setClickable(true);
        }
        delete_scene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.deleteAll(Scene.class,"id = ?",a+"");
                finish();
            }
        });
        add_mission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(More.this, MissionActivity.class);
                startActivity(intent);
            }
        });

        add_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(More.this, ConditionActivity.class);
                startActivity(intent1);
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.getDatabase();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                Date date=new Date(System.currentTimeMillis());
                String time1=simpleDateFormat.format(date);
                name_ml=model_name.getText().toString();    //获取输入框值
                //判断输入框中是否有值
                if(TextUtils.isEmpty(name_ml)){
                    Toast.makeText(More.this,"请输入自定义场景名称",Toast.LENGTH_SHORT).show();
                    return;
                }
                Temp temp=LitePal.findFirst(Temp.class,true);

                if(time==null){
                    temp.setTime(time1);
                    /**
                     * @description 数据转移 From temp to scene
                     */
                    Scene scene=new Scene();
                    scene.setTime(time1);
                    scene.setName(name_ml);
                    scene.setIsClick(temp.getIsClick());
                    List<S_Device> s_deviceList=new ArrayList<>();
                    List<Condition> conditionList=new ArrayList<>();
                    conditionList=LitePal.where("temp_id = ?",String.valueOf(temp.getId())).find(Condition.class,true);
                    if(!conditionList.isEmpty())
                        c_timeList=LitePal.where("condition_id = ?",String.valueOf(conditionList.get(0).getId())).find(C_Time.class,true);
                    List<C_Time> c_timeList=new ArrayList<>();//多个时间点
                    String isClick;
                    List<C_Time> c_timeList1=new ArrayList<>();//多个时间点
                    List<Condition> conditionList1=new ArrayList<>();//条件
                    List<Mission> missionList1=new ArrayList<>();//任务
                    List<S_Device> s_deviceList1=new ArrayList<>();
                    List<Sensor> sensorList=new ArrayList<>();
                    isClick=temp.getIsClick();

                    c_timeList1=LitePal.where("temp_id = ?",temp.getId()+"").find(C_Time.class,true);
                    conditionList1=LitePal.where("temp_id = ?",temp.getId()+"").find(Condition.class,true);
                    s_deviceList1=LitePal.where("temp_id = ?",temp.getId()+"").find(S_Device.class,true);
                    missionList1=LitePal.where("temp_id = ?",temp.getId()+"").find(Mission.class,true);
                    sensorList=LitePal.where("temp_id = ?",temp.getId()+"").find(Sensor.class,true);
                    c_timeList=c_timeList1;

                    for(int i=0;i<conditionList1.size();i++){
                        Condition condition=new Condition();
                        condition=conditionList1.get(i);
                        if(condition.getJudge()==3){
                            C_Time c_time=new C_Time();
                            C_Time c_time1=new C_Time();
                            c_time1=conditionList.get(i).getC_time();
                            c_time=c_time1;
                            c_time.setCondition(condition);
                            condition.setC_time(c_time);
                            c_time.setScene(scene);
                            c_time.setTemp(null);
                            c_time.setCondition(condition);
                            c_time.save();
                        }else if(condition.getJudge()==5){
                            Sensor sensor=new Sensor();
                            sensor=conditionList1.get(i).getSensor();
                            sensor.setTemp(null);
                            sensor.setScene(scene);
                            sensor.setCondition(condition);
                            sensor.save();
                        }
                        condition.setTemp(null);
                        condition.setScene(scene);
                        scene.getConditionList().add(condition);
                        condition.save();
                        scene.save();
                    }
                    for(int j=0;j<missionList1.size();j++){
                        Mission mission1 = new Mission();
                        mission1=missionList1.get(j);
                        if(mission1.getJudge()!=0){
                            List<S_Device> s_deviceList2=LitePal.where("mission_id = ?",missionList1.get(j).getId()+"").find(S_Device.class);
                            for(int i=0;i<s_deviceList2.size();i++){
                                S_Device s_device=new S_Device();
                                s_device=s_deviceList2.get(i);
                                s_device.setMission(mission1);
                                s_device.setScene(scene);
                                s_device.setTemp(null);
                                mission1.getS_deviceList().add(s_device);
                                s_device.save();
                            }
                        }
                        scene.getMissionList().add(mission1);
                        mission1.setScene(scene);
                        mission1.setTemp(null);
                        mission1.save();
                    }

                    scene.setIsClick(isClick);
                    scene.setTime(time1);
                    scene.setIsOpen(0);
                    scene.save();

                    //创建场景
                    String input = name_ml; // 中文输入
                     //将时间转化为16进制
                    if(!c_timeList.isEmpty()){
                        String transferTime=c_timeList.get(0).getTime();
                        hexTime=TimePointToHEX(transferTime);
                    }
                     //将字符转化为16进制
                    String name=NameToHEX(input);
                    /**
                     * @description
                     * 指令
                     */
                    //场景名长度
                    validData=getValidData("01",name,innerData);//00是内嵌数据长度
                    //创建场景
                    validDataLength=getValidDataLength(validData);
                   clientMQTT.publishMessagePlus(null,"0x0000","0xFE",validData,validDataLength+"");
                    if(hexTime!=null){
                        innerData=getInnerDataForTime(More.CONTROLLER_LONG_ADDRESS,"00",hexTime);
                        validData=getValidData("03",name,innerData);
                        //发送时间点条件
                        validDataLength=getValidDataLength(validData);
                        clientMQTT.publishMessagePlus(null,"0x0000","0xFE",validData,validDataLength);
                    }
                    //发送设备执行任务
                    if(!s_deviceList1.isEmpty()){
                        for(S_Device s_device:s_deviceList1){
                            String innerValidData=null;//内嵌有效数据数据
                            String lightModel=s_device.getLight_model();
                            int brightness=-1;
                            brightness=s_device.getBrightness();
                            String target_short_address=s_device.getTarget_short_address();
                            if(brightness==-1){
                            }else {
                                for(int i=0;i<s_device.getLightList().size();i++){
                                    int light=s_device.getLightList().get(i);
                                    innerValidData="0"+light+"040"+brightness;
                                    innerData=getInnerDataForCommand(target_short_address,s_device.getDevice_type(),innerValidData);
                                    validData=getValidData("05",name,innerData);
                                    validDataLength=getValidDataLength(validData);
                                    clientMQTT.publishMessagePlus(null,"0x0000","0xFE",validData,validDataLength);
                                }
                            }
                            if(s_device.getLight_model()!=null)
                                if(s_device.getLight_model().equals("3")){//呼吸灯指令
                                innerValidData="00"+"06";
                                innerData=getInnerDataForCommand(target_short_address,s_device.getDevice_type(),innerValidData);
                                validData=getValidData("05",name,innerData);
                                validDataLength=getValidDataLength(validData);
                                clientMQTT.publishMessagePlus(null,"0x0000","0xFE",validData,validDataLength);
                            }
                        }
                    }
                    if(!sensorList.isEmpty()){
                        for(int i=0;i<sensorList.size();i++){
                            Sensor sensor=sensorList.get(i);
                            switch (sensor.getDevice_type()){
                                case "05":
                                    innerData=sensor.getTarget_long_address()+"0"+sensor.getUpOrDown()+sensor.getI_temp();
                                break;
                                case "06":
                                    innerData=sensor.getTarget_long_address()+"0"+sensor.getUpOrDown()+sensor.getI_wetness();
                            }
                            validData=getValidData("03",name,innerData);
                            //发送时间点条件
                            validDataLength=getValidDataLength(validData);
                            clientMQTT.publishMessagePlus(null,"0x0000","0xFE",validData,validDataLength);
                        }
                    }


                }else if(time!=null){
                    //这边的操作是更新scene中的数据

                    /**
                     * @description 数据转移
                     */
                    Scene scene1=LitePal.where("id = ?",id).findFirst(Scene.class);

                        String scene_id=String.valueOf(scene1.getId());
                        LitePal.deleteAll(C_Time.class,"scene_id=?",scene_id);
                        LitePal.deleteAll(S_Device.class,"scene_id=?",scene_id);
                        LitePal.deleteAll(Mission.class,"scene_id=?",scene_id);
                        LitePal.deleteAll(Condition.class,"scene_id=?",scene_id);
                        scene1.delete();

                    Scene scene=new Scene();
                    scene.setId(Integer.valueOf(id));//TODO changed
                    scene.setTime(time1);
                    scene.setName(name_ml);
                    scene.setIsClick(temp.getIsClick());
                    List<S_Device> s_deviceList=new ArrayList<>();
                    List<Condition> conditionList=new ArrayList<>();
                    conditionList=LitePal.where("temp_id = ?",String.valueOf(temp.getId())).find(Condition.class,true);
                    if(!conditionList.isEmpty())
                        c_timeList=LitePal.where("condition_id = ?",String.valueOf(conditionList.get(0).getId())).find(C_Time.class,true);
                    List<C_Time> c_timeList=new ArrayList<>();//多个时间点
                    String isClick;
                    List<C_Time> c_timeList1=new ArrayList<>();//多个时间点
                    List<Condition> conditionList1=new ArrayList<>();//条件
                    List<Mission> missionList1=new ArrayList<>();//任务
                    List<S_Device> s_deviceList1=new ArrayList<>();
                    isClick=temp.getIsClick();
                    List<Sensor> sensorList=new ArrayList<>();
                    sensorList=LitePal.where("temp_id = ?",temp.getId()+"").find(Sensor.class,true);
                    conditionList1=LitePal.where("temp_id = ?",temp.getId()+"").find(Condition.class,true);
                    missionList1=LitePal.where("temp_id = ?",temp.getId()+"").find(Mission.class,true);


                    for(int i=0;i<conditionList1.size();i++){
                        Condition condition=new Condition();
                        condition=conditionList1.get(i);
                        if(condition.getJudge()==3){
                            C_Time c_time=new C_Time();
                            C_Time c_time1=new C_Time();
                            c_time1=conditionList.get(i).getC_time();
                            c_time=c_time1;
                            c_time.setCondition(condition);
                            condition.setC_time(c_time);
                            c_time.setScene(scene);
                            c_time.setTemp(null);
                            c_time.setCondition(condition);
                            c_time.save();
                        }else if(condition.getJudge()==5){
                            Sensor sensor=new Sensor();
                            sensor=conditionList1.get(i).getSensor();
                            sensor.setTemp(null);
                            sensor.setScene(scene);
                            sensor.setCondition(condition);
                            sensor.save();
                        }
                        condition.setTemp(null);
                        condition.setScene(scene);
                        scene.getConditionList().add(condition);
                        condition.save();
                        scene.save();
                    }

                    for(int j=0;j<missionList1.size();j++){
                        Mission mission1 = new Mission();
                        mission1=missionList1.get(j);
                        if(mission1.getJudge()!=0){
                            List<S_Device> s_deviceList2=LitePal.where("mission_id = ?",missionList1.get(j).getId()+"").find(S_Device.class);
                            for(int i=0;i<s_deviceList2.size();i++){
                                S_Device s_device=new S_Device();
                                s_device=s_deviceList2.get(i);
                                s_device.setMission(mission1);
                                s_device.setScene(scene);
                                s_device.setTemp(null);
                                mission1.getS_deviceList().add(s_device);
                                s_device.save();
                            }
                        }
                        scene.getMissionList().add(mission1);
                        mission1.setScene(scene);
                        mission1.setTemp(null);
                        mission1.save();
                    }
                    scene.setIsClick(isClick);
                    scene.setTime(time1);
                    scene.setIsOpen(0);
                    scene.save();
                    /**
                     * @decription 删除场景
                     *
                     */
                    String input=scene.getName();
                    String name1=NameToHEX(input);
                    //场景名长度
                    validData=getValidData("00",name1,innerData);//00是内嵌数据长度
                    //创建场景
                    validDataLength=getValidDataLength(validData);
                    clientMQTT.publishMessagePlus(null,"0x0000","0xFE",validData,validDataLength+"");

                    /**
                     * @description 创建场景
                     */
                    String input1 = name_ml; // 中文输入
                    //将时间转化为16进制
                    if(!c_timeList.isEmpty()){
                        String transferTime=c_timeList.get(0).getTime();
                        hexTime=TimePointToHEX(transferTime);
                    }
                    //将字符转化为16进制
                    String name=NameToHEX(input1);
                    /**
                     * @description
                     * 指令
                     */
                    //场景名长度
                    validData=getValidData("01",name,innerData);//00是内嵌数据长度
                    //创建场景
                    validDataLength=getValidDataLength(validData);
                    clientMQTT.publishMessagePlus(null,"0x0000","0xFE",validData,validDataLength+"");
                    if(hexTime!=null){
                        innerData=getInnerDataForTime(More.CONTROLLER_LONG_ADDRESS,"00",hexTime);
                        validData=getValidData("03",name,innerData);
                        //发送时间点条件
                        validDataLength=getValidDataLength(validData);
                        clientMQTT.publishMessagePlus(null,"0x0000","0xFE",validData,validDataLength);
                    }
                    //发送设备执行任务
                    if(!s_deviceList1.isEmpty()){
                        for(S_Device s_device:s_deviceList1){
                            String innerValidData=null;//内嵌有效数据数据
                            String lightModel=s_device.getLight_model();
                            int brightness=-1;
                            brightness=s_device.getBrightness();
                            String target_short_address=s_device.getTarget_short_address();
                            if(brightness==-1){
                            }else {
                                for(int i=0;i<s_device.getLightList().size();i++){
                                    int light=s_device.getLightList().get(i);
                                    innerValidData="0"+light+"040"+brightness;
                                    innerData=getInnerDataForCommand(target_short_address,s_device.getDevice_type(),innerValidData);
                                    validData=getValidData("05",name,innerData);
                                    validDataLength=getValidDataLength(validData);
                                    clientMQTT.publishMessagePlus(null,"0x0000","0xFE",validData,validDataLength);
                                }
                            }
                            if(s_device.getLight_model()!=null)
                                if(s_device.getLight_model().equals("3")){//呼吸灯指令
                                    innerValidData="00"+"06";
                                    innerData=getInnerDataForCommand(target_short_address,s_device.getDevice_type(),innerValidData);
                                    validData=getValidData("05",name,innerData);
                                    validDataLength=getValidDataLength(validData);
                                    clientMQTT.publishMessagePlus(null,"0x0000","0xFE",validData,validDataLength);
                                }
                        }
                    }
                    if(!sensorList.isEmpty()){
                        for(int i=0;i<sensorList.size();i++){
                            Sensor sensor=sensorList.get(i);
                            switch (sensor.getDevice_type()){
                                case "05":
                                    innerData=sensor.getTarget_long_address()+"0"+sensor.getUpOrDown()+sensor.getI_temp();
                                    break;
                                case "06":
                                    innerData=sensor.getTarget_long_address()+"0"+sensor.getUpOrDown()+sensor.getI_wetness();
                            }
                            validData=getValidData("03",name,innerData);
                            //发送时间点条件
                            validDataLength=getValidDataLength(validData);
                            clientMQTT.publishMessagePlus(null,"0x0000","0xFE",validData,validDataLength);
                        }
                    }
                    /**
                     * @description 时间指令
                     */


                }
                finish();
            }

        });
    }

    /**
     * @description 生成有效数据
     * @param operation 场景操作选择位
     * @param name      场景名(16进制)
     * @param innerData 内嵌有效数据数据
     * @return validData 有效数据内容
     */
    private String getValidData(String operation,String name,String innerData){
        String validData;
        String nameLength;
        String innerDataLength;
        nameLength=Integer.toHexString(name.length()/2);
        if(nameLength.length()==1)
            nameLength="0"+nameLength;
        if(innerData==null)
            innerDataLength="00";
        else
            innerDataLength=Integer.toHexString(innerData.length()/2);
        if(innerDataLength.length()==1)
            innerDataLength="0"+innerDataLength;
        if(innerData!=null)
            return validData="0x"+operation+nameLength+name+innerDataLength+innerData;
        else
            return validData="0x"+operation+nameLength+name+innerDataLength;

    }

    /**
     * @description 获得有效数据长度,有"0x"的那种偶
     * @param validData 有效数据
     * @return 有效数据长度
     */
    private String getValidDataLength(String validData){
        String validDataLength=Integer.toHexString(validData.length()/2-1);
        if(validDataLength.length()==1)
            validDataLength="0"+validDataLength;
       return "0x"+validDataLength;
    }

    /**
     * @description 获得内嵌有效数据数据
     * @return 内嵌有效数据数据
     */
    private String getInnerDataForSensor(){
        String innerData="";
        return innerData;
    }

    /**
     *
     * @param controller_long_address
     * @param judge 时间点： 00 时间段： 01
     * @param time 16进制的时间
     * @return 时间点的内嵌有效数据
     */
    private String getInnerDataForTime(String controller_long_address,String judge,String time){
        String innerData=controller_long_address+judge+time;
        return innerData;
    }
    private String getInnerDataForTime(String controller_long_address,String judge,String timeStart,String timeEnd){
        String innerData="";

        return innerData;
    }

    /**
     *
     * @param target_short_address
     * @param device_type
     * @param innerValidData
     * @return 内嵌有效数据数据
     */
    private String getInnerDataForCommand(String target_short_address,String device_type,String innerValidData){
        String innerValidDataLength=Integer.toHexString(innerValidData.length()/2);
        if(innerValidDataLength.length()==1)
            innerValidDataLength="0"+innerValidDataLength;
        String innerData=target_short_address+device_type+innerValidDataLength+innerValidData;
        return innerData;
    }
    /**
     * @description
     * 将时间转化为16进制
     */
    private String TimePointToHEX(String time){

        String[] parts = time.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        String hexString1 = Integer.toHexString(hour);
        String hexString2 = Integer.toHexString(minute);
        if (hexString1.length() == 1) {
            hexString1 = "0" + hexString1;
        }
        if (hexString2.length() == 1) {
            hexString2 = "0" + hexString2;
        }
        String hexString=hexString1+hexString2;
        String HEXTime=hexString.toUpperCase();
        return HEXTime;
    }
    /**
     * @description
     * 将字符转化为16进制
     */
    private String NameToHEX(String input){
        // 将中文字符串转换为字节数组
        byte[] bytes = input.getBytes();
        // 将字节数组转换为16进制字符串
        StringBuilder hex = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            hex.append(String.format("%02X", b));
        }
        // 输出16进制字符串
        String name=hex.toString();
        return name;
    }
    private void TransferDataFromSceneToTemp(Temp temp,Scene scene){
        List<C_Time> c_timeList=new ArrayList<>();//多个时间点
        List<Condition> conditionList=new ArrayList<>();//条件
        List<Mission> missionList=new ArrayList<>();//任务
        List<S_Device> s_deviceList=new ArrayList<>();
        String isClick;
        List<C_Time> c_timeList1=new ArrayList<>();//多个时间点
        List<Condition> conditionList1=new ArrayList<>();//条件
        List<Mission> missionList1=new ArrayList<>();//任务
        List<S_Device> s_deviceList1=new ArrayList<>();

        isClick=scene.getIsClick();

        c_timeList1=LitePal.where("scene_id = ?",scene.getId()+"").find(C_Time.class,true);
        conditionList1=LitePal.where("scene_id = ?",scene.getId()+"").find(Condition.class,true);
        s_deviceList1=LitePal.where("scene_id = ?",scene.getId()+"").find(S_Device.class,true);
        missionList1=LitePal.where("scene_id = ?",scene.getId()+"").find(Mission.class,true);
        c_timeList=c_timeList1;
        conditionList=conditionList1;
        s_deviceList=s_deviceList1;
        missionList=missionList1;
        for(int i=0;i<conditionList.size();i++){
            Condition condition=new Condition();
            condition=conditionList.get(i);
            temp.getConditionList().add(condition);
            condition.save();
        }
        temp.setMissionList(missionList);

        temp.setIsClick(isClick);
        temp.setS_deviceList(s_deviceList);
        temp.setC_timeList(c_timeList);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date=new Date(System.currentTimeMillis());
        String time1=simpleDateFormat.format(date);
        temp.setTime(time1);
        temp.save();
        int a=0;
    }
    private void TransferDataFromTempToScene(Scene scene,Temp temp,String name){
        List<C_Time> c_timeList=new ArrayList<>();//多个时间点
        List<Condition> conditionList=new ArrayList<>();//条件
        List<Mission> missionList=new ArrayList<>();//任务
        List<S_Device> s_deviceList=new ArrayList<>();
        String isClick;
        List<C_Time> c_timeList1=new ArrayList<>();//多个时间点
        List<Condition> conditionList1=new ArrayList<>();//条件
        List<Mission> missionList1=new ArrayList<>();//任务
        List<S_Device> s_deviceList1=new ArrayList<>();

        isClick=temp.getIsClick();

        c_timeList1=LitePal.where("temp_id = ?",temp.getId()+"").find(C_Time.class,true);
        conditionList1=LitePal.where("temp_id = ?",temp.getId()+"").find(Condition.class,true);
        s_deviceList1=LitePal.where("temp_id = ?",temp.getId()+"").find(S_Device.class,true);
        missionList1=LitePal.where("temp_id = ?",temp.getId()+"").find(Mission.class,true);
        c_timeList=c_timeList1;
        conditionList=conditionList1;
        s_deviceList=s_deviceList1;
        missionList=missionList1;

        for(int i=0;i<conditionList.size();i++){
            Condition condition=new Condition();
            condition=conditionList.get(i);
            if(condition.getJudge()==3){
                C_Time c_time=new C_Time();
                c_time=condition.getC_time();
                c_time.setCondition(condition);
                condition.setC_time(c_time);
                c_time.setScene(scene);
                c_time.setCondition(condition);
                c_time.save();
            }
            condition.setScene(scene);
            scene.getConditionList().add(condition);
            condition.save();
            scene.save();
        }
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date=new Date(System.currentTimeMillis());
        String time1=simpleDateFormat.format(date);
        scene.setTime(time1);
        scene.setIsOpen(0);
        scene.save();
    }
    class MyRunnable implements Runnable{


        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clientMQTT.publishMessagePlus(null,"0x0000","0xFE",validData,validDataLength);
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
    @Override
    protected void onStart() {
        super.onStart();
        initRecyclerViewCon();
        initRecyclerViewMiss();
    }
    private void initRecyclerViewCon(){
            mConditionList.clear();
            Temp temp=LitePal.findLast(Temp.class);
            String temp_id=temp.getId()+"";
            mConditionList=LitePal.where("temp_id = ? and scene_id is null",temp_id).find(Condition.class);
            LinearLayoutManager layoutManager=new LinearLayoutManager(More.this);
            recy_condition.setLayoutManager(layoutManager);
            ConAndMissAdaptor conAndMissAdaptor=new ConAndMissAdaptor(mConditionList);
            conAndMissAdaptor.setContext(More.this);
            recy_condition.setAdapter(conAndMissAdaptor);
            conAndMissAdaptor.notifyDataSetChanged();
    }
    private void initRecyclerViewMiss(){

            mMissionList.clear();
            Temp temp=LitePal.findLast(Temp.class);
            String temp_id=temp.getId()+"";
            mMissionList=LitePal.where("temp_id = ? and scene_id is null",temp_id).find(Mission.class,true);
            LinearLayoutManager layoutManager=new LinearLayoutManager(More.this);
            recy_mission.setLayoutManager(layoutManager);
            MissionAdaptor missionAdaptor=new MissionAdaptor(mMissionList);
            missionAdaptor.setContext(More.this);
            recy_mission.setAdapter(missionAdaptor);
            missionAdaptor.notifyDataSetChanged();
    }
    private void TransferDataFromMission(Mission mission,Mission mission1){

        mission1.setJudge(mission.getJudge());
        mission1.setTime(mission.getTime());
        mission1.save();
    }
    private void  TransferDataFromCondition(Condition condition, Condition condition1){
        condition1.setTime(condition.getTime());
        condition1.setJudge(condition.getJudge());
        condition1.setS_deviceList(condition.getS_deviceList());
        condition1.setIsClick(condition.getIsClick());
        condition1.setSensor(condition.getSensor());
        condition1.setC_time(condition.getC_time());
        condition1.save();
    }
    private void TransferDataFromS_Device(S_Device s_device,  S_Device s_device1){
         String target_long_address=s_device.getTarget_long_address();
         String target_short_address=s_device.getTarget_short_address();
         String device_type=s_device.getDevice_type();
         String name=s_device.getName();//设备的名称
         String tem_over= s_device.getTem_over();//温度高于
         String tem_below= s_device.getTem_below();//温度低于
         String isSmoke= s_device.getIsSmoke();//是否烟雾报警
         List<Integer> lightList=new ArrayList<>();
         lightList=s_device.getLightList();
         String open_light= s_device.getOpen_light();
         String close_light= s_device.getClose_light();
         String light_model= s_device.getLight_model();//1:normal   2:sleep 3:breathe 4:自定义
         int brightness= s_device.getBrightness();
         String air_open= s_device.getAir_open();
         String air_close= s_device.getAir_close();
         String air_model= s_device.getAir_model();//1,2,3制热，制冷，通风
         String wind= s_device.getWind();//1,2,3小，中，大
         String curtain_open= s_device.getCurtain_open();
         String curtain_close= s_device.getCurtain_close();
         String curtain_deep= s_device.getCurtain_deep();
        s_device1.setDevice_type(device_type);
        s_device1.setTarget_long_address(target_long_address);
        s_device1.setTarget_short_address(target_short_address);
        s_device1.setName(name);
        s_device1.setTem_over(tem_over);
        s_device1.setTem_below(tem_below);
        s_device1.setIsSmoke(isSmoke);
        s_device1.setLightList(lightList);
        s_device1.setOpen_light(open_light);
        s_device1.setClose_light(close_light);
        s_device1.setLight_model(light_model);
        s_device1.setBrightness(brightness);
        s_device1.setAir_open(air_open);
        s_device1.setAir_close(air_close);
        s_device1.setAir_model(air_model);
        s_device1.setWind(wind);
        s_device1.setCurtain_open(curtain_open);
        s_device1.setCurtain_close(curtain_close);
        s_device1.setCurtain_deep(curtain_deep);
        s_device1.save();
    }
    private void TransferDataFromC_Time(C_Time c_time,C_Time c_time1){
        c_time1.setTime_start(c_time.getTime_start());
        c_time1.setTime_end(c_time.getTime_end());
        c_time1.setTime(c_time.getTime());
        c_time1.save();
    }

}
