package com.hechuang.hepay.bean;

import java.util.List;

public class BusinessBannerBean {

    /**
     * data : {"msg":"加载成功","status":1,"list":[{"UserId":"15167056","ShopPhoto":"http://www.hetianpay.com/Public/static/images/emptypic.png"}]}
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
         * msg : 加载成功
         * status : 1
         * list : [{"UserId":"15167056","ShopPhoto":"http://www.hetianpay.com/Public/static/images/emptypic.png"}]
         */
        private String msg;
        private int status;
        private String prod;
        private String userid;

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUserid() {
            return userid;
        }

        public String getProd() {
            return prod;
        }

        public void setProd(String prod) {
            this.prod = prod;
        }

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
             * UserId : 15167056
             * ShopPhoto : http://www.hetianpay.com/Public/static/images/emptypic.png
             */

            private String UserId;
            private String ShopPhoto;

            public String getUserId() {
                return UserId;
            }

            public void setUserId(String UserId) {
                this.UserId = UserId;
            }

            public String getShopPhoto() {
                return ShopPhoto;
            }

            public void setShopPhoto(String ShopPhoto) {
                this.ShopPhoto = ShopPhoto;
            }

            public ListBean(String userId, String shopPhoto) {
                UserId = userId;
                ShopPhoto = shopPhoto;
            }

            @Override
            public String toString() {
                return "ListBean{" +
                        "UserId='" + UserId + '\'' +
                        ", ShopPhoto='" + ShopPhoto + '\'' +
                        '}';
            }
        }

        public DataBean(String msg, int status, String prod, String userid, List<ListBean> list) {
            this.msg = msg;
            this.status = status;
            this.prod = prod;
            this.userid = userid;
            this.list = list;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "msg='" + msg + '\'' +
                    ", status=" + status +
                    ", prod='" + prod + '\'' +
                    ", list=" + list +
                    '}';
        }
    }

    public BusinessBannerBean(DataBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BusinessBannerBean{" +
                "data=" + data +
                '}';
    }
}
