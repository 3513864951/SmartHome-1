package com.example.smarthome.Objects;

/**
 * @description 弃用
 */
public class Info {
    //
    private String adress;
    private String code;
    private String mess;

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public Info(String adress, String code, String mess) {
        this.adress = adress;
        this.code = code;
        this.mess = mess;
    }
}

