package com.hechuang.hepay.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

public class Home_goods_twolistBean extends SectionEntity<Home_goods_twoBean.DataBean.ListBean.ThreeBean> {
    private String two_id;

    public Home_goods_twolistBean(boolean isHeader, String header, String two_id) {
        super(isHeader, header);
        this.two_id = two_id;
    }

    public Home_goods_twolistBean(Home_goods_twoBean.DataBean.ListBean.ThreeBean threeBean) {
        super(threeBean);
    }

    public void setTwo_id(String two_id) {
        this.two_id = two_id;
    }

    public String getTwo_id() {
        return two_id;
    }
}
