package com.anbang.qipai.biji.cqrs.c.service;

import com.anbang.qipai.biji.cqrs.c.domain.result.PukeActionResult;
import com.anbang.qipai.biji.cqrs.c.domain.result.ReadyToNextPanResult;

import java.util.Map;
import java.util.Set;

public interface PukePlayCmdService {

    PukeActionResult chupai(String playerId, String toudaoIndex, String zhongdaoIndex, String weidaoIndex, Long actionTime) throws Exception;

    ReadyToNextPanResult readyToNextPan(String playerId) throws Exception;

    Map<String, Boolean> qipai(String playerId, String gameId) throws Exception;

    PukeActionResult autoChupai(String playerId, Long actionTime, String gameId) throws Exception;

    ReadyToNextPanResult readyToNextPan(String playerId, Set<String> playerIds) throws Exception;

    ReadyToNextPanResult autoReadyToNextPan(String playerId, Set<String> playerIds,String gameId) throws Exception;

}
