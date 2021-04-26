package com.anbang.qipai.biji.cqrs.c.domain.result;

import com.anbang.qipai.biji.cqrs.c.domain.OptionalPlay;
import com.dml.shisanshui.pai.HuaSe;
import com.dml.shisanshui.pai.PukePai;
import com.dml.shisanshui.pai.PukePaiMian;
import com.dml.shisanshui.pai.paixing.Dao;
import com.dml.shisanshui.pai.paixing.Paixing;
import com.dml.shisanshui.pai.paixing.PaixingSolution;
import com.dml.shisanshui.pai.paixing.comparator.DaoComparator;

import java.math.BigDecimal;
import java.util.*;

/**
 * 大菠萝个人结算分
 */
public class BijiJiesuanScore {
    private PaixingSolution chupaiSolution; //牌型
    private List<PukePai> playerShoupai;    //玩家手牌
    private BijiDaoScore toudao;            //头道
    private BijiDaoScore zhongdao;          //中道
    private BijiDaoScore weidao;            //尾道
    private Map<String, PlayerJiesuanScore> playerDaoMap = new HashMap<>();
    private Set<String> daqiangPlayerSet = new HashSet<>();

    private boolean quanhonghei;    //全黑红
    private boolean quanshun;       //全顺
    private boolean tongguan;       //通关
    private boolean sanqing;        //三清
    private boolean tonghuadatou;   //同花打头
    private boolean shunqingdatou;  //顺清打头
    private boolean shuangsantiao;  //双三条
    private boolean sizhang;        //四张
    private boolean shuangsizhang;  //双四张
    private boolean shuangshunqing; //双顺清
    private boolean quanshunqing;   //全顺清
    private boolean quansantiao;    //全三条

    private double score;           //基础结算分
    private double value;           //总分

    private int toudaoWinCount;     //头道获胜数
    private int zhongdaoWinCount;   //中道获胜数
    private int weidaoWinCount;     //尾道获胜数
    private double toudaoScore;     //头道分数
    private double zhongdaoScore;   //中道分数
    private double weidaoScore;     //尾道分数

    /**
     * 计算每个玩家的每道之间的基础分
     *
     * @param playerId      玩家ID
     * @param solution      玩家牌型
     * @param daoComparator 牌型比较器
     */
    public void calculatePlayerJiesuanScore(String playerId, PaixingSolution solution, DaoComparator daoComparator, boolean playerQipai, boolean playerQipai2) {
        toudaoWinCount += calculatePlayerToudao(solution, daoComparator, playerQipai, playerQipai2);
        zhongdaoWinCount += calculatePlayerZhongdao(solution, daoComparator, playerQipai, playerQipai2);
        weidaoWinCount += calculatePlayerWeidao(solution, daoComparator, playerQipai, playerQipai2);
    }

    /**
     * 计算 俩俩玩家之间头道大小
     *
     * @param solution      另一玩家牌型
     * @param daoComparator 道比较器
     */
    private int calculatePlayerToudao(PaixingSolution solution, DaoComparator daoComparator, boolean qipai, boolean qipai2) {
        if (qipai2 && !qipai) {
            return 1;
        }
        if (daoComparator.compare(chupaiSolution.getToudao(), solution.getToudao()) > 0 && !qipai) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 计算 俩俩玩家之间中道大小
     *
     * @param solution      另一玩家牌型
     * @param daoComparator 道比较器
     */
    private int calculatePlayerZhongdao(PaixingSolution solution, DaoComparator daoComparator, boolean qipai, boolean qipai2) {
        if (qipai2 && !qipai) {
            return 1;
        }
        if (daoComparator.compare(chupaiSolution.getZhongdao(), solution.getZhongdao()) > 0 && !qipai) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 计算 俩俩玩家之间尾道大小
     *
     * @param solution      另一玩家牌型
     * @param daoComparator 道比较器
     */
    private int calculatePlayerWeidao(PaixingSolution solution, DaoComparator daoComparator, boolean qipai, boolean qipai2) {
        if (qipai2 && !qipai) {
            return 1;
        }
        if (daoComparator.compare(chupaiSolution.getWeidao(), solution.getWeidao()) > 0 && !qipai) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 计算每道基础分
     *
     * @param renshu 人数
     */
    public void calculateScore(int renshu, int qipaiCount) {
        switch (renshu) {
            case 2:
                toudaoScore = erRenChangCalculateScore(toudaoWinCount, qipaiCount);
                zhongdaoScore = erRenChangCalculateScore(zhongdaoWinCount, qipaiCount);
                weidaoScore = erRenChangCalculateScore(weidaoWinCount, qipaiCount);
                break;
            case 3:
                toudaoScore = sanRenchangCalculateScore(toudaoWinCount, qipaiCount);
                zhongdaoScore = sanRenchangCalculateScore(zhongdaoWinCount, qipaiCount);
                weidaoScore = sanRenchangCalculateScore(weidaoWinCount, qipaiCount);
                break;
            case 4:
                toudaoScore = siRenchangCalculateScore(toudaoWinCount, qipaiCount);
                zhongdaoScore = siRenchangCalculateScore(zhongdaoWinCount, qipaiCount);
                weidaoScore = siRenchangCalculateScore(weidaoWinCount, qipaiCount);
                break;
            case 5:
                toudaoScore = wuRenchangCalculateScore(toudaoWinCount, qipaiCount);
                zhongdaoScore = wuRenchangCalculateScore(zhongdaoWinCount, qipaiCount);
                weidaoScore = wuRenchangCalculateScore(weidaoWinCount, qipaiCount);
                break;
        }


    }

    /**
     * 2人场每道分数计算
     *
     * @param yingshu 赢玩家数
     */
    public int erRenChangCalculateScore(int yingshu, int qipaiCount) {
        if (yingshu == 1) return 1;
        else if (yingshu == 0) {
            if (qipaiCount == 2) return 0;
            else return -1;
        } else return 0;
    }

    /**
     * 3人场每道分数计算
     *
     * @param yingshu    赢玩家数
     * @param qipaiCount 弃牌玩家数
     */
    public int sanRenchangCalculateScore(int yingshu, int qipaiCount) {
        if (yingshu == 2) {
            if (qipaiCount == 2) return 4;
            else return 3;
        } else if (yingshu == 1) return -1;
        else if (yingshu == 0) {
            if (qipaiCount == 3) return 0;
            else return -2;
        } else return 0;
    }

    /**
     * 4人场每道分数计算
     *
     * @param yingshu    赢玩家数
     * @param qipaiCount 弃牌玩家数
     */
    public int siRenchangCalculateScore(int yingshu, int qipaiCount) {
        if (yingshu == 3) {
            if (qipaiCount == 2) return 7;
            else if (qipaiCount == 3) return 9;
            else return 6;
        } else if (yingshu == 2) return -1;
        else if (yingshu == 1) return -2;
        else if (yingshu == 0) {
            if (qipaiCount == 4) return 0;
            else return -3;
        } else return 0;
    }

    /**
     * 5人场每道分数计算
     *
     * @param yingshu    赢玩家数
     * @param qipaiCount 弃牌玩家数
     */
    public int wuRenchangCalculateScore(int yingshu, int qipaiCount) {
        if (yingshu == 4) {
            if (qipaiCount == 2) return 11;
            else if (qipaiCount == 3) return 13;
            else if (qipaiCount == 4) return 16;
            else return 10;
        } else if (yingshu == 3) return -1;
        else if (yingshu == 2) return -2;
        else if (yingshu == 1) return -3;
        else if (yingshu == 0) {
            if (qipaiCount == 5) return -0;
            else return -4;
        } else return 0;
    }

    /**
     * 计算总基础分
     */
    public void calculateDaoScore(double difen) {
        toudao = new BijiDaoScore();
        toudao.setPaixing(chupaiSolution.getToudao().getPaixing());
        toudao.setTypecode(chupaiSolution.getToudao().getTypeCode());
        zhongdao = new BijiDaoScore();
        zhongdao.setPaixing(chupaiSolution.getZhongdao().getPaixing());
        zhongdao.setTypecode(chupaiSolution.getZhongdao().getTypeCode());
        weidao = new BijiDaoScore();
        weidao.setPaixing(chupaiSolution.getWeidao().getPaixing());
        weidao.setTypecode(chupaiSolution.getWeidao().getTypeCode());

        toudaoScore = new BigDecimal(Double.toString(difen)).multiply(new BigDecimal(toudaoScore)).doubleValue();
        zhongdaoScore = new BigDecimal(Double.toString(difen)).multiply(new BigDecimal(zhongdaoScore)).doubleValue();
        weidaoScore = new BigDecimal(Double.toString(difen)).multiply(new BigDecimal(weidaoScore)).doubleValue();

        toudao.jiesaunScore(toudaoScore);
        zhongdao.jiesaunScore(zhongdaoScore);
        weidao.jiesaunScore(weidaoScore);

        score = toudaoScore + zhongdaoScore + weidaoScore;
        jiesuan(score);
    }

    /**
     * 计算喜牌分
     */
    public int calculateXipai(int renshu, OptionalPlay optionalPlay, int qipaiCount, boolean qipai, double difen) {
        Dao toudao = chupaiSolution.getToudao();
        Dao zhongdao = chupaiSolution.getZhongdao();
        Dao weidao = chupaiSolution.getWeidao();
        int[] dianshuArray = new int[14];
        for (PukePai pukePai : playerShoupai) {
            PukePaiMian paiMian = pukePai.getPaiMian();
            dianshuArray[paiMian.dianShu().ordinal()]++;
        }

        if (optionalPlay.isTongguan() && !qipai) { //通关
            if (toudaoWinCount + zhongdaoWinCount + weidaoWinCount == (renshu - 1) * 3) {
                tongguan = true;
            }
        }

        if (optionalPlay.isQuanhonghei() && !qipai) { //全黑红
            int red = 0;
            int black = 0;
            for (PukePai pukePai : playerShoupai) {
                if (pukePai.getPaiMian().huaSe().equals(HuaSe.fangkuai) || pukePai.getPaiMian().huaSe().equals(HuaSe.hongxin)) {
                    red++;
                } else if (pukePai.getPaiMian().huaSe().equals(HuaSe.meihua) || pukePai.getPaiMian().huaSe().equals(HuaSe.heitao)) {
                    black++;
                }
            }
            if (red == 9 || black == 9) {
                quanhonghei = true;
            }
        }

        if (optionalPlay.isQuanshun() && !qipai) {    //全顺
            int index = 0;
            while (true) {
                if (dianshuArray[index] != 0) {
                    break;
                } else {
                    index++;
                }
            }
            int lianxu = 0;
            for (; index < dianshuArray.length; index++) {
                if (dianshuArray[index] == 1) {
                    lianxu++;
                } else {
                    break;
                }
            }
            if (lianxu == 9) {
                quanshun = true;
            }

        }

        if (optionalPlay.isSanqing() && !qipai) { //三清
            if ((toudao.getPaixing().equals(Paixing.tonghua) || toudao.getPaixing().equals(Paixing.tonghuashun))
                    && (zhongdao.getPaixing().equals(Paixing.tonghua) || zhongdao.getPaixing().equals(Paixing.tonghuashun))
                    && (weidao.getPaixing().equals(Paixing.tonghua) || weidao.getPaixing().equals(Paixing.tonghuashun))) {
                sanqing = true;
            }
        }

        if (optionalPlay.isTonghuadatou() && !qipai) {    //同花打头
            if (toudao.getPaixing().equals(Paixing.tonghua) || toudao.getPaixing().equals(Paixing.tonghuashun)) {
                tonghuadatou = true;
            }
        }

        if (optionalPlay.isShunqingdatou() && !qipai) {   //顺清打头
            if (toudao.getPaixing().equals(Paixing.tonghuashun)) {
                shunqingdatou = true;
            }
        }

        int santiaoCount = 0;
        if (toudao.getPaixing().equals(Paixing.santiao)) santiaoCount++;
        if (zhongdao.getPaixing().equals(Paixing.santiao)) santiaoCount++;
        if (weidao.getPaixing().equals(Paixing.santiao)) santiaoCount++;
        if (santiaoCount == 2 && optionalPlay.isShuangsantiao() && !qipai) {  //双三条打头
            shuangsantiao = true;
        } else if (santiaoCount == 3 && optionalPlay.isQuansantiao() && !qipai) { //全三条
            quansantiao = true;
        }
        if (santiaoCount > 0 && optionalPlay.isSizhang() && !qipai) { //四张
            int sizhangCount = 0;
            for (int pai : dianshuArray) {
                if (pai == 4) {
                    sizhangCount++;
                }
            }
            if (sizhangCount == 1) {
                sizhang = true;
            } else if ((santiaoCount == 2 && sizhangCount == 2)) {
                shuangsizhang = true;
            }
        }


        int tonghuashunCount = 0;
        if (toudao.getPaixing().equals(Paixing.tonghuashun)) tonghuashunCount++;
        if (zhongdao.getPaixing().equals(Paixing.tonghuashun)) tonghuashunCount++;
        if (weidao.getPaixing().equals(Paixing.tonghuashun)) tonghuashunCount++;
        if (tonghuashunCount == 2 && optionalPlay.isShuangshunqing() && !qipai) { //双顺清
            shuangshunqing = true;
        } else if (tonghuashunCount == 3 && optionalPlay.isQuanshunqing() && !qipai) {    //全顺清
            quanshunqing = true;
        }

        return calculateValue(renshu, optionalPlay, qipaiCount, difen);

    }

    /**
     * 计算特殊牌型分数
     */
    private int calculateValue(int renshu, OptionalPlay optionalPlay, int qipaiCount, double difen) {
        double xipaiScore;
        int playXipaiScore = 0;
        if (optionalPlay.isXipaigudingfen()) {
            xipaiScore = 5;
        } else {
            xipaiScore = renshu - 1;
        }
        xipaiScore = new BigDecimal(Double.toString(difen)).multiply(new BigDecimal(xipaiScore)).doubleValue();

        if (quanhonghei) {      //全红黑
            playXipaiScore += xipaiScore;
        }
        if (quanshun) {         //全顺
            playXipaiScore += xipaiScore;
        }
        if (tongguan) {         //通关
            playXipaiScore += xipaiScore;
        }
        if (sanqing) {          //三清
            playXipaiScore += xipaiScore;
        }
        if (tonghuadatou) {     //同花打头
            playXipaiScore += xipaiScore;
        }
        if (shunqingdatou) {    //顺清打头
            playXipaiScore += xipaiScore;
        }
        if (shuangsantiao) {    //双三条
            playXipaiScore += xipaiScore;
        }
        if (shuangshunqing) {   //双顺清
            playXipaiScore += xipaiScore;
        }
        if (sizhang) {          //四张
            playXipaiScore += xipaiScore;
        }
        if (shuangsizhang) {    //双四张
            playXipaiScore += xipaiScore;
        }
        if (quanshunqing) {     //全顺清
            playXipaiScore += xipaiScore * 3;
        }
        if (quansantiao) {      //全三条
            playXipaiScore += xipaiScore * 3;
        }
        jiesuan(playXipaiScore * (renshu - 1 - qipaiCount));
        return playXipaiScore;
    }


    /**
     * 是否有特殊牌型
     */
    public boolean hasTeshupaixing() {
        return quanhonghei || quanshun || tongguan || sanqing || tonghuadatou || shunqingdatou || shuangsantiao || sizhang || shuangshunqing || quanshunqing || quansantiao;
    }

    /**
     * 修正总分
     */
    public void jiesuan(double detal) {
        value += detal;
    }

    public int getPlayerJiesuanScoreById(String playerId) {
        return playerDaoMap.get(playerId).getValue();
    }

    public void setPlayerJiesuanScoreById(String playerId, int value) {
        playerDaoMap.get(playerId).setValue(value);
    }

    public PaixingSolution getChupaiSolution() {
        return chupaiSolution;
    }

    public void setChupaiSolution(PaixingSolution chupaiSolution) {
        this.chupaiSolution = chupaiSolution;
    }

    public List<PukePai> getPlayerShoupai() {
        return playerShoupai;
    }

    public void setPlayerShoupai(List<PukePai> playerShoupai) {
        this.playerShoupai = playerShoupai;
    }

    public BijiDaoScore getToudao() {
        return toudao;
    }

    public void setToudao(BijiDaoScore toudao) {
        this.toudao = toudao;
    }

    public BijiDaoScore getZhongdao() {
        return zhongdao;
    }

    public void setZhongdao(BijiDaoScore zhongdao) {
        this.zhongdao = zhongdao;
    }

    public BijiDaoScore getWeidao() {
        return weidao;
    }

    public void setWeidao(BijiDaoScore weidao) {
        this.weidao = weidao;
    }

    public Map<String, PlayerJiesuanScore> getPlayerDaoMap() {
        return playerDaoMap;
    }

    public void setPlayerDaoMap(Map<String, PlayerJiesuanScore> playerDaoMap) {
        this.playerDaoMap = playerDaoMap;
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

    public boolean isTonghuadatou() {
        return tonghuadatou;
    }

    public void setTonghuadatou(boolean tonghuadatou) {
        this.tonghuadatou = tonghuadatou;
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

    public boolean isShuangshunqing() {
        return shuangshunqing;
    }

    public void setShuangshunqing(boolean shuangshunqing) {
        this.shuangshunqing = shuangshunqing;
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

    public int getToudaoWinCount() {
        return toudaoWinCount;
    }

    public void setToudaoWinCount(int toudaoWinCount) {
        this.toudaoWinCount = toudaoWinCount;
    }

    public int getZhongdaoWinCount() {
        return zhongdaoWinCount;
    }

    public void setZhongdaoWinCount(int zhongdaoWinCount) {
        this.zhongdaoWinCount = zhongdaoWinCount;
    }

    public int getWeidaoWinCount() {
        return weidaoWinCount;
    }

    public void setWeidaoWinCount(int weidaoWinCount) {
        this.weidaoWinCount = weidaoWinCount;
    }

    public double getToudaoScore() {
        return toudaoScore;
    }

    public void setToudaoScore(double toudaoScore) {
        this.toudaoScore = toudaoScore;
    }

    public double getZhongdaoScore() {
        return zhongdaoScore;
    }

    public void setZhongdaoScore(double zhongdaoScore) {
        this.zhongdaoScore = zhongdaoScore;
    }

    public double getWeidaoScore() {
        return weidaoScore;
    }

    public void setWeidaoScore(double weidaoScore) {
        this.weidaoScore = weidaoScore;
    }

    public boolean isShuangsizhang() {
        return shuangsizhang;
    }

    public void setShuangsizhang(boolean shuangsizhang) {
        this.shuangsizhang = shuangsizhang;
    }
}
