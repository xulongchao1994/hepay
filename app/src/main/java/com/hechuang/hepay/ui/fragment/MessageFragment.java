package com.hechuang.hepay.ui.fragment;

import android.view.View;
import android.widget.TextView;

import com.hechuang.hepay.R;
import com.hechuang.hepay.base.BaseFragment;
import com.hechuang.hepay.persenter.MessagePersenter;
import com.hechuang.hepay.util.MyToast;
import com.hechuang.hepay.view.IMessageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Android_xu on 2018/1/15.
 * 消息
 */

public class MessageFragment extends BaseFragment<MessagePersenter> implements IMessageView, View.OnClickListener {
    @BindView(R.id.message_text)
    TextView mTextView;
    private Unbinder mUnbinder;//注解框架

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
        return R.layout.fragment_message;
    }

    @Override
    protected void initViews(View view) {
        mUnbinder = ButterKnife.bind(this, view);
        mTextView.setOnClickListener(this);
    }

    @Override
    protected void initPersenter() {
        mPersenter = new MessagePersenter(this, getActivity());
    }

    @Override
    public void onClick(View v) {
        MyToast.showMsg("消息");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //注解框架需要在销毁页面时解除绑定
        mUnbinder.unbind();
    }
}
