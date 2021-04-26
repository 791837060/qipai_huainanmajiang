//package com.anbang.qipai.dalianmeng.plan.dao;
//
//import java.util.List;
//
//public interface MemberDayResultDao {
//
//    void save(MemberDayResult result);
//
//    long countByLianmengIdAndRefererAndMemberIdAndTime(String lianmengId,String referer, String memberId, long startTime, long endTime);
//
//    List<MemberDayResult> findByLianmengIdAndRefererAndMemberIdAndTime(int page, int size, String lianmengId,String referer, String memberId,
//                                                            long startTime, long endTime);
//
//    MemberDayResult findByLianmengIdAndRefererAndPlayerIdAndTime(String lianmengId,String referer, String playerId, long startTime, long endTime);
//
//    MemberDayResult findById(String id);
//
//    void updateIncById(String id, int dayingjiaCount, int
//            juCount, double yushiCost, double totalScore, long createTime);
//
//    void removeById(String id);
//
//    void removeByTime(long endTime);
//}
