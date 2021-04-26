package com.anbang.qipai.admin.plan.dao.mongodb.mongodbreportdao;

import com.anbang.qipai.admin.plan.bean.report.BasicDataReport;
import com.anbang.qipai.admin.plan.dao.reportdao.BasicDataReportDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * @author YaphetS
 * @date 2018/11/24
 */
@Repository
public class MongodbBasicDataReportDao implements BasicDataReportDao {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public BasicDataReport findByCreateTime(long createTime) {
        Query query = new Query(where("createTime").is(createTime));
        return mongoTemplate.findOne(query,BasicDataReport.class);
    }

    /**
     * insert添加会导致并发问题,采用upsert
     * @param basicDataReport
     */
    @Override
    public void insert(BasicDataReport basicDataReport) {
        mongoTemplate.insert(basicDataReport);
    }

    @Override
    public void upsert(BasicDataReport basicDataReport) {
        Update update=new Update();
        update.set("currentQuantity",basicDataReport.getCurrentQuantity());
        update.set("maxQuantity",basicDataReport.getMaxQuantity());
        mongoTemplate.upsert(query(where("createTime").is(basicDataReport.getCreateTime())),
                update, BasicDataReport.class);
    }

    public void save(BasicDataReport basicDataReport){
        mongoTemplate.save(basicDataReport);
    }


    @Override
    public void incCurrentQuantity(BasicDataReport basicDataReport,int modify) {

        Query query=new Query(where("id").is(basicDataReport.getId()));
        Update update=new Update().inc("currentQuantity",modify);
        mongoTemplate.updateFirst(query,update,BasicDataReport.class);
    }

    @Override
    public void updateMaxQuantity(BasicDataReport basicDataReport) {
        Query query=new Query(where("id").is(basicDataReport.getId()));
        Update update=new Update().set("maxQuantity",basicDataReport.getMaxQuantity());
        mongoTemplate.updateFirst(query,update,BasicDataReport.class);
    }

    @Override
    public List<BasicDataReport> findBasicDataAfterTime(long startTime) {
        Query query = new Query(where("createTime").gte(startTime));
        return mongoTemplate.find(query, BasicDataReport.class);
    }
}
