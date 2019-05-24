package com.hechuang.hepay.bean;

import java.util.List;

public class Business_Goods_LiftBean {

    /**
     * data : {"msg":"加载成功！","status":1,"list":[{"id":"1","name":"特色小吃"},{"id":"4","name":"能量套餐"}]}
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
         * msg : 加载成功！
         * status : 1
         * list : [{"id":"1","name":"特色小吃"},{"id":"4","name":"能量套餐"}]
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
             * id : 1
             * name : 特色小吃
             */

            private String id;
            private String name;
            private Boolean isclick;

            public Boolean getIsclick() {
                return isclick;
            }

            public void setIsclick(Boolean isclick) {
                this.isclick = isclick;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            @Override
            public String toString() {
                return "ListBean{" +
                        "id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        ", isclick=" + isclick +
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

    @Override
    public String toString() {
        return "Business_Goods_LiftBean{" +
                "data=" + data +
                '}';
    }
}
