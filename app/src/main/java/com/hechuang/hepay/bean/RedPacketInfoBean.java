package com.hechuang.hepay.bean;

/**
 * Created by Android_xu on 2018/1/17.
 */

public class RedPacketInfoBean {

    /**
     * data : {"status":1,"msg":"加载成功","list":{"id":"30","userid":"123456123","bonusmoney":"11.12","status":"0","adddate":"2018-01-16 17:18:10.863","modifydate":null}}
     */

    private DataBean data;

    public RedPacketInfoBean(DataBean data) {
        this.data = data;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * status : 1
         * msg : 加载成功
         * list : {"id":"30","userid":"123456123","bonusmoney":"11.12","status":"0","adddate":"2018-01-16 17:18:10.863","modifydate":null}
         */

        private int status;
        private String msg;
        private ListBean list;

        @Override
        public String toString() {
            return "DataBean{" +
                    "status=" + status +
                    ", msg='" + msg + '\'' +
                    ", list=" + list +
                    '}';
        }

        public DataBean(int status, String msg, ListBean list) {
            this.status = status;
            this.msg = msg;
            this.list = list;
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

        public ListBean getList() {
            return list;
        }

        public void setList(ListBean list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 30
             * userid : 123456123
             * bonusmoney : 11.12
             * status : 0
             * adddate : 2018-01-16 17:18:10.863
             * modifydate : null
             */

            private String id;
            private String userid;
            private String bonusmoney;
            private String status;
            private String adddate;
            private String modifydate;

            public ListBean(String id, String userid, String bonusmoney, String status, String adddate, String modifydate) {
                this.id = id;
                this.userid = userid;
                this.bonusmoney = bonusmoney;
                this.status = status;
                this.adddate = adddate;
                this.modifydate = modifydate;
            }

            @Override
            public String toString() {
                return "ListBean{" +
                        "id='" + id + '\'' +
                        ", userid='" + userid + '\'' +
                        ", bonusmoney='" + bonusmoney + '\'' +
                        ", status='" + status + '\'' +
                        ", adddate='" + adddate + '\'' +
                        ", modifydate=" + modifydate +
                        '}';
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getBonusmoney() {
                return bonusmoney;
            }

            public void setBonusmoney(String bonusmoney) {
                this.bonusmoney = bonusmoney;
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

            public String getModifydate() {
                return modifydate;
            }

            public void setModifydate(String modifydate) {
                this.modifydate = modifydate;
            }
        }
    }
}
