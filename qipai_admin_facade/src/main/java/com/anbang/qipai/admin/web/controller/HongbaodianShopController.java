package com.anbang.qipai.admin.web.controller;

import com.anbang.qipai.admin.plan.bean.shop.HongbaodianShopProductDbo;
import com.anbang.qipai.admin.plan.service.shopservice.HongbaodianShopService;
import com.anbang.qipai.admin.remote.service.QipaiHongBaoRemoteService;
import com.anbang.qipai.admin.remote.vo.CommonRemoteVO;
import com.anbang.qipai.admin.util.CommonVOUtil;
import com.anbang.qipai.admin.web.query.HongbaoRecordQuery;
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

/**
 * @Description: 红包点商城
 */
@CrossOrigin
@RestController
@RequestMapping("/hongbaodianShop")
public class HongbaodianShopController {

    @Autowired
    private HongbaodianShopService hongbaodianShopService;

    @Autowired
    private QipaiHongBaoRemoteService qipaiHongBaoRemoteService;


    @RequestMapping("/saveHongbaodianProduct")
    public CommonVO saveHongbaodianProduct(HongbaodianShopProductDbo productDbo){
        if (productDbo == null || StringUtils.isAnyBlank(productDbo.getDesc(), productDbo.getIconurl(), productDbo.getType())){
            return CommonVOUtil.lackParameter();
        }
        if (StringUtils.isBlank(productDbo.getId())) {
            productDbo.setId(null);
        }
        hongbaodianShopService.addHongbaodianProduct(productDbo);
        return CommonVOUtil.success("success");
    }

    @RequestMapping("/deleteHongbaodianProduct")
    public CommonVO deleteHongbaodianProduct(String id){
        String[] ids = {id};
        hongbaodianShopService.delete(ids);
        return CommonVOUtil.success("success");
    }

    @RequestMapping("/getHongbaodianShopProduct")
    public CommonVO getHongbaodianShopProduct(String id) {
        HongbaodianShopProductDbo productDbo = hongbaodianShopService.getHongbaodianShopProduct(id);
        return CommonVOUtil.success(productDbo,"success");
    }

    @RequestMapping("/listHongbaodianShopProduct")
    public CommonVO listHongbaodianShopProduct(int page, int size){
        ListPage listPage = hongbaodianShopService.listHongbaodianShopProduct(page,size);
        return CommonVOUtil.success(listPage,"success");
    }

    /**
     * status;// 状态：PROCESS（未发货）,SUCCESS（已发货）,FAIL（拒绝）
     */
    @RequestMapping("/listScordRecordByQuery")
    public CommonVO listScordRecordByQuery(int page, int size, HongbaoRecordQuery recordQuery){
        ListPage listPage = hongbaodianShopService.finScordRecordByQuery(page,size, recordQuery);
        int processCount = (int) hongbaodianShopService.countByMemberIdAndStatus("", "PROCESS");
        Map data = new HashMap<>();
        data.put("listPage", listPage);
        data.put("processCount", processCount);
        return CommonVOUtil.success(data,"success");
    }

    @RequestMapping("/release")
    public CommonVO release(){
        List<HongbaodianShopProductDbo> productDboList = hongbaodianShopService.listAllHongbaodianShopProduct();
        CommonRemoteVO remoteVO = qipaiHongBaoRemoteService.release(productDboList);
        if (!remoteVO.isSuccess()) {
            return CommonVOUtil.systemException();
        }
        return CommonVOUtil.success("success");
    }

    @RequestMapping("/pass")
    public CommonVO pass(String id){
        CommonRemoteVO remoteVO = qipaiHongBaoRemoteService.pass(id);
        if (!remoteVO.isSuccess()) {
            return CommonVOUtil.systemException();
        }
        return CommonVOUtil.success("success");
    }

    @RequestMapping("/refuse")
    public CommonVO refuse(String id){
        CommonRemoteVO remoteVO = qipaiHongBaoRemoteService.refuse(id);
        if (!remoteVO.isSuccess()) {
            return CommonVOUtil.systemException();
        }
        return CommonVOUtil.success("success");
    }
}
