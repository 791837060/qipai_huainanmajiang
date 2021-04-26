package com.anbang.qipai.qinyouquan.plan.dao;

import com.anbang.qipai.qinyouquan.plan.bean.MemberDayResultData;

import java.util.List;

public interface MemberDayResultDataDao {
    void save(MemberDayResultData memberDayResultData);

    MemberDayResultData findByMemberIdAndLianmengIdAndTime(String memberId, String lianmengId, long startTime, long endTime);

    List<MemberDayResultData> findByMemberIdAndLianmengIdAndTime(int page ,int size,List<String> memberId, String lianmengId,
                                                                 long startTime, long endTime, String diamondCostSort,
                                                                 String juCountSort, String zhanjiCountSort, String dayingjiaCountSort, String diamondSort);

    long countByMemberIdAndLianmengIdAndTime(List<String> memberIds, String lianmengId,
                                             long startTime, long endTime, String diamondCostSort,
                                             String juCountSort, String zhanjiCountSort, String dayingjiaCountSort, String diamondSort);

    List<MemberDayResultData> findByLianmengIdAndTime(int page,int size,String lianmengId, long startTime, long endTime);

    long getAmountByLianmengIdAndTime(String lianmengId, long startTime, long endTime);

    void increaseErrenDayingjiaCount(String memberId, String lianmengId, long startTime, long endTime, int amount);

    void increaseMemberErrenDayingjiaCount(String memberId,String lianmengId ,long startTime,long endTime,int amount);

    void increaseSanrenDayingjiaCount(String memberId,String lianmengId ,long startTime,long endTime,int amount);

    void increaseMemberSanrenDayingjiaCount(String memberId, String lianmengId, long startTime, long endTime, int amount);

    void increaseSirenDayingjiaCount(String memberId, String lianmengId, long startTime, long endTime, int amount);

    void increaseMemberSirenDayingjiaCount(String memberId, String lianmengId, long startTime, long endTime, int amount);

    void increaseErrenJuCount(String memberId, String lianmengId, long startTime, long endTime, int amount);

    void increaseMemberErrenJuCount(String memberId,String lianmengId ,long startTime,long endTime,int amount);

    void increaseSanrenJuCount(String memberId,String lianmengId ,long startTime,long endTime,int amount);

    void increaseMemberSanrenJuCount(String memberId, String lianmengId, long startTime, long endTime, int amount);

    void increaseSirenJuCount(String memberId, String lianmengId, long startTime, long endTime, int amount);

    void increaseMemberSirenJuCount(String memberId, String lianmengId, long startTime, long endTime, int amount);

    void increaseDiamondCost(String memberId, String lianmengId, long startTime, long endTime, int amount);

    void increaseMemberDiamondCost(String memberId, String lianmengId, long startTime, long endTime, int amount);

    void increaseMemberTotalScore(String memberId, String lianmengId, long startTime, long endTime, double amount);

    void increaseTotalScore(String memberId, String lianmengId, long startTime, long endTime, double amount);

    void increaseFinishJuCount(String memberId, String lianmengId, long startTime, long endTime, int amount);

    void increaseDiamond(String memberId, String lianmengId, long startTime, long endTime, int amount);

    void increaseMemberDiamond(String memberId, String lianmengId, long startTime, long endTime, int amount);

    void increaseDayingjiaCount(String memberId, String lianmengId, long startTime, long endTime, int amount);


    void deleteByTime(long startTime);

    void deleteByMemberIdAndLianmengId(String memberId, String lianmengId);

    MemberDayResultData findByMembersIdAndLianmengIdAndTime(String memberId,String playerId, String lianmengId, long startTime, long endTime);

    void increaseDuorenDayingjiaCount(String memberId, String lianmengId, long startTime, long endTime, int amount);

    void increaseMemberDuorenDayingjiaCount(String memberId, String lianmengId, long startTime, long endTime, int amount);

    void increaseDuorenJuCount(String memberId, String lianmengId, long startTime, long endTime, int amount);

    void increaseMemberDuorenJuCount(String memberId, String lianmengId, long startTime, long endTime, int amount);

}
