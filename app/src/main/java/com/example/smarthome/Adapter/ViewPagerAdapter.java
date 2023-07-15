package com.example.smarthome.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.smarthome.Database.Device;
import com.example.smarthome.MQTT.ClientMQTT;
import com.example.smarthome.Page_Samrt.AdjustTheLights;
import com.example.smarthome.R;
import com.example.smarthome.View.StepSeekBar;
import com.github.iielse.switchbutton.SwitchView;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.litepal.LitePal;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter{
    private ClientMQTT clientMQTT;
    private List<View> mList;
    private SwitchView breathe,open,open_All;
    private String target_long_address;
    private StepSeekBar brightness;
    int breath1=0;
    int breath2=0;
    int breath3=0;
    int breath4=0;
    int open1=0;
    int open2=0;
    int open3=0;
    int open4=0;
int  openAll1=0;
private Context context;
    /**
     *
     * @param list
     * @param target_long_address 传入长地址寻找设备，在AdjustLights那里用getIntent()获取传入的
     */
    public ViewPagerAdapter(List list,String target_long_address) {
        this.mList = list;
        this.target_long_address=target_long_address;
    }
public void setContext(Context context){
        this.context=context;
}
    /**
     * 返回当前有效视图的个数
     */
    @Override
    public int getCount() {
        return mList.size();
    }

    /**
     * 该函数用来判断instantiateItem(ViewGroup, int)函数所返回来的Key与一个页面视图是否是代表的同一个视图(即它俩是否是对应的，对应的表示同一个View)
     * @return 如果对应的是同一个View，返回True，否则返回False。
     */

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 这个函数的实现的功能是创建指定位置的页面视图。适配器有责任增加即将创建的View视图到这里给定的
     * container中，这是为了确保在finishUpdate(viewGroup)返回时这已经被完成
     * @return 返回一个代表新增视图页面的Object（Key），这里没必要非要返回视图本身，也可以这个页面的
     * 其它容器（只要可以与新增加的view一一对应即可）
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        clientMQTT=new ClientMQTT("light");
        try {
            clientMQTT.Mqtt_innit();
        } catch (
                MqttException e) {
            e.printStackTrace();
        }
       clientMQTT.startReconnect(context);
        Device device= LitePal.where("target_long_address = ?",target_long_address).findFirst(Device.class);
        String target_short_address=device.getTarget_short_address();
        String device_type= device.getDevice_type();

        String valid_data;
        String valid_data_length;
        breathe=(SwitchView)mList.get(position).findViewById(R.id.v_switch_1);
        open=(SwitchView)mList.get(position).findViewById(R.id.primary_button);
        brightness=(StepSeekBar)mList.get(position).findViewById(R.id.brightness);
        open_All=(SwitchView)mList.get(position).findViewById(R.id.open_All);
        open_All.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(openAll1==0){
                    clientMQTT.publishMessagePlus(null,"0x"+target_short_address,"0x"+device_type,"0x0004"+"05","0x03");
                    openAll1=1;
                }
                else{
                    clientMQTT.publishMessagePlus(null,"0x"+target_short_address,"0x"+device_type,"0x000400","0x03");
                    openAll1=0;
                }

            }
        });
        switch (position){
            case 0://第一个界面的控件

                breathe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(breath1==0){
                            clientMQTT.publishMessagePlus(null,"0x"+target_short_address,"0x"+device_type,"0x0106","0x02");
                            breath1=1;

                        }
                        else{
                            clientMQTT.publishMessagePlus(null,"0x"+target_short_address,"0x"+device_type,"0x0105","0x02");
                            breath1=0;
                        }

                    }
                });

                open.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(open1==0){
                            clientMQTT.publishMessagePlus(null,"0x"+target_short_address,"0x"+device_type,"0x0104"+"05","0x03");
                            open1=1;

                        }
                        else{
                            clientMQTT.publishMessagePlus(null,"0x"+target_short_address,"0x"+device_type,"0x010400","0x03");
                            open1=0;
                        }
                    }
                });

                brightness.setOnCursorChangeListener(new StepSeekBar.OnCursorChangeListener() {
                    @Override
                    public void onCursorChanged(int location, String textMark) {
                        String bright=String.valueOf(location);
                        if(bright.length()==1)
                            bright="0"+bright;
                        clientMQTT.publishMessagePlus(null,"0x"+target_short_address,"0x"+device_type,"0x0104"+bright,"0x03");

                    }
                });
                break;
            case 1:
                breathe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(breath2==0){
                            clientMQTT.publishMessagePlus(null,"0x"+target_short_address,"0x"+device_type,"0x0206","0x02");
                            breath2=1;

                        }
                        else{
                            clientMQTT.publishMessagePlus(null,"0x"+target_short_address,"0x"+device_type,"0x0205","0x02");
                            breath2=0;
                        }

                    }
                });
                open.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(open2==0){
                            clientMQTT.publishMessagePlus(null,"0x"+target_short_address,"0x"+device_type,"0x0204"+"05","0x03");
                            open2=1;

                        }
                        else{
                            clientMQTT.publishMessagePlus(null,"0x"+target_short_address,"0x"+device_type,"0x020400","0x03");
                            open2=0;
                        }
                    }
                });
                brightness.setOnCursorChangeListener(new StepSeekBar.OnCursorChangeListener() {
                    @Override
                    public void onCursorChanged(int location, String textMark) {
                        String bright=String.valueOf(location);
                        if(bright.length()==1)
                            bright="0"+bright;
                        clientMQTT.publishMessagePlus(null,"0x"+target_short_address,"0x"+device_type,"0x02" +
                                "04"+bright,"0x03");


                    }
                });
                break;
            case 2:
                breathe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(breath3==0){
                            clientMQTT.publishMessagePlus(null,"0x"+target_short_address,"0x"+device_type,"0x0306","0x02");
                            breath3=1;

                        }
                        else{
                            clientMQTT.publishMessagePlus(null,"0x"+target_short_address,"0x"+device_type,"0x0305","0x02");
                            breath3=0;
                        }

                    }
                });
                open.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(open3==0){
                            clientMQTT.publishMessagePlus(null,"0x"+target_short_address,"0x"+device_type,"0x0304"+"05","0x03");
                            open3=1;

                        }
                        else{
                            clientMQTT.publishMessagePlus(null,"0x"+target_short_address,"0x"+device_type,"0x030400","0x03");
                            open3=0;
                        }
                    }
                });                    brightness.setOnCursorChangeListener(new StepSeekBar.OnCursorChangeListener() {
                    @Override
                    public void onCursorChanged(int location, String textMark) {
                        String bright=String.valueOf(location);
                        if(bright.length()==1)
                            bright="0"+bright;
                        clientMQTT.publishMessagePlus(null,"0x"+target_short_address,"0x"+device_type,"0x0304"+bright,"0x03");

                    }
                });
                break;
            case 3:
                breathe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(breath4==0){
                            clientMQTT.publishMessagePlus(null,"0x"+target_short_address,"0x"+device_type,"0x0406","0x02");
                            breath4=1;

                        }
                        else{
                            clientMQTT.publishMessagePlus(null,"0x"+target_short_address,"0x"+device_type,"0x0405","0x02");
                            breath4=0;
                        }

                    }
                });
                open.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(open4==0){
                            clientMQTT.publishMessagePlus(null,"0x"+target_short_address,"0x"+device_type,"0x0404"+"05","0x03");
                            open4=1;

                        }
                        else{
                            clientMQTT.publishMessagePlus(null,"0x"+target_short_address,"0x"+device_type,"0x040400","0x03");
                            open4=0;
                        }
                    }
                });                    brightness.setOnCursorChangeListener(new StepSeekBar.OnCursorChangeListener() {
                    @Override
                    public void onCursorChanged(int location, String textMark) {
                        String bright=String.valueOf(location);
                        if(bright.length()==1)
                            bright="0"+bright;
                        clientMQTT.publishMessagePlus(null,"0x"+target_short_address,"0x"+device_type,"0x0404"+bright,"0x03");
                    }
                });
                break;
        }
        container.addView(mList.get(position));
        return mList.get(position);
    }

    /**
     * 该方法实现的功能是移除一个给定位置的页面。适配器有责任从容器中删除这个视图。这是为了确保
     * 在finishUpdate(viewGroup)返回时视图能够被移除
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mList.get(position));
    }
}
