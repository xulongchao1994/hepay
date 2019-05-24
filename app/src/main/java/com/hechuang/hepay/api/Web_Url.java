package com.hechuang.hepay.api;

/**
 * Created by Android_xu on 2018/2/6.
 */

public class Web_Url {
    //首页我
    public static String HOME = ApiFactory.HOST + "index.php/Home";
    //购物车
    public static String SHOPPING = ApiFactory.HOST + "index.php/Home/Cart/shopping_list_show";
    //商品
    public static String SHANGPIN = ApiFactory.HOST + "index.php/Home/Product";
    //我的   http://htf.99xyg.com/index.php/Home/User/index        index.php/Home/User/user_center
    public static String ME_URL = ApiFactory.HOST + "index.php/Home/User/index";
    //订单
    public static String OREDER_URL = ApiFactory.HOST + "index.php/Home/Order";
    //注册
    public static String REG_URL = ApiFactory.HOST + "index.php/Home/Login/reg";
    //红包明细
    public static String HONGBAOMINGXI_URL = ApiFactory.HOST + "index.php/Home/Record/record_redpacket";
    //钱包明细
    public static String QIANBAOMINGXI_URL = ApiFactory.HOST + "index.php/Home/Record/record_rebate";

    //找回密码
    public static String FIND_PSW = ApiFactory.HOST + "index.php/Home/Login/password_find";

    //资讯列表
    public static String ARTICE_URL = ApiFactory.HOST + "index.php/Home/Article/article_list/";
}
