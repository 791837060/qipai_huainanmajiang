package com.anbang.qipai.qinyouquan.cqrs.q.dao.mongodb;

import com.anbang.qipai.qinyouquan.cqrs.q.dao.BanDeskMateDao;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.BanDeskMate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author cxy
 * @program: qipai
 * @Date: Created in 2019/11/14 9:40
 */
@Component
public class MongodbBanDeskMateDao implements BanDeskMateDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(BanDeskMate banDeskmate) {
        mongoTemplate.insert(banDeskmate);

    }

    @Override
    public BanDeskMate findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, BanDeskMate.class);
    }


    @Override
    public long countByLianmengIdAndMemberId(String lianmengId, String memberId,long queryTime) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("operateId").is(memberId));
        }
        if (queryTime!=0){
            query.addCriteria(Criteria.where("createTime").lt(queryTime));
        }
        return mongoTemplate.count(query, BanDeskMate.class);
    }

    @Override
    public List<BanDeskMate> findByLianmengIdAndMemberId(int page, int size, String lianmengId, String memberId,long queryTime) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("operateId").is(memberId));
        }
        if (queryTime!=0){
            query.addCriteria(Criteria.where("createTime").lt(queryTime));
        }
        query.with(new Sort(Sort.Direction.DESC, "createTime"));
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, BanDeskMate.class);
    }

    @Override
    public void remove(String id,String lianmengId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        mongoTemplate.remove(query, BanDeskMate.class);
    }

    @Override
    public BanDeskMate findByHehuorenId(String hehuorenId, String lianmengId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("hehuorenId").is(hehuorenId));
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        return mongoTemplate.findOne(query, BanDeskMate.class);
    }

    @Override
    public BanDeskMate findByMemberAIdAndMemberBId(String memberAId, String memberBId, String lianmengId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("memberAId").is(memberAId));
        query.addCriteria(Criteria.where("memberBId").is(memberBId));
        query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        return mongoTemplate.findOne(query, BanDeskMate.class);
    }


}
