package com.example.smarthome.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Database.AddModel;
import com.example.smarthome.R;

import java.util.List;

public class AddMedalAdapter extends RecyclerView.Adapter <AddMedalAdapter.AddMedalHolder> {
    List<AddModel> addMedalHelpers;

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public AddMedalAdapter(List<AddModel> addMedalHelpers) {
        this.addMedalHelpers = addMedalHelpers;
    }



    @NonNull
    @Override
    public AddMedalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_medal, parent, false);
        AddMedalHolder addMedalHolder = new AddMedalHolder(view);
        return addMedalHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddMedalHolder holder, int position) {
        AddModel addMedalHelper = addMedalHelpers.get(position);
        holder.title.setText(addMedalHelper.getModel());


    }

    public static class AddMedalHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        public AddMedalHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.medal_iv);
            title = itemView.findViewById(R.id.medal_tv);
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
    public interface  OnItemClickListener{
        void OnItemClickListener(View view,int position);
    }
    @Override
    public int getItemCount() {
        return addMedalHelpers.size();
    }
}
