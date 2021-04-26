package com.anbang.qipai.qinyouquan.cqrs.q.dao;

import com.anbang.qipai.qinyouquan.cqrs.q.dbo.AllianceDbo;

import java.util.List;

public interface AllianceDboDao {
    void save(AllianceDbo allianceDbo);

    AllianceDbo findById(String id);

    long countAll();

    List<AllianceDbo> findByLianmengId(String lianmengId,String memberId);

    long countByAgentIdAndLianmengId(String mengzhu,String lianmengId);

    List<AllianceDbo> findByAgentIdAndLianmengId( String mengzhu,String lianmengId);

    List<AllianceDbo> findByMengzhuAndPage(int page, int size, String mengzhu);

    List<AllianceDbo> findByMengzhu(String mengzhu);

    void removeById(String id);

    long countByConditions(String mengzhu, String nickname, String phone, String userName);

    List<AllianceDbo> findByConditions(int page, int size, String mengzhu, String nickname, String phone, String userName);


    long countAmountByAgentId(String mengzhu);

    List<AllianceDbo> findByAgentId(int page, int size, String mengzhu);

    void updateDesc(String lianmengId, String desc);

    void updateSetting(String lianmengId, boolean renshuHide, boolean kongzhuoqianzhi,
                       boolean nicknameHide, boolean idHide, boolean banAlliance,boolean zhuomanHide,
                       int buzhunbeituichushichang, boolean zidongzhunbei, boolean lianmengIdHide);

}
