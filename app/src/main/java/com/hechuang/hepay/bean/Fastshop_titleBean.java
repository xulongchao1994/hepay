package com.hechuang.hepay.bean;

/**
 * 联盟商家模糊搜索
 */

public class Fastshop_titleBean {

    /**
     * Id : 1
     * name : 餐饮美食
     * pid : 0
     * oid : 0
     * Lay : 1
     * IdPath :
     * disable : 1
     * sort : 0
     */

    private String Id;
    private String name;
    private String pid;
    private String oid;
    private String Lay;
    private String IdPath;
    private String disable;
    private String sort;

    public Fastshop_titleBean(String id, String name, String pid, String oid, String lay, String idPath, String disable, String sort) {
        Id = id;
        this.name = name;
        this.pid = pid;
        this.oid = oid;
        Lay = lay;
        IdPath = idPath;
        this.disable = disable;
        this.sort = sort;
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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getLay() {
        return Lay;
    }

    public void setLay(String Lay) {
        this.Lay = Lay;
    }

    public String getIdPath() {
        return IdPath;
    }

    public void setIdPath(String IdPath) {
        this.IdPath = IdPath;
    }

    public String getDisable() {
        return disable;
    }

    public void setDisable(String disable) {
        this.disable = disable;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

}
