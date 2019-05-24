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
import com.hechuang.hepay.bean.Union_top_classify_bean;

import java.util.List;

/**
 * Created by Android_xu on 2018/3/11.
 */

public class Classify_rl_adapter extends RecyclerView.Adapter<Classify_rl_adapter.classify_rl_viewholder> {
    private static final String TAG = "Classify_rl_adapter";
    private List<Union_top_classify_bean.DataBean.ListBean> mDatas;
    private LayoutInflater inflater;
    /**
     * 页数下标,从0开始(当前是第几页)
     */
    private int curIndex;
    /**
     * 每一页显示的个数
     */
    private int pageSize;
    private Context mContext;

    public Classify_rl_adapter(Context mcontext, List<Union_top_classify_bean.DataBean.ListBean> datas, int curIndex, int pageSize) {
        this.mContext = mcontext;
        this.mDatas = datas;
        this.inflater = LayoutInflater.from(mcontext);
        this.curIndex = curIndex;
        this.pageSize = pageSize;
    }

    @Override
    public classify_rl_viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new classify_rl_viewholder(inflater.inflate(R.layout.adapter_item_gridview, parent, false));
    }

    @Override
    public void onBindViewHolder(classify_rl_viewholder holder, final int position) {
        int pos = position + curIndex * pageSize;
        holder.tv.setText(mDatas.get(pos).getName());
//        Log.d(TAG, "onBindViewHolder: "+mDatas.get(pos).getName());
        Glide.with(mContext).load(mDatas.get(pos).getIcon()).error(R.mipmap.ic_launcher).into(holder.iv);
        holder.itemView.setOnClickListener(v -> {
            if (mOnitemlatener != null) {
                mOnitemlatener.ontiemdfadfadf(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size() > (curIndex + 1) * pageSize ? pageSize : (mDatas.size() - curIndex * pageSize);
    }

    class classify_rl_viewholder extends RecyclerView.ViewHolder {

        public TextView tv;
        public ImageView iv;

        public classify_rl_viewholder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.textView);
            iv = itemView.findViewById(R.id.imageView);
        }
    }

    private Onitemlatener mOnitemlatener;

    public void setOnitemlatener(Onitemlatener onitemlatener) {
        this.mOnitemlatener = onitemlatener;
    }

    public interface Onitemlatener {
        void ontiemdfadfadf(int p);
    }
}
