package com.hechuang.hepay.bean;

import java.util.List;

public class Home_shopBean {

    /**
     * status : 1
     * msg : 加载成功
     * data : {"banner":[{"id":0,"style":0,"price":0,"Integral":0,"url":"","urlid":"1","imgs":"http://www.hetianpay.com/Public/temp/ad/ad_01.jpg"}]}
     */

    private String status;
    private String msg;
    private DataBean data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<BannerBean> banner;

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public static class BannerBean {
            /**
             * id : 0
             * style : 0
             * price : 0
             * Integral : 0
             * url :
             * urlid : 1
             * imgs : http://www.hetianpay.com/Public/temp/ad/ad_01.jpg
             */

            private String id;
            private String style;
            private String price;
            private String name;
            private String Integral;
            private String url;
            private String urlid;
            private String imgs;

            public void setName(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getStyle() {
                return style;
            }

            public void setStyle(String style) {
                this.style = style;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getIntegral() {
                return Integral;
            }

            public void setIntegral(String Integral) {
                this.Integral = Integral;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getUrlid() {
                return urlid;
            }

            public void setUrlid(String urlid) {
                this.urlid = urlid;
            }

            public String getImgs() {
                return imgs;
            }

            public void setImgs(String imgs) {
                this.imgs = imgs;
            }

            public BannerBean(String id, String style, String price, String integral, String url, String urlid, String imgs, String name) {
                this.id = id;
                this.style = style;
                this.price = price;
                Integral = integral;
                this.url = url;
                this.urlid = urlid;
                this.imgs = imgs;
                this.name = name;
            }
        }

        public DataBean(List<BannerBean> banner) {
            this.banner = banner;
        }
    }

    public Home_shopBean(String status, String msg, DataBean data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
}
