package com.dml.shisanshui.ju;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.dml.shisanshui.pai.paixing.comparator.DaoComparator;
import com.dml.shisanshui.player.ShisanshuiPlayer;
import com.dml.shisanshui.preparedapai.avaliablepai.AvaliablePaiFiller;
import com.dml.shisanshui.preparedapai.lipai.ShoupaiSortStrategy;
import com.dml.shisanshui.preparedapai.luanpai.LuanpaiStrategy;
import com.dml.shisanshui.preparedapai.zuowei.ZuoweiDeterminer;
import com.dml.shisanshui.gameprocess.CurrentPanFinishiDeterminer;
import com.dml.shisanshui.gameprocess.JuFinishiDeterminer;
import com.dml.shisanshui.pai.paixing.Dao;
import com.dml.shisanshui.pan.CurrentPanResultBuilder;
import com.dml.shisanshui.pan.Pan;
import com.dml.shisanshui.pan.PanActionFrame;
import com.dml.shisanshui.pan.PanResult;
import com.dml.shisanshui.player.action.ChupaiDaoCalculator;
import com.dml.shisanshui.player.action.ChupaiPaixingSolutionFilter;
import com.dml.shisanshui.player.action.ShisanshuiChupaiAction;
import com.dml.shisanshui.preparedapai.fapai.FapaiStrategy;

public class Ju {
    private Pan currentPan;
    private List<PanResult> finishedPanResultList = new ArrayList<>();
    private Map<String, String> depositPlayerList = new HashMap<>();
    private JuResult juResult;
    private CurrentPanFinishiDeterminer currentPanFinishiDeterminer;
    private JuFinishiDeterminer juFinishiDeterminer;
    private ZuoweiDeterminer zuoweiDeterminer;
    private AvaliablePaiFiller avaliablePaiFiller;
    private ShoupaiSortStrategy shoupaiSortStrategy;
    private LuanpaiStrategy luanpaiStrategyForFirstPan;
    private LuanpaiStrategy luanpaiStrategyForNextPan;
    private FapaiStrategy fapaiStrategyForFirstPan;
    private FapaiStrategy fapaiStrategyForNextPan;
    private DaoComparator daoComparator;
    private ChupaiDaoCalculator chupaiDaoCalculator;
    private ChupaiPaixingSolutionFilter chupaiPaixingSolutionFilter;
    private CurrentPanResultBuilder currentPanResultBuilder;
    private JuResultBuilder juResultBuilder;

    public Dao findDaoByPlayerIdAndIndex(String playerId, String index) throws Exception {
        return currentPan.findDaoByPlayerIdAndIndex(playerId, index);
    }

    public PanActionFrame chupai(String playerId, String toudaoIndex, String zhongdaoIndex, String weidaoIndex, long actionTime, boolean jinyuanzi) throws Exception {
        ShisanshuiChupaiAction action = currentPan.chupai(playerId, toudaoIndex, zhongdaoIndex, weidaoIndex, daoComparator);
        if (currentPanFinishiDeterminer.determineToFinishCurrentPan(this)) {    // 是否盘结束
            PanResult panResult = currentPanResultBuilder.buildCurrentPanResult(this, actionTime);
            finishedPanResultList.add(panResult);
            PanActionFrame panActionFrame = currentPan.recordPanActionFrame(action, actionTime);

            //全光结束游戏（进园子模式下只有一个人赢分，其余人都是0分）
            int guangtouPlayerCount = 0;
            if (jinyuanzi) {
                for (ShisanshuiPlayer player : currentPan.getPlayerIdPlayerMap().values()) {
                    if (player.getPlayerTotalScore() <= 0) {
                        guangtouPlayerCount++;
                    }
                }
            }
            int playerCount = currentPan.getPlayerIdPlayerMap().size();

            currentPan = null;
            if (juFinishiDeterminer.determineToFinishJu(this) || playerCount - 1 == guangtouPlayerCount) {    // 是否局结束
                juResult = juResultBuilder.buildJuResult(this);
            }
            return panActionFrame;
        } else {
            return currentPan.recordPanActionFrame(action, actionTime);
        }
    }

    public PanActionFrame autoChupai(String playerId, Long actionTime, boolean jinyuanzi) throws Exception {
        ShisanshuiChupaiAction action = currentPan.autoChupai(playerId, daoComparator);
        if (currentPanFinishiDeterminer.determineToFinishCurrentPan(this)) {    // 是否盘结束
            PanResult panResult = currentPanResultBuilder.buildCurrentPanResult(this, actionTime);
            finishedPanResultList.add(panResult);
            PanActionFrame panActionFrame = currentPan.recordPanActionFrame(action, actionTime);

            //全光结束游戏（进园子模式下只有一个人赢分，其余人都是0分）
            int guangtouPlayerCount = 0;
            if (jinyuanzi) {
                for (ShisanshuiPlayer player : currentPan.getPlayerIdPlayerMap().values()) {
                    if (player.getPlayerTotalScore() <= 0) {
                        guangtouPlayerCount++;
                    }
                }
            }
            int playerCount = currentPan.getPlayerIdPlayerMap().size();

            currentPan = null;
            if (juFinishiDeterminer.determineToFinishJu(this) || playerCount - 1 == guangtouPlayerCount) {// 是否局结束
                juResult = juResultBuilder.buildJuResult(this);
            }
            return panActionFrame;
        } else {
            return currentPan.recordPanActionFrame(action, actionTime);
        }
    }

    public PanActionFrame startFirstPan(List<String> allPlayerIds, long startTime) throws Exception {
        currentPan = new Pan();
        currentPan.setNo(1);
        allPlayerIds.forEach((pid) -> currentPan.addPlayer(pid));
        zuoweiDeterminer.determineZuowei(this);
        avaliablePaiFiller.fillAvaliablePai(this);                                                        //可用牌
        luanpaiStrategyForFirstPan.luanpai(this);                                                         //乱牌
        fapaiStrategyForFirstPan.fapai(this);                                                             //发牌
        currentPan.getPlayerIdPlayerMap().values().forEach((player) -> player.lipai(shoupaiSortStrategy));   //理牌
        currentPan.generateAllChupaiPaixingSolution(chupaiDaoCalculator);                                    //生成所有合理的出牌方案
        currentPan.generateChupaiPaixingSolutionsForTips(chupaiPaixingSolutionFilter);                       //出牌提示
        return currentPan.recordPanActionFrame(null, startTime);

    }

    public void startNextPan() throws Exception {
        currentPan = new Pan();
        currentPan.setNo(countFinishedPan() + 1);
        PanResult latestFinishedPanResult = findLatestFinishedPanResult();
        List<String> allPlayerIds = latestFinishedPanResult.allPlayerIds();
        allPlayerIds.forEach((pid) -> currentPan.addPlayer(pid));
        zuoweiDeterminer.determineZuowei(this);
        avaliablePaiFiller.fillAvaliablePai(this);
        luanpaiStrategyForNextPan.luanpai(this);                                                         //乱牌
        fapaiStrategyForNextPan.fapai(this);                                                             //发牌
        currentPan.getPlayerIdPlayerMap().values().forEach((player) -> player.lipai(shoupaiSortStrategy));  //理牌
        currentPan.generateAllChupaiPaixingSolution(chupaiDaoCalculator);                                   //生成所有合理的出牌方案
        currentPan.generateChupaiPaixingSolutionsForTips(chupaiPaixingSolutionFilter);                      //出牌提示
        currentPan.recordPanActionFrame(null, System.currentTimeMillis());
    }

    public void finish() {
        juResult = juResultBuilder.buildJuResult(this);
    }

    public int countFinishedPan() {
        return finishedPanResultList.size();
    }

    public PanResult findLatestFinishedPanResult() {
        if (!finishedPanResultList.isEmpty()) {
            return finishedPanResultList.get(finishedPanResultList.size() - 1);
        } else {
            return null;
        }
    }

    public Pan getCurrentPan() {
        return currentPan;
    }

    public void setCurrentPan(Pan currentPan) {
        this.currentPan = currentPan;
    }

    public List<PanResult> getFinishedPanResultList() {
        return finishedPanResultList;
    }

    public void setFinishedPanResultList(List<PanResult> finishedPanResultList) {
        this.finishedPanResultList = finishedPanResultList;
    }

    public JuResult getJuResult() {
        return juResult;
    }

    public void setJuResult(JuResult juResult) {
        this.juResult = juResult;
    }

    public CurrentPanFinishiDeterminer getCurrentPanFinishiDeterminer() {
        return currentPanFinishiDeterminer;
    }

    public void setCurrentPanFinishiDeterminer(CurrentPanFinishiDeterminer currentPanFinishiDeterminer) {
        this.currentPanFinishiDeterminer = currentPanFinishiDeterminer;
    }

    public JuFinishiDeterminer getJuFinishiDeterminer() {
        return juFinishiDeterminer;
    }

    public void setJuFinishiDeterminer(JuFinishiDeterminer juFinishiDeterminer) {
        this.juFinishiDeterminer = juFinishiDeterminer;
    }

    public AvaliablePaiFiller getAvaliablePaiFiller() {
        return avaliablePaiFiller;
    }

    public void setAvaliablePaiFiller(AvaliablePaiFiller avaliablePaiFiller) {
        this.avaliablePaiFiller = avaliablePaiFiller;
    }

    public LuanpaiStrategy getLuanpaiStrategyForFirstPan() {
        return luanpaiStrategyForFirstPan;
    }

    public void setLuanpaiStrategyForFirstPan(LuanpaiStrategy luanpaiStrategyForFirstPan) {
        this.luanpaiStrategyForFirstPan = luanpaiStrategyForFirstPan;
    }

    public LuanpaiStrategy getLuanpaiStrategyForNextPan() {
        return luanpaiStrategyForNextPan;
    }

    public void setLuanpaiStrategyForNextPan(LuanpaiStrategy luanpaiStrategyForNextPan) {
        this.luanpaiStrategyForNextPan = luanpaiStrategyForNextPan;
    }

    public FapaiStrategy getFapaiStrategyForFirstPan() {
        return fapaiStrategyForFirstPan;
    }

    public void setFapaiStrategyForFirstPan(FapaiStrategy fapaiStrategyForFirstPan) {
        this.fapaiStrategyForFirstPan = fapaiStrategyForFirstPan;
    }

    public FapaiStrategy getFapaiStrategyForNextPan() {
        return fapaiStrategyForNextPan;
    }

    public void setFapaiStrategyForNextPan(FapaiStrategy fapaiStrategyForNextPan) {
        this.fapaiStrategyForNextPan = fapaiStrategyForNextPan;
    }

    public ChupaiDaoCalculator getChupaiDaoCalculator() {
        return chupaiDaoCalculator;
    }

    public void setChupaiDaoCalculator(ChupaiDaoCalculator chupaiDaoCalculator) {
        this.chupaiDaoCalculator = chupaiDaoCalculator;
    }

    public ChupaiPaixingSolutionFilter getChupaiPaixingSolutionFilter() {
        return chupaiPaixingSolutionFilter;
    }

    public void setChupaiPaixingSolutionFilter(ChupaiPaixingSolutionFilter chupaiPaixingSolutionFilter) {
        this.chupaiPaixingSolutionFilter = chupaiPaixingSolutionFilter;
    }

    public CurrentPanResultBuilder getCurrentPanResultBuilder() {
        return currentPanResultBuilder;
    }

    public void setCurrentPanResultBuilder(CurrentPanResultBuilder currentPanResultBuilder) {
        this.currentPanResultBuilder = currentPanResultBuilder;
    }

    public JuResultBuilder getJuResultBuilder() {
        return juResultBuilder;
    }

    public void setJuResultBuilder(JuResultBuilder juResultBuilder) {
        this.juResultBuilder = juResultBuilder;
    }

    public ZuoweiDeterminer getZuoweiDeterminer() {
        return zuoweiDeterminer;
    }

    public void setZuoweiDeterminer(ZuoweiDeterminer zuoweiDeterminer) {
        this.zuoweiDeterminer = zuoweiDeterminer;
    }

    public DaoComparator getDaoComparator() {
        return daoComparator;
    }

    public void setDaoComparator(DaoComparator daoComparator) {
        this.daoComparator = daoComparator;
    }

    public ShoupaiSortStrategy getShoupaiSortStrategy() {
        return shoupaiSortStrategy;
    }

    public void setShoupaiSortStrategy(ShoupaiSortStrategy shoupaiSortStrategy) {
        this.shoupaiSortStrategy = shoupaiSortStrategy;
    }

    public Map<String, String> getDepositPlayerList() {
        return depositPlayerList;
    }

    public void setDepositPlayerList(Map<String, String> depositPlayerList) {
        this.depositPlayerList = depositPlayerList;
    }
}
