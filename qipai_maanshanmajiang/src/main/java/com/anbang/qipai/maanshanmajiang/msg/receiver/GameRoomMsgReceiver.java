package com.anbang.qipai.maanshanmajiang.msg.receiver;

import java.util.ArrayList;
import java.util.List;

import com.anbang.qipai.maanshanmajiang.cqrs.c.domain.MajiangGameValueObject;
import com.anbang.qipai.maanshanmajiang.cqrs.c.service.GameCmdService;
import com.anbang.qipai.maanshanmajiang.cqrs.q.dbo.JuResultDbo;
import com.anbang.qipai.maanshanmajiang.cqrs.q.dbo.MajiangGameDbo;
import com.anbang.qipai.maanshanmajiang.cqrs.q.dbo.MajiangGamePlayerDbo;
import com.anbang.qipai.maanshanmajiang.cqrs.q.service.MajiangGameQueryService;
import com.anbang.qipai.maanshanmajiang.cqrs.q.service.MajiangPlayQueryService;
import com.anbang.qipai.maanshanmajiang.msg.channel.GameRoomSink;
import com.anbang.qipai.maanshanmajiang.msg.msjobj.CommonMO;
import com.anbang.qipai.maanshanmajiang.msg.msjobj.MajiangHistoricalJuResult;
import com.anbang.qipai.maanshanmajiang.msg.service.MaanshanMajiangGameMsgService;
import com.anbang.qipai.maanshanmajiang.msg.service.MaanshanMajiangResultMsgService;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.dml.mpgame.game.player.GamePlayerOnlineState;
import com.google.gson.Gson;

@EnableBinding(GameRoomSink.class)
public class GameRoomMsgReceiver {

	@Autowired
	private GameCmdService gameCmdService;

	@Autowired
	private MajiangGameQueryService majiangGameQueryService;

	@Autowired
	private MajiangPlayQueryService majiangPlayQueryService;

	@Autowired
	private MaanshanMajiangResultMsgService maanshanMajiangResultMsgService;

	@Autowired
	private MaanshanMajiangGameMsgService maanshanMajiangGameMsgService;

	private Gson gson = new Gson();

	@StreamListener(GameRoomSink.MAANSHANMAJIANGROOM)
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
					MajiangGameDbo majiangGameDbo = majiangGameQueryService.findMajiangGameDboById(gameId);
					if (majiangGameDbo == null) {
						maanshanMajiangGameMsgService.gameFinished(gameId);
						continue;
					}
					boolean playerOnline = false;
					for (MajiangGamePlayerDbo player : majiangGameDbo.getPlayers()) {
						if (GamePlayerOnlineState.online.equals(player.getOnlineState())) {
							playerOnline = true;
						}
					}
					if (playerOnline) {
						maanshanMajiangGameMsgService.delay(gameId);
					} else {
						maanshanMajiangGameMsgService.gameFinished(gameId);
						MajiangGameValueObject gameValueObject = gameCmdService.finishGameImmediately(gameId);
						majiangGameQueryService.finishGameImmediately(gameValueObject);
						JuResultDbo juResultDbo = majiangPlayQueryService.findJuResultDbo(gameId);
						if (juResultDbo != null) {
							MajiangHistoricalJuResult juResult = new MajiangHistoricalJuResult(juResultDbo,
									majiangGameDbo);
							maanshanMajiangResultMsgService.recordJuResult(juResult);
						}
					}
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
	}
}
