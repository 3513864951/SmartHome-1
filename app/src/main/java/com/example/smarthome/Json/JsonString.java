package com.example.smarthome.Json;
import java.util.HashMap;
import java.util.Map;
/**
 * @description 生成JSON字符串
 */
public class JsonString {
    private String timestamp;
    private String firmware_version;
    private String device_id;
    private String misc;
    private String target_short_address;
    private String device_type;
    private String valid_data;
    private String valid_data_length;
    private String controller_long_address="0xBD542A27004B1200";
    public JsonString( String timestamp,String controller_long_address,String device_id, String misc, String target_short_address, String device_type, String valid_data, String valid_data_length) {
        this.timestamp=timestamp;
        this.device_id = device_id;
        this.misc = misc;
        this.target_short_address = target_short_address;
        this.device_type = device_type;
        this.valid_data = valid_data;
        this.valid_data_length = valid_data_length;
    }

    @Override
    public String toString() {
        misc=null;
        return "{" +"\"firmware_version\":"+"\"1.2.3\","+
                "\"controller_long_address\":\""+controller_long_address+"\","+
                "\"device_id\":\""+device_id+"\"," +
                "\"other_data\": {" +
                "\"misc\":\""+misc+"\"}," +
                "\"target_short_address\":\""+target_short_address+"\","+
                "\"device_type\":\""+device_type+"\"," +
                "\"valid_data\":\"" +valid_data+"\"," +
                "\"valid_data_length\":\""+valid_data_length+"\""+
                "}";
    }

}
