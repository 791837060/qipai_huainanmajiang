package com.anbang.qipai.game.plan.dao;

import com.anbang.qipai.game.plan.bean.historicalresult.GameJuPlayerResult;
import com.anbang.qipai.game.plan.bean.members.MemberLatAndLon;

import java.util.List;


public interface MemberLatAndLonDao {
    void save(MemberLatAndLon memberLatAndLon);

    List<MemberLatAndLon> find(List<String> memberIds, String roomNo);

    MemberLatAndLon findBymemberId(String memberId);

    void deleteMemberLatAndLon(String playerId);

}
