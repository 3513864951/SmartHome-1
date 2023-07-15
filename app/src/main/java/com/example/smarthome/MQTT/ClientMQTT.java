package com.example.smarthome.MQTT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.smarthome.Json.JsonString;
import com.example.smarthome.Json.ParseJson;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.fusesource.hawtdispatch.Dispatch;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.codec.MessageSupport;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientMQTT {

    private ScheduledExecutorService scheduler;
    private MQTT mqtt;
    private MqttClient client;
    private MqttClient mqttClient;
    private Handler handler;
    private MqttConnectOptions options;
    private MqttConnectOptions options1;
    private String topic="light";
    /**
     * @description 用来判断是否是获取已经链接的设备还是发现设备
     * 0 为发现设备 1 为更新设备
     */
    private int flag=0;
    private static final String userName = "ESP32-C3-username";
//    private static final String password = "ESP32-C3-password";
    private static final String password = "sUFhMRm3FUx6RqhKWYnRQBGQF6y1YZYHLsrVtaoYKA2GrPu9";
    public  static final String serverURI="tcp://home.towo.eu.org:21883";
    //TODO networkFlag
    private String device_id=MqttClient.generateClientId();

    private String topicName;
    private final MemoryPersistence memoryPersistence=new MemoryPersistence();
    public ClientMQTT( String topicName) {
        this.topicName = topicName;
    }

    public String getdevice_id() {
        return device_id;
    }

    public void setdevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getTopicName() {
        return topicName;
    }
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
    public void setFlag(int flag){
        this.flag=flag;
    }
    public void Mqtt_innit() throws MqttException {
        client=new MqttClient(serverURI,device_id,memoryPersistence);
        options=new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(false);
        //设置连接的用户名
        options.setUserName(userName);
        //设置连接的密码
        options.setPassword(ClientMQTT.password.toCharArray());
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(10);
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
        options.setKeepAliveInterval(20);
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("连接失败----------");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String jsonData=new String(message.getPayload());
                ParseJson parseJson=new ParseJson();
                Log.i("数据到达!",jsonData);
                parseJson.ParseJsonData(jsonData,flag);
                System.out.println("数据到达----------");
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("发送成功----------");
            }
        });
    }
    public void Mqtt_connect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(!(client.isConnected()))
                    {

                        options.setUserName(userName);
                        //设置连接的密码
                        options.setPassword(ClientMQTT.password.toCharArray());
                        client.connect(options);
//                        client.subscribe("ESP32toAPP");//订阅、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、
                        Message message=new Message();
                        message.what=31;
                        handler.sendMessage(message);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Message message=new Message();
                    message.what=30;
                    handler.sendMessage(message);
                    //666
                }
            }
        }).start();


    }
    public void startReconnect(Context context){
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (!client.isConnected()) {
                    Mqtt_connect();
                }
            }
        }, 0*1000, 10 * 1000, TimeUnit.MILLISECONDS);
        handler = new Handler(Looper.myLooper()) {
            @Override
            @SuppressLint("SetTextI18n")
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1: //开机校验更新回传
                        break;
                    case 2:  // 反馈回传

                        break;
                    case 3:  //MQTT 收到消息回传   UTF8Buffer msg=new UTF8Buffer(object.toString());

                        System.out.println(msg.obj.toString());   // 显示MQTT数据
                        break;
                    case 30:  //连接失败
                        Toast.makeText(context,"连接失败" ,Toast.LENGTH_SHORT).show();
                        break;
                    case 31:   //连接成功
//                        Toast.makeText(context,"连接成功" ,Toast.LENGTH_SHORT).show();
                        try {
                            client.subscribe("ESP32toAPP",0);
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
            }
        };

    }
    public void Subscribe(Context context1){
        try {
//            client.subscribe("ESP32toAPP",0);
 mqttClient=new MqttClient(serverURI,device_id,memoryPersistence);;
 options1=new MqttConnectOptions();

            options1=new MqttConnectOptions();
            options1.setAutomaticReconnect(true);
            options1.setCleanSession(false);
            //设置连接的用户名
            options1.setUserName(userName);
            //设置连接的密码
            options1.setPassword(ClientMQTT.password.toCharArray());
            // 设置超时时间 单位为秒
            options1.setConnectionTimeout(10);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options1.setKeepAliveInterval(20);
            if (!mqttClient.isConnected()) {
                MqttConnectOptions finalOptions = options1;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if(!(mqttClient.isConnected()))
                            {

                                finalOptions.setUserName(userName);
                                //设置连接的密码
                                finalOptions.setPassword(ClientMQTT.password.toCharArray());
                                mqttClient.connect(finalOptions);
//                                mqttClient.subscribe("ESP32toAPP",0);
                                MqttMessage message = new MqttMessage();
                                message.setPayload("666".getBytes());
                                mqttClient.publish("APPtoESP32",message);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }
    //为活动专门设计的，配置多个而不会重复接收数据
public void publishMessagePlusForActivity(String misc,String target_short_address,String device_type,String valid_data,String valid_data_length){
    // 设置系统默认时区为 CST（即中国标准时间）
    TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
// 获取当前北京时间
    LocalDateTime localDateTime = LocalDateTime.now();
// 使用 ISO-8601 格式化器将其转换为字符串
    String timestamp = DateTimeFormatter.ISO_DATE_TIME.format(localDateTime);
    String controller_long_address="79482B27004B1200";
    if (mqttClient == null || !client.isConnected()) {
        return;
    }
    MqttMessage message = new MqttMessage();
    JsonString jsonString=new JsonString(timestamp,controller_long_address,device_id,misc,target_short_address,device_type,valid_data,valid_data_length);
    message.setPayload(jsonString.toString().getBytes());
    try {
        mqttClient.publish("APPtoESP32",message);//上传信息
    } catch (MqttException e) {

        e.printStackTrace();
    }
}

    public void unSubscribe(){
        try {
            mqttClient.unsubscribe("ESP32toAPP");

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    //这边的map改成List<Map<String,String>>，毕竟设备不只是一个
    public void publishMessagePlus(String misc,String target_short_address,String device_type,String valid_data,String valid_data_length)
    {
//        LocalDateTime localDateTime = LocalDateTime.now();
//        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ISO_DATE_TIME;
//        String timestamp=dateTimeFormatter.format(localDateTime);
//        // 设置系统默认时区为 CST（即中国标准时间）
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
// 获取当前北京时间
        LocalDateTime localDateTime = LocalDateTime.now();
// 使用 ISO-8601 格式化器将其转换为字符串
        String timestamp = DateTimeFormatter.ISO_DATE_TIME.format(localDateTime);
        String controller_long_address="79482B27004B1200";
        if (client == null || !client.isConnected()) {
            return;
        }
        MqttMessage message = new MqttMessage();
        JsonString jsonString=new JsonString(timestamp,controller_long_address,device_id,misc,target_short_address,device_type,valid_data,valid_data_length);
        message.setPayload(jsonString.toString().getBytes());
        Log.e("688888",jsonString.toString());
        try {
            client.publish("APPtoESP32",message);//上传信息
        } catch (MqttException e) {

            e.printStackTrace();
        }
    }
    public void closeContact() throws MqttException {
        client.close();
    }

}
