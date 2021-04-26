package com.anbang.qipai.admin.remote.service;

import com.anbang.qipai.admin.plan.bean.chaguan.ChaguanShopProduct;
import com.anbang.qipai.admin.plan.bean.chaguan.GuanzhuShopProduct;
import com.anbang.qipai.admin.remote.vo.CommonRemoteVO;
import com.anbang.qipai.admin.web.query.GuanzhuQuery;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("qipai-chaguan")
public interface QipaiChaguanRemoteService {

    @RequestMapping("/shop/product_add")
    public CommonRemoteVO shop_product_add(@RequestBody ChaguanShopProduct product);

    @RequestMapping("/shop/product_update")
    public CommonRemoteVO shop_product_update(@RequestBody ChaguanShopProduct product);

    @RequestMapping("/shop/product_remove")
    public CommonRemoteVO shop_product_remove(@RequestBody String[] productIds);

    @RequestMapping("/agentchaguan/apply_pass")
    public CommonRemoteVO agentchaguan_apply_pass(@RequestParam(value = "applyId") String applyId);

    @RequestMapping("/agentchaguan/apply_refuse")
    public CommonRemoteVO agentchaguan_apply_refuse(@RequestParam(value = "applyId") String applyId);

    @RequestMapping("/agentchaguan/chaguan_remove")
    public CommonRemoteVO chaguan_chaguan_remove(@RequestParam(value = "chaguanId") String chaguanId);

    @RequestMapping("/agentchaguan/chaguan_member_remove_rpc")
    public CommonRemoteVO chaguan_member_remove_rpc(@RequestParam(value = "chaguanId") String chaguanId,
                                                    @RequestParam(value = "memberId") String memberId);

    @RequestMapping("/agentchaguan/chaguan_update_rpc")
    public CommonRemoteVO chaguan_update_rpc(@RequestParam(value = "chaguanId") String chaguanId,
                                             @RequestParam(value = "name") String name, @RequestParam(value = "desc") String desc);

    @RequestMapping("/game/remove")
    public CommonRemoteVO game_remove(@RequestParam(value = "endTime") long endTime);


    @RequestMapping("/chaguan_rpc/adminQueryGuanzhuShop")
    CommonRemoteVO adminQueryGuanzhuShop(@RequestParam(value = "agentId") String agentId,
                                         @RequestParam(value = "agentName") String agentName,
                                         @RequestParam(value = "page") int page,
                                         @RequestParam(value = "size") int size);

    @RequestMapping("/chaguan_rpc/adminUpdateGuanzhuShop")
    CommonRemoteVO adminUpdateGuanzhuShop(@RequestBody GuanzhuShopProduct guanzhuShopProduct);

    @RequestMapping("/chaguan_rpc/adminRemoveGuanzhuShop")
    CommonRemoteVO adminRemoveGuanzhuShop(@RequestParam(value = "id") String id);

    @RequestMapping("/chaguan_rpc/listGuanzhuByQuery")
    CommonRemoteVO listGuanzhuByQuery(@RequestBody GuanzhuQuery guanzhuQuery,
                                      @RequestParam(value = "page") int page,
                                      @RequestParam(value = "size") int size);

    @RequestMapping("/dayingjiaka/giveDayingjiakatoagent")
    CommonRemoteVO giveDayingjiakatoagent(@RequestParam(value = "agentId") String agentId,
                                          @RequestParam(value = "amount") int amount,
                                          @RequestParam(value = "textSummary") String textSummary);

    @RequestMapping("/chaguanyushi/givechaguanyushitoagent")
    CommonRemoteVO givechaguanyushitoagent(@RequestParam(value = "agentId") String agentId,
                                           @RequestParam(value = "amount") int amount,
                                           @RequestParam(value = "textSummary") String textSummary);

}
