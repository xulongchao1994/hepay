package com.hechuang.hepay.bean;

/**
 * Created by Android_xu on 2017/9/27.
 * 消息页面列表数据
 */

public class NewsBean {
    private String id;
    private String title;
    private String sourceID;
    private String msgtype;
    private String userid;
    private String ishasread;
    private String addtime;
    private String intro;

    public NewsBean(String id, String title, String sourceID, String msgtype, String userid, String ishasread, String addtime, String intro) {
        this.id = id;
        this.title = title;
        this.sourceID = sourceID;
        this.msgtype = msgtype;
        this.userid = userid;
        this.ishasread = ishasread;
        this.addtime = addtime;
        this.intro = intro;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSourceID(String sourceID) {
        this.sourceID = sourceID;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setIshasread(String ishasread) {
        this.ishasread = ishasread;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSourceID() {
        return sourceID;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public String getUserid() {
        return userid;
    }

    public String getIshasread() {
        return ishasread;
    }

    public String getAddtime() {
        return addtime;
    }

    public String getIntro() {
        return intro;
    }
}
