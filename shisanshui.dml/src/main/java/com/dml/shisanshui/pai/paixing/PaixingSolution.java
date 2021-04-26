package com.dml.shisanshui.pai.paixing;

/**
 * 用于比较的牌型方案
 *
 * @author lsc
 */
public class PaixingSolution {

    /**
     * 头道
     */
    private Dao toudao;

    /**
     * 中道
     */
    private Dao zhongdao;

    /**
     * 尾道
     */
    private Dao weidao;

//	private boolean yitiaolong;

    public Dao getToudao() {
        return toudao;
    }

    public void setToudao(Dao toudao) {
        this.toudao = toudao;
    }

    public Dao getZhongdao() {
        return zhongdao;
    }

    public void setZhongdao(Dao zhongdao) {
        this.zhongdao = zhongdao;
    }

    public Dao getWeidao() {
        return weidao;
    }

    public void setWeidao(Dao weidao) {
        this.weidao = weidao;
    }

//    public boolean isYitiaolong() {
//        return yitiaolong;
//    }
//
//    public void setYitiaolong(boolean yitiaolong) {
//        this.yitiaolong = yitiaolong;
//    }

}
