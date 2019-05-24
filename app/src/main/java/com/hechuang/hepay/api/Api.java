package com.hechuang.hepay.api;

import com.hechuang.hepay.bean.AlianceshopHostShopBean;
import com.hechuang.hepay.bean.AllianceShopBannerBean;
import com.hechuang.hepay.bean.AllianceShopClassifyBean;
import com.hechuang.hepay.bean.AlliancesShopHotGoodsBean;
import com.hechuang.hepay.bean.AllorderBean;
import com.hechuang.hepay.bean.AuthCodeBean;
import com.hechuang.hepay.bean.BindingBean;
import com.hechuang.hepay.bean.BusinessBannerBean;
import com.hechuang.hepay.bean.BusinessClickShoppingBan;
import com.hechuang.hepay.bean.BusinessShoppingBean;
import com.hechuang.hepay.bean.Business_Goods_LiftBean;
import com.hechuang.hepay.bean.Business_Goods_RightBean;
import com.hechuang.hepay.bean.ForceModifyPwdBean;
import com.hechuang.hepay.bean.GoodsBean;
import com.hechuang.hepay.bean.GoodsListBean;
import com.hechuang.hepay.bean.HTFGoodInfoBean;
import com.hechuang.hepay.bean.Home_Banner_Bean;
import com.hechuang.hepay.bean.Home_ClassifyBean;
import com.hechuang.hepay.bean.Home_NewsBean;
import com.hechuang.hepay.bean.Home_goods_oneBean;
import com.hechuang.hepay.bean.Home_goods_twoBean;
import com.hechuang.hepay.bean.Home_shopBean;
import com.hechuang.hepay.bean.HomepageBean;
import com.hechuang.hepay.bean.LoginBean;
import com.hechuang.hepay.bean.NewListBean;
import com.hechuang.hepay.bean.OrderInfoBean;
import com.hechuang.hepay.bean.PhoneLoginBean;
import com.hechuang.hepay.bean.PhoneSuccessBean;
import com.hechuang.hepay.bean.RedPacketInfoBean;
import com.hechuang.hepay.bean.RedPacketListBean;
import com.hechuang.hepay.bean.RegisterBean;
import com.hechuang.hepay.bean.ShopValidate_payBean;
import com.hechuang.hepay.bean.Union_list_Bean;
import com.hechuang.hepay.bean.Union_top_banner_bean;
import com.hechuang.hepay.bean.Union_top_classify_bean;
import com.hechuang.hepay.bean.Uploadimglistdata;
import com.hechuang.hepay.bean.VersionBean;
import com.hechuang.hepay.bean.BusinessInfoBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Android_xu on 2017/12/25.
 */

public interface Api {
    /**
     * 登录接口
     *
     * @param username
     * @param passworid
     * @return
     */
    @FormUrlEncoded
    @POST("Api/Login/login.php?act=login")
    Observable<LoginBean> postLogin(@Field("username") String username, @Field("password") String passworid, @Field("type") String type);


    /**
     * 自动登录接口
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Api/login/automatic_login.php")
    Observable<LoginBean> postzidongLogin(@Field("userid") String userid, @Field("token") String token);

    /**
     * 红包列表
     *
     * @param userid
     * @param usertoken
     * @return
     */
    @FormUrlEncoded
    @POST("Api/Bonus/RedBonus.php")
    Observable<RedPacketListBean> postredpacklistdata(@Field("userid") String userid, @Field("usertoken") String usertoken);

    /**
     * 红包详情
     *
     * @param userid
     * @param usertoken
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("Api/Bonus/RedDetial.php")
    Observable<RedPacketInfoBean> postredpackinfodata(@Field("userid") String userid, @Field("usertoken") String usertoken, @Field("id") String id);


    /**
     * 强制修改密码
     *
     * @param userid
     * @param token
     * @param mobile
     * @param name
     * @param pwd
     * @param dbipwd
     * @return
     */
    @FormUrlEncoded
    @POST("Api/Login/pap.php")
    Observable<ForceModifyPwdBean> Post_modifytowpwd(@Field("userid") String userid,
                                                     @Field("token") String token,
                                                     @Field("mobile") String mobile,
                                                     @Field("name") String name,
                                                     @Field("pwd") String pwd,
                                                     @Field("dbipwd") String dbipwd);

    /**
     * 获取版本信息
     *
     * @return
     */
    @POST("Public/wap/version.php")
    Observable<VersionBean> post_version();

    /**
     * 获取轮播图
     * 获取广告页
     * 获取推荐商品
     *
     * @return
     */
    @POST("Api/Index/indexImg.php?act=img")
    Observable<Union_top_banner_bean> post_banner();

    /**
     * 联盟商家分类
     *
     * @return
     */
    @POST("Api/Index/indexImg.php?act=category")
    Observable<Union_top_classify_bean> post_classify();

    /**
     * 联盟商家列表数据
     *
     * @return
     */
    @FormUrlEncoded
    @POST("Api/Index/unshop.php?act=unshop")
    Observable<Union_list_Bean> post_list_data(
            @Field("lng") String lng,
            @Field("lat") String lat,
            @Field("keywords") String keywords,
            @Field("pid") String pid,
            @Field("aid") String aid,
            @Field("qid") String qid,
            @Field("Page") String Page,
            @Field("id") String id,
            @Field("hitid") String hitid,
            @Field("disid") String disid

    );

    /**
     * 获取首页数据
     *
     * @return
     */
    @POST("index.php/home/index/index")
    Observable<HomepageBean> post_home_data();

    /**
     * 获取商品数据
     *
     * @return
     */
    @POST("index.php/home/goods/goodslist")
    Observable<GoodsBean> post_goods_data(
    );

    /**
     * 获取图片列表（上传图片）
     */
    @FormUrlEncoded
    @POST("Api/Unshop/UpImg.php")
    Observable<Uploadimglistdata> post_imgaes(@Field("userid") String userid,
                                              @Field("usertoken") String tokenid);

    /**
     * 订单列表接口
     */
    @FormUrlEncoded
    @POST("Api/Order/orderlist.php")
    Observable<AllorderBean> post_allorderdata(@Field("username") String username, @Field("token") String tokenid,
                                               @Field("num") String num, @Field("orderstatus") String orderstatus, @Field("Page") String Page);

    /**
     * 订单详情
     */
    @FormUrlEncoded
    @POST("Api/Order/orderdetail.php")
    Observable<OrderInfoBean> post_orderinfodata(@Field("username") String ursername, @Field("token") String tokenid, @Field("orderid") String orderid);

    /**
     * 首页新闻轮播
     */
    @POST("Api/Home/index.php")
    Observable<NewListBean> post_newlsit();

    /**
     * 首页商品1
     */
    @POST("Api/Integral/Home/show.php?a=1")
    Observable<Home_shopBean> post_homeshop1();

    /**
     * 首页商品2
     */
    @POST("Api/Integral/Home/show.php?a=2")
    Observable<Home_shopBean> post_homeshop2();

    /**
     * 首页商品3
     */
    @POST("Api/Integral/Home/show.php?a=3")
    Observable<Home_shopBean> post_homeshop3();

    /**
     * 首页轮播图
     */
    @POST("Api/Integral/Home/index.php")
    Observable<Home_Banner_Bean> post_homebeean();

    /**
     * 首页分区图标
     */
    @POST("Api/Integral/Home/classify.php")
    Observable<Home_ClassifyBean> post_homeclassify();


    /**
     * 首页公告接口
     */
    @POST("Api/Home/index.php")
    Observable<Home_NewsBean> post_homenews();

    /**
     * 商品分类一级分类数据
     */
    @POST("Api/Home/one.php")
    Observable<Home_goods_oneBean> post_homeone();

    /**
     * 商品分类二三级分类数据
     */
    @FormUrlEncoded
    @POST("Api/Home/two.php")
    Observable<Home_goods_twoBean> post_hometwo(@Field("id") String id);

    /**
     * 获取验证码接口
     */
    @FormUrlEncoded
    @POST("Api/Home/vcode.php")
    Observable<AuthCodeBean> post_authCode(@Field("mobile") String mobile);

    /**
     * 手机验证码登录后返回的用户列表
     */
    @FormUrlEncoded
    @POST("Api/Home/token.php")
    Observable<PhoneLoginBean> post_phonelogin(@Field("mobile") String mobile, @Field("vcode") String vcode);

    /**
     * 验证码登录成功
     */
    @FormUrlEncoded
    @POST("Api/Home/logon.php")
    Observable<PhoneSuccessBean> post_phonelogin_success(@Field("token") String token, @Field("userid") String userid);

    /**
     * 微信绑定账号
     *
     * @param unionid
     * @param userid
     * @param password
     * @param rmdcode
     * @return
     */
    @FormUrlEncoded
    @POST("Api/login/binding.php")
    Observable<BindingBean> post_bindphone(@Field("unionid") String unionid, @Field("userid") String userid, @Field("password") String password, @Field("rmdcode") String rmdcode);

    /**
     * 注册
     *
     * @param mobile
     * @param rmdcode
     * @param password
     * @param vcode
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST("Api/Home/register.php")
    Observable<RegisterBean> REGISTER(
            @Field("mobile") String mobile,
            @Field("rmdcode") String rmdcode,
            @Field("password") String password,
            @Field("vcode") String vcode,
            @Field("type") String type);

    /**
     * 积分商城商品列表
     *
     * @param ranking
     * @param type
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST("Api/Home/commodity.php")
    Observable<GoodsListBean> GOODSLIST_POST(
            @Field("ranking") String ranking,
            @Field("type") String type,
            @Field("page") String page,
            @Field("infos") String infos
    );

    /**
     * 新版联盟商家分类
     *
     * @return
     */
    @POST("Api/mobile/indexImg.php?act=category")
    Observable<AllianceShopClassifyBean> ALLIANCESHOP_CLASSIFY();


    /**
     * 新版联盟商家首页录播图，公告，广告位
     *
     * @return
     */
    @POST("Api/mobile/indexImg.php?act=img")
    Observable<AllianceShopBannerBean> ALLIANCESHOP_BANNER();


    /**
     * 联盟商家详情轮播图
     */
    @FormUrlEncoded
    @POST("Api/Unshop/indexImg.php")
    Observable<BusinessBannerBean> BUSINESS_BANNER(@Field("id") String userid);

    /**
     * 商家分类信息
     */
    @FormUrlEncoded
    @POST("Api/Unshop/commodity.php")
    Observable<Business_Goods_LiftBean> BUSINESS_GOODS_LIFT(@Field("userid") String userid);

    /**
     * 获取右边商品信息
     */
    @FormUrlEncoded
    @POST("Api/Unshop/product.php")
    Observable<Business_Goods_RightBean> BUSINESS_GOODS_RIGHT(@Field("userid") String userid, @Field("id") String id, @Field("agenid") String agenid, @Field("token") String token);

    /**
     * 联盟商家热门商家接口
     */
    @FormUrlEncoded
    @POST("Api/Mobile/unshop.php?act=unshop")
    Observable<AlianceshopHostShopBean> ALLIANCESHOPHOST(
            @Field("lat") String lat,
            @Field("lng") String lng,
            @Field("pid") String pid,
            @Field("aid") String aid,
            @Field("qid") String qid
    );

    /**
     * 联盟商家热门商品接口
     */
    @FormUrlEncoded
    @POST("Api/Unshop/hotcommodity.php")
    Observable<AlliancesShopHotGoodsBean> ALLIANCES_SHOP_HOT_GOODS(@Field("pid") String pid, @Field("aid") String aid, @Field("qid") String qid);

    /**
     * 联盟商家详情信息
     */
    @FormUrlEncoded
    @POST("Api/Unshop/shopmessage.php")
    Observable<BusinessInfoBean> BUSINESS_INFO(@Field("userid") String userid);

    /**
     * 商家详情购物车加
     *
     * @param supplierid 商家ID
     * @param proid      商品id
     * @param styleid    规格ID
     * @param agenid     会员id
     * @return
     */
    @FormUrlEncoded
    @POST("Api/Unshop/shopping.php?type=1")
    Observable<BusinessClickShoppingBan> BUSINESS_SHOPPING_ADD(@Field("supplierid") String supplierid, @Field("proid") String proid
            , @Field("styleid") String styleid
            , @Field("agenid") String agenid
            , @Field("token") String token
    );

    /**
     * 商家详情购物车减
     *
     * @param supplierid 商家ID
     * @param proid      商品id
     * @param styleid    规格ID
     * @param agenid     会员id
     * @return
     */
    @FormUrlEncoded
    @POST("Api/Unshop/shopping.php?type=2")
    Observable<BusinessClickShoppingBan> BUSINESS_SHOPPING_REDUCE(@Field("supplierid") String supplierid, @Field("proid") String proid
            , @Field("styleid") String styleid
            , @Field("agenid") String agenid
            , @Field("token") String token
    );

    /**
     * 获取购物车列表
     *
     * @param supid
     * @param agenid
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("Api/Unshop/shoppinglist.php")
    Observable<BusinessShoppingBean> BUSINESS_SHOPPING(@Field("supid") String supid, @Field("agenid") String agenid, @Field("token") String token);

    /**
     * 联盟商家扫码改变订单状态
     */
    @FormUrlEncoded
    @POST("Api/Unshop/Validate_pay.php")
    Observable<ShopValidate_payBean> SHOP_VALIDATE_PAY(@Field("userid") String userid, @Field("token") String token, @Field("orderpaynum") String orderpaynum);

    /**
     * 联盟商家详情去付款是查询商家购物车
     */
    @FormUrlEncoded
    @POST("Api/Unshop/payment.php")
    Observable<BusinessClickShoppingBan> BUSINESS_SHOPPING_GOPAY(@Field("supplierid") String supplierid
            , @Field("agenid") String agenid
            , @Field("token") String token);


    @FormUrlEncoded
    @POST("Api/Unshop/commodityxq.php")
    Observable<HTFGoodInfoBean> HTF_GOOD_INFO_BEAN_OBSERVABLE(@Field("userid") String urerid, @Field("token") String token, @Field("proid") String proid);
}
