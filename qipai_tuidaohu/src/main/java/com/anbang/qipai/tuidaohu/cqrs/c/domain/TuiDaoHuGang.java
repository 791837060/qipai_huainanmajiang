package com.anbang.qipai.tuidaohu.cqrs.c.domain;

import com.dml.majiang.pai.MajiangPai;
import com.dml.majiang.pai.fenzu.GangType;
import com.dml.majiang.player.MajiangPlayer;

public class TuiDaoHuGang {
    private int zimoMingGangShu;        //自摸杠
    private int fangGangmingGangShu;
    private int anGangShu;              //暗杠
    private int value;

    public TuiDaoHuGang() {

    }

    public TuiDaoHuGang(MajiangPlayer player) {
        MajiangPai[] majiangPais = MajiangPai.values();
        zimoMingGangShu = 0;
        fangGangmingGangShu = 0;
        anGangShu = 0;
        for (int i = 0; i < majiangPais.length; i++) {
            if (player.ifGangchu(majiangPais[i], GangType.gangdachu)) {
                fangGangmingGangShu++;
            }
            if (player.ifGangchu(majiangPais[i], GangType.kezigangmo)||player.ifGangchu(majiangPais[i],GangType.kezigangshoupai)) {
                zimoMingGangShu++;
            }
            if (player.ifGangchu(majiangPais[i], GangType.shoupaigangmo) || player.ifGangchu(majiangPais[i], GangType.gangsigeshoupai)) {
                anGangShu++;
            }
        }
    }

    /**
     * 暗杠一个每人两分 明杠只扣被杠人3分
     *
     * @param playerCount   玩家数量
     * @param fangGangCount 放杠数量
     */
    public void calculate(int playerCount, int fangGangCount) {
        value = ((anGangShu * 2+zimoMingGangShu) * (playerCount - 1)) + ((fangGangmingGangShu - fangGangCount) * 3);
    }

    public int jiesuan(int delta) {
        return value += delta;
    }

    public int getZimoMingGangShu() {
        return zimoMingGangShu;
    }

    public void setZimoMingGangShu(int zimoMingGangShu) {
        this.zimoMingGangShu = zimoMingGangShu;
    }

    public int getFangGangmingGangShu() {
        return fangGangmingGangShu;
    }

    public void setFangGangmingGangShu(int fangGangmingGangShu) {
        this.fangGangmingGangShu = fangGangmingGangShu;
    }

    public int getAnGangShu() {
        return anGangShu;
    }

    public void setAnGangShu(int anGangShu) {
        this.anGangShu = anGangShu;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
