package com.example.smarthome.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Database.Sensor;
import com.example.smarthome.R;
import com.example.smarthome.Scene.SensorCondition;

import java.util.ArrayList;
import java.util.List;

public class SceneSensorAdaptor extends RecyclerView.Adapter<SceneSensorAdaptor.ViewHolder> {
    List<Sensor> sensorList=new ArrayList<>();

    public SceneSensorAdaptor (List<Sensor> sensorList){
        this.sensorList=sensorList;
    }



    public class ViewHolder extends RecyclerView.ViewHolder  {

        ImageView image;
        TextView name,target_long_address;
        View sceneView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sceneView = itemView;
            image = sceneView.findViewById(R.id.image);
            name = sceneView.findViewById(R.id.name);
            target_long_address=sceneView.findViewById(R.id.target_long_address);
        }
    }
    @NonNull
    @Override
    public SceneSensorAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.scenesensorlist,parent,false);
        final SceneSensorAdaptor.ViewHolder holder=new SceneSensorAdaptor.ViewHolder(view);
        holder.sceneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(parent.getContext(), SensorCondition.class);
                intent.putExtra("device_type",sensorList.get(holder.getAdapterPosition()).getDevice_type());
                intent.putExtra("target_long_address",sensorList.get(holder.getAdapterPosition()).getTarget_long_address());
                parent.getContext().startActivity(intent);
            }
        });
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull SceneSensorAdaptor.ViewHolder holder, int position) {
        String device_type=sensorList.get(holder.getAdapterPosition()).getDevice_type();
        String target_long_address=sensorList.get(holder.getAdapterPosition())
                .getController_long_address();
        switch (device_type){
            //TODO 不显示空调的
            case "05":
                holder.name.setText("独立温度传感器");
                holder.target_long_address.setText(target_long_address);
                break;
            case "06":
                holder.name.setText("独立湿度传感器");
                holder.target_long_address.setText(target_long_address);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return sensorList.size();
    }
}
