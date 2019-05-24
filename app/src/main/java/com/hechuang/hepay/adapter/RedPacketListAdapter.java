package com.hechuang.hepay.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hechuang.hepay.R;
import com.hechuang.hepay.base.RvAdapter;
import com.hechuang.hepay.base.RvHolder;
import com.hechuang.hepay.base.RvListener;
import com.hechuang.hepay.bean.RedPacketListBean;

import java.util.List;

/**
 * 红包列表
 * Created by Android_xu on 2018/1/9.
 */

public class RedPacketListAdapter extends RvAdapter<RedPacketListBean.DataBean.ListBean> {
    public RedPacketListAdapter(List<RedPacketListBean.DataBean.ListBean> list, Context mContext, RvListener lsitener) {
        super(list, mContext, lsitener);
    }

    private static final String TAG = "RedPacketListAdapter";
    @Override
    protected int getLayoutId(int viewtype) {
        return R.layout.adapter_redpacketlist;
    }

    @Override
    protected RvHolder getHolder(View view, int viewtype) {
        return new RedPacketListViewHolder(view, viewtype, lsitener);
    }

    class RedPacketListViewHolder extends RvHolder<RedPacketListBean.DataBean.ListBean> {
        TextView mTitle;
        TextView mText;
        ImageView mIcon;

        public RedPacketListViewHolder(View itemView, int type, RvListener rvListener) {
            super(itemView, type, rvListener);
            mTitle = (TextView) itemView.findViewById(R.id.adapter_redpacketlist_title);
            mText = (TextView) itemView.findViewById(R.id.adapter_redpacketlist_text);
            mIcon = itemView.findViewById(R.id.adapter_redpacketlist_icon);
        }

        @Override
        public void bindHolder(RedPacketListBean.DataBean.ListBean redPacketListBean, int position) {
            mTitle.setText(redPacketListBean.getAdddate());
            mText.setText("有效期七天");
//            Log.d(TAG, "bindHolder: "+redPacketListBean.getStatus());
            if (redPacketListBean.getStatus().equals("0")) {
                Glide.with(mContext).load(R.drawable.cash).override(300,100).into(mIcon);
            } else {
                Glide.with(mContext).load(R.drawable.opencash).override(300,100).into(mIcon);
            }
        }
    }

    public void setnotifyadapter() {
        notifyDataSetChanged();
    }
}
