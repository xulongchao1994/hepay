package com.hechuang.hepay.bean;

import java.io.Serializable;
import java.util.List;

public class BusinessShoppingBean implements Serializable {

    /**
     * data : {"count":"￥10","num":2,"msg":"加载成功！","status":1,"list":[{"commodity":{"proname":"娃哈哈","proimg":"http://htf.99xyg.com/Upload/Sproduct/2018-10-28/154072656345694.jpg","price":"￥5.00","stylename":"60ml","proid":"1","styleid":"1","ProNum":"2"},"total":"￥10"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * count : ￥10
         * num : 2
         * msg : 加载成功！
         * status : 1
         * list : [{"commodity":{"proname":"娃哈哈","proimg":"http://htf.99xyg.com/Upload/Sproduct/2018-10-28/154072656345694.jpg","price":"￥5.00","stylename":"60ml","proid":"1","styleid":"1","ProNum":"2"},"total":"￥10"}]
         */

        private String count;
        private int num;
        private String msg;
        private int status;
        private String url;
        private List<ListBean> list;

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
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

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable {
            /**
             * commodity : {"proname":"娃哈哈","proimg":"http://htf.99xyg.com/Upload/Sproduct/2018-10-28/154072656345694.jpg","price":"￥5.00","stylename":"60ml","proid":"1","styleid":"1","ProNum":"2"}
             * total : ￥10
             */

            private CommodityBean commodity;
            private String total;

            public CommodityBean getCommodity() {
                return commodity;
            }

            public void setCommodity(CommodityBean commodity) {
                this.commodity = commodity;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public static class CommodityBean implements Serializable {
                /**
                 * proname : 娃哈哈
                 * proimg : http://htf.99xyg.com/Upload/Sproduct/2018-10-28/154072656345694.jpg
                 * price : ￥5.00
                 * stylename : 60ml
                 * proid : 1
                 * styleid : 1
                 * ProNum : 2
                 */

                private String proname;
                private String proimg;
                private String price;
                private String stylename;
                private String proid;
                private String styleid;
                private String ProNum;

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

                public String getStylename() {
                    return stylename;
                }

                public void setStylename(String stylename) {
                    this.stylename = stylename;
                }

                public String getProid() {
                    return proid;
                }

                public void setProid(String proid) {
                    this.proid = proid;
                }

                public String getStyleid() {
                    return styleid;
                }

                public void setStyleid(String styleid) {
                    this.styleid = styleid;
                }

                public String getProNum() {
                    return ProNum;
                }

                public void setProNum(String ProNum) {
                    this.ProNum = ProNum;
                }

                @Override
                public String toString() {
                    return "CommodityBean{" +
                            "proname='" + proname + '\'' +
                            ", proimg='" + proimg + '\'' +
                            ", price='" + price + '\'' +
                            ", stylename='" + stylename + '\'' +
                            ", proid='" + proid + '\'' +
                            ", styleid='" + styleid + '\'' +
                            ", ProNum='" + ProNum + '\'' +
                            '}';
                }
            }

            @Override
            public String toString() {
                return "ListBean{" +
                        "commodity=" + commodity +
                        ", total='" + total + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "count='" + count + '\'' +
                    ", num=" + num +
                    ", msg='" + msg + '\'' +
                    ", status=" + status +
                    ", list=" + list +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BusinessShoppingBean{" +
                "data=" + data +
                '}';
    }
}
