package com.anbang.qipai.admin.plan.dao.juprizedao;

import com.anbang.qipai.admin.plan.bean.juprize.JuPrizeRelease;

import java.util.List;

/**
 * @Description:
 */
public interface JuPrizeReleaseDao {

    void save(JuPrizeRelease juPrizeRelease);

    void delete(String id);

    List<JuPrizeRelease> listJuPrize();
}
