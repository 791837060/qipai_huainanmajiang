package com.anbang.qipai.admin.plan.dao.shopdao;

import com.anbang.qipai.admin.plan.bean.shop.ScoreShopProductDbo;

import java.util.List;

public interface ScoreShopProductDao {
    void save(ScoreShopProductDbo scoreShopProductDbo);

    void delete(String id);

    List<ScoreShopProductDbo> listScoreShopProductDbo(int page, int size);

    List<ScoreShopProductDbo> listScoreShopProductDbo();

    long countScoreShopProductDbo();

    ScoreShopProductDbo getScoreShopProductDbo(String id);

    void updateSpentNum(String id, int spentNum);


}
