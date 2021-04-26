package com.anbang.qipai.doudizhu.cqrs.c.domain;

import com.dml.doudizhu.pai.dianshuzu.FeijiDianShuZu;
import com.dml.doudizhu.pai.dianshuzu.HuojianDianShuZu;
import com.dml.doudizhu.pai.dianshuzu.SandaierDianShuZu;
import com.dml.doudizhu.pai.dianshuzu.SandaiyiDianShuZu;
import com.dml.doudizhu.pai.dianshuzu.SidaierDianShuZu;
import com.dml.doudizhu.pai.dianshuzu.SidaiyiDianShuZu;
import com.dml.doudizhu.pai.waihao.WaihaoGenerator;
import com.dml.puke.pai.DianShu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DanGeZhadanDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DanzhangDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DuiziDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.LianduiDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.LiansanzhangDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.SanzhangDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.ShunziDianShuZu;
import com.dml.puke.wanfa.dianshu.paizu.DianShuZuPaiZu;

public class DoudizhuWaihaoGenerator implements WaihaoGenerator {

	@Override
	public void generateWaihao(DianShuZuPaiZu dianShuZuPaiZu) {
		DianShuZu dianShuZu = dianShuZuPaiZu.getDianShuZu();
		// 单张
		if (dianShuZu instanceof DanzhangDianShuZu) {
			DianShu dianshu = ((DanzhangDianShuZu) dianShuZu).getDianShu();
			dianShuZuPaiZu.setWaihao(dianshu.name());
		}
		// 对子
		if (dianShuZu instanceof DuiziDianShuZu) {
			DianShu dianshu = ((DuiziDianShuZu) dianShuZu).getDianShu();
			dianShuZuPaiZu.setWaihao(2 + dianshu.name());
		}
		// 三张
		if (dianShuZu instanceof SanzhangDianShuZu) {
			DianShu dianshu = ((SanzhangDianShuZu) dianShuZu).getDianShu();
			dianShuZuPaiZu.setWaihao(3 + dianshu.name());
		}
		// 顺子
		if (dianShuZu instanceof ShunziDianShuZu) {
			dianShuZuPaiZu.setWaihao("shunzi");
		}
		// 连对
		if (dianShuZu instanceof LianduiDianShuZu) {
			dianShuZuPaiZu.setWaihao("liandui");
		}
		// 连三张
		if (dianShuZu instanceof LiansanzhangDianShuZu) {
			dianShuZuPaiZu.setWaihao("feiji");
		}
		// 飞机
		if (dianShuZu instanceof FeijiDianShuZu) {
			dianShuZuPaiZu.setWaihao("feiji");
		}
		// 三带一
		if (dianShuZu instanceof SandaiyiDianShuZu) {
			dianShuZuPaiZu.setWaihao("sandaiyi");
		}
		// 三带二
		if (dianShuZu instanceof SandaierDianShuZu) {
			dianShuZuPaiZu.setWaihao("sandaier");
		}
		// 四带二
		if (dianShuZu instanceof SidaiyiDianShuZu) {
			dianShuZuPaiZu.setWaihao("sidaier");
		}
		if (dianShuZu instanceof SidaierDianShuZu) {
			dianShuZuPaiZu.setWaihao("sidaier");
		}
		// 单个炸弹
		if (dianShuZu instanceof DanGeZhadanDianShuZu) {
			dianShuZuPaiZu.setWaihao("zhadan");
		}
		// 王炸
		if (dianShuZu instanceof HuojianDianShuZu) {
			dianShuZuPaiZu.setWaihao("huojian");
		}
	}

}
