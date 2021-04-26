package com.anbang.qipai.guandan.msg.receiver;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.anbang.qipai.guandan.cqrs.c.domain.PukeGameValueObject;
import com.anbang.qipai.guandan.cqrs.c.service.GameCmdService;
import com.anbang.qipai.guandan.cqrs.q.dbo.JuResultDbo;
import com.anbang.qipai.guandan.cqrs.q.dbo.PukeGameDbo;
import com.anbang.qipai.guandan.cqrs.q.dbo.PukeGamePlayerDbo;
import com.anbang.qipai.guandan.cqrs.q.service.PukeGameQueryService;
import com.anbang.qipai.guandan.cqrs.q.service.PukePlayQueryService;
import com.anbang.qipai.guandan.msg.channel.GameRoomSink;
import com.anbang.qipai.guandan.msg.msjobj.CommonMO;
import com.anbang.qipai.guandan.msg.msjobj.PukeHistoricalJuResult;
import com.anbang.qipai.guandan.msg.service.GuandanGameMsgService;
import com.anbang.qipai.guandan.msg.service.GuandanResultMsgService;
import com.dml.mpgame.game.player.GamePlayerOnlineState;
import com.google.gson.Gson;

@EnableBinding(GameRoomSink.class)
public class GameRoomMsgReceiver {

	@Autowired
	private GameCmdService gameCmdService;

	@Autowired
	private PukeGameQueryService pukeGameQueryService;

	@Autowired
	private PukePlayQueryService pukePlayQueryService;

	@Autowired
	private GuandanResultMsgService guandanResultMsgService;

	@Autowired
	private GuandanGameMsgService guandanGameMsgService;

	private Gson gson = new Gson();

	@StreamListener(GameRoomSink.GUANDANGAMEROOM)
	public void removeGameRoom(CommonMO mo) {
		String msg = mo.getMsg();
		String json = gson.toJson(mo.getData());
		if ("gameIds".equals(msg)) {
			List<String> gameIds = gson.fromJson(json, ArrayList.class);
			for (String gameId : gameIds) {
				try {
					if (StringUtil.isBlank(gameId)) {
						continue;
					}
					PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);
					if (pukeGameDbo == null) {
						guandanGameMsgService.gameFinished(gameId);
						continue;
					}
					boolean playerOnline = false;
					for (PukeGamePlayerDbo player : pukeGameDbo.getPlayers()) {
						if (GamePlayerOnlineState.online.equals(player.getOnlineState())) {
							playerOnline = true;
						}
					}
					if (playerOnline) {
						guandanGameMsgService.delay(gameId);
					} else {
						guandanGameMsgService.gameFinished(gameId);
						PukeGameValueObject gameValueObject = gameCmdService.finishGameImmediately(gameId);
						pukeGameQueryService.finishGameImmediately(gameValueObject,null);
						JuResultDbo juResultDbo = pukePlayQueryService.findJuResultDbo(gameId);
						if (juResultDbo != null) {
							PukeHistoricalJuResult juResult = new PukeHistoricalJuResult(juResultDbo, pukeGameDbo);
							guandanResultMsgService.recordJuResult(juResult);
						}
					}
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
	}

}
