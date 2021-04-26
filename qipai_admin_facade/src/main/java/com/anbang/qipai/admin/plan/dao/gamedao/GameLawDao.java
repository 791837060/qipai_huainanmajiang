package com.anbang.qipai.admin.plan.dao.gamedao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.games.GameLaw;

public interface GameLawDao {

	void save(GameLaw law);

	void update(GameLaw law);

	void remove(String id);

	List<GameLaw> findGameLawByConditions(int page, int size, GameLaw law);

	long getAmountByConditions(GameLaw law);

}
