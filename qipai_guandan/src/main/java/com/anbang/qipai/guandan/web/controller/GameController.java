package com.anbang.qipai.guandan.web.controller;

import com.anbang.qipai.guandan.cqrs.c.domain.Automatic;
import com.anbang.qipai.guandan.cqrs.c.domain.OptionalPlay;
import com.anbang.qipai.guandan.cqrs.c.domain.PukeGameValueObject;
import com.anbang.qipai.guandan.cqrs.c.domain.result.ReadyForGameResult;
import com.anbang.qipai.guandan.cqrs.c.domain.state.PukeGamePlayerChaodiState;
import com.anbang.qipai.guandan.cqrs.c.domain.state.StartChaodi;
import com.anbang.qipai.guandan.cqrs.c.service.GameCmdService;
import com.anbang.qipai.guandan.cqrs.c.service.PlayerAuthService;
import com.anbang.qipai.guandan.cqrs.q.dbo.*;
import com.anbang.qipai.guandan.cqrs.q.service.PukeGameQueryService;
import com.anbang.qipai.guandan.cqrs.q.service.PukePlayQueryService;
import com.anbang.qipai.guandan.msg.msjobj.PukeHistoricalJuResult;
import com.anbang.qipai.guandan.msg.service.WatchRecordMsgService;
import com.anbang.qipai.guandan.msg.service.GuandanGameMsgService;
import com.anbang.qipai.guandan.msg.service.GuandanResultMsgService;
import com.anbang.qipai.guandan.msg.service.WiseCrackMsgServcie;
import com.anbang.qipai.guandan.plan.bean.PlayerInfo;
import com.anbang.qipai.guandan.plan.service.PlayerInfoService;
import com.anbang.qipai.guandan.utils.CommonVoUtil;
import com.anbang.qipai.guandan.utils.SpringUtil;
import com.anbang.qipai.guandan.web.vo.*;
import com.anbang.qipai.guandan.websocket.GamePlayWsNotifier;
import com.anbang.qipai.guandan.websocket.QueryScope;
import com.anbang.qipai.guandan.websocket.WatchQueryScope;
import com.dml.mpgame.game.*;
import com.dml.mpgame.game.extend.fpmpv.VoteNotPassWhenWaitingNextPan;
import com.dml.mpgame.game.extend.vote.FinishedByVote;
import com.dml.mpgame.game.extend.vote.VoteNotPassWhenPlaying;
import com.dml.mpgame.game.player.GamePlayerOnlineState;
import com.dml.mpgame.game.player.PlayerReadyToStart;
import com.dml.mpgame.game.watch.WatchRecord;
import com.dml.mpgame.game.watch.Watcher;
import com.dml.puke.wanfa.position.Position;
import com.dml.shuangkou.pan.PanActionFrame;
import com.dml.shuangkou.pan.PanValueObject;
import com.dml.shuangkou.player.ShuangkouPlayerValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private PlayerAuthService playerAuthService;

    @Autowired
    private GameCmdService gameCmdService;

    @Autowired
    private PukeGameQueryService pukeGameQueryService;

    @Autowired
    private PukePlayQueryService pukePlayQueryService;

    @Autowired
    private GamePlayWsNotifier wsNotifier;

    @Autowired
    private GuandanGameMsgService gameMsgService;

    @Autowired
    private GuandanResultMsgService guandanResultMsgService;

    @Autowired
    private WiseCrackMsgServcie wiseCrackMsgServcie;

    @Autowired
    private WatchRecordMsgService watchRecordMsgService;

    @Autowired
    private PlayerInfoService playerInfoService;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private final Automatic automatic = SpringUtil.getBean(Automatic.class);

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 新一局游戏
     */
    @RequestMapping(value = "/newgame")
    @ResponseBody
    public CommonVO newgame(String playerId, int panshu, int renshu, double difen, OptionalPlay optionalPlay, @RequestParam(required = false) int powerLimit) {
        CommonVO vo = new CommonVO();
        String newGameId = UUID.randomUUID().toString();
        PukeGameValueObject pukeGameValueObject = gameCmdService.newPukeGame(newGameId, playerId, panshu, renshu, optionalPlay, difen, powerLimit);
        pukeGameQueryService.newPukeGame(pukeGameValueObject);
        notReadyQuit(playerId, newGameId, pukeGameValueObject);
        String token = playerAuthService.newSessionForPlayer(playerId);
        Map data = new HashMap();
        data.put("gameId", newGameId);
        data.put("token", token);
        vo.setData(data);
        gameMsgService.newSessionForPlayer(playerId, token, newGameId);
        return vo;
    }

    /**
     * 加入游戏
     */
    @RequestMapping(value = "/joingame")
    @ResponseBody
    public CommonVO joingame(String playerId, String gameId) {
        CommonVO vo = new CommonVO();
        PukeGameValueObject pukeGameValueObject;
        try {
            pukeGameValueObject = gameCmdService.joinGame(playerId, gameId);
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg(e.getClass().toString());
            return vo;
        }
        pukeGameQueryService.joinGame(pukeGameValueObject);
        notReadyQuit(playerId, gameId, pukeGameValueObject);
        // 通知其他玩家
        for (String otherPlayerId : pukeGameValueObject.allPlayerIds()) {
            if (!otherPlayerId.equals(playerId)) {
                GamePlayerOnlineState onlineState = pukeGameValueObject.findPlayerOnlineState(otherPlayerId);
                if (onlineState.equals(GamePlayerOnlineState.online)) {
                    wsNotifier.notifyToQuery(otherPlayerId, QueryScope.scopesForState(pukeGameValueObject.getState(),
                            pukeGameValueObject.findPlayerState(otherPlayerId)));
                }
            }
        }
        String token = playerAuthService.newSessionForPlayer(playerId);
        Map data = new HashMap();
        data.put("token", token);
        vo.setData(data);
        gameMsgService.newSessionForPlayer(playerId, token, gameId);
        return vo;
    }

    /**
     * 加入观战
     */
    @RequestMapping(value = "/joinwatch")
    @ResponseBody
    public CommonVO joinWatch(String playerId, String gameId) {
        PukeGameValueObject pukeGameValueObject;
        String nickName = "";
        String headimgurl = "";

        // 加入观战
        try {
            PlayerInfo playerInfo = playerInfoService.findPlayerInfoById(playerId);
            nickName = playerInfo.getNickname();
            headimgurl = playerInfo.getHeadimgurl();
            pukeGameValueObject = gameCmdService.joinWatch(playerId, nickName, headimgurl, gameId);
        } catch (CrowdLimitsException e) {
            return CommonVoUtil.error("too many watchers");
        } catch (Exception e) {
            return CommonVoUtil.error(e.getClass().toString());
        }

        // 通知游戏玩家
        for (String otherPlayerId : pukeGameValueObject.allPlayerIds()) {
            wsNotifier.notifyWatchInfo(otherPlayerId, "input", playerId, nickName, headimgurl);
        }
        // 通知其他观战者
        Map<String, Watcher> map = gameCmdService.getwatch(gameId);
        if (!CollectionUtils.isEmpty(map)) {
            for (Watcher list : map.values()) {
                if (!list.getId().equals(playerId)) {
                    wsNotifier.notifyWatchInfo(list.getId(), "input", playerId, nickName, headimgurl);
                }
            }
        }

        // 返回查询token
        String token = playerAuthService.newSessionForPlayer(playerId);

        Watcher watcher = new Watcher();
        watcher.setId(playerId);
        watcher.setHeadimgurl(headimgurl);
        watcher.setNickName(nickName);
        watcher.setState("join");
        watcher.setJoinTime(System.currentTimeMillis());
        WatchRecord watchRecord = pukeGameQueryService.saveWatchRecord(gameId, watcher);
        watchRecordMsgService.joinWatch(watchRecord);

        Map data = new HashMap();
        data.put("token", token);
        gameMsgService.newSessionForPlayer(playerId, token, gameId);
        return CommonVoUtil.success(data, "join watch success");
    }

    /**
     * 离开观战
     */
    @RequestMapping(value = "/leavewatch")
    @ResponseBody
    public CommonVO leaveWatch(String token, String gameId) {
        String playerId = playerAuthService.getPlayerIdByToken(token);
        if (playerId == null) {
            return CommonVoUtil.error("invalid token");
        }
        PukeGameValueObject pukeGameValueObject;
        String nickName = "";
        String headimgurl = "";

        try {
            nickName = playerInfoService.findPlayerInfoById(playerId).getNickname();
            pukeGameValueObject = gameCmdService.leaveWatch(playerId, gameId);
        } catch (Exception e) {
            return CommonVoUtil.error(e.getClass().toString());
        }

        // 通知游戏玩家
        for (String otherPlayerId : pukeGameValueObject.allPlayerIds()) {
            wsNotifier.notifyWatchInfo(otherPlayerId, "leave", playerId, nickName, headimgurl);
        }
        // 通知观战者
        Map<String, Watcher> map = gameCmdService.getwatch(gameId);
        if (!CollectionUtils.isEmpty(map)) {
            for (Watcher list : map.values()) {
                if (!list.getId().equals(playerId)) {
                    wsNotifier.notifyWatchInfo(list.getId(), "input", playerId, nickName, headimgurl);
                }
            }
        }

        Watcher watcher = new Watcher();
        watcher.setId(playerId);
        watcher.setHeadimgurl(headimgurl);
        watcher.setNickName(nickName);
        watcher.setState("leave");
        WatchRecord watchRecord = pukeGameQueryService.saveWatchRecord(gameId, watcher);
        watchRecordMsgService.leaveWatch(watchRecord);

        return CommonVoUtil.success("leave success");
    }

    /**
     * 查询正在观战的玩家
     */
    @RequestMapping(value = "/queryWatch")
    @ResponseBody
    public CommonVO queryWatch(String gameId) {
        Map<String, Watcher> map = gameCmdService.getwatch(gameId);
        if (CollectionUtils.isEmpty(map)) {
            return CommonVoUtil.success("queryWatch success");
        }
        return CommonVoUtil.success(map.values(), "queryWatch success");
    }

    /**
     * 观战者看到的信息
     */
    @RequestMapping(value = "/watchinginfo")
    @ResponseBody
    public CommonVO watchingInfo(String gameId) {
        CommonVO vo = new CommonVO();
        PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);
        GameVO gameVO = new GameVO(pukeGameDbo);
        Map data = new HashMap();
        data.put("game", gameVO);
        vo.setData(data);
        return vo;
    }

    /**
     * 挂起（手机按黑的时候调用）
     */
    @RequestMapping(value = "/hangup")
    @ResponseBody
    public CommonVO hangup(String token) {
        CommonVO vo = new CommonVO();
        String playerId = playerAuthService.getPlayerIdByToken(token);
        if (playerId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }

        String gameIdByPlayerId = gameCmdService.getGameIdByPlayerId(playerId);
        if (gameIdByPlayerId != null) { //离线托管
            automatic.offlineHosting(gameIdByPlayerId, playerId);
        }

        PukeGameValueObject pukeGameValueObject;
        String flag = "query";
        try {
            pukeGameValueObject = gameCmdService.leaveGameByHangup(playerId);
            if (pukeGameValueObject == null) {
                vo.setSuccess(true);
                return vo;
            }
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg(e.getClass().getName());
            return vo;
        }
        pukeGameQueryService.leaveGame(pukeGameValueObject);
        notReadyQuit(playerId, pukeGameValueObject.getId(), pukeGameValueObject);
        // 断开玩家的socket
        wsNotifier.closeSessionForPlayer(playerId);
        String gameId = pukeGameValueObject.getId();
        JuResultDbo juResultDbo = pukePlayQueryService.findJuResultDbo(gameId);
        // 记录战绩
        if (juResultDbo != null) {
            PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);
            PukeHistoricalJuResult juResult = new PukeHistoricalJuResult(juResultDbo, pukeGameDbo);
            guandanResultMsgService.recordJuResult(juResult);
        }
        if (pukeGameValueObject.getState().name().equals(FinishedByVote.name) || pukeGameValueObject.getState().name().equals(Canceled.name)) {
            gameMsgService.gameFinished(gameId);
            flag = WatchQueryScope.watchEnd.name();
        } else {
            gameMsgService.gamePlayerLeave(pukeGameValueObject, playerId);
        }
        // 通知其他玩家
        for (String otherPlayerId : pukeGameValueObject.allPlayerIds()) {
            if (!otherPlayerId.equals(playerId)) {
                GamePlayerOnlineState onlineState = pukeGameValueObject.findPlayerOnlineState(otherPlayerId);
                if (onlineState.equals(GamePlayerOnlineState.online)) {
                    List<QueryScope> scopes = QueryScope.scopesForState(pukeGameValueObject.getState(), pukeGameValueObject.findPlayerState(otherPlayerId));
                    scopes.remove(QueryScope.panResult);
                    if (pukeGameValueObject.getState().name().equals(VoteNotPassWhenPlaying.name) ||
                            pukeGameValueObject.getState().name().equals(VoteNotPassWhenWaitingNextPan.name) ||
                            pukeGameValueObject.getState().name().equals(StartChaodi.name)) {
                        scopes.remove(QueryScope.gameFinishVote);
                    }
                    wsNotifier.notifyToQuery(otherPlayerId, scopes);
                }
            }
        }
        hintWatcher(gameId, flag);
        return vo;
    }

    /**
     * 离开游戏(非退出,还会回来的)
     */
    @RequestMapping(value = "/leavegame")
    @ResponseBody
    public CommonVO leavegame(String token) {
        CommonVO vo = new CommonVO();
        String playerId = playerAuthService.getPlayerIdByToken(token);
        if (playerId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }

        String gameIdByPlayerId = gameCmdService.getGameIdByPlayerId(playerId);
        if (gameIdByPlayerId != null) { //离线托管
            automatic.offlineHosting(gameIdByPlayerId, playerId);
        }

        PukeGameValueObject pukeGameValueObject;
        String endFlag = "query";
        try {
            pukeGameValueObject = gameCmdService.leaveGame(playerId);
            if (pukeGameValueObject == null) {
                vo.setSuccess(true);
                return vo;
            }
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg(e.getClass().getName());
            return vo;
        }
        pukeGameQueryService.leaveGame(pukeGameValueObject);
        notReadyQuit(playerId, pukeGameValueObject.getId(), pukeGameValueObject);
        // 断开玩家的socket
        wsNotifier.closeSessionForPlayer(playerId);
        String gameId = pukeGameValueObject.getId();
        JuResultDbo juResultDbo = pukePlayQueryService.findJuResultDbo(gameId);
        // 记录战绩
        if (juResultDbo != null) {
            PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);
            PukeHistoricalJuResult juResult = new PukeHistoricalJuResult(juResultDbo, pukeGameDbo);
            guandanResultMsgService.recordJuResult(juResult);
        }
        if (pukeGameValueObject.getState().name().equals(FinishedByVote.name) || pukeGameValueObject.getState().name().equals(Canceled.name)) {
            gameMsgService.gameFinished(gameId);
            endFlag = WatchQueryScope.watchEnd.name();
        } else if (pukeGameValueObject.getState().name().equals(Finished.name)) {
            gameMsgService.gameCanceled(gameId, playerId);
        } else {
            gameMsgService.gamePlayerLeave(pukeGameValueObject, playerId);
        }
        // 通知其他玩家
        for (String otherPlayerId : pukeGameValueObject.allPlayerIds()) {
            if (!otherPlayerId.equals(playerId)) {
                GamePlayerOnlineState onlineState = pukeGameValueObject.findPlayerOnlineState(otherPlayerId);
                if (onlineState.equals(GamePlayerOnlineState.online)) {
                    List<QueryScope> scopes = QueryScope.scopesForState(pukeGameValueObject.getState(), pukeGameValueObject.findPlayerState(otherPlayerId));
                    if (!pukeGameValueObject.getState().name().equals(Finished.name)) {
                        scopes.remove(QueryScope.panResult);
                    }
                    if (pukeGameValueObject.getState().name().equals(VoteNotPassWhenPlaying.name) || pukeGameValueObject.getState().name().equals(VoteNotPassWhenWaitingNextPan.name)) {
                        scopes.remove(QueryScope.gameFinishVote);
                    }
                    wsNotifier.notifyToQuery(otherPlayerId, scopes);
                }
            }
        }
        hintWatcher(gameId, endFlag);
        return vo;
    }

    /**
     * 返回游戏
     */
    @RequestMapping(value = "/backtogame")
    @ResponseBody
    public CommonVO backtogame(String playerId, String gameId) {
        // 是观战返回新token
        Map<String, Watcher> map = gameCmdService.getwatch(gameId);
        if (!CollectionUtils.isEmpty(map) && map.containsKey(playerId)) {
            List<String> playerIds = new ArrayList<>();
            playerIds.add(playerId);
            wsNotifier.notifyToWatchQuery(playerIds, "query");
            Map data = new HashMap();
            String token = playerAuthService.newSessionForPlayer(playerId);
            data.put("token", token);
            return CommonVoUtil.success(data, "backtogame success");
        }

        CommonVO vo = new CommonVO();
        Map data = new HashMap();
        vo.setData(data);
        PukeGameValueObject pukeGameValueObject;
        try {
            pukeGameValueObject = gameCmdService.backToGame(playerId, gameId);
        } catch (Exception e) {
            // 如果找不到game，看下是否是已经结束(正常结束和被投票)的game
            if (e instanceof GameNotFoundException) {
                PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);
                if (pukeGameDbo != null && (pukeGameDbo.getState().name().equals(FinishedByVote.name) || pukeGameDbo.getState().name().equals(Finished.name))) {
                    data.put("queryScope", QueryScope.juResult);
                    return vo;
                }
            }
            vo.setSuccess(false);
            vo.setMsg(e.getClass().toString());
            return vo;
        }
        pukeGameQueryService.backToGame(playerId, pukeGameValueObject);
        // 通知其他人
        for (String otherPlayerId : pukeGameValueObject.allPlayerIds()) {
            if (!otherPlayerId.equals(playerId)) {
                GamePlayerOnlineState onlineState = pukeGameValueObject.findPlayerOnlineState(otherPlayerId);
                if (onlineState.equals(GamePlayerOnlineState.online)) {
                    List<QueryScope> scopes = QueryScope.scopesForState(pukeGameValueObject.getState(), pukeGameValueObject.findPlayerState(otherPlayerId));
                    scopes.remove(QueryScope.panResult);
                    if (pukeGameValueObject.getState().name().equals(VoteNotPassWhenPlaying.name) || pukeGameValueObject.getState().name().equals(VoteNotPassWhenWaitingNextPan.name)) {
                        scopes.remove(QueryScope.gameFinishVote);
                    }
                    wsNotifier.notifyToQuery(otherPlayerId, scopes);
                }
            }
        }
        String token = playerAuthService.newSessionForPlayer(playerId);
        data.put("token", token);
        gameMsgService.newSessionForPlayer(playerId, token, gameId);

        gameCmdService.playLeaveGameHosting(playerId, gameId, false);//离线返回游戏取消托管
        return vo;
    }

    /**
     * 游戏的所有信息,不包含局
     *
     * @param gameId
     * @return
     */
    @RequestMapping(value = "/info")
    @ResponseBody
    public CommonVO info(String gameId) {
        CommonVO vo = new CommonVO();
        PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);
        GameVO gameVO = new GameVO(pukeGameDbo);
        Map data = new HashMap();
        try {
            tuoguan(gameId, gameVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        data.put("game", gameVO);
        vo.setData(data);
        return vo;
    }

    /**
     * 最开始的准备,不适用下一盘的准备
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/ready")
    @ResponseBody
    public CommonVO ready(String token) {
        CommonVO vo = new CommonVO();
        Map data = new HashMap();
        vo.setData(data);
        String playerId = playerAuthService.getPlayerIdByToken(token);
        if (playerId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }

        ReadyForGameResult readyForGameResult;
        try {
            readyForGameResult = gameCmdService.readyForGame(playerId, System.currentTimeMillis());
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg(e.getClass().getName());
            return vo;
        }

        pukePlayQueryService.readyForGame(readyForGameResult);// TODO 一起点准备的时候可能有同步问题.要靠框架解决
        // 通知其他人
        for (String otherPlayerId : readyForGameResult.getPukeGame().allPlayerIds()) {
            if (!otherPlayerId.equals(playerId)) {
                GamePlayerOnlineState onlineState = readyForGameResult.getPukeGame()
                        .findPlayerOnlineState(otherPlayerId);
                if (onlineState.equals(GamePlayerOnlineState.online)) {
                    wsNotifier.notifyToQuery(otherPlayerId,
                            QueryScope.scopesForState(readyForGameResult.getPukeGame().getState(),
                                    readyForGameResult.getPukeGame().findPlayerState(otherPlayerId)));
                }
            }
        }

        List<QueryScope> queryScopes = new ArrayList<>();
        queryScopes.add(QueryScope.gameInfo);
        if (readyForGameResult.getPukeGame().getState().name().equals(Playing.name)) {
            queryScopes.add(QueryScope.panForMe);
            gameMsgService.start(readyForGameResult.getPukeGame().getId());
        }
        if (readyForGameResult.getPukeGame().getState().name().equals(StartChaodi.name)) {
            queryScopes.add(QueryScope.chaodiInfo);
            queryScopes.add(QueryScope.panForMe);
            gameMsgService.start(readyForGameResult.getPukeGame().getId());
        }
        data.put("queryScopes", queryScopes);
        return vo;
    }

    /**
     * 最开始的取消准备,不适用下一盘的准备
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/cancelready")
    @ResponseBody
    public CommonVO cancelReady(String token) {
        CommonVO vo = new CommonVO();
        Map data = new HashMap();
        vo.setData(data);
        String playerId = playerAuthService.getPlayerIdByToken(token);
        if (playerId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }

        ReadyForGameResult readyForGameResult;
        try {
            readyForGameResult = gameCmdService.cancelReadyForGame(playerId, System.currentTimeMillis());
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg(e.getClass().getName());
            return vo;
        }

        pukePlayQueryService.readyForGame(readyForGameResult);// TODO 一起点准备的时候可能有同步问题.要靠框架解决
        // 通知其他人
        notReadyQuit(playerId, readyForGameResult.getPukeGame().getId(), readyForGameResult.getPukeGame());
        for (String otherPlayerId : readyForGameResult.getPukeGame().allPlayerIds()) {
            if (!otherPlayerId.equals(playerId)) {
                GamePlayerOnlineState onlineState = readyForGameResult.getPukeGame()
                        .findPlayerOnlineState(otherPlayerId);
                if (onlineState.equals(GamePlayerOnlineState.online)) {
                    wsNotifier.notifyToQuery(otherPlayerId,
                            QueryScope.scopesForState(readyForGameResult.getPukeGame().getState(),
                                    readyForGameResult.getPukeGame().findPlayerState(otherPlayerId)));

                }
            }
        }

        List<QueryScope> queryScopes = new ArrayList<>();
        queryScopes.add(QueryScope.gameInfo);
        if (readyForGameResult.getPukeGame().getState().name().equals(Playing.name)) {
            queryScopes.add(QueryScope.panForMe);
        }
        data.put("queryScopes", queryScopes);
        return vo;
    }

    @RequestMapping(value = "/finish")
    @ResponseBody
    public CommonVO finish(String token) {
        CommonVO vo = new CommonVO();
        Map data = new HashMap();
        vo.setData(data);
        String playerId = playerAuthService.getPlayerIdByToken(token);
        if (playerId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }

        PukeGameValueObject pukeGameValueObject;
        String endFlag = "query";
        try {
            pukeGameValueObject = gameCmdService.finish(playerId, System.currentTimeMillis());
            if (pukeGameValueObject.getState().name().equals(WaitingStart.name)) {
                vo.setSuccess(false);
                vo.setMsg("waitStart");
                return vo;
            } else if (pukeGameValueObject.getOptionalPlay().isBanJiesan()) {
                vo.setSuccess(false);
                vo.setMsg("banJiesan");
                return vo;
            }
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg(e.getClass().getName());
            return vo;
        }
        pukeGameQueryService.finish(pukeGameValueObject);
        String gameId = pukeGameValueObject.getId();
        JuResultDbo juResultDbo = pukePlayQueryService.findJuResultDbo(gameId);
        // 记录战绩
        if (juResultDbo != null) {
            PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);
            PukeHistoricalJuResult juResult = new PukeHistoricalJuResult(juResultDbo, pukeGameDbo);
            guandanResultMsgService.recordJuResult(juResult);
        }

        if (pukeGameValueObject.getState().name().equals(FinishedByVote.name)
                || pukeGameValueObject.getState().name().equals(Canceled.name)) {
            gameMsgService.gameFinished(gameId);
            endFlag = WatchQueryScope.watchEnd.name();
            data.put("queryScope", QueryScope.gameInfo);
        } else {
            // 游戏没结束有两种可能：一种是发起了投票。还有一种是游戏没开始，解散发起人又不是房主，那就自己走人。
            if (pukeGameValueObject.allPlayerIds().contains(playerId)) {
                data.put("queryScope", QueryScope.gameFinishVote);
            } else {
                data.put("queryScope", null);
                gameMsgService.gamePlayerLeave(pukeGameValueObject, playerId);
            }
        }

        // 通知其他人来查询
        for (String otherPlayerId : pukeGameValueObject.allPlayerIds()) {
            if (!otherPlayerId.equals(playerId)) {
                GamePlayerOnlineState onlineState = pukeGameValueObject.findPlayerOnlineState(otherPlayerId);
                if (onlineState.equals(GamePlayerOnlineState.online)) {
                    List<QueryScope> scopes = QueryScope.scopesForState(pukeGameValueObject.getState(),
                            pukeGameValueObject.findPlayerState(otherPlayerId));
                    scopes.remove(QueryScope.panResult);
                    wsNotifier.notifyToQuery(otherPlayerId, scopes);
                }
            }
        }

        hintWatcher(gameId, endFlag);
        return vo;
    }

    @RequestMapping(value = "/quit")
    @ResponseBody
    public CommonVO quit(String token) {
        CommonVO vo = new CommonVO();
        Map data = new HashMap();
        vo.setData(data);
        String playerId = playerAuthService.getPlayerIdByToken(token);
        if (playerId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }

        PukeGameValueObject pukeGameValueObject;
        String endFlag = "query";
        try {
            pukeGameValueObject = gameCmdService.quit(playerId, System.currentTimeMillis(), null);
            if (!pukeGameValueObject.getState().name().equals(WaitingStart.name)) {
                vo.setSuccess(false);
                vo.setMsg("gameStart");
                return vo;
            }
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg(e.getClass().getName());
            return vo;
        }
        pukeGameQueryService.quit(pukeGameValueObject);
        String gameId = pukeGameValueObject.getId();
        data.put("queryScope", null);
        gameMsgService.gamePlayerLeave(pukeGameValueObject, playerId);

        // 通知其他人来查询
        for (String otherPlayerId : pukeGameValueObject.allPlayerIds()) {
            if (!otherPlayerId.equals(playerId)) {
                GamePlayerOnlineState onlineState = pukeGameValueObject.findPlayerOnlineState(otherPlayerId);
                if (onlineState.equals(GamePlayerOnlineState.online)) {
                    List<QueryScope> scopes = QueryScope.scopesForState(pukeGameValueObject.getState(), pukeGameValueObject.findPlayerState(otherPlayerId));
                    scopes.remove(QueryScope.panResult);
                    wsNotifier.notifyToQuery(otherPlayerId, scopes);
                }
            }
        }
        // 游戏结束通知观战者
        hintWatcher(gameId, endFlag);
        return vo;
    }

    @RequestMapping(value = "/vote_to_finish")
    @ResponseBody
    public CommonVO votetofinish(String token, boolean yes) {
        CommonVO vo = new CommonVO();
        Map data = new HashMap();
        vo.setData(data);
        String playerId = playerAuthService.getPlayerIdByToken(token);
        if (playerId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }

        PukeGameValueObject pukeGameValueObject;
        String endFlag = "query";
        try {
            pukeGameValueObject = gameCmdService.voteToFinish(playerId, yes);
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg(e.getClass().getName());
            return vo;
        }
        String gameId = pukeGameValueObject.getId();
        pukeGameQueryService.automaticToFinish(pukeGameValueObject);
        JuResultDbo juResultDbo = pukePlayQueryService.findJuResultDbo(gameId);
        // 记录战绩
        if (juResultDbo != null) {
            PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);
            PukeHistoricalJuResult juResult = new PukeHistoricalJuResult(juResultDbo, pukeGameDbo);
            guandanResultMsgService.recordJuResult(juResult);
        }
        if (pukeGameValueObject.getState().name().equals(FinishedByVote.name)
                || pukeGameValueObject.getState().name().equals(Canceled.name)) {
            gameMsgService.gameFinished(gameId);
            endFlag = WatchQueryScope.watchEnd.name();
        }
        data.put("queryScope", QueryScope.gameFinishVote);
        // 通知其他人来查询投票情况
        for (String otherPlayerId : pukeGameValueObject.allPlayerIds()) {
            if (!otherPlayerId.equals(playerId)) {
                GamePlayerOnlineState onlineState = pukeGameValueObject.findPlayerOnlineState(otherPlayerId);
                if (onlineState.equals(GamePlayerOnlineState.online)) {
                    List<QueryScope> scopes = QueryScope.scopesForState(pukeGameValueObject.getState(),
                            pukeGameValueObject.findPlayerState(otherPlayerId));
                    scopes.remove(QueryScope.panResult);
                    wsNotifier.notifyToQuery(otherPlayerId, scopes);
                }
            }
        }

        hintWatcher(gameId, endFlag);
        return vo;
    }

    /**
     * 投票倒计时结束弃权
     */
    @RequestMapping(value = "/timeover_to_waiver")
    @ResponseBody
    public CommonVO timeoverToWaiver(String token) {
        CommonVO vo = new CommonVO();
        Map data = new HashMap();
        vo.setData(data);
        String playerId = playerAuthService.getPlayerIdByToken(token);
        if (playerId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }

        PukeGameValueObject pukeGameValueObject;
        String endFlag = "query";
        try {
            pukeGameValueObject = gameCmdService.voteToFinishByTimeOver(playerId, System.currentTimeMillis());
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg(e.getClass().getName());
            return vo;
        }
        String gameId = pukeGameValueObject.getId();
        pukeGameQueryService.automaticToFinish(pukeGameValueObject);
        JuResultDbo juResultDbo = pukePlayQueryService.findJuResultDbo(gameId);
        // 记录战绩
        if (juResultDbo != null) {
            PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);
            PukeHistoricalJuResult juResult = new PukeHistoricalJuResult(juResultDbo, pukeGameDbo);
            guandanResultMsgService.recordJuResult(juResult);
        }
        if (pukeGameValueObject.getState().name().equals(FinishedByVote.name)
                || pukeGameValueObject.getState().name().equals(Canceled.name)) {
            gameMsgService.gameFinished(gameId);
            endFlag = WatchQueryScope.watchEnd.name();
        }
        data.put("queryScope", QueryScope.gameFinishVote);
        // 通知其他人来查询投票情况
        for (String otherPlayerId : pukeGameValueObject.allPlayerIds()) {
            if (!otherPlayerId.equals(playerId)) {
                GamePlayerOnlineState onlineState = pukeGameValueObject.findPlayerOnlineState(otherPlayerId);
                if (onlineState.equals(GamePlayerOnlineState.online)) {
                    List<QueryScope> scopes = QueryScope.scopesForState(pukeGameValueObject.getState(),
                            pukeGameValueObject.findPlayerState(otherPlayerId));
                    scopes.remove(QueryScope.panResult);
                    wsNotifier.notifyToQuery(otherPlayerId, scopes);
                }
            }
        }

        hintWatcher(gameId, endFlag);
        return vo;
    }

    @RequestMapping(value = "/finish_vote_info")
    @ResponseBody
    public CommonVO finishvoteinfo(String gameId) {

        CommonVO vo = new CommonVO();
        GameFinishVoteDbo gameFinishVoteDbo = pukeGameQueryService.findGameFinishVoteDbo(gameId);
        Map data = new HashMap();
        data.put("vote", new GameFinishVoteVO(gameFinishVoteDbo.getVote()));
        vo.setData(data);
        return vo;

    }

    @RequestMapping(value = "/chaodi_info")
    @ResponseBody
    public CommonVO chaodiinfo(String gameId) {
        CommonVO vo = new CommonVO();
        PukeGamePlayerChaodiDbo pukeGamePlayerChaodiDbo = pukePlayQueryService.findLastPlayerChaodiDboByGameId(gameId);
        Map<String, PukeGamePlayerChaodiState> playerChaodiStateMap = new HashMap<>();
        if (pukeGamePlayerChaodiDbo != null) {
            playerChaodiStateMap = pukeGamePlayerChaodiDbo.getPlayerChaodiStateMap();
        }
        Map data = new HashMap();
        data.put("chaodiState", playerChaodiStateMap);
        vo.setData(data);
        return vo;

    }

    @RequestMapping(value = "/wisecrack")
    @ResponseBody
    public CommonVO wisecrack(String token, String gameId, String ordinal) {
        CommonVO vo = new CommonVO();
        String playerId = playerAuthService.getPlayerIdByToken(token);
        if (playerId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);
        for (PukeGamePlayerDbo otherPlayer : pukeGameDbo.getPlayers()) {
            if (!otherPlayer.getPlayerId().equals(playerId)) {
                wsNotifier.notifyToListenWisecrack(otherPlayer.getPlayerId(), ordinal, playerId);
            }
        }
        wiseCrackMsgServcie.wisecrack(playerId);
        vo.setSuccess(true);
        return vo;
    }

    @RequestMapping(value = "/playback")
    @ResponseBody
    public CommonVO playback(String gameId, int panNo) {
        CommonVO vo = new CommonVO();
        Map data = new HashMap();
        vo.setData(data);
        List<PanActionFrameDbo> frameList = pukePlayQueryService.findPanActionFrameDboForBackPlay(gameId, panNo);
        List<PanActionFrameVO> frameVOList = new ArrayList<>();
        for (PanActionFrameDbo frame : frameList) {
            frame.getPanActionFrame().getPanAfterAction().getPaiListValueObject().setPaiList(null);
            frameVOList.add(new PanActionFrameVO(frame.getPanActionFrame()));
        }
        List<PukeGameInfoDbo> infos = pukePlayQueryService.findGameInfoDboForBackPlay(gameId, panNo);
        PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);
        pukeGameDbo.setPanNo(panNo);
        GameVO gameVO = new GameVO(pukeGameDbo);
        PukeGamePlayerChaodiDbo pukeGamePlayerChaodiDbo = pukePlayQueryService
                .findPlayerChaodiDboByGameIdAndPanNo(gameId, panNo);
        Map<String, PukeGamePlayerChaodiState> playerChaodiStateMap = new HashMap<>();
        if (pukeGamePlayerChaodiDbo != null) {
            playerChaodiStateMap = pukeGamePlayerChaodiDbo.getPlayerChaodiStateMap();
        }
        PanResultDbo panResultDbo = pukePlayQueryService.findPanResultDbo(gameId, panNo);
        data.put("panResult", new PanResultVO(panResultDbo, pukeGameDbo));
        data.put("chaodiState", playerChaodiStateMap);
        data.put("game", gameVO);
        data.put("framelist", frameVOList);
        data.put("infos", infos);
        return vo;
    }

    @RequestMapping(value = "/speak")
    @ResponseBody
    public CommonVO speak(String token, String gameId, String wordId) {
        CommonVO vo = new CommonVO();
        String playerId = playerAuthService.getPlayerIdByToken(token);
        if (playerId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);
        List<PukeGamePlayerDbo> playerList = pukeGameDbo.getPlayers();
        for (PukeGamePlayerDbo player : playerList) {
            if (!player.getPlayerId().equals(playerId)) {
                wsNotifier.notifyToListenSpeak(player.getPlayerId(), wordId, playerId, true);
            }
        }
        // 观战者接收语音
        Map<String, Object> map = gameCmdService.getwatch(gameId);
        if (!CollectionUtils.isEmpty(map)) {
            List<String> playerIds = map.entrySet().stream().map(e -> e.getKey()).collect(Collectors.toList());
            for (String list : playerIds) {
                if (!list.equals(playerId)) {
                    wsNotifier.notifyToListenSpeak(list, wordId, playerId, false);
                }
            }
        }

        vo.setSuccess(true);
        return vo;
    }

    /**
     * 重启服务清空游戏
     */
    @RequestMapping(value = "/quitAllGame")
    public String quitAllGame(String token) {
        if (!"2019".equals(token)) {
            return "blank";
        }

        try {
            Set<String> stringSet = gameCmdService.listGameId();
            if (!CollectionUtils.isEmpty(stringSet)) {
                for (String gameId : stringSet) {
                    PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);
                    if (pukeGameDbo == null) {
                        continue;
                    }

                    // 结束游戏并保存数据
                    PukeGameValueObject gameValueObject = gameCmdService.finishGameImmediately(gameId);
                    pukeGameQueryService.finishGameImmediately(gameValueObject, null);
                    gameMsgService.gameFinished(gameId);
                    JuResultDbo juResultDbo = pukePlayQueryService.findJuResultDbo(gameId);
                    if (juResultDbo != null) {
                        PukeHistoricalJuResult juResult = new PukeHistoricalJuResult(juResultDbo,
                                pukeGameDbo);
                        guandanResultMsgService.recordJuResult(juResult);
                    }
                }
            }
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    /**
     * 通知观战者
     */
    private void hintWatcher(String gameId, String flag) {
        Map<String, Object> map = gameCmdService.getwatch(gameId);
        if (!CollectionUtils.isEmpty(map)) {
            List<String> playerIds = map.entrySet().stream().map(e -> e.getKey()).collect(Collectors.toList());
            wsNotifier.notifyToWatchQuery(playerIds, flag);
            if (WatchQueryScope.watchEnd.name().equals(flag)) {
                gameCmdService.recycleWatch(gameId);
            }
        }
    }

    /**
     * 按时间删除游戏数据
     */
    @RequestMapping("/remove")
    public CommonVO removeGameData() {
        CommonVO vo = new CommonVO();
        long endTime = System.currentTimeMillis() - 30L * 24 * 60 * 60 * 1000;
        pukeGameQueryService.removeGameDataByTime(endTime);
        return vo;
    }


    /**
     * 结束某个游戏
     */
    @RequestMapping(value = "/quitGame")
    public CommonVO quitGame(String gameId) {
        CommonVO vo = new CommonVO();
        try {
            PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);
            if (pukeGameDbo == null) {
                vo.setSuccess(false);
                vo.setMsg("invalid gameId");
                return vo;
            }
            // 结束游戏并保存数据
            PukeGameValueObject gameValueObject = gameCmdService.finishGameImmediately(gameId);
            pukeGameQueryService.finishGameImmediately(gameValueObject, null);
            gameMsgService.gameFinished(gameId);
            JuResultDbo juResultDbo = pukePlayQueryService.findJuResultDbo(gameId);
            if (juResultDbo != null) {
                PukeHistoricalJuResult juResult = new PukeHistoricalJuResult(juResultDbo, pukeGameDbo);
                guandanResultMsgService.recordJuResult(juResult);
            }
            for (String otherPlayerId : gameValueObject.allPlayerIds()) {
                wsNotifier.notifyToQuery(otherPlayerId, QueryScope.scopesForState(gameValueObject.getState(), gameValueObject.findPlayerState(otherPlayerId)));
            }
            vo.setSuccess(true);
            vo.setMsg("quit game success");
            return vo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        vo.setSuccess(false);
        vo.setMsg("SysException");
        return vo;
    }

    @RequestMapping(value = "/deposit")
    @ResponseBody
    public CommonVO deposit(String playerId, String gameId, boolean deposit) {
        logger.info("deposit," + "Time:" + System.currentTimeMillis() + ",playerId:" + playerId + ",deposit:" + deposit + ",gameId:" + gameId);
        CommonVO vo = new CommonVO();
        Map data = new HashMap();
        List<String> queryScopes = new ArrayList<>();
        data.put("queryScopes", queryScopes);
        vo.setData(data);
        queryScopes.add(QueryScope.gameInfo.name());
        queryScopes.add(QueryScope.panForMe.name());

        Map<String, String> tuoguanPlayerIds = gameCmdService.playLeaveGameHosting(playerId, null, false);//gameId传null返回当前托管玩家集合
        if (!tuoguanPlayerIds.containsKey(playerId) && deposit) {   //玩家没有托管&&要托管
            PanActionFrame panActionFrame = pukePlayQueryService.findAndFilterCurrentPanValueObjectForPlayer(gameId);
            automatic.autoDaOrGuo(playerId, panActionFrame, gameId);//离线玩家自动出牌或过牌
            logger.info("在线托管," + "Time:" + System.currentTimeMillis() + ",playerId:" + playerId + ",gameId:" + gameId);
        }
        gameCmdService.playLeaveGameHosting(playerId, gameId, deposit);
        return vo;
    }


    /**
     * 托管状态设置
     *
     * @param gameId 游戏ID
     * @param gameVO 游戏实体对象
     */
    private void tuoguan(String gameId, GameVO gameVO) {
        Map<String, String> tuoguanPlayerIds = gameCmdService.playLeaveGameHosting(null, gameId, false);
        if (tuoguanPlayerIds != null) {
            List<PukeGamePlayerVO> playerList = gameVO.getPlayerList();
            Set<String> playerIds = tuoguanPlayerIds.keySet();
            for (PukeGamePlayerVO pukeGamePlayerVO : playerList) {
                if (playerIds.contains(pukeGamePlayerVO.getPlayerId())) {    //设置托管状态
                    pukeGamePlayerVO.setDeposit(true);
                }
            }
        }

    }

    private void notReadyQuit(String playerId, String newGameId, PukeGameValueObject pukeGameValueObject) {
        if (pukeGameValueObject.getOptionalPlay().getBuzhunbeituichushichang() != 0) {
            executorService.submit(() -> {
                try {
                    int sleepTime = pukeGameValueObject.getOptionalPlay().getBuzhunbeituichushichang();
                    Thread.sleep((sleepTime + 1) * 1000L);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(newGameId);
                for (PukeGamePlayerDbo player : pukeGameDbo.getPlayers()) {
                    if (player.getPlayerId().equals(playerId)) {
                        if (pukeGameDbo.getState().name().equals(WaitingStart.name)) {
                            if (!PlayerReadyToStart.name.equals(player.getState().name())) {
                                PukeGameValueObject pukeGameValueObject1 = null;
                                try {
                                    pukeGameValueObject1 = gameCmdService.quit(playerId, System.currentTimeMillis(), newGameId);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                pukeGameQueryService.quit(pukeGameValueObject1);
                                for (String otherPlayerId : pukeGameValueObject1.allPlayerIds()) {
                                    List<QueryScope> scopes = QueryScope.scopesForState(pukeGameValueObject1.getState(),
                                            pukeGameValueObject1.findPlayerState(otherPlayerId));
                                    wsNotifier.notifyToQuery(otherPlayerId, scopes);
                                }
                                if (pukeGameValueObject1.getPlayers().size() == 0) {
                                    gameMsgService.gameFinished(newGameId);
                                }
                                gameMsgService.gamePlayerLeave(pukeGameValueObject1, playerId);
                                wsNotifier.sendMessageToQuit(playerId);
                            }
                        }
                    }
                }
            });

        }
    }

}
