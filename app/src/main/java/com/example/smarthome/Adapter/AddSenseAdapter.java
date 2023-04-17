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
import com.example.smarthome.Database.AddSense;
import com.example.smarthome.Page_Samrt.Add_Sense;

import com.example.smarthome.R;

import java.util.List;

public class AddSenseAdapter extends RecyclerView.Adapter<AddSenseAdapter.ViewHolder> {
    private Context mContext; // 声明一个上下文对象
    private List<AddSense> list; // 声明一个信息列表w
    OnItemClickListener onItemClickListener;//声明接口对象



    public void set0nItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;//传递接口
    }
    public interface OnItemClickListener{
        void OnItemClick(View view,int position);//设置接口
    }

    public AddSenseAdapter(List<AddSense> senseList) {
        list = senseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_sense, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent.getContext().getApplicationContext(), Add_Sense.class);
                String  sense = viewHolder.s_sense.getText().toString().trim();
                intent.putExtra("sense", sense);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                parent.getContext().startActivity(intent);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddSenseAdapter.ViewHolder holder, int position) {
        AddSense addSense=list.get(position);
        holder.s_sense.setText(addSense.getSense_name());
        holder.itemView.setOnClickListener(v -> {
            onItemClickListener.OnItemClick(v,position);
        });

    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView s_sense;
        View view;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            s_sense=(TextView) itemView.findViewById(R.id.sense_tv);
            view=itemView;

        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}
