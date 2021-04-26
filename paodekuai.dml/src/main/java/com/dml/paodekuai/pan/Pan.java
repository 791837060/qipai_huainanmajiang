package com.dml.paodekuai.pan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dml.paodekuai.pai.waihao.WaihaoGenerator;
import com.dml.paodekuai.player.PaodekuaiPlayer;
import com.dml.paodekuai.player.PlayerNotFoundException;
import com.dml.paodekuai.player.action.PaodekuaiPlayerAction;
import com.dml.paodekuai.player.action.da.DaAction;
import com.dml.paodekuai.player.action.da.KedaPaiSolutionsForTipsGenerator;
import com.dml.paodekuai.player.action.da.PlayerCanNotActionException;
import com.dml.paodekuai.player.action.da.YaPaiSolutionsTipsFilter;
import com.dml.paodekuai.player.action.da.solution.DianShuZuYaPaiSolutionCalculator;
import com.dml.paodekuai.player.action.da.solution.ZaDanYaPaiSolutionCalculator;
import com.dml.paodekuai.player.action.guo.CanNotGuoException;
import com.dml.paodekuai.player.action.guo.GuoAction;
import com.dml.puke.pai.PukePai;
import com.dml.puke.wanfa.dianshu.paizu.DianShuZuPaiZu;
import com.dml.puke.wanfa.position.Position;
import com.dml.puke.wanfa.position.PositionUtil;

public class Pan {
    private int no;
    private Map<String, PaodekuaiPlayer> paodekuaiPlayerIdMajiangPlayerMap = new HashMap<>();
    private Map<Position, String> positionPlayerIdMap = new HashMap<>();
    private List<PukePai> avaliablePaiList = new ArrayList<>();
    private List<DianShuZuPaiZu> dachuPaiZuList = new ArrayList<>();
    private List<String> noPaiPlayerIdList = new ArrayList<>();// 按走的顺序排的playerid数组
    private Position actionPosition;
    private String latestDapaiPlayerId;
    private String zhuaniaoPlayerId; // 抓鸟玩家id
    private List<PanActionFrame> actionFrameList = new ArrayList<>();
    private Set<String> baodanSet = new HashSet<>();
    /**
     * 打出A的次数
     */
    private int dachuACount = 0;

    /**
     * 勾选黑桃三先出必出或者红桃三先出必出时，2人局没有人有黑桃三或红桃三
     */
    private boolean notExistentSan = false;

    public boolean ifPlayerHasPai(String playerId) throws PlayerNotFoundException {
        PaodekuaiPlayer player = paodekuaiPlayerIdMajiangPlayerMap.get(playerId);
        if (player == null) {
            throw new PlayerNotFoundException();
        }
        return !player.getAllShoupai().isEmpty();
    }

    public List<String> findAllPlayerId() {
        return new ArrayList<>(paodekuaiPlayerIdMajiangPlayerMap.keySet());
    }

    public PanActionFrame recordPanActionFrame(PaodekuaiPlayerAction action, long actionTime) {
        PanActionFrame frame = new PanActionFrame(action, new PanValueObject(this), actionTime);
        frame.setNo(actionFrameList.size());
        actionFrameList.add(frame);
        return frame;
    }

    public PanActionFrame findLatestActionFrame() {
        if (!actionFrameList.isEmpty()) {
            return actionFrameList.get(actionFrameList.size() - 1);
        } else {
            return null;
        }
    }

    public void addPlayer(String playerId) {
        PaodekuaiPlayer paodekuaiPlayer = new PaodekuaiPlayer();
        paodekuaiPlayer.setId(playerId);
        paodekuaiPlayerIdMajiangPlayerMap.put(playerId, paodekuaiPlayer);
    }

    public void addFrame(PanActionFrame panActionFrame) {
        actionFrameList.add(panActionFrame);
    }

    public List<String> sortedPlayerIdList() {
        List<String> list = new ArrayList<>(paodekuaiPlayerIdMajiangPlayerMap.keySet());
        Collections.sort(list);
        return list;
    }

    public void updateActionPositionByActionPlayer(String playerId) throws Exception {
        PaodekuaiPlayer player = paodekuaiPlayerIdMajiangPlayerMap.get(playerId);
        if (player == null) {
            throw new PlayerNotFoundException();
        }
        actionPosition = player.getPosition();
    }

    public void updatePlayerPosition(String playerId, Position position) throws PlayerNotFoundException {
        PaodekuaiPlayer player = paodekuaiPlayerIdMajiangPlayerMap.get(playerId);
        if (player == null) {
            throw new PlayerNotFoundException();
        }
        player.setPosition(position);
        positionPlayerIdMap.put(position, playerId);
    }

    public String playerIdForPosition(Position position) {
        return positionPlayerIdMap.get(position);
    }

    public DaAction da(String playerId, List<Integer> paiIds, String dianshuZuheIdx, WaihaoGenerator waihaoGenerator)
            throws Exception {
        PaodekuaiPlayer daPlayer = paodekuaiPlayerIdMajiangPlayerMap.get(playerId);
        if (daPlayer == null) {
            throw new PlayerNotFoundException();
        }
        if (!actionPosition.equals(daPlayer.getPosition())) {
            throw new PlayerCanNotActionException();
        }

        // 如果是大的人打牌，那先要清桌
        if (latestDapaiPlayerId == null || ifStartYapai()) {
            paodekuaiPlayerIdMajiangPlayerMap.values().forEach((player) -> {
                player.putPublicDachuPaiZuToLishi();
            });
        }

        daPlayer.da(paiIds, dianshuZuheIdx, waihaoGenerator);

        // 剩一张牌时报单
        if (daPlayer.getAllShoupai().size() == 1) {
            baodanSet.add(daPlayer.getId());
        }

        // 本次打出的牌
        DianShuZuPaiZu publicDachuPaiZu = daPlayer.getPublicDachuPaiZu();
        dachuPaiZuList.add(publicDachuPaiZu);

        latestDapaiPlayerId = playerId;
        if (daPlayer.getAllShoupai().isEmpty()) {
            noPaiPlayerIdList.add(playerId);
        }
        DaAction daAction = new DaAction(playerId);
        daAction.setDachuPaiZu(publicDachuPaiZu);

        return daAction;
    }

    public GuoAction guo(String playerId) throws Exception {
        PaodekuaiPlayer player = paodekuaiPlayerIdMajiangPlayerMap.get(playerId);
        if (player == null) {
            throw new PlayerNotFoundException();
        }
        if (!actionPosition.equals(player.getPosition())) {
            throw new PlayerCanNotActionException();
        }
        // 大的人必须出牌，不能过，第一手牌也不能过
        if (latestDapaiPlayerId == null || playerId.equals(latestDapaiPlayerId)) {
            throw new CanNotGuoException();
        }
        player.guo();
        return new GuoAction(player.getId());
    }

    public void updateNextPlayersDaSolution(DianShuZuYaPaiSolutionCalculator dianShuZuYaPaiCalculator, ZaDanYaPaiSolutionCalculator zaDanYaPaiCalculator) {
        String dachuPlayerId = latestDapaiPlayerId;

        boolean dachuHeotaosan = false;
        boolean dachuHongtaosan = false;
        for (PaodekuaiPlayer player : paodekuaiPlayerIdMajiangPlayerMap.values()) {
            if (player.isDachuHeitaoSan()) {
                dachuHeotaosan = true;
            } else if (player.isDachuHonxinSan()) {
                dachuHongtaosan = true;
            }
        }

        if (dachuPlayerId != null) {
            PaodekuaiPlayer dachuPlayer = paodekuaiPlayerIdMajiangPlayerMap.get(dachuPlayerId);
            if (dachuPlayer != null) {
                DianShuZuPaiZu dachuPaiZu = dachuPlayer.getPublicDachuPaiZu();
                if (dachuPaiZu != null) {
                    PaodekuaiPlayer yapaiPlayer = null;
                    try {
                        yapaiPlayer = findNextActionPlayer();
                    } catch (PlayerNotFoundException e) {

                    }
                    if (yapaiPlayer != null) {
                        yapaiPlayer.addDaPaiDianShuSolutions(dianShuZuYaPaiCalculator.calculate(dachuPaiZu.getDianShuZu(), yapaiPlayer.getShoupaiDianShuAmountArray(), afterNextBaodan(), this, yapaiPlayer));
                        if (dachuACount != 1) {  //如果有首A比用2压玩法 第一次打出A后有2的玩家只能用2压
                            yapaiPlayer.addDaPaiDianShuSolutions(zaDanYaPaiCalculator.calculate(dachuPaiZu.getDianShuZu(), yapaiPlayer.getShoupaiDianShuAmountArray(),
                                    dachuHeotaosan, dachuHongtaosan,notExistentSan));
                        }
                    }
                }
            }
        }

    }

    public void generateYaPaiSolutionsForTips(YaPaiSolutionsTipsFilter yaPaiSolutionsTipsFilter) {
        PaodekuaiPlayer yapaiPlayer = null;
        try {
            yapaiPlayer = findNextActionPlayer();
        } catch (PlayerNotFoundException e) {

        }
        if (yapaiPlayer != null) {
            yapaiPlayer.generateYaPaiSolutionsForTips(yaPaiSolutionsTipsFilter);
        }
    }

    public void generateDaPaiSolutionsForTips(KedaPaiSolutionsForTipsGenerator kedaPaiSolutionsForTipsGenerator) {
        PaodekuaiPlayer yapaiPlayer = null;
        try {
            yapaiPlayer = findNextActionPlayer();
        } catch (PlayerNotFoundException e) {

        }
        if (yapaiPlayer != null) {
            yapaiPlayer.generateDaPaiSolutionsForTips(kedaPaiSolutionsForTipsGenerator);
        }
    }

    public void updateActionPositionToNextPlayer() {
        PaodekuaiPlayer yapaiPlayer = null;
        try {
            yapaiPlayer = findNextActionPlayer();
        } catch (PlayerNotFoundException e) {

        }
        if (yapaiPlayer != null) {
            yapaiPlayer.putPublicDachuPaiZuToLishi();
            yapaiPlayer.setGuo(false);
            actionPosition = yapaiPlayer.getPosition();
        }
    }

    /**
     * 查找下一个打牌的玩家
     *
     * @throws PlayerNotFoundException
     */
    public PaodekuaiPlayer findNextActionPlayer() throws PlayerNotFoundException {
        Position nextPosition = PositionUtil.nextPositionClockwise(actionPosition);
        String yapaiPlayerId = positionPlayerIdMap.get(nextPosition);
        while (yapaiPlayerId == null || !ifPlayerHasPai(yapaiPlayerId) && !yapaiPlayerId.equals(latestDapaiPlayerId)) {
            nextPosition = PositionUtil.nextPositionClockwise(nextPosition);
            yapaiPlayerId = positionPlayerIdMap.get(nextPosition);
        }
        PaodekuaiPlayer yapaiPlayer = paodekuaiPlayerIdMajiangPlayerMap.get(yapaiPlayerId);
        return yapaiPlayer;
    }

    public PaodekuaiPlayer findPlayer(String dapaiPlayerId) {
        return paodekuaiPlayerIdMajiangPlayerMap.get(dapaiPlayerId);
    }

    public PaodekuaiPlayer findDuijiaPlayer(String playerId) {
        PaodekuaiPlayer player = paodekuaiPlayerIdMajiangPlayerMap.get(playerId);
        if (player == null) {
            return null;
        }
        Position nextPosition = PositionUtil.nextPositionClockwise(player.getPosition());
        nextPosition = PositionUtil.nextPositionClockwise(nextPosition);
        String duijiaPlayerId = positionPlayerIdMap.get(nextPosition);
        if (duijiaPlayerId == null) {
            return null;
        }
        return paodekuaiPlayerIdMajiangPlayerMap.get(duijiaPlayerId);
    }

    /**
     * 是否是新一轮压牌
     *
     * @throws PlayerNotFoundException
     */
    public boolean ifStartYapai() throws PlayerNotFoundException {
        Position nextPosition = PositionUtil.nextPositionClockwise(actionPosition);
        String yapaiPlayerId = positionPlayerIdMap.get(nextPosition);
        while (yapaiPlayerId == null || !ifPlayerHasPai(yapaiPlayerId) && !yapaiPlayerId.equals(latestDapaiPlayerId)) {
            nextPosition = PositionUtil.nextPositionClockwise(nextPosition);
            yapaiPlayerId = positionPlayerIdMap.get(nextPosition);
        }
        PaodekuaiPlayer yapaiPlayer = paodekuaiPlayerIdMajiangPlayerMap.get(yapaiPlayerId);
        return yapaiPlayer.getId().equals(latestDapaiPlayerId);
    }

    /**
     * 查找三人局中下下家玩家id
     */
    public String findAfterNextPlayer() {
        Position nextPosition = PositionUtil.nextPositionClockwise(actionPosition);
        String nextPlayerId = positionPlayerIdMap.get(nextPosition);
        while (nextPlayerId == null) {
            nextPosition = PositionUtil.nextPositionClockwise(nextPosition);
            nextPlayerId = positionPlayerIdMap.get(nextPosition);
        }

        nextPosition = PositionUtil.nextPositionClockwise(nextPosition);
        nextPlayerId = positionPlayerIdMap.get(nextPosition);
        while (nextPlayerId == null) {
            nextPosition = PositionUtil.nextPositionClockwise(nextPosition);
            nextPlayerId = positionPlayerIdMap.get(nextPosition);
        }
        return nextPlayerId;
    }

    /**
     * 通过三人局中下下家是否报单决定下家单张牌的打牌方案
     */
    public boolean afterNextBaodan() {
        boolean baodan = false;
        String afterNextPlayerId = findAfterNextPlayer();
        if (baodanSet.contains(afterNextPlayerId)) {
            baodan = true;
        }
        return baodan;
    }

    /**
     * ------------------set/get
     */

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Map<String, PaodekuaiPlayer> getPaodekuaiPlayerIdMajiangPlayerMap() {
        return paodekuaiPlayerIdMajiangPlayerMap;
    }

    public void setPaodekuaiPlayerIdMajiangPlayerMap(Map<String, PaodekuaiPlayer> paodekuaiPlayerIdMajiangPlayerMap) {
        this.paodekuaiPlayerIdMajiangPlayerMap = paodekuaiPlayerIdMajiangPlayerMap;
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

    public List<String> getNoPaiPlayerIdList() {
        return noPaiPlayerIdList;
    }

    public void setNoPaiPlayerIdList(List<String> noPaiPlayerIdList) {
        this.noPaiPlayerIdList = noPaiPlayerIdList;
    }

    public List<DianShuZuPaiZu> getDachuPaiZuList() {
        return dachuPaiZuList;
    }

    public void setDachuPaiZuList(List<DianShuZuPaiZu> dachuPaiZuList) {
        this.dachuPaiZuList = dachuPaiZuList;
    }

    public Position getActionPosition() {
        return actionPosition;
    }

    public void setActionPosition(Position actionPosition) {
        this.actionPosition = actionPosition;
    }

    public String getLatestDapaiPlayerId() {
        return latestDapaiPlayerId;
    }

    public void setLatestDapaiPlayerId(String latestDapaiPlayerId) {
        this.latestDapaiPlayerId = latestDapaiPlayerId;
    }

    public List<PanActionFrame> getActionFrameList() {
        return actionFrameList;
    }

    public void setActionFrameList(List<PanActionFrame> actionFrameList) {
        this.actionFrameList = actionFrameList;
    }

    public String getZhuaniaoPlayerId() {
        return zhuaniaoPlayerId;
    }

    public void setZhuaniaoPlayerId(String zhuaniaoPlayerId) {
        this.zhuaniaoPlayerId = zhuaniaoPlayerId;
    }

    public Set<String> getBaodanSet() {
        return baodanSet;
    }

    public void setBaodanSet(Set<String> baodanSet) {
        this.baodanSet = baodanSet;
    }

    public int getDachuACount() {
        return dachuACount;
    }

    public void setDachuACount(int dachuACount) {
        this.dachuACount = dachuACount;
    }

    public boolean isNotExistentSan() {
        return notExistentSan;
    }

    public void setNotExistentSan(boolean notExistentSan) {
        this.notExistentSan = notExistentSan;
    }
}
