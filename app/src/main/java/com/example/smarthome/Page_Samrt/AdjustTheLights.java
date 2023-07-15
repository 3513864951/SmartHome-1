package com.example.smarthome.Page_Samrt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.smarthome.Adapter.ManageAdaptor;
import com.example.smarthome.Adapter.ViewPagerAdapter;
import com.example.smarthome.MQTT.ClientMQTT;
import com.example.smarthome.R;
import com.example.smarthome.View.StepSeekBar;
import com.example.smarthome.View.SwapTb.SortViewPagerAdapter;
import com.example.smarthome.View.SwapTb.SpringView;
import com.example.smarthome.View.SwapTb.ViewPagerIndicator;
import com.github.iielse.switchbutton.SwitchView;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.ArrayList;
import java.util.List;


public class AdjustTheLights extends AppCompatActivity {
    public static final String CONTROLLER_LONG_ADDRESS = "controller_long_address";
    public static final String TARGET_LONG_ADDRESS = "target_long_address";
    public static final String TARGET_SHORT_ADDRESS = "target_short_address";
    public static final String NETWORK_FLAG = "network_flag";
    public static final String DEVICE_TYPE = "device_type";
    Toolbar lights_tb;
    private    ClientMQTT clientMQTT;
    private Button open_light;
    private Button shut_light;
    private Spinner spinner_model;
    private Spinner spinner_home;
    private String target_short_address;
    private StepSeekBar stepSeekBar;
    private ViewPager viewPager;
    private ViewPagerIndicator indicator;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mList;
    private List<String> mDatas;
    private int itemCount = 4;
    SwitchView light_breathe;
    SpringView springView;


    View view1;
    View view2;
    View view3;
    View view4;

    int currentPosition = 0;
    int endPosition;
    int beginPosition;

    TextView[] tvs = new TextView[4];
    private TextView tab_new, tab_hot, tab_free, tab_member;
    private float item_width;
    private int screenWidth;
    List<View> viewList=new ArrayList<>();
    int indicatorColorId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_the_lights);
        viewPager = (ViewPager) findViewById(R.id.vp);

        LayoutInflater inflater = LayoutInflater.from(this);
        view1 = inflater.inflate(R.layout.vp1, null);
        view2 = inflater.inflate(R.layout.vp1, null);
        view3 = inflater.inflate(R.layout.vp1, null);
        view4 = inflater.inflate(R.layout.vp1, null);
        indicator = (ViewPagerIndicator) findViewById(R.id.indicator);
        mList = new ArrayList<Fragment>();
        for (int i = 0; i < itemCount; i++) {
            Fragment fragment = new MeFragment();
            mList.add(fragment);
        }

        mDatas = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            mDatas.add("灯" + i);
        }

        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
        Intent intent = getIntent();
        String target_long_address = intent.getStringExtra(ManageAdaptor.TARGET_LONG_ADDRESS);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(viewList, target_long_address);
        viewPagerAdapter.setContext(AdjustTheLights.this);
        viewPager.setAdapter(viewPagerAdapter);
        //将viewpager与indicator绑定
        indicator.setDatas(mDatas);
        indicator.setViewPager(viewPager, 4);

    }
}