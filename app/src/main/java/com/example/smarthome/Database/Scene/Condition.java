package com.example.smarthome.Database.Scene;

import com.example.smarthome.Database.Sensor;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;
/**
 * @description 场景条件
 */
public class Condition extends LitePalSupport {


    //场景控制就用    Scene就好
    //点击
    private int id;
    private Scene scene;//这个是condition属于什么场景
    private Temp temp;
    private String time;//用来判断是否是再次编辑，+ 也能通过时间找到条件，进而进行界面内容的初始化

    /**
     * @param flag 为了快速的查找一个condition对象里面存的条件的种类，放入的数据为第几个条件
     *
     */
    private int judge;

    private List<S_Device> S_deviceList=new ArrayList<>();//每一个这里面存放着条件
    //1
    private String isClick;
    //2
    private C_Time c_time=new C_Time();
    //3
    private Scene c_scene;//XX场景执行就执行
    //4
    private Sensor sensor;
    //5
    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public int getJudge() {
        return judge;
    }

    public void setJudge(int judge) {
        this.judge = judge;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public List<S_Device> getS_deviceList() {
        return S_deviceList;
    }

    public void setS_deviceList(List<S_Device> s_deviceList) {
        S_deviceList = s_deviceList;
    }

    public String getIsClick() {
        return isClick;
    }

    public void setIsClick(String isClick) {
        this.isClick = isClick;
    }

    public C_Time getC_time() {
        return c_time;
    }

    public void setC_time(C_Time c_time) {
        this.c_time = c_time;
    }

    public Scene getC_scene() {
        return c_scene;
    }

    public void setC_scene(Scene c_scene) {
        this.c_scene = c_scene;
    }
}
