package com.anbang.qipai.guandan.msg.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.guandan.cqrs.c.domain.PukeGameValueObject;
import com.anbang.qipai.guandan.msg.channel.GuandankouGameSource;
import com.anbang.qipai.guandan.msg.msjobj.CommonMO;
import com.dml.shuangkou.pan.PanValueObject;

@EnableBinding(GuandankouGameSource.class)
public class GuandanGameMsgService {

	@Autowired
	private GuandankouGameSource guandanGameSource;

	public void gamePlayerLeave(PukeGameValueObject pukeGameValueObject, String playerId) {
		boolean playerIsQuit = true;
		for (String pid : pukeGameValueObject.allPlayerIds()) {
			if (pid.equals(playerId)) {
				playerIsQuit = false;
				break;
			}
		}
		if (playerIsQuit) {
			CommonMO mo = new CommonMO();
			mo.setMsg("playerQuit");
			Map data = new HashMap();
			data.put("gameId", pukeGameValueObject.getId());
			data.put("playerId", playerId);
			mo.setData(data);
			guandanGameSource.guandanGame().send(MessageBuilder.withPayload(mo).build());
		}
	}

	public void newSessionForPlayer(String playerId, String token, String gameId) {
		CommonMO mo = new CommonMO();
		mo.setMsg("new token");
		Map data = new HashMap();
		data.put("playerId", playerId);
		data.put("token", token);
		data.put("gameId", gameId);
		mo.setData(data);
		guandanGameSource.guandanGame().send(MessageBuilder.withPayload(mo).build());
	}

	/**
	 * 游戏非正常结束
	 */
	public void gameCanceled(String gameId, String playerId) {
		CommonMO mo = new CommonMO();
		mo.setMsg("ju canceled");
		Map data = new HashMap();
		data.put("gameId", gameId);
		data.put("playerId", playerId);
		data.put("leaveTime", System.currentTimeMillis());
		mo.setData(data);
		guandanGameSource.guandanGame().send(MessageBuilder.withPayload(mo).build());
	}

	public void gameFinished(String gameId) {
		CommonMO mo = new CommonMO();
		mo.setMsg("ju finished");
		Map data = new HashMap();
		data.put("gameId", gameId);
		mo.setData(data);
		guandanGameSource.guandanGame().send(MessageBuilder.withPayload(mo).build());
	}

	public void panFinished(PukeGameValueObject pukeGameValueObject, PanValueObject panAfterAction) {
		CommonMO mo = new CommonMO();
		mo.setMsg("pan finished");
		Map data = new HashMap();
		data.put("gameId", pukeGameValueObject.getId());
		data.put("no", panAfterAction.getNo());
		data.put("playerIds", pukeGameValueObject.allPlayerIds());
		mo.setData(data);
		guandanGameSource.guandanGame().send(MessageBuilder.withPayload(mo).build());
	}

	public void delay(String gameId) {
		CommonMO mo = new CommonMO();
		mo.setMsg("game delay");
		Map data = new HashMap();
		data.put("gameId", gameId);
		mo.setData(data);
		guandanGameSource.guandanGame().send(MessageBuilder.withPayload(mo).build());
	}

	public void start(String gameId) {
		CommonMO mo = new CommonMO();
		mo.setMsg("game start");
		Map data = new HashMap();
		data.put("gameId", gameId);
		mo.setData(data);
		guandanGameSource.guandanGame().send(MessageBuilder.withPayload(mo).build());
	}
}
