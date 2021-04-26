package com.anbang.qipai.dalianmeng.cqrs.q.dao;

import com.anbang.qipai.dalianmeng.cqrs.q.dbo.AllianceDbo;

import java.util.List;

public interface AllianceDboDao {
    void save(AllianceDbo allianceDbo);

    AllianceDbo findById(String id);

    long countAll();

    List<AllianceDbo> findByLianmengId(String lianmengId,String memberId);

    long countByAgentIdAndLianmengId(String agentId,String lianmengId);

    List<AllianceDbo> findByAgentIdAndLianmengId( String agentId,String lianmengId);

    List<AllianceDbo> findByMengzhuAndPage(int page, int size, String mengzhu);

    List<AllianceDbo> findByMengzhu(String mengzhu);

    void removeById(String id);

    long countByConditions(String agentId, String nickname, String phone, String userName);

    List<AllianceDbo> findByConditions(int page, int size, String agentId, String nickname, String phone, String userName);


    long countAmountByAgentId(String agentId);

    List<AllianceDbo> findByAgentId(int page, int size, String agentId);

    void updateDesc(String lianmengId, String desc);

    void updateSetting(String lianmengId, boolean renshuHide, boolean kongzhuoqianzhi,
                       boolean nicknameHide, boolean idHide, boolean banAlliance,boolean zhuomanHide,
                       int buzhunbeituichushichang, boolean zidongzhunbei, boolean lianmengIdHide);

}
