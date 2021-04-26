package com.anbang.qipai.qinyouquan.cqrs.q.service;

import com.anbang.qipai.qinyouquan.cqrs.q.dao.LianmengDiamondAccountDboDao;
import com.anbang.qipai.qinyouquan.cqrs.q.dao.LianmengDiamondAccountingRecordDao;
import com.anbang.qipai.qinyouquan.cqrs.q.dao.MemberDboDao;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.LianmengDiamondAccountDbo;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.LianmengDiamondAccountingRecord;
import com.anbang.qipai.qinyouquan.plan.bean.game.GameTable;
import com.anbang.qipai.qinyouquan.util.TimeUtil;
import com.anbang.qipai.qinyouquan.web.vo.LianmengDiamondAccountingRecordVO;
import com.dml.accounting.AccountingRecord;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LianmengDiamondService {

    @Autowired
    private LianmengDiamondAccountDboDao lianmengDiamondAccountDboDao;

    @Autowired
    private LianmengDiamondAccountingRecordDao lianmengDiamondAccountingRecordDao;

    @Autowired
    private MemberDboDao memberDboDao;


    public LianmengDiamondAccountDbo findDiamondAccountDbo(String agentId) {
        return lianmengDiamondAccountDboDao.findByAgentId(agentId);
    }

    public void newLianmengYushiAccount(String accountId, String agentId) {
        LianmengDiamondAccountDbo account = new LianmengDiamondAccountDbo();
        account.setId(accountId);
        account.setAgentId(agentId);
        lianmengDiamondAccountDboDao.save(account);
    }

    /**
     * 玉石流水记录
     */
    public LianmengDiamondAccountingRecord withdraw(String mengzhuId, AccountingRecord accountingRecord, String lianmengId, GameTable gameTable) {
        LianmengDiamondAccountingRecord record = new LianmengDiamondAccountingRecord();
        record.setAccountId(accountingRecord.getAccountId());
        record.setMengzhuId(mengzhuId);
        if (gameTable!=null){
            record.setWanfaName(gameTable.getWanfa().getWanfaName());
            record.setGame(gameTable.getGame());
            record.setGameRoomNo(gameTable.getNo());
        }
        record.setLianmengId(lianmengId);
        record.setAccountAmount((int) accountingRecord.getAccountingAmount());
        record.setBalance((int) accountingRecord.getBalanceAfter());
        record.setSummary(accountingRecord.getSummary());
        record.setNo((int) accountingRecord.getAccountingNo());
        record.setAccountingTime(accountingRecord.getAccountingTime());
        lianmengDiamondAccountingRecordDao.save(record);
        lianmengDiamondAccountDboDao.updateBalance(record.getAccountId(), record.getBalance());
        return record;
    }

    public ListPage queryRecordByMemberIdAndLianmengId(int page, int size, String memberId, String lianmengId, long queryTime, boolean searchGameCost) {
        long startTime = TimeUtil.getDayStartTime(queryTime);
        int amount = (int) lianmengDiamondAccountingRecordDao.countByMemberIdAndLianmengId(memberId, lianmengId,startTime,queryTime, searchGameCost);
        List<LianmengDiamondAccountingRecord> recordList = lianmengDiamondAccountingRecordDao.findByMemberIdsAndLianmengId(page, size, memberId, lianmengId,startTime,queryTime,searchGameCost);
        List<LianmengDiamondAccountingRecordVO> return_vos = new ArrayList<>();
        for (LianmengDiamondAccountingRecord LianmengDiamondAccountingRecord : recordList) {
            LianmengDiamondAccountingRecordVO lianmengDiamondAccountingRecordVO =new LianmengDiamondAccountingRecordVO(LianmengDiamondAccountingRecord);
            lianmengDiamondAccountingRecordVO.setNickname(memberDboDao.findById(lianmengDiamondAccountingRecordVO.
                    getMemberId()).getNickname());
            return_vos.add(lianmengDiamondAccountingRecordVO);
        }
        return new ListPage(return_vos, page, size, amount);
    }



}
