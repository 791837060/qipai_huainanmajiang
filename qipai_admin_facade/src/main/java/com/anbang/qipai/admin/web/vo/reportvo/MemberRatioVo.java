package com.anbang.qipai.admin.web.vo.reportvo;

/**
 * @Description:
 */
public class MemberRatioVo {
    private String cardType;  //最近一次冲卡的类型
    private String cardSource;  //卡的来源
    private int num;

    private double radio;  //所占比例


    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardSource() {
        return cardSource;
    }

    public void setCardSource(String cardSource) {
        this.cardSource = cardSource;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getRadio() {
        return radio;
    }

    public void setRadio(double radio) {
        this.radio = radio;
    }
}
