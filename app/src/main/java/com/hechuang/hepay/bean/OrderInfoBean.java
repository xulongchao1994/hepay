package com.hechuang.hepay.bean;

import java.util.List;

public class OrderInfoBean {

    /**
     * data : {"status":1,"message":"数据获取成功","list":[{"Status":"已发货","AddDate":"2018-05-04 11:55:44","InnerOrderId":"20180504115545684884","UserTel":"18788888222","ReceiveName":"测试","Supplier":"深圳金生真爱珠宝商贸有限公司","address":"河南省 郑州市 惠济区 和创测试","downtime":"还剩-1天-3时自动确认","orderdetail":[{"ProName":"金生真爱-系列手表\u2014新款时尚潮流经典复古水钻女表方形女士手表石英表","StyleName":"B1104L-E白色","proNum":"1","StyleId":"297","ProImg":"http://img.hshc618.com/Upload/IntegralPro/2018-03-19/152142600256403.jpg","money":"￥780.00+1805.00积分"}],"zhong":"共1种商品,合计:￥780+1805.00积分"}]}
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
         * list : [{"Status":"已发货","AddDate":"2018-05-04 11:55:44","InnerOrderId":"20180504115545684884","UserTel":"18788888222","ReceiveName":"测试","Supplier":"深圳金生真爱珠宝商贸有限公司","address":"河南省 郑州市 惠济区 和创测试","downtime":"还剩-1天-3时自动确认","orderdetail":[{"ProName":"金生真爱-系列手表\u2014新款时尚潮流经典复古水钻女表方形女士手表石英表","StyleName":"B1104L-E白色","proNum":"1","StyleId":"297","ProImg":"http://img.hshc618.com/Upload/IntegralPro/2018-03-19/152142600256403.jpg","money":"￥780.00+1805.00积分"}],"zhong":"共1种商品,合计:￥780+1805.00积分"}]
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
             * Status : 已发货
             * AddDate : 2018-05-04 11:55:44
             * InnerOrderId : 20180504115545684884
             * UserTel : 18788888222
             * ReceiveName : 测试
             * Supplier : 深圳金生真爱珠宝商贸有限公司
             * address : 河南省 郑州市 惠济区 和创测试
             * downtime : 还剩-1天-3时自动确认
             * orderdetail : [{"ProName":"金生真爱-系列手表\u2014新款时尚潮流经典复古水钻女表方形女士手表石英表","StyleName":"B1104L-E白色","proNum":"1","StyleId":"297","ProImg":"http://img.hshc618.com/Upload/IntegralPro/2018-03-19/152142600256403.jpg","money":"￥780.00+1805.00积分"}]
             * zhong : 共1种商品,合计:￥780+1805.00积分
             */

            private String Status;
            private String AddDate;
            private String InnerOrderId;
            private String UserTel;
            private String ReceiveName;
            private String Supplier;
            private String address;
            private String downtime;
            private String zhong;
            private List<OrderdetailBean> orderdetail;

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

            public String getUserTel() {
                return UserTel;
            }

            public void setUserTel(String UserTel) {
                this.UserTel = UserTel;
            }

            public String getReceiveName() {
                return ReceiveName;
            }

            public void setReceiveName(String ReceiveName) {
                this.ReceiveName = ReceiveName;
            }

            public String getSupplier() {
                return Supplier;
            }

            public void setSupplier(String Supplier) {
                this.Supplier = Supplier;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getDowntime() {
                return downtime;
            }

            public void setDowntime(String downtime) {
                this.downtime = downtime;
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
                 * ProName : 金生真爱-系列手表—新款时尚潮流经典复古水钻女表方形女士手表石英表
                 * StyleName : B1104L-E白色
                 * proNum : 1
                 * StyleId : 297
                 * ProImg : http://img.hshc618.com/Upload/IntegralPro/2018-03-19/152142600256403.jpg
                 * money : ￥780.00+1805.00积分
                 */

                private String ProName;
                private String StyleName;
                private String proNum;
                private String StyleId;
                private String ProImg;
                private String money;

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
