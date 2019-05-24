package com.hechuang.hepay.bean;

import java.util.List;

/**
 * Created by Android_xu on 2018/2/27.
 */

public class WthrcdnBean {

    /**
     * data : {"yesterday":{"date":"26日星期一","high":"高温 20℃","fx":"西南风","low":"低温 6℃","fl":"<![CDATA[<3级]]>","type":"多云"},"city":"郑州","aqi":"243","forecast":[{"date":"27日星期二","high":"高温 15℃","fengli":"<![CDATA[<3级]]>","low":"低温 5℃","fengxiang":"东北风","type":"小雨"},{"date":"28日星期三","high":"高温 19℃","fengli":"<![CDATA[3-4级]]>","low":"低温 7℃","fengxiang":"西南风","type":"晴"},{"date":"1日星期四","high":"高温 12℃","fengli":"<![CDATA[3-4级]]>","low":"低温 3℃","fengxiang":"东风","type":"多云"},{"date":"2日星期五","high":"高温 18℃","fengli":"<![CDATA[<3级]]>","low":"低温 5℃","fengxiang":"南风","type":"多云"},{"date":"3日星期六","high":"高温 22℃","fengli":"<![CDATA[3-4级]]>","low":"低温 8℃","fengxiang":"南风","type":"小雨"}],"ganmao":"将有一次强降温过程，极易发生感冒，请特别注意增加衣服保暖防寒。","wendu":"11"}
     * status : 1000
     * desc : OK
     */

    private DataBean data;
    private String status;
    private String desc;

    public WthrcdnBean(DataBean data, String status, String desc) {
        this.data = data;
        this.status = status;
        this.desc = desc;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public static class DataBean {

        private String city;
        private String aqi;
        private String ganmao;
        private String wendu;
        private List<ForecastBean> forecast;

        public DataBean( String city,  String ganmao, String wendu, List<ForecastBean> forecast) {
            this.city = city;
            this.ganmao = ganmao;
            this.wendu = wendu;
            this.forecast = forecast;
        }


        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getGanmao() {
            return ganmao;
        }

        public void setGanmao(String ganmao) {
            this.ganmao = ganmao;
        }

        public String getWendu() {
            return wendu;
        }

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }

        public List<ForecastBean> getForecast() {
            return forecast;
        }

        public void setForecast(List<ForecastBean> forecast) {
            this.forecast = forecast;
        }

        public static class YesterdayBean {
            /**
             * date : 26日星期一
             * high : 高温 20℃
             * fx : 西南风
             * low : 低温 6℃
             * fl : <![CDATA[<3级]]>
             * type : 多云
             */

            private String date;
            private String high;
            private String fx;
            private String low;
            private String fl;
            private String type;

            public YesterdayBean(String date, String high, String fx, String low, String fl, String type) {
                this.date = date;
                this.high = high;
                this.fx = fx;
                this.low = low;
                this.fl = fl;
                this.type = type;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getFx() {
                return fx;
            }

            public void setFx(String fx) {
                this.fx = fx;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class ForecastBean {
            /**
             * date : 27日星期二
             * high : 高温 15℃
             * fengli : <![CDATA[<3级]]>
             * low : 低温 5℃
             * fengxiang : 东北风
             * type : 小雨
             */

            private String date;
            private String high;
            private String fengli;
            private String low;
            private String fengxiang;
            private String type;

            public ForecastBean(String date, String high, String fengli, String low, String fengxiang, String type) {
                this.date = date;
                this.high = high;
                this.fengli = fengli;
                this.low = low;
                this.fengxiang = fengxiang;
                this.type = type;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getFengli() {
                return fengli;
            }

            public void setFengli(String fengli) {
                this.fengli = fengli;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getFengxiang() {
                return fengxiang;
            }

            public void setFengxiang(String fengxiang) {
                this.fengxiang = fengxiang;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
