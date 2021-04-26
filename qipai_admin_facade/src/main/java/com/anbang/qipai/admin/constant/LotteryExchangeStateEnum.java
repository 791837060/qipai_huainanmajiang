package com.anbang.qipai.admin.constant;

public enum LotteryExchangeStateEnum {
    REFUSE("refuse","已驳回"),
    APPLYING("applying ","已申请"),
    ISSUED("issued","已发放")
    ;

    public static String getName(String code) {
        for (LotteryExchangeStateEnum list : LotteryExchangeStateEnum.values()) {
            if (list.getCode().equals(code)) {
                return list.getName();
            }
        }
        return "";
    }

    private String code;
    private String name;

    LotteryExchangeStateEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
