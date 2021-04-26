package com.anbang.qipai.dalianmeng.plan.dao;

import com.anbang.qipai.dalianmeng.plan.bean.PayForContribution;
import com.anbang.qipai.dalianmeng.plan.bean.game.Game;
import com.anbang.qipai.dalianmeng.plan.bean.LianmengWanfa;

import java.util.List;

public interface LianmengWanfaDao {
    void save(LianmengWanfa wanfa);

    List<LianmengWanfa> findByLianmengId(String lianmengId);

    LianmengWanfa findByLianmengIdAndGame(String lianmengId, Game game);

    LianmengWanfa findByWanfaId(String wanfaId);

    void deleteLianmengWanfa(String lianmengId);

    void updateLianmengWanfa(LianmengWanfa lianmengWanfa);

    List<LianmengWanfa> findAllWafabyLianmengId(int page, int size, String lianmengId);

}
