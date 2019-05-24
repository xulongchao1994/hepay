package com.hechuang.hepay.ui.fragment;

import android.view.View;
import android.widget.TextView;

import com.hechuang.hepay.R;
import com.hechuang.hepay.base.BaseFragment;
import com.hechuang.hepay.bean.Union_top_classify_bean;
import com.hechuang.hepay.persenter.MinePersenter;
import com.hechuang.hepay.util.MyToast;
import com.hechuang.hepay.view.IMineView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Android_xu on 2018/1/15.
 */

public class MineFragment extends BaseFragment<MinePersenter> implements IMineView, View.OnClickListener {
    @BindView(R.id.mine_text)
    TextView mTextView;
    private Unbinder mUnbinder;
    private List<Union_top_classify_bean> mBeanList;

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
        return R.layout.fragment_mine;
    }

    public void setlistdata(List<Union_top_classify_bean> mlistdata) {
        this.mBeanList = mlistdata;
    }

    @Override
    protected void initViews(View view) {
        mUnbinder = ButterKnife.bind(this, view);
        mTextView.setOnClickListener(this);
    }

    @Override
    protected void initPersenter() {
        mPersenter = new MinePersenter(this, getActivity());
    }

    @Override
    public void onClick(View v) {
        MyToast.showMsg("wode");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //注解框架需要在销毁页面时解除绑定
        mUnbinder.unbind();
    }
}
