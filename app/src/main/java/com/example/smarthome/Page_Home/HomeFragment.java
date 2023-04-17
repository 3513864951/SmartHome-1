package com.example.smarthome.Page_Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smarthome.Database.Device;
import com.example.smarthome.Database.Scene.Temp;
import com.example.smarthome.Page_Huiju.ManageDevices;
import com.example.smarthome.R;
import com.example.smarthome.Scan.ScanActivity;
import com.example.smarthome.View.TestScene;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
//import com.example.smarthome.Scan.ScanActivity;

public class HomeFragment extends Fragment {
    TextView home_set_voice,home_scene,home_wifi,home_wangguan,home_set,enroll,home_test;
    private int i=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date=new Date(System.currentTimeMillis());
        String time=simpleDateFormat.format(date);
        Temp temp=new Temp();
        temp.setTime(time);
        temp.setIsClick("-1");
        temp.save();
        deleteData();
        return inflater.inflate(R.layout.home,container,false);
    }
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
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        home_set_voice=getActivity().findViewById(R.id.home_set_voice);
        home_scene=getActivity().findViewById(R.id.home_scene);
        home_wifi=getActivity().findViewById(R.id.home_wife);
        home_wangguan=getActivity().findViewById(R.id.home_wangguan);
        home_set=getActivity().findViewById(R.id.home_set);
        enroll=getActivity().findViewById(R.id.enroll);
        home_test=getActivity().findViewById(R.id.home_test);
        home_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(getActivity(), FindDevices.class);
                startActivity(intent1);
            }
        });
        home_scene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent1=new Intent(getActivity(), HomeScene.class);

                Intent intent1=new Intent(getActivity(), WangGuan.class);
                startActivity(intent1);
            }
        });
        home_set_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getActivity(), ManageDevices.class);
                startActivity(intent1);
            }
        });
        home_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getActivity(), Set.class);
                startActivity(intent1);
            }
        });
        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getActivity(), enter.class);
                startActivity(intent1);
            }
        });

        home_wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getActivity(), ScanActivity.class);
                startActivity(intent1);
            }
        });
        home_wangguan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getActivity(), TestScene.class);
                startActivity(intent1);
            }
        });


    }

}