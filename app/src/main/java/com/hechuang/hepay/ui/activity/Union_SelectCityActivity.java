package com.hechuang.hepay.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hechuang.hepay.R;
import com.hechuang.hepay.adapter.SelectCityAdapter;
import com.hechuang.hepay.base.BaseAppCompatActivity;
import com.hechuang.hepay.bean.SelectCityBean;
import com.hechuang.hepay.customview.RecycleViewDivider;
import com.hechuang.hepay.persenter.SelectVityPersenter;
import com.hechuang.hepay.view.ISelectCityView;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 切换城市
 */
public class Union_SelectCityActivity extends BaseAppCompatActivity<SelectVityPersenter> implements ISelectCityView, SelectCityAdapter.OnSelectCityItmeListener, View.OnClickListener {
    @BindView(R.id.userinfocity_Province)
    RecyclerView mRecyclerView;
    @BindView(R.id.userinfocity_back)
    ImageView mBack;
    @BindView(R.id.userinfocity_title)
    TextView mTitle;
    private String province = "";
    private String city = "";
    private SelectCityAdapter province_adapter;
    private SelectCityAdapter city_adapter;
    private SelectCityAdapter district_adapter;
    private int cishu = 1;

    @Override
    protected int initlayout() {
        return R.layout.activity_selectcity;
    }

    @Override
    protected void initPersenter() {

        setMPersenter(new SelectVityPersenter(this, this));
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(this, 1));
        mTitle.setOnClickListener(this);
        mBack.setOnClickListener(this);
        getMPersenter().getProvince();
    }


    private void settwocity(String name) {
        province = name;
        getMPersenter().getCity(name);
    }

    private void setthreecity(String name) {
        city = name;
        getMPersenter().getCount(name);
    }

    @Override
    public void onClick(View view) {
        cishu = cishu - 1;
        switch (cishu) {
            case 2:
                settwocity(province);
                break;
            case 1:
                getMPersenter().getProvince();
                break;
            case 0:
                finish();
                break;
        }
    }

    private static final String TAG = "UserinfoSelectCityActiv";

    @Override
    public void OnSelectCityItmeListener(int position, String name) {
        cishu = cishu + 1;
        switch (cishu) {
            case 2:
                settwocity(name);
                break;
            case 3:
                setthreecity(name);
                Intent data = new Intent();
                data.putExtra("city", city);
                data.putExtra("province", province);
                setResult(RESULT_OK, data);
                Union_SelectCityActivity.this.finish();
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            cishu = cishu - 1;
            switch (cishu) {
                case 1:
                    getMPersenter().getProvince();
                    break;
                case 0:
                    finish();
                    break;
            }
        }
        return false;

    }

    @Override
    public void getprovinceok(@NotNull SelectCityBean.DataBean selectCityBean) {
        province_adapter = new SelectCityAdapter(this, selectCityBean.getList());
        province_adapter.setOnSelectCityItmeListener(this);
        mRecyclerView.setAdapter(province_adapter);
    }

    @Override
    public void getcityok(@NotNull SelectCityBean.DataBean selectCityBean) {
        city_adapter = new SelectCityAdapter(this, selectCityBean.getList());
        city_adapter.setOnSelectCityItmeListener(this);
        mRecyclerView.setAdapter(city_adapter);
    }

    @Override
    public void getcountok(@NotNull SelectCityBean.DataBean selectCityBean) {

        if (selectCityBean.getList() != null && selectCityBean.getList().size() > 0) {
            district_adapter = new SelectCityAdapter(this, selectCityBean.getList());
            district_adapter.setOnSelectCityItmeListener(this);
            mRecyclerView.setAdapter(district_adapter);
        } else {
            Intent data = new Intent();
            data.putExtra("city", city);
            data.putExtra("province", province);
            data.putExtra("district", "");
            setResult(RESULT_OK, data);
            Union_SelectCityActivity.this.finish();
        }
    }

    @Override
    public void showloading() {

    }

    @Override
    public void dissmissloading() {

    }

    @Override
    public void getdataerror(String msg) {

    }

}
