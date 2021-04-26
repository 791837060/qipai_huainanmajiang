package com.dml.shuangkou.player.action.da;

import com.dml.puke.wanfa.dianshu.paizu.DianShuZuPaiZu;
import com.dml.shuangkou.player.action.ShuangkouPlayerAction;
import com.dml.shuangkou.player.action.ShuangkouPlayerActionType;

/**
 * 打
 * 
 * @author Neo
 *
 */
public class DaAction extends ShuangkouPlayerAction {

	private DianShuZuPaiZu dachuPaiZu;

	public DaAction() {
	}

	public DaAction(String actionPlayerId) {
		super(ShuangkouPlayerActionType.da, actionPlayerId);
	}

	public DianShuZuPaiZu getDachuPaiZu() {
		return dachuPaiZu;
	}

	public void setDachuPaiZu(DianShuZuPaiZu dachuPaiZu) {
		this.dachuPaiZu = dachuPaiZu;
	}

}
