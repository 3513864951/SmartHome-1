package com.example.smarthome.Database;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;
/**
 * @description 房间
 */
public class AddHomes extends LitePalSupport {
    private int id;
    private String home;//名称
    private String time;//判断
    private String flag;//判断是不是缓存数据对象
    private List<Device> deviceList=new ArrayList<>();
    private List<String> strings=new ArrayList<>();

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }



    public AddHomes(){
        this.home=home;
    }
    @Override
    public String toString() {
        return "AddHomes{" +
                ", home='" + home + '\'' +
                '}';
    }
}
