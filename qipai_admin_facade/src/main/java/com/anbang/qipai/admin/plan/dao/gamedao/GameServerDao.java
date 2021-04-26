package com.anbang.qipai.admin.plan.dao.gamedao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.games.Game;
import com.anbang.qipai.admin.plan.bean.games.GameServer;

public interface GameServerDao {

	void save(GameServer gameServer);

	void remove(String[] ids);

	List<GameServer> findAllByGame(Game game);

    List<GameServer> findGameServersByIds(List<String> ids);

    void updateGameServerState(List<String>ids,int state);

}
