package com.hechuang.hepay.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hechuang.hepay.R;
import com.hechuang.hepay.base.RvAdapter;
import com.hechuang.hepay.base.RvHolder;
import com.hechuang.hepay.base.RvListener;

import java.util.List;

/**
 * Created by Android_xu on 2018/2/10.
 */

public class HePay_popupAdapter extends RvAdapter<String> {
    private static final String TAG = "HePay_popupAdapter";
    public HePay_popupAdapter(List<String> list, Context mContext, RvListener lsitener) {
        super(list, mContext, lsitener);
    }

    @Override
    protected int getLayoutId(int viewtype) {
        return R.layout.adapter_hepay_popup;
    }

    @Override
    protected RvHolder getHolder(View view, int viewtype) {
        return new Hepa_popupViewHolder(view, viewtype, lsitener);
    }

    class Hepa_popupViewHolder extends RvHolder<String> {
        TextView title;

        public Hepa_popupViewHolder(View itemView, int type, RvListener rvListener) {
            super(itemView, type, rvListener);
            title = itemView.findViewById(R.id.adapter_hepay_popup_title);
        }

        @Override
        public void bindHolder(String s, int position) {
            title.setText(s);
            Log.d(TAG, "HePay_popupAdapter: "+s);
        }
    }
}
