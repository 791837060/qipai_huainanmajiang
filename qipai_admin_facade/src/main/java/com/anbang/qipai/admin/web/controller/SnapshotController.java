package com.anbang.qipai.admin.web.controller;

import com.anbang.qipai.admin.plan.service.gameservice.GameServerService;
import com.anbang.qipai.admin.remote.service.QipaiChaguanRemoteService;
import com.anbang.qipai.admin.remote.service.QipaiGameRemoteService;
import com.anbang.qipai.admin.remote.service.QipaiMembersRemoteService;
import com.anbang.qipai.admin.remote.service.QipaiXiuxianchangRemoteService;
import com.anbang.qipai.admin.web.vo.CommonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/snapshot")
public class SnapshotController {

    @Autowired
    private QipaiMembersRemoteService qipaiMembersRemoteService;

    @Autowired
    private QipaiGameRemoteService qipaiGameRemoteService;

    @Autowired
    private QipaiChaguanRemoteService qipaiChaguanRemoteService;

    @Autowired
    private QipaiXiuxianchangRemoteService qipaiXiuxianchangRemoteService;

    @Autowired
    private GameServerService gameServerService;

    /**
     *
     * 删除一个月前的游戏数据
     *
     * @return
     */
    @RequestMapping("/removegamedata")
    public CommonVO removegamedata() {
        CommonVO vo = new CommonVO();
        long endTime = System.currentTimeMillis() - 30L * 24 * 60 * 60 * 1000;
        qipaiGameRemoteService.game_remove(endTime);
        qipaiChaguanRemoteService.game_remove(endTime);
        qipaiXiuxianchangRemoteService.game_remove(endTime);
        return vo;
    }

    @RequestMapping("/savemembers")
    public CommonVO saveMembers() {
        CommonVO vo = new CommonVO();
        vo.setSuccess(true);
        try {
            this.qipaiMembersRemoteService.snapshot_save();
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
        return vo;
    }

    @RequestMapping("/savegame")
    public CommonVO saveGame() {
        CommonVO vo = new CommonVO();
        vo.setSuccess(true);
        try {
            this.qipaiGameRemoteService.snapshot_save();
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
        return vo;
    }

    @RequestMapping("/savegameserver")
    public CommonVO saveGameServer(@RequestParam("ids[]") List<String> ids) {
        CommonVO vo = new CommonVO();
        vo.setSuccess(true);
        try {
            List<String> failedMsg = this.gameServerService.saveSnapshot(ids);
            for (String s : failedMsg) {
                System.out.println(s);
            }
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
        return vo;
    }

}
