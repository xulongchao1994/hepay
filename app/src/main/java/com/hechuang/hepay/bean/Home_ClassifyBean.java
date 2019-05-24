package com.hechuang.hepay.bean;

import java.util.List;

public class Home_ClassifyBean {

    /**
     * status : 1
     * msg : 加载成功
     * data : [{"id":0,"name":"积分兑换","url":"","imgs":"http://www.hetianpay.com/Public/static/mobile/images/Icon/activity.png"},{"id":1,"name":"积分兑购","url":"","imgs":"http://www.hetianpay.com/Public/static/mobile/images/Icon/exchange.png"},{"ud":2,"name":"积分活动","url":"","imgs":"http://www.hetianpay.com/Public/static/mobile/images/Icon/purchase.png"},{"id":3,"name":"线下商城","url":"","imgs":"http://www.hetianpay.com/Public/static/mobile/images/Icon/shop.png"}]
     */

    private String status;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 0
         * name : 积分兑换
         * url :
         * imgs : http://www.hetianpay.com/Public/static/mobile/images/Icon/activity.png
         * ud : 2
         */

        private String id;
        private String name;
        private String url;
        private String imgs;

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }


        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", url='" + url + '\'' +
                    ", imgs='" + imgs + '\'' +
                    '}';
        }

        public DataBean(String id, String name, String url, String imgs) {
            this.id = id;
            this.name = name;
            this.url = url;
            this.imgs = imgs;
        }
    }

    @Override
    public String toString() {
        return "Home_ClassifyBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public Home_ClassifyBean(String status, String msg, List<DataBean> data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
}
