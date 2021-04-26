package com.anbang.qipai.guandan.cqrs.c.service;

import java.util.ArrayList;
import java.util.Set;

import com.anbang.qipai.guandan.cqrs.c.domain.result.ChaodiResult;
import com.anbang.qipai.guandan.cqrs.c.domain.result.PukeActionResult;
import com.anbang.qipai.guandan.cqrs.c.domain.result.ReadyToNextPanResult;

public interface PukePlayCmdService {

	PukeActionResult da(String playerId, ArrayList<Integer> paiIds, String dianshuZuheIdx, Long actionTime) throws Exception;

    PukeActionResult autoDa(String playerId, ArrayList<Integer> paiIds, String dianshuZuheIdx, Long actionTime,String gameId) throws Exception;

    PukeActionResult guo(String playerId, Long actionTime) throws Exception;

    PukeActionResult autoGuo(String playerId, Long actionTime,String gameId) throws Exception;

    ReadyToNextPanResult readyToNextPan(String playerId) throws Exception;

    ReadyToNextPanResult readyToNextPan(String playerId, Set<String> playerIds) throws Exception;

	ChaodiResult chaodi(String playerId, Boolean chaodi, Long actionTime) throws Exception;

    ReadyToNextPanResult autoReadyToNextPan(String playerId, Set<String> playerIds,String gameId) throws Exception;

}
