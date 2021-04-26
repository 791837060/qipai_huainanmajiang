package com.anbang.qipai.biji.web.vo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.anbang.qipai.biji.cqrs.c.domain.result.BijiJiesuanScore;
import com.dml.shisanshui.pai.paixing.PaixingSolution;

public class BijiJiesuanScoreVO {
    private BijiDaoScoreVO toudao;    //首道得分
    private BijiDaoScoreVO zhongdao;  //中道得分
    private BijiDaoScoreVO weidao;    //尾道得分
    private List<PlayerJiesuanScoreVO> playerDaoList = new ArrayList<>();
    private Set<String> daqiangPlayerSet;

    private boolean quanhonghei;    //全黑红
    private boolean quanshun;       //全顺
    private boolean tongguan;       //通关
    private boolean sanqing;        //三清
    private boolean tonghuadatou;   //同花打头
    private boolean shunqingdatou;  //顺清打头
    private boolean shuangsantiao;  //双三条
    private boolean sizhang;        //四张
    private boolean shuangshunqing; //双顺清
    private boolean quanshunqing;   //全顺清
    private boolean quansantiao;    //全三条

    private double score;           //基础结算分
    private double value;           //总分


    public BijiJiesuanScoreVO() {

    }

    public BijiJiesuanScoreVO(BijiJiesuanScore bijiJiesuanScore) {
        PaixingSolution chupaiSolution = bijiJiesuanScore.getChupaiSolution();
        toudao = new BijiDaoScoreVO(bijiJiesuanScore.getToudao(), chupaiSolution.getToudao());
        zhongdao = new BijiDaoScoreVO(bijiJiesuanScore.getZhongdao(), chupaiSolution.getZhongdao());
        weidao = new BijiDaoScoreVO(bijiJiesuanScore.getWeidao(), chupaiSolution.getWeidao());
        bijiJiesuanScore.getPlayerDaoMap().values().forEach((playerDao) -> {
            playerDaoList.add(new PlayerJiesuanScoreVO(playerDao));
        });
        daqiangPlayerSet = new HashSet<>(bijiJiesuanScore.getDaqiangPlayerSet());

        quanhonghei = bijiJiesuanScore.isQuanhonghei();
        quanshun = bijiJiesuanScore.isQuanshun();
        tongguan = bijiJiesuanScore.isTongguan();
        sanqing = bijiJiesuanScore.isSanqing();
        tonghuadatou = bijiJiesuanScore.isTonghuadatou();
        shunqingdatou = bijiJiesuanScore.isShunqingdatou();
        shuangshunqing = bijiJiesuanScore.isShuangshunqing();
        shuangsantiao = bijiJiesuanScore.isShuangsantiao();
        sizhang = bijiJiesuanScore.isSizhang();
        quanshunqing = bijiJiesuanScore.isQuanshunqing();
        quansantiao = bijiJiesuanScore.isQuansantiao();

        score = bijiJiesuanScore.getScore();
        value = bijiJiesuanScore.getValue();
    }

    public BijiDaoScoreVO getToudao() {
        return toudao;
    }

    public void setToudao(BijiDaoScoreVO toudao) {
        this.toudao = toudao;
    }

    public BijiDaoScoreVO getZhongdao() {
        return zhongdao;
    }

    public void setZhongdao(BijiDaoScoreVO zhongdao) {
        this.zhongdao = zhongdao;
    }

    public BijiDaoScoreVO getWeidao() {
        return weidao;
    }

    public void setWeidao(BijiDaoScoreVO weidao) {
        this.weidao = weidao;
    }

    public List<PlayerJiesuanScoreVO> getPlayerDaoList() {
        return playerDaoList;
    }

    public void setPlayerDaoList(List<PlayerJiesuanScoreVO> playerDaoList) {
        this.playerDaoList = playerDaoList;
    }

    public Set<String> getDaqiangPlayerSet() {
        return daqiangPlayerSet;
    }

    public void setDaqiangPlayerSet(Set<String> daqiangPlayerSet) {
        this.daqiangPlayerSet = daqiangPlayerSet;
    }

    public boolean isQuanhonghei() {
        return quanhonghei;
    }

    public void setQuanhonghei(boolean quanhonghei) {
        this.quanhonghei = quanhonghei;
    }

    public boolean isQuanshun() {
        return quanshun;
    }

    public void setQuanshun(boolean quanshun) {
        this.quanshun = quanshun;
    }

    public boolean isTongguan() {
        return tongguan;
    }

    public void setTongguan(boolean tongguan) {
        this.tongguan = tongguan;
    }

    public boolean isSanqing() {
        return sanqing;
    }

    public void setSanqing(boolean sanqing) {
        this.sanqing = sanqing;
    }

    public boolean isShunqingdatou() {
        return shunqingdatou;
    }

    public void setShunqingdatou(boolean shunqingdatou) {
        this.shunqingdatou = shunqingdatou;
    }

    public boolean isShuangsantiao() {
        return shuangsantiao;
    }

    public void setShuangsantiao(boolean shuangsantiao) {
        this.shuangsantiao = shuangsantiao;
    }

    public boolean isSizhang() {
        return sizhang;
    }

    public void setSizhang(boolean sizhang) {
        this.sizhang = sizhang;
    }

    public boolean isQuanshunqing() {
        return quanshunqing;
    }

    public void setQuanshunqing(boolean quanshunqing) {
        this.quanshunqing = quanshunqing;
    }

    public boolean isQuansantiao() {
        return quansantiao;
    }

    public void setQuansantiao(boolean quansantiao) {
        this.quansantiao = quansantiao;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isTonghuadatou() {
        return tonghuadatou;
    }

    public void setTonghuadatou(boolean tonghuadatou) {
        this.tonghuadatou = tonghuadatou;
    }

    public boolean isShuangshunqing() {
        return shuangshunqing;
    }

    public void setShuangshunqing(boolean shuangshunqing) {
        this.shuangshunqing = shuangshunqing;
    }
}
