package com.dml.shuangkou.pai.waihao;

import com.dml.puke.pai.DianShu;
import com.dml.puke.wanfa.dianshu.dianshuzu.*;
import com.dml.puke.wanfa.dianshu.paizu.DianShuZuPaiZu;
import com.dml.shuangkou.pai.dianshuzu.LianXuZhadanDianShuZu;
import com.dml.shuangkou.pai.dianshuzu.SandaierDianShuZu;
import com.dml.shuangkou.pai.dianshuzu.WangZhadanDianShuZu;

public class ShuangkouWaihaoGenerator implements WaihaoGenerator {

    @Override
    public void generateWaihao(DianShuZuPaiZu dianShuZuPaiZu) {
        DianShuZu dianShuZu = dianShuZuPaiZu.getDianShuZu();
        //单张
        if (dianShuZu instanceof DanzhangDianShuZu) {
            DianShu dianshu = ((DanzhangDianShuZu) dianShuZu).getDianShu();
            dianShuZuPaiZu.setWaihao(dianshu.name());
        }
        //对子
        if (dianShuZu instanceof DuiziDianShuZu) {
            DianShu dianshu = ((DuiziDianShuZu) dianShuZu).getDianShu();
            dianShuZuPaiZu.setWaihao(2 + dianshu.name());
        }
        //三张
        if (dianShuZu instanceof SanzhangDianShuZu) {
            DianShu dianshu = ((SanzhangDianShuZu) dianShuZu).getDianShu();
            dianShuZuPaiZu.setWaihao(3 + dianshu.name());
        }
        //顺子
        if (dianShuZu instanceof ShunziDianShuZu) {
            dianShuZuPaiZu.setWaihao("shunzi");
        }
        //连对
        if (dianShuZu instanceof LianduiDianShuZu) {
            dianShuZuPaiZu.setWaihao("jiemeidui");
        }
        // 连三张(钢板)
        if (dianShuZu instanceof LiansanzhangDianShuZu) {
            dianShuZuPaiZu.setWaihao("santuobei");
        }
        //三带二
        if (dianShuZu instanceof SandaierDianShuZu) {
            dianShuZuPaiZu.setWaihao("sandaier");
        }
        //同花顺
        if (dianShuZu instanceof TonghuashunDianShuZu) {
            dianShuZuPaiZu.setWaihao("tonghuashun");
        }
        //单个炸弹
        if (dianShuZu instanceof DanGeZhadanDianShuZu) {
            DianShu dianshu = ((DanGeZhadanDianShuZu) dianShuZu).getDianShu();
            dianShuZuPaiZu.setWaihao(((DanGeZhadanDianShuZu) dianShuZu).getSize() + dianshu.name());
        }
        //连续炸弹
        if (dianShuZu instanceof LianXuZhadanDianShuZu) {
            LianXuZhadanDianShuZu lianXuZhadanDianShuZu = (LianXuZhadanDianShuZu) dianShuZu;
            DianShu[] lianXuDianShuArray = lianXuZhadanDianShuZu.getLianXuDianShuArray();
            int lianxuCount = lianXuDianShuArray.length;
            if (lianxuCount < 3) {
                lianxuCount = 3;
            }
            dianShuZuPaiZu.setWaihao(lianXuZhadanDianShuZu.getXianShu() + "xian" + lianxuCount + "lianzha");
        }
        // 王炸
        if (dianShuZu instanceof WangZhadanDianShuZu) {
            WangZhadanDianShuZu wangZhadanDianShuZu = (WangZhadanDianShuZu) dianShuZu;
            int xiaowangCount = wangZhadanDianShuZu.getXiaowangCount();
            int dawangCount = wangZhadanDianShuZu.getDawangCount();
            if (xiaowangCount + dawangCount == 3) {
                dianShuZuPaiZu.setWaihao("sanwangzha");
            } else if (xiaowangCount + dawangCount == 4) {
                dianShuZuPaiZu.setWaihao("sisiling");
            }
        }

    }

}
