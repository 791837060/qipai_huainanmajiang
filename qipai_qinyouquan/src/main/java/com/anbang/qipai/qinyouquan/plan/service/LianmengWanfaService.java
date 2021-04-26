package com.anbang.qipai.qinyouquan.plan.service;

import com.anbang.qipai.qinyouquan.plan.bean.LianmengWanfa;
import com.anbang.qipai.qinyouquan.plan.dao.LianmengWanfaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LianmengWanfaService {
    @Autowired
    private LianmengWanfaDao lianmengWanfaDao;

    public void addNewWanfa(LianmengWanfa lianmengWanfa){
        lianmengWanfaDao.save(lianmengWanfa);
    }

    public List<LianmengWanfa> findLianmengWanfaBylianmengId(String lianmengId){
        return lianmengWanfaDao.findByLianmengId(lianmengId);
    }

    public LianmengWanfa findLianmengWanfaByWanfaId(String wanfaId){
        return lianmengWanfaDao.findByWanfaId(wanfaId);
    }

    public void updateLianmengWanfa(LianmengWanfa lianmengWanfa){
        lianmengWanfaDao.updateLianmengWanfa(lianmengWanfa);
    }

    public void deleteLianmengWanfa(String wanfaId){
        lianmengWanfaDao.deleteLianmengWanfa(wanfaId);
    }


    public List<LianmengWanfa> findAllWafabyLianmengId(int page, int size, String lianmengId) {
        List<LianmengWanfa> lianmengWanfaList=lianmengWanfaDao.findAllWafabyLianmengId(page,size,lianmengId);
        return lianmengWanfaList;

    }
}
