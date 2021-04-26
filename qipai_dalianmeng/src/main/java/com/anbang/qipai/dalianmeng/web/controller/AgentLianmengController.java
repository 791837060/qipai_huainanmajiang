package com.anbang.qipai.dalianmeng.web.controller;


import com.anbang.qipai.dalianmeng.cqrs.q.dbo.AllianceDbo;
import com.anbang.qipai.dalianmeng.cqrs.q.dbo.Identity;
import com.anbang.qipai.dalianmeng.cqrs.q.dbo.MemberLianmengDbo;
import com.anbang.qipai.dalianmeng.cqrs.q.service.LianmengMemberService;
import com.anbang.qipai.dalianmeng.cqrs.q.service.LianmengService;
import com.anbang.qipai.dalianmeng.plan.bean.game.GameServer;
import com.anbang.qipai.dalianmeng.plan.bean.game.GameTable;
import com.anbang.qipai.dalianmeng.plan.service.MemberAuthService;
import com.anbang.qipai.dalianmeng.plan.service.PlayService;
import com.anbang.qipai.dalianmeng.web.vo.CommonVO;
import com.google.gson.Gson;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 用户联盟管理
 *
 * @author lsc
 */
@CrossOrigin
@RestController
@RequestMapping("/agentlianmeng")
public class AgentLianmengController {

    @Autowired
    private MemberAuthService memberAuthService;

    @Autowired
    private LianmengService lianmengService;

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private PlayService playService;

    @Autowired
    private LianmengMemberService lianmengMemberService;

    private Gson gson = new Gson();



    @RequestMapping("/gameQuit")
    public CommonVO gameQuit(String token, String lianmengId, String roomNo) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (StringUtils.isEmpty(memberId)) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        AllianceDbo allianceDbo = lianmengService.findAllianceDboById(lianmengId);
        if (allianceDbo == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid lianmengId");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo = lianmengService.findMemberLianmengDboByMemberIdAndLianmengId(memberId, lianmengId);
        MemberLianmengDbo memberLianmengDbo1 = memberLianmengDbo;

        if (!StringUtils.isEmpty(memberLianmengDbo1.getZhushouId())) {
            if (lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId) != null) {
                memberLianmengDbo1 = lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId);
            }
        }
        if (!memberLianmengDbo1.getIdentity().equals(Identity.MENGZHU)) {
            vo.setSuccess(false);
            vo.setMsg("not mengzhu");
            return vo;
        }
        GameTable gameTable = playService.findOpenGameTable(roomNo);
        if (gameTable == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid room number");
            return vo;
        }
        GameServer gameServer = gameTable.getServerGame().getServer();
        Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/quitGame");
        req.param("gameId", gameTable.getServerGame().getGameId());
        Map resData;
        try {
            ContentResponse res = req.send();
            String resJson = new String(res.getContent());
            CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
            if (resVo.isSuccess()) {
                resData = (Map) resVo.getData();
            } else {
                playService.deleteGameTableByNo(roomNo);
                vo.setSuccess(true);
                vo.setMsg("quit game success");
                return vo;
            }
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }
        vo.setData(resData);
        return vo;
    }


}
