package com.dml.paodekuai.player.action.da.solution;

import java.util.Map;

import com.dml.paodekuai.pan.Pan;
import com.dml.paodekuai.player.PaodekuaiPlayer;
import com.dml.puke.wanfa.dianshu.dianshuzu.DianShuZu;

/**
 * 通过正常的点数组(非炸弹)去压牌的方案
 *
 * @author Neo
 */
public interface DianShuZuYaPaiSolutionCalculator {
    /**
     * @param baodan 报单
     */
    Map<String, DaPaiDianShuSolution> calculate(DianShuZu beiYaDianShuZu, int[] dianShuAmountArray, boolean baodan);

    Map<String, DaPaiDianShuSolution> calculate(DianShuZu beiYaDianShuZu, int[] dianShuAmountArray, boolean baodan, Pan pan, PaodekuaiPlayer yapaiPlayer);
}
