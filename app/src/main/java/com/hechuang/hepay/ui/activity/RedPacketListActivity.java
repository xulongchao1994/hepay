package com.hechuang.hepay.ui.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.hechuang.hepay.R;
import com.hechuang.hepay.adapter.RedPacketListAdapter;
import com.hechuang.hepay.base.BaseAppCompatActivity;
import com.hechuang.hepay.base.RvListener;
import com.hechuang.hepay.bean.RedPacketListBean;
import com.hechuang.hepay.customview.RedPacketPopupWindow;
import com.hechuang.hepay.persenter.RedPacketListPersenter;
import com.hechuang.hepay.util.MyToast;
import com.hechuang.hepay.view.IRedPacketListView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 红包列表
 * Created by Android_xu on 2018/1/9.
 */
public class RedPacketListActivity extends BaseAppCompatActivity<RedPacketListPersenter> implements IRedPacketListView, OnRefreshListener, OnNetWorkErrorListener, RvListener, View.OnClickListener {
    @BindView(R.id.redpacketlist_outlogin)
    TextView outlogin_tv;
    @BindView(R.id.redpacketlist_list)
    LRecyclerView mLRecyclerView;
    @BindView(R.id.redpacketlist_back)
    ImageView mBack;
    @BindView(R.id.empty_view)
    TextView Emptyview;
    RedPacketListBean redPacketListBeans;
    RedPacketListAdapter adapter;
    LRecyclerViewAdapter LAdapter;
    private static final String TAG = "RedPacketListActivity";
    private int page = 1;
    Button redpack_cai;
    private int list_position;
    ObjectAnimator objectAnimator;
    private SharedPreferences sp;
    ImageView close;

    @Override
    protected int initlayout() {
        return R.layout.activity_redpacketlist;
    }

    @Override
    protected void initPersenter() {
        setMPersenter(new RedPacketListPersenter(this, this));
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        outlogin_tv.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mLRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mLRecyclerView.setOnLoadMoreListener(this);
        mLRecyclerView.setOnRefreshListener(this);
        mLRecyclerView.setOnNetWorkErrorListener(this);
        mLRecyclerView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
        mLRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mLRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        getMPersenter().getlistdata("");
    }

    private void addview() {
        View addview = LayoutInflater.from(this).inflate(R.layout.view_redpacketlistitem, null);
        LAdapter.addHeaderView(addview);
    }


    @Override
    public void showloading() {
        getMLoading().show();
    }

    @Override
    public void dissmissloading() {
        getMLoading().dismiss();
    }

    @Override
    public void getdataerror(String msg) {
        mLRecyclerView.setVisibility(View.GONE);
        Emptyview.setVisibility(View.VISIBLE);
        MyToast.showMsg(msg);
    }


    @Override
    public void onRefresh() {
        page = 1;
        getMPersenter().getlistdata(String.valueOf(page));
        mLRecyclerView.refreshComplete(10);
    }

    @Override
    public void reload() {
        mLRecyclerView.setEmptyView(Emptyview);
    }

    /**
     * 弹出红包详情的页面
     *
     * @param id
     * @param position
     */
    @Override
    public void onItemListener(int id, int position) {
        list_position = position - 2;
        switch (redPacketListBeans.getData().getList().get(list_position).getStatus()) {
            case "0"://未拆开
                showpopupwindow();
                info_text.setText("现金红包天天抢");
                redpack_cai.setVisibility(View.VISIBLE);
                break;
            case "1"://已拆开
//                info_text.setText("红包已拆开");
//                redpack_cai.setVisibility(View.GONE);
                Intent intent = new Intent(RedPacketListActivity.this, RedPacketInfoActivity.class);
                intent.putExtra("id", redPacketListBeans.getData().getList().get(list_position).getId());
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            case "2"://过期
//                info_text.setText("红包已过期");
//                redpack_cai.setVisibility(View.GONE);
                Intent intent2 = new Intent(RedPacketListActivity.this, RedPacketInfoActivity.class);
                intent2.putExtra("id", redPacketListBeans.getData().getList().get(list_position).getId());
                intent2.putExtra("type", 2);
                startActivity(intent2);
                break;
        }
    }

    @Override
    public void getlistdatasuccess(RedPacketListBean redPacketListBean) {
        redPacketListBean.getData().setStatus(1);
//        List<RedPacketListBean.DataBean.ListBean> listBeans = new ArrayList<>();
//        RedPacketListBean.DataBean.ListBean listBean = new RedPacketListBean.DataBean.ListBean("1", "0", "  ");
//        RedPacketListBean.DataBean.ListBean listBean1 = new RedPacketListBean.DataBean.ListBean("2", "1", "  ");
//        RedPacketListBean.DataBean.ListBean listBean2 = new RedPacketListBean.DataBean.ListBean("3", "2", "  ");
//        listBeans.add(listBean);
//        listBeans.add(listBean1);
//        listBeans.add(listBean2);
//        redPacketListBean.getData().setList(listBeans);
        if (redPacketListBean.getData().getStatus() == 1) {
            if (redPacketListBean.getData().getList() != null && redPacketListBean.getData().getList().size() > 0) {
                redPacketListBeans = redPacketListBean;
                adapter = new RedPacketListAdapter(redPacketListBean.getData().getList(), this, this);
                LAdapter = new LRecyclerViewAdapter(adapter);
                mLRecyclerView.setAdapter(LAdapter);
                mLRecyclerView.setLoadMoreEnabled(false);
                addview();
                mLRecyclerView.setVisibility(View.VISIBLE);
                Emptyview.setVisibility(View.GONE);
            } else {
                mLRecyclerView.setVisibility(View.GONE);
                Emptyview.setVisibility(View.VISIBLE);
            }
        } else {
            mLRecyclerView.setVisibility(View.GONE);
            Emptyview.setVisibility(View.VISIBLE);
        }
    }

//    @Override
//    public void getredpacketsuccess(RedPacketInfoBean redPacketInfoBean) {
//        info_moeny.setVisibility(View.VISIBLE);
//        redPacketInfoBean.getData().getList().setStatus("1");
//        redPacketInfoBean.getData().getList().setBonusmoney("10000.00");
//        info_moeny.setText(redPacketInfoBean.getData().getList().getBonusmoney());
//        Log.d(TAG, "getredpacketsuccess: " + redPacketInfoBean.getData().toString());
//        if (redPacketInfoBean.getData().getList().getStatus().equals("1")) {//成功
//            info_text.setText("红包已领取");
//            redpack_cai.setVisibility(View.GONE);
//            objectAnimator.clone();
//            if (redpacket != null && redpacket.isShowing())
//                redpacket.dismiss();
//            Intent intent = new Intent(RedPacketListActivity.this, RedPacketInfoActivity.class);
//            intent.putExtra("id", redPacketInfoBean.getData().getList().getId());
//            intent.putExtra("type", 0);
//            startActivity(intent);
//        } else if (redPacketInfoBean.getData().getList().getStatus().equals("0")) {//已经领取过了
//            redpack_cai.setVisibility(View.GONE);
//            info_text.setText("红包已拆开");
//        } else if (redPacketInfoBean.getData().getList().getStatus().equals("2")) {//过期
//            //设置过期页面
//            info_text.setText("红包已过期");
//            objectAnimator.clone();//动画停止
//            redpack_cai.setVisibility(View.GONE);
//        }
//        mPersenter.getlistdata("");
//    }
//
//    @Override
//    public void getredpacketinfoerror(String msg) {
//        MyToast.showMsg(msg);
//        info_moeny.setVisibility(View.VISIBLE);
//        info_moeny.setText("红包返利，天天领");
//        info_text.setText("网络错误");
//        redpack_cai.setVisibility(View.GONE);
//        objectAnimator.clone();
//        adapter.setnotifyadapter();
//    }

    @Override
    protected void onResume() {
        super.onResume();
        if (redpacket != null && redpacket.isShowing())
            redpacket.dismiss();

        page = 1;
        getMPersenter().getlistdata(String.valueOf(page));
        mLRecyclerView.refreshComplete(10);
    }

    RedPacketPopupWindow redpacket;
    ImageView info_icon;
    TextView info_text;
    TextView info_moeny;
    LinearLayout viewdissmiss;
    RelativeLayout viewbaochi;

    /**
     * 显示红包的弹出框以及后续操作
     */
    public void showpopupwindow() {
        View view_red = LayoutInflater.from(this).inflate(R.layout.view_redpacketinfo, null);
//        redpacket = new AlertDialog.Builder(this).setView(view_red)
//                .show();
        redpacket = new RedPacketPopupWindow(this);
        viewdissmiss = view_red.findViewById(R.id.redpacketinfo_viewdissmiss);
        viewdissmiss.setOnClickListener(this);
        viewbaochi = view_red.findViewById(R.id.redpacketinfo_viewbaochi);
        viewbaochi.setOnClickListener(this);
        redpack_cai = view_red.findViewById(R.id.redpacketinfo_bt);
        info_icon = view_red.findViewById(R.id.redpacketinfo_img);
        info_text = view_red.findViewById(R.id.redpacketinfo_text);
        info_moeny = view_red.findViewById(R.id.redpacketinfo_moeny);
        info_moeny.setVisibility(View.VISIBLE);
        close = view_red.findViewById(R.id.redpacketinfo_close);
        close.setOnClickListener(this);
        Glide.with(this).load(R.mipmap.ic_launcher).override(80, 80).into(info_icon);
        redpack_cai.setOnClickListener(this);
        objectAnimator = ObjectAnimator.ofFloat(redpack_cai, "RotationY", 0f, 360f);
        objectAnimator.setDuration(5000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        redpacket.setContentView(view_red);
        redpacket.showAsDropDown(mBack);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.redpacketlist_back:
                finish();
                break;
            case R.id.redpacketlist_outlogin:
                new AlertDialog.Builder(this).setMessage("是否退出登录？").setPositiveButton("确定", (dialog, which) -> outlogin()).setNegativeButton("取消", null).show();
                break;
            case R.id.redpacketinfo_bt:
                if (redPacketListBeans.getData().getList().get(list_position).getStatus().equals("1") || redPacketListBeans.getData().getList().get(list_position).getStatus().equals("2")) {
                    return;
                } else {
                    redpack_cai.setEnabled(false);
                    redpack_cai.setBackground(getResources().getDrawable(R.drawable.redpack_moeny));
                    objectAnimator.start();
                    new Handler().postAtTime(() -> {
                        redpack_cai.setEnabled(true);
                        objectAnimator.clone();
                        if (redpacket != null && redpacket.isShowing())
                            redpacket.dismiss();
                        Intent intent = new Intent(RedPacketListActivity.this, RedPacketInfoActivity.class);
                        if (list_position == 0) {
                            intent.putExtra("id", redPacketListBeans.getData().getList().get(list_position).getId());
                            intent.putExtra("type", 0);
                        } else if (list_position == 1) {
                            intent.putExtra("id", redPacketListBeans.getData().getList().get(list_position).getId());
                            intent.putExtra("type", 1);
                        } else {
                            intent.putExtra("id", redPacketListBeans.getData().getList().get(list_position).getId());
                            intent.putExtra("type", 2);
                        }
                        startActivity(intent);

//                        ARouter.getInstance().build("/activity/redpacketinfo")
//                                .withString("id", redPacketListBeans.getData().getList().get(list_position).getId())
//                                .withInt("type", 0)
//                                .navigation();
                    }, 1000);
                }
                break;
            case R.id.redpacketinfo_close:
                redpacket.dismiss();
                break;
            case R.id.redpacketinfo_viewdissmiss:
                redpacket.dismiss();
                break;
            case R.id.redpacketinfo_viewbaochi://不可取消，利用点击事件冲突保持弹出框的显示
                break;
        }
    }

    /**
     * 退出登录
     */
    private void outlogin() {
        sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name", "");
        editor.putString("password", "");
        editor.putString("token_id", "");
        editor.putString("urserid", "");
        editor.putString("usertype", "");
        editor.putBoolean("islogin", false);
        editor.commit();
        startActivity(new Intent(RedPacketListActivity.this, LoginActivity.class));
        finish();
    }
}
