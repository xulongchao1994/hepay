package com.hechuang.hepay.view;


import com.hechuang.hepay.base.BaseView;

/**
 * Created by Android_xu on 2017/9/27.
 * 消息详情
 */

public interface INewsInfoView extends BaseView {

    void setwebview(String userid, String addtime, String Title, String editorValue);
}
