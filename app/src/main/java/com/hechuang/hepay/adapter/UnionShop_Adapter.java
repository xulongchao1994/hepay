package com.hechuang.hepay.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hechuang.hepay.R;
import com.hechuang.hepay.base.RvAdapter;
import com.hechuang.hepay.base.RvHolder;
import com.hechuang.hepay.base.RvListener;
import com.hechuang.hepay.bean.Union_list_Bean;

import java.util.List;

/**
 * Created by Android_xu on 2018/3/3.
 */

public class UnionShop_Adapter extends RvAdapter<Union_list_Bean.DataBean.ListBean> {
    public UnionShop_Adapter(List<Union_list_Bean.DataBean.ListBean> list, Context mContext, RvListener lsitener) {
        super(list, mContext, lsitener);
    }

    @Override
    protected int getLayoutId(int viewtype) {
        return R.layout.adapter_fastshopitme;
    }

    @Override
    protected RvHolder getHolder(View view, int viewtype) {
        return new UnionShopViewHolder(view, viewtype, lsitener);
    }

    private static final String TAG = "UnionShop_Adapter";

    class UnionShopViewHolder extends RvHolder<Union_list_Bean.DataBean.ListBean> {
        ImageView icon;
        TextView shopname;
        TextView shopcontent;
        TextView addres;
        TextView shorejli;
        //        ImageView shopping_img;
        LinearLayout shopname_layout;
        LinearLayout zhekou_layout;
        TextView zhekou_text;

        public UnionShopViewHolder(View itemView, int type, RvListener rvListener) {
            super(itemView, type, rvListener);
            icon = (ImageView) itemView.findViewById(R.id.adapter_fastshop_icon);
            shopname = (TextView) itemView.findViewById(R.id.adapter_fastshop_shopname);
            shopcontent = (TextView) itemView.findViewById(R.id.adapter_fastshop_shopcontent);
            addres = (TextView) itemView.findViewById(R.id.adapter_fastshop_addres);
            shorejli = (TextView) itemView.findViewById(R.id.adapter_fastshop_juli);
//            shopping_img = (ImageView) itemView.findViewById(R.id.adapter_fastshop_shopping_img);
            shopname_layout = (LinearLayout) itemView.findViewById(R.id.adapter_fastshop_shopname_laout);
            zhekou_layout = itemView.findViewById(R.id.adapter_fastshop_zhekoulayout);
            zhekou_text = itemView.findViewById(R.id.adapter_fastshop_zhekoutext);
        }

        @Override
        public void bindHolder(Union_list_Bean.DataBean.ListBean shopBase, int position) {
            Glide.with(mContext).load(shopBase.getShopPhoto()).override(200, 200).diskCacheStrategy(DiskCacheStrategy.ALL).into(icon);
            shopname.setText(shopBase.getShopName());
//            StringBuffer shopcontent_str = new StringBuffer();
//            if (shopBase.getCounty() != null && !shopBase.getCounty().equals("") && !shopBase.getCounty().equals("null")) {
//                shopcontent_str.append("【" + shopBase.getCounty() + "】");
//            }
//            if (shopBase.getCategoryname() != null && !shopBase.getCategoryname().equals("") && !shopBase.getCategoryname().equals("null")) {
//                shopcontent_str.append(shopBase.getCategoryname());
//            }
            if (shopBase.getGgfeelv() != null && !shopBase.getGgfeelv().equals("")) {
                zhekou_layout.setVisibility(View.VISIBLE);
                zhekou_text.setText(shopBase.getGgfeelv() + "折");
            } else {
                zhekou_layout.setVisibility(View.VISIBLE);
            }
            if (!shopBase.getSale().equals("")) {
                shopcontent.setText(shopBase.getSale() + "人已光顾");
            }
            List<Union_list_Bean.DataBean.ListBean.ShopTagsBean> listShopTagsBean = shopBase.getShopTags();
            String[] shoreTags = new String[listShopTagsBean.size()];
            if (listShopTagsBean != null && listShopTagsBean.size() > 0) {
                for (int i = 0; i < listShopTagsBean.size(); i++) {
                    shoreTags[i] = listShopTagsBean.get(i).getName();
                }
            }
            Log.d(TAG, "bindHolder: " + shopBase.getAddress());
            addres.setText(shopBase.getAddress());
            shorejli.setText(shopBase.getDistance());

        }

    }
}
