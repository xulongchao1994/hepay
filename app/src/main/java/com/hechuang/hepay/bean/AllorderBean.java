package com.hechuang.hepay.bean;

import java.util.List;

public class AllorderBean {

    /**
     * data : {"status":1,"message":"数据获取成功","list":[{"Status":"待付款","AddDate":"2018-05-09","InnerOrderId":"20180509104931173937","RowNumber":"1","orderdetail":[{"ProName":"金生真爱-系列手表\u2014女士蓝色手表手表女商务休闲水钻蓝色细表带小表盘 ","StyleName":"J3005L","proNum":"1","StyleId":"295","Supplier":"深圳金生真爱珠宝商贸有限公司","ProImg":"http://img.hshc618.com/Upload/IntegralPro/2018-03-19/152142579411598.jpg","money":"￥2380.00+5405.00积分"}],"zhong":"共1种商品,合计:￥2380+5405.00积分"}]}
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
         * status : 1
         * message : 数据获取成功
         * list : [{"Status":"待付款","AddDate":"2018-05-09","InnerOrderId":"20180509104931173937","RowNumber":"1","orderdetail":[{"ProName":"金生真爱-系列手表\u2014女士蓝色手表手表女商务休闲水钻蓝色细表带小表盘 ","StyleName":"J3005L","proNum":"1","StyleId":"295","Supplier":"深圳金生真爱珠宝商贸有限公司","ProImg":"http://img.hshc618.com/Upload/IntegralPro/2018-03-19/152142579411598.jpg","money":"￥2380.00+5405.00积分"}],"zhong":"共1种商品,合计:￥2380+5405.00积分"}]
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
             * Status : 待付款
             * AddDate : 2018-05-09
             * InnerOrderId : 20180509104931173937
             * orderdetail : [{"ProName":"金生真爱-系列手表\u2014女士蓝色手表手表女商务休闲水钻蓝色细表带小表盘 ","StyleName":"J3005L","proNum":"1","StyleId":"295","Supplier":"深圳金生真爱珠宝商贸有限公司","ProImg":"http://img.hshc618.com/Upload/IntegralPro/2018-03-19/152142579411598.jpg","money":"￥2380.00+5405.00积分"}]
             * zhong : 共1种商品,合计:￥2380+5405.00积分
             */

            private String Status;
            private String AddDate;
            private String InnerOrderId;
            private String zhong;
            private List<OrderdetailBean> orderdetail;

            public ListBean(String status, String addDate, String innerOrderId, String zhong, List<OrderdetailBean> orderdetail) {
                Status = status;
                AddDate = addDate;
                InnerOrderId = innerOrderId;
                this.zhong = zhong;
                this.orderdetail = orderdetail;
            }

            public String getStatus() {
                return Status;
            }

            public void setStatus(String Status) {
                this.Status = Status;
            }

            public String getAddDate() {
                return AddDate;
            }

            public void setAddDate(String AddDate) {
                this.AddDate = AddDate;
            }

            public String getInnerOrderId() {
                return InnerOrderId;
            }

            public void setInnerOrderId(String InnerOrderId) {
                this.InnerOrderId = InnerOrderId;
            }


            public String getZhong() {
                return zhong;
            }

            public void setZhong(String zhong) {
                this.zhong = zhong;
            }

            public List<OrderdetailBean> getOrderdetail() {
                return orderdetail;
            }

            public void setOrderdetail(List<OrderdetailBean> orderdetail) {
                this.orderdetail = orderdetail;
            }

            public static class OrderdetailBean {
                /**
                 * ProName : 金生真爱-系列手表—女士蓝色手表手表女商务休闲水钻蓝色细表带小表盘
                 * StyleName : J3005L
                 * proNum : 1
                 * StyleId : 295
                 * Supplier : 深圳金生真爱珠宝商贸有限公司
                 * ProImg : http://img.hshc618.com/Upload/IntegralPro/2018-03-19/152142579411598.jpg
                 * money : ￥2380.00+5405.00积分
                 */

                private String ProName;
                private String StyleName;
                private String proNum;
                private String StyleId;
                private String Supplier;
                private String ProImg;
                private String money;

                public OrderdetailBean(String proName, String styleName, String proNum, String styleId, String supplier, String proImg, String money) {
                    ProName = proName;
                    StyleName = styleName;
                    this.proNum = proNum;
                    StyleId = styleId;
                    Supplier = supplier;
                    ProImg = proImg;
                    this.money = money;
                }

                public String getProName() {
                    return ProName;
                }

                public void setProName(String ProName) {
                    this.ProName = ProName;
                }

                public String getStyleName() {
                    return StyleName;
                }

                public void setStyleName(String StyleName) {
                    this.StyleName = StyleName;
                }

                public String getProNum() {
                    return proNum;
                }

                public void setProNum(String proNum) {
                    this.proNum = proNum;
                }

                public String getStyleId() {
                    return StyleId;
                }

                public void setStyleId(String StyleId) {
                    this.StyleId = StyleId;
                }

                public String getSupplier() {
                    return Supplier;
                }

                public void setSupplier(String Supplier) {
                    this.Supplier = Supplier;
                }

                public String getProImg() {
                    return ProImg;
                }

                public void setProImg(String ProImg) {
                    this.ProImg = ProImg;
                }

                public String getMoney() {
                    return money;
                }

                public void setMoney(String money) {
                    this.money = money;
                }
            }
        }
    }
}
