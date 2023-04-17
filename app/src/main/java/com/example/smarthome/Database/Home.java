package com.example.smarthome.Database;

import org.litepal.crud.LitePalSupport;
/**
 * @description 弃用
 */
public class Home extends LitePalSupport {
    public String getHomename() {
        return homename;
    }

    public void setHomename(String homename) {
        this.homename = homename;
    }

    private String homename;
    public Home(){
        this.homename=homename;
    }
    @Override
    public String toString() {
        return "Home{" +
                ", homename='" + homename + '\'' +
                '}';
    }
}
