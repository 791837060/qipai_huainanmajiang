package com.anbang.qipai.admin.plan.service.chaguanservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.chaguan.MemberChaguanYushiRecordDbo;
import com.anbang.qipai.admin.plan.dao.chaguandao.MemberChaguanYushiRecordDboDao;

@Service
public class MemberChaguanYushiService {

	@Autowired
	private MemberChaguanYushiRecordDboDao memberChaguanYushiRecordDboDao;

	public void recordMemberChaguanYushiRecordDbo(MemberChaguanYushiRecordDbo dbo) {
		memberChaguanYushiRecordDboDao.save(dbo);
	}

}
