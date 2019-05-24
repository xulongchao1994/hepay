package com.hechuang.hepay.bean;

import java.util.List;

/**
 * Created by Android_xu on 2018/3/7.
 */

public class Union_top_classify_bean {

    /**
     * data : {"list":[{"Id":"1","name":"餐饮美食","icon":"&#xf0129;"},{"Id":"2","name":"酒店宾馆","icon":null},{"Id":"3","name":"家居装饰","icon":null},{"Id":"4","name":"美容美发","icon":null},{"Id":"5","name":"服装服饰","icon":null},{"Id":"6","name":"数码家电","icon":null},{"Id":"7","name":"母婴用品","icon":null},{"Id":"8","name":"图书音像","icon":null},{"Id":"9","name":"旅行出游","icon":null},{"Id":"10","name":"鲜花礼品","icon":null},{"Id":"11","name":"休闲娱乐","icon":null},{"Id":"12","name":"教育培训","icon":null},{"Id":"13","name":"珠宝玉器","icon":null},{"Id":"14","name":"鞋包皮具","icon":null},{"Id":"15","name":"婚纱摄影","icon":null},{"Id":"16","name":"汽车配件","icon":null},{"Id":"17","name":"媒体广告","icon":null},{"Id":"18","name":"保险服务","icon":null},{"Id":"19","name":"日用品百货","icon":null},{"Id":"20","name":"养生保健","icon":null},{"Id":"21","name":"生活服务","icon":null}],"msg":"加载成功","shownum":10,"status":1}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public Union_top_classify_bean(DataBean data) {
        this.data = data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * list : [{"Id":"1","name":"餐饮美食","icon":"&#xf0129;"},{"Id":"2","name":"酒店宾馆","icon":null},{"Id":"3","name":"家居装饰","icon":null},{"Id":"4","name":"美容美发","icon":null},{"Id":"5","name":"服装服饰","icon":null},{"Id":"6","name":"数码家电","icon":null},{"Id":"7","name":"母婴用品","icon":null},{"Id":"8","name":"图书音像","icon":null},{"Id":"9","name":"旅行出游","icon":null},{"Id":"10","name":"鲜花礼品","icon":null},{"Id":"11","name":"休闲娱乐","icon":null},{"Id":"12","name":"教育培训","icon":null},{"Id":"13","name":"珠宝玉器","icon":null},{"Id":"14","name":"鞋包皮具","icon":null},{"Id":"15","name":"婚纱摄影","icon":null},{"Id":"16","name":"汽车配件","icon":null},{"Id":"17","name":"媒体广告","icon":null},{"Id":"18","name":"保险服务","icon":null},{"Id":"19","name":"日用品百货","icon":null},{"Id":"20","name":"养生保健","icon":null},{"Id":"21","name":"生活服务","icon":null}]
         * msg : 加载成功
         * shownum : 10
         * status : 1
         */

        private String msg;
        private int shownum;
        private int status;
        private List<ListBean> list;

        public DataBean(String msg, int shownum, int status, List<ListBean> list) {
            this.msg = msg;
            this.shownum = shownum;
            this.status = status;
            this.list = list;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "msg='" + msg + '\'' +
                    ", shownum=" + shownum +
                    ", status=" + status +
                    ", list=" + list +
                    '}';
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getShownum() {
            return shownum;
        }

        public void setShownum(int shownum) {
            this.shownum = shownum;
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
            public ListBean(String id, String name, String icon, String url) {
                Id = id;
                this.name = name;
                this.icon = icon;
                this.url = url;
            }

            /**
             * Id : 1
             * name : 餐饮美食
             * icon : &#xf0129;
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
        }
    }
}
