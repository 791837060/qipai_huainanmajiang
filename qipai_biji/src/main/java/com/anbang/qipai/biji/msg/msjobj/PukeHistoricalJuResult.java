package com.anbang.qipai.biji.msg.msjobj;

import java.util.ArrayList;
import java.util.List;

import com.anbang.qipai.biji.cqrs.c.domain.result.BijiJuResult;
import com.anbang.qipai.biji.cqrs.q.dbo.JuResultDbo;
import com.anbang.qipai.biji.cqrs.q.dbo.PukeGameDbo;
import com.anbang.qipai.biji.web.vo.PanResultVO;

public class PukeHistoricalJuResult {
	private String gameId;
	private String dayingjiaId;
	private String datuhaoId;
	private int panshu;
	private int lastPanNo;
	private List<BijiJuPlayerResultMO> playerResultList;
	private PanResultVO lastPanResult;
	private long finishTime;

	public PukeHistoricalJuResult() {

	}

	public PukeHistoricalJuResult(JuResultDbo juResultDbo, PukeGameDbo pukeGameDbo) {
		gameId = juResultDbo.getGameId();
		BijiJuResult juResult = juResultDbo.getJuResult();
		dayingjiaId = juResult.getDayingjiaId();
		datuhaoId = juResult.getDatuhaoId();
		if (juResultDbo.getLastPanResult() != null) {
			lastPanResult = new PanResultVO(juResultDbo.getLastPanResult(), pukeGameDbo);
		}
		finishTime = juResultDbo.getFinishTime();
		this.panshu = pukeGameDbo.getPanshu();
		lastPanNo = juResult.getFinishedPanCount();
		playerResultList = new ArrayList<>();
		if (juResult.getPlayerResultList() != null) {
			juResult.getPlayerResultList().forEach((juPlayerResult) -> playerResultList.add(new BijiJuPlayerResultMO(juPlayerResult, pukeGameDbo.findPlayer(juPlayerResult.getPlayerId()))));
		} else {
			pukeGameDbo.getPlayers().forEach((pukeGamePlayerDbo) -> playerResultList.add(new BijiJuPlayerResultMO(pukeGamePlayerDbo)));
		}

		if (pukeGameDbo.getOptionalPlay().isJinyuanzi()) {   //进园子玩法下最后得分要减去游戏开始时给的园子分
			for (BijiJuPlayerResultMO bijiJuPlayerResultMO : playerResultList) {
				double totalScore = bijiJuPlayerResultMO.getTotalScore();
				totalScore -= pukeGameDbo.getOptionalPlay().getYuanzifen();
				bijiJuPlayerResultMO.setTotalScore(totalScore);
			}
		}

	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getDayingjiaId() {
		return dayingjiaId;
	}

	public void setDayingjiaId(String dayingjiaId) {
		this.dayingjiaId = dayingjiaId;
	}

	public String getDatuhaoId() {
		return datuhaoId;
	}

	public void setDatuhaoId(String datuhaoId) {
		this.datuhaoId = datuhaoId;
	}

	public int getPanshu() {
		return panshu;
	}

	public void setPanshu(int panshu) {
		this.panshu = panshu;
	}

	public int getLastPanNo() {
		return lastPanNo;
	}

	public void setLastPanNo(int lastPanNo) {
		this.lastPanNo = lastPanNo;
	}

	public List<BijiJuPlayerResultMO> getPlayerResultList() {
		return playerResultList;
	}

	public void setPlayerResultList(List<BijiJuPlayerResultMO> playerResultList) {
		this.playerResultList = playerResultList;
	}

	public long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(long finishTime) {
		this.finishTime = finishTime;
	}

	public PanResultVO getLastPanResult() {
		return lastPanResult;
	}

	public void setLastPanResult(PanResultVO lastPanResult) {
		this.lastPanResult = lastPanResult;
	}

}
