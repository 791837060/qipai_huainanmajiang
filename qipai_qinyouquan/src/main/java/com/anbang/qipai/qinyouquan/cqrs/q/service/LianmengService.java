package com.anbang.qipai.qinyouquan.cqrs.q.service;

import com.anbang.qipai.qinyouquan.cqrs.c.domain.CreateLianmengResult;
import com.anbang.qipai.qinyouquan.cqrs.c.domain.JoinLianmengResult;
import com.anbang.qipai.qinyouquan.plan.bean.MemberDayResultData;
import com.anbang.qipai.qinyouquan.plan.bean.game.Game;
import com.anbang.qipai.qinyouquan.plan.bean.game.GameTable;
import com.anbang.qipai.qinyouquan.plan.bean.result.GameHistoricalJuResult;
import com.anbang.qipai.qinyouquan.plan.dao.GameHistoricalJuResultDao;
import com.anbang.qipai.qinyouquan.cqrs.q.dao.*;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.*;
import com.anbang.qipai.qinyouquan.plan.dao.MemberDayResultDataDao;
import com.anbang.qipai.qinyouquan.web.vo.*;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    private MemberDiamondAccountDboDao memberDiamondAccountDboDao;

    @Autowired
    private LianmengDiamondAccountDboDao lianmengDiamondAccountDboDao;

    @Autowired
    private GameHistoricalJuResultDao gameHistoricalJuResultDao;

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
            MemberDayResultData memberDayResultData = memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(allianceDbo.getMengzhu(), allianceDbo.getId(), startTime, endTime);
            if (memberDayResultData!=null){
                lianmengCost.setLianmengyushiCost(memberDayResultData.getDiamondCost());
                lianmengCost.setJuCost(memberDayResultData.getDayingjiaCount());
            }
            lianmengCostList.add(lianmengCost);
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
            LianmengDiamondAccountDbo accountDbo = lianmengDiamondAccountDboDao.findByAgentId(memberLianmengDbo.getMemberId());
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
    public AllianceDbo createLianmeng(CreateLianmengResult result, String name, String mengzhu) {
        MemberDbo memberDbo = memberDboDao.findById(mengzhu);
        AllianceDbo allianceDbo = new AllianceDbo();
        allianceDbo.setId(result.getLianmengId());
        allianceDbo.setName(name);
        allianceDbo.setMengzhu(mengzhu);
        allianceDbo.setHeadimgurl(memberDbo.getHeadimgurl());

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


        MemberDiamondAccountDbo memberDiamondAccountDbo = new MemberDiamondAccountDbo();
        memberDiamondAccountDbo.setId(joinLianmengResult.getDiamondAccountId());
        memberDiamondAccountDbo.setMemberId(memberId);
        memberDiamondAccountDbo.setLianmengId(lianmengId);
        memberDiamondAccountDboDao.save(memberDiamondAccountDbo);

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


    public void updateMemberLianmengDboIdentity(String memberId, String lianmengId, Identity identity) {
        memberLianmengDboDao.updateIdentity(memberId, lianmengId, identity);
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
            vo.setHeadimgurl(allianceDbo.getHeadimgurl());
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

            MemberDiamondAccountDbo memberDiamondAccountDbo = new MemberDiamondAccountDbo();
            memberDiamondAccountDbo.setId(joinLianmengResult.getDiamondAccountId());
            memberDiamondAccountDbo.setMemberId(record.getMemberId());
            memberDiamondAccountDbo.setLianmengId(record.getLianmengId());
            memberDiamondAccountDboDao.save(memberDiamondAccountDbo);


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

    public List<AllianceDbo> findByMengzhuId(String mengzhuId){
        return allianceDboDao.findByAgentIdAndLianmengId(mengzhuId, null);
    }

    //后台——砖石消耗——详情
    public ListPage queryLianmengGoldCost(String lianmengId,int page,int size,Long startTime, Long endTime){
        List<LianmengmemberCost> lianmengmemberCostList = new ArrayList<>();
        Game[] games = Game.values();
        for (Game game : games) {
            LianmengmemberCost lianmengCost = new LianmengmemberCost();
            int diamondCosts = 0;
            List<GameHistoricalJuResult> gameHistoricalJuResultGameList = gameHistoricalJuResultDao.queryLianmengGoldCost(lianmengId,game,startTime,endTime);
            if (gameHistoricalJuResultGameList.size()!=0){
                for (int i=0 ; i< gameHistoricalJuResultGameList.size() ;i++){
                    int amount = gameHistoricalJuResultGameList.size();
                    int diamondCost = gameHistoricalJuResultGameList.get(i).getDiamondCost();
                    diamondCosts += diamondCost;
                    if (i == gameHistoricalJuResultGameList.size()-1){
                        lianmengCost.setAmount(amount);
                        lianmengCost.setDiamondCosts(diamondCosts);
                        lianmengCost.setGame(game);
                    }
                }
                lianmengmemberCostList.add(lianmengCost);
            }
        }
        return new ListPage(lianmengmemberCostList,page,size,1);
    }

    public ListPage queryLianmengHehuorens(int page, int size, String lianmengId) {
        List<LianmengHehuoren> hehuorens = new ArrayList<>();
        int amount = (int) memberLianmengDboDao.countByLianmengIdAndIdentity(lianmengId, Identity.MENGZHU,0);
        amount += (int) memberLianmengDboDao.countByLianmengIdAndIdentity(lianmengId, Identity.HEHUOREN,0);
        int xiajiaCount = 0;
        List<MemberLianmengDbo> memberLianmengDboList = memberLianmengDboDao.findByLianmengIdAndIdentity(page, size, lianmengId, Identity.MENGZHU);
        memberLianmengDboList.addAll(memberLianmengDboDao.findByLianmengIdAndIdentity(page, size, lianmengId, Identity.HEHUOREN));
        for (MemberLianmengDbo memberLianmengDbo : memberLianmengDboList) {
            LianmengHehuoren hehuoren = new LianmengHehuoren();
            MemberDbo memberDbo = memberDboDao.findById(memberLianmengDbo.getMemberId());
            xiajiaCount = (int) memberLianmengDboDao.getxiajiCountByLianmengIdAndIdentity(lianmengId,memberLianmengDbo.getSuperiorMemberId());
            hehuoren.setChengyuanCount(xiajiaCount);
            hehuoren.setMemberId(memberDbo.getId());
            hehuoren.setNickname(memberDbo.getNickname());
            hehuoren.setXiajiCount((int) memberLianmengDboDao.countByLianmengIdAndReferer(lianmengId, memberLianmengDbo.getMemberId()));
            hehuoren.setBan(memberLianmengDbo.isBan());
            int juCount = (int) gameHistoricalJuResultDao.getAmountByLianmengIdAndMemberIdAndTime(null, lianmengId, memberDbo.getId(), 0, 0);//对局总数
            LianmengDiamondAccountDbo accountDbo = lianmengDiamondAccountDboDao.findByAgentId(memberLianmengDbo.getMemberId());
            if (accountDbo != null) {
                hehuoren.setBalance(accountDbo.getBalance());//砖石余额
            }
            hehuoren.setJuCount(juCount);
            long endTime= System.currentTimeMillis();               //当前时间
            long threeStartTime= endTime - (3*3600000 * 24);        //三日前
            long servenStartTime = endTime - (7*3600000 * 24);      //七天前
            hehuoren.setThreeDayJuCount((int) gameHistoricalJuResultDao.getAmountByLianmengIdAndMemberIdAndTime(null, lianmengId, memberDbo.getId(), threeStartTime, endTime));//三日对局总数
            hehuoren.setSevenDayJuCount((int) gameHistoricalJuResultDao.getAmountByLianmengIdAndMemberIdAndTime(null, lianmengId, memberDbo.getId(), servenStartTime, endTime));//七日对局总数
            hehuorens.add(hehuoren);
        }
        return new ListPage(hehuorens, page, size, amount);
    }

    public ListPage queryLianmengMembers(int page, int size, String lianmengId) {
        List<LianmengMember> chengyuanList = new ArrayList<>();
        int amount = (int) memberLianmengDboDao.countByLianmengIdAndIdentity(lianmengId, Identity.CHENGYUAN,0);
        List<MemberLianmengDbo> memberLianmengDboList = memberLianmengDboDao.findByLianmengIdAndIdentity(page, size, lianmengId, Identity.CHENGYUAN);
        for (MemberLianmengDbo memberLianmengDbo : memberLianmengDboList) {
            LianmengMember chengyuan = new LianmengMember();
            MemberDbo memberDbo = memberDboDao.findById(memberLianmengDbo.getMemberId());
            chengyuan.setMemberId(memberDbo.getId());
            chengyuan.setNickname(memberDbo.getNickname());
            if (memberLianmengDbo.getSuperiorMemberId() != null) {
                MemberDbo shangji = memberDboDao.findById(memberLianmengDbo.getMemberId());
                chengyuan.setShangji(shangji.getNickname());
                chengyuan.setShangjiId(shangji.getId());
            }
            chengyuan.setBan(memberLianmengDbo.isBan());
            MemberDiamondAccountDbo accountDbo = lianmengDiamondAccountDboDao.findByMemberId(memberLianmengDbo.getMemberId());
            if (accountDbo != null) {
                chengyuan.setBalance(accountDbo.getBalance());//砖石余额
            }
            int juCount = (int) gameHistoricalJuResultDao.getAmountByLianmengIdAndMemberIdAndTime(null, lianmengId, memberDbo.getId(), 0, 0);//对局总数
            chengyuan.setJuCount(juCount);
            long endTime= System.currentTimeMillis();
            long threeStartTime= endTime - (3*3600000 * 24);
            long servenStartTime = endTime - (7*3600000 * 24);
            chengyuan.setThreeDayJuCount((int) gameHistoricalJuResultDao.getAmountByLianmengIdAndMemberIdAndTime(null, lianmengId, memberDbo.getId(), threeStartTime, endTime));//三日对局总数
            chengyuan.setSevenDayJuCount((int) gameHistoricalJuResultDao.getAmountByLianmengIdAndMemberIdAndTime(null, lianmengId, memberDbo.getId(), servenStartTime, endTime));//七日对局总数
            chengyuanList.add(chengyuan);
        }
        return new ListPage(chengyuanList, page, size, amount);
    }

    public ListPage queryLianmengJuResult(int page,int size, String lianmengId, String playerId, long startTime, long endTime) {
        List<LianmengMember> chengyuanList = new ArrayList<>();
        int amount = (int) memberLianmengDboDao.countByLianmengId(lianmengId);
        List<MemberLianmengDbo> memberLianmengDboList = memberLianmengDboDao.findByLianmengsIdAndIdentity(page, size,playerId, lianmengId, Identity.CHENGYUAN);
        for (MemberLianmengDbo memberLianmengDbo : memberLianmengDboList) {
            LianmengMember chengyuan = new LianmengMember();
            MemberDbo memberDbo = memberDboDao.findById(memberLianmengDbo.getMemberId());
            chengyuan.setMemberId(memberDbo.getId());//玩家id
            chengyuan.setNickname(memberDbo.getNickname());//玩家昵称
            //当日对局总数
            chengyuan.setDayJuCount((int) gameHistoricalJuResultDao.getAmountByLianmengIdAndMemberIdAndTime(null, lianmengId, memberDbo.getId(), startTime, endTime));
            //大赢家数
            MemberDayResultData byMemberIdAndLianmengIdAndThreeTime = memberDayResultDataDao.findByMembersIdAndLianmengIdAndTime(memberDbo.getId(),playerId,lianmengId,startTime,endTime);
            chengyuan.setDayingjiaCount(byMemberIdAndLianmengIdAndThreeTime.getDayingjiaCount());
            //砖石消耗
            chengyuan.setLianmengmembercost(byMemberIdAndLianmengIdAndThreeTime.getDiamondCost());
            chengyuanList.add(chengyuan);
        }
        return new ListPage(chengyuanList, page, size, amount);
    }


}
