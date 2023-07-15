package com.example.smarthome.Page_Samrt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.biometric.BiometricPrompt;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.smarthome.Adapter.ManageAdaptor;
import com.example.smarthome.MQTT.ClientMQTT;
import com.example.smarthome.R;
import com.example.smarthome.Scene.More;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.concurrent.Executor;
/**
 * @description 门锁
 */
public class Monitoring extends AppCompatActivity {
    CardView open_the_door,close_the_door;
    ClientMQTT clientMQTT;
    String target_short_address;
    Toolbar voice_tb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);
        open_the_door=findViewById(R.id.open_the_door);
        close_the_door=findViewById(R.id.close_the_door);
        voice_tb=findViewById(R.id.voice_tb);
        Intent intent=getIntent();
        target_short_address=intent.getStringExtra(ManageAdaptor.TARGET_SHORT_ADDRESS);
        clientMQTT=new ClientMQTT("light");
        try {
            clientMQTT.Mqtt_innit();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        clientMQTT.startReconnect(Monitoring.this);
        voice_tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        open_the_door.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BiometricPrompt.PromptInfo promptInfo=new BiometricPrompt.PromptInfo.Builder()
                        .setTitle("请核实")
                        .setDescription("需要用户验证身份信息")
                        .setNegativeButtonText("取消")
                        .build();
                getPrompt().authenticate(promptInfo);

            }
        });
        close_the_door.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BiometricPrompt.PromptInfo promptInfo=new BiometricPrompt.PromptInfo.Builder()
                        .setTitle("请核实")
                        .setDescription("需要用户验证身份信息")
                        .setNegativeButtonText("取消")
                        .build();
                getPrompt().authenticate(promptInfo);

            }
        });

    }

    private BiometricPrompt getPrompt(){
        Executor executor=ContextCompat.getMainExecutor(this);
        BiometricPrompt.AuthenticationCallback callback=new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                notifyUser(errString.toString());
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                notifyUser("验证成功");
                clientMQTT.publishMessagePlus(null,"0x"+target_short_address,"0x08","0x01","0x01");
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                notifyUser("验证失败");
                clientMQTT.publishMessagePlus(null,"0x"+target_short_address,"0x08","0x00","0x01");
            }

        };
        BiometricPrompt biometricPrompt=new BiometricPrompt(this,executor,callback);
          return biometricPrompt;
}
    private void notifyUser(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();

    }
}