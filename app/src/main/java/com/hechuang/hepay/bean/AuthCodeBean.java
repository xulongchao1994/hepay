package com.hechuang.hepay.bean;

public class AuthCodeBean {

    /**
     * data : {"status":1,"msg":"添加成功！","vcode":"058812"}
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
         * msg : 添加成功！
         * vcode : 058812
         */

        private int status;
        private String msg;
        private String vcode;

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

        public String getVcode() {
            return vcode;
        }

        public void setVcode(String vcode) {
            this.vcode = vcode;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "status=" + status +
                    ", msg='" + msg + '\'' +
                    ", vcode='" + vcode + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AuthCodeBean{" +
                "data=" + data +
                '}';
    }
}
