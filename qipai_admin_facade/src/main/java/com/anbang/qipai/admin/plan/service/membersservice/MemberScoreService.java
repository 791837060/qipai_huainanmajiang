package com.anbang.qipai.admin.plan.service.membersservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.members.MemberScoreRecordDbo;
import com.anbang.qipai.admin.plan.dao.membersdao.MemberScoreRecordDao;
import com.anbang.qipai.admin.web.vo.membersvo.RecordSummaryTexts;
import com.dml.accounting.TextAccountingSummary;
import com.highto.framework.web.page.ListPage;

@Service
public class MemberScoreService {
	@Autowired
	private MemberScoreRecordDao memberScoreRecordDao;

	public ListPage findScoreRecordByMemberId(int page, int size, String memberId) {
		long amount = memberScoreRecordDao.getAmountByMemberId(memberId);
		List<MemberScoreRecordDbo> recordList = memberScoreRecordDao.findScoreRecordByMemberId(page, size, memberId);
		for (int i = 0; i < recordList.size(); i++) {
			TextAccountingSummary summary = (TextAccountingSummary) recordList.get(i).getSummary();
			TextAccountingSummary newSummary = new TextAccountingSummary(
					RecordSummaryTexts.getSummaryText(summary.getText()));
			recordList.get(i).setSummary(newSummary);
		}
		ListPage listPage = new ListPage(recordList, page, size, (int) amount);
		return listPage;
	}

	public void addScoreRecord(MemberScoreRecordDbo dbo) {
		memberScoreRecordDao.addScoreRecord(dbo);
	}

	public MemberScoreRecordDbo findRecentlyScoreRecordByMemberId(String memberId) {
		return memberScoreRecordDao.findRecentlyScoreRecordByMemberId(memberId);
	}

}
