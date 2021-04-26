package com.dml.doudizhu.pan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dml.doudizhu.player.DoudizhuPlayerValueObject;
import com.dml.puke.pai.PaiListValueObject;
import com.dml.puke.wanfa.dianshu.paizu.DianShuZuPaiZu;
import com.dml.puke.wanfa.position.Position;

public class PanValueObject {
	private int no;
	private List<DoudizhuPlayerValueObject> doudizhuPlayerList;
	private PaiListValueObject paiListValueObject;
	private Map<Position, String> positionPlayerIdMap;
	private List<DianShuZuPaiZu> dachuPaiZuList;
	private String dizhuPlayerId;// 地主id
	private String yingjiaPlayerId;// 赢家id
	private Position actionPosition;
	private String latestDapaiPlayerId;

	public PanValueObject() {

	}

	public PanValueObject(Pan pan) {
		no = pan.getNo();
		doudizhuPlayerList = new ArrayList<>();
		pan.getDoudizhuPlayerIdPlayerMap().values()
				.forEach((doudizhuPlayer) -> doudizhuPlayerList.add(new DoudizhuPlayerValueObject(doudizhuPlayer)));
		paiListValueObject = new PaiListValueObject(pan.getAvaliablePaiList());
		positionPlayerIdMap = new HashMap<>(pan.getPositionPlayerIdMap());
		dachuPaiZuList = new ArrayList<>(pan.getDachuPaiZuList());
		actionPosition = pan.getActionPosition();
		latestDapaiPlayerId = pan.getLatestDapaiPlayerId();
		dizhuPlayerId = pan.getDizhuPlayerId();
		yingjiaPlayerId = pan.getYingjiaPlayerId();
	}

	public List<String> allPlayerIds() {
		List<String> list = new ArrayList<>();
		for (DoudizhuPlayerValueObject player : doudizhuPlayerList) {
			list.add(player.getId());
		}
		return list;
	}

	public Position playerPosition(String playerId) {
		for (DoudizhuPlayerValueObject player : doudizhuPlayerList) {
			if (player.getId().equals(playerId)) {
				return player.getPosition();
			}
		}
		return null;
	}

	public DoudizhuPlayerValueObject findPlayer(String playerId) {
		for (DoudizhuPlayerValueObject player : doudizhuPlayerList) {
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

	public List<DoudizhuPlayerValueObject> getDoudizhuPlayerList() {
		return doudizhuPlayerList;
	}

	public void setDoudizhuPlayerList(List<DoudizhuPlayerValueObject> doudizhuPlayerList) {
		this.doudizhuPlayerList = doudizhuPlayerList;
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

	public List<DianShuZuPaiZu> getDachuPaiZuList() {
		return dachuPaiZuList;
	}

	public void setDachuPaiZuList(List<DianShuZuPaiZu> dachuPaiZuList) {
		this.dachuPaiZuList = dachuPaiZuList;
	}

	public Position getActionPosition() {
		return actionPosition;
	}

	public void setActionPosition(Position actionPosition) {
		this.actionPosition = actionPosition;
	}

	public String getLatestDapaiPlayerId() {
		return latestDapaiPlayerId;
	}

	public void setLatestDapaiPlayerId(String latestDapaiPlayerId) {
		this.latestDapaiPlayerId = latestDapaiPlayerId;
	}

	public String getDizhuPlayerId() {
		return dizhuPlayerId;
	}

	public void setDizhuPlayerId(String dizhuPlayerId) {
		this.dizhuPlayerId = dizhuPlayerId;
	}

	public String getYingjiaPlayerId() {
		return yingjiaPlayerId;
	}

	public void setYingjiaPlayerId(String yingjiaPlayerId) {
		this.yingjiaPlayerId = yingjiaPlayerId;
	}

}
