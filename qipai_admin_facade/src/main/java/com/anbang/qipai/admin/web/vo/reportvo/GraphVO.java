package com.anbang.qipai.admin.web.vo.reportvo;


import java.util.Arrays;
import java.util.List;

/**
 * 折线图
 * @author YaphetS
 * @date 2018/11/22
 */
public class GraphVO {
    int[] countByToday;
    int[] countByWeek;
    int[] countByMonth;

    public GraphVO() {
    }

    public GraphVO(int[] countByWeek, int[] countByMonth) {
        this.countByWeek = countByWeek;
        this.countByMonth = countByMonth;
    }

    public GraphVO(int[] countByToday, int[] countByWeek, int[] countByMonth) {
        this.countByToday = countByToday;
        this.countByWeek = countByWeek;
        this.countByMonth = countByMonth;
    }

    public int[] getCountByToday() {
        return countByToday;
    }

    public void setCountByToday(int[] countByToday) {
        this.countByToday = countByToday;
    }

    public int[] getCountByWeek() {
        return countByWeek;
    }

    public void setCountByWeek(int[] countByWeek) {
        this.countByWeek = countByWeek;
    }

    public int[] getCountByMonth() {
        return countByMonth;
    }

    public void setCountByMonth(int[] countByMonth) {
        this.countByMonth = countByMonth;
    }

    @Override
    public String toString() {
        return "GraphVO{" +
                "countByToday=" + Arrays.toString(countByToday) +
                ", countByWeek=" + Arrays.toString(countByWeek) +
                ", countByMonth=" + Arrays.toString(countByMonth) +
                '}';
    }
}
