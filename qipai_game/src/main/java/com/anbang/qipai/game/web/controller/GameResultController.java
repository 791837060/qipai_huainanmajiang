package com.anbang.qipai.game.web.controller;

import com.anbang.qipai.game.plan.bean.historicalresult.GameHistoricalJuResult;
import com.anbang.qipai.game.plan.service.GameHistoricalJuResultService;
import com.anbang.qipai.game.web.vo.CommonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 游戏结果 - rpc生产者
 */
@RestController
@RequestMapping("/gameResult")
public class GameResultController {
    @Autowired
    private GameHistoricalJuResultService majiangHistoricalResultService;

    /**
     * 根据游戏id获取对局战绩
     */
    @RequestMapping(value = "/getJuResult")
    public CommonVO getJuResult(String gameId) {
        CommonVO vo = new CommonVO();
        GameHistoricalJuResult majiangHistoricalResult = majiangHistoricalResultService
                .getJuResultByGameId(gameId);
        vo.setSuccess(true);
        vo.setMsg("historical result detail");
        vo.setData(majiangHistoricalResult);
        return vo;
    }
}
