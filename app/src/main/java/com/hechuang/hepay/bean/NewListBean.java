package com.hechuang.hepay.bean;

import java.util.List;

public class NewListBean {

    /**
     * data : {"list":[{"id":"763","title":"【家居专区】不用电的\u201c空调\u201d，还凉而不冰？","addtime":"2018-07-03 10:02:35"}],"status":1,"msg":"数据记录"}
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
         * list : [{"id":"763","title":"【家居专区】不用电的\u201c空调\u201d，还凉而不冰？","addtime":"2018-07-03 10:02:35"}]
         * status : 1
         * msg : 数据记录
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

            @Override
            public String toString() {
                return "ListBean{" +
                        "id='" + id + '\'' +
                        ", title='" + title + '\'' +
                        ", addtime='" + addtime + '\'' +
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
        return "NewListBean{" +
                "data=" + data +
                '}';
    }
}
