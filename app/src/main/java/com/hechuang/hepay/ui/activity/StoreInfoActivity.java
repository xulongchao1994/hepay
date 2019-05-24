package com.hechuang.hepay.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hechuang.hepay.R;
import com.hechuang.hepay.adapter.StoreInfo_Adapter;
import com.hechuang.hepay.base.BaseAppCompatActivity;
import com.hechuang.hepay.bean.StoreinfoListBean;
import com.hechuang.hepay.bean.UserData;
import com.hechuang.hepay.customview.BaseScrollView;
import com.hechuang.hepay.customview.FullyLinearLayoutManager;
import com.hechuang.hepay.persenter.StoreInfoPersenter;
import com.hechuang.hepay.util.GlideImageLoader;
import com.hechuang.hepay.util.MyToast;
import com.hechuang.hepay.view.IStoreInfoView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;


/**
 * 店铺详情
 */
public class StoreInfoActivity extends BaseAppCompatActivity<StoreInfoPersenter> implements IStoreInfoView, View.OnClickListener {
    private ImageView mBack;
    private ImageView mFenxiang;
    private Banner mBanner;
    private TextView mName;
    private TextView mShuaka;
    private TextView mWifi;
    private TextView mWeixinpay;
    private TextView mZhifubaopay;
    private TextView mTingchewei;
    private TextView mAddress;
    private TextView mJuli;
    private ImageView mPhone;
    private TextView mStore_tese;
    private TextView mStore_kouhao;
    private TextView mStore_jieshao;
    private Button mMove;
    private RecyclerView mRecyclerView;
    String Store_id = "";
    String lat = "";
    String lng = "";
    String phone = "";
    ImageView xiaofeibi;
    BaseScrollView scrollView;
    private String button_url;
//    private TextView buturl;

    @Override
    protected int initlayout() {
        return R.layout.activity_storeinfo;
    }

    @Override
    protected void initPersenter() {
        setMPersenter(new StoreInfoPersenter(this, this));
    }


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
////        initview();
//    }

    /**
     * 初始化视图
     */
    @Override
    protected void initView() {
        Store_id = getIntent().getStringExtra("id");
        lat = getIntent().getStringExtra("lat");
        if (lat == null) lat = "";
        lng = getIntent().getStringExtra("lng");
        if (lng == null) lng = "";
        getMPersenter().getdata(Store_id, lat, lng);
        scrollView = (BaseScrollView) findViewById(R.id.storeinfo_scrollview);
        mBack = (ImageView) findViewById(R.id.storeinfo_back);
        mBack.setOnClickListener(this);
        mFenxiang = (ImageView) findViewById(R.id.storeinfo_fenxiang);
        mFenxiang.setOnClickListener(this);
        mBanner = findViewById(R.id.storeinfo_icon);
        mBanner.setImageLoader(new GlideImageLoader());
        DisplayMetrics display = getResources().getDisplayMetrics();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(display.widthPixels, display.widthPixels / 2);
        mBanner.setLayoutParams(params);
        mBanner.isAutoPlay(true);
        //轮播时间
        mBanner.setDelayTime(3000);
        //banner的动画样式
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        xiaofeibi = (ImageView) findViewById(R.id.storeinfo_xiaofeibi);
        mName = (TextView) findViewById(R.id.storeinfo_name);
        mJuli = (TextView) findViewById(R.id.storeinfo_juli);
        mShuaka = (TextView) findViewById(R.id.storeinfo_shuaka);
        mWifi = (TextView) findViewById(R.id.storeinfo_wifi);
        mWeixinpay = (TextView) findViewById(R.id.storeinfo_weixinpay);
        mZhifubaopay = (TextView) findViewById(R.id.storeinfo_zhifubao);
        mTingchewei = (TextView) findViewById(R.id.storeinfo_tingchewei);
        mAddress = (TextView) findViewById(R.id.storeinfo_address);
        mPhone = (ImageView) findViewById(R.id.storeinfo_phone);
        mPhone.setOnClickListener(this);
        mStore_tese = (TextView) findViewById(R.id.storeinfo_tese);
        mStore_kouhao = (TextView) findViewById(R.id.storeinfo_kouhao);
        mStore_jieshao = (TextView) findViewById(R.id.storeinfo_jieshao);
        mMove = findViewById(R.id.storeinfo_move);
        mMove.setOnClickListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.storeinfo_recyclerview);
        mRecyclerView.setLayoutManager(new FullyLinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void showloading() {
        getMLoading().show();
    }

    @Override
    public void dissmissloading() {
        if (getMLoading()!=null) {
            getMLoading().dismiss();
        }
    }

    @Override
    public void getdataerror(String msg) {
        MyToast.showMsg(msg);
    }


    String imgurl = "";
    private static final String TAG = "StoreInfoActivity";
    String store_id = "";
    String shopname = "";

    @Override
    public void setdata(final StoreinfoListBean mStoreinfoListBean, String msg) {
        if (mStoreinfoListBean.getShareaccounths().equals("1")) {
            xiaofeibi.setVisibility(View.VISIBLE);
        } else {
            xiaofeibi.setVisibility(View.GONE);
        }
        imgurl = mStoreinfoListBean.getShopphoto();
//        Glide.with(this)
//                .load(mStoreinfoListBean.getShopphoto())
//                .override(600, 400)
//                .into(mIcon);
        mBanner.setImages(mStoreinfoListBean.getMiniList());
        mBanner.start();
        shopname = mStoreinfoListBean.getShopname();
        mName.setText(shopname);
        mJuli.setText(mStoreinfoListBean.getDistance());
        switch (mStoreinfoListBean.getTagsList().size()) {
            case 0:
                mShuaka.setVisibility(View.GONE);
                mWifi.setVisibility(View.GONE);
                mWeixinpay.setVisibility(View.GONE);
                mZhifubaopay.setVisibility(View.GONE);
                mTingchewei.setVisibility(View.GONE);
                break;
            case 1:
                mShuaka.setVisibility(View.VISIBLE);
                mWifi.setVisibility(View.GONE);
                mWeixinpay.setVisibility(View.GONE);
                mZhifubaopay.setVisibility(View.GONE);
                mTingchewei.setVisibility(View.GONE);
                mShuaka.setText(mStoreinfoListBean.getTagsList().get(0).getTags());
                break;
            case 2:
                mShuaka.setVisibility(View.VISIBLE);
                mWifi.setVisibility(View.VISIBLE);
                mWeixinpay.setVisibility(View.GONE);
                mZhifubaopay.setVisibility(View.GONE);
                mTingchewei.setVisibility(View.GONE);
                mShuaka.setText(mStoreinfoListBean.getTagsList().get(0).getTags());
                mWifi.setText(mStoreinfoListBean.getTagsList().get(1).getTags());
                break;
            case 3:
                mShuaka.setVisibility(View.VISIBLE);
                mWifi.setVisibility(View.VISIBLE);
                mWeixinpay.setVisibility(View.VISIBLE);
                mZhifubaopay.setVisibility(View.GONE);
                mTingchewei.setVisibility(View.GONE);
                mShuaka.setText(mStoreinfoListBean.getTagsList().get(0).getTags());
                mWifi.setText(mStoreinfoListBean.getTagsList().get(1).getTags());
                mWeixinpay.setText(mStoreinfoListBean.getTagsList().get(2).getTags());
                break;
            case 4:
                mShuaka.setVisibility(View.VISIBLE);
                mWifi.setVisibility(View.VISIBLE);
                mWeixinpay.setVisibility(View.VISIBLE);
                mZhifubaopay.setVisibility(View.VISIBLE);
                mTingchewei.setVisibility(View.GONE);
                mShuaka.setText(mStoreinfoListBean.getTagsList().get(0).getTags());
                mWifi.setText(mStoreinfoListBean.getTagsList().get(1).getTags());
                mWeixinpay.setText(mStoreinfoListBean.getTagsList().get(2).getTags());
                mZhifubaopay.setText(mStoreinfoListBean.getTagsList().get(3).getTags());
                break;
            case 5:
                mShuaka.setVisibility(View.VISIBLE);
                mWifi.setVisibility(View.VISIBLE);
                mWeixinpay.setVisibility(View.VISIBLE);
                mZhifubaopay.setVisibility(View.VISIBLE);
                mTingchewei.setVisibility(View.VISIBLE);
                mShuaka.setText(mStoreinfoListBean.getTagsList().get(0).getTags());
                mWifi.setText(mStoreinfoListBean.getTagsList().get(1).getTags());
                mWeixinpay.setText(mStoreinfoListBean.getTagsList().get(2).getTags());
                mZhifubaopay.setText(mStoreinfoListBean.getTagsList().get(3).getTags());
                mTingchewei.setText(mStoreinfoListBean.getTagsList().get(4).getTags());
                break;

        }
        if (mStoreinfoListBean.getIsshow().equals("")) {
            mMove.setVisibility(View.GONE);
        } else {
            mMove.setVisibility(View.VISIBLE);
            mMove.setText(mStoreinfoListBean.getIsshow());
        }
        button_url = mStoreinfoListBean.getBtnurl();
        mAddress.setText(mStoreinfoListBean.getAddrass());
        phone = mStoreinfoListBean.getMoblis();
        mStore_tese.setText(mStoreinfoListBean.getTese());
        mStore_kouhao.setText(mStoreinfoListBean.getShopculture());
        mStore_jieshao.setText(mStoreinfoListBean.getShopcontent());
        StoreInfo_Adapter adapter = new StoreInfo_Adapter(this, mStoreinfoListBean.getNearbyList());
//        LRecyclerViewAdapter LAdapter = new LRecyclerViewAdapter(this, adapter);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickLinener(new StoreInfo_Adapter.ItemClickLinener() {
            @Override
            public void OnItemClickLinener(View v, int position) {
                Intent intent = new Intent(StoreInfoActivity.this, StoreInfoActivity.class);
                intent.putExtra("id", mStoreinfoListBean.getNearbyList().get(position).getId());
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                startActivity(intent);
//                ARouter.getInstance().build("/activity/storeinfo")
//                        .withString("id", mStoreinfoListBean.getNearbyList().get(position).getId())
//                        .withString("lat", lat)
//                        .withString("lng", lng)
//                        .navigation();
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        }).start();
    }

    @Override
    public void data_error(String msg) {
        MyToast.showMsg(msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.storeinfo_back:
                finish();
                break;
            case R.id.storeinfo_phone:
                if (phone != null) {
                    call_phone();
                }
                break;
            case R.id.storeinfo_move:
//                Log.d(TAG, "onClick: " + button_url);
                if (UserData.islogin) {
                    Intent intent = new Intent(StoreInfoActivity.this, WebActivity.class);
                    intent.putExtra("web_url", button_url);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(StoreInfoActivity.this, LoginActivity.class));
                }
                break;
            case R.id.storeinfo_fenxiang://分享
//                showShare();
                break;
        }
    }


    private void call_phone() {
        if (Build.VERSION.SDK_INT >= 23) {
            int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 123);
            } else {
                Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                startActivity(intent2);
            }
        } else {
            Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            startActivity(intent2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 123:
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    call_phone();
                } else {
                    MyToast.showMsg("权限被禁止");
                }
                break;
        }
    }


//    private void showShare() {
//        OnekeyShare oks = new OnekeyShare();
////关闭sso授权
//        oks.disableSSOWhenAuthorize();
//// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
//        oks.setTitle(shopname);
//// titleUrl是标题的网络链接，QQ和QQ空间等使用
//        oks.setTitleUrl(Api.STORE_FENXIANG + Store_id);
//// text是分享文本，所有平台都需要这个字段
//        oks.setText(getResources().getString(R.string.share_text));
////        oks.setImagePath(imgurl);
//        oks.setImageUrl(imgurl);
//// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
////oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
//// url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl(Api.STORE_FENXIANG + Store_id);
//// comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
//// site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(getString(R.string.app_name));
//// siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl(Api.STORE_FENXIANG + Store_id);
//
//// 启动分享GUI
//        oks.show(StoreInfoActivity.this);
//    }
}
