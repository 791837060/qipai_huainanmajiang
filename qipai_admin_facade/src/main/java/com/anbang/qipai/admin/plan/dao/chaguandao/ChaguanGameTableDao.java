package com.anbang.qipai.admin.plan.dao.chaguandao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.chaguan.ChaguanGameTable;
import com.anbang.qipai.admin.plan.bean.games.Game;

public interface ChaguanGameTableDao {

	void save(ChaguanGameTable gameTable);

	ChaguanGameTable findTableByGameAndServerGameGameId(Game game, String serverGameId);

	void updateGameTable(ChaguanGameTable gameTable);

	public int countTableByPlayerIdAndTableNo(String playerId, String tableNo);

	public List<ChaguanGameTable> findTableByPlayerIdAndTableNo(int page, int size, String playerId, String tableNo);
}
