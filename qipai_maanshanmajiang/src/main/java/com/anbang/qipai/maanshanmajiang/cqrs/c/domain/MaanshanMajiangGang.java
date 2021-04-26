package com.anbang.qipai.maanshanmajiang.cqrs.c.domain;

import com.dml.majiang.pai.MajiangPai;
import com.dml.majiang.pai.fenzu.GangType;
import com.dml.majiang.player.MajiangPlayer;

public class MaanshanMajiangGang {
    private int zimoMingGangShu;        //明杠(刻子与刚摸的手牌或手牌)
    private int fangGangmingGangShu;    //明杠(手牌中3张加别人打出的一张)
    private int anGangShu;              //暗杠
    private int value;

    public MaanshanMajiangGang() {
    }

    public MaanshanMajiangGang(MajiangPlayer player) {
        MajiangPai[] yijiupaiArray = new MajiangPai[]{MajiangPai.yiwan, MajiangPai.jiuwan, MajiangPai.yitong, MajiangPai.jiutong, MajiangPai.yitiao, MajiangPai.jiutiao};
        MajiangPai[] erbapaiArray = new MajiangPai[]{
                MajiangPai.erwan, MajiangPai.sanwan, MajiangPai.siwan, MajiangPai.wuwan, MajiangPai.liuwan, MajiangPai.qiwan, MajiangPai.bawan,
                MajiangPai.ertong, MajiangPai.santong, MajiangPai.sitong, MajiangPai.wutong, MajiangPai.liutong, MajiangPai.qitong, MajiangPai.batong,
                MajiangPai.ertiao, MajiangPai.santiao, MajiangPai.sitiao, MajiangPai.wutiao, MajiangPai.liutiao, MajiangPai.qitiao, MajiangPai.batiao};
        MajiangPai[] zipaipaiArray = new MajiangPai[]{MajiangPai.dongfeng, MajiangPai.nanfeng, MajiangPai.xifeng, MajiangPai.beifeng, MajiangPai.hongzhong, MajiangPai.facai, MajiangPai.baiban};
        zimoMingGangShu = 0;
        fangGangmingGangShu = 0;
        anGangShu = 0;
        for (int i = 0; i < yijiupaiArray.length; i++) {
            if (player.ifGangchu(yijiupaiArray[i], GangType.gangdachu) || player.ifGangchu(yijiupaiArray[i], GangType.sanbanziminggang)) {
                fangGangmingGangShu++;
            }
            if (player.ifGangchu(yijiupaiArray[i], GangType.kezigangmo)) {
                zimoMingGangShu++;
            }
            if (player.ifGangchu(yijiupaiArray[i], GangType.shoupaigangmo)
                    || player.ifGangchu(yijiupaiArray[i], GangType.sanbanziangangmo)
                    || player.ifGangchu(yijiupaiArray[i], GangType.sanbanziangangshoupai)
                    || player.ifGangchu(yijiupaiArray[i], GangType.gangsigeshoupai)) {
                anGangShu++;
            }
        }
        for (int i = 0; i < erbapaiArray.length; i++) {
            if (player.ifGangchu(erbapaiArray[i], GangType.gangdachu) || player.ifGangchu(erbapaiArray[i], GangType.sanbanziminggang)) {
                fangGangmingGangShu++;
            }
            if (player.ifGangchu(erbapaiArray[i], GangType.kezigangmo)) {
                zimoMingGangShu++;
            }
            if (player.ifGangchu(erbapaiArray[i], GangType.shoupaigangmo)
                    || player.ifGangchu(erbapaiArray[i], GangType.sanbanziangangmo)
                    || player.ifGangchu(erbapaiArray[i], GangType.sanbanziangangshoupai)
                    || player.ifGangchu(erbapaiArray[i], GangType.gangsigeshoupai)) {
                anGangShu++;
            }
        }
        for (int i = 0; i < zipaipaiArray.length; i++) {
            if (player.ifGangchu(zipaipaiArray[i], GangType.gangdachu) || player.ifGangchu(zipaipaiArray[i], GangType.sanbanziminggang)) {
                fangGangmingGangShu++;
            }
            if (player.ifGangchu(zipaipaiArray[i], GangType.kezigangmo)) {
                zimoMingGangShu++;
            }
            if (player.ifGangchu(zipaipaiArray[i], GangType.shoupaigangmo)
                    || player.ifGangchu(zipaipaiArray[i], GangType.sanbanziangangmo)
                    || player.ifGangchu(zipaipaiArray[i], GangType.sanbanziangangshoupai)
                    || player.ifGangchu(zipaipaiArray[i], GangType.gangsigeshoupai)) {
                anGangShu++;
            }
        }
    }

    public void calculate(int playerCount) {
        //暗杠每人4分
        //明杠每人2分
        value = ((anGangShu * 4) * (playerCount - 1)) + (((zimoMingGangShu + fangGangmingGangShu) * 2) * (playerCount - 1));
    }

    public void calculate(int anGangCount, int minggangCount) {
        value -= ((anGangCount * 4) + (minggangCount * 2));
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
