package com.hechuang.hepay.bean;

public class BusinessClickShoppingBan {

    /**
     * data : {"msg":"删除成功！","status":1}
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
         * msg : 删除成功！
         * status : 1
         */

        private String msg;
        private int status;
        private String count;
        private String num;
        private String url;

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getCount() {
            return count;
        }

        public String getNum() {
            return num;
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

        @Override
        public String toString() {
            return "DataBean{" +
                    "msg='" + msg + '\'' +
                    ", status=" + status +
                    ", count='" + count + '\'' +
                    ", num='" + num + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BusinessClickShoppingBan{" +
                "data=" + data +
                '}';
    }
}
