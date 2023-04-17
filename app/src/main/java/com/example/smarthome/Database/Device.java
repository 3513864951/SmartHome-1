package com.example.smarthome.Database;

import com.example.smarthome.Database.Scene.Scene;

import org.litepal.crud.LitePalSupport;

import java.util.List;
/**
 * @description 电器
 */
public class Device extends LitePalSupport {

    public static final String TARGET_LONG_ADDRESS="target_long_address";
    private int id;
    private String misc;
    private String target_short_address;
    private String device_type;//设备码
    private String valid_data;
    private String valid_data_length;
    private String network_flag;//入网标志
    //01 入网，00 未入网
    private String target_long_address;
    private String controller_long_address;
    private String data;//存放jsonData


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getController_long_address() {
        return controller_long_address;
    }

    public void setController_long_address(String controller_long_address) {
        this.controller_long_address = controller_long_address;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    private String name;//电器命名
private int curtain_extent;//窗帘程度
    //表关联
    private Model model;//离家模式等等等。。。。。。
    private Home home;//房间
    private List<Scene> sceneList;
    private AddHomes addHomes;
    private int flag;//是否同意允许该家器接入,
    private int isUpdate;//判断app开启电器入网是否收到了这条电器信息,在线标志
    private String light_id;
    private int light_brightness;//亮度
    private int light_temp;//色温
    private String wetness;
    private int air_HotOrCold;//制热还是制冷
    private String air_temp;//空调温度
    private int use;//是否已经成为场景的执行任务


    public AddHomes getAddHomes() {
        return addHomes;
    }

    public void setAddHomes(AddHomes addHomes) {
        this.addHomes = addHomes;
    }

    public int getUse() {
        return use;
    }

    public void setUse(int use) {
        this.use = use;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWetness() {
        return wetness;
    }

    public List<Scene> getSceneList() {
        return sceneList;
    }

    public void setSceneList(List<Scene> sceneList) {
        this.sceneList = sceneList;
    }

    public String getLight_id() {
        return light_id;
    }

    public void setLight_id(String light_id) {
        this.light_id = light_id;
    }

    public void setWetness(String wetness) {
        this.wetness = wetness;
    }

    public String getTarget_long_address() {
        return target_long_address;
    }

    public void setTarget_long_address(String target_long_address) {
        this.target_long_address = target_long_address;
    }


    public String getNetwork_flag() {
        return network_flag;
    }

    public void setNetwork_flag(String network_flag) {
        this.network_flag = network_flag;
    }

    public String getMisc() {
        return misc;
    }

    public void setMisc(String misc) {
        this.misc = misc;
    }

    public String getTarget_short_address() {
        return target_short_address;
    }

    public void setTarget_short_address(String target_short_address) {
        this.target_short_address = target_short_address;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getValid_data() {
        return valid_data;
    }

    public void setValid_data(String valid_data) {
        this.valid_data = valid_data;
    }

    public String getValid_data_length() {
        return valid_data_length;
    }

    public void setValid_data_length(String valid_data_length) {
        this.valid_data_length = valid_data_length;
    }

    public int getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(int isUpdate) {
        this.isUpdate = isUpdate;
    }


    public int getLight_brightness() {
        return light_brightness;
    }

    public void setLight_brightness(int light_brightness) {
        this.light_brightness = light_brightness;
    }

    public int getLight_temp() {
        return light_temp;
    }

    public void setLight_temp(int light_temp) {
        this.light_temp = light_temp;
    }

    public int getAir_HotOrCold() {
        return air_HotOrCold;
    }

    public void setAir_HotOrCold(int air_HotOrCold) {
        this.air_HotOrCold = air_HotOrCold;
    }

    public String getAir_temp() {
        return air_temp;
    }

    public void setAir_temp(String air_temp) {
        this.air_temp = air_temp;
    }

    public int getCurtain_extent() {
        return curtain_extent;
    }

    public void setCurtain_extent(int curtain_extent) {
        this.curtain_extent = curtain_extent;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }


    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Home getRoom() {
        return home;
    }

    public void setRoom(Home home) {
        this.home = home;
    }



    public Device() {
    }
}
