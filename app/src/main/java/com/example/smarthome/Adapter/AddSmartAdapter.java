package com.example.smarthome.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Database.Helper.AddSmartHelper;
import com.example.smarthome.R;

import java.util.ArrayList;

public class AddSmartAdapter extends RecyclerView.Adapter <AddSmartAdapter.AddSmartHolder> {
    ArrayList<AddSmartHelper> addSmartHelpers;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public AddSmartAdapter(ArrayList<AddSmartHelper> addSmartHelpers) {
        this.addSmartHelpers = addSmartHelpers;
    }

    @NonNull
    @Override
    public AddSmartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_smart, parent, false);
        AddSmartHolder addSmartHolder = new AddSmartHolder(view);
        return addSmartHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddSmartHolder holder, int position) {
        AddSmartHelper addSmartHelper = addSmartHelpers.get(position);
        holder.imageView.setImageResource(addSmartHelper.getImage());
        holder.title.setText(addSmartHelper.getTitle());
        if(onItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=holder.getLayoutPosition();//1
                    onItemClickListener.OnItemClickListener(holder.itemView,position);//2
                }
            });
        }
        if(onItemLongClickListener!=null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position=holder.getLayoutPosition();
                    onItemLongClickListener.onItemLongClick(holder.itemView,position);
                    return true;//返回true表示消耗了事件不会继续传递啦！
                }
            });
        }

    }

    public static class AddSmartHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        public AddSmartHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.add_im);
            title = itemView.findViewById(R.id.add_tv);
        }

    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener=onItemLongClickListener;
    }
    public  interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }
    public interface OnItemClickListener{
        void OnItemClickListener(View view,int position);
    }
    @Override
    public int getItemCount() {
        return addSmartHelpers.size();
    }
}
