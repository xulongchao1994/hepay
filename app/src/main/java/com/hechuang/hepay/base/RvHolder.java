package com.hechuang.hepay.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/8/24.
 * 自定义的recycelrview viewholder
 */

public abstract class RvHolder<T> extends RecyclerView.ViewHolder {
    protected RvListener rvListener;

    public RvHolder(View itemView, int type, final RvListener rvListener) {
        super(itemView);
        this.rvListener = rvListener;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rvListener != null) rvListener.onItemListener(v.getId(), getAdapterPosition());
            }
        });
    }

    public abstract void bindHolder(T t, int position);
}
