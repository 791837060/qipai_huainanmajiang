package com.anbang.qipai.zongyangmajiang.cqrs.c.domain;

import com.dml.majiang.ju.Ju;
import com.dml.majiang.pai.MajiangPai;
import com.dml.majiang.pan.Pan;
import com.dml.majiang.pan.guipai.GuipaiDeterminer;
import com.dml.majiang.player.MajiangPlayer;

import java.util.List;
import java.util.Random;

public class ZongyangMajiangGuipaiDeterminer implements GuipaiDeterminer {

    private long seed;
    private boolean hongzhonglaizi;

    public ZongyangMajiangGuipaiDeterminer() {
    }

    public ZongyangMajiangGuipaiDeterminer(long seed, boolean hongzhonglaizi) {
        this.seed = seed;
        this.hongzhonglaizi = hongzhonglaizi;
    }

    public ZongyangMajiangGuipaiDeterminer(long seed) {
        this.seed = seed;
    }

    /**
     * 随机鬼牌
     * @param ju 当前局
     * @throws Exception
     */
    @Override
    public void determineGuipai(Ju ju) throws Exception {
        Pan currentPan = ju.getCurrentPan();
        List<MajiangPai> paiTypeList = currentPan.getPaiTypeList();
        Random r = new Random(seed + currentPan.getNo());
        MajiangPai guipaiType = paiTypeList.get(r.nextInt(paiTypeList.size()));
        currentPan.publicGuipaiAndRemoveFromList(guipaiType);

        for (MajiangPlayer majiangPlayer : currentPan.getMajiangPlayerIdMajiangPlayerMap().values()) {
            majiangPlayer.addGuipaiType(guipaiType);
        }
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public boolean isHongzhonglaizi() {
        return hongzhonglaizi;
    }

    public void setHongzhonglaizi(boolean hongzhonglaizi) {
        this.hongzhonglaizi = hongzhonglaizi;
    }
}
