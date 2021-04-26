package com.anbang.qipai.admin.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.admin.msg.service.MemberClubCardsSourceService;
import com.anbang.qipai.admin.plan.bean.members.MemberClubCard;
import com.anbang.qipai.admin.plan.service.membersservice.MemberClubCardService;
import com.anbang.qipai.admin.web.vo.CommonVO;

/**
 * 会员卡controller
 * 
 * @author 林少聪 2018.7.9
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/clubcard")
public class ClubCardController {

	@Autowired
	private MemberClubCardService clubCardService;

	@Autowired
	private MemberClubCardsSourceService memberClubCardsSourceService;

	/**
	 * 查询游戏商城会员卡列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/showclubcard", method = RequestMethod.POST)
	public CommonVO showClubCard() {
		CommonVO vo = new CommonVO();
		List<MemberClubCard> cardList = clubCardService.showClubCard();
		vo.setSuccess(true);
		vo.setMsg("clubCardList");
		vo.setData(cardList);
		return vo;
	}

	/**
	 * 添加游戏商城会员卡
	 * 
	 * @param clubCard
	 * @return
	 */
	@RequestMapping(value = "/addclubcard", method = RequestMethod.POST)
	public CommonVO addClubCard(MemberClubCard clubCard) {
		CommonVO vo = new CommonVO();
		if (clubCard.getName() == null) {
			vo.setSuccess(false);
			vo.setMsg("at least one param is null");
			return vo;
		}
		if (clubCard.getFirstDiscount() < 0.1 || clubCard.getFirstDiscount() > 1) {
			vo.setSuccess(false);
			vo.setMsg("invalid first discount");
			return vo;
		}
		long time = clubCard.getTime() * 24L * 60 * 60 * 1000;
		clubCard.setTime(time);
		clubCardService.addClubCard(clubCard);
		memberClubCardsSourceService.addClubCard(clubCard);
		vo.setSuccess(true);
		return vo;
	}

	/**
	 * 删除游戏商城会员卡
	 * 
	 * @param clubCardIds
	 * @return
	 */
	@RequestMapping(value = "/deleteclubcard", method = RequestMethod.POST)
	public CommonVO deleteClubCard(@RequestParam(value = "id") String[] clubCardIds) {
		CommonVO vo = new CommonVO();
		clubCardService.deleteClubCardByIds(clubCardIds);
		memberClubCardsSourceService.deleteClubCards(clubCardIds);
		vo.setSuccess(true);
		return vo;
	}

	/**
	 * 修改游戏商城会员卡
	 * 
	 * @param clubCard
	 * @return
	 */
	@RequestMapping(value = "/updateclubcard", method = RequestMethod.POST)
	public CommonVO updateClubCard(MemberClubCard clubCard) {
		CommonVO vo = new CommonVO();
		if (clubCard.getFirstDiscount() < 0.1 || clubCard.getFirstDiscount() > 1) {
			vo.setSuccess(false);
			vo.setMsg("invalid first discount");
			return vo;
		}
		long time = clubCard.getTime() * 24L * 60 * 60 * 1000;
		clubCard.setTime(time);
		clubCardService.updateClubCard(clubCard);
		memberClubCardsSourceService.updateClubCards(clubCard);
		vo.setSuccess(true);
		return vo;
	}

}
