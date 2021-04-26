package com.anbang.qipai.admin.plan.dao.mongodb.mongodbpermissiondao;

import com.anbang.qipai.admin.plan.bean.permission.AdminDiamondLimitRecord;
import com.anbang.qipai.admin.plan.dao.permissiondao.AdminDiamondLimitRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;


@Component
public class MongodbAdminDiamondLimitRecordDao implements AdminDiamondLimitRecordDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void updateAdminDiamondLimint( AdminDiamondLimitRecord adminDiamondLimitRecord) {
        mongoTemplate.insert(adminDiamondLimitRecord);
    }

    @Override
    public List<AdminDiamondLimitRecord> findByUser(String adminId, long startTime, long endTime , int page, int size) {
        Query query=new Query();
        if (!StringUtils.isEmpty(adminId)){
            query.addCriteria(Criteria.where("adminId").is(adminId));
        }
        if (!StringUtils.isEmpty(startTime)&&!StringUtils.isEmpty(endTime)){
            query.addCriteria(Criteria.where("accountingTime").gt(startTime).lt(endTime));
        }
        //按照创建时间排序(由近到远)
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "accountingTime")));
        query.skip((page - 1) * size);
        query.limit(size);
       return mongoTemplate.find(query, AdminDiamondLimitRecord.class);
    }

    @Override
    public long count(String adminId, long startTime, long endTime) {
        Query query=new Query();
        Criteria criteria=new Criteria();
        query.addCriteria(criteria.where("adminId").is(adminId));
        if (!StringUtils.isEmpty(startTime)&&!StringUtils.isEmpty(endTime)){
            query.addCriteria(Criteria.where("accountingTime").gt(startTime).lt(endTime));
        }
        return mongoTemplate.count(query, AdminDiamondLimitRecord.class);
    }
}
