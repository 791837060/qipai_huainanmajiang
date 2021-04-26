package com.anbang.qipai.admin.plan.dao.shopdao;

import com.anbang.qipai.admin.plan.bean.shop.HongbaodianProductRecord;
import com.anbang.qipai.admin.web.query.HongbaoRecordQuery;

import java.util.List;


public interface HongbaodianProductRecordDao {

	void save(HongbaodianProductRecord record);

	HongbaodianProductRecord findById(String id);

	void updateStatusById(String id, String status);

	void updateDeliverTimeById(String id, long deliverTime);

	long countByMemberIdAndStatus(String memberId, String status);

	List<HongbaodianProductRecord> findByMemberIdAndStatus(int page, int size, String memberId, String status);

	List<HongbaodianProductRecord> findByQuery(int page, int size, HongbaoRecordQuery recordQuery);

	long countByQuery(HongbaoRecordQuery recordQuery);
}
