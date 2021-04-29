package com.anbang.qipai.guandan.cqrs.c.domain;

import com.anbang.qipai.guandan.cqrs.c.domain.result.*;
import com.anbang.qipai.guandan.cqrs.c.domain.state.*;
import com.anbang.qipai.guandan.utils.SpringUtil;
import com.dml.mpgame.game.Finished;
import com.dml.mpgame.game.Playing;
import com.dml.mpgame.game.extend.fpmpv.FixedPlayersMultipanAndVotetofinishGame;
import com.dml.mpgame.game.extend.multipan.WaitingNextPan;
import com.dml.mpgame.game.extend.vote.VoteNotPassWhenPlaying;
import com.dml.mpgame.game.player.GamePlayer;
import com.dml.mpgame.game.player.PlayerPlaying;
import com.dml.puke.pai.DianShu;
import com.dml.puke.pai.PukePai;
import com.dml.puke.wanfa.dianshu.dianshuzu.comparator.*;
import com.dml.shuangkou.gameprocess.FixedPanNumbersJuFinishiDeterminer;
import com.dml.shuangkou.gameprocess.OnePlayerHasPaiPanFinishiDeterminer;
import com.dml.shuangkou.ju.Ju;
import com.dml.shuangkou.pai.dianshuzu.DaipaiComparator;
import com.dml.shuangkou.pai.dianshuzu.DianShuZuCalculator;
import com.dml.shuangkou.pai.dianshuzu.PaiXing;
import com.dml.shuangkou.pai.dianshuzu.TongDengDaiPaiComparator;
import com.dml.shuangkou.pai.jiesuanpai.HongxinErDangPai;
import com.dml.shuangkou.pai.jiesuanpai.ShoupaiJiesuanPai;
import com.dml.shuangkou.pai.waihao.ShuangkouWaihaoGenerator;
import com.dml.shuangkou.pan.Pan;
import com.dml.shuangkou.pan.PanActionFrame;
import com.dml.shuangkou.pan.PanResult;
import com.dml.shuangkou.player.ShuangkouPlayer;
import com.dml.shuangkou.preparedapai.avaliablepai.DoubleAvaliablePaiFiller;
import com.dml.shuangkou.preparedapai.fapai.YiciJiuzhangFapaiStrategy;
import com.dml.shuangkou.preparedapai.lipai.DianshuOrPaishuShoupaiSortStrategy;
import com.dml.shuangkou.preparedapai.xianda.HongxinbaXiandaPlayerDeterminer;
import com.dml.shuangkou.wanfa.BianXingWanFa;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PukeGame extends FixedPlayersMultipanAndVotetofinishGame {
    private double difen;
    private int panshu;
    private int renshu;
    private OptionalPlay optionalPlay;
    private int powerLimit;
    private Ju ju;
    private final Map<String, Double> playerTotalScoreMap = new HashMap<>();

    private Map<String, Integer> playerGongxianfenMap = new HashMap<>();
    private final Map<String, Integer> playerGongxianfenDetalMap = new HashMap<>();
    private final Map<String, GuandanGongxianFen> playerTotalGongxianfenMap = new HashMap<>();
    private Map<String, Integer> playerMaxXianshuMap = new HashMap<>();
    private Map<String, Integer> playerOtherMaxXianshuMap = new HashMap<>();
    private Map<String, Integer> playerMingciMap = new HashMap<>();
    private List<String> chaodiPlayerIdList = new ArrayList<>();
    private final Map<String, PukeGamePlayerChaodiState> playerChaodiStateMap = new HashMap<>();

    Automatic automatic = SpringUtil.getBean(Automatic.class);
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * 开始局和开始第一盘
     *
     * @param currentTime 时间戳
     */
    private void createJuAndStartFirstPan(long currentTime) throws Exception {
        ju = new Ju();

        ju.setCurrentPanFinishiDeterminer(new OnePlayerHasPaiPanFinishiDeterminer());   //盘结束条件
        ju.setJuFinishiDeterminer(new FixedPanNumbersJuFinishiDeterminer(panshu));      //局结束条件
        ju.setAvaliablePaiFiller(new DoubleAvaliablePaiFiller());                       //牌库（两副牌）
        ju.setLuanpaiStrategyForFirstPan(new GuandaiRandomLuanpaiStrategy());           //第一盘乱牌策略
        ju.setLuanpaiStrategyForNextPan(new GuandaiRandomLuanpaiStrategy());            //下一盘乱牌策略
        ju.setFapaiStrategyForFirstPan(new YiciJiuzhangFapaiStrategy());                //第一盘发牌
        ju.setFapaiStrategyForNextPan(new YiciJiuzhangFapaiStrategy());                 //下一盘发牌
        ju.setShoupaiSortStrategy(new DianshuOrPaishuShoupaiSortStrategy());            //理牌策略
        ju.setZuduiStrategyForFirstPan(new RandomPaiZuduiStrategy());                   //第一盘组队策略
        ju.setZuduiStrategyForNextPan(new RandomPaiZuduiStrategy());                    //下一盘组队策略
        ju.setXiandaPlayerDeterminer(new HongxinbaXiandaPlayerDeterminer());            //先出牌
        ju.setWaihaoGenerator(new ShuangkouWaihaoGenerator());                          //外号

        GuandanCurrentPanResultBuilder panResultBuilder = new GuandanCurrentPanResultBuilder();
        Map<String, GuandanGongxianFen> totalGongxianfenMap = new HashMap<>();
        panResultBuilder.setPlayerTotalGongxianfenMap(totalGongxianfenMap);
        panResultBuilder.setRenshu(renshu);
//        panResultBuilder.setBx(optionalPlay.getBx());
//        panResultBuilder.setBxfd(optionalPlay.isBxfd());
//        panResultBuilder.setJxfd(optionalPlay.isJxfd());
//        panResultBuilder.setSxfd(optionalPlay.isSxfd());
//        panResultBuilder.setGxjb(optionalPlay.isGxjb());
        panResultBuilder.setDifen(difen);
        panResultBuilder.setOptionalPlay(optionalPlay);
        ju.setCurrentPanResultBuilder(panResultBuilder);        //盘结果
        ju.setJuResultBuilder(new GuandankouJuResultBuilder()); //局结果

//        if (ChaPai.wuxu.equals(optionalPlay.getChapai())) {
//            ju.setLuanpaiStrategyForFirstPan(new ErliuhunHasSiwangLuanpaiStrategy(optionalPlay.getBx(), currentTime));
//            ju.setLuanpaiStrategyForNextPan(new LastPanChuPaiOrdinalLuanpaiStrategy());
//        } else if (ChaPai.erliuhun.equals(optionalPlay.getChapai())) {
//            ju.setLuanpaiStrategyForFirstPan(new ErliuhunHasSiwangLuanpaiStrategy(optionalPlay.getBx(), currentTime));
//            ju.setLuanpaiStrategyForNextPan(new ErliuhunHasSiwangLuanpaiStrategy(optionalPlay.getBx(), currentTime + 1));
//        } else if (ChaPai.sanwu.equals(optionalPlay.getChapai())) {
//            ju.setLuanpaiStrategyForFirstPan(new SanwuHasSiwangLuanpaiStrategy(optionalPlay.getBx(), currentTime));
//            ju.setLuanpaiStrategyForNextPan(new SanwuHasSiwangLuanpaiStrategy(optionalPlay.getBx(), currentTime + 2));
//        } else if (ChaPai.ba.equals(optionalPlay.getChapai())) {
//            ju.setLuanpaiStrategyForFirstPan(new BazhangHasSiwangLuanpaiStrategy(optionalPlay.getBx(), currentTime));
//            ju.setLuanpaiStrategyForNextPan(new BazhangHasSiwangLuanpaiStrategy(optionalPlay.getBx(), currentTime + 3));
//        }
//         ju.setLuanpaiStrategyForFirstPan(new NoZhadanLuanpaiStrategy());
//         ju.setLuanpaiStrategyForNextPan(new NoZhadanLuanpaiStrategy());
//         ju.setFapaiStrategyForFirstPan(new ShiSanZhangFapaiStrategy());
//         ju.setFapaiStrategyForNextPan(new ShiSanZhangFapaiStrategy());
//        if (FaPai.san.equals(optionalPlay.getFapai())) {
//            ju.setFapaiStrategyForFirstPan(new YiciSanzhangFapaiStrategy());
//            ju.setFapaiStrategyForNextPan(new YiciSanzhangFapaiStrategy());
//        } else if (FaPai.sanliu.equals(optionalPlay.getFapai())) {
//            ju.setFapaiStrategyForFirstPan(new YisanSiliuFapaiStrategy());
//            ju.setFapaiStrategyForNextPan(new YisanSiliuFapaiStrategy());
//        } else if (FaPai.liuqi.equals(optionalPlay.getFapai())) {
//            ju.setFapaiStrategyForFirstPan(new YiliuSanqiFapaiStrategy());
//            ju.setFapaiStrategyForNextPan(new YiliuSanqiFapaiStrategy());
//        } else if (FaPai.jiu.equals(optionalPlay.getFapai())) {
//            ju.setFapaiStrategyForFirstPan(new YiciJiuzhangFapaiStrategy());
//            ju.setFapaiStrategyForNextPan(new YiciJiuzhangFapaiStrategy());
//        }

        // ju.setKeyaDaPaiDianShuSolutionsGenerator(keyaDaPaiDianShuSolutionsGenerator);

        DanGeDianShuZuComparator danGeDianShuZuComparator = new NoZhadanDanGeDianShuZuComparator();
        LianXuDianShuZuComparator lianXuDianShuZuComparator = new TongDengLianXuDianShuZuComparator();
        DaipaiComparator daipaiComparator = new TongDengDaiPaiComparator();
        ZhadanComparator zhadanComparator = new GuandanZhadanComparator();

        GuandanAllKedaPaiSolutionsGenerator GuandanAllKedaPaiSolutionsGenerator = new GuandanAllKedaPaiSolutionsGenerator();
        GuandanAllKedaPaiSolutionsGenerator.setBx(optionalPlay.getBx());
        GuandanAllKedaPaiSolutionsGenerator.setLianXuDianShuZuComparator(lianXuDianShuZuComparator);
        GuandanAllKedaPaiSolutionsGenerator.setZhadanComparator(zhadanComparator);
        ju.setAllKedaPaiSolutionsGenerator(GuandanAllKedaPaiSolutionsGenerator);//可打牌方案

        GuandanYaPaiSolutionsTipsFilter GuandanYaPaiSolutionsTipsFilter = new GuandanYaPaiSolutionsTipsFilter();
        GuandanYaPaiSolutionsTipsFilter.setZhadanComparator(zhadanComparator);
        GuandanYaPaiSolutionsTipsFilter.setBx(optionalPlay.getBx());
        ju.setYaPaiSolutionsTipsFilter(GuandanYaPaiSolutionsTipsFilter);//压牌提示

        GuandanDianShuZuYaPaiSolutionCalculator dianShuZuYaPaiSolutionCalculator = new GuandanDianShuZuYaPaiSolutionCalculator();
        dianShuZuYaPaiSolutionCalculator.setBx(optionalPlay.getBx());
        dianShuZuYaPaiSolutionCalculator.setDanGeDianShuZuComparator(danGeDianShuZuComparator);
        dianShuZuYaPaiSolutionCalculator.setLianXuDianShuZuComparator(lianXuDianShuZuComparator);
        dianShuZuYaPaiSolutionCalculator.setDaipaiComparator(daipaiComparator);
        ju.setDianShuZuYaPaiSolutionCalculator(dianShuZuYaPaiSolutionCalculator);//点数压牌计算器

        GuandanZaDanYaPaiSolutionCalculator zaDanYaPaiSolutionCalculator = new GuandanZaDanYaPaiSolutionCalculator();
        zaDanYaPaiSolutionCalculator.setBx(optionalPlay.getBx());
        zaDanYaPaiSolutionCalculator.setZhadanComparator(zhadanComparator);
        ju.setZaDanYaPaiSolutionCalculator(zaDanYaPaiSolutionCalculator);//炸弹压牌计算器

//        ju.addDaListener(new XianshuCountDaActionStatisticsListener());//新增监听器

        //开始第一盘
        ju.startFirstPan(allPlayerIds(), currentTime);

        //正常模式0分开始 进园子以园子分开始
        for (ShuangkouPlayer player : ju.getCurrentPan().getShuangkouPlayerIdPlayerMap().values()) {
            if (optionalPlay.isJinyuanzi()) {
                playerTotalScoreMap.put(player.getId(), (double) optionalPlay.getYuanzifen());
                player.setPlayerTotalScore(optionalPlay.getYuanzifen());
            } else {
                playerTotalScoreMap.put(player.getId(), 0d);
                player.setPlayerTotalScore(0);
            }
        }

        allPlayerIds().forEach((pid) -> playerMaxXianshuMap.put(pid, 1));
        allPlayerIds().forEach((pid) -> playerOtherMaxXianshuMap.put(pid, 1));
        allPlayerIds().forEach((pid) -> playerTotalGongxianfenMap.put(pid, calculateTotalGongxianfenForPlayer(pid)));
        totalGongxianfenMap.putAll(playerTotalGongxianfenMap);
    }

    /**
     * 开始下一盘
     */
    @Override
    protected void startNextPan() throws Exception {
        playerGongxianfenMap = new HashMap<>();
        playerMaxXianshuMap = new HashMap<>();
        playerOtherMaxXianshuMap = new HashMap<>();
        playerMingciMap = new HashMap<>();
        ju.startNextPan();
        Map<String, GuandanGongxianFen> totalGongxianfenMap = new HashMap<>();
        GuandanCurrentPanResultBuilder panResultBuilder = (GuandanCurrentPanResultBuilder) ju.getCurrentPanResultBuilder();
        panResultBuilder.setPlayerTotalGongxianfenMap(totalGongxianfenMap);
        allPlayerIds().forEach((pid) -> playerMaxXianshuMap.put(pid, 1));
        allPlayerIds().forEach((pid) -> playerOtherMaxXianshuMap.put(pid, 1));
        allPlayerIds().forEach((pid) -> playerTotalGongxianfenMap.put(pid, calculateTotalGongxianfenForPlayer(pid)));
        totalGongxianfenMap.putAll(playerTotalGongxianfenMap);
        boolean hasChaodi = false;
        chaodiPlayerIdList = new ArrayList<>();
        Set<String> cannotChaodiSet = new HashSet<>();
//        if (optionalPlay.isChaodi()) {
//            for (String playerId : allPlayerIds()) {
//                if (!tryPlayerHasZhadan(playerId)) {
//                    chaodiPlayerIdList.add(playerId);
//                    hasChaodi = true;
//                } else {
//                    cannotChaodiSet.add(playerId);
//                }
//            }
//        }
        if (hasChaodi) {    // 能够抄底
            GuandanCurrentPanResultBuilder GuandanCurrentPanResultBuilder = (GuandanCurrentPanResultBuilder) ju.getCurrentPanResultBuilder();
            List<String> chaodiPlayerIds = new ArrayList<>(chaodiPlayerIdList);
            GuandanCurrentPanResultBuilder.setChaodiPlayerIdList(chaodiPlayerIds);
            allPlayerIds().forEach((pid) -> {
                if (cannotChaodiSet.contains(pid)) {
                    playerChaodiStateMap.put(pid, PukeGamePlayerChaodiState.cannotchaodi);
                } else {
                    playerChaodiStateMap.put(pid, PukeGamePlayerChaodiState.startChaodi);
                }
            });
            state = new StartChaodi();
            updateAllPlayersState(new PlayerChaodi());
        } else {
            state = new Playing();
            updateAllPlayersState(new PlayerPlaying());
        }

    }

    /**
     * 打牌
     *
     * @param playerId       玩家ID
     * @param paiIds         扑克索引集合
     * @param dianshuZuheIdx 点数组索引
     * @param actionTime     时间戳
     */
    public PukeActionResult da(String playerId, List<Integer> paiIds, String dianshuZuheIdx, long actionTime) throws Exception {
        PanActionFrame panActionFrame;
        panActionFrame = ju.da(playerId, paiIds, dianshuZuheIdx, actionTime);
        PukeActionResult result = new PukeActionResult();
        result.setPanActionFrame(panActionFrame);
//        // 记录贡献分
//        XianshuCountDaActionStatisticsListener GuandanListener = ju.getActionStatisticsListenerManager().findDaListener(XianshuCountDaActionStatisticsListener.class);
//        Map<String, int[]> playerXianshuMap = GuandanListener.getPlayerXianshuMap();
//        Map<String, GuandanXianshuBeishu> maxXianshuMap = new HashMap<>();
//        Map<String, GuandanGongxianFen> gongxianfenMap = new HashMap<>();
//        List<GuandanGongxianFen> panPlayerGongxianfenList = new ArrayList<>();
//        for (String pid : allPlayerIds()) {
//            int[] xianshuCount = playerXianshuMap.get(pid);
//            GuandanXianshuBeishu xianshubeishu = new GuandanXianshuBeishu(xianshuCount);
//            xianshubeishu.calculate(false, false, false);/////////////////////////
//            maxXianshuMap.put(pid, xianshubeishu);
//            GuandanGongxianFen gongxianfen = new GuandanGongxianFen(xianshuCount);
//            gongxianfen.calculateXianshu(false);////////////////////////
//            gongxianfen.calculate(renshu);
//            panPlayerGongxianfenList.add(gongxianfen);
//            gongxianfenMap.put(pid, gongxianfen);
//        }
//        // 两两结算贡献分
//        for (int i = 0; i < renshu; i++) {
//            GuandanGongxianFen gongxian1 = panPlayerGongxianfenList.get(i);
//            for (int j = (i + 1); j < renshu; j++) {
//                GuandanGongxianFen gongxian2 = panPlayerGongxianfenList.get(j);
//                // 结算贡献分
//                int fen1 = gongxian1.getValue();
//                int fen2 = gongxian2.getValue();
//                gongxian1.jiesuan(-fen2);
//                gongxian2.jiesuan(-fen1);
//            }
//        }
//        // 计算贡献分
//        for (String pid : allPlayerIds()) {
//            GuandanGongxianFen gongxianfen = gongxianfenMap.get(pid);
//            Integer detal = playerGongxianfenMap.get(pid);
//            if (detal == null) {
//                detal = 0;
//            }
//            playerGongxianfenDetalMap.put(pid, gongxianfen.getTotalscore() - detal);
//            playerGongxianfenMap.put(pid, gongxianfen.getTotalscore());
//        }

        if (state.name().equals(VoteNotPassWhenPlaying.name)) {
            state = new Playing();
        }
        checkAndFinishPan();
        if (state.name().equals(WaitingNextPan.name) || state.name().equals(Finished.name)) {// 盘结束了
            GuandanPanResult panResult = (GuandanPanResult) ju.findLatestFinishedPanResult();
            for (GuandanPanPlayerResult GuandanPanPlayerResult : panResult.getPanPlayerResultList()) {
                playerTotalScoreMap.put(GuandanPanPlayerResult.getPlayerId(), GuandanPanPlayerResult.getTotalScore());
            }
            result.setPanResult(panResult);
            if (state.name().equals(Finished.name)) {// 局结束了
                result.setJuResult((GuandanJuResult) ju.getJuResult());
            }
        } else {
            // 计算胜负分
            List<String> playerIds = allPlayerIds();
            Pan currentPan = ju.getCurrentPan();
            allPlayerIds().forEach((pid) -> {
                if (renshu > 2) {
                    ShuangkouPlayer duijiaPlayer = currentPan.findDuijiaPlayer(pid);
                    String duijiaPlayerId = duijiaPlayer.getId();
                    int maxXianshu = 1;
                    int otherMaxXianshu = 1;
//                    for (String id : playerIds) {
//                        if (id.equals(pid) || id.equals(duijiaPlayerId)) {
//                            GuandanXianshuBeishu xianshubeishu = maxXianshuMap.get(id);
//                            if (xianshubeishu.getValue() > maxXianshu) {
//                                maxXianshu = xianshubeishu.getValue();
//                            }
//                        } else {
//                            GuandanXianshuBeishu xianshubeishu = maxXianshuMap.get(id);
//                            if (xianshubeishu.getValue() > otherMaxXianshu) {
//                                otherMaxXianshu = xianshubeishu.getValue();
//                            }
//                        }
//                    }
                    playerMaxXianshuMap.put(pid, maxXianshu);
                    playerOtherMaxXianshuMap.put(pid, otherMaxXianshu);
                } else {
//                    for (String id : playerIds) {
//                        if (id.equals(pid)) {
//                            GuandanXianshuBeishu xianshubeishu = maxXianshuMap.get(id);
//                            playerMaxXianshuMap.put(pid, xianshubeishu.getValue());
//                        } else {
//                            GuandanXianshuBeishu xianshubeishu = maxXianshuMap.get(id);
//                            playerOtherMaxXianshuMap.put(pid, xianshubeishu.getValue());
//                        }
//                    }
                }
            });
            // 记录名次
            List<String> noPaiPlayerIdList = ju.getCurrentPan().getNoPaiPlayerIdList();
            if (!noPaiPlayerIdList.isEmpty()) {
                for (int i = 0; i < noPaiPlayerIdList.size(); i++) {
                    playerMingciMap.put(noPaiPlayerIdList.get(i), i + 1);
                }
            }
        }
        result.setPukeGame(new PukeGameValueObject(this));
        Map<String, String> depositPlayerList = ju.getDepositPlayerList();
        Pan currentPan = ju.getCurrentPan();
        if (currentPan != null) {
            String xiajiaPlayerId = currentPan.getPositionPlayerIdMap().get(currentPan.getActionPosition());
            if (depositPlayerList.containsKey(xiajiaPlayerId)) {
                executorService.submit(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ShuangkouPlayer xiajiaPlayer = currentPan.findPlayer(xiajiaPlayerId);
                    automatic.autoDapai(xiajiaPlayer, this.getId());
                });
            }
        }
        return result;
    }

    /**
     * 过牌
     *
     * @param playerId   玩家ID
     * @param actionTime 时间戳
     */
    public PukeActionResult guo(String playerId, long actionTime) throws Exception {
        PanActionFrame panActionFrame;
        panActionFrame = ju.guo(playerId, actionTime);

        PukeActionResult result = new PukeActionResult();
        result.setPanActionFrame(panActionFrame);
        if (state.name().equals(VoteNotPassWhenPlaying.name)) {
            state = new Playing();
        }
        result.setPukeGame(new PukeGameValueObject(this));

        //托管玩家自动出牌
        Map<String, String> depositPlayerList = ju.getDepositPlayerList();
        Pan currentPan = ju.getCurrentPan();
        if (currentPan != null) {
            String xiajiaPlayerId = currentPan.getPositionPlayerIdMap().get(currentPan.getActionPosition());
            if (depositPlayerList.containsKey(xiajiaPlayerId)) {
                executorService.submit(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ShuangkouPlayer xiajiaPlayer = currentPan.findPlayer(xiajiaPlayerId);
                    automatic.autoDapai(xiajiaPlayer, this.getId());
                });
            }
        }
        return result;
    }

    public ChaodiResult chaodi(String playerId, boolean chaodi, long actionTime) throws Exception {
        ChaodiResult chaodiResult = new ChaodiResult();
        if (tryPlayerHasZhadan(playerId)) {
            playerChaodiStateMap.put(playerId, PukeGamePlayerChaodiState.cannotchaodi);
        } else {
            if (chaodi) {
                playerChaodiStateMap.put(playerId, PukeGamePlayerChaodiState.chaodi);
            } else {
                playerChaodiStateMap.put(playerId, PukeGamePlayerChaodiState.buchao);
            }
        }
        if (state.name().equals(VoteNotPassWhenChaodi.name)) {
            state = new StartChaodi();
        }
        updatePlayerState(playerId, new PlayerAfterChaodi());
        boolean start = true;
        boolean finish = false;
        for (PukeGamePlayerChaodiState chaodiState : playerChaodiStateMap.values()) {
            if (chaodiState.equals(PukeGamePlayerChaodiState.startChaodi)) {
                start = false;
            }
            if (chaodiState.equals(PukeGamePlayerChaodiState.chaodi)) {
                finish = true;
            }
        }
        PanActionFrame firstPanActionFrame = ju.getCurrentPan().findLatestActionFrame();
        chaodiResult.setPanActionFrame(firstPanActionFrame);
        if (finish) {// 抄底成功
            GuandanCurrentPanResultBuilder GuandanCurrentPanResultBuilder = (GuandanCurrentPanResultBuilder) ju.getCurrentPanResultBuilder();
            PanResult panResult = GuandanCurrentPanResultBuilder.buildCurrentPanResultByChaodi(ju, actionTime);
            ju.getFinishedPanResultList().add(panResult);
            ju.setCurrentPan(null);
            if (ju.getJuFinishiDeterminer().determineToFinishJu(ju)) {// 是否局结束
                ju.setJuResult(ju.getJuResultBuilder().buildJuResult(ju));
            }
            checkAndFinishPan();
            if (state.name().equals(WaitingNextPan.name) || state.name().equals(Finished.name)) {// 盘结束了
                GuandanPanResult GuandanPanResult = (GuandanPanResult) ju.findLatestFinishedPanResult();
                for (GuandanPanPlayerResult GuandanPanPlayerResult : GuandanPanResult.getPanPlayerResultList()) {
                    playerTotalScoreMap.put(GuandanPanPlayerResult.getPlayerId(), GuandanPanPlayerResult.getTotalScore());
                }
                chaodiResult.setPanResult(GuandanPanResult);
                if (state.name().equals(Finished.name)) {// 局结束了
                    chaodiResult.setJuResult((GuandanJuResult) ju.getJuResult());
                }
            }
            chaodiResult.setPukeGame(new PukeGameValueObject(this));
            return chaodiResult;
        }
        if (start) {// 未成功抄底，正常游戏
            state = new Playing();
            updateAllPlayersState(new PlayerPlaying());
        }
        chaodiResult.setPukeGame(new PukeGameValueObject(this));
        return chaodiResult;
    }

    private boolean tryPlayerHasZhadan(String playerId) {
        Pan currentPan = ju.getCurrentPan();
        ShuangkouPlayer player = currentPan.findPlayer(playerId);
        Map<Integer, PukePai> allShoupai = player.getAllShoupai();
        int[] dianshuCountArray = new int[16];
        for (PukePai pukePai : allShoupai.values()) {
            DianShu dianShu = pukePai.getPaiMian().dianShu();
            dianshuCountArray[dianShu.ordinal()]++;
        }
        int xiaowangCount = dianshuCountArray[13];
        int dawangCount = dianshuCountArray[14];
        if (xiaowangCount + dawangCount >= 3) {// 有王炸
            return true;
        }
        int wangCount = 0;
        if (BianXingWanFa.qianbian.equals(optionalPlay.getBx())) {// 千变
            wangCount = xiaowangCount + dawangCount;
            // 减去王牌的数量
            dianshuCountArray[13] = 0;
            dianshuCountArray[14] = 0;
        } else if (BianXingWanFa.banqianbian.equals(optionalPlay.getBx())) {// 半千变;
            wangCount = dawangCount;
            // 减去王牌的数量
            if (xiaowangCount > 0 && xiaowangCount % 2 == 0) {
                wangCount++;
                dianshuCountArray[13] = dianshuCountArray[13] - 2;
            }
            dianshuCountArray[14] = 0;
        } else if (BianXingWanFa.baibian.equals(optionalPlay.getBx())) {// 百变
            wangCount = dawangCount;
            // 减去王牌的数量
            dianshuCountArray[14] = 0;
        }
        if (wangCount > 0) {
            // 有王牌
            return tryHasZhadanWithWangDang(wangCount, dianshuCountArray, dawangCount);
        } else {
            // 没有王牌
            return tryHasZhadanWithoutWangDang(dianshuCountArray);
        }
    }

    private boolean tryHasZhadanWithWangDang(int wangCount, int[] dianshuCountArray, int dawangCount) {
        // 循环王的各种当法
        int maxZuheCode = (int) Math.pow(13, wangCount);
        int[] modArray = new int[wangCount];
        for (int m = 0; m < wangCount; m++) {
            modArray[m] = (int) Math.pow(13, wangCount - 1 - m);
        }
        for (int zuheCode = 0; zuheCode < maxZuheCode; zuheCode++) {
            ShoupaiJiesuanPai[] hongxinErDangPaiArray = new ShoupaiJiesuanPai[wangCount];
            int temp = zuheCode;
            int previousGuipaiDangIdx = 0;
            for (int n = 0; n < wangCount; n++) {
                int mod = modArray[n];
                int shang = temp / mod;
                if (shang >= previousGuipaiDangIdx) {// 计算王的各种当法，排除效果相同的当法
                    int yu = temp % mod;

//                    if (BianXingWanFa.qianbian.equals(optionalPlay.getBx())) {// 千变
//                        if (n < dawangCount) {
//                            hongxinErDangPaiArray[n] = new DawangDangPai(DianShu.getDianShuByOrdinal(shang));
//                        } else {
//                            hongxinErDangPaiArray[n] = new XiaowangDangPai(1, DianShu.getDianShuByOrdinal(shang));
//                        }
//                    } else if (BianXingWanFa.banqianbian.equals(optionalPlay.getBx())) {// 半千变;
//                        if (n < dawangCount) {
//                            hongxinErDangPaiArray[n] = new DawangDangPai(DianShu.getDianShuByOrdinal(shang));
//                        } else {
//                            hongxinErDangPaiArray[n] = new XiaowangDangPai(2, DianShu.getDianShuByOrdinal(shang));
//                        }
//                    } else if (BianXingWanFa.baibian.equals(optionalPlay.getBx())) {// 百变
//                        hongxinErDangPaiArray[n] = new DawangDangPai(DianShu.getDianShuByOrdinal(shang));
//                    }

                    hongxinErDangPaiArray[n] = new HongxinErDangPai(DianShu.getDianShuByOrdinal(shang));

                    temp = yu;
                    previousGuipaiDangIdx = shang;
                } else {
                    hongxinErDangPaiArray = null;
                    break;
                }
            }
            if (hongxinErDangPaiArray != null) {
                // 加上当牌的数量
                for (ShoupaiJiesuanPai jiesuanPai : hongxinErDangPaiArray) {
                    dianshuCountArray[jiesuanPai.getDangPaiType().ordinal()]++;
                }
                PaiXing paiXing = new PaiXing();
                // 普通炸弹
                paiXing.setDanGeZhadanDianShuZuList(DianShuZuCalculator.calculateDanGeZhadanDianShuZu(dianshuCountArray));
                // 连续炸弹
                paiXing.setLianXuZhadanDianShuZuList(DianShuZuCalculator.calculateLianXuZhadanDianShuZu(dianshuCountArray));
                if (paiXing.hasZhadan()) {
                    return true;
                }
                // 减去当牌的数量
                for (ShoupaiJiesuanPai jiesuanPai : hongxinErDangPaiArray) {
                    dianshuCountArray[jiesuanPai.getDangPaiType().ordinal()]--;
                }
            }
        }
        return false;
    }

    private boolean tryHasZhadanWithoutWangDang(int[] dianshuCountArray) {
        PaiXing paiXing = new PaiXing();
        // 普通炸弹
        paiXing.setDanGeZhadanDianShuZuList(DianShuZuCalculator.calculateDanGeZhadanDianShuZu(dianshuCountArray));
        // 连续炸弹
        paiXing.setLianXuZhadanDianShuZuList(DianShuZuCalculator.calculateLianXuZhadanDianShuZu(dianshuCountArray));
        return paiXing.hasZhadan();
    }

    private GuandanGongxianFen calculateTotalGongxianfenForPlayer(String playerId) {
        int[] xianshuArray = new int[9];
        return GuandanShoupaiGongxianfenCalculator.calculateTotalGongxianfenWithShouPaiForPlayer(playerId, ju, optionalPlay.getBx(), xianshuArray, false);////////////////////////
    }


    public PanActionFrame findFirstPanActionFrame() {
        return ju.getCurrentPan().findLatestActionFrame();
    }

    @Override
    protected boolean checkToFinishGame() {
        return ju.getJuResult() != null;
    }

    @Override
    protected boolean checkToFinishCurrentPan() {
        return ju.getCurrentPan() == null;
    }

    @Override
    protected void updatePlayerToExtendedVotingState(GamePlayer player) {
        if (player.getState().name().equals(PlayerChaodi.name)) {
            player.setState(new PlayerVotingWhenChaodi());
        } else if (player.getState().name().equals(PlayerAfterChaodi.name)) {
            player.setState(new PlayerVotingWhenAfterChaodi());
        }
    }

    @Override
    protected void updateToExtendedVotingState() {
        if (state.name().equals(StartChaodi.name) || state.name().equals(VoteNotPassWhenChaodi.name)) {
            state = new VotingWhenChaodi();
        }
    }

    @Override
    protected void updatePlayerToExtendedVotedState(GamePlayer player) {
        String stateName = player.getState().name();
        if (stateName.equals(PlayerVotingWhenChaodi.name)) {
            player.setState(new PlayerVotedWhenChaodi());
        } else if (player.getState().name().equals(PlayerVotingWhenAfterChaodi.name)) {
            player.setState(new PlayerVotedWhenAfterChaodi());
        }
    }

    @Override
    protected void recoveryPlayersStateFromExtendedVoting() throws Exception {
        if (state.name().equals(VoteNotPassWhenChaodi.name)) {
            for (GamePlayer player : idPlayerMap.values()) {
                if (player.getState().name().equals(PlayerVotingWhenChaodi.name) || player.getState().name().equals(PlayerVotedWhenChaodi.name)) {
                    updatePlayerState(player.getId(), new PlayerChaodi());
                } else if (player.getState().name().equals(PlayerVotingWhenAfterChaodi.name) || player.getState().name().equals(PlayerVotedWhenAfterChaodi.name)) {
                    updatePlayerState(player.getId(), new PlayerAfterChaodi());
                }
            }
        }
    }

    @Override
    protected void updateToVoteNotPassStateFromExtendedVoting() {
        if (state.name().equals(VotingWhenChaodi.name)) {
            state = new VoteNotPassWhenChaodi();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public PukeGameValueObject toValueObject() {
        return new PukeGameValueObject(this);
    }

    @Override
    public void start(long currentTime) throws Exception {
        createJuAndStartFirstPan(currentTime);
        boolean hasChaodi = false;
        Set<String> cannotChaodiSet = new HashSet<>();
//        if (optionalPlay.isChaodi()) {
//            for (String playerId : allPlayerIds()) {
//                if (!tryPlayerHasZhadan(playerId)) {
//                    chaodiPlayerIdList.add(playerId);
//                    hasChaodi = true;
//                } else {
//                    cannotChaodiSet.add(playerId);
//                }
//            }
//        }
        if (hasChaodi) {// 能够抄底
            GuandanCurrentPanResultBuilder GuandanCurrentPanResultBuilder = (GuandanCurrentPanResultBuilder) ju.getCurrentPanResultBuilder();
            List<String> chaodiPlayerIds = new ArrayList<>(chaodiPlayerIdList);
            GuandanCurrentPanResultBuilder.setChaodiPlayerIdList(chaodiPlayerIds);
            allPlayerIds().forEach((pid) -> {
                if (cannotChaodiSet.contains(pid)) {
                    playerChaodiStateMap.put(pid, PukeGamePlayerChaodiState.cannotchaodi);
                } else {
                    playerChaodiStateMap.put(pid, PukeGamePlayerChaodiState.startChaodi);
                }
            });
            state = new StartChaodi();
            updateAllPlayersState(new PlayerChaodi());
        } else {
            state = new Playing();
            updateAllPlayersState(new PlayerPlaying());
        }
    }

    @Override
    public void finish() {
        if (ju != null) {
            ju.finish();
        }
    }

    public double getDifen() {
        return difen;
    }

    public void setDifen(double difen) {
        this.difen = difen;
    }

    public int getPanshu() {
        return panshu;
    }

    public void setPanshu(int panshu) {
        this.panshu = panshu;
    }

    public int getRenshu() {
        return renshu;
    }

    public void setRenshu(int renshu) {
        this.renshu = renshu;
    }

    public OptionalPlay getOptionalPlay() {
        return optionalPlay;
    }

    public void setOptionalPlay(OptionalPlay optionalPlay) {
        this.optionalPlay = optionalPlay;
    }

    public Ju getJu() {
        return ju;
    }

    public void setJu(Ju ju) {
        this.ju = ju;
    }

    public Map<String, Double> getPlayerTotalScoreMap() {
        return playerTotalScoreMap;
    }

    public Map<String, Integer> getPlayerGongxianfenMap() {
        return playerGongxianfenMap;
    }

    public void setPlayerGongxianfenMap(Map<String, Integer> playerGongxianfenMap) {
        this.playerGongxianfenMap = playerGongxianfenMap;
    }

    public Map<String, Integer> getPlayerGongxianfenDetalMap() {
        return playerGongxianfenDetalMap;
    }

    public Map<String, GuandanGongxianFen> getPlayerTotalGongxianfenMap() {
        return playerTotalGongxianfenMap;
    }

    public Map<String, Integer> getPlayerMaxXianshuMap() {
        return playerMaxXianshuMap;
    }

    public void setPlayerMaxXianshuMap(Map<String, Integer> playerMaxXianshuMap) {
        this.playerMaxXianshuMap = playerMaxXianshuMap;
    }

    public Map<String, Integer> getPlayerOtherMaxXianshuMap() {
        return playerOtherMaxXianshuMap;
    }

    public void setPlayerOtherMaxXianshuMap(Map<String, Integer> playerOtherMaxXianshuMap) {
        this.playerOtherMaxXianshuMap = playerOtherMaxXianshuMap;
    }

    public Map<String, Integer> getPlayerMingciMap() {
        return playerMingciMap;
    }

    public void setPlayerMingciMap(Map<String, Integer> playerMingciMap) {
        this.playerMingciMap = playerMingciMap;
    }

    public List<String> getChaodiPlayerIdList() {
        return chaodiPlayerIdList;
    }

    public void setChaodiPlayerIdList(List<String> chaodiPlayerIdList) {
        this.chaodiPlayerIdList = chaodiPlayerIdList;
    }

    public Map<String, PukeGamePlayerChaodiState> getPlayerChaodiStateMap() {
        return playerChaodiStateMap;
    }

    public int getPowerLimit() {
        return powerLimit;
    }

    public void setPowerLimit(int powerLimit) {
        this.powerLimit = powerLimit;
    }
}
