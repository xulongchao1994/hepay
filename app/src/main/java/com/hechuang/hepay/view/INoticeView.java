package com.hechuang.hepay.view;

import com.hechuang.hepay.base.BaseView;
import com.hechuang.hepay.bean.NoticeListBean;

import java.util.List;

/**
 * Created by Android_xu on 2017/11/13.
 * 公告页面
 */

public interface INoticeView extends BaseView {
    void setnoticelistdata(List<NoticeListBean> mNoticelistbean);
    void setnotmore(String msg);
}
