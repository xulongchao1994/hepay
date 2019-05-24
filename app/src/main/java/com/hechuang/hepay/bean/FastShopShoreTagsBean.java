package com.hechuang.hepay.bean;

/**
 * 联盟商家（可刷卡......）
 */

public class FastShopShoreTagsBean {
    private String id;
    private String tagid;
    private String unshopid;
    private String row_number;
    private String name;

    /**
     * @param id
     * @param tagid
     * @param unshopid
     * @param row_number
     * @param name
     */
    public FastShopShoreTagsBean(String id, String tagid, String unshopid, String row_number, String name) {
        this.id = id;
        this.tagid = tagid;
        this.unshopid = unshopid;
        this.row_number = row_number;
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTagid(String tagid) {
        this.tagid = tagid;
    }

    public void setUnshopid(String unshopid) {
        this.unshopid = unshopid;
    }

    public void setRow_number(String row_number) {
        this.row_number = row_number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getTagid() {
        return tagid;
    }

    public String getUnshopid() {
        return unshopid;
    }

    public String getRow_number() {
        return row_number;
    }

    public String getName() {
        return name;
    }
}
