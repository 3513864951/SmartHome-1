package com.example.smarthome.Json;


//newname:     Tools

import com.alibaba.fastjson.JSONObject;
import com.example.smarthome.Objects.Info;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Map;
/**
 * @description 弃用
 */
public class JSONTools {
    static final String host="66666";
    static final int port=6666;
    void SubmitData(Map<String, Object> map) throws IOException {
        Socket socket=new Socket(JSONTools.host, JSONTools.port);
        OutputStream outputStream=socket.getOutputStream();
        OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream);
        BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
        JSONObject jsonObject=new JSONObject(map);
    }

//public  void PutDataInMap(String adress,String code,String mess){
//Map<String,Object> map=new HashMap<>();
//map.put("adress",adress);
//map.put("code",code);
//map.put("mess",mess);
//
//}
//    void ParseJsonWithObject(String jsonData, Info info){
//        try {
//            org.json.JSONObject jsonObject=new org.json.JSONObject(jsonData);
//            info.setAdress(jsonObject.getString("adress"));//
//            info.setCode(jsonObject.getString("code"));
//            info.setMess(jsonObject.getString("mess"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    //这个要看返回的内容设计



}
