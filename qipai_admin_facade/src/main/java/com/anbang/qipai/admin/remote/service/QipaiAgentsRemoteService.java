package com.anbang.qipai.admin.remote.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anbang.qipai.admin.plan.bean.agents.AgentClubCard;
import com.anbang.qipai.admin.plan.bean.agents.AgentImageDbo;
import com.anbang.qipai.admin.plan.bean.agents.AgentType;
import com.anbang.qipai.admin.remote.vo.AccountRemoteVO;
import com.anbang.qipai.admin.remote.vo.CommonRemoteVO;

@FeignClient("qipai-agents")
public interface QipaiAgentsRemoteService {

	@RequestMapping("/apply/applypass")
	public CommonRemoteVO apply_pass(@RequestParam(value = "recordId") String recordId,
			@RequestParam(value = "typeId") String typeId);

	@RequestMapping("/apply/applyrefuse")
	public CommonRemoteVO apply_refuse(@RequestParam(value = "recordId") String recordId);

	@RequestMapping("/agent/agentaccount")
	public AccountRemoteVO agent_account(@RequestParam(value = "agentId") String agentId);

	@RequestMapping("/agent/settype")
	public CommonRemoteVO agent_settype(@RequestParam(value = "agentId") String agentId,
			@RequestParam(value = "typeId") String typeId);

	@RequestMapping("/agent/setboss")
	public CommonRemoteVO agent_setboss(@RequestParam(value = "agentId") String agentId,
			@RequestParam(value = "bossId") String bossId, @RequestParam(value = "bossName") String bossName);

	@RequestMapping("/agent/removeboss")
	public CommonRemoteVO agent_removeboss(@RequestParam(value = "agentId") String agentId);

	@RequestMapping("/agent/ban")
	public CommonRemoteVO agent_ban(@RequestParam(value = "agentId") String agentId);

	@RequestMapping("/agent/liberate")
	public CommonRemoteVO agent_liberate(@RequestParam(value = "agentId") String agentId);

	@RequestMapping("/clubcard/addagentclubcard")
	public CommonRemoteVO clubcard_addagentclubcard(@RequestBody AgentClubCard card);

	@RequestMapping("/clubcard/updateagentclubcard")
	public CommonRemoteVO clubcard_updateagentclubcard(@RequestBody AgentClubCard card);

	@RequestMapping("/clubcard/deleteagentclubcard")
	public CommonRemoteVO clubcard_deleteagentclubcard(@RequestParam(value = "cardId") String cardId);

	@RequestMapping("/score/givescoretoagent")
	public CommonRemoteVO score_givescoretoagent(@RequestParam(value = "agentId") String agentId,
			@RequestParam(value = "amount") int amount, @RequestParam(value = "textSummary") String textSummary);

	@RequestMapping("/clubcard/giveclubcardcoinstoagent")
	public CommonRemoteVO clubcard_giveclubcardcoinstoagent(@RequestParam(value = "agentId") String agentId,
			@RequestParam(value = "amount") int amount, @RequestParam(value = "textSummary") String textSummary);

	@RequestMapping("/clubcard/giveclubcardritoagent")
	public CommonRemoteVO clubcard_giveclubcardritoagent(@RequestParam(value = "agentId") String agentId,
			@RequestParam(value = "amount") int amount, @RequestParam(value = "textSummary") String textSummary);

	@RequestMapping("/clubcard/giveclubcardzhoutoagent")
	public CommonRemoteVO clubcard_giveclubcardzhoutoagent(@RequestParam(value = "agentId") String agentId,
			@RequestParam(value = "amount") int amount, @RequestParam(value = "textSummary") String textSummary);

	@RequestMapping("/clubcard/giveclubcardyuetoagent")
	public CommonRemoteVO clubcard_giveclubcardyuetoagent(@RequestParam(value = "agentId") String agentId,
			@RequestParam(value = "amount") int amount, @RequestParam(value = "textSummary") String textSummary);

	@RequestMapping("/clubcard/giveclubcardjitoagent")
	public CommonRemoteVO clubcard_giveclubcardjitoagent(@RequestParam(value = "agentId") String agentId,
			@RequestParam(value = "amount") int amount, @RequestParam(value = "textSummary") String textSummary);

	@RequestMapping("/image/addimage")
	public CommonRemoteVO image_addimage(@RequestBody AgentImageDbo image);

	@RequestMapping("/image/deleteimage")
	public CommonRemoteVO image_deleteimage(@RequestParam(value = "imageId") String imageId);

	@RequestMapping("/agent/addagenttype")
	public CommonRemoteVO agent_addagenttype(@RequestBody AgentType type);

	@RequestMapping("/agent/updateagenttype")
	public CommonRemoteVO agent_updateagenttype(@RequestBody AgentType type);

	@RequestMapping("/agent/removeagenttype")
	public CommonRemoteVO agent_removeagenttype(@RequestBody String[] typeIds);

	@RequestMapping("/reward/rewardapplypass")
	public CommonRemoteVO reward_rewardapplypass(@RequestParam(value = "recordId") String recordId);

	@RequestMapping("/reward/rewardapplyrefuse")
	public CommonRemoteVO reward_rewardapplyrefuse(@RequestParam(value = "recordId") String recordId,
			@RequestParam(value = "orderId") String orderId, @RequestParam(value = "desc") String desc);
}
