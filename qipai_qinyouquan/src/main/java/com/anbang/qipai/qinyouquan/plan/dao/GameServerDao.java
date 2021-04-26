package com.anbang.qipai.qinyouquan.plan.dao;


import com.anbang.qipai.qinyouquan.plan.bean.game.Game;
import com.anbang.qipai.qinyouquan.plan.bean.game.GameServer;

import java.util.List;

public interface GameServerDao {

	void save(GameServer gameServer);

	void remove(String[] ids);

	List<GameServer> findAllByGame(Game game);

	List<GameServer> findGameServersByIds(List<String> ids);

	void updateGameServerState(List<String> ids, int state);

	List<GameServer> findServersByState(Game game, int state);

}
