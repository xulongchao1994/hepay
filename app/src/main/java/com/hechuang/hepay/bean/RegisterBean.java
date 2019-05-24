package com.hechuang.hepay.bean;

public class RegisterBean {


    /**
     * data : {"status":1,"msg":"注册成功！","token":"9788a59b6f2e3df6daf1bfaf5451b2cb"}
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
         * msg : 注册成功！
         * token : 9788a59b6f2e3df6daf1bfaf5451b2cb
         */

        private int status;
        private String msg;
        private String token;

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

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
