package com.anbang.qipai.admin.plan.dao.chaguandao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.chaguan.ChaguanYushiRecordDbo;
import com.anbang.qipai.admin.web.vo.chaguanvo.ChaguanYushiRecordDboVO;

public interface ChaguanYushiRecordDboDao {

	void save(ChaguanYushiRecordDbo dbo);

	long countByConditions(ChaguanYushiRecordDboVO record,String text);

	List<ChaguanYushiRecordDbo> findByConditions(int page, int size, ChaguanYushiRecordDboVO record,String text);
}
