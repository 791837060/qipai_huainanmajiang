package com.anbang.qipai.biji.cqrs.c.domain;

import com.anbang.qipai.biji.cqrs.c.domain.result.BijiJuResult;
import com.anbang.qipai.biji.cqrs.c.domain.result.BijiPanPlayerResult;
import com.anbang.qipai.biji.cqrs.c.domain.result.BijiPanResult;
import com.anbang.qipai.biji.cqrs.c.domain.result.PukeActionResult;
import com.anbang.qipai.biji.utils.SpringUtil;
import com.dml.mpgame.game.Finished;
import com.dml.mpgame.game.Playing;
import com.dml.mpgame.game.extend.fpmpv.FixedPlayersMultipanAndVotetofinishGame;
import com.dml.mpgame.game.extend.multipan.WaitingNextPan;
import com.dml.mpgame.game.extend.vote.VoteNotPassWhenPlaying;
import com.dml.mpgame.game.player.GamePlayer;
import com.dml.mpgame.game.player.PlayerPlaying;
import com.dml.shisanshui.gameprocess.AllPlayerChupaiPanFinishiDeterminer;
import com.dml.shisanshui.gameprocess.FixedPanNumbersJuFinishiDeterminer;
import com.dml.shisanshui.ju.Ju;
import com.dml.shisanshui.pai.paixing.Dao;
import com.dml.shisanshui.pai.paixing.comparator.TypeCodeDaoComparator;
import com.dml.shisanshui.pan.Pan;
import com.dml.shisanshui.pan.PanActionFrame;
import com.dml.shisanshui.player.ShisanshuiPlayer;
import com.dml.shisanshui.position.Position;
import com.dml.shisanshui.preparedapai.fapai.EveryPlayerShisanzhangFapaiStrategy;
import com.dml.shisanshui.preparedapai.lipai.DianshuOrHuaseShoupaiSortStrategy;
import com.dml.shisanshui.preparedapai.luanpai.RandomLuanPaiStrategy;
import com.dml.shisanshui.preparedapai.zuowei.JoinGameZuoweiDeterminer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PukeGame extends FixedPlayersMultipanAndVotetofinishGame {
    private int panshu;                 //盘数
    private int renshu;                 //人数
    private OptionalPlay optionalPlay;  //玩法
    private Ju ju;                      //局
    private Double difen;               //底分
    private int powerLimit;             //淘汰分

    private Map<String, Double> playerTotalScoreMap = new HashMap<>();
    private Map<String, Position> playerIdPositionMap = new HashMap<>();

    /**
     * 创建局并开始第一盘
     */
    public PanActionFrame createJuAndStartFirstPan(long startTime) throws Exception {
        ju = new Ju();

        ju.setCurrentPanFinishiDeterminer(new AllPlayerChupaiPanFinishiDeterminer());                                       //盘结束决定器
        ju.setJuFinishiDeterminer(new FixedPanNumbersJuFinishiDeterminer(panshu));                                          //盘固定局数决定器
        ju.setAvaliablePaiFiller(new BijiAvaliablePaiFiller());                                                             //可用牌
        ju.setLuanpaiStrategyForFirstPan(new RandomLuanPaiStrategy(startTime));                                             //首盘乱牌策略
        ju.setLuanpaiStrategyForNextPan(new RandomLuanPaiStrategy(startTime + 1));                                     //下一盘乱牌策略
        ju.setFapaiStrategyForFirstPan(new EveryPlayerShisanzhangFapaiStrategy());                                          //首盘发牌策略
        ju.setFapaiStrategyForNextPan(new EveryPlayerShisanzhangFapaiStrategy());                                           //下一盘发牌策略
        ju.setShoupaiSortStrategy(new DianshuOrHuaseShoupaiSortStrategy());                                                 //理牌策略
        ju.setChupaiDaoCalculator(new BijiChupaiDaoCalculator());                                                           //牌型计算器

        JoinGameZuoweiDeterminer joinGameZuoweiDeterminer = new JoinGameZuoweiDeterminer();
        joinGameZuoweiDeterminer.setPlayerIdPositionMap(playerIdPositionMap);
        ju.setZuoweiDeterminer(joinGameZuoweiDeterminer);                                                                   //决定玩家座位

        TypeCodeDaoComparator typeCodeDaoComparator = new TypeCodeDaoComparator();
        ju.setDaoComparator(typeCodeDaoComparator);                                                                         //道比较器（编码）

        BijiChupaiPaixingSolutionFilter bijiChupaiPaixingSolutionFilter = new BijiChupaiPaixingSolutionFilter();
        bijiChupaiPaixingSolutionFilter.setDaoComparator(typeCodeDaoComparator);
        ju.setChupaiPaixingSolutionFilter(bijiChupaiPaixingSolutionFilter);                                                 //出牌提示

        BijiCurrentPanResultBuilder bijiCurrentPanResultBuilder = new BijiCurrentPanResultBuilder();
        bijiCurrentPanResultBuilder.setDaoComparator(typeCodeDaoComparator);
        bijiCurrentPanResultBuilder.setRenshu(renshu);
        bijiCurrentPanResultBuilder.setOptionalPlay(optionalPlay);
        bijiCurrentPanResultBuilder.setDifen(difen);
        ju.setCurrentPanResultBuilder(bijiCurrentPanResultBuilder);                                                         //盘结果
        ju.setJuResultBuilder(new BijiJuResultBuilder());                                                                   //局结果

        PanActionFrame panActionFrame = ju.startFirstPan(allPlayerIds(), startTime);

        //正常模式0分开始 进园子以园子分开始
        for (ShisanshuiPlayer player : ju.getCurrentPan().getPlayerIdPlayerMap().values()) {
            if (optionalPlay.isJinyuanzi()) {
                playerTotalScoreMap.put(player.getId(), (double) optionalPlay.getYuanzifen());
                player.setPlayerTotalScore(optionalPlay.getYuanzifen());
            } else {
                playerTotalScoreMap.put(player.getId(), 0d);
                player.setPlayerTotalScore(0);
            }

        }

        return panActionFrame;
    }

    /**
     * 更新玩家门风
     *
     * @param playerId 玩家ID
     */
    public void updatePlayerPosition(String playerId) {
        List<Position> positionList = new ArrayList<>();
        if (renshu == 2) {
            positionList.add(Position.dong);
            positionList.add(Position.xi);
        } else if (renshu == 3) {
            positionList.add(Position.dong);
            positionList.add(Position.nan);
            positionList.add(Position.xi);
        } else if (renshu == 4) {
            positionList.add(Position.dong);
            positionList.add(Position.nan);
            positionList.add(Position.xi);
            positionList.add(Position.bei);
        } else if (renshu == 5) {
            positionList.add(Position.dong);
            positionList.add(Position.nan);
            positionList.add(Position.xi);
            positionList.add(Position.bei);
            positionList.add(Position.zhong);
        }
        Map<String, Position> positionMap = new HashMap<>();
        for (String pid : playerIdPositionMap.keySet()) {
            if (idPlayerMap.containsKey(pid)) {
                positionMap.put(pid, playerIdPositionMap.get(pid));
                positionList.remove(playerIdPositionMap.get(pid));
            }
        }
        playerIdPositionMap = positionMap;
        if (!positionList.isEmpty()) {
            playerIdPositionMap.put(playerId, positionList.remove(0));
        }
    }

    /**
     * 出牌
     *
     * @param playerId      玩家ID
     * @param toudaoIndex   头道编码
     * @param zhongdaoIndex 中道编码
     * @param weidaoIndex   尾道编码
     * @param actionTime    时间戳
     */
    public PukeActionResult chupai(String playerId, String toudaoIndex, String zhongdaoIndex, String weidaoIndex, long actionTime) throws Exception {
        PanActionFrame panActionFrame = ju.chupai(playerId, toudaoIndex, zhongdaoIndex, weidaoIndex, actionTime, optionalPlay.isJinyuanzi());
        PukeActionResult result = new PukeActionResult();
        result.setPanActionFrame(panActionFrame);
        if (state.name().equals(VoteNotPassWhenPlaying.name)) {
            state = new Playing();
        }

        if (ju.getCurrentPan() != null) {
            Map<String, String> depositPlayerList = ju.getDepositPlayerList();
            Map<String, ShisanshuiPlayer> playerIdPlayerMap = ju.getCurrentPan().getPlayerIdPlayerMap();
            playerIdPlayerMap.get(playerId).setTuoguanChupai(true);
            for (String tuoguanPlayerId : depositPlayerList.keySet()) {
                ShisanshuiPlayer player = playerIdPlayerMap.get(tuoguanPlayerId);
                if (!player.isTuoguanChupai()) {
                    executorService.submit(() -> {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        automatic.autoChupai(tuoguanPlayerId, depositPlayerList.get(tuoguanPlayerId));
                        player.setTuoguanChupai(true);
                    });
                }
            }
        }

        checkAndFinishPan();

        if (state.name().equals(WaitingNextPan.name) || state.name().equals(Finished.name)) {// 盘结束了
            BijiPanResult panResult = (BijiPanResult) ju.findLatestFinishedPanResult();
            for (BijiPanPlayerResult doudizhuPanPlayerResult : panResult.getPanPlayerResultList()) {
                playerTotalScoreMap.put(doudizhuPanPlayerResult.getPlayerId(), doudizhuPanPlayerResult.getTotalScore());
            }
            result.setPanResult(panResult);
            if (state.name().equals(Finished.name)) {// 局结束了
                result.setJuResult((BijiJuResult) ju.getJuResult());
            }
        }
        result.setPukeGame(new PukeGameValueObject(this));
        return result;
    }

    private final Automatic automatic = SpringUtil.getBean(Automatic.class);
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * 自动出牌
     *
     * @param playerId   游戏ID
     * @param actionTime 执行动作时间戳
     */
    public PukeActionResult autoChupai(String playerId, Long actionTime) throws Exception {
        Map<String, String> depositPlayerList = ju.getDepositPlayerList();
        PanActionFrame panActionFrame;
        PukeActionResult result = new PukeActionResult();
        if (depositPlayerList.containsKey(playerId)) {
            panActionFrame = ju.autoChupai(playerId, actionTime, optionalPlay.isJinyuanzi());
            result.setPanActionFrame(panActionFrame);
        }
        if (state.name().equals(VoteNotPassWhenPlaying.name)) {
            state = new Playing();
        }

        if (ju.getCurrentPan() != null) {
            Map<String, ShisanshuiPlayer> playerIdPlayerMap = ju.getCurrentPan().getPlayerIdPlayerMap();
            if (depositPlayerList.containsKey(playerId)) {
                playerIdPlayerMap.get(playerId).setTuoguanChupai(true);
            }
            for (String tuoguanPlayerId : depositPlayerList.keySet()) {
                ShisanshuiPlayer player = playerIdPlayerMap.get(tuoguanPlayerId);
                if (!player.isTuoguanChupai()) {
                    player.setTuoguanChupai(true);
                    if (player.getChupaiSolutionForTips().isEmpty()) {
                        break;
                    }
                    executorService.submit(() -> {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        automatic.autoChupai(tuoguanPlayerId, depositPlayerList.get(tuoguanPlayerId));
                    });
                }
            }
        }

        checkAndFinishPan();

        if (state.name().equals(WaitingNextPan.name) || state.name().equals(Finished.name)) {// 盘结束了
            BijiPanResult panResult = (BijiPanResult) ju.findLatestFinishedPanResult();
            for (BijiPanPlayerResult doudizhuPanPlayerResult : panResult.getPanPlayerResultList()) {
                playerTotalScoreMap.put(doudizhuPanPlayerResult.getPlayerId(), doudizhuPanPlayerResult.getTotalScore());
            }
            result.setPanResult(panResult);
            if (state.name().equals(Finished.name)) {// 局结束了
                result.setJuResult((BijiJuResult) ju.getJuResult());
            }
        }
        result.setPukeGame(new PukeGameValueObject(this));
        return result;
    }

    public Dao findDaoByPlayerIdAndIndex(String playerId, String index) throws Exception {
        return ju.findDaoByPlayerIdAndIndex(playerId, index);
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
    protected void startNextPan() throws Exception {
        ju.startNextPan();
        state = new Playing();
        updateAllPlayersState(new PlayerPlaying());
    }

    @Override
    public void start(long currentTime) throws Exception {
        state = new Playing();
        updateAllPlayersState(new PlayerPlaying());
    }

    @Override
    public void finish() throws Exception {
        if (ju != null) {
            ju.finish();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public PukeGameValueObject toValueObject() {
        return new PukeGameValueObject(this);
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

    public void setPlayerTotalScoreMap(Map<String, Double> playerTotalScoreMap) {
        this.playerTotalScoreMap = playerTotalScoreMap;
    }

    public Map<String, Position> getPlayerIdPositionMap() {
        return playerIdPositionMap;
    }

    public void setPlayerIdPositionMap(Map<String, Position> playerIdPositionMap) {
        this.playerIdPositionMap = playerIdPositionMap;
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

}
