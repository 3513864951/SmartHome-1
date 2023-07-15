package com.example.smarthome.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Database.Scene.Scene;
import com.example.smarthome.R;
import com.example.smarthome.Scene.More;

import java.util.ArrayList;
import java.util.List;



public class SceneAdaptor extends RecyclerView.Adapter<SceneAdaptor.ViewHolder> {
    private SceneAdaptor.OnItemClickListener mItemClickListener;
    private List<Scene> sceneList=new ArrayList<>();
    public SceneAdaptor(List<Scene> sceneList){
        this.sceneList=sceneList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView sceneName;
        TextView deviceJudge;
        View sceneView;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sceneView = itemView;
            sceneView.setOnClickListener(this);
            imageView = sceneView.findViewById(R.id.iv_display);
            sceneName = sceneView.findViewById(R.id.tv_display);

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
    public SceneAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.add_medal,parent,false);
        final SceneAdaptor.ViewHolder holder=new SceneAdaptor.ViewHolder(view);
        holder.sceneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(parent.getContext(), More.class);
                intent.putExtra("id",String.valueOf(sceneList.get(holder.getAdapterPosition()).getId()));
                parent.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SceneAdaptor.ViewHolder holder, int position) {
    holder.sceneName.setText(sceneList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return sceneList.size();
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
    public void setOnItemClickListener(SceneAdaptor.OnItemClickListener listener) {
        mItemClickListener = listener;
    }
}
