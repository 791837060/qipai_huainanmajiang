package com.anbang.qipai.biji.plan.dao;

import com.anbang.qipai.biji.plan.bean.PlayerInfo;

public interface PlayerInfoDao {

	PlayerInfo findById(String playerId);

	void save(PlayerInfo playerInfo);
}
