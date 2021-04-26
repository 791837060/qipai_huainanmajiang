package com.anbang.qipai.qinyouquan.cqrs.q.service;



import com.anbang.qipai.qinyouquan.plan.bean.MemberDayResultData;
import com.anbang.qipai.qinyouquan.plan.bean.game.GameTable;
import com.anbang.qipai.qinyouquan.plan.dao.GameMemberTableDao;
import com.anbang.qipai.qinyouquan.plan.dao.MemberDayResultDataDao;
import com.anbang.qipai.qinyouquan.util.TimeUtil;
import com.anbang.qipai.qinyouquan.web.vo.LianmengMemberVO;
import com.anbang.qipai.qinyouquan.cqrs.q.dao.*;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.BanDeskMate;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.Identity;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.MemberDbo;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.MemberLianmengDbo;
import com.highto.framework.web.page.ListPage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LianmengMemberService {

    @Autowired
    private MemberDboDao memberDboDao;

    @Autowired
    private MemberLianmengDboDao memberLianmengDboDao;

    @Autowired
    private BanDeskMateDao banDeskMateDao;

    @Autowired
    private MemberDayResultDataDao memberDayResultDataDao;

    @Autowired
    private MemberDiamondAccountDboDao memberDiamondAccountDboDao;

    @Autowired
    private GameMemberTableDao gameMemberTableDao;

    @Autowired
    private MemberDiamondAccountingRecordDao memberDiamondAccountingRecordDao;

    @Autowired
    private LianmengDiamondAccountDboDao lianmengDiamondAccountDboDao;

    public Map queryMemberByMemberIdAndLianmengIdAndSuperior(String memberId, String lianmengId, long queryTime) {
        long startTime = TimeUtil.getDayStartTime(queryTime);
        Map data = new HashMap();
        MemberLianmengDbo memberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(memberId, lianmengId);
        MemberDayResultData memberDayResultData = memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(), lianmengId, startTime, queryTime);
        if (memberDayResultData == null) {
            data.put("totalJuCount", 0);
            data.put("totalDayingjiaCount", 0);
            data.put("totalDiamond", 0);
            data.put("totalPowerCost", 0);
            data.put("totalPower", 0);
            data.put("memberCount",0);
            return data;
        }
        data.put("totalJuCount", memberDayResultData.getErrenJuCount() + memberDayResultData.getSanrenJuCount() + memberDayResultData.getSirenJuCount()+memberDayResultData.getDuorenJuCount());
        data.put("totalDayingjiaCount", memberDayResultData.getErrenDayingjiaCount() + memberDayResultData.getSanrenDayingjiaCount() + memberDayResultData.getSirenDayingjiaCount() + memberDayResultData.getDuorenDayingjiaCount());
        data.put("totalDiamond", memberDayResultData.getDiamond());
        data.put("totalDiamondCost", memberDayResultData.getDiamondCost());
        data.put("totalScore",memberDayResultData.getTotalScore());
        if (memberLianmengDbo.getIdentity().equals(Identity.MENGZHU)){
            data.put("memberCount",memberLianmengDboDao.countrenshu(lianmengId, queryTime));
        }else if (memberLianmengDbo.getIdentity().equals(Identity.HEHUOREN)){
            data.put("memberCount",memberLianmengDboDao.getAmountByMemberIdAndLianmengIdAndSuperior(lianmengId,memberLianmengDbo.getMemberId(),queryTime)+1);
        }else if (memberLianmengDbo.getIdentity().equals(Identity.CHENGYUAN)){
            data.put("memberCount",1);
        }
        String onlineState=memberLianmengDbo.getOnlineState();
        if (onlineState.equals("online")){
            if (gameMemberTableDao.findByMemberId(memberLianmengDbo.getMemberId())!=null) {
                memberLianmengDbo.setOnlineState("inPlaying");
            }
        }
        LianmengMemberVO lianmengMemberVO = new LianmengMemberVO(memberLianmengDbo,memberDayResultData,onlineState,true);
        if (lianmengMemberVO.getIdentity().equals(Identity.MENGZHU)) {
            lianmengMemberVO.setDiamond(lianmengDiamondAccountDboDao.findByAgentId(memberId).getBalance());
        }
        data.put("lianmengMemberVO", lianmengMemberVO);
        return data;

    }

    public ListPage queryMemberByMemberIdAndLianmengIdAndSuperior(int page, int size, String memberId, String lianmengId, long queryTime) {
        long startTime = TimeUtil.getDayStartTime(queryTime);
        MemberLianmengDbo memberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(memberId, lianmengId);
        List<MemberLianmengDbo> memberLianmengDboList = new ArrayList<>();
        int count = 0;
        if (memberLianmengDbo.getIdentity().equals(Identity.MENGZHU)) {
            count = (int) memberLianmengDboDao.getAmountByMemberIdAndLianmengIdAndSuperior(lianmengId,memberId,queryTime)-1;
            int hehuorenCount = (int)memberLianmengDboDao.countByLianmengIdAndIdentity(lianmengId, Identity.HEHUOREN,queryTime);
            int zhushouCount = (int)memberLianmengDboDao.countByLianmengIdAndZhushouId(lianmengId,memberId,queryTime);
            memberLianmengDboList.addAll(memberLianmengDboDao.findByLianmengIdAndZhushouId(page,size,lianmengId,memberId,queryTime));
            memberLianmengDboList.addAll(memberLianmengDboDao.findByLianmengIdAndSuperiorMemberIdAndIdentity(memberLianmengDboList.size()==0?(page-1)*size-zhushouCount:0,size-memberLianmengDboList.size(),lianmengId,memberId,Identity.HEHUOREN,queryTime));
            memberLianmengDboList.addAll(memberLianmengDboDao.findByLianmengIdAndSuperiorMemberIdAndIdentity(memberLianmengDboList.size()==0?(page-1)*size-zhushouCount-hehuorenCount:0,size-memberLianmengDboList.size(),lianmengId,memberId,Identity.CHENGYUAN,queryTime));
        }else if(memberLianmengDbo.getIdentity().equals(Identity.HEHUOREN)){
            count = (int) memberLianmengDboDao.getAmountByMemberIdAndLianmengIdAndSuperior(lianmengId,memberId,queryTime);
            int zhushouCount = (int)memberLianmengDboDao.countByLianmengIdAndZhushouId(lianmengId,memberId,queryTime);
            memberLianmengDboList.addAll(memberLianmengDboDao.findByLianmengIdAndZhushouId(page,size,lianmengId,memberId,queryTime));
            memberLianmengDboList.addAll(memberLianmengDboDao.findByLianmengIdAndSuperiorMemberIdAndIdentity(memberLianmengDboList.size()==0?(page-1)*size-zhushouCount:0,size-memberLianmengDboList.size(),lianmengId,memberId,Identity.CHENGYUAN,queryTime));
        }else if (memberLianmengDbo.getIdentity().equals(Identity.CHENGYUAN)){
            count = 0;
        }
        List<LianmengMemberVO> lianmengMemberVOList=new ArrayList<>();
        for (MemberLianmengDbo lianmengDbo : memberLianmengDboList) {
            LianmengMemberVO vo = new LianmengMemberVO();
            vo.setMemberId(lianmengDbo.getMemberId());
            vo.setNickname(lianmengDbo.getNickname());
            vo.setHeadimgurl(lianmengDbo.getHeadimgurl());
            vo.setSuperiorMemberId(lianmengDbo.getSuperiorMemberId());
            vo.setZhushouId(lianmengDbo.getZhushouId());
            vo.setIdentity(lianmengDbo.getIdentity());
            vo.setOnlineState(lianmengDbo.getOnlineState());
            if (vo.getOnlineState().equals("online")){
                if (gameMemberTableDao.findByMemberId(lianmengDbo.getMemberId())!=null) {
                    vo.setOnlineState("inPlaying");
                }
            }
            vo.setBan(lianmengDbo.isBan());
            vo.setFree(lianmengDbo.isFree());
            vo.setMaxScore(lianmengDbo.getMaxScore());
            vo.setMinScore(lianmengDbo.getMinScore());
            MemberDayResultData memberDayResultData = memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(lianmengDbo.getMemberId(), lianmengId, startTime, queryTime);
            if (memberDayResultData != null) {
                vo.setDiamondCost(memberDayResultData.getDiamondCost());
                vo.setJuCount(memberDayResultData.getErrenJuCount() + memberDayResultData.getSanrenJuCount() + memberDayResultData.getSirenJuCount() + memberDayResultData.getDuorenJuCount());
                vo.setErJuCount(memberDayResultData.getErrenJuCount());
                vo.setSanJuCount(memberDayResultData.getSanrenJuCount());
                vo.setSiJuCount(memberDayResultData.getSirenJuCount());
                vo.setDuoJuCount(memberDayResultData.getDuorenJuCount());
                vo.setDayingjiaCount(memberDayResultData.getErrenDayingjiaCount() + memberDayResultData.getSanrenDayingjiaCount() + memberDayResultData.getSirenDayingjiaCount() + memberDayResultData.getDuorenDayingjiaCount());
                vo.setErDayingjiaCount(memberDayResultData.getErrenDayingjiaCount());
                vo.setSanDayingjiaCount(memberDayResultData.getSanrenDayingjiaCount());
                vo.setSiDayingjiaCount(memberDayResultData.getSirenDayingjiaCount());
                vo.setDuoDayingjiaCount(memberDayResultData.getDuorenDayingjiaCount());
                vo.setDiamond(memberDayResultData.getDiamond());
                vo.setTotalScore(memberDayResultData.getTotalScore());
            } else {
                vo.setJuCount(0);
                vo.setDayingjiaCount(0);
            }
            lianmengMemberVOList.add(vo);
        }

        return new ListPage(lianmengMemberVOList,page,size,count);
    }

    public ListPage queryMemberByMemberIdAndLianmengIdAndSuperior(int page, int size, String memberId, String lianmengId, long queryTime,
                                                                  String diamondCostSort,  String juCountSort,String zhanjiCountSort,
                                                                  String dayingjiaCountSort,   String onlineSort, String diamondSort) {
        long startTime = TimeUtil.getDayStartTime(queryTime);

        List<LianmengMemberVO> vos = new ArrayList<>();
        if (onlineSort!=null){
            List<MemberLianmengDbo> memberLianmengDboList = memberLianmengDboDao.findByMemberIdAndLianmengIdAndSuperior(page, size, lianmengId, memberId, onlineSort);
            for (MemberLianmengDbo memberLianmengDbo : memberLianmengDboList) {
                if (memberLianmengDbo.getIdentity().equals(Identity.MENGZHU)) {
                    continue;
                }
                LianmengMemberVO vo = new LianmengMemberVO();
                vo.setMemberId(memberLianmengDbo.getMemberId());
                vo.setNickname(memberLianmengDbo.getNickname());
                vo.setHeadimgurl(memberLianmengDbo.getHeadimgurl());
                vo.setSuperiorMemberId(memberLianmengDbo.getSuperiorMemberId());
                vo.setZhushouId(memberLianmengDbo.getZhushouId());
                vo.setIdentity(memberLianmengDbo.getIdentity());
                vo.setOnlineState(memberLianmengDbo.getOnlineState());
                if (vo.getOnlineState().equals("online")){
                    if (gameMemberTableDao.findByMemberId(memberLianmengDbo.getMemberId())!=null) {
                        vo.setOnlineState("inPlaying");
                    }
                }
                vo.setBan(memberLianmengDbo.isBan());
                vo.setFree(memberLianmengDbo.isFree());
                vo.setMaxScore(memberLianmengDbo.getMaxScore());
                vo.setMinScore(memberLianmengDbo.getMinScore());
                MemberDayResultData memberDayResultData = memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(memberLianmengDbo.getMemberId(), lianmengId, startTime, queryTime);
                if (memberDayResultData != null) {
                    vo.setDiamondCost(memberDayResultData.getDiamondCost());
                    vo.setJuCount(memberDayResultData.getErrenJuCount() + memberDayResultData.getSanrenJuCount() + memberDayResultData.getSirenJuCount()+memberDayResultData.getDuorenJuCount());
                    vo.setErJuCount(memberDayResultData.getErrenJuCount());
                    vo.setSanJuCount(memberDayResultData.getSanrenJuCount());
                    vo.setSiJuCount(memberDayResultData.getSirenJuCount());
                    vo.setDuoJuCount(memberDayResultData.getDuorenJuCount());
                    vo.setDayingjiaCount(memberDayResultData.getErrenDayingjiaCount() + memberDayResultData.getSanrenDayingjiaCount() + memberDayResultData.getSirenDayingjiaCount()+memberDayResultData.getDuorenDayingjiaCount());
                    vo.setErDayingjiaCount(memberDayResultData.getErrenDayingjiaCount());
                    vo.setSanDayingjiaCount(memberDayResultData.getSanrenDayingjiaCount());
                    vo.setSiDayingjiaCount(memberDayResultData.getSirenDayingjiaCount());
                    vo.setDuoDayingjiaCount(memberDayResultData.getDuorenDayingjiaCount());
                    vo.setDiamond(memberDayResultData.getDiamond());
                    vo.setTotalScore(memberDayResultData.getTotalScore());
                } else {
                    vo.setJuCount(0);
                    vo.setDayingjiaCount(0);
                }
                vos.add(vo);
            }
            return new ListPage(vos, page, size, vos.size() + 1);
        }else {
            List<String> memberIds = memberLianmengDboDao.listIdsByLianmengIdAndSuperiorMemberId(lianmengId, memberId);
            memberIds.remove(memberId);
            List<MemberDayResultData> byMemberIdAndLianmengIdAndTime = memberDayResultDataDao.findByMemberIdAndLianmengIdAndTime(page, size, memberIds, lianmengId, startTime, queryTime
                    , diamondCostSort, juCountSort, zhanjiCountSort, dayingjiaCountSort, diamondSort);
            long count = memberDayResultDataDao.countByMemberIdAndLianmengIdAndTime(memberIds, lianmengId, startTime, queryTime
                    , diamondCostSort, juCountSort, zhanjiCountSort, dayingjiaCountSort, diamondSort);
            List<LianmengMemberVO> memberVOS=new ArrayList<>();
            for (MemberDayResultData memberDayResultData : byMemberIdAndLianmengIdAndTime) {
                MemberLianmengDbo memberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(memberDayResultData.getMemberId(), memberDayResultData.getLianmengId());
                String onlineState=memberLianmengDbo.getOnlineState();
                if (onlineState.equals("online")){
                    if (gameMemberTableDao.findByMemberId(memberLianmengDbo.getMemberId())!=null) {
                        memberLianmengDbo.setOnlineState("inPlaying");
                    }
                }
                LianmengMemberVO lianmengMemberVO = new LianmengMemberVO(memberLianmengDbo,memberDayResultData,onlineState,false);
                memberVOS.add(lianmengMemberVO);
            }
            return new ListPage(memberVOS, page, size, (int) count);
        }


    }



    public long countOnlineMemberByLianmengId(String lianmengId) {
        return memberLianmengDboDao.countOnlineMemberByLianmengId(lianmengId);
    }

    public MemberLianmengDbo findByMemberIdAndLianmengId(String memberId, String lianmengId) {
        return memberLianmengDboDao.findByMemberIdAndLianmengId(memberId, lianmengId);
    }

    public void updateMemberLianmengDboSuperiorMember(String memberId, String lianmengId, String superiorMemberId) {
        memberLianmengDboDao.updateSuperiorMember(memberId, lianmengId, superiorMemberId);
    }

    public void updateMemberLianmengDboContributionProportion(String memberId, String lianmengId, int contributionProportion) {
        memberLianmengDboDao.updateContributionProportion(memberId, lianmengId, contributionProportion);
    }

    public void updateZhushouId(String memberId, String lianmengId, String zhushouId) {
        memberLianmengDboDao.updateZhushouId(memberId, lianmengId, zhushouId);
    }

    public void updateFree(String memberId, String lianmengId, boolean free) {
        memberLianmengDboDao.updateFree(memberId, lianmengId, free);
    }

    public void updateMemberLianmengDboIdentity(String memberId, String lianmengId, Identity identity) {
        memberLianmengDboDao.updateIdentity(memberId, lianmengId, identity);
    }

    public void updateDayScoreLimit(String memberId, String lianmengId, int maxScore,int minScore) {
        memberLianmengDboDao.updateDayScoreLimit(memberId, lianmengId, maxScore,minScore);
    }

    public void banMemberLianmengDbo(String memberId, String lianmengId) {
        memberLianmengDboDao.updateBanByMemberIdAndLianmengId(memberId, lianmengId, true);
    }

    public void banMemberLianmengDboByReferer(String referer, String lianmengId) {
        memberLianmengDboDao.updateBanByRefererAndLianmengId(referer, lianmengId, true);
    }

    public void releaseMemberLianmengDbo(String memberId, String lianmengId) {
        memberLianmengDboDao.updateBanByMemberIdAndLianmengId(memberId, lianmengId, false);
    }

    public void releaseMemberLianmengDboByReferer(String referer, String lianmengId) {
        memberLianmengDboDao.updateBanByRefererAndLianmengId(referer, lianmengId, false);
    }

    public void removeMemberLianmengDbo(String memberId, String lianmengId) {
        memberLianmengDboDao.removeByMemberIdAndLianmengId(memberId, lianmengId);
    }

    public void removeMemberLianmengDboByReferer(String referer, String lianmengId) {
        memberLianmengDboDao.removeByRefererAndLianmengId(referer, lianmengId);
    }

    public void updateSuperiorMemberIdAndIdentity(String updateSuperiorMemberId, String lianmengId, String memberId) {
        memberLianmengDboDao.updateSuperiorMemberIdAndIdentity(updateSuperiorMemberId, lianmengId, memberId);
    }



    public void addHehuorenBanDeskMate(String banMemberId, boolean suoyouxiaji, String operateId,String lianmengId) {
        BanDeskMate banDeskMate = new BanDeskMate();
        banDeskMate.setOperateId(operateId);
        banDeskMate.setLianmengId(lianmengId);
        banDeskMate.setHehuorenId(banMemberId);
        banDeskMate.setSuoyouxiaji(suoyouxiaji);
        banDeskMate.setCreateTime(System.currentTimeMillis());
        banDeskMateDao.save(banDeskMate);
    }

    public void addBanDeskMate(String memberAId,String memberBId, String operateId,String lianmengId) {
        BanDeskMate banDeskMate = new BanDeskMate();
        banDeskMate.setOperateId(operateId);
        banDeskMate.setLianmengId(lianmengId);
        banDeskMate.setMemberAId(memberAId);
        banDeskMate.setMemberBId(memberBId);
        banDeskMate.setCreateTime(System.currentTimeMillis());
        banDeskMateDao.save(banDeskMate);
    }

    public void removeBanDeskMate(String banId,String lianmengId){
        banDeskMateDao.remove(banId,lianmengId);
    }

    public ListPage queryBanDeskMate(int page,int size ,String memberId ,String lianmengId,long queryTime){
        List<Map<String,Object>> vos = new ArrayList<>();
        int amount;
        List<BanDeskMate> banDeskMateList;
        if (memberLianmengDboDao.findByMemberIdAndLianmengId(memberId,lianmengId).getIdentity().equals(Identity.MENGZHU)) {
            amount = (int) banDeskMateDao.countByLianmengIdAndMemberId(lianmengId,null,queryTime);
            banDeskMateList = banDeskMateDao.findByLianmengIdAndMemberId(page,size,lianmengId,null,queryTime);
        }else {
            amount = (int) banDeskMateDao.countByLianmengIdAndMemberId(lianmengId,memberId,queryTime);
            banDeskMateList = banDeskMateDao.findByLianmengIdAndMemberId(page,size,lianmengId,memberId,queryTime);
        }
        for(BanDeskMate banDeskMate:banDeskMateList){
            Map<String ,Object> vo = new HashMap<>();
            if (banDeskMate.getMemberAId()!=null){
                MemberDbo memberDboA = memberDboDao.findById(banDeskMate.getMemberAId());
                vo.put("memberAId",memberDboA.getId());
                vo.put("memberANickname",memberDboA.getNickname());
                vo.put("memberAHeadimgurl",memberDboA.getHeadimgurl());
                MemberDbo memberDboB = memberDboDao.findById(banDeskMate.getMemberBId());
                vo.put("memberBId",memberDboB.getId());
                vo.put("memberBNickname",memberDboB.getNickname());
                vo.put("memberBHeadimgurl",memberDboB.getHeadimgurl());
                MemberDbo memberDboC = memberDboDao.findById(banDeskMate.getOperateId());
                vo.put("operateId",memberDboC.getId());
                vo.put("operateNickname",memberDboC.getNickname());
                vo.put("banId",banDeskMate.getId());
            }else {
                MemberDbo memberDboA = memberDboDao.findById(banDeskMate.getHehuorenId());
                vo.put("memberAId",memberDboA.getId());
                vo.put("memberANickname",memberDboA.getNickname());
                vo.put("memberAHeadimgurl",memberDboA.getHeadimgurl());
                MemberDbo memberDboC = memberDboDao.findById(banDeskMate.getOperateId());
                vo.put("operateId",memberDboC.getId());
                vo.put("operateNickname",memberDboC.getNickname());
                vo.put("banId",banDeskMate.getId());
                vo.put("suoyouxiaji",banDeskMate.isSuoyouxiaji());
            }
            vo.put("createTime",banDeskMate.getCreateTime());

            vos.add(vo);
        }
        return new ListPage(vos, page, size, amount);
    }

    public boolean verifyBanDesk(GameTable gameTable , String memberId){
        for (String player : gameTable.getPlayers()) {
            if (banDeskMateDao.findByMemberAIdAndMemberBId(memberId,player, gameTable.getLianmengId())!=null) {
                return false;
            }
            if (banDeskMateDao.findByMemberAIdAndMemberBId(player,memberId, gameTable.getLianmengId())!=null){
                return false;
            }
        }
        MemberLianmengDbo memberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(memberId, gameTable.getLianmengId());
        if (banDeskMateDao.findByHehuorenId(memberLianmengDbo.getSuperiorMemberId(),gameTable.getLianmengId())!=null) {
            for (String player : gameTable.getPlayers()) {
                MemberLianmengDbo playerMemberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(player, gameTable.getLianmengId());
                if (playerMemberLianmengDbo.getSuperiorMemberId().equals(memberLianmengDbo.getSuperiorMemberId())){
                    return false;
                }else {
                    if (banDeskMateDao.findByHehuorenId(memberLianmengDbo.getSuperiorMemberId(),gameTable.getLianmengId()).isSuoyouxiaji()){
                        while (playerMemberLianmengDbo.getIdentity()!=Identity.MENGZHU){
                            if (playerMemberLianmengDbo.getSuperiorMemberId().equals(memberLianmengDbo.getSuperiorMemberId())){
                                return false;
                            }
                            playerMemberLianmengDbo=memberLianmengDboDao.findByMemberIdAndLianmengId(playerMemberLianmengDbo.getSuperiorMemberId(),gameTable.getLianmengId());
                        }
                    }
                }
            }
        }
        memberLianmengDbo=memberLianmengDboDao.findByMemberIdAndLianmengId(memberLianmengDbo.getSuperiorMemberId(),gameTable.getLianmengId());
        while (memberLianmengDbo.getIdentity()!=Identity.MENGZHU){
            if (banDeskMateDao.findByHehuorenId(memberLianmengDbo.getSuperiorMemberId(),gameTable.getLianmengId())!=null) {
                for (String player : gameTable.getPlayers()) {
                    MemberLianmengDbo playerMemberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(player, gameTable.getLianmengId());
                    if (playerMemberLianmengDbo.getSuperiorMemberId().equals(memberLianmengDbo.getSuperiorMemberId())){
                        return false;
                    }else {
                        if (banDeskMateDao.findByHehuorenId(memberLianmengDbo.getSuperiorMemberId(),gameTable.getLianmengId()).isSuoyouxiaji()){
                            while (playerMemberLianmengDbo.getIdentity()!=Identity.MENGZHU){
                                if (playerMemberLianmengDbo.getSuperiorMemberId().equals(memberLianmengDbo.getSuperiorMemberId())){
                                    return false;
                                }
                                playerMemberLianmengDbo=memberLianmengDboDao.findByMemberIdAndLianmengId(playerMemberLianmengDbo.getSuperiorMemberId(),gameTable.getLianmengId());
                            }
                        }
                    }
                }
            }
            memberLianmengDbo=memberLianmengDboDao.findByMemberIdAndLianmengId(memberLianmengDbo.getSuperiorMemberId(),gameTable.getLianmengId());
        }
        return true;
    }

    public long countrenshu(String lianmengId) {
        return memberLianmengDboDao.countrenshu(lianmengId);
    }

    public List<MemberLianmengDbo> findByMemberId(String memberId) {
        return memberLianmengDboDao.findByMemberId(memberId);
    }

    public List<MemberLianmengDbo> findOnlineMemberByLianmengId(String lianmengId) {
        return memberLianmengDboDao.findOnlineMemberByLianmengId(lianmengId);
    }

    public List queryXiaji(String memberId,String lianmengId){
        List data = new ArrayList();
        MemberLianmengDbo memberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(memberId, lianmengId);
        List<MemberLianmengDbo> memberLianmengDboList;
        if (memberLianmengDbo.getIdentity().equals(Identity.MENGZHU)){
             memberLianmengDboList = memberLianmengDboDao.findByLianmengIdAndIdentity(lianmengId, Identity.HEHUOREN);
        }else {
            memberLianmengDboList = memberLianmengDboDao.findByLianmengIdAndSuperiorMemberIdAndIdentity(lianmengId,memberId, Identity.HEHUOREN);
        }
        for (MemberLianmengDbo lianmengDbo : memberLianmengDboList) {
            Map<String,String> vo = new HashMap();
            MemberDbo byId = memberDboDao.findById(lianmengDbo.getMemberId());
            vo.put("memberId",byId.getId());
            vo.put("nickname",byId.getNickname());
            vo.put("headimgurl",byId.getHeadimgurl());
            data.add(vo);
        }
        return data;
    }

    public ListPage findByNicknameOrMemberId(int page, int size, String search, String lianmengId, long queryTime) {
        int count= (int) memberLianmengDboDao.countByNicknameOrMemberIdAndLianmengId(search, lianmengId, queryTime);
        List<MemberLianmengDbo> byNicknameOrMemberIdAndLianmengId = memberLianmengDboDao.findByNicknameOrMemberIdAndLianmengId(page, size, search, lianmengId, queryTime);
        return new ListPage(byNicknameOrMemberIdAndLianmengId,page,size,count);
    }

    public void updateMemberScore(String memberId,String lianmengId,double score){
        memberLianmengDboDao.updateMemberScore(memberId,lianmengId, 0d);
    }

}
