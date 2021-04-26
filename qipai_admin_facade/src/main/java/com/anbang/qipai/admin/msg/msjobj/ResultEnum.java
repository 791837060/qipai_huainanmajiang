package com.anbang.qipai.admin.msg.msjobj;

/**
 * @author YaphetS
 * @date 2018/12/5
 */
public enum ResultEnum {
    DIANPAO_JU_RESULT("dianpaomajiang ju result"),
    FANGPAO_JU_RESULT("fangpaomajiang ju result"),
    RUIAN_JU_RESULT("ruianmajiang ju result"),
    WENZHOUMAJIANG_JU_RESULT("wenzhoumajiang ju result"),
    //SHUANGKOU_JU_REUSLT(),
    ;

    private String message;

    ResultEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
