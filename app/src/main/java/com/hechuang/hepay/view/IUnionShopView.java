package com.hechuang.hepay.view;

import com.hechuang.hepay.base.BaseView;
import com.hechuang.hepay.bean.Union_list_Bean;
import com.hechuang.hepay.bean.Union_top_banner_bean;
import com.hechuang.hepay.bean.Union_top_classify_bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 联盟商家
 * Created by Android_xu on 2018/2/4.
 */

public interface IUnionShopView extends BaseView {
    /**
     * 获取轮播图
     *
     * @param union_top_banner_bean
     */
    void getbanner_data(Union_top_banner_bean union_top_banner_bean);

    /**
     * 分类
     *
     * @param union_top_classify_bean
     */
    void getclassify_data(Union_top_classify_bean union_top_classify_bean);

    /**
     * 获取列表数据
     */

    void getlist_data(List<Union_list_Bean.DataBean.ListBean> mlistdata);

    /**
     * 进入页面是加载附近
     */
    void setinitview(ArrayList<String> discrictList);

    /**
     * 城市选择完后更新附近
     */
    void setunview(ArrayList<String> discrictList);

    /**
     * 获取列表数据出错
     */
    void getdataerror_list();
}
