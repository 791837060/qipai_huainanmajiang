package com.anbang.qipai.admin.remote;

import com.anbang.qipai.admin.constant.LotteryType;

public enum LotteryMoTypeEnum {

    /**
     * 实物
     */
    ENTIRY,
    /**
     * 红包
     */
    HONGBAO,
    /**
     * 会员卡日卡
     */
    MEMBER_CARD_DAY,
    /**
     * 周卡会员卡
     */
    MEMBER_CARD_WEAK,
    /**
     * 月卡会员卡
     */
    MEMBER_CARD_MONTH,
    /**
     * 季卡会员卡
     */
    MEMBER_CARD_SEASON,
    /**
     * 话费
     */
    PHONE_FEE,
    /**
     * 玉石
     */
    GOLD;

    public static String of(LotteryMoTypeEnum type) {
        switch (type) {
            case GOLD:
                return LotteryType.GOLD;
            case HONGBAO:
                return LotteryType.HONG_BAO;
            case PHONE_FEE:
                return LotteryType.PHONE_FEE;
            case ENTIRY:
                return LotteryType.ENTITY;
            case MEMBER_CARD_DAY:
                return LotteryType.MEMBER_CARD_DAY;
            case MEMBER_CARD_WEAK:
                return LotteryType.MEMBER_CARD_WEAK;
            case MEMBER_CARD_MONTH:
                return LotteryType.MEMBER_CARD_MONTH;
            case MEMBER_CARD_SEASON:
                return LotteryType.MEMBER_CARD_SEASON;
            default:
                return null;
        }
    }

}
