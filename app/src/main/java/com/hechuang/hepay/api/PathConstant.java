package com.hechuang.hepay.api;

/**
 */
public class PathConstant {
    public static final String APP_NAME = "/hetianpay";
    public static final String APP_NAME_ZH = "hetianpay";
//    public static final String BUSINESS = "http://un.hetianpay.com/";
    public static final String BUSINESS = "http://unshop.99xyg.com/";
    /**
     * apk下载路径
     */
    public static final String APP_DOWNLOAD_URL = ApiFactory.APK_DOWN + "hetianpay.apk";
    /**
     * 图片目录
     */
    public static final String IMG_DIR = APP_NAME + "/img/";
    /**
     * 安装包目录
     */
    public static final String APK_DIR = APP_NAME + "/apk/";
    /**
     * 其他文件目录
     */
    public static final String TEMP_DIR = APP_NAME + "/temp/";
    /**
     * 获取城市列表（省
     */
    public static final String GET_PROVINCE = ApiFactory.HOST + "Api/Area/Area.php?act=province";
    /**
     * 获取城市列表(市
     */
    public static final String GET_CITY = ApiFactory.HOST + "Api/Area/Area.php?act=city";
    /**
     * 获取城市列表（区
     */
    public static final String GET_COUNT = ApiFactory.HOST + "Api/Area/Area.php?act=country";
    /**
     * 上传图片（获取已经上传的图片
     */
    public static final String GET_IMAGELIST = ApiFactory.HOST + "Api/Unshop/ImgList.php";
    /**
     * 上传图片（上传图片
     */
    public static final String UPLOADIMAGE = ApiFactory.HOST + "Api/Unshop/UpImg.php";
    /**
     * 上传图片（删除图片
     */
    public static final String DELETEIMAGE = ApiFactory.HOST + "Api/Unshop/delImg.php";
    /**
     * 联盟商家上传地址
     */
    public static final String STORE_ADDRESS = ApiFactory.HOST + "Api/Unshop/Adm.php";
    /**
     * 全部订单
     */
    public static final String ALLORDER_URL = ApiFactory.HOST + "Api/Order/orderlist.php";
    /**
     * 轮播图地址
     */
    public static final String HOEM_BANNER = ApiFactory.HOST + "Api/Integral/Home/index.php";
    /**
     * 首页分区
     */
    public static final String HOEM_CLASSIFY = ApiFactory.HOST + "Api/Integral/Home/classify.php";
    /**
     * shouyexinwen
     */
    public static final String HOME_NEWS = ApiFactory.HOST + "Api/Home/index.php";
    /**
     * shouyexinwen
     */
    public static final String HOME_SHOP1 = ApiFactory.HOST + "Api/Integral/show.php?a=1";
    /**
     * shouyexinwen
     */
    public static final String HOME_SHOP2 = ApiFactory.HOST + "Api/Integral/show.php?a=2";
    /**
     * shouyexinwen
     */
    public static final String HOME_SHOP3 = ApiFactory.HOST + "Api/Integral/show.php?a=3";


    /**
     * 联盟商家购物车加
     */
    public static final String BUSINESS_ADDSHOPPING = ApiFactory.HOST + "Api/Unshop/shopping.php?type=1";


    /**
     * 联盟商家购物车减
     */
    public static final String BUSINESS_REDUCESHOPPING = ApiFactory.HOST + "Api/Unshop/shopping.php?type=2";

    /**
     * 联盟商家详情轮播图
     */
    public static final String BUSINESS_BANNER = ApiFactory.HOST + "Api/Unshop/indexImg.php";

    /**
     * 查询商家购物车有没有商品
     */
    public static final String BUSINESS_GOPAY = ApiFactory.HOST + "Api/Unshop/payment.php";
    /**
     * 联盟商家搜索weburl
     */
    public static final String BUSINESS_SOUSUO = BUSINESS + "index.php/Home/Product/search";
    /**
     * 全部商家
     */
    public static final String BUSINESS_MOVESHOP = BUSINESS + "index.php/Home/Product/merchant_list";
    /**
     * 联盟商家分类
     */
    public static final String BUSINESS_CLASSIFY = BUSINESS + "index.php/Home/Product/category";
    /**
     * 联盟商家购物车
     */
    public static final String BUSINESS_SHOPPING = BUSINESS + "index.php/Home/Product/commodity_list";
    /**
     * 联盟商家个人中心
     */
    public static final String BUSINESS_MINE = BUSINESS + "index.php/Home/Personal/index";
}
