package com.anbang.qipai.admin.plan.dao.mongodb.mongodbreportdao;

import com.anbang.qipai.admin.plan.bean.report.DetailedReport;
import com.anbang.qipai.admin.plan.bean.report.PlatformReport;
import com.anbang.qipai.admin.plan.dao.reportdao.DetailedReportDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author YaphetS
 * @date 2018/11/26
 */
@Repository
public class MongodbDetailedReportDao implements DetailedReportDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void upsert(DetailedReport detailedReport) {
        Update update=new Update();
        update.set("maxOnlineTime",detailedReport.getMaxOnlineTime());
        mongoTemplate.upsert(query(where("createTime").is(detailedReport.getCreateTime())),
                update,DetailedReport.class);
    }

    @Override
    public DetailedReport findByCreateTime(long dayStartTime) {
        return mongoTemplate.findOne(query(where("createTime").is(dayStartTime)),DetailedReport.class);
    }

    @Override
    public void updateByOnline(DetailedReport detailedReport) {
        Update update=new Update();
        update.set("onlineCount",detailedReport.getOnlineCount());
        update.set("maxOnlineTime",detailedReport.getMaxOnlineTime());
        mongoTemplate.updateFirst(query(where("createTime").is(detailedReport.getCreateTime())),update,DetailedReport.class);
    }

    @Override
    public List<DetailedReport> findByTime(Long startTime, Long endTime) {
        Query query = new Query(Criteria.where("createTime").gte(startTime).lte(endTime));
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));
        return mongoTemplate.find(query, DetailedReport.class);
    }

    @Override
    public void upsertOnlineData(DetailedReport detailedReport) {
        Update update=new Update();
        update.set("onlineCount",detailedReport.getOnlineCount());
        update.set("maxOnlineTime",detailedReport.getMaxOnlineTime());
        mongoTemplate.upsert(query(where("createTime").is(detailedReport.getCreateTime())),
                update,DetailedReport.class);
    }

    @Override
    public void upsertLoginUser(DetailedReport detailedReport) {
        Update update=new Update();
        update.set("loginUser",detailedReport.getLoginUser());
        update.set("totalUserCount",detailedReport.getTotalUserCount());
        mongoTemplate.upsert(query(where("createTime").is(detailedReport.getCreateTime())),
                update,DetailedReport.class);
    }

    @Override
    public void incPowerCount(DetailedReport detailedReport) {
        Query query=new Query(where("createTime").is(detailedReport.getCreateTime()));
        Update update=new Update().inc("powerCount",1);
        mongoTemplate.updateFirst(query,update,DetailedReport.class);
    }

    @Override
    public List<DetailedReport> findDetailedReportAfterTime(long startTime) {
        Query query = new Query(Criteria.where("createTime").gte(startTime));
//        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "createTime"));
//        query.with(sort);
        return mongoTemplate.find(query, DetailedReport.class);
    }

    @Override
    public void upsertActiveUserAndDayOnlineTime(DetailedReport detailedReport) {
        Update update=new Update();
        update.set("activeUser",detailedReport.getActiveUser());
        update.set("dayOnlineTime",detailedReport.getDayOnlineTime());
        mongoTemplate.upsert(query(where("createTime").is(detailedReport.getCreateTime())),
                update,DetailedReport.class);
    }



    @Override
    public void upsertAddUserCountAndTotalUserCount(DetailedReport report) {
        Update update=new Update();
        update.inc("addUserCount",1);
        update.set("totalUserCount",report.getTotalUserCount());
        mongoTemplate.upsert(query(where("createTime").is(report.getCreateTime())),
                update,DetailedReport.class);
    }
}
