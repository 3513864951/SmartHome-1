package com.example.smarthome.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.smarthome.Helper.AddSmartHelper;
import com.example.smarthome.R;
import com.example.smarthome.Scene.More;
import com.example.smarthome.View.DeleteRecyclerViewDiy.MoveTouchCallback;

import java.util.Collections;
import java.util.List;

/**
 * 项目名称：LayoutManager
 * 类描述：自定义的recycler的适配器
 * 作者：峰哥
 * 创建时间：2016/12/22 16:40
 * 邮箱：470794349@qq.com
 * 修改简介：
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> implements MoveTouchCallback.ItemTouchAdapter {

    private Context context;
    private int src;
    private List<AddSmartHelper> results;
    private OnItemLongClickListener onItemLongClickListener;
    MyViewHolder myViewHolder;

    public RecyclerAdapter(int src, List<AddSmartHelper> results) {
        this.results = results;
        this.src = src;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(src, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.imageView.setImageResource(results.get(position).getImage());
        holder.textView.setText(results.get(position).getTitle());

    }

    public boolean setOnLongClickLisener(View view) {
        MyViewHolder holder = (MyViewHolder) view.getTag();
        if (onItemLongClickListener != null) {
            this.onItemLongClickListener.onItemLongClick(view, holder.getAdapterPosition());
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    //在这里可以设置最后一位或者特定位置不可拖动，实际需求可能会用到
    @Override
    public void onMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(results, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(results, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onSwiped(int position) {
        results.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private ImageView imageView;

        private MyViewHolder(View itemView) {
            super(itemView);
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            int width = 0;
            int height1 = 0;
            if (wm != null) {
                wm.getDefaultDisplay().getMetrics(outMetrics);
            }
            width = outMetrics.widthPixels;
//            height1=outMetrics.heightPixels;
            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
//            layoutParams.height = height1 ;
            layoutParams.height = 340;
            itemView.setLayoutParams(layoutParams);
            textView = itemView.findViewById(R.id.add_tv);
            imageView = itemView.findViewById(R.id.add_im);

        }

    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public void del(int i) {
        results.remove(i);
        notifyDataSetChanged();
    }

}