package com.anbang.qipai.admin.plan.dao.mongodb.mongodbmembersdao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.members.MemberDbo;
import com.anbang.qipai.admin.plan.dao.membersdao.MemberDao;
import com.anbang.qipai.admin.web.query.MemberQuery;
import com.anbang.qipai.admin.web.vo.membersvo.MemberVO;

@Component
public class MongodbMemberDao implements MemberDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<MemberDbo> findMemberDboByConditions(int page, int size, MemberVO member) {
		Query query = new Query();
		if (member.getId() != null && !"".equals(member.getId())) {
			query.addCriteria(Criteria.where("id").is(member.getId()));
		}
		if (member.getNickname() != null && !"".equals(member.getNickname())) {
			query.addCriteria(Criteria.where("nickname").regex(member.getNickname()));
		}
		if (member.getOnlineState() != null && !"".equals(member.getOnlineState())) {
			query.addCriteria(Criteria.where("onlineState").is(member.getOnlineState()));
		}
		if ("true".equals(member.getIsVip()) || "false".equals(member.getIsVip())) {
			query.addCriteria(Criteria.where("vip").is(Boolean.valueOf(member.getIsVip())));
		}
		if (StringUtils.isNotBlank(member.getIpAddress())) {
			query.addCriteria(Criteria.where("ipAddress").regex(member.getIpAddress()));
		}
		query.addCriteria(Criteria.where("robot").is(member.isRobot())); // 默认查询非机器人的玩家
		query.with(member.getSort());
		query.skip((page - 1) * size);
		query.limit(size);
		return mongoTemplate.find(query, MemberDbo.class);
	}

	@Override
	public List<MemberDbo> findMemberDboByQuery(int page, int size, MemberQuery memberQuery) {
		Query query = new Query();
		if (StringUtils.isNotBlank(memberQuery.getId())) {
			query.addCriteria(Criteria.where("id").is(memberQuery.getId()));
		}
		if (StringUtils.isNotBlank(memberQuery.getNickname())) {
			query.addCriteria(Criteria.where("nickname").regex(memberQuery.getNickname()));
		}
		if (StringUtils.isNotBlank(memberQuery.getOnlineState())) {
			query.addCriteria(Criteria.where("onlineState").is(memberQuery.getOnlineState()));
		}
		if (CollectionUtils.isNotEmpty(memberQuery.getIds())) {
			query.addCriteria(Criteria.where("id").in(memberQuery.getIds()));
		}
		if (StringUtils.isNotBlank(memberQuery.getAgentId())) {
			query.addCriteria(Criteria.where("agentId").is(memberQuery.getAgentId()));
			query.addCriteria(Criteria.where("bindAgent").is(true));
		}
		query.addCriteria(Criteria.where("robot").is(false)); // 默认查询非机器人的玩家
		query.with(memberQuery.getSort());
		query.skip((page - 1) * size);
		query.limit(size);
		return mongoTemplate.find(query, MemberDbo.class);
	}

	@Override
	public long countByQuery(MemberQuery memberQuery) {
		Query query = new Query();
		if (StringUtils.isNotBlank(memberQuery.getId())) {
			query.addCriteria(Criteria.where("id").is(memberQuery.getId()));
		}
		if (StringUtils.isNotBlank(memberQuery.getNickname())) {
			query.addCriteria(Criteria.where("nickname").regex(memberQuery.getNickname()));
		}
		if (StringUtils.isNotBlank(memberQuery.getOnlineState())) {
			query.addCriteria(Criteria.where("onlineState").is(memberQuery.getOnlineState()));
		}
		if (CollectionUtils.isNotEmpty(memberQuery.getIds())) {
			query.addCriteria(Criteria.where("id").in(memberQuery.getIds()));
		}
		if (StringUtils.isNotBlank(memberQuery.getAgentId())) {
			query.addCriteria(Criteria.where("agentId").is(memberQuery.getAgentId()));
			query.addCriteria(Criteria.where("bindAgent").is(true));
		}
		query.addCriteria(Criteria.where("robot").is(false)); // 默认查询非机器人的玩家
		return mongoTemplate.count(query, MemberDbo.class);
	}

	@Override
	public long getAmountByConditions(MemberVO member) {
		Query query = new Query();
		if (member.getId() != null && !"".equals(member.getId())) {
			query.addCriteria(Criteria.where("id").is(member.getId()));
		}
		if (member.getNickname() != null && !"".equals(member.getNickname())) {
			query.addCriteria(Criteria.where("nickname").regex(member.getNickname()));
		}
		if (member.getOnlineState() != null && !"".equals(member.getOnlineState())) {
			query.addCriteria(Criteria.where("onlineState").is(member.getOnlineState()));
		}
		if ("true".equals(member.getIsVip()) || "false".equals(member.getIsVip())) {
			query.addCriteria(Criteria.where("vip").is(Boolean.valueOf(member.getIsVip())));
		}
		if (StringUtils.isNotBlank(member.getIpAddress())) {
			query.addCriteria(Criteria.where("ipAddress").regex(member.getIpAddress()));
		}

		query.addCriteria(Criteria.where("robot").is(member.isRobot())); // 默认查询非机器人的玩家
		return mongoTemplate.count(query, MemberDbo.class);
	}

	@Override
	public void addMember(MemberDbo member) {
		mongoTemplate.insert(member);
	}

	@Override
	public MemberDbo findMemberById(String memberId) {
		return mongoTemplate.findById(memberId, MemberDbo.class);
	}

	@Override
	public long countNewMemberByTime(long startTime, long endTime) {
		Query query = new Query(Criteria.where("createTime").gte(startTime).lt(endTime));
		query.addCriteria(Criteria.where("robot").is(false)); // 默认查询非机器人的玩家
		return mongoTemplate.count(query, MemberDbo.class);
	}

	@Override
	public long countVipMember() {
		Query query = new Query(Criteria.where("vip").is(true));
		query.addCriteria(Criteria.where("robot").is(false)); // 默认查询非机器人的玩家
		return mongoTemplate.count(query, MemberDbo.class);
	}

	@Override
	public void updateMemberPhone(String memberId, String phone) {
		Query query = new Query(Criteria.where("id").is(memberId));
		Update update = new Update();
		update.set("phone", phone);
		mongoTemplate.updateFirst(query, update, MemberDbo.class);
	}

	@Override
	public void updateMemberVip(String memberId, boolean vip) {
		Query query = new Query(Criteria.where("id").is(memberId));
		Update update = new Update();
		update.set("vip", vip);
		mongoTemplate.updateFirst(query, update, MemberDbo.class);
	}

	@Override
	public void memberOrderDelive(String memberId, boolean vip, long vipEndTime, int vipLevel, double vipScore,
			double cost) {
		Query query = new Query(Criteria.where("id").is(memberId));
		Update update = new Update();
		update.set("vipEndTime", vipEndTime);
		update.set("vip", vip);
		update.set("vipLevel", vipLevel);
		update.set("vipScore", vipScore);
		update.set("cost", cost);
		mongoTemplate.updateFirst(query, update, MemberDbo.class);
	}

	@Override
	public void updateMemberRealUser(String memberId, String realName, String IDcard, boolean verify) {
		Query query = new Query(Criteria.where("id").is(memberId));
		Update update = new Update();
		update.set("realName", realName);
		update.set("idCard", IDcard);
		update.set("verifyUser", verify);
		mongoTemplate.updateFirst(query, update, MemberDbo.class);
	}

	@Override
	public void rechargeVip(String memberId, boolean vip, long vipEndTime) {
		Query query = new Query(Criteria.where("id").is(memberId));
		Update update = new Update();
		update.set("vipEndTime", vipEndTime);
		update.set("vip", vip);
		mongoTemplate.updateFirst(query, update, MemberDbo.class);
	}

	@Override
	public void updateMemberOnlineState(String memberId, String onlineState) {
		Query query = new Query(Criteria.where("id").is(memberId));
		Update update = new Update();
		update.set("onlineState", onlineState);
		mongoTemplate.updateFirst(query, update, MemberDbo.class);
	}

	@Override
	public void updateOnlineStateAndIP(String memberId, String onlineState, String loginIp, String ipAddress) {
		Query query = new Query(Criteria.where("id").is(memberId));
		Update update = new Update();
		update.set("onlineState", onlineState);
		update.set("loginIp", loginIp);
		update.set("ipAddress", ipAddress);
		mongoTemplate.updateFirst(query, update, MemberDbo.class);
	}

	@Override
	public void updateIPAddress(String memberId, String ipAddress) {
		Query query = new Query(Criteria.where("id").is(memberId));
		Update update = new Update();
		update.set("ipAddress", ipAddress);
		mongoTemplate.updateFirst(query, update, MemberDbo.class);
	}

	@Override
	public List<String> findAllMemberId() {
		Query query = new Query();
		query.addCriteria(Criteria.where("robot").is(false)); // 默认查询非机器人的玩家
		List<MemberDbo> memberDboList = mongoTemplate.find(query, MemberDbo.class);
		List<String> ids = new ArrayList<String>();
		for (int i = 0; i < memberDboList.size(); i++) {
			ids.add(memberDboList.get(i).getId());
		}
		return ids;
	}

	@Override
	public List<String> findMemberId() {
		Query query = new Query(Criteria.where("vip").is(false));
		query.addCriteria(Criteria.where("robot").is(false)); // 默认查询非机器人的玩家
		List<MemberDbo> memberDboList = mongoTemplate.find(query, MemberDbo.class);
		List<String> ids = new ArrayList<String>();
		for (int i = 0; i < memberDboList.size(); i++) {
			ids.add(memberDboList.get(i).getId());
		}
		return ids;
	}

	@Override
	public List<String> findVipMemberId() {
		Query query = new Query(Criteria.where("vip").is(true));
		query.addCriteria(Criteria.where("robot").is(false)); // 默认查询非机器人的玩家
		List<MemberDbo> memberDboList = mongoTemplate.find(query, MemberDbo.class);
		List<String> ids = new ArrayList<String>();
		for (int i = 0; i < memberDboList.size(); i++) {
			ids.add(memberDboList.get(i).getId());
		}
		return ids;
	}

	@Override
	public List<MemberDbo> findMemberAfterTime(long startTime) {
		Query query = new Query(Criteria.where("createTime").gte(startTime));
		query.addCriteria(Criteria.where("robot").is(false)); // 默认查询非机器人的玩家
		return mongoTemplate.find(query, MemberDbo.class);
	}

	@Override
	public long countOnlineState() {
		Query query = new Query(Criteria.where("onlineState").is("online"));
		query.addCriteria(Criteria.where("robot").is(false)); // 默认查询非机器人的玩家
		return mongoTemplate.count(query, MemberDbo.class);
	}

	@Override
	public void updateMemberGold(String memberId, int gold) {
		Query query = new Query(Criteria.where("id").is(memberId));
		Update update = new Update();
		update.set("gold", gold);
		mongoTemplate.updateFirst(query, update, MemberDbo.class);
	}

	@Override
	public void updateMemberGoldAndCost(String memberId, int gold, int goldTotalCost) {
		Query query = new Query(Criteria.where("id").is(memberId));
		Update update = new Update();
		update.set("gold", gold);
		update.set("goldTotalCost", goldTotalCost);
		mongoTemplate.updateFirst(query, update, MemberDbo.class);
	}

	@Override
	public void updateMemberScore(String memberId, int score) {
		Query query = new Query(Criteria.where("id").is(memberId));
		Update update = new Update();
		update.set("score", score);
		mongoTemplate.updateFirst(query, update, MemberDbo.class);
	}

	@Override
	public void updateMemberCost(String memberId, double cost) {
		Query query = new Query(Criteria.where("id").is(memberId));
		Update update = new Update();
		update.set("cost", cost);
		mongoTemplate.updateFirst(query, update, MemberDbo.class);
	}

	@Override
	public long countAmount() {
		Query query = new Query();
		query.addCriteria(Criteria.where("robot").is(false)); // 默认查询非机器人的玩家
		return mongoTemplate.count(query, MemberDbo.class);
	}

	@Override
	public void updateMemberBindAgent(String memberId, String agentId, boolean bindAgent) {
		Query query = new Query(Criteria.where("id").is(memberId));
		Update update = new Update();
		update.set("bindAgent", bindAgent);
		update.set("agentId", agentId);
		mongoTemplate.updateFirst(query, update, MemberDbo.class);
	}

	@Override
	public List<MemberDbo> findMemberDboByIds(String[] memberIds) {
		Object[] ids = memberIds;
		Query query = new Query(Criteria.where("id").in(ids));
		query.addCriteria(Criteria.where("robot").is(false)); // 默认查询非机器人的玩家
		return mongoTemplate.find(query, MemberDbo.class);
	}

	@Override
	public void removeMemberBindAgent(String memberId) {
		Query query = new Query(Criteria.where("id").is(memberId));
		Update update = new Update();
		update.set("bindAgent", false);
		update.unset("agentId");
		mongoTemplate.updateFirst(query, update, MemberDbo.class);
	}

	@Override
	public void updateMemberBaseInfo(String memberId, String nickname, String headimgurl, String gender, String reqIP) {
		Query query = new Query(Criteria.where("id").is(memberId));
		Update update = new Update().set("nickname", nickname).set("headimgurl", headimgurl).set("gender", gender);
		if (!StringUtils.isBlank(reqIP)) {
			update.set("reqIP", reqIP);
		}
		mongoTemplate.updateFirst(query, update, MemberDbo.class);
	}

	@Override
	public long countRobotVipMember() {
		Query query = new Query(Criteria.where("vip").is(true));
		query.addCriteria(Criteria.where("robot").is(true));
		return mongoTemplate.count(query, MemberDbo.class);
	}

	@Override
	public long countRobotAmount() {
		Query query = new Query();
		query.addCriteria(Criteria.where("robot").is(true));
		return mongoTemplate.count(query, MemberDbo.class);
	}

	@Override
	public List<MemberDbo> findMemberByIP(int page, int size, String loginIp) {
		Query query = new Query();
		query.addCriteria(Criteria.where("loginIp").is(loginIp));
		query.addCriteria(Criteria.where("robot").is(false)); // 默认查询非机器人的玩家

		query.skip((page - 1) * size);
		query.limit(size);
		return mongoTemplate.find(query, MemberDbo.class);
	}

	@Override
	public List<MemberDbo> findMemberByReqIP(int page, int size, String reqIP) {
		Query query = new Query();
		query.addCriteria(Criteria.where("reqIP").is(reqIP));
		query.addCriteria(Criteria.where("robot").is(false)); // 默认查询非机器人的玩家

		query.skip((page - 1) * size);
		query.limit(size);
		return mongoTemplate.find(query, MemberDbo.class);
	}

	@Override
	public long countMemberByIP(String loginIp) {
		Query query = new Query();
		query.addCriteria(Criteria.where("loginIp").is(loginIp));
		query.addCriteria(Criteria.where("robot").is(false)); // 默认查询非机器人的玩家
		return mongoTemplate.count(query, MemberDbo.class);
	}

	@Override
	public long countMemberByReqIP(String reqIP) {
		Query query = new Query();
		query.addCriteria(Criteria.where("reqIP").is(reqIP));
		query.addCriteria(Criteria.where("robot").is(false)); // 默认查询非机器人的玩家
		return mongoTemplate.count(query, MemberDbo.class);
	}

	@Override
	public void updateMemberXiuxianchangGold(String memberId, int xiuxianchangGold) {
		Query query = new Query(Criteria.where("id").is(memberId));
		Update update = new Update();
		update.set("xiuxianchangGold", xiuxianchangGold);
		mongoTemplate.updateFirst(query, update, MemberDbo.class);
	}

    @Override
    public void updateMemberDalianmengApply(String memberId, boolean dalianmeng, boolean qinyouquan) {
        Query query = new Query(Criteria.where("id").is(memberId));
        Update update = new Update();
        update.set("dalianmeng", dalianmeng);
        update.set("qinyouquan", qinyouquan);
        mongoTemplate.updateFirst(query, update, MemberDbo.class);
    }
}
