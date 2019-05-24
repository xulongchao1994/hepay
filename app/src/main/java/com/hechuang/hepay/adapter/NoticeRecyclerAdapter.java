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
import com.hechuang.hepay.bean.NoticeListBean;

import java.util.List;

/**
 * Created by Android_xu on 2017/11/13.
 */

public class NoticeRecyclerAdapter extends RvAdapter<NoticeListBean> {
    public NoticeRecyclerAdapter(List<NoticeListBean> list, Context mContext, RvListener lsitener) {
        super(list, mContext, lsitener);
    }

    @Override
    protected int getLayoutId(int viewtype) {
        return R.layout.adapter_notice;
    }

    @Override
    protected RvHolder getHolder(View view, int viewtype) {
        return new NoticeRecyclerViewHolder(view, viewtype, lsitener);
    }

    class NoticeRecyclerViewHolder extends RvHolder<NoticeListBean> {
        TextView title, notice_type, notice_time;
        ImageView mImageView;

        public NoticeRecyclerViewHolder(View itemView, int type, RvListener rvListener) {
            super(itemView, type, rvListener);
            title = (TextView) itemView.findViewById(R.id.adapter_notice_title);
            notice_type = (TextView) itemView.findViewById(R.id.adapter_notice_type);
            notice_time = (TextView) itemView.findViewById(R.id.adapter_notice_time);
            mImageView = (ImageView) itemView.findViewById(R.id.adapter_notice_img);
        }

        @Override
        public void bindHolder(NoticeListBean noticeListBean, int position) {
            title.setText(noticeListBean.getTitle());
            notice_type.setText(noticeListBean.getCategoryname());
            notice_time.setText(noticeListBean.getAddtime());
            Glide.with(mContext).load(noticeListBean.getImgpath()).override(200, 100).into(mImageView);
        }
    }
}
