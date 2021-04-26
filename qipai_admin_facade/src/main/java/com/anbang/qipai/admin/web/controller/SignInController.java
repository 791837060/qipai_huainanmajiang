package com.anbang.qipai.admin.web.controller;

import java.util.List;
import java.util.UUID;

import com.anbang.qipai.admin.msg.service.SignInPrizeSourceService;
import com.anbang.qipai.admin.plan.bean.members.MemberExchangeEntityDbo;
import com.anbang.qipai.admin.plan.bean.members.SendOutGoods;
import com.anbang.qipai.admin.plan.service.membersservice.MemberExchangeEntityService;
import com.anbang.qipai.admin.remote.LotteryMoEnum;
import com.anbang.qipai.admin.web.vo.membersvo.MemberExchangeEntityPageVO;
import com.anbang.qipai.admin.web.vo.membersvo.SignInPrizeLogPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.anbang.qipai.admin.plan.bean.signin.SignInPrize;
import com.anbang.qipai.admin.plan.bean.signin.SignInPrizeExchangeLog;
import com.anbang.qipai.admin.plan.bean.signin.SignInPrizeLog;
import com.anbang.qipai.admin.plan.service.signinservice.SignInPrizeExchangeLogService;
import com.anbang.qipai.admin.plan.service.signinservice.SignInPrizeLogService;
import com.anbang.qipai.admin.plan.service.signinservice.SignInPrizeService;
import com.anbang.qipai.admin.web.vo.CommonVO;

/**
 * 主要有抽奖奖励的CRUD以及发布抽奖奖励的输出通道；
 * 其中中奖概率以及首次中奖概率可以在发布抽奖中进行设置；
 * 按照产品设计，发布的抽奖奖励个数不超过10个；
 * 抽奖的概率以及首次中奖概率可以设置为0；
 * <p>
 * 签到抽奖Controller
 */
@CrossOrigin
@RestController
@RequestMapping("/signin")
public class SignInController {

    @Autowired
    private SignInPrizeService signInPrizeService;

    @Autowired
    private SignInPrizeLogService signInPrizeLogService;

    @Autowired
    private SignInPrizeExchangeLogService signInPrizeExchangeLogService;

    @Autowired
    private SignInPrizeSourceService signInPrizeSourceService;

    @Autowired
    private MemberExchangeEntityService entityService;

    //添加抽奖奖励
    @RequestMapping(value = "/addsigninprize")
    public CommonVO addSignInPrize(SignInPrize signInPrize) {
        CommonVO vo = new CommonVO();
        if (!signInPrize.getType().equals("谢谢惠顾")) {
            if (signInPrize.getName() == null ||
                    signInPrize.getSingleNum() == 0 ||
                    signInPrize.getStoreNum() == 0 ||
                    signInPrize.getIconUrl() == null ||
                    //中奖概率可以设置为0，中奖概率类型设计为包装类，可以与null进行比较
                    signInPrize.getPrizeProb() == null ||
                    signInPrize.getFirstPrizeProb() == null ||
                    signInPrize.getOverstep() == null //新增OverStep必填
                    ) {
                vo.setSuccess(false);
                vo.setMsg("incompleteParam");
                return vo;
            }
        } else {
            if (signInPrize.getName() == null ||
                    signInPrize.getIconUrl() == null ||
                    //中奖概率可以设置为0，中奖概率类型设计为包装类，可以与null进行比较
                    signInPrize.getPrizeProb() == null ||
                    signInPrize.getFirstPrizeProb() == null ||
                    signInPrize.getOverstep() == null //新增OverStep必填)
                    ) {
                vo.setSuccess(false);
                vo.setMsg("incompleteParam");
                return vo;
            }
        }

//        int count = signInPrizeService.countSignInPrize();
//        //这里进行抽奖的奖励设置
//        if (count >= 10 && signInPrize.getOverstep().equals("否")) {
//            vo.setSuccess(false);
//            vo.setMsg("overstep");
//            return vo;
//        }
        signInPrize.setId(String.valueOf(signInPrizeService.findIndex() + 1));
        signInPrizeService.addSignInPrize(signInPrize);
        vo.setSuccess(true);
        vo.setMsg("success");
        return vo;
    }

    //查询所有抽奖奖励
    @RequestMapping(value = "/querysigninprize")
    public CommonVO querySignInPrize() {
        CommonVO vo = new CommonVO();
        List<SignInPrize> list = signInPrizeService.querySignInPrize();
        vo.setSuccess(true);
        vo.setMsg("success");
        vo.setData(list);
        return vo;
    }

    //根据id查询抽奖奖励
    @RequestMapping(value = "/querysigninprizebyid")
    public CommonVO querySignInPrizeById(String id) {
        CommonVO vo = new CommonVO();
        SignInPrize signInPrize = signInPrizeService.querySignInPrizeById(id);
        vo.setSuccess(true);
        vo.setMsg("success");
        vo.setData(signInPrize);
        return vo;
    }

    //根据id删除抽奖奖励
    @RequestMapping(value = "/deletesigninprizebyid", method = RequestMethod.POST)
    public CommonVO deleteSignInPrizeById(String id) {
        CommonVO vo = new CommonVO();
        signInPrizeService.deleteSignInPrizeByIdAdvice(id);
        vo.setSuccess(true);
        vo.setMsg("success");
        return vo;
    }

    //修改抽奖奖品(无论是否发布，修改后都要把state设置为1)
    @RequestMapping(value = "/updatesigninprize", method = RequestMethod.POST)
    public CommonVO updateSignInPrize(SignInPrize signInPrize) {
        CommonVO vo = new CommonVO();
        signInPrizeService.updateSignInPrize(signInPrize);
        vo.setSuccess(true);
        vo.setMsg("success");
        return vo;
    }

    //发布10个抽奖奖励
    @RequestMapping(value = "/releasesigninprize", method = RequestMethod.POST)
    public CommonVO releaseSignInPrize() {
        CommonVO vo = new CommonVO();
        int count = signInPrizeService.countSignInPrize();
        if (count < 10) {
            vo.setSuccess(false);
            vo.setMsg("notEnough");
            vo.setData(count);
            return vo;
        }
        List<SignInPrize> list = signInPrizeService.querySignInPrize();
        long checkPrizeProb = 0;
        long checkFirstPrizeProb = 0;
        for (SignInPrize signInPrize : list) {
            checkPrizeProb += signInPrize.getPrizeProb();
            checkFirstPrizeProb += signInPrize.getFirstPrizeProb();
        }
        if (checkPrizeProb != 10000000) {
            vo.setSuccess(false);
            vo.setMsg("中奖概率设置有误");
            //vo.setData(checkPrizeProb);
            return vo;
        }
        if (checkFirstPrizeProb != 10000000) {
            vo.setSuccess(false);
            vo.setMsg("首次中奖概率设置有误");
            //vo.setData(checkFirstPrizeProb);
            return vo;
        }
        // kafka发消息
        signInPrizeService.releaseSignInPrize();
        vo.setSuccess(true);
        vo.setMsg("success");
        return vo;
    }

    //查询抽奖中奖纪录
    @RequestMapping(value = "/querysigninprizelog", method = RequestMethod.POST)
    public CommonVO querySignInPrizeLog(SignInPrizeLog signInPrizeLog,
                                        @RequestParam(value = "startTime", required = false) Long startTime,
                                        @RequestParam(value = "endTime", required = false) Long endTime,
                                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                                        @RequestParam(name = "size", defaultValue = "10") Integer size) {
        CommonVO vo = new CommonVO();
        List<SignInPrizeLog> signInPrizeLogs =
                signInPrizeLogService.querySignInPrizeLog(signInPrizeLog, startTime, endTime, page, size);
        int count = signInPrizeLogService.countSignInPrizeLog(signInPrizeLog, startTime, endTime);

        SignInPrizeLogPageVO signInPrizeLogPageVO = new SignInPrizeLogPageVO();
        signInPrizeLogPageVO.setCount(count);
        signInPrizeLogPageVO.setPage(page);
        signInPrizeLogPageVO.setSize(size);
        signInPrizeLogPageVO.setList(signInPrizeLogs);

        vo.setSuccess(true);
        vo.setMsg("success");
        vo.setData(signInPrizeLogPageVO);
        return vo;
    }

    //查询抽奖奖品兑换纪录
    @RequestMapping(value = "/querysigninprizeexchangelog", method = RequestMethod.POST)
    public CommonVO querySignInPrizeExchangeLog(SignInPrizeExchangeLog signInPrizeExchangeLog,
                                                @RequestParam(value = "startTime", required = false) Long startTime,
                                                @RequestParam(value = "endTime", required = false) Long endTime) {
        CommonVO vo = new CommonVO();
        List<SignInPrizeExchangeLog> list = signInPrizeExchangeLogService
                .querySignInPrizeExchangeLog(signInPrizeExchangeLog, startTime, endTime);
        vo.setSuccess(true);
        vo.setMsg("success");
        vo.setData(list);
        return vo;
    }

    //查询抽奖奖品兑换纪录  最终版本
    @RequestMapping(value = "/queryexchangeentity", method = RequestMethod.POST)
    public CommonVO queryExchangeEntity(String memberId, String nickName, String telephone,
                                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                                        @RequestParam(name = "size", defaultValue = "10") Integer size,
                                        @RequestParam(value = "startTime", required = false) Long startTime,
                                        @RequestParam(value = "endTime", required = false) Long endTime) {

        MemberExchangeEntityPageVO entityPageVO = entityService.findWithConditions(memberId, nickName,
                telephone, page, size,
                startTime, endTime);
        CommonVO vo = new CommonVO();
        vo.setSuccess(true);
        vo.setData(entityPageVO);
        return vo;
    }


    @RequestMapping("/querypendingreward")
    public CommonVO queryPendingReward() {
        CommonVO commonVO = new CommonVO();
        commonVO.setSuccess(true);
        commonVO.setData(entityService.countPending());
        return commonVO;
    }

    /**
     * 统计抽奖奖品个数
     *
     * @return
     */
    @RequestMapping(value = "/countunissuesigninprize", method = RequestMethod.POST)
    public CommonVO countUnIssueSignInPrize() {
        CommonVO vo = new CommonVO();
        int count = signInPrizeExchangeLogService.countUnIssueSignInPrize();
        vo.setSuccess(true);
        vo.setMsg("success");
        vo.setData(count);
        return vo;
    }

    //发放兑换记录奖品
    @RequestMapping(value = "/issuesigninprize", method = RequestMethod.POST)
    public CommonVO issueSignInPrize(int id) {
        CommonVO vo = new CommonVO();
        signInPrizeExchangeLogService.issueSignInPrize(id);
        vo.setSuccess(true);
        vo.setMsg("success");
        return vo;
    }

    //发货  实体
    @RequestMapping(value = {"/sendoutgood"})
    public CommonVO sendOutGood(String id, boolean send) {
        CommonVO vo = new CommonVO();
        MemberExchangeEntityDbo entityDbo = entityService.findById(id);

        if (entityDbo == null) {
            vo.setSuccess(false);
            vo.setMsg("没有该条中奖记录");
        }
        if (entityDbo.getHasExchange()) {
            vo.setSuccess(false);
            vo.setMsg("已经兑换过了");
            return vo;
        }

        //暂时不用通知会员大厅已经发货
//        SendOutGoods goods = new SendOutGoods();
//        goods.setHasSent("YES");
//        goods.setMemberId(memberId);
//        goods.setRaffleRecordId(raffleRecordId);
//        signInPrizeSourceService.sendOutGood(goods);


        entityDbo.setDistributeTime(System.currentTimeMillis());
        entityDbo.setHasExchange(true);
        entityService.saveOne(entityDbo);
        vo.setSuccess(true);
        vo.setMsg("发货成功");
        return vo;
    }

    /*
    * 批量删除奖品
    * 删除全部
    **/
    @RequestMapping(value = "batchDelete")
    @ResponseBody
    public CommonVO batchDelete() {
        List<SignInPrize> list = signInPrizeService.querySignInPrize();
        for (SignInPrize signInPrize : list) {
            signInPrizeService.deleteSignInPrizeById(signInPrize.getId());
        }
        CommonVO commonVO = new CommonVO();
        commonVO.setSuccess(true);
        commonVO.setMsg("success");
        return commonVO;
    }

    /*
    * 批量新增奖品
    * 一共新增10个
    **/
    @RequestMapping(value = "batchAdd")
    @ResponseBody
    public CommonVO batchAdd() {
        for (int i = 0; i < 10; i++) {
            SignInPrize signInPrize = new SignInPrize();
            signInPrize.setId(String.valueOf(i));
            signInPrize.setIndex(String.valueOf(i));
            signInPrize.setName("日卡");
            signInPrize.setCardType("日卡");
            signInPrize.setType("会员卡");
            signInPrize.setSingleNum(10);
            signInPrize.setStoreNum(1000);
            signInPrize.setPrizeProb(1000000);
            signInPrize.setFirstPrizeProb(1000000);
            signInPrize.setIconUrl("1");
            signInPrize.setOverstep("否");
            addSignInPrize(signInPrize);
        }

        CommonVO commonVO = new CommonVO();
        commonVO.setSuccess(true);
        commonVO.setMsg("success");
        return commonVO;
    }


}
