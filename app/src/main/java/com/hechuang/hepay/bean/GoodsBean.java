package com.hechuang.hepay.bean;

import java.util.List;

/**
 * Created by Android_xu on 2018/3/21.
 */

public class GoodsBean {

    /**
     * data : {"status":1,"message":"数据获取成功","list":[{"proid":"215","proname":"美国黑提（木塞） 750ml/瓶","proimg":"/Upload/IntegralPro/2018-03-05/152025979068878.jpg","integral":"160.00","price":"","row_number":"1","sales":"2"},{"proid":"214","proname":"久久玫瑰（胶塞）  750ml/瓶","proimg":"/Upload/IntegralPro/2018-03-05/152025970332705.jpg","integral":"140.00","price":"","row_number":"2","sales":"2"},{"proid":"213","proname":"香格里拉甜红（木塞）   750ml/瓶","proimg":"/Upload/IntegralPro/2018-03-05/152025949662424.jpg","integral":"180.00","price":"","row_number":"3","sales":"1"},{"proid":"212","proname":"玛歌纳德-谷地干红  750ml/瓶","proimg":"/Upload/IntegralPro/2018-03-05/152025905410480.jpg","integral":"440.00","price":"","row_number":"4","sales":"2"},{"proid":"211","proname":"赛奇娜-优选干红  750ml/瓶","proimg":"/Upload/IntegralPro/2018-03-05/152025894681751.jpg","integral":"440.00","price":"","row_number":"5","sales":"1"},{"proid":"210","proname":"赛奇娜-传奇干红   750ml/瓶","proimg":"/Upload/IntegralPro/2018-03-05/152025881692136.jpg","integral":"440.00","price":"","row_number":"6","sales":"1"},{"proid":"209","proname":"赛奇娜-珍藏干红  750ml/瓶","proimg":"/Upload/IntegralPro/2018-03-05/152025869396764.jpg","integral":"400.00","price":"","row_number":"7","sales":"2"},{"proid":"208","proname":"赛奇娜-晚收干红   750ml/瓶","proimg":"/Upload/IntegralPro/2018-03-05/152025840074451.jpg","integral":"400.00","price":"","row_number":"8","sales":"1"}]}
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
         * message : 数据获取成功
         * list : [{"proid":"215","proname":"美国黑提（木塞） 750ml/瓶","proimg":"/Upload/IntegralPro/2018-03-05/152025979068878.jpg","integral":"160.00","price":"","row_number":"1","sales":"2"},{"proid":"214","proname":"久久玫瑰（胶塞）  750ml/瓶","proimg":"/Upload/IntegralPro/2018-03-05/152025970332705.jpg","integral":"140.00","price":"","row_number":"2","sales":"2"},{"proid":"213","proname":"香格里拉甜红（木塞）   750ml/瓶","proimg":"/Upload/IntegralPro/2018-03-05/152025949662424.jpg","integral":"180.00","price":"","row_number":"3","sales":"1"},{"proid":"212","proname":"玛歌纳德-谷地干红  750ml/瓶","proimg":"/Upload/IntegralPro/2018-03-05/152025905410480.jpg","integral":"440.00","price":"","row_number":"4","sales":"2"},{"proid":"211","proname":"赛奇娜-优选干红  750ml/瓶","proimg":"/Upload/IntegralPro/2018-03-05/152025894681751.jpg","integral":"440.00","price":"","row_number":"5","sales":"1"},{"proid":"210","proname":"赛奇娜-传奇干红   750ml/瓶","proimg":"/Upload/IntegralPro/2018-03-05/152025881692136.jpg","integral":"440.00","price":"","row_number":"6","sales":"1"},{"proid":"209","proname":"赛奇娜-珍藏干红  750ml/瓶","proimg":"/Upload/IntegralPro/2018-03-05/152025869396764.jpg","integral":"400.00","price":"","row_number":"7","sales":"2"},{"proid":"208","proname":"赛奇娜-晚收干红   750ml/瓶","proimg":"/Upload/IntegralPro/2018-03-05/152025840074451.jpg","integral":"400.00","price":"","row_number":"8","sales":"1"}]
         */

        private int status;
        private String message;
        private List<ListBean> list;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * proid : 215
             * proname : 美国黑提（木塞） 750ml/瓶
             * proimg : /Upload/IntegralPro/2018-03-05/152025979068878.jpg
             * integral : 160.00
             * price :
             * row_number : 1
             * sales : 2
             */

            private String proid;
            private String proname;
            private String proimg;
            private String integral;
            private String price;
            private String row_number;
            private String sales;

            public String getProid() {
                return proid;
            }

            public void setProid(String proid) {
                this.proid = proid;
            }

            public String getProname() {
                return proname;
            }

            public void setProname(String proname) {
                this.proname = proname;
            }

            public String getProimg() {
                return proimg;
            }

            public void setProimg(String proimg) {
                this.proimg = proimg;
            }

            public String getIntegral() {
                return integral;
            }

            public void setIntegral(String integral) {
                this.integral = integral;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getRow_number() {
                return row_number;
            }

            public void setRow_number(String row_number) {
                this.row_number = row_number;
            }

            public String getSales() {
                return sales;
            }

            public void setSales(String sales) {
                this.sales = sales;
            }
        }
    }
}
