package com.hechuang.hepay.bean;

import java.util.List;

public class AllianceShopBannerBean {

    /**
     * data : {"banner":[{"style":0,"url":"","urlid":"","imgs":"http://192.168.0.150:8050/Public/temp/Unshop/banner_01.jpg"}],"ad":{"notice":"这是一句通告语句！"},"shopimg":{"style":1,"url":"","urlid":"","imgs":"http://192.168.0.150:8050/Public/temp/Unshop/ad_01.jpg"},"msg":"加载成功","status":1}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * banner : [{"style":0,"url":"","urlid":"","imgs":"http://192.168.0.150:8050/Public/temp/Unshop/banner_01.jpg"}]
         * ad : {"notice":"这是一句通告语句！"}
         * shopimg : {"style":1,"url":"","urlid":"","imgs":"http://192.168.0.150:8050/Public/temp/Unshop/ad_01.jpg"}
         * msg : 加载成功
         * status : 1
         */

        private AdBean ad;
        private ShopimgBean shopimg;
        private String msg;
        private int status;
        private List<BannerBean> banner;

        public AdBean getAd() {
            return ad;
        }

        public void setAd(AdBean ad) {
            this.ad = ad;
        }

        public ShopimgBean getShopimg() {
            return shopimg;
        }

        public void setShopimg(ShopimgBean shopimg) {
            this.shopimg = shopimg;
        }

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

        public static class AdBean {
            /**
             * notice : 这是一句通告语句！
             */

            private String notice;

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }
        }

        public static class ShopimgBean {
            /**
             * style : 1
             * url :
             * urlid :
             * imgs : http://192.168.0.150:8050/Public/temp/Unshop/ad_01.jpg
             */

            private int style;
            private String url;
            private String urlid;
            private String imgs;

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

        public static class BannerBean {
            /**
             * style : 0
             * url :
             * urlid :
             * imgs : http://192.168.0.150:8050/Public/temp/Unshop/banner_01.jpg
             */

            private int style;
            private String url;
            private String urlid;
            private String imgs;

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

            @Override
            public String toString() {
                return "BannerBean{" +
                        "style=" + style +
                        ", url='" + url + '\'' +
                        ", urlid='" + urlid + '\'' +
                        ", imgs='" + imgs + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "ad=" + ad +
                    ", shopimg=" + shopimg +
                    ", msg='" + msg + '\'' +
                    ", status=" + status +
                    ", banner=" + banner +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AllianceShopBannerBean{" +
                "data=" + data +
                '}';
    }
}
