package com.dml.doudizhu.player.action.da;

import com.dml.doudizhu.player.action.DoudizhuPlayerAction;
import com.dml.doudizhu.player.action.DoudizhuPlayerActionType;
import com.dml.puke.wanfa.dianshu.paizu.DianShuZuPaiZu;

/**
 * 打
 * 
 * @author lsc
 *
 */
public class DaAction extends DoudizhuPlayerAction {

	private DianShuZuPaiZu dachuPaiZu;

	public DaAction() {
	}

	public DaAction(String actionPlayerId) {
		super(DoudizhuPlayerActionType.da, actionPlayerId);
	}

	public DianShuZuPaiZu getDachuPaiZu() {
		return dachuPaiZu;
	}

	public void setDachuPaiZu(DianShuZuPaiZu dachuPaiZu) {
		this.dachuPaiZu = dachuPaiZu;
	}

}
