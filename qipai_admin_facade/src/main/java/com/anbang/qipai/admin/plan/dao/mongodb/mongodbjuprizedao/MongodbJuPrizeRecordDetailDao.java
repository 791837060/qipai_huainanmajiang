package com.anbang.qipai.admin.plan.dao.mongodb.mongodbjuprizedao;

import com.anbang.qipai.admin.plan.bean.juprize.JuPrizeRecordDetail;
import com.anbang.qipai.admin.plan.dao.juprizedao.JuPrizeRecordDetailDao;
import com.anbang.qipai.admin.web.vo.juprize.JuPrizeRecordDetailQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongodbJuPrizeRecordDetailDao implements JuPrizeRecordDetailDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(JuPrizeRecordDetail juPrizeRecordDetail) {
        mongoTemplate.save(juPrizeRecordDetail);
    }

    @Override
    public long countJuPrizeRecordByQuery(JuPrizeRecordDetailQuery recordQuery) {
        Query query = new Query();
        if (StringUtils.isNotBlank(recordQuery.getPlayerId())) {
            query.addCriteria(Criteria.where("playerId").is(recordQuery.getPlayerId()));
        }
        if (StringUtils.isNotBlank(recordQuery.getNickName())) {
            query.addCriteria(Criteria.where("nickName").regex(recordQuery.getNickName()));
        }
        if (StringUtils.isNotBlank(recordQuery.getPhone())) {
            query.addCriteria(Criteria.where("phone").is(recordQuery.getPhone()));
        }
        if (StringUtils.isNotBlank(recordQuery.getLoginIp())) {
            query.addCriteria(Criteria.where("loginIp").is(recordQuery.getLoginIp()));
        }

        if (recordQuery.getStartTime() != null || recordQuery.getEndTime() != null) {
            Criteria criteria = Criteria.where("sendTime");
            if (recordQuery.getStartTime() != null) {
                criteria = criteria.gte(recordQuery.getStartTime());
            }
            if (recordQuery.getEndTime() != null) {
                criteria = criteria.lte(recordQuery.getEndTime());
            }
            query.addCriteria(criteria);
        }
        return mongoTemplate.count(query, JuPrizeRecordDetail.class);
    }

    @Override
    public List<JuPrizeRecordDetail> listJuPrizeRecordByQuery(int page, int size, JuPrizeRecordDetailQuery recordQuery) {
        Query query = new Query();
        if (StringUtils.isNotBlank(recordQuery.getPlayerId())) {
            query.addCriteria(Criteria.where("playerId").is(recordQuery.getPlayerId()));
        }
        if (StringUtils.isNotBlank(recordQuery.getNickName())) {
            query.addCriteria(Criteria.where("nickName").regex(recordQuery.getNickName()));
        }
        if (StringUtils.isNotBlank(recordQuery.getPhone())) {
            query.addCriteria(Criteria.where("phone").is(recordQuery.getPhone()));
        }
        if (StringUtils.isNotBlank(recordQuery.getLoginIp())) {
            query.addCriteria(Criteria.where("loginIp").is(recordQuery.getLoginIp()));
        }

        if (recordQuery.getStartTime() != null || recordQuery.getEndTime() != null) {
            Criteria criteria = Criteria.where("sendTime");
            if (recordQuery.getStartTime() != null) {
                criteria = criteria.gte(recordQuery.getStartTime());
            }
            if (recordQuery.getEndTime() != null) {
                criteria = criteria.lte(recordQuery.getEndTime());
            }
            query.addCriteria(criteria);
        }
        query.with(new Sort(Sort.Direction.DESC, "sendTime"));
        query.skip((page - 1) * size);
        query.limit(size);
        return mongoTemplate.find(query, JuPrizeRecordDetail.class);
    }
}
