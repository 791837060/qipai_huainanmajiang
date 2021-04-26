package com.dml.shuangkou.pan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dml.puke.pai.PukePai;
import com.dml.puke.wanfa.dianshu.paizu.DianShuZuPaiZu;
import com.dml.puke.wanfa.position.Position;
import com.dml.puke.wanfa.position.PositionUtil;
import com.dml.shuangkou.pai.waihao.WaihaoGenerator;
import com.dml.shuangkou.player.PlayerNotFoundException;
import com.dml.shuangkou.player.ShuangkouPlayer;
import com.dml.shuangkou.player.action.ShuangkouPlayerAction;
import com.dml.shuangkou.player.action.da.DaAction;
import com.dml.shuangkou.player.action.da.KedaPaiSolutionsForTipsGenerator;
import com.dml.shuangkou.player.action.da.PlayerCanNotActionException;
import com.dml.shuangkou.player.action.da.YaPaiSolutionsTipsFilter;
import com.dml.shuangkou.player.action.da.solution.DianShuZuYaPaiSolutionCalculator;
import com.dml.shuangkou.player.action.da.solution.ZaDanYaPaiSolutionCalculator;
import com.dml.shuangkou.player.action.guo.CanNotGuoException;
import com.dml.shuangkou.player.action.guo.GuoAction;

public class Pan {
    private int no;
    private Map<String, ShuangkouPlayer> shuangkouPlayerIdPlayerMap = new HashMap<>();
    private Map<Position, String> positionPlayerIdMap = new HashMap<>();
    private List<PukePai> avaliablePaiList = new ArrayList<>();
    private List<DianShuZuPaiZu> dachuPaiZuList = new ArrayList<>();
    private List<String> noPaiPlayerIdList = new ArrayList<>();// 按走的顺序排的playerid数组
    private boolean chuifeng;// 吹风
    private Position actionPosition;
    private String latestDapaiPlayerId;
    private List<PanActionFrame> actionFrameList = new ArrayList<>();

    public boolean ifPlayerHasPai(String playerId) throws PlayerNotFoundException {
        ShuangkouPlayer player = shuangkouPlayerIdPlayerMap.get(playerId);
        if (player == null) {
            throw new PlayerNotFoundException();
        }
        return !player.getAllShoupai().isEmpty();
    }

    public List<String> findAllPlayerId() {
        return new ArrayList<>(shuangkouPlayerIdPlayerMap.keySet());
    }

    public PanActionFrame recordPanActionFrame(ShuangkouPlayerAction action, long actionTime) {
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

    public boolean isNextActionNo(int actionNo) {
        return actionFrameList.size() == actionNo;
    }

    public void addPlayer(String playerId) {
        ShuangkouPlayer shuangkouPlayer = new ShuangkouPlayer();
        shuangkouPlayer.setId(playerId);
        shuangkouPlayerIdPlayerMap.put(playerId, shuangkouPlayer);
    }

    public void addFrame(PanActionFrame panActionFrame) {
        actionFrameList.add(panActionFrame);
    }

    public List<String> sortedPlayerIdList() {
        List<String> list = new ArrayList<>(shuangkouPlayerIdPlayerMap.keySet());
        Collections.sort(list);
        return list;
    }

    public void updateActionPositionByActionPlayer(String playerId) throws Exception {
        ShuangkouPlayer player = shuangkouPlayerIdPlayerMap.get(playerId);
        if (player == null) {
            throw new PlayerNotFoundException();
        }
        actionPosition = player.getPosition();
    }

    public void updatePlayerPosition(String playerId, Position position) throws PlayerNotFoundException {
        ShuangkouPlayer player = shuangkouPlayerIdPlayerMap.get(playerId);
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
        ShuangkouPlayer daPlayer = shuangkouPlayerIdPlayerMap.get(playerId);
        if (daPlayer == null) {
            throw new PlayerNotFoundException();
        }
        if (!actionPosition.equals(daPlayer.getPosition())) {
            throw new PlayerCanNotActionException();
        }
        // 如果是大的人打牌，那先要清桌
        if (latestDapaiPlayerId == null || ifStartYapai()) {
            shuangkouPlayerIdPlayerMap.values().forEach((player) -> {
                player.putPublicDachuPaiZuToLishi();
            });
        }
        daPlayer.da(paiIds, dianshuZuheIdx, waihaoGenerator);
        DianShuZuPaiZu publicDachuPaiZu = daPlayer.getPublicDachuPaiZu();
        dachuPaiZuList.add(publicDachuPaiZu);
        latestDapaiPlayerId = playerId;
        chuifeng = false;
        if (daPlayer.getAllShoupai().isEmpty()) {
            chuifeng = true;
            noPaiPlayerIdList.add(playerId);
        }
        DaAction daAction = new DaAction(playerId);
        daAction.setDachuPaiZu(publicDachuPaiZu);
        return daAction;
    }

    public GuoAction guo(String playerId) throws Exception {
        ShuangkouPlayer player = shuangkouPlayerIdPlayerMap.get(playerId);
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
        if (dachuPlayerId != null) {
            ShuangkouPlayer dachuPlayer = shuangkouPlayerIdPlayerMap.get(dachuPlayerId);
            if (dachuPlayer != null) {
                DianShuZuPaiZu dachuPaiZu = dachuPlayer.getPublicDachuPaiZu();
                if (dachuPaiZu != null) {
                    ShuangkouPlayer yapaiPlayer = null;
                    try {
                        yapaiPlayer = findNextActionPlayer();
                    } catch (PlayerNotFoundException e) {

                    }
                    if (yapaiPlayer != null) {
                        yapaiPlayer.addDaPaiDianShuSolutions(dianShuZuYaPaiCalculator.calculate(dachuPaiZu.getDianShuZu(), yapaiPlayer.getShoupaiDianShuAmountArray()));
                        yapaiPlayer.addDaPaiDianShuSolutions(zaDanYaPaiCalculator.calculate(dachuPaiZu.getDianShuZu(), yapaiPlayer.getShoupaiDianShuAmountArray()));
                    }
                }
            }
        }

    }

    public void generateYaPaiSolutionsForTips(YaPaiSolutionsTipsFilter yaPaiSolutionsTipsFilter) {
        ShuangkouPlayer yapaiPlayer = null;
        try {
            yapaiPlayer = findNextActionPlayer();
        } catch (PlayerNotFoundException e) {

        }
        if (yapaiPlayer != null) {
            yapaiPlayer.generateYaPaiSolutionsForTips(yaPaiSolutionsTipsFilter);
        }
    }

    public void generateDaPaiSolutionsForTips(KedaPaiSolutionsForTipsGenerator kedaPaiSolutionsForTipsGenerator) {
        ShuangkouPlayer yapaiPlayer = null;
        try {
            yapaiPlayer = findNextActionPlayer();
        } catch (PlayerNotFoundException e) {

        }
        if (yapaiPlayer != null) {
            yapaiPlayer.generateDaPaiSolutionsForTips(kedaPaiSolutionsForTipsGenerator);
        }
    }

    public void updateActionPositionToNextPlayer() {
        ShuangkouPlayer yapaiPlayer = null;
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
    public ShuangkouPlayer findNextActionPlayer() throws PlayerNotFoundException {
        Position nextPosition = PositionUtil.nextPositionClockwise(actionPosition);
        String yapaiPlayerId = positionPlayerIdMap.get(nextPosition);
        while (yapaiPlayerId == null || !ifPlayerHasPai(yapaiPlayerId) && !yapaiPlayerId.equals(latestDapaiPlayerId)) {
            nextPosition = PositionUtil.nextPositionClockwise(nextPosition);
            yapaiPlayerId = positionPlayerIdMap.get(nextPosition);
        }
        ShuangkouPlayer yapaiPlayer = shuangkouPlayerIdPlayerMap.get(yapaiPlayerId);
        if (chuifeng) {// 吹风
            nextPosition = PositionUtil.nextPositionClockwise(actionPosition);
            yapaiPlayerId = positionPlayerIdMap.get(nextPosition);
            while (yapaiPlayerId == null
                    || !ifPlayerHasPai(yapaiPlayerId) && !yapaiPlayerId.equals(latestDapaiPlayerId)) {
                nextPosition = PositionUtil.nextPositionClockwise(nextPosition);
                yapaiPlayerId = positionPlayerIdMap.get(nextPosition);
            }
            String playerId = noPaiPlayerIdList.get(noPaiPlayerIdList.size() - 1);
            if (playerId.equals(yapaiPlayerId)) {
                yapaiPlayer = findDuijiaPlayer(playerId);
            }
        }
        return yapaiPlayer;
    }

    public ShuangkouPlayer findPlayer(String dapaiPlayerId) {
        return shuangkouPlayerIdPlayerMap.get(dapaiPlayerId);
    }

    /**
     * 获取玩家对家玩家
     *
     * @param playerId 玩家ID
     */
    public ShuangkouPlayer findDuijiaPlayer(String playerId) {
        ShuangkouPlayer player = shuangkouPlayerIdPlayerMap.get(playerId);
        if (player == null) {
            return null;
        }
        Position nextPosition = PositionUtil.nextPositionClockwise(player.getPosition());
        nextPosition = PositionUtil.nextPositionClockwise(nextPosition);
        String duijiaPlayerId = positionPlayerIdMap.get(nextPosition);
        if (duijiaPlayerId == null) {
            return null;
        }
        return shuangkouPlayerIdPlayerMap.get(duijiaPlayerId);
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
        ShuangkouPlayer yapaiPlayer = shuangkouPlayerIdPlayerMap.get(yapaiPlayerId);
        if (chuifeng) {// 吹风
            nextPosition = PositionUtil.nextPositionClockwise(actionPosition);
            yapaiPlayerId = positionPlayerIdMap.get(nextPosition);
            while (yapaiPlayerId == null
                    || !ifPlayerHasPai(yapaiPlayerId) && !yapaiPlayerId.equals(latestDapaiPlayerId)) {
                nextPosition = PositionUtil.nextPositionClockwise(nextPosition);
                yapaiPlayerId = positionPlayerIdMap.get(nextPosition);
            }
            String playerId = noPaiPlayerIdList.get(noPaiPlayerIdList.size() - 1);
            if (playerId.equals(yapaiPlayerId)) {
                return true;
            }
            return false;
        } else {
            return yapaiPlayer.getId().equals(latestDapaiPlayerId);
        }
    }

    public void clearTonghuashunSolution() {
        for (ShuangkouPlayer player : shuangkouPlayerIdPlayerMap.values()) {
            player.getTonghuashunSolutionList().clear();
        }
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Map<String, ShuangkouPlayer> getShuangkouPlayerIdPlayerMap() {
        return shuangkouPlayerIdPlayerMap;
    }

    public void setShuangkouPlayerIdPlayerMap(Map<String, ShuangkouPlayer> shuangkouPlayerIdPlayerMap) {
        this.shuangkouPlayerIdPlayerMap = shuangkouPlayerIdPlayerMap;
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

    public boolean isChuifeng() {
        return chuifeng;
    }

    public void setChuifeng(boolean chuifeng) {
        this.chuifeng = chuifeng;
    }

}
