package com.hechuang.hepay.ui.activity;

import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hechuang.hepay.R;
import com.hechuang.hepay.api.Web_Url;
import com.hechuang.hepay.base.BaseAppCompatActivity;
import com.hechuang.hepay.bean.RedPacketInfoBean;
import com.hechuang.hepay.persenter.RedPacketInfoPersenter;
import com.hechuang.hepay.util.Eyes;
import com.hechuang.hepay.util.MyToast;
import com.hechuang.hepay.util.Utils;
import com.hechuang.hepay.view.IRedPacketInfoView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 红包详情
 * Created by Android_xu on 2018/1/9.
 */
public class RedPacketInfoActivity extends BaseAppCompatActivity<RedPacketInfoPersenter> implements IRedPacketInfoView, View.OnClickListener {
    @BindView(R.id.redpacketinfo_red_back)
    ImageView mBack;
    @BindView(R.id.redpacketinfo_red_moeny)
    TextView mMoeny;
    @BindView(R.id.redpacketinfo_red_jilu)
    TextView mJilu;
    @BindView(R.id.redpacketinfo_red_qinabao)
    TextView mQianbao;
    @BindView(R.id.redpacketinfo_red_guoqi)
    TextView mGuoqi;
    private static final String TAG = "RedPacketInfoActivity";
    private int red_type = 1;

    @Override
    protected int initlayout() {
        return R.layout.activity_redpacketinfo;
    }

    @Override
    protected void initPersenter() {
        setMPersenter(new RedPacketInfoPersenter(this, this));
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        Eyes.setStatusBarColor(this, ContextCompat.getColor(this, R.color.packet_red));
        mBack.setOnClickListener(this);
        String id = getIntent().getStringExtra("id");
        red_type = getIntent().getIntExtra("type", 1);
//        Log.d(TAG, "initView: " + red_type);
        mJilu.setOnClickListener(this);
        mQianbao.setOnClickListener(this);
        getMPersenter().getredpacketinfo(id);
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
        MyToast.showMsg(msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.redpacketinfo_red_back:
                finish();
                break;
            case R.id.redpacketinfo_red_jilu:
                Utils.startwebactivity(this, Web_Url.HONGBAOMINGXI_URL);
                finish();
                break;
            case R.id.redpacketinfo_red_qinabao:
                Utils.startwebactivity(this, Web_Url.QIANBAOMINGXI_URL);
                finish();
                break;
        }

    }

    @Override
    public void getredpacketsuccess(RedPacketInfoBean redPacketInfoBean) {
//        Log.d(TAG, "getredpacketsuccess: " + redPacketInfoBean.getData().getList().getStatus());
        switch (redPacketInfoBean.getData().getList().getStatus()) {
            case "0"://成功
                mMoeny.setText(redPacketInfoBean.getData().getList().getBonusmoney());
                mMoeny.setVisibility(View.VISIBLE);
                mGuoqi.setVisibility(View.GONE);
                mQianbao.setVisibility(View.VISIBLE);
                if (red_type == 0) {
                    Utils.playSound(this, R.raw.playjinbi);
                    Vibrator vibrator = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);
                    vibrator.vibrate(700);
                }
                break;
            case "1"://已拆过
                mMoeny.setText(redPacketInfoBean.getData().getList().getBonusmoney());
                mMoeny.setVisibility(View.VISIBLE);
                mGuoqi.setVisibility(View.GONE);
                mQianbao.setVisibility(View.VISIBLE);
                if (red_type == 0) {
                    Utils.playSound(this, R.raw.playjinbi);
                    Vibrator vibrator = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);
                    vibrator.vibrate(700);
                }
                break;
            case "2"://过期
                mMoeny.setText(redPacketInfoBean.getData().getList().getBonusmoney());
                mMoeny.setVisibility(View.GONE);
                mGuoqi.setVisibility(View.VISIBLE);
                mQianbao.setVisibility(View.GONE);
                mGuoqi.setText("红包已过期");
                break;
        }
    }

    @Override
    public void getredpacketinfoerror(String msg) {
        MyToast.showMsg(msg);
        mMoeny.setText("0.00");
        mMoeny.setVisibility(View.GONE);
        mGuoqi.setVisibility(View.VISIBLE);
        mQianbao.setVisibility(View.GONE);
        mGuoqi.setText("网络出错，请稍后重试");
    }
}
