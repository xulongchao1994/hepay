package com.hechuang.hepay.bean;

public class AllianceShopClearShopping {

    /**
     * data : {"msg":"请先登录！","status":0}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        public DataBean(String msg, int status) {
            this.msg = msg;
            this.status = status;
        }

        /**
         * msg : 请先登录！
         * status : 0
         */

        private String msg;
        private int status;

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
    }
}
