package com.example.smarthome.Json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.smarthome.Database.Device;
import com.example.smarthome.Database.Scene.C_Time;
import com.example.smarthome.Database.Scene.Condition;
import com.example.smarthome.Database.Scene.Scene;
import com.example.smarthome.Database.Sensor;
import com.google.gson.Gson;

import org.litepal.LitePal;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @description 解析JSON数据
 */
public class ParseJson {
    public void parseJson(String jsonData, Map<String,String> map){
        JSONObject jsonObject=JSONObject.parseObject(jsonData);
        JSONArray sensors=jsonObject.getJSONArray("sensors");
        JSONArray jsonArray= JSON.parseArray(sensors.toString());
        int size=jsonArray.size();
        for (int i = 0; i < size; i++) {
            JSONObject jsonObject1=jsonArray.getJSONObject(i);
            map.put("source_short_address",jsonObject1.getString("source_short_address"));
            map.put("network_flag",jsonObject1.getString("network_flag"));
            map.put("source_command",jsonObject1.getString("source_command"));//设备类型
            map.put("source_data",jsonObject1.getString("source_data"));
            map.put("misc",jsonObject1.getString("misc"));
        }

    }
    public void ParseJsonData(String jsonData,int flag) {
        JSONObject jsonObject = JSONObject.parseObject(jsonData);
        String valid_data_length=jsonObject.getString("valid_data_length").substring(2,4);
        String device_type_ = jsonObject.getString("device_type");
        String valid_data = jsonObject.getString("valid_data");
        String length1=Integer.toHexString(valid_data.length()/2-1);
    if(length1.length()==1) {
        length1="0"+length1;
    }
         flag=0;
        if(!length1.equals(valid_data_length)) {
            if ("0x00".equals(device_type_)) {
                String controller_long_address = jsonObject.getString("controller_long_address");
                String device_type = valid_data.substring(2, 4);
                String network_flag = valid_data.substring(4, 6);
                String target_long_address = valid_data.substring(6, 22);
                String target_short_address = valid_data.substring(22, 26);
                List<Device> deviceList = LitePal.where("target_long_address = ?", target_long_address).find(Device.class);
                Device device = new Device();
                if (deviceList.isEmpty()) {
                    device.setDevice_type(device_type);
                    device.setNetwork_flag(network_flag);
                    device.setTarget_long_address(target_long_address);
                    device.setTarget_short_address(target_short_address);
                    device.setController_long_address(controller_long_address);
                    device.setUse(0);
                    device.setData(jsonData);
                    if("01".equals(network_flag)) {
                        device.setIsUpdate(1);
                    } else {
                        device.setIsUpdate(0);
                    }
                    device.setFlag(0);
                    if (LitePal.where("target_long_address = ?", target_long_address).find(Device.class).isEmpty()) {
                        device.save();
                    }else {
                        device.updateAll("target_long_address = ?", target_long_address);
                    }

                } else {
                    device.setDevice_type(device_type);
                    device.setNetwork_flag(network_flag);
                    device.setTarget_long_address(target_long_address);
                    device.setTarget_short_address(target_short_address);
                    device.setController_long_address(controller_long_address);
                    device.setData(jsonData);
                    if(network_flag.equals("01")) {
                        device.setIsUpdate(1);
                    } else {
                        device.setIsUpdate(0);
                    }
                    if (!LitePal.where("target_long_address = ?", target_long_address).find(Device.class).isEmpty()) {
                        device.updateAll("target_long_address = ?", target_long_address);
                    }
                }
            } else if("0xFC".equals(device_type_)){
                String controller_long_address = jsonObject.getString("controller_long_address");
                String device_type = valid_data.substring(2, 4);
                String network_flag = valid_data.substring(4, 6);
                String target_long_address = valid_data.substring(6, 22);
                String target_short_address = valid_data.substring(22, 26);
                //4-7
                int a=Integer.parseInt(device_type);
                if(a>=4){
                    switch (device_type) {
                        case "04": {
                            String temp1 = valid_data.substring(2, 4);
                            String temp2 = valid_data.substring(4, 6);
                            String wetness = valid_data.substring(6, 8);
                            int temp_1 = Integer.valueOf(temp1, 16);
                            int temp_2 = Integer.valueOf(temp2, 16);
                            int wetness_i = Integer.valueOf(wetness, 16);
                            List<Sensor> sensorList = LitePal.where("target_long_address = ? and device_type = ?", target_long_address, "04").find(Sensor.class);
                            Sensor sensor = new Sensor();
                            sensor.setDevice_type("04");
                            sensor.setData(jsonData);
                            sensor.setTarget_short_address(target_short_address);
                            sensor.setController_long_address(controller_long_address);
                            sensor.setTarget_long_address(target_long_address);
                            sensor.setTemp_air(String.valueOf(temp_1 + 0.1 * temp_2));
                            sensor.setWetness(String.valueOf(wetness_i));
                            sensor.setValid_data(valid_data);
                            if (network_flag.equals("01")) {
                                sensor.setIsUpdate(1);
                            } else {
                                sensor.setIsUpdate(0);
                            }
                            if (LitePal.where("target_long_address = ?", target_long_address).find(Sensor.class).isEmpty()) {
                                sensor.setFlag(1);
                                sensor.save();
                            } else {
                                sensor.updateAll("target_long_address = ?", target_long_address);
                            }

                            break;
                        }
                        case "05": {
                            String temp1 = valid_data.substring(2, 4);
                            String temp2 = valid_data.substring(4, 6);
                            int temp_1 = Integer.valueOf(temp1, 16);
                            int temp_2 = Integer.valueOf(temp2, 16);
                            List<Sensor> sensorList = LitePal.where("target_long_address = ? and device_type = ?", target_long_address, "05").find(Sensor.class);
                            Sensor sensor = new Sensor();
                            sensor.setDevice_type("05");
                            sensor.setData(jsonData);

                            sensor.setTarget_short_address(target_short_address);
                            sensor.setController_long_address(controller_long_address);
                            sensor.setTarget_long_address(target_long_address);
                            sensor.setTemp_air(String.valueOf(temp_1 + 0.1 * temp_2));
                            sensor.setValid_data(valid_data);
                            if (network_flag.equals("01")) {
                                sensor.setIsUpdate(1);
                            } else {
                                sensor.setIsUpdate(1);
                            }
                            if (LitePal.where("target_long_address = ?", target_long_address).find(Sensor.class).isEmpty()) {
                                sensor.setFlag(0);
                                sensor.save();
                            } else {
                                sensor.updateAll("target_long_address = ?", target_long_address);
                            }

                            break;
                        }
                        case "06": {
                            String wetness = valid_data.substring(2, 4);
                            int wetness_i = Integer.valueOf(wetness, 16);
                            List<Sensor> sensorList = LitePal.where("target_long_address = ? and device_type = ?", target_long_address, "06").find(Sensor.class);
                            Sensor sensor = new Sensor();
                            sensor.setData(jsonData);

                            sensor.setDevice_type("06");
                            sensor.setTarget_short_address(target_short_address);
                            sensor.setController_long_address(controller_long_address);
                            sensor.setTarget_long_address(target_long_address);

                            sensor.setWetness(String.valueOf(wetness_i));
                            sensor.setValid_data(valid_data);
                            if (network_flag.equals("01")) {
                                sensor.setIsUpdate(1);
                            } else {
                                sensor.setIsUpdate(0);
                            }
                            if (LitePal.where("target_long_address = ?", target_long_address).find(Sensor.class).isEmpty()) {
                                sensor.setFlag(1);
                                sensor.save();
                            } else {
                                sensor.updateAll("target_long_address = ?", target_long_address);
                            }

                            break;
                        }
                        case "07": {
                            String smoking = valid_data.substring(0, 2);
                            int smoking_i = Integer.valueOf(smoking, 16);
                            List<Sensor> sensorList = LitePal.where("target_long_address = ? and device_type = ?", target_long_address, "07").find(Sensor.class);
                            Sensor sensor = new Sensor();
                            sensor.setData(jsonData);

                            sensor.setDevice_type("07");
                            sensor.setTarget_short_address(target_short_address);
                            sensor.setController_long_address(controller_long_address);
                            sensor.setTarget_long_address(target_long_address);

                            sensor.setSmoking(String.valueOf(smoking_i));
                            sensor.setValid_data(valid_data);
                            if (network_flag.equals("01")) {
                                sensor.setIsUpdate(1);
                            } else {
                                sensor.setIsUpdate(0);
                            }
                            if (LitePal.where("target_long_address = ?", target_short_address).find(Sensor.class).isEmpty()) {
                                sensor.setFlag(1);
                                sensor.save();
                            } else {
                                sensor.updateAll("target_long_address = ?", target_long_address);
                            }

                            break;
                        }
                    }

                }else {
                    List<Device> deviceList = LitePal.where("target_long_address = ?", target_long_address).find(Device.class);
                    Device device = new Device();
                    if (deviceList.isEmpty()) {
                        device.setDevice_type(device_type);
                        device.setNetwork_flag(network_flag);
                        device.setTarget_long_address(target_long_address);
                        device.setTarget_short_address(target_short_address);
                        device.setController_long_address(controller_long_address);
                        device.setData(jsonData);
                        device.setUse(0);
                        if(network_flag.equals("01")) {
                            device.setIsUpdate(1);
                        } else {
                            device.setIsUpdate(0);
                        }
                        device.setFlag(1);
                        if (LitePal.where("target_long_address = ?", target_long_address).find(Device.class).isEmpty()) {
                            device.save();
                        }else {
                            device.updateAll("target_long_address = ?", target_long_address);
                        }

                    } else {
                        device.setDevice_type(device_type);
                        device.setNetwork_flag(network_flag);
                        device.setTarget_long_address(target_long_address);
                        device.setTarget_short_address(target_short_address);
                        device.setController_long_address(controller_long_address);
                        device.setData(jsonData);
                        if(network_flag.equals("01")) {
                            device.setIsUpdate(1);
                        } else {
                            device.setIsUpdate(0);
                        }
                        device.setFlag(1);
                        device.setToDefault("");
                        if (!LitePal.where("target_long_address = ?", target_long_address).find(Device.class).isEmpty()) {
                            device.updateAll("target_long_address = ?", target_long_address);
                        }
                    }
                }

            }else if("0xFE".equals(device_type_)){
                String validData=jsonObject.getString("valid_data");
                String operation=validData.substring(2,4);
                String nameLength=validData.substring(4,6);
                int length=Integer.valueOf(nameLength,16);
                String hexName=validData.substring(6,6+length*2);
                String name = hexToChinese(hexName);
                String innerValidLength=validData.substring(6+length*2,8+length*2);
                int int_innerDataLength=Integer.valueOf(innerValidLength,16);
                String innerData=validData.substring(8+length*2,8+length*2+int_innerDataLength*2);
                List<Scene> sceneList=new ArrayList<>();
                sceneList=LitePal.where("name = ?",name).find(Scene.class);
                if(sceneList.isEmpty())
                {
                    Scene scene=new Scene();
                    scene.setName(name);
                    scene.save();
                }
                Scene scene=LitePal.where("name = ?",name).findFirst(Scene.class);
                switch (operation){
                    case "01":
                        break;
                    case "03":
                        Condition condition=new Condition();
                        String judge=innerData.substring(0,16);
                        /**
                         * 执行时间条件
                         */
                        if("70E46125004B1200".equals(judge)){
                            C_Time c_time=new C_Time();
                            if("00".equals(innerData.substring(16,18))){
                                String time=validData.substring(18,20);
                                c_time.setTime(time);
                                condition.setC_time(c_time);
                            }else
                            {
                                String timeStart=validData.substring(18,24);
                                String timeEnd=validData.substring(24,30);
                                c_time.setTime_end(timeEnd);
                                c_time.setTime_start(timeStart);
                                condition.setC_time(c_time);
                            }
                            c_time.setScene(scene);
                            condition.setScene(scene);
                            c_time.save();
                            condition.save();
                        }else
                        {
                            String target_long_address=innerData.substring(16,24);
                            String flag1=innerData.substring(24,26);
                            String sensorData=innerData.substring(26,int_innerDataLength-10);
                            Sensor sensor=LitePal.where("target_long_address = ?",target_long_address).findFirst(Sensor.class);
                            String device_type=sensor.getDevice_type();
                            if(device_type.equals("05")){
                                int  temp_Int=Integer.parseInt(sensorData.substring(0,2),16);
                                int temp_decimal=Integer.parseInt(sensorData.substring(2,4),16);
                                String temperature=temp_Int+temp_decimal*0.1+"";
                                sensor.setTemp_air(temperature);
                                scene.save();
                            }
                        }
                        break;

                }





            }
            else if (device_type_.equals("0x01")) {
                String controller_long_address = jsonObject.getString("controller_long_address");
                String target_short_address = jsonObject.getString("target_short_address");
                String target_long_address=jsonObject.getString("target_long_address");
                List<Device> deviceList = LitePal.where("target_short_address = ? and device_type = ?", target_short_address,"01").find(Device.class);
                    Device device = new Device();
                    device.setValid_data(valid_data);
                    device.setDevice_type("01");
                    device.setTarget_short_address(target_short_address);
                    device.setController_long_address(controller_long_address);
                    device.setData(jsonData);
                    device.setNetwork_flag("01");
                    device.setIsUpdate(1);
                    if (LitePal.where("target_long_address = ?", target_long_address).find(Device.class).isEmpty()) {
                        device.setFlag(0);
                        device.setUse(0);
                        device.save();
                    }else {
                        device.updateAll("target_long_address = ?", target_long_address);
                    }


            }
            else if("0x03".equals(device_type_)){
                String controller_long_address = jsonObject.getString("controller_long_address");
                String deep = jsonObject.getString("valid_data");
                String target_short_address = jsonObject.getString("target_short_address");
                String target_long_address=jsonObject.getString("target_long_address");
                List<Device> deviceList = LitePal.where("target_short_address = ? and device_type = ?", target_short_address,"03").find(Device.class);
                Device device = new Device();
                    device.setCurtain_extent(Integer.parseInt(deep));
                    device.setDevice_type("03");
                    device.setTarget_short_address(target_short_address);
                    device.setController_long_address(controller_long_address);
                    device.setData(jsonData);
                    device.setNetwork_flag("01");
                    device.setIsUpdate(1);
                    if (LitePal.where("target_long_address = ?", target_long_address).find(Device.class).isEmpty()) {
                        device.setFlag(0);
                        device.setUse(0);
                        device.save();
                    }else {
                        device.updateAll("target_long_address = ?", target_long_address);
                    }

            }else if("0x04".equals(device_type_)){
                String controller_long_address = jsonObject.getString("controller_long_address");
                String target_short_address = jsonObject.getString("target_short_address");
                String target_long_address=jsonObject.getString("target_long_address");
                String temp1=valid_data.substring(2,4);
                String temp2=valid_data.substring(4,6);
                String wetness=valid_data.substring(6,8);
                int temp_1=Integer.valueOf(temp1,16);
                int temp_2=Integer.valueOf(temp2,16);
                int wetness_i=Integer.valueOf(wetness,16);
                List<Sensor> sensorList = LitePal.where("target_short_address = ? and device_type = ?", target_short_address,"04").find(Sensor.class);
                Sensor sensor=new Sensor();
                    sensor.setDevice_type("04");
                    sensor.setTarget_short_address(target_short_address);
                    sensor.setController_long_address(controller_long_address);
                sensor.setTarget_long_address(target_long_address);
                sensor.setTemp_air(String.valueOf(temp_1+0.1*temp_2));
                    sensor.setWetness(String.valueOf(wetness_i));
                    sensor.setValid_data(valid_data);

                    sensor.setIsUpdate(1);
                    if (LitePal.where("target_short_address = ?", target_short_address).find(Sensor.class).isEmpty()) {
                        sensor.setFlag(0);

                        sensor.save();
                    }else
                        sensor.updateAll("target_short_address = ?", target_short_address);

            }else if(device_type_.equals("0x05")){
                String controller_long_address = jsonObject.getString("controller_long_address");
                String target_short_address = jsonObject.getString("target_short_address");
                String target_long_address=jsonObject.getString("target_long_address");
                String temp1=valid_data.substring(2,4);
                String temp2=valid_data.substring(4,6);
                int temp_1=Integer.valueOf(temp1,16);
                int temp_2=Integer.valueOf(temp2,16);
                List<Sensor> sensorList = LitePal.where("target_short_address = ? and device_type = ?", target_short_address,"05").find(Sensor.class);
                Sensor sensor=new Sensor();
                    sensor.setDevice_type("05");
                    sensor.setTarget_short_address(target_short_address);
                    sensor.setController_long_address(controller_long_address);
                sensor.setTarget_long_address(target_long_address);
                sensor.setTemp_air(String.valueOf(temp_1+0.1*temp_2));
                    sensor.setValid_data(valid_data);
                    sensor.setIsUpdate(1);
                    if (LitePal.where("target_short_address = ?", target_short_address).find(Sensor.class).isEmpty()) {
                        sensor.setFlag(0);
                        sensor.save();
                    }else {
                        sensor.updateAll("target_short_address = ?", target_short_address);
                    }


            }else if(device_type_.equals("0x06")){
                String controller_long_address = jsonObject.getString("controller_long_address");
                String target_long_address=jsonObject.getString("target_long_address");

                String target_short_address = jsonObject.getString("target_short_address");
                String wetness=valid_data.substring(2,4);
                int wetness_i=Integer.valueOf(wetness,16);
                List<Sensor> sensorList = LitePal.where("target_short_address = ? and device_type = ?", target_short_address,"06").find(Sensor.class);
                Sensor sensor=new Sensor();
                sensor.setTarget_long_address(target_long_address);
                    sensor.setDevice_type("06");
                    sensor.setTarget_short_address(target_short_address);
                    sensor.setController_long_address(controller_long_address);
                    sensor.setWetness(String.valueOf(wetness_i));
                    sensor.setValid_data(valid_data);
                    sensor.setIsUpdate(1);
                    if (LitePal.where("target_short_address = ?", target_short_address).find(Sensor.class).isEmpty()) {
                        sensor.setFlag(0);
                        sensor.save();
                    }else {
                        sensor.updateAll("target_short_address = ?", target_short_address);
                    }


            }else if(device_type_.equals("0x07")){
                String controller_long_address = jsonObject.getString("controller_long_address");
                String target_long_address=jsonObject.getString("target_long_address");

                String target_short_address = jsonObject.getString("target_short_address");
                String smoking=valid_data.substring(0,2);
                int smoking_i=Integer.valueOf(smoking,16);
                List<Sensor> sensorList = LitePal.where("target_short_address = ? and device_type = ?", target_short_address,"07").find(Sensor.class);
                Sensor sensor=new Sensor();
                    sensor.setDevice_type("07");
                    sensor.setTarget_short_address(target_short_address);
                    sensor.setController_long_address(controller_long_address);
                sensor.setTarget_long_address(target_long_address);
                    sensor.setSmoking(String.valueOf(smoking_i));
                    sensor.setValid_data(valid_data);
                    sensor.setIsUpdate(1);
                    if (LitePal.where("target_short_address = ?", target_short_address).find(Sensor.class).isEmpty()) {
                        sensor.setFlag(0);
                        sensor.save();
                    }else
                        sensor.updateAll("target_short_address = ?", target_short_address);


            }else if(device_type_.equals("0x08")){
                String controller_long_address = jsonObject.getString("controller_long_address");
                String deep = jsonObject.getString("valid_data");
                String target_short_address = jsonObject.getString("target_short_address");
                List<Device> deviceList = LitePal.where("target_short_address = ? and device_type = ?", target_short_address,"08").find(Device.class);
                Device device = new Device();
                    device.setDevice_type("08");
                    device.setTarget_short_address(target_short_address);
                    device.setController_long_address(controller_long_address);
                    device.setData(jsonData);
                    device.setNetwork_flag("01");
                    device.setIsUpdate(1);
                    if (LitePal.where("target_short_address = ?", target_short_address).find(Device.class).isEmpty()) {
                        device.setFlag(0);
                        device.setUse(0);
                        device.save();
                    }else
                        device.updateAll("target_short_address = ?", target_short_address);

                //TODO 门锁
            }else if(device_type_.equals("0x09")){
                String controller_long_address = jsonObject.getString("controller_long_address");
                String deep = jsonObject.getString("valid_data");
                String target_short_address = jsonObject.getString("target_short_address");
                List<Device> deviceList = LitePal.where("target_short_address = ? and device_type = ?", target_short_address,"09").find(Device.class);
                Device device = new Device();

                    device.setDevice_type("09");

                    device.setTarget_short_address(target_short_address);
                    device.setController_long_address(controller_long_address);
                    device.setData(jsonData);
                device.setNetwork_flag("01");

                device.setIsUpdate(1);
                    if (LitePal.where("target_short_address = ?", target_short_address).find(Device.class).isEmpty()) {
                        device.setFlag(0);
                        device.setUse(0);
                        device.save();
                    }else
                        device.updateAll("target_short_address = ?", target_short_address);

                //TODO 门锁
            }
        }

    }
    public void parseJsonAndUpdateDatabase(String jsonData){

        JSONObject jsonObject=JSONObject.parseObject(jsonData);
        JSONArray sensors=jsonObject.getJSONArray("sensors");
        JSONArray jsonArray= JSON.parseArray(sensors.toString());
        int size=jsonArray.size();

        for (int i = 0; i < size; i++) {
            JSONObject jsonObject1=jsonArray.getJSONObject(i);
            List<Device> devices=LitePal.where("source_long_address = ?",jsonObject1.getString("source_long_address")).find(Device.class);
            Device device=new Device();
            if(devices.isEmpty()){
                String source_data=jsonObject1.getString("source_data");
                device.setNetwork_flag("01");
                device.setIsUpdate(1);
                device.setMisc(jsonObject1.getString("misc"));
                device.setFlag(0);
                device.setUse(0);
                device.save();
            }else{
                device.setIsUpdate(1);//关闭开放入网获得直接返回，还要加一个系统返回键的监听，要记得update为0防止下次没这个反而显示
                device.updateAll("source_long_address = ?",jsonObject1.getString("source_long_address"));
            }
        }}
    public void parseJsonAndUpdateDatabase(String jsonData, List<Map<String,String>> devicesList){
        Map<String,String> map=new HashMap<>();
        JSONObject jsonObject=JSONObject.parseObject(jsonData);
        JSONArray sensors=jsonObject.getJSONArray("sensors");
        JSONArray jsonArray= JSON.parseArray(sensors.toString());
        int size=jsonArray.size();

        for (int i = 0; i < size; i++) {

            JSONObject jsonObject1=jsonArray.getJSONObject(i);
            map.put("source_short_address",jsonObject1.getString("source_short_address"));
            map.put("source_long_address",jsonObject1.getString("source_long_address"));
            map.put("network_flag",jsonObject1.getString("network_flag"));
            map.put("source_command",jsonObject1.getString("source_command"));//设备类型
            map.put("source_data",jsonObject1.getString("source_data"));
            map.put("misc",jsonObject1.getString("misc"));
            Device device=new Device();
            device.setUse(0);
            device.setMisc(jsonObject1.getString("misc"));
            device.setFlag(0);
            boolean a=device.isSaved();

            if(device.isSaved())
                return;
            device.save();
            devicesList.add(map);



        }
    }
    public static String hexToChinese(String hexString) {
        byte[] bytes = new byte[hexString.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hexString.substring(2 * i, 2 * i + 2), 16);
        }
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

}
