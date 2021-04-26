package com.anbang.qipai.admin.remote;

/**
 * @Author: 吴硕涵
 * @Date: 2018/12/3 3:33 PM
 * @Version 1.0
 */
public enum LotteryMoEnum {
    /**
     * 实物
     */
    ENTIRY("ENTIRY", "实体", "实物"),
    /**
     * 红包
     */
    HONGBAO("HONGBAO", "红包", "红包"),
    /**
     * 会员卡日卡
     */
    MEMBER_CARD_DAY("MEMBER_CARD_DAY", "会员卡日卡", "日卡"),
    /**
     * 周卡会员卡
     */
    MEMBER_CARD_WEAK("MEMBER_CARD_WEAK", "周卡会员卡", "周卡"),
    /**
     * 月卡会员卡
     */
    MEMBER_CARD_MONTH("MEMBER_CARD_MONTH", "月卡会员卡", "月卡"),
    /**
     * 季卡会员卡
     */
    MEMBER_CARD_SEASON("MEMBER_CARD_SEASON", "季卡会员卡", "季卡"),
    /**
     * 话费
     */
    PHONE_FEE("PHONE_FEE", "话费", "话费"),
    /**
     * 玉石
     */
    GOLD("GOLD", "玉石", "玉石"),


    GOlD1("GOLD1", "玉石", "玉石"),

    GOlD2("GOLD2", "玉石", "玉石");;

    private String Type;
    private String description;
    private String Name;

    LotteryMoEnum(String type, String description, String name) {
        Type = type;
        this.description = description;
        Name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
