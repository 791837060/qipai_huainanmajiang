package com.anbang.qipai.admin.plan.dao.mongodb.mongodbreportdao;

import com.anbang.qipai.admin.plan.bean.report.OnlineTimeReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * @author YaphetS
 * @date 2018/12/6
 */
@Repository
public class MongodbOnlineTimeReportDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void upsertByCreateTimeAndMemberId(OnlineTimeReport report) {
        mongoTemplate.upsert(query(where("createTime").is(report.getCreateTime()).and("memberId").is(report.getMemberId())),
                update("onlineTime", report.getOnlineTime()), OnlineTimeReport.class);
    }

    public List<OnlineTimeReport> findByTime(Long createTime) {
        return mongoTemplate.find(query(where("createTime").is(createTime)),OnlineTimeReport.class);
    }

    public OnlineTimeReport find(long dayStartTime, String memberId) {
        return mongoTemplate.findOne(query(where("createTime").is(dayStartTime).and("memberId").is(memberId)),OnlineTimeReport.class);

    }
}
