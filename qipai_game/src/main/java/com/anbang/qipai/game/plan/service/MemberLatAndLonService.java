package com.anbang.qipai.game.plan.service;


import com.anbang.qipai.game.plan.bean.historicalresult.GameJuPlayerResult;
import com.anbang.qipai.game.plan.bean.members.MemberLatAndLon;
import com.anbang.qipai.game.plan.dao.MemberLatAndLonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberLatAndLonService {

    @Autowired
    private MemberLatAndLonDao memberLatAndLonDao;

    public void save(MemberLatAndLon memberLatAndLon) {
        memberLatAndLonDao.save(memberLatAndLon);
    }

    public List<MemberLatAndLon> find(List<String> memberIds, String roomNo) {
        return memberLatAndLonDao.find(memberIds, roomNo);
    }

    public MemberLatAndLon findBymemberId(String memberId) {
        return memberLatAndLonDao.findBymemberId(memberId);
    }

    public void deleteMemberLatAndLon(String playerId) {
        memberLatAndLonDao.deleteMemberLatAndLon(playerId);
    }

}
