package com.anbang.qipai.qinyouquan.plan.bean;

import com.anbang.qipai.qinyouquan.plan.bean.game.Game;

import java.util.List;

/**
 * 联盟玩法
 */
public class LianmengWanfa {
    private String id;
    private String lianmengId;                      //联盟id
    private String wanfaName;                       //玩法名称
    private Game game;                              //游戏类型
    private List<String> laws;                      //玩法
    private Double difen;                           //底分
    private String payType;                         //支付类型
    private String lixianchengfaScore;              //离线惩罚分
    private String lixianshichang;                  //离线时长
    private Boolean zidongkaishi;                   //自动开始
    private Double zidongkaishiTime;                //自动开始时长
    private String yuanzifen;                       //园子分


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLianmengId() {
        return lianmengId;
    }

    public void setLianmengId(String lianmengId) {
        this.lianmengId = lianmengId;
    }

    public String getWanfaName() {
        return wanfaName;
    }

    public void setWanfaName(String wanfaName) {
        this.wanfaName = wanfaName;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<String> getLaws() {
        return laws;
    }

    public void setLaws(List<String> laws) {
        this.laws = laws;
    }

    public Double getDifen() {
        return difen;
    }

    public void setDifen(Double difen) {
        this.difen = difen;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getLixianchengfaScore() {
        return lixianchengfaScore;
    }

    public void setLixianchengfaScore(String lixianchengfaScore) {
        this.lixianchengfaScore = lixianchengfaScore;
    }

    public String getLixianshichang() {
        return lixianshichang;
    }

    public void setLixianshichang(String lixianshichang) {
        this.lixianshichang = lixianshichang;
    }

    public Boolean getZidongkaishi() {
        return zidongkaishi;
    }

    public void setZidongkaishi(Boolean zidongkaishi) {
        this.zidongkaishi = zidongkaishi;
    }

    public Double getZidongkaishiTime() {
        return zidongkaishiTime;
    }

    public void setZidongkaishiTime(Double zidongkaishiTime) {
        this.zidongkaishiTime = zidongkaishiTime;
    }

    public String getYuanzifen() {
        return yuanzifen;
    }

    public void setYuanzifen(String yuanzifen) {
        this.yuanzifen = yuanzifen;
    }
}
