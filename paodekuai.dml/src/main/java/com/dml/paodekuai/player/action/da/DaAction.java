package com.dml.paodekuai.player.action.da;

import com.dml.puke.wanfa.dianshu.paizu.DianShuZuPaiZu;
import com.dml.paodekuai.player.action.PaodekuaiPlayerAction;
import com.dml.paodekuai.player.action.PaodekuaiPlayerActionType;

/**
 * 打
 * 
 * @author Neo
 *
 */
public class DaAction extends PaodekuaiPlayerAction {

	private DianShuZuPaiZu dachuPaiZu;

	public DaAction() {
	}

	public DaAction(String actionPlayerId) {
		super(PaodekuaiPlayerActionType.da, actionPlayerId);
	}

	public DianShuZuPaiZu getDachuPaiZu() {
		return dachuPaiZu;
	}

	public void setDachuPaiZu(DianShuZuPaiZu dachuPaiZu) {
		this.dachuPaiZu = dachuPaiZu;
	}

}
