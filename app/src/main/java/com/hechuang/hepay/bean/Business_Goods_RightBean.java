package com.hechuang.hepay.bean;

import java.util.List;

public class Business_Goods_RightBean {

    /**
     * data : {"msg":"加载成功！","status":1,"list":[{"proid":"1","proname":"娃哈哈","proimg":"http://192.168.10.219:8056/Upload/Sproduct/2018-10-28/154072656345694.jpg","price":"￥5.00","style":[{"styleid":"8","stylename":"500ML","price":"￥8.00"},{"styleid":"9","stylename":"800ML","price":"￥18.00"},{"styleid":"10","stylename":"1000ML","price":"￥25.00"}]},{"proid":"5","proname":"水杯","proimg":"http://192.168.10.219:8056/Upload/Sproduct/2018-10-29/154080532794342.jpg","price":"￥8.00","style":[{"styleid":"8","stylename":"500ML","price":"￥8.00"},{"styleid":"9","stylename":"800ML","price":"￥18.00"},{"styleid":"10","stylename":"1000ML","price":"￥25.00"}]},{"proid":"6","proname":"钥匙","proimg":"http://192.168.10.219:8056/Upload/Sproduct/2018-10-29/154080556032060.jpg","price":"￥34.00","style":[{"styleid":"11","stylename":"车钥匙","price":"￥34.00"},{"styleid":"12","stylename":"大门钥匙","price":"￥23.00"},{"styleid":"13","stylename":"火车钥匙","price":"￥234.00"},{"styleid":"14","stylename":"飞机钥匙","price":"￥43453.00"}]}]}
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
         * list : [{"proid":"1","proname":"娃哈哈","proimg":"http://192.168.10.219:8056/Upload/Sproduct/2018-10-28/154072656345694.jpg","price":"￥5.00"},{"proid":"5","proname":"水杯","proimg":"http://192.168.10.219:8056/Upload/Sproduct/2018-10-29/154080532794342.jpg","price":"￥8.00","style":[{"styleid":"8","stylename":"500ML","price":"￥8.00"},{"styleid":"9","stylename":"800ML","price":"￥18.00"},{"styleid":"10","stylename":"1000ML","price":"￥25.00"}]},{"proid":"6","proname":"钥匙","proimg":"http://192.168.10.219:8056/Upload/Sproduct/2018-10-29/154080556032060.jpg","price":"￥34.00","style":[{"styleid":"11","stylename":"车钥匙","price":"￥34.00"},{"styleid":"12","stylename":"大门钥匙","price":"￥23.00"},{"styleid":"13","stylename":"火车钥匙","price":"￥234.00"},{"styleid":"14","stylename":"飞机钥匙","price":"￥43453.00"}]}]
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
             * proimg : http://192.168.10.219:8056/Upload/Sproduct/2018-10-28/154072656345694.jpg
             * price : ￥5.00
             * style : [{"styleid":"8","stylename":"500ML","price":"￥8.00"},{"styleid":"9","stylename":"800ML","price":"￥18.00"},{"styleid":"10","stylename":"1000ML","price":"￥25.00"}]
             */

            private String proid;
            private String proname;
            private String proimg;
            private String price;
            private String marketprice;
            private String pronum;
            private String styleid;
            private String coupon;
            private List<StyleBean> style;

            public void setCoupon(String coupon) {
                this.coupon = coupon;
            }

            public String getCoupon() {
                return coupon;
            }

            public void setStyleid(String styleid) {
                this.styleid = styleid;
            }

            public String getStyleid() {
                return styleid;
            }

            public void setMarketprice(String marketprice) {
                this.marketprice = marketprice;
            }

            public void setPronum(String pronum) {
                this.pronum = pronum;
            }

            public String getMarketprice() {
                return marketprice;
            }

            public String getPronum() {
                return pronum;
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

            public List<StyleBean> getStyle() {
                return style;
            }

            public void setStyle(List<StyleBean> style) {
                this.style = style;
            }

            public static class StyleBean {
                /**
                 * styleid : 8
                 * stylename : 500ML
                 * price : ￥8.00
                 */

                private String styleid;
                private String stylename;
                private String price;
                private String proid;
                private String pronum;

                public void setProid(String proid) {
                    this.proid = proid;
                }

                public void setPronum(String pronum) {
                    this.pronum = pronum;
                }

                public String getProid() {
                    return proid;
                }

                public String getPronum() {
                    return pronum;
                }

                public String getStyleid() {
                    return styleid;
                }

                public void setStyleid(String styleid) {
                    this.styleid = styleid;
                }

                public String getStylename() {
                    return stylename;
                }

                public void setStylename(String stylename) {
                    this.stylename = stylename;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                @Override
                public String toString() {
                    return "StyleBean{" +
                            "styleid='" + styleid + '\'' +
                            ", stylename='" + stylename + '\'' +
                            ", price='" + price + '\'' +
                            ", proid='" + proid + '\'' +
                            ", pronum='" + pronum + '\'' +
                            '}';
                }
            }

            @Override
            public String toString() {
                return "ListBean{" +
                        "proid='" + proid + '\'' +
                        ", proname='" + proname + '\'' +
                        ", proimg='" + proimg + '\'' +
                        ", price='" + price + '\'' +
                        ", marketprice='" + marketprice + '\'' +
                        ", pronum='" + pronum + '\'' +
                        ", styleid='" + styleid + '\'' +
                        ", style=" + style +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "msg='" + msg + '\'' +
                    ", status=" + status +
                    ", list=" + list +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Business_Goods_RightBean{" +
                "data=" + data +
                '}';
    }
}
