package com.hechuang.hepay.view;

import com.hechuang.hepay.base.BaseView;
import com.hechuang.hepay.bean.VersionBean;
import com.hechuang.hepay.bean.WthrcdnBean;

/**
 * Created by Android_xu on 2018/1/9.
 * 首页
 */

public interface IHePayView extends BaseView {

    void getwthrcdnok(WthrcdnBean.DataBean wthrcdnBean);

    /**
     * 获取版本信息
     */
    void getversion(VersionBean versionBean);

    /**
     *
     * @param namestr
     */
    void getname(String[] namestr);

    void getnameerror();
}
