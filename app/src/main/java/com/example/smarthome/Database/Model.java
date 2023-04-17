package com.example.smarthome.Database;

import org.litepal.crud.LitePalSupport;

import java.util.List;
/**
 * @description 弃用
 */
public class Model extends LitePalSupport {
    private int model;//模式
    private int curtain_model;
    private int light_model;
    private int lock_model;
    private int heater_model;

    private List<Light> lightList;
    private List<Curtain> curtainList;
    private List<AirConditioner> airConditionerList;
    private List<Device> deviceList;

    public List<Light> getLightList() {
        return lightList;
    }

    public void setLightList(List<Light> lightList) {
        this.lightList = lightList;
    }

    public List<Curtain> getCurtainList() {
        return curtainList;
    }

    public void setCurtainList(List<Curtain> curtainList) {
        this.curtainList = curtainList;
    }

    public List<AirConditioner> getAirConditionerList() {
        return airConditionerList;
    }

    public void setAirConditionerList(List<AirConditioner> airConditionerList) {
        this.airConditionerList = airConditionerList;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public int getCurtain_model() {
        return curtain_model;
    }

    public void setCurtain_model(int curtain_model) {
        this.curtain_model = curtain_model;
    }

    public int getLight_model() {
        return light_model;
    }

    public void setLight_model(int light_model) {
        this.light_model = light_model;
    }

    public int getLock_model() {
        return lock_model;
    }

    public void setLock_model(int lock_model) {
        this.lock_model = lock_model;
    }

    public int getHeater_model() {
        return heater_model;
    }

    public void setHeater_model(int heater_model) {
        this.heater_model = heater_model;
    }

    public Model(int model, int curtain_model, int light_model, int lock_model, int heater_model) {
        this.model = model;
        this.curtain_model = curtain_model;
        this.light_model = light_model;
        this.lock_model = lock_model;
        this.heater_model = heater_model;
    }

    public Model(int model, int curtain_model, int light_model, int lock_model) {
        this.model = model;
        this.curtain_model = curtain_model;
        this.light_model = light_model;
        this.lock_model = lock_model;
    }

    public Model() {
    }
}
