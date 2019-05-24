package com.hechuang.hepay.ui.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hechuang.hepay.R;
import com.hechuang.hepay.adapter.Classify_rl_adapter;
import com.hechuang.hepay.adapter.UnionShop_Adapter;
import com.hechuang.hepay.adapter.ViewPagerAdapter;
import com.hechuang.hepay.base.BaseAppCompatActivity;
import com.hechuang.hepay.bean.Union_list_Bean;
import com.hechuang.hepay.bean.Union_top_banner_bean;
import com.hechuang.hepay.bean.Union_top_classify_bean;
import com.hechuang.hepay.bean.UserData;
import com.hechuang.hepay.persenter.UnionShopPersenter;
import com.hechuang.hepay.util.Eyes;
import com.hechuang.hepay.util.GlideImageLoader;
import com.hechuang.hepay.util.MyToast;
import com.hechuang.hepay.util.Utils;
import com.hechuang.hepay.view.IUnionShopView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 联盟商家
 * Created by Android_xu on 2018/3/3.
 */
public class UnionShop_Activity extends BaseAppCompatActivity<UnionShopPersenter> implements IUnionShopView, View.OnClickListener {
    @BindView(R.id.lrv_union_shop_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.union_shop_banner)
    Banner mBanner;
    @BindView(R.id.union_shop_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.union_shop_img)
    ImageView mImageView;
    @BindView(R.id.union_shop_viewpager_dian)
    LinearLayout mLinearLayout;
    @BindView(R.id.img_union_fiveimg_lift)
    ImageView fiveimg_lift;
    @BindView(R.id.img_union_fiveimg_right_tl)
    ImageView fiveimg_right_tl;
    @BindView(R.id.img_union_fiveimg_right_tr)
    ImageView fiveimg_right_tr;
    @BindView(R.id.img_union_fiveimg_right_bl)
    ImageView fiveimg_right_bl;
    @BindView(R.id.img_union_fiveimg_right_br)
    ImageView fiveimg_right_br;
    @BindView(R.id.union_shop_back)
    ImageView mBack;
    @BindView(R.id.union_shop_s_et)
    EditText mEt_s;
    @BindView(R.id.union_shop_s_iv)
    ImageView mIv_s;
    @BindView(R.id.union_shop_city_layout)
    LinearLayout mCity_layou;
    @BindView(R.id.union_shop_city_tv)
    TextView mCity_tv;
    @BindView(R.id.union_shop_fujin)
    LinearLayout ll_fujin;
    @BindView(R.id.union_shop_s_layout)
    LinearLayout s_layout;
    @BindView(R.id.tv_union_shop_noting)
    TextView mNoting;
    @BindView(R.id.layout_union_fiveimg)
    LinearLayout fiveimg_layout;
    private List<View> mPagerList;
    private List<Union_top_classify_bean.DataBean.ListBean> mDatas;
    private ImageView[] tips;
    private static final int UNION_SELECT_CITY = 1002;
    private int page;
    private String province = "";
    private String city = "";
    /**
     * 总的页数
     */
    private int pageCount;
    /**
     * 每一页显示的个数
     */
    private int pageSize = 10;
    /**
     * 当前显示的是第几页
     */
    private int curIndex = 0;
    private String s_str = "";

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

    }

    @Override
    protected int initlayout() {
        return R.layout.activity_union_shop;
    }

    @Override
    protected void initPersenter() {
        setMPersenter(new UnionShopPersenter(this, this));
    }

    private static final String TAG = "UnionShop_Activity";

    @Override
    protected void initView() {
        page = 1;
        ButterKnife.bind(this);

        Eyes.setStatusBarColor(this, ContextCompat.getColor(this, R.color.main_appbar));
        int window_w = Utils.getwindow_w(UnionShop_Activity.this);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mImageView.getLayoutParams();
        layoutParams.weight = LinearLayout.LayoutParams.MATCH_PARENT;
        layoutParams.height = window_w / 7;
        mImageView.setLayoutParams(layoutParams);
        mBanner.setImageLoader(new GlideImageLoader());
        LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) fiveimg_layout.getLayoutParams();
        layoutParams1.height = window_w / 5 * 2;
        fiveimg_layout.setLayoutParams(layoutParams1);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //轮播时间
        mBanner.setDelayTime(3000);
        //banner的动画样式
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        DisplayMetrics display = getResources().getDisplayMetrics();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(display.widthPixels, display.widthPixels / 3);
        mBanner.setLayoutParams(params);
        mBack.setOnClickListener(this);
//        mImageView.setOnClickListener(this);
        ll_fujin.setOnClickListener(this);
        mCity_layou.setOnClickListener(this);
        s_layout.setOnClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setVisibility(View.VISIBLE);
        mNoting.setVisibility(View.GONE);
        getMPersenter().getbanner2();
        getMPersenter().getclassify();
//        Log.d(TAG, "initView: " + UserData.province + UserData.city);
        getMPersenter().getlistdata("", "", "", UserData.province, UserData.city, "", page, "", "", "");
        mEt_s.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                s_str = mEt_s.getText().toString();
            }
        });
        city = UserData.city;
        if (city.equals("") || city.equals("null")) {
            mCity_tv.setText("切换城市");
        } else {
            mCity_tv.setText(city);
        }

    }

    private void setclassifyimg() {
        pageCount = (int) Math.ceil(mDatas.size() * 1.0 / pageSize);
        mPagerList = new ArrayList<View>();
        for (int i = 0; i < pageCount; i++) {
            // 每个页面都是inflate出一个新实例
            RecyclerView recyclerView = new RecyclerView(this);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, pageSize / 2);
            recyclerView.setLayoutManager(gridLayoutManager);
            Classify_rl_adapter adapter = new Classify_rl_adapter(this, mDatas, i, pageSize);
            recyclerView.setAdapter(adapter);
            mPagerList.add(recyclerView);
            adapter.setOnitemlatener(p -> {
                int pos = p + curIndex * pageSize;
                Intent intent = new Intent(UnionShop_Activity.this, UnionShopActivity.class);
                intent.putExtra("city", city);
                intent.putExtra("id", mDatas.get(pos).getId());
                intent.putExtra("province", province);
                intent.putExtra("name", mDatas.get(pos).getName());
                startActivity(intent);

            });
        }
        //设置适配器
        mViewPager.setAdapter(new ViewPagerAdapter(mPagerList));
        //设置圆点
        setOvalLayout();
    }


    /**
     * 设置圆点
     */
    public void setOvalLayout() {
        tips = new ImageView[pageCount];
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(this);
            tips[i] = imageView;
            if (i == 0) {
                Glide.with(this).load(R.drawable.dot_selected).into(tips[i]);
            } else {
                Glide.with(this).load(R.drawable.dot_normal).into(tips[i]);
            }
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(new ViewGroup.LayoutParams(40,
                    40));
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
            mLinearLayout.addView(imageView, layoutParams);
        }
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                curIndex = position;
                setImageBackground(position);
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });

    }

    /**
     * 设置选中的tip的背景
     *
     * @param selectItems
     */
    private void setImageBackground(int selectItems) {
        for (int i = 0; i < tips.length; i++) {
            if (i == selectItems) {
                Glide.with(this)
                        .load(R.drawable.dot_selected)
                        .override(300, 300)
                        .into(tips[i]);
            } else {
                Glide.with(this)
                        .load(R.drawable.dot_normal)
                        .override(300, 300)
                        .into(tips[i]);
            }
        }
    }

    int danzhang_style;
    String danzhang_url;
    String danzhang_urlid;

    @Override
    public void getbanner_data(Union_top_banner_bean union_top_banner_bean) {
        List<String> imgs = new ArrayList<>();
        for (int i = 0; i < union_top_banner_bean.getData().getBanner().size(); i++) {
            imgs.add(union_top_banner_bean.getData().getBanner().get(i).getImgs());
//            Log.d("unionshop", union_top_banner_bean.getData().getBanner().get(i).getImgs());
        }
        mBanner.setImages(imgs);
        mBanner.setOnBannerListener(position ->
                {
                    switch (union_top_banner_bean.getData().getBanner().get(position).getStyle()) {
                        case 0://无连接
                            break;
                        case 1://商家
//                            ARouter.getInstance().build("/activity/storeinfo")
//                                    .withString("lat", UserData.latitude)
//                                    .withString("lng", UserData.lontitude).
//                                    withString("id", union_top_banner_bean.getData().getBanner().get(position).getUrlid())
//                                    .navigation();
                            Intent intent = new Intent(UnionShop_Activity.this, StoreInfoActivity.class);
                            intent.putExtra("lat", UserData.latitude);
                            intent.putExtra("lng", UserData.lontitude);
                            intent.putExtra("id", union_top_banner_bean.getData().getBanner().get(position).getUrlid());
                            startActivity(intent);
                            break;
                        case 2://自定义
//                            ARouter.getInstance().build("/activity/web")
//                                    .withString("web_url", union_top_banner_bean.getData().getBanner().get(position).getUrl()).navigation();
                            Intent intent1 = new Intent(UnionShop_Activity.this, WebActivity.class);
                            intent1.putExtra("web_url", union_top_banner_bean.getData().getBanner().get(position).getUrl());
                            startActivity(intent1);
                            break;
                    }
                }
        );
        mBanner.start();
        danzhang_style = union_top_banner_bean.getData().getAd().get(0).getStyle();
        danzhang_urlid = union_top_banner_bean.getData().getAd().get(0).getUrlid();
        danzhang_url = union_top_banner_bean.getData().getAd().get(0).getUrl();
        Glide.with(this).load(union_top_banner_bean.getData().getAd().get(0).getImgs()).into(mImageView);
        Glide.with(this).load(union_top_banner_bean.getData().getShopimg().get(0).getImgs()).into(fiveimg_lift);
        Glide.with(this).load(union_top_banner_bean.getData().getShopimg().get(1).getImgs()).into(fiveimg_right_tl);
        Glide.with(this).load(union_top_banner_bean.getData().getShopimg().get(2).getImgs()).into(fiveimg_right_tr);
        Glide.with(this).load(union_top_banner_bean.getData().getShopimg().get(3).getImgs()).into(fiveimg_right_bl);
        Glide.with(this).load(union_top_banner_bean.getData().getShopimg().get(4).getImgs()).into(fiveimg_right_br);
        mImageView.setOnClickListener((v -> {
            switch (danzhang_style) {
                case 0://无连接
                    break;
                case 1://商家
//                    ARouter.getInstance().build("/activity/storeinfo")
//                            .withString("lat", UserData.latitude)
//                            .withString("lng", UserData.lontitude).withString("id", )
//                            .navigation();

                    Intent intent = new Intent(UnionShop_Activity.this, StoreInfoActivity.class);
                    intent.putExtra("lat", UserData.latitude);
                    intent.putExtra("lng", UserData.lontitude);
                    intent.putExtra("id", danzhang_urlid);
                    startActivity(intent);
                    break;
                case 2://自定义
                    Intent intent1 = new Intent(UnionShop_Activity.this, WebActivity.class);
                    intent1.putExtra("web_url", danzhang_url);
                    startActivity(intent1);
//                    ARouter.getInstance().build("/activity/web")
//                            .withString("url", danzhang_url).navigation();
                    break;
            }
        }));
        fiveimg_lift.setOnClickListener(v -> {
            switch (union_top_banner_bean.getData().getShopimg().get(0).getStyle()) {
                case 0://无连接
                    break;
                case 1://商家
//                    ARouter.getInstance().build("/activity/storeinfo")
//                            .withString("lat", UserData.latitude)
//                            .withString("lng", UserData.lontitude).withString("id", union_top_banner_bean.getData().getShopimg().get(0).getUrlid())
//                            .navigation();


                    Intent intent = new Intent(UnionShop_Activity.this, StoreInfoActivity.class);
                    intent.putExtra("lat", UserData.latitude);
                    intent.putExtra("lng", UserData.lontitude);
                    intent.putExtra("id", union_top_banner_bean.getData().getShopimg().get(0).getUrlid());
                    startActivity(intent);
                    break;
                case 2://自定义

                    Intent intent1 = new Intent(UnionShop_Activity.this, WebActivity.class);
                    intent1.putExtra("web_url", union_top_banner_bean.getData().getShopimg().get(0).getUrl());
                    startActivity(intent1);
//                    ARouter.getInstance().build("/activity/web")
//                            .withString("url", union_top_banner_bean.getData().getShopimg().get(0).getUrl()).navigation();
                    break;
            }
        });
        fiveimg_right_tl.setOnClickListener(v -> {
            switch (union_top_banner_bean.getData().getShopimg().get(1).getStyle()) {
                case 0://无连接
                    break;
                case 1://商家
//                    ARouter.getInstance().build("/activity/storeinfo")
//                            .withString("lat", UserData.latitude)
//                            .withString("lng", UserData.lontitude).withString("id", union_top_banner_bean.getData().getShopimg().get(1).getUrlid())
//                            .navigation();

                    Intent intent = new Intent(UnionShop_Activity.this, StoreInfoActivity.class);
                    intent.putExtra("lat", UserData.latitude);
                    intent.putExtra("lng", UserData.lontitude);
                    intent.putExtra("id", union_top_banner_bean.getData().getShopimg().get(1).getUrlid());
                    startActivity(intent);
                    break;
                case 2://自定义

                    Intent intent1 = new Intent(UnionShop_Activity.this, WebActivity.class);
                    intent1.putExtra("web_url", union_top_banner_bean.getData().getShopimg().get(1).getUrl());
                    startActivity(intent1);
//                    ARouter.getInstance().build("/activity/web")
//                            .withString("url", union_top_banner_bean.getData().getShopimg().get(1).getUrl()).navigation();
                    break;
            }
        });
        fiveimg_right_tr.setOnClickListener(v -> {
            switch (union_top_banner_bean.getData().getShopimg().get(2).getStyle()) {
                case 0://无连接
                    break;
                case 1://商家
//                    ARouter.getInstance().build("/activity/storeinfo")
//                            .withString("lat", UserData.latitude)
//                            .withString("lng", UserData.lontitude).withString("id", union_top_banner_bean.getData().getShopimg().get(2).getUrlid())
//                            .navigation();

                    Intent intent = new Intent(UnionShop_Activity.this, StoreInfoActivity.class);
                    intent.putExtra("lat", UserData.latitude);
                    intent.putExtra("lng", UserData.lontitude);
                    intent.putExtra("id", union_top_banner_bean.getData().getShopimg().get(2).getUrlid());
                    startActivity(intent);
                    break;
                case 2://自定义

                    Intent intent1 = new Intent(UnionShop_Activity.this, WebActivity.class);
                    intent1.putExtra("web_url", union_top_banner_bean.getData().getShopimg().get(2).getUrl());
                    startActivity(intent1);
//                    ARouter.getInstance().build("/activity/web")
//                            .withString("url", union_top_banner_bean.getData().getShopimg().get(2).getUrl()).navigation();
                    break;
            }
        });
        fiveimg_right_bl.setOnClickListener(v -> {
            switch (union_top_banner_bean.getData().getShopimg().get(3).getStyle()) {
                case 0://无连接
                    break;
                case 1://商家
//                    ARouter.getInstance().build("/activity/storeinfo")
//                            .withString("lat", UserData.latitude)
//                            .withString("lng", UserData.lontitude).withString("id", union_top_banner_bean.getData().getShopimg().get(3).getUrlid())
//                            .navigation();

                    Intent intent = new Intent(UnionShop_Activity.this, StoreInfoActivity.class);
                    intent.putExtra("lat", UserData.latitude);
                    intent.putExtra("lng", UserData.lontitude);
                    intent.putExtra("id", union_top_banner_bean.getData().getShopimg().get(3).getUrlid());
                    startActivity(intent);
                    break;
                case 2://自定义

                    Intent intent1 = new Intent(UnionShop_Activity.this, WebActivity.class);
                    intent1.putExtra("web_url", union_top_banner_bean.getData().getShopimg().get(3).getUrl());
                    startActivity(intent1);
//                    ARouter.getInstance().build("/activity/web")
//                            .withString("url", union_top_banner_bean.getData().getShopimg().get(3).getUrl()).navigation();
                    break;
            }
        });
        fiveimg_right_br.setOnClickListener(v -> {
            switch (union_top_banner_bean.getData().getShopimg().get(4).getStyle()) {
                case 0://无连接
                    break;
                case 1://商家
//                    ARouter.getInstance().build("/activity/storeinfo")
//                            .withString("lat", UserData.latitude)
//                            .withString("lng", UserData.lontitude).withString("id", union_top_banner_bean.getData().getShopimg().get(4).getUrlid())
//                            .navigation();

                    Intent intent = new Intent(UnionShop_Activity.this, StoreInfoActivity.class);
                    intent.putExtra("lat", UserData.latitude);
                    intent.putExtra("lng", UserData.lontitude);
                    intent.putExtra("id", union_top_banner_bean.getData().getShopimg().get(4).getUrlid());
                    startActivity(intent);
                    break;
                case 2://自定义

                    Intent intent1 = new Intent(UnionShop_Activity.this, WebActivity.class);
                    intent1.putExtra("web_url", union_top_banner_bean.getData().getShopimg().get(4).getUrl());
                    startActivity(intent1);
//                    ARouter.getInstance().build("/activity/web")
//                            .withString("url", union_top_banner_bean.getData().getShopimg().get(4).getUrl()).navigation();
                    break;
            }
        });
    }

    @Override
    public void getclassify_data(Union_top_classify_bean union_top_classify_bean) {
        mDatas = union_top_classify_bean.getData().getList();
        setclassifyimg();
    }

    @Override
    public void getlist_data(List<Union_list_Bean.DataBean.ListBean> mlistdata) {
        if (mlistdata != null && mlistdata.size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mNoting.setVisibility(View.GONE);
            UnionShop_Adapter adapter = new UnionShop_Adapter(mlistdata, this, (id, position) -> {
                Intent intent = new Intent(UnionShop_Activity.this, StoreInfoActivity.class);
                intent.putExtra("lat", UserData.latitude);
                intent.putExtra("lng", UserData.lontitude);
                intent.putExtra("id", mlistdata.get(position).getID());
                startActivity(intent);
            });
            mRecyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void setinitview(ArrayList<String> discrictList) {

    }

    @Override
    public void setunview(ArrayList<String> discrictList) {

    }

    @Override
    public void getdataerror_list() {
        mNoting.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == UNION_SELECT_CITY) {
            switch (resultCode) {
                case RESULT_OK:
                    province = intent.getStringExtra("province");
//                    final String selectID = intent.getStringExtra("regionID");
                    city = intent.getStringExtra("city");
                    final String selectDistrict = intent.getStringExtra("district");
//                    province = selectProvince;
//                    city = selectCity;
                    mCity_tv.setText(city);
                    getMPersenter().getlistdata("", "", "", province, city, selectDistrict, 1, "", "", "");
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.union_shop_back://返回
                finish();
                break;
            case R.id.union_shop_s_iv://搜索-放大镜
                if (s_str.equals("")) {
                    MyToast.showMsg("请输入搜索的商家");
                } else {
                    getMPersenter().getlistdata("", "", s_str, "", "", "", 1, "", "", "");
                }
                break;
            case R.id.union_shop_city_layout://城市选择
                startActivityForResult(new Intent(UnionShop_Activity.this, Union_SelectCityActivity.class), UNION_SELECT_CITY);
//                ARouter.getInstance().build("/activity/unionselectcity").navigation(this, UNION_SELECT_CITY);
                break;
            case R.id.union_shop_fujin:
//                ARouter.getInstance().build("/activity/unionshop1")
//                        .withString("province", province).withString("city", city).navigation();
                Intent intent = new Intent(UnionShop_Activity.this, UnionShopActivity.class);
                intent.putExtra("province", province);
                intent.putExtra("city", city);
                startActivity(intent);
                break;
            case R.id.union_shop_s_layout:
//                ARouter.getInstance().build("/activity/unionshop1")
//                        .withString("province", province).withString("city", city).navigation();
                Intent intent1 = new Intent(UnionShop_Activity.this, UnionShopActivity.class);
                intent1.putExtra("province", province);
                intent1.putExtra("city", city);
                startActivity(intent1);
                break;
//            case R.id.union_shop_img://单张广告图
//                if (danzhang_url == null || danzhang_urlid == null) {
//                    return;
//                } else {
//                    switch (danzhang_style) {
//                        case 0://无连接
//                            break;
//                        case 1://商家
//                            ARouter.getInstance().build("/activity/storeinfo")
//                                    .withString("lat", UserData.latitude)
//                                    .withString("lng", UserData.lontitude).withString("id", danzhang_urlid)
//                                    .navigation();
//                            break;
//                        case 2://自定义
//                            ARouter.getInstance().build("/activity/web")
//                                    .withString("url", danzhang_url).navigation();
//                            break;
//                    }
//                    break;
//                }
        }
    }

}
