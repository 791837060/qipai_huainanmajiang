package com.anbang.qipai.admin.plan.bean.historicalresult;

/**
 * @author yins
 * @Description: 添加回放码
 */
public class GameHistoricalPanResultExpand extends GameHistoricalPanResult {
    private int codeStatus;  // 0无，1有
    private String code;  //放码

    public int getCodeStatus() {
        return codeStatus;
    }

    public void setCodeStatus(int codeStatus) {
        this.codeStatus = codeStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
