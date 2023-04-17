package com.example.smarthome.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.smarthome.Page_Home.HomeFragment;
import com.example.smarthome.Page_Huiju.HuijuFrament;
import com.example.smarthome.Page_Samrt.SmartFragment;
import com.example.smarthome.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomSmartHome extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
/*注解：getWindow().getDecorView()拿到当前活动DecorView，
再setSystemUiVisibility()改变系统UI的显示
View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN和View.SYSTEM_UI_FLAG_LAYOUT_STABLE
就表示活动的布局会显示在状态栏上面，再将状态栏设置为透明色setStatusBarColor()*/

        setContentView(R.layout.activity_bottom_smart_home);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        bottomNavigationView=findViewById(R.id.smart_home);
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.framentConntaint,new HuijuFrament()).commit();
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment=null;
                switch ( item.getItemId() ) {
                    case R.id.navigation_home:

                        fragment = new HuijuFrament();
                        break;
                    case R.id.navigation_dashboard:
                        fragment = new SmartFragment();

                        break;
                    case R.id.navigation_notifications:
                        fragment = new HomeFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.framentConntaint,fragment).commit();
                return true;

            }
        });



    }
}