package com.anbang.qipai.qinyouquan.cqrs.c.service;

import com.anbang.qipai.qinyouquan.cqrs.c.domain.CreateLianmengResult;
import com.anbang.qipai.qinyouquan.cqrs.c.domain.JoinLianmengResult;

public interface LianmengCmdService {
    CreateLianmengResult createNewLianmengId(String memberId, Long currentTime) throws Exception;

    String removeLianmeng(String lianmengId)throws Exception;

    JoinLianmengResult joinLianmeng(String memberId, String lianmengId) throws Exception;

}
