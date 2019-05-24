package com.hechuang.hepay.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 * 自定义的recyclerview adapter
 */
@SuppressWarnings("unchecked")
public abstract class RvAdapter<T> extends RecyclerView.Adapter<RvHolder> {
    protected List<T> list;
    protected Context mContext;
    protected RvListener lsitener;
    protected LayoutInflater mInflater;

    public RvAdapter(List<T> list, Context mContext, RvListener lsitener) {
        this.list = list;
        this.mContext = mContext;
        this.lsitener = lsitener;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(getLayoutId(viewType), parent, false);
        return getHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(RvHolder holder, int position) {
        holder.bindHolder(list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    protected abstract int getLayoutId(int viewtype);

    protected abstract RvHolder getHolder(View view, int viewtype);
}
