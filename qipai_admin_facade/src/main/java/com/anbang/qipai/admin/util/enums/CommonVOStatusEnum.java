package com.anbang.qipai.admin.util.enums;

/**
 * @author YaphetS
 * @date 2018/11/21
 */
public enum  CommonVOStatusEnum {
    TOKEN_NOT_EXIST("TOKEN不存在"),
    AGENTID_NOT_EXIST("推广员id不存在"),
    MEMBERID_NOT_EXIST("会员id不存在"),
    CODE_NOT_EXIST("code不存在")
    ;

    private String msg;

    CommonVOStatusEnum(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
