package com.hechuang.hepay.bean;

import java.util.List;

public class GoodsListBean {

    /**
     * status : 1
     * message : 数据调用成功！
     * list : [{"proid":"223","proname":"荣友-中药提速多功能治疗仪 ","proimg":"http://192.168.10.219:8056/Upload/IntegralPro/2018-03-15/152107919326894.jpg","integral":"24000.00积分","price":".00","voucher":""}]
     */

    private int status;
    private String message;
    private List<ListBean> list;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * proid : 223
         * proname : 荣友-中药提速多功能治疗仪
         * proimg : http://192.168.10.219:8056/Upload/IntegralPro/2018-03-15/152107919326894.jpg
         * integral : 24000.00积分
         * price : .00
         * voucher :
         */

        private String proid;
        private String proname;
        private String proimg;
        private String integral;
        private String price;
        private String voucher;
        private String weight;
        private String height;

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getWeight() {
            return weight;
        }

        public String getHeight() {
            return height;
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

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getVoucher() {
            return voucher;
        }

        public void setVoucher(String voucher) {
            this.voucher = voucher;
        }

        @Override
        public String toString() {
            return "ListBean{" +
                    "proid='" + proid + '\'' +
                    ", proname='" + proname + '\'' +
                    ", proimg='" + proimg + '\'' +
                    ", integral='" + integral + '\'' +
                    ", price='" + price + '\'' +
                    ", voucher='" + voucher + '\'' +
                    ", weight='" + weight + '\'' +
                    ", height='" + height + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GoodsListBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", list=" + list +
                '}';
    }
}
