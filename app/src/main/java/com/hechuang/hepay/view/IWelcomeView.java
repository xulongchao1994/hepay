package com.hechuang.hepay.view;

import com.hechuang.hepay.base.BaseView;
import com.hechuang.hepay.bean.Welcome_list_Bean;

import java.util.List;

/**
 * 欢迎页面
 * Created by Android_xu on 2018/1/9.
 */

public interface IWelcomeView extends BaseView {
    /**
     * 设置一张图片
     *
     * @param bean
     */
    void setwelcome_img(Welcome_list_Bean bean);

    /**
     * 设置viewpager
     *
     * @param beans
     */
    void setwelcome_viewpager(List<Welcome_list_Bean> beans);


    /**
     * 登录成功
     */
    void login_success(String msg);

    /**
     * 登录失败
     */
    void login_error(String msg);
}
