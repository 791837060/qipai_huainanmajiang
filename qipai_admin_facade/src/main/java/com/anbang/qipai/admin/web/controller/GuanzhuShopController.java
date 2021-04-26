package com.anbang.qipai.admin.web.controller;

import com.anbang.qipai.admin.plan.bean.chaguan.GuanzhuShopProduct;
import com.anbang.qipai.admin.plan.service.chaguanservice.GuanzhuShopOrderService;
import com.anbang.qipai.admin.remote.service.QipaiChaguanRemoteService;
import com.anbang.qipai.admin.remote.vo.CommonRemoteVO;
import com.anbang.qipai.admin.util.CommonVOUtil;
import com.anbang.qipai.admin.web.query.GuanzhuOrderQuery;
import com.anbang.qipai.admin.web.query.GuanzhuQuery;
import com.anbang.qipai.admin.web.vo.CommonVO;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 馆主商城
 *
 * @author ethan
 */
@CrossOrigin
@RestController
@RequestMapping("/guanzhuShop")
public class GuanzhuShopController {
    @Autowired
    private GuanzhuShopOrderService guanzhuShopOrderService;

    @Autowired
    private QipaiChaguanRemoteService qipaiChaguanRemoteService;

    @RequestMapping("/adminQueryGuanzhuShop")
    public CommonVO adminQueryGuanzhuShop(String agentId, String agentName, int page, int size){
        CommonRemoteVO remoteVO = qipaiChaguanRemoteService.adminQueryGuanzhuShop(agentId, agentName, page, size);
        if (remoteVO != null && remoteVO.isSuccess()) {
            return CommonVOUtil.success(remoteVO.getData(), "success");
        }
        return CommonVOUtil.systemException();
    }

    @RequestMapping("/adminUpdateGuanzhuShop")
    public CommonVO adminUpdateGuanzhuShop(GuanzhuShopProduct guanzhuShopProduct){
        CommonRemoteVO remoteVO = qipaiChaguanRemoteService.adminUpdateGuanzhuShop(guanzhuShopProduct);
        if (remoteVO != null && remoteVO.isSuccess()) {
            return CommonVOUtil.success(remoteVO.getData(), "success");
        }
        return CommonVOUtil.systemException();
    }

    @RequestMapping("/adminRemoveGuanzhuShop")
    public CommonVO adminRemoveGuanzhuShop(String id){
        CommonRemoteVO remoteVO = qipaiChaguanRemoteService.adminRemoveGuanzhuShop(id);
        if (remoteVO != null && remoteVO.isSuccess()) {
            return CommonVOUtil.success(remoteVO.getData(), "success");
        }
        return CommonVOUtil.systemException();
    }

    @RequestMapping("/listGuanzhuShopOrder")
    public CommonVO listGuanzhuShopOrder(GuanzhuOrderQuery guanzhuOrderQuery, int page, int size){
        ListPage listPage = guanzhuShopOrderService.listByQuery(guanzhuOrderQuery, page, size);
        return CommonVOUtil.success(listPage, "success");
    }

    @RequestMapping("/listGuanzhuByQuery")
    public CommonVO listGuanzhuByQuery(GuanzhuQuery guanzhuQuery, int page, int size){
        CommonRemoteVO remoteVO = qipaiChaguanRemoteService.listGuanzhuByQuery(guanzhuQuery, page, size);
        if (remoteVO != null && remoteVO.isSuccess()) {
            return CommonVOUtil.success(remoteVO.getData(), "success");
        }
        return CommonVOUtil.systemException();
    }

}
