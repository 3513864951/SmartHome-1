package com.example.smarthome.Json;
/**
 * @description 弃用
 */
public class JsonArray {
    private int adress;
    private int code;
    private String mess;


    public JsonArray(int adress, int code, String mess) {
        this.adress = adress;
        this.code = code;
        this.mess = mess;
    }

    public int getAdress() {
        return adress;
    }

    public void setAdress(int adress) {
        this.adress = adress;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public String toString() {
        return "[{ \"adress\":" + adress
                + ",\"code\":\"" + code
                + "\",\"mess\":" + mess
                + "}]";
    }
}
