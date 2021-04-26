package com.anbang.qipai.dalianmeng.cqrs.q.dao.mongodb;

import com.anbang.qipai.dalianmeng.cqrs.q.dao.BanDeskmateRecordDao;
import com.anbang.qipai.dalianmeng.cqrs.q.dbo.BanDeskmateRecord;
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
 * @Date: Created in 2019/11/14 17:53
 */
@Component
public class MongodbBanDeskmateRecordDao implements BanDeskmateRecordDao {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public void save(BanDeskmateRecord banDeskmateRecord) {
        mongoTemplate.insert(banDeskmateRecord);

    }


    @Override
    public long countAll() {
        Query query = new Query();
        return mongoTemplate.count(query, BanDeskmateRecord.class);
    }

    @Override
    public List<BanDeskmateRecord> findAll(int page ,int size) {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "createTime"));
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query,BanDeskmateRecord.class);
    }

    @Override
    public long countByLianmengIdAndMemberId(String lianmengId, String memberId) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("operateId").is(memberId));
        }
        return mongoTemplate.count(query, BanDeskmateRecord.class);
    }

    @Override
    public List<BanDeskmateRecord> findByLianmengIdAndMemberId(int page, int size, String lianmengId, String memberId) {
        Query query = new Query();
        if (!StringUtils.isEmpty(lianmengId)) {
            query.addCriteria(Criteria.where("lianmengId").is(lianmengId));
        }
        if (!StringUtils.isEmpty(memberId)) {
            query.addCriteria(Criteria.where("operateId").is(memberId));
        }
        query.with(new Sort(Sort.Direction.DESC, "createTime"));
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query,BanDeskmateRecord.class);
    }

    @Override
    public void remove(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.remove(query,BanDeskmateRecord.class);

    }
}
