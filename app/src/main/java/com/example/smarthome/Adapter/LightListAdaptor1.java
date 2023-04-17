package com.example.smarthome.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.R;

import java.util.List;

public class LightListAdaptor1 extends RecyclerView.Adapter<LightListAdaptor1.ViewHolder> {
    private int layoutId;
    private List<? extends Object> data;
    public Context context;
    private OnItemClickListner onItemClickListner;//单击事件
    private OnItemLongClickListner onItemLongClickListner;//长按单击事件
    private boolean clickFlag = true;//单击事件和长单击事件的屏蔽标识



    public LightListAdaptor1(Context context, int layoutId, List<? extends Object> data) {
        this.layoutId = layoutId;
        this.data = data;
        this.context = context;
    }



    @NonNull
    @Override
    public LightListAdaptor1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        final ViewHolder holder = new ViewHolder(v, context);
        //单击事件回调
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListner == null)
                    return;
                if (clickFlag) {
                    onItemClickListner.onItemClickListner(v, holder.getLayoutPosition());
                }
                clickFlag = true;
            }
        });
        //单击长按事件回调
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListner == null)
                    return false;
                onItemLongClickListner.onItemLongClickListner(v, holder.getLayoutPosition());
                clickFlag = false;
                return false;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LightListAdaptor1.ViewHolder holder, int position) {
        if (mCallBack != null)
            mCallBack.convert(holder, data.get(position), position);
    }

    @Override
    public int getItemCount() {
        int count = data.size();
        return count;
    }

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    public interface OnItemClickListner {
        void onItemClickListner(View v, int position);
    }

    public void setOnItemLongClickListner(OnItemLongClickListner onItemLongClickListner) {
        this.onItemLongClickListner = onItemLongClickListner;
    }


    public interface OnItemLongClickListner {
        void onItemLongClickListner(View v, int position);
    }

    CallBack mCallBack;

    public void setCallBack(CallBack CallBack) {
        this.mCallBack = CallBack;
    }

    public interface CallBack {
        <T extends Object> void convert(ViewHolder holder, T bean, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View convertView;
        Context context;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            this.convertView = itemView;
            this.context = context;
        }

        public View getItemView() {
            return convertView;
        }

        public void setText(int id, String text) {
            TextView tx = convertView.findViewById(id);
            tx.setText(text);
        }
        public void setBackground(int id,int flag){
            ConstraintLayout constraintLayout=convertView.findViewById(id);
            if(flag==0)
               constraintLayout.setBackgroundResource(R.drawable.blackbackground);
               else
                    constraintLayout.setBackgroundColor(0xFFFFFFFF);
        }
        public View getView(int id) {
            return convertView.findViewById(id);
        }

        public interface OnClickListener {
            void onClickListner(View v);
        }
    }
}
