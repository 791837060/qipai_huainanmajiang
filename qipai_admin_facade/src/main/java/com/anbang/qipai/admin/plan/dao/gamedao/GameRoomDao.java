package com.anbang.qipai.admin.plan.dao.gamedao;

import com.anbang.qipai.admin.plan.bean.games.Game;
import com.anbang.qipai.admin.plan.bean.games.GameRoom;

import java.util.List;

public interface GameRoomDao {

	void save(GameRoom gameRoom);

	int count(long startTimeForCreate, long endTimeForCreate, String createMemberId, boolean vip);

	GameRoom findRoomOpen(String roomNo);

	GameRoom findRoomByGameAndServerGameGameId(Game game, String serverGameId);

	List<GameRoom> findExpireGameRoom(long deadlineTime, boolean finished);

	void updateGameRoomFinished(List<String> ids, boolean finished);

	void updateFinishGameRoom(Game game, String serverGameId, boolean finished);

	void updateGameRoomCurrentPanNum(Game game, String serverGameId, int no);

	public int countRoomByPlayerIdAndRoomNo(String playerId, String roomNo);

	public List<GameRoom> findRoomByPlayerIdAndRoomNo(int page, int size, String playerId, String roomNo);
}
