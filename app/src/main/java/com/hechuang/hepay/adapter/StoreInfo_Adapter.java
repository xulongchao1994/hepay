package com.hechuang.hepay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hechuang.hepay.R;
import com.hechuang.hepay.bean.StoreinfoListBean;

import java.util.List;

/**
 * 商家闲情页面列表
 */

public class StoreInfo_Adapter extends RecyclerView.Adapter<StoreInfo_Adapter.StoreInfo_AdapterViewHolder> {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<StoreinfoListBean.nearby> bean;

    public StoreInfo_Adapter(Context mContext, List<StoreinfoListBean.nearby> bean) {
        this.mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.bean = bean;
    }

    @Override
    public StoreInfo_AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StoreInfo_AdapterViewHolder(mInflater.inflate(R.layout.adapter_storeinfo, parent, false));
    }

    @Override
    public void onBindViewHolder(StoreInfo_AdapterViewHolder holder, final int position) {
        StoreinfoListBean.nearby nearby = bean.get(position);
        Glide.with(mContext)
                .load(nearby.getShopphoto())
                .into(holder.mIcon);
        holder.mName.setText(nearby.getShopname());
        holder.mJuli.setText(nearby.getDis());
        holder.mType.setText(nearby.getCategoryname());
        int tats_size = nearby.getTags().size();
        switch (tats_size) {
            case 0:
                holder.mShuaka.setVisibility(View.GONE);
                holder.mWifi.setVisibility(View.GONE);
                holder.mWeixinpay.setVisibility(View.GONE);
                holder.mZhifubaopay.setVisibility(View.GONE);
                holder.mTingchewei.setVisibility(View.GONE);
                break;
            case 1:
                holder.mShuaka.setVisibility(View.VISIBLE);
                holder.mWifi.setVisibility(View.GONE);
                holder.mWeixinpay.setVisibility(View.GONE);
                holder.mZhifubaopay.setVisibility(View.GONE);
                holder.mTingchewei.setVisibility(View.GONE);
                holder.mShuaka.setText(nearby.getTags().get(0).getTag());
                break;
            case 2:
                holder.mShuaka.setVisibility(View.VISIBLE);
                holder.mWifi.setVisibility(View.VISIBLE);
                holder.mWeixinpay.setVisibility(View.GONE);
                holder.mZhifubaopay.setVisibility(View.GONE);
                holder.mTingchewei.setVisibility(View.GONE);
                holder.mShuaka.setText(nearby.getTags().get(0).getTag());
                holder.mWifi.setText(nearby.getTags().get(1).getTag());
                break;
            case 3:
                holder.mShuaka.setVisibility(View.VISIBLE);
                holder.mWifi.setVisibility(View.VISIBLE);
                holder.mWeixinpay.setVisibility(View.VISIBLE);
                holder.mZhifubaopay.setVisibility(View.GONE);
                holder.mTingchewei.setVisibility(View.GONE);
                holder.mShuaka.setText(nearby.getTags().get(0).getTag());
                holder.mWifi.setText(nearby.getTags().get(1).getTag());
                holder.mWeixinpay.setText(nearby.getTags().get(2).getTag());
                break;
            case 4:
                holder.mShuaka.setVisibility(View.VISIBLE);
                holder.mWifi.setVisibility(View.VISIBLE);
                holder.mWeixinpay.setVisibility(View.VISIBLE);
                holder.mZhifubaopay.setVisibility(View.VISIBLE);
                holder.mTingchewei.setVisibility(View.GONE);
                holder.mShuaka.setText(nearby.getTags().get(0).getTag());
                holder.mWifi.setText(nearby.getTags().get(1).getTag());
                holder.mWeixinpay.setText(nearby.getTags().get(2).getTag());
                holder.mZhifubaopay.setText(nearby.getTags().get(3).getTag());
                break;
            case 5:
                holder.mShuaka.setVisibility(View.VISIBLE);
                holder.mWifi.setVisibility(View.VISIBLE);
                holder.mWeixinpay.setVisibility(View.VISIBLE);
                holder.mZhifubaopay.setVisibility(View.VISIBLE);
                holder.mTingchewei.setVisibility(View.VISIBLE);
                holder.mShuaka.setText(nearby.getTags().get(0).getTag());
                holder.mWifi.setText(nearby.getTags().get(1).getTag());
                holder.mWeixinpay.setText(nearby.getTags().get(2).getTag());
                holder.mZhifubaopay.setText(nearby.getTags().get(3).getTag());
                holder.mTingchewei.setText(nearby.getTags().get(4).getTag());
                break;
        }
        holder.mAddress.setText(nearby.getAddress());
        if (nearby.getShareaccounths().equals("1")) {
            holder.mXiaofeibi.setVisibility(View.VISIBLE);
        } else {
            holder.mXiaofeibi.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(v -> {
            if (ItemClickLinener != null) {
                ItemClickLinener.OnItemClickLinener(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (bean.size() >= 2) {
            return 2;
        } else {
            return bean.size();
        }
    }


    class StoreInfo_AdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView mIcon;
        TextView mName;
        TextView mJuli;
        TextView mType;
        TextView mShuaka;
        TextView mWifi;
        TextView mWeixinpay;
        TextView mZhifubaopay;
        TextView mTingchewei;
        TextView mAddress;
        ImageView mXiaofeibi;

        public StoreInfo_AdapterViewHolder(View itemView) {
            super(itemView);
            mIcon = (ImageView) itemView.findViewById(R.id.adapter_storeinfo_icon);
            mName = (TextView) itemView.findViewById(R.id.adapter_storeinfo_name);
            mJuli = (TextView) itemView.findViewById(R.id.adapter_storeinfo_juli);
            mType = (TextView) itemView.findViewById(R.id.adapter_storeinfo_type);
            mShuaka = (TextView) itemView.findViewById(R.id.adapter_storeinfo_shuaka);
            mWifi = (TextView) itemView.findViewById(R.id.adapter_storeinfo_wifi);
            mWeixinpay = (TextView) itemView.findViewById(R.id.adapter_storeinfo_weixinpay);
            mZhifubaopay = (TextView) itemView.findViewById(R.id.adapter_storeinfo_zhifubao);
            mTingchewei = (TextView) itemView.findViewById(R.id.adapter_storeinfo_tingchewei);
            mAddress = (TextView) itemView.findViewById(R.id.adapter_storeinfo_address);
            mXiaofeibi = (ImageView) itemView.findViewById(R.id.adapter_storeinfo_xiaofeibi);
        }
    }

    private ItemClickLinener ItemClickLinener;

    public interface ItemClickLinener {
        void OnItemClickLinener(View v, int position);
    }

    public void setOnItemClickLinener(ItemClickLinener onItemClickLinener) {
        this.ItemClickLinener = onItemClickLinener;
    }
}
