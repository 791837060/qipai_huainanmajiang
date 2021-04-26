package com.anbang.qipai.admin.constant;

/**
 * @author yins
 */
public class Constants {

    public static final String DEFAULTGAMETYPE = "三财神茶苑";

    public static final long DAY_MESC = 24 * 60 * 60 * 1000;

    //月份
    public static final String[] MONTHLIST = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

    //七日毫秒数
    public static final long SEVEN_DAY_MS = 7 * 24 * 60 * 60 * 1000;

    // 默认页数
    public static class PageSetup {
        public static final int DEFAULT_PAGE = 1;
        public static final int DEFAULT_SIZE = 20;
    }

    public static class Task {
        public static final String EXCHANGE_TYPE = "hongBaoDian";   //默认为红包点兑换
    }
}
