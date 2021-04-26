package com.anbang.qipai.admin.web.controller;

import com.anbang.qipai.admin.cqrs.c.service.AdminAuthService;
import com.anbang.qipai.admin.plan.bean.permission.Admin;
import com.anbang.qipai.admin.plan.bean.permission.AdminDiamondLimitRecord;
import com.anbang.qipai.admin.plan.bean.permission.AdminRefRole;
import com.anbang.qipai.admin.plan.service.permissionservice.AdminDiamondLimitRecordService;
import com.anbang.qipai.admin.plan.service.permissionservice.AdminService;
import com.anbang.qipai.admin.remote.service.QinyouquanRemoteService;
import com.anbang.qipai.admin.remote.vo.CommonRemoteVO;
import com.anbang.qipai.admin.web.vo.CommonVO;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin
@RestController
@RequestMapping("/qinyouquan")
public class QinyouquanController {

    @Autowired
    private QinyouquanRemoteService qinyouquanRemoteService;

    @Autowired
    private AdminAuthService adminAuthService;

    @Autowired
    private AdminDiamondLimitRecordService adminDiamondLimitRecordService;

    @Autowired
    private AdminService adminService;


/*    @RequestMapping("/giveToMember")
    public CommonVO GiveToMember(String token, String memberId, Integer amount){
        CommonVO vo=new CommonVO();
        AdminDiamondLimitRecord adminDiamondLimitRecord = new AdminDiamondLimitRecord();
        String adminId = adminAuthService.getAdminIdBySessionId(token);
        if (adminId == null) {
            vo.setSuccess(false);
            vo.setMsg("管理员不存在");
            return vo;
        }
        Admin admin = adminService.findAdminById(adminId);
        int diamondLimit = admin.getDiamondLimit();
        if (amount>diamondLimit){
            vo.setSuccess(false);
            vo.setMsg("限额不足");
            return vo;
        }
        CommonRemoteVO commonRemoteVO = qinyouquanRemoteService.giveYushiToMember(memberId, amount);
        if (commonRemoteVO.isSuccess()){
            adminService.updateAdminDiamondLimint(adminId,amount,diamondLimit);
            //查询赠送砖石以后管理员的剩余额度
            long accountingTime=System.currentTimeMillis();//充值时间
            Admin admin1 = adminService.queryAdminDiamondBalance1(adminId);
            int balance = admin1.getDiamondLimit();//获取管理员剩余砖石额度。
            adminDiamondLimitRecord.setAccountingTime(accountingTime);
            adminDiamondLimitRecord.setBalance(balance);
            adminDiamondLimitRecord.setDiamondAmount(amount);
            adminDiamondLimitRecord.setMemberId(memberId);
            adminDiamondLimitRecord.setAdminId(adminId);
            adminService.insertAdminDiamondLimint(adminDiamondLimitRecord);
            vo.setMsg("添加成功");
            return vo;
        }else {
            vo.setSuccess(false);
            vo.setMsg(commonRemoteVO.getMsg());
            return vo;
        }

    }*/

    /**
     * 修改玉石限额
     * adminId  被修改对象Id
     * adminIdBySession  管理员id
     */

    @RequestMapping("/updateAdminJadeLimit")
    public CommonVO updateAdminDiamondLimint(String token, Integer jadeLimit, String adminId, String nickName){
        CommonVO vo=new CommonVO();
        long accountingTime=System.currentTimeMillis();//充值时间
        AdminDiamondLimitRecord adminDiamondLimitRecord = new AdminDiamondLimitRecord();
        String adminIdBySession = adminAuthService.getAdminIdBySessionId(token);
        if (adminIdBySession == null) {
            vo.setSuccess(false);
            vo.setMsg("管理员不存在");
            return vo;
        }
        Admin adminById = adminService.findAdminById(adminIdBySession);
        //修改自己的额度
        boolean adminRole=false;
        for (AdminRefRole adminRefRole : adminById.getRoleList()) {
            if (adminRefRole.getRole().equals("admin")) {
                adminRole=true;
                break;
            }
        }
        if (adminRole){
            if (!adminId.equals(adminById.getId())){
                Admin adminById1 = adminService.findAdminById(adminIdBySession);
                if (adminById1.getJadeLimit()<jadeLimit) {
                    vo.setSuccess(false);
                    vo.setMsg("限额不足");
                    return vo;
                }
                adminService.updateAdminJadeLimint(adminIdBySession,jadeLimit,adminById1.getJadeLimit());
            }else {
                Admin adminById1 = adminService.findAdminById(adminIdBySession);
                if (adminById1.getJadeLimit()<jadeLimit) {
                    vo.setSuccess(false);
                    vo.setMsg("限额不足");
                    return vo;
                }
                adminService.updateAdminJadeLimint(adminId,0,jadeLimit);
                //查询赠送玉石后管理员的剩余额度
                Admin admin = adminService.queryAdminDiamondBalance(adminId);
                int jadeBalance = admin.getJadeLimit();//获取管理员剩余玉石额度。
                adminDiamondLimitRecord.setAccountingTime(accountingTime);
                adminDiamondLimitRecord.setJadeBalace(jadeBalance);
                adminDiamondLimitRecord.setJadeAmount(jadeLimit);
                adminDiamondLimitRecord.setNickName(nickName);
                adminDiamondLimitRecord.setAdminId(adminIdBySession);
                adminService.insertAdminDiamondLimint(adminDiamondLimitRecord);
                return vo;
            }
        }
        //修改别人的额度
        adminService.updateAdminJadeLimint(adminId,0,jadeLimit);
        //查询赠送玉石以后管理员的剩余额度
        Admin admin = adminService.queryAdminDiamondBalance1(adminIdBySession);
        int jadeBalance = admin.getJadeLimit();//获取管理员剩余玉石额度。
        adminDiamondLimitRecord.setAccountingTime(accountingTime);
        adminDiamondLimitRecord.setJadeBalace(jadeBalance);
        adminDiamondLimitRecord.setJadeAmount(jadeLimit);
        adminDiamondLimitRecord.setNickName(nickName);
        adminDiamondLimitRecord.setAdminId(adminIdBySession);
        adminService.insertAdminDiamondLimint(adminDiamondLimitRecord);
        vo.setMsg("修改成功");
        return vo;
    }

    /**
     * 查询钻石流水
     */
    @RequestMapping("/queryAdminDiamondLimint")
    public CommonVO queryAdminDiamondLimint(@RequestParam(value = "page", defaultValue = "1") int page,
                                            @RequestParam(value = "size", defaultValue = "10") int size,
                                            long startTime, long endTime, String adminId) {
        CommonVO vo = new CommonVO();
        ListPage listPage =adminDiamondLimitRecordService.findByUser(adminId,startTime,endTime,page,size);
        vo.setMsg("查询成功");
        vo.setData(listPage);
        return vo;
    }

    /**
     * 联盟详情查询
     * 需要token，在操作拦截器哪里做权限验证
     */
    @RequestMapping("/querylianmeng")
    public CommonVO querylianmeng(@RequestParam(value = "page",defaultValue = "1") int page,
                                  @RequestParam(value = "size",defaultValue = "30") int size,
                                  String lianmengId, String memberId){
        CommonVO vo = new CommonVO();
        CommonRemoteVO commonRemoteVO = qinyouquanRemoteService.queryLianmeng(page,size,lianmengId,memberId);
        if (commonRemoteVO.isSuccess()){
            vo.setMsg(commonRemoteVO.getMsg());
            vo.setData(commonRemoteVO.getData());
            return vo;
        }
        return vo;
    }
    /**
     *  查询联盟合伙人
     *  需要token，在操作拦截器哪里做权限验证
     */
    @RequestMapping("/querylianmeng_hehuoren")
    public CommonVO querylianmeng_hehuoren(@RequestParam(value = "page",defaultValue = "1") int page,
                                           @RequestParam(value = "size",defaultValue = "30") int size,
                                           String lianmengId){
        CommonVO vo = new CommonVO();
        CommonRemoteVO commonRemoteVO = qinyouquanRemoteService.querylianmeng_hehuoren(page,size,lianmengId);
        if (commonRemoteVO.isSuccess()){
            vo.setMsg(commonRemoteVO.getMsg());
            vo.setData(commonRemoteVO.getData());
            return vo;
        }
        return vo;
    }
    /**
     *  查询联盟玩家
     *  需要token，在操作拦截器哪里做权限验证
     */
    @RequestMapping("/querylianmeng_member")
    public CommonVO querylianmeng_member(@RequestParam(value = "page",defaultValue = "1") int page,
                                         @RequestParam(value = "size",defaultValue = "30") int size,
                                         String lianmengId){
        CommonVO vo = new CommonVO();
        CommonRemoteVO commonRemoteVO = qinyouquanRemoteService.querylianmeng_member(page,size,lianmengId);
        if (commonRemoteVO.isSuccess()){
            vo.setMsg(commonRemoteVO.getMsg());
            vo.setData(commonRemoteVO.getData());
            return vo;
        }
        return vo ;
    }

    /**
     *  查询联盟的合伙人的玩家id
     *  需要token，在操作拦截器哪里做权限验证
     */
    @RequestMapping("/query_hehuoren_member")
    public CommonVO query_hehuoren_member(@RequestParam(value = "lianmengId")String lianmengId){
        CommonVO vo = new CommonVO();
        CommonRemoteVO commonRemoteVO=qinyouquanRemoteService.query_hehuoren_member(lianmengId);
        if (commonRemoteVO.isSuccess()){
            vo.setMsg(commonRemoteVO.getMsg());
            vo.setData(commonRemoteVO.getData());
            return vo;
        }
        return vo ;
    }
    /**
     * 查询所有联盟盟主
     * 需要token，在操作拦截器哪里做权限验证
     */
    @RequestMapping("/lianmeng_mengzhu")
    public CommonVO lianmeng_mengzhu(@RequestParam(value = "memberId")String memberId){
        CommonVO vo = new CommonVO();
        CommonRemoteVO commonRemoteVO=qinyouquanRemoteService.lianmeng_mengzhu(memberId);
        if (commonRemoteVO.isSuccess()){
            vo.setMsg(commonRemoteVO.getMsg());
            vo.setData(commonRemoteVO.getData());
            return vo;
        }
        return vo ;
    }
    /**
     *  查询所有盟主的联盟
     *  需要token，在操作拦截器哪里做权限验证
     */
    @RequestMapping("/querylianmengBymengzhu")
    public CommonVO querylianmengBymengzhu(@RequestParam(value = "page",defaultValue = "1") int page,
                                           @RequestParam(value = "size",defaultValue = "30") int size,
                                           String mengzhuId){
        CommonVO vo = new CommonVO();
        CommonRemoteVO commonRemoteVO=qinyouquanRemoteService.querylianmengBymengzhu(page,size,mengzhuId);
        if (commonRemoteVO.isSuccess()){
            vo.setMsg(commonRemoteVO.getMsg());
            vo.setData(commonRemoteVO.getData());
            return vo;
        }
        return vo;
    }
    /**
     *  查询——大联盟消耗
     */
    @RequestMapping("/lianmeng_cost")
    public CommonVO lianmeng_cost(String goldSort, String gameSort, String mengzhu, String lianmengId, Long startTime, Long endTime){
        CommonVO vo = new CommonVO();
        CommonRemoteVO commonRemoteVO=qinyouquanRemoteService.lianmeng_cost(goldSort,gameSort,mengzhu,lianmengId,startTime,endTime);
        if (commonRemoteVO.isSuccess()){
            vo.setMsg(commonRemoteVO.getMsg());
            vo.setData(commonRemoteVO.getData());
            return vo;
        }
        return vo;
    }
    /**
     * 房间查询
     * 需要token，在操作拦截器哪里做权限验证
     */
    @RequestMapping("/gametable_query")
    public CommonVO gametable_query(@RequestParam(value = "page",defaultValue = "1") int page,
                                    @RequestParam(value = "size",defaultValue = "30") int size,
                                    String memberId, String lianmengId, String game, Long startTime, Long endTime,@RequestParam(value = "no")String no,String state){
        CommonVO vo = new CommonVO();
        CommonRemoteVO commonRemoteVO=qinyouquanRemoteService.gametable_query(page,size,memberId,lianmengId,game,startTime,endTime,no,state);
        if (commonRemoteVO.isSuccess()){
            vo.setMsg(commonRemoteVO.getMsg());
            vo.setData(commonRemoteVO.getData());
            return vo;
        }
        return vo;
    }
    /**
     * 查询游戏日报
     * 需要token，在操作拦截器哪里做权限验证
     */
    @RequestMapping("/gamereport")
    public CommonVO gamereport(@RequestParam(value = "startTime",required = true) Long startTime,
                               @RequestParam(value = "endTime",required = true) Long endTime,
                               @RequestParam(value = "game",required = true) String game){
        CommonVO vo = new CommonVO();
        CommonRemoteVO commonRemoteVO=qinyouquanRemoteService.gamereport(startTime,endTime,game);
        if (commonRemoteVO.isSuccess()){
            vo.setMsg(commonRemoteVO.getMsg());
            vo.setData(commonRemoteVO.getData());
            return vo;
        }
        return vo;
    }
    /**
     * 查看盟主能量消耗详情
     * 需要token，在操作拦截器哪里做权限验证
     */
    @RequestMapping("/queryMemberPowerDetail")
    public CommonVO queryMemberPowerDetail(@RequestParam(value = "page",defaultValue = "1") int page,
                                           @RequestParam(value = "size",defaultValue = "30") int size,
                                           String memberId, String lianmengId, long queryTime){
        CommonVO vo = new CommonVO();
        CommonRemoteVO commonRemoteVO=qinyouquanRemoteService.queryMemberPowerDetail(page,size,memberId,lianmengId,queryTime);
        if (commonRemoteVO.isSuccess()){
            vo.setMsg(commonRemoteVO.getMsg());
            vo.setData(commonRemoteVO.getData());
            return vo;
        }
        return vo;
    }
    /**
     *  查看对局房间详情
     *  需要token，在操作拦截器哪里做权限验证
     */
    @RequestMapping("/queryroomdetail")
    public CommonVO queryroomdetail(String gameId){
        CommonVO vo=new CommonVO();
        CommonRemoteVO commonRemoteVO = qinyouquanRemoteService.queryroomdetail(gameId);
        if (commonRemoteVO.isSuccess()){
            vo.setMsg(commonRemoteVO.getMsg());
            vo.setData(commonRemoteVO.getData());
            return vo;
        }
        return vo ;
    }


}
