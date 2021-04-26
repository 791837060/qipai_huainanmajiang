package com.anbang.qipai.admin.plan.service.membersservice;

import java.util.List;


import com.anbang.qipai.admin.plan.bean.members.MemberYushi;
import com.anbang.qipai.admin.plan.dao.membersdao.MemberYushiDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.members.MemberGoldRecordDbo;
import com.anbang.qipai.admin.plan.dao.membersdao.MemberGoldRecordDao;
import com.anbang.qipai.admin.web.vo.membersvo.RecordSummaryTexts;
import com.dml.accounting.TextAccountingSummary;
import com.highto.framework.web.page.ListPage;

@Service
public class MemberGoldService {

	@Autowired
	private MemberGoldRecordDao memberGoldRecordDao;

	@Autowired
    private MemberYushiDao memberYushiDao;

	public ListPage findGoldRecordByMemberId(int page, int size, String memberId) {
		long amount = memberGoldRecordDao.getAmountByMemberId(memberId);
		List<MemberGoldRecordDbo> recordList = memberGoldRecordDao.findGoldRecordByMemberId(page, size, memberId);
		for (int i = 0; i < recordList.size(); i++) {
			TextAccountingSummary summary = (TextAccountingSummary) recordList.get(i).getSummary();
			TextAccountingSummary newSummary = new TextAccountingSummary(
					RecordSummaryTexts.getSummaryText(summary.getText()));
			recordList.get(i).setSummary(newSummary);
		}
		ListPage listPage = new ListPage(recordList, page, size, (int) amount);
		return listPage;
	}

	public void addGoldRecord(MemberGoldRecordDbo dbo) {
		memberGoldRecordDao.addGoldRecord(dbo);
	}

	public MemberGoldRecordDbo findRecentlyGoldRecordByMemberId(String memberId) {
		return memberGoldRecordDao.findRecentlyGoldRecordByMemberId(memberId);
	}

	public List<MemberYushi> findAllYushi(){
	    return memberYushiDao.findAllYushi();
    }

    public void addYushi(MemberYushi yushi) {
        memberYushiDao.addYushi(yushi);
    }

    public void deleteYushiByIds(String[] yushiId) {
        memberYushiDao.deleteYushiByIds(yushiId);
    }

    public void updateYushi(MemberYushi yushi) {
        memberYushiDao.updateYushi(yushi);
    }

    public MemberYushi getYushiById(String id) {
        return memberYushiDao.getYushiById(id);
    }

}
