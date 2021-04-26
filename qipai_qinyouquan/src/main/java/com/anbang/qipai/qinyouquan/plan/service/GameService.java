package com.anbang.qipai.qinyouquan.plan.service;

import com.anbang.qipai.qinyouquan.plan.bean.game.Game;
import com.anbang.qipai.qinyouquan.plan.bean.game.GameLaw;
import com.anbang.qipai.qinyouquan.plan.bean.game.GameServer;
import com.anbang.qipai.qinyouquan.plan.bean.game.LawsMutexGroup;
import com.anbang.qipai.qinyouquan.plan.dao.GameLawDao;
import com.anbang.qipai.qinyouquan.plan.dao.GameServerDao;
import com.anbang.qipai.qinyouquan.plan.dao.LawsMutexGroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GameService {

    public static final int GAME_SERVER_STATE_RUNNING = 0;
    public static final int GAME_SERVER_STATE_STOP = 1;

    @Autowired
    private GameLawDao gameLawDao;

    @Autowired
    private LawsMutexGroupDao lawsMutexGroupDao;

    @Autowired
    private GameServerDao gameServerDao;

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

    public void addLawsMutexGroup(LawsMutexGroup lawsMutexGroup) {
        lawsMutexGroupDao.save(lawsMutexGroup);
    }

    public void removeLawsMutexGroup(String groupId) {
        lawsMutexGroupDao.remove(groupId);
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
}
