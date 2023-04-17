package com.example.smarthome.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Database.AddHomes;
import com.example.smarthome.Page_Huiju.AddHome;

import com.example.smarthome.R;

import java.util.List;

public class AddHomesAdapter extends RecyclerView.Adapter<AddHomesAdapter.ViewHolder> {
    private Context mContext; // 声明一个上下文对象
    private List<AddHomes> list; // 声明一个信息列表w
    OnItemClickListener onItemClickListener;//声明接口对象
    RecyclerAdapter.OnItemLongClickListener onItemLongClickListener;
    public void set0nItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;//传递接口
    }
    public interface OnItemClickListener{
        void OnItemClick(View view,int position);//设置接口
    }

    public AddHomesAdapter(List<AddHomes> homesList) {
        list = homesList;
    }
    public void setContext(Context context){
        mContext=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_smart, parent, false);
        ViewHolder holder = new ViewHolder(view);


        return holder;
    }
    public  interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }

    @Override
    public void onBindViewHolder(@NonNull AddHomesAdapter.ViewHolder holder, int position) {
        AddHomes addHomes=list.get(position);
        holder.h_home.setText(addHomes.getHome());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AddHome.class);
                intent.putExtra("time", addHomes.getTime());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
        holder.h_home.setText(list.get(position).getHome());
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView h_home;
        View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            h_home=(TextView) itemView.findViewById(R.id.tv_name);
            view=itemView;

        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}