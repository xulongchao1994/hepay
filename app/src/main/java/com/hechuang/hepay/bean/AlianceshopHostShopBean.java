package com.hechuang.hepay.bean;

import java.util.List;

public class AlianceshopHostShopBean {

    /**
     * data : {"list":[{"ID":"380","userid":"1503608893","ShopName":"登封市卢店和创店","ShopPhoto":"http://www.hetianpay.com/Public/static/images/emptypic.png","Address":"河南省-郑州市-登封市-卢店镇卢店市场老康家超市东150米","ggfeelv":"9.5折","sale":"0","distance":"64.01Km"},{"ID":"559","userid":"1803711","ShopName":"金水区和创联盟店","ShopPhoto":"http://www.hetianpay.com/Public/static/images/emptypic.png","Address":"河南省-郑州市-金水区-三全路渠东路呈祥花园一楼一号商铺","ggfeelv":"9.5折","sale":"0","distance":"12.55Km"},{"ID":"584","userid":"15290878369","ShopName":"和创低价超市","ShopPhoto":"http://www.hetianpay.com/Public/static/images/emptypic.png","Address":"河南省-郑州市-荥阳市-泰成悦府小区8号楼9号","ggfeelv":"9.5折","sale":"0","distance":"36.16Km"}],"status":1,"msg":"加载成功"}
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
         * list : [{"ID":"380","userid":"1503608893","ShopName":"登封市卢店和创店","ShopPhoto":"http://www.hetianpay.com/Public/static/images/emptypic.png","Address":"河南省-郑州市-登封市-卢店镇卢店市场老康家超市东150米","ggfeelv":"9.5折","sale":"0","distance":"64.01Km"},{"ID":"559","userid":"1803711","ShopName":"金水区和创联盟店","ShopPhoto":"http://www.hetianpay.com/Public/static/images/emptypic.png","Address":"河南省-郑州市-金水区-三全路渠东路呈祥花园一楼一号商铺","ggfeelv":"9.5折","sale":"0","distance":"12.55Km"},{"ID":"584","userid":"15290878369","ShopName":"和创低价超市","ShopPhoto":"http://www.hetianpay.com/Public/static/images/emptypic.png","Address":"河南省-郑州市-荥阳市-泰成悦府小区8号楼9号","ggfeelv":"9.5折","sale":"0","distance":"36.16Km"}]
         * status : 1
         * msg : 加载成功
         */

        private int status;
        private String msg;
        private List<ListBean> list;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * ID : 380
             * userid : 1503608893
             * ShopName : 登封市卢店和创店
             * ShopPhoto : http://www.hetianpay.com/Public/static/images/emptypic.png
             * Address : 河南省-郑州市-登封市-卢店镇卢店市场老康家超市东150米
             * ggfeelv : 9.5折
             * sale : 0
             * distance : 64.01Km
             */

            private String ID;
            private String userid;
            private String ShopName;
            private String ShopPhoto;
            private String Address;
            private String ggfeelv;
            private String sale;
            private String distance;

            public ListBean(String ID, String userid, String shopName, String shopPhoto, String address, String ggfeelv, String sale, String distance) {
                this.ID = ID;
                this.userid = userid;
                ShopName = shopName;
                ShopPhoto = shopPhoto;
                Address = address;
                this.ggfeelv = ggfeelv;
                this.sale = sale;
                this.distance = distance;
            }

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getShopName() {
                return ShopName;
            }

            public void setShopName(String ShopName) {
                this.ShopName = ShopName;
            }

            public String getShopPhoto() {
                return ShopPhoto;
            }

            public void setShopPhoto(String ShopPhoto) {
                this.ShopPhoto = ShopPhoto;
            }

            public String getAddress() {
                return Address;
            }

            public void setAddress(String Address) {
                this.Address = Address;
            }

            public String getGgfeelv() {
                return ggfeelv;
            }

            public void setGgfeelv(String ggfeelv) {
                this.ggfeelv = ggfeelv;
            }

            public String getSale() {
                return sale;
            }

            public void setSale(String sale) {
                this.sale = sale;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }
        }
    }
}
