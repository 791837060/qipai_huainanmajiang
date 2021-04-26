package com.anbang.qipai.tuidaohu.cqrs.c.domain;

import com.dml.majiang.ju.Ju;
import com.dml.majiang.pai.MajiangPai;
import com.dml.majiang.pan.avaliablepai.AvaliablePaiFiller;

import java.util.*;

public class TuiDaoHuRemoveZhongOrFengFaRandomAvaliablePaiFiller implements AvaliablePaiFiller {

    private long seed;
    OptionalPlay optionalPlay;

    public TuiDaoHuRemoveZhongOrFengFaRandomAvaliablePaiFiller() {
    }

    public TuiDaoHuRemoveZhongOrFengFaRandomAvaliablePaiFiller(long seed, OptionalPlay optionalPlay) {
        this.seed = seed;
        this.optionalPlay = optionalPlay;
    }

    /**
     * 填充可用牌
     * 可选是否去掉风牌、发白和红中
     *
     * @param ju 当前局
     * @throws Exception
     */
    @Override
    public void fillAvaliablePai(Ju ju) throws Exception {
        Set<MajiangPai> notPlaySet = new HashSet<>(); //去除牌形
        notPlaySet.add(MajiangPai.chun);
        notPlaySet.add(MajiangPai.xia);
        notPlaySet.add(MajiangPai.qiu);
        notPlaySet.add(MajiangPai.dong);
        notPlaySet.add(MajiangPai.mei);
        notPlaySet.add(MajiangPai.lan);
        notPlaySet.add(MajiangPai.zhu);
        notPlaySet.add(MajiangPai.ju);
        notPlaySet.add(MajiangPai.dongfeng);
        notPlaySet.add(MajiangPai.nanfeng);
        notPlaySet.add(MajiangPai.xifeng);
        notPlaySet.add(MajiangPai.beifeng);
        notPlaySet.add(MajiangPai.facai);
        notPlaySet.add(MajiangPai.baiban);

        MajiangPai[] allMajiangPaiArray = MajiangPai.values();
        List<MajiangPai> playPaiTypeList = new ArrayList<>();
        for (int i = 0; i < allMajiangPaiArray.length; i++) {
            MajiangPai pai = allMajiangPaiArray[i];
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
