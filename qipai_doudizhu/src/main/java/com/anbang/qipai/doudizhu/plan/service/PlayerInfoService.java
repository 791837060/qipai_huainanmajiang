package com.anbang.qipai.doudizhu.plan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anbang.qipai.doudizhu.plan.bean.PlayerInfo;
import com.anbang.qipai.doudizhu.plan.dao.PlayerInfoDao;

@Component
public class PlayerInfoService {

	@Autowired
	private PlayerInfoDao playerInfoDao;

	public PlayerInfo findPlayerInfoById(String playerId) {
		return playerInfoDao.findById(playerId);
	}

	public void save(PlayerInfo playerInfo) {
		playerInfoDao.save(playerInfo);
	}
}
