package com.dml.paodekuai.player.action.da;

import java.util.Map;

import com.dml.puke.pai.PukePai;
import com.dml.paodekuai.player.action.da.solution.DaPaiDianShuSolution;

/**
 * 生成所有可打的牌的方案
 *
 * @author Neo
 */
public interface AllKedaPaiSolutionsGenerator {
    /**
     * 一般情况的所有可打牌
     *
     * @param baodan 本盘是否已报单
     */
    Map<String, DaPaiDianShuSolution> generateAllKedaPaiSolutions(Map<Integer, PukePai> allShoupai, boolean baodan);

    /**
     * 一般情况的所有可打牌
     *
     * @param allShoupai     手牌
     * @param baodan         报单
     * @param dachuHeitaoSan 打出过黑桃三
     * @param dachuHonxinSan 打出过红心三
     * @return
     */
    Map<String, DaPaiDianShuSolution> generateAllKedaPaiSolutions(Map<Integer, PukePai> allShoupai, boolean baodan, boolean dachuHeitaoSan, boolean dachuHonxinSan, boolean notExistentSan);

    /**
     * 首次出牌时
     */
    Map<String, DaPaiDianShuSolution> firstAllKedaPaiSolutions(Map<Integer, PukePai> allShoupai, boolean notExistentSan);
}
