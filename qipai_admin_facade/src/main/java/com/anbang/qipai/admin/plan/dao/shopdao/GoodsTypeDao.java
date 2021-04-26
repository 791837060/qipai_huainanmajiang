package com.anbang.qipai.admin.plan.dao.shopdao;

import com.anbang.qipai.admin.plan.bean.shop.GoodsType;

import java.util.List;

public interface GoodsTypeDao {
    void save(GoodsType goodsType);

    void delete(String id);

    List<GoodsType> listGoodsType();

    GoodsType getGoodsType(String id);

}
