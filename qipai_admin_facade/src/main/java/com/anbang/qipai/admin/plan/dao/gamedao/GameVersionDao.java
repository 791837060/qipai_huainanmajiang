package com.anbang.qipai.admin.plan.dao.gamedao;

import com.anbang.qipai.admin.plan.bean.games.GameVersion;

import java.util.List;

public interface GameVersionDao {
    void save(GameVersion gameVersion);

    void remove(String id);

    long countByBean(GameVersion gameVersion);

    List<GameVersion> findByBean(int page, int size, GameVersion gameVersion);

    GameVersion findLastRecord(String gameType);
}
