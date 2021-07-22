package com.anbang.qipai.huainanmajiang.cqrs.c.domain;

import com.dml.majiang.pai.MajiangPai;
import com.dml.majiang.player.MajiangPlayer;

/**
 * 手牌型无关结算参数
 *
 * @author lsc
 */
public class ShoupaixingWuguanJiesuancanshu {
    /**
     * 全部为序数牌
     */
    private boolean allXushupaiInSameCategory;
    /**
     * 有字牌
     */
    private boolean hasZipai;
    /**
     * 清一色
     */
    private boolean qingyise;
    /**
     * 混一色
     */
    private boolean hunyise;
    /**
     * 鬼牌数量
     */
    private int guipaiShu;
    /**
     * 吃出牌数量
     */
    private int chichupaiZuCount;
    /**
     * 手牌总数量
     */
    private int fangruShoupaiCount;

    public ShoupaixingWuguanJiesuancanshu(MajiangPlayer player, MajiangPai huPai) {
        allXushupaiInSameCategory = player.allXushupaiInSameCategory(huPai);//全部序数牌
        hasZipai = player.hasZipai();                                       //字牌
        qingyise = (allXushupaiInSameCategory && !hasZipai);                //清一色（都是序数牌 没有字牌）
        hunyise = (allXushupaiInSameCategory && hasZipai);                  //混一色（都是序数牌 有字牌）
        guipaiShu = player.countGuipai();                                   //鬼牌数量
        chichupaiZuCount = player.countChichupaiZu();                       //吃出牌数量
        fangruShoupaiCount = player.countAllFangruShoupai();                //手牌总数量
    }

    public boolean isHunyise() {
        return hunyise;
    }

    public void setHunyise(boolean hunyise) {
        this.hunyise = hunyise;
    }

    public boolean isQingyise() {
        return qingyise;
    }

    public void setQingyise(boolean qingyise) {
        this.qingyise = qingyise;
    }

    public boolean isAllXushupaiInSameCategory() {
        return allXushupaiInSameCategory;
    }

    public void setAllXushupaiInSameCategory(boolean allXushupaiInSameCategory) {
        this.allXushupaiInSameCategory = allXushupaiInSameCategory;
    }

    public boolean isHasZipai() {
        return hasZipai;
    }

    public void setHasZipai(boolean hasZipai) {
        this.hasZipai = hasZipai;
    }

    public int getChichupaiZuCount() {
        return chichupaiZuCount;
    }

    public void setChichupaiZuCount(int chichupaiZuCount) {
        this.chichupaiZuCount = chichupaiZuCount;
    }

    public int getGuipaiShu() {
        return guipaiShu;
    }

    public void setGuipaiShu(int guipaiShu) {
        this.guipaiShu = guipaiShu;
    }

    public int getFangruShoupaiCount() {
        return fangruShoupaiCount;
    }

    public void setFangruShoupaiCount(int fangruShoupaiCount) {
        this.fangruShoupaiCount = fangruShoupaiCount;
    }
}
