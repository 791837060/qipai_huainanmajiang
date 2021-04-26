package com.dml.doudizhu.preparedapai.luanpai;

import java.util.ArrayList;
import java.util.List;

import com.dml.doudizhu.ju.Ju;
import com.dml.doudizhu.pan.Pan;
import com.dml.puke.pai.PukePai;

/**
 * 测试用
 * 
 * @author lsc
 *
 */
public class DoNoThingLuanPaiStrategy implements LuanPaiStrategy {

	@Override
	public void luanpai(Ju ju) throws Exception {
		Pan currentPan = ju.getCurrentPan();
		List<PukePai> allPaiList = currentPan.getAvaliablePaiList();
		List<PukePai> newAllPaiList = new ArrayList<>();
		while (!allPaiList.isEmpty()) {
			newAllPaiList.add(allPaiList.remove(0));
		}
		ju.getCurrentPan().setAvaliablePaiList(newAllPaiList);
	}

}
