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

        import com.example.smarthome.Database.Device;
import com.example.smarthome.R;

import java.util.ArrayList;
        import java.util.List;
        import java.util.Map;

public class HumidifierAdaptor extends RecyclerView.Adapter<HumidifierAdaptor.ViewHolder>{
    private HumidifierAdaptor.OnItemClickListner onItemClickListner;//单击事件
    private HumidifierAdaptor.OnItemLongClickListner onItemLongClickListner;//长按单击事件
    private List<Map<String,String>> airList;
    private List<Device> mAirList=new ArrayList<>();
    private int layoutId;
    public Context context;
    private boolean clickFlag = true;//单击事件和长单击事件的屏蔽标识
    public HumidifierAdaptor(List<Map<String,String>> mAirList){
        this.airList=mAirList;
    }
    public interface ItemSelectedCallBack {
        void convert(HumidifierAdaptor.ViewHolder holder, int position);
    }

    public HumidifierAdaptor(Context context, int layoutId, List<Device> mAirList) {
        this.layoutId = layoutId;
        this.mAirList = mAirList;
        this.context = context;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView air_image;
        TextView air_name;
        View airView;
        ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            airView=itemView;
            air_image=airView.findViewById(R.id.air_image);
            air_name=airView.findViewById(R.id.air_name);
        }
        public View getItemView() {
            return airView;
        }
        public View getView(int id) {
            return airView.findViewById(id);
        }

        public interface OnClickListener {
            void onClickListener(View v);
        }
    }
    @NonNull
    @Override
    public HumidifierAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.scene_airlist,parent,false);
        final HumidifierAdaptor.ViewHolder holder=new HumidifierAdaptor.ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
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
        view.setOnLongClickListener(new View.OnLongClickListener() {
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
    public void onBindViewHolder(@NonNull HumidifierAdaptor.ViewHolder holder, int position) {
        holder.air_image.setImageResource(R.drawable.humidifier);
        holder.air_name.setText(mAirList.get(position).getTarget_long_address());
        if (mCallBack != null)
            mCallBack.convert(holder, mAirList.get(position), position);
    }

    @Override
    public int getItemCount() {
        if(mAirList==null)
            return 0;
        else
            return mAirList.size();
    }
    public void setOnItemClickListner(HumidifierAdaptor.OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    public interface OnItemClickListner {
        void onItemClickListner(View v, int position);
    }

    public void setOnItemLongClickListner(HumidifierAdaptor.OnItemLongClickListner onItemLongClickListner) {
        this.onItemLongClickListner = onItemLongClickListner;
    }


    public interface OnItemLongClickListner {
        void onItemLongClickListner(View v, int position);
    }

    HumidifierAdaptor.CallBack mCallBack;

    public void setCallBack(HumidifierAdaptor.CallBack CallBack) {
        this.mCallBack = CallBack;
    }

    public interface CallBack {
        <T extends Object> void convert(HumidifierAdaptor.ViewHolder holder, T bean, int position);
    }
}
