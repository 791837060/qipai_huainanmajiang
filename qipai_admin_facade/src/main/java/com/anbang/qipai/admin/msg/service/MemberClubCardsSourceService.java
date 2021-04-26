package com.anbang.qipai.admin.msg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import com.anbang.qipai.admin.msg.channel.source.MemberClubCardsSource;
import com.anbang.qipai.admin.msg.msjobj.CommonMO;
import com.anbang.qipai.admin.plan.bean.members.MemberClubCard;

/**
*@author   created by hanzhuofan  2018.09.26
*/
@EnableBinding(MemberClubCardsSource.class)
public class MemberClubCardsSourceService {

	@Autowired
	private MemberClubCardsSource memberClubCardsSource;
	
	public void addClubCard(MemberClubCard card) {
		CommonMO mo = new CommonMO();
		mo.setMsg(MemberClubCardsSource.addClubCard);
		mo.setData(card);
		memberClubCardsSource.memberClubCard().send(MessageBuilder.withPayload(mo).build());
	}

	public void deleteClubCards(String[] clubCardIds) {
		CommonMO mo = new CommonMO();
		mo.setMsg(MemberClubCardsSource.deleteClubCard);
		mo.setData(clubCardIds);
		memberClubCardsSource.memberClubCard().send(MessageBuilder.withPayload(mo).build());
	}

	public void updateClubCards(MemberClubCard clubCard) {
		CommonMO mo = new CommonMO();
		mo.setMsg(MemberClubCardsSource.updateClubCard);
		mo.setData(clubCard);
		memberClubCardsSource.memberClubCard().send(MessageBuilder.withPayload(mo).build());
	}
}
