package com.hechuang.hepay.ui.activity;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.hechuang.hepay.R;
import com.hechuang.hepay.adapter.UnionShop_Adapter;
import com.hechuang.hepay.api.ApiFactory;
import com.hechuang.hepay.api.MyOkHttp;
import com.hechuang.hepay.api.PathConstant;
import com.hechuang.hepay.base.BaseAppCompatActivity;
import com.hechuang.hepay.bean.FastShopBase;
import com.hechuang.hepay.bean.Fastshop_titleBean;
import com.hechuang.hepay.bean.Union_list_Bean;
import com.hechuang.hepay.bean.Union_top_banner_bean;
import com.hechuang.hepay.bean.Union_top_classify_bean;
import com.hechuang.hepay.bean.UserData;
import com.hechuang.hepay.customview.ViewLeft;
import com.hechuang.hepay.customview.ViewMiddle;
import com.hechuang.hepay.customview.ViewRight;
import com.hechuang.hepay.customview.Viewsaixuan;
import com.hechuang.hepay.persenter.UnionShopPersenter;
import com.hechuang.hepay.util.KeyBoardUtils;
import com.hechuang.hepay.util.MyToast;
import com.hechuang.hepay.view.IUnionShopView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;

/**
 * 联盟商家（搜索）
 * Created by Android_xu on 2018/2/4.
 */

public class UnionShopActivity extends BaseAppCompatActivity<UnionShopPersenter> implements IUnionShopView, View.OnClickListener, OnRefreshListener, OnLoadMoreListener, OnNetWorkErrorListener {
    @BindView(R.id.unionshop_back)
    LinearLayout mBack;
    @BindView(R.id.unionshop_back_text)
    TextView mBack_text;
    @BindView(R.id.unionshop_title_city_text)
    TextView location_text;
    @BindView(R.id.unionshop_gray_nothing)
    TextView mNothing;
    @BindView(R.id.unionshop_LRecyclerView)
    LRecyclerView mRecyclerView;
    @BindView(R.id.unionshop_city_layout)
    LinearLayout city_layout;
    @BindView(R.id.unionshop_s_et)
    EditText mEt_s;
    @BindView(R.id.unionshop_s_iv)
    ImageView mIv_s;
    //    @BindView(R.id.unionshop_expandtab_view)
//    ExpandTabView mExpandTabView;
    private ArrayList<View> mViewArray = null;
    private ViewLeft viewLeft;
    private ViewMiddle viewMiddle;
    private ViewRight viewRight;
    private Viewsaixuan viewsaixuan;
    private List<String> search_list = new ArrayList<>();
    private String search_str;
    String buxian = "附近";
    //    EditText search_edit;
    private static final int SELECT_CITY = 1001;
    ArrayList<String> mTextArray;
    private UnionShop_Adapter adapter;
    private LRecyclerViewAdapter MyAdapter;
    List<Fastshop_titleBean> fastshop_titlelist = null;
    private ArrayList<String> discrictList;
    private int page = 1;
    String z_id = "";
    String qid = "";
    String hit = "";
    String shareaccounths = "";
    //------定位----
    private String province = "";
    private String discrict = "";
    //    private String provinceId = "";
    private String city = "";
    //    private String cityid = "";
    String keywords = "";
    public UnionShopActivity mUnionShopActivity;
    List<FastShopBase> listshop;
    private String latitude = "";
    private String lontitude = "";
    //    SQLiteDatabase db2;
    private String name = "";
    private String classify_id;

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
        return R.layout.activity_unionshop;
    }

    @Override
    protected void initPersenter() {
        setMPersenter(new UnionShopPersenter(this, this));
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mUnionShopActivity = this;
        city = getIntent().getStringExtra("city");
        classify_id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        province = getIntent().getStringExtra("province");
        discrict = getIntent().getStringExtra("discrict");
        Log.d("unionshop", city + "  " + province + "  " + discrict);
        if (name == null) {
            name = "";
        }
        if (classify_id == null) {
            classify_id = "";
        }
        if (city == null) {
            city = "";
        }
        if (province == null) province = "";
        if (discrict == null) discrict = "";
        getMPersenter().getclassify();
        mEt_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UnionShopActivity.this, UserWebActivity.class);
                intent.putExtra("web_url", PathConstant.BUSINESS + "index.php/Home/Product/search");
                startActivity(intent);
            }
        });
//        Log.d(TAG, "initView: " + province);
        getMPersenter().getlistdata("", "", keywords, province, city, discrict, 1, classify_id, "", "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.unionshop_back:
                finish();
                break;
            case R.id.unionshop_city_layout:
                startActivityForResult(new Intent(UnionShopActivity.this, Union_SelectCityActivity.class), SELECT_CITY);
                break;
            case R.id.unionshop_s_iv://搜索-放大镜  跳转到搜索页面
                Intent intent = new Intent(UnionShopActivity.this, UserWebActivity.class);
                intent.putExtra("web_url", ApiFactory.HOST + "index.php/Home/Product/search");
                startActivity(intent);
//                KeyBoardUtils.closeKeyBoard(mEt_s);
//                if (keywords.equals("")) {
//                    MyToast.showMsg("请输入搜索的商家");
//                } else {
//                    getMPersenter().getlistdata("", "", keywords, province, city, "", 1, classify_id, "", "");
//                }
                break;
        }
    }

    @Override
    public void onRefresh() {//下拉刷新
        mRecyclerView.setNoMore(false);
        mRecyclerView.setLoadMoreEnabled(true);
        page = 1;
//        Log.d(TAG, "onRefresh: " + page);
        getMPersenter().getlistdata("", "", keywords, province, city, discrict, page, classify_id, "", "");
    }

    @Override
    public void onLoadMore() {//加载更多
        page = page + 1;
//        Log.d(TAG, "onLoadMore: " + page);
        getMPersenter().getlistdata("", "", keywords, province, city, discrict, page, classify_id, "", "");
    }

    @Override
    public void reload() {//错误
    }

//    /**
//     * 获取商家模糊搜索数据
//     */
//
//    private void gettitledata() {
//        MyOkHttp.getInstance().get(ApiFactory.HSHC_HOST + "Api/Index/indexImg.php?act=category", new MyOkHttp.RequestCallBack() {
//            @Override
//            public void success(String data) {
//                fastshop_titlelist = new ArrayList<Fastshop_titleBean>();
//                Fastshop_titleBean titlelistbean = null;
//                try {
//                    JSONObject json = new JSONObject(data);
//                    JSONObject datas = json.getJSONObject("data");
//                    String status = String.valueOf(datas.get("status"));
//                    String msg = String.valueOf(datas.get("msg"));
//                    if (status.equals("1")) {
//                        JSONArray cateInfo = datas.getJSONArray("list");
//                        if (cateInfo != null) {
//                            for (int i = 0; i < cateInfo.length(); i++) {
//                                JSONObject list = cateInfo.getJSONObject(i);
//                                String id = String.valueOf(list.get("Id"));
//                                String name = String.valueOf(list.get("name"));
//                                String pid = String.valueOf(list.get("pid"));
//                                String oid = String.valueOf(list.get("oid"));
//                                String Lay = String.valueOf(list.get("Lay"));
//                                String IdPath = String.valueOf(list.get("IdPath"));
//                                String disable = String.valueOf(list.get("disable"));
//                                String sort = String.valueOf(list.get("sort"));
//                                titlelistbean = new Fastshop_titleBean(id, name, pid, oid, Lay, IdPath, disable, sort);
//                                fastshop_titlelist.add(titlelistbean);
//                            }
//                        }
//                    }
//                } catch (JSONException e) {
//                }
//                jiazaiview();
//            }
//
//            @Override
//            public void fail(Request request, Exception e) {
//
//            }
//        }, mLoading);
//    }


//    private void initListener() {
//        viewLeft.setOnSelectListener(new ViewLeft.OnSelectListener() {
//            @Override
//            public void getValue(String distance, String showText) {
//                if (distance.equals("0")) {
//                    z_id = "";
//                } else {
//                    z_id = distance;
//                }
//                page = 1;
//                onRefresh(viewLeft, showText);
//                mBack_text.setText(showText);
//            }
//        });
//        if (viewMiddle != null) {
//            viewMiddle.setOnSelectListener(new ViewMiddle.OnSelectListener() {
//
//                @Override
//                public void getValue(String lift) {
//                    if (lift.equals("附近")) {
//                        qid = "";
//                    } else {
//                        qid = lift;
//                    }
//                    page = 1;
//                    mExpandTabView.onPressBack();
//                    int position = getPositon(viewMiddle);
//                    if (position >= 0 && !mExpandTabView.getTitle(position).equals(lift)) {
//                        mExpandTabView.setTitle(lift, position);
//                    }
//                    mEt_s.setText("");
////                    Log.d(TAG, "getValue: " + lift);
//                    getMPersenter().getlistdata("", "", keywords, province, city, lift, page, classify_id, "", "");
//                }
//            });
//        }
//        viewRight.setOnSelectListener(new ViewRight.OnSelectListener() {
//            @Override
//            public void getValue(String distance, String showText) {
//                page = 1;
//                int position = getPositon(viewRight);
//                if (position >= 0 && !mExpandTabView.getTitle(position).equals(showText)) {
//                    mExpandTabView.setTitle(showText, position);
//                }
//                mExpandTabView.onPressBack();
//                switch (showText) {
//                    case "人气":
//                        getMPersenter().getlistdata("", "", keywords, province, city, "", page, classify_id, "1", "");
//                        break;
//                    case "距离":
//                        getMPersenter().getlistdata("", "", keywords, province, city, "", page, classify_id, "", "1");
//                        break;
//                }
//                mEt_s.setText("");
//            }
//        });
//        viewsaixuan.setOnSelectListener(new Viewsaixuan.OnSelectListener() {
//            @Override
//            public void getValue(String distance, String showText) {
//                shareaccounths = "con=" + distance;
//                page = 1;
//                onRefresh(viewsaixuan, showText);
//            }
//        });
//    }

//    private void onRefresh(View view, String showText) {
//        mExpandTabView.onPressBack();
//        int position = getPositon(view);
//        if (position >= 0 && !mExpandTabView.getTitle(position).equals(showText)) {
//            mExpandTabView.setTitle(showText, position);
//        }
////        keywords = showText;
//        if (keywords.equals("全部")) {
//            classify_id = "";
//        } else {
//            for (int i = 0; i < mUnion_top_classify_bean.getData().getList().size(); i++) {
//                if (mUnion_top_classify_bean.getData().getList().get(i).getName().equals(showText)) {
//                    classify_id = mUnion_top_classify_bean.getData().getList().get(i).getId();
//                    getMPersenter().getlistdata("", "", keywords, province, city, "", page, classify_id, "", "");
//
//                }
//            }
//        }
//
//        mEt_s.setText("");
//
//    }

//    private int getPositon(View tView) {
//        for (int i = 0; i < mViewArray.size(); i++) {
//            if (mViewArray.get(i) == tView) {
//                return i;
//            }
//        }
//        return -1;
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == SELECT_CITY) {
            switch (resultCode) {
                case RESULT_OK:
                    final String selectProvince = intent.getStringExtra("province");
//                    final String selectID = intent.getStringExtra("regionID");
                    final String selectCity = intent.getStringExtra("city");
                    final String selectDistrict = intent.getStringExtra("district");
//                    final String selectProvinceId = intent.getStringExtra("provinceId");
                    province = selectProvince;
                    city = selectCity;
//                    provinceId = selectProvinceId;
                    address_maps(city, selectDistrict);
                    location_text.setText(city);
                    getMPersenter().postcount(city, 2);
                    getMPersenter().getlistdata("", "", "", province, city, discrict, 1, "", "", "");
                    break;
            }
        }
    }

    private static final String TAG = "UnionShopActivity";

    /**
     * 地理位置转为坐标系
     *
     * @return location :location[0] = lontitude  location[1]=latitude
     */
    public void address_maps(String city, String discrict) {
        String url = ApiFactory.ADDERS_MAPS + "&address=" + discrict + "&city=" + city + "&ak=" + UserData.baidu_ak;
        MyOkHttp.getInstance().get(url, new MyOkHttp.RequestCallBack() {
            @Override
            public void success(String data) {
                String data1 = data.replace("renderOption&&renderOption({", "{");
                String data2 = data1.replace("})", "}");
                try {
                    JSONObject json = new JSONObject(data2);
                    String status = String.valueOf(json.get("status"));
                    if (status.equals("0")) {
                        JSONObject result = json.getJSONObject("result");
                        JSONObject item = result.getJSONObject("location");
                        lontitude = String.valueOf(item.get("lng"));
                        latitude = String.valueOf(item.get("lat"));
                    } else {

                    }
                } catch (JSONException e) {
                }
            }

            @Override
            public void fail(Request request, Exception e) {

            }
        }, null);
    }


    @Override
    public void getbanner_data(Union_top_banner_bean union_top_banner_bean) {
    }

    Union_top_classify_bean mUnion_top_classify_bean;

    @Override
    public void getclassify_data(Union_top_classify_bean union_top_classify_bean) {
        //TODO
        mUnion_top_classify_bean = union_top_classify_bean;
        mBack.setOnClickListener(this);
        location_text.setText(city);
        mBack_text.setText(name);
        city_layout.setOnClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));// 添加分割线。
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setOnRefreshListener(this);
        mRecyclerView.setOnLoadMoreListener(this);
//        mRecyclerView.setOnNetWorkErrorListener(this);
        //设置头部加载颜色
        mRecyclerView.setHeaderViewColor(R.color.appbar, R.color.black_overlay, android.R.color.white);
        //设置底部加载颜色
        mRecyclerView.setFooterViewColor(R.color.appbar, R.color.black_overlay, android.R.color.white);
        //设置底部加载文字提示
        mRecyclerView.setFooterViewHint(UserData.recycler_loadinghint, UserData.recycler_nomorehint, UserData.recycler_nonetworkhint);
        mIv_s.setOnClickListener(this);
        mEt_s.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                keywords = mEt_s.getText().toString();
            }
        });
        mEt_s.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                keywords = mEt_s.getText().toString().trim();
                KeyBoardUtils.closeKeyBoard(mEt_s);
                if (keywords.equals("")) {
                    MyToast.showMsg("请输入搜索的商家");
                } else {
                    getMPersenter().getlistdata(UserData.lontitude, UserData.latitude, keywords, province, city, discrict, 1, classify_id, "", "");
                }
                return true;
            }
            return false;
        });
        getMPersenter().postcount(city, 1);
        getMPersenter().getlistdata(UserData.lontitude, UserData.latitude, keywords, province, city, discrict, page, classify_id, "", "");
    }

    @Override
    public void getlist_data(List<Union_list_Bean.DataBean.ListBean> mlistdata) {
        if (adapter == null) {
            adapter = new UnionShop_Adapter(mlistdata, this, null);
            MyAdapter = new LRecyclerViewAdapter(adapter);
            mRecyclerView.setAdapter(MyAdapter);
        } else {
            adapter.notifyDataSetChanged();
            MyAdapter.notifyDataSetChanged();
        }
        if (mlistdata != null && mlistdata.size() > 0) {
            mNothing.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        } else if (mlistdata.size() < 5) {
            mNothing.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mRecyclerView.setLoadMoreEnabled(false);
            mRecyclerView.setNoMore(true);
        } else {
            if (page == 1) {
                mNothing.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            } else {
                mNothing.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        }
        mRecyclerView.refreshComplete(10);
        MyAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d(TAG, "onItemClick: " +
                        mlistdata.get(position).getCategoryID() + "   " + mlistdata.get(position).getCategoryname()
                        + "   " + mlistdata.get(position).getID()
                        + "    " + mlistdata.get(position).getID()
                );
//                ARouter.getInstance().build("/activity/storeinfo")
//                        .withString("id", mlistdata.get(position).getID())
//                        .withString("lat", UserData.latitude)
//                        .withString("lng", UserData.lontitude)
//                        .navigation();
                Intent intent = new Intent(UnionShopActivity.this, BusinessActivity.class);
//                intent.putExtra("lat", UserData.latitude);
//                intent.putExtra("lng", UserData.lontitude);
                intent.putExtra("shopid", mlistdata.get(position).getID());
                intent.putExtra("shopname", mlistdata.get(position).getShopName());
                startActivity(intent);
            }
        });
    }

    @Override
    public void setinitview(ArrayList<String> discrictLists) {
//        if (mViewArray == null) mViewArray = new ArrayList<>();
//        else mViewArray.clear();
//        if (mTextArray == null) mTextArray = new ArrayList<String>();
//        else mTextArray.clear();
//        discrictList = discrictLists;
//        discrictList.add(0, buxian);
//        viewMiddle = new ViewMiddle(this, discrictList);
//        Union_top_classify_bean.DataBean.ListBean mUn_classify = new Union_top_classify_bean.DataBean.ListBean("0", "全部", "", "");
//        mUnion_top_classify_bean.getData().getList().add(0, mUn_classify);
//        viewLeft = new ViewLeft(this, mUnion_top_classify_bean.getData().getList());
//        viewRight = new ViewRight(this);
//        viewsaixuan = new Viewsaixuan(this);
//
//        mViewArray.add(viewLeft);
//        if (viewMiddle != null) {
//            mViewArray.add(viewMiddle);
//            if (name.equals("")) {
//                mTextArray.add("全部");
//            } else {
//                mTextArray.add(name);
//            }
//        }
//        mViewArray.add(viewRight);
//        mTextArray.add("附近");
//        mTextArray.add("智能排序");
//        mExpandTabView.setValue(mTextArray, mViewArray);
//        mExpandTabView.setviewlog(mExpandTabView);
//        initListener();
    }

    @Override
    public void setunview(ArrayList<String> discrictLists) {
//        if (mViewArray == null) mViewArray = new ArrayList<>();
//        else mViewArray.clear();
//        if (mTextArray == null) mTextArray = new ArrayList<String>();
//        else mTextArray.clear();
//        discrictList = discrictLists;
//        discrictList.add(0, buxian);
//        viewMiddle.setdata(discrictList);
//        mExpandTabView.setTitle(fastshop_titlelist.get(0).getName(), 0);
//        mExpandTabView.setTitle(name, 1);
//        mExpandTabView.setTitle("智能排序", 2);
//        mExpandTabView.setTitle("筛选", 3);
//        qid = "";
//        z_id = "";
//        hit = "";
//        page = 1;
//        shareaccounths = "";
    }

    @Override
    public void getdataerror_list() {
        if (page == 1) {
            mNothing.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mRecyclerView.setNoMore(true);
            mRecyclerView.setLoadMoreEnabled(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        KeyBoardUtils.closeKeyBoard(mEt_s);
    }
}

