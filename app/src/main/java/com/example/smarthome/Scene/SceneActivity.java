package com.example.smarthome.Scene;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adapter.SceneAdaptor;
import com.example.smarthome.Database.Scene.Scene;
import com.example.smarthome.R;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
/**
 * @description 弃用
 */
public class SceneActivity extends AppCompatActivity {
    private List<Scene> sceneList=new ArrayList<>();
    private RecyclerView recyclerView;
    private Toolbar scene_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sceneactivity);
        scene_back=findViewById(R.id.scene_back);
        recyclerView=findViewById(R.id.recy_scene);
        initListener();
        initRecyclerView();
        initSceneRecyclerView();
    }

    private void initRecyclerView() {
        initContent();
    }
    private void initContent(){
    }
    private void initListener(){
        scene_back.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void initSceneRecyclerView(){
        sceneList.clear();
        sceneList=LitePal.findAll(Scene.class);
        LinearLayoutManager layoutManager=new LinearLayoutManager(SceneActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        SceneAdaptor sceneAdaptor=new SceneAdaptor(sceneList);
        recyclerView.setAdapter(sceneAdaptor);
        sceneAdaptor.notifyDataSetChanged();
    }
}
