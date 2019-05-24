package com.hechuang.hepay.bean;

/**
 * Created by Android_xu on 2018/1/16.
 */

public class LoginBean {

    /**
     * data : {"list":{"userid":"13574","username":"shang01","usertype":"2","servicefee":".00000","sessionid":"hjgsapqc65kncsldh34i64rat2","token":"54c7861deed68c89bb9086a821740bca"},"status":1,"msg":"登录成功！"}
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
         * list : {"userid":"13574","username":"shang01","usertype":"2","servicefee":".00000","sessionid":"hjgsapqc65kncsldh34i64rat2","token":"54c7861deed68c89bb9086a821740bca"}
         * status : 1
         * msg : 登录成功！
         */

        private ListBean list;
        private String status;
        private String msg;
        private String userid;
        private String token;


        public void setUserid(String userid) {
            this.userid = userid;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUserid() {
            return userid;
        }

        public String getToken() {
            return token;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "list=" + list +
                    ", status=" + status +
                    ", msg='" + msg + '\'' +
                    '}';
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
             * userid : 13574
             * username : shang01
             * usertype : 2
             * servicefee : .00000
             * sessionid : hjgsapqc65kncsldh34i64rat2
             * token : 54c7861deed68c89bb9086a821740bca
             */

            private String userid;
            private String username;
            private String usertype;
            private String servicefee;
            private String sessionid;
            private String namestatus;
            private String url;
            private String token;

            @Override
            public String toString() {
                return "ListBean{" +
                        "userid='" + userid + '\'' +
                        ", username='" + username + '\'' +
                        ", usertype='" + usertype + '\'' +
                        ", servicefee='" + servicefee + '\'' +
                        ", sessionid='" + sessionid + '\'' +
                        ", namestatus='" + namestatus + '\'' +
                        ", url='" + url + '\'' +
                        ", token='" + token + '\'' +
                        '}';
            }

            public void setNamestatus(String namestatus) {
                this.namestatus = namestatus;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getNamestatus() {
                return namestatus;
            }

            public String getUrl() {
                return url;
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

            public String getServicefee() {
                return servicefee;
            }

            public void setServicefee(String servicefee) {
                this.servicefee = servicefee;
            }

            public String getSessionid() {
                return sessionid;
            }

            public void setSessionid(String sessionid) {
                this.sessionid = sessionid;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public ListBean(String userid, String username, String usertype, String servicefee, String sessionid, String namestatus, String url, String token) {
                this.userid = userid;
                this.username = username;
                this.usertype = usertype;
                this.servicefee = servicefee;
                this.sessionid = sessionid;
                this.namestatus = namestatus;
                this.url = url;
                this.token = token;
            }
        }

        public DataBean(ListBean list, String status, String msg, String userid, String token) {
            this.list = list;
            this.status = status;
            this.msg = msg;
            this.userid = userid;
            this.token = token;
        }
    }

    public LoginBean(DataBean data) {
        this.data = data;
    }
}
