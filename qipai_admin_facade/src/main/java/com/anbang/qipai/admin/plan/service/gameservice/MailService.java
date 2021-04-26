package com.anbang.qipai.admin.plan.service.gameservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.mail.MailList;
import com.anbang.qipai.admin.plan.bean.mail.MailState;
import com.anbang.qipai.admin.plan.bean.mail.SystemMail;
import com.anbang.qipai.admin.plan.bean.members.MemberClubCard;
import com.anbang.qipai.admin.plan.dao.membersdao.MemberClubCardDao;
import com.anbang.qipai.admin.plan.dao.systemmaildao.MailDao;
import com.highto.framework.web.page.ListPage;

@Service
public class MailService {

	@Autowired
	private MailDao maildao;

	@Autowired
	private MemberClubCardDao clubCardDao;

	public Map<String, Object> findall(Integer page, Integer size, String adminname, Integer status) {
		if (page < 1) {
			page = 1;
		}
		Sort sort = new Sort(Sort.Direction.DESC, "createtime");

		Pageable pageable = new PageRequest(page - 1, size, sort);
		Query query = null;
		if (adminname == null || adminname.equals("")) {
			query = new Query(Criteria.where("status").is(status));
		} else {
			Criteria criteria = new Criteria();
			criteria.andOperator(Criteria.where("adminname").regex(adminname), Criteria.where("status").is(status));// 多条件查询
			query = new Query(criteria);
		}
		return maildao.findall(query, pageable, size);
	}

	public ListPage find_mail_record(Integer page, Integer size, String memberId, String mailType, String adminName,
			Long startTime, Long endTime) {
		if (page < 1) {
			page = 1;
		}
		List<MailList> lists = new ArrayList<MailList>();
		Query query = new Query();
		if (memberId != null && !memberId.equals("")) {
			query.addCriteria(Criteria.where("memberid").is(memberId));
		}
		if (startTime != null && endTime != null && startTime != 0 && endTime != 0) {
			query.addCriteria(Criteria.where("createTime").gte(startTime).lte(endTime));
		}
		List<String> liststr = new ArrayList<String>();
		if (mailType != null && !"".equals(mailType) || adminName != null && !"".equals(adminName)) {
			List<SystemMail> listSystemMail = maildao.findSystemMail(mailType, adminName);
			for (SystemMail systemMail : listSystemMail) {
				liststr.add(systemMail.getId());
			}
			query.addCriteria(Criteria.where("mailid").in(liststr));
		}
		query.addCriteria(Criteria.where("deletestate").is("1"));
		int count = maildao.count(query);// 总条数
		Pageable pageable = new PageRequest(page - 1, size);
		List<MailState> list = maildao.find_mail_record(query, pageable);
		for (MailState mailState : list) {
			SystemMail systemMail1 = maildao.findMailById(mailState.getMailid());
			if (systemMail1 != null) {
				MailList mailList = new MailList();
				mailList.setId(mailState.getId());
				mailList.setReceive(mailState.getReceive());
				mailList.setRewardTime(mailState.getRewardTime());
				mailList.setMemberId(mailState.getMemberid());
				mailList.setSystemMail(systemMail1);
				if (systemMail1.getVipCardId() != null) {
					MemberClubCard clubCard = clubCardDao.getClubCardById(systemMail1.getVipCardId());
					if (clubCard != null) {
						mailList.setVipCardName(clubCard.getName());
					}
				}
				lists.add(mailList);
			}
		}
		ListPage listPage = new ListPage(lists, page, size, count);
		return listPage;
	}

	public void addmail(SystemMail mail) {
		maildao.addmail(mail);
	}

	public void updateMailState(MailState mailState) {
		maildao.updateMailState(mailState);
	}

	/**
	 * 把一个用户的所有邮件设为已读
	 **/
	public void findallmembermail(String memberid) {
		List<MailState> list = maildao.findallmembermail(memberid);
		for (MailState mailState : list) {// 循环把这个会员的所有邮件设为已读
			mailState.setStatemail("0");
			maildao.updateMailState(mailState);
		}
	}

	public void addMailById(List<MailState> list) {
		for (MailState mailState : list) {
			maildao.addMailById(mailState);
		}
	}

	/**
	 * 管理员批量删除邮件
	 **/
	public void deleteAllMailState(String[] ids) {
		for (String id : ids) {
			MailState mailState = maildao.findMailStateById(id);
			mailState.setDeletestate("0");
			maildao.updateMailState(mailState);
		}
	}

	/**
	 * 删除所有已读
	 * 
	 * @param memberid
	 *            会员id
	 **/
	public void deleteallmail(String memberid) {
		List<MailState> list = maildao.findallmembermail(memberid);
		for (MailState mailState : list) {
			if (mailState.getStatemail().equals("0") && mailState.getReceive().equals("0")
					|| mailState.getReceive().equals("2")) {
				mailState.setDeletestate("0");
			}
			maildao.updateMailState(mailState);
		}
	}

}
