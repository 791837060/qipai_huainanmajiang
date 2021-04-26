package com.dml.paodekuai.pai.waihao;

import com.dml.paodekuai.pai.dianshuzu.*;
import com.dml.puke.pai.DianShu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DanGeZhadanDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DanzhangDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DuiziDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.LianduiDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.SanzhangDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.ShunziDianShuZu;
import com.dml.puke.wanfa.dianshu.paizu.DianShuZuPaiZu;

public class PaodekuaiWaihaoGenerator implements WaihaoGenerator {

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
		// 顺子
		if (dianShuZu instanceof ShunziDianShuZu) {
			dianShuZuPaiZu.setWaihao("shunzi");
		}
		// 连对
		if (dianShuZu instanceof LianduiDianShuZu) {
			dianShuZuPaiZu.setWaihao("liandui");
		}

		// 单个炸弹
		if (dianShuZu instanceof DanGeZhadanDianShuZu) {
			dianShuZuPaiZu.setWaihao("zhadan");
		}

		// 333炸
		if (dianShuZu instanceof SanBoomDianShuZu) {
			dianShuZuPaiZu.setWaihao("zhadan");
		}

		// aaa炸
		if (dianShuZu instanceof ABoomDianShuZu) {
			dianShuZuPaiZu.setWaihao("zhadan");
		}

		// 三带二
		if (dianShuZu instanceof SandaierDianShuZu) {
			dianShuZuPaiZu.setWaihao("sandaier");
		}

		// 飞机
		if (dianShuZu instanceof FeijiDianShuZu) {
			dianShuZuPaiZu.setWaihao("feiji");
		}

		// 三张
		if (dianShuZu instanceof SanzhangDianShuZu) {
			SanzhangDianShuZu sanzhangDianShuZu = (SanzhangDianShuZu) dianShuZu;
			dianShuZuPaiZu.setWaihao(3 + sanzhangDianShuZu.getDianShu().name());
		}

		// 四带二
		if (dianShuZu instanceof SidaierDianShuZu) {
			dianShuZuPaiZu.setWaihao("sidiasan");
		}

		// 四带三
		if (dianShuZu instanceof SidaisanDianShuZu) {
			dianShuZuPaiZu.setWaihao("sidaisan");
		}

	}
}
