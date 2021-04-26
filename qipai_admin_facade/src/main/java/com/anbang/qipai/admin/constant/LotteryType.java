package com.anbang.qipai.admin.constant;

public interface LotteryType {
    String HONG_BAO = "红包";
    String GOLD = "玉石";
    String PHONE_FEE = "话费";
    String ENTITY = "实物";
    String MEMBER_CARD_DAY = "日卡";
    String MEMBER_CARD_WEAK = "周卡";
    String MEMBER_CARD_MONTH = "月卡";
    String MEMBER_CARD_SEASON = "季卡";

    public static boolean isClubCard(String type) {
        if (type.equals("MEMBER_CARD_DAY") ||
                type.equals("MEMBER_CARD_WEAK") ||
                type.equals("MEMBER_CARD_MONTH") ||
                type.equals("MEMBER_CARD_SEASON")) {
            return true;
        }
        return false;
    }


    public static boolean exchangeAble(String type) {
        if (LotteryType.isValid(type)) {
            switch (type) {
                case ENTITY:
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }

    public static boolean isValid(String type) {
        switch (type) {
            case HONG_BAO:
            case GOLD:
            case PHONE_FEE:
            case ENTITY:
            case MEMBER_CARD_DAY:
            case MEMBER_CARD_WEAK:
            case MEMBER_CARD_MONTH:
            case MEMBER_CARD_SEASON:
                return true;
            default:
                return false;
        }
    }

}
