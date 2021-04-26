package com.anbang.qipai.maanshanmajiang.cqrs.c.domain;

import com.dml.majiang.player.shoupai.gouxing.GouXingPanHu;

/**
 * 构型没有单牌且只有一个对子就成胡。这种是最普遍的。
 */
public class MaanshanMajiangGouXingPanHu extends GouXingPanHu {


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
     */
    @Override
    protected boolean panHu(int chichuShunziCount, int pengchuKeziCount, int gangchuGangziCount, int shoupaiDanpaiCount, int shoupaiDuiziCount, int shoupaiKeziCount, int shoupaiGangziCount, int shoupaiShunziCount) {
        //正常胡型
        return shoupaiDanpaiCount == 0 && shoupaiDuiziCount == 1;
    }

}
