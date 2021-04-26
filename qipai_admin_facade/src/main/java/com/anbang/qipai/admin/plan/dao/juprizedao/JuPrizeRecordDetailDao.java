package com.anbang.qipai.admin.plan.dao.juprizedao;

import com.anbang.qipai.admin.plan.bean.juprize.JuPrizeRecordDetail;
import com.anbang.qipai.admin.web.vo.juprize.JuPrizeRecordDetailQuery;

import java.util.List;

/**
 * @Description:
 */
public interface JuPrizeRecordDetailDao {
    void save (JuPrizeRecordDetail juPrizeRecordDetail);

    long countJuPrizeRecordByQuery(JuPrizeRecordDetailQuery query);

    List<JuPrizeRecordDetail> listJuPrizeRecordByQuery (int page,int size, JuPrizeRecordDetailQuery query);
}
