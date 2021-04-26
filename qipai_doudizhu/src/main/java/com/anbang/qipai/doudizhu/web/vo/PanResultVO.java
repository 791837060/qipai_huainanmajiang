package com.anbang.qipai.doudizhu.web.vo;

import java.util.ArrayList;
import java.util.List;

import com.anbang.qipai.doudizhu.cqrs.q.dbo.DoudizhuPanPlayerResultDbo;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.GameLatestInfoDbo;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.PanResultDbo;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.PukeGameDbo;

public class PanResultVO {

	private List<DoudizhuPanPlayerResultVO> playerResultList;

	private boolean dizhuying;

	private String yingjiaPlayerId;// 赢家id

	private int panNo;

	private long finishTime;

	private PanActionFrameVO lastPanActionFrame;

	private GameInfoVO gameInfoVO;

	public PanResultVO() {

	}
    public PanResultVO(PanResultDbo panResultDbo, PukeGameDbo pukeGameDbo) {
        List<DoudizhuPanPlayerResultDbo> list = panResultDbo.getPlayerResultList();
        playerResultList = new ArrayList<>();
        dizhuying = panResultDbo.isDizhuying();
        yingjiaPlayerId = panResultDbo.getYingjiaPlayerId();
        if (list != null) {
            list.forEach((panPlayerResult) -> {
                playerResultList.add(new DoudizhuPanPlayerResultVO(
                        pukeGameDbo.findPlayer(panPlayerResult.getPlayerId()), panPlayerResult, yingjiaPlayerId));
            });
        }
        panNo = panResultDbo.getPanNo();
        finishTime = panResultDbo.getFinishTime();
        lastPanActionFrame = new PanActionFrameVO(panResultDbo.getPanActionFrame());
        gameInfoVO = new GameInfoVO(panResultDbo.getGameLatestInfoDbo());
    }

	public PanResultVO(PanResultDbo panResultDbo, PukeGameDbo pukeGameDbo, GameLatestInfoDbo gameLatestInfoDbo) {
		List<DoudizhuPanPlayerResultDbo> list = panResultDbo.getPlayerResultList();
		playerResultList = new ArrayList<>();
		dizhuying = panResultDbo.isDizhuying();
		yingjiaPlayerId = panResultDbo.getYingjiaPlayerId();
		if (list != null) {
			list.forEach((panPlayerResult) -> {
				playerResultList.add(new DoudizhuPanPlayerResultVO(
						pukeGameDbo.findPlayer(panPlayerResult.getPlayerId()), panPlayerResult, yingjiaPlayerId));
			});
		}
		panNo = panResultDbo.getPanNo();
		finishTime = panResultDbo.getFinishTime();
		lastPanActionFrame = new PanActionFrameVO(panResultDbo.getPanActionFrame(),gameLatestInfoDbo);
		gameInfoVO = new GameInfoVO(panResultDbo.getGameLatestInfoDbo());
	}

	public List<DoudizhuPanPlayerResultVO> getPlayerResultList() {
		return playerResultList;
	}

	public void setPlayerResultList(List<DoudizhuPanPlayerResultVO> playerResultList) {
		this.playerResultList = playerResultList;
	}

	public boolean isDizhuying() {
		return dizhuying;
	}

	public void setDizhuying(boolean dizhuying) {
		this.dizhuying = dizhuying;
	}

	public int getPanNo() {
		return panNo;
	}

	public void setPanNo(int panNo) {
		this.panNo = panNo;
	}

	public long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(long finishTime) {
		this.finishTime = finishTime;
	}

	public PanActionFrameVO getLastPanActionFrame() {
		return lastPanActionFrame;
	}

	public void setLastPanActionFrame(PanActionFrameVO lastPanActionFrame) {
		this.lastPanActionFrame = lastPanActionFrame;
	}

	public GameInfoVO getGameInfoVO() {
		return gameInfoVO;
	}

	public void setGameInfoVO(GameInfoVO gameInfoVO) {
		this.gameInfoVO = gameInfoVO;
	}

	public String getYingjiaPlayerId() {
		return yingjiaPlayerId;
	}

	public void setYingjiaPlayerId(String yingjiaPlayerId) {
		this.yingjiaPlayerId = yingjiaPlayerId;
	}

}
