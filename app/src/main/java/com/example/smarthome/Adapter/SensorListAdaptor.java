package com.example.smarthome.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Database.Scene.Scene;
import com.example.smarthome.Database.Sensor;
import com.example.smarthome.R;
import com.example.smarthome.View.SelfTextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SensorListAdaptor  extends RecyclerView.Adapter<SensorListAdaptor.ViewHolder>{
    private SensorListAdaptor.OnItemClickListener mItemClickListener;
    private List<Sensor> mSensorList;

    public SensorListAdaptor(List<Sensor> mSensorList){
        this.mSensorList=mSensorList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView air_image;
        TextView air_name,sensor_display1,sensor_display2,symbol1,symbol2;
        SelfTextView tv1,tv2;
        View sensorView;
        ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sensorView=itemView;
            sensorView.setOnClickListener(this);
            air_name=sensorView.findViewById(R.id.sensor_name);
            sensor_display1=sensorView.findViewById(R.id.sensor_display1);
            sensor_display2=sensorView.findViewById(R.id.sensor_display2);
            symbol1=sensorView.findViewById(R.id.symbol1);
            symbol2=sensorView.findViewById(R.id.symbol2);
            tv1=sensorView.findViewById(R.id.tv1);
            tv2=sensorView.findViewById(R.id.tv2);
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
    public SensorListAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sensorlist,parent,false);
        final SensorListAdaptor.ViewHolder holder=new SensorListAdaptor.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SensorListAdaptor.ViewHolder holder, int position) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date=new Date(System.currentTimeMillis());
        Sensor sensor=new Sensor();
        String time1=simpleDateFormat.format(date);
        sensor.setTime(time1);
        sensor.update(mSensorList.get(holder.getAdapterPosition()).getId());
        String device_type=mSensorList.get(holder.getAdapterPosition()).getDevice_type();
        switch (device_type){
            case ("04"):
            //
            holder.tv1.setmTitleText(Float.valueOf(mSensorList.get(holder.getAdapterPosition()).getTemp_air()),1);
            holder.tv2.setmTitleText(Float.valueOf(mSensorList.get(holder.getAdapterPosition()).getWetness()),2);
                break;
            case ("05"):
            holder.tv2.setVisibility(View.INVISIBLE);
            holder.tv1.setmTitleText(Float.valueOf(mSensorList.get(holder.getAdapterPosition()).getTemp_air()),2);
            holder.symbol2.setVisibility(View.INVISIBLE);
                break;
            case ("06"):
                holder.sensor_display1.setText("湿度：");
                holder.tv2.setVisibility(View.INVISIBLE);
                holder.symbol1.setText("%");
                holder.tv1.setmTitleText(Float.valueOf(mSensorList.get(holder.getAdapterPosition()).getWetness()),2);
                holder.symbol2.setVisibility(View.INVISIBLE);
                break;
                 case ("07"):
                     holder.sensor_display1.setText("烟雾浓度：");
                     holder.tv2.setVisibility(View.INVISIBLE);
                     holder.tv1.setmTitleText(Float.valueOf(mSensorList.get(holder.getAdapterPosition()).getSmoking()),2);
                     holder.symbol1.setText("%");
                     holder.symbol2.setVisibility(View.INVISIBLE);
                      break;
        }


    }

    @Override
    public int getItemCount() {
        return mSensorList.size();
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
    public void setOnItemClickListener(SensorListAdaptor.OnItemClickListener listener) {
        mItemClickListener = listener;
    }
    
}
