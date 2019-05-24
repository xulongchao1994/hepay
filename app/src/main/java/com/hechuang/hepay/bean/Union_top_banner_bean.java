package com.hechuang.hepay.bean;

import java.util.List;

/**
 * Created by Android_xu on 2018/3/9.
 */

public class Union_top_banner_bean {

    /**
     * data : {"banner":[{"style":0,"url":"","urlid":"","imgs":"http://img.hshc618.com/public/temp/WapAD/banner/banner_11.jpg"},{"style":1,"url":"","urlid":"","imgs":"http://img.hshc618.com/public/temp/WapAD/banner/banner_11.jpg"},{"style":2,"url":"","urlid":"","imgs":"http://img.hshc618.com/public/temp/WapAD/banner/banner_11.jpg"},{"style":3,"url":"","urlid":"","imgs":"http://img.hshc618.com/public/temp/WapAD/banner/banner_11.jpg"}],"ad":[{"style":1,"url":"","urlid":"","imgs":"http://img.hshc618.com/public/temp/WapAD/banner/banner_11.jpg"}],"shopimg":[{"style":0,"url":"","urlid":"","imgs":"http://img.hshc618.com/public/temp/WapAD/banner/banner_11.jpg"},{"style":0,"url":"","urlid":"","imgs":"http://img.hshc618.com/public/temp/WapAD/banner/banner_11.jpg"},{"style":0,"url":"","urlid":"","imgs":"http://img.hshc618.com/public/temp/WapAD/banner/banner_11.jpg"},{"style":0,"url":"","urlid":"","imgs":"http://img.hshc618.com/public/temp/WapAD/banner/banner_11.jpg"},{"style":0,"url":"","urlid":"","imgs":"http://img.hshc618.com/public/temp/WapAD/banner/banner_11.jpg"}],"msg":"加载成功","status":1}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "msg='" + msg + '\'' +
                    ", status=" + status +
                    ", banner=" + banner +
                    ", ad=" + ad +
                    ", shopimg=" + shopimg +
                    '}';
        }

        /**
         * banner : [{"style":0,"url":"","urlid":"","imgs":"http://img.hshc618.com/public/temp/WapAD/banner/banner_11.jpg"},{"style":1,"url":"","urlid":"","imgs":"http://img.hshc618.com/public/temp/WapAD/banner/banner_11.jpg"},{"style":2,"url":"","urlid":"","imgs":"http://img.hshc618.com/public/temp/WapAD/banner/banner_11.jpg"},{"style":3,"url":"","urlid":"","imgs":"http://img.hshc618.com/public/temp/WapAD/banner/banner_11.jpg"}]
         * ad : [{"style":1,"url":"","urlid":"","imgs":"http://img.hshc618.com/public/temp/WapAD/banner/banner_11.jpg"}]
         * shopimg : [{"style":0,"url":"","urlid":"","imgs":"http://img.hshc618.com/public/temp/WapAD/banner/banner_11.jpg"},{"style":0,"url":"","urlid":"","imgs":"http://img.hshc618.com/public/temp/WapAD/banner/banner_11.jpg"},{"style":0,"url":"","urlid":"","imgs":"http://img.hshc618.com/public/temp/WapAD/banner/banner_11.jpg"},{"style":0,"url":"","urlid":"","imgs":"http://img.hshc618.com/public/temp/WapAD/banner/banner_11.jpg"},{"style":0,"url":"","urlid":"","imgs":"http://img.hshc618.com/public/temp/WapAD/banner/banner_11.jpg"}]
         * msg : 加载成功
         * status : 1
         */

        private String msg;
        private int status;
        private List<BannerBean> banner;
        private List<AdBean> ad;
        private List<ShopimgBean> shopimg;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<AdBean> getAd() {
            return ad;
        }

        public void setAd(List<AdBean> ad) {
            this.ad = ad;
        }

        public List<ShopimgBean> getShopimg() {
            return shopimg;
        }

        public void setShopimg(List<ShopimgBean> shopimg) {
            this.shopimg = shopimg;
        }

        public static class BannerBean {
            /**
             * style : 0
             * url :
             * urlid :
             * imgs : http://img.hshc618.com/public/temp/WapAD/banner/banner_11.jpg
             */

            private int style;
            private String url;
            private String urlid;
            private String imgs;

            @Override
            public String toString() {
                return "BannerBean{" +
                        "style=" + style +
                        ", url='" + url + '\'' +
                        ", urlid='" + urlid + '\'' +
                        ", imgs='" + imgs + '\'' +
                        '}';
            }

            public int getStyle() {
                return style;
            }

            public void setStyle(int style) {
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
        }

        public static class AdBean {
            /**
             * style : 1
             * url :
             * urlid :
             * imgs : http://img.hshc618.com/public/temp/WapAD/banner/banner_11.jpg
             */

            private int style;
            private String url;
            private String urlid;
            private String imgs;

            @Override
            public String toString() {
                return "AdBean{" +
                        "style=" + style +
                        ", url='" + url + '\'' +
                        ", urlid='" + urlid + '\'' +
                        ", imgs='" + imgs + '\'' +
                        '}';
            }

            public int getStyle() {
                return style;
            }

            public void setStyle(int style) {
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
        }

        public static class ShopimgBean {
            /**
             * style : 0
             * url :
             * urlid :
             * imgs : http://img.hshc618.com/public/temp/WapAD/banner/banner_11.jpg
             */

            private int style;
            private String url;
            private String urlid;
            private String imgs;

            @Override
            public String toString() {
                return "ShopimgBean{" +
                        "style=" + style +
                        ", url='" + url + '\'' +
                        ", urlid='" + urlid + '\'' +
                        ", imgs='" + imgs + '\'' +
                        '}';
            }

            public int getStyle() {
                return style;
            }

            public void setStyle(int style) {
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
        }
    }
}
