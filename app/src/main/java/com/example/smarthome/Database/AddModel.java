package com.example.smarthome.Database;
import org.litepal.crud.LitePalSupport;
/**
 * @description 弃用
 */
public class AddModel extends LitePalSupport  {

    private String model;
    public void setModel(String model) {
        this.model =model;
    }
    public String getModel() {
        return model;
    }
    public AddModel(){
        this.model=model;
    }
    @Override
    public String toString() {
        return "AddModel{" +
                ", model='" + model + '\'' +
                '}';
    }

}