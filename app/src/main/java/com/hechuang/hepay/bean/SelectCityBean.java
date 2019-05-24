package com.hechuang.hepay.bean;

import java.util.List;

/**
 * Created by Android_xu on 2018/3/24.
 */

public class SelectCityBean {

    /**
     * data : {"list":["北京市","天津市","河北省","山西省","内蒙古自治区","辽宁省","吉林省","黑龙江","上海市","江苏省","浙江省","安徽省","福建省","江西省","山东省","河南省","湖北省","湖南省","广东省","广西壮族自治区","海南省","重庆市","四川省","贵州省","云南省","西藏自治区","陕西省","甘肃省","青海省","宁夏回族自治区","新疆维吾尔自治区","台湾省","香港特别行政区","澳门特别行政区"],"status":1,"msg":"加载成功"}
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
         * list : ["北京市","天津市","河北省","山西省","内蒙古自治区","辽宁省","吉林省","黑龙江","上海市","江苏省","浙江省","安徽省","福建省","江西省","山东省","河南省","湖北省","湖南省","广东省","广西壮族自治区","海南省","重庆市","四川省","贵州省","云南省","西藏自治区","陕西省","甘肃省","青海省","宁夏回族自治区","新疆维吾尔自治区","台湾省","香港特别行政区","澳门特别行政区"]
         * status : 1
         * msg : 加载成功
         */

        private String status;
        private String msg;
        private List<String> list;

        public DataBean(String status, String msg, List<String> list) {
            this.status = status;
            this.msg = msg;
            this.list = list;
        }

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

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }
    }
}
