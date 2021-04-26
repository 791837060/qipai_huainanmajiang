package com.anbang.qipai.admin.cqrs.c.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.cqrs.c.service.impl.CmdServiceBase;
import com.dml.users.UserSessionsManager;

@Component
public class AdminAuthService extends CmdServiceBase {

	private static int sessionKeepaliveTime = 8 * 60 * 60 * 1000;

	@Autowired
	private UserSessionsManager userSessionsManager;

	public String thirdAuth(String adminId) {
		String sessionId = UUID.randomUUID().toString();
		userSessionsManager.createEngrossSessionForUser(adminId, sessionId, System.currentTimeMillis(),
				sessionKeepaliveTime);
		return sessionId;
	}

	public String getAdminIdBySessionId(String sessionId) {
		userSessionsManager.updateUserSession(sessionId, System.currentTimeMillis(), sessionKeepaliveTime);
		return userSessionsManager.getUserIdBySessionId(sessionId);
	}

	public void removeSessionByAdminId(String adminId) {
		userSessionsManager.removeSessionsForUser(adminId);
	}
}
