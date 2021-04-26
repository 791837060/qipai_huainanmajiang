package com.anbang.qipai.admin.plan.dao.shopdao;

import com.anbang.qipai.admin.plan.bean.shop.ScoreProductRecord;
import com.anbang.qipai.admin.web.query.ScoreRecordQuery;

import java.util.List;

public interface ScoreProductRecordDao {

	void save(ScoreProductRecord record);

	ScoreProductRecord findById(String id);

	void updateStatusById(String id, String status);

	void updateDeliverTimeById(String id, long deliverTime);

	long countByMemberIdAndStatus(String memberId, String status);

	List<ScoreProductRecord> findByMemberIdAndStatus(int page, int size, String memberId, String status);

	List<ScoreProductRecord> findByQuery(int page, int size, ScoreRecordQuery recordQuery);

	long countByQuery(ScoreRecordQuery recordQuery);
}
