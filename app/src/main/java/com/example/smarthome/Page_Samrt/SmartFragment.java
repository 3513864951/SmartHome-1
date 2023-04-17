package com.example.smarthome.Page_Samrt;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adapter.AddModelAdapter2;
import com.example.smarthome.Adapter.AddSmartAdapter;
import com.example.smarthome.Adapter.ManageAdaptor;
import com.example.smarthome.Database.AddModel;
import com.example.smarthome.Database.Device;
import com.example.smarthome.Database.Scene.C_Time;
import com.example.smarthome.Database.Scene.Condition;
import com.example.smarthome.Database.Scene.Mission;
import com.example.smarthome.Database.Scene.S_Device;
import com.example.smarthome.Database.Scene.Scene;
import com.example.smarthome.Database.Scene.Temp;
import com.example.smarthome.R;
import com.example.smarthome.Scene.More;
import com.example.smarthome.Scene.SmartSensor;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmartFragment extends Fragment {
    private List<Map<String,String>> deviceList=new ArrayList<>();
    private List<Device> devicelist=new ArrayList<>();
    private List<Device> onlineDeviceList=new ArrayList<>();
    private List<Device> outlineDeviceList=new ArrayList<>();
    private List<Scene> sceneList=new ArrayList<>();
    private ImageView sensor;

    String name_m;
    private RecyclerView recy_scene,online_device,outline_device;
    AddSmartAdapter rvadapter;
    AddModelAdapter2 addModelAdapter2;
    private int i=0;
//    AddModel addModel=new AddModel(recy_scene);
    ImageView add;
    List<AddModel> list = new ArrayList<>();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.smart_fragment,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        recy_scene=getActivity().findViewById(R.id.recy_scene);
        sensor=getActivity().findViewById(R.id.add_sense_iv);
        online_device=getActivity().findViewById(R.id.add_smart);
       outline_device=getActivity().findViewById(R.id.outline_device);
        super.onActivityCreated(savedInstanceState);
        add=getActivity().findViewById(R.id.add_home);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getActivity(), More.class);
                List<Temp> tempList=new ArrayList<>();
                tempList=LitePal.findAll(Temp.class);
                if(!tempList.isEmpty())//如果暂存数据库不为空，就遍历清空Scene,device.....中与temp有关的数据
                {

                    for (int j = 0; j < tempList.size(); j++) {
                        String temp_id=String.valueOf(tempList.get(j).getId());
                        LitePal.deleteAll(C_Time.class,"temp_id=?",temp_id);
                        LitePal.deleteAll(S_Device.class,"temp_id=?",temp_id);
                        LitePal.deleteAll(Mission.class,"temp_id=?",temp_id);
                        LitePal.deleteAll(Condition.class,"temp_id=?",temp_id);
                        j++;
                    }
                    tempList.clear();
                }
                //最后记得删除t所有tempLitePal.deleteAll(Temp.class);
                LitePal.deleteAll(Temp.class);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                Date date=new Date(System.currentTimeMillis());
                String time=simpleDateFormat.format(date);
                Temp temp=new Temp();
                temp.setTime(time);
                temp.setIsClick("-1");
                temp.save();
                   startActivity(intent3);
            }
        });
        sensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), SmartSensor.class);
                getActivity().startActivity(intent);
            }
        });

        deleteData();
        initRecyclerViewOnline();
        initRecyclerViewOutline();
        recyclerView3();
//        initSceneRecyclerView();
    }

    private void recyclerView3() {
//        recy_scene.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        List<AddModel> all = LitePal.findAll(AddModel.class);
//        addModelAdapter2= new AddModelAdapter2(all);
//        recy_scene.setAdapter(addModelAdapter2);
//        addModelAdapter2.set0nItemClickListener(new AddModelAdapter2.OnItemClickListener() {
//            @Override
//            public void OnItemClick(View view, int position) {
//                Intent intent=new Intent(getActivity(), SetAllShow.class);
//                startActivity(intent);
//            }
//        });

    }

    //private void recyclerView2() {
//    recy_scene.setHasFixedSize(true);
//    recy_scene.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//    ArrayList<AddMedalHelper> addMedalHelpers = new ArrayList<>();
//    addMedalHelpers .add(new AddMedalHelper(R.drawable.leave_home, "离家模式"));
//    addMedalHelpers .add(new AddMedalHelper(R.drawable.back_home, "回家模式"));
//    addMedalHelpers .add(new AddMedalHelper(R.drawable.night, "夜间模式"));
//    addMedalHelpers .add(new AddMedalHelper(R.drawable.more, "更多模式"));
//    medalAdapter = new AddMedalAdapter(addMedalHelpers);
//    recy_scene.setAdapter(medalAdapter);
//    medalAdapter.setOnItemLongClickListener(new AddMedalAdapter.OnItemLongClickListener() {
//        @Override
//        public void onItemLongClick(View view, int position) {
//            switch (position) {
//                case 0:
//                    Intent intent0 = new Intent(getActivity(), GoOff.class);
//                    startActivity(intent0);
//                    break;
//                case 1:
//                    Intent intent1 = new Intent(getActivity(), GoHome.class);
//                    startActivity(intent1);
//                    break;
//                case 2:
//                    Intent intent2 = new Intent(getActivity(), Night.class);
//                    startActivity(intent2);
//                    break;
//            }
//        }
//    });
//    medalAdapter.setOnItemClickListener(new AddMedalAdapter.OnItemClickListener() {
//        @Override
//        public void OnItemClickListener(View view, int position) {
//            switch (position){
//                case 0:
//                    break;
//                case 1:
//                    break;
//                case 2:
//                    break;
//                case 3:
//                    Intent intent3 = new Intent(getActivity(), More.class);
//                    startActivity(intent3);
//                    break;
//            }
//        }
//    });
//
//
//}
    private void deleteData(){
        List<Device> deviceList= LitePal.findAll(Device.class);
        for(int i=0;i<deviceList.size();i++){
            if(Integer.valueOf(deviceList.get(i).getDevice_type(),16)>10||Integer.valueOf(deviceList.get(i).getDevice_type(),16)<0)
            {
                int id=deviceList.get(i).getId();
                LitePal.deleteAll(Device.class,"id = ?",id+"");
            }
        }
    }
    private void initRecyclerViewOnline(){
        onlineDeviceList=LitePal.where("flag= ? and network_flag = ?","1","01").find(Device.class);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        online_device.setLayoutManager(linearLayout);
        ManageAdaptor manageAdaptor = new ManageAdaptor(onlineDeviceList);
        manageAdaptor.InputFlag(1);
        online_device.setAdapter(manageAdaptor);
        manageAdaptor.notifyDataSetChanged();

    }

    private void initRecyclerViewOutline(){
        outlineDeviceList=LitePal.where("flag= ? and network_flag = ?","1","00").find(Device.class);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        outline_device.setLayoutManager(linearLayout);
        ManageAdaptor manageAdaptor = new ManageAdaptor(outlineDeviceList);
        manageAdaptor.InputFlag(1);
        outline_device.setAdapter(manageAdaptor);
        manageAdaptor.notifyDataSetChanged();
    }

//    private void initSceneRecyclerView(){
//        sceneList.clear();
//        sceneList=LitePal.findAll(Scene.class);
//        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
//        recy_scene.setLayoutManager(layoutManager);
//        SceneAdaptor sceneAdaptor=new SceneAdaptor(sceneList);
//        recy_scene.setAdapter(sceneAdaptor);
//        sceneAdaptor.notifyDataSetChanged();
//    }
    private void initContent()
    {
        deviceList.clear();

        devicelist= LitePal.order("device_type desc").where("flag= ? and network_flag = ?","1","1").find(Device.class);
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


