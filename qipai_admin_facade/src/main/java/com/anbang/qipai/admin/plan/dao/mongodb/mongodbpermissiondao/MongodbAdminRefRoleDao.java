package com.anbang.qipai.admin.plan.dao.mongodb.mongodbpermissiondao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.permission.AdminRefRole;
import com.anbang.qipai.admin.plan.dao.permissiondao.AdminRefRoleDao;

@Component
public class MongodbAdminRefRoleDao implements AdminRefRoleDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void save(AdminRefRole ref) {
		mongoTemplate.insert(ref);
	}

	@Override
	public List<AdminRefRole> findByAdminId(String adminId) {
		Query query = new Query(Criteria.where("adminId").is(adminId));
		return mongoTemplate.find(query, AdminRefRole.class);
	}

	@Override
	public void removeByRoleIds(String[] roleIds) {
		Object[] ids = roleIds;
		Query query = new Query(Criteria.where("roleId").in(ids));
		mongoTemplate.remove(query, AdminRefRole.class);
	}

	@Override
	public void removeByAdminIds(String[] adminIds) {
		Object[] ids = adminIds;
		Query query = new Query(Criteria.where("adminId").in(ids));
		mongoTemplate.remove(query, AdminRefRole.class);
	}

}
