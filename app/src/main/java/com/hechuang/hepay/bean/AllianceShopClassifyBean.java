package com.hechuang.hepay.bean;

import java.util.List;

public class AllianceShopClassifyBean {

    /**
     * data : {"list":[{"Id":"1","name":"餐饮美食","icon":""},{"Id":"2","name":"酒店宾馆","icon":""},{"Id":"3","name":"家居装饰","icon":""},{"Id":"4","name":"美容美发","icon":""}],"msg":"加载成功","status":1}
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
         * list : [{"Id":"1","name":"餐饮美食","icon":""},{"Id":"2","name":"酒店宾馆","icon":""},{"Id":"3","name":"家居装饰","icon":""},{"Id":"4","name":"美容美发","icon":""}]
         * msg : 加载成功
         * status : 1
         */

        private String msg;
        private int status;
        private List<ListBean> list;

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

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * Id : 1
             * name : 餐饮美食
             * icon :
             */

            private String Id;
            private String name;
            private String icon;
            private String url;

            public void setUrl(String url) {
                this.url = url;
            }

            public String getUrl() {
                return url;
            }

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            @Override
            public String toString() {
                return "ListBean{" +
                        "Id='" + Id + '\'' +
                        ", name='" + name + '\'' +
                        ", icon='" + icon + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "msg='" + msg + '\'' +
                    ", status=" + status +
                    ", list=" + list +
                    '}';
        }
    }
}
