package com.anbang.qipai.admin.plan.dao.mongodb.mongodbpermissiondao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.permission.AdminDiamondLimitRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.permission.Admin;
import com.anbang.qipai.admin.plan.bean.permission.Privilege;
import com.anbang.qipai.admin.plan.bean.permission.Role;
import com.anbang.qipai.admin.plan.dao.permissiondao.AdminDao;

@Component
public class MongodbAdminDao implements AdminDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Admin findAdminByNicknameAndPass(String nickname, String pass) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.and("nickname").is(nickname);
		criteria.and("pass").is(pass);
		query.addCriteria(criteria);
		return mongoTemplate.findOne(query, Admin.class);
	}

	@Override
	public List<Admin> findAdminByNickname(int page, int size, String nickname) {
		Query query = new Query();
		if (nickname != null && !"".equals(nickname)) {
			query.addCriteria(Criteria.where("nickname").regex(nickname));
		}
		query.skip((page - 1) * size);
		query.limit(size);
		return mongoTemplate.find(query, Admin.class);
	}

	@Override
	public void addAdmin(Admin admin) {
		mongoTemplate.insert(admin);
	}

	@Override
	public void deleteAdminByIds(String[] ids) {
		Object[] idsTemp = ids;
		Query query = new Query(Criteria.where("id").in(idsTemp));
		mongoTemplate.remove(query, Admin.class);
	}

	@Override
	public void updateAdminPass(String adminId, String pass) {
		Query query = new Query(Criteria.where("id").is(adminId));
		Update update = new Update();
		update.set("pass", pass);
		mongoTemplate.updateFirst(query, update, Admin.class);
	}

	@Override
	public long getAmountByNickname(String nickname) {
		Query query = new Query();
		if (nickname != null && !"".equals(nickname)) {
			query.addCriteria(Criteria.where("nickname").regex(nickname));
		}
		return mongoTemplate.count(query, Admin.class);
	}

	@Override
	public void deleteRoleByRoleId(String[] roleIds) {
		Object[] ids = roleIds;
		Query query = new Query();
		for (Object id : ids) {
			Update update = new Update();
			// TODO用$in取代is
			update.pull("roleList", new Query(Criteria.where("roleList.id").is(id)));
			mongoTemplate.updateMulti(query, update, Admin.class);
		}
	}

	@Override
	public void updateRoleList(String adminId, List<Role> roleList) {
		Query query = new Query(Criteria.where("id").is(adminId));
		Update update = new Update();
		update.set("roleList", roleList);
		mongoTemplate.updateFirst(query, update, Admin.class);
	}

	@Override
	public Admin findAdminById(String adminId) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.and("id").is(adminId);
		query.addCriteria(criteria);
		return mongoTemplate.findOne(query, Admin.class);
	}

	@Override
	public Admin findAdminById1(String adminIdBySession) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.and("id").is(adminIdBySession);
		query.addCriteria(criteria);
		return mongoTemplate.findOne(query, Admin.class);
	}

	@Override
	public void updatePrivilegeByRole(String roleId, List<Privilege> privilegeList) {
		Query query = new Query(Criteria.where("roleList.id").is(roleId));
		Update update = new Update();
		update.set("roleList.$.privilegeList", privilegeList);
		mongoTemplate.updateFirst(query, update, Admin.class);
	}

	@Override
	public void deletePrivilegeByPrivilegeId(String[] privilegeIds) {
		Object[] ids = privilegeIds;
		Query query = new Query();
		for (Object id : ids) {
			Update update = new Update();
			// TODO用$in取代is
			update.pull("privilegeList", new Query(Criteria.where("id").is(id)));
			mongoTemplate.updateMulti(query, update, Admin.class);
		}
	}

	/**
	 * 更新砖石额度
	 */
	@Override
	public void updateAdminDiamondLimint(String adminId, int diamondLimit) {
		Query query = new Query(Criteria.where("id").is(adminId));
		Update update = new Update();
		update.set("diamondLimit", diamondLimit);
		mongoTemplate.updateFirst(query, update, Admin.class);
	}
	/**
	 * 更新玉石额度
	 */
	@Override
	public void updateAdminJadeLimint(String adminId, int jadeLimit) {
		Query query = new Query(Criteria.where("id").is(adminId));
		Update update = new Update();
		update.set("jadeLimit", jadeLimit);
		mongoTemplate.updateFirst(query, update, Admin.class);
	}

	//修改完限额以后在数据库插入对应的修改流水
	@Override
	public void insertAdminDiamondLimint(AdminDiamondLimitRecord adminDiamondLimitRecord) {
		mongoTemplate.insert(adminDiamondLimitRecord);
	}
}
