package com.anbang.qipai.game.plan.service;


import com.anbang.qipai.game.plan.bean.historicalresult.GameJuPlayerResult;
import com.anbang.qipai.game.plan.bean.members.MemberLatAndLon;
import com.anbang.qipai.game.plan.dao.MemberLatAndLonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberLatAndLonService {

    @Autowired
    private MemberLatAndLonDao memberLatAndLonDao;

    public void save(MemberLatAndLon memberLatAndLon) {
        memberLatAndLonDao.save(memberLatAndLon);
    }

    public List<MemberLatAndLon> find(List<String> memberIds, String roomNo) {
        return memberLatAndLonDao.find(memberIds, roomNo);
    }

    public MemberLatAndLon findBymemberId(String memberId) {
        return memberLatAndLonDao.findBymemberId(memberId);
    }

    public void del(String playerId) {
        memberLatAndLonDao.del(playerId);
    }

    public void paodekuaiDels(List<GameJuPlayerResult> playerResultList) {
        memberLatAndLonDao.paodekuaiDels(playerResultList);
    }

    public void huangshibaDels(List<GameJuPlayerResult> playerResultList) {
        memberLatAndLonDao.huangshibaDels(playerResultList);
    }

    public void bohuDels(List<GameJuPlayerResult> playerResultList) {
        memberLatAndLonDao.bohuDels(playerResultList);
    }

    public void guandanDels(List<GameJuPlayerResult> playerResultList) {
        memberLatAndLonDao.guandanDels(playerResultList);
    }

    public void yangzhouMajiangDels(List<GameJuPlayerResult> juPlayerResultList) {
        memberLatAndLonDao.yangzhouMajiangDels(juPlayerResultList);
    }

    public void yizhengMajiangDels(List<GameJuPlayerResult> juPlayerResultList) {
        memberLatAndLonDao.yizhengMajiangDels(juPlayerResultList);
    }

    public void doudizhuDels(List<GameJuPlayerResult> playerResultList) {
        memberLatAndLonDao.doudizhuDels(playerResultList);
    }

    public void taizhouMajiangDels(List<GameJuPlayerResult> juPlayerResultList) {
        memberLatAndLonDao.taizhouMajiangDels(juPlayerResultList);
    }

    public void bijiDels(List<GameJuPlayerResult> playerResultList) {
        memberLatAndLonDao.bijiDels(playerResultList);
    }

    public void tianchangxiaohuaDels(List<GameJuPlayerResult> juPlayerResultList) {
        memberLatAndLonDao.tianchangxiaohuaDels(juPlayerResultList);
    }

    public void taixingMajiangDels(List<GameJuPlayerResult> juPlayerResultList) {
        memberLatAndLonDao.taixingMajiangDels(juPlayerResultList);
    }

    public void gaoyouMajiangDels(List<GameJuPlayerResult> juPlayerResultList) {
        memberLatAndLonDao.gaoyouMajiangDels(juPlayerResultList);
    }

    public void hongzhongMajiangDels(List<GameJuPlayerResult> juPlayerResultList) {
        memberLatAndLonDao.hongzhongMajiangDels(juPlayerResultList);
    }

    public void maanshanMajiangDels(List<GameJuPlayerResult> juPlayerResultList) {
        memberLatAndLonDao.maanshanMajiangDels(juPlayerResultList);
    }

}
