package com.anbang.qipai.doudizhu.msg.receiver;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.anbang.qipai.doudizhu.cqrs.c.domain.PukeGameValueObject;
import com.anbang.qipai.doudizhu.cqrs.c.service.GameCmdService;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.JuResultDbo;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.PukeGameDbo;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.PukeGamePlayerDbo;
import com.anbang.qipai.doudizhu.cqrs.q.service.PukeGameQueryService;
import com.anbang.qipai.doudizhu.cqrs.q.service.PukePlayQueryService;
import com.anbang.qipai.doudizhu.msg.channel.GameRoomSink;
import com.anbang.qipai.doudizhu.msg.msjobj.CommonMO;
import com.anbang.qipai.doudizhu.msg.msjobj.PukeHistoricalJuResult;
import com.anbang.qipai.doudizhu.msg.service.DoudizhuGameMsgService;
import com.anbang.qipai.doudizhu.msg.service.DoudizhuResultMsgService;
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
	private DoudizhuResultMsgService doudizhuResultMsgService;

	@Autowired
	private DoudizhuGameMsgService doudizhuGameMsgService;

	private Gson gson = new Gson();

	@StreamListener(GameRoomSink.DOUDIZHUGAMEROOM)
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
						doudizhuGameMsgService.gameFinished(gameId);
						continue;
					}
					boolean playerOnline = false;
					for (PukeGamePlayerDbo player : pukeGameDbo.getPlayers()) {
						if (GamePlayerOnlineState.online.equals(player.getOnlineState())) {
							playerOnline = true;
						}
					}
					if (playerOnline) {
						doudizhuGameMsgService.delay(gameId);
					} else {
						doudizhuGameMsgService.gameFinished(gameId);
						PukeGameValueObject gameValueObject = gameCmdService.finishGameImmediately(gameId);
						pukeGameQueryService.finishGameImmediately(gameValueObject);
						JuResultDbo juResultDbo = pukePlayQueryService.findJuResultDbo(gameId);
						if (juResultDbo != null) {
							PukeHistoricalJuResult juResult = new PukeHistoricalJuResult(juResultDbo, pukeGameDbo);
							doudizhuResultMsgService.recordJuResult(juResult);
						}
					}
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
	}

}
