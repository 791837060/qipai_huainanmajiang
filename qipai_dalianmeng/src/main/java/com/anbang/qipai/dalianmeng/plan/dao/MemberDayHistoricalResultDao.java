//package com.anbang.qipai.dalianmeng.plan.dao;
//
//import java.util.List;
//
//public interface MemberDayHistoricalResultDao {
//
//    void save(MemberDayHistoricalResult result);
//
//    long countByLianmengIdAndRefererAndMemberIdAndTime(String lianmengId, String referer, String memberId, long startTime, long endTime);
//
//    List<MemberDayHistoricalResult> findByLianmengIdAndRefererAndMemberIdAndTime(int page, int size, String lianmengId, String referer,
//                                                                                 String memberId, long startTime, long endTime);
//
//    MemberDayHistoricalResult findByLianmengIdAndRefererAndPlayerIdAndTime(String lianmengId, String referer, String playerId, long startTime, long endTime);
//
//    void updateIncById(String id, int dayingjiaCount, int juCount, double yushiCost, double totalScore, long createTime);
//
//    void removeByTime(long endTime);
//}
