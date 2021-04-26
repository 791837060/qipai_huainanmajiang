package com.dml.puke.pai;

/**
 * 扑克所有54个牌面的枚举
 *
 * @author Neo
 */
public enum PukePaiMian {
    heitaosan(HuaSe.heitao, DianShu.san), hongxinsan(HuaSe.hongxin, DianShu.san), meihuasan(HuaSe.meihua, DianShu.san), fangkuaisan(HuaSe.fangkuai, DianShu.san),
    heitaosi(HuaSe.heitao, DianShu.si), hongxinsi(HuaSe.hongxin, DianShu.si), meihuasi(HuaSe.meihua, DianShu.si), fangkuaisi(HuaSe.fangkuai, DianShu.si),
    heitaowu(HuaSe.heitao, DianShu.wu), hongxinwu(HuaSe.hongxin, DianShu.wu), meihuawu(HuaSe.meihua, DianShu.wu), fangkuaiwu(HuaSe.fangkuai, DianShu.wu),
    heitaoliu(HuaSe.heitao, DianShu.liu), hongxinliu(HuaSe.hongxin, DianShu.liu), meihualiu(HuaSe.meihua, DianShu.liu), fangkuailiu(HuaSe.fangkuai, DianShu.liu),
    heitaoqi(HuaSe.heitao, DianShu.qi), hongxinqi(HuaSe.hongxin, DianShu.qi), meihuaqi(HuaSe.meihua, DianShu.qi), fangkuaiqi(HuaSe.fangkuai, DianShu.qi),
    heitaoba(HuaSe.heitao, DianShu.ba), hongxinba(HuaSe.hongxin, DianShu.ba), meihuaba(HuaSe.meihua, DianShu.ba), fangkuaiba(HuaSe.fangkuai, DianShu.ba),
    heitaojiu(HuaSe.heitao, DianShu.jiu), hongxinjiu(HuaSe.hongxin, DianShu.jiu), meihuajiu(HuaSe.meihua, DianShu.jiu), fangkuaijiu(HuaSe.fangkuai, DianShu.jiu),
    heitaoshi(HuaSe.heitao, DianShu.shi), hongxinshi(HuaSe.hongxin, DianShu.shi), meihuashi(HuaSe.meihua, DianShu.shi), fangkuaishi(HuaSe.fangkuai, DianShu.shi),
    heitaoJ(HuaSe.heitao, DianShu.J), hongxinJ(HuaSe.hongxin, DianShu.J), meihuaJ(HuaSe.meihua, DianShu.J), fangkuaiJ(HuaSe.fangkuai, DianShu.J),
    heitaoQ(HuaSe.heitao, DianShu.Q), hongxinQ(HuaSe.hongxin, DianShu.Q), meihuaQ(HuaSe.meihua, DianShu.Q), fangkuaiQ(HuaSe.fangkuai, DianShu.Q),
    heitaoK(HuaSe.heitao, DianShu.K), hongxinK(HuaSe.hongxin, DianShu.K), meihuaK(HuaSe.meihua, DianShu.K), fangkuaiK(HuaSe.fangkuai, DianShu.K),
    heitaoA(HuaSe.heitao, DianShu.A), hongxinA(HuaSe.hongxin, DianShu.A), meihuaA(HuaSe.meihua, DianShu.A), fangkuaiA(HuaSe.fangkuai, DianShu.A),
    heitaoer(HuaSe.heitao, DianShu.er), hongxiner(HuaSe.hongxin, DianShu.er), meihuaer(HuaSe.meihua, DianShu.er), fangkuaier(HuaSe.fangkuai, DianShu.er),
    xiaowang(null, DianShu.xiaowang), dawang(null, DianShu.dawang), hongxiner2(HuaSe.hongxin, DianShu.hongxiner2);

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
