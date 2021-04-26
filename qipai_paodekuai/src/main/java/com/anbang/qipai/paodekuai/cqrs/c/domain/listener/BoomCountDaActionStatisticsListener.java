package com.anbang.qipai.paodekuai.cqrs.c.domain.listener;

import java.util.HashMap;
import java.util.Map;

import com.dml.paodekuai.ju.Ju;
import com.dml.paodekuai.pan.PanActionFrame;
import com.dml.paodekuai.player.action.da.DaAction;
import com.dml.paodekuai.player.action.da.DaActionStatisticsListener;
import com.dml.paodekuai.player.action.guo.GuoAction;
import com.dml.paodekuai.wanfa.OptionalPlay;
import com.dml.puke.wanfa.dianshu.dianshuzu.DianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.ZhadanDianShuZu;


/**
 * 记录每盘每个玩家打出的炸弹数
 */
public class BoomCountDaActionStatisticsListener implements DaActionStatisticsListener {

    private Map<String, Integer> playerzhadanshuMap = new HashMap<>();

    private Map<String, Integer> playerBeiyaZhadanShuMap = new HashMap<>();

    private OptionalPlay optionalPlay;

    public BoomCountDaActionStatisticsListener(OptionalPlay optionalPlay) {
        this.optionalPlay = optionalPlay;
    }

    public BoomCountDaActionStatisticsListener() {
    }

    @Override
    public void updateForNextPan() {
        playerzhadanshuMap.replaceAll((l, v) -> 0);
        playerBeiyaZhadanShuMap.replaceAll((l, v) -> 0);
    }

    @Override
    public void update(DaAction daAction, Ju ju) {
        String daActionPlayerId = daAction.getActionPlayerId();
        int boomCount = playerzhadanshuMap.computeIfAbsent(daActionPlayerId, k -> 0);
        DianShuZu dianShuZu = daAction.getDachuPaiZu().getDianShuZu();
        if (dianShuZu instanceof ZhadanDianShuZu) {
            PanActionFrame lastAction = ju.getCurrentPan().getActionFrameList().get(ju.getCurrentPan().getActionFrameList().size() - 1);
            if (optionalPlay.isZhadanbeiyabugeifen() && lastAction.getAction() instanceof DaAction) {   //炸弹被压不算分
                DaAction lastDa = (DaAction) lastAction.getAction();
                if (lastDa.getDachuPaiZu().getDianShuZu() instanceof ZhadanDianShuZu && !lastAction.getAction().getActionPlayerId().equals(daAction.getActionPlayerId())) { //如果上一手是炸弹 并且不是自己打的
                    Integer beiyaPlayerBoomCount = playerzhadanshuMap.get(lastAction.getAction().getActionPlayerId());
                    if (beiyaPlayerBoomCount >= 1) beiyaPlayerBoomCount--;
                    playerzhadanshuMap.put(lastAction.getAction().getActionPlayerId(), beiyaPlayerBoomCount);
                    playerzhadanshuMap.put(daActionPlayerId, boomCount + 1);
                } else {
                    playerzhadanshuMap.put(daActionPlayerId, boomCount + 1);
                }
            } else if (optionalPlay.isYingsuanzha() && lastAction.getAction() instanceof DaAction) {    //赢了算炸
                DaAction lastDa = (DaAction) lastAction.getAction();
                if (lastDa.getDachuPaiZu().getDianShuZu() instanceof ZhadanDianShuZu && !lastAction.getAction().getActionPlayerId().equals(daAction.getActionPlayerId())) {
                    Integer beiyaPlayerBoomCount = playerzhadanshuMap.get(lastAction.getAction().getActionPlayerId());
                    if (beiyaPlayerBoomCount == 1) {
                        beiyaPlayerBoomCount++;
                    }
                    playerBeiyaZhadanShuMap.put(lastAction.getAction().getActionPlayerId(), beiyaPlayerBoomCount);
                    playerBeiyaZhadanShuMap.put(daActionPlayerId, 0);
                    playerzhadanshuMap.put(daActionPlayerId, beiyaPlayerBoomCount + playerzhadanshuMap.get(daActionPlayerId));
                } else {
                    playerzhadanshuMap.put(daActionPlayerId, boomCount + 1);
                }
            } else {
                playerzhadanshuMap.put(daActionPlayerId, boomCount + 1);
            }
        }

    }

    public Map<String, Integer> getPlayerzhadanshuMap() {
        return playerzhadanshuMap;
    }

    public void setPlayerzhadanshuMap(Map<String, Integer> playerzhadanshuMap) {
        this.playerzhadanshuMap = playerzhadanshuMap;
    }

    public OptionalPlay getOptionalPlay() {
        return optionalPlay;
    }

    public void setOptionalPlay(OptionalPlay optionalPlay) {
        this.optionalPlay = optionalPlay;
    }

    public Map<String, Integer> getPlayerBeiyaZhadanShuMap() {
        return playerBeiyaZhadanShuMap;
    }

    public void setPlayerBeiyaZhadanShuMap(Map<String, Integer> playerBeiyaZhadanShuMap) {
        this.playerBeiyaZhadanShuMap = playerBeiyaZhadanShuMap;
    }
}
