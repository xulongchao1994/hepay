package com.hechuang.hepay.bean;

import java.util.List;

public class HTFGoodInfoBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String proid;
        private String proname;
        private String proimg;
        private String supplierid;
        private String procontent;
        private String description;
        private String msg;
        private String status;
        private List<ListBean> list;

        public void setProid(String proid) {
            this.proid = proid;
        }

        public void setProname(String proname) {
            this.proname = proname;
        }

        public void setProimg(String proimg) {
            this.proimg = proimg;
        }

        public void setSupplierid(String supplierid) {
            this.supplierid = supplierid;
        }

        public void setProcontent(String procontent) {
            this.procontent = procontent;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public String getProid() {
            return proid;
        }

        public String getProname() {
            return proname;
        }

        public String getProimg() {
            return proimg;
        }

        public String getSupplierid() {
            return supplierid;
        }

        public String getProcontent() {
            return procontent;
        }

        public String getDescription() {
            return description;
        }

        public String getMsg() {
            return msg;
        }

        public String getStatus() {
            return status;
        }

        public List<ListBean> getList() {
            return list;
        }

        public static class ListBean {
            private String styleid;
            private String price;
            private String sylename;
            private String pronum;

            public void setStyleid(String styleid) {
                this.styleid = styleid;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public void setSylename(String sylename) {
                this.sylename = sylename;
            }

            public void setPronum(String pronum) {
                this.pronum = pronum;
            }

            public String getStyleid() {
                return styleid;
            }

            public String getPrice() {
                return price;
            }

            public String getSylename() {
                return sylename;
            }

            public String getPronum() {
                return pronum;
            }
        }

    }
}
