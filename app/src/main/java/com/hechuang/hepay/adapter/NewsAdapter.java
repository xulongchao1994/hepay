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
import com.hechuang.hepay.bean.NewsBean;

import java.util.List;

/**
 * Created by Android_xu on 2017/9/27.
 * 消息页面
 */

public class NewsAdapter extends RvAdapter<NewsBean> {

    public NewsAdapter(List<NewsBean> list, Context mContext, RvListener lsitener) {
        super(list, mContext, lsitener);
    }

    @Override
    protected int getLayoutId(int viewtype) {
        return R.layout.adapter_newlrecyclerview;
    }

    @Override
    protected RvHolder getHolder(View view, int viewtype) {
        return new NewsViewHolder(view, viewtype, lsitener);
    }

    class NewsViewHolder extends RvHolder<NewsBean> {
        TextView title;
        TextView time;
        ImageView news_icon;
        TextView context_str;
        TextView info_str;
        ImageView read;

        public NewsViewHolder(View itemView, int type, RvListener rvListener) {
            super(itemView, type, rvListener);
            title = (TextView) itemView.findViewById(R.id.adapter_news_title);
            time = (TextView) itemView.findViewById(R.id.adapter_news_time);
            news_icon = (ImageView) itemView.findViewById(R.id.adapter_news_icon);
            context_str = (TextView) itemView.findViewById(R.id.adapter_news_context);
            info_str = (TextView) itemView.findViewById(R.id.adapter_news_info);
            read = (ImageView) itemView.findViewById(R.id.adapter_news_read);
        }

        private static final String TAG = "NewsViewHolder";

        @Override
        public void bindHolder(NewsBean newsBean, int position) {
            title.setText(newsBean.getTitle());
            time.setText(newsBean.getAddtime());
//            Log.d(TAG, "bindHolder: " + newsBean.getMsgtype());
            switch (newsBean.getMsgtype().trim()) {
                case "1":
                    Glide.with(mContext).load(R.drawable.newmsg_1).override(100, 100).into(news_icon);
                    break;
                case "2":
                    Glide.with(mContext).load(R.drawable.newmsg_2).override(100, 100).into(news_icon);
                    break;
                case "3":
                    Glide.with(mContext).load(R.drawable.newmsg_3).override(100, 100).into(news_icon);
                    break;
            }
            context_str.setText(newsBean.getIntro());
            info_str.setText(newsBean.getSourceID());
            switch (newsBean.getIshasread()) {
                case "0":
                    read.setVisibility(View.VISIBLE);
                    break;
                case "1":
                    read.setVisibility(View.GONE);
                    break;
            }
        }
    }
}
