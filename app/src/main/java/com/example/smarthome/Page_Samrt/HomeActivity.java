package com.example.smarthome.Page_Samrt;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adapter.HomeAdaptor;
import com.example.smarthome.Database.Home;
import com.example.smarthome.Database.Helper.AddHomeHelper;
import com.example.smarthome.R;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
/**
 * @description 弃用
 */
public class HomeActivity extends AppCompatActivity {
    private ArrayList<AddHomeHelper> addHomeHelperArrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeactivity);
        //弄完
        initView();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recy_home);
        LinearLayoutManager linearLayout = new LinearLayoutManager(HomeActivity.this);
        recyclerView.setLayoutManager(linearLayout);
        HomeAdaptor homeAdaptor = new HomeAdaptor(addHomeHelperArrayList);
        recyclerView.setAdapter(homeAdaptor);
        homeAdaptor.notifyDataSetChanged();
            }

            private void initView(){
                addHomeHelperArrayList.clear();
                List<Home> homelist = LitePal.order("id desc").find(Home.class);
                if(!homelist.isEmpty())
                    for(Home home: homelist){
//                        String title=home.getTitle();
//                        Map<String,String> map=new HashMap<>();
//                        map.put("title",home.getTitle());
//                        homeList.add(map);
//                        AddHomeHelper addHomeHelper=new AddHomeHelper(1,home.getTitle());
//                        addHomeHelperArrayList.add(addHomeHelper);
                    }


            }


}
