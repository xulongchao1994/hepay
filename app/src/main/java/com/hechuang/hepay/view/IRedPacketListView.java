package com.hechuang.hepay.view;

import com.hechuang.hepay.base.BaseView;
import com.hechuang.hepay.bean.RedPacketListBean;

/**
 * 红包列表
 * Created by Android_xu on 2018/1/9.
 */

public interface IRedPacketListView extends BaseView {
    /**
     * 获取数据成功
     *
     * @param redPacketListBean
     */
    void getlistdatasuccess(RedPacketListBean redPacketListBean);

//    /**
//     * 收取红包成功
//     *
//     * @param redPacketInfoBean
//     */
//    void getredpacketsuccess(RedPacketInfoBean redPacketInfoBean);
//
//    /**
//     * 获取红包失败
//     */
//    void getredpacketinfoerror(String msg);
}
