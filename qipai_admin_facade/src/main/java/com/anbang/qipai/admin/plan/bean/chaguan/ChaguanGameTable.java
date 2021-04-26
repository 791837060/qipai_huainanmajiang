package com.anbang.qipai.admin.plan.bean.chaguan;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.games.Game;
import com.anbang.qipai.admin.plan.bean.games.GameLaw;
import com.anbang.qipai.admin.plan.bean.games.ServerGame;

public class ChaguanGameTable {
	private String id;
	private String no;// 房间6位编号,可循环使用
	private String chaguanId;// 茶馆id
	private Game game;
	private List<GameLaw> laws;
	private int playersCount;
	private int currentPanNo;
	private int panCountPerJu;
	private ServerGame serverGame;
	private List<String> playerList;
	private long createTime;
	private String state;// 状态

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getChaguanId() {
		return chaguanId;
	}

	public void setChaguanId(String chaguanId) {
		this.chaguanId = chaguanId;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public List<GameLaw> getLaws() {
		return laws;
	}

	public void setLaws(List<GameLaw> laws) {
		this.laws = laws;
	}

	public int getPlayersCount() {
		return playersCount;
	}

	public void setPlayersCount(int playersCount) {
		this.playersCount = playersCount;
	}

	public int getCurrentPanNo() {
		return currentPanNo;
	}

	public void setCurrentPanNo(int currentPanNo) {
		this.currentPanNo = currentPanNo;
	}

	public int getPanCountPerJu() {
		return panCountPerJu;
	}

	public void setPanCountPerJu(int panCountPerJu) {
		this.panCountPerJu = panCountPerJu;
	}

	public ServerGame getServerGame() {
		return serverGame;
	}

	public void setServerGame(ServerGame serverGame) {
		this.serverGame = serverGame;
	}

	public List<String> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(List<String> playerList) {
		this.playerList = playerList;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
