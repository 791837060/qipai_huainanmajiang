package com.anbang.qipai.qinyouquan.plan.dao;

import com.anbang.qipai.qinyouquan.plan.bean.game.Game;
import com.anbang.qipai.qinyouquan.plan.bean.LianmengWanfa;

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
