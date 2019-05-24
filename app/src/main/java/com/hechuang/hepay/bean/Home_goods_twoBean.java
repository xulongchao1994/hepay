package com.hechuang.hepay.bean;

import java.util.List;

public class Home_goods_twoBean {

    /**
     * data : {"status":"1","msg":"输出成功！","list":[{"two_name":"家纺","two_id":"2","three":[{"id":"3","name":"四件套","imgpath":"http://192.168.10.219:8056/Upload/KindImg/5bb0ef64bf8d8.jpg"},{"id":"4","name":"被子","imgpath":"http://192.168.10.219:8056/Upload/KindImg/5bafb3e653038.jpg"},{"id":"5","name":"厚被","imgpath":""},{"id":"6","name":"枕头","imgpath":"http://192.168.10.219:8056/Upload/KindImg/5baf90ea86b66.jpg"},{"id":"7","name":"儿童套件","imgpath":""},{"id":"8","name":"毛毯","imgpath":""},{"id":"9","name":"床单 被罩","imgpath":""}]}]}
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
         * list : [{"two_name":"家纺","two_id":"2","three":[{"id":"3","name":"四件套","imgpath":"http://192.168.10.219:8056/Upload/KindImg/5bb0ef64bf8d8.jpg"},{"id":"4","name":"被子","imgpath":"http://192.168.10.219:8056/Upload/KindImg/5bafb3e653038.jpg"},{"id":"5","name":"厚被","imgpath":""},{"id":"6","name":"枕头","imgpath":"http://192.168.10.219:8056/Upload/KindImg/5baf90ea86b66.jpg"},{"id":"7","name":"儿童套件","imgpath":""},{"id":"8","name":"毛毯","imgpath":""},{"id":"9","name":"床单 被罩","imgpath":""}]}]
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
             * two_name : 家纺
             * two_id : 2
             * three : [{"id":"3","name":"四件套","imgpath":"http://192.168.10.219:8056/Upload/KindImg/5bb0ef64bf8d8.jpg"},{"id":"4","name":"被子","imgpath":"http://192.168.10.219:8056/Upload/KindImg/5bafb3e653038.jpg"},{"id":"5","name":"厚被","imgpath":""},{"id":"6","name":"枕头","imgpath":"http://192.168.10.219:8056/Upload/KindImg/5baf90ea86b66.jpg"},{"id":"7","name":"儿童套件","imgpath":""},{"id":"8","name":"毛毯","imgpath":""},{"id":"9","name":"床单 被罩","imgpath":""}]
             */

            private String two_name;
            private String two_id;
            private List<ThreeBean> three;

            public String getTwo_name() {
                return two_name;
            }

            public void setTwo_name(String two_name) {
                this.two_name = two_name;
            }

            public String getTwo_id() {
                return two_id;
            }

            public void setTwo_id(String two_id) {
                this.two_id = two_id;
            }

            public List<ThreeBean> getThree() {
                return three;
            }

            public void setThree(List<ThreeBean> three) {
                this.three = three;
            }

            public static class ThreeBean {
                /**
                 * id : 3
                 * name : 四件套
                 * imgpath : http://192.168.10.219:8056/Upload/KindImg/5bb0ef64bf8d8.jpg
                 */

                private String id;
                private String name;
                private String imgpath;

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

                public String getImgpath() {
                    return imgpath;
                }

                public void setImgpath(String imgpath) {
                    this.imgpath = imgpath;
                }

                @Override
                public String toString() {
                    return "ThreeBean{" +
                            "id='" + id + '\'' +
                            ", name='" + name + '\'' +
                            ", imgpath='" + imgpath + '\'' +
                            '}';
                }
            }

            @Override
            public String toString() {
                return "ListBean{" +
                        "two_name='" + two_name + '\'' +
                        ", two_id='" + two_id + '\'' +
                        ", three=" + three +
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
        return "Home_goods_twoBean{" +
                "data=" + data +
                '}';
    }
}
