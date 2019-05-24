package com.hechuang.hepay.bean;

import java.util.List;

/**
 * Created by Android_xu on 2018/3/9.
 */

public class Union_list_Bean {

    /**
     * data : {"list":[{"ID":"3550","ShopName":"闹闹测试","ShopPhoto":"http://www.hetianpay.com/Public/static/images/emptypic.png","Province":"河南省","City":"郑州市","County":"中原区","Address":"河南省-郑州市-中原区-132456测试123456","Status":"1","ShopType":"2","Hits":"0","Elite":"0","ShopMapX":"113.5572814248","ShopMapY":"34.7794742932","ShopContent":"浑身颤抖胡从撒擦","CategoryID":"3","tags":"1,2,5","RowNumber":"5","distance":"12138.43Km","shopTags":[{"id":"4677","tagid":"1","unshopid":"3550","name":"可刷卡"},{"id":"4678","tagid":"2","unshopid":"3550","name":"WIFI"},{"id":"4679","tagid":"5","unshopid":"3550","name":"停车位"}],"Categoryname":"家居装饰"}],"status":1,"msg":"加载成功"}
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
         * list : [{"ID":"3550","ShopName":"闹闹测试","ShopPhoto":"http://www.hetianpay.com/Public/static/images/emptypic.png","Province":"河南省","City":"郑州市","County":"中原区","Address":"河南省-郑州市-中原区-132456测试123456","Status":"1","ShopType":"2","Hits":"0","Elite":"0","ShopMapX":"113.5572814248","ShopMapY":"34.7794742932","ShopContent":"浑身颤抖胡从撒擦","CategoryID":"3","tags":"1,2,5","RowNumber":"5","distance":"12138.43Km","shopTags":[{"id":"4677","tagid":"1","unshopid":"3550","name":"可刷卡"},{"id":"4678","tagid":"2","unshopid":"3550","name":"WIFI"},{"id":"4679","tagid":"5","unshopid":"3550","name":"停车位"}],"Categoryname":"家居装饰"}]
         * status : 1
         * msg : 加载成功
         */

        private int status;
        private String msg;
        private List<ListBean> list;

        @Override
        public String toString() {
            return "DataBean{" +
                    "status=" + status +
                    ", msg='" + msg + '\'' +
                    ", list=" + list +
                    '}';
        }

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
             * ID : 3550
             * ShopName : 闹闹测试
             * ShopPhoto : http://www.hetianpay.com/Public/static/images/emptypic.png
             * Province : 河南省
             * City : 郑州市
             * County : 中原区
             * Address : 河南省-郑州市-中原区-132456测试123456
             * Status : 1
             * ShopType : 2
             * Hits : 0
             * Elite : 0
             * ShopMapX : 113.5572814248
             * ShopMapY : 34.7794742932
             * ShopContent : 浑身颤抖胡从撒擦
             * CategoryID : 3
             * tags : 1,2,5
             * RowNumber : 5
             * distance : 12138.43Km
             * shopTags : [{"id":"4677","tagid":"1","unshopid":"3550","name":"可刷卡"},{"id":"4678","tagid":"2","unshopid":"3550","name":"WIFI"},{"id":"4679","tagid":"5","unshopid":"3550","name":"停车位"}]
             * Categoryname : 家居装饰
             */

            private String ID;
            private String ShopName;
            private String ShopPhoto;
            private String Province;
            private String City;
            private String County;
            private String Address;
            private String Status;
            private String ShopType;
            private String Hits;
            private String Elite;
            private String ShopMapX;
            private String ShopMapY;
            private String ShopContent;
            private String CategoryID;
            private String tags;
            private String RowNumber;
            private String distance;
            private String Categoryname;
            private String ggfeelv;
            private String sale;
            private List<ShopTagsBean> shopTags;

            public String getGgfeelv() {
                return ggfeelv;
            }

            public String getSale() {
                return sale;
            }

            public void setSale(String sale) {
                this.sale = sale;
            }

            public void setGgfeelv(String ggfeelv) {
                this.ggfeelv = ggfeelv;
            }

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public String getShopName() {
                return ShopName;
            }

            public void setShopName(String ShopName) {
                this.ShopName = ShopName;
            }

            public String getShopPhoto() {
                return ShopPhoto;
            }

            public void setShopPhoto(String ShopPhoto) {
                this.ShopPhoto = ShopPhoto;
            }

            public String getProvince() {
                return Province;
            }

            public void setProvince(String Province) {
                this.Province = Province;
            }

            public String getCity() {
                return City;
            }

            public void setCity(String City) {
                this.City = City;
            }

            public String getCounty() {
                return County;
            }

            public void setCounty(String County) {
                this.County = County;
            }

            public String getAddress() {
                return Address;
            }

            public void setAddress(String Address) {
                this.Address = Address;
            }

            public String getStatus() {
                return Status;
            }

            public void setStatus(String Status) {
                this.Status = Status;
            }

            public String getShopType() {
                return ShopType;
            }

            public void setShopType(String ShopType) {
                this.ShopType = ShopType;
            }

            public String getHits() {
                return Hits;
            }

            public void setHits(String Hits) {
                this.Hits = Hits;
            }

            public String getElite() {
                return Elite;
            }

            public void setElite(String Elite) {
                this.Elite = Elite;
            }

            public String getShopMapX() {
                return ShopMapX;
            }

            public void setShopMapX(String ShopMapX) {
                this.ShopMapX = ShopMapX;
            }

            public String getShopMapY() {
                return ShopMapY;
            }

            public void setShopMapY(String ShopMapY) {
                this.ShopMapY = ShopMapY;
            }

            public String getShopContent() {
                return ShopContent;
            }

            public void setShopContent(String ShopContent) {
                this.ShopContent = ShopContent;
            }

            public String getCategoryID() {
                return CategoryID;
            }

            public void setCategoryID(String CategoryID) {
                this.CategoryID = CategoryID;
            }

            public String getTags() {
                return tags;
            }

            public void setTags(String tags) {
                this.tags = tags;
            }

            public String getRowNumber() {
                return RowNumber;
            }

            public void setRowNumber(String RowNumber) {
                this.RowNumber = RowNumber;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getCategoryname() {
                return Categoryname;
            }

            public void setCategoryname(String Categoryname) {
                this.Categoryname = Categoryname;
            }

            public List<ShopTagsBean> getShopTags() {
                return shopTags;
            }

            public void setShopTags(List<ShopTagsBean> shopTags) {
                this.shopTags = shopTags;
            }

            public static class ShopTagsBean {
                /**
                 * id : 4677
                 * tagid : 1
                 * unshopid : 3550
                 * name : 可刷卡
                 */

                private String id;
                private String tagid;
                private String unshopid;
                private String name;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getTagid() {
                    return tagid;
                }

                public void setTagid(String tagid) {
                    this.tagid = tagid;
                }

                public String getUnshopid() {
                    return unshopid;
                }

                public void setUnshopid(String unshopid) {
                    this.unshopid = unshopid;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                @Override
                public String toString() {
                    return "ShopTagsBean{" +
                            "id='" + id + '\'' +
                            ", tagid='" + tagid + '\'' +
                            ", unshopid='" + unshopid + '\'' +
                            ", name='" + name + '\'' +
                            '}';
                }
            }

            @Override
            public String toString() {
                return "ListBean{" +
                        "ID='" + ID + '\'' +
                        ", ShopName='" + ShopName + '\'' +
                        ", ShopPhoto='" + ShopPhoto + '\'' +
                        ", Province='" + Province + '\'' +
                        ", City='" + City + '\'' +
                        ", County='" + County + '\'' +
                        ", Address='" + Address + '\'' +
                        ", Status='" + Status + '\'' +
                        ", ShopType='" + ShopType + '\'' +
                        ", Hits='" + Hits + '\'' +
                        ", Elite='" + Elite + '\'' +
                        ", ShopMapX='" + ShopMapX + '\'' +
                        ", ShopMapY='" + ShopMapY + '\'' +
                        ", ShopContent='" + ShopContent + '\'' +
                        ", CategoryID='" + CategoryID + '\'' +
                        ", tags='" + tags + '\'' +
                        ", RowNumber='" + RowNumber + '\'' +
                        ", distance='" + distance + '\'' +
                        ", Categoryname='" + Categoryname + '\'' +
                        ", shopTags=" + shopTags +
                        '}';
            }
        }
    }
}
