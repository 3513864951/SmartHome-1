package com.example.smarthome.Adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adapter.base.CommonAdapter;
import com.example.smarthome.Adapter.base.ViewHolder;

import com.example.smarthome.Database.AddModel;

import com.example.smarthome.Database.Device;
import com.example.smarthome.R;
import com.example.smarthome.View.pullextend.ExtendListHeader;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Renny on 2018/1/3.
 */

public class ExtendHeadAdapter extends RecyclerView.Adapter<ExtendHeadAdapter.ViewHolder> {
    private Context mContext; // 声明一个上下文对象
    private List<Device> deviceList=new ArrayList<>(); // 声明一个信息列表w
    private List<Integer> idList=new ArrayList<>();
    OnItemClickListener onItemClickListener;//声明接口对象
    RecyclerAdapter.OnItemLongClickListener onItemLongClickListener;
    private static int count=1;
    private static int size=1;

    public void set0nItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;//传递接口
    }
    public interface OnItemClickListener{
        void OnItemClick(View view,int position);//设置接口
    }
    public void setDeviceId(List<Integer> idList){
        this.idList=idList;
        size=idList.size();
    }
    public ExtendHeadAdapter(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(parent.getContext().getApplicationContext(), ExtendListHeader.class);
//                String  device = viewHolder.d_device.getText().toString().trim();
//                intent.putExtra("device", device);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                parent.getContext().startActivity(intent);
//                关于Activity跳转，Context中有一个startActivity方法，
//                Activity继承自Context，重载了startActivity方法。
//                如果使用Activity的startActivity方法，不会有任何限制，
//                而如果使用Context的startActivity方法的話，就需要开启一个新的的task，
//                遇到这个异常，是因为使用了Context的startActivity方法。解决办法是，加一个flag。
            }
        });
        viewHolder.device_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return viewHolder;
    }


    public  interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }

    @Override
    public void onBindViewHolder(@NonNull ExtendHeadAdapter.ViewHolder holder, int position) {
        Device device=deviceList.get(position);
        holder.d_device.setText(deviceList.get(0).getName());
        holder.device_category_display.setText(deviceList.get(position).getTarget_long_address());
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView d_device,device_category_display;
        View view;
        CardView device_reject;
        CardView device_approve;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            d_device=(TextView) itemView.findViewById(R.id.device_category_display);
            view=itemView;
            device_approve=view.findViewById(R.id.device_approve);
            device_reject=view.findViewById(R.id.device_reject);
            device_category_display=view.findViewById(R.id.device_category_display);
        }
    }
    @Override
    public int getItemCount() {
        if (deviceList.size()>=1)
            return 1;
        else
            return 0;
    }
}