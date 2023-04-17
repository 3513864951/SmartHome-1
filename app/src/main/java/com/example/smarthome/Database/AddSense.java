package com.example.smarthome.Database;

import org.litepal.crud.LitePalSupport;
/**
 * @description 弃用
 */
public class AddSense extends LitePalSupport {

    private String sense_name;
    public void setSense_name(String sense_name) {
        this.sense_name = sense_name;
    }


    public String getSense_name() {
        return sense_name;
    }

    public AddSense() {
        this.sense_name =sense_name;
    }

    @Override
    public String toString() {
        return "AddSense{" +
                ",sense='" + sense_name + '\'' +
                '}';
    }
}



