package com.anbang.qipai.maanshanmajiang.cqrs.c.domain;

import com.dml.majiang.ju.Ju;
import com.dml.majiang.pai.MajiangPai;
import com.dml.majiang.pan.avaliablepai.AvaliablePaiFiller;

import java.util.*;

public class MaanshanMajiangFaRandomAvaliablePaiFiller implements AvaliablePaiFiller {

    private long seed;
    OptionalPlay optionalPlay;

    public MaanshanMajiangFaRandomAvaliablePaiFiller() {
    }

    public MaanshanMajiangFaRandomAvaliablePaiFiller(long seed, OptionalPlay optionalPlay) {
        this.seed = seed;
        this.optionalPlay = optionalPlay;
    }

    /**
     * 填充可用牌
     *
     * @param ju 当前局
     */
    @Override
    public void fillAvaliablePai(Ju ju) throws Exception {
        Set<MajiangPai> notPlaySet = new HashSet<>();//去除牌形
        notPlaySet.add(MajiangPai.chun);
        notPlaySet.add(MajiangPai.xia);
        notPlaySet.add(MajiangPai.qiu);
        notPlaySet.add(MajiangPai.dong);
        notPlaySet.add(MajiangPai.mei);
        notPlaySet.add(MajiangPai.lan);
        notPlaySet.add(MajiangPai.zhu);
        notPlaySet.add(MajiangPai.ju);
        if (optionalPlay.isWufeng()) {
            notPlaySet.add(MajiangPai.dongfeng);
            notPlaySet.add(MajiangPai.nanfeng);
            notPlaySet.add(MajiangPai.xifeng);
            notPlaySet.add(MajiangPai.beifeng);
            notPlaySet.add(MajiangPai.hongzhong);
            notPlaySet.add(MajiangPai.facai);
            notPlaySet.add(MajiangPai.baiban);
        }

        MajiangPai[] allMajiangPaiArray = MajiangPai.values();

        List<MajiangPai> playPaiTypeList = new ArrayList<>();
        for (MajiangPai pai : allMajiangPaiArray) {
            if (!notPlaySet.contains(pai)) {
                playPaiTypeList.add(pai);
            }
        }

        List<MajiangPai> allPaiList = new ArrayList<>();
        playPaiTypeList.forEach((paiType) -> {
            for (int i = 0; i < 4; i++) {
                allPaiList.add(paiType);
            }
        });

        Collections.shuffle(allPaiList, new Random(seed));  //乱牌
        ju.getCurrentPan().setAvaliablePaiList(allPaiList); //牌库可用牌
        ju.getCurrentPan().setPaiTypeList(playPaiTypeList); //可用牌面
        seed++;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public OptionalPlay getOptionalPlay() {
        return optionalPlay;
    }

    public void setOptionalPlay(OptionalPlay optionalPlay) {
        this.optionalPlay = optionalPlay;
    }
}
