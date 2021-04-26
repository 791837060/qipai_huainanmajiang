package com.anbang.qipai.dalianmeng.msg.receiver;


import com.anbang.qipai.dalianmeng.msg.msjobs.CommonMO;
import com.anbang.qipai.dalianmeng.msg.sink.GameServerSink;
import com.anbang.qipai.dalianmeng.plan.bean.game.GameLaw;
import com.anbang.qipai.dalianmeng.plan.bean.game.GameServer;
import com.anbang.qipai.dalianmeng.plan.bean.game.LawsMutexGroup;
import com.anbang.qipai.dalianmeng.plan.service.GameService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.List;

@EnableBinding(GameServerSink.class)
public class GameServerMsgReceiver {

	@Autowired
	private GameService gameService;

	private Gson gson = new Gson();

	@StreamListener(GameServerSink.GAMESERVER)
	public void gameServer(CommonMO mo) {
		String msg = mo.getMsg();
		String json = gson.toJson(mo.getData());
		if ("online".equals(msg)) {
			GameServer gameServer = gson.fromJson(json, GameServer.class);
			gameService.onlineGameServer(gameServer);
		}
		if ("offline".equals(msg)) {
			String[] gameServerIds = gson.fromJson(json, String[].class);
			gameService.offlineGameServer(gameServerIds);
		}
		if ("create gamelaw".equals(msg)) {
			GameLaw law = gson.fromJson(json, GameLaw.class);
			gameService.createGameLaw(law);
		}
		if ("update gamelaw".equals(msg)) {
			GameLaw law = gson.fromJson(json, GameLaw.class);
			gameService.updateGameLaw(law);
		}
		if ("remove gamelaw".equals(msg)) {
			String lawId = gson.fromJson(json, String.class);
			gameService.removeGameLaw(lawId);
		}
		if ("create lawsmutexgroup".equals(msg)) {
			LawsMutexGroup lawsMutexGroup = gson.fromJson(json, LawsMutexGroup.class);
			gameService.addLawsMutexGroup(lawsMutexGroup);
		}
		if ("remove lawsmutexgroup".equals(msg)) {
			String groupId = gson.fromJson(json, String.class);
			gameService.removeLawsMutexGroup(groupId);
		}
		if ("StopGameServersFailed".equals(msg)) {
			List<String> ids = (List<String>) mo.getData();
			this.gameService.startGameServers(ids);
		}

		if ("RecoverGameServersFailed".equals(msg)) {
			List<String> ids = (List<String>) mo.getData();
			this.gameService.stopGameServers(ids);
		}
	}

}
