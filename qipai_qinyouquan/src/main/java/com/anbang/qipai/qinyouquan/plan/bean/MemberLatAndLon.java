package com.anbang.qipai.qinyouquan.plan.bean;


/**
 * 玩家经纬度
 */
public class MemberLatAndLon {



    private String id;//id

    private String lat;//经度

    private String lon;//纬度

    private String repIP;//ip地址

    private String roomNo;//房间编号

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getRepIP() {
        return repIP;
    }

    public void setRepIP(String repIP) {
        this.repIP = repIP;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }
}
