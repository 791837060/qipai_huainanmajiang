package com.anbang.qipai.admin.plan.dao.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.report.PlatformReport;
import com.anbang.qipai.admin.plan.dao.PlatformReportDao;

@Component
public class MongodbPlatformReportDao implements PlatformReportDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<PlatformReport> findReportByTime(int page, int size, long startTime, long endTime) {
		// 需要按照date建立索引
		Query query = new Query(Criteria.where("date").gte(startTime).lte(endTime));
		query.with(new Sort(new Order(Direction.DESC, "date")));
		query.skip((page - 1) * size);
		query.limit(size);
		return mongoTemplate.find(query, PlatformReport.class);
	}

	@Override
	public void addReport(PlatformReport report) {
		mongoTemplate.insert(report);
	}

    /**
     * 在时间段中查询(无分页)
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<PlatformReport> findAllReportByTime(Long startTime, Long endTime) {
        Query query = new Query(Criteria.where("date").gte(startTime).lte(endTime));
        query.with(new Sort(new Order(Direction.DESC, "date")));
        return mongoTemplate.find(query, PlatformReport.class);
    }

    @Override
	public long getAmountByTime(long startTime, long endTime) {
		Query query = new Query(Criteria.where("date").gte(startTime).lte(endTime));
		return mongoTemplate.count(query, PlatformReport.class);
	}

}
