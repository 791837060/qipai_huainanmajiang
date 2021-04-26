package com.anbang.qipai.dalianmeng.cqrs.q.dao;

import com.anbang.qipai.dalianmeng.cqrs.q.dbo.PlayBackDbo;
import com.anbang.qipai.dalianmeng.plan.bean.game.Game;

public interface PlayBackDboDao {

	void save(PlayBackDbo dbo);

	PlayBackDbo findById(String id);

	PlayBackDbo findByGameAndGameIdAndPanNo(Game game, String gameId, int panNo);
}
