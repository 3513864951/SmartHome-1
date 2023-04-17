package com.example.smarthome.Page_Huiju;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.smarthome.Adapter.ManageAdaptor;
import com.example.smarthome.Database.AddHomes;
import com.example.smarthome.Database.Device;
import com.example.smarthome.R;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description 添加房间
 */
public class AddHome extends AppCompatActivity {
    View view;
    Toolbar addHome_back;
    Button create_home;
    EditText home_name;
    AddHomes addHomes=new AddHomes();
    List<Device> deviceList=new ArrayList<>();
    static int id=0;
    RecyclerView device_list;
    String name_hm;
    String time="0";
    public   Integer dra=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_home);
        addHome_back=findViewById(R.id.addHome_back);
        setSupportActionBar(addHome_back);
        device_list=findViewById(R.id.device_list);
        Intent intent=getIntent();
        time=intent.getStringExtra("time");
        if(time!=null){
            addHomes=LitePal.where("time = ?",time).findFirst(AddHomes.class);
            id=addHomes.getId();
        }
        else
            addHomes=new AddHomes();


        LitePal.deleteAll(AddHomes.class,"flag= ?","temp");
        AddHomes addHomes=new AddHomes();
        addHomes.setFlag("temp");
        addHomes.save();
        init();
        recyclerView();

    }
    public boolean onCreateOptionsMenu(Menu menu) {//加载toolbar.xml这个菜单文件
        getMenuInflater().inflate(R.menu.homemanage, menu);
        return true;//表示允许创建菜单
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//处理各个按钮的点击事件
        switch (item.getItemId()) {//判断点击的按钮是哪个
            case R.id.add_device:
                Intent intent=new Intent(AddHome.this,HomeDeviceManage.class);
                if(time!=null)
                    intent.putExtra("id",id);
                startActivity(intent);
                break;
            default:
        }
        return true;
    }
    private void init() {
        create_home=findViewById(R.id.create_home);
        home_name=findViewById(R.id.home_name);
        if(time!=null)
            home_name.setText(addHomes.getHome());
        addHome_back.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        create_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_hm=home_name.getText().toString();    //获取输入框值
                //判断输入框中是否有值
                if(TextUtils.isEmpty(name_hm)){
                    Toast.makeText(AddHome.this,"请输入房间名称",Toast.LENGTH_SHORT).show();
                    return;
                }
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                Date date=new Date(System.currentTimeMillis());
                String time1=simpleDateFormat.format(date);
                AddHomes addHomes1=LitePal.where("flag = ?","temp").findFirst(AddHomes.class);
                int idd=addHomes1.getId();
                List<Device> deviceList=new ArrayList<>();
                deviceList=LitePal.where("addHomes_id = ?",idd+"").find(Device.class);
                for(int i=0;i<deviceList.size();i++){
                    deviceList.get(i).setAddHomes(null);
                    deviceList.get(i).save();
                }
                for(int i=0;i<addHomes1.getStrings().size();i++){
                    List<Device> deviceList1=LitePal.findAll(Device.class);
                    for(int j=0;j<deviceList1.size();j++){
                        if(deviceList1.get(j).getTarget_long_address().equals(addHomes1.getStrings().get(i))){
                            addHomes.getDeviceList().add(deviceList1.get(j));
                            break;
                        }
                    }
                }
                addHomes.setHome(name_hm);
                addHomes.setTime(time1);
                if(time==null)
                    addHomes.save();
                else
                    addHomes.updateAll("time = ?",time);
                finish();
            }
        });
    }
    private void recyclerView(){
        deviceList=LitePal.where("addHomes_id = ?",id+"").find(Device.class);
        LinearLayoutManager layoutManager=new LinearLayoutManager(AddHome.this,LinearLayoutManager.HORIZONTAL,false);
        device_list.setLayoutManager(layoutManager);
        ManageAdaptor manageAdaptor=new ManageAdaptor(deviceList);
        device_list.setAdapter(manageAdaptor);
        manageAdaptor.notifyDataSetChanged();
    }
    @Override
    protected void onStart() {
        super.onStart();
        init();
    }


}