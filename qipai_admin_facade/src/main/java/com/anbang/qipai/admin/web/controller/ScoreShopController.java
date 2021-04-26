package com.anbang.qipai.admin.web.controller;

import com.anbang.qipai.admin.plan.bean.shop.GoodsType;
import com.anbang.qipai.admin.plan.bean.shop.ScoreShopProductDbo;
import com.anbang.qipai.admin.plan.service.shopservice.ShopManageService;
import com.anbang.qipai.admin.remote.service.QipaiMembersRemoteService;
import com.anbang.qipai.admin.remote.vo.CommonRemoteVO;
import com.anbang.qipai.admin.util.CommonVOUtil;
import com.anbang.qipai.admin.web.query.ScoreRecordQuery;
import com.anbang.qipai.admin.web.vo.CommonVO;
import com.highto.framework.web.page.ListPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/scoreshop")
public class ScoreShopController {
    @Autowired
    private ShopManageService shopManageService;

    @Autowired
    private QipaiMembersRemoteService qipaiMembersRomoteService;

    @RequestMapping("/saveGoodsType")
    public CommonVO saveGoodsType(GoodsType goodsType){
        if (goodsType == null || StringUtils.isBlank(goodsType.getType())) {
            return CommonVOUtil.lackParameter();
        }

        goodsType.setType(goodsType.getType().trim());
        if (StringUtils.isBlank(goodsType.getId())) {
            goodsType.setId(null);
        }

        if (StringUtils.isNotBlank(goodsType.getId())) {
            CommonRemoteVO remoteVO = qipaiMembersRomoteService.updateType(goodsType.getId(), goodsType.getType());
            if (!remoteVO.isSuccess()) {
                return CommonVOUtil.systemException();
            }
            return CommonVOUtil.success("success");
        } else {
            CommonRemoteVO remoteVO = qipaiMembersRomoteService.addType(goodsType.getType());
            if (!remoteVO.isSuccess()) {
                return CommonVOUtil.systemException();
            }
            return CommonVOUtil.success("success");
        }
    }

    @RequestMapping("/deleteGoodsType")
    public CommonVO deleteGoodsType(String id){
        String[] ids = {id};
        CommonRemoteVO remoteVO = qipaiMembersRomoteService.removeType(ids);
        if (!remoteVO.isSuccess()) {
            return CommonVOUtil.systemException();
        }
//        shopManageService.deleteGoodsType(id);
        return CommonVOUtil.success("success");
    }

    @RequestMapping("/listGoodsType")
    public CommonVO listGoodsType(){
        List<GoodsType> goodsTypes = shopManageService.listGoodsType();
        return CommonVOUtil.success(goodsTypes, "success");
    }

    @RequestMapping("/getGoodsType")
    public CommonVO getGoodsType(String id){
        GoodsType goodsType = shopManageService.getGoodsType(id);
        return CommonVOUtil.success(goodsType, "success");
    }

    @RequestMapping("/saveScoreShopProduct")
    public CommonVO saveScoreShopProduct(ScoreShopProductDbo scoreShopProduct){
        if (scoreShopProduct== null || StringUtils.isAnyBlank(scoreShopProduct.getDesc(), scoreShopProduct.getIconurl(),
                scoreShopProduct.getType())){
            return CommonVOUtil.lackParameter();
        }
        if (StringUtils.isBlank(scoreShopProduct.getId())) {
            scoreShopProduct.setId(null);
        }
        scoreShopProduct.setCreateTime(System.currentTimeMillis());
        shopManageService.saveScoreShopProduct(scoreShopProduct);
        return CommonVOUtil.success("success");
    }

    @RequestMapping("/deleteScoreShopProduct")
    public CommonVO deleteScoreShopProduct(String id){
        shopManageService.deleteScoreShopProduct(id);
        return CommonVOUtil.success("success");
    }

    @RequestMapping("/listScoreShopProduct")
    public CommonVO listScoreShopProduct(int page, int size){
        ListPage listPage = shopManageService.listScoreShopProductDbo(page, size);
        return CommonVOUtil.success(listPage, "success");
    }

    @RequestMapping("/getScoreShopProduct")
    public CommonVO getScoreShopProduct(String id){
        ScoreShopProductDbo scoreShopProductDbo = shopManageService.getScoreShopProductDbo(id);
        return CommonVOUtil.success(scoreShopProductDbo, "success");
    }

    /**
     * 发布商品
     */
    @RequestMapping("/postScoreGoods")
    public CommonVO postScoreGoods(){
        List<ScoreShopProductDbo> productDbos = shopManageService.listScoreShopProductDbo();
        CommonRemoteVO remoteVO = qipaiMembersRomoteService.release(productDbos);
        if (!remoteVO.isSuccess()) {
            return CommonVOUtil.systemException();
        }
        return CommonVOUtil.success("success");
    }

    /**
     * 积分兑换记录
     * status;// 状态：PROCESS（未发货）,SUCCESS（已发货）,FAIL（拒绝）
     */
    @RequestMapping("/listScoreRecord")
    public CommonVO listScoreRecord(int page, int size, ScoreRecordQuery scoreRecordQuery){
        ListPage listPage = shopManageService.finScordRecordByQuery(page, size, scoreRecordQuery);
        int processCount = (int)shopManageService.countScoreRecord("", "PROCESS");
        Map data = new HashMap();
        data.put("listPage", listPage);
        data.put("processCount", processCount);
        return CommonVOUtil.success(data,"success");
    }

    /**
     * 通过商品发货审核
     */
    @RequestMapping("/pass")
    public CommonVO passScoreProductRecord(String id) {
        CommonRemoteVO remoteVO = qipaiMembersRomoteService.pass(id);
        if (!remoteVO.isSuccess()) {
            return CommonVOUtil.systemException();
        }
        return CommonVOUtil.success("success");
    }

    /**
     * 拒绝商品发货审核
     */
    @RequestMapping("/refuse")
    public CommonVO refuseScoreProductRecord(String id) {
        CommonRemoteVO remoteVO = qipaiMembersRomoteService.refuse(id);
        if (!remoteVO.isSuccess()) {
            return CommonVOUtil.systemException();
        }
        return CommonVOUtil.success("success");
    }
}
