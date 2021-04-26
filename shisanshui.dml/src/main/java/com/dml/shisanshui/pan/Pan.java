package com.dml.shisanshui.pan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dml.shisanshui.pai.PukePai;
import com.dml.shisanshui.pai.paixing.comparator.DaoComparator;
import com.dml.shisanshui.pai.paixing.Dao;
import com.dml.shisanshui.pai.paixing.PaixingSolution;
import com.dml.shisanshui.player.PlayerNotFoundException;
import com.dml.shisanshui.player.ShisanshuiPlayer;
import com.dml.shisanshui.player.action.ChupaiDaoCalculator;
import com.dml.shisanshui.player.action.ChupaiPaixingSolutionFilter;
import com.dml.shisanshui.player.action.ShisanshuiChupaiAction;
import com.dml.shisanshui.position.Position;

public class Pan {

    private int no;

    private Map<String, ShisanshuiPlayer> playerIdPlayerMap = new HashMap<>();

    private Map<Position, String> positionPlayerIdMap = new HashMap<>();

    private List<PukePai> avaliablePaiList = new ArrayList<>();

    private List<PanActionFrame> actionFrameList = new ArrayList<>();

    public PanActionFrame recordPanActionFrame(ShisanshuiChupaiAction action, long actionTime) {
        PanActionFrame frame = new PanActionFrame(action, new PanValueObject(this), actionTime);
        frame.setNo(actionFrameList.size());
        actionFrameList.add(frame);
        return frame;
    }

    public List<String> sortedPlayerIds() {
        return new ArrayList<>(playerIdPlayerMap.keySet());
    }

    public ShisanshuiPlayer findShisanshuiPlayerById(String playerId) {
        return playerIdPlayerMap.get(playerId);
    }

    public void addPlayer(String playerId) {
        ShisanshuiPlayer player = new ShisanshuiPlayer();
        player.setId(playerId);
        playerIdPlayerMap.put(playerId, player);
    }

    public void generateAllChupaiPaixingSolution(ChupaiDaoCalculator chupaiDaoCalculator) {
        for (ShisanshuiPlayer player : playerIdPlayerMap.values()) {
            player.putChupaiSolutionCandidates(chupaiDaoCalculator.generateAllPaixingSolution(player.getAllShoupai()));
        }
    }

    public void generateChupaiPaixingSolutionsForTips(ChupaiPaixingSolutionFilter chupaiPaixingSolutionFilter) {
        for (ShisanshuiPlayer player : playerIdPlayerMap.values()) {
            player.generateChupaiSolutionForTips(chupaiPaixingSolutionFilter);
        }
    }

    public void updatePlayerPositionByPlayerId(String playerId, Position position) throws PlayerNotFoundException {
        if (!playerIdPlayerMap.containsKey(playerId)) {
            throw new PlayerNotFoundException();
        }
        ShisanshuiPlayer player = playerIdPlayerMap.get(playerId);
        player.setPosition(position);
        positionPlayerIdMap.put(position, playerId);
    }

    public Dao findDaoByPlayerIdAndIndex(String playerId, String index) throws Exception {
        if (!playerIdPlayerMap.containsKey(playerId)) {
            throw new PlayerNotFoundException();
        }
        ShisanshuiPlayer player = playerIdPlayerMap.get(playerId);
        return player.findDaoByIndex(index);
    }

    public ShisanshuiChupaiAction chupai(String playerId, String toudaoIndex, String zhongdaoIndex, String weidaoIndex, DaoComparator daoComparator) throws Exception {
        if (!playerIdPlayerMap.containsKey(playerId)) {
            throw new PlayerNotFoundException();
        }
        ShisanshuiPlayer player = playerIdPlayerMap.get(playerId);
        PaixingSolution solution = player.chupai(toudaoIndex, zhongdaoIndex, weidaoIndex, daoComparator);
        ShisanshuiChupaiAction action = new ShisanshuiChupaiAction(playerId, solution);
        return action;
    }

    public ShisanshuiChupaiAction autoChupai(String playerId, DaoComparator daoComparator) throws Exception {
        if (!playerIdPlayerMap.containsKey(playerId)) {
            throw new PlayerNotFoundException();
        }
        ShisanshuiPlayer player = playerIdPlayerMap.get(playerId);
        PaixingSolution solution = player.autoChupai(daoComparator);
        return new ShisanshuiChupaiAction(playerId, solution);
    }

    public PanActionFrame findLatestActionFrame() {
        if (!actionFrameList.isEmpty()) {
            return actionFrameList.get(actionFrameList.size() - 1);
        } else {
            return null;
        }
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Map<String, ShisanshuiPlayer> getPlayerIdPlayerMap() {
        return playerIdPlayerMap;
    }

    public void setPlayerIdPlayerMap(Map<String, ShisanshuiPlayer> playerIdPlayerMap) {
        this.playerIdPlayerMap = playerIdPlayerMap;
    }

    public Map<Position, String> getPositionPlayerIdMap() {
        return positionPlayerIdMap;
    }

    public void setPositionPlayerIdMap(Map<Position, String> positionPlayerIdMap) {
        this.positionPlayerIdMap = positionPlayerIdMap;
    }

    public List<PukePai> getAvaliablePaiList() {
        return avaliablePaiList;
    }

    public void setAvaliablePaiList(List<PukePai> avaliablePaiList) {
        this.avaliablePaiList = avaliablePaiList;
    }

    public List<PanActionFrame> getActionFrameList() {
        return actionFrameList;
    }

    public void setActionFrameList(List<PanActionFrame> actionFrameList) {
        this.actionFrameList = actionFrameList;
    }
}
