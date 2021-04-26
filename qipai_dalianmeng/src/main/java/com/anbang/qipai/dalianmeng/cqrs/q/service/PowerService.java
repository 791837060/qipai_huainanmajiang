package com.anbang.qipai.dalianmeng.cqrs.q.service;

import com.anbang.qipai.dalianmeng.cqrs.q.dao.*;
import com.anbang.qipai.dalianmeng.cqrs.q.dbo.*;
import com.anbang.qipai.dalianmeng.plan.dao.GameHistoricalJuResultDao;
import com.anbang.qipai.dalianmeng.util.TimeUtil;
import com.dml.accounting.AccountingRecord;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cxy
 * @program: qipai
 * @Date: Created in 2019/11/11 11:08
 */

@Service
public class PowerService {
    @Autowired
    private PowerAccountDboDao powerAccountDboDao;

    @Autowired
    private PowerAccountingRecordDao powerAccountingRecordDao;

    @Autowired
    private MemberLianmengDboDao memberLianmengDboDao;

    @Autowired
    private MemberDboDao memberDboDao;

    @Autowired
    private GameHistoricalJuResultDao gameHistoricalJuResultDao;

    @Autowired
    private BoxAccountDboDao boxAccountDboDao;

    public PowerAccountDbo findPowerAccountDbo(String memberId, String lianmengId) {
        return powerAccountDboDao.findByMemberIdAndLianmengId(memberId, lianmengId);
    }

    /**
     * 能量流水记录
     */
    public PowerAccountingRecord withdraw(String memberId, String lianmengId, String referer, AccountingRecord accountingRecord ) {
        PowerAccountingRecord record = new PowerAccountingRecord();
        record.setAccountId(accountingRecord.getAccountId());
        record.setMemberId(memberId);
        record.setLianmengId(lianmengId);
        record.setReferer(referer);
        record.setAccountAmount( accountingRecord.getAccountingAmount());
        record.setBalance( accountingRecord.getBalanceAfter());
        record.setSummary(accountingRecord.getSummary());
        record.setNo((int) accountingRecord.getAccountingNo());
        record.setAccountingTime(accountingRecord.getAccountingTime());
        powerAccountingRecordDao.save(record);
        powerAccountDboDao.updateBalance(record.getAccountId(), record.getBalance());
        return record;
    }


    public ListPage queryRecordByMemberIdAndLianmengId(int page, int size, String memberId, String lianmengId,long queryTime,boolean searchGameCost) {
        long startTime = TimeUtil.getDayStartTime(queryTime);
        int amount;
        if (searchGameCost){
            amount = (int) powerAccountingRecordDao.countByMemberIdAndLianmengId(memberId, lianmengId,startTime,queryTime, null);
        }else {
            amount = (int) powerAccountingRecordDao.countByMemberIdAndLianmengId(memberId, lianmengId,startTime,queryTime, "game ju finish");
        }

        List<PowerAccountingRecord> recordList;
        if (searchGameCost){
            recordList = powerAccountingRecordDao.findByMemberIdAndLianmengId(page, size, memberId, lianmengId,startTime,queryTime,null);

        }else {
            recordList = powerAccountingRecordDao.findByMemberIdAndLianmengId(page, size, memberId, lianmengId,startTime,queryTime,"game ju finish");

        }
        MemberDbo memberDbo = memberDboDao.findById(memberId);
        List<Map<String, Object>> vos = new ArrayList<>();
        for(PowerAccountingRecord powerAccountingRecord:recordList){
            Map<String, Object> vo = new HashMap<String, Object>();
            vo.put("accountAmount",powerAccountingRecord.getAccountAmount());
            vo.put("balance",powerAccountingRecord.getBalance());
            vo.put("summary",powerAccountingRecord.getSummary());
            vo.put("accountingTime",powerAccountingRecord.getAccountingTime());
            if (powerAccountingRecord.getReferer()!=null) {
                vo.put("referer",powerAccountingRecord.getReferer());
            }

            vo.put("memberId",memberDbo.getId());
            vo.put("nickName",memberDbo.getNickname());
            vos.add(vo);
        }
        return new ListPage(vos, page, size, amount);
    }

    public ListPage queryXiajiRecordByMemberIdAndLianmengId(int page, int size, String memberId, String lianmengId, long queryTime, boolean searchGameCost) {
        long startTime = TimeUtil.getDayStartTime(queryTime);
        List<MemberLianmengDbo> byMemberIdAndLianmengIdAndSuperior = memberLianmengDboDao.findByMemberIdAndLianmengIdAndSuperior( lianmengId, memberId);
        MemberLianmengDbo byMemberIdAndLianmengId = memberLianmengDboDao.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (!byMemberIdAndLianmengId.getIdentity().equals(Identity.MENGZHU)) {
            byMemberIdAndLianmengIdAndSuperior.add(byMemberIdAndLianmengId);
        }
        int amount;
        if (searchGameCost) {
            amount = byMemberIdAndLianmengIdAndSuperior.stream().mapToInt(memberLianmengDbo->(int) powerAccountingRecordDao.countByMemberIdAndLianmengId(memberLianmengDbo.getMemberId(), lianmengId, startTime, queryTime, null)).sum();
        }else {
            amount = byMemberIdAndLianmengIdAndSuperior.stream().mapToInt(memberLianmengDbo->(int) powerAccountingRecordDao.countByMemberIdAndLianmengId(memberLianmengDbo.getMemberId(), lianmengId, startTime, queryTime, "game ju finish")).sum();

        }
        List<String> memberIds = new ArrayList<>();
        for (MemberLianmengDbo memberLianmengDbo : byMemberIdAndLianmengIdAndSuperior) {
            memberIds.add(memberLianmengDbo.getMemberId());
        }        List<PowerAccountingRecord> recordList ;

        if (searchGameCost){
            recordList = powerAccountingRecordDao.findByMemberIdsAndLianmengId(page, size, memberIds, lianmengId, startTime, queryTime, null);

        }else {
            recordList = powerAccountingRecordDao.findByMemberIdsAndLianmengId(page, size, memberIds, lianmengId, startTime, queryTime, "game ju finish");

        }
        List<Map<String, Object>> vos = new ArrayList<>();
        for(PowerAccountingRecord powerAccountingRecord:recordList){
            MemberDbo memberDbo = memberDboDao.findById(powerAccountingRecord.getMemberId());
            Map<String, Object> vo = new HashMap<String, Object>();
            vo.put("accountAmount",powerAccountingRecord.getAccountAmount());
            vo.put("balance",powerAccountingRecord.getBalance());
            vo.put("summary",powerAccountingRecord.getSummary());
            vo.put("accountingTime",powerAccountingRecord.getAccountingTime());
            if (powerAccountingRecord.getReferer()!=null) {
                vo.put("referer",powerAccountingRecord.getReferer());
            }
            vo.put("memberId",memberDbo.getId());
            vo.put("nickName",memberDbo.getNickname());
            vos.add(vo);
        }
        return new ListPage(vos, page, size, amount);
    }




}
