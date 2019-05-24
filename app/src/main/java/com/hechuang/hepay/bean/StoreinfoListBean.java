package com.hechuang.hepay.bean;

import java.util.List;

/**
 */

public class StoreinfoListBean {
    private String id;
    private String userid;
    private String shopname;
    private String shopphoto;
    private List<tags> tagsList;
    private String shopMapx;
    private String shopmapy;
    private String addrass;
    private String moblis;
    private String tese;
    private String shopculture;
    private String shopcontent;
    private String shareaccounths;
    private String distance;
    private List<String> miniList;
    private String isshow;
    private String btnurl;
    private List<nearby> nearbyList;

    public StoreinfoListBean(String id, String userid, String shopname, String shopphoto, List<tags> tagsList, String shopMapx, String shopmapy, String addrass,
                             String moblis, String tese, String shopculture, String shopcontent, String shareaccounths,
                             String distance, String isshow, String btnurl, List<nearby> nearbyList, List<String> minis) {
        this.id = id;
        this.userid = userid;
        this.shopname = shopname;
        this.shopphoto = shopphoto;
        this.tagsList = tagsList;
        this.shopMapx = shopMapx;
        this.shopmapy = shopmapy;
        this.addrass = addrass;
        this.moblis = moblis;
        this.tese = tese;
        this.shopculture = shopculture;
        this.shopcontent = shopcontent;
        this.shareaccounths = shareaccounths;
        this.distance = distance;
        this.isshow = isshow;
        this.btnurl = btnurl;
        this.nearbyList = nearbyList;
        this.miniList = minis;
    }

    public void setMiniList(List<String> miniList) {
        this.miniList = miniList;
    }

    public List<String> getMiniList() {
        return miniList;
    }

    public void setIsshow(String isshow) {
        this.isshow = isshow;
    }

    public void setBtnurl(String btnurl) {
        this.btnurl = btnurl;
    }

    public String getIsshow() {
        return isshow;
    }

    public String getBtnurl() {
        return btnurl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setShareaccounths(String shareaccounths) {
        this.shareaccounths = shareaccounths;
    }

    public String getShareaccounths() {
        return shareaccounths;
    }

    public void setShopculture(String shopculture) {
        this.shopculture = shopculture;
    }

    public String getShopculture() {
        return shopculture;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public void setShopphoto(String shopphoto) {
        this.shopphoto = shopphoto;
    }

    public void setTagsList(List<tags> tagsList) {
        this.tagsList = tagsList;
    }

    public void setShopMapx(String shopMapx) {
        this.shopMapx = shopMapx;
    }

    public void setShopmapy(String shopmapy) {
        this.shopmapy = shopmapy;
    }

    public void setAddrass(String addrass) {
        this.addrass = addrass;
    }

    public void setMoblis(String moblis) {
        this.moblis = moblis;
    }

    public void setTese(String tese) {
        this.tese = tese;
    }

    public void setShopcontent(String shopcontent) {
        this.shopcontent = shopcontent;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setNearbyList(List<nearby> nearbyList) {
        this.nearbyList = nearbyList;
    }

    public String getUserid() {
        return userid;
    }

    public String getShopname() {
        return shopname;
    }

    public String getShopphoto() {
        return shopphoto;
    }

    public List<tags> getTagsList() {
        return tagsList;
    }

    public String getShopMapx() {
        return shopMapx;
    }

    public String getShopmapy() {
        return shopmapy;
    }

    public String getAddrass() {
        return addrass;
    }

    public String getMoblis() {
        return moblis;
    }

    public String getTese() {
        return tese;
    }

    public String getShopcontent() {
        return shopcontent;
    }

    public String getDistance() {
        return distance;
    }

    public List<nearby> getNearbyList() {
        return nearbyList;
    }

//    public static class mini {
//        private String imageuri;
//
//        public mini(String imageuri) {
//            this.imageuri = imageuri;
//        }
//
//        public String getImageuri() {
//            return imageuri;
//        }
//
//        public void setImageuri(String imageuri) {
//            this.imageuri = imageuri;
//        }
//    }

    public static class nearby {
        private String id;
        private String shopphoto;
        private String shopname;
        private String shopmapx;
        private String shopmapy;
        private String province;
        private String city;
        private String county;
        private String address;
        private String categoryid;
        private List<nearby.tags> tags;
        private String shareaccounths;
        private String categoryname;
        private String dis;

        public nearby(String id, String shopphoto, String shopname, String shopmapx, String shopmapy, String province,
                      String city, String county, String address, String categoryid, List<nearby.tags> tags,
                      String shareaccounths, String categoryname, String dis
        ) {
            this.id = id;
            this.shopphoto = shopphoto;
            this.shopname = shopname;
            this.shopmapx = shopmapx;
            this.shopmapy = shopmapy;
            this.province = province;
            this.city = city;
            this.county = county;
            this.address = address;
            this.categoryid = categoryid;
            this.tags = tags;
            this.shareaccounths = shareaccounths;
            this.categoryname = categoryname;
            this.dis = dis;
        }

        public void setShareaccounths(String shareaccounths) {
            this.shareaccounths = shareaccounths;
        }

        public void setCategoryname(String categoryname) {
            this.categoryname = categoryname;
        }

        public String getShareaccounths() {
            return shareaccounths;
        }

        public String getCategoryname() {
            return categoryname;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setShopphoto(String shopphoto) {
            this.shopphoto = shopphoto;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }

        public void setShopmapx(String shopmapx) {
            this.shopmapx = shopmapx;
        }

        public void setShopmapy(String shopmapy) {
            this.shopmapy = shopmapy;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setCategoryid(String categoryid) {
            this.categoryid = categoryid;
        }

        public void setTags(List<nearby.tags> tags) {
            this.tags = tags;
        }

        public void setDis(String dis) {
            this.dis = dis;
        }

        public String getId() {
            return id;
        }

        public String getShopphoto() {
            return shopphoto;
        }

        public String getShopname() {
            return shopname;
        }

        public String getShopmapx() {
            return shopmapx;
        }

        public String getShopmapy() {
            return shopmapy;
        }

        public String getProvince() {
            return province;
        }

        public String getCity() {
            return city;
        }

        public String getCounty() {
            return county;
        }

        public String getAddress() {
            return address;
        }

        public String getCategoryid() {
            return categoryid;
        }

        public List<nearby.tags> getTags() {
            return tags;
        }

        public String getDis() {
            return dis;
        }

        public static class tags {
            String tag;

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getTag() {
                return tag;
            }

            public tags(String tag) {
                this.tag = tag;
            }
        }
    }

    public static class tags {
        private String tags;

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getTags() {
            return tags;
        }

        public tags(String tags) {
            this.tags = tags;
        }
    }
}
