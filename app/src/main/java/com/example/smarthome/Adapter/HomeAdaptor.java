package com.example.smarthome.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Database.Helper.AddHomeHelper;
import com.example.smarthome.R;

import java.util.ArrayList;

public class HomeAdaptor extends RecyclerView.Adapter<HomeAdaptor.ViewHolder> {
    private ArrayList<AddHomeHelper> mAddHomeHelperArrayList=new ArrayList<>();
public HomeAdaptor(ArrayList<AddHomeHelper> addHomeHelperArrayList){
    mAddHomeHelperArrayList=addHomeHelperArrayList;
}
static class ViewHolder extends RecyclerView.ViewHolder{
    ImageView imageView;
    TextView textView;
    View homeView;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        homeView=itemView;
        imageView=homeView.findViewById(R.id.home_image);
        textView=homeView.findViewById(R.id.home_title);
    }
}
    @NonNull
    @Override
    public HomeAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.homelist,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdaptor.ViewHolder holder, int position) {
        holder.imageView.setImageResource(mAddHomeHelperArrayList.get(position).getImage());
        holder.textView.setText(mAddHomeHelperArrayList.get(position).getTitle());
        holder.homeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "你是玩明白的",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAddHomeHelperArrayList.size();
    }
}
