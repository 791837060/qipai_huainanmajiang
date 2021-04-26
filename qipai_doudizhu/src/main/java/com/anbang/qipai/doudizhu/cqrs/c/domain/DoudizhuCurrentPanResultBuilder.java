package com.anbang.qipai.doudizhu.cqrs.c.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.anbang.qipai.doudizhu.cqrs.c.domain.jiaofen.DoudizhuJiaofenDizhuDeterminer;
import com.anbang.qipai.doudizhu.cqrs.c.domain.listener.ChuntainAndFanchuntianOpportunityDetector;
import com.anbang.qipai.doudizhu.cqrs.c.domain.listener.ZhadanDaActionStatisticsListener;
import com.anbang.qipai.doudizhu.cqrs.c.domain.qiangdizhu.QiangdizhuDizhuDeterminer;
import com.anbang.qipai.doudizhu.cqrs.c.domain.result.DoudizhuPanPlayerResult;
import com.anbang.qipai.doudizhu.cqrs.c.domain.result.DoudizhuPanResult;
import com.dml.doudizhu.ju.Ju;
import com.dml.doudizhu.pan.CurrentPanResultBuilder;
import com.dml.doudizhu.pan.Pan;
import com.dml.doudizhu.pan.PanResult;
import com.dml.doudizhu.pan.PanValueObject;
import com.dml.doudizhu.player.DoudizhuPlayer;
import com.dml.doudizhu.preparedapai.dipai.SanzhangDipaiDeterminer;

public class DoudizhuCurrentPanResultBuilder implements CurrentPanResultBuilder {

	private int renshu;
	private double difen;
    private int fdbs;//封顶倍数
    private boolean jiaofen;


    @Override
	public PanResult buildCurrentPanResult(Ju ju, long panFinishTime) {
		DoudizhuPanResult latestFinishedPanResult = (DoudizhuPanResult) ju.findLatestFinishedPanResult();
		Map<String, Double> playerTotalScoreMap = new HashMap<>();
		if (latestFinishedPanResult != null) {
			for (DoudizhuPanPlayerResult panPlayerResult : latestFinishedPanResult.getPanPlayerResultList()) {
				playerTotalScoreMap.put(panPlayerResult.getPlayerId(), panPlayerResult.getTotalScore());
			}
		}
		Pan currentPan = ju.getCurrentPan();
		String dizhu = currentPan.getDizhuPlayerId();
		boolean dizhuying = false;
		DoudizhuPlayer dizhuPlayer = currentPan.findDizhu();
		if (dizhuPlayer.getAllShoupai().size() == 0) {
			dizhuying = true;
		}

		ChuntainAndFanchuntianOpportunityDetector chuntainAndFanchuntianOpportunityDetector = ju
				.getActionStatisticsListenerManager().findDaListener(ChuntainAndFanchuntianOpportunityDetector.class);
		ZhadanDaActionStatisticsListener zhadanDaActionStatisticsListener = ju.getActionStatisticsListenerManager()
				.findDaListener(ZhadanDaActionStatisticsListener.class);

		SanzhangDipaiDeterminer sanzhangDipaiDeterminer = (SanzhangDipaiDeterminer) ju.getDipaiDeterminer();
		DoudizhuBeishu beishu = new DoudizhuBeishu();
		beishu.setRenshu(renshu);
        if (jiaofen){
            beishu.setQiangdizhuCount(0);
            DoudizhuJiaofenDizhuDeterminer jiaofenDizhuDeterminer=(DoudizhuJiaofenDizhuDeterminer) ju.getDizhuDeterminer();
            beishu.setJiaofen(jiaofenDizhuDeterminer.getScore());
        }else {
            QiangdizhuDizhuDeterminer qiangdizhuDizhuDeterminer = (QiangdizhuDizhuDeterminer) ju.getDizhuDeterminer();
            beishu.setQiangdizhuCount(qiangdizhuDizhuDeterminer.getQiangdizhuCount());
            beishu.setJiaofen(0);
        }

		beishu.setDachuZhadanCount(zhadanDaActionStatisticsListener.getDachuZhadanCount());
		beishu.setChuntian(chuntainAndFanchuntianOpportunityDetector.isChuntian());
		beishu.setFanchuntian(chuntainAndFanchuntianOpportunityDetector.isFanChuntian());
		beishu.setFdbs(fdbs);
		beishu.calculate();

		Map<String, Integer> playerZhadanCountMap = zhadanDaActionStatisticsListener.getPlayerZhadanCountMap();
		List<DoudizhuPanPlayerResult> panPlayerResultList = new ArrayList<>();
		if (dizhuying) {// 地主赢
			for (DoudizhuPlayer player : currentPan.getDoudizhuPlayerIdPlayerMap().values()) {
				DoudizhuPanPlayerResult playerResult = new DoudizhuPanPlayerResult();
				if (dizhu.equals(player.getId())) {
					playerResult.setPlayerId(player.getId());
					playerResult.setYing(true);
					playerResult.setDizhu(true);
					playerResult.setDifen(difen);
					playerResult.setBeishu(beishu);
					playerResult.setScore((renshu - 1) *  beishu.getValue());
					Integer count = playerZhadanCountMap.get(player.getId());
					if (count == null) {
						count = 0;
					}
					playerResult.setZhadanCount(count);
				} else {
					playerResult.setPlayerId(player.getId());
					playerResult.setDifen(difen);
					playerResult.setBeishu(beishu);
					playerResult.setScore(-1 *  beishu.getValue());
					Integer count = playerZhadanCountMap.get(player.getId());
					if (count == null) {
						count = 0;
					}
					playerResult.setZhadanCount(count);
				}
				panPlayerResultList.add(playerResult);
			}
		} else {// 农民赢
			for (DoudizhuPlayer player : currentPan.getDoudizhuPlayerIdPlayerMap().values()) {
				DoudizhuPanPlayerResult playerResult = new DoudizhuPanPlayerResult();
				if (dizhu.equals(player.getId())) {
					playerResult.setPlayerId(player.getId());
					playerResult.setDizhu(true);
					playerResult.setDifen(difen);
					playerResult.setBeishu(beishu);
					playerResult.setScore(-(renshu - 1) *  beishu.getValue());
					Integer count = playerZhadanCountMap.get(player.getId());
					if (count == null) {
						count = 0;
					}
					playerResult.setZhadanCount(count);
				} else {
					playerResult.setPlayerId(player.getId());
					playerResult.setYing(true);
					playerResult.setDifen(difen);
					playerResult.setBeishu(beishu);
					playerResult.setScore( beishu.getValue());
					Integer count = playerZhadanCountMap.get(player.getId());
					if (count == null) {
						count = 0;
					}
					playerResult.setZhadanCount(count);
				}
				panPlayerResultList.add(playerResult);
			}
		}
		panPlayerResultList.forEach((playerResult) -> {
		    playerResult.setScore(new BigDecimal(Double.toString(difen)).multiply(new BigDecimal(Double.toString(playerResult.getScore()))).doubleValue());
			// 计算累计总分
			if (latestFinishedPanResult != null) {
				playerResult
						.setTotalScore(playerTotalScoreMap.get(playerResult.getPlayerId()) + playerResult.getScore());
			} else {
				playerResult.setTotalScore(playerResult.getScore());
			}
		});
		DoudizhuPanResult panResult = new DoudizhuPanResult();
		panResult.setYingjiaPlayerId(currentPan.getYingjiaPlayerId());
		panResult.setDizhuying(dizhuying);
		panResult.setPan(new PanValueObject(currentPan));
		panResult.setPanPlayerResultList(panPlayerResultList);
		panResult.setPanFinishTime(panFinishTime);
		return panResult;
	}

	public int getRenshu() {
		return renshu;
	}

	public void setRenshu(int renshu) {
		this.renshu = renshu;
	}

    public double getDifen() {
        return difen;
    }

    public void setDifen(double difen) {
        this.difen = difen;
    }

    public int getFdbs() {
        return fdbs;
    }

    public void setFdbs(int fdbs) {
        this.fdbs = fdbs;
    }

    public boolean isJiaofen() {
        return jiaofen;
    }

    public void setJiaofen(boolean jiaofen) {
        this.jiaofen = jiaofen;
    }
}
