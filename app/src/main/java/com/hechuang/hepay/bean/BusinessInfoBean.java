package com.hechuang.hepay.bean;

public class BusinessInfoBean {

    /**
     * data : {"msg":"加载成功","status":1,"list":{"UserId":"15167056","ShopName":"恩强福食","Mobile":"18338921021","Address":"河南省-新乡市-长垣县-常村镇打车张村（服务网点）","ShopContent":"和创线下联盟合作实体店","ggfeelv":"9.5折","sale":"0"}}
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
         * list : {"UserId":"15167056","ShopName":"恩强福食","Mobile":"18338921021","Address":"河南省-新乡市-长垣县-常村镇打车张村（服务网点）","ShopContent":"和创线下联盟合作实体店","ggfeelv":"9.5折","sale":"0"}
         */

        private String msg;
        private int status;
        private ListBean list;

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

        public ListBean getList() {
            return list;
        }

        public void setList(ListBean list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * UserId : 15167056
             * ShopName : 恩强福食
             * Mobile : 18338921021
             * Address : 河南省-新乡市-长垣县-常村镇打车张村（服务网点）
             * ShopContent : 和创线下联盟合作实体店
             * ggfeelv : 9.5折
             * sale : 0
             */

            private String UserId;
            private String ShopName;
            private String Mobile;
            private String Address;
            private String ShopContent;
            private String ggfeelv;
            private String sale;
            private String url;
            private String time;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getUrl() {
                return url;
            }

            public String getUserId() {
                return UserId;
            }

            public void setUserId(String UserId) {
                this.UserId = UserId;
            }

            public String getShopName() {
                return ShopName;
            }

            public void setShopName(String ShopName) {
                this.ShopName = ShopName;
            }

            public String getMobile() {
                return Mobile;
            }

            public void setMobile(String Mobile) {
                this.Mobile = Mobile;
            }

            public String getAddress() {
                return Address;
            }

            public void setAddress(String Address) {
                this.Address = Address;
            }

            public String getShopContent() {
                return ShopContent;
            }

            public void setShopContent(String ShopContent) {
                this.ShopContent = ShopContent;
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

            @Override
            public String toString() {
                return "ListBean{" +
                        "UserId='" + UserId + '\'' +
                        ", ShopName='" + ShopName + '\'' +
                        ", Mobile='" + Mobile + '\'' +
                        ", Address='" + Address + '\'' +
                        ", ShopContent='" + ShopContent + '\'' +
                        ", ggfeelv='" + ggfeelv + '\'' +
                        ", sale='" + sale + '\'' +
                        ", url='" + url + '\'' +
                        '}';
            }
        }

    }
}
