package com.hechuang.hepay.bean;

/**
 * Created by Android_xu on 2017/11/13.
 */

public class NoticeListBean {
    private String id;
    private String title;
    private String categoryid;
    private String addtime;
    private String RowNumber;
    private String categoryname;
    private String imgpath;

    public NoticeListBean(String id, String title, String categoryid, String addtime, String rowNumber, String categoryname
            , String imgpath) {
        this.id = id;
        this.title = title;
        this.categoryid = categoryid;
        this.addtime = addtime;
        this.RowNumber = rowNumber;
        this.categoryname = categoryname;
        this.imgpath = imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public void setRowNumber(String rowNumber) {
        RowNumber = rowNumber;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public String getAddtime() {
        return addtime;
    }

    public String getRowNumber() {
        return RowNumber;
    }

    public String getCategoryname() {
        return categoryname;
    }
}
