package com.dml.shisanshui.player.action;

import java.util.List;
import java.util.Map;

import com.dml.shisanshui.pai.PukePai;
import com.dml.shisanshui.pai.paixing.Dao;

public interface ChupaiDaoCalculator {

	List<Dao> generateAllPaixingSolution(Map<Integer, PukePai> allShoupai);

}
