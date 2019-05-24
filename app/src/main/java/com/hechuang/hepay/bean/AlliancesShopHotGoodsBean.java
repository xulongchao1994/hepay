package com.hechuang.hepay.bean;

import java.util.List;

public class AlliancesShopHotGoodsBean {

    /**
     * data : {"msg":"加载成功！","status":1,"list":[{"proid":"1","proname":"娃哈哈","proimg":"http://192.168.10.219:8050/Upload/Sproduct/2018-10-28/154072656345694.jpg","price":"￥5.00","marketprice":"￥23.00","pro":"8"},{"proid":"3","proname":"尿不湿","proimg":"http://192.168.10.219:8050/Upload/Sproduct/2018-10-29/154077967574552.jpg","price":"￥80.00","marketprice":"￥23.00","pro":"4"},{"proid":"5","proname":"水杯","proimg":"http://192.168.10.219:8050/Upload/Sproduct/2018-10-29/154080532794342.jpg","price":"￥8.00","marketprice":"￥23.00","pro":"3"},{"proid":"2","proname":"溶豆豆","proimg":"http://192.168.10.219:8050/Upload/Sproduct/2018-10-28/154072664766870.jpg","price":"￥2.00","marketprice":"￥23.00","pro":"2"}]}
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
         * msg : 加载成功！
         * status : 1
         * list : [{"proid":"1","proname":"娃哈哈","proimg":"http://192.168.10.219:8050/Upload/Sproduct/2018-10-28/154072656345694.jpg","price":"￥5.00","marketprice":"￥23.00","pro":"8"},{"proid":"3","proname":"尿不湿","proimg":"http://192.168.10.219:8050/Upload/Sproduct/2018-10-29/154077967574552.jpg","price":"￥80.00","marketprice":"￥23.00","pro":"4"},{"proid":"5","proname":"水杯","proimg":"http://192.168.10.219:8050/Upload/Sproduct/2018-10-29/154080532794342.jpg","price":"￥8.00","marketprice":"￥23.00","pro":"3"},{"proid":"2","proname":"溶豆豆","proimg":"http://192.168.10.219:8050/Upload/Sproduct/2018-10-28/154072664766870.jpg","price":"￥2.00","marketprice":"￥23.00","pro":"2"}]
         */

        private String msg;
        private int status;
        private List<ListBean> list;

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

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * proid : 1
             * proname : 娃哈哈
             * proimg : http://192.168.10.219:8050/Upload/Sproduct/2018-10-28/154072656345694.jpg
             * price : ￥5.00
             * marketprice : ￥23.00
             * pro : 8
             */

            private String proid;
            private String proname;
            private String proimg;
            private String price;
            private String marketprice;
            private String pro;
            private String url;
            private String coupon;

            public void setCoupon(String coupon) {
                this.coupon = coupon;
            }

            public String getCoupon() {
                return coupon;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getUrl() {
                return url;
            }

            public String getProid() {
                return proid;
            }

            public void setProid(String proid) {
                this.proid = proid;
            }

            public String getProname() {
                return proname;
            }

            public void setProname(String proname) {
                this.proname = proname;
            }

            public String getProimg() {
                return proimg;
            }

            public void setProimg(String proimg) {
                this.proimg = proimg;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getMarketprice() {
                return marketprice;
            }

            public void setMarketprice(String marketprice) {
                this.marketprice = marketprice;
            }

            public String getPro() {
                return pro;
            }

            public void setPro(String pro) {
                this.pro = pro;
            }

            public ListBean(String proid, String proname, String proimg, String price, String marketprice, String url, String coupon, String pro) {
                this.proid = proid;
                this.proname = proname;
                this.proimg = proimg;
                this.price = price;
                this.marketprice = marketprice;
                this.pro = pro;
                this.url = url;
                this.coupon = coupon;
            }
        }

        public DataBean(String msg, int status, List<ListBean> list) {
            this.msg = msg;
            this.status = status;
            this.list = list;
        }
    }

    public AlliancesShopHotGoodsBean(DataBean data) {
        this.data = data;
    }
}
