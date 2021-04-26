package com.anbang.qipai.admin.plan.bean.games;
/**
 * 
 * @author yins
 * 玩家记录
 *
 */
public class PlayersRecord {
	
	private String playerId;

	private boolean isVip;
	
	private int payGold;
		
	
	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public boolean isVip() {
		return isVip;
	}

	public void setVip(boolean isVip) {
		this.isVip = isVip;
	}

	public int getPayGold() {
		return payGold;
	}

	public void setPayGold(int payGold) {
		this.payGold = payGold;
	}	
	
}
