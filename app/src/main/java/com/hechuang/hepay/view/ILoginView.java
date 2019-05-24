package com.hechuang.hepay.view;

import com.hechuang.hepay.base.BaseView;
import com.hechuang.hepay.bean.AuthCodeBean;
import com.hechuang.hepay.bean.PhoneLoginBean;
import com.hechuang.hepay.bean.PhoneSuccessBean;

/**
 * Created by Android_xu on 2018/1/9.
 */

public interface ILoginView extends BaseView {

    /**
     * 登录成功
     *
     * @param msg
     */
    void login_ok(String msg);

    /**
     * 登录失败
     *
     * @param msg
     */
    void login_error(String msg);

    /**
     * 强制修改密码
     */
    void startmodifypsw(String msg);


    /**
     * 获取验证码成功
     */
    void getauthcode(AuthCodeBean codeBean);

    /**
     * 获取验证码
     */
    void getautherror(String msg);

    /**
     * 短信验证获取用户列表成功
     */
    void getphoneuserlist(PhoneLoginBean phoneLoginBean);

    /**
     * 短信验证获取用户列表失败
     */
    void getphoneuserlisterror(String msg);


    /**
     * 短信登录成功
     */
    void getphoneloginsuccess(PhoneSuccessBean phoneSuccessBean);

    /**
     * 短信登录失败
     */
    void getphoneloginerror(String msg);
}
