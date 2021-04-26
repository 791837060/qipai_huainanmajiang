package com.anbang.qipai.dalianmeng.cqrs.q.service;

import com.anbang.qipai.dalianmeng.cqrs.q.dao.MemberDboDao;
import com.anbang.qipai.dalianmeng.cqrs.q.dao.MemberLianmengDboDao;
import com.anbang.qipai.dalianmeng.cqrs.q.dao.ScoreAccountDboDao;
import com.anbang.qipai.dalianmeng.cqrs.q.dao.ScoreAccountingRecordDao;
import com.anbang.qipai.dalianmeng.cqrs.q.dbo.*;
import com.anbang.qipai.dalianmeng.util.TimeUtil;
import com.anbang.qipai.dalianmeng.web.vo.ScoreAccountingRecordVO;
import com.dml.accounting.AccountingRecord;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ScoreService {

    @Autowired
    private ScoreAccountDboDao scoreAccountDboDao;

    @Autowired
    private MemberDboDao memberDboDao;

    @Autowired
    private ScoreAccountingRecordDao scoreAccountingRecordDao;

    @Autowired
    private MemberLianmengDboDao memberLianmengDboDao;

    public ScoreAccountDbo findScoreAccountDbo(String memberId, String lianmengId) {
        return scoreAccountDboDao.findByMemberIdAndLianmengId(memberId, lianmengId);
    }

    /**
     * 查询贡献值流水
     * @param page
     * @param size
     * @param memberId
     * @param lianmengId
     * @return
     */
    public ListPage findByMemberIdAndLianmengId(int page, int size, String memberId, String lianmengId,long queryTime){
        long startTime = TimeUtil.getDayStartTime(queryTime);
        int amount = (int) scoreAccountingRecordDao.countByMemberIdAndLianmengId(memberId, lianmengId,startTime,queryTime);
        List<ScoreAccountingRecordVO> scoreAccountingRecordVOS= new ArrayList<>();
        List<ScoreAccountingRecord> scoreAccountingRecords = scoreAccountingRecordDao.findByMemberIdAndLianmengId(page, size, memberId, lianmengId,startTime,queryTime);
        for (ScoreAccountingRecord scoreAccountingRecord:scoreAccountingRecords){
            ScoreAccountingRecordVO scoreAccountingRecordVO = new ScoreAccountingRecordVO();
            scoreAccountingRecordVO.setAccountAmount(scoreAccountingRecord.getAccountAmount());
            scoreAccountingRecordVO.setAccountingTime(scoreAccountingRecord.getAccountingTime());
            scoreAccountingRecordVO.setBalance(scoreAccountingRecord.getBalance());
            scoreAccountingRecordVO.setMemberId(scoreAccountingRecord.getMemberId());
            scoreAccountingRecordVO.setReferer(scoreAccountingRecord.getReferer());
            MemberDbo memberDbo = memberDboDao.findById(scoreAccountingRecord.getReferer());
            scoreAccountingRecordVO.setRefererHeadimgurl(memberDbo.getHeadimgurl());
            scoreAccountingRecordVO.setRefererNickname(memberDbo.getNickname());
            scoreAccountingRecordVOS.add(scoreAccountingRecordVO);

        }
        return new ListPage(scoreAccountingRecordVOS, page, size, amount);
    }
    /**
     * 增减用户贡献值
     */
    public ScoreAccountingRecord withdraw(String memberId, String lianmengId, String referer, AccountingRecord accountingRecord ) {
        ScoreAccountingRecord record = new ScoreAccountingRecord();
        record.setAccountId(accountingRecord.getAccountId());
        record.setMemberId(memberId);
        record.setLianmengId(lianmengId);
        record.setReferer(referer);
        record.setAccountAmount( accountingRecord.getAccountingAmount());
        record.setBalance( accountingRecord.getBalanceAfter());
        record.setSummary(accountingRecord.getSummary());
        record.setNo((int) accountingRecord.getAccountingNo());
        record.setAccountingTime(accountingRecord.getAccountingTime());
        scoreAccountingRecordDao.save(record);
        scoreAccountDboDao.updateBalance(record.getAccountId(), record.getBalance(),0);
        double totalScore=0;
        MemberLianmengDbo memberLianmengDbo = memberLianmengDboDao.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberLianmengDbo.getIdentity().equals(Identity.MENGZHU)){
            List<ScoreAccountDbo> bylianmemngId = scoreAccountDboDao.findBylianmemngId(lianmengId);
            if (bylianmemngId!=null){
                for (ScoreAccountDbo powerAccountDbo : bylianmemngId) {
                    totalScore +=powerAccountDbo.getBalance();
                }
            }
        }
        if (!memberLianmengDbo.getIdentity().equals(Identity.CHENGYUAN)){
            totalScore=0;
            List<MemberLianmengDbo> memberLianmengDbos = memberLianmengDboDao.findByMemberIdAndLianmengIdAndSuperior( lianmengId, memberLianmengDbo.getMemberId());
            MemberLianmengDbo memberLianmengDbo1= memberLianmengDboDao.findByMemberIdAndLianmengIdAndIdentity(memberLianmengDbo.getMemberId(), lianmengId, memberLianmengDbo.getIdentity());
            if (memberLianmengDbo1!=null){
                totalScore += scoreAccountDboDao.findByMemberIdAndLianmengId(memberLianmengDbo1.getMemberId(),lianmengId).getBalance();
            }
            if (memberLianmengDbos!=null){
                for (MemberLianmengDbo lianmengDbo : memberLianmengDbos) {
                    if (!lianmengDbo.getIdentity().equals(Identity.CHENGYUAN)){
                        totalScore += scoreAccountDboDao.findByMemberIdAndLianmengId(lianmengDbo.getMemberId(),lianmengId).getBalance();
                    }
                }
            }
        }
        scoreAccountDboDao.updateBalance(record.getAccountId(), record.getBalance(),totalScore);
        return record;
    }


//    public double countLianmengTotalScore(String lianmengId,String memberId, Identity identity, long startTime, long endTime) {
//        double count=0;
//        if (identity.equals(Identity.MENGZHU)){
//           List<ScoreAccountingRecord> scoreAccountingRecordList=scoreAccountingRecordDao.findByMemberIdAndLianmengId2(lianmengId,null,startTime,endTime);
//            for (ScoreAccountingRecord scoreAccountingRecord : scoreAccountingRecordList) {
//                if (scoreAccountingRecord.getAccountAmount()>=0){
//                   count+= scoreAccountingRecord.getBalance();
//                }
//            }
//
//            return count;
//        }
//        if (identity.equals(Identity.CHENGYUAN)){
//            List<ScoreAccountingRecord> byMemberIdAndLianmengId = scoreAccountingRecordDao.findByMemberIdAndLianmengId2(lianmengId, memberId, startTime, endTime);
//            for (ScoreAccountingRecord scoreAccountingRecord : byMemberIdAndLianmengId) {
//                if (scoreAccountingRecord.getAccountAmount()>=0){
//                    count+= scoreAccountingRecord.getBalance();
//                }
//            }
//            return count;
//        }else {
//            List<MemberLianmengDbo> lianmengDbos=memberLianmengDboDao.findByLianmengIdAndSuperiorIdentity(lianmengId,identity,null);
//            if (lianmengDbos!=null){
//                List<String> memberIds=new ArrayList<>();
//                for (MemberLianmengDbo lianmengDbo : lianmengDbos) {
//                    String memberId1 = lianmengDbo.getMemberId();
//                    memberIds.add(memberId1);
//                }
//                List<ScoreAccountingRecord> byMemberIdAndLianmengId = scoreAccountingRecordDao.findByMemberIdAndLianmengId2(lianmengId, memberId, startTime, endTime);
//                for (ScoreAccountingRecord scoreAccountingRecord : byMemberIdAndLianmengId) {
//                    if (scoreAccountingRecord.getAccountAmount()>=0){
//                        count+= scoreAccountingRecord.getBalance();
//                    }
//                }
//                return count+ scoreAccountingRecordDao.countByMemberIdAndLianmengIde3(lianmengId,memberIds,startTime,endTime);
//            }else {
//                List<ScoreAccountingRecord> byMemberIdAndLianmengId = scoreAccountingRecordDao.findByMemberIdAndLianmengId2(lianmengId, memberId, startTime, endTime);
//                for (ScoreAccountingRecord scoreAccountingRecord : byMemberIdAndLianmengId) {
//                    if (scoreAccountingRecord.getAccountAmount()>=0){
//                        count+= scoreAccountingRecord.getBalance();
//                    }
//                }
//                return count;
//            }
//
//        }
//    }
}
