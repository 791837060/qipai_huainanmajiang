package com.anbang.qipai.qinyouquan.cqrs.c.service.impl;

import com.anbang.qipai.qinyouquan.cqrs.c.domain.playback.PlayBackCodeManager;
import com.anbang.qipai.qinyouquan.cqrs.c.service.PlayBackCodeCmdService;
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
