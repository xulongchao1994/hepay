package com.hechuang.hepay.base;

/**
 * Created by Android_xu on 2017/12/25.
 * 基础view
 */


public interface BaseView {
    /**
     * 显示等待框
     */
    void showloading();

    /**
     * 隐藏等待框
     */
    void dissmissloading();

    /**
     * 获取数据失败
     */
    void getdataerror(String msg);
}
