package com.example.smarthome.Database;

import org.litepal.crud.LitePalSupport;

import java.util.List;
/**
 * @description 弃用
 */
public class Room extends LitePalSupport {
    private int category;
    private List<Device> deviceList;
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    public Room() {
    }
}
