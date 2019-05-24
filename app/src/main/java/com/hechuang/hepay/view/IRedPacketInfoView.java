package com.hechuang.hepay.view;

import com.hechuang.hepay.base.BaseView;
import com.hechuang.hepay.bean.RedPacketInfoBean;

/**
 * 红包详情
 * Created by Android_xu on 2018/1/9.
 */

public interface IRedPacketInfoView extends BaseView{

    /**
     * 收取红包成功
     *
     * @param redPacketInfoBean
     */
    void getredpacketsuccess(RedPacketInfoBean redPacketInfoBean);

    /**
     * 获取红包失败
     */
    void getredpacketinfoerror(String msg);
}
