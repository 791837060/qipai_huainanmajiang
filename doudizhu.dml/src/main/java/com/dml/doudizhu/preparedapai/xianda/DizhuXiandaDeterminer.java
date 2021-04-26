package com.dml.doudizhu.preparedapai.xianda;

import com.dml.doudizhu.ju.Ju;
import com.dml.doudizhu.pan.Pan;

/**
 * 地主先出牌
 * 
 * @author lsc
 *
 */
public class DizhuXiandaDeterminer implements XiandaDeterminer {

	@Override
	public String determineToXiandaplayer(Ju ju) throws Exception {
		Pan currentPan = ju.getCurrentPan();
		return currentPan.getDizhuPlayerId();
	}

}
