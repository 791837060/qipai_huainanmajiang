package com.anbang.qipai.admin.plan.dao.mongodb.mongodbgamedao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.games.SystemNotice;
import com.anbang.qipai.admin.plan.dao.gamedao.SystemNoticeDao;

@Component
public class MongdbSystemNoticeDao implements SystemNoticeDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void save(SystemNotice notice) {
		mongoTemplate.insert(notice);
	}

	@Override
	public void updateSystemNoticeState(String noticeId, String adminName, String state) {
		Query query = new Query(Criteria.where("id").is(noticeId));
		Update update = new Update();
		update.set("adminName", adminName);
		update.set("state", state);
		mongoTemplate.updateFirst(query, update, SystemNotice.class);
	}

	@Override
	public List<SystemNotice> findByAdminName(int page, int size, String adminName) {
		Query query = new Query();
		if (adminName != null && !"".equals(adminName)) {
			query.addCriteria(Criteria.where("adminName").regex(adminName));
		}
		query.addCriteria(Criteria.where("valid").is(true));
		query.limit(size);
		query.skip((page - 1) * size);
		query.with(new Sort(new Order(Direction.DESC, "createTime")));
		return mongoTemplate.find(query, SystemNotice.class);
	}

	@Override
	public long countByAdminName(String adminName) {
		Query query = new Query();
		if (adminName != null && !"".equals(adminName)) {
			query.addCriteria(Criteria.where("adminName").regex(adminName));
		}
		query.addCriteria(Criteria.where("valid").is(true));
		return mongoTemplate.count(query, SystemNotice.class);
	}

	@Override
	public void updateSystemNoticeValid(String noticeId, String adminName, boolean valid) {
		Query query = new Query(Criteria.where("id").is(noticeId));
		Update update = new Update();
		update.set("adminName", adminName);
		update.set("valid", valid);
		mongoTemplate.updateFirst(query, update, SystemNotice.class);
	}

	@Override
	public void addSystemNotices(List<SystemNotice> notices) {
		mongoTemplate.insertAll(notices);
	}

	@Override
	public SystemNotice findById(String noticeId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(noticeId));
		return mongoTemplate.findOne(query, SystemNotice.class);
	}

}
