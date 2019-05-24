package com.hechuang.hepay.view;


import com.hechuang.hepay.base.BaseView;
import com.hechuang.hepay.bean.NewsBean;

import java.util.List;

/**
 * Created by Android_xu on 2017/9/27.
 * 消息页面
 */

public interface INewsView extends BaseView {

    void setnewlistview(List<NewsBean> newsBeanList);

    void listerror(String msg);
}
