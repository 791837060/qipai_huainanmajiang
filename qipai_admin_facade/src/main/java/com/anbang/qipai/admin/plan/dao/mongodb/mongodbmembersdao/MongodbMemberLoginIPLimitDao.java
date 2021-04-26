package com.anbang.qipai.admin.plan.dao.mongodb.mongodbmembersdao;

import com.anbang.qipai.admin.plan.bean.members.MemberLoginIPLimit;
import com.anbang.qipai.admin.plan.dao.membersdao.MemberLoginIPLimitDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description:
 */
@Component
public class MongodbMemberLoginIPLimitDao implements MemberLoginIPLimitDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(MemberLoginIPLimit record) {
        mongoTemplate.insert(record);
    }

    @Override
    public void updateMemberLoginLimitRecordEfficientById(String[] ids, boolean efficient) {
        Object[] tempIds = ids;
        Query query = new Query(Criteria.where("id").in(tempIds));
        Update update = new Update();
        update.set("efficient", efficient);
        mongoTemplate.updateMulti(query, update, MemberLoginIPLimit.class);
    }

    @Override
    public List<MemberLoginIPLimit> findMemberLoginLimitRecordByLoginIp(int page, int size, String loginIp) {
        Query query = new Query();
        if (StringUtils.isNotBlank(loginIp)) {
            query.addCriteria(Criteria.where("loginIp").is(loginIp));
        }
        query.addCriteria(Criteria.where("efficient").is(true));
        query.limit(size);
        query.skip((page - 1) * size);
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));
        return mongoTemplate.find(query, MemberLoginIPLimit.class);
    }

    @Override
    public long getAmountByLoginIp(String loginIp) {
        Query query = new Query();
        if (StringUtils.isNotBlank(loginIp)) {
            query.addCriteria(Criteria.where("loginIp").is(loginIp));
        }
        query.addCriteria(Criteria.where("efficient").is(true));
        return mongoTemplate.count(query, MemberLoginIPLimit.class);
    }

}
