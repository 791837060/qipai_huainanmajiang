package com.anbang.qipai.admin.web.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.admin.plan.bean.agents.AgentApplyRecord;
import com.anbang.qipai.admin.plan.bean.agents.AgentClubCard;
import com.anbang.qipai.admin.plan.bean.agents.AgentDbo;
import com.anbang.qipai.admin.plan.bean.agents.AgentImageDbo;
import com.anbang.qipai.admin.plan.bean.agents.AgentType;
import com.anbang.qipai.admin.plan.bean.agents.AgentWithdrawRecordDbo;
import com.anbang.qipai.admin.plan.service.agentsservice.AgentApplyRecordService;
import com.anbang.qipai.admin.plan.service.agentsservice.AgentClubCardRecordDboService;
import com.anbang.qipai.admin.plan.service.agentsservice.AgentClubCardService;
import com.anbang.qipai.admin.plan.service.agentsservice.AgentDboService;
import com.anbang.qipai.admin.plan.service.agentsservice.AgentImageDboService;
import com.anbang.qipai.admin.plan.service.agentsservice.AgentInvitationRecordService;
import com.anbang.qipai.admin.plan.service.agentsservice.AgentRewardRecordDboService;
import com.anbang.qipai.admin.plan.service.agentsservice.AgentScoreRecordDboService;
import com.anbang.qipai.admin.plan.service.agentsservice.AgentTypeService;
import com.anbang.qipai.admin.remote.service.QipaiAgentsRemoteService;
import com.anbang.qipai.admin.remote.vo.AccountRemoteVO;
import com.anbang.qipai.admin.remote.vo.CommonRemoteVO;
import com.anbang.qipai.admin.util.QiniuUtil;
import com.anbang.qipai.admin.util.QrCodeCreateUtil;
import com.anbang.qipai.admin.web.vo.CommonVO;
import com.anbang.qipai.admin.web.vo.agentsvo.AgentClubCardRecordDboVO;
import com.anbang.qipai.admin.web.vo.agentsvo.AgentDboVO;
import com.anbang.qipai.admin.web.vo.agentsvo.AgentInvitationRecordVO;
import com.anbang.qipai.admin.web.vo.agentsvo.AgentRewardRecordDboVO;
import com.anbang.qipai.admin.web.vo.agentsvo.AgentScoreRecordDboVO;
import com.highto.framework.web.page.ListPage;

/**
 * 推广员controller
 * 
 * @author 林少聪 2018.7.13
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/agent")
public class AgentController {

	@Autowired
	private AgentApplyRecordService agentApplyRecordService;

	@Autowired
	private AgentDboService agentDboService;

	@Autowired
	private AgentClubCardService agentClubCardService;

	@Autowired
	private AgentClubCardRecordDboService agentClubCardRecordDboService;

	@Autowired
	private AgentScoreRecordDboService agentScoreRecordDboService;

	@Autowired
	private QipaiAgentsRemoteService qipaiAgentsRemoteService;

	@Autowired
	private AgentInvitationRecordService agentInvitationRecordService;

	@Autowired
	private AgentImageDboService agentImageDboService;

	@Autowired
	private AgentTypeService agentTypeService;

	@Autowired
	private AgentRewardRecordDboService agentRewardRecordDboService;

	/**
	 * 查询推广员，不包括未通过申请的
	 * 
	 * @param page
	 * @param size
	 * @param agent
	 * @return
	 */
	@RequestMapping(value = "/queryagent", method = RequestMethod.POST)
	public CommonVO queryAgent(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
			AgentDboVO agent) {
		CommonVO vo = new CommonVO();
		Map data = new HashMap();
		ListPage listPage = agentDboService.findAgentDboByConditions(page, size, agent);
		data.put("agentList", listPage);
		long amount = agentDboService.countAmountByConditions(agent);
		data.put("amount", amount);
		vo.setSuccess(true);
		vo.setMsg("agentList");
		vo.setData(data);
		return vo;
	}

	/**
	 * 查看推广员详情
	 * 
	 * @param agentId
	 * @return
	 */
	@RequestMapping(value = "/agentdetail", method = RequestMethod.POST)
	public CommonVO queryAgentDetail(String agentId) {
		CommonVO vo = new CommonVO();
		Map<String, Object> data = new HashMap<String, Object>();
		AgentDbo agent = agentDboService.findAgentDboById(agentId);
		data.put("agent", agent);
		AccountRemoteVO accountRemoteVo = qipaiAgentsRemoteService.agent_account(agentId);
		if (accountRemoteVo.isSuccess()) {
			data.put("score", accountRemoteVo.getScore());
			data.put("clubCardZhou", accountRemoteVo.getClubCardZhou());
			data.put("clubCardYue", accountRemoteVo.getClubCardYue());
			data.put("clubCardJi", accountRemoteVo.getClubCardJi());
			data.put("clubCardRi", accountRemoteVo.getClubCardRi());
			data.put("coins", accountRemoteVo.getCoins());
			data.put("reward", accountRemoteVo.getReward());
		}
		vo.setSuccess(true);
		vo.setMsg("agent detail");
		vo.setData(data);
		return vo;
	}

	/**
	 * 查看绑定玩家
	 */
	@RequestMapping("/queryinvitatemember")
	public CommonVO queryInvitateMember(String agentId, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer size, Boolean haveLogin) {
		CommonVO vo = new CommonVO();
		Map<String, Object> data = new HashMap<String, Object>();
		AgentInvitationRecordVO record = new AgentInvitationRecordVO();
		record.setAgentId(agentId);
		record.setHaveLogin(haveLogin);
		ListPage listPage = agentInvitationRecordService.findInvitationRecordByConditions(page, size, record);
		data.put("listPage", listPage);
		vo.setSuccess(true);
		vo.setMsg("recordList");
		vo.setData(data);
		return vo;
	}

	/**
	 * 查看申请记录，包括已经处理过的
	 * 
	 * @param page
	 * @param size
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/queryapplyrecord", method = RequestMethod.POST)
	public CommonVO queryApplyRecord(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size, AgentApplyRecord record) {
		CommonVO vo = new CommonVO();
		ListPage listPage = agentApplyRecordService.findAgentApplyRecordByConditions(page, size, record);
		vo.setSuccess(true);
		vo.setMsg("recordList");
		vo.setData(listPage);
		return vo;
	}

	/**
	 * 查询推广员商城会员卡
	 * 
	 * @param page
	 * @param size
	 * @param card
	 * @return
	 */
	@RequestMapping(value = "/queryagentclubcard", method = RequestMethod.POST)
	public CommonVO queryAgentClubCard(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size, AgentClubCard card) {
		CommonVO vo = new CommonVO();
		ListPage listPage = agentClubCardService.findAgentClubCardByConditions(page, size, card);
		vo.setSuccess(true);
		vo.setMsg("cardList");
		vo.setData(listPage);
		return vo;
	}

	/**
	 * 查询会员卡记录
	 * 
	 * @param page
	 * @param size
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/queryclubcardrecord", method = RequestMethod.POST)
	public CommonVO queryClubCardRecord(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size, AgentClubCardRecordDboVO record) {
		CommonVO vo = new CommonVO();
		ListPage listPage = agentClubCardRecordDboService.findAgentClubCardRecordDboByConditions(page, size, record);
		vo.setSuccess(true);
		vo.setMsg("recordList");
		vo.setData(listPage);
		return vo;
	}

	/**
	 * 查询会员卡购买记录
	 * 
	 * @param page
	 * @param size
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/queryclubcardbuy", method = RequestMethod.POST)
	public CommonVO queryClubCardBuy(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size, AgentClubCardRecordDboVO record) {
		CommonVO vo = new CommonVO();
		record.setType("buy");
		ListPage listPage = agentClubCardRecordDboService.findAgentClubCardRecordDboByConditions(page, size, record);
		vo.setSuccess(true);
		vo.setMsg("recordList");
		vo.setData(listPage);
		return vo;
	}

	/**
	 * 查询推广员的积分记录
	 * 
	 * @param page
	 * @param size
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/queryscorerecord", method = RequestMethod.POST)
	public CommonVO queryScoreRecord(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size, AgentScoreRecordDboVO record) {
		CommonVO vo = new CommonVO();
		ListPage listPage = agentScoreRecordDboService.findAgentScoreRecordDboByConditions(page, size, record);
		vo.setSuccess(true);
		vo.setMsg("recordList");
		vo.setData(listPage);
		return vo;
	}

	/**
	 * 查询会员卡兑换记录
	 * 
	 * @param page
	 * @param size
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/queryscoreexchange", method = RequestMethod.POST)
	public CommonVO queryScoreExchange(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size, AgentScoreRecordDboVO record) {
		CommonVO vo = new CommonVO();
		record.setType("exchange");
		ListPage listPage = agentScoreRecordDboService.findAgentScoreRecordDboByConditions(page, size, record);
		vo.setSuccess(true);
		vo.setMsg("recordList");
		vo.setData(listPage);
		return vo;
	}

	/**
	 * 查询推广员的邀请记录
	 * 
	 * @param page
	 * @param size
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/queryagentinvitationrecord", method = RequestMethod.POST)
	public CommonVO queryAgentInvitationRecord(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size, AgentInvitationRecordVO record) {
		CommonVO vo = new CommonVO();
		ListPage listPage = agentInvitationRecordService.findInvitationRecordByConditions(page, size, record);
		vo.setSuccess(true);
		vo.setMsg("recordList");
		vo.setData(listPage);
		return vo;
	}

	/**
	 * 查询推广码使用记录
	 *
	 * @param page
	 * @param size
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/queryinvitecoderecord", method = RequestMethod.POST)
	public CommonVO queryInviteCodeRecord(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size, AgentInvitationRecordVO record) {
		CommonVO vo = new CommonVO();
		ListPage listPage = agentInvitationRecordService.findInviteCodeRecordByConditions(page, size, record);
		vo.setSuccess(true);
		vo.setMsg("recordList");
		vo.setData(listPage);
		return vo;
	}

	/**
	 * 查询代理类型
	 */
	@RequestMapping(value = "/queryagenttype", method = RequestMethod.POST)
	public CommonVO queryAgentType(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size, AgentType type) {
		CommonVO vo = new CommonVO();
		Map data = new HashMap<>();
		ListPage listPage = agentTypeService.findByConditions(page, size, type);
		data.put("listPage", listPage);
		vo.setSuccess(true);
		vo.setMsg("agenttype");
		vo.setData(data);
		return vo;
	}

	/**
	 * 查询推广收益
	 */
	@RequestMapping(value = "/queryagentreward", method = RequestMethod.POST)
	public CommonVO queryAgentReward(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size, AgentRewardRecordDboVO record) {
		CommonVO vo = new CommonVO();
		Map data = new HashMap<>();
		ListPage listPage = agentRewardRecordDboService.findAgentRewardRecordDboByConditions(page, size, record);
		data.put("listPage", listPage);
		vo.setSuccess(true);
		vo.setMsg("agent reward");
		vo.setData(data);
		return vo;
	}

	/**
	 * 查询推广员申请
	 */
	@RequestMapping(value = "/queryagentrewardapply", method = RequestMethod.POST)
	public CommonVO queryAgentRewardApply(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size, AgentWithdrawRecordDbo record) {
		CommonVO vo = new CommonVO();
		Map data = new HashMap<>();
		long amount = agentRewardRecordDboService.countAgentWithdrawRecordDboAmountByApplying();
		data.put("amount", amount);
		ListPage listPage = agentRewardRecordDboService.findAgentWithdrawRecordDboByConditions(page, size, record);
		data.put("listPage", listPage);
		vo.setSuccess(true);
		vo.setMsg("agent reward apply");
		vo.setData(data);
		return vo;
	}

	/**
	 * 推广员申请通过
	 * 
	 * @param recordId
	 * @return
	 */
	@RequestMapping(value = "/applypass", method = RequestMethod.POST)
	public CommonVO applyPass(String recordId, String type) {
		CommonVO vo = new CommonVO();
		AgentApplyRecord record = agentApplyRecordService.findAgentApplyRecordById(recordId);
		if (record == null) {
			vo.setSuccess(false);
			vo.setMsg("not found apply record");
			return vo;
		}
		AgentType agentType = agentTypeService.findByType(type);
		if (agentType == null) {
			vo.setSuccess(false);
			vo.setMsg("not found agenttype");
			return vo;
		}
		CommonRemoteVO rvo = qipaiAgentsRemoteService.apply_pass(recordId, agentType.getId());
		vo.setSuccess(rvo.isSuccess());
		return vo;
	}

	/**
	 * 推广员申请拒绝
	 * 
	 * @param recordId
	 * @return
	 */
	@RequestMapping(value = "/applyrefuse", method = RequestMethod.POST)
	public CommonVO applyRefuse(String recordId) {
		CommonVO vo = new CommonVO();
		AgentApplyRecord record = agentApplyRecordService.findAgentApplyRecordById(recordId);
		if (record == null) {
			vo.setSuccess(false);
			vo.setMsg("not found apply record");
			return vo;
		}
		CommonRemoteVO rvo = qipaiAgentsRemoteService.apply_refuse(recordId);
		vo.setSuccess(rvo.isSuccess());
		return vo;
	}

	/**
	 * 设置代理类型
	 */
	@RequestMapping(value = "/settype", method = RequestMethod.POST)
	public CommonVO setType(String agentId, String type) {
		CommonVO vo = new CommonVO();
		AgentType agentType = agentTypeService.findByType(type);
		if (type == null) {
			vo.setSuccess(false);
			vo.setMsg("not found agenttype");
			return vo;
		}
		CommonRemoteVO rvo = qipaiAgentsRemoteService.agent_settype(agentId, agentType.getId());
		vo.setSuccess(rvo.isSuccess());
		return vo;
	}

	/**
	 * 绑定推广员，设置上下级
	 * 
	 * @param agentId
	 * @param bossId
	 * @return
	 */
	@RequestMapping(value = "/setboss", method = RequestMethod.POST)
	public CommonVO setBoss(String agentId, String bossId) {
		CommonVO vo = new CommonVO();
		AgentDbo junior = agentDboService.findAgentDboById(agentId);
		if (junior == null) {
			vo.setSuccess(false);
			vo.setMsg("junior not found");
			return vo;
		}
		AgentDbo boss = agentDboService.findAgentDboById(bossId);
		if (boss == null) {
			vo.setSuccess(false);
			vo.setMsg("boss not found");
			return vo;
		}
		CommonRemoteVO rvo = qipaiAgentsRemoteService.agent_setboss(agentId, bossId, boss.getNickname());
		vo.setSuccess(rvo.isSuccess());
		return vo;
	}

	/**
	 * 解除绑定
	 * 
	 * @param agentId
	 * @return
	 */
	@RequestMapping(value = "/removeboss", method = RequestMethod.POST)
	public CommonVO removeBoss(String agentId) {
		CommonVO vo = new CommonVO();
		CommonRemoteVO rvo = qipaiAgentsRemoteService.agent_removeboss(agentId);
		vo.setSuccess(rvo.isSuccess());
		return vo;
	}

	/**
	 * 封禁推广员
	 * 
	 * @param agentId
	 * @return
	 */
	@RequestMapping(value = "/ban", method = RequestMethod.POST)
	public CommonVO ban(String agentId) {
		CommonVO vo = new CommonVO();
		CommonRemoteVO rvo = qipaiAgentsRemoteService.agent_ban(agentId);
		vo.setSuccess(rvo.isSuccess());
		AgentDbo agent = agentDboService.findAgentDboById(agentId);
		vo.setData(agent.getState());
		return vo;
	}

	/**
	 * 解封推广员
	 * 
	 * @param agentId
	 * @return
	 */
	@RequestMapping(value = "/liberate", method = RequestMethod.POST)
	public CommonVO liberate(String agentId) {
		CommonVO vo = new CommonVO();
		CommonRemoteVO rvo = qipaiAgentsRemoteService.agent_liberate(agentId);
		vo.setSuccess(rvo.isSuccess());
		AgentDbo agent = agentDboService.findAgentDboById(agentId);
		vo.setData(agent.getState());
		return vo;
	}

	/**
	 * 添加推广员商城会员卡，包括积分会员卡和人民币会员卡
	 * 
	 * @param card
	 * @return
	 */
	@RequestMapping(value = "/addagentclubcard", method = RequestMethod.POST)
	public CommonVO addAgentClubCard(AgentClubCard card) {
		card.valid();
		CommonVO vo = new CommonVO();
		card.setSale(true);
		CommonRemoteVO rvo = qipaiAgentsRemoteService.clubcard_addagentclubcard(card);
		vo.setSuccess(rvo.isSuccess());
		return vo;
	}

	/**
	 * 修改推广员商城会员卡
	 * 
	 * @param card
	 * @return
	 */
	@RequestMapping(value = "/updateagentclubcard", method = RequestMethod.POST)
	public CommonVO updateAgentClubCard(AgentClubCard card) {
		card.valid();
		CommonVO vo = new CommonVO();
		CommonRemoteVO rvo = qipaiAgentsRemoteService.clubcard_updateagentclubcard(card);
		vo.setSuccess(rvo.isSuccess());
		return vo;
	}

	/**
	 * 删除推广员会员卡
	 * 
	 * @param cardId
	 * @return
	 */
	@RequestMapping(value = "/deleteagentclubcard", method = RequestMethod.POST)
	public CommonVO deleteAgentClubCard(String cardId) {
		CommonVO vo = new CommonVO();
		CommonRemoteVO rvo = qipaiAgentsRemoteService.clubcard_deleteagentclubcard(cardId);
		vo.setSuccess(rvo.isSuccess());
		return vo;
	}

	/**
	 * 调整推广员会员卡
	 * 
	 * @param agentId
	 * @param cardAmount
	 * @return
	 */
	@RequestMapping(value = "/clubcardmanager", method = RequestMethod.POST)
	public CommonVO clubcardManager(String agentId, String card, int cardAmount) {
		CommonVO vo = new CommonVO();
		CommonRemoteVO rvo = new CommonRemoteVO();
		rvo.setSuccess(false);
		if ("ri".equals(card)) {
			rvo = qipaiAgentsRemoteService.clubcard_giveclubcardritoagent(agentId, cardAmount, "admin adjust");
		}
		if ("zhou".equals(card)) {
			rvo = qipaiAgentsRemoteService.clubcard_giveclubcardzhoutoagent(agentId, cardAmount, "admin adjust");
		}
		if ("yue".equals(card)) {
			rvo = qipaiAgentsRemoteService.clubcard_giveclubcardyuetoagent(agentId, cardAmount, "admin adjust");
		}
		if ("ji".equals(card)) {
			rvo = qipaiAgentsRemoteService.clubcard_giveclubcardjitoagent(agentId, cardAmount, "admin adjust");
		}
		vo.setSuccess(rvo.isSuccess());
		vo.setMsg(rvo.getMsg());
		return vo;
	}

	/**
	 * 推广员玉石调整
	 */
	@RequestMapping(value = "/goldmanager", method = RequestMethod.POST)
	public CommonVO goldManager(String agentId, int goldAmount) {
		CommonVO vo = new CommonVO();
		CommonRemoteVO rvo = new CommonRemoteVO();
		rvo.setSuccess(false);
		rvo = qipaiAgentsRemoteService.clubcard_giveclubcardcoinstoagent(agentId, goldAmount, "admin adjust");
		vo.setSuccess(rvo.isSuccess());
		vo.setMsg(rvo.getMsg());
		return vo;
	}

	/**
	 * 调整推广员积分
	 * 
	 * @param agentId
	 * @param scoreAmount
	 * @return
	 */
	@RequestMapping(value = "/scoremanager", method = RequestMethod.POST)
	public CommonVO scoreManager(String agentId, int scoreAmount) {
		CommonVO vo = new CommonVO();
		CommonRemoteVO rvo = new CommonRemoteVO();
		rvo.setSuccess(false);
		rvo = qipaiAgentsRemoteService.score_givescoretoagent(agentId, scoreAmount, "admin adjust");
		vo.setSuccess(rvo.isSuccess());
		vo.setMsg(rvo.getMsg());
		return vo;
	}

	/**
	 * 查询推广员二维码
	 */
	@RequestMapping(value = "/qrcode", method = RequestMethod.GET)
	public void qrcode(String agentId, HttpServletResponse response) {
		AgentDbo agent = agentDboService.findAgentDboById(agentId);
		if (agent != null) {
			try {
				String REDIRECT_URI = "http://3cs.3cscy.com/image/qrcode_redirect?invitationCode="
						+ agent.getInvitationCode();
				// 合成二维码
				BufferedImage qrCodeImg = QrCodeCreateUtil.createQrCode(REDIRECT_URI, 1000);
				// 获取LOGO
				BufferedImage logo = ImageIO.read(new File("/data/app/qipai_admin_facade/logo.jpg"));
				// 合成图片
				QrCodeCreateUtil.mergeImag(qrCodeImg, logo, 350, 350, 200, 200);
				ImageIO.write(qrCodeImg, "jpg", response.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 查询推广图
	 */
	@RequestMapping(value = "/queryimage", method = RequestMethod.POST)
	public CommonVO queryimage() {
		CommonVO vo = new CommonVO();
		Map data = new HashMap<>();
		List<AgentImageDbo> imageList = agentImageDboService.findAgentImageDbo();
		data.put("imageList", imageList);
		vo.setSuccess(true);
		vo.setMsg("imageList");
		vo.setData(data);
		return vo;
	}

	/**
	 * 添加推广图
	 */
	@RequestMapping(value = "/addimage", method = RequestMethod.POST)
	public CommonVO addimage(String fileName, @RequestParam(required = true) Integer ordinal) {
		CommonVO vo = new CommonVO();
		if (ordinal < 1 || ordinal > 3) {
			vo.setSuccess(false);
			vo.setMsg("invalid ordinal");
			return vo;
		}
		agentImageDboService.deleteAgentImageDboByOrdinal(ordinal);
		AgentImageDbo image = new AgentImageDbo();
		image.setOrdinal(ordinal);
		image.setFileName(fileName);
		image.setDownloadUrl("http://qiniu.3cscy.com/" + fileName);
		String[] s = fileName.split("\\.");
		String imageFormat = "";
		try {
			imageFormat = s[s.length - 1];
		} catch (Exception e) {
			vo.setSuccess(false);
			vo.setMsg(e.getClass().getName());
			return vo;
		}
		image.setImageFormat(imageFormat);
		CommonRemoteVO rvo = qipaiAgentsRemoteService.image_addimage(image);
		vo.setSuccess(rvo.isSuccess());
		return vo;
	}

	/**
	 * 删除推广图
	 */
	@RequestMapping(value = "/deleteimage", method = RequestMethod.POST)
	public CommonVO deleteimage(String imageId) {
		CommonVO vo = new CommonVO();
		CommonRemoteVO rvo = qipaiAgentsRemoteService.image_deleteimage(imageId);
		vo.setSuccess(rvo.isSuccess());
		return vo;
	}

	/**
	 * 七牛云上传图片token
	 */
	@RequestMapping(value = "/uptoken", method = RequestMethod.POST)
	public CommonVO uptoken() {
		CommonVO vo = new CommonVO();
		Map data = new HashMap<>();
		String uptoken = QiniuUtil.getUpToken();
		data.put("uptoken", uptoken);
		vo.setSuccess(true);
		vo.setMsg("uptoken");
		vo.setData(data);
		return vo;
	}

	/**
	 * 添加代理类型
	 */
	@RequestMapping("/addagenttype")
	public CommonVO addAgentType(AgentType type) {
		CommonVO vo = new CommonVO();
		if (type.getMemberReward() > 50 || type.getJuniorReward() > 15) {
			vo.setSuccess(false);
			vo.setMsg("reward too much");
			return vo;
		}
		if (Double.valueOf(type.getMemberReward() * 100).intValue() != type.getMemberReward() * 100
				|| Double.valueOf(type.getJuniorReward() * 100).intValue() != type.getJuniorReward() * 100) {
			vo.setSuccess(false);
			vo.setMsg("invalid reward");
			return vo;
		}
		type.setMemberReward(type.getMemberReward() / 100);
		type.setJuniorReward(type.getJuniorReward() / 100);
		CommonRemoteVO rvo = qipaiAgentsRemoteService.agent_addagenttype(type);
		vo.setSuccess(rvo.isSuccess());
		vo.setMsg(rvo.getMsg());
		return vo;
	}

	/**
	 * 修改代理类型
	 */
	@RequestMapping("/updateagenttype")
	public CommonVO updateAgentType(AgentType type) {
		CommonVO vo = new CommonVO();
		if (type.getMemberReward() > 50 || type.getJuniorReward() > 15) {
			vo.setSuccess(false);
			vo.setMsg("reward too much");
			return vo;
		}
		if (Double.valueOf(type.getMemberReward() * 100).intValue() != type.getMemberReward() * 100
				|| Double.valueOf(type.getJuniorReward() * 100).intValue() != type.getJuniorReward() * 100) {
			vo.setSuccess(false);
			vo.setMsg("invalid reward");
			return vo;
		}
		type.setMemberReward(type.getMemberReward() / 100);
		type.setJuniorReward(type.getJuniorReward() / 100);
		CommonRemoteVO rvo = qipaiAgentsRemoteService.agent_updateagenttype(type);
		vo.setSuccess(rvo.isSuccess());
		vo.setMsg(rvo.getMsg());
		return vo;
	}

	/**
	 * 删除代理类型
	 */
	@RequestMapping("/removeagenttype")
	public CommonVO removeAgentType(@RequestParam(value = "id") String[] typeIds) {
		CommonVO vo = new CommonVO();
		CommonRemoteVO rvo = qipaiAgentsRemoteService.agent_removeagenttype(typeIds);
		vo.setSuccess(rvo.isSuccess());
		vo.setMsg(rvo.getMsg());
		return vo;
	}

	/**
	 * 推广员提现申请通过
	 */
	@RequestMapping(value = "/rewardapplypass", method = RequestMethod.POST)
	public CommonVO rewardApplyPass(String recordId) {
		CommonVO vo = new CommonVO();
		CommonRemoteVO rvo = qipaiAgentsRemoteService.reward_rewardapplypass(recordId);
		vo.setSuccess(rvo.isSuccess());
		vo.setMsg(rvo.getMsg());
		return vo;
	}

	/**
	 * 推广员提现申请拒绝
	 */
	@RequestMapping(value = "/rewardapplyrefuse", method = RequestMethod.POST)
	public CommonVO rewardApplyRefuse(String recordId, String desc) {
		CommonVO vo = new CommonVO();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		CommonRemoteVO rvo = qipaiAgentsRemoteService.reward_rewardapplyrefuse(recordId, uuid, desc);
		vo.setSuccess(rvo.isSuccess());
		vo.setMsg(rvo.getMsg());
		return vo;
	}
}
