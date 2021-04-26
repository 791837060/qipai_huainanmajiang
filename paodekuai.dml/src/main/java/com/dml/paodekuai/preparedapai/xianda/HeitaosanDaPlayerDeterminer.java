package com.dml.paodekuai.preparedapai.xianda;

import com.dml.paodekuai.ju.Ju;
import com.dml.paodekuai.pan.Pan;
import com.dml.paodekuai.pan.PanResult;
import com.dml.paodekuai.player.PaodekuaiPlayer;
import com.dml.paodekuai.player.PlayerNotFoundException;
import com.dml.paodekuai.wanfa.OptionalPlay;
import com.dml.puke.pai.PukePai;
import com.dml.puke.pai.PukePaiMian;
import com.dml.puke.pai.QiShouLiangPaiMark;
import com.dml.puke.wanfa.position.Position;

import javax.swing.plaf.basic.BasicScrollPaneUI;
import java.util.Collections;
import java.util.List;

/**
 * 三人选择黑桃三玩法时黑桃三先出
 */
public class HeitaosanDaPlayerDeterminer implements XiandaPlayerDeterminer {
    private OptionalPlay optionalPlay;

    @Override
    public String determineXiandaPlayerFirst(Ju ju) throws PlayerNotFoundException {
        Pan currentPan = ju.getCurrentPan();
        List<String> playerIdList = currentPan.sortedPlayerIdList();
        Collections.shuffle(playerIdList);
        String daplayerId = null;

        if (optionalPlay.isBichu() || optionalPlay.isHeitaosanXianchuBubichu()) {
            boolean hasChupai = false;
            PukePaiMian[] values = PukePaiMian.values();
            PukePaiMian pukePaiMian;
            for (int i = 0; i < 52; i += 4) {
                pukePaiMian = values[i];
                if (!hasChupai) {
                    for (PaodekuaiPlayer player : currentPan.getPaodekuaiPlayerIdMajiangPlayerMap().values()) {
                        for (PukePai pukePai : player.getAllShoupai().values()) {
                            // 黑桃三设为先出牌，埋下亮牌标记
                            if (pukePai.getPaiMian().equals(pukePaiMian)) {
                                pukePai.setMark(new QiShouLiangPaiMark());
                                daplayerId = player.getId();
                                hasChupai = true;
                                break;
                            }
                        }
                    }
                }
            }
        } else if (optionalPlay.isHongxinsanxianchu() || optionalPlay.isHongtaosanXianchuBubichu()) {
            boolean hasChupai = false;
            PukePaiMian[] values = PukePaiMian.values();
            PukePaiMian pukePaiMian;
            for (int i = 1; i < 52; i += 4) {
                pukePaiMian = values[i];
                if (!hasChupai) {
                    for (PaodekuaiPlayer player : currentPan.getPaodekuaiPlayerIdMajiangPlayerMap().values()) {
                        for (PukePai pukePai : player.getAllShoupai().values()) {
                            // 红心三设为先出牌，埋下亮牌标记
                            if (pukePai.getPaiMian().equals(pukePaiMian)) {
                                pukePai.setMark(new QiShouLiangPaiMark());
                                daplayerId = player.getId();
                                hasChupai = true;
                                break;
                            }
                        }
                    }
                }
            }
        } else {
            PanResult latestFinishedPanResult = ju.findLatestFinishedPanResult();
            if (latestFinishedPanResult != null) {
                daplayerId = latestFinishedPanResult.getPan().getLatestDapaiPlayerId();
            }
            if (daplayerId == null) {
                daplayerId = playerIdList.get(0);
            }
        }

        // 分配玩家位置
        currentPan.updatePlayerPosition(daplayerId, Position.dong);
        Position[] positions = Position.values();

        if (playerIdList.size() == 2) { // 2人时，东西对坐
            for (String playerId : playerIdList) {
                if (!playerId.equals(daplayerId)) {
                    currentPan.updatePlayerPosition(playerId, positions[2]);
                }
            }
        } else {
            int index = 1;
            for (String playerId : playerIdList) {
                if (!playerId.equals(daplayerId)) {
                    currentPan.updatePlayerPosition(playerId, positions[index]);
                    index++;
                }
            }
        }
        return daplayerId;
    }

    @Override
    public String determineXiandaPlayerNext(Ju ju) throws PlayerNotFoundException {
        Pan currentPan = ju.getCurrentPan();
        List<String> playerIdList = currentPan.sortedPlayerIdList();
        Collections.shuffle(playerIdList);
        String daplayerId = null;
        PanResult latestFinishedPanResult = ju.findLatestFinishedPanResult();
        if (latestFinishedPanResult != null) {
            daplayerId = latestFinishedPanResult.getPan().getLatestDapaiPlayerId();
        }
        if (daplayerId == null) {
            daplayerId = playerIdList.get(0);
        }

        // 分配玩家位置
        currentPan.updatePlayerPosition(daplayerId, Position.dong);
        Position[] positions = Position.values();

        if (playerIdList.size() == 2) { // 2人时，东西对坐
            for (String playerId : playerIdList) {
                if (!playerId.equals(daplayerId)) {
                    currentPan.updatePlayerPosition(playerId, positions[2]);
                }
            }
        } else {
            int index = 1;
            for (String playerId : playerIdList) {
                if (!playerId.equals(daplayerId)) {
                    currentPan.updatePlayerPosition(playerId, positions[index]);
                    index++;
                }
            }
        }
        return daplayerId;
    }

    public HeitaosanDaPlayerDeterminer() {
    }

    public OptionalPlay getOptionalPlay() {
        return optionalPlay;
    }

    public void setOptionalPlay(OptionalPlay optionalPlay) {
        this.optionalPlay = optionalPlay;
    }
}
