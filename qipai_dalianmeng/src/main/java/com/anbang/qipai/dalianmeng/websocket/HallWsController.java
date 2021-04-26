package com.anbang.qipai.dalianmeng.websocket;


import com.anbang.qipai.dalianmeng.plan.service.MemberAuthService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class HallWsController extends TextWebSocketHandler {

	@Autowired
	private HallWsNotifier wsNotifier;

    @Autowired
    private MemberAuthService memberAuthService;

	private ExecutorService executorService = Executors.newCachedThreadPool();

	private Gson gson = new Gson();

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		executorService.submit(() -> {
			CommonMO mo = gson.fromJson(message.getPayload(), CommonMO.class);
			String msg = mo.getMsg();
			if ("heartbeat".equals(msg)) {// 心跳
				synchronized (session) {
					if (session.isOpen()) {
						processHeartbeat(session, mo.getData());
					}
				}
			} else {
			}
		});

	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		wsNotifier.addSession(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		synchronized (session) {
			wsNotifier.removeSession(session.getId());
		}
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable error) throws Exception {
		executorService.submit(() -> {
			try {
				session.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		wsNotifier.removeSession(session.getId());
		error.printStackTrace();
	}

	/**
	 * 心跳
	 *
	 * @param session
	 * @param data
	 */
	private void processHeartbeat(WebSocketSession session, Object data) {
		Map map = (Map) data;
		String token = (String) map.get("token");
		if (token == null) {// 非法访问
			try {
				session.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		String memberId = memberAuthService.getMemberIdBySessionId(token);
		if (memberId == null) {// 非法的token
			try {
				session.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
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
