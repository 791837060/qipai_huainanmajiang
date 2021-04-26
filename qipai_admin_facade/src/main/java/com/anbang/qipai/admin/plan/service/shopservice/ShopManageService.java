package com.anbang.qipai.admin.plan.service.shopservice;

import com.anbang.qipai.admin.plan.bean.shop.GoodsType;
import com.anbang.qipai.admin.plan.bean.shop.ScoreProductRecord;
import com.anbang.qipai.admin.plan.bean.shop.ScoreShopProductDbo;
import com.anbang.qipai.admin.plan.dao.shopdao.GoodsTypeDao;
import com.anbang.qipai.admin.plan.dao.shopdao.ScoreProductRecordDao;
import com.anbang.qipai.admin.plan.dao.shopdao.ScoreShopProductDao;
import com.anbang.qipai.admin.web.query.ScoreRecordQuery;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopManageService {
    @Autowired
    private GoodsTypeDao goodsTypeDao;

    @Autowired
    private ScoreShopProductDao scoreShopProductDao;

    @Autowired
    private ScoreProductRecordDao scoreProductRecordDao;

    public void saveGoodsType(GoodsType goodsType){
        goodsTypeDao.save(goodsType);
    }

    public void deleteGoodsType(String id){
        goodsTypeDao.delete(id);
    }

    public List<GoodsType> listGoodsType(){
        return goodsTypeDao.listGoodsType();
    }

    public GoodsType getGoodsType(String id){
        return goodsTypeDao.getGoodsType(id);
    }

    public void saveScoreShopProduct(ScoreShopProductDbo scoreShopProductDbo){
        scoreShopProductDao.save(scoreShopProductDbo);
    }

    public void deleteScoreShopProduct(String id){
        scoreShopProductDao.delete(id);
    }

    public ListPage listScoreShopProductDbo(int page, int size){
        long count = scoreShopProductDao.countScoreShopProductDbo();
        List<ScoreShopProductDbo> scoreShopProductDbos = scoreShopProductDao.listScoreShopProductDbo(page,size);
        ListPage listPage = new ListPage(scoreShopProductDbos, page, size, (int)count);
        return listPage;
    }

    public List<ScoreShopProductDbo> listScoreShopProductDbo(){
        return scoreShopProductDao.listScoreShopProductDbo();
    }

    public ScoreShopProductDbo getScoreShopProductDbo(String id){
        return scoreShopProductDao.getScoreShopProductDbo(id);
    }

    public void updateSpentNum(String id, int spentNum) {
        scoreShopProductDao.updateSpentNum(id, spentNum);
    }

    public long countScoreRecord(String id, String status){
        return scoreProductRecordDao.countByMemberIdAndStatus(id, status);
    }

    public ListPage finScordRecordByQuery(int page, int size, ScoreRecordQuery recordQuery) {
        int count = (int) scoreProductRecordDao.countByQuery(recordQuery);
        List<ScoreProductRecord> records = scoreProductRecordDao.findByQuery(page, size, recordQuery);
        return new ListPage(records, page, size, count);
    }

}

