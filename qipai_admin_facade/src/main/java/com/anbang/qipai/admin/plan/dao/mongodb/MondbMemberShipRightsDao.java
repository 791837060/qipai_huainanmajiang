package com.anbang.qipai.admin.plan.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.membershiprights.MemberShipRights;
import com.anbang.qipai.admin.plan.dao.membershiprightsdao.MemberShipRightsDao;

@Component
public class MondbMemberShipRightsDao implements MemberShipRightsDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public MemberShipRights saveMemberShipRights(MemberShipRights commonuser) {
		mongoTemplate.save(commonuser);
		return commonuser;
	}

	@Override
	public MemberShipRights findallCommonUser() {
		return mongoTemplate.findById("1", MemberShipRights.class);
	}

	@Override
	public void updateGameMemberShipRights(MemberShipRights commonuser) {
		Query query = new Query(Criteria.where("id").is("1"));
		Update update = new Update();
		update.set("planMemberRoomsCount", commonuser.getPlanMemberRoomsCount());
		update.set("vipMemberRoomsCount", commonuser.getVipMemberRoomsCount());
		update.set("planMemberRoomsAliveHours", commonuser.getPlanMemberRoomsAliveHours());
		update.set("vipMemberRoomsAliveHours", commonuser.getVipMemberRoomsAliveHours());
		update.set("planMemberMaxCreateRoomDaily", commonuser.getPlanMemberMaxCreateRoomDaily());
		update.set("planMemberCreateRoomDailyGoldPrice", commonuser.getPlanMemberCreateRoomDailyGoldPrice());
		update.set("planMemberJoinRoomGoldPrice", commonuser.getPlanMemberJoinRoomGoldPrice());
		mongoTemplate.updateFirst(query, update, MemberShipRights.class);
	}

	@Override
	public void updateMembersMemberShipRights(MemberShipRights commonuser) {
		Query query = new Query(Criteria.where("id").is("1"));
		Update update = new Update();
		update.set("signGoldNumber", commonuser.getSignGoldNumber());
		update.set("goldForNewNember", commonuser.getGoldForNewNember());
		update.set("inviteIntegralNumber", commonuser.getInviteIntegralNumber());
		update.set("vipGrowIntegralSpeed", commonuser.getVipGrowIntegralSpeed());
		update.set("planGrowIntegralSpeed", commonuser.getPlanGrowIntegralSpeed());
		update.set("vipGrowGradeSpeed", commonuser.getVipGrowGradeSpeed());
		update.set("goldForAgentInvite", commonuser.getGoldForAgentInvite());
		mongoTemplate.updateFirst(query, update, MemberShipRights.class);
	}

}
