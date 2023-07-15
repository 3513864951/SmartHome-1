package com.example.smarthome.View.pullextend;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adapter.ExtendHeadAdapter;
import com.example.smarthome.Database.Device;
import com.example.smarthome.Database.Helper.UIHelper;
import com.example.smarthome.MQTT.ClientMQTT;
import com.example.smarthome.R;
import com.example.smarthome.animation.RuwangAnimationAlpha;
import com.google.android.material.snackbar.Snackbar;
import com.hb.dialog.myDialog.MyAlertInputDialog;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;


/**
 * 这个类封装了下拉刷新的布局
 */
public class ExtendListHeader extends ExtendLayout {


    float containerHeight = UIHelper.dip2px(60);
    float listHeight = UIHelper.dip2px(500);
    boolean arrivedListHeight = false;
    private RecyclerView mRecyclerView;
    RelativeLayout relativeLayout;
    MyAlertInputDialog myAlertInputDialog1;
    String device_nl;
    ImageView sun, moon;
    ExtendHeadAdapter extendHeadAdapter;
    TextView add_device, start_network_tv;
    CardView ruwang, start_network;
    private ClientMQTT clientMQTT;
    ObjectAnimator objectAnimator;
    List<String> mDatas = new ArrayList<>();
    List<Integer> idList = new ArrayList<>();
    List<Device> deviceList = new ArrayList<>();
    /**
     * 原点
     */

    private ExpendPoint mExpendPoint;

    /**
     * 构造方法
     *
     * @param context context
     */
    public ExtendListHeader(Context context) {
        super(context);

    }


    /**
     * 构造方法
     *
     * @param context context
     * @param attrs   attrs
     */
    public ExtendListHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(getContext(), R.layout.extend_header, this);
        sun = view.findViewById(R.id.sun);
        moon = view.findViewById(R.id.moon);
        ruwang = view.findViewById(R.id.ruwang);
        start_network = findViewById(R.id.startNetwork);
        start_network_tv = findViewById(R.id.startNetwork_tv);
        add_device = view.findViewById(R.id.add_device);
        add_device.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ruwangListener();
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 在此处获取listHeight 和 containerHeight值
        listHeight = mRecyclerView == null ? UIHelper.dip2px(120) : mRecyclerView.getMeasuredHeight();
        containerHeight = listHeight / 2;
    }

    @Override
    protected void bindView(View container) {
        mRecyclerView = findViewById(R.id.device_list);
        mExpendPoint = findViewById(R.id.expend_point);
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    protected View createLoadingView(Context context, AttributeSet attrs) {
        return LayoutInflater.from(context).inflate(R.layout.extend_header, null);
    }


    @Override
    public int getContentSize() {
        return (int) (containerHeight);
    }

    @Override
    public int getListSize() {
        return (int) (listHeight);
    }


    @Override
    protected void onReset() {
        mExpendPoint.setVisibility(VISIBLE);
        mExpendPoint.setAlpha(1);
        mExpendPoint.setTranslationY(0);
        mRecyclerView.setTranslationY(0);
        arrivedListHeight = false;
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.4f, 1.0f, 0.50f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(4000);
//        scaleAnimation.setRepeatCount(100);
//        rongYi.startAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1f);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
//        alphaAnimation.setFillAfter(fragment);
        alphaAnimation.setDuration(4000);
//        fragment.startAnimation(alphaAnimation);
        TranslateAnimation translateAnimation = new TranslateAnimation(0f, 0f, 100f, -950f);
        translateAnimation.setDuration(4000);
//        translateAnimation.setRepeatCount(100);
//        rongYi.startAnimation(translateAnimation);
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.setFillAfter(true);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);
        sun.startAnimation(animationSet);

        ScaleAnimation scaleAnimation2 = new ScaleAnimation(0f, 1.0f, 0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation2.setDuration(5000);

//        scaleAnimation.setRepeatCount(100);
//        rongYi.startAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation2 = new AlphaAnimation(0f, 1f);
        alphaAnimation2.setInterpolator(new AccelerateInterpolator());
//        alphaAnimation.setFillAfter(fragment);
        alphaAnimation2.setDuration(1000);
        AnimationSet animationSet2 = new AnimationSet(false);
        animationSet2.setFillAfter(true);
        animationSet2.addAnimation(alphaAnimation2);
        animationSet2.addAnimation(scaleAnimation2);
        moon.startAnimation(animationSet2);
        Connector.getDatabase();


        relativeLayout = findViewById(R.id.pull_to_refresh_header_content);
        clientMQTT = new ClientMQTT("light");
        try {
            clientMQTT.Mqtt_innit();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        clientMQTT.startReconnect(getContext());
        deviceList = LitePal
                .order("id desc")//TODO 用network的话要是退出软件，network没有及时变为00
                .where("flag = ? and isUpdate = ?", "0", "1")
                .find(Device.class);
        Log.e("555", deviceList.size() + "");
        adddevice();

        moon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlphaAnimation alphaAnimation3 = new AlphaAnimation(0.1f, 1f);
                alphaAnimation3.setInterpolator(new AccelerateInterpolator());
//        alphaAnimation.setFillAfter(fragment);
                alphaAnimation3.setDuration(1000);

                start_network.startAnimation(alphaAnimation3);
                start_network.setVisibility(CardView.VISIBLE);
                objectAnimator = ObjectAnimator.ofFloat(start_network, "alpha", 1f, 0.7f, 1f);
                objectAnimator.setDuration(9000);
                objectAnimator.setInterpolator(new RuwangAnimationAlpha());
                objectAnimator.setRepeatCount(Animation.REVERSE);
                objectAnimator.start();

                ruwang.startAnimation(alphaAnimation3);
                ruwang.setVisibility(CardView.VISIBLE);
                objectAnimator = ObjectAnimator.ofFloat(ruwang, "alpha", 1f, 0.7f, 1f);
                objectAnimator.setDuration(9000);
                objectAnimator.setInterpolator(new RuwangAnimationAlpha());
                objectAnimator.setRepeatCount(Animation.REVERSE);
                objectAnimator.start();
                adddevice();
                extendHeadAdapter.notifyDataSetChanged();
            }
        });
        start_network.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clientMQTT.publishMessagePlus(null, "0x0000", "0xFF", "0x0002", "0x02");
            }
        });

        start_network_tv.setOnClickListener(v -> {
            clientMQTT.publishMessagePlus(null, "0x0000", "0xFF", "0x0002", "0x02");
        });
        ruwang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ruwangListener();
            }
        });


    }

    private void ruwangListener() {
        //点击入网以后连接网络判断是否可以执行下面的步骤？以下是弹出提示框是否进行设备接入................
        int i = 1;
        if (deviceList.size() > 0) {
            adddevice();
            myAlertInputDialog1 = new MyAlertInputDialog(getContext()).builder().
                    setTitle("发现设备是否接入").setPositiveButton("同意", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(getContext()).builder()
                                            .setTitle("请输入设备名称")
                                            .setEditText("").setCancelable(true);
                                    if (deviceList.size() == 0) {
                                        myAlertInputDialog.dismiss();
                                    }
                                    myAlertInputDialog.dismiss();
                                    String source_long_address = deviceList.get(0).getTarget_long_address();
                                    Device device = new Device();
                                    device.setFlag(1);
                                    device.updateAll("target_long_address = ?", source_long_address);
                                    //向中控发送APP同意入网信息
                                    String source_short_address_1 = deviceList.get(0).getTarget_short_address();
                                    String source_short_address = "0x" + source_short_address_1;

                                    myAlertInputDialog.setPositiveButton("确认", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            clientMQTT.publishMessagePlus(null, source_short_address, "0xFF", "0x0001", "0x02");
                                            device_nl = myAlertInputDialog.getResult();
                                            if (TextUtils.isEmpty(device_nl)) {
                                                Toast.makeText(getContext(), "请输入传感器名称", Toast.LENGTH_SHORT).show();
                                                return;
                                            } else {
                                                device.setName(device_nl);
                                                device.updateAll("target_long_address = ?", source_long_address);
                                            }

                                            myAlertInputDialog.dismiss();
                                            myAlertInputDialog1.dismiss();
                                            idList.remove(0);
                                            deviceList.remove(0);
                                            adddevice();
                                        }
                                    }).setNegativeButton("取消", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            clientMQTT.publishMessagePlus(null, "0x0000", "0xFF", "0x0000", "0x02");
                                            extendHeadAdapter.notifyDataSetChanged();
                                        }
                                    });
                                    adddevice();
                                    adddevice();
                                    myAlertInputDialog.show();
                                }

                            }
                    ).setNegativeButton("拒绝", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String source_short_address_1 = deviceList.get(0).getTarget_short_address();
                            String source_short_address = "0x" + source_short_address_1;
                            Toast.makeText(getContext(), "好吧", Toast.LENGTH_SHORT).show();
                            clientMQTT.publishMessagePlus(null, source_short_address, "0xFF", "0x0000", "0x02");
                            idList.remove(0);
                            deviceList.remove(0);
                            adddevice();
                            ruwang.setVisibility(VISIBLE);
                            return;
                        }
                    });
            if (deviceList.size() == 0) {
                myAlertInputDialog1.dismiss();
            } else {
                myAlertInputDialog1.show();
            }
            adddevice();


        } else {
            Snackbar.make(relativeLayout, "未发现可入网设备", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void adddevice() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        idList = new ArrayList<>();
        if (deviceList.size() >= 1) {
            for (int i = 0; i < deviceList.size(); i++) {
                idList.add(deviceList.get(i).getId());
            }
        }
        extendHeadAdapter = new ExtendHeadAdapter(deviceList);
        extendHeadAdapter.setDeviceId(idList);
        mRecyclerView.setAdapter(extendHeadAdapter);
        if (deviceList.size() == 0) {
            ruwang.setClickable(false);
        } else {
            ruwang.setClickable(true);
        }
        extendHeadAdapter.notifyDataSetChanged();
//TODO 这里是设备显示的Adaptor
    }

    @Override
    public void onVisibility() {
        sun.setVisibility(INVISIBLE);
        moon.setVisibility(INVISIBLE);
        ruwang.setVisibility(INVISIBLE);
    }

    @Override
    protected void onReleaseToRefresh() {

    }

    @Override
    protected void onPullToRefresh() {

    }

    @Override
    protected void onArrivedListHeight() {
        arrivedListHeight = true;
    }

    @Override
    protected void onRefreshing() {

    }

    @Override
    public void onPull(int offset) {
        if (!arrivedListHeight) {
            mExpendPoint.setVisibility(VISIBLE);
            float percent = Math.abs(offset) / containerHeight;
            int moreOffset = Math.abs(offset) - (int) containerHeight;
            if (percent <= 1.0f) {
                mExpendPoint.setPercent(percent);
                mExpendPoint.setTranslationY(-Math.abs(offset) / 2 + mExpendPoint.getHeight() / 2);
                mRecyclerView.setTranslationY(-containerHeight);
            } else {
                float subPercent = (moreOffset) / (listHeight - containerHeight);
                subPercent = Math.min(1.0f, subPercent);
                mExpendPoint.setTranslationY(-(int) containerHeight / 2 + mExpendPoint.getHeight() / 2 + (int) containerHeight * subPercent / 2);
                mExpendPoint.setPercent(1.0f);
                float alpha = (1 - subPercent * 2);
                mExpendPoint.setAlpha(Math.max(alpha, 0));
                mRecyclerView.setTranslationY(-(1 - subPercent) * containerHeight);
            }
        }
        if (Math.abs(offset) >= listHeight) {
            mExpendPoint.setVisibility(INVISIBLE);
            mRecyclerView.setTranslationY(-(Math.abs(offset) - listHeight) / 2);
        }
    }

    public void tran() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.4f, 1.0f, 0.50f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(3000);
        scaleAnimation.setRepeatCount(100);
//        rongYi.startAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1f);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
//        alphaAnimation.setFillAfter(fragment);
        alphaAnimation.setDuration(3000);
//        fragment.startAnimation(alphaAnimation);
        TranslateAnimation translateAnimation = new TranslateAnimation(0f, 0f, 100f, -100f);
        translateAnimation.setDuration(3000);
        translateAnimation.setRepeatCount(100);
//        rongYi.startAnimation(translateAnimation);
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.setFillAfter(true);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);
        sun.startAnimation(animationSet);
    }


}