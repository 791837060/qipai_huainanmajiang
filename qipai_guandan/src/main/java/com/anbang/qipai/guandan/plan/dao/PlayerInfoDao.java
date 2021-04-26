package com.anbang.qipai.guandan.plan.dao;

import com.anbang.qipai.guandan.plan.bean.PlayerInfo;

public interface PlayerInfoDao {

	PlayerInfo findById(String playerId);

	void save(PlayerInfo playerInfo);
}
