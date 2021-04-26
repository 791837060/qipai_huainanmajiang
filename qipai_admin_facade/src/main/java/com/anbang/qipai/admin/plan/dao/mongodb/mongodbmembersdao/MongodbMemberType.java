package com.anbang.qipai.admin.plan.dao.mongodb.mongodbmembersdao;

import com.anbang.qipai.admin.plan.bean.members.MemberType;
import com.anbang.qipai.admin.plan.dao.membersdao.MemberTypeDao;
import com.anbang.qipai.admin.web.vo.reportvo.MemberRatioVo;
import com.mongodb.BasicDBObject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MongodbMemberType implements MemberTypeDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(MemberType memberType) {
        memberType.setValid(true);
        mongoTemplate.save(memberType);
    }

    @Override
    public void remove(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("isValid", false);
        mongoTemplate.updateFirst(query, update, MemberType.class);
    }

    @Override
    public List<MemberType> listByBeanAndTime(MemberType memberType, Long time) {
        Query query = new Query();
        if (StringUtils.isNotBlank(memberType.getId())) {
            query.addCriteria(Criteria.where("id").is(memberType.getId()));
        }
        if (memberType.getCardSource() != null) {
            query.addCriteria(Criteria.where("cardSource").is(memberType.getCardSource().name()));
        }
        if (time != null) {
            query.addCriteria(Criteria.where("vipEndTime").lte(time));
        }
        return mongoTemplate.find(query, MemberType.class);
    }

    @Override
    public List<MemberRatioVo> queryRatio(List<String> ids) {
        Criteria criteria = new Criteria();
        criteria = criteria.and("isValid").is(true);

        if(CollectionUtils.isNotEmpty(ids)) {
            criteria = criteria.and("id").in(ids);
        }

        Aggregation aggregation = Aggregation.newAggregation(MemberType.class, Aggregation.match(criteria),
                Aggregation.group("cardType","cardSource").count().as("num"));
        AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(aggregation, MemberType.class,
                BasicDBObject.class);
        List<BasicDBObject> dbObjects = result.getMappedResults();
        List<MemberRatioVo> vos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(dbObjects)) {
            for (BasicDBObject list : dbObjects) {
                MemberRatioVo vo = new MemberRatioVo();
                vo.setCardType(list.getString("cardType"));
                vo.setCardSource(list.getString("cardSource"));
                vo.setNum(list.getInt("num"));
                vos.add(vo);
            }
        }
        return vos;
    }

    @Override
    public int countPayPlayer() {
        Aggregation aggregation = Aggregation.newAggregation(MemberType.class,
                Aggregation.count().as("num"));
        AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(aggregation, MemberType.class,
                BasicDBObject.class);
        List<BasicDBObject> dbObjects = result.getMappedResults();
        if (CollectionUtils.isEmpty(dbObjects)){
            return 0;
        }
        int count = dbObjects.get(0).getInt("num");
        return count;
    }
}
