package com.anbang.qipai.admin.plan.service.membersservice;

import java.util.ArrayList;
import java.util.List;

import com.anbang.qipai.admin.remote.service.QipaiMembersRemoteService;
import com.anbang.qipai.admin.remote.vo.CommonRemoteVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.members.MemberClubCard;
import com.anbang.qipai.admin.plan.dao.membersdao.MemberClubCardDao;

@Service
public class MemberClubCardService {

    @Autowired
    private MemberClubCardDao clubCardDao;

    @Autowired
    private QipaiMembersRemoteService qipaiMembersRemoteService;

    public List<MemberClubCard> showClubCard() {
        List<MemberClubCard> cardList = clubCardDao.findAllClubCard();
        for (MemberClubCard card : cardList) {
            long time = card.getTime();
            card.setTime(time / (24 * 60 * 60 * 1000));
        }
        return cardList;
    }

    public void giveClubCardToMember(String id, Integer vipTime, int singleNum) {
//		String param = "";
//		switch (vipTime) {
//			case 1:
//				param = "日卡";
//				break;
//			case 7:
//				param = "周卡";
//				break;
//			case 30:
//				param = "月卡";
//				break;
//			case 90:
//				param = "季卡";
//				break;
//			default:
//				return;
//		}
        long day = 24L * 60 * 60 * 1000;
        for (int i = 0; i < singleNum; i++) {
            CommonRemoteVO rvo = qipaiMembersRemoteService.give_viptime_id(id, vipTime * day);
        }
        return;
    }

    public void addClubCard(MemberClubCard clubCard) {
        clubCardDao.addClubCard(clubCard);
    }

    public void deleteClubCardByIds(String[] clubCardIds) {
        clubCardDao.deleteClubCardByIds(clubCardIds);
    }

    public void updateClubCard(MemberClubCard clubCard) {
        clubCardDao.updateClubCard(clubCard);
    }

    public MemberClubCard findClubCardById(String id) {
        return clubCardDao.getClubCardById(id);
    }

}
