package com.hechuang.hepay.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hechuang.hepay.R;
import com.hechuang.hepay.base.RvAdapter;
import com.hechuang.hepay.base.RvHolder;
import com.hechuang.hepay.base.RvListener;
import com.hechuang.hepay.bean.Uploadimglistdata;

import java.util.List;

public class UpLoadImageAdapter extends RvAdapter<Uploadimglistdata.DataBean.ListBean> {
    private boolean isc;

    public UpLoadImageAdapter(List<Uploadimglistdata.DataBean.ListBean> list, Context mContext, RvListener lsitener) {
        super(list, mContext, lsitener);
    }

    public void gaibianimg() {
        notifyDataSetChanged();
    }

    @Override
    protected int getLayoutId(int viewtype) {
        return R.layout.adapter_uploadimage;
    }

    @Override
    protected RvHolder getHolder(View view, int viewtype) {
        return new UpLoadImageViewHolderview(view, viewtype, lsitener);
    }

    class UpLoadImageViewHolderview extends RvHolder<Uploadimglistdata.DataBean.ListBean> {
        ImageView mImageView;
        ImageView mdelete;

        public UpLoadImageViewHolderview(View itemView, int type, RvListener rvListener) {
            super(itemView, type, rvListener);
            mImageView = itemView.findViewById(R.id.adapter_uploadimage_img);
            mdelete = itemView.findViewById(R.id.adapter_uploadimage_de);
        }

        @Override
        public void bindHolder(Uploadimglistdata.DataBean.ListBean listBean, int position) {
//            Log.d("uploadimage", listBean.getUrl());
            if (listBean.getUrl().equals("xiaoshi")) {
                Glide.with(mContext).load(R.drawable.image_kongbai).override(300, 200).into(mImageView);
                mdelete.setVisibility(View.GONE);
            } else if (listBean.getUrl().equals("jiahao")) {
                Glide.with(mContext).load(R.drawable.upload_jia).override(300, 200).into(mImageView);
                mdelete.setVisibility(View.GONE);
            } else {
                Glide.with(mContext).load(listBean.getUrl()).error(R.drawable.icon_error).override(300, 200).into(mImageView);
                mdelete.setVisibility(View.VISIBLE);
            }
            if (listBean.isIsshow_dt()) {
                mdelete.setVisibility(View.VISIBLE);
            } else {
                mdelete.setVisibility(View.GONE);
            }
            if (listBean.isIsdelete()) {
                Glide.with(mContext).load(R.drawable.image_isc_true).into(mdelete);
            } else {
                Glide.with(mContext).load(R.drawable.image_isc_flase).into(mdelete);
            }
//            mdelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mOndeleteimageListener != null) {
//                        mOndeleteimageListener.OnDeleteImageListener(position);
//                    }
//                }
//            });
        }
    }

//    private OndeleteimageListener mOndeleteimageListener;
//
//    private interface OndeleteimageListener {
//        void OnDeleteImageListener(int position);
//    }
//
//    public void setOndeleteimageListener(OndeleteimageListener ondeleteimageListener) {
//        this.mOndeleteimageListener = ondeleteimageListener;
//    }
}
