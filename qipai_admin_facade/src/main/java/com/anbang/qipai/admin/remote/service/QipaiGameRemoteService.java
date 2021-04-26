package com.anbang.qipai.admin.remote.service;

import com.anbang.qipai.admin.plan.bean.games.*;
import com.anbang.qipai.admin.remote.vo.CommonRemoteVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 游戏大厅远程服务
 *
 * @author neo
 */
@FeignClient("qipai-game/game")
public interface QipaiGameRemoteService {

    @RequestMapping(value = "/game/query_memberplayingroom")
    public CommonRemoteVO game_queryMemberPlayingRoom(@RequestParam("memberId") String memberId);

    @RequestMapping(value = "/game/game_server_online")
    public CommonRemoteVO game_gameServerOnline(@RequestBody GameServer gameServer);

    @RequestMapping(value = "/game/game_server_offline")
    public CommonRemoteVO game_gameServerOffline(@RequestBody String[] gameServerIds);

    @RequestMapping(value = "/game/add_law")
    public CommonRemoteVO game_addLaw(@RequestBody GameLaw law);

    @RequestMapping(value = "/game/update_law")
    public CommonRemoteVO game_updateLaw(@RequestBody GameLaw law);

    @RequestMapping(value = "/game/remove_law")
    public CommonRemoteVO game_removelaw(@RequestParam("lawId") String lawId);

    @RequestMapping(value = "/game/add_mutexgroup")
    public CommonRemoteVO game_addmutexgroup(@RequestBody LawsMutexGroup lawsMutexGroup);

    @RequestMapping(value = "/game/remove_mutexgroup")
    public CommonRemoteVO game_removemutexgroup(@RequestParam("groupId") String groupId);

    @RequestMapping("/game/remove")
    public CommonRemoteVO game_remove(@RequestParam(value = "endTime") long endTime);

    @RequestMapping(value = "/sysnotice/addnotice")
    public CommonRemoteVO addNotice(@RequestParam(value = "content") String content,
                                    @RequestBody SystemNoticePlace[] places, @RequestParam(value = "adminName") String adminName);

    @RequestMapping(value = "/sysnotice/startnotice")
    public CommonRemoteVO startNotice(@RequestParam(value = "id") String id,
                                      @RequestParam(value = "adminName") String adminName);

    @RequestMapping(value = "/sysnotice/stopnotice")
    public CommonRemoteVO stopNotice(@RequestParam(value = "id") String id,
                                     @RequestParam(value = "adminName") String adminName);

    @RequestMapping(value = "/sysnotice/removenotice")
    public CommonRemoteVO removeNotice(@RequestParam(value = "id") String id,
                                       @RequestParam(value = "adminName") String adminName);

    @RequestMapping(value = "/mail/addmail")
    public CommonRemoteVO addmail(@RequestParam(value = "mail") String mail);

    @RequestMapping(value = "/mail/addmailbyid")
    public CommonRemoteVO addMailById(@RequestParam(value = "mail") String mail, @RequestBody List<String> idss);

    @RequestMapping(value = "/member/plan_rights_conf")
    public CommonRemoteVO commonuser(@RequestParam(value = "planMemberRoomsCount") int planMemberRoomsCount,
                                     @RequestParam(value = "planMemberRoomsAliveHours") int planMemberRoomsAliveHours,
                                     @RequestParam(value = "planMemberMaxCreateRoomDaily") int planMemberMaxCreateRoomDaily,
                                     @RequestParam(value = "planMemberCreateRoomDailyGoldPrice") int planMemberCreateRoomDailyGoldPrice,
                                     @RequestParam(value = "planMemberJoinRoomGoldPrice") int planMemberJoinRoomGoldPrice);

    @RequestMapping(value = "/member/vip_rights_conf")
    public CommonRemoteVO vipuser(@RequestParam(value = "vipMemberRoomsCount") int vipMemberRoomsCount,
                                  @RequestParam(value = "vipMemberRoomsAliveHours") int vipMemberRoomsAliveHours);

    @RequestMapping(value = "/snapshot/save")
    CommonRemoteVO snapshot_save();

    @RequestMapping(value = "/result/query_historicalrecord")
    public CommonRemoteVO queryHistoricalRecord(@RequestParam(value = "page") Integer page,
                                                @RequestParam(value = "size") Integer size,
                                                @RequestParam(value = "memberId") String memberId);

    @RequestMapping(value = "/result/query_backcode")
    public CommonRemoteVO queryBackcode(@RequestParam(value = "game") Game game,
                                        @RequestParam(value = "gameId") String gameId,
                                        @RequestParam(value = "panNo") Integer panNo);

    @RequestMapping(value = "/result/get_backcode")
    public CommonRemoteVO getBackcode(@RequestParam(value = "game") Game game,
                                      @RequestParam(value = "gameId") String gameId,
                                      @RequestParam(value = "panNo") Integer panNo);
}
