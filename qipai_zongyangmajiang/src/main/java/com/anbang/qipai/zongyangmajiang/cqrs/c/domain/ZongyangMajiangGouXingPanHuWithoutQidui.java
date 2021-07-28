package com.anbang.qipai.zongyangmajiang.cqrs.c.domain;

import com.dml.majiang.player.shoupai.gouxing.GouXingPanHu;

public class ZongyangMajiangGouXingPanHuWithoutQidui extends GouXingPanHu {
    /**
     * 胡牌构型
     *
     * @param chichuShunziCount  吃出顺子
     * @param pengchuKeziCount   碰出刻子
     * @param gangchuGangziCount 杠出杠子
     * @param shoupaiDanpaiCount 手牌当牌
     * @param shoupaiDuiziCount  手牌对子
     * @param shoupaiKeziCount   手牌刻子
     * @param shoupaiGangziCount 手牌杠子
     * @param shoupaiShunziCount 手牌顺子
     * @return
     */
    @Override
    protected boolean panHu(int chichuShunziCount, int pengchuKeziCount, int gangchuGangziCount, int shoupaiDanpaiCount, int shoupaiDuiziCount, int shoupaiKeziCount, int shoupaiGangziCount, int shoupaiShunziCount) {
        boolean isHu;
        if ((shoupaiDanpaiCount == 0 && shoupaiDuiziCount == 1)) { //正常胡型
            isHu = true;
//        } else if (shoupaiDuiziCount == 7) { //七小对
//            isHu = true;
//        } else if (shoupaiDuiziCount == 5 && shoupaiGangziCount == 1) { //豪华七小对
//            isHu = true;
        } else {
            isHu = false;
        }
        return isHu;
    }

}
