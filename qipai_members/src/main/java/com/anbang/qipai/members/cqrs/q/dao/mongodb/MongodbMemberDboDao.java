package com.anbang.qipai.members.cqrs.q.dao.mongodb;

import com.anbang.qipai.members.cqrs.q.dao.MemberDboDao;
import com.anbang.qipai.members.cqrs.q.dao.mongodb.repository.MemberDboRepository;
import com.anbang.qipai.members.cqrs.q.dbo.AuthorizationDbo;
import com.anbang.qipai.members.cqrs.q.dbo.MemberDbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class MongodbMemberDboDao implements MemberDboDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private MemberDboRepository repository;

	@Override
	public void save(MemberDbo memberDbo) {
		repository.save(memberDbo);
	}

    @Override
    public void update(String memberId, String nickname, String headimgurl, String gender, String phone) {
        Query query = new Query(Criteria.where("id").is(memberId));
        Update update = new Update();
        if (nickname != null) {
            update.set("nickname", nickname);
        }
        if (nickname != null) {
            update.set("headimgurl", headimgurl);
        }
        if (gender != null) {
            update.set("gender", gender);
        }
        if (phone != null) {
            update.set("phone", phone);
        }

        mongoTemplate.updateFirst(query, update, MemberDbo.class);
    }

	@Override
    public MemberDbo findMemberById(String memberId) {
        Query query = new Query(Criteria.where("id").is(memberId));
        return mongoTemplate.findOne(query, MemberDbo.class);
    }

    @Override
    public void updateMemberPhone(String memberId, String phone) {
        Query query = new Query(Criteria.where("id").is(memberId));
        Update update = new Update();
        update.set("phone", phone);
        mongoTemplate.updateFirst(query, update, MemberDbo.class);
    }

    @Override
    public void updateMemberWeChat(String memberId, boolean verifyWeChat) {
        Query query = new Query(Criteria.where("id").is(memberId));
        Update update = new Update();
        update.set("verifyWeChat", verifyWeChat);
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
	public void updateMemberGold(String memberId, int gold) {
		Query query = new Query(Criteria.where("id").is(memberId));
		Update update = new Update();
		update.set("gold", gold);
		mongoTemplate.updateFirst(query, update, MemberDbo.class);
	}

	@Override
	public AuthorizationDbo findThirdAuthorizationDboByMemberId(String id) {
		Query query = new Query(Criteria.where("memberId").is(id));
		return mongoTemplate.findOne(query, AuthorizationDbo.class);
	}

	@Override
	public void updateMemberByMemberId(MemberDbo memberDbo, String memberId) {
		Query query =new Query(Criteria.where("id").is(memberId));
		Update update= new Update();
		update.set("headimgurl",memberDbo.getHeadimgurl());
		update.set("nickname",memberDbo.getNickname());
		update.set("gender",memberDbo.getGender());
		update.set("phone",memberDbo.getPhone());

		mongoTemplate.updateFirst(query,update, MemberDbo.class);

	}

    @Override
    public void updateMemberDalianmengApply(String memberId, boolean dalianmeng, boolean qinyouquan) {
        Query query = new Query(Criteria.where("id").is(memberId));
        Update update = new Update();
        update.set("dalianmeng", dalianmeng);
        update.set("qinyouquan", qinyouquan);
        mongoTemplate.updateFirst(query, update, MemberDbo.class);
    }


    @Override
	public void updateMemberReqIP(String memberId, String reqIP) {
		Query query = new Query(Criteria.where("id").is(memberId));
		Update update = new Update();
		update.set("reqIP", reqIP);
		mongoTemplate.updateFirst(query, update, MemberDbo.class);
	}

	@Override
	public MemberDbo findMemberByPhone(String phone) {
		Query query = new Query(Criteria.where("phone").is(phone));
		return mongoTemplate.findOne(query, MemberDbo.class);
	}

}
