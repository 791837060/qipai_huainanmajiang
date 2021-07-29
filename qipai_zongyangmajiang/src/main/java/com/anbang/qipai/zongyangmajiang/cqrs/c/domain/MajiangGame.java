package com.anbang.qipai.zongyangmajiang.cqrs.c.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.anbang.qipai.zongyangmajiang.cqrs.c.domain.listener.ZongyangMajiangPengGangActionStatisticsListener;
import com.anbang.qipai.zongyangmajiang.cqrs.c.domain.listener.ZongyangMajiangRandomMustHasDongPlayersMenFengDeterminer;
import com.anbang.qipai.zongyangmajiang.cqrs.c.domain.test.ZongyangMajiangFaPaiStrategyTest;
import com.dml.majiang.ju.Ju;
import com.dml.majiang.ju.finish.FixedPanNumbersJuFinishiDeterminer;

import com.dml.majiang.ju.firstpan.HasGuipaiStartFirstPanProcess;
import com.dml.majiang.ju.nextpan.HasGuipaiStartNextPanProcess;
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
    private Double difen;                   //底分
    private int powerLimit;
    private OptionalPlay optionalPlay;   //麻将可选玩法
    private Ju ju;
    private Map<String, Double> playeTotalScoreMap = new HashMap<>();
    private Set<String> xipaiPlayerIds = new HashSet<>();
    private String lianmengId;
    private String createplayId;

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

    /**
     * 创建局并开始第一盘
     *
     * @param currentTime 时间戳
     * @return
     * @throws Exception
     */
    public PanActionFrame createJuAndStartFirstPan(long currentTime) throws Exception {
        ju = new Ju();
        ju.setStartFirstPanProcess(new HasGuipaiStartFirstPanProcess());  //第一盘开始
        ju.setStartNextPanProcess(new HasGuipaiStartNextPanProcess());    //下一盘开始
        ju.setPlayersMenFengDeterminerForFirstPan(new ZongyangMajiangRandomMustHasDongPlayersMenFengDeterminer(currentTime, createplayId));  //第一盘建房玩家东风
        ju.setPlayersMenFengDeterminerForNextPan(new ZongyangMajiangPlayersMenFengDeterminer());                       //下一盘胡牌坐东风
        ju.setZhuangDeterminerForFirstPan(new MenFengDongZhuangDeterminer());   //第一盘东风坐庄
        ju.setZhuangDeterminerForNextPan(new MenFengDongZhuangDeterminer());    //下一盘东风坐庄
        ju.setAvaliablePaiFiller(new ZongyangMajiangRemoveZhongOrFengFaRandomAvaliablePaiFiller(currentTime + 2, optionalPlay));   //填充可用牌
        ju.setGuipaiDeterminer(new ZongyangMajiangGuipaiDeterminer(currentTime + 3, true));             //鬼牌（财神、混）
        ju.setFaPaiStrategy(new ZongyangMajiangFaPaiStrategyTest(13));                            //顺序发牌
        ju.setCurrentPanFinishiDeterminer(new ZongyangMajiangPanFinishiDeterminer());                              //局结束条件
        ju.setGouXingPanHu(new ZongyangMajiangGouXingPanHuWithoutQidui());                                                     //可胡牌构型
        ju.setCurrentPanPublicWaitingPlayerDeterminer(new WaitDaPlayerPanPublicWaitingPlayerDeterminer());  //等待出牌
        ju.setJuFinishiDeterminer(new FixedPanNumbersJuFinishiDeterminer(panshu));                          //当前局结束条件
        ju.setJuResultBuilder(new ZongyangMajiangJuResultBuilder());                                               //局记分结果
        ju.setInitialActionUpdater(new ZhuangMoPaiInitialActionUpdater());                                  //庄家摸牌
        ZongyangMajiangPanResultBuilder tuiDaoHuPanResultBuilder = new ZongyangMajiangPanResultBuilder();                 //盘记分结果
        tuiDaoHuPanResultBuilder.setDifen(difen);
        tuiDaoHuPanResultBuilder.setOptionalPlay(optionalPlay);
        ju.setCurrentPanResultBuilder(tuiDaoHuPanResultBuilder);
        ZongyangMajiangHuPaiSolutionsTipsFilter tuiDaoHuHuPaiSolutionsTipsFilter = new ZongyangMajiangHuPaiSolutionsTipsFilter();
        tuiDaoHuHuPaiSolutionsTipsFilter.setOptionalPlay(optionalPlay);
        ju.setHupaiPaixingSolutionFilter(tuiDaoHuHuPaiSolutionsTipsFilter);               //胡牌提示

        ju.setMoActionProcessor(new ZongyangMajiangMoActionProcessor());                         //摸牌动作处理
        ju.setMoActionUpdater(new ZongyangMajiangMoActionUpdater());                             //摸牌动作更新
        ju.setDaActionProcessor(new DachushoupaiDaActionProcessor());                     //打牌动作处理
        ZongyangMajiangDaActionUpdater daUpdater = new ZongyangMajiangDaActionUpdater();                //打牌动作处理
        daUpdater.setDianpao(false);//只可自摸
        ju.setDaActionUpdater(daUpdater);
        ju.setChiActionProcessor(new PengganghuFirstChiActionProcessor());                //吃牌动作处理
        ju.setChiActionUpdater(new ZongyangMajiangChiActionUpdater());                           //吃牌动作更新
        ju.setPengActionProcessor(new HuFirstBuPengActionProcessor());                    //碰牌动作处理
        ju.setPengActionUpdater(new ZongyangMajiangPengActionUpdater());                         //碰牌动作更新
        ju.setGangActionProcessor(new HuFirstBuGangActionProcessor());                    //杠牌动作处理
        ZongyangMajiangGangActionUpdater tuiDaoHuGangActionUpdater = new ZongyangMajiangGangActionUpdater();
        ju.setGangActionUpdater(tuiDaoHuGangActionUpdater);                         //杠牌动作更新
        ju.setGuoActionProcessor(new DoNothingGuoActionProcessor());                      //过牌动作处理
        ju.setGuoActionUpdater(new ZongyangMajiangGuoActionUpdater());                           //过牌动作更新
        ju.setHuActionProcessor(new ZongyangMajiangHuActionProcessor());                         //胡牌动作处理
        ju.setHuActionUpdater(new ZongyangMajiangClearAllActionHuActionUpdater());               //胡牌动作更新

        ju.addActionStatisticsListener(new ZongyangMajiangPengGangActionStatisticsListener());           //杠统计监测器
        ju.addActionStatisticsListener(new ZongyangMajiangLastMoActionPlayerRecorder());                 //最后摸牌监测器
        ju.addActionStatisticsListener(new TianHuAndDihuOpportunityDetector());                   //天胡地胡监测器
        ju.addActionStatisticsListener(new GuoHuBuHuStatisticsListener());                        //过胡不胡监测器
        ju.addActionStatisticsListener(new GuoPengBuPengStatisticsListener());                    //过碰不碰监测器
        ju.addActionStatisticsListener(new GuoGangBuGangStatisticsListener());                    //过杠不杠监测器

        // 开始第一盘
        ju.startFirstPan(allPlayerIds());

        // 必然庄家已经先摸了一张牌了
        return ju.getCurrentPan().findLatestActionFrame();
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
            ZongyangMajiangPanResult panResult = (ZongyangMajiangPanResult) ju.findLatestFinishedPanResult();
            for (ZongyangMajiangPanPlayerResult tuiDaoHuPanPlayerResult : panResult.getPanPlayerResultList()) {
                playeTotalScoreMap.put(tuiDaoHuPanPlayerResult.getPlayerId(), tuiDaoHuPanPlayerResult.getTotalScore());
            }
            result.setPanResult(panResult);
            if (state.name().equals(Finished.name)) { //局结束了
                result.setJuResult((ZongyangMajiangJuResult) ju.getJuResult());
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
            ZongyangMajiangPanResult panResult = (ZongyangMajiangPanResult) ju.findLatestFinishedPanResult();
            for (ZongyangMajiangPanPlayerResult yingjiuzhangPanPlayerResult : panResult.getPanPlayerResultList()) {
                playeTotalScoreMap.put(yingjiuzhangPanPlayerResult.getPlayerId(), yingjiuzhangPanPlayerResult.getTotalScore());
            }
            result.setPanResult(panResult);
            if (state.name().equals(Finished.name)) {// 局结束了
                result.setJuResult((ZongyangMajiangJuResult) ju.getJuResult());
            }
        }
        result.setMajiangGame(new MajiangGameValueObject(this));
        return result;
    }


    @Override
    public void start(long currentTime) throws Exception {
        xipaiPlayerIds.clear();
        state = new Playing();
        updateAllPlayersState(new PlayerPlaying());
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
        // TODO Auto-generated method stub
    }

    @Override
    protected void updateToExtendedVotingState() {
        // TODO Auto-generated method stub
    }

    @Override
    protected void updatePlayerToExtendedVotedState(GamePlayer player) {
        // TODO Auto-generated method stub
    }

    @Override
    protected void recoveryPlayersStateFromExtendedVoting() throws Exception {
        // TODO Auto-generated method stub
    }

    @Override
    protected void updateToVoteNotPassStateFromExtendedVoting() throws Exception {
        // TODO Auto-generated method stub
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

    public String getCreateplayId() {
        return createplayId;
    }

    public void setCreateplayId(String createplayId) {
        this.createplayId = createplayId;
    }
}
