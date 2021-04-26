package com.anbang.qipai.qinyouquan.web.vo;

import com.anbang.qipai.qinyouquan.cqrs.q.dbo.AllianceDbo;
import com.anbang.qipai.qinyouquan.plan.bean.result.GameHistoricalJuResult;

public class JuResultVO {
    private AllianceDbo alliance;
    private GameHistoricalJuResult juResult;

    public AllianceDbo getAlliance() {
        return alliance;
    }

    public void setAlliance(AllianceDbo alliance) {
        this.alliance = alliance;
    }

    public GameHistoricalJuResult getJuResult() {
        return juResult;
    }

    public void setJuResult(GameHistoricalJuResult juResult) {
        this.juResult = juResult;
    }
}
