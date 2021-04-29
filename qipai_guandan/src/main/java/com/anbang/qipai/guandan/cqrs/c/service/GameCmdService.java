package com.anbang.qipai.guandan.cqrs.c.service;

import com.anbang.qipai.guandan.cqrs.c.domain.OptionalPlay;
import com.anbang.qipai.guandan.cqrs.c.domain.PukeGameValueObject;
import com.anbang.qipai.guandan.cqrs.c.domain.result.ReadyForGameResult;
import com.dml.shuangkou.pan.PanActionFrame;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GameCmdService {

    PukeGameValueObject newPukeGame(String gameId, String playerId, Integer panshu, Integer renshu, OptionalPlay optionalPlay, Double difen,Integer powerLimit);

    PukeGameValueObject joinGame(String playerId, String gameId) throws Exception;

    PukeGameValueObject leaveGame(String playerId) throws Exception;

    PukeGameValueObject leaveGameByHangup(String playerId) throws Exception;

    PukeGameValueObject leaveGameByOffline(String playerId) throws Exception;

    PukeGameValueObject backToGame(String playerId, String gameId) throws Exception;

    ReadyForGameResult readyForGame(String playerId, Long currentTime) throws Exception;

    ReadyForGameResult cancelReadyForGame(String playerId, Long currentTime) throws Exception;

    PukeGameValueObject finish(String playerId, Long currentTime) throws Exception;

    PukeGameValueObject quit(String playerId, Long currentTime,String gameId) throws Exception;

    PukeGameValueObject voteToFinish(String playerId, Boolean yes) throws Exception;

    PukeGameValueObject voteToFinishByTimeOver(String playerId, Long currentTime) throws Exception;

    PukeGameValueObject finishGameImmediately(String gameId) throws Exception;

    void bindPlayer(String playerId, String gameId) throws Exception;

    PukeGameValueObject joinWatch(String playerId, String nickName, String headimgurl, String gameId) throws Exception;

    PukeGameValueObject leaveWatch(String playerId, String gameId) throws Exception;

    Map getwatch(String gameId);

    void recycleWatch(String gameId);

    Set<String> listGameId();

    Map<String, String> playLeaveGameHosting(String playerId, String gameId, boolean isLeave);

    PukeGameValueObject automaticToFinish(String gameId) throws Exception;

    String getActionPlayerId(String gameId);

    List<PanActionFrame> getPanActionFrame(String gameId) throws Exception;

    String getGameIdByPlayerId(String playerId);

}
