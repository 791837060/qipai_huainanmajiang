package com.anbang.qipai.qinyouquan.plan.service;


import com.anbang.qipai.qinyouquan.remote.service.QipaiMembersRemoteService;
import com.anbang.qipai.qinyouquan.remote.vo.CommonRemoteVO;
import com.dml.users.UserSessionsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class MemberAuthService {

	@Resource(name = "memberSessionsManager")
	private UserSessionsManager userSessionsManager;

	@Autowired
	private QipaiMembersRemoteService qipaiMembersRomoteService;

	public String getMemberIdBySessionId(String sessionId) {
		String memberId = userSessionsManager.getUserIdBySessionId(sessionId);
		if (memberId == null) {
			CommonRemoteVO rvo = qipaiMembersRomoteService.auth_trytoken(sessionId);
			if (rvo.isSuccess()) {
				Map data = (Map) rvo.getData();
				memberId = (String) data.get("memberId");
				userSessionsManager.createEngrossSessionForUser(memberId, sessionId, System.currentTimeMillis(), 0);
			}
		}
		return memberId;
	}

	public void removeSessionByMemberId(String memberId) {
		userSessionsManager.removeSessionsForUser(memberId);
	}

}
