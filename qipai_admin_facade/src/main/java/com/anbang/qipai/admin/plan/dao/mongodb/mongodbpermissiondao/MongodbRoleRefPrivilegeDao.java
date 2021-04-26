package com.anbang.qipai.admin.plan.dao.mongodb.mongodbpermissiondao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.permission.RoleRefPrivilege;
import com.anbang.qipai.admin.plan.dao.permissiondao.RoleRefPrivilegeDao;

@Component
public class MongodbRoleRefPrivilegeDao implements RoleRefPrivilegeDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void save(RoleRefPrivilege ref) {
		mongoTemplate.insert(ref);
	}

	@Override
	public List<RoleRefPrivilege> findByRoleId(String roleId) {
		Query query = new Query(Criteria.where("roleId").is(roleId));
		return mongoTemplate.find(query, RoleRefPrivilege.class);
	}

	@Override
	public void removeByPrivilegeIds(String[] privilegeIds) {
		Object[] ids = privilegeIds;
		Query query = new Query(Criteria.where("privilegeId").in(ids));
		mongoTemplate.remove(query, RoleRefPrivilege.class);
	}

	@Override
	public void removeByRoleIds(String[] roleIds) {
		Object[] ids = roleIds;
		Query query = new Query(Criteria.where("roleId").in(ids));
		mongoTemplate.remove(query, RoleRefPrivilege.class);
	}

}
