package com.dml.shisanshui.pan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dml.shisanshui.pai.PaiListValueObject;
import com.dml.shisanshui.player.ShisanshuiPlayerValueObject;
import com.dml.shisanshui.position.Position;

public class PanValueObject {
	private int no;
	private List<ShisanshuiPlayerValueObject> playerList;
	private PaiListValueObject paiListValueObject;
	private Map<Position, String> positionPlayerIdMap;

	public PanValueObject() {

	}

	public PanValueObject(Pan pan) {
		no = pan.getNo();
		playerList = new ArrayList<>();
		pan.getPlayerIdPlayerMap().values()
				.forEach((player) -> playerList.add(new ShisanshuiPlayerValueObject(player)));
		paiListValueObject = new PaiListValueObject(pan.getAvaliablePaiList());
		positionPlayerIdMap = new HashMap<>(pan.getPositionPlayerIdMap());
	}

	public List<String> allPlayerIds() {
		List<String> list = new ArrayList<>();
		for (ShisanshuiPlayerValueObject player : playerList) {
			list.add(player.getId());
		}
		return list;
	}

	public ShisanshuiPlayerValueObject findPlayer(String playerId) {
		for (ShisanshuiPlayerValueObject player : playerList) {
			if (player.getId().equals(playerId)) {
				return player;
			}
		}
		return null;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public List<ShisanshuiPlayerValueObject> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(List<ShisanshuiPlayerValueObject> playerList) {
		this.playerList = playerList;
	}

	public PaiListValueObject getPaiListValueObject() {
		return paiListValueObject;
	}

	public void setPaiListValueObject(PaiListValueObject paiListValueObject) {
		this.paiListValueObject = paiListValueObject;
	}

	public Map<Position, String> getPositionPlayerIdMap() {
		return positionPlayerIdMap;
	}

	public void setPositionPlayerIdMap(Map<Position, String> positionPlayerIdMap) {
		this.positionPlayerIdMap = positionPlayerIdMap;
	}

}
