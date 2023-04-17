package com.example.smarthome.Database.Scene;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;
/**
 * @description 场景中的设备
 */
public class S_Device extends LitePalSupport {

    private String category;//判断是条件电器还是执行电器,1为执行的，0为条件
    private int id;
   //只需要记录设备长地址短地址
    private String target_long_address;
    private String target_short_address;
    private String device_type;
    private Condition condition;
    private Mission mission;
    private Temp temp;
    private Scene scene;

    private String name;//设备的名称
    private String tem_over;//温度高于
    private String tem_below;//温度低于
    private String isSmoke;//是否烟雾报警
//执行电器该有的属性
    private List<Integer> lightList=new ArrayList<>();
    private String open_light;
    private String close_light;
    private String light_model;//1:normal   2:sleep 3:breathe 4:自定义
    private int brightness;
    private String air_open;
    private String air_close;
    private String air_model;//1,2,3制热，制冷，通风
    private String wind;//1,2,3小，中，大
    private String curtain_open;
    private String curtain_close;
    private String curtain_deep;
    //发现设备的·那个switch退出应用后仍然保存状态


    public List<Integer> getLightList() {
        return lightList;
    }

    public void setLightList(List<Integer> lightList) {
        this.lightList = lightList;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public String getTarget_short_address() {
        return target_short_address;
    }

    public void setTarget_short_address(String target_short_address) {
        this.target_short_address = target_short_address;
    }

    public String getLight_model() {
        return light_model;
    }

    public void setLight_model(String light_model) {
        this.light_model = light_model;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getAir_model() {
        return air_model;
    }

    public void setAir_model(String air_model) {
        this.air_model = air_model;
    }

    public String getCurtain_deep() {
        return curtain_deep;
    }

    public void setCurtain_deep(String curtain_deep) {
        this.curtain_deep = curtain_deep;
    }

    public String getTarget_long_address() {
        return target_long_address;
    }

    public void setTarget_long_address(String target_long_address) {
        this.target_long_address = target_long_address;
    }

    public String getCurtain_open() {
        return curtain_open;
    }

    public void setCurtain_open(String curtain_open) {
        this.curtain_open = curtain_open;
    }

    public String getCurtain_close() {
        return curtain_close;
    }

    public void setCurtain_close(String curtain_close) {
        this.curtain_close = curtain_close;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public String getTem_over() {
        return tem_over;
    }

    public void setTem_over(String tem_over) {
        this.tem_over = tem_over;
    }

    public String getTem_below() {
        return tem_below;
    }

    public void setTem_below(String tem_below) {
        this.tem_below = tem_below;
    }

    public String getIsSmoke() {
        return isSmoke;
    }

    public void setIsSmoke(String isSmoke) {
        this.isSmoke = isSmoke;
    }

    public String getOpen_light() {
        return open_light;
    }

    public void setOpen_light(String open_light) {
        this.open_light = open_light;
    }

    public String getClose_light() {
        return close_light;
    }

    public void setClose_light(String close_light) {
        this.close_light = close_light;
    }

    public String getAir_open() {
        return air_open;
    }

    public void setAir_open(String air_open) {
        this.air_open = air_open;
    }

    public String getAir_close() {
        return air_close;
    }

    public void setAir_close(String air_close) {
        this.air_close = air_close;
    }
}
