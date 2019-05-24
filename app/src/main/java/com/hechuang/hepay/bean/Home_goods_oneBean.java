package com.hechuang.hepay.bean;

import java.util.List;

public class Home_goods_oneBean {

    /**
     * data : {"status":"1","msg":"输出成功！","list":[{"id":"1","name":"家具/家饰/家纺"},{"id":"10","name":"美食/生鲜/零食"},{"id":"19","name":"保健品"},{"id":"24","name":"家电/数码/手机"},{"id":"29","name":"女装/男装/内衣"},{"id":"35","name":"珠宝/眼镜/手表"},{"id":"62","name":"美妆/护肤/彩妆/香氛/男士"}]}
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
         * msg : 输出成功！
         * list : [{"id":"1","name":"家具/家饰/家纺"},{"id":"10","name":"美食/生鲜/零食"},{"id":"19","name":"保健品"},{"id":"24","name":"家电/数码/手机"},{"id":"29","name":"女装/男装/内衣"},{"id":"35","name":"珠宝/眼镜/手表"},{"id":"62","name":"美妆/护肤/彩妆/香氛/男士"}]
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
             * id : 1
             * name : 家具/家饰/家纺
             */
            private Boolean ischeck;
            private String id;
            private String name;

            public void setIscheck(Boolean ischeck) {
                this.ischeck = ischeck;
            }

            public Boolean getIscheck() {
                return ischeck;
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
                        "ischeck=" + ischeck +
                        ", id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        '}';
            }

        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "status='" + status + '\'' +
                    ", msg='" + msg + '\'' +
                    ", list=" + list +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Home_goods_oneBean{" +
                "data=" + data +
                '}';
    }
}
