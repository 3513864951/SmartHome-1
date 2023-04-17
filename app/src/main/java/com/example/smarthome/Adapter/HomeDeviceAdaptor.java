package com.example.smarthome.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Database.Device;
import com.example.smarthome.R;

import java.util.ArrayList;
import java.util.List;

public class HomeDeviceAdaptor extends RecyclerView.Adapter<HomeDeviceAdaptor.ViewHolder>{
    private List<Device> deviceList=new ArrayList<>();
    private List<Device> chooseDevice=new ArrayList<>();
    public List<Device> getDevices(){
        return chooseDevice;
    }
    public void setDevices(List<Device> devices){
            this.chooseDevice=devices;
    }
    public HomeDeviceAdaptor( List<Device> deviceList){
        this.deviceList=deviceList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView imageView;
        CheckBox checkBox;
        View view;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.name);
            imageView=itemView.findViewById(R.id.image);
            checkBox=itemView.findViewById(R.id.checkBox);
            view=itemView;

        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homedevicelist, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeDeviceAdaptor.ViewHolder holder, int position) {
        String device_type=deviceList.get(holder.getAdapterPosition()).getDevice_type();
        String name=deviceList.get(holder.getAdapterPosition()).getName();
        String target_long_address=deviceList.get(holder.getAdapterPosition()).getTarget_long_address();
        switch (device_type){
            case "01":
                holder.imageView.setImageResource(R.drawable.adjust_lights);
                if(name!=null)
                    holder.name.setText(name);
                else
                    holder.name.setText(target_long_address);
                break;
            case "02":
                holder.imageView.setImageResource(R.drawable.air_condition_smart);
                if(name!=null)
                    holder.name.setText(name);
                else
                    holder.name.setText(target_long_address);
                break;
            case "03":
                holder.imageView.setImageResource(R.drawable.curtain_smart);
                if(name!=null)
                    holder.name.setText(name);
                else
                    holder.name.setText(target_long_address);
                break;
        }
    holder.checkBox.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(holder.checkBox.isChecked())
                chooseDevice.add(deviceList.get(holder.getAdapterPosition()));
            else
                chooseDevice.remove(deviceList.get(holder.getAdapterPosition()));
        }
    });
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }
}
