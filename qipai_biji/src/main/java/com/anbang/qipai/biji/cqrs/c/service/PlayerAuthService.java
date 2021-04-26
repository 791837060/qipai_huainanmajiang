package com.anbang.qipai.biji.cqrs.c.service;

import com.dml.users.UserSessionsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PlayerAuthService {

	@Autowired
	private UserSessionsManager userSessionsManager;

	public String getPlayerIdByToken(String token) {
		return userSessionsManager.getUserIdBySessionId(token);
	}

	public String newSessionForPlayer(String playerId) {
		String token = UUID.randomUUID().toString();
		userSessionsManager.createEngrossSessionForUser(playerId, token, System.currentTimeMillis(), 0);// 没有超时管理
		return token;
	}

}
