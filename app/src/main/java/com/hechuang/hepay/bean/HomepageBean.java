package com.hechuang.hepay.bean;

import java.util.List;

/**
 * Created by Android_xu on 2018/3/16.
 */

public class HomepageBean {

    /**
     * data : {"status":1,"message":"加载成功！","list":{"banner":[{"style":0,"url":"","urlid":"","imgs":"http://img.hshc618.com//Upload/IntegralPro/2018-03-05/152025979068878.jpg"},{"style":1,"url":"","urlid":"","imgs":"http://img.hshc618.com//Upload/IntegralPro/2018-03-05/152025979068878.jpg"},{"style":2,"url":"","urlid":"","imgs":"http://img.hshc618.com//Upload/IntegralPro/2018-03-05/152025979068878.jpg"}],"icon":[{"id":1,"url":"/index.php/Home/product/index/ishit/1","name":"积分兑换","imgs":""},{"id":2,"url":"/index.php/Home/product/index/ishit/2","name":"积分兑购","imgs":""},{"id":3,"url":"","name":"积分活动","imgs":""},{"id":4,"url":"","name":"线下商家","imgs":""}],"adp":[{"categoryid":0,"url":"","goodsid":"","imgs":""},{"categoryid":1,"url":"","goodsid":"","imgs":""},{"categoryid":2,"url":"","goodsid":"","imgs":""},{"categoryid":3,"url":"","goodsid":"","imgs":""},{"categoryid":4,"url":"","goodsid":"","imgs":""}],"article":[{"id":"1","title":"和创会\u2014向这个最伟大的全民创业时代致敬！ ","addtime":"2016-08-05","row_number":"1"},{"id":"2","title":"和创会部分厂家最新运费政策","addtime":"2016-08-05","row_number":"2"},{"id":"14","title":"中国梦·赢在新\u2014\u2014和创会新春招商峰会","addtime":"2016-09-06","row_number":"3"},{"id":"15","title":"赢战猴年.和创未来 \u2014\u2014聚焦中原创业峰会","addtime":"2016-09-10","row_number":"4"},{"id":"16","title":"健康·创业·公益行\u2014\u2014汝州站","addtime":"2016-09-10","row_number":"5"},{"id":"17","title":"健康·创业·公益行\u2014\u2014封丘站","addtime":"2016-09-10","row_number":"6"}]}}
     */

    private DataBean data;

    public HomepageBean(DataBean data) {
        this.data = data;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * status : 1
         * message : 加载成功！
         * list : {"banner":[{"style":0,"url":"","urlid":"","imgs":"http://img.hshc618.com//Upload/IntegralPro/2018-03-05/152025979068878.jpg"},{"style":1,"url":"","urlid":"","imgs":"http://img.hshc618.com//Upload/IntegralPro/2018-03-05/152025979068878.jpg"},{"style":2,"url":"","urlid":"","imgs":"http://img.hshc618.com//Upload/IntegralPro/2018-03-05/152025979068878.jpg"}],"icon":[{"id":1,"url":"/index.php/Home/product/index/ishit/1","name":"积分兑换","imgs":""},{"id":2,"url":"/index.php/Home/product/index/ishit/2","name":"积分兑购","imgs":""},{"id":3,"url":"","name":"积分活动","imgs":""},{"id":4,"url":"","name":"线下商家","imgs":""}],"adp":[{"categoryid":0,"url":"","goodsid":"","imgs":""},{"categoryid":1,"url":"","goodsid":"","imgs":""},{"categoryid":2,"url":"","goodsid":"","imgs":""},{"categoryid":3,"url":"","goodsid":"","imgs":""},{"categoryid":4,"url":"","goodsid":"","imgs":""}],"article":[{"id":"1","title":"和创会\u2014向这个最伟大的全民创业时代致敬！ ","addtime":"2016-08-05","row_number":"1"},{"id":"2","title":"和创会部分厂家最新运费政策","addtime":"2016-08-05","row_number":"2"},{"id":"14","title":"中国梦·赢在新\u2014\u2014和创会新春招商峰会","addtime":"2016-09-06","row_number":"3"},{"id":"15","title":"赢战猴年.和创未来 \u2014\u2014聚焦中原创业峰会","addtime":"2016-09-10","row_number":"4"},{"id":"16","title":"健康·创业·公益行\u2014\u2014汝州站","addtime":"2016-09-10","row_number":"5"},{"id":"17","title":"健康·创业·公益行\u2014\u2014封丘站","addtime":"2016-09-10","row_number":"6"}]}
         */

        private int status;
        private String message;
        private ListBean list;

        public DataBean(int status, String message, ListBean list) {
            this.status = status;
            this.message = message;
            this.list = list;
        }

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

        public ListBean getList() {
            return list;
        }

        public void setList(ListBean list) {
            this.list = list;
        }

        public static class ListBean {
            private List<BannerBean> banner;
            private List<IconBean> icon;
            private List<AdpBean> adp;
            private List<ArticleBean> article;

            public ListBean(List<BannerBean> banner, List<IconBean> icon, List<AdpBean> adp, List<ArticleBean> article) {
                this.banner = banner;
                this.icon = icon;
                this.adp = adp;
                this.article = article;
            }

            public List<BannerBean> getBanner() {
                return banner;
            }

            public void setBanner(List<BannerBean> banner) {
                this.banner = banner;
            }

            public List<IconBean> getIcon() {
                return icon;
            }

            public void setIcon(List<IconBean> icon) {
                this.icon = icon;
            }

            public List<AdpBean> getAdp() {
                return adp;
            }

            public void setAdp(List<AdpBean> adp) {
                this.adp = adp;
            }

            public List<ArticleBean> getArticle() {
                return article;
            }

            public void setArticle(List<ArticleBean> article) {
                this.article = article;
            }

            public static class BannerBean {
                /**
                 * style : 0
                 * url :
                 * urlid :
                 * imgs : http://img.hshc618.com//Upload/IntegralPro/2018-03-05/152025979068878.jpg
                 */

                private int style;
                private String url;
                private String urlid;
                private String imgs;

                public BannerBean(int style, String url, String urlid, String imgs) {
                    this.style = style;
                    this.url = url;
                    this.urlid = urlid;
                    this.imgs = imgs;
                }

                public int getStyle() {
                    return style;
                }

                public void setStyle(int style) {
                    this.style = style;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getUrlid() {
                    return urlid;
                }

                public void setUrlid(String urlid) {
                    this.urlid = urlid;
                }

                public String getImgs() {
                    return imgs;
                }

                public void setImgs(String imgs) {
                    this.imgs = imgs;
                }
            }

            public static class IconBean {
                /**
                 * id : 1
                 * url : /index.php/Home/product/index/ishit/1
                 * name : 积分兑换
                 * imgs :
                 */

                private int id;
                private String url;
                private String name;
                private String imgs;

                public IconBean(int id, String url, String name, String imgs) {
                    this.id = id;
                    this.url = url;
                    this.name = name;
                    this.imgs = imgs;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getImgs() {
                    return imgs;
                }

                public void setImgs(String imgs) {
                    this.imgs = imgs;
                }
            }

            public static class AdpBean {
                /**
                 * categoryid : 0
                 * url :
                 * goodsid :
                 * imgs :
                 */

                private int categoryid;
                private String url;
                private String goodsid;
                private String imgs;

                public AdpBean(int categoryid, String url, String goodsid, String imgs) {
                    this.categoryid = categoryid;
                    this.url = url;
                    this.goodsid = goodsid;
                    this.imgs = imgs;
                }

                public int getCategoryid() {
                    return categoryid;
                }

                public void setCategoryid(int categoryid) {
                    this.categoryid = categoryid;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getGoodsid() {
                    return goodsid;
                }

                public void setGoodsid(String goodsid) {
                    this.goodsid = goodsid;
                }

                public String getImgs() {
                    return imgs;
                }

                public void setImgs(String imgs) {
                    this.imgs = imgs;
                }
            }

            public static class ArticleBean {
                /**
                 * id : 1
                 * title : 和创会—向这个最伟大的全民创业时代致敬！
                 * addtime : 2016-08-05
                 * row_number : 1
                 */

                private String id;
                private String title;
                private String addtime;
                private String row_number;

                public ArticleBean(String id, String title, String addtime, String row_number) {
                    this.id = id;
                    this.title = title;
                    this.addtime = addtime;
                    this.row_number = row_number;
                }

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

                public String getRow_number() {
                    return row_number;
                }

                public void setRow_number(String row_number) {
                    this.row_number = row_number;
                }
            }
        }
    }
}
