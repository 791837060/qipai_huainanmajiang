package com.anbang.qipai.maanshanmajiang.web.vo;

import java.util.List;
import java.util.Map;

import com.dml.majiang.pai.MajiangPai;
import com.dml.majiang.pai.XushupaiCategory;
import com.dml.majiang.pai.valueobj.MajiangPaiValueObject;
import com.dml.majiang.player.action.MajiangPlayerAction;
import com.dml.majiang.player.chupaizu.ChichuPaiZu;
import com.dml.majiang.player.chupaizu.GangchuPaiZu;
import com.dml.majiang.player.chupaizu.PengchuPaiZu;
import com.dml.majiang.player.valueobj.MajiangPlayerValueObject;
import com.dml.majiang.position.MajiangPosition;

public class MajiangPlayerValueObjectVO {

    private String id;
    /**
     * 门风
     */
    private MajiangPosition menFeng;
    //剩下的
    private FangruShoupaiListVO fangruShoupaiList;
    /**
     * 公开的牌，不能行牌
     */
    private List<MajiangPai> publicPaiList;

    //可作的动作
    private List<MajiangPlayerAction> actionCandidates;

    private Map<MajiangPai, List<MajiangPai>> hupaiCandidates;

    private List<MajiangPai> kehuCandidates;

    /**
     * 刚摸进待处理的手牌（未放入）
     */
    private MajiangPaiValueObject gangmoShoupai;

    /**
     * 打出的牌
     */
    private List<MajiangPai> dachupaiList;

    private List<ChichuPaiZu> chichupaiZuList;
    private List<PengchuPaiZu> pengchupaiZuList;
    private List<GangchuPaiZu> gangchupaiZuList;

    private boolean watingForMe = false;
    /**
     * 即时结算杠分
     */
    public int gangScore = 0;
    /**
     * 定缺类型
     */
    private XushupaiCategory quemen;
    /**
     * 建议定缺类型
     */
    private XushupaiCategory suggestQuemen;

    public MajiangPlayerValueObjectVO(MajiangPlayerValueObject majiangPlayerValueObject) {
        id = majiangPlayerValueObject.getId();
        menFeng = majiangPlayerValueObject.getMenFeng();
        fangruShoupaiList = new FangruShoupaiListVO(majiangPlayerValueObject.getFangruShoupaiList(), majiangPlayerValueObject.getFangruGuipaiList(), majiangPlayerValueObject.getTotalShoupaiCount());
        publicPaiList = majiangPlayerValueObject.getPublicPaiList();
        actionCandidates = majiangPlayerValueObject.getActionCandidates();
        if (actionCandidates != null && !actionCandidates.isEmpty()) {
            watingForMe = true;
        }
        hupaiCandidates = majiangPlayerValueObject.getHupaiCandidates();
        kehuCandidates = majiangPlayerValueObject.getKehuCandidates();
        gangmoShoupai = majiangPlayerValueObject.getGangmoShoupai();
        dachupaiList = majiangPlayerValueObject.getDachupaiList();
        chichupaiZuList = majiangPlayerValueObject.getChichupaiZuList();
        pengchupaiZuList = majiangPlayerValueObject.getPengchupaiZuList();
        gangchupaiZuList = majiangPlayerValueObject.getGangchupaiZuList();
        gangScore = majiangPlayerValueObject.getGangScore();
        quemen = majiangPlayerValueObject.getQuemen();
        suggestQuemen = majiangPlayerValueObject.getSuggestQuemen();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MajiangPosition getMenFeng() {
        return menFeng;
    }

    public void setMenFeng(MajiangPosition menFeng) {
        this.menFeng = menFeng;
    }

    public FangruShoupaiListVO getFangruShoupaiList() {
        return fangruShoupaiList;
    }

    public void setFangruShoupaiList(FangruShoupaiListVO fangruShoupaiList) {
        this.fangruShoupaiList = fangruShoupaiList;
    }

    public List<MajiangPai> getPublicPaiList() {
        return publicPaiList;
    }

    public void setPublicPaiList(List<MajiangPai> publicPaiList) {
        this.publicPaiList = publicPaiList;
    }

    public List<MajiangPlayerAction> getActionCandidates() {
        return actionCandidates;
    }

    public void setActionCandidates(List<MajiangPlayerAction> actionCandidates) {
        this.actionCandidates = actionCandidates;
    }

    public Map<MajiangPai, List<MajiangPai>> getHupaiCandidates() {
        return hupaiCandidates;
    }

    public void setHupaiCandidates(Map<MajiangPai, List<MajiangPai>> hupaiCandidates) {
        this.hupaiCandidates = hupaiCandidates;
    }

    public List<MajiangPai> getKehuCandidates() {
        return kehuCandidates;
    }

    public void setKehuCandidates(List<MajiangPai> kehuCandidates) {
        this.kehuCandidates = kehuCandidates;
    }

    public MajiangPaiValueObject getGangmoShoupai() {
        return gangmoShoupai;
    }

    public void setGangmoShoupai(MajiangPaiValueObject gangmoShoupai) {
        this.gangmoShoupai = gangmoShoupai;
    }

    public List<MajiangPai> getDachupaiList() {
        return dachupaiList;
    }

    public void setDachupaiList(List<MajiangPai> dachupaiList) {
        this.dachupaiList = dachupaiList;
    }

    public List<ChichuPaiZu> getChichupaiZuList() {
        return chichupaiZuList;
    }

    public void setChichupaiZuList(List<ChichuPaiZu> chichupaiZuList) {
        this.chichupaiZuList = chichupaiZuList;
    }

    public List<PengchuPaiZu> getPengchupaiZuList() {
        return pengchupaiZuList;
    }

    public void setPengchupaiZuList(List<PengchuPaiZu> pengchupaiZuList) {
        this.pengchupaiZuList = pengchupaiZuList;
    }

    public List<GangchuPaiZu> getGangchupaiZuList() {
        return gangchupaiZuList;
    }

    public void setGangchupaiZuList(List<GangchuPaiZu> gangchupaiZuList) {
        this.gangchupaiZuList = gangchupaiZuList;
    }

    public boolean isWatingForMe() {
        return watingForMe;
    }

    public void setWatingForMe(boolean watingForMe) {
        this.watingForMe = watingForMe;
    }

    public int getGangScore() {
        return gangScore;
    }

    public void setGangScore(int gangScore) {
        this.gangScore = gangScore;
    }

    public XushupaiCategory getQuemen() {
        return quemen;
    }

    public void setQuemen(XushupaiCategory quemen) {
        this.quemen = quemen;
    }

    public XushupaiCategory getSuggestQuemen() {
        return suggestQuemen;
    }

    public void setSuggestQuemen(XushupaiCategory suggestQuemen) {
        this.suggestQuemen = suggestQuemen;
    }
}
