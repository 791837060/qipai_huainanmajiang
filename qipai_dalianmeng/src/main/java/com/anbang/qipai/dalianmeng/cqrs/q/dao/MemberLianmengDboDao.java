package com.anbang.qipai.dalianmeng.cqrs.q.dao;

import com.anbang.qipai.dalianmeng.cqrs.q.dbo.Identity;
import com.anbang.qipai.dalianmeng.cqrs.q.dbo.MemberLianmengDbo;

import java.util.List;

public interface MemberLianmengDboDao {

    void save(MemberLianmengDbo memberLianmengDbo);

    MemberLianmengDbo findByMemberIdAndLianmengId(String memberId, String lianmengId);

    MemberLianmengDbo findByMemberIdAndLianmengIdAndIdentity(String memberId, String lianmengId, Identity identity);

    MemberLianmengDbo findByAgentIdAndLianmengIdAndIdentity(String agentId, String lianmengId, Identity identity);

    void updateSuperiorMember(String memberId, String lianmengId, String superiorMemberId);

    void updateContributionProportion(String memberId, String lianmengId, int contributionProportion);

    void updateIdentity(String memberId, String lianmengId, Identity identity);

    void updateOnlineState(String memberId, String onlineState);

    long countByLianmengIdAndReferer(String lianmengId, String referer);

    long countByMemberIdAndIdentity(String memberId, Identity identity);

    List<MemberLianmengDbo> findByMemberIdAndLianmengIdAndSuperior(String lianmengId, String superiorMemberId);

    List<MemberLianmengDbo> findByMemberIdAndLianmengIdAndSuperior(int page, int size, String lianmengId, String superiorMemberId, String onlineSort);

    List<MemberLianmengDbo> findByMemberIdAndLianmengId(int page, int size, String memberId, String lianmengId);

    List<MemberLianmengDbo> findByMemberId(String memberId);

    List<MemberLianmengDbo> findByNicknameOrMemberIdAndLianmengId(int page, int size, String nickname, String lianmengId, long queryTime);

    List<MemberLianmengDbo> findByMemberIdAndIdentity(String memberId, Identity identity);

    long countByLianmengId(String lianmengId);

    long countByLianmengIdAndIdentity(String lianmengId, Identity identity, String memberId, long queryTime);

    long countByLianmengIdAndIdentity(String lianmengId, Identity identity);


    List<MemberLianmengDbo> findByLianmengIdAndIdentity(int page, int size, String lianmengId, Identity identity);

    List<MemberLianmengDbo> findByLianmengIdAndIdentity(String lianmengId, Identity identity);

    long countOnlineMemberByLianmengId(String lianmengId);

    List<MemberLianmengDbo> findByLianmengId(int page, int size, String lianmengId);

    void removeByLianmengId(String lianmengId);

    void removeByMemberIdAndLianmengId(String memberId, String lianmengId);

    void removeByRefererAndLianmengId(String referer, String lianmengId);

    void updateBanByMemberIdAndLianmengId(String memberId, String lianmengId, boolean ban);

    void updateBanByRefererAndLianmengId(String referer, String lianmengId, boolean ban);

    long countrenshu(String lianmengId, long queryTime);

    long getAmountByMemberIdAndLianmengIdAndSuperior(String lianmengId, String superiorMemberId, long queryTime);

    List<MemberLianmengDbo> findByMemberIdAndLianmengIdAndIdentity1(String memeberId, String lianmengId, Identity identity);

    List<MemberLianmengDbo> findOnlineMemberByLianmengId(String lianmengId);

    void updateSuperiorMemberIdAndIdentity(String memberId, String lianmengId, String superiorMemberId);

    List<MemberLianmengDbo> findAll();

    void updateZhushouId(String memberId, String lianmengId, String zhushouId);

    List<MemberLianmengDbo> findByLianmengIdAndSuperiorMemberIdAndIdentity(String lianmengId, String superiorMemberId, Identity identity);


    List<MemberLianmengDbo> findByLianmengIdAndSuperiorMemberIdAndIdentity(int skip, int size, String lianmengId, String superiorMemberId, Identity identity, long queryTime);

    long countByNicknameOrMemberIdAndLianmengId(String nickname, String lianmengId, long queryTime);

    long countByLianmengIdAndZhushouId(String lianmengId, String zhushouId, long queryTime);

    List<MemberLianmengDbo> findByLianmengIdAndZhushouId(int page, int size, String lianmengId, String zhushouId, long queryTime);

    List<String> listIdsByLianmengIdAndSuperiorMemberId(String lianmengId, String superiorMemberId);


}
