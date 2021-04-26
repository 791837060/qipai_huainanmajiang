package com.anbang.qipai.dalianmeng.cqrs.c.service.impl;

import com.anbang.qipai.dalianmeng.cqrs.c.domain.playback.PlayBackCodeManager;
import com.anbang.qipai.dalianmeng.cqrs.c.service.PlayBackCodeCmdService;
import org.springframework.stereotype.Component;

@Component
public class PlayBackCodeCmdServiceImpl extends CmdServiceBase implements PlayBackCodeCmdService {

	@Override
	public Integer getPlayBackCode() {
		PlayBackCodeManager playBackCodeManager = singletonEntityRepository.getEntity(PlayBackCodeManager.class);
		int code = playBackCodeManager.getPlayBackCode();
		return code;
	}

}
