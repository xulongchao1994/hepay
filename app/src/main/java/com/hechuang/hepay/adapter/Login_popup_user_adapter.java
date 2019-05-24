package com.hechuang.hepay.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hechuang.hepay.R;
import com.hechuang.hepay.bean.PhoneLoginBean;
import com.hechuang.hepay.util.GlideCircleTransform;

import java.util.List;

public class Login_popup_user_adapter extends BaseQuickAdapter<PhoneLoginBean.DataBean.ListBean, BaseViewHolder> {
    public Login_popup_user_adapter(int layoutResId, @Nullable List<PhoneLoginBean.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PhoneLoginBean.DataBean.ListBean item) {
        Glide.with(mContext).load(item.getAvatarUrl()).error(R.drawable.avatar_default).bitmapTransform(new GlideCircleTransform(mContext)).into((ImageView) helper.getView(R.id.login_popup_user_icon));
        helper.setText(R.id.login_popup_user_name, item.getUserId());
    }
}
