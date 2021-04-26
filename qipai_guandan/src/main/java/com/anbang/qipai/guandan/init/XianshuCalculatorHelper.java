package com.anbang.qipai.guandan.init;

import java.util.HashMap;
import java.util.Map;

public class XianshuCalculatorHelper {

	private static Map<String, Integer> xianshuCountMap;
	private static Map<String, Integer> gongxianFenCountMap;

	public static void calculateXianshu() {
		long startTime = System.currentTimeMillis();
		System.out.println("开始计算线数倍数：");
		xianshuCountMap = new HashMap<>();
		gongxianFenCountMap = new HashMap<>();
		calculateSiXian();
		calculateWuXian();
		calculateLiuXian();
		calculateQiXian();
		calculateBaXian();
		calculateJiuXian();
		calculateShiXian();
		calculateShiyiXian();
		calculateShierXian();
		long endTime = System.currentTimeMillis();
		System.out.println("线数倍数计算结束:" + (endTime - startTime) + "毫秒");
	}

	public static Map<String, Integer> getXianshuCountMap() {
		return xianshuCountMap;
	}

	public static void setXianshuCountMap(Map<String, Integer> xianshuCountMap) {
		XianshuCalculatorHelper.xianshuCountMap = xianshuCountMap;
	}

	public static Map<String, Integer> getGongxianFenCountMap() {
		return gongxianFenCountMap;
	}

	public static void setGongxianFenCountMap(Map<String, Integer> gongxianFenCountMap) {
		XianshuCalculatorHelper.gongxianFenCountMap = gongxianFenCountMap;
	}

	/**
	 * 计算四线倍数及贡献分
	 */
	private static void calculateSiXian() {
		String s = "011110000";
		// 2
		xianshuCountMap.put("100000000", 4);
		gongxianFenCountMap.put("100000000", 0);

		xianshuCountMap.put("200000000", 4);
		gongxianFenCountMap.put("200000000", 0);
	}

	/**
	 * 计算五线倍数及贡献分
	 */
	private static void calculateWuXian() {
		// 4
		xianshuCountMap.put("300000000", 5);
		gongxianFenCountMap.put("300000000", 0);

		xianshuCountMap.put("210000000", 5);
		gongxianFenCountMap.put("210000000", 0);

		xianshuCountMap.put("110000000", 5);
		gongxianFenCountMap.put("110000000", 0);

		xianshuCountMap.put("010000000", 5);
		gongxianFenCountMap.put("010000000", 0);
	}

	/**
	 * 计算六线倍数及贡献分
	 */
	private static void calculateLiuXian() {
		// 12
		xianshuCountMap.put("400000000", 6);
		gongxianFenCountMap.put("400000000", 4);

		xianshuCountMap.put("310000000", 6);
		gongxianFenCountMap.put("310000000", 4);

		xianshuCountMap.put("301000000", 6);
		gongxianFenCountMap.put("301000000", 4);

		xianshuCountMap.put("220000000", 6);
		gongxianFenCountMap.put("220000000", 4);

		xianshuCountMap.put("211000000", 6);
		gongxianFenCountMap.put("211000000", 4);

		xianshuCountMap.put("201000000", 6);
		gongxianFenCountMap.put("201000000", 4);

		xianshuCountMap.put("120000000", 6);
		gongxianFenCountMap.put("120000000", 4);

		xianshuCountMap.put("111000000", 6);
		gongxianFenCountMap.put("111000000", 4);

		xianshuCountMap.put("101000000", 6);
		gongxianFenCountMap.put("101000000", 4);

		xianshuCountMap.put("020000000", 6);
		gongxianFenCountMap.put("020000000", 4);

		xianshuCountMap.put("011000000", 6);
		gongxianFenCountMap.put("011000000", 4);

		xianshuCountMap.put("001000000", 6);
		gongxianFenCountMap.put("001000000", 4);
	}

	/**
	 * 计算七线倍数及贡献分
	 */
	private static void calculateQiXian() {
		// 39
		xianshuCountMap.put("500000000", 7);
		gongxianFenCountMap.put("500000000", 8);

		xianshuCountMap.put("410000000", 7);
		gongxianFenCountMap.put("410000000", 8);

		xianshuCountMap.put("401000000", 7);
		gongxianFenCountMap.put("401000000", 8);

		xianshuCountMap.put("320000000", 7);
		gongxianFenCountMap.put("320000000", 8);

		xianshuCountMap.put("311000000", 7);
		gongxianFenCountMap.put("311000000", 8);

		xianshuCountMap.put("302000000", 7);
		gongxianFenCountMap.put("302000000", 8);

		xianshuCountMap.put("300100000", 7);
		gongxianFenCountMap.put("300100000", 8);

		xianshuCountMap.put("230000000", 7);
		gongxianFenCountMap.put("230000000", 8);

		xianshuCountMap.put("221000000", 7);
		gongxianFenCountMap.put("221000000", 8);

		xianshuCountMap.put("212000000", 7);
		gongxianFenCountMap.put("212000000", 8);

		xianshuCountMap.put("210100000", 7);
		gongxianFenCountMap.put("210100000", 8);

		xianshuCountMap.put("202000000", 7);
		gongxianFenCountMap.put("202000000", 8);

		xianshuCountMap.put("200100000", 7);
		gongxianFenCountMap.put("200100000", 8);

		xianshuCountMap.put("130000000", 7);
		gongxianFenCountMap.put("130000000", 8);

		xianshuCountMap.put("121000000", 7);
		gongxianFenCountMap.put("121000000", 8);

		xianshuCountMap.put("112000000", 7);
		gongxianFenCountMap.put("112000000", 8);

		xianshuCountMap.put("110100000", 7);
		gongxianFenCountMap.put("110100000", 8);

		xianshuCountMap.put("102000000", 7);
		gongxianFenCountMap.put("102000000", 8);

		xianshuCountMap.put("100100000", 7);
		gongxianFenCountMap.put("100100000", 8);

		xianshuCountMap.put("030000000", 7);
		gongxianFenCountMap.put("030000000", 8);

		xianshuCountMap.put("021000000", 7);
		gongxianFenCountMap.put("021000000", 8);

		xianshuCountMap.put("012000000", 7);
		gongxianFenCountMap.put("012000000", 8);

		xianshuCountMap.put("010100000", 7);
		gongxianFenCountMap.put("010100000", 8);

		xianshuCountMap.put("002000000", 7);
		gongxianFenCountMap.put("002000000", 8);

		xianshuCountMap.put("000100000", 7);
		gongxianFenCountMap.put("000100000", 8);

		xianshuCountMap.put("120100000", 7);
		gongxianFenCountMap.put("120100000", 12);

		xianshuCountMap.put("111100000", 7);
		gongxianFenCountMap.put("111100000", 12);

		xianshuCountMap.put("101100000", 7);
		gongxianFenCountMap.put("101100000", 12);

		xianshuCountMap.put("020100000", 7);
		gongxianFenCountMap.put("020100000", 12);

		xianshuCountMap.put("011100000", 7);
		gongxianFenCountMap.put("011100000", 12);

		xianshuCountMap.put("001100000", 7);
		gongxianFenCountMap.put("001100000", 12);

		xianshuCountMap.put("400100000", 7);
		gongxianFenCountMap.put("400100000", 12);

		xianshuCountMap.put("310100000", 7);
		gongxianFenCountMap.put("310100000", 12);

		xianshuCountMap.put("301100000", 7);
		gongxianFenCountMap.put("301100000", 12);

		xianshuCountMap.put("220100000", 7);
		gongxianFenCountMap.put("220100000", 12);

		xianshuCountMap.put("211100000", 7);
		gongxianFenCountMap.put("211100000", 12);

		xianshuCountMap.put("201100000", 7);
		gongxianFenCountMap.put("201100000", 12);

		xianshuCountMap.put("121100000", 7);
		gongxianFenCountMap.put("121100000", 16);
	}

	/**
	 * 计算八线倍数及贡献分
	 */
	private static void calculateBaXian() {
		// 103
		xianshuCountMap.put("500100000", 8);
		gongxianFenCountMap.put("500100000", 16);

        xianshuCountMap.put("231000000", 8);
        gongxianFenCountMap.put("231000000", 16);

        xianshuCountMap.put("321000000", 8);
        gongxianFenCountMap.put("321000000", 16);


		xianshuCountMap.put("000010000", 8);
		gongxianFenCountMap.put("000010000", 16);

		xianshuCountMap.put("501000000", 8);
		gongxianFenCountMap.put("501000000", 16);

		xianshuCountMap.put("210010000", 8);
		gongxianFenCountMap.put("210010000", 16);

		xianshuCountMap.put("110010000", 8);
		gongxianFenCountMap.put("110010000", 16);

		xianshuCountMap.put("010010000", 8);
		gongxianFenCountMap.put("010010000", 16);

		xianshuCountMap.put("300010000", 8);
		gongxianFenCountMap.put("300010000", 16);

		xianshuCountMap.put("200010000", 8);
		gongxianFenCountMap.put("200010000", 16);

		xianshuCountMap.put("100010000", 8);
		gongxianFenCountMap.put("100010000", 16);

		xianshuCountMap.put("000200000", 8);
		gongxianFenCountMap.put("000200000", 16);

		xianshuCountMap.put("210200000", 8);
		gongxianFenCountMap.put("210200000", 16);

		xianshuCountMap.put("110200000", 8);
		gongxianFenCountMap.put("110200000", 16);

		xianshuCountMap.put("010200000", 8);
		gongxianFenCountMap.put("010200000", 16);

		xianshuCountMap.put("300200000", 8);
		gongxianFenCountMap.put("300200000", 16);

		xianshuCountMap.put("200200000", 8);
		gongxianFenCountMap.put("200200000", 16);

		xianshuCountMap.put("100200000", 8);
		gongxianFenCountMap.put("100200000", 16);

		xianshuCountMap.put("003000000", 8);
		gongxianFenCountMap.put("003000000", 16);

		xianshuCountMap.put("113000000", 8);
		gongxianFenCountMap.put("113000000", 16);

		xianshuCountMap.put("013000000", 8);
		gongxianFenCountMap.put("013000000", 16);

		xianshuCountMap.put("303000000", 8);
		gongxianFenCountMap.put("303000000", 16);

		xianshuCountMap.put("203000000", 8);
		gongxianFenCountMap.put("203000000", 16);

		xianshuCountMap.put("103000000", 8);
		gongxianFenCountMap.put("103000000", 16);

		xianshuCountMap.put("040000000", 8);
		gongxianFenCountMap.put("040000000", 16);

		xianshuCountMap.put("140000000", 8);
		gongxianFenCountMap.put("140000000", 16);

		xianshuCountMap.put("030100000", 8);
		gongxianFenCountMap.put("030100000", 16);

		xianshuCountMap.put("031000000", 8);
		gongxianFenCountMap.put("031000000", 16);

		xianshuCountMap.put("131000000", 8);
		gongxianFenCountMap.put("131000000", 16);

		xianshuCountMap.put("330000000", 8);
		gongxianFenCountMap.put("330000000", 16);

		xianshuCountMap.put("420000000", 8);
		gongxianFenCountMap.put("420000000", 16);

		xianshuCountMap.put("510000000", 8);
		gongxianFenCountMap.put("510000000", 16);

		xianshuCountMap.put("600000000", 8);
		gongxianFenCountMap.put("600000000", 16);

		xianshuCountMap.put("500100000", 8);
		gongxianFenCountMap.put("500100000", 16);

		xianshuCountMap.put("002100000", 8);
		gongxianFenCountMap.put("002100000", 16);

		xianshuCountMap.put("212100000", 8);
		gongxianFenCountMap.put("212100000", 16);

		xianshuCountMap.put("112100000", 8);
		gongxianFenCountMap.put("112100000", 16);

		xianshuCountMap.put("012100000", 8);
		gongxianFenCountMap.put("012100000", 16);

		xianshuCountMap.put("302100000", 8);
		gongxianFenCountMap.put("302100000", 16);

		xianshuCountMap.put("202100000", 8);
		gongxianFenCountMap.put("202100000", 16);

		xianshuCountMap.put("102100000", 8);
		gongxianFenCountMap.put("102100000", 16);

		xianshuCountMap.put("401100000", 8);
		gongxianFenCountMap.put("401100000", 16);

		xianshuCountMap.put("411000000", 8);
		gongxianFenCountMap.put("411000000", 16);

		xianshuCountMap.put("021100000", 8);
		gongxianFenCountMap.put("021100000", 16);

		xianshuCountMap.put("311100000", 8);
		gongxianFenCountMap.put("311100000", 16);

		xianshuCountMap.put("221100000", 8);
		gongxianFenCountMap.put("221100000", 16);

		xianshuCountMap.put("121100000", 8);
		gongxianFenCountMap.put("121100000", 16);

		xianshuCountMap.put("030100000", 8);
		gongxianFenCountMap.put("030100000", 16);

		xianshuCountMap.put("130100000", 8);
		gongxianFenCountMap.put("130100000", 16);

		xianshuCountMap.put("230100000", 8);
		gongxianFenCountMap.put("230100000", 16);

		xianshuCountMap.put("320100000", 8);
		gongxianFenCountMap.put("320100000", 16);

		xianshuCountMap.put("410100000", 8);
		gongxianFenCountMap.put("410100000", 16);

		xianshuCountMap.put("312000000", 8);
		gongxianFenCountMap.put("312000000", 16);

		xianshuCountMap.put("222000000", 8);
		gongxianFenCountMap.put("222000000", 16);

		xianshuCountMap.put("122000000", 8);
		gongxianFenCountMap.put("122000000", 16);

		xianshuCountMap.put("201010000", 8);
		gongxianFenCountMap.put("201010000", 16);

		xianshuCountMap.put("101010000", 8);
		gongxianFenCountMap.put("101010000", 16);

		xianshuCountMap.put("402000000", 8);
		gongxianFenCountMap.put("402000000", 16);

		xianshuCountMap.put("400200000", 8);
		gongxianFenCountMap.put("400200000", 20);

		xianshuCountMap.put("022000000", 8);
		gongxianFenCountMap.put("022000000", 20);

		xianshuCountMap.put("001200000", 8);
		gongxianFenCountMap.put("001200000", 20);

		xianshuCountMap.put("020200000", 8);
		gongxianFenCountMap.put("020200000", 20);

		xianshuCountMap.put("001010000", 8);
		gongxianFenCountMap.put("001010000", 20);

		xianshuCountMap.put("001200000", 8);
		gongxianFenCountMap.put("001200000", 20);

		xianshuCountMap.put("211010000", 8);
		gongxianFenCountMap.put("211010000", 20);

		xianshuCountMap.put("201200000", 8);
		gongxianFenCountMap.put("201200000", 20);

		xianshuCountMap.put("120200000", 8);
		gongxianFenCountMap.put("120200000", 20);

		xianshuCountMap.put("111010000", 8);
		gongxianFenCountMap.put("111010000", 20);

		xianshuCountMap.put("111200000", 8);
		gongxianFenCountMap.put("111200000", 20);

		xianshuCountMap.put("101200000", 8);
		gongxianFenCountMap.put("101200000", 20);

		xianshuCountMap.put("011200000", 8);
		gongxianFenCountMap.put("011200000", 20);

		xianshuCountMap.put("011010000", 8);
		gongxianFenCountMap.put("011010000", 20);

		xianshuCountMap.put("301010000", 8);
		gongxianFenCountMap.put("301010000", 20);

		xianshuCountMap.put("120010000", 8);
		gongxianFenCountMap.put("120010000", 20);

		xianshuCountMap.put("400010000", 8);
		gongxianFenCountMap.put("400010000", 20);

		xianshuCountMap.put("020010000", 8);
		gongxianFenCountMap.put("020010000", 20);

		xianshuCountMap.put("310010000", 8);
		gongxianFenCountMap.put("310010000", 20);

		xianshuCountMap.put("220010000", 8);
		gongxianFenCountMap.put("220010000", 20);

		xianshuCountMap.put("000110000", 8);
		gongxianFenCountMap.put("000110000", 24);

		xianshuCountMap.put("210110000", 8);
		gongxianFenCountMap.put("210110000", 24);

		xianshuCountMap.put("110110000", 8);
		gongxianFenCountMap.put("110110000", 24);

		xianshuCountMap.put("010110000", 8);
		gongxianFenCountMap.put("010110000", 24);

		xianshuCountMap.put("300110000", 8);
		gongxianFenCountMap.put("300110000", 24);

		xianshuCountMap.put("200110000", 8);
		gongxianFenCountMap.put("200110000", 24);

		xianshuCountMap.put("100110000", 8);
		gongxianFenCountMap.put("100110000", 24);

		xianshuCountMap.put("002010000", 8);
		gongxianFenCountMap.put("002010000", 24);

		xianshuCountMap.put("212010000", 8);
		gongxianFenCountMap.put("212010000", 24);

		xianshuCountMap.put("112010000", 8);
		gongxianFenCountMap.put("112010000", 24);

		xianshuCountMap.put("012010000", 8);
		gongxianFenCountMap.put("012010000", 24);

		xianshuCountMap.put("202010000", 8);
		gongxianFenCountMap.put("202010000", 24);

		xianshuCountMap.put("102010000", 8);
		gongxianFenCountMap.put("102010000", 24);

		xianshuCountMap.put("401010000", 8);
		gongxianFenCountMap.put("401010000", 24);

		xianshuCountMap.put("021010000", 8);
		gongxianFenCountMap.put("021010000", 24);

		xianshuCountMap.put("121010000", 8);
		gongxianFenCountMap.put("121010000", 24);

		xianshuCountMap.put("030010000", 8);
		gongxianFenCountMap.put("030010000", 24);

		xianshuCountMap.put("130010000", 8);
		gongxianFenCountMap.put("130010000", 24);

		xianshuCountMap.put("501010000", 8);
		gongxianFenCountMap.put("501010000", 24);

		xianshuCountMap.put("100110000", 8);
		gongxianFenCountMap.put("100110000", 24);

		xianshuCountMap.put("211110000", 8);
		gongxianFenCountMap.put("211110000", 28);

		xianshuCountMap.put("111110000", 8);
		gongxianFenCountMap.put("111110000", 28);

		xianshuCountMap.put("011110000", 8);
		gongxianFenCountMap.put("011110000", 28);

		xianshuCountMap.put("101110000", 8);
		gongxianFenCountMap.put("101110000", 28);

		xianshuCountMap.put("301110000", 8);
		gongxianFenCountMap.put("301110000", 28);

		xianshuCountMap.put("201110000", 8);
		gongxianFenCountMap.put("201110000", 28);

		xianshuCountMap.put("400110000", 8);
		gongxianFenCountMap.put("400110000", 28);

		xianshuCountMap.put("020110000", 8);
		gongxianFenCountMap.put("020110000", 28);

		xianshuCountMap.put("120110000", 8);
		gongxianFenCountMap.put("120110000", 28);

		xianshuCountMap.put("001110000", 8);
		gongxianFenCountMap.put("001110000", 28);
	}

	/**
	 * 计算九线倍数及贡献分
	 */
	private static void calculateJiuXian() {
		// 117


        xianshuCountMap.put("141000000", 9);
        gongxianFenCountMap.put("141000000", 32);

		xianshuCountMap.put("601000000", 9);
		gongxianFenCountMap.put("601000000", 32);

		xianshuCountMap.put("400001000", 9);
		gongxianFenCountMap.put("400001000", 32);

		xianshuCountMap.put("310001000", 9);
		gongxianFenCountMap.put("310001000", 32);

		xianshuCountMap.put("300001000", 9);
		gongxianFenCountMap.put("300001000", 32);

		xianshuCountMap.put("210001000", 9);
		gongxianFenCountMap.put("210001000", 32);

		xianshuCountMap.put("200020000", 9);
		gongxianFenCountMap.put("200020000", 32);

		xianshuCountMap.put("200001000", 9);
		gongxianFenCountMap.put("200001000", 32);

		xianshuCountMap.put("100020000", 9);
		gongxianFenCountMap.put("100020000", 32);

		xianshuCountMap.put("100001000", 9);
		gongxianFenCountMap.put("100001000", 32);

		xianshuCountMap.put("110020000", 9);
		gongxianFenCountMap.put("110020000", 32);

		xianshuCountMap.put("110001000", 9);
		gongxianFenCountMap.put("110001000", 32);

		xianshuCountMap.put("100300000", 9);
		gongxianFenCountMap.put("100300000", 32);

		xianshuCountMap.put("100210000", 9);
		gongxianFenCountMap.put("100210000", 32);

		xianshuCountMap.put("050000000", 9);
		gongxianFenCountMap.put("050000000", 32);

		xianshuCountMap.put("104000000", 9);
		gongxianFenCountMap.put("104000000", 32);

		xianshuCountMap.put("014000000", 9);
		gongxianFenCountMap.put("014000000", 32);

		xianshuCountMap.put("041000000", 9);
		gongxianFenCountMap.put("041000000", 32);

		xianshuCountMap.put("103010000", 9);
		gongxianFenCountMap.put("103010000", 32);

		xianshuCountMap.put("040100000", 9);
		gongxianFenCountMap.put("040100000", 32);

		xianshuCountMap.put("110300000", 9);
		gongxianFenCountMap.put("110300000", 32);

		xianshuCountMap.put("102200000", 9);
		gongxianFenCountMap.put("102200000", 32);

		xianshuCountMap.put("022100000", 9);
		gongxianFenCountMap.put("022100000", 32);

		xianshuCountMap.put("031010000", 9);
		gongxianFenCountMap.put("031010000", 32);

		xianshuCountMap.put("032000000", 9);
		gongxianFenCountMap.put("032000000", 32);

		xianshuCountMap.put("010300000", 9);
		gongxianFenCountMap.put("010300000", 32);

		xianshuCountMap.put("010020000", 9);
		gongxianFenCountMap.put("010020000", 32);

		xianshuCountMap.put("010300000", 9);
		gongxianFenCountMap.put("010300000", 32);

		xianshuCountMap.put("010001000", 9);
		gongxianFenCountMap.put("010001000", 32);

		xianshuCountMap.put("030200000", 9);
		gongxianFenCountMap.put("030200000", 32);

		xianshuCountMap.put("031010000", 9);
		gongxianFenCountMap.put("031010000", 32);

		xianshuCountMap.put("030200000", 9);
		gongxianFenCountMap.put("030200000", 32);

		xianshuCountMap.put("030110000", 9);
		gongxianFenCountMap.put("030110000", 32);

		xianshuCountMap.put("021110000", 9);
		gongxianFenCountMap.put("021110000", 32);

		xianshuCountMap.put("023000000", 9);
		gongxianFenCountMap.put("023000000", 32);

		xianshuCountMap.put("022010000", 9);
		gongxianFenCountMap.put("022010000", 32);

		xianshuCountMap.put("021200000", 9);
		gongxianFenCountMap.put("021200000", 32);

		xianshuCountMap.put("010001000", 9);
		gongxianFenCountMap.put("010001000", 32);

		xianshuCountMap.put("004000000", 9);
		gongxianFenCountMap.put("004000000", 32);

		xianshuCountMap.put("003100000", 9);
		gongxianFenCountMap.put("003100000", 32);

        xianshuCountMap.put("013100000", 9);
        gongxianFenCountMap.put("013100000", 32);

		xianshuCountMap.put("003010000", 9);
		gongxianFenCountMap.put("003010000", 32);

		xianshuCountMap.put("014000000", 9);
		gongxianFenCountMap.put("014000000", 32);

		xianshuCountMap.put("012200000", 9);
		gongxianFenCountMap.put("012200000", 32);

		xianshuCountMap.put("002200000", 9);
		gongxianFenCountMap.put("002200000", 32);

		xianshuCountMap.put("002110000", 9);
		gongxianFenCountMap.put("002110000", 32);

		xianshuCountMap.put("000300000", 9);
		gongxianFenCountMap.put("000300000", 32);

		xianshuCountMap.put("000210000", 9);
		gongxianFenCountMap.put("000210000", 32);

		xianshuCountMap.put("001210000", 9);
		gongxianFenCountMap.put("001210000", 32);

		xianshuCountMap.put("000020000", 9);
		gongxianFenCountMap.put("000020000", 32);

		xianshuCountMap.put("000001000", 9);
		gongxianFenCountMap.put("000001000", 32);

		xianshuCountMap.put("010300000", 9);
		gongxianFenCountMap.put("010300000", 32);

        xianshuCountMap.put("103100000", 9);
        gongxianFenCountMap.put("103100000", 32);

		xianshuCountMap.put("010210000", 9);
		gongxianFenCountMap.put("010210000", 32);

		xianshuCountMap.put("400001000", 9);
		gongxianFenCountMap.put("400001000", 36);

		xianshuCountMap.put("310001000", 9);
		gongxianFenCountMap.put("310001000", 36);

		xianshuCountMap.put("301001000", 9);
		gongxianFenCountMap.put("301001000", 36);

		xianshuCountMap.put("220001000", 9);
		gongxianFenCountMap.put("220001000", 36);

		xianshuCountMap.put("101020000", 9);
		gongxianFenCountMap.put("101020000", 36);

		xianshuCountMap.put("211001000", 9);
		gongxianFenCountMap.put("211001000", 36);

		xianshuCountMap.put("201020000", 9);
		gongxianFenCountMap.put("201020000", 36);

		xianshuCountMap.put("201001000", 9);
		gongxianFenCountMap.put("201001000", 36);

		xianshuCountMap.put("120001000", 9);
		gongxianFenCountMap.put("120001000", 36);

		xianshuCountMap.put("111001000", 9);
		gongxianFenCountMap.put("111001000", 36);

		xianshuCountMap.put("101001000", 9);
		gongxianFenCountMap.put("101001000", 36);

		xianshuCountMap.put("020020000", 9);
		gongxianFenCountMap.put("020020000", 36);

		xianshuCountMap.put("020001000", 9);
		gongxianFenCountMap.put("020001000", 36);

		xianshuCountMap.put("001001000", 9);
		gongxianFenCountMap.put("001001000", 36);

		xianshuCountMap.put("020020000", 9);
		gongxianFenCountMap.put("020020000", 36);

		xianshuCountMap.put("020001000", 9);
		gongxianFenCountMap.put("020001000", 36);

		xianshuCountMap.put("011020000", 9);
		gongxianFenCountMap.put("011020000", 36);

		xianshuCountMap.put("011001000", 9);
		gongxianFenCountMap.put("011001000", 36);

		xianshuCountMap.put("001300000", 9);
		gongxianFenCountMap.put("001300000", 36);

		xianshuCountMap.put("001300000", 9);
		gongxianFenCountMap.put("001300000", 36);

		xianshuCountMap.put("001020000", 9);
		gongxianFenCountMap.put("001020000", 36);

		xianshuCountMap.put("001001000", 9);
		gongxianFenCountMap.put("001001000", 36);

		xianshuCountMap.put("001020000", 9);
		gongxianFenCountMap.put("001020000", 36);

		xianshuCountMap.put("202001000", 9);
		gongxianFenCountMap.put("202001000", 40);

		xianshuCountMap.put("200101000", 9);
		gongxianFenCountMap.put("200101000", 40);

		xianshuCountMap.put("100120000", 9);
		gongxianFenCountMap.put("100120000", 40);

		xianshuCountMap.put("100101000", 9);
		gongxianFenCountMap.put("100101000", 40);

		xianshuCountMap.put("121001000", 9);
		gongxianFenCountMap.put("121001000", 40);

		xianshuCountMap.put("112001000", 9);
		gongxianFenCountMap.put("112001000", 40);

		xianshuCountMap.put("110101000", 9);
		gongxianFenCountMap.put("110101000", 40);

		xianshuCountMap.put("102001000", 9);
		gongxianFenCountMap.put("102001000", 40);

		xianshuCountMap.put("130001000", 9);
		gongxianFenCountMap.put("130001000", 40);

		xianshuCountMap.put("030001000", 9);
		gongxianFenCountMap.put("030001000", 40);

		xianshuCountMap.put("021001000", 9);
		gongxianFenCountMap.put("021001000", 40);

		xianshuCountMap.put("010120000", 9);
		gongxianFenCountMap.put("010120000", 40);

		xianshuCountMap.put("010101000", 9);
		gongxianFenCountMap.put("010101000", 40);

		xianshuCountMap.put("030001000", 9);
		gongxianFenCountMap.put("030001000", 40);

		xianshuCountMap.put("021001000", 9);
		gongxianFenCountMap.put("021001000", 40);

		xianshuCountMap.put("012001000", 9);
		gongxianFenCountMap.put("012001000", 40);

		xianshuCountMap.put("002001000", 9);
		gongxianFenCountMap.put("002001000", 40);

		xianshuCountMap.put("000120000", 9);
		gongxianFenCountMap.put("000120000", 40);

		xianshuCountMap.put("000120000", 9);
		gongxianFenCountMap.put("000120000", 40);

		xianshuCountMap.put("000101000", 9);
		gongxianFenCountMap.put("000101000", 40);

		xianshuCountMap.put("100101000", 9);
		gongxianFenCountMap.put("100101000", 40);

		xianshuCountMap.put("002020000", 9);
		gongxianFenCountMap.put("002020000", 40);

		xianshuCountMap.put("201101000", 9);
		gongxianFenCountMap.put("201101000", 44);

		xianshuCountMap.put("101101000", 9);
		gongxianFenCountMap.put("101101000", 44);

		xianshuCountMap.put("031001000", 9);
		gongxianFenCountMap.put("031001000", 44);

		xianshuCountMap.put("020101000", 9);
		gongxianFenCountMap.put("020101000", 44);

		xianshuCountMap.put("011101000", 9);
		gongxianFenCountMap.put("011101000", 44);

		xianshuCountMap.put("001120000", 9);
		gongxianFenCountMap.put("001120000", 44);

		xianshuCountMap.put("001101000", 9);
		gongxianFenCountMap.put("001101000", 44);

		xianshuCountMap.put("100011000", 9);
		gongxianFenCountMap.put("100011000", 48);

		xianshuCountMap.put("110011000", 9);
		gongxianFenCountMap.put("110011000", 48);

		xianshuCountMap.put("010011000", 9);
		gongxianFenCountMap.put("010011000", 48);

        xianshuCountMap.put("100201000", 9);
        gongxianFenCountMap.put("100201000", 48);

		xianshuCountMap.put("200011000", 9);
		gongxianFenCountMap.put("200011000", 48);

		xianshuCountMap.put("003001000", 9);
		gongxianFenCountMap.put("003001000", 48);

		xianshuCountMap.put("021101000", 9);
		gongxianFenCountMap.put("021101000", 48);

		xianshuCountMap.put("010011000", 9);
		gongxianFenCountMap.put("010011000", 48);

		xianshuCountMap.put("000201000", 9);
		gongxianFenCountMap.put("000201000", 48);

		xianshuCountMap.put("000011000", 9);
		gongxianFenCountMap.put("000011000", 48);

		xianshuCountMap.put("010201000", 9);
		gongxianFenCountMap.put("010201000", 48);

		xianshuCountMap.put("101011000", 9);
		gongxianFenCountMap.put("101011000", 52);

		xianshuCountMap.put("020011000", 9);
		gongxianFenCountMap.put("020011000", 52);

		xianshuCountMap.put("011011000", 9);
		gongxianFenCountMap.put("011011000", 52);

		xianshuCountMap.put("001011000", 9);
		gongxianFenCountMap.put("001011000", 52);

		xianshuCountMap.put("000111000", 9);
		gongxianFenCountMap.put("000111000", 56);

	}

	/**
	 * 计算十线倍数及贡献分
	 */
	private static void calculateShiXian() {
		// 74
		xianshuCountMap.put("300000100", 10);
		gongxianFenCountMap.put("300000100", 64);

		xianshuCountMap.put("210000100", 10);
		gongxianFenCountMap.put("210000100", 64);

		xianshuCountMap.put("200002000", 10);
		gongxianFenCountMap.put("200002000", 64);

		xianshuCountMap.put("200000100", 10);
		gongxianFenCountMap.put("200000100", 64);

		xianshuCountMap.put("110002000", 10);
		gongxianFenCountMap.put("110002000", 64);

		xianshuCountMap.put("110000100", 10);
		gongxianFenCountMap.put("110000100", 64);

		xianshuCountMap.put("010000100", 10);
		gongxianFenCountMap.put("010000100", 64);

		xianshuCountMap.put("010002000", 10);
		gongxianFenCountMap.put("010002000", 64);

		xianshuCountMap.put("000400000", 10);
		gongxianFenCountMap.put("000400000", 64);

		xianshuCountMap.put("000310000", 10);
		gongxianFenCountMap.put("000310000", 64);

		xianshuCountMap.put("000220000", 10);
		gongxianFenCountMap.put("000220000", 64);

		xianshuCountMap.put("000030000", 10);
		gongxianFenCountMap.put("000030000", 64);

		xianshuCountMap.put("000021000", 10);
		gongxianFenCountMap.put("000021000", 64);

		xianshuCountMap.put("000002000", 10);
		gongxianFenCountMap.put("000002000", 64);

		xianshuCountMap.put("000000100", 10);
		gongxianFenCountMap.put("000000100", 64);

		xianshuCountMap.put("005000000", 10);
		gongxianFenCountMap.put("005000000", 64);

		xianshuCountMap.put("100000100", 10);
		gongxianFenCountMap.put("100000100", 64);

		xianshuCountMap.put("100002000", 10);
		gongxianFenCountMap.put("100002000", 64);

		xianshuCountMap.put("120000100", 10);
		gongxianFenCountMap.put("120000100", 64);

		xianshuCountMap.put("011000100", 10);
		gongxianFenCountMap.put("011000100", 68);

		xianshuCountMap.put("001030000", 10);
		gongxianFenCountMap.put("001030000", 68);

		xianshuCountMap.put("001002000", 10);
		gongxianFenCountMap.put("001002000", 68);

		xianshuCountMap.put("400000100", 10);
		gongxianFenCountMap.put("400000100", 68);

		xianshuCountMap.put("310000100", 10);
		gongxianFenCountMap.put("310000100", 68);

		xianshuCountMap.put("201000100", 10);
		gongxianFenCountMap.put("201000100", 68);

		xianshuCountMap.put("120000100", 10);
		gongxianFenCountMap.put("120000100", 68);

		xianshuCountMap.put("101000100", 10);
		gongxianFenCountMap.put("101000100", 68);

		xianshuCountMap.put("020000100", 10);
		gongxianFenCountMap.put("020000100", 68);

		xianshuCountMap.put("001030000", 10);
		gongxianFenCountMap.put("001030000", 68);

		xianshuCountMap.put("001002000", 10);
		gongxianFenCountMap.put("001002000", 68);

		xianshuCountMap.put("001000100", 10);
		gongxianFenCountMap.put("001000100", 68);

		xianshuCountMap.put("011000100", 10);
		gongxianFenCountMap.put("011000100", 68);

		xianshuCountMap.put("111000100", 10);
		gongxianFenCountMap.put("111000100", 68);

		xianshuCountMap.put("101000100", 10);
		gongxianFenCountMap.put("101000100", 68);

		xianshuCountMap.put("200100100", 10);
		gongxianFenCountMap.put("200100100", 72);

		xianshuCountMap.put("110100100", 10);
		gongxianFenCountMap.put("110100100", 72);

		xianshuCountMap.put("100100100", 10);
		gongxianFenCountMap.put("100100100", 72);

		xianshuCountMap.put("010100100", 10);
		gongxianFenCountMap.put("010100100", 72);

		xianshuCountMap.put("000130000", 10);
		gongxianFenCountMap.put("000130000", 72);

		xianshuCountMap.put("000102000", 10);
		gongxianFenCountMap.put("000102000", 72);

		xianshuCountMap.put("000100100", 10);
		gongxianFenCountMap.put("000100100", 72);

		xianshuCountMap.put("002002000", 10);
		gongxianFenCountMap.put("002002000", 72);

		xianshuCountMap.put("200100100", 10);
		gongxianFenCountMap.put("200100100", 72);

		xianshuCountMap.put("110100100", 10);
		gongxianFenCountMap.put("110100100", 72);

		xianshuCountMap.put("102000100", 10);
		gongxianFenCountMap.put("102000100", 72);

		xianshuCountMap.put("030000100", 10);
		gongxianFenCountMap.put("030000100", 72);

		xianshuCountMap.put("021000100", 10);
		gongxianFenCountMap.put("021000100", 72);

		xianshuCountMap.put("012000100", 10);
		gongxianFenCountMap.put("012000100", 72);

		xianshuCountMap.put("002002000", 10);
		gongxianFenCountMap.put("002002000", 72);

		xianshuCountMap.put("002000100", 10);
		gongxianFenCountMap.put("002000100", 72);

		xianshuCountMap.put("101100100", 10);
		gongxianFenCountMap.put("101100100", 76);

		xianshuCountMap.put("020100100", 10);
		gongxianFenCountMap.put("020100100", 76);

		xianshuCountMap.put("001100100", 10);
		gongxianFenCountMap.put("001100100", 76);

		xianshuCountMap.put("020100100", 10);
		gongxianFenCountMap.put("020100100", 76);

		xianshuCountMap.put("110010100", 10);
		gongxianFenCountMap.put("110010100", 80);

		xianshuCountMap.put("100010100", 10);
		gongxianFenCountMap.put("100010100", 80);

		xianshuCountMap.put("010200100", 10);
		gongxianFenCountMap.put("010200100", 80);

		xianshuCountMap.put("010010100", 10);
		gongxianFenCountMap.put("010010100", 80);

		xianshuCountMap.put("003000100", 10);
		gongxianFenCountMap.put("003000100", 80);

		xianshuCountMap.put("002100100", 10);
		gongxianFenCountMap.put("002100100", 80);

		xianshuCountMap.put("000200100", 10);
		gongxianFenCountMap.put("000200100", 80);

		xianshuCountMap.put("000010100", 10);
		gongxianFenCountMap.put("000010100", 80);

		xianshuCountMap.put("200010100", 10);
		gongxianFenCountMap.put("200010100", 80);

		xianshuCountMap.put("001200100", 10);
		gongxianFenCountMap.put("001200100", 84);

		xianshuCountMap.put("001010100", 10);
		gongxianFenCountMap.put("001010100", 84);

		xianshuCountMap.put("002010100", 10);
		gongxianFenCountMap.put("002010100", 88);

		xianshuCountMap.put("000110100", 10);
		gongxianFenCountMap.put("000110100", 88);

		xianshuCountMap.put("200001100", 10);
		gongxianFenCountMap.put("200001100", 96);

		xianshuCountMap.put("100001100", 10);
		gongxianFenCountMap.put("100001100", 96);

		xianshuCountMap.put("000020100", 10);
		gongxianFenCountMap.put("000020100", 96);

		xianshuCountMap.put("000001100", 10);
		gongxianFenCountMap.put("000001100", 96);

        xianshuCountMap.put("010001100", 10);
        gongxianFenCountMap.put("010001100", 96);

		xianshuCountMap.put("001001100", 10);
		gongxianFenCountMap.put("001001100", 100);

		xianshuCountMap.put("000101100", 10);
		gongxianFenCountMap.put("000101100", 104);

		xianshuCountMap.put("000011100", 10);
		gongxianFenCountMap.put("000011100", 112);
	}

	/**
	 * 计算十一线倍数及贡献分
	 */
	private static void calculateShiyiXian() {
		// 43
		xianshuCountMap.put("000003000", 11);
		gongxianFenCountMap.put("000003000", 128);

		xianshuCountMap.put("100000200", 11);
		gongxianFenCountMap.put("100000200", 128);

		xianshuCountMap.put("010000200", 11);
		gongxianFenCountMap.put("010000200", 128);

		xianshuCountMap.put("010000010", 11);
		gongxianFenCountMap.put("010000010", 128);

		xianshuCountMap.put("200000010", 11);
		gongxianFenCountMap.put("200000010", 128);

		xianshuCountMap.put("110000010", 11);
		gongxianFenCountMap.put("110000010", 128);

		xianshuCountMap.put("000000010", 11);
		gongxianFenCountMap.put("000000010", 128);

		xianshuCountMap.put("000000200", 11);
		gongxianFenCountMap.put("000000200", 128);

		xianshuCountMap.put("300000010", 11);
		gongxianFenCountMap.put("300000010", 128);

		xianshuCountMap.put("210000010", 11);
		gongxianFenCountMap.put("210000010", 128);

		xianshuCountMap.put("100000010", 11);
		gongxianFenCountMap.put("100000010", 128);

		xianshuCountMap.put("001000200", 11);
		gongxianFenCountMap.put("001000200", 132);

		xianshuCountMap.put("001000010", 11);
		gongxianFenCountMap.put("001000010", 132);

		xianshuCountMap.put("120000010", 11);
		gongxianFenCountMap.put("120000010", 132);

		xianshuCountMap.put("400000010", 11);
		gongxianFenCountMap.put("400000010", 132);

		xianshuCountMap.put("101000010", 11);
		gongxianFenCountMap.put("101000010", 132);

		xianshuCountMap.put("201000010", 11);
		gongxianFenCountMap.put("201000010", 132);

		xianshuCountMap.put("020000010", 11);
		gongxianFenCountMap.put("020000010", 132);

		xianshuCountMap.put("011000010", 11);
		gongxianFenCountMap.put("011000010", 132);

		xianshuCountMap.put("111000010", 11);
		gongxianFenCountMap.put("111000010", 132);

		xianshuCountMap.put("000100200", 11);
		gongxianFenCountMap.put("000100200", 136);

		xianshuCountMap.put("010100010", 11);
		gongxianFenCountMap.put("010100010", 136);

		xianshuCountMap.put("200100010", 11);
		gongxianFenCountMap.put("200100010", 136);

		xianshuCountMap.put("110100010", 11);
		gongxianFenCountMap.put("110100010", 136);

		xianshuCountMap.put("000100010", 11);
		gongxianFenCountMap.put("000100010", 136);

		xianshuCountMap.put("102000010", 11);
		gongxianFenCountMap.put("102000010", 136);

		xianshuCountMap.put("100100010", 11);
		gongxianFenCountMap.put("100100010", 136);

		xianshuCountMap.put("030000010", 11);
		gongxianFenCountMap.put("030000010", 136);

		xianshuCountMap.put("021000010", 11);
		gongxianFenCountMap.put("021000010", 136);

		xianshuCountMap.put("002000010", 11);
		gongxianFenCountMap.put("002000010", 136);

		xianshuCountMap.put("001100010", 11);
		gongxianFenCountMap.put("001100010", 140);

		xianshuCountMap.put("200010010", 11);
		gongxianFenCountMap.put("200010010", 144);

        xianshuCountMap.put("000200010", 11);
        gongxianFenCountMap.put("000200010", 144);

		xianshuCountMap.put("100010010", 11);
		gongxianFenCountMap.put("100010010", 144);

		xianshuCountMap.put("010200010", 11);
		gongxianFenCountMap.put("010200010", 144);

		xianshuCountMap.put("010010010", 11);
		gongxianFenCountMap.put("010010010", 144);

		xianshuCountMap.put("002100010", 11);
		gongxianFenCountMap.put("002100010", 144);

		xianshuCountMap.put("000010010", 11);
		gongxianFenCountMap.put("000010010", 144);

		xianshuCountMap.put("001010010", 11);
		gongxianFenCountMap.put("001010010", 148);

		xianshuCountMap.put("000110010", 11);
		gongxianFenCountMap.put("000110010", 152);

		xianshuCountMap.put("100001010", 11);
		gongxianFenCountMap.put("100001010", 160);

		xianshuCountMap.put("010001010", 11);
		gongxianFenCountMap.put("010001010", 160);

		xianshuCountMap.put("000020010", 11);
		gongxianFenCountMap.put("000020010", 160);



		xianshuCountMap.put("000001010", 11);
		gongxianFenCountMap.put("000001010", 160);

		xianshuCountMap.put("001001010", 11);
		gongxianFenCountMap.put("001001010", 164);

		xianshuCountMap.put("000101010", 11);
		gongxianFenCountMap.put("000101010", 168);
	}

	/**
	 * 计算十二线倍数及贡献分
	 */
	private static void calculateShierXian() {
		// 28
		xianshuCountMap.put("000000001", 12);
		gongxianFenCountMap.put("000000001", 256);

		xianshuCountMap.put("300000001", 12);
		gongxianFenCountMap.put("300000001", 256);

		xianshuCountMap.put("210000001", 12);
		gongxianFenCountMap.put("210000001", 256);

		xianshuCountMap.put("200000001", 12);
		gongxianFenCountMap.put("200000001", 256);

		xianshuCountMap.put("110000001", 12);
		gongxianFenCountMap.put("110000001", 256);

		xianshuCountMap.put("100000001", 12);
		gongxianFenCountMap.put("100000001", 256);

		xianshuCountMap.put("010000001", 12);
		gongxianFenCountMap.put("010000001", 256);

		xianshuCountMap.put("201000001", 12);
		gongxianFenCountMap.put("201000001", 260);

		xianshuCountMap.put("120000001", 12);
		gongxianFenCountMap.put("120000001", 260);

		xianshuCountMap.put("111000001", 12);
		gongxianFenCountMap.put("111000001", 260);

		xianshuCountMap.put("101000001", 12);
		gongxianFenCountMap.put("101000001", 260);

		xianshuCountMap.put("020000001", 12);
		gongxianFenCountMap.put("020000001", 260);

		xianshuCountMap.put("011000001", 12);
		gongxianFenCountMap.put("011000001", 260);

		xianshuCountMap.put("001000001", 12);
		gongxianFenCountMap.put("001000001", 260);

		xianshuCountMap.put("100100001", 12);
		gongxianFenCountMap.put("100100001", 264);

		xianshuCountMap.put("010100001", 12);
		gongxianFenCountMap.put("010100001", 264);

		xianshuCountMap.put("200100001", 12);
		gongxianFenCountMap.put("200100001", 264);

		xianshuCountMap.put("030000001", 12);
		gongxianFenCountMap.put("030000001", 264);

		xianshuCountMap.put("010100001", 12);
		gongxianFenCountMap.put("010100001", 264);

		xianshuCountMap.put("000100001", 12);
		gongxianFenCountMap.put("000100001", 264);

		xianshuCountMap.put("002000001", 12);
		gongxianFenCountMap.put("002000001", 264);

		xianshuCountMap.put("001100001", 12);
		gongxianFenCountMap.put("001100001", 268);

		xianshuCountMap.put("100010001", 12);
		gongxianFenCountMap.put("100010001", 272);

		xianshuCountMap.put("010010001", 12);
		gongxianFenCountMap.put("010010001", 272);

		xianshuCountMap.put("000200001", 12);
		gongxianFenCountMap.put("000200001", 272);

		xianshuCountMap.put("000010001", 12);
		gongxianFenCountMap.put("000010001", 272);

		xianshuCountMap.put("001010001", 12);
		gongxianFenCountMap.put("001010001", 276);

		xianshuCountMap.put("000110001", 12);
		gongxianFenCountMap.put("000110001", 280);
	}
}
