package com.hechuang.hepay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hechuang.hepay.R;

import java.util.List;

/**
 * Created by Android_xu on 2017/10/16.
 */

public class SelectCityAdapter extends RecyclerView.Adapter<SelectCityAdapter.SelectCityViewHolder> {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<String> selectList;

    public SelectCityAdapter(Context context, List<String> selectList) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        this.selectList = selectList;
    }

    @Override
    public SelectCityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectCityViewHolder(mInflater.inflate(R.layout.adapter_selectcity, parent, false));
    }

    @Override
    public void onBindViewHolder(SelectCityViewHolder holder, final int position) {
//        Map<String, String> citylsit = selectList.get(position);
        final String name = selectList.get(position);
//        final String id = citylsit.get("id");
        holder.mTextView.setText(name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSelectCityItmeListener != null) {
                    mOnSelectCityItmeListener.OnSelectCityItmeListener(position, name);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return selectList == null ? 0 : selectList.size();
    }

    class SelectCityViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public SelectCityViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.adapter_select_city);
        }
    }

    {
    }

    public OnSelectCityItmeListener mOnSelectCityItmeListener;

    public interface OnSelectCityItmeListener {
        void OnSelectCityItmeListener(int position, String name);
    }

    public void setOnSelectCityItmeListener(OnSelectCityItmeListener onSelectCityItmeListener) {
        this.mOnSelectCityItmeListener = onSelectCityItmeListener;
    }
}
