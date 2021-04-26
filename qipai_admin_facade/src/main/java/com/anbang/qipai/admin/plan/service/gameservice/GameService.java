package com.anbang.qipai.admin.plan.service.gameservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.anbang.qipai.admin.plan.bean.games.*;
import com.anbang.qipai.admin.plan.bean.historicalresult.GameHistoricalJuResult;
import com.anbang.qipai.admin.plan.bean.historicalresult.GameHistoricalPanResult;
import com.anbang.qipai.admin.plan.bean.historicalresult.GameHistoricalPanResultExpand;
import com.anbang.qipai.admin.plan.dao.gamedao.*;
import com.anbang.qipai.admin.remote.service.QipaiGameRemoteService;
import com.anbang.qipai.admin.remote.vo.CommonRemoteVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.msg.channel.source.GameServerManagerSource;
import com.anbang.qipai.admin.msg.msjobj.CommonMO;
import com.anbang.qipai.admin.msg.receiver.GameServerMsgConstant;
import com.highto.framework.web.page.ListPage;

@Component
@EnableBinding(GameServerManagerSource.class)
public class GameService {

	public static final int GAME_SERVER_STATE_RUNNING = 0;
	public static final int GAME_SERVER_STATE_STOP = 1;

	@Autowired
	private GameLawDao gameLawDao;

	@Autowired
	private LawsMutexGroupDao lawsMutexGroupDao;

	@Autowired
	private GameServerDao gameServerDao;

	@Autowired
	private GameServerManagerSource gameServerManagerSource;

	@Autowired
	private GameRoomDao gameRoomDao;

	@Autowired
	private GameHistoricalJuResultDao gameHistoricalJuResultDao;

	@Autowired
	private GameHistoricalPanResultDao gameHistoricalPanResultDao;

	@Autowired
	private QipaiGameRemoteService qipaiGameRomoteService;

	public void onlineGameServer(GameServer gameServer) {
		gameServerDao.save(gameServer);
	}

	public void offlineGameServer(String[] ids) {
		gameServerDao.remove(ids);
	}

	public List<GameServer> findAllServersForGame(Game game) {
		return gameServerDao.findAllByGame(game);
	}

	public void createGameLaw(GameLaw law) {
		gameLawDao.save(law);
	}

	public void updateGameLaw(GameLaw law) {
		gameLawDao.update(law);
	}

	public void removeGameLaw(String lawId) {
		gameLawDao.remove(lawId);
	}

	public ListPage findGameLawByConditions(int page, int size, GameLaw law) {
		long amount = gameLawDao.getAmountByConditions(law);
		List<GameLaw> list = gameLawDao.findGameLawByConditions(page, size, law);
		ListPage listPage = new ListPage(list, page, size, (int) amount);
		return listPage;
	}

	public void addLawsMutexGroup(LawsMutexGroup lawsMutexGroup) {
		lawsMutexGroupDao.save(lawsMutexGroup);
	}

	public void removeLawsMutexGroup(String groupId) {
		lawsMutexGroupDao.remove(groupId);
	}

	public ListPage findLawsMutexGroupByConditions(int page, int size, LawsMutexGroup lawsMutexGroup) {
		long amount = lawsMutexGroupDao.getAmountByConditions(lawsMutexGroup);
		List<LawsMutexGroup> list = lawsMutexGroupDao.findLawsMutexGroupByConditions(page, size, lawsMutexGroup);
		ListPage listPage = new ListPage(list, page, size, (int) amount);
		return listPage;
	}

	public void stopGameServersAndSendMsg(List<String> ids) {
		if (ids != null && ids.size() > 0) {
			CommonMO commonMO = new CommonMO();
			commonMO.setMsg(GameServerMsgConstant.STOP_GAME_SERVERS);
			commonMO.setData(ids);
			//通知game系统
			this.gameServerManagerSource.gameServerManager().send(MessageBuilder.withPayload(commonMO).build());
            //修改admin的gameServer的state字段
			this.stopGameServers(ids);
		}
	}

	public void recoverGameServersAndSendMsg(List<String> ids) {
		if (ids != null && ids.size() > 0) {
			CommonMO commonMO = new CommonMO();
			commonMO.setMsg(GameServerMsgConstant.RECOVER_GAME_SERVERS);
			commonMO.setData(ids);
			this.gameServerManagerSource.gameServerManager().send(MessageBuilder.withPayload(commonMO).build());
			this.startGameServers(ids);
		}
	}

	public void startGameServers(List<String> ids) {
		if (ids != null && ids.size() > 0) {
			this.gameServerDao.updateGameServerState(ids, GameService.GAME_SERVER_STATE_RUNNING);
		}
	}

	public void stopGameServers(List<String> ids) {
		if (ids != null && ids.size() > 0) {
			this.gameServerDao.updateGameServerState(ids, GameService.GAME_SERVER_STATE_STOP);
		}
	}

	/**
	 * ----------游戏房间mq消费及房间查询
	 */

	public void gameRoomFinished(Game game, String serverGameId) {
		gameRoomDao.updateFinishGameRoom(game, serverGameId, true);
	}

	public void panFinished(Game game, String serverGameId, int no, List<String> playerIds) {
		gameRoomDao.updateGameRoomCurrentPanNum(game, serverGameId, no);
	}

	public GameRoom findRoomByGameAndServerGameGameId(Game game, String serverGameId) {
		return gameRoomDao.findRoomByGameAndServerGameGameId(game, serverGameId);
	}

	public void saveGameRoom(GameRoom gameRoom) {
		this.gameRoomDao.save(gameRoom);
	}

	public ListPage queryRoomList (int page, int size, String playerId, String roomNo) {
		int count = gameRoomDao.countRoomByPlayerIdAndRoomNo(playerId, roomNo);
		List<GameRoom> gameRooms = gameRoomDao.findRoomByPlayerIdAndRoomNo(page, size, playerId, roomNo);
		List<Map> maps = new ArrayList<>();
		for (GameRoom gameRoom : gameRooms) {
			Map map = new HashMap();
			map.put("gameId", gameRoom.getServerGame().getGameId());
			map.put("roomNo", gameRoom.getNo());
			map.put("roomType", gameRoom.getGame());
			String countPan = gameRoom.getCurrentPanNum() + "/" + gameRoom.getPanCountPerJu();
			map.put("countPan", countPan);
			map.put("playersCount", gameRoom.getPlayersCount());
			if (CollectionUtils.isNotEmpty(gameRoom.getPlayersRecord())) {
				String playerIds = gameRoom.getPlayersRecord().stream().map(PlayersRecord::getPlayerId).collect(Collectors.joining(","));
				map.put("playerIds", playerIds);
			}
			map.put("createTime", String.valueOf(gameRoom.getCreateTime()));
			maps.add(map);
		}
		ListPage listPage = new ListPage(maps, page, size, count);
		return listPage;
	}

	public Map queryRoomDetail (String gameId) {
		GameHistoricalJuResult juResult = gameHistoricalJuResultDao.findJuResultBygameId(gameId);
		List<GameHistoricalPanResult> panResults = gameHistoricalPanResultDao.findPanResultByGameId(gameId);
		List<GameHistoricalPanResultExpand> result = new ArrayList<>();
		for (GameHistoricalPanResult list : panResults) {
			GameHistoricalPanResultExpand expand = new GameHistoricalPanResultExpand();
			BeanUtils.copyProperties(list, expand);
			// 获取返回码
			CommonRemoteVO commonRemoteVO = qipaiGameRomoteService.queryBackcode(list.getGame(), list.getGameId(), list.getNo());
			if (commonRemoteVO.getData() == null) {
				expand.setCodeStatus(0);
			} else {
				expand.setCodeStatus(1);
				expand.setCode(String.valueOf(commonRemoteVO.getData()));
			}
			result.add(expand);
		}

		Map map = new HashMap();
		if (juResult != null) {
			map.put("totalScore", juResult.getPlayerResultList());
		}
		map.put("list", result);
		return map;
	}



}
