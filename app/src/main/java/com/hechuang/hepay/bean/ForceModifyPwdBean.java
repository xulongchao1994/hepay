package com.hechuang.hepay.bean;

/**
 * Created by Android_xu on 2017/12/4.
 * 强制修改密码
 */

public class ForceModifyPwdBean {

    /**
     * data : {"status":1,"msg":"修改成功,请使用新密码重新登陆"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "status=" + status +
                    ", msg='" + msg + '\'' +
                    '}';
        }

        /**
         * status : 1
         * msg : 修改成功,请使用新密码重新登陆
         */

        private int status;
        private String msg;

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
    }
}
