package com.hechuang.hepay.bean;

/**
 * Created by Android_xu on 2018/3/7.
 */

public class VersionBean {

    /**
     * versionCode : 2.8
     * versionName : 43
     * status : 1
     * auto : 1
     * msg : 成功
     */

    private String versionCode;
    private String versionName;
    private String status;
    private String auto;
    private String where;
    private String browser;
    private String msg;

    @Override
    public String toString() {
        return "VersionBean{" +
                "versionCode='" + versionCode + '\'' +
                ", versionName='" + versionName + '\'' +
                ", status='" + status + '\'' +
                ", auto='" + auto + '\'' +
                ", where='" + where + '\'' +
                ", browser='" + browser + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getWhere() {
        return where;
    }

    public String getBrowser() {
        return browser;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuto() {
        return auto;
    }

    public void setAuto(String auto) {
        this.auto = auto;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
