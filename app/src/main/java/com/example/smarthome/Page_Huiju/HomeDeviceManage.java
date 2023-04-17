package com.example.smarthome.Page_Huiju;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adapter.HomeDeviceAdaptor;
import com.example.smarthome.Database.AddHomes;
import com.example.smarthome.Database.Device;
import com.example.smarthome.R;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
/**
 * @description 房间设备管理
 */
public class HomeDeviceManage extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    List<Device> chooseDevice=new ArrayList<>();
    List<Device> deviceList=new ArrayList<>();
    HomeDeviceAdaptor homeDeviceAdaptor;
    AddHomes addHomes;
    AddHomes temp;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeadddevice);
        Intent intent=getIntent();
        recyclerView=findViewById(R.id.home_add_device);
        toolbar=findViewById(R.id.manageHome_back);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        id=intent.getStringExtra("id");
        temp=LitePal.where("flag = ?","temp").findFirst(AddHomes.class);
        addHomes=LitePal.where("id = ?",id).findFirst(AddHomes.class);
        initRecyclerView();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//处理各个按钮的点击事件
        switch (item.getItemId()) {//判断点击的按钮是哪个
            case R.id.save_device:
                    List<Device> devices=new ArrayList<>();
                    chooseDevice=homeDeviceAdaptor.getDevices();
                        for(int i=0;i<chooseDevice.size();i++){
                            temp.getStrings().add(chooseDevice.get(i).getTarget_long_address());
                            temp.updateAll("flag = ?","temp");
                        }
                finish();
                break;
            default:
        }
        return true;
    }
    public boolean onCreateOptionsMenu(Menu menu) {//加载toolbar.xml这个菜单文件
        getMenuInflater().inflate(R.menu.deviceadd, menu);
        return true;//表示允许创建菜单
    }
    private void initRecyclerView(){
        if(id!=null)
            chooseDevice= LitePal.where("addHomes_id = ?",id).find(Device.class);
        deviceList=LitePal.findAll(Device.class);
        homeDeviceAdaptor=new HomeDeviceAdaptor(deviceList);
        homeDeviceAdaptor.setDevices(chooseDevice);
        //TODO 设置为已选择
        GridLayoutManager gridLayoutManager=new GridLayoutManager(HomeDeviceManage.this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(homeDeviceAdaptor);
        homeDeviceAdaptor.notifyDataSetChanged();
    }
}
