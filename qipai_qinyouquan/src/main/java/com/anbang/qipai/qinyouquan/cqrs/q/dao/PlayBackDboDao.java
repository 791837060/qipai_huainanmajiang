package com.anbang.qipai.qinyouquan.cqrs.q.dao;

import com.anbang.qipai.qinyouquan.cqrs.q.dbo.PlayBackDbo;
import com.anbang.qipai.qinyouquan.plan.bean.game.Game;

public interface PlayBackDboDao {

	void save(PlayBackDbo dbo);

	PlayBackDbo findById(String id);

	PlayBackDbo findByGameAndGameIdAndPanNo(Game game, String gameId, int panNo);
}
