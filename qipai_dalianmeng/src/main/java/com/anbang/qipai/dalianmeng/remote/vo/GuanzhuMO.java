package com.anbang.qipai.dalianmeng.remote.vo;

import java.util.Map;

public class GuanzhuMO {
    private String guanzhuId;
    private String headurlimg;
    private String nickname;

    public GuanzhuMO(){

    }

    public GuanzhuMO(Map data){
        guanzhuId= (String) data.get("guanzhuId");
        headurlimg= (String) data.get("headurlimg");
        nickname= (String) data.get("nickname");
    }

    public String getGuanzhuId() {
        return guanzhuId;
    }

    public void setGuanzhuId(String guanzhuId) {
        this.guanzhuId = guanzhuId;
    }

    public String getHeadurlimg() {
        return headurlimg;
    }

    public void setHeadurlimg(String headurlimg) {
        this.headurlimg = headurlimg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
