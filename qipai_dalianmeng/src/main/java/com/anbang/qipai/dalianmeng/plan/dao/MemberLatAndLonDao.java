package com.anbang.qipai.dalianmeng.plan.dao;



import com.anbang.qipai.dalianmeng.plan.bean.MemberLatAndLon;

import java.util.List;


public interface MemberLatAndLonDao {
    void save(MemberLatAndLon memberLatAndLon);

    List<MemberLatAndLon> find(List<String> memberIds,String roomNo);

	void deleteMemberLatAndLon(String playerId);

    MemberLatAndLon findBymemberId(String memberId, String roomNo);

}
