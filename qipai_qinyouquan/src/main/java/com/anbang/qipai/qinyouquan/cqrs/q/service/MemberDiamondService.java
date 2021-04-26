package com.anbang.qipai.qinyouquan.cqrs.q.service;



import com.anbang.qipai.qinyouquan.plan.bean.game.GameTable;
import com.anbang.qipai.qinyouquan.cqrs.q.dao.MemberDboDao;
import com.anbang.qipai.qinyouquan.cqrs.q.dao.MemberDiamondAccountDboDao;
import com.anbang.qipai.qinyouquan.cqrs.q.dao.MemberDiamondAccountingRecordDao;
import com.anbang.qipai.qinyouquan.cqrs.q.dao.MemberLianmengDboDao;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.MemberDiamondAccountDbo;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.MemberDiamondAccountingRecord;
import com.anbang.qipai.qinyouquan.util.TimeUtil;
import com.anbang.qipai.qinyouquan.web.vo.MemberDiamondAccountingRecordVO;
import com.dml.accounting.AccountingRecord;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cxy
 * @program: qipai
 * @Date: Created in 2019/11/11 11:08
 */

@Service
public class MemberDiamondService {
    @Autowired
    private MemberDiamondAccountDboDao memberDiamondAccountDboDao;

    @Autowired
    private MemberDiamondAccountingRecordDao memberDiamondAccountingRecordDao;

    @Autowired
    private MemberLianmengDboDao memberLianmengDboDao;

    @Autowired
    private MemberDboDao memberDboDao;

    public MemberDiamondAccountDbo findDiamondAccountDbo(String memberId, String lianmengId) {
        return memberDiamondAccountDboDao.findByMemberIdAndLianmengId(memberId, lianmengId);
    }

    /**
     * 能量流水记录
     */
    public MemberDiamondAccountingRecord withdraw(String memberId, String lianmengId, String referer, AccountingRecord accountingRecord , GameTable gameTable) {
        MemberDiamondAccountingRecord record = new MemberDiamondAccountingRecord();
        record.setAccountId(accountingRecord.getAccountId());
        record.setMemberId(memberId);
        if (gameTable!=null){
            record.setWanfaName(gameTable.getWanfa().getWanfaName());
            record.setGame(gameTable.getGame());
            record.setGameRoomNo(gameTable.getNo());
        }
        record.setLianmengId(lianmengId);
        record.setReferer(referer);
        record.setAccountAmount( accountingRecord.getAccountingAmount());
        record.setBalance( accountingRecord.getBalanceAfter());
        record.setSummary(accountingRecord.getSummary());
        record.setNo((int) accountingRecord.getAccountingNo());
        record.setAccountingTime(accountingRecord.getAccountingTime());
        memberDiamondAccountingRecordDao.save(record);
        memberDiamondAccountDboDao.updateBalance(record.getAccountId(), record.getBalance());
        return record;
    }




    public ListPage queryRecordByMemberIdsAndLianmengId(int page, int size, String memberId, String lianmengId, long queryTime, boolean searchGameCost) {
        long startTime = TimeUtil.getDayStartTime(queryTime);
        List<String> memberIds = memberLianmengDboDao.listIdsByLianmengIdAndSuperiorMemberId(lianmengId, memberId);
        memberIds.add(memberId);
        int amount = (int) memberDiamondAccountingRecordDao.countByMemberIdAndLianmengId(memberIds, lianmengId,startTime,queryTime, searchGameCost);
        List<MemberDiamondAccountingRecord> recordList = memberDiamondAccountingRecordDao.findByMemberIdsAndLianmengId(page, size, memberIds, lianmengId,startTime,queryTime,searchGameCost);
        List<MemberDiamondAccountingRecordVO> return_vos = new ArrayList<>();
        for (MemberDiamondAccountingRecord memberDiamondAccountingRecord : recordList) {
            MemberDiamondAccountingRecordVO memberDiamondAccountingRecordVO =new MemberDiamondAccountingRecordVO(memberDiamondAccountingRecord);
            memberDiamondAccountingRecordVO.setNickname(memberDboDao.findById(memberDiamondAccountingRecordVO.
                    getMemberId()).getNickname());
            return_vos.add(memberDiamondAccountingRecordVO);
        }
        return new ListPage(return_vos, page, size, amount);
    }



}
