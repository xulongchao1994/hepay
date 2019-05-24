package com.hechuang.hepay.bean;

import java.util.List;

public class Uploadimglistdata {

    /**
     * data : {"status":1,"msg":"加载成功！","list":[{"id":1,"url":"http://192.168.1.150:8050/Upload/shang01/82f36343a70bdcb5d671040d70e62a45.png"}]}
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
         * msg : 加载成功！
         * list : [{"id":1,"url":"http://192.168.1.150:8050/Upload/shang01/82f36343a70bdcb5d671040d70e62a45.png"}]
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
             * id : 1
             * url : http://192.168.1.150:8050/Upload/shang01/82f36343a70bdcb5d671040d70e62a45.png
             */

            private String id;
            private String url;
            private boolean isdelete;
            private boolean isshow_dt;

            public ListBean(String id, String url, boolean isdelete, boolean isshow_dt) {
                this.id = id;
                this.url = url;
                this.isdelete = isdelete;
                this.isshow_dt = isshow_dt;
            }

            public void setIsshow_dt(boolean isshow_dt) {
                this.isshow_dt = isshow_dt;
            }

            public boolean isIsshow_dt() {
                return isshow_dt;
            }

            public void setIsdelete(boolean isdelete) {
                this.isdelete = isdelete;
            }

            public boolean isIsdelete() {
                return isdelete;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
