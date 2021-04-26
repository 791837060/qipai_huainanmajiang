package com.anbang.qipai.game.plan.dao;

import com.anbang.qipai.game.plan.bean.historicalresult.GameJuPlayerResult;
import com.anbang.qipai.game.plan.bean.members.MemberLatAndLon;

import java.util.List;


public interface MemberLatAndLonDao {
    void save(MemberLatAndLon memberLatAndLon);

    List<MemberLatAndLon> find(List<String> memberIds, String roomNo);

    MemberLatAndLon findBymemberId(String memberId);

    void del(String playerId);

    void paodekuaiDels(List<GameJuPlayerResult> playerResultList);

    void huangshibaDels(List<GameJuPlayerResult> playerResultList);

    void bohuDels(List<GameJuPlayerResult> playerResultList);

    void guandanDels(List<GameJuPlayerResult> playerResultList);

    void yangzhouMajiangDels(List<GameJuPlayerResult> juPlayerResultList);

    void yizhengMajiangDels(List<GameJuPlayerResult> juPlayerResultList);

    void doudizhuDels(List<GameJuPlayerResult> playerResultList);

    void bijiDels(List<GameJuPlayerResult> playerResultList);

    void taizhouMajiangDels(List<GameJuPlayerResult> juPlayerResultList);

    void tianchangxiaohuaDels(List<GameJuPlayerResult> juPlayerResultList);

    void taixingMajiangDels(List<GameJuPlayerResult> juPlayerResultList);

    void gaoyouMajiangDels(List<GameJuPlayerResult> juPlayerResultList);

    void hongzhongMajiangDels(List<GameJuPlayerResult> juPlayerResultList);

    void maanshanMajiangDels(List<GameJuPlayerResult> juPlayerResultList);

}
