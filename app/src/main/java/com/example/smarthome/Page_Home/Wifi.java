package com.example.smarthome.Page_Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.nsd.WifiP2pUpnpServiceRequest;
import android.os.Bundle;

import com.example.smarthome.R;

import java.util.List;
/**
 * @description 弃用
 */
public class Wifi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
//        WifiManager wifiManager=(WifiManager) getSystemService(Context.WIFI_SERVICE);
//        List<ScanResult> scanResultList=wifiManager.getScanResults();
//        WifiP2pUpnpServiceRequest wifiP2pUpnpServiceRequest
//                wifiP2pUpnpServiceRequest

    }
}