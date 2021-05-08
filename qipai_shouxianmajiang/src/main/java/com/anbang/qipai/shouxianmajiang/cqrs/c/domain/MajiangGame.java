package com.anbang.qipai.shouxianmajiang.cqrs.c.domain;

import com.anbang.qipai.shouxianmajiang.cqrs.c.domain.listener.ShouxianMajiangPengGangActionStatisticsListener;
import com.anbang.qipai.shouxianmajiang.cqrs.c.domain.piao.*;
import com.dml.majiang.ju.Ju;
import com.dml.majiang.ju.finish.FixedPanNumbersJuFinishiDeterminer;
import com.dml.majiang.pan.Pan;
import com.dml.majiang.pan.frame.PanActionFrame;
import com.dml.majiang.pan.publicwaitingplayer.WaitDaPlayerPanPublicWaitingPlayerDeterminer;
import com.dml.majiang.player.action.chi.PengganghuFirstChiActionProcessor;
import com.dml.majiang.player.action.da.DachushoupaiDaActionProcessor;
import com.dml.majiang.player.action.gang.HuFirstBuGangActionProcessor;
import com.dml.majiang.player.action.guo.DoNothingGuoActionProcessor;
import com.dml.majiang.player.action.initial.ZhuangMoPaiInitialActionUpdater;
import com.dml.majiang.player.action.listener.comprehensive.GuoHuBuHuStatisticsListener;
import com.dml.majiang.player.action.listener.comprehensive.GuoPengBuPengStatisticsListener;
import com.dml.majiang.player.action.listener.comprehensive.TianHuAndDihuOpportunityDetector;
import com.dml.majiang.player.action.listener.gang.GuoGangBuGangStatisticsListener;
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

import java.util.*;

public class MajiangGame extends FixedPlayersMultipanAndVotetofinishGame {
    private int panshu;                  //盘数
    private int renshu;                  //人数
    private Double difen;                   //底分
    private int powerLimit;
    private OptionalPlay optionalPlay;   //麻将可选玩法
    private Ju ju;
    private Map<String, Double> playeTotalScoreMap = new HashMap<>();
    private Set<String> xipaiPlayerIds = new HashSet<>();
    private String lianmengId;
    private Map<String, MajiangPlayerXiapiaoState> playerXiapiaoStateMap = new HashMap<>();
    private Map<String, Integer> playerpiaofenMap = new HashMap<>();

    /**
     * 洗牌
     *
     * @param playerId 玩家ID
     * @return
     */
    public MajiangGameValueObject xipai(String playerId) {
        xipaiPlayerIds.add(playerId);
        return new MajiangGameValueObject(this);
    }

    public PanActionFrame findFirstPanActionFrame() {
        return ju.getCurrentPan().findLatestActionFrame();
    }

    /**
     * 创建局并开始第一盘
     *
     * @param currentTime 时间戳
     * @return
     * @throws Exception
     */
    public void createJuAndStartFirstPan(long currentTime) throws Exception {
        ju = new Ju();
        ju.setStartFirstPanProcess(new ShouxianMajiangStartFirstPanProcess());  //第一盘开始
        ju.setStartNextPanProcess(new ShouxianMajiangClassicStartNextPanProcess());    //下一盘开始
        ju.setPlayersMenFengDeterminerForFirstPan(new RandomMustHasDongPlayersMenFengDeterminer(currentTime));  //第一盘随机玩家东风
        ju.setPlayersMenFengDeterminerForNextPan(new ShouxianMajiangPlayersMenFengDeterminer());                       //下一盘胡牌坐东风
        ju.setZhuangDeterminerForFirstPan(new MenFengDongZhuangDeterminer());   //第一盘东风坐庄
        ju.setZhuangDeterminerForNextPan(new MenFengDongZhuangDeterminer());    //下一盘东风坐庄
        ju.setAvaliablePaiFiller(new ShouxianMajiangRemoveZhongOrFengFaRandomAvaliablePaiFiller(currentTime + 2, optionalPlay));   //填充可用牌
        ju.setGuipaiDeterminer(new ShouxianMajiangGuipaiDeterminer(currentTime + 3, true));             //鬼牌（财神、混）
        ju.setFaPaiStrategy(new ShouxianMajiangFaPaiStrategy(13));                            //顺序发牌
        ju.setCurrentPanFinishiDeterminer(new ShouxianMajiangPanFinishiDeterminer());                              //局结束条件
        ju.setGouXingPanHu(new ShouxianMajiangGouXingPanHuWithoutQidui());                                                     //可胡牌构型

        ju.setCurrentPanPublicWaitingPlayerDeterminer(new WaitDaPlayerPanPublicWaitingPlayerDeterminer());  //等待出牌
        ju.setJuFinishiDeterminer(new FixedPanNumbersJuFinishiDeterminer(panshu));                          //当前局结束条件
        ju.setJuResultBuilder(new ShouxianMajiangJuResultBuilder());                                               //局记分结果
        ju.setInitialActionUpdater(new ZhuangMoPaiInitialActionUpdater());                                  //庄家摸牌
        ShouxianMajiangPanResultBuilder tuiDaoHuPanResultBuilder = new ShouxianMajiangPanResultBuilder();                 //盘记分结果
        tuiDaoHuPanResultBuilder.setDifen(difen);
        tuiDaoHuPanResultBuilder.setOptionalPlay(optionalPlay);
        tuiDaoHuPanResultBuilder.setPlayerpiaofenMap(playerpiaofenMap);
        ju.setCurrentPanResultBuilder(tuiDaoHuPanResultBuilder);
        ShouxianMajiangHuPaiSolutionsTipsFilter tuiDaoHuHuPaiSolutionsTipsFilter = new ShouxianMajiangHuPaiSolutionsTipsFilter();
        tuiDaoHuHuPaiSolutionsTipsFilter.setOptionalPlay(optionalPlay);
        ju.setHupaiPaixingSolutionFilter(tuiDaoHuHuPaiSolutionsTipsFilter);               //胡牌提示

        ju.setMoActionProcessor(new ShouxianMajiangMoActionProcessor());                         //摸牌动作处理
        ju.setMoActionUpdater(new ShouxianMajiangMoActionUpdater());                             //摸牌动作更新
        ju.setDaActionProcessor(new DachushoupaiDaActionProcessor());                     //打牌动作处理
        ShouxianMajiangDaActionUpdater daUpdater = new ShouxianMajiangDaActionUpdater();                //打牌动作处理
        daUpdater.setDianpao(true);//只可自摸
        ju.setDaActionUpdater(daUpdater);
        ju.setChiActionProcessor(new PengganghuFirstChiActionProcessor());                //吃牌动作处理
        ju.setChiActionUpdater(new ShouxianMajiangChiActionUpdater());                           //吃牌动作处理
        ju.setPengActionProcessor(new HuFirstBuPengActionProcessor());                    //碰牌动作处理
        ju.setPengActionUpdater(new ShouxianMajiangPengActionUpdater());                         //碰牌动作处理
        ju.setGangActionProcessor(new HuFirstBuGangActionProcessor());                    //杠牌动作处理
        ShouxianMajiangGangActionUpdater tuiDaoHuGangActionUpdater = new ShouxianMajiangGangActionUpdater();
//        tuiDaoHuGangActionUpdater.setQiangGang(optionalPlay.isQiangganghu());
        ju.setGangActionUpdater(tuiDaoHuGangActionUpdater);                         //杠牌动作处理
        ju.setGuoActionProcessor(new DoNothingGuoActionProcessor());                      //过牌动作处理
        ju.setGuoActionUpdater(new ShouxianMajiangGuoActionUpdater());                           //过牌动作处理
        ju.setHuActionProcessor(new ShouxianMajiangHuActionProcessor());                         //胡牌动作处理
        ju.setHuActionUpdater(new ShouxianMajiangClearAllActionHuActionUpdater());               //胡牌动作处理

        ju.addActionStatisticsListener(new ShouxianMajiangPengGangActionStatisticsListener());           //杠统计监测器
        ju.addActionStatisticsListener(new ShouxianMajiangLastMoActionPlayerRecorder());                 //最后摸牌监测器
        ju.addActionStatisticsListener(new TianHuAndDihuOpportunityDetector());                   //天胡地胡监测器
        ju.addActionStatisticsListener(new GuoHuBuHuStatisticsListener());                        //过胡不胡监测器
        ju.addActionStatisticsListener(new GuoPengBuPengStatisticsListener());                    //过碰不碰监测器
        ju.addActionStatisticsListener(new GuoGangBuGangStatisticsListener());                    //过杠不杠监测器

        Pan firstPan = new Pan();
        firstPan.setNo(1);
        allPlayerIds().forEach(firstPan::addPlayer);
        ju.setCurrentPan(firstPan);
        if (optionalPlay.isJiaopao()) {
            allPlayerIds().forEach((pid)->playerpiaofenMap.put(pid, 0));
            allPlayerIds().forEach((pid)->playerXiapiaoStateMap.put(pid, MajiangPlayerXiapiaoState.waitForxiapiao));
        } else {
            allPlayerIds().forEach((pid)->playerpiaofenMap.put(pid, 0));
            ju.startFirstPan(allPlayerIds());
        }

    }

    /**
     * 行牌
     *
     * @param playerId   玩家ID
     * @param actionId   动作ID
     * @param actionNo   动作编号
     * @param actionTime 动作时间
     * @return
     * @throws Exception
     */
    public MajiangActionResult action(String playerId, int actionId, int actionNo, long actionTime) throws Exception {
        PanActionFrame panActionFrame = ju.action(playerId, actionId, actionNo, actionTime);
        MajiangActionResult result = new MajiangActionResult();
        result.setPanActionFrame(panActionFrame);
        if (state.name().equals(VoteNotPassWhenPlaying.name)) {
            state = new Playing();
        }
        if (!xipaiPlayerIds.isEmpty()) {
            xipaiPlayerIds.clear();
        }
        checkAndFinishPan();

        if (state.name().equals(WaitingNextPan.name) || state.name().equals(Finished.name)) { //盘结束了
            ShouxianMajiangPanResult panResult = (ShouxianMajiangPanResult) ju.findLatestFinishedPanResult();
            for (ShouxianMajiangPanPlayerResult tuiDaoHuPanPlayerResult : panResult.getPanPlayerResultList()) {
                playeTotalScoreMap.put(tuiDaoHuPanPlayerResult.getPlayerId(), tuiDaoHuPanPlayerResult.getTotalScore());
            }
            result.setPanResult(panResult);
            if (state.name().equals(Finished.name)) { //局结束了
                result.setJuResult((ShouxianMajiangJuResult) ju.getJuResult());
            }
        }
        result.setMajiangGame(new MajiangGameValueObject(this));
        return result;
    }

    public MajiangActionResult automaticAction(String playerId, int actionId, long actionTime) throws Exception {
        PanActionFrame panActionFrame = ju.automaticAction(playerId, actionId, actionTime);
        MajiangActionResult result = new MajiangActionResult();
        result.setPanActionFrame(panActionFrame);
        if (state.name().equals(VoteNotPassWhenPlaying.name)) {
            state = new Playing();
        }
        checkAndFinishPan();
        if (state.name().equals(WaitingNextPan.name) || state.name().equals(Finished.name)) {// 盘结束了
            ShouxianMajiangPanResult panResult = (ShouxianMajiangPanResult) ju.findLatestFinishedPanResult();
            for (ShouxianMajiangPanPlayerResult yingjiuzhangPanPlayerResult : panResult.getPanPlayerResultList()) {
                playeTotalScoreMap.put(yingjiuzhangPanPlayerResult.getPlayerId(), yingjiuzhangPanPlayerResult.getTotalScore());
            }
            result.setPanResult(panResult);
            if (state.name().equals(Finished.name)) {// 局结束了
                result.setJuResult((ShouxianMajiangJuResult) ju.getJuResult());
            }
        }
        result.setMajiangGame(new MajiangGameValueObject(this));
        return result;
    }

    public XiapiaoResult xiapiao(String playerId, int piaofen) throws Exception {
        XiapiaoResult xiapiaoResult = new XiapiaoResult();
        List<String> playerIdList = new ArrayList<>(this.playerpiaofenMap.keySet());
        playerpiaofenMap.put(playerId,piaofen);
        this.playerpiaofenMap.put(playerId,piaofen);
        this.playerXiapiaoStateMap.put(playerId,MajiangPlayerXiapiaoState.over);
        if (state.name().equals(VoteNotPassWhenXiapiao.name)) {
            state = new XiapiaoState();
        }
        updatePlayerState(playerId, new PlayerAfterXiapiao());
        boolean start = true;
        int xiaopiaoOverPlayerCount=0;
        Map<String, String> depositPlayerList = ju.getDepositPlayerList();
        for (String pid : playerIdList) {
            if (MajiangPlayerXiapiaoState.waitForxiapiao.equals(this.playerXiapiaoStateMap.get(pid))) {
                start = false;
            }else if (MajiangPlayerXiapiaoState.over.equals(this.playerXiapiaoStateMap.get(pid))){
                xiaopiaoOverPlayerCount++;
            }
        }
        if (xiaopiaoOverPlayerCount+depositPlayerList.size()==ju.getCurrentPan().getMajiangPlayerIdMajiangPlayerMap().size()){
            start=true;
            for (String tuogaunPlayerId:depositPlayerList.keySet()) {
                this.playerXiapiaoStateMap.put(tuogaunPlayerId,MajiangPlayerXiapiaoState.over);
            }
        }
        if (start) {
            if (ju.getCurrentPan().getNo()==1){
                ju.startFirstPan(allPlayerIds());
            }else {
                ju.startNextPan();
            }
            state = new Playing();
            updateAllPlayersState(new PlayerPlaying());
            PanActionFrame firstActionFrame=ju.getCurrentPan().findLatestActionFrame();
            xiapiaoResult.setFirstActionFrame(firstActionFrame);
        }
        MajiangGameValueObject majiangGame = new MajiangGameValueObject(this);
        majiangGame.setPlayerXiapiaoStateMap(playerXiapiaoStateMap);
        majiangGame.setPlayerpiaofenMap(playerpiaofenMap);
        xiapiaoResult.setMajiangGame(majiangGame);
        return xiapiaoResult;
    }


    @Override
    public void start(long currentTime) throws Exception {
        createJuAndStartFirstPan(currentTime);
        if (optionalPlay.isJiaopao()) {
            state = new XiapiaoState();
            updateAllPlayersState(new PlayerXiapiao());
        } else {
            state = new Playing();
            updateAllPlayersState(new PlayerPlaying());
        }
    }

    @Override
    protected void startNextPan() throws Exception {
        ju.startNextPan();
        state = new Playing();
        updateAllPlayersState(new PlayerPlaying());
    }

    @Override
    public void finish() throws Exception {
        if (ju != null) {
            ju.finish();
        }
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
        if (player.getState().name().equals(PlayerXiapiao.name)) {
            player.setState(new PlayerVotingWhenXiapiao());
        } else if (player.getState().name().equals(PlayerAfterXiapiao.name)) {
            player.setState(new PlayerVotingWhenAfterXiapiao());
        }
    }

    @Override
    protected void updateToExtendedVotingState() {
        if (state.name().equals(XiapiaoState.name) || state.name().equals(VoteNotPassWhenXiapiao.name)) {
            state = new VotingWhenXiapiao();
        }
    }

    @Override
    protected void updatePlayerToExtendedVotedState(GamePlayer player) {
        if (player.getState().name().equals(PlayerVotingWhenXiapiao.name)) {
            player.setState(new PlayerVotedWhenXiapiao());
        } else if (player.getState().name().equals(PlayerVotingWhenAfterXiapiao.name)) {
            player.setState(new PlayerVotedWhenAfterXiapiao());
        }
    }

    @Override
    protected void recoveryPlayersStateFromExtendedVoting() throws Exception {
        if (state.name().equals(VoteNotPassWhenXiapiao.name)) {
            for (GamePlayer player : idPlayerMap.values()) {
                if (player.getState().name().equals(PlayerVotingWhenXiapiao.name)
                        || player.getState().name().equals(PlayerVotedWhenXiapiao.name)) {
                    updatePlayerState(player.getId(), new PlayerXiapiao());
                } else if (player.getState().name().equals(PlayerVotingWhenAfterXiapiao.name)
                        || player.getState().name().equals(PlayerVotedWhenAfterXiapiao.name)) {
                    updatePlayerState(player.getId(), new PlayerAfterXiapiao());
                }
            }
        }
    }

    @Override
    protected void updateToVoteNotPassStateFromExtendedVoting() throws Exception {
        if (state.name().equals(VotingWhenXiapiao.name)) {
            state = new VoteNotPassWhenXiapiao();
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

    public String getLianmengId() {
        return lianmengId;
    }

    public void setLianmengId(String lianmengId) {
        this.lianmengId = lianmengId;
    }

    public Map<String, MajiangPlayerXiapiaoState> getPlayerXiapiaoStateMap() {
        return playerXiapiaoStateMap;
    }

    public void setPlayerXiapiaoStateMap(Map<String, MajiangPlayerXiapiaoState> playerXiapiaoStateMap) {
        this.playerXiapiaoStateMap = playerXiapiaoStateMap;
    }

    public Map<String, Integer> getPlayerpiaofenMap() {
        return playerpiaofenMap;
    }

    public void setPlayerpiaofenMap(Map<String, Integer> playerpiaofenMap) {
        this.playerpiaofenMap = playerpiaofenMap;
    }
}
