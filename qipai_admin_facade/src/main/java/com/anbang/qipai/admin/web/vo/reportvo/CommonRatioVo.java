package com.anbang.qipai.admin.web.vo.reportvo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 饼图比例vo
 */
public class CommonRatioVo {
    private String name;
    private int num;
    private double ratio;
    private List<MemberRatioVo> ratioVos = new ArrayList<>();

    public CommonRatioVo(){

    }

    public CommonRatioVo(String name) {
        this.name = name;
    }

    public CommonRatioVo(String name, int num, double ratio) {
        this.name = name;
        this.num = num;
        this.ratio = ratio;
    }

    public CommonRatioVo(String name, int num, double ratio, List<MemberRatioVo> ratioVos) {
        this.name = name;
        this.num = num;
        this.ratio = ratio;
        this.ratioVos = ratioVos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public List<MemberRatioVo> getRatioVos() {
        return ratioVos;
    }

    public void setRatioVos(List<MemberRatioVo> ratioVos) {
        this.ratioVos = ratioVos;
    }
}
