package com.anbang.qipai.maanshanmajiang.cqrs.c.domain;

import java.util.*;

import com.anbang.qipai.maanshanmajiang.cqrs.c.domain.dingque.*;
import com.anbang.qipai.maanshanmajiang.cqrs.c.domain.listener.MaanshanMajiangPengGangActionStatisticsListener;
import com.anbang.qipai.maanshanmajiang.cqrs.c.domain.test.MaanshanMajiangFaPaiStrategyTest;
import com.dml.majiang.ju.Ju;
import com.dml.majiang.ju.result.JuResultBuilder;
import com.dml.majiang.pai.XushupaiCategory;
import com.dml.majiang.pan.frame.PanActionFrame;
import com.dml.majiang.pan.publicwaitingplayer.WaitDaPlayerPanPublicWaitingPlayerDeterminer;
import com.dml.majiang.player.MajiangPlayer;
import com.dml.majiang.player.action.chi.PengganghuFirstChiActionProcessor;
import com.dml.majiang.player.action.da.DachushoupaiDaActionProcessor;
import com.dml.majiang.player.action.dingque.MajiangDingqueAction;
import com.dml.majiang.player.action.guo.DoNothingGuoActionProcessor;
import com.dml.majiang.player.action.initial.ZhuangMoPaiInitialActionUpdater;
import com.dml.majiang.player.action.mo.LundaoMopai;
import com.dml.majiang.player.action.mo.MajiangMoAction;
import com.dml.majiang.player.action.peng.HuFirstBuPengActionProcessor;
import com.dml.majiang.player.menfeng.RandomMustHasDongPlayersMenFengDeterminer;
import com.dml.majiang.player.zhuang.MenFengDongZhuangDeterminer;
import com.dml.mpgame.game.Finished;
import com.dml.mpgame.game.Playing;
import com.dml.mpgame.game.extend.fpmpv.FixedPlayersMultipanAndVotetofinishGame;
import com.dml.mpgame.game.extend.multipan.WaitingNextPan;
import com.dml.mpgame.game.extend.vote.VoteNotPassWhenPlaying;
import com.dml.mpgame.game.player.GamePlayer;
import com.dml.mpgame.game.player.PlayerPlaying;

public class MajiangGame extends FixedPlayersMultipanAndVotetofinishGame {
    private int panshu;                  //盘数
    private int renshu;                  //人数
    private Double difen;                //底分
    private int powerLimit;              //淘汰分
    private OptionalPlay optionalPlay;   //玩法
    private Ju ju;
    private Map<String, Double> playeTotalScoreMap = new HashMap<>();
    private Set<String> xipaiPlayerIds = new HashSet<>();

    private int currentDao = 1;
    private Map<String, List<Double>> playerTotalDaoScoreMap = new HashMap<>();
    private Map<String, Double> playerTiwaixunhuanScoreMap = new HashMap<>();

    /**
     * 创建局并开始第一盘
     *
     * @param currentTime 时间戳
     */
    public PanActionFrame createJuAndStartFirstPan(long currentTime) throws Exception {
        ju = new Ju();
        ju.setStartFirstPanProcess(new ClassicStartFirstPanProcess());                                                //第一盘开始
        ju.setStartNextPanProcess(new ClassicStartNextPanProcess());                                                  //下一盘开始
        ju.setPlayersMenFengDeterminerForFirstPan(new RandomMustHasDongPlayersMenFengDeterminer(currentTime));        //第一盘随机玩家东风
        ju.setPlayersMenFengDeterminerForNextPan(new MaanshanMajiangPlayersMenFengDeterminer());                      //下一盘胡牌坐东风
        ju.setZhuangDeterminerForFirstPan(new MenFengDongZhuangDeterminer());                                         //第一盘东风坐庄
        ju.setZhuangDeterminerForNextPan(new MenFengDongZhuangDeterminer());                                          //下一盘东风坐庄
        ju.setAvaliablePaiFiller(new MaanshanMajiangFaRandomAvaliablePaiFiller(currentTime + 2, optionalPlay));  //填充可用牌
        ju.setFaPaiStrategy(new MaanshanMajiangFaPaiStrategy(13, optionalPlay));                 //顺序发牌
        ju.setCurrentPanFinishiDeterminer(new MaanshanMajiangPanFinishiDeterminer());                                 //盘结束条件
        ju.setJuFinishiDeterminer(new MaanshanMajiangFixedPanNumbersJuFinishiDeterminer(panshu, optionalPlay));       //局结束条件
        ju.setCurrentPanPublicWaitingPlayerDeterminer(new WaitDaPlayerPanPublicWaitingPlayerDeterminer());            //等待出牌
        ju.setJuResultBuilder(new MaanshanMajiangJuResultBuilder());                                                  //局记分结果
        ju.setInitialActionUpdater(new ZhuangMoPaiInitialActionUpdater());                                            //庄家摸牌
        ju.setGouXingPanHu(new MaanshanMajiangGouXingPanHu());                                                        //可胡牌构型
        ju.setCurrentPanResultBuilder(new MaanshanMajiangPanResultBuilder(optionalPlay, difen));                      //盘记分结果
        ju.setHupaiPaixingSolutionFilter(new MaanshanMajiangHuPaiSolutionsTipsFilter(optionalPlay));                  //胡牌提示

        ju.setMoActionProcessor(new MaanshanMajiangMoActionProcessor());            //摸牌动作处理
        ju.setMoActionUpdater(new MaanshanMajiangMoActionUpdater());                //摸牌动作更新
        ju.setDaActionProcessor(new DachushoupaiDaActionProcessor());               //打牌动作处理
        ju.setDaActionUpdater(new MaanshanMajiangDaActionUpdater());                //打牌动作更新
        ju.setChiActionProcessor(new PengganghuFirstChiActionProcessor());          //吃牌动作处理
        ju.setChiActionUpdater(new MaanshanMajiangChiActionUpdater());              //吃牌动作更新
        ju.setPengActionProcessor(new HuFirstBuPengActionProcessor());              //碰牌动作处理
        ju.setPengActionUpdater(new MaanshanMajiangPengActionUpdater());            //碰牌动作更新
        ju.setGangActionProcessor(new MaanshanMajiangGangActionProcessor());        //杠牌动作处理
        ju.setGangActionUpdater(new MaanshanMajiangGangActionUpdater());            //杠牌动作更新
        ju.setGuoActionProcessor(new DoNothingGuoActionProcessor());                //过牌动作处理
        ju.setGuoActionUpdater(new MaanshanMajiangGuoActionUpdater());              //过牌动作更新
        ju.setHuActionProcessor(new MaanshanMajiangHuActionProcessor());            //胡牌动作处理
        ju.setHuActionUpdater(new MaanshanMajiangClearAllActionHuActionUpdater());  //胡牌动作更新

        ju.addActionStatisticsListener(new MaanshanMajiangPengGangActionStatisticsListener());  //杠统计监测器
//        ju.addActionStatisticsListener(new TianHuAndDihuOpportunityDetector());                 //天胡地胡监测器
//        ju.addActionStatisticsListener(new GuoHuBuHuStatisticsListener());                      //过胡不胡监测器
//        ju.addActionStatisticsListener(new GuoPengBuPengStatisticsListener());                  //过碰不碰监测器
//        ju.addActionStatisticsListener(new TianchangxiaohuaLastMoActionPlayerRecorder());       //最后摸牌监测器
//        ju.addActionStatisticsListener(new GuoGangBuGangStatisticsListener());                  //过杠不杠监测器

        // 开始第一盘
        ju.startFirstPan(allPlayerIds());

        //以50分开始
        for (MajiangPlayer player : ju.getCurrentPan().getMajiangPlayerIdMajiangPlayerMap().values()) {
            playeTotalScoreMap.put(player.getId(), 50d);
            player.setPlayerTotalScore(50);
            List<Double> list = new ArrayList<>();
            switch (optionalPlay.getDaozi()) {
                case 1:
                    list.add(50d);
                    break;
                case 2:
                    list.add(50d);
                    list.add(50d);
                    break;
                case 3:
                    list.add(50d);
                    list.add(50d);
                    list.add(50d);
                    break;
            }
            playerTotalDaoScoreMap.put(player.getId(), list);
            playerTiwaixunhuanScoreMap.put(player.getId(), 0d);
        }

        // 必然庄家已经先摸了一张牌了
        return ju.getCurrentPan().findLatestActionFrame();
    }

    @Override
    public void start(long currentTime) throws Exception {
//        xipaiPlayerIds.clear();
//        state = new Playing();
//        updateAllPlayersState(new PlayerPlaying());
        state = new DingqueState();
        updateAllPlayersState(new PlayerDingque());
    }

    @Override
    protected void startNextPan() throws Exception {
        ju.startNextPan();
//        state = new Playing();
//        updateAllPlayersState(new PlayerPlaying());
        state = new DingqueState();
        updateAllPlayersState(new PlayerDingque());
    }

    @Override
    public void finish() throws Exception {
        if (ju != null) {
//            ju.finish();
            MaanshanMajiangJuResultBuilder juResultBuilder = (MaanshanMajiangJuResultBuilder) ju.getJuResultBuilder();
            ju.setJuResult(juResultBuilder.buildJuResult(ju, this));
        }
    }

    /**
     * 行牌
     *
     * @param playerId   玩家ID
     * @param actionId   动作ID
     * @param actionNo   动作编号
     * @param actionTime 动作时间
     */
    public MajiangActionResult action(String playerId, int actionId, int actionNo, long actionTime) throws Exception {
        ju.setCurrentDao(currentDao);
        PanActionFrame panActionFrame = ju.action(playerId, actionId, actionNo, actionTime);
        MajiangActionResult result = new MajiangActionResult();
        result.setPanActionFrame(panActionFrame);
        if (state.name().equals(VoteNotPassWhenPlaying.name)) {
            state = new Playing();
        }
        if (!xipaiPlayerIds.isEmpty()) {
            xipaiPlayerIds.clear();
        }

//        if (ju.getCurrentPan() != null) {
//            //即时更新杠分（两个模式都是）
//            for (MajiangPlayer player : ju.getCurrentPan().getMajiangPlayerIdMajiangPlayerMap().values()) {
//                playeTotalScoreMap.put(player.getId(), player.getPlayerTotalScore());
//            }
//        }

        checkAndFinishPan();

        if (state.name().equals(WaitingNextPan.name) || state.name().equals(Finished.name)) { //盘结束了
            MaanshanMajiangPanResult panResult = (MaanshanMajiangPanResult) ju.findLatestFinishedPanResult();
            boolean newDao = false;
            for (MaanshanMajiangPanPlayerResult maanshanMajiangPanPlayerResult : panResult.getPanPlayerResultList()) {
                if (maanshanMajiangPanPlayerResult.getTotalScore() <= 0) {
                    newDao = true;
                    break;
                }
            }
            ju.setNewdao(newDao);
            if (newDao) {
                for (MaanshanMajiangPanPlayerResult maanshanMajiangPanPlayerResult : panResult.getPanPlayerResultList()) {
                    playeTotalScoreMap.put(maanshanMajiangPanPlayerResult.getPlayerId(), 50d);
                    List<Double> doubles = playerTotalDaoScoreMap.get(maanshanMajiangPanPlayerResult.getPlayerId());
                    doubles.set(currentDao - 1, maanshanMajiangPanPlayerResult.getTotalScore());
                    playerTotalDaoScoreMap.put(maanshanMajiangPanPlayerResult.getPlayerId(), doubles);
                }
                currentDao++;
            } else {
                for (MaanshanMajiangPanPlayerResult maanshanMajiangPanPlayerResult : panResult.getPanPlayerResultList()) {
                    playeTotalScoreMap.put(maanshanMajiangPanPlayerResult.getPlayerId(), maanshanMajiangPanPlayerResult.getTotalScore());
                    List<Double> doubles = playerTotalDaoScoreMap.get(maanshanMajiangPanPlayerResult.getPlayerId());
                    doubles.set(currentDao - 1, maanshanMajiangPanPlayerResult.getTotalScore());
                    playerTotalDaoScoreMap.put(maanshanMajiangPanPlayerResult.getPlayerId(), doubles);
                }
            }

            for (MaanshanMajiangPanPlayerResult maanshanMajiangPanPlayerResult : panResult.getPanPlayerResultList()) {
                Double tiwaixunhuanScore = playerTiwaixunhuanScoreMap.get(maanshanMajiangPanPlayerResult.getPlayerId());
                tiwaixunhuanScore += maanshanMajiangPanPlayerResult.getTiwaixunhuanScore();
                playerTiwaixunhuanScoreMap.put(maanshanMajiangPanPlayerResult.getPlayerId(), tiwaixunhuanScore);
            }

            //试探一局是否结束
            MaanshanMajiangFixedPanNumbersJuFinishiDeterminer juFinishiDeterminer = (MaanshanMajiangFixedPanNumbersJuFinishiDeterminer) ju.getJuFinishiDeterminer();
            if (juFinishiDeterminer.determineToFinishJu(ju, this)) {
                finish();
                checkAndFinishPan();
            }

            result.setPanResult(panResult);
            if (state.name().equals(Finished.name)) { //局结束了
                result.setJuResult((MaanshanMajiangJuResult) ju.getJuResult());
            }
        }

        result.setMajiangGame(new MajiangGameValueObject(this));
        return result;
    }

    public MajiangActionResult automaticAction(String playerId, int actionId, long actionTime) throws Exception {
        ju.setCurrentDao(currentDao);
        PanActionFrame panActionFrame = ju.automaticAction(playerId, actionId, actionTime);
        MajiangActionResult result = new MajiangActionResult();
        result.setPanActionFrame(panActionFrame);
        if (state.name().equals(VoteNotPassWhenPlaying.name)) {
            state = new Playing();
        }

        checkAndFinishPan();

        if (state.name().equals(WaitingNextPan.name) || state.name().equals(Finished.name)) { //盘结束了
            MaanshanMajiangPanResult panResult = (MaanshanMajiangPanResult) ju.findLatestFinishedPanResult();
            boolean newDao = false;
            for (MaanshanMajiangPanPlayerResult maanshanMajiangPanPlayerResult : panResult.getPanPlayerResultList()) {
                if (maanshanMajiangPanPlayerResult.getTotalScore() <= 0) {
                    newDao = true;
                    break;
                }
            }

            if (newDao) {
                for (MaanshanMajiangPanPlayerResult maanshanMajiangPanPlayerResult : panResult.getPanPlayerResultList()) {
                    playeTotalScoreMap.put(maanshanMajiangPanPlayerResult.getPlayerId(), 50d);
                    List<Double> doubles = playerTotalDaoScoreMap.get(maanshanMajiangPanPlayerResult.getPlayerId());
                    doubles.set(currentDao - 1, maanshanMajiangPanPlayerResult.getTotalScore());
                    playerTotalDaoScoreMap.put(maanshanMajiangPanPlayerResult.getPlayerId(), doubles);
                }
                currentDao++;
            } else {
                for (MaanshanMajiangPanPlayerResult maanshanMajiangPanPlayerResult : panResult.getPanPlayerResultList()) {
                    playeTotalScoreMap.put(maanshanMajiangPanPlayerResult.getPlayerId(), maanshanMajiangPanPlayerResult.getTotalScore());
                    List<Double> doubles = playerTotalDaoScoreMap.get(maanshanMajiangPanPlayerResult.getPlayerId());
                    doubles.set(currentDao - 1, maanshanMajiangPanPlayerResult.getTotalScore());
                    playerTotalDaoScoreMap.put(maanshanMajiangPanPlayerResult.getPlayerId(), doubles);
                }
            }

            //试探一局是否结束
            MaanshanMajiangFixedPanNumbersJuFinishiDeterminer juFinishiDeterminer = (MaanshanMajiangFixedPanNumbersJuFinishiDeterminer) ju.getJuFinishiDeterminer();
            if (juFinishiDeterminer.determineToFinishJu(ju, this)) {
                finish();
                checkAndFinishPan();
            }

            result.setPanResult(panResult);
            if (state.name().equals(Finished.name)) { //局结束了
                result.setJuResult((MaanshanMajiangJuResult) ju.getJuResult());
            }
        }
        result.setMajiangGame(new MajiangGameValueObject(this));
        return result;
    }

    /**
     * 定缺
     *
     * @param playerId 玩家
     * @param quemen   缺一门类型
     */
    public MajiangActionResult dingque(String playerId, XushupaiCategory quemen) throws Exception {
        if (quemen == null) {
            throw new MajiangPlayerDingqueException();
        }

        if (state.name().equals(VoteNotPassWhenDingque.name)) {
            state = new DingqueState();
        }
        updatePlayerState(playerId, new PlayerAfterDingque());
        boolean start = true;
        MajiangPlayer majiangPlayer = ju.getCurrentPan().getMajiangPlayerIdMajiangPlayerMap().get(playerId);
        majiangPlayer.setQuemen(quemen);
        int dingqueOverPlayerCount = 0;
        Map<String, String> depositPlayerList = ju.getDepositPlayerList();
        for (MajiangPlayer player : ju.getCurrentPan().getMajiangPlayerIdMajiangPlayerMap().values()) {
            if (player.getQuemen() != null) {
                dingqueOverPlayerCount++;
            } else {
                start = false;
            }
        }
        if (dingqueOverPlayerCount + depositPlayerList.size() == ju.getCurrentPan().getMajiangPlayerIdMajiangPlayerMap().size()) {
            start = true;
            for (String tuogaunPlayerId : depositPlayerList.keySet()) {
                MajiangPlayer tuoguanPlayer = ju.getCurrentPan().getMajiangPlayerIdMajiangPlayerMap().get(tuogaunPlayerId);
                tuoguanPlayer.setQuemen(tuoguanPlayer.calculateQuemen());
            }
        }
        if (start) {
            state = new Playing();
            updateAllPlayersState(new PlayerPlaying());
            MaanshanMajiangMoActionUpdater moActionUpdater = (MaanshanMajiangMoActionUpdater) ju.getMoActionUpdater();
            moActionUpdater.updateActions(new MajiangMoAction(ju.getCurrentPan().getZhuangPlayerId(), new LundaoMopai()), ju);

            for (MajiangPlayer player : ju.getCurrentPan().getMajiangPlayerIdMajiangPlayerMap().values()) {
                MaanshanMajiangPaiOrderShoupaiSortComparator maanshanMajiangPaiOrderShoupaiSortComparator = new MaanshanMajiangPaiOrderShoupaiSortComparator(player.getQuemen());
                player.setFangruShoupaiListSortComparator(maanshanMajiangPaiOrderShoupaiSortComparator);
                player.getFangruShoupaiList().sort(maanshanMajiangPaiOrderShoupaiSortComparator);
            }

        }
        PanActionFrame panActionFrame = ju.getCurrentPan().recordPanActionFrame(new MajiangDingqueAction(playerId), System.currentTimeMillis());
        MajiangActionResult result = new MajiangActionResult();
        result.setPanActionFrame(panActionFrame);
        result.setMajiangGame(new MajiangGameValueObject(this));
        return result;
    }

    synchronized public MajiangActionResult automaticDingque(String playerId) throws Exception {
        if (state.name().equals(VoteNotPassWhenDingque.name)) {
            state = new DingqueState();
        }
        updatePlayerState(playerId, new PlayerAfterDingque());
        boolean start = true;
        MajiangPlayer majiangPlayer = ju.getCurrentPan().getMajiangPlayerIdMajiangPlayerMap().get(playerId);
        majiangPlayer.setQuemen(majiangPlayer.calculateQuemen());
        int dingqueOverPlayerCount = 0;
        Map<String, String> depositPlayerList = ju.getDepositPlayerList();
        for (MajiangPlayer player : ju.getCurrentPan().getMajiangPlayerIdMajiangPlayerMap().values()) {
            if (player.getQuemen() != null) {
                dingqueOverPlayerCount++;
            } else {
                start = false;
            }
        }
        if (dingqueOverPlayerCount + depositPlayerList.size() == ju.getCurrentPan().getMajiangPlayerIdMajiangPlayerMap().size()) {
            start = true;
            for (String tuogaunPlayerId : depositPlayerList.keySet()) {
                MajiangPlayer tuoguanPlayer = ju.getCurrentPan().getMajiangPlayerIdMajiangPlayerMap().get(tuogaunPlayerId);
                tuoguanPlayer.setQuemen(tuoguanPlayer.calculateQuemen());
            }
        }

        if (start) {
            state = new Playing();
            updateAllPlayersState(new PlayerPlaying());
            MaanshanMajiangMoActionUpdater moActionUpdater = (MaanshanMajiangMoActionUpdater) ju.getMoActionUpdater();
            moActionUpdater.updateActions(new MajiangMoAction(ju.getCurrentPan().getZhuangPlayerId(), new LundaoMopai()), ju);

            for (MajiangPlayer player : ju.getCurrentPan().getMajiangPlayerIdMajiangPlayerMap().values()) {
                MaanshanMajiangPaiOrderShoupaiSortComparator maanshanMajiangPaiOrderShoupaiSortComparator = new MaanshanMajiangPaiOrderShoupaiSortComparator(player.getQuemen());
                player.setFangruShoupaiListSortComparator(maanshanMajiangPaiOrderShoupaiSortComparator);
                player.getFangruShoupaiList().sort(maanshanMajiangPaiOrderShoupaiSortComparator);
            }

        }
        PanActionFrame panActionFrame = ju.getCurrentPan().recordPanActionFrame(new MajiangDingqueAction(playerId), System.currentTimeMillis());
        MajiangActionResult result = new MajiangActionResult();
        result.setPanActionFrame(panActionFrame);
        result.setMajiangGame(new MajiangGameValueObject(this));
        return result;
    }

    /**
     * 洗牌
     *
     * @param playerId 玩家ID
     */
    public MajiangGameValueObject xipai(String playerId) {
        xipaiPlayerIds.add(playerId);
        return new MajiangGameValueObject(this);
    }

    @Override
    protected boolean checkToFinishGame() throws Exception {
        return ju.getJuResult() != null;
    }

    @Override
    protected boolean checkToFinishCurrentPan() throws Exception {
        return ju.getCurrentPan() == null;
    }

    @Override
    protected void updatePlayerToExtendedVotingState(GamePlayer player) {
        if (player.getState().name().equals(PlayerDingque.name)) {
            player.setState(new PlayerVotingWhenDingque());
        } else if (player.getState().name().equals(PlayerAfterDingque.name)) {
            player.setState(new PlayerVotingWhenAfterDingque());
        }
    }

    @Override
    protected void updateToExtendedVotingState() {
        if (state.name().equals(DingqueState.name) || state.name().equals(VoteNotPassWhenDingque.name)) {
            state = new VotingWhenDingque();
        }
    }

    @Override
    protected void updatePlayerToExtendedVotedState(GamePlayer player) {
        if (player.getState().name().equals(PlayerVotingWhenDingque.name)) {
            player.setState(new PlayerVotedWhenDingque());
        } else if (player.getState().name().equals(PlayerVotingWhenAfterDingque.name)) {
            player.setState(new PlayerVotedWhenAfterDingque());
        }
    }

    @Override
    protected void recoveryPlayersStateFromExtendedVoting() throws Exception {
        if (state.name().equals(VoteNotPassWhenDingque.name)) {
            for (GamePlayer player : idPlayerMap.values()) {
                if (player.getState().name().equals(PlayerVotingWhenDingque.name) || player.getState().name().equals(PlayerVotedWhenDingque.name)) {
                    updatePlayerState(player.getId(), new PlayerDingque());
                } else if (player.getState().name().equals(PlayerVotingWhenAfterDingque.name) || player.getState().name().equals(PlayerVotedWhenAfterDingque.name)) {
                    updatePlayerState(player.getId(), new PlayerAfterDingque());
                }
            }
        }
    }

    @Override
    protected void updateToVoteNotPassStateFromExtendedVoting() throws Exception {
        if (state.name().equals(VotingWhenDingque.name)) {
            state = new VoteNotPassWhenDingque();
        }
    }

    @Override
    public MajiangGameValueObject toValueObject() {
        return new MajiangGameValueObject(this);
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

    public Ju getJu() {
        return ju;
    }

    public void setJu(Ju ju) {
        this.ju = ju;
    }

    public Map<String, Double> getPlayeTotalScoreMap() {
        return playeTotalScoreMap;
    }

    public void setPlayeTotalScoreMap(Map<String, Double> playeTotalScoreMap) {
        this.playeTotalScoreMap = playeTotalScoreMap;
    }

    public Set<String> getXipaiPlayerIds() {
        return xipaiPlayerIds;
    }

    public void setXipaiPlayerIds(Set<String> xipaiPlayerIds) {
        this.xipaiPlayerIds = xipaiPlayerIds;
    }

    public OptionalPlay getOptionalPlay() {
        return optionalPlay;
    }

    public void setOptionalPlay(OptionalPlay optionalPlay) {
        this.optionalPlay = optionalPlay;
    }

    public Double getDifen() {
        return difen;
    }

    public void setDifen(Double difen) {
        this.difen = difen;
    }

    public int getPowerLimit() {
        return powerLimit;
    }

    public void setPowerLimit(int powerLimit) {
        this.powerLimit = powerLimit;
    }

    public int getCurrentDao() {
        return currentDao;
    }

    public void setCurrentDao(int currentDao) {
        this.currentDao = currentDao;
    }

    public Map<String, List<Double>> getPlayerTotalDaoScoreMap() {
        return playerTotalDaoScoreMap;
    }

    public void setPlayerTotalDaoScoreMap(Map<String, List<Double>> playerTotalDaoScoreMap) {
        this.playerTotalDaoScoreMap = playerTotalDaoScoreMap;
    }

    public Map<String, Double> getPlayerTiwaixunhuanScoreMap() {
        return playerTiwaixunhuanScoreMap;
    }

    public void setPlayerTiwaixunhuanScoreMap(Map<String, Double> playerTiwaixunhuanScoreMap) {
        this.playerTiwaixunhuanScoreMap = playerTiwaixunhuanScoreMap;
    }

}
