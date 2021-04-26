package com.anbang.qipai.dalianmeng.cqrs.q.service;

import com.anbang.qipai.dalianmeng.cqrs.c.domain.CreateLianmengResult;
import com.anbang.qipai.dalianmeng.cqrs.c.domain.JoinLianmengResult;
import com.anbang.qipai.dalianmeng.cqrs.q.dao.*;
import com.anbang.qipai.dalianmeng.cqrs.q.dbo.*;
import com.anbang.qipai.dalianmeng.plan.bean.LianmengDiamondDayCost;
import com.anbang.qipai.dalianmeng.plan.bean.MemberDayResultData;
import com.anbang.qipai.dalianmeng.plan.dao.GameHistoricalJuResultDao;
import com.anbang.qipai.dalianmeng.plan.dao.LianmengDiamondDayCostDao;
import com.anbang.qipai.dalianmeng.plan.dao.MemberDayResultDataDao;
import com.anbang.qipai.dalianmeng.web.vo.*;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LianmengService {
    @Autowired
    private AllianceDboDao allianceDboDao;

    @Autowired
    private MemberApplyingRecordDao applyingRecordDao;

    @Autowired
    private MemberLianmengDboDao memberLianmengDboDao;

    @Autowired
    private MemberDboDao memberDboDao;

    @Autowired
    private PowerAccountDboDao powerAccountDboDao;

    @Autowired
    private BoxAccountDboDao boxAccountDboDao;

    @Autowired
    private LianmengYushiAccountDboDao lianmengYushiAccountDboDao;

    @Autowired
    private GameHistoricalJuResultDao gameHistoricalJuResultDao;

    @Autowired
    private LianmengYushiAccountingRecordDao lianmengYushiAccountingRecordDao;

    @Autowired
    private ScoreAccountDboDao scoreAccountDboDao;

    @Autowired
    private LianmengDiamondDayCostDao lianmengDiamondDayCostDao;

    @Autowired
    private MemberDayResultDataDao memberDayResultDataDao;


    public List<LianmengCost> queryLianmengCost(String agentId, String lianmengId, String goldSort, String gameSort, long startTime, long endTime) {
        List<LianmengCost> lianmengCostList = new ArrayList<>();
        List<AllianceDbo> allianceDboList = allianceDboDao.findByAgentIdAndLianmengId(agentId, lianmengId);
        for (AllianceDbo allianceDbo : allianceDboList) {
            LianmengCost lianmengCost = new LianmengCost();
            lianmengCost.setLianmengId(allianceDbo.getId());
            lianmengCost.setMengzhuId(allianceDbo.getMengzhu());
            lianmengCost.setMengzhuNickname(allianceDbo.getNickname());
            lianmengCost.setMengzhuHeadimgurl(memberDboDao.findById(allianceDbo.getMengzhu()).getHeadimgurl());
            lianmengCost.setName(allianceDbo.getName());
            List<LianmengDiamondDayCost> allByLianmengId = lianmengDiamondDayCostDao.findAllByLianmengId(allianceDbo.getId(), startTime, endTime);
            int count =0 ;
            for (LianmengDiamondDayCost lianmengDiamondDayCost : allByLianmengId) {
                count+=lianmengDiamondDayCost.getCost();
            }
            List<MemberDayResultData> allByMemberIdAndLianmengIdAndTime = memberDayResultDataDao.findAllByMemberIdAndLianmengIdAndTime(allianceDbo.getMengzhu(), allianceDbo.getId(), startTime, endTime);
            int juCount=0;
            for (MemberDayResultData memberDayResultData : allByMemberIdAndLianmengIdAndTime) {
                juCount+=memberDayResultData.getDayingjiaCount();
            }
            lianmengCost.setJuCost(juCount);
            lianmengCost.setLianmengyushiCost(count);lianmengCostList.add(lianmengCost);
        }
        //按照钻石数降序
        if (goldSort != null) {
            if (goldSort.equals("DESC")) {
                Collections.sort(lianmengCostList, new Comparator<LianmengCost>() {
                    @Override
                    public int compare(LianmengCost o1, LianmengCost o2) {
                        return o2.getLianmengyushiCost() - o1.getLianmengyushiCost();
                    }
                });
            }
            //按照钻石数升序
            if (goldSort.equals("ASC")) {
                Collections.sort(lianmengCostList, new Comparator<LianmengCost>() {
                    @Override
                    public int compare(LianmengCost o1, LianmengCost o2) {
                        return o1.getLianmengyushiCost() - o2.getLianmengyushiCost();
                    }
                });
            }
        }
        if (gameSort != null) {
            //按照局数降序
            if (gameSort.equals("DESC")) {
                Collections.sort(lianmengCostList, new Comparator<LianmengCost>() {
                    @Override
                    public int compare(LianmengCost o1, LianmengCost o2) {
                        return o2.getJuCost() - o1.getJuCost();
                    }
                });
            }
            //按照局数升序
            if (gameSort.equals("ASC")) {
                Collections.sort(lianmengCostList, new Comparator<LianmengCost>() {
                    @Override
                    public int compare(LianmengCost o1, LianmengCost o2) {
                        return o1.getJuCost() - o2.getJuCost();
                    }
                });
            }
        }

        return lianmengCostList;
    }


    public ListPage queryLianmeng(int page, int size, String lianmengId, String memberId) {


        List<LianmengDetail> lianmengDetails = new ArrayList<>();
        List<AllianceDbo> allianceDboList = allianceDboDao.findByLianmengId(lianmengId, memberId);
        int index = (page - 1) * size;
        if (allianceDboList.size() > index) {
            for (; index < page * size && index < allianceDboList.size(); index++) {
                AllianceDbo allianceDbo = allianceDboList.get(index);
                LianmengDetail lianmengDetail = new LianmengDetail();
                MemberDbo memberDbo = memberDboDao.findById(allianceDbo.getMengzhu());
                lianmengDetail.setLianmengId(allianceDbo.getId());
                lianmengDetail.setName(allianceDbo.getName());
                lianmengDetail.setLianmengCount((int) memberLianmengDboDao.countByLianmengId(allianceDbo.getId()));
                int hehuorenCount = 0;
                hehuorenCount += (int) memberLianmengDboDao.countByLianmengIdAndIdentity(allianceDbo.getId(), Identity.HEHUOREN);
                lianmengDetail.setHehuorenCount(hehuorenCount);
                lianmengDetail.setMengzhuId(allianceDbo.getMengzhu());
                lianmengDetail.setNickname(memberDbo.getNickname());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                lianmengDetail.setCreateTime(format.format(new Date(allianceDbo.getCreateTime())));
                lianmengDetails.add(lianmengDetail);
            }
        }
        return new ListPage(lianmengDetails, page, size, allianceDboList.size());
    }

    public void removeAlliance(String lianmengId) {
        allianceDboDao.removeById(lianmengId);
        memberLianmengDboDao.removeByLianmengId(lianmengId);
    }


    public LianmengDetail queryLianmengDetail(String lianmengId) {
        LianmengDetail lianmengDetail = new LianmengDetail();
        int amount = (int) memberLianmengDboDao.countByLianmengIdAndIdentity(lianmengId, Identity.HEHUOREN);

        AllianceDbo allianceDbo = allianceDboDao.findById(lianmengId);
        MemberDbo memberDbo = memberDboDao.findById(allianceDbo.getMengzhu());
        lianmengDetail.setLianmengId(allianceDbo.getId());
        lianmengDetail.setName(allianceDbo.getName());
        lianmengDetail.setLianmengCount((int) memberLianmengDboDao.countByLianmengId(allianceDbo.getId()));
        lianmengDetail.setHehuorenCount(amount);
        lianmengDetail.setMengzhuId(allianceDbo.getMengzhu());
        lianmengDetail.setNickname(memberDbo.getNickname());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        lianmengDetail.setCreateTime(format.format(new Date(allianceDbo.getCreateTime())));
        return lianmengDetail;
    }

    public List<AgentVO> queryMengzhu(String memeberId) {
        List<AgentVO> agentVOList = new ArrayList<>();
        // int amount = (int) memberLianmengDboDao.countByMemberIdAndIdentity1(memeberId, Identity.MENGZHU) ;
        List<MemberLianmengDbo> memberLianmengDboList = memberLianmengDboDao.findByMemberIdAndLianmengIdAndIdentity1(memeberId, null, Identity.MENGZHU);
        for (int i = 0; i < memberLianmengDboList.size(); i++) {
            for (int j = memberLianmengDboList.size() - 1; j > i; j--) {
                if (memberLianmengDboList.get(j).getNickname().equals(memberLianmengDboList.get(i).getNickname())) {
                    memberLianmengDboList.remove(j);
                }
            }
        }
        for (MemberLianmengDbo memberLianmengDbo : memberLianmengDboList) {
            AgentVO agentVO = new AgentVO();
            agentVO.setMemberId(memberLianmengDbo.getMemberId());
            MemberDbo memberDbo = memberDboDao.findById(memberLianmengDbo.getMemberId());
            agentVO.setHeadimgurl(memberDbo.getHeadimgurl());
            agentVO.setNickname(memberDbo.getNickname());
            AllianceDbo allianceDbo = allianceDboDao.findById(memberLianmengDbo.getLianmengId());
            agentVO.setLianmengCount((int) memberLianmengDboDao.countByMemberIdAndIdentity(allianceDbo.getMengzhu(), Identity.MENGZHU));
            LianmengYushiAccountDbo accountDbo = lianmengYushiAccountDboDao.findByAgentId(memberLianmengDbo.getMemberId());
            if (accountDbo != null) {
                agentVO.setZuanshi(accountDbo.getBalance());
            }
            agentVOList.add(agentVO);
        }
        return agentVOList;
    }

    public ListPage queryLianmengHehuoren(int page, int size, String lianmengId) {
        List<LianmengHehuoren> hehuorens = new ArrayList<>();
        int amount = (int) memberLianmengDboDao.countByLianmengIdAndIdentity(lianmengId, Identity.MENGZHU);
        amount += (int) memberLianmengDboDao.countByLianmengIdAndIdentity(lianmengId, Identity.HEHUOREN);

        List<MemberLianmengDbo> memberLianmengDboList = memberLianmengDboDao.findByLianmengIdAndIdentity(page, size, lianmengId, Identity.MENGZHU);
        memberLianmengDboList.addAll(memberLianmengDboDao.findByLianmengIdAndIdentity(page, size, lianmengId, Identity.HEHUOREN));

        for (MemberLianmengDbo memberLianmengDbo : memberLianmengDboList) {
            LianmengHehuoren hehuoren = new LianmengHehuoren();
            MemberDbo memberDbo = memberDboDao.findById(memberLianmengDbo.getMemberId());
            hehuoren.setMemberId(memberDbo.getId());
            hehuoren.setNickname(memberDbo.getNickname());

            hehuoren.setXiajiCount((int) memberLianmengDboDao.countByLianmengIdAndReferer(lianmengId, memberLianmengDbo.getMemberId()));
            hehuoren.setBan(memberLianmengDbo.isBan());
            hehuorens.add(hehuoren);
        }
        return new ListPage(hehuorens, page, size, amount);
    }

    public ListPage queryLianmengMember(int page, int size, String lianmengId) {
        List<LianmengMember> chengyuanList = new ArrayList<>();
        int amount = (int) memberLianmengDboDao.countByLianmengIdAndIdentity(lianmengId, Identity.CHENGYUAN);

        List<MemberLianmengDbo> memberLianmengDboList = memberLianmengDboDao.findByLianmengIdAndIdentity(page, size, lianmengId, Identity.CHENGYUAN);
        for (MemberLianmengDbo memberLianmengDbo : memberLianmengDboList) {
            LianmengMember chengyuan = new LianmengMember();
            MemberDbo memberDbo = memberDboDao.findById(memberLianmengDbo.getMemberId());
            chengyuan.setMemberId(memberDbo.getId());
            chengyuan.setNickname(memberDbo.getNickname());
            if (memberLianmengDbo.getSuperiorMemberId() != null) {
                MemberDbo shangji = memberDboDao.findById(memberLianmengDbo.getSuperiorMemberId());
                chengyuan.setShangji(shangji.getNickname());
                chengyuan.setShangjiId(shangji.getId());
            }
            chengyuan.setBan(memberLianmengDbo.isBan());
            chengyuanList.add(chengyuan);
        }
        return new ListPage(chengyuanList, page, size, amount);
    }

    /**
     * 创建联盟
     */
    public AllianceDbo createLianmeng(CreateLianmengResult result, String name, String info, String mengzhu) {
        MemberDbo memberDbo = memberDboDao.findById(mengzhu);
        AllianceDbo allianceDbo = new AllianceDbo();
        allianceDbo.setId(result.getLianmengId());
        allianceDbo.setName(name);
        allianceDbo.setMengzhu(mengzhu);

        allianceDbo.setNickname(memberDbo.getNickname());


        allianceDbo.setDesc(null);
        allianceDbo.setCreateTime(System.currentTimeMillis());
        allianceDboDao.save(allianceDbo);

        MemberLianmengDbo memberLianmengDbo = new MemberLianmengDbo();
        memberLianmengDbo.setLianmengId(result.getLianmengId());

        memberLianmengDbo.setMemberId(mengzhu);
        memberLianmengDbo.setHeadimgurl(memberDbo.getHeadimgurl());
        memberLianmengDbo.setNickname(memberDbo.getNickname());
        memberLianmengDbo.setSuperiorMemberId(mengzhu);
        memberLianmengDbo.setIdentity(Identity.MENGZHU);
        memberLianmengDbo.setOnlineState("offline");
        memberLianmengDbo.setCreateTime(System.currentTimeMillis());
        memberLianmengDbo.setZhushouId("");
        memberLianmengDboDao.save(memberLianmengDbo);

        PowerAccountDbo powerAccountDbo = new PowerAccountDbo();
        powerAccountDbo.setId(result.getPowerAccountId());
        powerAccountDbo.setMemberId(mengzhu);
        powerAccountDbo.setLianmengId(result.getLianmengId());
        powerAccountDboDao.save(powerAccountDbo);

        BoxAccountDbo boxAccountDbo = new BoxAccountDbo();
        boxAccountDbo.setId(result.getBoxAccountId());
        boxAccountDbo.setMemberId(mengzhu);
        boxAccountDbo.setLianmengId(result.getLianmengId());
        boxAccountDboDao.save(boxAccountDbo);

        ScoreAccountDbo scoreAccountDbo = new ScoreAccountDbo();
        scoreAccountDbo.setId(result.getScoreAccountId());
        scoreAccountDbo.setMemberId(mengzhu);
        scoreAccountDbo.setLianmengId(result.getLianmengId());
        scoreAccountDboDao.save(scoreAccountDbo);
        return allianceDbo;
    }

    /**
     * 创建联盟
     */
    public AllianceDbo createLianmeng(CreateLianmengResult result, String name, String mengzhu) {
        MemberDbo memberDbo = memberDboDao.findById(mengzhu);
        AllianceDbo allianceDbo = new AllianceDbo();
        allianceDbo.setId(result.getLianmengId());
        allianceDbo.setName(name);
        allianceDbo.setMengzhu(mengzhu);

        allianceDbo.setNickname(memberDbo.getNickname());


        allianceDbo.setDesc(null);
        allianceDbo.setCreateTime(System.currentTimeMillis());
        allianceDboDao.save(allianceDbo);

        MemberLianmengDbo memberLianmengDbo = new MemberLianmengDbo();
        memberLianmengDbo.setLianmengId(result.getLianmengId());

        memberLianmengDbo.setMemberId(mengzhu);
        memberLianmengDbo.setHeadimgurl(memberDbo.getHeadimgurl());
        memberLianmengDbo.setNickname(memberDbo.getNickname());
        memberLianmengDbo.setSuperiorMemberId(mengzhu);
        memberLianmengDbo.setIdentity(Identity.MENGZHU);
        memberLianmengDbo.setOnlineState("offline");
        memberLianmengDbo.setCreateTime(System.currentTimeMillis());
        memberLianmengDbo.setZhushouId("");
        memberLianmengDboDao.save(memberLianmengDbo);

        PowerAccountDbo powerAccountDbo = new PowerAccountDbo();
        powerAccountDbo.setId(result.getPowerAccountId());
        powerAccountDbo.setMemberId(mengzhu);
        powerAccountDbo.setLianmengId(result.getLianmengId());
        powerAccountDboDao.save(powerAccountDbo);

        BoxAccountDbo boxAccountDbo = new BoxAccountDbo();
        boxAccountDbo.setId(result.getBoxAccountId());
        boxAccountDbo.setMemberId(mengzhu);
        boxAccountDbo.setLianmengId(result.getLianmengId());
        boxAccountDboDao.save(boxAccountDbo);

        ScoreAccountDbo scoreAccountDbo = new ScoreAccountDbo();
        scoreAccountDbo.setId(result.getScoreAccountId());
        scoreAccountDbo.setMemberId(mengzhu);
        scoreAccountDbo.setLianmengId(result.getLianmengId());
        scoreAccountDboDao.save(scoreAccountDbo);
        return allianceDbo;
    }

    public AllianceDbo findAllianceDboById(String lianmengId) {
        return allianceDboDao.findById(lianmengId);
    }

    /**
     * 联盟成员申请
     */
    public MemberApplyingRecord apply(String memberId, String lianmengId, Identity identity, String inviteMemberId) {
        MemberApplyingRecord apply = new MemberApplyingRecord();
        MemberDbo memberDbo = memberDboDao.findById(memberId);
        MemberDbo memberDbo1 = memberDboDao.findById(inviteMemberId);
        apply.setInviteMemberId(memberDbo1.getId());
        apply.setIdentity(identity);
        apply.setLianmengId(lianmengId);
        apply.setMemberId(memberId);
        apply.setNickname(memberDbo.getNickname());
        apply.setHeadimgurl(memberDbo.getHeadimgurl());
        apply.setInviteMemberNickname(memberDbo1.getNickname());
        apply.setInviteMemberHeadimgurl(memberDbo1.getHeadimgurl());
        apply.setState(ApplyState.APPLYING);
        apply.setCreateTime(System.currentTimeMillis());

        applyingRecordDao.save(apply);
        return apply;
    }


    public MemberApplyingRecord findMemberApplyingRecordByApplyId(String applyId) {
        return applyingRecordDao.findById(applyId);
    }

    public void updateStateAndAuditorById(String applyId, String auditor) {
        MemberDbo auditorMemberDbo = memberDboDao.findById(auditor);
        applyingRecordDao.updateStateAndAuditorById(applyId, auditorMemberDbo, ApplyState.PASS);
    }

    /**
     * 查询未处理的申请
     */
    public ListPage queryApplyingRecord(int page, int size, String lianmengId,long queryTime) {
        int amount = (int) applyingRecordDao.countByStateAndLianmengIdAndIdentity(ApplyState.APPLYING, lianmengId, queryTime);
        List<MemberApplyingRecord> recordList = applyingRecordDao.findByStateAndLianmengIdAndIdentity(page, size, ApplyState.APPLYING, lianmengId, queryTime);
        return new ListPage(recordList, page, size, amount);
    }

    /**
     * 查询已处理的申请
     */
    public ListPage queryApplyRecord(int page, int size, String lianmengId,long queryTime) {
        int amount = (int) applyingRecordDao.countByNotStateAndLianmengId(ApplyState.APPLYING, lianmengId,queryTime);
        List<MemberApplyingRecord> recordList = applyingRecordDao.findByNotStateAndLianmengId(page, size, ApplyState.APPLYING, lianmengId,queryTime);
        return new ListPage(recordList, page, size, amount);
    }


    /**
     * 加入联盟
     */
    public void joinLianmeng(String memberId, String lianmengId, String superiorMemberId, JoinLianmengResult joinLianmengResult) {
        MemberLianmengDbo memberLianmengDbo = new MemberLianmengDbo();
        memberLianmengDbo.setLianmengId(lianmengId);
        memberLianmengDbo.setMemberId(memberId);
        MemberDbo memberDbo = memberDboDao.findById(memberId);
        memberLianmengDbo.setHeadimgurl(memberDbo.getHeadimgurl());
        memberLianmengDbo.setNickname(memberDbo.getNickname());
        memberLianmengDbo.setIdentity(Identity.CHENGYUAN);
        memberLianmengDbo.setSuperiorMemberId(superiorMemberId);
        memberLianmengDbo.setOnlineState("offline");
        memberLianmengDbo.setCreateTime(System.currentTimeMillis());
        memberLianmengDbo.setZhushouId("");
        memberLianmengDboDao.save(memberLianmengDbo);


        PowerAccountDbo powerAccountDbo = new PowerAccountDbo();
        powerAccountDbo.setId(joinLianmengResult.getPowerAccountId());
        powerAccountDbo.setMemberId(memberId);
        powerAccountDbo.setLianmengId(lianmengId);
        powerAccountDboDao.save(powerAccountDbo);

        BoxAccountDbo boxAccountDbo = new BoxAccountDbo();
//        boxAccountDbo.setId(joinLianmengResult.getBoxAccountId());
        boxAccountDbo.setMemberId(memberId);
        boxAccountDbo.setLianmengId(lianmengId);
        boxAccountDboDao.save(boxAccountDbo);

    }

    /**
     * 被移除重新加入联盟
     */
    public void reJoinLianmeng(String memberId, String lianmengId, String superiorMemberId) {
        MemberLianmengDbo memberLianmengDbo = new MemberLianmengDbo();
        memberLianmengDbo.setLianmengId(lianmengId);
        memberLianmengDbo.setMemberId(memberId);
        MemberDbo memberDbo = memberDboDao.findById(memberId);
        memberLianmengDbo.setHeadimgurl(memberDbo.getHeadimgurl());
        memberLianmengDbo.setNickname(memberDbo.getNickname());
        memberLianmengDbo.setIdentity(Identity.CHENGYUAN);
        memberLianmengDbo.setSuperiorMemberId(superiorMemberId);
        memberLianmengDbo.setOnlineState("offline");
        memberLianmengDbo.setCreateTime(System.currentTimeMillis());
        memberLianmengDbo.setZhushouId("");
        memberLianmengDboDao.save(memberLianmengDbo);
    }


    public void updateMemberLianmengDboIdentity(String memberId, String lianmengId, Identity identity
            , String scoreAccountId) {
        memberLianmengDboDao.updateIdentity(memberId, lianmengId, identity);
        ScoreAccountDbo scoreAccountDbo = new ScoreAccountDbo();
        scoreAccountDbo.setId(scoreAccountId);
        scoreAccountDbo.setMemberId(memberId);
        scoreAccountDbo.setLianmengId(lianmengId);
        scoreAccountDboDao.save(scoreAccountDbo);
    }

    public void updateRejoinMemberLianmengDboIdentity(String memberId, String lianmengId, Identity identity) {
        memberLianmengDboDao.updateIdentity(memberId, lianmengId, identity);
    }


    public MemberLianmengDbo findMemberLianmengDboByAgentIdAndLianmengIdAndIdentity(String agentId, String lianmengId, Identity identity) {
        return memberLianmengDboDao.findByAgentIdAndLianmengIdAndIdentity(agentId, lianmengId, identity);
    }

    public MemberLianmengDbo findMemberLianmengDboByMemberIdAndLianmengId(String memberId, String lianmengId) {
        return memberLianmengDboDao.findByMemberIdAndLianmengId(memberId, lianmengId);
    }


    public void updateMemberLianmengDboOnlineState(String memberId, String onlineState) {
        memberLianmengDboDao.updateOnlineState(memberId, onlineState);
    }


    public List<LianmengVO> findGuanliyuanLianmeng(String memberId) {
        List<LianmengVO> lianmengVOList = new ArrayList<>();
        List<MemberLianmengDbo> memberLianmengDboList = memberLianmengDboDao.findByMemberIdAndIdentity(memberId, Identity.MENGZHU);
        memberLianmengDboList.addAll(memberLianmengDboDao.findByMemberIdAndIdentity(memberId, Identity.HEHUOREN));
        for (MemberLianmengDbo memberLianmengDbo : memberLianmengDboList) {
            String lianmengId = memberLianmengDbo.getLianmengId();
            AllianceDbo allianceDbo = allianceDboDao.findById(lianmengId);
            LianmengVO vo = new LianmengVO();
            vo.setId(lianmengId);
            vo.setName(allianceDbo.getName());
            vo.setMemberCount((int) memberLianmengDboDao.countByLianmengId(lianmengId));
            vo.setSelf(memberId.equals(allianceDbo.getMengzhu()));
            lianmengVOList.add(vo);
        }
        return lianmengVOList;
    }

    public List<LianmengVO> findLianmengByMemberId(String memberId) {
        List<LianmengVO> lianmengVOList = new ArrayList<>();
        List<MemberLianmengDbo> memberLianmengDboList = memberLianmengDboDao.findByMemberIdAndIdentity(memberId, null);
        for (MemberLianmengDbo memberLianmengDbo : memberLianmengDboList) {
            String lianmengId = memberLianmengDbo.getLianmengId();
            AllianceDbo allianceDbo = allianceDboDao.findById(lianmengId);
            LianmengVO vo = new LianmengVO();
            vo.setId(lianmengId);
            vo.setLianmengIdHide(allianceDbo.getLianmengIdHide());
            vo.setName(allianceDbo.getName());
            if (allianceDbo.isRenshuHide()){
                vo.setRenshuHide(true);
            }
            vo.setMemberCount((int) memberLianmengDboDao.countByLianmengId(lianmengId));
            vo.setSelf(memberId.equals(allianceDbo.getMengzhu()));
            lianmengVOList.add(vo);
        }
        return lianmengVOList;
    }


    public int countLianmengMemberByLianmengIdAndReferer(String lianmengId, String referer) {
        return (int) memberLianmengDboDao.countByLianmengIdAndReferer(lianmengId, referer);
    }

    public List<LianmengVO> findLianmengByMengzhu(String memberId) {
        List<LianmengVO> lianmengVOList = new ArrayList<>();
        List<AllianceDbo> allianceDboList = allianceDboDao.findByMengzhu(memberId);
        for (AllianceDbo allianceDbo : allianceDboList) {
            String lianmengId = allianceDbo.getId();
            LianmengVO vo = new LianmengVO();
            vo.setId(lianmengId);
            vo.setName(allianceDbo.getName());
            lianmengVOList.add(vo);
        }
        return lianmengVOList;
    }

    public List<MemberLianmengDbo> findMemberByLianmengIdAndIdentity(String lianmengId, Identity identity) {
        List<MemberLianmengDbo> memberLianmengDboList = memberLianmengDboDao.findByLianmengIdAndIdentity(lianmengId, identity);
        return memberLianmengDboList;
    }


    public int countLianmengByMemberIdAndIdentity(String memberId, Identity identity) {
        return (int) memberLianmengDboDao.countByMemberIdAndIdentity(memberId, identity);
    }


    public ListPage findLianmengDboByAgentId(int page, int size, String agentId) {
        List<AllianceVO> list = new ArrayList<>();
        long amount = allianceDboDao.countAmountByAgentId(agentId);
        List<AllianceDbo> allianceDboList = allianceDboDao.findByAgentId(page, size, agentId);
        for (AllianceDbo dbo : allianceDboList) {
            AllianceVO vo = new AllianceVO();
            vo.setId(dbo.getId());
            vo.setDesc(dbo.getDesc());
            vo.setName(dbo.getName());
            vo.setOnlineCount((int) memberLianmengDboDao.countOnlineMemberByLianmengId(dbo.getId()));
            list.add(vo);
        }
        return new ListPage(list, page, size, (int) amount);
    }

    /**
     * 通过申请
     */
    public void passApply(String applyId, String auditor, JoinLianmengResult joinLianmengResult) {
        MemberDbo auditorMemberDbo = memberDboDao.findById(auditor);
        applyingRecordDao.updateStateAndAuditorById(applyId, auditorMemberDbo, ApplyState.PASS);
        MemberApplyingRecord record = applyingRecordDao.findById(applyId);
        MemberLianmengDbo memberLianmengDbo1 = memberLianmengDboDao.findByMemberIdAndLianmengId(record.getMemberId(), record.getLianmengId());
        if (memberLianmengDbo1 == null) {
            MemberLianmengDbo memberLianmengDbo = new MemberLianmengDbo();
            memberLianmengDbo.setLianmengId(record.getLianmengId());
            memberLianmengDbo.setMemberId(record.getMemberId());
            MemberDbo memberDbo = memberDboDao.findById(record.getMemberId());
            memberLianmengDbo.setHeadimgurl(memberDbo.getHeadimgurl());
            memberLianmengDbo.setNickname(memberDbo.getNickname());
            memberLianmengDbo.setIdentity(record.getIdentity());

            memberLianmengDbo.setSuperiorMemberId(record.getInviteMemberId());
            memberLianmengDbo.setOnlineState("offline");
            memberLianmengDbo.setCreateTime(System.currentTimeMillis());
            memberLianmengDbo.setZhushouId("");
            memberLianmengDboDao.save(memberLianmengDbo);

            PowerAccountDbo powerAccountDbo = new PowerAccountDbo();
            powerAccountDbo.setId(joinLianmengResult.getPowerAccountId());
            powerAccountDbo.setMemberId(record.getMemberId());
            powerAccountDbo.setLianmengId(record.getLianmengId());
            powerAccountDboDao.save(powerAccountDbo);

            BoxAccountDbo boxAccountDbo = new BoxAccountDbo();
//            boxAccountDbo.setId(joinLianmengResult.getBoxAccountId());
            boxAccountDbo.setMemberId(record.getMemberId());
            boxAccountDbo.setLianmengId(record.getLianmengId());
            boxAccountDboDao.save(boxAccountDbo);
        }
    }


    /**
     * 拒绝申请
     */
    public void refuseApply(String applyId, String auditor) {
        MemberDbo auditorMemberDbo = memberDboDao.findById(auditor);
        applyingRecordDao.updateStateAndAuditorById(applyId, auditorMemberDbo, ApplyState.REFUSE);
    }


    /**
     * 玩家被踢出联盟后重新申请加入
     */
    public void repassApply(String applyId, String auditor) {
        MemberDbo auditorMemberDbo = memberDboDao.findById(auditor);
        applyingRecordDao.updateStateAndAuditorById(applyId, auditorMemberDbo, ApplyState.PASS);
        MemberApplyingRecord record = applyingRecordDao.findById(applyId);
        if (record != null) {
            MemberLianmengDbo memberLianmengDbo = new MemberLianmengDbo();
            memberLianmengDbo.setLianmengId(record.getLianmengId());
            memberLianmengDbo.setMemberId(record.getMemberId());
            MemberDbo memberDbo = memberDboDao.findById(record.getMemberId());
            memberLianmengDbo.setHeadimgurl(memberDbo.getHeadimgurl());
            memberLianmengDbo.setNickname(memberDbo.getNickname());
            memberLianmengDbo.setIdentity(record.getIdentity());
            memberLianmengDbo.setSuperiorMemberId(record.getInviteMemberId());
            memberLianmengDbo.setOnlineState("offline");
            memberLianmengDbo.setCreateTime(System.currentTimeMillis());
            memberLianmengDbo.setZhushouId("");
            memberLianmengDboDao.save(memberLianmengDbo);
        }
    }

    public void updateDesc(String lianmengId, String desc) {
        allianceDboDao.updateDesc(lianmengId, desc);
    }

    public ListPage querylianmengBymengzhu(int page, int size, String mengzhuId) {
        List<LianmengDetail> lianmengDetails = new ArrayList<>();
        List<AllianceDbo> allianceDboList = allianceDboDao.findByMengzhu(mengzhuId);
        int index = (page - 1) * size;
        if (allianceDboList.size() > index) {
            for (; index < page * size && index < allianceDboList.size(); index++) {
                AllianceDbo allianceDbo = allianceDboList.get(index);
                LianmengDetail lianmengDetail = new LianmengDetail();
                MemberDbo memberDbo = memberDboDao.findById(allianceDbo.getMengzhu());
                lianmengDetail.setLianmengId(allianceDbo.getId());
                lianmengDetail.setName(allianceDbo.getName());
                lianmengDetail.setLianmengCount((int) memberLianmengDboDao.countByLianmengId(allianceDbo.getId()));
                int hehuorenCount = 1;
                hehuorenCount += (int) memberLianmengDboDao.countByLianmengIdAndIdentity(allianceDbo.getId(), Identity.HEHUOREN);
                hehuorenCount += (int) memberLianmengDboDao.countByLianmengIdAndIdentity(allianceDbo.getId(), Identity.CHENGYUAN);
                lianmengDetail.setHehuorenCount(hehuorenCount);
                lianmengDetail.setMengzhuId(allianceDbo.getMengzhu());
                lianmengDetail.setNickname(memberDbo.getNickname());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                lianmengDetail.setCreateTime(format.format(new Date(allianceDbo.getCreateTime())));
                lianmengDetails.add(lianmengDetail);
            }
        }
        return new ListPage(lianmengDetails, page, size, allianceDboList.size());
    }

    public List<MemberApplyingRecord> findByMemberIdAndLianmengAndIdentity(String memberId, String lianmengId) {
        return applyingRecordDao.findByMemberIdAndLianmengAndIdentity(memberId, lianmengId);
    }


    public void updateSetting(String lianmengId, boolean renshuHide, boolean kongzhuoqianzhi,
                              boolean nicknameHide, boolean idHide, boolean banAlliance, boolean zhuomanHide,
                              int buzhunbeituichushichang, boolean zidongzhunbei, boolean lianmengIdHide) {
        allianceDboDao.updateSetting(lianmengId, renshuHide, kongzhuoqianzhi, nicknameHide, idHide, banAlliance,
                zhuomanHide, buzhunbeituichushichang, zidongzhunbei, lianmengIdHide);
    }

}
