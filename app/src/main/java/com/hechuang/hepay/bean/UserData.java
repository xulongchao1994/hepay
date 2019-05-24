package com.hechuang.hepay.bean;

/**
 * Created by Android_xu on 2017/12/20.
 * 用于储存临时数据
 */

public class UserData {
    //sessionid
    public static String sessionid = "";
    //tokenid
    public static String tokenid = "";
    //serviceffe
    public static String serviceffe = "";
    //usertype
    public static String usertyep = "";
    //userid
    public static String userid = "";
    //username
    public static String username = "";
    //判断是否登录
    public static boolean islogin = false;
    //定位信息
    public static String latitude = "";//获取纬度信息
    public static String lontitude = ""; //获取经度信息
    public static String province = ""; //获取省份
    public static String city = "";//获取城市
    public static String discrict = "";//获取区县
    public static String streetnumber = "";
    public static String street = "";//获取街道信息
    public static String addr = "";//获取详细地址信息

    public static String recycler_loadinghint = "拼命加载中";
    public static String recycler_nomorehint = "已经全部为你呈现了";
    public static String recycler_nonetworkhint = "网络不给力啊，点击再试一次吧";

    public static String baidu_ak = "IrOhLlC7ZzUW8Pre63cid9GmjXrcBfZs";
    public static String gaode_ak = "46908be89194ec0cfcdf35c8c74dc3fd";

    public static int dbcode = 0;

    public static String zhuan_username = "";

    public static String namestatus = "";//登录时判断此用户有没有修改用户名
    public static String isurl = "";//如果没有修改过用户名的话跳转至此链接

    public static String wxappid = "wx734679904993348f";
    public static String wxsecret = "b31efc49c43979201680a8d7721917fb";
    public static String unionid = "";
    public static String userimg = "";
    public static String[] bai_url = {
            "99xyg.com",
            "hshc618",
            "hetianpay",
            "at.alicdn.com",
            "cnzz.com",
            "api.map.baidu.com",
            "wx.tenpay.com",
            "wx",
            "wx.gtimg.com",
            "openapi.alipay.com",
            "as.alipayobjects.com",
            "a.alipayobjects.com",
            "g.alicdn.com",
            "mclient.alipay.com",
            "map.bdimg.com",
            "ditu.baidu.com",
            "cnzz.mmstat.co",
            "192.168.10.22",
            "tscenter.alipay.com",
            "mclient.alipay.com",
            "alipay",
            "code.jquery.com",
            "entphz.alipay.com",
            "alipayobjects",
            "alicdn",
            "gtimg"
    };
}
