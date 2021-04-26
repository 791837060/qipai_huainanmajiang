package com.anbang.qipai.admin.remote.service;

import com.anbang.qipai.admin.remote.vo.CommonRemoteVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//远程调用大联盟接口
@FeignClient("qipai-dalianmeng/dalianmeng")
public interface DalianmengRemoteService {

	@RequestMapping("/power/giveYushiToMember")
	public CommonRemoteVO giveYushiToMember(@RequestParam(value = "memberId") String memberId,
                                            @RequestParam(value = "amount") Integer amount);

	@RequestMapping("/member/queryLianmeng")
	public CommonRemoteVO queryLianmeng(@RequestParam(value = "memberId") String memberId);

	//查询联盟(联盟详情查询)
	@RequestMapping("/lianmeng/querylianmeng")
	public CommonRemoteVO queryLianmeng(@RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "size", defaultValue = "30") int size,
                                        @RequestParam(value = "lianmengId") String lianmengId,
                                        @RequestParam(value = "memberId") String memberId);
	//查询联盟合伙人
	@RequestMapping("/lianmeng/querylianmeng_hehuoren")
	public CommonRemoteVO querylianmeng_hehuoren(@RequestParam(value = "page", defaultValue = "1") int page,
                                                 @RequestParam(value = "size", defaultValue = "30") int size,
                                                 @RequestParam(value = "lianmengId") String lianmengId);
	//查询联盟玩家
	@RequestMapping("/lianmeng/querylianmeng_member")
	public CommonRemoteVO querylianmeng_member(@RequestParam(value = "page", defaultValue = "1") int page,
                                               @RequestParam(value = "size", defaultValue = "30") int size,
                                               @RequestParam(value = "lianmengId") String lianmengId);

	//查询联盟的合伙人的玩家id
	@RequestMapping("/lianmeng/query_hehuoren_member")
	public CommonRemoteVO query_hehuoren_member(@RequestParam(value = "lianmengId") String lianmengId);

	//查询所有联盟盟主
	@RequestMapping("/lianmeng/lianmeng_mengzhu")
    CommonRemoteVO lianmeng_mengzhu(@RequestParam(value = "memberId") String memberId);

	//查询所有盟主的联盟
	@RequestMapping("/lianmeng/querylianmengBymengzhu")
    CommonRemoteVO querylianmengBymengzhu(@RequestParam(value = "page", defaultValue = "1") int page,
                                          @RequestParam(value = "size", defaultValue = "30") int size,
                                          @RequestParam(value = "mengzhuId") String mengzhuId);
	// 查询所有联盟消耗
	@RequestMapping("/lianmeng/lianmeng_cost")
    CommonRemoteVO lianmeng_cost(@RequestParam(value = "goldSort") String goldSort,
                                 @RequestParam(value = "gameSort") String gameSort,
                                 @RequestParam(value = "mengzhu") String mengzhu,
                                 @RequestParam(value = "lianmengId") String lianmengId,
                                 @RequestParam(value = "startTime") Long startTime,
                                 @RequestParam(value = "endTime") Long endTime);


	//生成回放码
	@RequestMapping("/result/shareplayback")
    CommonRemoteVO shareplayback(@RequestParam(value = "game") String game,
                                 @RequestParam(value = "gameId") String gameId,
                                 @RequestParam(value = "panNo") int panNo);
	//房间查询
	@RequestMapping("/game/gametable_query")
    CommonRemoteVO gametable_query(@RequestParam(value = "page", defaultValue = "1") int page,
                                   @RequestParam(value = "size", defaultValue = "20") int size,
                                   @RequestParam(value = "memberId") String memberId,
                                   @RequestParam(value = "lianmengId") String lianmengId,
                                   @RequestParam(value = "game") String game,
                                   @RequestParam(value = "startTime") Long startTime,
                                   @RequestParam(value = "endTime") Long endTime,
								   @RequestParam(value = "no") String no,
								   @RequestParam(value = "state") String state);
	//查询游戏日报
	@RequestMapping("/result/gamereport")
    CommonRemoteVO gamereport(@RequestParam(value = "startTime", required = true) Long startTime,
                              @RequestParam(value = "endTime", required = true) Long endTime,
                              @RequestParam(value = "game", required = true) String game);
	// 查看盟主能量消耗详情
	@RequestMapping("/power/queryMemberPowerDetail")
    CommonRemoteVO queryMemberPowerDetail(@RequestParam(value = "page", defaultValue = "1") int page,
                                          @RequestParam(value = "size", defaultValue = "30") int size,
                                          @RequestParam(value = "memberId") String memberId,
                                          @RequestParam(value = "lianmengId") String lianmengId,
                                          @RequestParam(value = "queryTime") long queryTime);
	//查看房间对局详情
	@RequestMapping("/result/queryroomdetail")
    CommonRemoteVO queryroomdetail(@RequestParam(value = "gameId") String gameId);


}
