package com.anbang.qipai.admin.plan.dao.juprizedao;

import com.anbang.qipai.admin.plan.bean.juprize.JuPrize;

import java.util.List;

public interface JuPrizeDao {
    void save(JuPrize juPrize);

    void delete(String id);

    JuPrize getJuPrize(String id);

    List<JuPrize> listJuPrize();

    void updateStoreNum(String id, int storeNum);

}
