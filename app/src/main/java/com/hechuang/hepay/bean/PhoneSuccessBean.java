package com.hechuang.hepay.bean;

import java.util.List;

public class PhoneSuccessBean {

    /**
     * data : {"status":1,"msg":"登陆成功！","list":[{"UserId":"961234","userType":"0","TrueName":"闹闹","AvatarUrl":"http://192.168.10.219:8056/Public/static/images/avatar_default.jpg","sessionid":"75vv0oa45bmju3g8pfrfqd1p37","token":"2c3ec081bb72cdedd311ebedfc439730"}]}
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
         * msg : 登陆成功！
         * list : [{"UserId":"961234","userType":"0","TrueName":"闹闹","AvatarUrl":"http://192.168.10.219:8056/Public/static/images/avatar_default.jpg","sessionid":"75vv0oa45bmju3g8pfrfqd1p37","token":"2c3ec081bb72cdedd311ebedfc439730"}]
         */

        private int status;
        private String msg;
        private List<ListBean> list;

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
             * UserId : 961234
             * userType : 0
             * TrueName : 闹闹
             * AvatarUrl : http://192.168.10.219:8056/Public/static/images/avatar_default.jpg
             * sessionid : 75vv0oa45bmju3g8pfrfqd1p37
             * token : 2c3ec081bb72cdedd311ebedfc439730
             */

            private String UserId;
            private String userType;
            private String TrueName;
            private String AvatarUrl;
            private String sessionid;
            private String token;

            public String getUserId() {
                return UserId;
            }

            public void setUserId(String UserId) {
                this.UserId = UserId;
            }

            public String getUserType() {
                return userType;
            }

            public void setUserType(String userType) {
                this.userType = userType;
            }

            public String getTrueName() {
                return TrueName;
            }

            public void setTrueName(String TrueName) {
                this.TrueName = TrueName;
            }

            public String getAvatarUrl() {
                return AvatarUrl;
            }

            public void setAvatarUrl(String AvatarUrl) {
                this.AvatarUrl = AvatarUrl;
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

            @Override
            public String toString() {
                return "ListBean{" +
                        "UserId='" + UserId + '\'' +
                        ", userType='" + userType + '\'' +
                        ", TrueName='" + TrueName + '\'' +
                        ", AvatarUrl='" + AvatarUrl + '\'' +
                        ", sessionid='" + sessionid + '\'' +
                        ", token='" + token + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "status=" + status +
                    ", msg='" + msg + '\'' +
                    ", list=" + list +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PhoneSuccessBean{" +
                "data=" + data +
                '}';
    }
}
