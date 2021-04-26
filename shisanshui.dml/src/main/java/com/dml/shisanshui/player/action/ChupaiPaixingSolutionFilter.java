package com.dml.shisanshui.player.action;

import java.util.List;
import java.util.Map;

import com.dml.shisanshui.pai.PukePai;
import com.dml.shisanshui.pai.paixing.Dao;
import com.dml.shisanshui.pai.paixing.PaixingSolution;

public interface ChupaiPaixingSolutionFilter {

	List<PaixingSolution> filter(Map<Integer, PukePai> allShoupai, List<Dao> daoList);
}
