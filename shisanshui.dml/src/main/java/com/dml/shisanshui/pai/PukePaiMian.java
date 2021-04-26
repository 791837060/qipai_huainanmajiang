package com.dml.shisanshui.pai;

/**
 * 扑克所有54个牌面的枚举,按点数为主，花色位次的顺序排序
 *
 * @author lsc
 */
public enum PukePaiMian {
    fangkuaier(HuaSe.fangkuai, DianShu.er), meihuaer(HuaSe.meihua, DianShu.er), hongxiner(HuaSe.hongxin, DianShu.er), heitaoer(HuaSe.heitao, DianShu.er),
    fangkuaisan(HuaSe.fangkuai, DianShu.san), meihuasan(HuaSe.meihua, DianShu.san), hongxinsan(HuaSe.hongxin, DianShu.san), heitaosan(HuaSe.heitao, DianShu.san),
    fangkuaisi(HuaSe.fangkuai, DianShu.si), meihuasi(HuaSe.meihua, DianShu.si), hongxinsi(HuaSe.hongxin, DianShu.si), heitaosi(HuaSe.heitao, DianShu.si),
    fangkuaiwu(HuaSe.fangkuai, DianShu.wu), meihuawu(HuaSe.meihua, DianShu.wu), hongxinwu(HuaSe.hongxin, DianShu.wu), heitaowu(HuaSe.heitao, DianShu.wu),
    fangkuailiu(HuaSe.fangkuai,DianShu.liu), meihualiu(HuaSe.meihua, DianShu.liu), hongxinliu(HuaSe.hongxin, DianShu.liu), heitaoliu(HuaSe.heitao, DianShu.liu),
    fangkuaiqi(HuaSe.fangkuai, DianShu.qi), meihuaqi(HuaSe.meihua, DianShu.qi), hongxinqi(HuaSe.hongxin, DianShu.qi), heitaoqi(HuaSe.heitao, DianShu.qi),
    fangkuaiba(HuaSe.fangkuai, DianShu.ba), meihuaba(HuaSe.meihua, DianShu.ba), hongxinba(HuaSe.hongxin, DianShu.ba), heitaoba(HuaSe.heitao, DianShu.ba),
    fangkuaijiu(HuaSe.fangkuai, DianShu.jiu), meihuajiu(HuaSe.meihua, DianShu.jiu), hongxinjiu(HuaSe.hongxin, DianShu.jiu), heitaojiu(HuaSe.heitao, DianShu.jiu),
    fangkuaishi(HuaSe.fangkuai, DianShu.shi), meihuashi(HuaSe.meihua, DianShu.shi), hongxinshi(HuaSe.hongxin, DianShu.shi), heitaoshi(HuaSe.heitao, DianShu.shi),
    fangkuaiJ(HuaSe.fangkuai, DianShu.J), meihuaJ(HuaSe.meihua, DianShu.J), hongxinJ(HuaSe.hongxin, DianShu.J), heitaoJ(HuaSe.heitao, DianShu.J),
    fangkuaiQ(HuaSe.fangkuai, DianShu.Q), meihuaQ(HuaSe.meihua, DianShu.Q), hongxinQ(HuaSe.hongxin, DianShu.Q), heitaoQ(HuaSe.heitao, DianShu.Q),
    fangkuaiK(HuaSe.fangkuai, DianShu.K), meihuaK(HuaSe.meihua, DianShu.K), hongxinK(HuaSe.hongxin, DianShu.K), heitaoK(HuaSe.heitao, DianShu.K),
    fangkuaiA(HuaSe.fangkuai, DianShu.A), meihuaA(HuaSe.meihua, DianShu.A), hongxinA(HuaSe.hongxin, DianShu.A), heitaoA(HuaSe.heitao, DianShu.A),
    xiaowang(null, DianShu.xiaowang), dawang(null, DianShu.dawang);

    private final HuaSe huaSe;

    private final DianShu dianShu;

    PukePaiMian(HuaSe huaSe, DianShu dianShu) {
        this.huaSe = huaSe;
        this.dianShu = dianShu;
    }

    public HuaSe huaSe() {
        return huaSe;
    }

    public DianShu dianShu() {
        return dianShu;
    }

}
