package com.anbang.qipai.admin.plan.service.gameservice;

import com.anbang.qipai.admin.plan.bean.games.GameVersion;
import com.anbang.qipai.admin.plan.dao.gamedao.GameVersionDao;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameVersionService {

    @Autowired
    private GameVersionDao gameVersionDao;

    public void save(GameVersion gameVersion) {
        gameVersion.setCreateTime(System.currentTimeMillis());
        gameVersionDao.save(gameVersion);
    }

    public void remove(String id) {
        gameVersionDao.remove(id);
    }

    public ListPage findByBean(int page, int size, GameVersion gameVersion){
        long amount = gameVersionDao.countByBean(gameVersion);
        List<GameVersion> gameVersions = gameVersionDao.findByBean(page, size, gameVersion);
        ListPage listPage = new ListPage(gameVersions, page, size, (int)amount);
        return listPage;
    }

    public GameVersion findLastRecord(String gameType) {
        return gameVersionDao.findLastRecord(gameType);
    }
}
