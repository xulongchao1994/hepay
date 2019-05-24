package com.hechuang.hepay.bean;

import java.util.List;

/**
 */

public class FastShopBase {
    private String id;
    private String userid;
    private String shopname;
    private String categoryid;
    private String mobile;
    private String shopphoto;
    private String wechat;
    private String shoplicence;
    private String province;
    private String city;
    private String county;
    private String address;
    private String shopculture;
    private String shopcontent;
    private String adddate;
    private String passdate;
    private String modifydate;
    private String status;
    private String serviceid;
    private String shopmapx;
    private String shopmapy;
    private String reason;
    private String shoptype;
    private String modifyeditor;
    private String tags;
    private String tese;
    private String elite;
    private String hits;
    private String modifytype;
    private String fzrname;
    private String fzrphone;
    private String isindex;
    private String shoplogo;
    private String display;
    private String serviceshop;
    private String shareaccounths;
    private String distance;
    private List<FastShopShoreTagsBean> shoreTags;
    private String categoryanme;

    public FastShopBase(String id, String userid, String shopname, String categoryid, String mobile, String shopphoto, String wechat, String shoplicence, String province, String city, String county, String address, String shopculture, String shopcontent, String adddate, String passdate, String modifydate, String status, String serviceid, String shopmapx, String shopmapy, String reason, String shoptype, String modifyeditor, String tags, String tese, String elite, String hits, String modifytype, String fzrname, String fzrphone, String isindex, String shoplogo, String display, String serviceshop, String shareaccounths,
                        String distance, List<FastShopShoreTagsBean> shoreTags, String categoryanme) {
        this.id = id;
        this.userid = userid;
        this.shopname = shopname;
        this.categoryid = categoryid;
        this.mobile = mobile;
        this.shopphoto = shopphoto;
        this.wechat = wechat;
        this.shoplicence = shoplicence;
        this.province = province;
        this.city = city;
        this.county = county;
        this.address = address;
        this.shopculture = shopculture;
        this.shopcontent = shopcontent;
        this.adddate = adddate;
        this.passdate = passdate;
        this.modifydate = modifydate;
        this.status = status;
        this.serviceid = serviceid;
        this.shopmapx = shopmapx;
        this.shopmapy = shopmapy;
        this.reason = reason;
        this.shoptype = shoptype;
        this.modifyeditor = modifyeditor;
        this.tags = tags;
        this.tese = tese;
        this.elite = elite;
        this.hits = hits;
        this.modifytype = modifytype;
        this.fzrname = fzrname;
        this.fzrphone = fzrphone;
        this.isindex = isindex;
        this.shoplogo = shoplogo;
        this.display = display;
        this.serviceshop = serviceshop;
        this.shareaccounths = shareaccounths;
        this.distance = distance;
        this.shoreTags = shoreTags;
        this.categoryanme = categoryanme;
    }

    public void setIsindex(String isindex) {
        this.isindex = isindex;
    }

    public void setShoplogo(String shoplogo) {
        this.shoplogo = shoplogo;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public void setServiceshop(String serviceshop) {
        this.serviceshop = serviceshop;
    }

    public void setShareaccounths(String shareaccounths) {
        this.shareaccounths = shareaccounths;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getIsindex() {
        return isindex;
    }

    public String getShoplogo() {
        return shoplogo;
    }

    public String getDisplay() {
        return display;
    }

    public String getServiceshop() {
        return serviceshop;
    }

    public String getShareaccounths() {
        return shareaccounths;
    }

    public String getDistance() {
        return distance;
    }

    public void setShoreTags(List<FastShopShoreTagsBean> shoreTags) {
        this.shoreTags = shoreTags;
    }

    public void setCategoryanme(String categoryanme) {
        this.categoryanme = categoryanme;
    }

    public List<FastShopShoreTagsBean> getShoreTags() {
        return shoreTags;
    }

    public String getCategoryanme() {
        return categoryanme;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setShopphoto(String shopphoto) {
        this.shopphoto = shopphoto;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public void setShoplicence(String shoplicence) {
        this.shoplicence = shoplicence;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setShopculture(String shopculture) {
        this.shopculture = shopculture;
    }

    public void setShopcontent(String shopcontent) {
        this.shopcontent = shopcontent;
    }

    public void setAdddate(String adddate) {
        this.adddate = adddate;
    }

    public void setPassdate(String passdate) {
        this.passdate = passdate;
    }

    public void setModifydate(String modifydate) {
        this.modifydate = modifydate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setServiceid(String serviceid) {
        this.serviceid = serviceid;
    }

    public void setShopmapx(String shopmapx) {
        this.shopmapx = shopmapx;
    }

    public void setShopmapy(String shopmapy) {
        this.shopmapy = shopmapy;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setShoptype(String shoptype) {
        this.shoptype = shoptype;
    }

    public void setModifyeditor(String modifyeditor) {
        this.modifyeditor = modifyeditor;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setTese(String tese) {
        this.tese = tese;
    }

    public void setElite(String elite) {
        this.elite = elite;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }

    public void setModifytype(String modifytype) {
        this.modifytype = modifytype;
    }

    public void setFzrname(String fzrname) {
        this.fzrname = fzrname;
    }

    public void setFzrphone(String fzrphone) {
        this.fzrphone = fzrphone;
    }

    public String getId() {
        return id;
    }

    public String getUserid() {
        return userid;
    }

    public String getShopname() {
        return shopname;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public String getMobile() {
        return mobile;
    }

    public String getShopphoto() {
        return shopphoto;
    }

    public String getWechat() {
        return wechat;
    }

    public String getShoplicence() {
        return shoplicence;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getCounty() {
        return county;
    }

    public String getAddress() {
        return address;
    }

    public String getShopculture() {
        return shopculture;
    }

    public String getShopcontent() {
        return shopcontent;
    }

    public String getAdddate() {
        return adddate;
    }

    public String getPassdate() {
        return passdate;
    }

    public String getModifydate() {
        return modifydate;
    }

    public String getStatus() {
        return status;
    }

    public String getServiceid() {
        return serviceid;
    }

    public String getShopmapx() {
        return shopmapx;
    }

    public String getShopmapy() {
        return shopmapy;
    }

    public String getReason() {
        return reason;
    }

    public String getShoptype() {
        return shoptype;
    }

    public String getModifyeditor() {
        return modifyeditor;
    }

    public String getTags() {
        return tags;
    }

    public String getTese() {
        return tese;
    }

    public String getElite() {
        return elite;
    }

    public String getHits() {
        return hits;
    }

    public String getModifytype() {
        return modifytype;
    }

    public String getFzrname() {
        return fzrname;
    }

    public String getFzrphone() {
        return fzrphone;
    }
}
