package com.hechuang.hepay.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hechuang.hepay.R;
import com.hechuang.hepay.base.BaseFragment;
import com.hechuang.hepay.persenter.Union_top_classify_Persenter;
import com.hechuang.hepay.view.IUnion_top_classify_fragment_view;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Android_xu on 2018/3/7.
 */

public class Union_top_classify_fragment extends BaseFragment<Union_top_classify_Persenter> implements IUnion_top_classify_fragment_view {
    @BindView(R.id.fragment_union_top_classify_rv)
    RecyclerView mRecyclerView;
    private Unbinder mUnbinder;

    @Override
    public void showloading() {

    }

    @Override
    public void dissmissloading() {

    }

    @Override
    public void getdataerror(String msg) {

    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_union_top_classify;
    }

    @Override
    protected void initViews(View view) {
        mUnbinder = ButterKnife.bind(this, view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 5);
        mRecyclerView.setLayoutManager(gridLayoutManager);

    }

    @Override
    protected void initPersenter() {

    }
}
