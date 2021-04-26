package com.anbang.qipai.maanshanmajiang.msg.msjobj;

import java.util.ArrayList;
import java.util.List;

import com.anbang.qipai.maanshanmajiang.cqrs.c.domain.MaanshanMajiangJuResult;
import com.anbang.qipai.maanshanmajiang.cqrs.q.dbo.JuResultDbo;
import com.anbang.qipai.maanshanmajiang.cqrs.q.dbo.MajiangGameDbo;

public class MajiangHistoricalJuResult {
    private String gameId;
    private String dayingjiaId;
    private String datuhaoId;
    private List<JuPlayerResultMO> playerResultList;
    private int lastPanNo;
    private int panshu;
    private long finishTime;

    public MajiangHistoricalJuResult(JuResultDbo juResultDbo, MajiangGameDbo majiangGameDbo) {
        gameId = juResultDbo.getGameId();
        MaanshanMajiangJuResult fangpaoMajiangJuResult = juResultDbo.getJuResult();
        dayingjiaId = fangpaoMajiangJuResult.getDayingjiaId();
        datuhaoId = fangpaoMajiangJuResult.getDatuhaoId();
        finishTime = juResultDbo.getFinishTime();
        this.panshu = majiangGameDbo.getPanshu();
        lastPanNo = fangpaoMajiangJuResult.getFinishedPanCount();
        playerResultList = new ArrayList<>();
        if (fangpaoMajiangJuResult.getPlayerResultList() != null) {
            fangpaoMajiangJuResult.getPlayerResultList().forEach((juPlayerResult) -> playerResultList.add(new JuPlayerResultMO(juPlayerResult, majiangGameDbo.findPlayer(juPlayerResult.getPlayerId()))));//总分要减去园子分
        } else {
            majiangGameDbo.getPlayers().forEach((majiangGamePlayerDbo) -> playerResultList.add(new JuPlayerResultMO(majiangGamePlayerDbo)));//默认起始分数为园子分
        }

        //要根据算法计算局结算分
//        if (majiangGameDbo.getOptionalPlay().isJinyuanzi()) {   //进园子玩法下最后得分要减去游戏开始时给的园子分
//            for (JuPlayerResultMO maanshanMajiangJuPlayerResultMO : playerResultList) {
//                double totalScore = maanshanMajiangJuPlayerResultMO.getTotalScore();
//                totalScore -= majiangGameDbo.getOptionalPlay().getYuanzifen();
//                maanshanMajiangJuPlayerResultMO.setTotalScore(totalScore);
//            }
//        }

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

    public List<JuPlayerResultMO> getPlayerResultList() {
        return playerResultList;
    }

    public void setPlayerResultList(List<JuPlayerResultMO> playerResultList) {
        this.playerResultList = playerResultList;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public int getLastPanNo() {
        return lastPanNo;
    }

    public void setLastPanNo(int lastPanNo) {
        this.lastPanNo = lastPanNo;
    }

    public int getPanshu() {
        return panshu;
    }

    public void setPanshu(int panshu) {
        this.panshu = panshu;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

}
