package com.anbang.qipai.dalianmeng.plan.dao;

import com.anbang.qipai.dalianmeng.plan.bean.MemberDayResultData;

import java.util.List;

public interface MemberDayResultDataDao {
    void save(MemberDayResultData memberDayResultData);

    MemberDayResultData findByMemberIdAndLianmengIdAndTime(String memberId, String lianmengId, long startTime, long endTime);

    List<MemberDayResultData> findByMemberIdAndLianmengIdAndTime(int page , int size, List<String> memberId, String lianmengId,
                                                                 long startTime, long endTime, String powerSort, String scoreSort, String juCountSort, String dayingjiaCountSort,
                                                                 String powerCostSort);

    long countByMemberIdAndLianmengIdAndTime(List<String> memberIds, String lianmengId,
                                             long startTime, long endTime);

    void increaseDayingjiaCount(String memberId, String lianmengId, long startTime, long endTime, int dayingjiaCount);

    void increaseJuCount(String memberId, String lianmengId, long startTime, long endTime, int juCount);

    void updatePowerCost(String memberId, String lianmengId, long startTime, long endTime, double power);

    void updatePower(String memberId, String lianmengId, long startTime, long endTime, double power);

    void updateMemberPower(String memberId, String lianmengId, long startTime, long endTime, double power);

    void updateScore(String memberId, String lianmengId, long startTime, long endTime, double score);

    void updateTotalScore(String memberId, String lianmengId, long startTime, long endTime, double score);

    void deleteByTime(long startTime);

    void increaseErrenDayingjiaCount(String memberId, String lianmengId, long startTime, long endTime, int amount);

    void increaseMemberErrenDayingjiaCount(String memberId,String lianmengId ,long startTime,long endTime,int amount);

    void increaseSanrenDayingjiaCount(String memberId,String lianmengId ,long startTime,long endTime,int amount);

    void increaseMemberSanrenDayingjiaCount(String memberId, String lianmengId, long startTime, long endTime, int amount);

    void increaseSirenDayingjiaCount(String memberId, String lianmengId, long startTime, long endTime, int amount);

    void increaseMemberSirenDayingjiaCount(String memberId, String lianmengId, long startTime, long endTime, int amount);

    void increaseDuorenDayingjiaCount(String memberId, String lianmengId, long startTime, long endTime, int amount);

    void increaseMemberDuorenDayingjiaCount(String memberId, String lianmengId, long startTime, long endTime, int amount);

    void increaseErrenJuCount(String memberId, String lianmengId, long startTime, long endTime, int amount);

    void increaseMemberErrenJuCount(String memberId,String lianmengId ,long startTime,long endTime,int amount);

    void increaseSanrenJuCount(String memberId,String lianmengId ,long startTime,long endTime,int amount);

    void increaseMemberSanrenJuCount(String memberId, String lianmengId, long startTime, long endTime, int amount);

    void increaseSirenJuCount(String memberId, String lianmengId, long startTime, long endTime, int amount);

    void increaseMemberSirenJuCount(String memberId, String lianmengId, long startTime, long endTime, int amount);

    void increaseDuorenJuCount(String memberId, String lianmengId, long startTime, long endTime, int amount);

    void increaseMemberDuorenJuCount(String memberId, String lianmengId, long startTime, long endTime, int amount);

    void updateMemberPowerCost(String memberId, String lianmengId, long startTime, long endTime, double power);

    List<MemberDayResultData> findAllByMemberIdAndLianmengIdAndTime(String memberId, String lianmengId , long startTime, long endTime);

}
