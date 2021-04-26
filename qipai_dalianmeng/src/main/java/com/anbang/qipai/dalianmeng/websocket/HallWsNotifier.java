package com.anbang.qipai.dalianmeng.websocket;

import com.google.gson.Gson;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class HallWsNotifier {

	private Map<String, WebSocketSession> idSessionMap = new ConcurrentHashMap<>();

	private Map<String, Long> sessionIdActivetimeMap = new ConcurrentHashMap<>();

	private Map<String, String> sessionIdMemberIdMap = new ConcurrentHashMap<>();

	private Map<String, String> memberIdSessionIdMap = new ConcurrentHashMap<>();

	private ExecutorService executorService = Executors.newCachedThreadPool();

	private Gson gson = new Gson();


	public WebSocketSession removeSession(String id) {
		WebSocketSession removedSession = idSessionMap.remove(id);
		sessionIdActivetimeMap.remove(id);
		if (removedSession != null) {
			String removedMemberId = sessionIdMemberIdMap.remove(id);
			if (removedMemberId != null) {
				memberIdSessionIdMap.remove(removedMemberId);
				// 更新用户现在状态
			}
		}
		return removedSession;
	}

	/**
	 * 是否是刚连上还没发过心跳消息的
	 * 
	 * @param id
	 * @return
	 */
	public boolean isRawSession(String id) {
		return !sessionIdMemberIdMap.containsKey(id);
	}

	public void addSession(WebSocketSession session) {
		idSessionMap.put(session.getId(), session);
		sessionIdActivetimeMap.put(session.getId(), System.currentTimeMillis());
	}

	public void updateSession(String sessionId, String memberId) {
		String sessionAlreadyExistsId = memberIdSessionIdMap.get(memberId);
		if (sessionAlreadyExistsId != null) {
			WebSocketSession removedSession = removeSession(sessionAlreadyExistsId);
			if (removedSession != null) {
				try {
					removedSession.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		sessionIdActivetimeMap.put(sessionId, System.currentTimeMillis());
		sessionIdMemberIdMap.put(sessionId, memberId);
		memberIdSessionIdMap.put(memberId, sessionId);
	}

	public void updateSession(String id) {
		sessionIdActivetimeMap.put(id, System.currentTimeMillis());
	}

	@Scheduled(cron = "0/10 * * * * ?")
	public void closeAndRemoveOTSessions() {
		sessionIdActivetimeMap.forEach((id, time) -> {
			if ((System.currentTimeMillis() - time) > (30 * 1000)) {
				WebSocketSession removedSession = removeSession(id);
				if (removedSession != null) {
					try {
						removedSession.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}



	private void sendMessage(WebSocketSession session, String message) {
		synchronized (session) {
			try {
				session.sendMessage(new TextMessage(message));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
