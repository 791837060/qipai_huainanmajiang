package com.anbang.qipai.admin.plan.service.agentsservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.agents.AgentInvitationRecord;
import com.anbang.qipai.admin.plan.bean.members.MemberDbo;
import com.anbang.qipai.admin.plan.dao.agentsdao.AgentInvitationRecordDao;
import com.anbang.qipai.admin.plan.dao.membersdao.MemberDao;
import com.anbang.qipai.admin.web.vo.agentsvo.AgentInvitationRecordVO;
import com.anbang.qipai.admin.web.vo.agentsvo.AgentInvitationVO;
import com.highto.framework.web.page.ListPage;

@Service
public class AgentInvitationRecordService {

	@Autowired
	private AgentInvitationRecordDao invitationRecordDao;

	@Autowired
	private MemberDao memberDao;

	public void addAgentInvitationRecord(AgentInvitationRecord record) {
		invitationRecordDao.addInvitationRecord(record);
	}

	public void saveAgentInvitationRecord(AgentInvitationRecord record) {
		invitationRecordDao.saveInvitationRecord(record);
	}

	public void banInvitationRecordById(String id) {
		invitationRecordDao.banInvitationRecordById(id);
	}

	public ListPage findInvitationRecordByConditions(int page, int size, AgentInvitationRecordVO record) {
		long amount = invitationRecordDao.getAmountByByConditions(record);
		List<AgentInvitationRecord> recordList = invitationRecordDao.findInvitationRecordByConditions(page, size,
				record);
		List<AgentInvitationVO> list = new ArrayList<>();
		for (AgentInvitationRecord r : recordList) {
			AgentInvitationVO vo = new AgentInvitationVO();
			MemberDbo member = memberDao.findMemberById(r.getMemberId());
			vo.setMemberId(member.getId());
			vo.setNickname(member.getNickname());
			vo.setGold(member.getGold());
			vo.setCreateTime(r.getCreateTime());
			vo.setLoginIp(r.getLoginIp());
			vo.setIpAddress(r.getIpAddress());
			list.add(vo);
		}
		ListPage listPage = new ListPage(list, page, size, (int) amount);
		return listPage;
	}

	public ListPage findInviteCodeRecordByConditions(int page, int size, AgentInvitationRecordVO record) {
		long amount = invitationRecordDao.getAmountByByConditions(record);
		List<AgentInvitationRecord> recordList = invitationRecordDao.findInvitationRecordByConditions(page, size,
				record);
		ListPage listPage = new ListPage(recordList, page, size, (int) amount);
		return listPage;
	}
}
