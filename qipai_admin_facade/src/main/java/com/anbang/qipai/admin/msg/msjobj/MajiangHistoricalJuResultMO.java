package com.anbang.qipai.admin.msg.msjobj;

import java.util.List;

/**
 * @author YaphetS
 * @date 2018/12/5
 */
public class MajiangHistoricalJuResultMO {
    private List<PlayerId> playerResultList;
    private long finishTime;

    public List<PlayerId> getPlayerResultList() {
        return playerResultList;
    }

    public void setPlayerResultList(List<PlayerId> playerResultList) {
        this.playerResultList = playerResultList;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    public String toString() {
        return "OnlineTimeReport{" +
                "playerResultList=" + playerResultList +
                ", finishTime=" + finishTime +
                '}';
    }
}
