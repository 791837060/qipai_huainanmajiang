package com.dml.paodekuai.pan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dml.puke.pai.PaiListValueObject;
import com.dml.puke.wanfa.dianshu.paizu.DianShuZuPaiZu;
import com.dml.puke.wanfa.position.Position;
import com.dml.paodekuai.player.PaodekuaiPlayerValueObject;

public class PanValueObject {
	private int no;
	private List<PaodekuaiPlayerValueObject> paodekuaiPlayerList;
	private PaiListValueObject paiListValueObject;
	private Map<Position, String> positionPlayerIdMap;
	private List<DianShuZuPaiZu> dachuPaiZuList;
	private Position actionPosition;
	private String latestDapaiPlayerId;

	public PanValueObject() {
	}

	public PanValueObject(Pan pan) {
		no = pan.getNo();
		paodekuaiPlayerList = new ArrayList<>();
		pan.getPaodekuaiPlayerIdMajiangPlayerMap().values()
				.forEach((paodekuaiPlayer) -> paodekuaiPlayerList.add(new PaodekuaiPlayerValueObject(paodekuaiPlayer)));
		paiListValueObject = new PaiListValueObject(pan.getAvaliablePaiList());
		positionPlayerIdMap = new HashMap<>(pan.getPositionPlayerIdMap());
		dachuPaiZuList = new ArrayList<>(pan.getDachuPaiZuList());
		actionPosition = pan.getActionPosition();
		latestDapaiPlayerId = pan.getLatestDapaiPlayerId();
	}

	public List<String> allPlayerIds() {
		List<String> list = new ArrayList<>();
		for (PaodekuaiPlayerValueObject player : paodekuaiPlayerList) {
			list.add(player.getId());
		}
		return list;
	}

	public Position playerPosition(String playerId) {
		for (PaodekuaiPlayerValueObject player : paodekuaiPlayerList) {
			if (player.getId().equals(playerId)) {
				return player.getPosition();
			}
		}
		return null;
	}

	public PaodekuaiPlayerValueObject findPlayer(String playerId) {
		for (PaodekuaiPlayerValueObject player : paodekuaiPlayerList) {
			if (player.getId().equals(playerId)) {
				return player;
			}
		}
		return null;
	}

	public Map<Position, String> getPositionPlayerIdMap() {
		return positionPlayerIdMap;
	}

	public void setPositionPlayerIdMap(Map<Position, String> positionPlayerIdMap) {
		this.positionPlayerIdMap = positionPlayerIdMap;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public PaiListValueObject getPaiListValueObject() {
		return paiListValueObject;
	}

	public void setPaiListValueObject(PaiListValueObject paiListValueObject) {
		this.paiListValueObject = paiListValueObject;
	}

	public List<DianShuZuPaiZu> getDachuPaiZuList() {
		return dachuPaiZuList;
	}

	public void setDachuPaiZuList(List<DianShuZuPaiZu> dachuPaiZuList) {
		this.dachuPaiZuList = dachuPaiZuList;
	}

	public List<PaodekuaiPlayerValueObject> getPaodekuaiPlayerList() {
		return paodekuaiPlayerList;
	}

	public void setPaodekuaiPlayerList(List<PaodekuaiPlayerValueObject> paodekuaiPlayerList) {
		this.paodekuaiPlayerList = paodekuaiPlayerList;
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

}
