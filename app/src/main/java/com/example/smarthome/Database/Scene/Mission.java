package com.example.smarthome.Database.Scene;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;
/**
 * @description 场景任务
 */
public class Mission extends LitePalSupport {
    private int id;
    private Temp temp;
    private String time;//设置的时间
    private Scene scene;
    /**
     * @tip 跟condition里面的一样
     */
    private int judge;
    private List<Scene> sceneList=new ArrayList<>();//执行的场景
    //1
    private List<String> messageList=new ArrayList<>();//系统通知
    //2
    private List<S_Device> s_deviceList=new ArrayList<>();//执行的设备
    //3 light
    //4 air
    //5 curtain
//6 加湿器

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

    public int getJudge() {
        return judge;
    }

    public void setJudge(int judge) {
        this.judge = judge;
    }

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Scene> getSceneList() {
        return sceneList;
    }

    public void setSceneList(List<Scene> sceneList) {
        this.sceneList = sceneList;
    }

    public List<String> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<String> messageList) {
        this.messageList = messageList;
    }

    public List<S_Device> getS_deviceList() {
        return s_deviceList;
    }

    public void setS_deviceList(List<S_Device> s_deviceList) {
        this.s_deviceList = s_deviceList;
    }
}
