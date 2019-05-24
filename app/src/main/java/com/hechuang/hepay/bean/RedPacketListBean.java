package com.hechuang.hepay.bean;

import java.util.List;

/**
 * 红包列表数据
 * Created by Android_xu on 2018/1/9.
 */

public class RedPacketListBean {

    /**
     * data : {"status":1,"msg":"加载成功","list":[{"id":"30","status":"0","adddate":"2018-01-16 分红"},{"id":"29","status":"0","adddate":"2018-01-16 分红"},{"id":"28","status":"0","adddate":"2018-01-16 分红"},{"id":"27","status":"0","adddate":"2018-01-16 分红"},{"id":"26","status":"0","adddate":"2018-01-01 分红"},{"id":"25","status":"0","adddate":"2018-01-16 分红"},{"id":"24","status":"0","adddate":"2018-01-16 分红"},{"id":"23","status":"0","adddate":"2018-01-16 分红"},{"id":"22","status":"0","adddate":"2018-01-10 分红"},{"id":"21","status":"0","adddate":"2018-01-16 分红"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public RedPacketListBean(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * status : 1
         * msg : 加载成功
         * list : [{"id":"30","status":"0","adddate":"2018-01-16 分红"},{"id":"29","status":"0","adddate":"2018-01-16 分红"},{"id":"28","status":"0","adddate":"2018-01-16 分红"},{"id":"27","status":"0","adddate":"2018-01-16 分红"},{"id":"26","status":"0","adddate":"2018-01-01 分红"},{"id":"25","status":"0","adddate":"2018-01-16 分红"},{"id":"24","status":"0","adddate":"2018-01-16 分红"},{"id":"23","status":"0","adddate":"2018-01-16 分红"},{"id":"22","status":"0","adddate":"2018-01-10 分红"},{"id":"21","status":"0","adddate":"2018-01-16 分红"}]
         */

        private int status;
        private String msg;
        private List<ListBean> list;

        public DataBean(int status, String msg, List<ListBean> list) {
            this.status = status;
            this.msg = msg;
            this.list = list;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "status=" + status +
                    ", msg='" + msg + '\'' +
                    ", list=" + list +
                    '}';
        }

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

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 30
             * status : 0
             * adddate : 2018-01-16 分红
             */

            private String id;
            private String status;
            private String adddate;

            @Override
            public String toString() {
                return "ListBean{" +
                        "id='" + id + '\'' +
                        ", status='" + status + '\'' +
                        ", adddate='" + adddate + '\'' +
                        '}';
            }

            public ListBean(String id, String status, String adddate) {
                this.id = id;
                this.status = status;
                this.adddate = adddate;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getAdddate() {
                return adddate;
            }

            public void setAdddate(String adddate) {
                this.adddate = adddate;
            }
        }
    }
}
