package com.anbang.qipai.doudizhu.plan.dao;

import com.anbang.qipai.doudizhu.plan.bean.PlayerInfo;

public interface PlayerInfoDao {

	PlayerInfo findById(String playerId);

	void save(PlayerInfo playerInfo);
}
