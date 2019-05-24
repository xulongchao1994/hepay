package com.hechuang.hepay.bean;

import java.util.List;

public class Home_NewsBean {

    /**
     * data : {"list":[{"id":"763","title":"【家居专区】不用电的\u201c空调\u201d，还凉而不冰？","addtime":"2018-07-03 10:02:35"},{"id":"762","title":"【双创峰会】媲美世界杯，电商双创交流会如火如荼\u2014\u2014内蒙站、邓州站","addtime":"2018-07-02 09:43:35"},{"id":"761","title":"【美酒专场】不念过去，不畏将来！巅峰，低谷，皆有精彩\u2014\u2014致德国队以及失意中的每个人！","addtime":"2018-07-01 09:48:37"},{"id":"760","title":"【创购脚步】农村电商创业项目对接大会\u2014\u2014内蒙站","addtime":"2018-06-30 09:23:40"},{"id":"759","title":"【参观考察】热烈欢迎郑州市科技局领导莅临创购参观考察！","addtime":"2018-06-29 10:20:42"},{"id":"758","title":"【美酒专场】梅西告诉你：破釜沉舟、全力以赴，希望就在！","addtime":"2018-06-28 10:14:36"}],"status":1,"msg":"数据记录"}
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
         * list : [{"id":"763","title":"【家居专区】不用电的\u201c空调\u201d，还凉而不冰？","addtime":"2018-07-03 10:02:35"},{"id":"762","title":"【双创峰会】媲美世界杯，电商双创交流会如火如荼\u2014\u2014内蒙站、邓州站","addtime":"2018-07-02 09:43:35"},{"id":"761","title":"【美酒专场】不念过去，不畏将来！巅峰，低谷，皆有精彩\u2014\u2014致德国队以及失意中的每个人！","addtime":"2018-07-01 09:48:37"},{"id":"760","title":"【创购脚步】农村电商创业项目对接大会\u2014\u2014内蒙站","addtime":"2018-06-30 09:23:40"},{"id":"759","title":"【参观考察】热烈欢迎郑州市科技局领导莅临创购参观考察！","addtime":"2018-06-29 10:20:42"},{"id":"758","title":"【美酒专场】梅西告诉你：破釜沉舟、全力以赴，希望就在！","addtime":"2018-06-28 10:14:36"}]
         * status : 1
         * msg : 数据记录
         */

        private String status;
        private String msg;
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

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 763
             * title : 【家居专区】不用电的“空调”，还凉而不冰？
             * addtime : 2018-07-03 10:02:35
             */

            private String id;
            private String title;
            private String addtime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public ListBean(String id, String title, String addtime) {
                this.id = id;
                this.title = title;
                this.addtime = addtime;
            }
        }

        public DataBean(String status, String msg, List<ListBean> list) {
            this.status = status;
            this.msg = msg;
            this.list = list;
        }
    }

    public Home_NewsBean(DataBean data) {
        this.data = data;
    }
}
