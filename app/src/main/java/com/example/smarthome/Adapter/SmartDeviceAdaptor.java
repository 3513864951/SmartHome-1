package com.example.smarthome.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Database.Scene.S_Device;
import com.example.smarthome.R;
import com.example.smarthome.Scene.SmartDevice.Set_air;
import com.example.smarthome.Scene.SmartDevice.Set_curtain;
import com.example.smarthome.Scene.SmartDevice.Set_lights;

import org.fusesource.mqtt.codec.PUBACK;

import java.util.ArrayList;
import java.util.List;

public class SmartDeviceAdaptor extends RecyclerView.Adapter<SmartDeviceAdaptor.ViewHolder> {
    private ConAndMissAdaptor.OnItemClickListener mItemClickListener;
    private List<S_Device> s_deviceList=new ArrayList<>();
    private String deviceType;

    public SmartDeviceAdaptor(List<S_Device> s_deviceList){
        this.s_deviceList=s_deviceList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView itemName;
        TextView deviceJudge;
        View deviceView;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deviceView=itemView;
            deviceView.setOnClickListener(this);
            imageView=deviceView.findViewById(R.id.iv_display);
            itemName=deviceView.findViewById(R.id.tv_display);
            deviceJudge=deviceView.findViewById(R.id.justForDeviceJudge);

        }

        @Override
        public void onClick(View v) {
            if(mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());

            }
        }
    }

    @NonNull
    @Override
    public SmartDeviceAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.conandmimsslist,parent,false);
        final SmartDeviceAdaptor.ViewHolder holder=new SmartDeviceAdaptor.ViewHolder(view);
        holder.deviceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String target_long_address=s_deviceList.get(holder.getAdapterPosition()).getTarget_long_address();
                deviceType=(holder.deviceJudge.getText().toString());
                switch (deviceType){

                    case "01":
                        Intent intent=new Intent(parent.getContext(), Set_lights.class);
                        intent.putExtra("target_long_address",target_long_address);
                        parent.getContext().startActivity(intent);
                        break;
                    case "02":
                        Intent intent1=new Intent(parent.getContext(), Set_air.class);
                        intent1.putExtra("target_long_address",target_long_address);
                        parent.getContext().startActivity(intent1);
                        break;
                    case "03":
                        Intent intent2=new Intent(parent.getContext(), Set_curtain.class);
                        intent2.putExtra("target_long_address",target_long_address);
                        parent.getContext().startActivity(intent2);
                        break;
                    default:
                        Toast.makeText(parent.getContext(),"有设备但是不显示捏。。。BUG...GG",Toast.LENGTH_SHORT).show();
                }
            }
        });



        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SmartDeviceAdaptor.ViewHolder holder, int position) {
        holder.itemName.setText(s_deviceList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    // 定义 Item 点击事件的监听器接口
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    // 定义 Item 长按事件的监听器接口
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    // 设置 Item 点击事件监听器
    public void setOnItemClickListener(ConAndMissAdaptor.OnItemClickListener listener) {
        mItemClickListener = listener;
    }
}
