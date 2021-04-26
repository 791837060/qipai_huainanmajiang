package com.anbang.qipai.admin.plan.dao.mongodb.mongodbpermissiondao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.permission.Privilege;
import com.anbang.qipai.admin.plan.dao.permissiondao.PrivilegeDao;

@Component
public class MongodbPrivilegeDao implements PrivilegeDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<Privilege> findAllPrivileges() {
		return mongoTemplate.findAll(Privilege.class);
	}

	@Override
	public void addPrivileges(List<Privilege> privilegeList) {
		mongoTemplate.insert(privilegeList, Privilege.class);
	}

	@Override
	public void deletePrivilegeByIds(String[] ids) {
		Object[] idsTemp = ids;
		Query query = new Query(Criteria.where("id").in(idsTemp));
		mongoTemplate.remove(query, Privilege.class);
	}

	@Override
	public void updatePrivilege(Privilege privilege) {
		Query query = new Query(Criteria.where("id").is(privilege.getId()));
		Update update = new Update();
		update.set("privilege", privilege.getPrivilege());
		update.set("uri", privilege.getUri());
		mongoTemplate.updateFirst(query, update, Privilege.class);
	}

	@Override
	public List<Privilege> findPrivilegeByName(int page, int size, String privilege) {
		Query query = new Query();
		if (privilege != null && !"".equals(privilege)) {
			query.addCriteria(Criteria.where("privilege").regex(privilege));
		}
		query.skip((page - 1) * size);
		query.limit(size);
		return mongoTemplate.find(query, Privilege.class);
	}

	@Override
	public long getAmountByName(String privilege) {
		Query query = new Query();
		if (privilege != null && !"".equals(privilege)) {
			query.addCriteria(Criteria.where("privilege").regex(privilege));
		}
		return mongoTemplate.count(query, Privilege.class);
	}

	@Override
	public List<Privilege> findPrivilegeByUri(List<String> uriList) {
		Query query = new Query(Criteria.where("uri").in(uriList));
		return mongoTemplate.find(query, Privilege.class);
	}

	@Override
	public List<Privilege> findPrivilegesByIds(String[] privilegeIds) {
		Object[] ids=privilegeIds;
		Query query = new Query(Criteria.where("id").in(ids));
		return mongoTemplate.find(query, Privilege.class);
	}

}