package com.example.smarthome.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Database.Home;
import com.example.smarthome.Database.Scene.Condition;
import com.example.smarthome.Database.Scene.Mission;
import com.example.smarthome.Database.Scene.S_Device;
import com.example.smarthome.Page_Samrt.AdjustTheAirCondition;
import com.example.smarthome.Page_Samrt.AdjustTheLights;
import com.example.smarthome.Page_Samrt.AdjustTheMusic;
import com.example.smarthome.Page_Samrt.AdustTheCurtain;
import com.example.smarthome.R;
import com.example.smarthome.Scene.SmartDevice.SmartDeviceList;

import java.util.ArrayList;
import java.util.List;
/**
 * @场景条件显示的适配器
 */
public class ConAndMissAdaptor  extends RecyclerView.Adapter<ConAndMissAdaptor.ViewHolder>{

    private ConAndMissAdaptor.OnItemClickListener mItemClickListener;
    private List<Condition> conditionList=new ArrayList<>();
    private List<Mission> missionList=new ArrayList<>();
    private List<S_Device> s_deviceList=new ArrayList<>();
    private String device_type;
    private String controller_long_address;
    private String target_long_address;
    private String target_short_address;
    private String network_flag;
    private String wetness;
    private String air_temp;
    private String time;
    private int judge;

    /**
     * @description
     * 0为条件
     * 1为任务
     */
    private int flag=0;
    public void setFlag(int flag){
        this.flag=flag;
    }
    public void inputTime(String time){
        this.time=time;
    }
    public ConAndMissAdaptor(List<Condition> conditionList){
        this.conditionList=conditionList;
    }
    public ConAndMissAdaptor(List<Mission> missionList,int flag){
        this.missionList=missionList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView itemName;
        View ConAndMiss;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ConAndMiss=itemView;
            ConAndMiss.setOnClickListener(this);
            imageView=ConAndMiss.findViewById(R.id.iv_display);
            itemName=ConAndMiss.findViewById(R.id.tv_display);
        }

        @Override
        public void onClick(View v) {
            if(mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());

            }
        }
    }

    /**
     *
     * @param parent
     * @param viewType
     * @return 
     * 
     *
     */
    @NonNull
    @Override
    public ConAndMissAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.conandmimsslist,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.ConAndMiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==0){

                    CharSequence type=holder.itemName.getText();
                    //TODO 忘了还有传感器，要命，记得还有上一个活动向这个活动的传入数据
                    //TODO 条件那就显示一个智能设备控制，点进去就是智能设备的列表，这需要保存设备的自定义名称

                    switch (type.toString()){
                        case "智能设备":
                            //TODO 由于智能设备合并了，因此如果只传一个condition的事件肯定不行，所以还是传入场景的时间，通过场景时间找到所有设备
                            Intent intent=new Intent(parent.getContext(), SmartDeviceList.class);
                            intent.putExtra("time",time);
                            view.getContext().startActivity(intent);
                            break;
                        case "点击执行":
                            //TODO 由于智能设备合并了，因此如果只传一个condition的事件肯定不行，所以还是传入场景的时间，通过场景时间找到所有设备
                            Intent intent1=new Intent(parent.getContext(), SmartDeviceList.class);
                            intent1.putExtra("time",time);
                            view.getContext().startActivity(intent1);
                            break;
                        case "场景控制":
                            //TODO 由于智能设备合并了，因此如果只传一个condition的事件肯定不行，所以还是传入场景的时间，通过场景时间找到所有设备
                            Intent intent2=new Intent(parent.getContext(), SmartDeviceList.class);
                            intent2.putExtra("time",time);
                            view.getContext().startActivity(intent2);
                            break;
                        case "传感器":
                            //TODO 由于智能设备合并了，因此如果只传一个condition的事件肯定不行，所以还是传入场景的时间，通过场景时间找到所有设备
                            Intent intent3=new Intent(parent.getContext(), SmartDeviceList.class);
                            intent3.putExtra("time",time);
                            view.getContext().startActivity(intent3);
                            break;
                        case "执行的场景":
                            //TODO 由于智能设备合并了，因此如果只传一个condition的事件肯定不行，所以还是传入场景的时间，通过场景时间找到所有设备
                            Intent intent4=new Intent(parent.getContext(), SmartDeviceList.class);
                            intent4.putExtra("time",time);
                            view.getContext().startActivity(intent4);
                            break;
                        case "系统通知":
                            //TODO 由于智能设备合并了，因此如果只传一个condition的事件肯定不行，所以还是传入场景的时间，通过场景时间找到所有设备
                            Intent intent5=new Intent(parent.getContext(), SmartDeviceList.class);
                            intent5.putExtra("time",time);
                            view.getContext().startActivity(intent5);
                            break;
                        case "执行的设备":
                            //TODO 由于智能设备合并了，因此如果只传一个condition的事件肯定不行，所以还是传入场景的时间，通过场景时间找到所有设备
                            Intent intent6=new Intent(parent.getContext(), SmartDeviceList.class);
                            intent6.putExtra("time",time);
                            view.getContext().startActivity(intent6);
                            break;


                    }


                }


            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ConAndMissAdaptor.ViewHolder holder, int position) {

        String category="没有匹配项为什么显示emm";
        //分类
        if(flag==0){
            judge=conditionList.get(position).getJudge();
            switch (judge){
                case 1:holder.imageView.setImageResource(R.drawable.ic_dashboard_black_24dp);category="智能设备";break;
                case 2:holder.imageView.setImageResource(R.drawable.hand_click);category="点击执行";break;
                case 3:holder.imageView.setImageResource(R.drawable.time);category="定时执行";break;
                case 4:holder.imageView.setImageResource(R.drawable.scene);category="场景控制";break;
                case 5:holder.imageView.setImageResource(R.drawable.sensor);category="传感器";break;
                default:
                    holder.imageView.setImageResource(R.drawable.bg_swpt);category="这绝对出bug了！";break;
            }

        }else if(flag==1){
            judge=missionList.get(position).getJudge();
            switch (judge){
                case 1:holder.imageView.setImageResource(R.drawable.scene);category="执行的场景";break;
                case 2:holder.imageView.setImageResource(R.drawable.message);category="系统通知";break;
                case 3:holder.imageView.setImageResource(R.drawable.ic_dashboard_black_24dp);category="执行的设备";break;
                default:
                    holder.imageView.setImageResource(R.drawable.bg_swpt);category="这绝对出bug了！";break;
            }


        }

        holder.itemName.setText(category);
    }

    @Override
    public int getItemCount() {
        if(flag==0)
            return conditionList.size();
        else if(flag==1)
            return missionList.size();
        return 0;
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
    public void setOnItemClickListener(ConAndMissAdaptor.OnItemClickListener listener) {
        mItemClickListener = listener;
    }

}
