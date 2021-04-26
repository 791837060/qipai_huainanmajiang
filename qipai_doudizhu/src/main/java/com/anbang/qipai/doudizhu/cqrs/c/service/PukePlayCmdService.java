package com.anbang.qipai.doudizhu.cqrs.c.service;

import java.util.ArrayList;

import com.anbang.qipai.doudizhu.cqrs.c.domain.result.PukeActionResult;
import com.anbang.qipai.doudizhu.cqrs.c.domain.result.QiangdizhuResult;
import com.anbang.qipai.doudizhu.cqrs.c.domain.result.ReadyToNextPanResult;

public interface PukePlayCmdService {

	PukeActionResult da(String playerId, ArrayList<Integer> paiIds, String dianshuZuheIdx, Long actionTime)
			throws Exception;

	PukeActionResult guo(String playerId, Long actionTime) throws Exception;

	ReadyToNextPanResult readyToNextPan(String playerId) throws Exception;

	QiangdizhuResult qiangdizhu(String playerId, Boolean qiang, Long currentTime) throws Exception;

    QiangdizhuResult jiaofenQiangdizhu(String playerId, Integer qiang, Long currentTime) throws Exception;

}
