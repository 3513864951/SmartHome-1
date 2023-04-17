package com.example.smarthome.Database;
import org.litepal.crud.LitePalSupport;

/**
 * @description 弃用
 */
public class AddDevice extends LitePalSupport {

    private static AddDevice app;
    private String device;

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
    public AddDevice(){
        this.device=device;
    }
    @Override
    public String toString() {
        return "AddDevice{" +
                ",device='" + device + '\'' +
                '}';
    }


}
