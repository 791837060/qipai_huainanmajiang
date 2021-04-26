package com.anbang.qipai.doudizhu.web.vo;

import java.util.ArrayList;
import java.util.List;

import com.anbang.qipai.doudizhu.cqrs.q.dbo.GameInfoDbo;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.GameLatestInfoDbo;
import com.dml.doudizhu.pan.PanValueObject;
import com.dml.puke.pai.PaiListValueObject;
import com.dml.puke.pai.PukePai;
import com.dml.puke.wanfa.dianshu.paizu.DianShuZuPaiZu;
import com.dml.puke.wanfa.position.Position;

public class PanValueObjectVO {
	private int no;
	private List<DoudizhuPlayerValueObjectVO> doudizhuPlayerList;
	private PaiListValueObject paiListValueObject;
	private List<DianShuZuPaiZu> dachuPaiZuList;
	private String dizhuPlayerId;// 地主id
	private Position actionPosition;
	private String latestDapaiPlayerId;
	private DoudizhuBeishuVO beishu;
	private List<PukePai> dipaiList;
	private int rangPai;

	public PanValueObjectVO() {
	}

	public PanValueObjectVO(PanValueObject panValueObject) {
		no = panValueObject.getNo();
		doudizhuPlayerList = new ArrayList<>();
		panValueObject.getDoudizhuPlayerList()
				.forEach((doudizhuPlayer) -> doudizhuPlayerList.add(new DoudizhuPlayerValueObjectVO(doudizhuPlayer)));
		paiListValueObject = panValueObject.getPaiListValueObject();
		dachuPaiZuList = panValueObject.getDachuPaiZuList();
		actionPosition = panValueObject.getActionPosition();
		latestDapaiPlayerId = panValueObject.getLatestDapaiPlayerId();
		dizhuPlayerId = panValueObject.getDizhuPlayerId();
	}

	public PanValueObjectVO(PanValueObject panValueObject, GameLatestInfoDbo gameLatestInfoDbo) {
		no = panValueObject.getNo();
		dizhuPlayerId = panValueObject.getDizhuPlayerId();
		doudizhuPlayerList = new ArrayList<>();
		panValueObject.getDoudizhuPlayerList().forEach((doudizhuPlayer) -> doudizhuPlayerList
				.add(new DoudizhuPlayerValueObjectVO(doudizhuPlayer, gameLatestInfoDbo.getPlayerQiangdizhuInfos())));
		if (doudizhuPlayerList.size() == 2 && gameLatestInfoDbo.getPanNo() == no) {
			rangPai = gameLatestInfoDbo.getQiangdizhuCount() - 1;
				rangPai = 0;

		}
		if (dizhuPlayerId != null) {
			doudizhuPlayerList.forEach((doudizhuPlayer) -> {
				if (doudizhuPlayer.getId().equals(dizhuPlayerId)) {
					if (doudizhuPlayer.getAllShoupai().getTotalShoupai() <= 4) {
						doudizhuPlayer.setNoPaiWarning(true);
					}
				} else {
					doudizhuPlayer.setRangPai(rangPai);
					if (doudizhuPlayer.getAllShoupai().getTotalShoupai() <= 4 + doudizhuPlayer.getRangPai()) {
						doudizhuPlayer.setNoPaiWarning(true);
					}
				}
			});
		}
		paiListValueObject = panValueObject.getPaiListValueObject();
		dachuPaiZuList = panValueObject.getDachuPaiZuList();
		actionPosition = panValueObject.getActionPosition();
		latestDapaiPlayerId = panValueObject.getLatestDapaiPlayerId();
		beishu = new DoudizhuBeishuVO(gameLatestInfoDbo.getBeishu());
		dipaiList = gameLatestInfoDbo.getDipaiList();
	}

	public PanValueObjectVO(PanValueObject panValueObject, GameInfoDbo gameInfoDbo) {
		no = panValueObject.getNo();
		doudizhuPlayerList = new ArrayList<>();
		panValueObject.getDoudizhuPlayerList().forEach((doudizhuPlayer) -> doudizhuPlayerList
				.add(new DoudizhuPlayerValueObjectVO(doudizhuPlayer, gameInfoDbo.getPlayerQiangdizhuInfos())));
		paiListValueObject = panValueObject.getPaiListValueObject();
		dachuPaiZuList = panValueObject.getDachuPaiZuList();
		actionPosition = panValueObject.getActionPosition();
		latestDapaiPlayerId = panValueObject.getLatestDapaiPlayerId();
		dizhuPlayerId = panValueObject.getDizhuPlayerId();
		beishu = new DoudizhuBeishuVO(gameInfoDbo.getBeishu());
		dipaiList = gameInfoDbo.getDipaiList();
	}

	public String getDizhuPlayerId() {
		return dizhuPlayerId;
	}

	public void setDizhuPlayerId(String dizhuPlayerId) {
		this.dizhuPlayerId = dizhuPlayerId;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public List<DoudizhuPlayerValueObjectVO> getDoudizhuPlayerList() {
		return doudizhuPlayerList;
	}

	public void setDoudizhuPlayerList(List<DoudizhuPlayerValueObjectVO> doudizhuPlayerList) {
		this.doudizhuPlayerList = doudizhuPlayerList;
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

	public DoudizhuBeishuVO getBeishu() {
		return beishu;
	}

	public void setBeishu(DoudizhuBeishuVO beishu) {
		this.beishu = beishu;
	}

	public List<PukePai> getDipaiList() {
		return dipaiList;
	}

	public void setDipaiList(List<PukePai> dipaiList) {
		this.dipaiList = dipaiList;
	}

	public int getRangPai() {
		return rangPai;
	}

	public void setRangPai(int rangPai) {
		this.rangPai = rangPai;
	}

}
