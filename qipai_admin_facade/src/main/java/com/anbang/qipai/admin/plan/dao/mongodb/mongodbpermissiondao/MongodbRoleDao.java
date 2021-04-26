package com.anbang.qipai.admin.plan.dao.mongodb.mongodbpermissiondao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.permission.Privilege;
import com.anbang.qipai.admin.plan.bean.permission.Role;
import com.anbang.qipai.admin.plan.dao.permissiondao.RoleDao;

@Component
public class MongodbRoleDao implements RoleDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<Role> findAllRoles() {
		return mongoTemplate.findAll(Role.class);
	}

	@Override
	public void addRole(Role role) {
		mongoTemplate.insert(role);
	}

	@Override
	public void deleteRoleByIds(String[] ids) {
		Object[] idsTemp = ids;
		Query query = new Query(Criteria.where("id").in(idsTemp));
		mongoTemplate.remove(query, Role.class);
	}

	@Override
	public void updateRole(Role role) {
		Query query = new Query(Criteria.where("id").is(role.getId()));
		Update update = new Update();
		update.set("role", role.getRole());
		mongoTemplate.updateFirst(query, update, Role.class);
	}

	@Override
	public List<Role> findRoleByName(int page, int size, String role) {
		Query query = new Query();
		if (role != null && !"".equals(role)) {
			query.addCriteria(Criteria.where("role").regex(role));
		}
		query.skip((page - 1) * size);
		query.limit(size);
		return mongoTemplate.find(query, Role.class);
	}

	@Override
	public long getAmountByName(String role) {
		Query query = new Query();
		if (role != null && !"".equals(role)) {
			query.addCriteria(Criteria.where("role").regex(role));
		}
		return mongoTemplate.count(query, Role.class);
	}

	@Override
	public Role findRoleById(String roleId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(roleId));
		return mongoTemplate.findOne(query, Role.class);
	}

	@Override
	public void updatePrivilegeList(String roleId, List<Privilege> privilegeList) {
		Query query = new Query(Criteria.where("id").is(roleId));
		Update update = new Update();
		update.set("privilegeList", privilegeList);
		mongoTemplate.updateFirst(query, update, Role.class);
	}

	@Override
	public void deletePrivilegeByPrivilegeId(String[] privilegeIds) {
		Object[] ids = privilegeIds;
		Query query = new Query();
		for (Object id : ids) {
			Update update = new Update();
			// TODO用$in取代is
			update.pull("privilegeList", new Query(Criteria.where("id").is(id)));
			mongoTemplate.updateMulti(query, update, Role.class);
		}
	}

	@Override
	public List<Role> findRolesByIds(String[] roleIds) {
		Object[] ids = roleIds;
		Query query = new Query(Criteria.where("id").in(ids));
		return mongoTemplate.find(query, Role.class);
	}

}
