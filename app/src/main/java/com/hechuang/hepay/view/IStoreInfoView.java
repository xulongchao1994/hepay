package com.hechuang.hepay.view;


import com.hechuang.hepay.base.BaseView;
import com.hechuang.hepay.bean.StoreinfoListBean;

/**
 */

public interface IStoreInfoView extends BaseView {
    void setdata(StoreinfoListBean mStoreinfoListBean, String msg);

    void data_error(String msg);
}
