package com.anbang.qipai.doudizhu.web.vo;

import java.util.List;

import com.anbang.qipai.doudizhu.cqrs.q.dbo.DoudizhuPanPlayerResultDbo;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.PukeGamePlayerDbo;
import com.dml.doudizhu.player.DoudizhuPlayerValueObject;

public class DoudizhuPanPlayerResultVO {
	private String playerId;
	private String nickname;
	private String headimgurl;
	private DoudizhuPlayerShoupaiVO allShoupai;
	private boolean ying;
	private boolean dizhu;
	private double difen;
	private DoudizhuBeishuVO beishu;
	private int zhadanCount;
	private double score;// 一盘结算分
	private double totalScore;// 总分

	public DoudizhuPanPlayerResultVO() {

	}

	public DoudizhuPanPlayerResultVO(PukeGamePlayerDbo playerDbo, DoudizhuPanPlayerResultDbo panPlayerResult,
			String yingjiaPlayerId) {
		playerId = playerDbo.getPlayerId();
		nickname = playerDbo.getNickname();
		headimgurl = playerDbo.getHeadimgurl();
		DoudizhuPlayerValueObject playerValueObject = panPlayerResult.getPlayer();
		List<List<Integer>> shoupaiIdListForSortList = playerValueObject.getShoupaiIdListForSortList();
		if (shoupaiIdListForSortList == null || shoupaiIdListForSortList.isEmpty()) {
			allShoupai = new DoudizhuPlayerShoupaiVO(playerValueObject.getAllShoupai(),
					playerValueObject.getTotalShoupai(), null);
		} else {
			allShoupai = new DoudizhuPlayerShoupaiVO(playerValueObject.getAllShoupai(),
					playerValueObject.getTotalShoupai(), shoupaiIdListForSortList.get(0));
		}
		ying = panPlayerResult.getPlayerResult().isYing();
		dizhu = panPlayerResult.getPlayerResult().isDizhu();
		difen = panPlayerResult.getPlayerResult().getDifen();
		beishu = new DoudizhuBeishuVO(panPlayerResult.getPlayerResult().getBeishu());
		if (!playerId.equals(yingjiaPlayerId)) {
			beishu.setChuntian(false);
			beishu.setFanchuntian(false);
		}
		zhadanCount = panPlayerResult.getPlayerResult().getZhadanCount();
		score = panPlayerResult.getPlayerResult().getScore();
		totalScore = panPlayerResult.getPlayerResult().getTotalScore();
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public DoudizhuPlayerShoupaiVO getAllShoupai() {
		return allShoupai;
	}

	public void setAllShoupai(DoudizhuPlayerShoupaiVO allShoupai) {
		this.allShoupai = allShoupai;
	}

    public double getDifen() {
        return difen;
    }

    public void setDifen(double difen) {
        this.difen = difen;
    }

    public DoudizhuBeishuVO getBeishu() {
		return beishu;
	}

	public void setBeishu(DoudizhuBeishuVO beishu) {
		this.beishu = beishu;
	}

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public boolean isDizhu() {
		return dizhu;
	}

	public void setDizhu(boolean dizhu) {
		this.dizhu = dizhu;
	}

	public int getZhadanCount() {
		return zhadanCount;
	}

	public void setZhadanCount(int zhadanCount) {
		this.zhadanCount = zhadanCount;
	}

	public boolean isYing() {
		return ying;
	}

	public void setYing(boolean ying) {
		this.ying = ying;
	}

}
