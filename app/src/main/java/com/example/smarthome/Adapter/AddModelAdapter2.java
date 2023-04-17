package com.example.smarthome.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Database.AddModel;
import com.example.smarthome.Database.Home;

import com.example.smarthome.R;
import com.example.smarthome.Scene.More;

import java.util.List;

public class AddModelAdapter2 extends RecyclerView.Adapter<AddModelAdapter2.ViewHolder> {
    private Context mContext; // 声明一个上下文对象
    private List<AddModel> list; // 声明一个信息列表w
    OnItemClickListener onItemClickListener;//声明接口对象
    RecyclerAdapter.OnItemLongClickListener onItemLongClickListener;
    public void set0nItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;//传递接口
    }
    public interface OnItemClickListener{
        void OnItemClick(View view,int position);//设置接口
    }

    public AddModelAdapter2(List<AddModel> modelList) {
        list = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_medal, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent.getContext().getApplicationContext(), More.class);
                String  model = viewHolder.m_model.getText().toString().trim();
                intent.putExtra("model", model);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                parent.getContext().startActivity(intent);
            }
        });

        return viewHolder;
    }
    public  interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }

    @Override
    public void onBindViewHolder(@NonNull AddModelAdapter2.ViewHolder holder, int position) {
        AddModel addModel=list.get(position);
        holder.m_model.setText(addModel.getModel());
        holder.itemView.setOnClickListener(v -> {
            onItemClickListener.OnItemClick(v,position);
        });

    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView m_model;
        View view;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            m_model=(TextView) itemView.findViewById(R.id.medal_tv);
            view=itemView;

        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}