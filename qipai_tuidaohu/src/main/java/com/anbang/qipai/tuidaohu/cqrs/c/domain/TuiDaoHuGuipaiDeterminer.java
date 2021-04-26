package com.anbang.qipai.tuidaohu.cqrs.c.domain;

import com.dml.majiang.ju.Ju;
import com.dml.majiang.pai.MajiangPai;
import com.dml.majiang.pan.Pan;
import com.dml.majiang.pan.guipai.GuipaiDeterminer;
import com.dml.majiang.player.MajiangPlayer;

public class TuiDaoHuGuipaiDeterminer implements GuipaiDeterminer {

    private long seed;
    private boolean hongzhonglaizi;

    public TuiDaoHuGuipaiDeterminer() {
    }

    public TuiDaoHuGuipaiDeterminer(long seed, boolean hongzhonglaizi) {
        this.seed = seed;
        this.hongzhonglaizi = hongzhonglaizi;
    }

    public TuiDaoHuGuipaiDeterminer(long seed) {
        this.seed = seed;
    }

    /**
     * 选择带红中，红中就是鬼牌
     * 不选就没有红中没有鬼牌
     *
     * @param ju 当前局
     * @throws Exception
     */
    @Override
    public void determineGuipai(Ju ju) throws Exception {
        Pan currentPan = ju.getCurrentPan();
        if (hongzhonglaizi) {
            MajiangPai guipaiType = MajiangPai.hongzhong;
            currentPan.publicGuipaiAndNotRemoveFromList(guipaiType); //公开并不移除鬼牌
            for (MajiangPlayer majiangPlayer : currentPan.getMajiangPlayerIdMajiangPlayerMap().values()) {
                majiangPlayer.addGuipaiType(guipaiType);//玩家添加鬼牌类型
            }
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
