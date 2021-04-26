package com.anbang.qipai.dalianmeng.plan.bean;

import com.anbang.qipai.dalianmeng.plan.bean.game.Game;

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
    private List<PayForContribution> contribution;  //区间
    private Integer minPower;                       //最低能量
    private Integer powerLimit;                     //能量限制
    private Double difen;                           //底分
    private String payType;                         //支付类型
    private double aaScore;                         //AA底分
    private String lixianchengfaScore;              //离线惩罚分
    private String lixianshichang;                  //离线时长
    private String yuanzifen;                       //园子分
    private double mengzhuAADifen;                  //盟主AA底分
    private Boolean zidongkaishi;                   //自动开始
    private Double zidongkaishiTime;                //自动开始时长

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

    public List<PayForContribution> getContribution() {
        return contribution;
    }

    public void setContribution(List<PayForContribution> contribution) {
        this.contribution = contribution;
    }

    public Integer getMinPower() {
        return minPower;
    }

    public void setMinPower(Integer minPower) {
        this.minPower = minPower;
    }

    public Double getDifen() {
        return difen;
    }

    public void setDifen(Double difen) {
        this.difen = difen;
    }

    public String getWanfaName() {
        return wanfaName;
    }

    public void setWanfaName(String wanfaName) {
        this.wanfaName = wanfaName;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public double getAaScore() {
        return aaScore;
    }

    public void setAaScore(double aaScore) {
        this.aaScore = aaScore;
    }

    public Integer getPowerLimit() {
        return powerLimit;
    }

    public void setPowerLimit(Integer powerLimit) {
        this.powerLimit = powerLimit;
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

    public double getMengzhuAADifen() {
        return mengzhuAADifen;
    }

    public void setMengzhuAADifen(double mengzhuAADifen) {
        this.mengzhuAADifen = mengzhuAADifen;
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
