package com.anbang.qipai.game.web.controller;

import com.anbang.qipai.game.plan.service.MemberAuthService;
import com.anbang.qipai.game.web.vo.CommonVO;
import com.anbang.qipai.game.websocket.HallWsNotifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin
@RestController
@RequestMapping("/invite")
public class InviteOnlineMemberController {

    @Autowired
    private HallWsNotifier wsNotifier;

    @Autowired
    private MemberAuthService memberAuthService;

    /**
     * 邀请玩家
     *
     * @param token          token
     * @param roomNo         房间号
     * @param inviteMemberId 邀请玩家
     * @param lianmengId     联盟ID
     * @return
     */
    @RequestMapping("/inviteOnlineMember")
    public CommonVO inviteOnlineMember(String token, String roomNo, String inviteMemberId, String lianmengId, String roomRule, String tablename, String nickname, String headimgurl,String lianmengType) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }

        wsNotifier.inviteMember(memberId, roomNo, inviteMemberId, lianmengId, roomRule, tablename, nickname, headimgurl,lianmengType);
        vo.setSuccess(true);
        return vo;
    }

}
