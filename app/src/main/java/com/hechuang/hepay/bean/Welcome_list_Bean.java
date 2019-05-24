package com.hechuang.hepay.bean;

/**
 * Created by Administrator on 2017/8/8.
 */

public class Welcome_list_Bean {
    String imgurl;
    String url;
    String mold;
    String id;
    String bgcolor;

    public Welcome_list_Bean(String imgurl, String url, String mold, String id, String bgcolor) {
        this.imgurl = imgurl;
        this.url = url;
        this.mold = mold;
        this.id = id;
        this.bgcolor = bgcolor;
    }

    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
    }

    public String getBgcolor() {
        return bgcolor;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMold(String mold) {
        this.mold = mold;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgurl() {
        return imgurl;
    }

    public String getUrl() {
        return url;
    }

    public String getMold() {
        return mold;
    }

    public String getId() {
        return id;
    }
}
