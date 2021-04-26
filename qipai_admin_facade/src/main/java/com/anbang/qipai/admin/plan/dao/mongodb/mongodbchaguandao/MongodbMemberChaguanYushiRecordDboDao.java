package com.anbang.qipai.admin.plan.dao.mongodb.mongodbchaguandao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.chaguan.MemberChaguanYushiRecordDbo;
import com.anbang.qipai.admin.plan.dao.chaguandao.MemberChaguanYushiRecordDboDao;
import com.anbang.qipai.admin.plan.dao.mongodb.mongodbchaguandao.repository.MemberChaguanYushiRecordDboRepository;

@Component
public class MongodbMemberChaguanYushiRecordDboDao implements MemberChaguanYushiRecordDboDao {

	@Autowired
	private MemberChaguanYushiRecordDboRepository repository;

	@Override
	public void save(MemberChaguanYushiRecordDbo dbo) {
		repository.save(dbo);
	}

}
