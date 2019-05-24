package com.hechuang.hepay.bean;

import java.util.List;

public class PhoneLoginBean {

    /**
     * data : {"status":1,"msg":"验证成功！","token":"281952","list":[{"UserId":"961234","TrueName":"闹闹","AvatarUrl":"http://192.168.10.219:8056/Public/static/images/avatar_default.jpg"},{"UserId":"9611230","TrueName":"闹闹","AvatarUrl":"http://192.168.10.219:8056/Public/static/images/avatar_default.jpg"}]}
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
         * msg : 验证成功！
         * token : 281952
         * list : [{"UserId":"961234","TrueName":"闹闹","AvatarUrl":"http://192.168.10.219:8056/Public/static/images/avatar_default.jpg"},{"UserId":"9611230","TrueName":"闹闹","AvatarUrl":"http://192.168.10.219:8056/Public/static/images/avatar_default.jpg"}]
         */

        private String status;
        private String msg;
        private String token;
        private List<ListBean> list;

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

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
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
             * TrueName : 闹闹
             * AvatarUrl : http://192.168.10.219:8056/Public/static/images/avatar_default.jpg
             */

            private String UserId;
            private String TrueName;
            private String AvatarUrl;

            public String getUserId() {
                return UserId;
            }

            public void setUserId(String UserId) {
                this.UserId = UserId;
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

            public ListBean(String userId, String trueName, String avatarUrl) {
                UserId = userId;
                TrueName = trueName;
                AvatarUrl = avatarUrl;
            }

            @Override
            public String toString() {
                return "ListBean{" +
                        "UserId='" + UserId + '\'' +
                        ", TrueName='" + TrueName + '\'' +
                        ", AvatarUrl='" + AvatarUrl + '\'' +
                        '}';
            }
        }

        public DataBean(String status, String msg, String token, List<ListBean> list) {
            this.status = status;
            this.msg = msg;
            this.token = token;
            this.list = list;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "status=" + status +
                    ", msg='" + msg + '\'' +
                    ", token='" + token + '\'' +
                    ", list=" + list +
                    '}';
        }
    }

    public PhoneLoginBean(DataBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PhoneLoginBean{" +
                "data=" + data +
                '}';
    }
}
