package com.dml.shuangkou.ju;

import java.util.*;

import com.dml.puke.wanfa.dianshu.dianshuzu.DanGeZhadanDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.ShunziDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.TonghuashunDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.comparator.CanNotCompareException;
import com.dml.puke.wanfa.dianshu.dianshuzu.comparator.LianXuDianShuZuComparator;
import com.dml.puke.wanfa.dianshu.dianshuzu.comparator.TongDengLianXuDianShuZuComparator;
import com.dml.puke.wanfa.dianshu.dianshuzu.comparator.TonghuashunDianShuZuComparator;
import com.dml.puke.wanfa.dianshu.paizu.DianShuZuPaiZu;
import com.dml.shuangkou.gameprocess.CurrentPanFinishiDeterminer;
import com.dml.shuangkou.gameprocess.JuFinishiDeterminer;
import com.dml.shuangkou.pai.dianshuzu.WangZhadanDianShuZu;
import com.dml.shuangkou.pai.waihao.WaihaoGenerator;
import com.dml.shuangkou.pan.CurrentPanResultBuilder;
import com.dml.shuangkou.pan.Pan;
import com.dml.shuangkou.pan.PanActionFrame;
import com.dml.shuangkou.pan.PanResult;
import com.dml.shuangkou.pan.PanValueObject;
import com.dml.shuangkou.player.ShuangkouPlayer;
import com.dml.shuangkou.player.action.ActionStatisticsListenerManager;
import com.dml.shuangkou.player.action.da.AllKedaPaiSolutionsGenerator;
import com.dml.shuangkou.player.action.da.DaAction;
import com.dml.shuangkou.player.action.da.DaActionStatisticsListener;
import com.dml.shuangkou.player.action.da.KedaPaiSolutionsForTipsGenerator;
import com.dml.shuangkou.player.action.da.KeyaDaPaiDianShuSolutionsGenerator;
import com.dml.shuangkou.player.action.da.YaPaiSolutionsTipsFilter;
import com.dml.shuangkou.player.action.da.solution.DaPaiDianShuSolution;
import com.dml.shuangkou.player.action.da.solution.DianShuZuYaPaiSolutionCalculator;
import com.dml.shuangkou.player.action.da.solution.ZaDanYaPaiSolutionCalculator;
import com.dml.shuangkou.player.action.guo.GuoAction;
import com.dml.shuangkou.player.action.guo.GuoActionStatisticsListener;
import com.dml.shuangkou.preparedapai.avaliablepai.AvaliablePaiFiller;
import com.dml.shuangkou.preparedapai.fapai.FapaiStrategy;
import com.dml.shuangkou.preparedapai.lipai.ShoupaiSortStrategy;
import com.dml.shuangkou.preparedapai.luanpai.LuanpaiStrategy;
import com.dml.shuangkou.preparedapai.xianda.XiandaPlayerDeterminer;
import com.dml.shuangkou.preparedapai.zudui.ZuduiStrategy;

public class Ju {

    private Pan currentPan;
    private List<PanResult> finishedPanResultList = new ArrayList<>();
    private JuResult juResult;
    private Map<String, String> depositPlayerList = new HashMap<>();

    private CurrentPanFinishiDeterminer currentPanFinishiDeterminer;
    private JuFinishiDeterminer juFinishiDeterminer;

    private AvaliablePaiFiller avaliablePaiFiller;
    private LuanpaiStrategy luanpaiStrategyForFirstPan;
    private LuanpaiStrategy luanpaiStrategyForNextPan;
    private FapaiStrategy fapaiStrategyForFirstPan;
    private FapaiStrategy fapaiStrategyForNextPan;
    private ShoupaiSortStrategy shoupaiSortStrategy;
    private ZuduiStrategy zuduiStrategyForFirstPan;
    private ZuduiStrategy zuduiStrategyForNextPan;
    private XiandaPlayerDeterminer xiandaPlayerDeterminer;
    private KeyaDaPaiDianShuSolutionsGenerator keyaDaPaiDianShuSolutionsGenerator;
    private YaPaiSolutionsTipsFilter yaPaiSolutionsTipsFilter;
    private AllKedaPaiSolutionsGenerator allKedaPaiSolutionsGenerator;
    private KedaPaiSolutionsForTipsGenerator kedaPaiSolutionsForTipsGenerator;

    private WaihaoGenerator waihaoGenerator;

    private ActionStatisticsListenerManager actionStatisticsListenerManager = new ActionStatisticsListenerManager();

    private CurrentPanResultBuilder currentPanResultBuilder;
    private JuResultBuilder juResultBuilder;

    private DianShuZuYaPaiSolutionCalculator dianShuZuYaPaiSolutionCalculator;
    private ZaDanYaPaiSolutionCalculator zaDanYaPaiSolutionCalculator;

    /**
     * 进园子光头玩家集合
     */
    private Set<String> gaungtouPlayers = new HashSet<>();

    TonghuashunDianShuZuComparator lianXuDianShuZuComparator = new TonghuashunDianShuZuComparator();

    public void addDaListener(DaActionStatisticsListener daActionStatisticsListener) {
        actionStatisticsListenerManager.addDaListener(daActionStatisticsListener);
    }

    public void addGuoListener(GuoActionStatisticsListener guoActionStatisticsListener) {
        actionStatisticsListenerManager.addGuoListener(guoActionStatisticsListener);
    }

    public void startFirstPan(List<String> allPlayerIds, long startTime) throws Exception {
        currentPan = new Pan();
        currentPan.setNo(1);
        allPlayerIds.forEach((pid) -> currentPan.addPlayer(pid));
        avaliablePaiFiller.fillAvaliablePai(this);
        // 先乱牌，再发牌，再理牌，再组队
        luanpaiStrategyForFirstPan.luanpai(this);
        fapaiStrategyForFirstPan.fapai(this);
        currentPan.getShuangkouPlayerIdPlayerMap().values().forEach((player) -> player.lipai(shoupaiSortStrategy));
        zuduiStrategyForFirstPan.zudui(this);
        // 谁第一个打牌
        String dapaiPlayerId = xiandaPlayerDeterminer.determineXiandaPlayer(this);
        ShuangkouPlayer player = currentPan.findPlayer(dapaiPlayerId);
        player.putYaPaiSolutionCandidates(allKedaPaiSolutionsGenerator.generateAllKedaPaiSolutions(player.getAllShoupai()));

        player.getTonghuashunSolutionList().putAll(player.calculateTonghuashunSolution());//添加同花顺出牌方案

        // 提示
        player.generateNotYaPaiSolutionsForTips(yaPaiSolutionsTipsFilter);
        // 滑动提示
        // player.generateDaPaiSolutionsForTips(kedaPaiSolutionsForTipsGenerator);
        currentPan.updateActionPositionByActionPlayer(dapaiPlayerId);
        currentPan.addFrame(new PanActionFrame(null, new PanValueObject(currentPan), startTime));

    }

    public void startNextPan() throws Exception {
        actionStatisticsListenerManager.updateListenersForNextPan();
        currentPan = new Pan();
        currentPan.setNo(countFinishedPan() + 1);
        PanResult latestFinishedPanResult = findLatestFinishedPanResult();
        List<String> allPlayerIds = latestFinishedPanResult.allPlayerIds();
        allPlayerIds.forEach((pid) -> currentPan.addPlayer(pid));
        avaliablePaiFiller.fillAvaliablePai(this);
        // 先乱牌，再发牌，再理牌，再组队
        luanpaiStrategyForNextPan.luanpai(this);
        fapaiStrategyForNextPan.fapai(this);
        currentPan.getShuangkouPlayerIdPlayerMap().values().forEach((player) -> player.lipai(shoupaiSortStrategy));
        zuduiStrategyForNextPan.zudui(this);
        // 谁第一个打牌
        String dapaiPlayerId = xiandaPlayerDeterminer.determineXiandaPlayer(this);
        ShuangkouPlayer player = currentPan.findPlayer(dapaiPlayerId);
        player.putYaPaiSolutionCandidates(allKedaPaiSolutionsGenerator.generateAllKedaPaiSolutions(player.getAllShoupai()));

        player.getTonghuashunSolutionList().putAll(player.calculateTonghuashunSolution());//添加同花顺出牌方案

        // 提示
        player.generateNotYaPaiSolutionsForTips(yaPaiSolutionsTipsFilter);
        // 滑动提示
        // player.generateDaPaiSolutionsForTips(kedaPaiSolutionsForTipsGenerator);
        currentPan.updateActionPositionByActionPlayer(dapaiPlayerId);
        currentPan.addFrame(new PanActionFrame(null, new PanValueObject(currentPan), System.currentTimeMillis()));
    }

    public PanActionFrame da(String playerId, List<Integer> paiIds, String dianshuZuheIdx, long actionTime) throws Exception {
        DaAction daAction = currentPan.da(playerId, paiIds, dianshuZuheIdx, waihaoGenerator);
        // 理牌每次要理的
        currentPan.findPlayer(playerId).lipai(shoupaiSortStrategy);
        actionStatisticsListenerManager.updateDaActionListener(daAction, this);
        currentPan.clearTonghuashunSolution();//清理同花顺出牌方案
        if (currentPanFinishiDeterminer.determineToFinishCurrentPan(this)) {// 是否盘结束
            PanResult panResult = currentPanResultBuilder.buildCurrentPanResult(this, actionTime);
            finishedPanResultList.add(panResult);
            PanActionFrame panActionFrame = currentPan.recordPanActionFrame(daAction, actionTime);
            int PlayerCount = currentPan.getShuangkouPlayerIdPlayerMap().size();
            currentPan = null;
            if (juFinishiDeterminer.determineToFinishJu(this) || guangtouOver(PlayerCount)) {// 是否局结束
                juResult = juResultBuilder.buildJuResult(this);
            }
            return panActionFrame;
        } else {
            // 生成下家的候选方案。
            currentPan.updateNextPlayersDaSolution(dianShuZuYaPaiSolutionCalculator, zaDanYaPaiSolutionCalculator);

            DianShuZu dianShuZu = daAction.getDachuPaiZu().getDianShuZu();
            ShuangkouPlayer nextActionPlayer = currentPan.findNextActionPlayer();

            if (dianShuZu instanceof DanGeZhadanDianShuZu) {
                DanGeZhadanDianShuZu danGeZhadanDianShuZu = (DanGeZhadanDianShuZu) dianShuZu;
                if (danGeZhadanDianShuZu.getSize() <= 5) {
                    nextActionPlayer.getTonghuashunSolutionList().putAll(nextActionPlayer.calculateTonghuashunSolution());//如果被压牌小于5张的炸弹，添加同花顺出牌方案
                }
            } else if (dianShuZu instanceof TonghuashunDianShuZu) {
                TonghuashunDianShuZu beiyaDianshuzu = (TonghuashunDianShuZu) dianShuZu;
                Map<String, List<DaPaiDianShuSolution>> tonghuashunSolution = nextActionPlayer.calculateTonghuashunSolution();
                Map<String, List<DaPaiDianShuSolution>> tonghuashunSolution2 = new HashMap<>();
                for (Map.Entry<String, List<DaPaiDianShuSolution>> huaseTonghuashun : tonghuashunSolution.entrySet()) {
                    String huase = huaseTonghuashun.getKey();
                    List<DaPaiDianShuSolution> daPaiDianShuSolutions = new ArrayList<>();
                    for (DaPaiDianShuSolution daPaiDianShuSolution : huaseTonghuashun.getValue()) {
                        TonghuashunDianShuZu tonghuashunDianShuZu = (TonghuashunDianShuZu) daPaiDianShuSolution.getDianShuZu();
                        if (lianXuDianShuZuComparator.compare(tonghuashunDianShuZu, beiyaDianshuzu) > 0) {
                            daPaiDianShuSolutions.add(daPaiDianShuSolution);
                        }
                    }
                    tonghuashunSolution2.put(huase, daPaiDianShuSolutions);
                }
                nextActionPlayer.getTonghuashunSolutionList().putAll(tonghuashunSolution2);//如果被压牌是同花顺，添加同花顺出牌方案
            } else if (dianShuZu instanceof WangZhadanDianShuZu) {  //同花顺不能压王炸

            } else {
                nextActionPlayer.getTonghuashunSolutionList().putAll(nextActionPlayer.calculateTonghuashunSolution());//添加同花顺出牌方案
            }

            //可压提示过滤
            currentPan.generateYaPaiSolutionsForTips(yaPaiSolutionsTipsFilter);
            //划起提示
            // currentPan.generateDaPaiSolutionsForTips(kedaPaiSolutionsForTipsGenerator);
            currentPan.updateActionPositionToNextPlayer();
            return currentPan.recordPanActionFrame(daAction, actionTime);
        }

    }

    public PanActionFrame guo(String playerId, long actionTime) throws Exception {
        GuoAction guoAction = currentPan.guo(playerId);
        actionStatisticsListenerManager.updateGuoActionListener(guoAction, this);

        currentPan.clearTonghuashunSolution();//清理同花顺出牌方案

        // 看下一人是否是最后出牌人
        if (currentPan.ifStartYapai()) {// 下一人是最后出牌人
            ShuangkouPlayer nextPlayer = currentPan.findNextActionPlayer();
            nextPlayer.putYaPaiSolutionCandidates(allKedaPaiSolutionsGenerator.generateAllKedaPaiSolutions(nextPlayer.getAllShoupai()));

            nextPlayer.getTonghuashunSolutionList().putAll(nextPlayer.calculateTonghuashunSolution());//添加同花顺出牌方案

            //可压提示过滤
            nextPlayer.generateNotYaPaiSolutionsForTips(yaPaiSolutionsTipsFilter);
            // 划起提示
            // nextPlayer.generateDaPaiSolutionsForTips(kedaPaiSolutionsForTipsGenerator);
            // currentPan.setChuifeng(false);
            currentPan.updateActionPositionToNextPlayer();
            currentPan.setLatestDapaiPlayerId(null);
        } else {
            //生成下家的候选方案。
            currentPan.updateNextPlayersDaSolution(dianShuZuYaPaiSolutionCalculator, zaDanYaPaiSolutionCalculator);
            ShuangkouPlayer nextActionPlayer = currentPan.findNextActionPlayer();

//            nextActionPlayer.getTonghuashunSolutionList().putAll(nextActionPlayer.calculateTonghuashunSolution());//添加同花顺出牌方案
            String latestDapaiPlayerId = currentPan.getLatestDapaiPlayerId();
            if (latestDapaiPlayerId != null) {
                ShuangkouPlayer dachuPlayer = currentPan.getShuangkouPlayerIdPlayerMap().get(latestDapaiPlayerId);
                if (dachuPlayer != null) {
                    DianShuZu dianShuZu = dachuPlayer.getPublicDachuPaiZu().getDianShuZu();
                    if (dianShuZu instanceof DanGeZhadanDianShuZu) {
                        DanGeZhadanDianShuZu danGeZhadanDianShuZu = (DanGeZhadanDianShuZu) dianShuZu;
                        if (danGeZhadanDianShuZu.getSize() <= 5) {
                            nextActionPlayer.getTonghuashunSolutionList().putAll(nextActionPlayer.calculateTonghuashunSolution());//如果被压牌小于5张的炸弹，添加同花顺出牌方案
                        }
                    } else if (dianShuZu instanceof TonghuashunDianShuZu) {
                        TonghuashunDianShuZu beiyaDianshuzu = (TonghuashunDianShuZu) dianShuZu;
                        Map<String, List<DaPaiDianShuSolution>> tonghuashunSolution = nextActionPlayer.calculateTonghuashunSolution();
                        Map<String, List<DaPaiDianShuSolution>> tonghuashunSolution2 = new HashMap<>();
                        for (Map.Entry<String, List<DaPaiDianShuSolution>> huaseTonghuashun : tonghuashunSolution.entrySet()) {
                            String huase = huaseTonghuashun.getKey();
                            List<DaPaiDianShuSolution> daPaiDianShuSolutions = new ArrayList<>();
                            for (DaPaiDianShuSolution daPaiDianShuSolution : huaseTonghuashun.getValue()) {
                                TonghuashunDianShuZu tonghuashunDianShuZu = (TonghuashunDianShuZu) daPaiDianShuSolution.getDianShuZu();
                                if (lianXuDianShuZuComparator.compare(tonghuashunDianShuZu, beiyaDianshuzu) > 0) {
                                    daPaiDianShuSolutions.add(daPaiDianShuSolution);
                                }
                            }
                            tonghuashunSolution2.put(huase, daPaiDianShuSolutions);
                        }
                        nextActionPlayer.getTonghuashunSolutionList().putAll(tonghuashunSolution2);//如果被压牌是同花顺，添加同花顺出牌方案
                    } else if (dianShuZu instanceof WangZhadanDianShuZu) {  //同花顺不能压王炸

                    } else {
                        nextActionPlayer.getTonghuashunSolutionList().putAll(nextActionPlayer.calculateTonghuashunSolution());//添加同花顺出牌方案
                    }
                }
            }

            //可压提示过滤
            currentPan.generateYaPaiSolutionsForTips(yaPaiSolutionsTipsFilter);
            //划起提示
            // currentPan.generateDaPaiSolutionsForTips(kedaPaiSolutionsForTipsGenerator);
            currentPan.updateActionPositionToNextPlayer();
        }
        return currentPan.recordPanActionFrame(guoAction, actionTime);
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

    public boolean guangtouOver(int PlayerCount) {
        int guangtouPlayerCount = gaungtouPlayers.size();
        if (PlayerCount == 2 && guangtouPlayerCount >= 1) {
            return true;
        } else return guangtouPlayerCount >= 2;
    }

    public Pan getCurrentPan() {
        return currentPan;
    }

    public void setCurrentPan(Pan currentPan) {
        this.currentPan = currentPan;
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

    public FapaiStrategy getFapaiStrategyForFirstPan() {
        return fapaiStrategyForFirstPan;
    }

    public void setFapaiStrategyForFirstPan(FapaiStrategy fapaiStrategyForFirstPan) {
        this.fapaiStrategyForFirstPan = fapaiStrategyForFirstPan;
    }

    public ShoupaiSortStrategy getShoupaiSortStrategy() {
        return shoupaiSortStrategy;
    }

    public void setShoupaiSortStrategy(ShoupaiSortStrategy shoupaiSortStrategy) {
        this.shoupaiSortStrategy = shoupaiSortStrategy;
    }

    public ZuduiStrategy getZuduiStrategyForFirstPan() {
        return zuduiStrategyForFirstPan;
    }

    public void setZuduiStrategyForFirstPan(ZuduiStrategy zuduiStrategyForFirstPan) {
        this.zuduiStrategyForFirstPan = zuduiStrategyForFirstPan;
    }

    public XiandaPlayerDeterminer getXiandaPlayerDeterminer() {
        return xiandaPlayerDeterminer;
    }

    public void setXiandaPlayerDeterminer(XiandaPlayerDeterminer xiandaPlayerDeterminer) {
        this.xiandaPlayerDeterminer = xiandaPlayerDeterminer;
    }

    public KeyaDaPaiDianShuSolutionsGenerator getKeyaDaPaiDianShuSolutionsGenerator() {
        return keyaDaPaiDianShuSolutionsGenerator;
    }

    public void setKeyaDaPaiDianShuSolutionsGenerator(
            KeyaDaPaiDianShuSolutionsGenerator keyaDaPaiDianShuSolutionsGenerator) {
        this.keyaDaPaiDianShuSolutionsGenerator = keyaDaPaiDianShuSolutionsGenerator;
    }

    public YaPaiSolutionsTipsFilter getYaPaiSolutionsTipsFilter() {
        return yaPaiSolutionsTipsFilter;
    }

    public void setYaPaiSolutionsTipsFilter(YaPaiSolutionsTipsFilter yaPaiSolutionsTipsFilter) {
        this.yaPaiSolutionsTipsFilter = yaPaiSolutionsTipsFilter;
    }

    public AllKedaPaiSolutionsGenerator getAllKedaPaiSolutionsGenerator() {
        return allKedaPaiSolutionsGenerator;
    }

    public void setAllKedaPaiSolutionsGenerator(AllKedaPaiSolutionsGenerator allKedaPaiSolutionsGenerator) {
        this.allKedaPaiSolutionsGenerator = allKedaPaiSolutionsGenerator;
    }

    public KedaPaiSolutionsForTipsGenerator getKedaPaiSolutionsForTipsGenerator() {
        return kedaPaiSolutionsForTipsGenerator;
    }

    public void setKedaPaiSolutionsForTipsGenerator(KedaPaiSolutionsForTipsGenerator kedaPaiSolutionsForTipsGenerator) {
        this.kedaPaiSolutionsForTipsGenerator = kedaPaiSolutionsForTipsGenerator;
    }

    public WaihaoGenerator getWaihaoGenerator() {
        return waihaoGenerator;
    }

    public void setWaihaoGenerator(WaihaoGenerator waihaoGenerator) {
        this.waihaoGenerator = waihaoGenerator;
    }

    public ActionStatisticsListenerManager getActionStatisticsListenerManager() {
        return actionStatisticsListenerManager;
    }

    public void setActionStatisticsListenerManager(ActionStatisticsListenerManager actionStatisticsListenerManager) {
        this.actionStatisticsListenerManager = actionStatisticsListenerManager;
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

    public DianShuZuYaPaiSolutionCalculator getDianShuZuYaPaiSolutionCalculator() {
        return dianShuZuYaPaiSolutionCalculator;
    }

    public void setDianShuZuYaPaiSolutionCalculator(DianShuZuYaPaiSolutionCalculator dianShuZuYaPaiSolutionCalculator) {
        this.dianShuZuYaPaiSolutionCalculator = dianShuZuYaPaiSolutionCalculator;
    }

    public ZaDanYaPaiSolutionCalculator getZaDanYaPaiSolutionCalculator() {
        return zaDanYaPaiSolutionCalculator;
    }

    public void setZaDanYaPaiSolutionCalculator(ZaDanYaPaiSolutionCalculator zaDanYaPaiSolutionCalculator) {
        this.zaDanYaPaiSolutionCalculator = zaDanYaPaiSolutionCalculator;
    }

    public LuanpaiStrategy getLuanpaiStrategyForNextPan() {
        return luanpaiStrategyForNextPan;
    }

    public void setLuanpaiStrategyForNextPan(LuanpaiStrategy luanpaiStrategyForNextPan) {
        this.luanpaiStrategyForNextPan = luanpaiStrategyForNextPan;
    }

    public FapaiStrategy getFapaiStrategyForNextPan() {
        return fapaiStrategyForNextPan;
    }

    public void setFapaiStrategyForNextPan(FapaiStrategy fapaiStrategyForNextPan) {
        this.fapaiStrategyForNextPan = fapaiStrategyForNextPan;
    }

    public ZuduiStrategy getZuduiStrategyForNextPan() {
        return zuduiStrategyForNextPan;
    }

    public void setZuduiStrategyForNextPan(ZuduiStrategy zuduiStrategyForNextPan) {
        this.zuduiStrategyForNextPan = zuduiStrategyForNextPan;
    }

    public Map<String, String> getDepositPlayerList() {
        return depositPlayerList;
    }

    public void setDepositPlayerList(Map<String, String> depositPlayerList) {
        this.depositPlayerList = depositPlayerList;
    }

    public Set<String> getGaungtouPlayers() {
        return gaungtouPlayers;
    }

    public void setGaungtouPlayers(Set<String> gaungtouPlayers) {
        this.gaungtouPlayers = gaungtouPlayers;
    }
}
