package com.anbang.qipai.admin.plan.service.membersservice;

import com.anbang.qipai.admin.plan.bean.members.MemberExchangeEntityDbo;
import com.anbang.qipai.admin.plan.dao.membersdao.MemberExchangeEntityDao;
import com.anbang.qipai.admin.plan.dao.mongodb.mongodbmembersdao.MongodbMemberExchangeEntityDao;
import com.anbang.qipai.admin.web.vo.membersvo.MemberExchangeEntityPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * @Author: 吴硕涵
 * @Date: 2018/12/25 2:54 PM
 * @Version 1.0
 */

@Service
public class MemberExchangeEntityService {
    @Autowired
    private MongodbMemberExchangeEntityDao memberExchangeEntityDao;

    public MemberExchangeEntityPageVO findWithConditions(String memberId, String nickName,
                                                         String telephone, int page, int size,
                                                         Long startTime, Long endTime) {
        List<MemberExchangeEntityDbo> list = memberExchangeEntityDao.findWithConditions(memberId, nickName, telephone, page, size,
                startTime, endTime);
        MemberExchangeEntityPageVO pageVO = new MemberExchangeEntityPageVO();
        pageVO.setList(list);
        pageVO.setCount(memberExchangeEntityDao.countWithConditions(memberId, nickName, telephone, startTime, endTime));
        pageVO.setPage(page);
        pageVO.setSize(size);
        return pageVO;
    }

    public void addOne(MemberExchangeEntityDbo dbo) {
        memberExchangeEntityDao.add(dbo);
    }

    public void saveOne(MemberExchangeEntityDbo dbo) {
        memberExchangeEntityDao.save(dbo);
    }

    public MemberExchangeEntityDbo findByMemberIdAndRecordId(String memberId, String recordId) {
        return memberExchangeEntityDao.findRecordByMemberIdAndRecordId(memberId, recordId);
    }

    public MemberExchangeEntityDbo findById(String id) {
        return memberExchangeEntityDao.findById(id);
    }

    public int countPending() {
        return memberExchangeEntityDao.countPendingReward();
    }

}
