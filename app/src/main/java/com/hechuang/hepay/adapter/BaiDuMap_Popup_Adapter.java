package com.hechuang.hepay.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hechuang.hepay.R;
import com.hechuang.hepay.base.RvAdapter;
import com.hechuang.hepay.base.RvHolder;
import com.hechuang.hepay.base.RvListener;
import com.hechuang.hepay.bean.BaiduListBean;

import java.util.List;

public class BaiDuMap_Popup_Adapter extends RvAdapter<BaiduListBean> {
    public BaiDuMap_Popup_Adapter(List<BaiduListBean> list, Context mContext, RvListener lsitener) {
        super(list, mContext, lsitener);
    }

    @Override
    protected int getLayoutId(int viewtype) {
        return R.layout.adapter_baidumap_popup;
    }

    @Override
    protected RvHolder getHolder(View view, int viewtype) {
        return new BaiDuMap_Popup_ViewHOlder(view, viewtype, lsitener);
    }

    class BaiDuMap_Popup_ViewHOlder extends RvHolder<BaiduListBean> {
        TextView name;
        TextView address;

        public BaiDuMap_Popup_ViewHOlder(View itemView, int type, RvListener rvListener) {
            super(itemView, type, rvListener);
            name = itemView.findViewById(R.id.adapter_popup_name);
            address = itemView.findViewById(R.id.adapter_popup_address);
        }

        @Override
        public void bindHolder(BaiduListBean poi, int position) {
            Log.d("baidumap", "bindHolder: " + poi.getName());
            name.setText(poi.getName());
            address.setText(poi.getAddress());
        }
    }
}
