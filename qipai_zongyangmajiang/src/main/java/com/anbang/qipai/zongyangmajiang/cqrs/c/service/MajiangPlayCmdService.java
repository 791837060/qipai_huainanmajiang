package com.anbang.qipai.zongyangmajiang.cqrs.c.service;

import com.anbang.qipai.zongyangmajiang.cqrs.c.domain.MajiangActionResult;
import com.anbang.qipai.zongyangmajiang.cqrs.c.domain.MajiangGameValueObject;
import com.anbang.qipai.zongyangmajiang.cqrs.c.domain.ReadyToNextPanResult;

import java.util.Set;

public interface MajiangPlayCmdService {

	MajiangGameValueObject xipai(String playerId) throws Exception;

	MajiangActionResult action(String playerId, Integer actionId, Integer actionNo, Long actionTime) throws Exception;

	ReadyToNextPanResult readyToNextPan(String playerId) throws Exception;

    MajiangActionResult automaticAction(String playerId, Integer actionId, Long actionTime, String gameId) throws Exception;

    ReadyToNextPanResult readyToNextPan(String playerId, Set<String> playerIds) throws Exception;

	ReadyToNextPanResult autoReadyToNextPan(String playerId, Set<String> playerIds,String gameId) throws Exception;

}
