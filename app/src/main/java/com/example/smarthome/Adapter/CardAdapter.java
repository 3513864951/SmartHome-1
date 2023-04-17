package com.example.smarthome.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Database.Home;
import com.example.smarthome.Page_Huiju.AddHome;
import com.example.smarthome.R;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private Context mContext; // 声明一个上下文对象
    private List<Home> mList = new ArrayList<>();
    AddSenseAdapter.OnItemClickListener onItemClickListener;//声明接口对象
    public void set0nItemClickListener(AddSenseAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;//传递接口
    }
    public interface OnItemClickListener{
        void OnItemClick(View view,int position);//设置接口
    }

    public CardAdapter(List<Home> List) {
        mList =List;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_smart, parent, false);
        ViewHolder  viewHolder = new ViewHolder(itemView);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext().getApplicationContext(), AddHome.class);
                String  homename = viewHolder.r_name.getText().toString().trim();
                intent.putExtra("homename",homename);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                parent.getContext().startActivity(intent);
            }
        });
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,  int position) {
        Home home=mList.get(position);
        holder.r_name. setText(home.getHomename());
        holder.itemView.setOnClickListener(v -> {
            onItemClickListener.OnItemClick(v,position);
        });
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView r_name;
        View view;
        public ViewHolder(final View itemView) {
            super(itemView);
            r_name=(TextView) itemView.findViewById(R.id.tv_name);
            view=itemView;


        }

    }
    @Override
    public int getItemCount() {
        return mList.size();
    }
}
