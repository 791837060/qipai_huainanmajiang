package com.anbang.qipai.dalianmeng.plan.dao.mongodb;


import com.anbang.qipai.dalianmeng.plan.bean.MemberLatAndLon;
import com.anbang.qipai.dalianmeng.plan.dao.MemberLatAndLonDao;
import com.anbang.qipai.dalianmeng.plan.dao.mongodb.repository.MemberLatAndLonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class MongodbMemberLatAndLonDao implements MemberLatAndLonDao {
    @Autowired
    private MemberLatAndLonRepository memberLatAndLonRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(MemberLatAndLon memberLatAndLon) {
        memberLatAndLonRepository.save(memberLatAndLon);
    }

    @Override
    public List<MemberLatAndLon> find(List<String> memberIds,String roomNo) {
        List<MemberLatAndLon> memberLatAndLons=new ArrayList<>();
        for (String memberId : memberIds) {
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(memberId));
            query.addCriteria(Criteria.where("roomNo").is(roomNo));
            memberLatAndLons.add(mongoTemplate.findOne(query, MemberLatAndLon.class));
        }
        return memberLatAndLons;
    }

	@Override
	public void deleteMemberLatAndLon(String playerId) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("id").is(playerId));
		query.addCriteria(criteria);
		mongoTemplate.remove(query, MemberLatAndLon.class);
	}


    @Override
    public MemberLatAndLon findBymemberId(String memberId, String roomNo) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(memberId));
        query.addCriteria(Criteria.where("roomNo").is(roomNo));
        return mongoTemplate.findOne(query, MemberLatAndLon.class);
    }

}
