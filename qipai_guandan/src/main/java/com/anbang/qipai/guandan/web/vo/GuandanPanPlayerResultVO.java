package com.anbang.qipai.guandan.web.vo;

import java.util.List;

import com.anbang.qipai.guandan.cqrs.q.dbo.PukeGamePlayerDbo;
import com.anbang.qipai.guandan.cqrs.q.dbo.GuandanPanPlayerResultDbo;
import com.dml.shuangkou.player.ShuangkouPlayerValueObject;

public class GuandanPanPlayerResultVO {
	private String playerId;
	private String nickname;
	private String headimgurl;
	private boolean chaodi;
	private ShuangkouPlayerShoupaiVO allShoupai;
	private GuandanMingcifenVO mingcifen;
//	private int xianshubeishu;
//	private int gongxianfen;
//	private int bufen;
	private double score;// 一盘结算分
	private double totalScore;// 总分

	public GuandanPanPlayerResultVO() {

	}

	public GuandanPanPlayerResultVO(PukeGamePlayerDbo playerDbo, GuandanPanPlayerResultDbo panPlayerResult) {
		playerId = playerDbo.getPlayerId();
		nickname = playerDbo.getNickname();
		headimgurl = playerDbo.getHeadimgurl();
		chaodi = panPlayerResult.getPlayerResult().isChaodi();
		ShuangkouPlayerValueObject shuangkouPlayerValueObject = panPlayerResult.getPlayer();
		List<List<Integer>> shoupaiIdListForSortList = shuangkouPlayerValueObject.getShoupaiIdListForSortList();
		if (shoupaiIdListForSortList == null || shoupaiIdListForSortList.isEmpty()) {
			allShoupai = new ShuangkouPlayerShoupaiVO(shuangkouPlayerValueObject.getAllShoupai(), shuangkouPlayerValueObject.getTotalShoupai(), null);
		} else {
			allShoupai = new ShuangkouPlayerShoupaiVO(shuangkouPlayerValueObject.getAllShoupai(), shuangkouPlayerValueObject.getTotalShoupai(), shoupaiIdListForSortList.get(0));
		}
		mingcifen = new GuandanMingcifenVO(panPlayerResult.getPlayerResult().getMingcifen());
//		xianshubeishu = panPlayerResult.getPlayerResult().getXianshubeishu();
//		gongxianfen = panPlayerResult.getPlayerResult().getGongxianfen().getTotalscore();
//		bufen = panPlayerResult.getPlayerResult().getBufen().getValue();
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

    public boolean isChaodi() {
        return chaodi;
    }

    public void setChaodi(boolean chaodi) {
        this.chaodi = chaodi;
    }

    public ShuangkouPlayerShoupaiVO getAllShoupai() {
        return allShoupai;
    }

    public void setAllShoupai(ShuangkouPlayerShoupaiVO allShoupai) {
        this.allShoupai = allShoupai;
    }

    public GuandanMingcifenVO getMingcifen() {
        return mingcifen;
    }

    public void setMingcifen(GuandanMingcifenVO mingcifen) {
        this.mingcifen = mingcifen;
    }

//    public int getXianshubeishu() {
//        return xianshubeishu;
//    }
//
//    public void setXianshubeishu(int xianshubeishu) {
//        this.xianshubeishu = xianshubeishu;
//    }
//
//    public int getGongxianfen() {
//        return gongxianfen;
//    }
//
//    public void setGongxianfen(int gongxianfen) {
//        this.gongxianfen = gongxianfen;
//    }
//
//    public int getBufen() {
//        return bufen;
//    }
//
//    public void setBufen(int bufen) {
//        this.bufen = bufen;
//    }

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
}
