package com.hechuang.hepay.bean;

import java.util.List;

public class Home_Banner_Bean {

    /**
     * status : 1
     * msg : 加载成功
     * data : {"banner":[{"style":0,"url":"","urlid":"1","imgs":"http://www.hetianpay.com/Public/temp/banner/banner_01.jpg"},{"style":0,"url":"","urlid":"1","imgs":"http://www.hetianpay.com/Public/temp/banner/banner_02.jpg"},{"style":0,"url":"","urlid":"1","imgs":"http://www.hetianpay.com/Public/temp/banner/banner_03.jpg"}]}
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
             * style : 0
             * url :
             * urlid : 1
             * imgs : http://www.hetianpay.com/Public/temp/banner/banner_01.jpg
             */

            private String style;
            private String url;
            private String urlid;
            private String imgs;

            public String getStyle() {
                return style;
            }

            public void setStyle(String style) {
                this.style = style;
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

            @Override
            public String toString() {
                return "BannerBean{" +
                        "style=" + style +
                        ", url='" + url + '\'' +
                        ", urlid='" + urlid + '\'' +
                        ", imgs='" + imgs + '\'' +
                        '}';
            }

            public BannerBean(String style, String url, String urlid, String imgs) {
                this.style = style;
                this.url = url;
                this.urlid = urlid;
                this.imgs = imgs;
            }
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "banner=" + banner +
                    '}';
        }

        public DataBean(List<BannerBean> banner) {
            this.banner = banner;
        }
    }

    @Override
    public String toString() {
        return "Home_Banner_Bean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public Home_Banner_Bean(String status, String msg, DataBean data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
}
