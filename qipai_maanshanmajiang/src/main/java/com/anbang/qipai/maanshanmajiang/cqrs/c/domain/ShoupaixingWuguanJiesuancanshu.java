package com.anbang.qipai.maanshanmajiang.cqrs.c.domain;

import com.dml.majiang.pai.MajiangPai;
import com.dml.majiang.pai.fenzu.GangType;
import com.dml.majiang.player.MajiangPlayer;
import com.dml.majiang.player.chupaizu.GangchuPaiZu;

import java.util.List;

/**
 * 手牌型无关结算参数
 *
 * @author lsc
 */
public class ShoupaixingWuguanJiesuancanshu {
    private boolean allXushupaiInSameCategory;   //全部为序数牌
    private boolean allXushupaiInSameAndNotZipaiCategory;  //全部为序数牌
    private boolean hasZipai;                    //有字牌
    private boolean qingyise;                    //清一色
    private boolean hunyise;                     //混一色
    private boolean fengyise;                    //风一色
    private int guipaiShu;                       //鬼牌数量
    private int chichupaiZuCount;                //吃出牌数量
    private int pengchuPaizuCount;               //碰出牌数量
    private int gangchuPaizuCount;               //杠出牌数量
    private int fangruShoupaiCount;              //手牌总数量
    private boolean hasMinggang;                 //有明杠
    private MajiangPai hupai;                    //胡的牌

    public ShoupaixingWuguanJiesuancanshu(MajiangPlayer player, MajiangPai huPai) {
        allXushupaiInSameCategory = player.allXushupaiInSameCategory(huPai);//序数牌花色相同
        allXushupaiInSameAndNotZipaiCategory = player.allXushupaiInSameAndNotZipaiCategory(huPai);//序数牌花色相同
        hasZipai = player.hasZipai();                                       //有字牌字牌
        qingyise = (allXushupaiInSameCategory && !hasZipai);                //清一色（同花色 没有字牌）
        hunyise = (allXushupaiInSameAndNotZipaiCategory && hasZipai);       //混一色（同花色 有字牌）
        fengyise = !player.hasXushupai();                                   //凤一色（没有序数牌）
        guipaiShu = player.countGuipai();                                   //鬼牌数量
        chichupaiZuCount = player.countChichupaiZu();                       //吃出牌数量
        pengchuPaizuCount = player.countPengchupaiZu();                     //碰出牌数量
        gangchuPaizuCount=player.countGangchupaiZu();                       //杠出牌数量
        fangruShoupaiCount = player.countAllFangruShoupai();                //手牌总数量
        if (gangchuPaizuCount > 0) {
            List<GangchuPaiZu> gangchupaiZuList = player.getGangchupaiZuList();
            for (GangchuPaiZu gangchuPaiZu : gangchupaiZuList) {
                if (gangchuPaiZu.getGangType().equals(GangType.kezigangmo)
                        || gangchuPaiZu.getGangType().equals(GangType.kezigangshoupai)
                        || gangchuPaiZu.getGangType().equals(GangType.gangdachu)) {
                    hasMinggang = true;
                    break;
                }
            }
        }
        this.hupai = huPai;                                                 //胡的牌
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

    public boolean isFengyise() {
        return fengyise;
    }

    public void setFengyise(boolean fengyise) {
        this.fengyise = fengyise;
    }

    public int getPengchuPaizuCount() {
        return pengchuPaizuCount;
    }

    public void setPengchuPaizuCount(int pengchuPaizuCount) {
        this.pengchuPaizuCount = pengchuPaizuCount;
    }

    public MajiangPai getHupai() {
        return hupai;
    }

    public void setHupai(MajiangPai hupai) {
        this.hupai = hupai;
    }

    public int getGangchuPaizuCount() {
        return gangchuPaizuCount;
    }

    public void setGangchuPaizuCount(int gangchuPaizuCount) {
        this.gangchuPaizuCount = gangchuPaizuCount;
    }

    public boolean isHasMinggang() {
        return hasMinggang;
    }

    public void setHasMinggang(boolean hasMinggang) {
        this.hasMinggang = hasMinggang;
    }

    public boolean isAllXushupaiInSameAndNotZipaiCategory() {
        return allXushupaiInSameAndNotZipaiCategory;
    }

    public void setAllXushupaiInSameAndNotZipaiCategory(boolean allXushupaiInSameAndNotZipaiCategory) {
        this.allXushupaiInSameAndNotZipaiCategory = allXushupaiInSameAndNotZipaiCategory;
    }
}
