package com.anbang.qipai.paodekuai.cqrs.c.domain;

import com.dml.paodekuai.ju.Ju;
import com.dml.paodekuai.preparedapai.avaliablepai.AvaliablePaiFiller;
import com.dml.paodekuai.wanfa.OptionalPlay;
import com.dml.puke.pai.PukePai;
import com.dml.puke.pai.PukePaiMian;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 一桌牌去掉大小王三个2一个A，共48张牌（保留黑桃2，删除黑桃A）
 */
public class PaodekuaiCutTypeAvaliablePaiFiller implements AvaliablePaiFiller {

    private OptionalPlay optionalPlay;


    public PaodekuaiCutTypeAvaliablePaiFiller() {
    }

    @Override
    public void fillAvaliablePai(Ju ju) throws Exception {
        Set<PukePaiMian> notPlaySet = new HashSet<>();
        if (optionalPlay.isShiwuzhang()) {
            notPlaySet.add(PukePaiMian.dawang);
            notPlaySet.add(PukePaiMian.xiaowang);
            notPlaySet.add(PukePaiMian.hongxiner);
            notPlaySet.add(PukePaiMian.meihuaer);
            notPlaySet.add(PukePaiMian.fangkuaier);
            notPlaySet.add(PukePaiMian.hongxinA);
            notPlaySet.add(PukePaiMian.fangkuaiA);
            notPlaySet.add(PukePaiMian.meihuaA);
            notPlaySet.add(PukePaiMian.fangkuaiK);
            notPlaySet.add(PukePaiMian.hongxiner2);//掼蛋中新加的 为了不影响跑得快要移除
        } else if (optionalPlay.isShiliuzhang()) {
            notPlaySet.add(PukePaiMian.dawang);
            notPlaySet.add(PukePaiMian.xiaowang);
            notPlaySet.add(PukePaiMian.hongxiner);
            notPlaySet.add(PukePaiMian.meihuaer);
            notPlaySet.add(PukePaiMian.fangkuaier);
            notPlaySet.add(PukePaiMian.heitaoA);
            notPlaySet.add(PukePaiMian.hongxiner2);//掼蛋中新加的 为了不影响跑得快要移除
        } else {
            notPlaySet.add(PukePaiMian.dawang);
            notPlaySet.add(PukePaiMian.xiaowang);
            notPlaySet.add(PukePaiMian.fangkuaier);
            notPlaySet.add(PukePaiMian.hongxiner2);//掼蛋中新加的 为了不影响跑得快要移除
        }
        List<PukePaiMian> playPaiTypeList = new ArrayList<>();
        // 移除不可用牌
        for (PukePaiMian pukePaiMian : PukePaiMian.values()) {
            if (!notPlaySet.contains(pukePaiMian)) {
                playPaiTypeList.add(pukePaiMian);
            }
        }

        List<PukePai> allPaiList = new ArrayList<>();
        // 生成牌
        int id = 0;
        for (PukePaiMian paiType : playPaiTypeList) {
            PukePai pai = new PukePai();
            pai.setId(id);
            pai.setPaiMian(paiType);
            allPaiList.add(pai);
            id++;
        }

        ju.getCurrentPan().setAvaliablePaiList(allPaiList);
    }

    public OptionalPlay getOptionalPlay() {
        return optionalPlay;
    }

    public void setOptionalPlay(OptionalPlay optionalPlay) {
        this.optionalPlay = optionalPlay;
    }
}
