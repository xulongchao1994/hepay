package com.hechuang.hepay.bean;

public class BindingBean {

    /**
     * list : {"userid":"90268","username":"9611230","usertype":"3","token":"6f5047b4ad8f4cb48955fea15c7d9a34","sessionid":"t4813c12pg3ukpm9psmdrti622","servicefee":""}
     * status : 1
     * msg : 绑定成功
     */

    private ListBean list;
    private String status;
    private String msg;

    public BindingBean(ListBean list, String status, String msg) {
        this.list = list;
        this.status = status;
        this.msg = msg;
    }

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class ListBean {
        /**
         * userid : 90268
         * username : 9611230
         * usertype : 3
         * token : 6f5047b4ad8f4cb48955fea15c7d9a34
         * sessionid : t4813c12pg3ukpm9psmdrti622
         * servicefee :
         */

        private String userid;
        private String username;
        private String usertype;
        private String token;
        private String sessionid;
        private String servicefee;

        public ListBean(String userid, String username, String usertype, String token, String sessionid, String servicefee) {
            this.userid = userid;
            this.username = username;
            this.usertype = usertype;
            this.token = token;
            this.sessionid = sessionid;
            this.servicefee = servicefee;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUsertype() {
            return usertype;
        }

        public void setUsertype(String usertype) {
            this.usertype = usertype;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getSessionid() {
            return sessionid;
        }

        public void setSessionid(String sessionid) {
            this.sessionid = sessionid;
        }

        public String getServicefee() {
            return servicefee;
        }

        public void setServicefee(String servicefee) {
            this.servicefee = servicefee;
        }

        @Override
        public String toString() {
            return "ListBean{" +
                    "userid='" + userid + '\'' +
                    ", username='" + username + '\'' +
                    ", usertype='" + usertype + '\'' +
                    ", token='" + token + '\'' +
                    ", sessionid='" + sessionid + '\'' +
                    ", servicefee='" + servicefee + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BindingBean{" +
                "list=" + list +
                ", status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}
