package com.example.smarthome.Scene.SmartDevice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.smarthome.Adapter.AirListAdaptor;
import com.example.smarthome.Adapter.CurtainListAdaptor;
import com.example.smarthome.Database.Device;
import com.example.smarthome.Database.Scene.Condition;
import com.example.smarthome.Database.Scene.S_Device;
import com.example.smarthome.Database.Scene.Temp;
import com.example.smarthome.R;
import com.google.android.material.button.MaterialButton;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Set_curtain extends AppCompatActivity {
    public static final String TIME="time";
    private List<Device> curtainList;
        private ImageView set_close;
        private ImageView set_open;
        private AppCompatSeekBar set_deep;
        private Toolbar scene_curtain;
        private MaterialButton create_home;
        private RecyclerView recyclerView;
        private CurtainListAdaptor curtainListAdaptor;
        private ConstraintLayout constraintLayout;
        private Condition condition;
        private List<Integer> positionList;
        private int open=-1;
        private int close=-1;
        private static int deep=-1;
        private int count=-1;
        private int flag=-1;//判断是否是创建新的条件
        private String timeIn;
    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_curtain);

        set_close=findViewById(R.id.set_close);
        set_open=findViewById(R.id.set_open);
        set_deep=findViewById(R.id.set_deep);
        scene_curtain=findViewById(R.id.scene_curtain);
        create_home=findViewById(R.id.create_home);
        //判断是否为再次编辑
        Intent intent=getIntent();
        timeIn=intent.getStringExtra(Set_curtain.TIME);
        if(timeIn==null)
            flag=0;//新的
        else
            flag=1;
        recyclerView=findViewById(R.id.select_condition);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        curtainListAdaptor=new CurtainListAdaptor(this,R.layout.scene_curtainlist,curtainList);
        recyclerView.setAdapter(curtainListAdaptor);
        curtainListAdaptor.notifyDataSetChanged();
        curtainListAdaptor.setOnItemClickListner(new CurtainListAdaptor.OnItemClickListner() {
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
                curtainListAdaptor.notifyDataSetChanged();

            }
        });
        curtainListAdaptor.setCallBack(new CurtainListAdaptor.CallBack() {
            @Override
            public <T> void convert(CurtainListAdaptor.ViewHolder holder, T bean, int position) {
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
        init();
    }
    private void init(){

        create_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                Date date=new Date(System.currentTimeMillis());
                String time=simpleDateFormat.format(date);
                if(flag==0)   //flag为0就新建Condition
                {
                    Temp temp=LitePal.findLast(Temp.class);
                    condition=new Condition();
                    condition.setTime(time);
                    condition.setTemp(temp);
                    condition.save();

                }else
                {
                    List<Condition> conditionList=LitePal.where("time = ?",Set_curtain.TIME).find(Condition.class);
                    condition=conditionList.get(0);
                }
                //如果open,deep,close不等于-1就保存
                if(positionList!=null) {
                    if (open != -1 || close != -1 || deep != -1) {
                        if (!positionList.isEmpty()) {
                            Temp temp = LitePal.findLast(Temp.class);
                            for (int i = 0; i < positionList.size(); i++) {
                                int n = positionList.get(i);
                                String target_long_address = curtainList.get(n).getTarget_long_address();
                                S_Device s_device = new S_Device();
                                if (open != -1)
                                    s_device.setCurtain_open("1");
                                else if (close != -1)
                                    s_device.setCurtain_open("0");
                                if (deep != -1)
                                    s_device.setCurtain_deep("" + deep);
                                s_device.setTarget_long_address(target_long_address);
                                s_device.setCategory("1");
                                //                            s_device.setTemp(temp);应该让condition跟temp关联，要不然要temp干嘛
                                //Condition是进入条件的时候创建的，
                                //如果退出创建场景时|Condition没有删除，由于Condition是跟Temp绑定的，就通过刚进入场景时的删除操作删除数据库中残留的Condition
                                //我该怎么知到这次进来是再次编辑条件还是创建新的条件
                                if (flag == 0) {
                                    s_device.setCondition(condition);
                                    s_device.save();
                                } else
                                    s_device.updateAll("target_long_address = ?", target_long_address);
                                finish();
                            }
                        } else
                            Toast.makeText(Set_curtain.this, "请选择设备！", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Set_curtain.this, "请选择要执行的任务！", Toast.LENGTH_SHORT).show();

                    }
                }else
                    Toast.makeText(Set_curtain.this,"请添加电器！",Toast.LENGTH_SHORT).show();

            }
        });
        scene_curtain.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先查找是否已经存在数据，没有就新建
                //应该加一个保存按钮
                finish();
            }
        });
        set_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(close==-1)
                {
                    set_close.setImageResource(R.drawable.curtain_close_selected);
                    set_open.setImageResource(R.drawable.curtain_open);
                    close=1;
                }else {
                    set_close.setImageResource(R.drawable.curtain_close);
                    close=-1;
                }
            }
        });
        set_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击后变色，再次点击后又变成灰色
                //点击后先检测这个是否已经处于选择状态，如果没有，就变亮，让另一个变暗；如果处于，就让这个变暗，另一个不用管
                if(open==-1)
                {
                    set_open.setImageResource(R.drawable.curtain_open_selected);
                    set_close.setImageResource(R.drawable.curtain_close);
                    open=1;
                }else {
                    set_open.setImageResource(R.drawable.curtain_open);
                    open=-1;
                }
            }
        });
        //设置深度监听
        set_deep.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                deep=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}