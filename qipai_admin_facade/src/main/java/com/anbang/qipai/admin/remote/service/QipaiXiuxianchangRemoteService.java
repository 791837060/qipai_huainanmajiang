package com.anbang.qipai.admin.remote.service;

import com.anbang.qipai.admin.remote.vo.CommonRemoteVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("qipai-xiuxianchang")
public interface QipaiXiuxianchangRemoteService {

    /**
     * 增加休闲场金币
     */
    @RequestMapping(value = "/gold/members_givegold")
    public CommonRemoteVO gold_members_givegold(@RequestBody String[] memberIds,
                                                @RequestParam(value = "amount") int amount, @RequestParam(value = "textSummary") String textSummary);

    @RequestMapping(value = "/game/query_memberplayingroom")
    public CommonRemoteVO game_queryMemberPlayingRoom(@RequestParam("page") int page, @RequestParam("size") int size);

    @RequestMapping("/game/remove")
    public CommonRemoteVO game_remove(@RequestParam(value = "endTime") long endTime);
}
